# README

## Docker locally

docker build -f ./backend/Dockerfile -t ambatlle/tasks-app-backend:latest
docker run --name tasks-app-backend -p 8080:8080 -it ambatlle/tasks-app-backend:latest

docker build -f ./frontend/Dockerfile -t ambatlle/tasks-app-frontend:latest
docker run --name tasks-app-frontend -p 80:3000 -it ambatlle/tasks-app-frontend:latest

or just

docker-compose up -d

access to [http://localhost] for frontend, backend is available on [http://localhost:8080]

## OCI

### Admin SSH

- ssh -i ./docker-vm-key opc@129.153.199.232

### Install & run tasks-demo application

1. sudo dnf install git
2. git clone [https://github.com/ambatlle/tasks-demo.git] (ambatlle/"token")

### Run app

1. cd tasks-demo/
2. sudo docker-compose up
3. curl [http://localhost:3000] should show the index html code if frontend is running properly
4. curl [http://localhost:8080/sample] should answer with an "ok" if backend is running properly, also you can query the backend health with curl [http://localhost:8081/healthcheck]

- Note: all the interactions have been done with sudo permissions, it should be done rootless, but not time...

### Docker installation

1. sudo dnf install -y dnf-utils zip unzip
2. sudo dnf config-manager --add-repo=[https://download.docker.com/linux/centos/docker-ce.repo]
3. sudo dnf remove -y runc
4. sudo dnf install -y docker-ce --nobest
5. sudo systemctl start docker
6. sudo systemctl enable docker

- Docker compose docker
  1. curl -s [https://api.github.com/repos/docker/compose/releases/latest] | grep browser_download_url | grep docker-compose-linux-x86_64|cut -d '"' -f 4 | wget -qi -
  2. sha256sum -c docker-compose-linux-x86_64.sha256
  3. chmod +x docker-compose-linux-x86_64
  4. sudo mv docker-compose-linux-x86_64 /usr/bin/docker-compose

### Git installation

Add some info

### References

- [https://medium.com/oracledevs/run-always-free-docker-container-on-oracle-cloud-infrastructure-c88e36b65610]
- [https://oracle-base.com/articles/linux/docker-install-docker-on-oracle-linux-ol8]
- [https://www.youtube.com/watch?v=Fiu9BiNocJ4]
- [https://techviewleo.com/install-docker-compose-on-oracle-linux/]

## TODO

- [ ] add authentication in someway
- [ ] document duckdns
- [ ] document .env files
- [ ] review why port not changes in production
- [ ] update docker-compose cmd info with env file (--listen $PORT)
- [ ] add some info about dynamic DNS [https://www.duckdns.org/domains]
