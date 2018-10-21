import { take , cancel, fork, call, put, takeEvery, select, cancelled } from 'redux-saga/effects'
import { TYPE_USER_LOGIN, actionUserLoginFail, actionLoadGameMoveHistorySuccess, actionUserLoginSuccess, TYPE_USER_REGISTER, actionUserRegisterSuccess, actionUserLogin, actionUserRegisterFail, TYPE_GET_USER_INFO, actionGetUserInfo, actionGetUserInfoSuccess, actionGetuserInfoFail, TYPE_LOAD_CACHE_LOGIN, actionLoadUserGameHistorySuccess, TYPE_LOAD_MOVE_HISTORY_FOR_GAME, actionLoadGameMoveHistory, TYPE_LOAD_USER_GAME_HISTORY } from './UserReducer';
import { UserApi, AuthApi } from './UserEAPI';
import { gameHistoryApi } from '../Game/ChessGameEAPI';






export function* userSaga(){
  yield takeEvery(TYPE_USER_LOGIN, login)
  yield takeEvery(TYPE_USER_REGISTER, register)

  yield takeEvery(TYPE_GET_USER_INFO, getUserInfo)

  yield takeEvery(TYPE_LOAD_CACHE_LOGIN, loadCacheLogin)

  yield takeEvery(TYPE_LOAD_USER_GAME_HISTORY, getGameHistory)
  yield takeEvery(TYPE_LOAD_MOVE_HISTORY_FOR_GAME, getMoveHistory)
}

function* login(action){
  try{
    const {userName, password} = action.model;
    const response = yield call(AuthApi.post, userName, password);

    yield put(actionUserLoginSuccess(response.data))
    yield put(actionGetUserInfo(response.data))
    if(action.model.keepLogin){
      localStorage.setItem('auth', JSON.stringify(response.data));
    }

  }catch(e){
    yield put(actionUserLoginFail(e))
  }
}

function* getGameHistory(action){
  try{
    const userToken = yield select((state) => state.user.auth.token)
    const response= call(gameHistoryApi.getHistory, userToken)
    
    yield put(actionLoadUserGameHistorySuccess(response.data))
  }catch(err){
    yield put(actionUserLogin(err)) //todo change this
  }
}

function* getMoveHistory(action){
  try{
    const response= call(gameHistoryApi.getMoveHistory, action.gameId)
    
    yield put(actionLoadGameMoveHistorySuccess(action.gameId,response.data))
  }catch(err){
    yield put(actionUserLogin(err)) //todo change this
  }
}

function* register(action){
  try{
    const {userName, password, email} = action.model;
    const response = yield call(UserApi.post, userName, password, email);
    
    if(response.data){

    }
    yield put(actionUserRegisterSuccess())
    yield put(actionUserLogin({
      userName,
      password
    }))
  }catch(e){
    yield put(actionUserRegisterFail(e))
  }
}

function* getUserInfo(action){
  try{
    const {user, token} = action

    const { userId } = user;

    const response = yield call(UserApi.get, userId, token)
    
    yield put(actionGetUserInfoSuccess(response.data))
    
  }catch(e){
    yield put(actionGetuserInfoFail(e))
  }
}


function* loadCacheLogin(action){
  const auth = localStorage.getItem('auth')
  let val = null;
  try{
    val = JSON.parse(auth);
    if(val){
      yield put(actionUserLoginSuccess(val))
      yield put(actionGetUserInfo(val))
    }
  }catch(e){

  }
}

