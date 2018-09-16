import { boardStrToRepArray, boardRepArrayToStr, deserializeState, seriliaseState} from '../module/Game/Utils'


export const regMove = (server) =>{
  server.mockPost("api/move", (request) =>{
		const body = JSON.parse(request.requestBody);
		const stateObj = deserializeState(body.state)
    const arrayRep = boardStrToRepArray(stateObj.boardStr);
    arrayRep[body.to] = arrayRep[body.from] 
		arrayRep[body.from] = 0
		
		
    return [200, { 'Content-Type': 'application/json' }, JSON.stringify({
      state: seriliaseState(Object.assign({}, stateObj, {
				boardStr: boardRepArrayToStr(arrayRep)
			}))
    })];
  });

  server.mockPost("api/move/available", (request) =>{
  	const body = JSON.parse(request.requestBody);
  	var a = [];
  	for (let i = 0; i < 64; i++){
  		if (i === body.from-1 || i === body.from+1 || i === body.from-8 || i === body.from+8){
  			a[i] = 1;
  		} else{
	  		a[i] = 0;
  		}
  	}

  	return [200, {'Content-Type': 'application/json'}, JSON.stringify({
  		available: a
  	})];
  })
}

