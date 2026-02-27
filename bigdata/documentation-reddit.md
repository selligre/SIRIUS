# Documentation du pipeline de données AskReddit
## Contexte et origine des données

Le jeu de données provient d’un dataset public Kaggle (« A Month of AskReddit ») contenant deux fichiers CSV couvrant un mois d’activité sur le sous-reddit r/AskReddit. Le fichier **askreddit_posts.csv** contient les publications (posts) avec, par exemple, les colonnes *post_id, author, timestamp, title, selftext, permalink, upvotes, num_comments,* etc. Le fichier **askreddit_comments.csv** contient les commentaires associés aux publications, avec notamment les colonnes *body* (texte du commentaire) et *score* (score/karma du commentaire). Le pipeline de traitement lit ces deux fichiers, réalise plusieurs transformations (jointure, calcul de nouvelles colonnes, remplissage de valeurs par défaut) et génère un fichier final `silver.parquet` prêt à l’analyse.

## Les 5 v
Le concept de Big Data se caractérise par cinq dimensions (« 5V ») fondamentales qui posent des défis particuliers. Dans notre contexte AskReddit :

- **Volume** – C’est la quantité de données traitées. Ici, le dataset fait plusieurs centaines de mégaoctets, ce qui nécessite des outils de traitement adaptés. Un grand volume de données permet des analyses approfondies, mais exige une infrastructure robuste pour stocker et traiter efficacement toutes les publications et commentaires.

- **Variété** – Cela désigne la diversité des types de données. On y trouve à la fois des données structurées (identifiants, dates, scores) et non structurées (textes de titre et de commentaire). Ce mélange de formats (texte libre vs métadonnées) illustre la variété des sources de données que l’on manipule.

- **Vélocité** – C’est la vitesse à laquelle les données sont générées et traitées. Reddit génère un flux continu de posts et commentaires en temps réel. Dans notre cas, nous traitons un snapshot mensuel, mais on doit garder à l’esprit que la source est à haute vélocité (posts et commentaires arrivent en permanence).

- **Véracité** – Cela concerne la fiabilité et la qualité des données. Sur Reddit, certaines informations peuvent être incomplètes ou mal formatées. Le pipeline gère cela en filtrant les titres manquants et en remplaçant les valeurs nulles par défaut (par exemple anonymous pour author, “Sans titre” pour title, etc.), afin d’assurer la véracité et d’éviter les incohérences dans le dataset final.

- **Valeur** – C’est l’utilité qu’on retire des données. Le Big Data prend tout son sens lorsqu’il permet de découvrir des insights exploitables (ex. tendances de discussion sur AskReddit, analyse d’opinion, etc.). Ici, bien que le dataset soit un exemple pédagogique, il peut servir à extraire des informations sur la communauté Reddit et à entraîner des modèles (ex. de NLP).

## Pipeline de transformations

Le code `process_data()` réalise les étapes suivantes pour produire le fichier `silver.parquet` :

1. **Lecture des fichiers CSV :** on charge `askreddit_posts.csv` (dans `df_posts`) et `askreddit_comments.csv` (dans `df_comments`) en DataFrames Pandas. On force `post_id` en type string pour éviter les conversions non voulues.

2. **Préparation des colonnes :**
 - Dans `df_posts`, on supprime les colonnes inutiles (`selftext` et `permalink`), puis on réinitialise l’index.
 - Dans `df_comments`, on extrait seulement les colonnes `body` et `score`, on réinitialise l’index, et on renomme : `body` devient `description`, `score` devient `comment_score`.

3. **Jointure littérale :** on concatène (`pd.concat` axis=1) les DataFrames `df_posts` et `df_comments` pour obtenir un seul DataFrame `df`. Cette « jointure littérale » append côte-à-côte les lignes de commentaires aux lignes de posts ; elle suppose que les deux fichiers ont le même nombre de lignes (ce qui est un artefact du dataset).

4. **Filtrage :** on supprime les enregistrements n’ayant pas de titre (`df.dropna(subset=['title'])`) pour se conformer à la consigne (« sans titre »).

5. **Calcul de nouvelles colonnes :**
 - `publication_date` – convertit le champ `timestamp`  (Unix epoch en secondes) en format datetime *YYYY-MM-DD HH:MM:SS* (`pd.to_datetime(...).strftime(...)`).

 - `type` – appliqué sur `num_comments` par la fonction `get_type()`. Cette fonction fait un hash MD5 du nombre de commentaires puis prend le modulo 3 pour répartir aléatoirement les posts en trois catégories (0, 1, 2).

 - `status` – appliqué sur `comment_score` par `get_status()`. Si le score du commentaire est négatif, `status = 3`; sinon `status = (score mod 3)`. Les valeurs non-numériques sont par défaut 1.

 - `date_time_start` – calculée par `create_time_start(publication_date)`. Cette fonction ajoute aléatoirement entre 1 et 13 jours à la date de publication, et fixe aléatoirement une heure (une heure entre 0 et 22, et minutes prises en [0, 15, 30, 45]). Cela simule une date/heure de début d’événement ultérieur.

 - `duration` – durées générées aléatoirement : on tire un entier de 1 à 91, puis on multiplie par 0.25 pour obtenir un nombre d’heures (de 0,25 h à 22,75 h).

 - `date_time_end` – calculée en ajoutant `duration` (en heures) à `date_time_start` (utilisation de `pd.to_timedelta`).

6. **Remplissage des valeurs manquantes (fillna) :** pour éviter tout *NULL* ou toute erreur (ex. NullPointerException en Java), on remplace les valeurs manquantes selon leur type. Par exemple : `post_id='unknown'`, `author='anonymous'`, `publication_date='1970-01-01 00:00:00'`, `title='Sans titre'`, `description='Aucune description disponible'`, `type=0`, `status=1`, `date_time_start='1970-01-01 00:00:00'`, `duration=0.0`, `date_time_end='1970-01-01 00:00:00'`. Cette étape assure la véracité finale des données.

7. **Sélection finale et export :** on ne conserve que les colonnes souhaitées, dans l’ordre :
```
post_id, author, publication_date, title, description, type, status, date_time_start, duration, date_time_end
```
On écrit ensuite le DataFrame final dans `silver.parquet` (format Parquet, moteur PyArrow).

## Mappage des données finales

Le tableau ci-dessous récapitule le schéma des colonnes en entrée (fichiers posts et commentaires), les transformations effectuées, et les colonnes finales du fichier Parquet :
| **Entrée (source)**                     | **Transformation / Explication**                                                            | **Sortie (fichier final)** |
|-----------------------------------------|---------------------------------------------------------------------------------------------|----------------------------|
| `post_id` (askreddit_posts.csv)         | Identifiant unique de la publication                                                        | **post_id**                |
| `author` (askreddit_posts.csv)          | Nom de l’auteur du post                                                                     | **author**                 |
| `timestamp` (en s, askreddit_posts.csv) | Converti en datetime `YYYY-MM-DD HH:MM:SS`                                                  | **publication_date**       |
| `title` (askreddit_posts.csv)           | Titre de la publication                                                                     | **title**                  |
| `body` (askreddit_comments.csv)         | Renommé en `description` (texte du commentaire)                                             | **description**            |
| `score` (askreddit_comments.csv)        | Utilisé pour calculer `status` via `get_status(score)`                                      | **status**                 |
| `num_comments` (askreddit_posts.csv)    | Utilisé pour calculer `type` via `get_type(num_comments)`                                   | **type**                   |
| (aucune, interne)                       | `date_time_start` = `publication_date` + (1–13 jours aléatoires + heure/quarter aléatoires) | **date_time_start**        |
| (aucune, interne)                       | `duration` généré aléatoirement (0,25 à 22,75 h)                                            | **duration**               |
| `date_time_start`                       | Calcul de `date_time_end` = `date_time_start` + `duration`                                  | **date_time_end**          |
		
Ce mapping montre comment chaque colonne finale est obtenue : certaines colonnes sont copiées des sources (post_id, author, title), d’autres sont renommées (body→description), et plusieurs sont calculées (publication_date, type, status, date_time_start, duration, date_time_end) à partir des données sources. Des valeurs par défaut sont appliquées sur toutes ces colonnes pour remplacer les éventuels *NaN* restants.