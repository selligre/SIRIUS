#!/bin/bash

# Define key adress
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR && cd ..

# Launching client
echo -e '\033[107m\033[1;92m'Application en cours d\'éxécution'\033[0m'
cd ./fimafeng-application/
echo -e '\033[107m\033[1;94m'Lancement de l\'application locale...'\033[0m'
exec java -jar ./target/fimafeng-application-1.0-SNAPSHOT-jar-with-dependencies.jar &