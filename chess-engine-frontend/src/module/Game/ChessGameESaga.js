import { take , call, put, takeEvery, select, cancelled } from 'redux-saga/effects'
import { eventChannel, END } from 'redux-saga'

import { 
  MOVE_REQUEST, 
  AVAILABLE_MOVE_REQUEST,
  actionUpdateGameStateSuccess, 
  actionMoveFail,
  actionHighlightAvailable,
  actionLoadSavedGameFail,
  LOAD_LOCAL_SAVED_GAME,
  actionSaveGameSuccess,
  actionSaveGameFail,
  SAVE_LOCAL_GAME,
  actionHighlightLastMove,
  actionUpdateGameStateFail,
  actionUndoFail,
  GAME_TYPE,
  actionUndoSuccess,
  UNDO_REQUEST,
  actionAddMoveHistory,
  NETWORKED_CREATE_LOBBY,
  actionNetworkedCreateLobbyFail,
  actionNetworkedCreateLobbySuccess,
  actionNetworkedJoinGame,
  actionNetworkedJoinGameFail,
  actionNetworkedJoinGameSuccess,
  NETWORKED_JOIN_GAME,
  GAME_STATUS,
  actionNetowkredGameStart,
} from './ChessGameReducer'

import { MoveApi, NetworkedGameApi} from './ChessGameEAPI'

import {
  seriliaseState,
  deserializeState
} from './Utils'


export function* gameSaga(){
  yield takeEvery(MOVE_REQUEST, MoveRequest);
  yield takeEvery(AVAILABLE_MOVE_REQUEST, AvailableMoveRequest);
  //save load
  yield takeEvery(LOAD_LOCAL_SAVED_GAME, LoadLocalSavedGame)
  yield takeEvery(SAVE_LOCAL_GAME, SaveLocalGame)
  yield takeEvery(UNDO_REQUEST, UndoRequest)

  yield takeEvery(NETWORKED_CREATE_LOBBY, NetworkedCreateLobby)
  yield takeEvery(NETWORKED_JOIN_GAME, NetworkedJoinLobby)
}


function* MoveRequest(action){
  try{
    const currentBoardState = yield select((state) => seriliaseState(state.game))
    const gameType = yield select((state) => state.game.gameType)
    let apiToCall = undefined

    switch(gameType){
      case GAME_TYPE.LOCAL_GAME:
        apiToCall = MoveApi.postMove
        break;
      case GAME_TYPE.INVITE_NETWOKRED:

        const playerTypeCheck = yield select((state) => {
          return {
            result: state.game.currentTurn === state.game.lobby.playerType,
            playerType: state.game.currentTurn
          }
        })
        const gameId = yield select((state) => {
          return state.game.lobby.gameId
        })
        
        if(playerTypeCheck.result === true){
          apiToCall = (...others)=> {
            console.log(others)
            return NetworkedGameApi.patchGame(gameId, playerTypeCheck.playerType , others)}
          
        }else{
          yield put(actionUpdateGameStateFail("Not the turn"))
          throw {message: "wrong turn"}
        }
        break;
      default:
        // console.log("illegal game type in move saga")
        throw {message: "illegal game type in move saga"}
    }

    const response =  yield call(apiToCall, currentBoardState, action.from, action.to);
    const stateObj = response.data;


    if(stateObj.state === currentBoardState){
      yield put(actionUpdateGameStateFail("Illegal move."))
    }else{
      yield put(actionAddMoveHistory({from: action.from , to: action.to}))
      yield put(actionUpdateGameStateSuccess({...response.data, state: deserializeState(stateObj.state)}))
      yield put(actionHighlightLastMove([action.from, action.to]))
    }
  }catch(e){
    yield put(actionMoveFail(e.message))
  }
}

function* LoadLocalSavedGame(action){
  try{
    const game = localStorage.getItem(action.index);
    const lastMoveStr = localStorage.getItem(action.lastMove);
    const lastMove = JSON.parse("[" + lastMoveStr + "]");
    const stateObj  = JSON.parse(game);
    console.log(stateObj)

    if(game){
      yield put(actionUpdateGameStateSuccess({... stateObj, state: deserializeState(stateObj.state) }))
      yield put(actionHighlightLastMove(lastMove))
    }else{
      yield put(actionLoadSavedGameFail("no saved game!"))
    }
  }catch(e){
    yield put(actionLoadSavedGameFail(e.message))
  }
}

function* SaveLocalGame(action){
  try{
    const currentBoardState = yield select((state) => {
      const stateObj = state.game;
      return JSON.stringify({
        state: seriliaseState(stateObj),
        isChecked: stateObj.isChecked,
        isCheckmate: stateObj.isCheckmate
      })
    })
    console.log(currentBoardState)
    const lastMovePair = yield select((state) => state.game.lastMovePair)
    localStorage.setItem(action.index, currentBoardState);
    localStorage.setItem(action.lastMove, lastMovePair);
    yield put(actionSaveGameSuccess(currentBoardState));
  }catch(e){
    yield put(actionSaveGameFail(e.message));
  }
}

function* AvailableMoveRequest(action){
  try{
    const currentBoardState = yield select((state) => seriliaseState(state.game))
    const availableMoves = yield call(MoveApi.postAvaliableMove, currentBoardState, action.from)
    yield put(actionHighlightAvailable(availableMoves.data.available))

  }catch(e){
    console.log("AvailableMoveRequest Api Error: ", e);
  }
}

function* UndoRequest(action){
  try{
    const currentState = yield select((state) => state.game)
    
    if(currentState.gameType === GAME_TYPE.LOCAL_GAME){
      yield put(actionUndoSuccess())
    }else{
      //call api
    }
  }catch(e){
    yield put(actionUndoFail())
  }
}



//networked game saga
function* networkedTimerLoop(gameId){

  const channel = yield call(networkedTimer, gameId)
  while(true){
    try{
      //all current state are named with "game" prefix
      const gameState = yield select((state) => state.game)
      const gameBoardRepStr = seriliaseState(gameState)
    
      let obj = yield take(channel) 
      const {status, state} = obj
      // const {isCheckmate, isChecked} = state
      const currentStatus = gameState.gameStatus;
      /*
      console.log({
        fromStatus: currentStatus,
        toStatus: status,
        equal: status === currentStatus,
        response:obj
      })*/

      if(currentStatus !== status){
        if(currentStatus === GAME_STATUS.INIT){
          if(status === GAME_STATUS.INGAME){
            yield put(actionNetowkredGameStart())
          }else{
            //TODO: is there a possibility it to be here?
            console.log("unhandle case_001")
          }
  
        }else if(currentStatus === GAME_STATUS.INGAME){
  
        }else if(currentStatus === GAME_STATUS.FINISHED){
  
        }else{
          console.log("game status error!")
        }
      }else{
        if(status === GAME_STATUS.INGAME){
          //
          if(gameBoardRepStr === state.state){
            //ignore
          }else{
            yield put(actionUpdateGameStateSuccess({...state, state: deserializeState(state.state)}))
          }
          
        }else{
          //doing nothing
        }

      }

      
      
      //TODO: logics here
    }catch(e){

    }finally{
      if (yield cancelled()) {
        channel.close()
        console.log('TODO:')
      } 
    }
  }
}

function* NetworkedCreateLobby(action) {
  try{
    const gameCreated = yield call(NetworkedGameApi.postGame);
    yield put(actionNetworkedCreateLobbySuccess(gameCreated.data))
    yield networkedTimerLoop(gameCreated.data.gameId)
  }catch(e){
    console.log("Create lobby fail")
    yield put(actionNetworkedCreateLobbyFail())
  }
}


function* NetworkedJoinLobby(action){
  try{
    const gameJoined = yield call(NetworkedGameApi.PutGame, action.gameId)
    console.log(gameJoined)
    yield put(actionNetworkedJoinGameSuccess(gameJoined.data))
    yield networkedTimerLoop(gameJoined.data.gameId)

  }catch(e){
    console.log("join lobby fail!")
    yield put(actionNetworkedJoinGameFail())
  }
}

//saga data channel for the state update timer
function networkedTimer(gameId){
  return eventChannel(emitter => {
      const iv = setInterval(() => {
        try{
          NetworkedGameApi.getGame(gameId).then((r)=>{
            emitter(r.data)
          }).catch(e => {
            emitter(END)
          });
          
        }catch(e){
          emitter(END)
        }
      }, 1000);

      return () => {
        clearInterval(iv)
      }
  })
}