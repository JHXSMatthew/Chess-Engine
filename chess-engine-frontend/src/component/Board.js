import React from 'react'

import { PiecesSVG } from '../resource/PieceResource'
import './Board.less';


export default class Board extends React.Component{


  getCellStyle = (index, selected)=>{
    const f = (Math.floor(index/8 % 2) === 0 ) ? (a,b)=> a===b : (a,b)=> a!==b;
    if (selected.length > 0 && index === selected[0]){
      return "cell-bg-highlight";
    }
    return f (index % 2 ,0)  ? "cell-bg-dark" : "cell-bg-light";
  }

  render(){
    const {rep,select,onCellClick} = this.props;
    
    let boardRep = [];
    for(let i = 0 ; i < 64 ; i ++){
      boardRep.push(
      <div className={this.getCellStyle(i, select)} key={i} onClick={()=>{onCellClick(i)}}>
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