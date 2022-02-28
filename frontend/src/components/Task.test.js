import { render, screen } from '@testing-library/react';
import Task from './Task';
import userEvent from "@testing-library/user-event";

describe('Task Component tests', () => {
  it('renders fields & save button', () => {
    render(<Task
      date="2021-04-05"
      description="Test description task"
      id={42}
      done={false}
      index={0}
      onDoneChange={(args) => {
        //props.onDoneChange({ id: args.id, done: args.done });
      }}
      onDeleteTask={({ id }) => {
        //props.onDeleteTask({id: id});
      }}
    />);
    const testId = screen.getByTestId("task");
    expect(testId).not.toBeNull();
    const testId2 = screen.getByTestId("task-42");
    expect(testId2).not.toBeNull();
    expect(testId).not.toBeNull();
    const taskDescription = screen.getByText(/Test description task/i);
    expect(taskDescription).toBeInTheDocument();
    const taskDate = screen.getByText(/05 April 2021/i);
    expect(taskDate).toBeInTheDocument();
    const taskDone = screen.getByRole('checkbox');
    expect(taskDone).not.toBeNull();
    // done is false in the expected
    expect(taskDone).not.toBeChecked();
  });

  it('emits onDoneChange event', () => {
    const spy = jest.fn();
    render(<Task
      date="2021-04-05"
      description="Test description task"
      id={22}
      done={false}
      index={0}
      onDoneChange={(args) => {
        spy(args);
      }}
      onDeleteTask={({ id }) => {
        //do-nothing
      }}
    />);
    const taskDone = screen.getByRole('checkbox');
    userEvent.click(taskDone);
    expect(taskDone).toBeChecked();
    expect(spy).toHaveBeenCalledTimes(1)
    expect(spy).toHaveBeenCalledWith({"done": true, "id": 22});
  });

  it('emits onDeleteTask event', () => {
    const spy = jest.fn();
    render(<Task
      date="2021-04-05"
      description="Test description task"
      id={22}
      done={false}
      index={0}
      onDoneChange={(args) => {
        spy(args);
      }}
      onDeleteTask={({ id }) => {
        spy(id);
      }}
    />);
    const deleteTask = screen.getByRole('button');
    userEvent.click(deleteTask);
    expect(spy).toHaveBeenCalledTimes(1)
    expect(spy).toHaveBeenCalledWith(22);
  });

});
