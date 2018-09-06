import React from 'react'

import { connect } from 'react-redux'

import Board from '../../component/Board'

import {actionLoadInitState} from './ChessGameReducer'

class Game extends React.Component{



  render(){
    const { boardRep } = this.props

    return (
      <div style={{width: "600px", height: "600px"}}>
        <Board rep={boardRep}/>
    </div>
    )
  }
}


const mapStateToProps = state =>{
  return {
    boardRep: state.boardRep
  }
}

const mapDispatchToProps = dispatch => {
  return {
    loadInitState: () => dispatch(actionLoadInitState()),
    getHint: (loc)=> dispatch({ type: "I AM WORKING ON IT"})
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Game)