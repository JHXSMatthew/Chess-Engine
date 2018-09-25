import React from 'react'

import { PiecesSVG } from '../resource/PieceResource'
import './Sidebar.less';


export default class Sidebar extends React.Component{

  render(){
    const {saveGame, loadGame, endGame, newLocalGame, gameType} = this.props;

    if (gameType) {
    return (
      <div>
        <button className='btn btn-primary' onClick={saveGame}> Save </button>
        <button className='btn btn-secondary ml-2' onClick={loadGame}> Load </button>
        
        <button className='btn btn-secondary ml-2' data-toggle='modal' data-target='#endGameScreen' 
                onClick={endGame}> Resign </button>
      </div>
    );
    } else {
      return (
        <div className="sidebar">
          <div className="row align-items-end">
            <div className="col-5">
              <button className='btn btn-primary' onClick={newLocalGame}> New Local Game </button>
            </div>
            <div className="col-5">
              <button className='btn btn-primary' > New Network Game </button>
            </div>
          </div>
          <div className="row align-items-start">
            <div className="col-5">
              <button className='btn btn-primary' > Load Local Game </button>
            </div>
            <div className="col-5">
              <button className='btn btn-primary' > Load Network Game </button>
            </div>
          </div>
        </div>
      );
    }
  }
}
