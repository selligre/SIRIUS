#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR && cd ..


# Compilation globale
$SCRIPT_DIR/compile-project.sh 

# Package du client
$SCRIPT_DIR/compile-client.sh

# Démarrage du client
$SCRIPT_DIR/start-client.sh
