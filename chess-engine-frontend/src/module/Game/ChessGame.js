import React from 'react'

import { connect } from 'react-redux'

import Board from '../../component/Board'

import Sidebar from './Sidebar'

import Style from "./ChessGame.less"

import {
  actionLoadInitState,
  actionSelectCell,
  actionClearSelect,
  actionMove,
  actionAvailableMove,
  actionSaveLocalGame,
  actionLoadLocalSavedGame,
  actionUpdateStateSuccess,
  actionEndGame,
  actionNewLocalGame,
  actionUndoRequest
} from './ChessGameReducer'
import { actionUpdateModalInfo } from '../../AppReducer';


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
    const { boardRep,onCellClick,availableMove, select, highlight, 
      lastMove, saveGame, loadGame, endGame, newLocalGame, gameType,
      currentTurn, undo, moveHistory } = this.props

    return (
      <div className='game'>
        <div className='game-left'>
          <Board 
          rep={boardRep}
          select={select}
          highlight={highlight}
          onCellClick={onCellClick}
          availableMove={availableMove}
          lastMove={lastMove} 
          gameType={gameType}
          />
        </div>
        <div className="game-right">
          <Sidebar  gameType={gameType}
                    loadGame={loadGame}
                    saveGame={saveGame}
                    endGame={endGame}
                    newLocalGame={newLocalGame}
                    currentTurn={currentTurn}
                    undoMove={undo}
                    moveHistory={moveHistory}
          />
        </div>

      </div>
    )
  }
}


const mapStateToProps = state =>{
  return {
    boardRep: state.game.boardRep,
    select: state.game.select,
    highlight: state.game.boardHightLight,
    lastMove: state.game.lastMovePair,
    gameType: state.game.gameType,
    currentTurn: state.game.currentTurn,
    moveHistory: state.game.moveHistory
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
    clearSelect: ()=> dispatch(actionClearSelect()),
    saveGame: () => dispatch(actionSaveLocalGame()),
    loadGame: () => {
      dispatch(actionNewLocalGame())
      dispatch(actionLoadLocalSavedGame())
    },
    newLocalGame: () => dispatch(actionNewLocalGame()),
    endGame: (winLose, reason='Checkmate') => {
      dispatch(actionEndGame(winLose))
      dispatch(actionUpdateModalInfo({
        content: "You " + (winLose?"win":'lose') +"!",
        show: true,
        title: reason,
        action: ()=> dispatch(actionLoadInitState())
      }))
    },
    undo: () => dispatch(actionUndoRequest())
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Game)