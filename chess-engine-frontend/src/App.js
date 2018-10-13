import React, { Component } from 'react';

import Game from './module/Game/ChessGame'
import About from './module/About/About';
import Login from './module/User/Login';
import ModalWrapper from './component/ModalWrapper';

import './App.less'

import { BrowserRouter, Switch, Route, Link } from 'react-router-dom'; 

import {connect} from 'react-redux';
import { actionToggleModal } from './AppReducer';




class Header extends Component{
  render(){

    const { auth } = this.props;

    return (
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <a className="navbar-brand" href="#">Chess Lty Ptd</a>
      <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span className="navbar-toggler-icon"></span>
      </button>
    
      <div className="collapse navbar-collapse" id="navbarSupportedContent">
        <ul className="navbar-nav mr-auto">
          {/* todo: active bind to the react-router */}
          <li className="nav-item"> 
            <Link className="nav-link" to="/">Game <span className="sr-only">(current)</span></Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/tute">Tutorial</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/about">About</Link>
          </li>
        </ul>
        {auth ? 
          <form className="form-inline my-2 my-lg-0">
            Welcome! <Link className="nav-link" to="/user">{auth.userName}</Link>
          </form>
        :<form className="form-inline my-2 my-lg-0">

          Welcome!  
          <Link className="nav-link" to="/login">Login</Link>
           or 
          <Link className="nav-link" to="/register">Register</Link>
        
        </form>
      }
      </div>
    </nav>
    )
  }
}

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div>
          <Header auth={this.props.auth}/>
          <ModalWrapper
            {...this.props.modal}
            buttonAction={this.props.buttonAction}
            hideSelf={this.props.hideModal}
          />

          <div className="container content-margin">
            
            <Route exact path="/" component={Game}/>
            <Route path="/about" component={About}/>
            <Route path="/login" component={Login}/>
          </div>
        </div>
          
      </BrowserRouter>


    );
  }
}



const mapStateToProps = state =>{
  return {
    modal: state.app.modal,
    auth: state.user.auth
  }
}

const mapDispatchToProps = dispatch => {
  return {
    hideModal: ()=> dispatch(actionToggleModal(false)),
    buttonAction: (action) => dispatch(action)
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App)
