import pandas as pd
import os
import hashlib
import random
import numpy as np
import datetime as dt
import time

# Configuration
DATA_DIR = os.path.join(os.path.dirname(__file__), 'data')
INPUT_POSTS_FILE = os.path.join(DATA_DIR, 'Books_rating.csv')
OUTPUT_FILE_PARQUET = os.path.join(DATA_DIR, 'silver.parquet')
DATETIME_FORMAT = '%Y-%m-%d %H:%M:%S'

"""
Data pattern:
    Id: int                                     To delete   ok
    Title: String                               To delete   ok
    Price: float                                To delete   ok
    User_id: String                             Keep for type   ok
    profilName: String                          Keep for user name
    review/helpfullness: String, pattern: n/m   To delete   ok
    review/score: float                         Keep for status
    review/time: datetime                       Keep for creation date
    review/summary: String                      keep for title
    review/text: String                         keep for description
"""

def get_type(val):
    encoded = str(val).encode('utf-8')
    hashed = hashlib.md5(encoded).hexdigest()
    as_int = int(hashed, 16)
    return as_int % 3

def get_status(score):
    try:
        val = float(score)
        if val < 0: return 3
        return int(val % 3)
    except:
        return 1

def create_time_start(date_str):
    try:
        if pd.isna(date_str): return None
        spacing = random.randrange(1, 14)
        original_date = dt.datetime.strptime(date_str, DATETIME_FORMAT)
        new_date = original_date + dt.timedelta(days=spacing)
        final_date = dt.datetime(new_date.year, new_date.month, new_date.day, 
                               random.randrange(0, 23), random.randrange(0, 45, 15))
        return final_date.strftime(DATETIME_FORMAT)
    except:
        return None

def process_data():
    start_time = time.time()
    try:
        
        print("Lecture des fichiers...")
        df = pd.read_csv(INPUT_POSTS_FILE)
        print(f"Nombre de ligne initial : {len(df.index)}")
        print(f"Nombre de colonne initial : {len(df.columns)}")

        # 1. Préparation des colonnes
        print("Suppression des colonnes inutiles")
        df = df.drop(columns=['Id','Title','Price','review/helpfulness'])
        df = df.rename(columns={'profileName': 'author', 'review/summary': 'title', 'review/text':'description'})
        
        # 2. Suppression des lignes avec des informations nécessaires vides
        print("Suppression de lignes incomplètes")
        df = df.dropna(subset=['User_id']).copy()
        
        # 3. Transformations
        print("Creation des colonnes manquantes")
        df['publication_date'] = pd.to_datetime(df['review/time'], unit='s', errors='coerce').dt.strftime(DATETIME_FORMAT)
        df['type'] = df['User_id'].apply(get_type)
        df['status'] = df['review/score'].apply(get_status)
        df['date_time_start'] = df['review/time'].apply(create_time_start)
        df['duration'] = np.random.randint(1, 23 * 4, df.shape[0]) * 0.25
        
        temp_end = pd.to_datetime(df['date_time_start']) + pd.to_timedelta(df['duration'].astype(float), unit='h')
        df['date_time_end'] = temp_end.dt.strftime(DATETIME_FORMAT)

        # 4. Définition des valeurs par défaut
        print("Suppression des colonnes plus nécessaires")
        
        values_to_fill = {
            'author': 'anonymous',
            'publication_date': '1970-01-01 00:00:00',
            'title': 'Sans titre',
            'description': 'Aucune description disponible', # Correction de votre erreur Java
            'type': 0,
            'status': 1,
            'date_time_start': '1970-01-01 00:00:00',
            'duration': 0.0,
            'date_time_end': '1970-01-01 00:00:00'
        }

        df = df.fillna(value=values_to_fill)

        # 5. Sélection finale et export
        final_columns = [
            'author', 'publication_date', 'title', 
            'description', 'type', 'status', 'date_time_start', 
            'duration', 'date_time_end'
        ]
    
        df_final = df[final_columns]
        

        # 6. Sauvegarde finale
        print(f"Sauvegarde en Parquet vers {OUTPUT_FILE_PARQUET}...")
        df_final.to_parquet(OUTPUT_FILE_PARQUET, engine='pyarrow', index=False)
        
        print(f"Nombre de ligne final : {len(df.index)}")
        print(f"Nombre de colonne final : {len(df.columns)}")
        print(f"Le programme s'est terminé après {time.time() - start_time} secondes")
    

    except Exception as e:
        print(f"Le programme a rencontré une erreur après {time.time() - start_time} secondes")
        print(f"Erreur : {e}")


if __name__ == "__main__":
    process_data()