import React from 'react'
import { ListGroup, ListGroupItem, Col, Row, Card,CardBody,Badge, Button, Form, FormGroup, Label, Input, FormText  } from 'reactstrap';

import { connect } from 'react-redux'

import UserForm from './UserRegisterForm'
import { actionUserLoginFail, actionUserLogoff, actionChangePassword } from './UserReducer';

import { Redirect } from 'react-router'




class UserCenter extends React.Component{

  render(){

    const { logoff } = this.props

    return (
      <div className='container'>
        {!this.props.auth && <Redirect to="/"/>}
        <Row>
            <h1>User Center</h1>
        </Row>
        <Row className='mt-3'>
          <Col xs="3">
            <UserCenterSideBar logoff={logoff}/>
          </Col>
          <Col xs="9">
            <Card>
              <CardBody>
                <Profile
                {...this.props.info}
                onPasswordChange={(b,c)=>this.props.changePassword(this.props.info.userName,b,c)}
                />
              </CardBody>
            </Card>
          </Col>
        </Row>
      </div>
    )

  }
}

class Profile extends React.Component{


  constructor(props){
    super(props)
    this.state = {
      old: "",
      new: "",
      confirm: "",
    }
  }

  onChange = (e) =>{
    const {target} = e;
    const {id, value} = target;
    this.setState({
      [id]: value
    })
  }

  render(){
    //todo: change password
    const { userName, onPasswordChange, email, MMR , matchPlayed, matchWin, rankGamePlayed, rankGameWin } = this.props

    return (
      <div>
        <h1>
          {userName} 
        </h1>
        <h4 style={{color: "grey", paddingLeft: "10px"}}>{email}</h4>

        <h3 className='mt-5'>Ranked Game</h3>
        <Form>
          <FormGroup row>
            <Label sm={3}>MMR</Label>
            <Col sm={2}>
              <Input value={MMR} readOnly />
            </Col>
          </FormGroup>

          <FormGroup row>
            <Label sm={3}>Game Played</Label>
            <Col sm={2}>
              <Input value={rankGamePlayed} readOnly />
            </Col>

            <Label sm={3}>Win</Label>
            <Col sm={2}>
              <Input value={rankGameWin} readOnly />
            </Col>
          </FormGroup>

          <h3 className='mt-5'>Casual Game</h3>
          <FormGroup row>
            <Label sm={3}>Match Game</Label>
            <Col sm={2}>
              <Input value={matchPlayed} readOnly />
            </Col>

            <Label sm={3}>Win</Label>
            <Col sm={2}>
              <Input value={matchWin} readOnly />
            </Col>
          </FormGroup>
          
        </Form> 
        <h3 className='mt-5'>Change Password</h3>

        <Form onSubmit={ (e)=>{
            e.preventDefault();
            if(this.state.new === this.state.confirm){
              onPasswordChange(this.state.old, this.state.new)
               
            }else{
              alert("please confirm your password.")
            }
           
            
        } }>
          <FormGroup row>
              <Label sm={6}>old password</Label>
              <Col sm={6}>
                <Input id="old"  type="password" value={this.state.old} onChange={this.onChange}/>
              </Col>
          </FormGroup>
          <FormGroup row>
              <Label sm={6}>new password</Label>
              <Col sm={6}>
                <Input id="new" type="password"  value={this.state.new} onChange={this.onChange}/>
              </Col>
          </FormGroup>
          <FormGroup row>
              <Label sm={6}>confirm password</Label>
              <Col sm={6}>
                <Input id="confirm" type="password" value={this.state.confirm} onChange={this.onChange}/>
              </Col>
          </FormGroup>
          <Button>Confirm</Button>
        </Form>  
      </div>
    )
  }
}

class UserCenterSideBar extends React.Component{

  render(){
    const {logoff} = this.props;

    return (
      <div>
        <ListGroup>
          <ListGroupItem active tag="button" action>Profile</ListGroupItem>
          <ListGroupItem tag="button" action disabled>Game History</ListGroupItem>
          <ListGroupItem tag="button" action disabled>Friend</ListGroupItem>
          <ListGroupItem tag="button" action onClick={logoff}>Log off</ListGroupItem>
      </ListGroup>
     </div>
    )
  }


}


const mapStateToProps = (state) => {
  return {
    ...state.user
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
      clearError: ()=> dispatch(actionUserLoginFail(undefined)),
      logoff: () => {dispatch(actionUserLogoff()) ; window.location.reload(); },
      changePassword: (id, old, newp)=> dispatch(actionChangePassword(id, old, newp))
  };
};


export default connect(mapStateToProps, mapDispatchToProps)(UserCenter)
