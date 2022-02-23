import React, { useState } from "react";
import useToggle from "../hooks/useToggle";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMinus } from "@fortawesome/free-solid-svg-icons";

//todo: add error handling
//todo: refactor toggler behaviour on a component
//todo: see how to reduce font awesome package size to only the min required
const AddTaskForm = (props) => {
  const [isFormOpen, toggleForm] = useToggle(false);

  const FormInternal = () => {
    const [description, setDescription] = useState("");
    const [date, setDate] = useState("");
    const [error, setError] = useState("");

    const handleDescriptionChange = (event) => {
      event.preventDefault();
      setDescription(event.target.value);
    };

    const handleDateChange = (event) => {
      event.preventDefault();
      setDate(event.target.value);
    };

    const handleSubmit = (event) => {
      event.preventDefault();
      if (description.length > 0 && date.length > 0) {
        setError("");
        props.onTaskSave({ description, date });
      } else {
        setError("All fields are required")
      }
    };

    return (
      <section className="container column">
        <div className="card">
          <form onSubmit={handleSubmit}>
            <div className="card-header">
              <h1 className="card-header-title">Add Task</h1>
            </div>
            <div className="card-content has-text-left">
              <div className="field">
                <label className="label">Description</label>
                <div className="control">
                  <input
                    className="input"
                    type="text"
                    name="description"
                    placeholder="Task description"
                    value={description}
                    onChange={handleDescriptionChange}
                  />
                </div>
              </div>
              <div className="field">
                <label className="label">Date</label>
                <div className="control" style={{ maxWidth: "fit-content" }}>
                  <input
                    className="input"
                    type="date"
                    name="date"
                    value={date}
                    onChange={handleDateChange}
                  />
                </div>
              </div>
              <div className="has-text-danger">
                <i>{error}</i>
              </div>
            </div>
            <div className="card-footer">
              <div className="field">
                <div className="control">
                  <button className="button mt-1 mb-3 ml-5 mt-3 is-dark" type="submit">
                    Save
                  </button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </section>
    );
  };

  return (
    <>
      <div className="container">
        <div className="has-text-right">
          <button className="button mr-3" onClick={toggleForm}>
            {!isFormOpen ? (
              "New task"
            ) : (
              <span className="is-size-4">
                <FontAwesomeIcon icon={faMinus} />
              </span>
            )}
          </button>
        </div>
      </div>
      {isFormOpen && <FormInternal />}
    </>
  );
};

export default AddTaskForm;
