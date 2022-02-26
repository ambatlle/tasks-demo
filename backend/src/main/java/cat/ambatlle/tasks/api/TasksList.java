package cat.ambatlle.tasks.api;

import java.util.ArrayList;

/**
 * Dummy ArrayList<Task> wrapper to allow mappings of responses
 * with list of tasks in testing to make assertions easier
 */
// TODO: 25/02/2022 see if there's another way without this class
public class TasksList extends ArrayList<Task> {
    public TasksList() {
        //needed by Jackson de/serialization
    }
}