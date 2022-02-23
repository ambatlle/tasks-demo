package cat.ambatlle.tasks.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {
    @JsonProperty
    private int id;
    @JsonProperty
    private String description;
    @JsonProperty
    private String date;
    @JsonProperty
    private boolean done;

    public Task() {
    }

    public Task(int id, String description, String date, boolean done) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
