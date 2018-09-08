import { boardStrToRepArray, boardRepArrayToStr} from '../module/Game/Utils'


export const regMove = (server) =>{
  server.mockPost("api/move", (request) =>{
    const body = JSON.parse(request.requestBody);
    const arrayRep = boardStrToRepArray(body.state);
    arrayRep[body.to] = arrayRep[body.from] 
    arrayRep[body.from] = 0
    
    return [200, { 'Content-Type': 'application/json' }, JSON.stringify({
      state: boardRepArrayToStr(arrayRep)
    })];
  });

  server.mockPost("api/availableMove", (request) =>{
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

