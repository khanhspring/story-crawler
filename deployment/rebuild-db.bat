@ECHO OFF

SET "SERVER_IP=149.28.156.237"
SET "SERVER_DIR=/app/db"

ECHO rebuild postgresql on server %SERVER_IP%

ECHO uploading configuration...
scp ../docker/docker-compose.db.yml root@%SERVER_IP%:%SERVER_DIR%/docker-compose.yml
scp -r ../docker/db root@%SERVER_IP%:%SERVER_DIR%

ECHO restarting...
ssh root@%SERVER_IP% "cd %SERVER_DIR% && docker compose up -d --build"

ECHO completed!