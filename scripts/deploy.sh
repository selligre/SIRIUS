# c'est juste une piste, j'ai aucune idee de comment il faut s'y prendre

m2=${M2_REPO}

exec mvn clean install

exec mvn clean compile assembly:single install

exec mvn clean compile assembly:single install

scp -P 5423 -rp ${m2}/fimafeng-backend\target\fimafeng-backend-1.0-SNAPSHOT-jar-with-dependencies.jar \ cgl-data@172.31.253.60:.

ssh cgl-data@172.31.253.60 java -jar ./fimafeng-backend-1.0-SNAPSHOT-jar-with-dependencies.jar

java -jar ${m2}/fimafeng-application-local\target\fimafeng-backend-1.0-SNAPSHOT-jar-with-dependencies.jar