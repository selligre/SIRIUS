import pandas as pd
import sys
from pathlib import Path

def inspect_parquet(file_path):
    try:
        # Options d'affichage pour voir les lignes complètes
        pd.set_option('display.max_columns', None)     # toutes les colonnes
        pd.set_option('display.width', None)           # largeur illimitée
        pd.set_option('display.max_colwidth', None)    # contenu non tronqué

        # Vérification du fichier
        file = Path(file_path)
        if not file.exists():
            print(f"❌ Fichier introuvable : {file_path}")
            return
        
        print(f"📂 Lecture du fichier : {file_path}\n")

        # Lecture du fichier parquet
        df = pd.read_parquet(file_path)

        # --- Structure du fichier ---
        print("📊 Structure du fichier (colonnes et types) :")
        print("-" * 50)
        for col, dtype in df.dtypes.items():
            print(f"{col} : {dtype}")

        # --- Infos globales ---
        print("\n📈 Informations générales :")
        print("-" * 50)
        print(f"Nombre de lignes : {len(df)}")
        print(f"Nombre de colonnes : {len(df.columns)}")

        # --- Aperçu des données ---
        print("\n🔍 Aperçu (10 premières lignes, COMPLETES) :")
        print("-" * 50)
        print(df.head(10).to_string(index=False))

    except Exception as e:
        print(f"❌ Erreur lors de la lecture : {e}")


if __name__ == "__main__":
    print("Lancement du script")
    if len(sys.argv) != 2:
        print("Usage : python inspect_parquet.py <chemin_du_fichier.parquet>")
    else:
        inspect_parquet(sys.argv[1])