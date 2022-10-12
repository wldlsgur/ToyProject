var _a, _b, _c, _d, _e, _f, _g, _h, _j, _k, _l, _m;
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
let today = new Date();
const imageController = new ImageController();
const nav = new Nav();
const modal = new Modal();
const commentController = new CommentController();
const msgController = new MsgController(socket);
const personnelcontroller = new PersonnelController();
const calanderController = new CalanderController();
const menuBarTag = document.querySelector(".menubar");
const userImage = document.querySelector("#userImage");
const userIamgeTag = document.querySelector(".user-info__image");
window.onload = () => {
    if ((userImage === null || userImage === void 0 ? void 0 : userImage.value) && userIamgeTag instanceof HTMLImageElement) {
        imageController.ShowUserImage(userIamgeTag, userImage === null || userImage === void 0 ? void 0 : userImage.value);
    }
    CalanderViewSet();
    personnelcontroller.Get().then((result) => {
        if (result) {
            personnelcontroller.SetPersonnelCalander(result);
        }
        msgController.SocketJoin();
    });
};
window.addEventListener("beforeunload", msgController.SocketLeave);
(_a = document.querySelector(".header__menu")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", () => {
    if (menuBarTag instanceof HTMLElement) {
        if (menuBarTag.style.display === "block") {
            return modal.MenuBarHidden();
        }
        modal.MenuBarShow();
    }
});
(_b = document.querySelector(".menulist__logout")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", () => {
    msgController.SocketLeave();
    nav.MovePageLogin();
});
(_c = document.querySelector(".menulist__room")) === null || _c === void 0 ? void 0 : _c.addEventListener("click", () => {
    msgController.SocketLeave();
    nav.MovePageRoom();
});
(_d = document
    .querySelector(".header__add")) === null || _d === void 0 ? void 0 : _d.addEventListener("click", modal.InputCommentShow);
(_e = document
    .querySelector(".commentForm__btn--exit")) === null || _e === void 0 ? void 0 : _e.addEventListener("click", modal.InputCommentHidden);
(_f = document
    .querySelector(".commentForm__btn--submit")) === null || _f === void 0 ? void 0 : _f.addEventListener("click", commentController.Post);
(_g = document
    .querySelector(".modalCommentInfo .commentForm__btn--exit")) === null || _g === void 0 ? void 0 : _g.addEventListener("click", modal.CommentInfoHidden);
(_h = document
    .querySelector(".modalCommentInfo .commentForm__btn--submit")) === null || _h === void 0 ? void 0 : _h.addEventListener("click", commentController.Delete);
(_j = document.querySelector(".bi-caret-left")) === null || _j === void 0 ? void 0 : _j.addEventListener("click", () => {
    today = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
    CalanderViewSet();
});
(_k = document.querySelector(".bi-caret-right")) === null || _k === void 0 ? void 0 : _k.addEventListener("click", () => {
    today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
    CalanderViewSet();
});
(_l = document.querySelector(".chatWrite")) === null || _l === void 0 ? void 0 : _l.addEventListener("submit", (e) => {
    msgController.PostMsgSocket(e);
});
(_m = document
    .querySelector(".chatWrite__input")) === null || _m === void 0 ? void 0 : _m.addEventListener("keyup", (e) => {
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
