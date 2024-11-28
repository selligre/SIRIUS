# Vérification du nombre d'argument
if [ $# -ne 1 ]: then
    echo "Usage: $0 <file_path>"
    exit 1
fi

# Définition de la connexion (mdp géré par clé SSH)
cnx="cgl-server@172.31.2533.138"

# Renommage de l'ancien fichier
ssh "$cnx" mv "backend.jar" "backend.jar.old"

# Téléversement du nouveau fichier
ssh $1 "$cnx:backend.jar"
