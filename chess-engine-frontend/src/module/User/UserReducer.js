
const initState = {
  auth: undefined,
  info: {

  },

  //ui state
  gameHistory: [],
  currentSelectGame: undefined,
  //{gameId: moveHistory}
  moveHisotry: {}
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
    case TYPE_LOAD_MOVE_HISTORY_FOR_GAME_SUCCESS:
      return Object.assign({}, state, {
        moveHisotry: Object.assign({}, state.moveHisotry, {
          [action.gameId]: action.moveHisotry
        })
      })
    case TYPE_LOAD_USER_GAME_HISTORY_SUCCESS:
      return Object.assign({}, state, {
        gameHistory: action.gameHistory
      })
    default:
      return state;

  }
}

export const TYPE_LOAD_USER_GAME_HISTORY = "TYPE_LOAD_USER_GAME_HISTORY"
export const TYPE_LOAD_USER_GAME_HISTORY_SUCCESS= "TYPE_LOAD_USER_GAME_HISTORY_SUCCESS"

export const TYPE_LOAD_MOVE_HISTORY_FOR_GAME = "TYPE_LOAD_MOVE_HISTORY_FOR_GAME"
export const TYPE_LOAD_MOVE_HISTORY_FOR_GAME_SUCCESS = "TYPE_LOAD_MOVE_HISTORY_FOR_GAME_SUCCESS"

export const actionLoadUserGameHistory = ()=>{
  return {
    type: TYPE_LOAD_USER_GAME_HISTORY
  }
}


export const actionLoadUserGameHistorySuccess = (gameHistory)=>{
  return {
    type: TYPE_LOAD_USER_GAME_HISTORY_SUCCESS,
    gameHistory
  }
}


export const actionLoadGameMoveHistory = (gameId)=>{
  return {
    type: TYPE_LOAD_MOVE_HISTORY_FOR_GAME,
    gameId
  }
}

export const actionLoadGameMoveHistorySuccess = (gameId, moveHistory)=>{
  return {
    type: TYPE_LOAD_MOVE_HISTORY_FOR_GAME_SUCCESS,
    gameId,
    moveHistory
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
  
