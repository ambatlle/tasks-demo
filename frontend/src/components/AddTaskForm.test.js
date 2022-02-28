import { render, screen, fireEvent } from '@testing-library/react';
import AddTaskForm from './AddTaskForm';
import userEvent from "@testing-library/user-event";

describe('AddTaskForm Component tests', () => {
  it('renders', () => {
    render(<AddTaskForm />);
  });

  it('shows/hides form properly when New task button is clicked', () => {
    render(<AddTaskForm />);

    const formHidden = screen.queryByTestId('addTaskForm');
    expect(formHidden).toBeNull();

    const newTaskButton = screen.getByTestId("newTaskButton");
    expect(newTaskButton.textContent).toBe("New task");

    userEvent.click(newTaskButton);

    const minusIcon = screen.queryByTestId("minusIcon");
    expect(minusIcon).not.toBeNull();

    const form = screen.queryByTestId('addTaskForm');
    expect(form).not.toBeNull();

    userEvent.click(newTaskButton);
    const formHidden1 = screen.queryByTestId('addTaskForm');
    expect(formHidden1).toBeNull();
  });

  it('has fields', () => {
    render(<AddTaskForm />);

    const newTaskButton = screen.getByTestId("newTaskButton");
    expect(newTaskButton.textContent).toBe("New task");
    userEvent.click(newTaskButton);

    const form = screen.queryByTestId('addTaskForm');
    expect(form).not.toBeNull();

    const descriptionField = screen.queryByTestId('descriptionField');
    expect(descriptionField).not.toBeNull();

    const dateField = screen.queryByTestId('dateField');
    expect(dateField).not.toBeNull();

    const saveButton = screen.queryByTestId('saveButton');
    expect(saveButton).not.toBeNull();
  });

  it('adds new task successfully', () => {

    const spy = jest.fn();
    render(<AddTaskForm onTaskSave={task => spy(task)} />);

    const formHidden = screen.queryByTestId('addTaskForm');
    expect(formHidden).toBeNull();

    const newTaskButton = screen.getByTestId("newTaskButton");
    expect(newTaskButton.textContent).toBe("New task");
    fireEvent.click(newTaskButton);

    const form = screen.queryByTestId('addTaskForm');
    expect(form).not.toBeNull();

    const descriptionField = screen.queryByTestId('descriptionField');
    expect(descriptionField).not.toBeNull();
    fireEvent.change(descriptionField, { target: { value: "Task test description" } });
    expect(descriptionField.value).toBe('Task test description');

    const dateField = screen.queryByTestId('dateField');
    expect(dateField).not.toBeNull();
    fireEvent.change(dateField, { target: { value: "2020-01-30" } });
    expect(dateField.value).toBe('2020-01-30');

    const saveButton = screen.queryByTestId('saveButton');
    expect(saveButton).not.toBeNull();
    userEvent.click(saveButton);

    const requiredError = screen.queryByTestId('requiredError');
    expect(requiredError).toBeNull();

    expect(spy).toHaveBeenCalledTimes(1)
    expect(spy).toHaveBeenCalledWith({"date": "2020-01-30", "description": "Task test description"});
  });

  it('shows required message on save empty form', () => {

    const spy = jest.fn();
    render(<AddTaskForm onTaskSave={task => spy(task)} />);

    const formHidden = screen.queryByTestId('addTaskForm');
    expect(formHidden).toBeNull();

    const newTaskButton = screen.getByTestId("newTaskButton");
    expect(newTaskButton.textContent).toBe("New task");
    userEvent.click(newTaskButton);

    const form = screen.queryByTestId('addTaskForm');
    expect(form).not.toBeNull();

    const saveButton = screen.queryByTestId('saveButton');
    expect(saveButton).not.toBeNull();
    userEvent.click(saveButton);

    const requiredError = screen.queryByTestId('requiredError');
    expect(requiredError).not.toBeNull();
  });
});