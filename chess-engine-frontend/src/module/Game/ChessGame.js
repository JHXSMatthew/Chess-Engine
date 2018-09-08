import React from 'react'

import { connect } from 'react-redux'

import Board from '../../component/Board'

import {
  actionLoadInitState,
  actionSelectCell,
  actionClearSelect,
  actionMove} from './ChessGameReducer'

class Game extends React.Component{

  checkMove = (state,select)=>{
    if(select.length === 2){
      //select a piece and not selecting itself twice
      if(state[select[0]] && select[0] !== select[1]){
        this.props.move(select[0], select[1])

      }
    }
  }

  componentDidUpdate(){
    const {boardRep, select} = this.props;

    this.checkMove(boardRep, select);
  }


  render(){
    const { boardRep,onCellClick, select, boardHightLight } = this.props
    return (
      <div style={{width: "600px", height: "600px"}}>
        <Board 
        rep={boardRep}
        select={select}
        highlight={boardHightLight}
        onCellClick={onCellClick} />
    </div>
    )
  }
}


const mapStateToProps = state =>{
  return {
    boardRep: state.game.boardRep,
    select: state.game.select
  }
}

const mapDispatchToProps = dispatch => {
  return {
    loadInitState: () => dispatch(actionLoadInitState()),
    getHint: (loc)=> dispatch({ type: "I AM WORKING ON IT"}),
    move: (from, to)=> { 
      dispatch(actionClearSelect());
      dispatch(actionMove(from, to));
    },
    onCellClick: (index) => dispatch(actionSelectCell(index)),
    clearSelect: ()=> dispatch(actionClearSelect())
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Game)