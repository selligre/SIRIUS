#!/bin/bash

# Vérifier si l'argument (authorId) a bien été fourni
if [ -z "$1" ]; then
  echo "Erreur : Veuillez spécifier un authorId en paramètre."
  echo "Usage : $0 <authorId>"
  exit 1
fi

AUTHOR_ID=$1

curl.exe -X POST http://172.31.250.155:21480/api/announcements -H "Content-Type: application/json" -d "{\"title\":\"Spectacle de l'école\",\"description\":\"Pour célébrer la fin de sirius\",\"dateTimeStart\":\"2026-05-22T14:00:00Z\",\"dateTimeEnd\":\"2026-05-22T15:00:00Z\",\"publicationDate\":\"2026-05-22T08:00:00Z\",\"status\":1,\"type\":2,\"authorId\":$AUTHOR_ID}"