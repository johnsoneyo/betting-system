#!/bin/bash

# Locate the script directory to find env.sh reliably
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
source "$SCRIPT_DIR/env.sh"

echo "🚀 Starting $CONTAINER_NAME..."

# Run docker-compose with the project name (-p) from config
docker compose \
  -p "$COMPOSE_PROJECT_NAME" \
  --file "$SCRIPT_DIR/docker-compose.yml" \
  --project-directory "$SCRIPT_DIR" \
  up --build --abort-on-container-exit
