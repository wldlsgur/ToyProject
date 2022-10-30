import "../styles/HOME/toDoItem.css";

const ToDoItemComponent = (props) => {
  return (
    <div className="toDoItem">
      <div className="toDOItem__CheckAndText">
        <input type="checkbox" className="toDOItem__CheckAndText__Check" />
        <p className="toDOItem__CheckAndText__text">{props.text}</p>
      </div>
      <div className="toDoItem__Btn">
        <button className="toDoItem__Btn__add">수정</button>
        <button className="toDoItem__Btn__add">삭제</button>
      </div>
    </div>
  );
};

export default ToDoItemComponent;
