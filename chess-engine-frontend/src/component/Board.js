import React from 'react'

import { PiecesSVG } from '../resource/PieceResource'
import './Board.less';


export default class Board extends React.Component{


  getCellStyle = (index)=>{
    const f = (Math.floor(index/8 % 2) === 0 ) ? (a,b)=> a===b : (a,b)=> a!==b;
    return f (index % 2 ,0)  ? "cell-bg-dark" : "cell-bg-light"
  }

  render(){
    const {rep} = this.props;
    
    let boardRep = [];
    for(let i = 0 ; i < 64 ; i ++){
      boardRep.push(
      <Cell className={this.getCellStyle(i)} key={i}>
        {PiecesSVG[rep[i]]}
      </Cell>)
    }
    return (
      <div className="board">
        {boardRep}
      </div>
    );
    
  }
}


class Cell extends React.Component{
  render(){
    
    return(
      <div className={this.props.className}>
        {this.props.children}
      </div>
    )
  }
}




