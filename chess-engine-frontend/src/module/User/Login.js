import React from 'react'

import { connect } from 'react-redux';
import { 
    actionUserLogin,
    actionUserLoginFail
} from './UserReducer';



import { Link, Redirect } from 'react-router-dom';


import './Login.css';
import { actionRedirectLogin } from '../../AppReducer';


class SignInView extends React.Component{
  
  constructor(props){
    super(props);
    this.state = {
      auth:{
        
      }
    };

    this.handleMemberIdChange = this.handleMemberIdChange.bind(this);
    this.hanldePasswordChange = this.hanldePasswordChange.bind(this);
    this.handleKeepLogin = this.handleKeepLogin.bind(this);
  }

  componentDidMount(){
    if(this.props.redirect){
      this.props.clearRedirect();
    }
  }

  handleMemberIdChange(e){
    const { target } = e
    if(target){
      const toUpdate = Object.assign(this.state.auth, {userName: target.value})
      this.setState({auth: toUpdate})
    }
  }

  hanldePasswordChange(e){
    const { target } = e
    if(target){
      const toUpdate = Object.assign(this.state.auth, {password: target.value})
      this.setState({auth: toUpdate})
    }
  }

  handleKeepLogin(e){
    const { target } = e
    if(target){
      const toUpdate = Object.assign(this.state.auth, {keepLogin: target.checked})
      this.setState({auth: toUpdate})
    }
  }

  componentDidUpdate(){
    if(this.props.err){
      const { response } = this.props.err
      if(response){
        this.props.clearError()
        if(response.status === 400){
          alert('incorrect password or username..')
        }else{
          alert('internal error. please try again later')
        }
      }
      
    }
  }



  render(){
    return (
      this.props.auth ? 
      (<Redirect to="/" />)
      :
      (<div className='text-center pt-5'>
        <form className='form-signin' onSubmit={(e)=> {e.preventDefault() ; this.props.onLogin(this.state.auth)}}>
          <h1 className="h3 mb-3 font-weight-normal"> Chess Engine Lty Ptd</h1>
            <label htmlFor="inputMemberId" className="sr-only">UserName</label>
            <input type="text" id="inputMemberId" className="form-control" placeholder="userName" onChange={this.handleMemberIdChange} required autoFocus/>
            <label htmlFor="inputPassword" className="sr-only">Password</label>
            <input type="password" id="inputPassword" className="form-control" placeholder="password" onChange={this.hanldePasswordChange} required/>
            <div className="checkbox mb-3">
            <label>
              <input type="checkbox" value="remember-me" onChange={this.handleKeepLogin} checked={this.state.auth.keepLogin}/> Keep Me Login
            </label>
            </div>
            <button className="btn btn-lg btn-primary btn-block">Login</button> 
            or 
            <Link className="nav-link" to="/register">Register</Link>

           <p className="mt-5 mb-3 text-muted">&copy; 2018-2020</p>
        </form>
      </div>)
    )
  }
}

const mapStateToProps = (state) => {
  return {
    ...state.user,
    redirect: state.app.redirect
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
      onLogin: (account) => dispatch(actionUserLogin(account)),
      clearError: ()=> dispatch(actionUserLoginFail(undefined)),
      clearRedirect: ()=> dispatch(actionRedirectLogin(false))
  };
};


export default connect(mapStateToProps, mapDispatchToProps)(SignInView)
