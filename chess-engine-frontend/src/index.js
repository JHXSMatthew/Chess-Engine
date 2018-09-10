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

//reducers
import {gameReducer} from './module/Game/ChessGameReducer'
//sagas
import { gameSaga} from './module/Game/ChessGameESaga'

//mock
import { Server } from 'react-mock'
import { init } from './mock/mock'

//router
import { BrowserRouter } from 'react-router-dom';


const saga = createSaga(); 


const store = createStore(
  combineReducers({
    game: gameReducer
  }),
  applyMiddleware(logger, saga)
);

saga.run(gameSaga);

ReactDOM.render(
<Provider store={store}>
  <BrowserRouter>
    <App />
  </BrowserRouter>
</Provider>, document.getElementById('root'));


registerServiceWorker();

init(Server);
Server.on();
