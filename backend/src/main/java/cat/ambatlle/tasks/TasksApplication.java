package cat.ambatlle.tasks;

import cat.ambatlle.tasks.resources.HelloResource;
import cat.ambatlle.tasks.resources.TasksResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

public class TasksApplication extends Application<TasksConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TasksApplication().run(args);
    }

    @Override
    public String getName() {
        return "tasks";
    }

    @Override
    public void initialize(final Bootstrap<TasksConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final TasksConfiguration configuration, final Environment environment) {
        // TODO: implement application
        registerResources(environment);
        configureCors(environment);
    }

    private void registerResources(Environment environment) {
        environment.jersey().register(new HelloResource());
        environment.jersey().register(new TasksResource());
    }

    private void configureCors(Environment environment) {
        // Enable CORS headers
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
                "X-Requested-With,Content-Type,Accept,Origin,Access-Control-Allow-Origin,Referer");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD,PATCH");
        cors.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }

}
