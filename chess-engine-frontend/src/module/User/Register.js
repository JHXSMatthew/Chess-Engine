import UserRegisterForm from './UserRegisterForm'
import React from 'react'
import { actionUserLoginFail, actionUserRegister } from './UserReducer';
 
import { Redirect } from 'react-router'
import { connect } from 'react-redux'


const Register = (props)=>{
  if(props.auth){
    return <Redirect to="/" />
  }

  
  if(props.err){
    props.clearError()
    const { response } = props.err
    if(response.status === 500){
      alert('User name exsits. Please use another one.')
    }else{
      alert('internal error. please try again later')
    }
  }

  return <UserRegisterForm onRegister={props.register}/>
}



const mapStateToProps = (state) => {
  return {
    ...state.user
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
      clearError: ()=> dispatch(actionUserLoginFail(undefined)),
      register: (model)=> dispatch(actionUserRegister(model))
  };
};


export default connect(mapStateToProps, mapDispatchToProps)(Register)
