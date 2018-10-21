import React from 'react'

import { Button, Form, FormGroup, Label, Input, FormText } from 'reactstrap';

import './Login.css';


class UserForm extends React.Component{

  constructor(props){
    super(props)
   
    this.state = {
      userName: undefined,
      password: undefined,
      email: undefined,
      agreed: false
    }
  }

  onUpdate = (event)=>{
    const id = event.target.id
    const value = event.target.value
    this.setState({
      [id]: value
    })
  }
  
  render(){
    const { onRegister } = this.props;
    return (
      <Form className="form-signin" onSubmit={(e)=>{ 
          e.preventDefault() ; 
          if(this.state.agreed){
            if(this.state.userName && this.state.password && this.state.email){
              onRegister(this.state) 
            }else{
              alert('Please fill all fields')
            }
          }else{
            alert('Please agree to the terms and conditions!')
          }
        }}>
        <FormGroup>
            <Label for="exampleEmail">Username</Label>
            <Input type="text" name="userName" id="userName" placeholder="Username" value={this.state.userName} onChange={this.onUpdate}/>
          </FormGroup>
          <FormGroup>
            <Label for="examplePassword">Password</Label>
            <Input type="password" name="password" id="password" placeholder="Password" value={this.state.password} onChange={this.onUpdate}/>
          </FormGroup>
          <FormGroup>
            <Label for="exampleEmail">Email</Label>
            <Input type="email" name="email" id="email" placeholder="Email" value={this.state.email} onChange={this.onUpdate}/>
          </FormGroup>
          <FormGroup check>
          <Label check>
            <Input type="checkbox" id='agreed' checked={this.state.agreed} onChange={({target}) => {
              this.setState({
                agreed: target.checked
              })
            }}/>{' '}
              <a href="/"> I agree to the terms and conditions </a>
          </Label>
        </FormGroup>
          <Button className="btn btn-info mx-0 my-1">Register</Button>
      </Form>
    )
  }
}

export default UserForm;