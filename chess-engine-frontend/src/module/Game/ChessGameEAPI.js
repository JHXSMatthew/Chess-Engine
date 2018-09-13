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
  	return axios.post("api/availableMove", {
  		state,
  		from
  	})
  }
}

export default Api

