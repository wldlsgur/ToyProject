import Nav from "../Common/nav.js";
import Modal from "../Common/modal.js";
import CommentController from "./comment.js";
import CalanderController from "./calander.js";
import PersonnelController from "./personnel.js";
import MsgController from "./msg.js";
import ImageController from "../Common/image.js";
import css from "../../stylesheets/calander/calander.css";
const temp = css;

const socket = io();
let today: Date = new Date();

const imageController: ImageController = new ImageController();
const nav: Nav = new Nav();
const modal: Modal = new Modal();
const commentController: CommentController = new CommentController();
const msgController: MsgController = new MsgController(socket);
const personnelcontroller: PersonnelController = new PersonnelController();
const calanderController: CalanderController = new CalanderController();
const menuBarTag = document.querySelector(".menubar");
const userImage: HTMLInputElement | null = document.querySelector("#userImage");
const userIamgeTag: HTMLImageElement | null =
  document.querySelector(".user-info__image");

window.onload = () => {
  if (userImage?.value && userIamgeTag instanceof HTMLImageElement) {
    imageController.ShowUserImage(userIamgeTag, userImage?.value);
  }
  CalanderViewSet();
  personnelcontroller.Get().then((result: object) => {
    if (result) {
      personnelcontroller.SetPersonnelCalander(result);
    }
    msgController.SocketJoin();
  });
};
window.addEventListener("beforeunload", msgController.SocketLeave);
document.querySelector(".header__menu")?.addEventListener("click", () => {
  if (menuBarTag instanceof HTMLElement) {
    if (menuBarTag.style.display === "block") {
      return modal.MenuBarHidden();
    }
    modal.MenuBarShow();
  }
});
document.querySelector(".menulist__logout")?.addEventListener("click", () => {
  msgController.SocketLeave();
  nav.MovePageLogin();
});
document.querySelector(".menulist__room")?.addEventListener("click", () => {
  msgController.SocketLeave();
  nav.MovePageRoom();
});

document
  .querySelector(".header__add")
  ?.addEventListener("click", modal.InputCommentShow);
document
  .querySelector(".commentForm__btn--exit")
  ?.addEventListener("click", modal.InputCommentHidden);
document
  .querySelector(".commentForm__btn--submit")
  ?.addEventListener("click", commentController.Post);
document
  .querySelector(".modalCommentInfo .commentForm__btn--exit")
  ?.addEventListener("click", modal.CommentInfoHidden);
document
  .querySelector(".modalCommentInfo .commentForm__btn--submit")
  ?.addEventListener("click", commentController.Delete);
document.querySelector(".bi-caret-left")?.addEventListener("click", () => {
  today = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
  CalanderViewSet();
});
document.querySelector(".bi-caret-right")?.addEventListener("click", () => {
  today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
  CalanderViewSet();
});
document.querySelector(".chatWrite")?.addEventListener("submit", (e) => {
  msgController.PostMsgSocket(e);
});
document
  .querySelector(".chatWrite__input")
  ?.addEventListener("keyup", (e: any) => {
    if (e.keyCode === 13) {
      msgController.PostMsgSocket(e);
    }
  });
socket.on("joinRoom", msgController.ShowJoinUser);
socket.on("leaveRoom", msgController.ShowLeaveUser);
socket.on("chat-msg", msgController.ShowMsg);

function CalanderViewSet() {
  calanderController.SetCalanderDate(today);
  commentController.Get(today).then((result) => {
    if (result) {
      commentController.SetCommentCalander(result);
    }
  });
}
