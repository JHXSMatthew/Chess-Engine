import { call, put, takeEvery, select } from 'redux-saga/effects'

import { 
  MOVE_REQUEST, 
  AVAILABLE_MOVE_REQUEST,
  actionUpdateStateSuccess, 
  actionMoveFail,
  actionHighlightAvailable
} from './ChessGameReducer'

import Api from './ChessGameEAPI'


export function* gameSaga(){
  yield takeEvery(MOVE_REQUEST, MoveRequest);
  yield takeEvery(AVAILABLE_MOVE_REQUEST, AvailableMoveRequest);
}


function* MoveRequest(action){
  try{
    const currentBoardState = yield select((state) => state.game.boardStr )
    console.log(currentBoardState)
    const newState = yield call(Api.postMove, currentBoardState ,action.from, action.to)
    yield put(actionUpdateStateSuccess(newState.data.state))
  }catch(e){
    yield put(actionMoveFail(e.message))
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