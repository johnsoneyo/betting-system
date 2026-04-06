
# --- Project Identity ---
# The folder name inside 'apps/' where the pom.xml lives
export SERVICE_NAME="event-outcomes-api"

export JAR_NAME="event-outcomes-api"
export JAR_VERSION="0.0.0"

# --- Docker Identity ---
# The name you want for the Docker Image
export IMAGE_NAME=${SERVICE_NAME}
# The name of the container when running locally
export CONTAINER_NAME="event-outcomes-api"
# The docker-compose project name (prevents collisions between apps)
export COMPOSE_PROJECT_NAME="docker-${SERVICE_NAME}"

# --- Ports ---
export APP_PORT="8080"      # Port inside container
export HOST_PORT="8080"     # Port exposed on your laptop
export DEBUG_PORT="4001"    # Debug port
