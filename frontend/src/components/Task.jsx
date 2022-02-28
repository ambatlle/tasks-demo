import React from "react";
import Moment from "moment";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCalendar, faTrashCan } from "@fortawesome/free-solid-svg-icons";

//todo: done should'nt be kept as a state, if service crashes, could be an incosistence
const Task = ({ id, description, date, done, onDoneChange, onDeleteTask }) => {
  const [isDone, setDone] = React.useState(done);

  const handleChange = () => {
    const newDone = !isDone;
    setDone(newDone);
    onDoneChange({ id: id, done: newDone });
  };

  const handleDelete = () => {
    onDeleteTask({ id: id });
  };

  React.useEffect(() => {}, [isDone]);

  const convertDate = () => {
    return Moment(date).format("DD MMMM yyyy");
  };

  return (
    <section className="container column"  data-testid="task">
      <div className="card" data-testid={`task-${id}`}>
        <div className="card-content">
          <span className="columns is-vcentered">
            <span className="column is-one">
              <button className="button is-hovered" onClick={handleDelete}>
                <span className="icon is-normal">
                  <FontAwesomeIcon icon={faTrashCan} />
                </span>
              </button>
            </span>
            <span className="column is-11">
              <div className="has-text-left is-size-4">{description}</div>
              <div className="has-text-left is-size-7 has-text-grey">
                <span className="mr-2">
                  <FontAwesomeIcon icon={faCalendar} />
                </span>
                <span>{convertDate()}</span>
              </div>
            </span>
            <span>
              <div className="field">
                <div className="control">
                  <label className="checkbox">
                    <input
                      className="mr-5"
                      type="checkbox"
                      name={id}
                      checked={isDone}
                      onChange={handleChange}
                    />
                  </label>
                </div>
              </div>
            </span>
          </span>
        </div>
      </div>
    </section>
  );
};

export default Task;
