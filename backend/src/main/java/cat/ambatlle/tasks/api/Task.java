package cat.ambatlle.tasks.api;

import cat.ambatlle.tasks.core.ValidationCreate;
import cat.ambatlle.tasks.core.ValidationToggleDone;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

// TODO: 24/02/2022 doc class
// TODO: 24/02/2022 map date as a real Date
public class Task {
    @NotNull(groups = {ValidationCreate.class, ValidationToggleDone.class})
    @Min(value = 1, groups = {ValidationCreate.class, ValidationToggleDone.class})
    private int id;
    @NotNull(groups = ValidationCreate.class)
    private String description = "";
    @NotNull(groups = ValidationCreate.class)
    private String date = "";
    @NotNull
    private boolean done;

    public Task() {
        //Jackson required
    }

    public Task(int id, String description, String date, boolean done) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.done = done;
    }

    @Override
    public String toString() {
        return "Task{" + "id=" + id + ", description='" + description + '\'' + ", date='" + date + '\'' + ", done=" +
                done + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return getId() == task.getId() && isDone() == task.isDone() && getDescription().equals(task.getDescription()) &&
                getDate().equals(task.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getDate(), isDone());
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
