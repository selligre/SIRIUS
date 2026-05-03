param(
    [Parameter(Mandatory = $true)]
    [int]$port
)
ssh fimafeng@172.31.250.155 -p $port