import "../styles/HOME/toDoList.css";
import ToDoItemComponent from "./toDoItem";
const ToDoListComponent = (props) => {
  let keys = 0;

  return (
    <div className="toDoList">
      {props.toDoList.map((value) => {
        return (
          <ToDoItemComponent key={++keys} text={value}></ToDoItemComponent>
        );
      })}
    </div>
  );
};

export default ToDoListComponent;
