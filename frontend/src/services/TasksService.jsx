//todo: error management
//todo: ENV parameter for api host
class TasksService {
  static instance = TasksService.instance || new TasksService();

  async sendData(url = "", data = {}, method = "POST") {
    const response = await fetch(url, {
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

  async addNewTask(task) {
    let newTask = { ...task };

    let postResponse = await TasksService.instance
      .sendData("http://localhost:8080/tasks", newTask)
      .then((data) => {
        return data;
      });

    return postResponse;
  }

  handleErrors(response) {
    if (!response.ok) {
      //return {error: response};
      return Promise.reject({ error: {code: response.status, message: response.statusText }});
    }
    return response;
  }

  getTasks() {
    return fetch("http://localhost:8080/tasks", {
      mode: "cors",
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "*",
      },
    })
      .then((response) => TasksService.instance.handleErrors(response))
      .catch((err) => {
        return Promise.reject(err);
      })
      .then((res) => {
        return res.json();
      });
  }

  async toggleDone({ id, done }) {
    let patchResponse = await TasksService.instance
      .sendData(`http://localhost:8080/tasks/${id}`, { done }, "PATCH")
      .then((data) => {
        return data;
      });

    return patchResponse;
  }
}

export default TasksService.instance;
