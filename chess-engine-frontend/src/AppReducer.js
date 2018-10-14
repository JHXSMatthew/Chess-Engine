const BACKDROP_TYPE = {
  static: 'static',
  dynamic: 'd'
}

const initState = {
  redirect: undefined,

  modal:{
    backdrop: BACKDROP_TYPE.static, 
    content: "",
    show: false,
    title: "",
    action: ()=>{}

  }
}

export const appReducer = (state = initState, action )=>{
  switch(action.type){
    case UPDATE_MODAL_INFO:
    case TOGGLE_MODAL:
      return Object.assign({}, state, {modal: modalReducer(state.modal, action)})
    case TYPE_REDIRECT_LOGIN:
      return Object.assign({}, state, {
        redirect: action.flag ? "/login": undefined
      })
    default:
      return state;
  }
}

export const modalReducer = (state = {}, action) =>{
  switch(action.type){
    case UPDATE_MODAL_INFO:
      return Object.assign({}, state, action)

    case TOGGLE_MODAL:
      return Object.assign({}, state, {show: action.show === undefined ? !state.show : action.show})
    default:
      return state;
  }
}


export const TYPE_REDIRECT_LOGIN = "TYPE_REDIRECT_LOGIN"
export const actionRedirectLogin = (flag) =>{
  return {
    type: TYPE_REDIRECT_LOGIN,
    flag
  }
}

const UPDATE_MODAL_INFO = "UPDATE_MODAL_INFO"
export const actionUpdateModalInfo = (modalObj) =>{
  return {
    ...modalObj,
    type: UPDATE_MODAL_INFO
  }
}

const TOGGLE_MODAL = "TOGGLE_MODAL"
export const actionToggleModal = (show)=>{
  return {
    type: TOGGLE_MODAL,
    show
  }
}

