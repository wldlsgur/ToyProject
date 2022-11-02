import "../styles/HOME/toDoItem.css";
import ModalComponent from "../componets/modal";
import Modal from "react-modal";
import { useState } from "react";

const ToDoItemComponent = (props) => {
  const modalOpen = useState(false);

  const openModal = () => {
    modalOpen[1](true);
  };
  const closeModal = () => {
    modalOpen[1](false);
  };

  const deleteToDolist = (key) => {
    let copy = [...props.toDoList];

    copy.forEach((element, index) => {
      if (element.id === key) {
        return copy.splice(index, 1);
      }
    });
    props.setToDoList(copy);
  };

  const checkToDoList = (e) => {
    if (e.target.checked) {
      e.target.parentNode.querySelector(
        ".toDOItem__CheckAndText__text"
      ).style.textDecorationLine = "line-through";
      e.target.parentNode.querySelector(
        ".toDOItem__CheckAndText__text"
      ).style.color = "gray";
    } else {
      e.target.parentNode.querySelector(
        ".toDOItem__CheckAndText__text"
      ).style.textDecorationLine = "none";
      e.target.parentNode.querySelector(
        ".toDOItem__CheckAndText__text"
      ).style.color = "black";
    }
  };

  return (
    <div className="toDoItem">
      <div className="toDOItem__CheckAndText">
        <input
          type="checkbox"
          className="toDOItem__CheckAndText__Check"
          onChange={checkToDoList}
        />
        <p className="toDOItem__CheckAndText__text">{props.text}</p>
      </div>
      <div className="toDoItem__Btn">
        <button className="toDoItem__Btn__add" onClick={openModal}>
          수정
        </button>
        <button
          className="toDoItem__Btn__del"
          onClick={() => {
            deleteToDolist(props.id);
          }}
        >
          삭제
        </button>
        <Modal isOpen={modalOpen[0]} onRequestClose={closeModal}>
          <ModalComponent
            text={props.text}
            closeModal={closeModal}
            toDoList={props.toDoList}
            setToDoList={props.setToDoList}
            id={props.id}
          ></ModalComponent>
        </Modal>
      </div>
    </div>
  );
};

export default ToDoItemComponent;
