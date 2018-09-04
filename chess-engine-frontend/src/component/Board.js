import React from 'react'

import Style from './Board.less';


export default class Board extends React.Component{


  getCellStyle = (index)=>{
    const f = (Math.floor(index/8 % 2) === 0 ) ? (a,b)=> a===b : (a,b)=> a!==b;
    return f (index % 2 ,0)  ? "cell-bg-dark" : "cell-bg-light"
  }

  render(){
    const {pieces} = this.props;

    let boardRep = [];
    for(let i = 0 ; i < 64 ; i ++){     
      boardRep.push(<Cell className={this.getCellStyle(i)} key={i} />)
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
        <Piece></Piece>
      </div>
    )
  }
}

class Piece extends React.Component{
  render(){
    return (
      <div>
        P
      </div>
    )
  }
}

