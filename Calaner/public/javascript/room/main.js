var _a, _b, _c, _d, _e, _f, _g, _h, _j, _k;
import Modal from "../Common/modal.js";
import Nav from "../Common/nav.js";
import ImageController from "../Common/image.js";
import RoomController from "./room.js";
import css from "../../stylesheets/room/room.css";
const temp = css;
const imageController = new ImageController();
const nav = new Nav();
const modal = new Modal();
const roomController = new RoomController();
const menuBarTag = document.querySelector(".menubar");
const userImage = document.querySelector("#userImage");
const userIamgeTag = document.querySelector(".user-info__image");
window.onload = () => {
    if ((userImage === null || userImage === void 0 ? void 0 : userImage.value) && userIamgeTag instanceof HTMLImageElement) {
        imageController.ShowUserImage(userIamgeTag, userImage === null || userImage === void 0 ? void 0 : userImage.value);
    }
    roomController.GetAllMyRoomList();
    roomController.GetAllRoomList();
};
(_a = document
    .querySelector(".header__add")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", modal.CreateRoomShow);
(_b = document
    .querySelector(".create-form__exit")) === null || _b === void 0 ? void 0 : _b.addEventListener("click", modal.CreateRoomHidden);
(_c = document
    .querySelector(".create-form__create")) === null || _c === void 0 ? void 0 : _c.addEventListener("click", roomController.Post);
(_d = document
    .querySelector(".modal-myroom .room-btn__exit")) === null || _d === void 0 ? void 0 : _d.addEventListener("click", modal.MyRoomInfoHidden);
(_e = document
    .querySelector(".modal-myroom .room-btn__delete")) === null || _e === void 0 ? void 0 : _e.addEventListener("click", roomController.Delete);
(_f = document
    .querySelector(".modal-myroom .room-btn__join")) === null || _f === void 0 ? void 0 : _f.addEventListener("click", roomController.MyRoomJoin);
(_g = document
    .querySelector(".modal-room .room-btn__exit")) === null || _g === void 0 ? void 0 : _g.addEventListener("click", modal.RoomHidden);
(_h = document
    .querySelector(".modal-room .room-btn__join")) === null || _h === void 0 ? void 0 : _h.addEventListener("click", roomController.RoomJoin);
(_j = document.querySelector(".header__menu")) === null || _j === void 0 ? void 0 : _j.addEventListener("click", () => {
    if (menuBarTag instanceof HTMLElement) {
        if (menuBarTag.style.display === "block") {
            return modal.MenuBarHidden();
        }
        modal.MenuBarShow();
    }
});
(_k = document
    .querySelector(".menulist__logout")) === null || _k === void 0 ? void 0 : _k.addEventListener("click", nav.MovePageLogin);
