import './App.css';


import React from 'react';
import AddTaskForm from './components/AddTaskForm';
import TasksList from './components/TasksList';
import useTasksList from './hooks/useTasksList';

function App() {
  const { tasks, addTask, toggleDone, deleteTask, isLoading, error } = useTasksList();

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
        <TasksList list={tasks}
          onDoneChange={({ id, done }) => toggleDone({ id, done })}
          onDeleteTask={({ id }) => deleteTask({ id })}
          loading={isLoading} />
      </main>
    </>
  );
}

export default App;
