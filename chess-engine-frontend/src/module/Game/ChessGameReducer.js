import {boardStrToRepArray, indexMorphism} from './Utils'

const INIT_BOARD_STATE_STR = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"
const LOCAL_SAVED_GAME_INDEX = "savedLocalGame"
const LOCAL_SAVED_GAME_LASTMOVE = "savedLocalLastMove"


export const GAME_TYPE = {
  LOCAL_GAME: 'LocalGame',
  INVITE_NETWOKRED: 'InviteNetworked'
}

export const GAME_STATUS = {
  INIT: 'lobby',
  INGAME: 'ingame',
  FINISHED: 'finished'
}


const lobbyInitState = {
  //undefined, show two buttons, create or join
  //true, show create view, read-only code
  //false, show the join lobby view
  creating: undefined,
  gameIdCopied: false,
  gameId: undefined,
  playerType: undefined
}

//reducer
const initState = {
  // UI states
  boardRep: boardStrToRepArray(INIT_BOARD_STATE_STR),
  boardHightLight: [],
  lastMovePair: [],
  select: [],
  gameType: "",
  gameStatus: GAME_STATUS.INIT,
  //the game state obj
  boardStr: INIT_BOARD_STATE_STR,
  currentTurn: "w",
  castling: "KQkq",
  enPassantTarget: "-",
  halfMove: "0",
  fullMove: "1",
  isChecked: false,
  isCheckmate: false,

  // storep revious state, for the undo
  history: [],
  //the real move history
  moveHistory: [],

  //networked game
  //game lobby (before game start)
  lobby: lobbyInitState
}


export const gameReducer  = (state = initState, action)=>{
  switch(action.type){
    case UPDATE_GAME_STATE_SUCCESS:
      const newGameState = action.state
      const {isCheckmate, isChecked} = action

      return Object.assign({},
        state, //old state
        newGameState, // gamestate
        { //UI state 
          boardHightLight: [],
          isCheckmate,
          isChecked,
          boardRep: boardStrToRepArray(newGameState.boardStr),
      });
    case MOVE_FAIL:
      return Object.assign({}, state, {
        boardHightLight: []
      })
    case UPDATE_GAME_STATE_FAIL:
      return state;
    case LOAD_INIT_BOARD_STATE:
      return Object.assign({}, state, initState)
    case CLEAR_SELECT:
      return Object.assign({}, state, {
        select: []
      })
    case SELECT_CHOP:
      return Object.assign({}, state, {
        select: state.select.slice(1)
      })
    case ON_SELECT_CELL:
      //a "pair" using array 

      // 3 cases:
      // if clicked on a piece, then highlight that square.
      // if clicked on a piece previouly, and clicked anywhere else, move that piece from -> to.
      // if clicked on empty squere initially, do nothing
      let newSelectListRep = [];
      let highlight = state.boardHightLight;
      if (state.gameType) {
        if (state.select.length === 0 && state.boardRep[action.index]){
          newSelectListRep = state.select.concat([action.index])
        } else if (state.select.length === 1 && state.select[0] !== action.index) {
          newSelectListRep = state.select.concat([action.index])
        } else {
          // Empty square initially, or deselect piece
          highlight = []
        }
      }
      return Object.assign({}, state, {
        select: newSelectListRep,
        boardHightLight: highlight
      })
    case HIGHLIGHT_AVAILABLE:
      return Object.assign({}, state, {
        boardHightLight: action.available
      })
    case HIGHLIGHT_LAST_MOVE:
      return Object.assign({}, state, {
        lastMovePair: action.lastMovePair
      })
    case END_GAME:
      return Object.assign({}, state, {
        gameStatus: GAME_STATUS.END
      })
    case NEW_LOCAL_GAME:
    case NEW_NETWORKED_GAME:
      return Object.assign({}, state, {
        boardStr: INIT_BOARD_STATE_STR,
        boardRep: boardStrToRepArray(INIT_BOARD_STATE_STR),
        boardHightLight: [],
        lastMovePair: [],
        select: [],      
      }, newGameReducer(state, action))
    case ADD_MOVE_HISTORY:
      return Object.assign({}, state, {
        history: [...state.history, Object.assign({}, state, {history: []})],
        moveHistory: [...state.moveHistory, action.move]
      })
    case UNDO_SUCCESS:
      if(state.history && state.history.length != 0){
        const history = state.history;
        return Object.assign({}, history[history.length -1], {
          history: [...state.history.slice(0, state.history.length -1)]
        })
      }else{
       return state;
      }
    case NETWORKED_GAME_START:
      return Object.assign({}, state, {
        gameStatus: GAME_STATUS.INGAME
      })
    //networked game reducer composition
    case NETWORKED_CREATE_LOBBY_SUCCESS:
    case NETWORKED_LOBBY_WANT_TO_JOIN:
    case NETWORKED_UPDATE_GAME_ID:
    case NETWOKRED_SET_GAMEID_COPIED:
    case NETWORKED_JOIN_GAME_SUCCESS:
    case NETWORKED_JOIN_GAME_FAIL:
      return Object.assign({}, state, {
        lobby: invitedNetowkredLobbyReducer(state.lobby,action)
      })
    default:
      return state;
  }
}
 
const invitedNetowkredLobbyReducer = (state, action) =>{
  switch(action.type){
    case NETWORKED_CREATE_LOBBY_SUCCESS:
      return Object.assign({}, state, {
        ...action.data,
        creating: true
      })   
    case NETWORKED_LOBBY_WANT_TO_JOIN:
      return Object.assign({}, state, {
        creating: false
      })
    case NETWORKED_UPDATE_GAME_ID:
      return Object.assign({}, state, {
        gameId: action.gameId
      })
    case NETWOKRED_SET_GAMEID_COPIED:
      return Object.assign({}, state,{
        gameIdCopied: action.copied
      })
    case NETWORKED_JOIN_GAME_SUCCESS:
      return Object.assign({}, state, {
        ...action.data
      })
    default:
      return state
  }
}

const newGameReducer = (state, action) => {
  switch(action.type){
    case NEW_NETWORKED_GAME:
      return {
        gameType: GAME_TYPE.INVITE_NETWOKRED,
      };
    case NEW_LOCAL_GAME:
      return {
        gameType: GAME_TYPE.LOCAL_GAME,
        gameStatus: GAME_STATUS.INGAME,
        lobby: Object.assign({}, state.lobby, {
          gameIdCopied: false
        })
      };
    default:
      return state;
  }
}

export const SELECT_CHOP = "SELECT_CHOP"
export const actionChopSelect = ()=>{
  return {
    type: SELECT_CHOP
  }
}

export const NETWORKED_GAME_START = "NETWORKED_GAME_START"
export const actionNetowkredGameStart = ()=>{
  return {
    type: NETWORKED_GAME_START
  }
}

export const NETWORKED_JOIN_GAME = "NETWORKED_JOIN_GAME"
export const actionNetworkedJoinGame = (gameId)=>{
  return {
    type: NETWORKED_JOIN_GAME,
    gameId
  }
}

export const NETWORKED_JOIN_GAME_SUCCESS = "NETWORKED_JOIN_GAME_SUCCESS"
export const actionNetworkedJoinGameSuccess = (data)=>{
  return {
    type: NETWORKED_JOIN_GAME_SUCCESS,
    data
  }
}

export const NETWORKED_JOIN_GAME_FAIL = "NETWORKED_JOIN_GAME_FAIL"
export const actionNetworkedJoinGameFail = (msg)=>{
  return {
    type: NETWORKED_JOIN_GAME_FAIL,
    msg
  }
}

export const NETWORKED_UPDATE_GAME_ID = "NETWORKED_UPDATE_GAME_ID"
export const actionNetworkedUpdateGameId = (gameId) =>{
  return {
    type: NETWORKED_UPDATE_GAME_ID,
    gameId
  }
}

export const NETWOKRED_SET_GAMEID_COPIED = "NETWOKRED_SET_GAMEID_COPIED"
export const actionNetworkedSetGameIdCopied = (copied) =>{
  return {
    type: NETWOKRED_SET_GAMEID_COPIED,
    copied
  }
}



export const NETWORKED_CREATE_LOBBY = "NETWORKED_CREATE_LOBBY"
export const actionNetworkedCreateLobby = () =>{
  return {
    type: NETWORKED_CREATE_LOBBY
  }
}

export const NETWORKED_CREATE_LOBBY_SUCCESS = "NETWORKED_CREATE_LOBBY_SUCCESS"
export const actionNetworkedCreateLobbySuccess = (data) =>{
  return {
    type: NETWORKED_CREATE_LOBBY_SUCCESS,
    data
  }
}

export const NETWORKED_CREATE_LOBBY_FAIL = "NETWORKED_CREATE_LOBBY_FAIL"
export const actionNetworkedCreateLobbyFail = () =>{
  return {
    type: NETWORKED_CREATE_LOBBY_FAIL
  }
}


const NETWORKED_LOBBY_WANT_TO_JOIN= "NETWORKED_LOBBY_WANT_TO_JOIN"
export const actionNetworkedWantToJoin = ()=>{
  return {
    type: NETWORKED_LOBBY_WANT_TO_JOIN
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

const UPDATE_GAME_STATE_SUCCESS = "UPDATE_GAME_STATE_SUCCESS"
export const actionUpdateGameStateSuccess = ({state, isChecked, isCheckmate}) =>{
  return {
    type: UPDATE_GAME_STATE_SUCCESS,
    state,
    isChecked,
    isCheckmate
  }
}
const UPDATE_GAME_STATE_FAIL = "UPDATE_GAME_STATE_FAIL"
export const actionUpdateGameStateFail = (message) =>{
  return {
    type: UPDATE_GAME_STATE_FAIL,
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
    from: from,
    to: to
  }
}

export const UNDO_REQUEST = "UNDO_REQUEST"
export const actionUndoRequest = () =>{
  return {
    type: UNDO_REQUEST
  }
}

export const UNDO_SUCCESS = "UNDO_SUCCESS"
export const actionUndoSuccess = () =>{
  return {
    type: UNDO_SUCCESS
  }
}

export const UNDO_FAIL = "UNDO_FAIL"
export const actionUndoFail = () =>{
  return {
    type: UNDO_FAIL
  }
}


export const ADD_MOVE_HISTORY = "ADD_MOVE_HISTORY"
export const actionAddMoveHistory = (move)=>{
  return {
    type:ADD_MOVE_HISTORY,
    move
  }
}


export const AVAILABLE_MOVE_REQUEST = "AVAILABLE_MOVE_REQUEST"
export const actionAvailableMove = (from)=>{
  return {
    type: AVAILABLE_MOVE_REQUEST,
    from: from
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
    index: LOCAL_SAVED_GAME_INDEX,
    lastMove: LOCAL_SAVED_GAME_LASTMOVE
  }
}

export const LOAD_LOCAL_SAVED_GAME = "LOAD_LOCAL_SAVED_GAME"
export const actionLoadLocalSavedGame = () =>{
  return {
    type: LOAD_LOCAL_SAVED_GAME,
    index: LOCAL_SAVED_GAME_INDEX,
    lastMove: LOCAL_SAVED_GAME_LASTMOVE
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

const HIGHLIGHT_LAST_MOVE = "HIGHLIGHT_LAST_MOVE"
export const actionHighlightLastMove = (lastMovePair) =>{
  return {
    type: HIGHLIGHT_LAST_MOVE,
    lastMovePair
  }
}

const END_GAME = "END_GAME"
export const actionEndGame = (winLose) =>{
  return {
    type: END_GAME,
    winLose
  }
}

const NEW_LOCAL_GAME = "NEW_LOCAL_GAME"
export const actionNewLocalGame = () =>{
  return {
    type: NEW_LOCAL_GAME
  }
}


const NEW_NETWORKED_GAME = "NEW_NETWORKED_GAME"
export const actionNewNetworkedGame = () =>{
  return {
    type: NEW_NETWORKED_GAME
  }
}

