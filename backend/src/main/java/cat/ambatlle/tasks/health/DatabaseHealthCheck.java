package cat.ambatlle.tasks.health;

import cat.ambatlle.tasks.db.TaskRepository;
import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Singleton;

import javax.inject.Inject;
import javax.ws.rs.ext.Provider;

//TODO: add logging
/**
 * Health check to verify if database is running and working fine.
 */
@Provider
@Singleton
public class DatabaseHealthCheck extends HealthCheck {
    TaskRepository taskRepository;

    public DatabaseHealthCheck() {
        //used by injector
    }

    @Inject
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    protected Result check() throws Exception {
        try {
            taskRepository.findTaskById(-1);
            return Result.healthy();
        } catch (Exception exc) {
            return Result.unhealthy(exc);
        }
    }
}
