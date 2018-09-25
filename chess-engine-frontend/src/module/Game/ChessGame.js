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
  actionNewLocalGame
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
    const { boardRep,onCellClick,availableMove, select, highlight, 
      lastMove, saveGame, loadGame, endGame, newLocalGame, gameType,
      currentTurn } = this.props
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
          />
        </div>

        <div className="modal fade" id="endGameScreen" tabIndex="-1" role="dialog" aria-labelledby="endGameScreenTitle" 
             aria-hidden="true" data-backdrop="static" data-keyboard="false">
          <div className="modal-dialog modal-dialog-centered" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title" id="exampleModalLongTitle">Checkmate</h5>
              </div>
              <div className="modal-body">
                You lose.
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-primary" data-dismiss="modal" 
                        onClick={this.props.loadInitState}>New Game</button>
              </div>
            </div>
          </div>
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
    currentTurn: state.game.currentTurn
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
    loadGame: () => dispatch(actionLoadLocalSavedGame()),
    newLocalGame: () => dispatch(actionNewLocalGame()),
    endGame: (winLose) => dispatch(actionEndGame(winLose))
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Game)