import React from 'react'

import { PiecesSVG } from '../resource/PieceResource'
import './Board.less';


export default class Board extends React.Component{


  getCellStyle = (index, selected, highlight)=>{
    const f = (Math.floor(index/8 % 2) === 0 ) ? (a,b)=> a===b : (a,b)=> a!==b;
    if (selected.length > 0 && index === selected[0]){
      return "cell-bg-selected";
    }
    if (highlight[index]) {
      return "cell-bg-highlight";
    }
    return f (index % 2 ,0)  ? "cell-bg-dark" : "cell-bg-light";
  }

  render(){
    const {rep,select,highlight,onCellClick,availableMove} = this.props;
    let boardRep = [];
    for(let i = 0 ; i < 64 ; i ++){
      boardRep.push(
      <div className={this.getCellStyle(i, select, highlight)} key={i} 
        onClick={()=>{onCellClick(i); if(rep[i] && select.length === 0){availableMove(i)}}}>
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