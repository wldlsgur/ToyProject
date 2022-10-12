import Login from "./login.js";
import Nav from "../Common/nav.js";
import css from "../../stylesheets/index/index.css";
const temp = css;

const nav = new Nav();
const login = new Login();

document
  .querySelector(".login-form__singUp")
  ?.addEventListener("click", nav.MovePageSignup);
document
  .querySelector(".login-form__login")
  ?.addEventListener("click", login.DoLoginCheck);
