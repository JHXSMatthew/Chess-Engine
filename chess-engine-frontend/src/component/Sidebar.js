import React from 'react'

import { PiecesSVG } from '../resource/PieceResource'
import './Sidebar.less';


export default class Sidebar extends React.Component{

  render(){
    const {saveGame, loadGame, endGame, newLocalGame, gameType} = this.props;
    if (gameType) {
      return (
        <div className="sidebar">
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">Opponent: </div>
            <div className="p-2">{PiecesSVG['p']}</div>
          </div>
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">Timer: </div>
            <div className="p-2">5:00/60:00</div>
          </div>
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">{PiecesSVG['P']}</div>
            <div className="p-2">'s Turn</div>
          </div>
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">Move History: </div>
          </div>
          <div className="d-flex flex-row flex-fill">
            <div className="p-2">
              <button className='btn btn-secondary ml-2' data-toggle='modal' data-target='#endGameScreen' 
                  onClick={endGame}> Resign </button>
            </div>
            <div className="p-2">
              <button className='btn btn-primary' onClick={saveGame}> Save </button>
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
      );
    } else {
      return (
        <div className="sidebar">
          <div className="d-flex flex-row flex-fill align-items-end">
              <button className='btn btn-primary' onClick={newLocalGame}> New Local Game </button>
              <button className='btn btn-primary' > New Network Game </button>
          </div>
          <div className="d-flex flex-row flex-fill align-items-start">
              <button className='btn btn-primary' > Load Local Game </button>
              <button className='btn btn-primary' > Load Network Game </button>
          </div>
        </div>
      );
    }
  }
}
