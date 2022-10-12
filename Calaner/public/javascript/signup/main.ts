import { Image, SignUP } from "./signUp.js";
import css from "../../stylesheets/signup/signup.css";
const temp = css;
const image = new Image();
const signUp = new SignUP();

document.querySelector("#image")?.addEventListener("change", image.ShowImage);
document
  .querySelector("#sameId__sameIdCb")
  ?.addEventListener("click", signUp.SameIdCheck);
document
  .querySelector(".form-input__id")
  ?.addEventListener("keyup", signUp.InitCheckBox);
document
  .querySelector(".form-input__submit")
  ?.addEventListener("click", signUp.doSignUp);
