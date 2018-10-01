import React from 'react'

import { PiecesSVG } from '../resource/PieceResource'
import './Board.less';


export default class Board extends React.Component{


  getCellStyle = (gameType, index, selected, highlight,lastMove)=>{
    const f = (Math.floor(index/8 % 2) === 0 ) ? (a,b)=> a===b : (a,b)=> a!==b;
    if (gameType) {
      if (index === lastMove[0] || index === lastMove[1]){
        return "cell-bg-lastMove";
      }
      
      if (selected.length > 0 && index === selected[0]){
        return "cell-bg-selected";
      }
      if (highlight.includes(index)) {
        return "cell-bg-highlight";
      }
  
    }
    return f (index % 2 ,0)  ? "cell-bg-light" : "cell-bg-dark";
  }

  render(){
    const {rep,select,highlight,onCellClick,availableMove,lastMove,gameType} = this.props;
    let boardRep = [];
    for(let i = 0 ; i < 64 ; i ++){
      boardRep.push(
      <div className={this.getCellStyle(gameType, i, select, highlight, lastMove)} key={i} 
        onClick={()=>{onCellClick(i); if(gameType && rep[i] && select.length === 0){availableMove(i)}}}>
          {PiecesSVG[rep[i]]}
      </div>)
    }
    return (
      <div className="board">
        {boardRep}
      </div>
    );
    
  }
}