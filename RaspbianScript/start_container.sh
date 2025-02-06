#!/bin/bash

# Define container for script and project path
CONTAINER_NAME="deployContainer"
PROJECT_DIR="/home/user/project"

# check and remove existing container
if docker ps -a --format '{{.Names}}' | grep -q "^$CONTAINER_NAME$"; then
    echo "Stopping and removing existing container: $CONTAINER_NAME"
    docker stop $CONTAINER_NAME
    docker rm $CONTAINER_NAME
fi

# start new container and set up time zone
echo "Starting new container: $CONTAINER_NAME"
docker run -dit --name $CONTAINER_NAME \
    -v $PROJECT_DIR:/app \
    -e TZ=Asia/Taipei \
    ubuntu-java17-git

# wait container fully create
sleep 3

# enter container and run java in background
echo "Entering container and starting Java service in background"
docker exec -d $CONTAINER_NAME bash -c "
    cd /app &&
    nohup java -jar github-contributor.jar > /app/nohup.out 2>&1 &
"

# show log message
echo "Java service is running in the background. Logs: $PROJECT_DIR/nohup.out"
echo "Use 'docker logs $CONTAINER_NAME' or 'tail -f $PROJECT_DIR/nohup.out' to check logs."
