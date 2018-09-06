import React, { Component } from 'react';

import Board from './component/Board'

class App extends Component {
  render() {
    return (
      <div style={{width: "600px", height: "600px"}}>
        <Board></Board>

      </div>
    );
  }
}

export default App;
