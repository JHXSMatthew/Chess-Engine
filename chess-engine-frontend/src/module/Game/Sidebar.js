import React from 'react'

import { PiecesSVG } from '../../resource/PieceResource'
import './Sidebar.less';

import { GAME_TYPE, actionNewNetworkedGame } from './ChessGameReducer';

import UUID from  'uuid/v1';

import InvitedNetworkedGamePanel from './SidebarNetworkedGamePanel'

import { connect } from 'react-redux'

import {
  actionLoadInitState,
  actionSaveLocalGame,
  actionLoadLocalSavedGame,
  actionEndGame,
  actionNewLocalGame,
  actionUndoRequest,
} from './ChessGameReducer'
import { actionUpdateModalInfo } from '../../AppReducer';


class Sidebar extends React.Component{


  getMoveHistoryView = (data)=>{
    if(data.length <= 0){
      return []
    }
    const historyShow = []
    let last = undefined

    const f = (a,b)=>{
      return (
        <div key={UUID()} className='d-flex flex-row flex-fill'>
          <div className='pl-3'>
            {a.from}:{a.to}
          </div>
          <div className='pl-4'>
            {b? b.from+ ":" + b.to: ""}
          </div>
        </div>)
    }

    for(let i in data){
      if(last){
        historyShow.push(
          f(last, data[i])
        )
        last = undefined;
      }else{
        last = data[i]
      }
    }
    if(data.length % 2 == 1){
      historyShow.push(f(last, undefined))

    }

    return historyShow;
  }


  renderSideBarByType = (gameType, moveHistoryView)=>{
    const {undoMove, saveGame, endGame, currentTurn} = this.props;

    if(gameType === GAME_TYPE.INVITE_NETWOKRED){
      return <InvitedNetworkedGamePanel/>
    }else if(gameType === GAME_TYPE.LOCAL_GAME){
        return <div className="sidebar">
          {gameType != GAME_TYPE.LOCAL_GAME && <div className="d-flex flex-row flex-fill">
            <div className="p-2">Opponent: </div>
            <div className="p-2">{PiecesSVG['p']}</div>
          </div>}
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">Timer: </div>
            <div className="p-2">5:00/60:00</div>
          </div>
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">{PiecesSVG[ currentTurn=== 'w'? 'P' : 'p']}</div>
            <div className="p-2">'s Turn</div>
          </div>
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">Move History: </div>
          </div>
          <div className='history'>
            {moveHistoryView}
          </div>
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">
              <button className='btn btn-secondary ml-2'
                  onClick={()=> endGame(false, "Resign")}> Resign </button>
            </div>
            <div className="p-2">
              <button className='btn btn-primary' onClick={saveGame}> Save </button>
            </div>
            <div className="p-2">
              <button className='btn btn-danger' onClick={undoMove}> Undo </button>
            </div>
          </div>
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">Timer: </div>
            <div className="p-2">5:00/60:00</div>
          </div>
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">You: </div>
            <div className="p-2">{PiecesSVG['P']}</div>
          </div>
        </div>
    }
  
  }

  render(){
    const {loadGame, newLocalGame, gameType, moveHistory, newNetworkedGame} = this.props;

    
    const moveHistoryView = this.getMoveHistoryView(moveHistory);

    return (
      !gameType ? 
        (<div className="sidebar">
          <div className="d-flex flex-row flex-fill align-items-end">
              <button className='btn btn-primary' onClick={newLocalGame}> New Local Game </button>
              <button className='btn btn-primary' onClick={newNetworkedGame} > New Network Game </button>
          </div>
          <div className="d-flex flex-row flex-fill align-items-start">
              <button className='btn btn-primary' onClick={loadGame}> Load Local Game </button>
              {/* <button className='btn btn-primary' > Load Network Game </button> */}
          </div>
        </div>)
        : this.renderSideBarByType(gameType, moveHistoryView)
    )

  }
}




const mapStateToProps = state =>{
  return {
    gameType: state.game.gameType,
    gameStatus: state.game.gameStatus,
    currentTurn: state.game.currentTurn,
    moveHistory: state.game.moveHistory,
  }
}

const mapDispatchToProps = dispatch => {
  return {
    saveGame: () => dispatch(actionSaveLocalGame()),
    loadGame: () => {
      dispatch(actionNewLocalGame())
      dispatch(actionLoadLocalSavedGame())
    },
    newLocalGame: () => dispatch(actionNewLocalGame()),
    newNetworkedGame: ()=> dispatch(actionNewNetworkedGame()),
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
)(Sidebar)