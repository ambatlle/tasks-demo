# HOW-TO Run Tasks App

- [HOW-TO Run Tasks App](#how-to-run-tasks-app)
  - [OCI deployed Task App](#oci-deployed-task-app)
  - [Run Docker locally](#run-docker-locally)
  - [Run Docker on OCI](#run-docker-on-oci)
  - [Environment files for docker-compose](#environment-files-for-docker-compose)
    - [Ports and API server endpoints](#ports-and-api-server-endpoints)
  - [Installing dependencies required on OCI instance](#installing-dependencies-required-on-oci-instance)
    - [References](#references)
  - [More details on](#more-details-on)
  - [TODO](#todo)

## OCI deployed Task App

At the time of writing this document the Task demo application was deployed on Oracle Cloud accessible through [Tasker](http://ambatlle-tasks.duckdns.org/) or by [IP](http://129.153.199.232/)

## Run Docker locally

1. `cat docker-compose.local.yml > docker-compose.yml`
2. `docker-compose up -d`

You can access to [http://localhost] for frontend and backend is available on [http://localhost:8080]

- Docker compose is preferred, although you could build&run the containers manually instead:

  1. `docker build -f ./backend/Dockerfile -t ambatlle/tasks-app-backend:latest`
  2. `docker run --name tasks-app-backend -p 8080:8080 -it ambatlle/tasks-app-backend:latest`
  3. `docker build -f ./frontend/Dockerfile -t ambatlle/tasks-app-frontend:latest`
  4. `docker run --name tasks-app-frontend -p 80:3000 -it ambatlle/tasks-app-frontend:latest`

  - **Note:**- you must set manually the API server and port passing it by [environment variable](#environment-files-for-docker-compose), if not it will assume that API is at [http://localhost:8080](http://localhost:8080) by default, which by default should work fine.

## Run Docker on OCI

1. `ssh -i ./docker-vm-key opc@129.153.199.232` (*use your cloud's IP and your public key*)
2. `git clone https://github.com/ambatlle/tasks-demo.git` *if not done yet*
3. `cd tasks-demo/`
4. `cat docker-compose.oci.yml > docker-compose.yml` *also there are a couple of docker configuration files to deploy backend on ARM architectures*
5. `sudo docker-compose up -d` *it will build the images the first time, if you want to force rebuild use `--build` flag*
6. `curl http://localhost` should show the index html code if frontend is running properly
7. `curl http://localhost:8080/sample` should answer with an "ok" if backend is running properly, also you can query the backend health with `curl http://localhost:8081/healthcheck`

- ***Note:*** all the interactions have been done with sudo permissions, it should be done rootless (*pending TODO*)
  - If you want, you can use aliases, `alias docker="sudo docker"` and `alias docker-compose="sudo docker-compose"` to make it easier.
- ***Note:*** the configuration of OCI instance Internet Gateway and allowed public ports its outside the aim of this documentation.
- ***Note:*** Docker compose is preferred, although you could build&run the containers manually instead:

  1. `docker build -f ./backend/Dockerfile -t ambatlle/tasks-app-backend:latest`
  2. `docker run --name tasks-app-backend -p 8080:8080 -it ambatlle/tasks-app-backend:latest`
  3. `docker build -f ./frontend/Dockerfile -t ambatlle/tasks-app-frontend:latest`
  4. `docker run --name tasks-app-frontend -p 80:3000 -it ambatlle/tasks-app-frontend:latest`

  - **Note:** you must set manually the API server and port passing it by [environment variable](#environment-files-for-docker-compose), if not it will assume that API is at [http://localhost:8080](http://localhost:8080) by default, which by default should work fine.

## Environment files for docker-compose

As you have seen there two docker-compose files, on them there are configured the environment variables required by the frontend, you can adapt them to your needs.

Frontend requires the following environment variables:

- `REACT_APP_SERVER`: on which server its located your API (default: `localhost`)
- `REACT_APP_PORT`: on which port its located your API (default: `8080`)

### Ports and API server endpoints

You can change the API server endpoints and backend/frontend ports modifing Docker compose files, but OCI instance has only allowed access on the ports used by default during the development.

## Installing dependencies required on OCI instance

1. `sudo dnf install -y dnf-utils zip unzip`
2. `sudo dnf config-manager --add-repo=https://download.docker.com/linux/centos/docker-ce.repo`
3. `sudo dnf remove -y runc`
4. `sudo dnf install -y docker-ce --nobest`
5. `sudo systemctl start docker`
6. `sudo systemctl enable docker`
7. Docker compose docker
   1. `curl -s https://api.github.com/repos/docker/compose/releases/latest | grep browser_download_url | grep docker-compose-linux-$(uname -i) | cut -d '"' -f 4 | wget -qi -`
   2. `sha256sum -c docker-compose-linux-$(uname -i).sha256` if not valid don't continue! Look for why it's not a valid file.
   3. `chmod +x docker-compose-linux-$(uname -i)`
   4. `sudo mv docker-compose-linux-$(uname -i) /usr/bin/docker-compose`
8. `sudo dnf install git`

### References

- [Run Always Free Docker Container on Oracle Cloud Infrastructure](https://medium.com/oracledevs/run-always-free-docker-container-on-oracle-cloud-infrastructure-c88e36b65610)
- [Docker : Install Docker on Oracle Linux 8 (OL8)](https://oracle-base.com/articles/linux/docker-install-docker-on-oracle-linux-ol8)
- [Create An Always Free VM In The Oracle Cloud In Less Than 60 Seconds!](https://www.youtube.com/watch?v=Fiu9BiNocJ4)
- [Installing Docker Compose on Oracle Linux 8](https://techviewleo.com/install-docker-compose-on-oracle-linux/)

## More details on

- [Frontend README](./frontend/README.md)
- [Backend README](./backend/README.md)

## TODO

- [ ] add authentication in someway
- [ ] investigate how to use docker in rootless mode
- [ ] create REACT_APP_PROTOCOL to be able to select https or https
  - [ ] By the way, configure https on OCI instance
- [ ] Try to map node_modules and maven repository to docker volumes to make build images faster.
