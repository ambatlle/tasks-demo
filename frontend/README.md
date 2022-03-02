# Tasker Frontend

- [Tasker Frontend](#tasker-frontend)
  - [How to start the Tasks frontend](#how-to-start-the-tasks-frontend)
    - [Json Server](#json-server)
  - [Testing](#testing)
  - [Front end components by folder](#front-end-components-by-folder)
    - [Used tools](#used-tools)
  - [TODO](#todo)
  - [*React default generated documentation*](#react-default-generated-documentation)
    - [Getting Started with Create React App](#getting-started-with-create-react-app)
    - [Available Scripts](#available-scripts)
      - [`npm start`](#npm-start)
      - [`npm test`](#npm-test)
      - [`npm run build`](#npm-run-build)
      - [`npm run eject`](#npm-run-eject)
      - [Learn More](#learn-more)
      - [Code Splitting](#code-splitting)
      - [Analyzing the Bundle Size](#analyzing-the-bundle-size)
      - [Making a Progressive Web App](#making-a-progressive-web-app)
      - [Advanced Configuration](#advanced-configuration)
      - [Deployment](#deployment)
      - [`npm run build` fails to minify](#npm-run-build-fails-to-minify)

## How to start the Tasks frontend

1. Run `npm install`
2. Run `npm run start`
3. Access to [Tasker](http://localhost:3000/) web

***For additional information about standard goals included you can read [*React default documentation*](#react-default-documentation)***

***Note:*** By default on development API Server must be running on [http://localhost:8080](http://localhost:8080), look at backend's documentation for more info about it. Also the embedded [Json Server](#json-server) can be used.

### Json Server

To simplify development the [Json server](https://www.npmjs.com/package/json-server) tool has been added for mocking purposes that can be run instead of running real API.

You can run it using the added goal with `npm run server`

## Testing

A few tests for the UI Components developed has been created, on folder `./test` you can find them.

`npm run test` will execute the UI components test available on folder `./test`

Also a goal to generate coverage reporting has been added, it can be run with `npm run coverage`, it will the report on console, meanwhile it will also generate them on the folder `./coverage`

## Front end components by folder

- `./assets/mock-data`: the [Json Server](#json-server) and its mock data.
- `./components`:
  - `AddTaskForm`: Component to handle the Task's creation form and its hidding button.
  - `ErrorBoundary`: An error boundary component to handle critical errors.
  - `Task`: Component to render tasks info and its delete button and done checkbox.
  - `TaskList`: Component to iterate all over the existing tasks, includes the use of the `Task` component in a loop.
- `./hooks`:
  - `useTasksList`: Used on `App` component to keep tasks state and interact with the `TasksService`
  - `useToggle`: A very simple state hook used by the `AddTaskForm`'s "New task" button to hide/show the form.
- `./services/TasksService`: A class component to act as intermediary of frontend and backend.
- `./App`: The main component that contains the `AddTaskForm` and the `TasksList`, and uses the `useTasksList` hook.

### Used tools

- [Json server](https://www.npmjs.com/package/json-server): as mock server for development purposes.
- [Bulma CSS framework](https://bulma.io/documentation/): to ease the CSS maquetation.
- [Font Awesome](https://fontawesome.com/start): To print some nice icons.
- [Visual Studio Code](https://code.visualstudio.com/)

## TODO

- [ ] Do some more tests to increase coverage of components
- [ ] Refactorize cards in a common Card component
- [ ] Create a model for Task information
- [ ] It would be an interesting idea to use Redux
- [ ] Create a component Toggler with de useToggler logic encapsulation.

## *React default generated documentation*

### Getting Started with Create React App

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

### Available Scripts

In the project directory, you can run:

#### `npm start`

Runs the app in the development mode.\
Open [http://localhost:3000](http://localhost:3000) to view it in your browser.

The page will reload when you make changes.\
You may also see any lint errors in the console.

#### `npm test`

Launches the test runner in the interactive watch mode.\
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

#### `npm run build`

Builds the app for production to the `build` folder.\
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.\
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

#### `npm run eject`

**Note: this is a one-way operation. Once you `eject`, you can't go back!**

If you aren't satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you're on your own.

You don't have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn't feel obligated to use this feature. However we understand that this tool wouldn't be useful if you couldn't customize it when you are ready for it.

#### Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).

#### Code Splitting

This section has moved here: [https://facebook.github.io/create-react-app/docs/code-splitting](https://facebook.github.io/create-react-app/docs/code-splitting)

#### Analyzing the Bundle Size

This section has moved here: [https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size](https://facebook.github.io/create-react-app/docs/analyzing-the-bundle-size)

#### Making a Progressive Web App

This section has moved here: [https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app](https://facebook.github.io/create-react-app/docs/making-a-progressive-web-app)

#### Advanced Configuration

This section has moved here: [https://facebook.github.io/create-react-app/docs/advanced-configuration](https://facebook.github.io/create-react-app/docs/advanced-configuration)

#### Deployment

This section has moved here: [https://facebook.github.io/create-react-app/docs/deployment](https://facebook.github.io/create-react-app/docs/deployment)

#### `npm run build` fails to minify

This section has moved here: [https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify](https://facebook.github.io/create-react-app/docs/troubleshooting#npm-run-build-fails-to-minify)
It has been developed using node's version 16.12.0
