import './App.css';


import React, { useEffect } from 'react';
import AddTaskForm from './components/AddTaskForm';
import TasksList from './components/TasksList';
import useTasksList from './hooks/useTasksList';



//todo: clean console ouputs
//todo: do some tests
//todo: refactor cards in CardComponent if possible
function App() {
  const { tasks, addTask, toggleDone, isLoading, error } = useTasksList();

  if (error) {
    throw new Error(error); //it should be catched by ErrorBoundary
  }

  return (
    <>
      <main className="App">
        <section className="section">
          <div className="container title">Tasker</div>
        </section>
        <AddTaskForm onTaskSave={task => addTask(task)} />
        <TasksList list={tasks} onDoneChange={({ id, done }) => toggleDone({ id, done })} loading={isLoading} />
      </main>
    </>
  );
}

export default App;
