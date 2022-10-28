import "../styles/HOME/input.css";

const InputComponent = (props) => {
  return (
    <div className="input">
      <input
        className="input__text"
        type="text"
        placeholder="할 일을 입력하세요"
      />
      <button className="input__addBtn">+</button>
    </div>
  );
};

export default InputComponent;
