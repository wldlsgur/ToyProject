import Modal from "../Common/modal.js";
import Nav from "../Common/nav.js";
import ImageController from "../Common/image.js";
import RoomController from "./room.js";
import css from "../../stylesheets/room/room.css";
const temp = css;

const imageController: ImageController = new ImageController();
const nav: Nav = new Nav();
const modal: Modal = new Modal();
const roomController: RoomController = new RoomController();
const menuBarTag = document.querySelector(".menubar");
const userImage: HTMLInputElement | null = document.querySelector("#userImage");
const userIamgeTag: HTMLImageElement | null =
  document.querySelector(".user-info__image");
window.onload = () => {
  if (userImage?.value && userIamgeTag instanceof HTMLImageElement) {
    imageController.ShowUserImage(userIamgeTag, userImage?.value);
  }
  roomController.GetAllMyRoomList();
  roomController.GetAllRoomList();
};
document
  .querySelector(".header__add")
  ?.addEventListener("click", modal.CreateRoomShow);
document
  .querySelector(".create-form__exit")
  ?.addEventListener("click", modal.CreateRoomHidden);
document
  .querySelector(".create-form__create")
  ?.addEventListener("click", roomController.Post);
document
  .querySelector(".modal-myroom .room-btn__exit")
  ?.addEventListener("click", modal.MyRoomInfoHidden);
document
  .querySelector(".modal-myroom .room-btn__delete")
  ?.addEventListener("click", roomController.Delete);
document
  .querySelector(".modal-myroom .room-btn__join")
  ?.addEventListener("click", roomController.MyRoomJoin);

document
  .querySelector(".modal-room .room-btn__exit")
  ?.addEventListener("click", modal.RoomHidden);
document
  .querySelector(".modal-room .room-btn__join")
  ?.addEventListener("click", roomController.RoomJoin);
document.querySelector(".header__menu")?.addEventListener("click", () => {
  if (menuBarTag instanceof HTMLElement) {
    if (menuBarTag.style.display === "block") {
      return modal.MenuBarHidden();
    }
    modal.MenuBarShow();
  }
});
document
  .querySelector(".menulist__logout")
  ?.addEventListener("click", nav.MovePageLogin);
