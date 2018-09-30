import React from 'react'

import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';


class ModalWrapper extends React.Component{

  render(){
    const {backdrop, show, title, content, action, hideSelf } = this.props;
    return (
      <Modal isOpen={show} backdrop={backdrop}>
          <ModalHeader >{title}</ModalHeader>
          <ModalBody>
            {content}
          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={()=>{ 
              if(action){
                action() 
              }
              hideSelf()
            }}>confirm</Button>
          </ModalFooter>
        </Modal>
    )
  }
}

export default ModalWrapper;