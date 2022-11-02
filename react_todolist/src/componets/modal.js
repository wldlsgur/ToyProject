import "../styles/HOME/modal.css";
import React, { useRef } from "react";

const ModalComponent = (props) => {
  let { text, closeModal, toDoList, setToDoList, id } = props;
  const inputRef = useRef();

  const updateToDoList = () => {
    if (!inputRef.current.value) {
      return alert("수정 사항 입력해주세요!");
    }
    let copy = [...toDoList];

    copy.forEach((element) => {
      if (element.id === id) {
        element.text = inputRef.current.value;
      }
    });
    setToDoList(copy);
    closeModal();
  };

  return (
    <div className="Modal">
      <p>{text}</p>
      <input ref={inputRef} type="text" placeholder="수정 입력..." />
      <div className="Modal__Btn">
        <button onClick={updateToDoList}>수정</button>
        <button onClick={closeModal}>취소</button>
      </div>
    </div>
  );
};

export default ModalComponent;
