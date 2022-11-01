import "../styles/HOME/toDoItem.css";

const ToDoItemComponent = (props) => {
  const deleteToDolist = (key) => {
    let copy = [...props.toDoList];

    copy.forEach((element, index) => {
      if (element.id === key) {
        return copy.splice(index, 1);
      }
    });
    props.setToDoList(copy);
  };

  return (
    <div className="toDoItem">
      <div className="toDOItem__CheckAndText">
        <input type="checkbox" className="toDOItem__CheckAndText__Check" />
        <p className="toDOItem__CheckAndText__text">{props.text}</p>
      </div>
      <div className="toDoItem__Btn">
        <button className="toDoItem__Btn__add">수정</button>
        <button
          className="toDoItem__Btn__del"
          onClick={() => {
            deleteToDolist(props.id);
          }}
        >
          삭제
        </button>
      </div>
    </div>
  );
};

export default ToDoItemComponent;
