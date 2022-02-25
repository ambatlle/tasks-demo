import { useState, useEffect } from "react";
import TasksService from "../services/TasksService";

//todo: use redux instead of states
const useTasksList = () => {
  async function getTasks() {
    setLoading(true);
    let t = await TasksService.getTasks();
    return t;
  }

  async function addTask(task) {
    let newTask = await TasksService.addNewTask({
      ...task,
      id: tasks.length + 1,
      done: false,
    });
    setTasks([newTask, ...tasks]);
  }

  function toggleDone(...args) {
    TasksService.toggleDone(...args);
  }

  function deleteTask({ id }) {
    TasksService.deleteTask({ id })
      .then(() => {
        setLoading(false);
        let index = tasks.findIndex((task) => {
          return id === task.id;
        });
        let newList = [...tasks.slice(0, index), ...tasks.slice(index + 1)];
        setTasks(newList);
      })
      .catch((err) => {
        setLoading(false);
        setError(err);
      });
  }

  const [isLoading, setLoading] = useState(true);
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState(undefined);

  useEffect(() => {
    let aux = getTasks();
    aux
      .then((json) => {
        setTasks(json);
        setLoading(false);
      })
      .catch((err) => {
        setLoading(false);
        setError(err);
      });
  }, []);

  return { tasks, addTask, toggleDone, deleteTask, isLoading, error };
};

export default useTasksList;
