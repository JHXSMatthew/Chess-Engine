import { take , cancel, fork, call, put, takeEvery, select, cancelled } from 'redux-saga/effects'
import { eventChannel, END } from 'redux-saga'

import { 
  MOVE_REQUEST, 
  AVAILABLE_MOVE_REQUEST,
  NETWORKED_RESIGN_GAME,
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
  actionClearSelect,
  actionChopSelect,
  actionSelectCell,
  actionAvailableMove,
  NETWORKED_TIMER_DESTORY,
  actionDestoryNetworkedGameTimerSuccess,
  actionDestoryNetworkedGameTimer,
  actionEndGame,
  actionLoadInitState,
  JOIN_MATCH_QUEUE,
  actionJoinMatchQueueSuccess,
  actionJoinMatchQueueFail,
  actionMatchGameStart,
  actionMatchGameStartFail,
  actionMatchGameStartSuccess,
  MATCH_GAME_START,
  actionQueueTimerLoop,
  PROMOTE_PAWN,
  actionPromotePawn
} from './ChessGameReducer'

import { actionUpdateModalInfo,actionToggleModal, actionRedirectLogin, actionShowPromotionModal } from '../../AppReducer'

import { MoveApi, NetworkedGameApi, QueueApi, AIAPI} from './ChessGameEAPI'

import {
  seriliaseState,
  deserializeState,
  compareBoardRep
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
  yield takeEvery(NETWORKED_TIMER_DESTORY, NetworkedDestoryTimer)
  yield takeEvery(NETWORKED_RESIGN_GAME, NetworkedResignGame)

  //queue
  yield takeEvery(JOIN_MATCH_QUEUE, JoinQueue)
  yield takeEvery(MATCH_GAME_START, MatchGameStart)
  yield takeEvery(PROMOTE_PAWN, PromotePawn)
}


function* MoveRequest(action){
  try{
    const currentBoardState = yield select((state) => seriliaseState(state.game))
    const gameType = yield select((state) => state.game.gameType)
    let apiToCall = undefined

    if(gameType === GAME_TYPE.LOCAL_GAME){
      apiToCall = MoveApi.postMove
    }else if(gameType === GAME_TYPE.AI){
      apiToCall = AIAPI.postMove;
    }else{
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
          return NetworkedGameApi.patchGame(gameId, playerTypeCheck.playerType ,others)}
        
      }else{
        yield put(actionUpdateGameStateFail("Not the turn"))
        throw {message: "wrong turn"}
      }
    }

    const response =  yield call(apiToCall, currentBoardState, action.from, action.to);
    const stateObj = response.data;

    yield put(actionClearSelect())
    if(stateObj.state === currentBoardState){
      yield put(actionAvailableMove(action.to))
      yield put(actionSelectCell(action.to))
      yield put(actionUpdateGameStateFail("Illegal move."))

    }else{
      const movedPiece = yield select((state) => state.game.movePiece)
      const gameType = yield select((state) => state.game.gameType)
      const prevState = yield select((state) => state.game.boardRep)
      if (gameType === GAME_TYPE.LOCAL_GAME || gameType === GAME_TYPE.AI){
        yield put(actionAddMoveHistory({piece: movedPiece, from: action.from , to: action.to}))
        yield put(actionUpdateGameStateSuccess({...response.data, state: deserializeState(stateObj.state)}))
        if (gameType === GAME_TYPE.AI) {
          // add Ai move to history and highlight lastmove from Ai instead
          // movedpiece
          const newState = yield select((state) => state.game.boardRep)
          prevState[action.from] = 0
          prevState[action.to] = movedPiece
          var aiMove =  compareBoardRep(prevState, newState)
          yield put(actionAddMoveHistory({piece: aiMove.piece, from: aiMove.from , to: aiMove.to}))
          yield put(actionHighlightLastMove([aiMove.from, aiMove.to]))
        } else {
          if (stateObj.isChecked && !stateObj.isCheckmate){
             yield put(actionUpdateModalInfo({
              content: '',
              show: true,
              title: 'Check',
              action: actionToggleModal(false)
            }))
          }
          yield put(actionHighlightLastMove([action.from, action.to]))
        }
      }
      console.log("-----", stateObj)
      if (stateObj.isPromotion){
        console.log("--- promotion modal:", movedPiece, action.to)
        yield put(actionShowPromotionModal({
          content: movedPiece,
          show: true,
          title: 'Promotion',
          action: actionPromotePawn()
        }))
      }
    }

  }catch(e){
    yield put(actionMoveFail(e.message))
    yield put(actionAvailableMove(action.to))
    yield put(actionSelectCell(action.to))
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
    yield put(actionUpdateModalInfo({
        content: "Your local Game is saved!",
        show: true,
        title: "Save",
        action: actionLoadInitState()
    }))
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
      
      const {status, state, resignedPlayer, lastMove} = obj
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
          if(status === GAME_STATUS.FINISHED){
            //is checkmate? 
            console.log(obj)
            let win = undefined
            let reason = "Result"

            if(state.isCheckmate){
              const {currentTurn} = deserializeState(state.state)
              if(currentTurn === gameState.lobby.playerType){
                //lose  
                win= false;
              }else{
                //win
                win = true;
              }
              reason = 'CheckMate'
            }
            //check resign
            if(resignedPlayer){
              if(resignedPlayer === gameState.lobby.playerType){
                //lost
                win = false;
              }else{
                //win
                win = true;
              }
              reason = 'Resign'

            }
            if(win === undefined){
              console.log("this should never happen, check it.")
            }

            yield put(actionEndGame(win))
            yield put(actionUpdateModalInfo({
              content: 'You' + ' ' + (win?'win':'lose') +"!",
              show: true,
              title: reason,
              action: actionLoadInitState()
            }))
            //todo: this is anti-pattern, try fix it later

            yield put(actionDestoryNetworkedGameTimer())

            
          }
  
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
            const updatedGameState = yield select((state) => state.game)
            console.log("lastMove: ", obj.state.lastMove)
            const lastMoveFrom = obj.state.lastMove.from
            const lastMoveTo = obj.state.lastMove.to
            const lastMovePiece = updatedGameState.boardRep[lastMoveTo]

            // add move history
            // get last moved piece
            console.log("lastmovepiece", lastMovePiece)
            yield put(actionAddMoveHistory({piece: lastMovePiece, from: lastMoveFrom , to: lastMoveTo}))
            // highlight last move
            yield put(actionHighlightLastMove([lastMoveFrom, lastMoveTo]))
            if (updatedGameState.gameType === GAME_TYPE.INVITE_NETWOKRED && updatedGameState.isChecked &&
            updatedGameState.currentTurn === updatedGameState.lobby.playerType){
              yield put(actionUpdateModalInfo({
                content: '',
                show: true,
                title: 'Check',
                action: actionToggleModal(false)
              }))
            }
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
      } 
    }
  }
}


function* NetworkedResignGame(action){
  try{
    const gameId = yield select((state) => {
      return state.game.lobby.gameId
    })

    const playerType = yield select((state) => {
      return state.game.lobby.playerType
    })
    //call the api
    yield call(NetworkedGameApi.resignGame,gameId, playerType)
  }catch(e){
    console.log("regin err.")

  }
}

function* NetworkedDestoryTimer(action){
  try{
    const timer = yield select((state) => state.game.lobby.timerTask)
    if(timer){
      yield cancel(timer)
      yield put(actionDestoryNetworkedGameTimerSuccess())
    }else{    
      console.log("no timer.")
    }
    
  }catch(e){
    console.log("timer err.")

  }
}

function* NetworkedCreateLobby(action) {
  try{
    const gameCreated = yield call(NetworkedGameApi.postGame);
    const task = yield fork(networkedTimerLoop,gameCreated.data.gameId)
    yield put(actionNetworkedCreateLobbySuccess(gameCreated.data, task))
  }catch(e){
    console.log("Create lobby fail")
    yield put(actionNetworkedCreateLobbyFail())
  }
}

function* MatchGameStart(action){
  try{
    const gameCreated = {
      gameId: action.gameId,
      playerType: action.playerType
    };
    
    const task = yield fork(networkedTimerLoop,gameCreated.gameId)
    yield put(actionMatchGameStartSuccess(gameCreated, task))
  }catch(e){
    console.log("match game start fail")
    yield put(actionMatchGameStartFail())
  }
}



function* NetworkedJoinLobby(action){
  try{
    const gameJoined = yield call(NetworkedGameApi.PutGame, action.gameId)
    console.log(gameJoined)
    const task = yield fork(networkedTimerLoop, gameJoined.data.gameId)
    yield put(actionNetworkedJoinGameSuccess(gameJoined.data,task))

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



function* JoinQueue(action){
  try{
    const token = yield select((state) => {
      try{
        return state.user.auth.token;
      }catch(e){
        return undefined;
      }
    })
    if(!token){
      yield put(actionJoinMatchQueueFail("must login"))
      yield put(actionUpdateModalInfo({
        content: "Please login to play "+ action.gameType+ " game..",
        show: true,
        title: "Login required",
        action: actionRedirectLogin(true)
    }))
    }

    const response = yield call(QueueApi.post, action.gameType, token);
    
    const task = yield fork(queueLoop,response.data.id)
    yield put(actionJoinMatchQueueSuccess(response.data, task, action.gameType))

  }catch(e){
    yield put(actionJoinMatchQueueFail(e.message))
  }
}

function* queueLoop(queueEntryId){

  const token = yield select((state) => state.user.auth.token)

  const channel = yield call(queueTimer, queueEntryId, token)

  while(true){
    try{
     
      let obj = yield take(channel)
      const {assignedGame, playerType} = obj
      console.log(obj);
      if(assignedGame && playerType){
        yield put(actionMatchGameStart(assignedGame.id, playerType))
        channel.close()
        break;
      }      
      yield put(actionQueueTimerLoop())
    }catch(e){

    }finally{
      if (yield cancelled()) {
        channel.close()
      } 
    }
  }

}

function queueTimer(queueEntryId, token){
  return eventChannel(emitter =>{
    const iv = setInterval(()=>{
      try{
        QueueApi.get(queueEntryId, token).then((r) =>{
          emitter(r.data)
        }).catch(e=>{
          emitter(END);
        })

      }catch(e){
        emitter(END);
      }
    }, 1000)
    return ()=>{
      clearInterval(iv);
    }
  })
}

function* PromotePawn(action){
  try {
    
    const promoPiece = yield select((state) => state.game.promoSelected) 
    const currentBoardState = yield select((state) => seriliaseState(state.game))
    const to = yield select((state) => state.game.lastMovePair[1])
    const gameType = yield select((state) => state.game.gameType)

    let response = undefined;
    if(gameType === GAME_TYPE.LOCAL_GAME){
      response = yield call(MoveApi.postPromotion, currentBoardState, to, promoPiece)
    }else{
      const id = yield select((state) => {
        return state.game.lobby.gameId
      })
      //check player ==> current turn
      const playerTypeCheck = yield select((state) => {
        return {
          result: state.game.currentTurn === state.game.lobby.playerType,
          playerType: state.game.currentTurn
        }
      })
      console.log(playerTypeCheck)

    //TODO: a bug with engine, promotion has not done yet, at this line, current player should not change!!!!!!!
      // if(playerTypeCheck.result === true){
        response = yield call(NetworkedGameApi.promotionGame, id, playerTypeCheck.playerType,currentBoardState, to, promoPiece)
      // }
    }

    if(!response){
      console.log("promotion failed.")
    }
    const stateObj = response.data;

    yield put(actionUpdateGameStateSuccess({...response.data, state: deserializeState(stateObj.state)}))
    yield put(actionAddMoveHistory({piece: promoPiece, from: to , to: to}))

    if (stateObj.isChecked && !stateObj.isCheckmate){
       yield put(actionUpdateModalInfo({
        content: '',
        show: true,
        title: 'Check',
        action: actionToggleModal(false)
      }))
    }
  } catch (e) {
    console.log("error", e)
  }
}
