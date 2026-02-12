import pandas as pd
import os
import hashlib
import random
import datetime as dt
import numpy as np


BASE_DIRECTORY = "data/"
DATASET_NAME = "data.csv"
DATASET_SILVER = "silver.parquet"
COLS_TO_DROP = [
    'register_index',
    'comment_id',
    'url',
    'comments',
    'author_post_karma',
    'tag',
    'score'
]
COLS_TO_RENAME = {
    'datetime': 'publication_date',
    'text': 'description'
}

def remove_columns():
    print(f"Chargement du fichier {DATASET_SILVER}...")
    df = pd.read_parquet(BASE_DIRECTORY+DATASET_SILVER)
    print(f"Suppression des colonnes inutiles...")
    df_clean = df.drop(columns=COLS_TO_DROP, errors='ignore')
    df_clean.fillna(0)
    print(f"Sauvegarde du fichier en .parquet")
    df_clean.to_parquet(BASE_DIRECTORY+DATASET_SILVER, index=False)
    #df_clean.to_csv(BASE_DIRECTORY+'silver-remove-col.csv')
    print(f"Nettoyage termine !")
    
    org_size = os.path.getsize(BASE_DIRECTORY+DATASET_NAME)
    cleaned_size = os.path.getsize(BASE_DIRECTORY+DATASET_SILVER)
    pct_gain = round((1-cleaned_size/org_size)*100,2)
    print(f"Gain : {pct_gain}% plus leger")


def remove_rows():
    print(f"Chargement du fichier {DATASET_NAME}...")
    df = pd.read_csv(BASE_DIRECTORY+DATASET_NAME)
    print(f"Suppression des lignes inutiles...")
    df_clean = df.dropna(subset=['title'])
    print(f"Sauvegarde du fichier en .parquet")
    df_clean.to_parquet(BASE_DIRECTORY+DATASET_SILVER, index=False)
    #df_clean.to_csv(BASE_DIRECTORY+'silver-remove-row.csv')
    print(f"Nettoyage termine !")
    print(f"Gain : {round((1-len(df_clean)/len(df))*100,2)}% plus leger")


def rename_columns():
    print(f"Renommage des colonnes...")
    df = pd.read_parquet(BASE_DIRECTORY+DATASET_SILVER)
    df_clean = df.rename(columns=COLS_TO_RENAME)
    print(f"Mise a jour du fichier")
    df_clean.to_parquet(BASE_DIRECTORY+DATASET_SILVER, index=False)
    print(f"Renommage termine !")


def get_type(string):
    # on s'assure d'avoir la valeur en utf8
    encoded = string.encode('utf-8')
    # on recupere la version hexadecimale hashee
    hashed = hashlib.md5(encoded).hexdigest()
    as_int = int(hashed, 16)
    # on retourne le modulo pour avoir une valeur comprise parmi 0,1 et 2 (service, pret, evenement)
    return as_int % 3


def get_status(float):
    """ Retourne la valeur associée à l'etat de l'annonce
    0: brouillon
    1: a_analyser (le moins possible)
    2: publiée (et approuvée par l'algo de moderation)
    3: moderée (refusée par l'algo de moderation)
    """
    if float < 0:
        return 3
    else:
        return float%3


def create_time_start(date):
    # on souhaite avoir entre 1 et 14 jours après la date de creation
    spacing = random.randrange(1,14)
    original_date = dt.datetime.strptime(date, '%Y-%m-%d %H:%M:%S')
    new_date = original_date + dt.timedelta(days=spacing)
    final_starting_time = dt.datetime(new_date.year, new_date.month, new_date.day, random.randrange(0,23), random.randrange(0,45,15))
    return final_starting_time


def create_new_columns():
    print(f"Creation de nouvelles colonnes...")
    df = pd.read_parquet(BASE_DIRECTORY+DATASET_SILVER)
    # colonne type : service / pret / evenement
    df['type'] = df['post_id'].apply(get_type)
    # colonne status : brouillon, publiee, moderee (, a analyser)
    df['status'] = df['score'].apply(get_status)
    # colonne date_time_start : écart artificiel à partir de la date de creation
    df['date_time_start'] = df['publication_date'].apply(create_time_start)
    # colonne duration : duree en heure exprimee en float, granulaire par quart d'heure
    df['duration'] = np.random.randint(1, 23*4, df.shape[0])*0.25
    # colonne date_time_end : on additionne duration à date_time_start
    df['date_time_end'] = df['date_time_start'] + pd.to_timedelta(df['duration'].astype(float), unit='h')
    print(f"Sauvegarde du fichier en .parquet")
    df.to_parquet(BASE_DIRECTORY+DATASET_SILVER, index=False)
    print(f"Creation des nouvelles colonnes termine !")


def readSchema(file: str):
    df = pd.read_parquet(file) if file.endswith('.parquet') else pd.read_csv(file)
    print("SCHEMA:")
    print(df.dtypes)
    print("\nPREMIERE LIGNE:")
    print(df.iloc[0])

if __name__ == '__main__':
    print(f"Schema en entrée")
    readSchema(BASE_DIRECTORY+DATASET_NAME)
    print(f"Lancement des opérations...")
    print(f"="*50)
    remove_rows()
    print(f"="*50)
    rename_columns()
    print(f"="*50)
    create_new_columns()
    print(f"="*50)
    remove_columns()
    print(f"="*50)
    print(f"Toutes les opérations sont finies")
    print(f"="*50)
    print(f"Schema en sortie")
    readSchema(BASE_DIRECTORY+DATASET_SILVER)
