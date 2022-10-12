var _a, _b;
import Login from "./login.js";
import Nav from "../Common/nav.js";
import css from "../../stylesheets/index/index.css";
const temp = css;
const nav = new Nav();
const login = new Login();
(_a = document
    .querySelector(".login-form__singUp")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", nav.MovePageSignup);
(_b = document
    .querySelector(".login-form__login")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", login.DoLoginCheck);
