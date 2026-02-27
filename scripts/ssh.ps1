param(
    [Parameter(Mandatory = $true)]
    [int]$port
)
ssh fimafeng@172.31.249.140 -p $port