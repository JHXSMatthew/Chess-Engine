import React from 'react'
import { ListGroup, ListGroupItem, Col, Row, Card,CardBody } from 'reactstrap';

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
                <UserForm></UserForm>
              </CardBody>
            </Card>
          </Col>
        </Row>
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
          <ListGroupItem tag="button" action>Game History</ListGroupItem>
          <ListGroupItem tag="button" action>Friend</ListGroupItem>
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
