import React from "react";

class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {
    console.warn("There was an error", error, errorInfo);
  }

  render() {
    if (this.state.hasError) {
      return (
        <div className="container pt-6 mt-6">
          <div className="columns is-centered">
            <div className="column is-half">
              <h1 className="title has-text-danger is-italic has-text-weight-medium mb-0">
                Something went wrong! Call the 911!
              </h1>
              <h2 className="has-text-danger is-italic has-text-weight-medium">
                Kidding, about the 911, the error is very real and very irrecuperable.
              </h2>
              <span><button onClick={() => window.location.reload(false)}>Retry</button></span>
            </div>
          </div>
        </div>
      );
    }

    return this.props.children;
  }
}

export default ErrorBoundary;
