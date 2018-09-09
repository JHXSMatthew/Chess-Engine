import React from 'react'

import { connect } from 'react-redux'

import Board from '../../component/Board'

import Style from "./Game.less"

import {
  actionLoadInitState,
  actionSelectCell,
  actionClearSelect,
  actionMove,
  actionAvailableMove
} from './ChessGameReducer'


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
    const { boardRep,onCellClick,availableMove, select, highlight } = this.props
    return (
      <div className='game'>
        <div className='game-left'>
          <Board 
          rep={boardRep}
          select={select}
          highlight={highlight}
          onCellClick={onCellClick}
          availableMove={availableMove} />
        </div>
        <div className="game-right">
            
        </div>
      </div>
      
    )
  }
}


const mapStateToProps = state =>{
  return {
    boardRep: state.game.boardRep,
    select: state.game.select,
    highlight: state.game.boardHightLight
  }
}

const mapDispatchToProps = dispatch => {
  return {
    loadInitState: () => dispatch(actionLoadInitState()),
    move: (from, to)=> { 
      dispatch(actionClearSelect());
      dispatch(actionMove(from, to));
    },
    onCellClick: (index) => dispatch(actionSelectCell(index)),
    availableMove: (from) => dispatch(actionAvailableMove(from)),
    clearSelect: ()=> dispatch(actionClearSelect())
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Game)