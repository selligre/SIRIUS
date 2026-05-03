param(
    [Parameter(Mandatory = $true)]
    [int]$port
)
ssh fimafeng@172.31.250.155 -p $port "cat oauth2-proxy.log | grep logout | tail -n 10"
