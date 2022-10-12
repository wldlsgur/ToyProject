var _a, _b, _c, _d;
import { Image, SignUP } from "./signUp.js";
import css from "../../stylesheets/signup/signup.css";
const temp = css;
const image = new Image();
const signUp = new SignUP();
(_a = document.querySelector("#image")) === null || _a === void 0 ? void 0 : _a.addEventListener("change", image.ShowImage);
(_b = document
    .querySelector("#sameId__sameIdCb")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", signUp.SameIdCheck);
(_c = document
    .querySelector(".form-input__id")) === null || _c === void 0 ? void 0 : _c.addEventListener("keyup", signUp.InitCheckBox);
(_d = document
    .querySelector(".form-input__submit")) === null || _d === void 0 ? void 0 : _d.addEventListener("click", signUp.doSignUp);
