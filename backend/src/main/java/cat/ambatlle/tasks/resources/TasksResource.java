package cat.ambatlle.tasks.resources;

import cat.ambatlle.tasks.api.Task;
import cat.ambatlle.tasks.api.TasksList;
import cat.ambatlle.tasks.core.ValidationCreate;
import cat.ambatlle.tasks.core.ValidationToggleDone;
import cat.ambatlle.tasks.db.TaskRepository;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.validation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vyarus.guicey.jdbi3.tx.InTransaction;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// TODO: 24/02/2022 add some error management
// TODO: 25/02/2022 use autogenerated keys

/**
 * Tasks resource API
 */
@Path("/")
@Tag(name = "Tasks")
public class TasksResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksResource.class);

    TaskRepository taskRepository;

    @Inject
    public TasksResource(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Endpoint to get all the tasks
     *
     * @return all the tasks
     */
    @GET
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    @InTransaction
    @Operation(description = "Get all the tasks", responses = {
            @ApiResponse(responseCode = "200", description = "Tasks got successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Task.class))))})
    public TasksList getAllTasks() {
        LOGGER.debug("Getting all tasks");
        return new TasksList(taskRepository.getAllTasks());
    }

    /**
     * Endpoint to create a new task
     *
     * @param newTask the task to create
     * @return the created task
     */
    @POST
    @Timed
    @Path("/tasks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @InTransaction
    @Operation(description = "Adds a new Task", responses = {
            @ApiResponse(responseCode = "201", description = "Returns the successfully created task",
                    content = @Content(schema = @Schema(implementation = Task.class)))})
    public Response createTask(
            @Parameter(description = "Task to create") @NotNull @Valid @Validated({ValidationCreate.class})
            Task newTask) {

        LOGGER.debug("Creating a new task");
        taskRepository.insertTask(newTask);
        Task dbTask = taskRepository.findTaskById(newTask.getId());
        LOGGER.debug("Task with id {} created", dbTask.getId());
        return Response.status(Response.Status.CREATED).entity(dbTask).build();
    }

    /**
     * Changes the task's done status.
     *
     * @param id   tasks id to change status.
     * @param task task bean with done field, the rest are ignored if present.
     * @return The updated task.
     */
    @PATCH
    @Timed
    @Path("/tasks/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @InTransaction
    @Operation(description = "Changes the task done status", responses = {
            @ApiResponse(responseCode = "202", description = "Task done status changed",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Task.class)))),
            @ApiResponse(responseCode = "404", description = "Task not found")})
    public Response changeDoneStatus(
            @Parameter(description = "Task identifier", example = "321") @NotNull @PathParam(value = "id")
            @Min(1) int id, @Parameter(description = "Task", example = "{\"done\": \"false\"}") @NotNull @Valid
            @Validated({ValidationToggleDone.class}) Task task) {
        LOGGER.debug("Changing done status to task {} to {}", id, task.isDone());
        final int numRows = taskRepository.toggleDone(id, task.isDone());
        if (numRows > 0) {
            LOGGER.debug("Task with id {} updated", id);
            return Response.status(Response.Status.ACCEPTED).entity(taskRepository.findTaskById(id)).build();
        }
        LOGGER.debug("Task with id {} not found", id);
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    /**
     * Deletes a task
     *
     * @param id task's id to delete
     * @return response with result status of delete.
     */
    @DELETE
    @Timed
    @Path("/tasks/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @InTransaction
    @Operation(description = "Delete a task",
            responses = {@ApiResponse(responseCode = "204", description = "Task deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Task not found")})
    public Response removeTask(
            @Parameter(description = "Task identifier", example = "321") @NotNull @PathParam(value = "id") int id) {
        LOGGER.debug("Deleting task with id {}", id);
        int numRows = taskRepository.deleteTaskById(id);
        if (numRows > 0) {
            LOGGER.debug("Task with id {} deleted", id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        LOGGER.debug("Task with id {} not found", id);
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
