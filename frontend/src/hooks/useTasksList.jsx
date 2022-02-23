import { useState, useEffect } from "react";
import TasksService from "../services/TasksService";

//todo: use redux instead of states
const useTasksList = () => {
  const [isLoading, setLoading] = useState(true);
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    setLoading(true);
    console.log('is loading...');
    TasksService.getTasks()
      .then((json) => {
        setTasks(json);
        setLoading(false);
        console.log('has loaded!');
      });
  }, []);

  return { tasks, addTask, toggleDone, isLoading };

  async function addTask(task) {
    let newTask = await TasksService.addNewTask({...task, id: tasks.length + 1, done: false});
    let newTasksList = tasks.concat(newTask);
    setTasks(newTasksList);
  }

  function toggleDone(...args) {
    console.log("toggling done", JSON.stringify(args));
    TasksService.toggleDone(...args);
  }
};

export default useTasksList;
