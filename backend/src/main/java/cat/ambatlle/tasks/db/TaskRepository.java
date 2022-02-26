package cat.ambatlle.tasks.db;

import cat.ambatlle.tasks.api.Task;
import cat.ambatlle.tasks.api.TasksList;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import ru.vyarus.guicey.jdbi3.installer.repository.JdbiRepository;
import ru.vyarus.guicey.jdbi3.tx.InTransaction;

@JdbiRepository
@InTransaction
public interface TaskRepository {
    // TODO: 24/02/2022 use date instead of varchar

    @SqlUpdate(
            "INSERT INTO \"task\" (id, \"description\", \"date\", \"done\") VALUES (:id, :description, :date, :done)")
    void insertTask(@BindBean Task task);

    @SqlQuery("SELECT * FROM \"task\" ORDER BY id DESC")
    @RegisterBeanMapper(Task.class)
    TasksList getAllTasks();

    @SqlQuery("SELECT * FROM \"task\" WHERE id = :id")
    @RegisterBeanMapper(Task.class)
    Task findTaskById(@Bind("id") int id);

    @SqlUpdate(
            "UPDATE \"task\" SET \"done\" = :done WHERE id = :id")
    int toggleDone(@Bind("id") int id, @Bind("done") boolean done);

    @SqlUpdate(
            "UPDATE \"task\" SET \"description\" = :description, \"date\" = :date, \"done\" = :done WHERE id = :id")
    int updateTask(@BindBean Task task);

    @SqlUpdate(
            "DELETE FROM \"task\" WHERE id = :id")
    int deleteTaskById(@Bind("id") int id);
}