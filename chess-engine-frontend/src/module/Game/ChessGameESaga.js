import { call, put, takeEvery, select } from 'redux-saga/effects'

import { 
  MOVE_REQUEST, 
  AVAILABLE_MOVE_REQUEST,
  actionUpdateStateSuccess, 
  actionMoveFail,
  actionHighlightAvailable,
  actionLoadSavedGameFail,
  LOAD_LOCAL_SAVED_GAME,
  actionSaveGameSuccess,
  actionSaveGameFail,
  SAVE_LOCAL_GAME
} from './ChessGameReducer'

import Api from './ChessGameEAPI'


export function* gameSaga(){
  yield takeEvery(MOVE_REQUEST, MoveRequest);
  yield takeEvery(AVAILABLE_MOVE_REQUEST, AvailableMoveRequest);
  //save load
  yield takeEvery(LOAD_LOCAL_SAVED_GAME, LoadLocalSavedGame)
  yield takeEvery(SAVE_LOCAL_GAME, SaveLocalGame)
}


function* MoveRequest(action){
  try{
    const currentBoardState = yield select((state) => state.game.boardStr )
    const newState = yield call(Api.postMove, currentBoardState ,action.from, action.to)
    yield put(actionUpdateStateSuccess(newState.data.state))
  }catch(e){
    yield put(actionMoveFail(e.message))
  }
}

function* LoadLocalSavedGame(action){
  try{
    let game = localStorage.getItem(action.index);
    if(game){
      yield put(actionUpdateStateSuccess(game))
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

    localStorage.setItem(action.index, currentBoardState);
    yield put(actionSaveGameSuccess());
  }catch(e){
    yield put(actionSaveGameFail(e.message));
  }
}

function* AvailableMoveRequest(action){
  try{
    const currentBoardState = yield select((state) => state.game.boardStr )
    const availableMoves = yield call(Api.postAvaliableMove, currentBoardState, action.from, 0)
    yield put(actionHighlightAvailable(availableMoves.data.available))
  }catch(e){
    console.log("AvailableMoveRequest Api Error: ", e);
  }
}