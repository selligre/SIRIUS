#!/bin/bash

# Lancement du nouveau client
echo -e '\033[107m\033[1;92m'Application en cours d\'éxécution'\033[0m'
cd ./fimafeng-application-local/
echo -e '\033[107m\033[1;94m'Lancement de l\'application locale...'\033[0m'
exec java -jar ./target/fimafeng-application-local-1.0-SNAPSHOT-jar-with-dependencies.jar &