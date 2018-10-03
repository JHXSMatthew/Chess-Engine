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
  actionDestoryNetworkedGameTimer,
  GAME_STATUS,
  GAME_TYPE,
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
    const {boardRep, select, isCheckmate, isChecked, gameStatus, gameType} = this.props;

    this.checkMove(boardRep, select);

    if(isCheckmate && gameStatus === GAME_STATUS.INGAME && gameType === GAME_TYPE.LOCAL_GAME){
      this.props.endLocalGame(false, 'checkmate', this.currentTurnToDisplayName())
    }
  }


  render(){
    const { boardRep,onCellClick,availableMove, select, highlight, 
      lastMove, endLocalGame, gameType } = this.props

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
                <Sidebar 
                 endLocalGame={endLocalGame} />
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
    isChecked: state.game.isChecked
  }
}

const mapDispatchToProps = dispatch => {
  return {
    loadGameState: (stateStr) => { console.log(stateStr) 
      dispatch(actionUpdateGameStateSuccess({
          state: deserializeState(stateStr),
          isChecked: true,
          isCheckmate: false
         }))},
    move: (from, to)=> { 
      dispatch(actionMove(from, to));
    },
    onCellClick: (index) => dispatch(actionSelectCell(index)),
    availableMove: (from) => dispatch(actionAvailableMove(from)),
    //end local game
    endLocalGame: (winLose, reason='Checkmate', who='You') => {
      dispatch(actionEndGame(winLose))
      dispatch(actionUpdateModalInfo({
        content: who + " " + (winLose?"win":'lose') +"!",
        show: true,
        title: reason,
        action: actionLoadInitState()
      }))
    },
    
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Game)