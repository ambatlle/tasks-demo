# Tasks API

## How to start the Tasks API application

1. Run `mvn clean install` to build your application
2. Create and populate de H2 database `java -jar target/tasks-api-1.0-SNAPSHOT.jar db migrate config.yml`.

   This will just be necessary once at the beginning or every time that you need to regenerate the database.

3. Start application with `java -jar target/tasks-api-1.0-SNAPSHOT.jar server config.yml`
4. To check that your application is running enter url [http://localhost:8080/sample], you should see an "ok" answer.

### Notes

1. If you don't have maven configured on your system, the provided Maven wrapper can be used

    - on Windows use `mvnw.cmd clean install`
    - on Linux/Mac use `mvnw clean install` (not tested yet)
    - or you can use your preferred Java IDE with Maven integration

2. For practical reasons a file H2 database has been used, by default it's stored on `~/tasksApp/dbprod*`, you can
   customize it on the `config.yml` file, although Docker is using this path, so be carefull.

### Health Check

To see your application's health enter url [http://localhost:8081/healthcheck], a basic Database Healthcheck has been added.

### Testing

- how to run unit tests
- or how postman has been used
- coverage and surefire

### Docker containerisation

docker build . -t ambatlle/tasks-app-backend:latest
docker run --name tasks-app-backend -p 8080:8080 -it ambatlle/tasks-app-backend:latest

### Tools

libraries and so on...

- Mockaroo [https://www.mockaroo.com/] to generate some mock data for json-server & flyway
- Spring Boot Maven wrapper (and Maven)
- Docker
- Dropwizard
- H2
- Dropwizard-guicey [https://github.com/xvik/dropwizard-guicey]
- JDBI3
- Guicey-jdbi3
  - Integrates JDBI3 with guice. Based on dropwizard-jdbi3 integration.
  - [https://github.com/xvik/dropwizard-guicey-ext/tree/master/guicey-jdbi3]
- AssertJ
- Mockito
- JUnit
- Postman
- IntelliJ IDEA EAP
- Swagger
- Jacoco testing coverage reporting
- Surefire testing results reporting

## OpenAPI v3 & Swagger

### Swagger ui

Available on [http://localhost:8080/swagger-ui/index.html]

It serves the swagger dist located on src/main/resources/assets/swagger-ui/dist

Downloaded from [https://github.com/swagger-api/swagger-ui/]

### OpenAPI v3

[http://localhost:8080/openapi.json]

### References

- [https://github.com/swagger-api/swagger-ui/releases/tag/v4.5.2]
- [https://github.com/swagger-api/swagger-samples/tree/master/java/java-dropwizard]
- [https://niftysoft.github.io/devlog/2018/05/03/open-api-v3.html]
- [https://www.reonomy.com/blog/post/augmenting-dropwizard-with-swagger]
- [https://spin.atomicobject.com/2014/10/11/serving-static-assets-with-dropwizard/]

### Testing and reporting

It has been utilized JUnit 5, AspectJ and Mockito, for reporting tests and its coverage surefire and jacoco reports has
been added to site.

To view the reports use maven site goals for generation:

`./mvnw site site:run -p 8090`

- The port can be configured on pom property "site.port"

Site reports will be running on [http://localhost:8090/]

- Additionally, on the project's root path there is a very simple Postman collection to test the API (nothing really special)

`./Tasks API v1.postman_collection.json`

### TODO

- [ ] Review all TODOs on code
- [ ] Review all FIXME on code
- [ ] Do more testing!
- [ ] Document API for TaskResource
- [ ] Add site reports to docker backend image
  - [ ] Serve the site reports asset on app
- [ ] doc about surefire, jacoco
- [ ] doc about classes, bundles, etc.
- [ ] javadoc classes
