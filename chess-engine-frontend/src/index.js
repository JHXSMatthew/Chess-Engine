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
import { appReducer } from './AppReducer'
//sagas
import { gameSaga} from './module/Game/ChessGameESaga'

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
    app: appReducer
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
if(DEBUG){
  init(Server);
  Server.on();
}

