import axios from 'axios'


 const Api = {
  postMove : (state, from , to)=>{
    return axios.post("api/move", {
      state,
      from,
      to
    });
  },
  postAvaliableMove : (state, from) =>{

  }
}

export default Api

