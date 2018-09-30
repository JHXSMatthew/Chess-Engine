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
  actionEndGame,
  actionNewLocalGame,
  actionUndoRequest,
  actionUpdateGameStateSuccess,
  GAME_STATUS
} from './ChessGameReducer'
import { actionUpdateModalInfo } from '../../AppReducer';
import { SHOW_DEBUG_BUTTONS } from '../../config';
import { deserializeState } from './Utils';


class Game extends React.Component{

  checkMove = (state,select)=>{
    if(select.length === 2){
      //select a piece and not selecting itself twice
      if(state[select[0]] && select[0] !== select[1]){
        this.props.move(select[0], select[1])

      }
    }
  }

  currentTurnToDisplayName = ()=>{
    const {currentTurn} = this.props;
    return currentTurn === 'w' ? 'white' : 'black'

  }

  componentDidUpdate(){
    const {boardRep, select, isCheckmate, gameStatus} = this.props;

    this.checkMove(boardRep, select);

    if(isCheckmate && gameStatus === GAME_STATUS.INGAME){
      this.props.endGame(false, 'checkmate', this.currentTurnToDisplayName())
    }
  }


  render(){
    const { boardRep,onCellClick,availableMove, select, highlight, 
      lastMove, saveGame, loadGame, endGame, newLocalGame, gameType,
      currentTurn, undo, moveHistory } = this.props

    return (
      <div>
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

            
        {SHOW_DEBUG_BUTTONS &&
          <div>
            <div>Debug section, turn it off in config.js</div>
            <button className='btn btn-primary' onClick={()=> {this.props.loadGameState("4k2R/8/R2N2Q1/3P4/8/8/1PPPPPPP/1NB1KB2 w - - 0 1")}}>load check</button>
          </div>}
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
    gameStatus: state.game.gameStatus,
    currentTurn: state.game.currentTurn,
    moveHistory: state.game.moveHistory,
    isCheckmate: state.game.isCheckmate,
    isCheck: state.game.isCheck
  }
}

const mapDispatchToProps = dispatch => {
  return {
    loadInitState: () => dispatch(actionLoadInitState()),
    loadGameState: (stateStr) => { console.log(stateStr) 
      dispatch(actionUpdateGameStateSuccess({
          state: deserializeState(stateStr),
          isChecked: true,
          isCheckmate: false
         }))},
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
    endGame: (winLose, reason='Checkmate', who='You') => {
      dispatch(actionEndGame(winLose))
      dispatch(actionUpdateModalInfo({
        content: who + " " + (winLose?"win":'lose') +"!",
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