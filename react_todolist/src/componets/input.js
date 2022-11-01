import "../styles/HOME/input.css";
import React, { useRef } from "react";

const InputComponent = (props) => {
  const inputRef = useRef(null);

  const addTodoList = () => {
    if (!inputRef.current.value) {
      return alert("no text!");
    }
    // if (props.toDoList.includes(inputRef.current.value)) {
    //   return alert("already text!");
    // }

    let copy = [...props.toDoList];
    copy.push({
      id: ++props.id[0],
      text: inputRef.current.value,
    });
    props.setToDoList(copy);
    inputRef.current.value = "";
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
