# Définition de la connexion
cnx="cgl-server@172.31.253.138"

# Récupération de l'ID du processus du serveur actuel
pid=$(ssh "$cnx pgrep java")

#Extinction de l'ancien serveur
ssh "$cnx kill $pid"

# Démarrage du nouveau serveur
ssh "$cnx java -jar backend.jar"
