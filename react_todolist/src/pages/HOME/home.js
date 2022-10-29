import HeaderComponent from "../../componets/header";
import InputComponent from "../../componets/input";
import ToDoListComponent from "../../componets/toDoList";
import "../../styles/HOME/home.css";

const Home = (props) => {
  return (
    <div className="homePage">
      <HeaderComponent></HeaderComponent>
      <InputComponent></InputComponent>
      <ToDoListComponent></ToDoListComponent>
    </div>
  );
};

export default Home;
