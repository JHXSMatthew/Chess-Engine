import { call, put, takeEvery, select } from 'redux-saga/effects'

import { 
  MOVE_REQUEST, 
  actionUpdateStateSuccess, 
  actionMoveFail } from './ChessGameReducer'

import Api from './ChessGameEAPI'


export function* gameSaga(){
  yield takeEvery(MOVE_REQUEST, MoveRequest)
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