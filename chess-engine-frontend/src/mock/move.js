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
  })
}

