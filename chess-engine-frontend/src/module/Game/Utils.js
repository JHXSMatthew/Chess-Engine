
//state string includes board string and other meta informations

export const deserializeState = (stateString) =>{
  const split = stateString.split(" ");
  if(split.length < 6){
    return {}
  }
  return {
    boardStr: split[0],
    currentTurn: split[1],
    castling: split[2],
    enPassantTarget: split[3],
    halfMove: split[4],
    fullMove: split[5]
  }


}

export const seriliaseState = (stateObj) =>{
  if(stateObj){
    return [stateObj.boardStr, stateObj.currentTurn, stateObj.castling, stateObj.enPassantTarget, stateObj.halfMove, stateObj.fullMove].join(' ')
  }else{
    return "";
  }
}



// string rep: "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR" & length <= 64
// array rep : [r,n,b,q,...., N , R] & length = 64

//decode board rep string to an array
export const boardStrToRepArray = (repStr) =>{
  if(!repStr){
    return []
  }
  const xs = repStr.split("/");
  const acc = []
  for(let i of xs){
    const detail = i.split("");
    for(let j of detail){
      if(Number.isInteger(parseInt(j, 10))){
        for(let k = 0 ; k < parseInt(j, 10); k ++){
            acc.push(0)
        }
      }else{
        acc.push(j);
      }
    }
  }
  return acc;
}


// encode board rep array to a string
export const boardRepArrayToStr = (array) =>{
  if(!array){
    return "";
  }

  let final = ""
  let pending = 0

  for(let i in array){
    if(i>0 && i % 8 === 0){
      if(pending){
        final += pending
        pending = 0
      }
      final += "/"
    }

    if(array[i]){
      if(pending){
        final += pending
        pending = 0
      }
      final += array[i]
    }else{
      pending += 1;
    }
  }
  return final;

}



export const indexMorphism = (viewIndex) =>{
  if(viewIndex > 63 || viewIndex < 0){
    return -1;
  }

  return (8-Math.floor(viewIndex/8))*8 - (8 - viewIndex%8);
}
