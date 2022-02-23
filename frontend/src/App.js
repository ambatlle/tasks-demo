import './App.css';

import 'bulma/css/bulma.min.css';
import React, { useEffect } from 'react';
import AddTaskForm from './components/AddTaskForm';
import TasksList from './components/TasksList';
import useTasksList from './hooks/useTasksList';
import ErrorBoundary from './components/ErrorBoundary';


//todo: clean console ouputs
//todo: do some tests
//todo: refactor cards in CardComponent if possible
function App() {
  const { tasks, addTask, toggleDone, isLoading } = useTasksList();

  useEffect(() => {
    console.log("tasks changed!", tasks);
  }, [tasks]);

  function taskSaveHandler(e) {
    console.log(e);
    addTask(e);
  }

  function taskDoneChangeHandler({ id, done }) {
    console.log('taskDone', id, done)
    toggleDone({ id, done });
  }

  console.log('isLoading?', isLoading);
  return (
    <>
      <main className="App">
        <section className="section">
          <div className="container title">Tasker</div>
        </section>
        <ErrorBoundary>
          <AddTaskForm onTaskSave={taskSaveHandler} />
          <TasksList list={tasks} onDoneChange={taskDoneChangeHandler} loading={isLoading} />
        </ErrorBoundary>
      </main>
    </>
  );
}

export default App;
