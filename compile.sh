#!/bin/bash

ssh_key=${PWD}/key

echo -e '\033[107m\033[1;94m'Lancement de la compilation...'\033[0m' && echo ""
mvn clean install
echo -e '\033[107m\033[1;92m'Compilation complete terminée'\033[0m'

echo "" && echo ""
echo -e '\033[107m\033[1;94m'Compilation du client...'\033[0m' && echo ""
cd ./fimafeng-application-local/
mvn clean compile assembly:single install
echo -e '\033[107m\033[1;92m'Compilation du client terminée'\033[0m'

cd .. && cd ./fimafeng-backend/
echo "" && echo ""
echo -e '\033[107m\033[1;94m'Compilation du serveur...'\033[0m' && echo ""
mvn clean compile assembly:single install
echo -e '\033[107m\033[1;92m'Compilation du serveur terminée'\033[0m'

echo "" && echo ""
echo -e '\033[107m\033[1;94m'Récupération du processus d\'execution du serveur...'\033[0m' && echo ""
server_pid=$(ssh -i ${ssh_key} cgl-server@172.31.249.69 pgrep java)
echo -e '\033[107m\033[1;92m'PID trouvé : $server_pid'\033[0m'

echo "" && echo ""
echo -e '\033[107m\033[1;94m'Arrêt de l\'éxécution du serveur...'\033[0m' && echo ""
ssh -i ${ssh_key} cgl-server@172.31.249.69 kill $server_pid
echo -e '\033[107m\033[1;92m'Serveur arrêté'\033[0m'

echo "" && echo ""
echo -e '\033[107m\033[1;94m'Téléversement du nouveau jar...'\033[0m' && echo ""
scp -i ${ssh_key} ./target/fimafeng-backend-1.0-SNAPSHOT-jar-with-dependencies.jar cgl-server@172.31.249.69:
echo -e '\033[107m\033[1;92m'Téléversement terminé'\033[0m'

echo "" && echo ""
echo -e '\033[107m\033[1;94m'Exécution du nouveau serveur...'\033[0m' && echo ""
ssh -i ${ssh_key} cgl-server@172.31.249.69 "java -jar fimafeng-backend-1.0-SNAPSHOT-jar-with-dependencies.jar &"
echo -e '\033[107m\033[1;92m'Serveur en ligne'\033[0m'

read