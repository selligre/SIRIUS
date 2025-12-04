#!/bin/bash

# Define key adress
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR && cd ..

# Packaging client
echo -e '\033[107m\033[1;94m'Compilation du client...'\033[0m'
cd ./fimafeng-application-local/
mvn clean compile assembly:single install
echo -e '\033[107m\033[1;92m'Compilation du client terminée'\033[0m'