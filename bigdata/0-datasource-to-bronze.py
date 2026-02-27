import urllib.request as req
import zipfile
import os


BASE_DIRECTORY = "data/"
DATASET_URL = "https://www.kaggle.com/api/v1/datasets/download/thunderz/a-month-of-askreddit"
DATASET_ZIP_NAME = "a-month-of-askreddit.zip"


def create_data_folder():
    print(f"Verification de l'existance du dossier {BASE_DIRECTORY}...")
    if os.path.isdir(BASE_DIRECTORY):
       print(f"Dossier {BASE_DIRECTORY} trouve !")
       return
    print(f"Dossier inexistant, creation du dosser {BASE_DIRECTORY}")
    os.makedirs(BASE_DIRECTORY)
    print(f"Dossier {BASE_DIRECTORY} cree !")

def download_dataset():
    if os.path.exists(BASE_DIRECTORY+DATASET_ZIP_NAME):
        print(f"Fichier {DATASET_ZIP_NAME} deja telecharge, etape ignauree !")
        return
    print(f"Telechargement du dataset...")
    req.urlretrieve(DATASET_URL, BASE_DIRECTORY+DATASET_ZIP_NAME)
    print(f"Telechargement termine !")

def extract_dataset():
    print(f"Extraction du fichier {DATASET_ZIP_NAME}...")
    with zipfile.ZipFile(BASE_DIRECTORY+DATASET_ZIP_NAME, 'r') as zip_ref:
        zip_ref.extractall(BASE_DIRECTORY)
    print(f"Extraction finie !")
   

if __name__ == '__main__':
    print(f"Lancement des opérations...")
    print(f"="*50)
    create_data_folder()
    print(f"="*50)
    download_dataset()
    print(f"="*50)
    extract_dataset()
    print(f"="*50)
    print(f"Toutes les opérations sont finies")
