import React from 'react'

import { connect } from 'react-redux'

import { Modal, ModalHeader, ModalBody, ModalFooter, Button, ButtonGroup } from 'reactstrap';

import { actionSelectPromotion } from '../module/Game/ChessGameReducer'

import UUID from  'uuid/v1';

import { PiecesSVG } from '../resource/PieceResource';


class ModalWrapper extends React.Component{

  render(){
    const {backdrop, show, title, content, action, hideSelf, buttonAction } = this.props;

    if (content === 'p') { 
      var pieceColour = ['q', 'n', 'r', 'b'];
    } else {
      var pieceColour = ['Q', 'N', 'R', 'B'];
    }
    var promoSelect = [];
    for (let i in pieceColour){
      promoSelect.push(
        <Button outline colour="primary" value={pieceColour[i]} key={UUID()} className="w-25"
          onClick={() => this.props.selectPromotion(pieceColour[i])} active={this.props.promoSelected === pieceColour[i]}
          >
          <div>
            {PiecesSVG[pieceColour[i]]}
          </div>
        </Button>
      );
    }
    return (
      <Modal isOpen={show} backdrop={backdrop}>
          <ModalHeader >{title}</ModalHeader>
          <ModalBody>
            {title === "Promotion" ? 
            <ButtonGroup type="radio" name="options" className="d-flex flex-row">
            {promoSelect}
            </ButtonGroup>
            : content}
          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={()=>{ 
              if (title === "Promotion"){
                if (!this.props.promoSelected){
                  alert("Please select a piece to promote your pawn to.");
                } else {
                  buttonAction(action)
                  hideSelf()
                }
              } else {
                if(action){
                  buttonAction(action)
                }
                hideSelf()
              }
            }}>confirm</Button>
          </ModalFooter>
        </Modal>
    )
  }
}

const mapStateToProps = (state) => {
  return {
    ...state.game,
    promoSelected: state.game.promoSelected
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
      selectPromotion: (selected) => dispatch(actionSelectPromotion(selected))
  };
};

export default connect(mapStateToProps, mapDispatchToProps) (ModalWrapper);