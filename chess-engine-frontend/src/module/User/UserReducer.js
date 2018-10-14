
const initState = {
  auth: undefined,
  info: {

  }
}



export const userReducer  = (state=initState, action) =>{
  switch(action.type){
    case TYPE_USER_LOGIN_FAIL:
    case TYPE_USER_REGISTER_FAIL:
    case TYPE_GET_USER_INFO_FAIL:
      return Object.assign({}, state, { err: action.err })
    case TYPE_USER_LOGIN_SUCCESS:
      return Object.assign({}, state, { auth: action.auth })
    case TYPE_USER_LOGOFF:
      return {};
    case TYPE_GET_USER_INFO_SUCCESS:
      return Object.assign({}, state, {
        info: action.info
      })
    case LOAD_LEADERBOARD:
      return Object.assign({}, state, {
        info: action.info
      })
    default:
      return state;

  }
}

// const TYPE_USER_LOGIN = "TYPE_USER_LOGIN";
const TYPE_USER_LOGIN_FAIL = "TYPE_USER_LOGIN_FAIL";
const TYPE_USER_LOGIN_SUCCESS = "TYPE_USER_LOGIN_SUCCESS";
export const TYPE_USER_LOGOFF = "TYPE_USER_LOGOFF";

export const TYPE_USER_LOGIN = "TYPE_USER_LOGIN"
export const actionUserLogin = (model) =>{
  return {
    type: TYPE_USER_LOGIN,
    model
  }
}

export const actionUserLogoff = () => {
  localStorage.setItem('auth', null);
  return {
    type: TYPE_USER_LOGOFF
  }
}

export const actionUserLoginFail = (err)=>{
  return {
    type: TYPE_USER_LOGIN_FAIL,
    err
  }
}

export const actionUserLoginSuccess = (auth)=>{
  return {
    type: TYPE_USER_LOGIN_SUCCESS,
    auth
  }
}

export const TYPE_USER_REGISTER = "TYPE_USER_REGISTER"
export const TYPE_USER_REGISTER_SUCCESS = "TYPE_USER_REGISTER_SUCCESS"
export const TYPE_USER_REGISTER_FAIL = "TYPE_USER_REGISTER_FAIL"

export const actionUserRegister = (model)=>{
  return {
    type: TYPE_USER_REGISTER,
    model
  }
}

export const actionUserRegisterSuccess = () =>{
  return {
    type: TYPE_USER_REGISTER_SUCCESS
  }
}

export const actionUserRegisterFail = (err) =>{
  return {
    type: TYPE_USER_REGISTER_FAIL,
    err
  }
}

export const TYPE_GET_USER_INFO = "TYPE_GET_USER_INFO"
export const TYPE_GET_USER_INFO_SUCCESS = "TYPE_GET_USER_INFO_SUCCESS"
export const TYPE_GET_USER_INFO_FAIL = "TYPE_GET_USER_INFO_FAIL"

export const actionGetUserInfo = (tokenObj) =>{
  return {
    type: TYPE_GET_USER_INFO,
    ...tokenObj
  }
}

export const actionGetUserInfoSuccess = (info) => {
  return {
    type: TYPE_GET_USER_INFO_SUCCESS,
    info
  }
}

export const actionGetuserInfoFail = (err) => {
  return {
    type: TYPE_GET_USER_INFO_FAIL,
    err
  }
}




export const TYPE_LOAD_CACHE_LOGIN = "TYPE_LOAD_CACHE_LOGIN"
export const actionOnLoadCacheLogin = ()=> {
  return {
    type: TYPE_LOAD_CACHE_LOGIN
  }
}

export const GET_LEADERBOARD = "GET_LEADERBOARD"
export const actionGetLeaderboard = () => {
  return {
    type: GET_LEADERBOARD,
  }
}
  
export const LOAD_LEADERBOARD = "LOAD_LEADERBOARD"
export const actionLoadLeaderboard = (info) => {
  return {
    type: LOAD_LEADERBOARD,
    info
  }
}
  
