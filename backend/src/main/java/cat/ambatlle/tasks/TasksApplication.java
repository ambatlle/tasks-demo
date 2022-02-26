package cat.ambatlle.tasks;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.jdbi3.bundles.JdbiExceptionsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.guicey.jdbi3.JdbiBundle;
import ru.vyarus.guicey.jdbi3.installer.repository.RepositoryInstaller;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: 24/02/2022 doc class
// TODO: 24/02/2022 add logging
public class TasksApplication extends Application<TasksConfiguration> {

    private static final String ALLOWED_HEADERS =
            "X-Requested-With,Content-Type,Accept,Origin,Access-Control-Allow-Origin,Referer";
    private static final String ALLOWED_METHODS = "OPTIONS,GET,PUT,POST,DELETE,HEAD,PATCH";
    private static final String URL_PATTERNS = "/*";
    private static final String ALLOWED_ORIGINS_HEADER_VALUE = "*";

    public static void main(final String[] args) throws Exception {
        new TasksApplication().run(args);
    }

    @Override
    public String getName() {
        return "tasks";
    }

    @Override
    public void initialize(final Bootstrap<TasksConfiguration> bootstrap) {
        // Guice Dependency Injection configuration
        bootstrap.addBundle(GuiceBundle.builder().enableAutoConfig(getClass().getPackage().getName())
                .bundles(JdbiBundle.<TasksConfiguration>forDatabase((conf, env) -> conf.getDatabase()))
                .printDiagnosticInfo().build());

        // Conversion of SQLExceptions
        bootstrap.addBundle(new JdbiExceptionsBundle());

        // Flyway to create the database
        bootstrap.addBundle(new FlywayBundle<>() {
            @Override
            public DataSourceFactory getDataSourceFactory(TasksConfiguration configuration) {
                return configuration.getDatabase();
            }
        });

        // Swagger-ui
        bootstrap.addBundle(new AssetsBundle("/assets/swagger-ui/dist/", "/swagger-ui", "index.html"));

    }

    @Override
    public void run(final TasksConfiguration configuration, final Environment environment) {
        configureCors(environment);
        configureHealthChecks(configuration, environment);
        configureSwagger(environment);
    }

    private void configureSwagger(Environment environment) {
        OpenAPI oas = new OpenAPI();
        Info info = new Info().version("0.0")
                .title("Tasks API")
                .description("RESTful Tasks API.")
                .termsOfService("http://example.com/terms")
                .contact(new Contact().email("jaumea@gmail.com"));

        oas.info(info);
        SwaggerConfiguration oasConfig = new SwaggerConfiguration()
                .openAPI(oas)
                .prettyPrint(true)
                .resourcePackages(Stream.of("cat.ambatlle.tasks.resources")
                        .collect(Collectors.toSet()));
        environment.jersey().register(new OpenApiResource()
                .openApiConfiguration(oasConfig));
        environment.jersey().register(new RepositoryInstaller());
    }

    @Inject
    private void configureHealthChecks(final TasksConfiguration configuration, final Environment environment) {
        configuration.getHealthChecks().forEach((key, value) -> environment.healthChecks().register(key, value));
    }

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, ALLOWED_HEADERS);
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, ALLOWED_METHODS);
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, ALLOWED_ORIGINS_HEADER_VALUE);
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, URL_PATTERNS);
    }
}
