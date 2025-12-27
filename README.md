# Ville Partagée

> L'application Ville Partagée est projet pédagogique suivant les consignes du projet SIRIUS réalisé tout au long de la formation d'ingénieur SI de l'EPISEN.
> Notre version, Ville partagée, est une plateforme de collaboration citoyenne qui partage, liste et permet de s'inscrire à des annonces d'événements ou de prêts.

## Liste des étudiants

1. Clément TAURAND (FISA)
**Spécialités S5 choisies :**
  - DATA : Orchestration de services de données (ORC)	
  - DATA : Pipeline de traitements de données pour le cloud (PIP)
  - NCC : Architectures distribuées PaaS (PAAS)

2. Gilles MEUNIER (FISA)
**Spécialités S5 choisies :**
  - SEC : Sécurité systèmes et réseau (SSR)
  - SEC : Sécurité des logiciels
  - SEC : Politiques de sécurité et Dev Sec Ops (PSEC)

3. Louis TRAN (FISE)
**Spécialités S5 choisies :**
  - DATA : Orchestration de services de données (ORC)	
  - DATA : Pipeline de traitements de données pour le cloud (PIP)
  - NCC : Architectures distribuées PaaS (PAAS)

## Arborescence du projet

```
SIRIUS/
├── .github/
│   └── workflows/
│       └── deploy.yml          # Le script CI/CD (Github Actions)
│
├── announce-life-events/
│   ├── Dockerfile              # Dockerfile du service Announce Life Events
│   ├── pom.xml                 # Dépendances Maven
│   └── src/                    # Code application Java (Spring Boot)
│
├── announce-manager/
│   ├── Dockerfile              # Dockerfile du service Announce Manager
│   ├── pom.xml                 # Dépendances Maven
│   └── src/                    # Code application Java (Spring Boot)
│
├── cache/
│   └── Dockerfile              # Dockerfile du service Cache
│
├── database/
│   ├── Dockerfile              # Dockerfile de la BDD
│   └── init.sql                # Script SQL de création de tables
│
├── iam/                        # TODO: à completer
|
├── logs/                       # TODO: à completer
|
├── notifications/              # TODO: à completer
|
├── recommendation/             # TODO: à completer
|
├── reverse-proxy/              # TODO: à completer
│
├── search-service/             # Dossier du Back-end (Spring Boot)
│   ├── Dockerfile              # Dockerfile du service Java
│   ├── pom.xml                 # Dépendances Maven
│   └── src/
│
├── web-server/                 # Dossier du Front-end (React)
│   ├── Dockerfile              # Dockerfile du service React
│   ├── nginx.conf              # Config Nginx pour servir le React
│   ├── package.json
│   ├── public/
│   │   └── index.html
│   └── src/                    # Code application React
│
├── docker-compose.yml          # Pour lancer le tout en local
├── docker-stack.yml            # Ancien docker-compose, voué à être supprimé
└──  README.md                  # Fichier actuel
```