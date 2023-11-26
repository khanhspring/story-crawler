@ECHO OFF

SET "SERVER_IP=149.28.156.237"
SET "SERVER_DIR=/app/services"
SET "SERVICE_NAME=story-spider-service"
SET "VERSION=0.0.1-SNAPSHOT"

ECHO start deployment to server %SERVER_IP%

ECHO building jar file...
call ..\gradlew.bat clean build -p ..\

ECHO uploading jar file...
scp ../docker/Dockerfile root@%SERVER_IP%:%SERVER_DIR%/Dockerfile
scp ../docker/docker-compose.yml root@%SERVER_IP%:%SERVER_DIR%/docker-compose.yml
scp ../build/libs/%SERVICE_NAME%-%VERSION%.jar root@%SERVER_IP%:%SERVER_DIR%/target/app.jar

ECHO restarting app on server...
ssh root@%SERVER_IP% "cd %SERVER_DIR% && docker compose down && docker compose up -d --build"

ECHO completed!