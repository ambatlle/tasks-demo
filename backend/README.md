# Tasker Backend

In this project there will be the implementation for API REST used by the frontend, using Dropwizard as main development framework.

## TOC

- [Tasker Backend](#tasker-backend)
  - [TOC](#toc)
  - [How to start the Tasks API application](#how-to-start-the-tasks-api-application)
  - [Persistence](#persistence)
    - [Flyway configuration](#flyway-configuration)
  - [Health Check](#health-check)
  - [Dependency Injection](#dependency-injection)
  - [CORS](#cors)
  - [Resources](#resources)
  - [Model beans & validations](#model-beans--validations)
  - [Testing and reporting](#testing-and-reporting)
  - [OpenAPI 3 & Swagger](#openapi-3--swagger)
    - [Swagger UI](#swagger-ui)
    - [OpenAPI 3](#openapi-3)
  - [Used Tools](#used-tools)
  - [References](#references)
  - [TODO](#todo)

## How to start the Tasks API application

1. Run `mvn clean install` to build your application
2. Create and populate de H2 database `java -jar target/tasks-api-1.0-SNAPSHOT.jar db migrate config.yml`, it uses [Flyway](#flyway-configuration).
   _This will be necessary once at the beginning or every time that you need to recreate the database_
3. Start application with `java -jar target/tasks-api-1.0-SNAPSHOT.jar server config.yml`
4. To check that your application is running enter url [http://localhost:8080/sample](http://localhost:8080/sample), you should see an "ok" answer, if not, something went wrong.

**Notes:**

1. If you don't have Maven configured on your system, the provided Maven wrapper can be used
   - on Windows use `mvnw.cmd clean install`
   - on Linux/Mac use `mvnw clean install`
   - or you can use your preferred Java IDE with Maven integration
   - seems that some times due windows/linux end of line conversion there can be some problems on this files, if it happens to you, the fastter way that you have is use a tool like `dos2unix`, oldy but effective. Or you could fix it configuring [git EOL management](https://docs.github.com/en/get-started/getting-started-with-git/configuring-git-to-handle-line-endings) to use Linux style EOL.

## Persistence

For practical reasons a H2 file database has been used, by default it's stored on `~/tasksApp/dbprod*`, you can customize it on the [config.yml](./config.yml) file, although Docker is using this path to build the images, so be carefull.

The database interaction is handled by a JDBI3 Repository available on class [TaskRepository](./cat/../src/main/java/cat/ambatlle/tasks/db/TaskRepository.java)

### Flyway configuration

To create the database schema objects and populate them Flyway dropwizard integration is used, it is registered as bundle on [TasksApplication.initialize](./cat/../src/main/java/cat/ambatlle/tasks/TasksApplication.java), and it takes the table definition and its data from `./resources/db/migration` path.

## Health Check

To see your application's health enter url [http://localhost:8081/healthcheck](http://localhost:8081/healthcheck), a basic Database Healthcheck has been added.

Available on package [cat.ambatlle.tasks.health](./cat/../src/main/java/cat/ambatlle/tasks/health/DatabaseHealthCheck.java) and registered on [TasksApplication.configureHealthChecks](./cat/../src/main/java/cat/ambatlle/tasks/TasksApplication.java) method. To add more health checks, you can register them on [TasksConfiguration.getHealthChecks](./cat/../src/main/java/cat/ambatlle/tasks/TasksConfiguration.java) method.

## Dependency Injection

To manage dependencies between components of the application and avoid a thight coupling and to favor composition of components, it uses [Dropwizard guicey](#used-tools), which is a dropwizard integration of Google Guice.

With Dropwizard guicey:

- the application will inject the dependencies on the classes that need it, using the `@Inject` annotation on their dependencies.
- automatically discovers and registers all the Resources that have the annotation `@Path`
- also, will discover and register all the Repositories that have the annotation `@JdbiRepository`, automatically too.

This integration is regitered on [TasksApplication.initialize](./cat/../src/main/java/cat/ambatlle/tasks/TasksApplication.java).

_**Note:** Due the backends simplicity and to avoid over-engineering no interfaces have been used, favoring implementation over interface. Too get a lighter coupling, it could be done with implementations following the required interfaces._

## CORS

To make it simplier the backend configures a CORS policy that allows access from everywhere. And improvement could be configure it to only allow connections from trusted origins.

The CORS configuration can be seen at [TasksApplication.configureCors](./cat/../src/main/java/cat/ambatlle/tasks/TasksApplication.java) method.

## Resources

The Tasks API resource is available on [TasksResource](./src/main/java/cat/ambatlle/tasks/resources/TasksResource.java), it offers all the endpoints necessary by the frontend interacting with the injected [TaskRepository](./cat/../src/main/java/cat/ambatlle/tasks/db/TaskRepository.java).

See [Swagger UI](#swagger-ui) for more detailed documentation about the Resource API and its endpoints.

## Model beans & validations

- On package `cat.ambatlle.tasks.api` you can find the two beans used to model de Tasks data.
- On package `cat.ambatlle.tasks.core` you can find the validation interfaces to define validation groups, they are used to be applied to model beans and `TasksResource` endpoint's input parameters.

## Testing and reporting

To test the created resources it has been used JUnit, AspectJ, Mockito and Mockaroo (to generate some random data).

They can be run using `./mvnw test` goal.

To report the testing results it has been used the Surefire reports plugin and for testing coverage the Jacoco reports plugin. They can be generated with Maven's site goals.

To view the reports use maven site goals for generation:

`./mvnw site site:run`

- The port can be configured on pom property `site.port`

Site reports will be running on [http://localhost:8090/](http://localhost:8090/)

Additionally, on the backends's root folder there is a very simple Postman collection to test the API endpoints (nothing really special)

`./Tasks API v1.postman_collection.json`

## OpenAPI 3 & Swagger

### Swagger UI

Available on [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

When the backend is running, it serves the swagger dist SPA located on src/main/resources/assets/swagger-ui/dist

Downloaded from [https://github.com/swagger-api/swagger-ui/](https://github.com/swagger-api/swagger-ui/)

It's configured on [TasksApplication.initialize](./cat/../src/main/java/cat/ambatlle/tasks/TasksApplication.java) method as an Asset Bundle.

### OpenAPI 3

OpenAPI 3 spec for Tasks Resources can be accessed on [http://localhost:8080/openapi.json](http://localhost:8080/openapi.json), or for yaml format on [http://localhost:8080/openapi.yaml](http://localhost:8080/openapi.yaml)

The API has been documented with OpenAPI annotations, this info its shown in Swagger UI and added to the spec.

It's configured and registered on [TasksApplication.configureSwagger](./cat/../src/main/java/cat/ambatlle/tasks/TasksApplication.java) method.

## Used Tools

- [Mockaroo](https://www.mockaroo.com/) to generate some mock data for frontend json-server & flyway
- [Spring Boot Maven wrapper (and Maven)](https://start.spring.io/)
- [Docker](https://www.docker.com/)
- [Dropwizard](https://www.dropwizard.io/en/latest/index.html)
- [Dropwizard flyway integration](https://github.com/dropwizard/dropwizard-flyway)
- [H2 Database](https://www.h2database.com/html/main.html)
- [Dropwizard-guicey](https://github.com/xvik/dropwizard-guicey) Google Guize dependency injector dropwizard integration.
- [JDBI3](https://www.dropwizard.io/en/latest/manual/jdbi3.html)
- [Guicey-jdbi3](https://github.com/xvik/dropwizard-guicey-ext/tree/master/guicey-jdbi3): Integrates JDBI3 with guice. Based on dropwizard-jdbi3 integration.
- [AssertJ](https://joel-costigliola.github.io/assertj/)
- [Mockito](https://site.mockito.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Postman](https://www.postman.com/)
- [IntelliJ IDEA EAP](https://www.jetbrains.com/idea/nextversion/#section=windows)
- [Swagger](https://github.com/swagger-api/swagger-core)
- [Jacoco testing coverage reporting](https://www.jacoco.org/jacoco/trunk/doc/index.html)
- [Surefire testing results reporting](https://maven.apache.org/surefire/maven-surefire-report-plugin/index.html)
- [Duckdns](https://www.duckdns.org/) DNS for OCI public IP

## References

- [Swagger UI](https://github.com/swagger-api/swagger-ui/releases/tag/v4.5.2)
- [Swagger Dropwizard Samples](https://github.com/swagger-api/swagger-samples/tree/master/java/java-dropwizard)
- [Open API v3 and DropWizard](https://niftysoft.github.io/devlog/2018/05/03/open-api-v3.html)
- [Augmenting Dropwizard with Swagger and Rollbar](https://www.reonomy.com/blog/post/augmenting-dropwizard-with-swagger)
- [Serving Static Assets with DropWizard](https://spin.atomicobject.com/2014/10/11/serving-static-assets-with-dropwizard/)

## TODO

- [ ] **Important**: Add error handling on TasksResources?
- [ ] Review all TODOs on code
- [ ] Review all FIXME on code
- [ ] Do more testing!
- [ ] Add site reports to docker backend image?
  - [ ] Serve the site reports asset on app?
