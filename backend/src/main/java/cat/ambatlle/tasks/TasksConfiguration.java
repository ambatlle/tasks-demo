package cat.ambatlle.tasks;

import cat.ambatlle.tasks.health.DatabaseHealthCheck;
import com.codahale.metrics.health.HealthCheck;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Tasks App Configuration
 */
// TODO: 24/02/2022 try to use a pooled DS
public class TasksConfiguration extends Configuration {
    private static final String DATABASE_HEALTH = "database";

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @Inject
    private DatabaseHealthCheck databaseHealthCheck;

    /**
     * Gets the database DataSourceFactory based on configuration file.
     *
     * @return the application DataSourceFactory
     */
    public DataSourceFactory getDatabase() {
        return database;
    }

    /**
     * Gets all the health checks to configure in the application startup
     * @return all the health checks to configure
     */
    public Map<String, HealthCheck> getHealthChecks() {
        Map<String, HealthCheck> healthChecks = new HashMap<>();
        healthChecks.put(DATABASE_HEALTH, this.databaseHealthCheck);
        return healthChecks;
    }

    @Inject
    public void setDatabaseHealthCheck(DatabaseHealthCheck databaseHealthCheck) {
        this.databaseHealthCheck = databaseHealthCheck;
    }
}
