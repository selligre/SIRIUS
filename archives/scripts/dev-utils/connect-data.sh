#!/bin/bash

# Script de connexion au serveur
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

cd $SCRIPT_DIR
cd ../../..
ssh_key=${PWD}/key

echo Connexion a la VM Data
ssh -i $ssh_key cgl-data@172.31.253.60