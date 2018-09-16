import { API_ENDPOINT } from '../../config'
import axios from 'axios'


 const Api = {
  postMove : (state, from , to)=>{
    return axios.post(API_ENDPOINT + "api/move", {
      state,
      from,
      to
    });
  },
  postAvaliableMove : (state, from) =>{
  	return axios.post(API_ENDPOINT + "api/move/available", {
  		state,
  		from
  	})
  }
}

export default Api

