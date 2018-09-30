const BACKDROP_TYPE = {
  static: 'static',
  dynamic: 'd'
}

const initState = {
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

