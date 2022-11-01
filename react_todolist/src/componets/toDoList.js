import "../styles/HOME/toDoList.css";
import ToDoItemComponent from "./toDoItem";

const ToDoListComponent = (props) => {
  return (
    <div className="toDoList">
      {props.toDoList.map((value, index) => {
        return (
          <ToDoItemComponent
            toDoList={props.toDoList}
            setToDoList={props.setToDoList}
            key={value.id}
            id={value.id}
            text={value.text}
          ></ToDoItemComponent>
        );
      })}
    </div>
  );
};

export default ToDoListComponent;
