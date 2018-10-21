import React from 'react'
import { ListGroup, ListGroupItem, Col, Row, Card,CardBody,Badge, Button, Form, FormGroup, Label, Input, FormText  } from 'reactstrap';

import { connect } from 'react-redux'

import UserForm from './UserRegisterForm'
import { actionUserLoginFail, actionUserLogoff, actionLoadUserGameHistory } from './UserReducer';

import { Redirect } from 'react-router'
import BootstrapTable from 'react-bootstrap-table-next';

import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import { gameHistory } from '../Game/ChessGameEAPI';





class UserCenter extends React.Component{


  constructor(props){
    super(props)

    this.state = {
      select: 0
    }
  }

  updateSelect = (val) =>{
    this.setState({
      select: val
    })
  }

  getContent = (select, props) => {
    switch(select){
      case 0:
        return <Profile {...props.info} />
      case 1:
        props.loadGameHistory();
        return <GameHistory loadGameHistoryMove={props.loadGameHistoryMove} {...props.info}/>
      default:
        return <Profile {...props.info} />
    }
  }

  render(){

    const { logoff } = this.props
    const { select } = this.state;
    const content = this.getContent(select, this.props);

    return (
      <div className='container'>
        {!this.props.auth && <Redirect to="/"/>}
        <Row>
            <h1>User Center</h1>
        </Row>
        <Row className='mt-3'>
          <Col xs="3">
            <UserCenterSideBar onSelect={this.updateSelect} logoff={logoff} select={select}/>
          </Col>
          <Col xs="9">
            <Card>
              <CardBody>
                {content}
              </CardBody>
            </Card>
          </Col>
        </Row>
      </div>
    )

  }
}


const products = [ ];
const columns = [{
  dataField: '',
  text: 'GameId'
}, {
  dataField: 'name',
  text: 'Play As'
},{
  dataField: 'b',
  text: 'Win/Lost'
}, {
  dataField: 'date',
  text: 'Date'
}];

class GameHistory extends React.Component{
  


  render(){
    return (
      <BootstrapTable keyField='id' data={ this.props.gameHistory } columns={ columns } />
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
    const {logoff, onSelect, select} = this.props;

    return (
      <div>
        <ListGroup>
          <ListGroupItem active={select === 0} tag="button" action onClick={ () => onSelect(0) }>Profile</ListGroupItem>
          <ListGroupItem active={select === 1} tag="button" action onClick={ () => onSelect(1) }>Game History</ListGroupItem>
          <ListGroupItem active={select === 2} tag="button" action disabled>Friend</ListGroupItem>
          <ListGroupItem active={select === 3} tag="button" action onClick={logoff}>Log off</ListGroupItem>
      </ListGroup>
     </div>
    )
  }


}


const mapStateToProps = (state) => {
  return {
    ...state.user,
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
      clearError: ()=> dispatch(actionUserLoginFail(undefined)),
      logoff: () => {dispatch(actionUserLogoff()) ; window.location.reload(); },
      loadGameHistory: ()=> {dispatch(actionLoadUserGameHistory())}
  };
};


export default connect(mapStateToProps, mapDispatchToProps)(UserCenter)
