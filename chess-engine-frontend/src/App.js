import React, { Component } from 'react';

import Board from './component/Board'

class App extends Component {
  render() {
    return (
      <div style={{width: "800px", height: "800px"}}>
        <Board></Board>

      </div>
    );
  }
}

export default App;
