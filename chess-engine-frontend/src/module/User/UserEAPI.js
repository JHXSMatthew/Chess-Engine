import { API_ENDPOINT } from '../../config'
import axios from 'axios'


 export const AuthApi = {
  post : (userName, password)=>{ //auth: {userName, password}
    return axios.post(API_ENDPOINT + "/auth", {
      userName,
      password
    });
  },
  delete : (token, userId) =>{ //token: {token, userId}
  	return axios.delete(API_ENDPOINT + "/auth", {
      token,
      userId
  	})
  }
}

export const UserApi = {
  post : (userName, password, email)=>{ 
  return axios.post(API_ENDPOINT + "/user", {
      userName,
      password,
      email
    });
  },
  put : (userName, password, newPassword) =>{ //token: {token, userId}
    return axios.put(API_ENDPOINT + `/user/${userName}` , {
      password,
      newPassword
    });
  },
  get: (id, token)=>{
    return axios.get(API_ENDPOINT + `/user/${id}?token=${token}`)
  },
  
}

export const RankApi = {
  get : () => {
    return axios.get(API_ENDPOINT + `/ranking`)
  }
}