#!/bin/bash

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
source "$SCRIPT_DIR/env.sh"

echo "🛑 Stopping $CONTAINER_NAME..."

docker compose \
  -p "$COMPOSE_PROJECT_NAME" \
  --file "$SCRIPT_DIR/docker-compose.yml" \
  --project-directory "$SCRIPT_DIR" \
  down