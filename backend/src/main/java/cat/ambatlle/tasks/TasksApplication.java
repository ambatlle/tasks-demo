package cat.ambatlle.tasks;

import cat.ambatlle.tasks.resources.HelloResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
    public void run(final TasksConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        environment.jersey().register(new HelloResource());
    }

}
