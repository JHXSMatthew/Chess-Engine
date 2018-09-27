import React, { Component } from 'react';

import Game from './module/Game/ChessGame'

import './App.less'

import { Switch, Route, Link } from 'react-router-dom'; 
class Header extends Component{
  render(){
    return (
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <a className="navbar-brand" href="#">Chess Lty Ptd</a>
      <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span className="navbar-toggler-icon"></span>
      </button>
    
      <div className="collapse navbar-collapse" id="navbarSupportedContent">
        <ul className="navbar-nav mr-auto">
          <li className="nav-item active">
            <Link className="nav-link" to="/">Game <span className="sr-only">(current)</span></Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="tute">Tutorial</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="about">About</Link>
          </li>
        </ul>
        <form className="form-inline my-2 my-lg-0">
          
        </form>
      </div>
    </nav>
    )
  }
}

class App extends Component {
  render() {
    return (
      <div>
        <Header/>
        <div className="container content-margin">
          <Switch>
            <Route exact path="/" component={Game}/>
          </Switch>
        </div>
       
      </div>
    );
  }
}

export default App;
