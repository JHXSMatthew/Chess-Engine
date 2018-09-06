import {boardStrToRepArray} from './Utils'

const INIT_BOARD_STATE_STR = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"



//reducer
const initState = {
  boardStr: INIT_BOARD_STATE_STR,
  boardRep: boardStrToRepArray(INIT_BOARD_STATE_STR)
}

export const gameReducer  = (state = initState, action)=>{
  switch(action.type){
    case UPDATE_BOARD_STATE_SUCCESS:
      return Object.assign({}, state, {
        boardStr: state,
        
      });
    case UPDATE_BOARD_STATE_FAIL:
      return state;
    case LOAD_INIT_BOARD_STATE:
      return Object.assign({}, state, {
        boardStr: INIT_BOARD_STATE_STR,
        boardRep: boardStrToRepArray(INIT_BOARD_STATE_STR)
      })
    default:
      return state;
  }
}
 

const UPDATE_BOARD_STATE_SUCCESS = "UPDATE_BOARD_STATE_SUCCESS"
export const actionUpdateStateSuccess = (newState) =>{
  return {
    type: UPDATE_BOARD_STATE_SUCCESS,
    newState
  }
}
const UPDATE_BOARD_STATE_FAIL = "UPDATE_BOARD_STATE_FAIL"
export const actionUpdateStateFail = (reason) =>{
  return {
    type: UPDATE_BOARD_STATE_FAIL,
    reason
  }
}


const LOAD_INIT_BOARD_STATE = "LOAD_INIT_BOARD_STATE"
export const actionLoadInitState = ()=>{
  return {
    type: "LOAD_INIT_BOARD_STATE"
  }
}

