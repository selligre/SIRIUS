import pandas as pd
import psycopg2
from psycopg2.extras import execute_values
from sqlalchemy import create_engine
import socket
import sys
import traceback
import re

# Valeurs par défaut pour tester en local
DB_HOST_IP = 'localhost'
DB_HOST_PORT = '5432'


# Configuration de connexion PostgreSQL
POSTGRES_CONFIG = {
    'host': DB_HOST_IP,
    'database': 'fimafengdb',
    'user': 'fimafeng',
    'password': 'postgres',
    'port': DB_HOST_PORT
}

SILVER_PARQUET = 'data/silver.parquet'
TABLE_NAME = 'announce'


def load_and_prepare_data():
    """Charge et prépare les données du fichier silver.parquet"""
    print(f"Chargement du fichier {SILVER_PARQUET}...")
    df = pd.read_parquet(SILVER_PARQUET)
    
    print(f"Nombre de lignes: {len(df)}")
    print(f"Colonnes: {df.columns.tolist()}")
    
    # Conversion de publication_date en datetime si ce n'est pas déjà le cas
    df['publication_date'] = pd.to_datetime(df['publication_date'])
    
    # Conversion de type et status en types appropriés
    df['type'] = df['type'].astype(int)
    df['status'] = df['status'].astype(float)
    
    # Conversion datetime64[ns] pour les colonnes de date
    df['date_time_start'] = pd.to_datetime(df['date_time_start'])
    df['date_time_end'] = pd.to_datetime(df['date_time_end'])
    df['duration'] = df['duration'].astype(float)
    
    print("Préparation des données terminée.")
    
    return df


def sanitize_dataframe(df: pd.DataFrame) -> pd.DataFrame:
    """Normalize string-like columns and decode bytes safely to avoid encoding errors."""
    import pandas as _pd
    for col in df.select_dtypes(include=['object']).columns:
        def fix(v):
            if _pd.isna(v):
                return None
            if isinstance(v, (bytes, bytearray)):
                # try common decodings then fall back to replacement
                try:
                    return v.decode('utf-8')
                except Exception:
                    try:
                        return v.decode('latin-1')
                    except Exception:
                        return v.decode('utf-8', errors='replace')
            if not isinstance(v, str):
                return str(v)
            # ensure the resulting Python str contains only valid unicode
            return v.encode('utf-8', errors='replace').decode('utf-8')
        df[col] = df[col].apply(fix)
    return df


def test_tcp_connection(host: str, port: int, timeout: float = 3.0) -> bool:
    """Vérifie que le port TCP de la base est joignable (remplace le test psycopg2 brut)."""
    try:
        with socket.create_connection((host, int(port)), timeout=timeout):
            return True
    except Exception as e:
        print(f"Échec connexion TCP {host}:{port} -> {e}")
        return False


def insert_into_postgres(df):
    """Insère les données dans PostgreSQL"""
    print(f"Vérification reachabilité {POSTGRES_CONFIG['host']}:{POSTGRES_CONFIG['port']}...")
    if not test_tcp_connection(POSTGRES_CONFIG['host'], POSTGRES_CONFIG['port']):
        print("La base PostgreSQL n'est pas joignable sur le réseau. Abandon.")
        return

    try:
        # sanitize strings to avoid encoding issues (remplace octets invalides)
        df = sanitize_dataframe(df)

        # build engine with explicit client encoding option to help the server session
        dsn = (
            f"postgresql://{POSTGRES_CONFIG['user']}:{POSTGRES_CONFIG['password']}"
            f"@{POSTGRES_CONFIG['host']}:{POSTGRES_CONFIG['port']}/{POSTGRES_CONFIG['database']}"
        )
        engine = create_engine(dsn, connect_args={"options": "-c client_encoding=UTF8"})

        print(f"Insertion de {len(df)} lignes dans la table '{TABLE_NAME}'...")

        # final safety: force all object columns to valid unicode strings
        for c in df.select_dtypes(include=['object']).columns:
            df[c] = df[c].astype(str).apply(lambda x: x.encode('utf-8', errors='replace').decode('utf-8'))

        df.to_sql(
            TABLE_NAME,
            engine,
            if_exists='append',  # 'append' ou 'replace' selon vos besoins
            index=False,
            method='multi',
            chunksize=500
        )
        
        print(f"{len(df)} lignes insérées avec succès!")
        
        # Affichage des statistiques
        with engine.connect() as conn:
            result = conn.execute(f"SELECT COUNT(*) as total FROM {TABLE_NAME}")
            total = result.fetchone()[0]
            print(f"Total de lignes dans la table: {total}")

        engine.dispose()

    except Exception as e:
        print(f"Erreur lors de l'insertion: {e}")
        traceback.print_exc()
        sys.exit(1)


def verify_insertion():
    """Vérifie que les données ont été insérées correctement"""
    print("\nVérification des données insérées...")
    
    try:
        # use SQLAlchemy for verification (helps set client encoding)
        dsn = (
            f"postgresql://{POSTGRES_CONFIG['user']}:{POSTGRES_CONFIG['password']}"
            f"@{POSTGRES_CONFIG['host']}:{POSTGRES_CONFIG['port']}/{POSTGRES_CONFIG['database']}"
        )
        engine = create_engine(dsn, connect_args={"options": "-c client_encoding=UTF8"})
        with engine.connect() as conn:
            result = conn.execute(f"SELECT COUNT(*) as total FROM {TABLE_NAME}")
            total = result.fetchone()[0]
            print(f"  - Total de lignes: {total}")

            result = conn.execute(f"SELECT id, title, author, publication_date FROM {TABLE_NAME} LIMIT 3")
            print(f"  - Premières lignes:")
            for row in result.fetchall():
                # Ensure any bytes are represented safely
                safe_row = tuple((col.decode('utf-8', errors='replace') if isinstance(col, (bytes, bytearray)) else col) for col in row)
                print(f"    {safe_row}")
        engine.dispose()
        
    except Exception as e:
        print(f"Erreur lors de la vérification: {e}")
        traceback.print_exc()


if __name__ == '__main__':
    
    # Si des arguments sont passés en paramètre de ligne de commande
    if len(sys.argv)>1:
        # source: https://qodex.ai/all-tools/ip-address-regex-python-validator
        pattern = r"^((25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)(\.)){3}(25[0-5]|2[0-4]\d|1\d{2}|[1-9]?\d)$"

        if not re.match(pattern, sys.argv[1]):    
            raise Exception(f"L'argument d'adresse fournit n'est pas valide : {sys.argv[1]}")
        else:
            DB_HOST_IP = sys.argv[1]

        if not sys.argv[2].isdecimal:
            raise Exception(f"L'argument de port fournit n'est pas valide : {sys.argv[1]}")
        else:
            DB_HOST_IP = sys.argv[2]


    try:
        # Charger et préparer les données
        df = load_and_prepare_data()
        
        # Insérer dans PostgreSQL
        insert_into_postgres(df)
        
        # Vérifier l'insertion
        verify_insertion()
        
        print("\n" + "="*50)
        print("Process terminé avec succès!")
        print("="*50)
        
    except Exception as e:
        print(f"Erreur fatale: {e}")
        sys.exit(1)
