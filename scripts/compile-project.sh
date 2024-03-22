#!/bin/bash

# Récupération de l'emplacement du fichier
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )


# Compilation générale
echo -e '\033[107m\033[1;94m'Lancement de la compilation...'\033[0m'
mvn clean install
echo -e '\033[107m\033[1;92m'Compilation complete terminée'\033[0m'
