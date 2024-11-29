#!/bin/bash

# Define key adress
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
cd $SCRIPT_DIR && cd ..


# Global compilation
$SCRIPT_DIR/compile-project.sh 

# Package client
$SCRIPT_DIR/compile-client.sh

# Package server
$SCRIPT_DIR/compile-serveur.sh

# Updating and running server
$SCRIPT_DIR/upload-and-restart-server.sh &

# Launching client
$SCRIPT_DIR/start-client.sh