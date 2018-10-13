import React from 'react'

import { Button, Form, FormGroup, Label, Input, FormText } from 'reactstrap';




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
      <Form onSubmit={(e)=>{ 
          e.preventDefault() ; 
          if(this.state.agreed){
            if(this.state.userName && this.state.password && this.state.email){
              onRegister(this.state) 
            }else{
              alert('Please fill all fields')
            }
          }else{
            alert('Please agree the term and conditions!')
          }
        }}>
        <FormGroup>
            <Label for="exampleEmail">User Name</Label>
            <Input type="text" name="userName" id="userName" placeholder="Your User Name" value={this.state.userName} onChange={this.onUpdate}/>
          </FormGroup>
          <FormGroup>
            <Label for="examplePassword">Password</Label>
            <Input type="password" name="password" id="password" placeholder="password" value={this.state.password} onChange={this.onUpdate}/>
          </FormGroup>
          <FormGroup>
            <Label for="exampleEmail">Email</Label>
            <Input type="email" name="email" id="email" placeholder="email" value={this.state.email} onChange={this.onUpdate}/>
          </FormGroup>
          <FormGroup check>
          <Label check>
            <Input type="checkbox" id='agreed' checked={this.state.agreed} onChange={({target}) => {
              this.setState({
                agreed: target.checked
              })
            }}/>{' '}
              <a href="/"> I agree term of conditions </a>
          </Label>
        </FormGroup>
          <Button>Register</Button>
      </Form>
    )
  }
}

export default UserForm;