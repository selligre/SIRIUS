#!/bin/bash

# Packaging du client
echo -e '\033[107m\033[1;94m'Compilation du client...'\033[0m'
cd ./fimafeng-application-local/
mvn clean compile assembly:single install
echo -e '\033[107m\033[1;92m'Compilation du client terminée'\033[0m'