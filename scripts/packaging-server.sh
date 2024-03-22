#!/bin/bash

# Récupération de l'emplacement du fichier
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )


# Packaging du serveur
cd ./fimafeng-backend/
echo -e '\033[107m\033[1;94m'Compilation du serveur...'\033[0m'
mvn clean compile assembly:single install
echo -e '\033[107m\033[1;92m'Compilation du serveur terminée'\033[0m'