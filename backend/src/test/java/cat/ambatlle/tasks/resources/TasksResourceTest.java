package cat.ambatlle.tasks.resources;

import cat.ambatlle.tasks.api.Task;
import cat.ambatlle.tasks.api.TasksList;
import cat.ambatlle.tasks.db.TaskRepository;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class TasksResourceTest {

    public static final int UNPROCESSABLE_STATUS_CODE = 422;
    public static final int CREATED = Response.Status.CREATED.getStatusCode();
    private static final TaskRepository TASK_REPOSITORY = mock(TaskRepository.class);
    private static final ResourceExtension EXT =
            ResourceExtension.builder().addResource(new TasksResource(TASK_REPOSITORY)).build();
    public Invocation.Builder common_request;
    private TasksList tasks;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setup() {
        tasks = new TasksList();
        task1 = new Task();
        task1.setId(1);
        task1.setDescription("Testing purposes task 1");
        task1.setDone(false);
        task1.setDate("2022-02-25");
        tasks.add(task1);
        task2 = new Task();
        task2.setId(2);
        task2.setDescription("Testing purposes task 2");
        task2.setDone(false);
        task2.setDate("2022-02-25");
        tasks.add(task2);

        common_request = EXT.target("/tasks").request(MediaType.APPLICATION_JSON_TYPE);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(TASK_REPOSITORY);
    }

    @Test
    @DisplayName("Getting all the tasks happy path")
    void getAllTasks() {
        when(TASK_REPOSITORY.getAllTasks()).thenReturn(tasks);
        try (Response response = common_request.get()) {

            final TasksList found = response.readEntity(TasksList.class);

            assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
            assertThat(this.tasks.size()).isEqualTo(found.size()); // FIXME: 26/02/2022 redundant?
            assertThat(this.tasks).isEqualTo(found);
            verify(TASK_REPOSITORY).getAllTasks();
            verifyNoMoreInteractions(TASK_REPOSITORY);
        }
    }

    @Test
    @DisplayName("Creating a new task happy path")
    void createTask() {
        when(TASK_REPOSITORY.findTaskById(task1.getId())).thenReturn(task1);
        try (Response response = common_request.post(Entity.json(task1))) {

            final Task returned = response.readEntity(Task.class);

            assertThat(response.getStatus()).isEqualTo(CREATED);
            // TODO: 26/02/2022 change it to assert about generated Id when change is done
            assertThat(task1).isEqualTo(returned);
            verify(TASK_REPOSITORY).insertTask(task1);
            verify(TASK_REPOSITORY).findTaskById(task1.getId());
            verifyNoMoreInteractions(TASK_REPOSITORY);
        }
    }

    @Test
    @DisplayName("Creating a new task with null task should fail as unprocessable")
    void createTaskNotNull() {
        try (Response response = common_request.post(Entity.json(null))) {
            assertThat(response.getStatus()).isEqualTo(UNPROCESSABLE_STATUS_CODE);
            verifyZeroInteractions(TASK_REPOSITORY);
        }
    }

    @Test
    @DisplayName("Creating a new task with null required fields should fail")
    void createTaskNotValid() {
        Task taskNotValid = new Task(-1, null, null, false);
        try (Response response = common_request.post(Entity.json(taskNotValid))) {

            assertThat(response.getStatus()).isEqualTo(UNPROCESSABLE_STATUS_CODE);
            verifyZeroInteractions(TASK_REPOSITORY);
        }
    }

    @Test
    @DisplayName("Toggling task done status happy path")
    void changeDoneStatus() {
        final Task statusTask = new Task(2, null, null, true);
        final Task taskAfter = new Task(2, "test description", "2022-03-30", true);

        when(TASK_REPOSITORY.toggleDone(statusTask.getId(), statusTask.isDone())).thenReturn(1);
        when(TASK_REPOSITORY.findTaskById(statusTask.getId())).thenReturn(taskAfter);

        try (Response response = EXT.target("/tasks/" + statusTask.getId()).request(MediaType.APPLICATION_JSON_TYPE)
                .method("PATCH", Entity.json(statusTask))) {

            final Task returned = response.readEntity(Task.class);

            assertThat(response.getStatus()).isEqualTo(Response.Status.ACCEPTED.getStatusCode());
            assertThat(taskAfter).isEqualTo(returned);
            verify(TASK_REPOSITORY).toggleDone(statusTask.getId(), statusTask.isDone());
            verify(TASK_REPOSITORY).findTaskById(statusTask.getId());
            verifyNoMoreInteractions(TASK_REPOSITORY);
        }
    }

    @Test
    @DisplayName("Toggling task done status on a not found task")
    void changeDoneStatusNotFound() {
        final Task statusTask = new Task(2, null, null, true);
        when(TASK_REPOSITORY.toggleDone(statusTask.getId(), statusTask.isDone())).thenReturn(0);
        try (Response response = EXT.target("/tasks/" + statusTask.getId()).request(MediaType.APPLICATION_JSON_TYPE)
                .method("PATCH", Entity.json(statusTask))) {

            assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
            verify(TASK_REPOSITORY).toggleDone(statusTask.getId(), statusTask.isDone());
            verifyNoMoreInteractions(TASK_REPOSITORY);
        }
    }

    @Test
    @DisplayName("Deleting a task happy path")
    void removeTask() {
        when(TASK_REPOSITORY.deleteTaskById(2)).thenReturn(1);
        try (Response response = EXT.target("/tasks/2").request().delete()) {
            assertThat(response.getStatus()).isEqualTo(Response.Status.NO_CONTENT.getStatusCode());
            verify(TASK_REPOSITORY).deleteTaskById(2);
            verifyNoMoreInteractions(TASK_REPOSITORY);
        }
    }

    @Test
    @DisplayName("Deleting a no existing task should return not found")
    void removeTaskNotFound() {
        when(TASK_REPOSITORY.deleteTaskById(2)).thenReturn(0);
        try (Response response = EXT.target("/tasks/2").request().delete()) {
            assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
            verify(TASK_REPOSITORY).deleteTaskById(2);
            verifyNoMoreInteractions(TASK_REPOSITORY);
        }
    }
}