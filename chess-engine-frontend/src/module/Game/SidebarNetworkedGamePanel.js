import React from 'react'
import { connect } from 'react-redux'
import { actionNetworkedCreateLobby, actionNetworkedWantToJoin, actionNetworkedUpdateGameId, actionNetworkedSetGameIdCopied} from './ChessGameReducer';

import {CopyToClipboard} from 'react-copy-to-clipboard';



class InvitedNetworkedGamePanel extends React.Component{

  render(){
    const {creating, updateGameId, gameIdCopied} = this.props;

    const displayFirstMenu = creating == undefined;

    

    return (
      <div>
        <div className="d-flex flex-row flex-fill justify-content-center">
          <h4 className="p-2">Networked Game</h4>
        </div>
        {displayFirstMenu ?
          (<div className="d-flex flex-row flex-fill">
            <button className='btn btn-primary' onClick={this.props.createLobby}> Create Lobby </button>
            <button className='btn btn-secondary' onClick={this.props.wantToJoin}> Join Lobby </button>
          </div>)
          : (
            <div>
              <div className="input-group mb-3 mt-3">
                <div className="input-group-prepend">
                  <span className="input-group-text">Code</span>
                </div>
                {creating ?
                  <div>
                    <textarea type="text" className="form-control" value={this.props.gameId} readOnly aria-describedby="append"/>
                  </div>
                :
                  <textarea type="text" className="form-control" value={this.props.gameId} onChange={(event)=> { updateGameId(event.target.value) }} />
                }
                {creating && 
                  <div id="append" className="input-group-append">
                    <CopyToClipboard text={this.props.gameId} onCopy={()=> this.props.setCopied()}>
                      <button disabled={gameIdCopied} className="btn btn-outline-secondary" type="button" id="button-addon2" onClick={()=>{}}>{gameIdCopied?"√":"Copy"}</button>
                    </CopyToClipboard>
                  </div>}
                
              </div>
              {creating ?(

                <div>
                  <div className="d-flex flex-row flex-fill">
                    Your friend can enter this code to join the game!
                  </div>
                </div>)
              :(
                <div>
                <button className='btn btn-success'> Join  </button>
                <div className="d-flex flex-row flex-fill">
                  Enter code to join the game! 
                </div>
              </div>
              )
            }
            </div>)
        }
      </div>
    )
  }
}



const mapStateToProps = state =>{
  return {
    creating: state.game.lobby.creating,
    gameId: state.game.lobby.gameId,
    gameIdCopied: state.game.lobby.gameIdCopied
  }
}

const mapDispatchToProps = dispatch => {
  return {
   createLobby: ()=> dispatch(actionNetworkedCreateLobby()),
   wantToJoin: ()=> dispatch(actionNetworkedWantToJoin()),
   updateGameId: (gameId) => dispatch(actionNetworkedUpdateGameId(gameId)),
   setCopied: () => dispatch(actionNetworkedSetGameIdCopied(true))
  }
}


export default connect(
  mapStateToProps,
  mapDispatchToProps
)(InvitedNetworkedGamePanel)