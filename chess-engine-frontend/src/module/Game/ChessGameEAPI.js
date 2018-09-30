import { API_ENDPOINT } from '../../config'
import axios from 'axios'


 export const MoveApi = {
  postMove : (state, from , to)=>{
    return axios.post(API_ENDPOINT + "/move", {
      state,
      from,
      to
    });
  },
  postAvaliableMove : (state, from) =>{
  	return axios.post(API_ENDPOINT + "/move/available", {
  		state,
  		from
  	})
  }
}

export const NetworkedGameApi = {
  postGame: () => {
    return axios.post(API_ENDPOINT + '/game', {});
  },
  getGame: (id)=>{
    return axios.get(API_ENDPOINT + `/game/${id}`)
  },
  //join game
  PutGame: (id) =>{
    return axios.put(API_ENDPOINT + `/game/${id}`)
  },
  //move
  patchGame: (id, move) =>{
    return axios.patch(API_ENDPOINT + `/game/${id}`, move )
  }
}





