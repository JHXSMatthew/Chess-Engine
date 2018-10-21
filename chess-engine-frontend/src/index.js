import React from 'react';
import ReactDOM from 'react-dom';

import App from './App';
import registerServiceWorker from './registerServiceWorker';


//redux
import { Provider } from 'react-redux'
import { applyMiddleware, createStore, combineReducers} from 'redux'

//middlewares
import logger from 'redux-logger'
import createSaga from 'redux-saga'
import { fork } from 'redux-saga/effects'

//reducers
import {gameReducer} from './module/Game/ChessGameReducer'
import { appReducer } from './AppReducer'
import { userReducer } from './module/User/UserReducer';

//sagas
import { gameSaga} from './module/Game/ChessGameESaga'
import { userSaga } from './module/User/UserESaga';


//mock
import { Server } from 'react-mock'
import { init } from './mock/mock'

//router
import { BrowserRouter } from 'react-router-dom';

//configs
import { DEBUG } from './config'

//css
import 'bootstrap/dist/css/bootstrap.min.css';


const saga = createSaga(); 



const store = createStore(
  combineReducers({
    game: gameReducer,
    app: appReducer,
    user: userReducer
  }),
  applyMiddleware(logger, saga)
);


function* rootSaga() {
  yield [
    fork(gameSaga),
    fork(userSaga)
  ]
}

saga.run(rootSaga);

ReactDOM.render(
<Provider store={store}>
    <App />
</Provider>, document.getElementById('root'));


registerServiceWorker();
if(DEBUG){
  init(Server);
  Server.on();
}

