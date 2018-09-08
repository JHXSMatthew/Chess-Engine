import {boardStrToRepArray} from './Utils'

const INIT_BOARD_STATE_STR = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"



//reducer
const initState = {
  boardStr: INIT_BOARD_STATE_STR,
  boardRep: boardStrToRepArray(INIT_BOARD_STATE_STR),
  boardHightLight: [],
  select: []
}

export const gameReducer  = (state = initState, action)=>{
  switch(action.type){
    case UPDATE_BOARD_STATE_SUCCESS:
      return Object.assign({}, state, {
        boardStr: action.newState,
        boardRep: boardStrToRepArray(action.newState),
      });
    case UPDATE_BOARD_STATE_FAIL:
      return state;
    case LOAD_INIT_BOARD_STATE:
      return Object.assign({}, state, {
        boardStr: INIT_BOARD_STATE_STR,
        boardRep: boardStrToRepArray(INIT_BOARD_STATE_STR)
      })
    case CLEAR_SELECT:
      return Object.assign({}, state, {
        select: []
      })
    case ON_SELECT_CELL:
      //a "pair" using array 

      // 3 cases:
      // if clicked on a piece, then highlight that square.
      // if clicked on a piece previouly, and clicked anywhere else, move that piece from -> to.
      // if clicked on empty squere initially, do nothing
      var newSelectListRep = [];
      if (state.select.length === 0 && state.boardRep[action.index]){
        newSelectListRep = state.select.concat([action.index])
      } else if (state.select.length === 1) {
        newSelectListRep = state.select.concat([action.index])
      } else {
        // piece selected is not valid, then do nothing,
      }
      
      return Object.assign({}, state, {
        select: newSelectListRep,
        boardHightLight: []
      })
    default:
      return state;
  }
}
 

const CLEAR_SELECT = "CLEAR_SELECT";
export const actionClearSelect = ()=>{
  return {
    type: CLEAR_SELECT
  }
}

const ON_SELECT_CELL  = "SELECT_CELL"
export const actionSelectCell = (index)=>{
  return {
    type: ON_SELECT_CELL,
    index
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
export const actionUpdateStateFail = (message) =>{
  return {
    type: UPDATE_BOARD_STATE_FAIL,
    message
  }
}


const LOAD_INIT_BOARD_STATE = "LOAD_INIT_BOARD_STATE"
export const actionLoadInitState = ()=>{
  return {
    type: LOAD_INIT_BOARD_STATE
  }
}

export const MOVE_REQUEST = "MOVE_REQUEST"
export const actionMove = (from, to)=>{
  return {
    type: MOVE_REQUEST,
    from,
    to
  }
}

const MOVE_FAIL = "MOVE_FAIL"
export const actionMoveFail = (message)=>{
  return {
    type: MOVE_FAIL,
    message
  }
}
