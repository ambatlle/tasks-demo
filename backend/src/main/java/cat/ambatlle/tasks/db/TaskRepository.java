package cat.ambatlle.tasks.db;

import cat.ambatlle.tasks.api.Task;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository;
import ru.vyarus.guicey.jdbi3.tx.InTransaction;

import java.util.List;

/**
 * Repository to interact with Tasks database table.
 */
@JdbiRepository
@InTransaction
public interface TaskRepository {
    // TODO: 24/02/2022 use date instead of varchar

    /**
     * Inserts a new task on database.
     *
     * @param task task to insert.
     */
    @SqlUpdate(
            "INSERT INTO \"task\" (id, \"description\", \"date\", \"done\") VALUES (:id, :description, :date, :done)")
    void insertTask(@BindBean Task task);

    /**
     * Get all the tasks from database.
     *
     * @return all tasks on database.
     */
    @SqlQuery("SELECT * FROM \"task\" ORDER BY id DESC")
    @RegisterBeanMapper(Task.class)
    List<Task> getAllTasks();

    /**
     * Finds a task by its id on database.
     *
     * @param id Task's id to look for
     * @return the task data or <code>null</code> if task is not found
     */
    @SqlQuery("SELECT * FROM \"task\" WHERE id = :id")
    @RegisterBeanMapper(Task.class)
    Task findTaskById(@Bind("id") int id);

    /**
     * Marks a Task as done or undone on database.
     *
     * @param id Task's id
     * @param done <code>false</code> to mark it as undone, <code>true</code> to mark it as done.
     * @return
     */
    @SqlUpdate(
            "UPDATE \"task\" SET \"done\" = :done WHERE id = :id")
    int toggleDone(@Bind("id") int id, @Bind("done") boolean done);

    /**
     * Update all task data based on its id.
     *
     * @param task Task data to modify
     * @return the number of rows updated
     */
    @SqlUpdate(
            "UPDATE \"task\" SET \"description\" = :description, \"date\" = :date, \"done\" = :done WHERE id = :id")
    int updateTask(@BindBean Task task);

    /**
     * Deletes a Task from database.
     *
     * @param id Task's id to delete.
     * @return the number of rows deleted.
     */
    @SqlUpdate(
            "DELETE FROM \"task\" WHERE id = :id")
    int deleteTaskById(@Bind("id") int id);
}