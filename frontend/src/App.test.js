import React from 'react';
import App from './App';

import { render, screen } from '@testing-library/react';

describe('App Component tests', () => {

  it('renders without crashing', () => {
    render(<App />);
  });

  it('contains add form & tasksList', () => {
    render(<App />);

    const form = screen.queryByTestId("addTaskFormWrapper");
    expect(form).not.toBeNull();

    const list = screen.queryByTestId("taskList");
    expect(list).not.toBeNull();
  });
});
