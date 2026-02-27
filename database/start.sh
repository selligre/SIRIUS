sudo  

echo "Building the docker image"
sudo docker rm fimafeng-database

echo "Building the docker image"
sudo docker build -t fimafeng-database .

echo "Building the docker image"
sudo docker run -dp 5432:5432 --name fimafeng-database fimafeng-database -d