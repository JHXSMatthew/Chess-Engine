import { call, put, takeEvery, select } from 'redux-saga/effects'

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
  actionUpdateGameStateFail
} from './ChessGameReducer'

import Api from './ChessGameEAPI'

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
}


function* MoveRequest(action){
  try{
    const currentBoardState = yield select((state) => seriliaseState(state.game))
    const response = yield call(Api.postMove, currentBoardState ,action.from, action.to)
    const newState = response.data.state;

    if(newState === currentBoardState){
      yield put(actionUpdateGameStateFail("Illegal move."))
    }else{
      yield put(actionUpdateGameStateSuccess(deserializeState(newState)))
      yield put(actionHighlightLastMove([action.from, action.to]))
    }
  }catch(e){
    yield put(actionMoveFail(e.message))
  }
}

function* LoadLocalSavedGame(action){
  try{
    let game = localStorage.getItem(action.index);
    let lastMoveStr = localStorage.getItem(action.lastMove);
    let lastMove = JSON.parse("[" + lastMoveStr + "]");
    if(game){
      yield put(actionUpdateGameStateSuccess(deserializeState(game)))
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
    const currentBoardState = yield select((state) => state.game.boardStr )
    const lastMovePair = yield select((state) => state.game.lastMovePair)
    localStorage.setItem(action.index, currentBoardState);
    localStorage.setItem(action.lastMove, lastMovePair);
    yield put(actionSaveGameSuccess());
  }catch(e){
    yield put(actionSaveGameFail(e.message));
  }
}

function* AvailableMoveRequest(action){
  try{
    const currentBoardState = yield select((state) => seriliaseState(state.game))
    const availableMoves = yield call(Api.postAvaliableMove, currentBoardState, action.from, 0)
    // yield put(actionHighlightAvailable(availableMoves.data.available))
    yield put(actionHighlightAvailable([]))

  }catch(e){
    console.log("AvailableMoveRequest Api Error: ", e);
  }
}