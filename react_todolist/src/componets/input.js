import "../styles/HOME/input.css";
import React, { useRef } from "react";

const InputComponent = (props) => {
  const inputRef = useRef(null);

  const addTodoList = (e) => {
    let copy = [...props.toDoList];
    copy.push(inputRef.current.value);
    props.setToDoList(copy);
  };

  return (
    <div className="input">
      <input
        ref={inputRef}
        className="input__text"
        type="text"
        placeholder="할 일을 입력하세요"
      />
      <button className="input__addBtn" onClick={addTodoList}>
        +
      </button>
    </div>
  );
};

export default InputComponent;
