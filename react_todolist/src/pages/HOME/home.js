import HeaderComponent from "../../componets/header";
import InputComponent from "../../componets/input";
import "../../styles/HOME/home.css";

const Home = (props) => {
  return (
    <div className="homePage">
      <HeaderComponent></HeaderComponent>
      <InputComponent></InputComponent>
    </div>
  );
};

export default Home;
