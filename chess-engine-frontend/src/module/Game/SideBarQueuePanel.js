import React from 'react'
import { connect } from 'react-redux'
import { actionNetworkedCreateLobby, actionNetworkedWantToJoin, actionNetworkedUpdateGameId,
         actionNetworkedSetGameIdCopied, actionNetworkedJoinGame,
         actionCancelStartGame,
         GAME_TYPE} from './ChessGameReducer';

import {CopyToClipboard} from 'react-copy-to-clipboard';

import moment from 'moment'



class QueueGamePanel extends React.Component{

  render(){
    const {gameType, queueStartDateTime, MMR, queueTimerDot} = this.props;
    let dot = "."
    for(let i = 0 ; i< queueTimerDot ; i ++){
      dot=dot+"."
    }
    return (
      <div>
        <div className="d-flex flex-row flex-fill justify-content-center">
          <h3 className="p-2"> {gameType === GAME_TYPE.RANKED? "Rank " : "Casual"} Game</h3>
        </div>

        {gameType === GAME_TYPE.RANKED && <div className="d-flex flex-row flex-fill justify-content-center">
          MMR: {MMR}
        </div>}

        <div className="d-flex flex-row flex-fill justify-content-center">
          <span className="p-2"> Finding Games{dot}</span>
        </div>

         <div className="d-flex flex-row flex-fill justify-content-center">
          <span className="p-2"> { moment.utc(moment().diff(moment(queueStartDateTime))).format("mm:ss") }</span>
        </div>

       


        
        
      </div>
    )
  }
}



const mapStateToProps = state =>{
  return {
    creating: state.game.lobby.creating,
    gameId: state.game.lobby.gameId,
    gameIdCopied: state.game.lobby.gameIdCopied,
    gameType: state.game.gameType,
    queueStartDateTime: state.game.queueStartDateTime,
    MMR: state.user.info.MMR,
    queueTimerDot: state.game.queueTimerDot
  }
}

const mapDispatchToProps = dispatch => {
  return {
   createLobby: ()=> dispatch(actionNetworkedCreateLobby()),
   wantToJoin: ()=> dispatch(actionNetworkedWantToJoin()),
   updateGameId: (gameId) => dispatch(actionNetworkedUpdateGameId(gameId)),
   setCopied: () => dispatch(actionNetworkedSetGameIdCopied(true)),
   join: (id)=>dispatch(actionNetworkedJoinGame(id)),
   cancelStartGame: ()=> dispatch(actionCancelStartGame())
  }
}


export default connect(
  mapStateToProps,
  mapDispatchToProps
)(QueueGamePanel)