#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <IP_RP> <CERT_NAME>"
    exit 1
fi

IP=$1
WEB_SERVER=$2

openssl req -x509 -nodes \
-days 365 -newkey rsa:2048 \
-keyout /etc/nginx/ssl/private/$WEB_SERVER.key \
-out /etc/nginx/ssl/certs/$WEB_SERVER.crt \
-subj "/CN=$IP"

echo key : /etc/nginx/ssl/private/$WEB_SERVER.key
echo cert: /etc/nginx/ssl/certs/$WEB_SERVER.crt

# EXAMPLE
# sudo ./generate_cert.sh 172.31.249.140 app_web_server
