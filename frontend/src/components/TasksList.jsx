import React from "react";
import Task from "./Task";

const TasksList = (props) => {
  let hasTasks = props.list && props.list.length > 0;
  let numTasks = props.list ? props.list.length : 0;

  return (
    <section className="container column is-loading">
      <div className="card">
        <div className="card-header has-text-right">
          <h1 className="card-header-title">{numTasks} Tasks</h1>
        </div>
        <div className="card-content" style={{maxHeight: "75vh", overflowY: "scroll"}}>
          {hasTasks ? (
            props.list.map((task, index) => {
              return (
                <Task
                  key={task.id.toString()}
                  date={task.date}
                  description={task.description}
                  id={task.id}
                  done={task.done}
                  index={index}
                  onDoneChange={(args) => {
                    props.onDoneChange({ id: args.id, done: args.done });
                  }}
                />
              );
            })
          ) : !props.loading ? (
            <i>Bravo! You have no pending tasks!</i>
          ) : (
            <div className="columns">
              <div className="column is-full">
                <div className="loader is-loading is-size-1"/>
              </div>
            </div>
          )}
        </div>
      </div>
    </section>
  );
};

export default TasksList;
