

export const boardStrToRepArray = (repStr) =>{
  if(!repStr){
    return []
  }
  const xs = repStr.split("/");
  const acc = []
  for(let i of xs){
    const detail = i.split("");
    for(let j of detail){
      if(Number.isInteger(parseInt(j))){
        for(let k = 0 ; k < parseInt(j); k ++){
            acc.push(0)
        }
      }else{
        acc.push(j);
      }
    }
  }
  return acc;
}


export const boardRepArrayToStr = (array) =>{
  if(!array){
    return "";
  }

  let final = ""
  let pending = 0

  for(let i in array){
    if(i>0 && i % 8 == 0){
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