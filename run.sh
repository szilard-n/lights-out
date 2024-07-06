#!/bin/bash

# Store the initial directory
INITIAL_DIR=$(pwd)

# Function to build the backend using JVM strategy
build_jvm() {
    echo "Building backend module using JVM strategy..."
    cd "$INITIAL_DIR/backend" || exit 1
    ./mvnw clean install
}

# Build the backend
if build_jvm; then
    echo "Backend built successfully. Starting containers..."

    # Navigate back to the initial directory
    cd "$INITIAL_DIR" || exit 1

    # Stop and remove existing containers
    docker compose down

    # Build and start the Docker containers
    if docker compose up --build -d; then
        echo "Containers started successfully. Starting frontend..."

        # Navigate to the frontend directory
        cd "$INITIAL_DIR/frontend" || exit 1

        # Install npm dependencies
        if npm install; then
            echo "NPM dependencies installed successfully."

            # Start the frontend server
            ng serve --proxy-config proxy.conf.json
        else
            echo "Failed to install NPM dependencies."
            exit 1
        fi
    else
        echo "Failed to start containers."
        exit 1
    fi
else
    echo "Backend build failed."
    exit 1
fi