import React from 'react'

import { PiecesSVG } from '../../resource/PieceResource'
import './Sidebar.less';

import { GAME_TYPE, actionNewNetworkedGame, GAME_STATUS, actionMove, actionJoinQueue, actionUpdateGameType } from './ChessGameReducer';

import UUID from  'uuid/v1';

import InvitedNetworkedGamePanel from './SidebarNetworkedGamePanel'
import SideBarQueuePanel from "./SideBarQueuePanel";

import { connect } from 'react-redux'

import { indexToCoord } from './Utils';

import {
  actionLoadInitState,
  actionSaveLocalGame,
  actionLoadLocalSavedGame,
  actionEndGame,
  actionNewLocalGame,
  actionUndoRequest,
  actionResignNetworkedGame,
} from './ChessGameReducer'
import { actionUpdateModalInfo } from '../../AppReducer';


class Sidebar extends React.Component{

  componentDidUpdate(){
    var el = this.refs.historyList;
    if (el){
      el.scrollTop = el.scrollHeight;
    }
  }

  getMoveHistoryView = (data)=>{
    if(data.length <= 0){
      return []
    }
    const historyShow = []
    let last = undefined

    const f = (a,b)=>{
      return (
        <div key={UUID()} className='d-flex flex-row flex-fill'>
          <div className="w-50 p-0 d-flex flex-row">
            <div className="w-30">
              {PiecesSVG[a.piece]}
            </div>
            <div className="w-50 py-4">
              {indexToCoord(a.from)}:{indexToCoord(a.to)}
            </div>
          </div>
          {b?
            <div className="w-50 p-0 d-flex flex-row">
              <div className="w-30">
                {PiecesSVG[b.piece]}
              </div>
              <div className="w-50 py-4">
                {indexToCoord(b.from)}:{indexToCoord(b.to)}
              </div>
            </div>
          : ""
          }
        </div>)
          // <div className='pl-5'>
          //   {b? PiecesSVG[b.piece]+";"+ indexToCoord(b.from)+ ":" + indexToCoord(b.to): ""}
          // </div>
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

  resign = (gameType)=>{
    const {endLocalGame,resignNetworkedGame} = this.props
    if(gameType === GAME_TYPE.LOCAL_GAME || gameType === GAME_TYPE.AI){
      endLocalGame(false, "Resign")
    }else{
      resignNetworkedGame();
    }
  }

  renderSideBarByType = (gameType, moveHistoryView)=>{
    const {undoMove, saveGame, endGame, currentTurn, opponentColor, gameStatus} = this.props;

    if(gameType === GAME_TYPE.INVITE_NETWOKRED && gameStatus === GAME_STATUS.INIT){
      return <InvitedNetworkedGamePanel/>
    }else if( (gameType === GAME_TYPE.RANKED || gameType === GAME_TYPE.MATCH) && gameStatus === GAME_STATUS.INIT){
      return <SideBarQueuePanel />
    }else{
        return <div className="sidebar">
          {gameType != GAME_TYPE.LOCAL_GAME && <div className="d-flex flex-row flex-fill">
            <div className="p-2">Opponent: </div>
            <div className="p-2">{PiecesSVG[opponentColor=== 'w'? 'P' : 'p']}</div>
          </div>}
          
          {/* <div className="d-flex flex-row flex-fill">
            <div className="p-2">Timer: </div>
            <div className="p-2">5:00/60:00</div>
          </div> */}
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">{PiecesSVG[currentTurn=== 'w'? 'P' : 'p']}</div>
            <div className="p-2">'s Turn</div>
          </div>
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">Move History: </div>
          </div>
          {/*<div className="d-flex flex-row flex-fill">
                      <div className="pl-3">White </div>
                      <div className="pl-5">Black</div>
                    </div>*/}
          <div className='history' ref='historyList'>
            {moveHistoryView}
          </div>
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">
              <button className='btn btn-secondary ml-2'
                  onClick={()=> this.resign(gameType)}> Resign </button>
            </div>
            {gameType === GAME_TYPE.LOCAL_GAME && 
              <div className="p-2">
                <button className='btn btn-primary' onClick={saveGame}> Save </button>
              </div>
            }
            {gameType === GAME_TYPE.LOCAL_GAME && 
              <div className="p-2">
                <button className='btn btn-danger' onClick={undoMove}> Undo </button>
              </div>
            }
          </div>
          {/* <div className="d-flex flex-row flex-fill">
            <div className="p-2">Timer: </div>
            <div className="p-2">5:00/60:00</div>
          </div> */}
          {gameType != GAME_TYPE.LOCAL_GAME && <div className="d-flex flex-row flex-fill">
            <div className="p-2">You: </div>
            <div className="p-2">{PiecesSVG[opponentColor=== 'w'? 'p' : 'P']}</div>
          </div>}
        </div>
    }
  
  }

  render(){
    const {loadGame, newLocalGame, gameType, moveHistory, newNetworkedGame, newMatchGame, newRankGame, newAiGame} = this.props;

    
    const moveHistoryView = this.getMoveHistoryView(moveHistory);

    return (
      !gameType ? 
        (<div className="sidebar">
          <h5>Local Games</h5>
          <div className="d-flex flex-row flex-fill align-items-end">
              <button className='btn btn-primary' onClick={newLocalGame}> Play Local Game </button>
              <button className='btn btn-secondary' onClick={loadGame}> Load Local Game </button>
          </div>
          <h5>Networked Games</h5>
          <div className="d-flex flex-row flex-fill align-items-end">
              <button className='btn btn-primary' onClick={newMatchGame}> Play Casual Game </button>
              <button className='btn btn-success' onClick={newRankGame} > Play Rank Game </button>
          </div>
          <div className="d-flex flex-row flex-fill align-items-start">
            <button className='btn btn-primary' onClick={newNetworkedGame} >Game Room</button>
          </div>
          <h5>AI Games</h5>
          <div className="d-flex flex-row flex-fill align-items-start">
            <button className='btn btn-primary' onClick={newAiGame}> Play AI Game </button>
          </div>
          <h5 className='pt-4'>Help</h5>
          <ul>
            <li>
              Local Game: Play against your friend on the same computer.
            </li>
            <li>
              Casual Game: Find a player online to play against. 
            </li>
            <li>
              Rank Game: Find a player online to play against and earn MMR. 
            </li>
            <li>
              Game Room: Create a game room and invite your friend to play with you online! 
            </li>

          </ul>
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
    opponentColor: state.game.lobby.playerType === 'w' ? 'b': 'w'
  }
}

const mapDispatchToProps = dispatch => {
  return {
    saveGame: () => {
      dispatch(actionSaveLocalGame())
      dispatch(actionLoadInitState())
    },
    loadGame: () => {
      dispatch(actionNewLocalGame())
      dispatch(actionLoadLocalSavedGame())
    },
    newLocalGame: () => dispatch(actionNewLocalGame()),
    newNetworkedGame: ()=> dispatch(actionNewNetworkedGame()),
    newMatchGame: () => dispatch(actionJoinQueue(GAME_TYPE.MATCH)),
    newRankGame: () => dispatch(actionJoinQueue(GAME_TYPE.RANKED)),
    newAiGame: () => { dispatch(actionNewLocalGame()); dispatch(actionUpdateGameType(GAME_TYPE.AI)) }, 
    undoMove: () => dispatch(actionUndoRequest()),
    resignNetworkedGame: ()=> dispatch(actionResignNetworkedGame())
  }
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Sidebar)