#!/bin/bash

# Définition de la clé de connexion
cd ../..
echo ${PWS}
ssh_key=${PWD}/key

# Compilation globale du projet
echo -e '\033[107m\033[1;94m'Lancement de la compilation...'\033[0m'
mvn clean install
echo -e '\033[107m\033[1;92m'Compilation complete terminée'\033[0m'


# Packaging du client
printf "\n\n"
echo -e '\033[107m\033[1;94m'Compilation du client...'\033[0m'
cd ./fimafeng-application-local/
mvn clean compile assembly:single install
echo -e '\033[107m\033[1;92m'Compilation du client terminée'\033[0m'


# Packaging du serveur
cd .. && cd ./fimafeng-backend/
printf "\n\n"
echo -e '\033[107m\033[1;94m'Compilation du serveur...'\033[0m'
mvn clean compile assembly:single install
echo -e '\033[107m\033[1;92m'Compilation du serveur terminée'\033[0m'

## Mise à jour du serveur
# Récupération du PID du serveur distant
printf "\n\n"
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
scp -i ${ssh_key} ./target/fimafeng-backend-1.0-SNAPSHOT-jar-with-dependencies.jar cgl-server@172.31.249.69:
echo -e '\033[107m\033[1;92m'Téléversement terminé'\033[0m'


# Lancement du nouveau client
printf "\n\n"
cd .. && cd ./fimafeng-application-local/
echo -e '\033[107m\033[1;94m'Lancement de l\'application locale...'\033[0m'
exec java -jar ./target/fimafeng-application-local-1.0-SNAPSHOT-jar-with-dependencies.jar &
echo -e '\033[107m\033[1;92m'Application en cours d\'éxécution'\033[0m'


# Lancement du nouveau serveur
printf "\n\n"
echo -e '\033[107m\033[1;94m'Exécution du nouveau serveur...'\033[0m'
exec ssh -i ${ssh_key} cgl-server@172.31.249.69 "java -jar fimafeng-backend-1.0-SNAPSHOT-jar-with-dependencies.jar" &
echo -e '\033[107m\033[1;92m'Serveur en ligne'\033[0m'

# Permet de garder la console ouverte
read