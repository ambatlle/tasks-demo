import { render, screen } from '@testing-library/react';
import TasksList from './TasksList';
import userEvent from "@testing-library/user-event";

describe('TaskList Component tests', () => {
  it('without tasks renders no task', () => {
    render(<TasksList list={[]} />);
    const tasksList = screen.queryAllByTestId("taskList");
    expect(tasksList.length).toBe(1);
    const tasks = screen.queryAllByTestId("task");
    expect(tasks.length).toBe(0);
    const numTasksTitle = screen.getByTestId("numTasks");
    expect(numTasksTitle).not.toBeNull();
    expect(numTasksTitle.textContent).toBe("0 Tasks");

    const noTasksMessage = screen.getByTestId("noTasksMessage");
    expect(noTasksMessage).not.toBeNull();
    expect(noTasksMessage.textContent).toBe("Bravo! You have no pending tasks!");
  });

  it('renders the tasks', () => {
    const tasks = [{
      "id": 1,
      "description": "Future-proofed content-based strategy",
      "date": "2021-05-02",
      "done": true
    }, {
      "id": 2,
      "description": "Cross-platform exuding open system",
      "date": "2021-11-24",
      "done": false
    }, {
      "id": 3,
      "description": "Balanced analyzing forecast",
      "date": "2021-09-03",
      "done": false
    }, {
      "id": 4,
      "description": "Devolved scalable framework",
      "date": "2022-01-24",
      "done": true
    }, {
      "id": 5,
      "description": "Object-based reciprocal policy",
      "date": "2022-02-10",
      "done": false
    }];

    render(<TasksList list={tasks} />);
    const tasksElements = screen.queryAllByTestId("task");
    expect(tasksElements.length).toBe(5);
    const numTasksTitle = screen.getByTestId("numTasks");
    expect(numTasksTitle).not.toBeNull();
    expect(numTasksTitle.textContent).toBe("5 Tasks");

  });

  it('captures onDoneChange event', () => {
    const spy = jest.fn();
    const tasks = [{
      "id": 45,
      "description": "Balanced analyzing forecast",
      "date": "2021-09-03",
      "done": false
    }];
    render(<TasksList list={tasks}
      onDoneChange={({ id, done }) => spy({ id, done })} />);

    const taskDone = screen.getByRole('checkbox');
    userEvent.click(taskDone);
    expect(spy).toHaveBeenCalledTimes(1)
    expect(spy).toHaveBeenCalledWith({ "done": true, "id": 45 });
  });

  it('captures onDeleteTask event', () => {
    const spy = jest.fn();
    const tasks = [{
      "id": 45,
      "description": "Balanced analyzing forecast",
      "date": "2021-09-03",
      "done": false
    }];
    render(<TasksList list={tasks}
      onDeleteTask={({ id }) => spy({ id })} />);

    const deleteTask = screen.getByRole('button');
    userEvent.click(deleteTask);
    expect(spy).toHaveBeenCalledTimes(1)
    expect(spy).toHaveBeenCalledWith({id: 45});
  });
});
