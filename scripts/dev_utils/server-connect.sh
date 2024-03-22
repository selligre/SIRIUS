#!/bin/bash


SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

echo $SCRIPT_DIR

cd $SCRIPT_DIR/../../..
ls

#sleep 500

ssh_key=$SCRIPT_DIR/../../../key

echo Connexion a la VM (SV)
echo pswd: cgl-admin
start ssh -i $ssh_key cgl-server@172.31.249.69 &


read