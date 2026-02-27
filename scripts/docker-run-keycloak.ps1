param(
    [Parameter(Mandatory = $true)]
    [int]$port
)
ssh fimafeng@172.31.249.140 -p $port "sudo -S docker run -d -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak start-dev"