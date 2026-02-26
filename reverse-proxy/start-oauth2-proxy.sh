#!/bin/bash

BINARY="./oauth2-proxy"

OPTS="$OPTS --cookie-secret=YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXoxMjM0NTY="
OPTS="$OPTS --upstream=http://10.10.10.13:8080"
OPTS="$OPTS --http-address=127.0.0.1:4180"

# LOGS
OPTS="$OPTS --logging-filename=./oauth2-proxy.log"

# EMAIL
OPTS="$OPTS --email-domain=*"

# KEYCLOAK
OPTS="$OPTS --provider=oidc"
OPTS="$OPTS --client-id=app"
OPTS="$OPTS --client-secret=a6v6m7jT8hsk8yeW2EMSquDblVvvgzfd"
OPTS="$OPTS --redirect-url=http://172.31.249.140:21180/oauth2/callback"
OPTS="$OPTS --code-challenge-method=S256"
OPTS="$OPTS --cookie-expire=2h"
OPTS="$OPTS --cookie-secure=true"
OPTS="$OPTS --insecure-oidc-allow-unverified-email=true"

# BROWSER
OPTS="$OPTS --login-url=http://172.31.249.140:21280/realms/fimafeng/protocol/openid-connect/auth"
OPTS="$OPTS --redeem-url=http://10.10.10.12:8080/realms/fimafeng/protocol/openid-connect/token"

# OIDC
OPTS="$OPTS --skip-oidc-discovery=true"
OPTS="$OPTS --oidc-issuer-url=http://172.31.249.140:21280/realms/fimafeng"
OPTS="$OPTS --insecure-oidc-skip-issuer-verification=true"
OPTS="$OPTS --skip-provider-button=true"

# TOKEN
OPTS="$OPTS --oidc-jwks-url=http://10.10.10.12:8080/realms/fimafeng/protocol/openid-connect/certs"

# LOGOUT REDIRECT
OPTS="$OPTS --whitelist-domain=172.31.249.140:21280"

# START
echo "Démarrage de oauth2-proxy..."
exec $BINARY $OPTS
