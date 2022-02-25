# Tasks API

How to start the Tasks API application
---

1. Run `mvn clean install` to build your application
2. Create and populate de H2 database `java -jar target/tasks-api-1.0-SNAPSHOT.jar db migrate config.yml`.

   This will just be necessary once at the beginning or every time that you need to regenerate the database.

3. Start application with `java -jar target/tasks-api-1.0-SNAPSHOT.jar server config.yml`
4. To check that your application is running enter url http://localhost:8080/sample, you should see an "ok" answer.

#### Notes:

1. If you don't have maven configured on your system, the provided Maven wrapper can be used

    - on Windows use `mvnw.cmd clean install`
    - on Linux/Mac use `mvnw clean install` (not tested yet)
    - or you can use your preferred Java IDE with Maven integration

2. For practical reasons a file H2 database has been used, by default it's stored on `~/tasksApp/dbprod*`, you can
   customize it on the `config.yml` file.

Health Check
---

To see your application's health enter url http://localhost:8081/healthcheck

Testing
---

- how to run unit tests
- or how postman has been used

Docker containerisation
---
docker build . -t ambatlle/tasks-app-backend
docker run --name tasks-app-backend -p 8080:8080 -it ambatlle/tasks-app-backend

Used tools
---

libraries and so on...

- Mockaroo
- Spring Boot Maven wrapper (and Maven, of course)
- Docker
- dropwizard
- H2
- dropwizard-guicey
- guicey-jdbi3
- AssertJ
- JUnit
- Postman
- IntelliJ IDEA EAP

Pending TODOs
---

- [ ] dfasdfa
- [ ] dfadfasdfasfdsafs