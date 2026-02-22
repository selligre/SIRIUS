FROM python:3.11-slim

WORKDIR /app

# Installation des dépendances
RUN apt-get update && apt-get install -y libpq-dev gcc && rm -rf /var/lib/apt/lists/*

# Copie des requirements et installation
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copie des scripts Python
COPY 0-datasource-to-bronze.py .
COPY 1-bronze-to-silver.py .

# Création du dossier data
RUN mkdir -p /app/data

# Commande pour garder le conteneur allumé
CMD ["tail", "-f", "/dev/null"]