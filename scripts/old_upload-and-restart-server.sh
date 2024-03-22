#!/bin/bash

# Récupération de l'emplacement du fichier
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR && cd ../..
ssh_key=${PWD}/key


## Mise à jour du serveur
# Récupération du PID du serveur distant
echo -e '\033[107m\033[1;94m'Récupération du processus d\'execution du serveur...'\033[0m'
server_pid=$(ssh -i ${ssh_key} cgl-server@172.31.249.69 pgrep java)
echo -e '\033[107m\033[1;92m'PID trouvé : $server_pid'\033[0m'

# Arrêt (kill) du serveur distant
printf "\n\n"
echo -e '\033[107m\033[1;94m'Arrêt de l\'éxécution du serveur...'\033[0m'
ssh -i ${ssh_key} cgl-server@172.31.249.69 kill $server_pid
echo -e '\033[107m\033[1;92m'Serveur arrêté'\033[0m'

# Upload du nouveau serveur
printf "\n\n"
echo -e '\033[107m\033[1;94m'Téléversement du nouveau jar...'\033[0m'
cd $SCRIPT_DIR && cd ../fimafeng-backend/
scp -i ${ssh_key} ./target/fimafeng-backend-1.0-SNAPSHOT-jar-with-dependencies.jar cgl-server@172.31.249.69:
echo -e '\033[107m\033[1;92m'Téléversement terminé'\033[0m'

# Lancement du nouveau serveur
printf "\n\n"
echo -e '\033[107m\033[1;94m'Exécution du nouveau serveur...'\033[0m'
exec ssh -i ${ssh_key} cgl-server@172.31.249.69 "java -jar fimafeng-backend-1.0-SNAPSHOT-jar-with-dependencies.jar" &
echo -e '\033[107m\033[1;92m'Serveur en ligne'\033[0m'
