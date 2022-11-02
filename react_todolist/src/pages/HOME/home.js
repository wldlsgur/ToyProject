import HeaderComponent from "../../componets/header";
import InputComponent from "../../componets/input";
import ToDoListComponent from "../../componets/toDoList";
import "../../styles/HOME/home.css";
import { useState } from "react";

let keys = [0];
const Home = (props) => {
  const [toDoList, setToDoList] = useState([]);

  return (
    <div className="homePage">
      <HeaderComponent></HeaderComponent>

      <InputComponent
        toDoList={toDoList}
        setToDoList={setToDoList}
        id={keys}
      ></InputComponent>

      <ToDoListComponent
        toDoList={toDoList}
        setToDoList={setToDoList}
      ></ToDoListComponent>
    </div>
  );
};

export default Home;
