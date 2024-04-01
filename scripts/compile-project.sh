#!/bin/bash

# Define key adress
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR && cd ..

# Global compilation
echo -e '\033[107m\033[1;94m'Lancement de la compilation...'\033[0m'
mvn clean install
echo -e '\033[107m\033[1;92m'Compilation complete terminée'\033[0m'
