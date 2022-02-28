//todo: error management
//todo: ENV parameter for api host
class TasksService {
  static server = process.env.REACT_APP_API_SERVER || "localhost";
  static port = process.env.REACT_APP_API_PORT || 8080;
  static apiUrl = `http://${TasksService.server}:${TasksService.port}/tasks`;
  static instance = TasksService.instance || new TasksService();

  async sendBodyData(url = "", data = {}, method = "POST") {
    const response = await fetch(url, {
      timeout: 2500,
      method: method,
      mode: "cors",
      cache: "no-cache",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      redirect: "follow",
      referrerPolicy: "no-referrer",
      body: JSON.stringify(data),
    });
    return response.json();
  }

  async sendUrlData(url = "", method = "GET") {
    const response = await fetch(url, {
      timeout: 2500,
      method: method,
      mode: "cors",
      cache: "no-cache",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
      redirect: "follow",
      referrerPolicy: "no-referrer",
    })
      .then((response) => TasksService.instance.handleErrors(response))
      .catch((err) => {
        return Promise.reject(err);
      })
      .then((res) => {
        if (res.status === 200) {
          return res.json();
        } else {
          return {};
        }
      });
    return response;
  }

  async addNewTask(task) {
    let newTask = { ...task };

    let postResponse = await TasksService.instance
      .sendBodyData(TasksService.apiUrl, newTask)
      .then((data) => {
        return data;
      });

    return postResponse;
  }

  handleErrors(response) {
    if (!response.ok) {
      return Promise.reject({
        error: { code: response.status, message: response.statusText },
      });
    }
    return response;
  }

  async getTasks() {
    let response = await TasksService.instance.sendUrlData(TasksService.apiUrl);
    return response;
  }

  async toggleDone({ id, done }) {
    let patchResponse = await TasksService.instance
      .sendBodyData(`${TasksService.apiUrl}/${id}`, { done }, "PATCH")
      .then((data) => {
        return data;
      });

    return patchResponse;
  }

  deleteTask({ id }) {
    return TasksService.instance.sendUrlData(
      `${TasksService.apiUrl}/${id}`,
      "DELETE"
    );
  }
}

export default TasksService.instance;
