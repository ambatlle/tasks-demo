package cat.ambatlle.tasks.resources;

import cat.ambatlle.tasks.api.Task;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Path("/tasks")
public class TasksResource {

    private static final List<Task> tasks = new ArrayList<>();

    static {
        tasks.add(new Task(1, "task 1", "2022-02-01", false));
        tasks.add(new Task(2, "task 2", "2022-01-05", false));
        tasks.add(new Task(3, "task 3", "2022-03-10", false));
        tasks.add(new Task(4, "task 4", "2022-04-21", false));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTasks() {
        return tasks;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTask(Task newTask) {
        tasks.add(newTask);
        return Response.status(Response.Status.CREATED).entity(newTask).build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeDoneStatus(@PathParam(value = "id") int id, Task task) {
        final Optional<Task> first = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (first.isPresent()) {
            final Task updateTask = first.get();
            updateTask.setDone(task.isDone());
            return Response.status(Response.Status.ACCEPTED).entity(updateTask).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeTask(@PathParam(value = "id") int id, Task task) {
        final boolean removed = tasks.removeIf(t -> t.getId() == id);
        if(removed) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
