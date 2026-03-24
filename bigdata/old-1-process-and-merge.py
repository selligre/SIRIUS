import pandas as pd
import os
import hashlib
import random
import numpy as np
import datetime as dt

# Configuration
DATA_DIR = os.path.join(os.path.dirname(__file__), 'data')
INPUT_POSTS_FILE = os.path.join(DATA_DIR, 'askreddit_posts.csv')
INPUT_COMMENTS_FILE = os.path.join(DATA_DIR, 'askreddit_comments.csv')
OUTPUT_FILE_PARQUET = os.path.join(DATA_DIR, 'silver.parquet')
DATETIME_FORMAT = '%Y-%m-%d %H:%M:%S'

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
    try:
        print("Lecture des fichiers...")
        df_posts = pd.read_csv(INPUT_POSTS_FILE, dtype={'post_id': str})
        df_comments = pd.read_csv(INPUT_COMMENTS_FILE)

        # 1. Préparation des colonnes
        df_p = df_posts.drop(columns=['selftext', 'permalink'], errors='ignore').reset_index(drop=True)
        df_c = df_comments[['body', 'score']].copy().reset_index(drop=True)
        df_c = df_c.rename(columns={'body': 'description', 'score': 'comment_score'})

        # 2. Jointure Littérale
        df = pd.concat([df_p, df_c], axis=1)
        
        # On supprime les lignes sans titre comme demandé au début
        df = df.dropna(subset=['title']).copy()

        # 3. Transformations
        df['publication_date'] = pd.to_datetime(df['timestamp'], unit='s', errors='coerce').dt.strftime(DATETIME_FORMAT)
        df['type'] = df['num_comments'].apply(get_type)
        df['status'] = df['comment_score'].apply(get_status)
        df['date_time_start'] = df['publication_date'].apply(create_time_start)
        df['duration'] = np.random.randint(1, 23 * 4, df.shape[0]) * 0.25
        
        temp_end = pd.to_datetime(df['date_time_start']) + pd.to_timedelta(df['duration'].astype(float), unit='h')
        df['date_time_end'] = temp_end.dt.strftime(DATETIME_FORMAT)

        # 4. GESTION DES VALEURS PAR DÉFAUT (Anti-Erreur Java)
        # On définit des valeurs de remplacement selon le type de colonne
        values_to_fill = {
            'post_id': 'unknown',
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
            'post_id', 'author', 'publication_date', 'title', 
            'description', 'type', 'status', 'date_time_start', 
            'duration', 'date_time_end'
        ]
        
        df_final = df[final_columns]

        print(f"Sauvegarde en Parquet vers {OUTPUT_FILE_PARQUET}...")
        df_final.to_parquet(OUTPUT_FILE_PARQUET, engine='pyarrow', index=False)
        print("✓ Terminé.")

    except Exception as e:
        print(f"✗ Erreur : {e}")
        raise

if __name__ == "__main__":
    process_data()