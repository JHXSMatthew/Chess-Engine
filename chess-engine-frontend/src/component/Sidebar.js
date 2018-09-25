import React from 'react'

import { PiecesSVG } from '../resource/PieceResource'
import './Sidebar.less';


export default class Sidebar extends React.Component{

  render(){
    return (
      <div>
        <button className='btn btn-primary' onClick={this.props.saveGame}> Save </button>
        <button className='btn btn-secondary ml-2' onClick={this.props.loadGame}> Load </button>
        
        <button className='btn btn-secondary ml-2' data-toggle='modal' data-target='#endGameScreen' 
                onClick={this.props.endGame}> Resign </button>
      </div>
    );
  }
}
