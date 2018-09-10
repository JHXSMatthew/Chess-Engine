import {boardStrToRepArray} from './Utils'

const INIT_BOARD_STATE_STR = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"
const LOCAL_SAVED_GAME_INDEX = "savedLocalGame"


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
        boardHightLight: []
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
      var highlight = state.boardHightLight;
      if (state.select.length === 0 && state.boardRep[action.index]){
        newSelectListRep = state.select.concat([action.index])
      } else if (state.select.length === 1 && state.select[0] !== action.index) {
        newSelectListRep = state.select.concat([action.index])
      } else {
        // Empty square initially, or deselect piece
        highlight = []
      }
      
      return Object.assign({}, state, {
        select: newSelectListRep,
        boardHightLight: highlight
      })
    case HIGHLIGHT_AVAILABLE:
      return Object.assign({}, state, {
        boardHightLight: action.available
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

export const AVAILABLE_MOVE_REQUEST = "AVAILABLE_MOVE_REQUEST"
export const actionAvailableMove = (from)=>{
  return {
    type: AVAILABLE_MOVE_REQUEST,
    from
  }
}

const MOVE_FAIL = "MOVE_FAIL"
export const actionMoveFail = (message)=>{
  return {
    type: MOVE_FAIL,
    message
  }
}

const HIGHLIGHT_AVAILABLE = "HIGHLIGHT_AVAILABLE"
export const actionHighlightAvailable = (available)=>{
  return {
    type: HIGHLIGHT_AVAILABLE,
    available
  }
}


export const SAVE_LOCAL_GAME  = "SAVE_LOCAL_GAME"
export const actionSaveLocalGame = ()=>{
  return {
    type: SAVE_LOCAL_GAME,
    index: LOCAL_SAVED_GAME_INDEX
  }
}

export const LOAD_LOCAL_SAVED_GAME = "LOAD_LOCAL_SAVED_GAME"
export const actionLoadLocalSavedGame = () =>{
  return {
    type: LOAD_LOCAL_SAVED_GAME,
    index: LOCAL_SAVED_GAME_INDEX
  }
}

const LOAD_SAVED_GAME_FAIL = "LOAD_SAVED_GAME_FAIL"
export const actionLoadSavedGameFail = (message)=>{
  return {
    type: LOAD_SAVED_GAME_FAIL,
    message
  }
}

const SAVE_GAME_SUCCESS = "SAVE_GAME_SUCCESS"
export const actionSaveGameSuccess = (message) =>{
  return {
    type: SAVE_GAME_SUCCESS,
    message
  }
}

const SAVE_GAME_FAIL = "SAVE_GAME_FAIL"
export const actionSaveGameFail = (message) =>{
  return {
    type: SAVE_GAME_FAIL,
    message
  }
}
