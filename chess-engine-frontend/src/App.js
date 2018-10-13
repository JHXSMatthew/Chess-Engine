import React, { Component } from 'react';

import Game from './module/Game/ChessGame'
import About from './module/About/About';
import Login from './module/User/Login';

import UserCenter from './module/User/UserCenter'
import Register from './module/User/Register'

import ModalWrapper from './component/ModalWrapper';

import './App.less'

import { BrowserRouter, Switch, Route, Link } from 'react-router-dom'; 

import {connect} from 'react-redux';
import { actionToggleModal } from './AppReducer';
import { actionOnLoadCacheLogin } from './module/User/UserReducer';

import {
  Collapse,
  Navbar,
  NavbarToggler,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem } from 'reactstrap';



class Header extends Component{

  constructor(props){
    super(props)

    this.state = {
      isOpen: false
    }
  }

  toggle = () => {
    this.setState({
      isOpen: !this.state.isOpen
    });
  }
  
  render(){

    const { auth, info } = this.props;
    let userName = "";
    if(info){
      userName = info.userName
    }
    return (
      <div>
        <Navbar color="light" light expand="md">
          <NavbarBrand href="/">Chess Lty Ptd</NavbarBrand>
          <NavbarToggler onClick={this.toggle} />
          <Collapse isOpen={this.state.isOpen} navbar>
            <Nav className="mr-auto" navbar>
              <NavItem>
                <Link className="nav-link" to="/">Game <span className="sr-only">(current)</span></Link>              </NavItem>
              <NavItem>
                <Link className="nav-link" to="/tute">Tutorial</Link>
              </NavItem>
              <NavItem>
                <Link className="nav-link" to="/about">About</Link>
              </NavItem>
            </Nav>
            {auth ? 
          <div className="form-inline my-2 my-lg-0">
            Welcome! <Link className="nav-link" to="/user">{userName}</Link>
          </div>
            :<div className="form-inline my-2 my-lg-0">

              Welcome!  
              <Link className="nav-link" to="/login">Login</Link>
              or 
              <Link className="nav-link" to="/register">Register</Link>
            
            </div>
          }
          </Collapse>
        </Navbar>
      </div>
    )
  }
}

class App extends Component {
  constructor(props){
    super(props)
    props.tryCacheLogin();
  }

  render() {

    return (
      <BrowserRouter>
        <div>
          <Header auth={this.props.auth} info={this.props.userInfo}/>
          <ModalWrapper
            {...this.props.modal}
            buttonAction={this.props.buttonAction}
            hideSelf={this.props.hideModal}
          />

          <div className="container content-margin">
            
            <Route exact path="/" component={Game}/>
            <Route path="/register" component={Register}/>
            <Route path="/user" component={UserCenter}/>
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
    auth: state.user.auth,
    userInfo: state.user.info
  }
}

const mapDispatchToProps = dispatch => {
  return {
    hideModal: ()=> dispatch(actionToggleModal(false)),
    buttonAction: (action) => dispatch(action),
    tryCacheLogin: ()=> dispatch(actionOnLoadCacheLogin())
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(App)
