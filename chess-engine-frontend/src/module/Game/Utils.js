

export const boardStrToRepArray = (repStr) =>{
  if(!repStr){
    return []
  }
  const xs = repStr.split("/");
  const acc = []
  for(let i of xs){
    const detail = i.split("");
    console.log(detail)
    for(let j of detail){
      console.log(j)
      if(Number.isInteger(parseInt(j))){
        for(let k = 0 ; k < parseInt(j); k ++){
            acc.push("EMPTY")
        }
      }else{
        acc.push(j);
      }
    }
  }
  console.log(acc)
  return acc;
}