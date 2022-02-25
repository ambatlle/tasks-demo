# README

## Docker

docker build -f ./backend/Dockerfile -t ambatlle/tasks-app-backend:latest
docker run --name tasks-app-backend -p 8080:8080 -it ambatlle/tasks-app-backend:latest

docker build -f ./frontend/Dockerfile -t ambatlle/tasks-app-frontend:latest
docker run --name tasks-app-frontend -p 3000:3000 -it ambatlle/tasks-app-frontend:latest

or just

docker-compose up
