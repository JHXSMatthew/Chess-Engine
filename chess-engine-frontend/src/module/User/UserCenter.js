import React from 'react'
import { ListGroup, ListGroupItem, Col, Row, Card,CardBody,Badge, Button, Form, FormGroup, Label, Input, FormText  } from 'reactstrap';

import { connect } from 'react-redux'

import UserForm from './UserRegisterForm'
import { actionUserLoginFail, actionUserLogoff } from './UserReducer';

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
      logoff: () => dispatch(actionUserLogoff())
  };
};


export default connect(mapStateToProps, mapDispatchToProps)(UserCenter)
