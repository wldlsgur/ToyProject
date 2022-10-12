const createRoom = document.querySelector(".create-room");
const menuBarTag = document.querySelector(".menubar");
const userId = document.querySelector("#user_id");
const headerTag = document.querySelector(".header__title");

const commentId = document.querySelector(".contentId");
const commentUserImg = document.querySelector(".userInfo__img");
const commentDate: Element | null =
  document.querySelector(".commentInfo__date");
const commentParagraph: Element | null = document.querySelector(
  ".commentInfo__content"
);
const commnetUserName: Element | null =
  document.querySelector(".userInfo__name");

const myRoom = document.querySelector(".modal-myroom");
const myRoomId = myRoom?.querySelector(".room_id");
const myRoomCheif = myRoom?.querySelector(".room_cheif");
const myRoomTitle = myRoom?.querySelector(".title");
const myRoomPw = myRoom?.querySelector(".room-pw");
const myRoomPersonnel = myRoom?.querySelector(".personnel");
const myRoomDeleteBtnTag = document.querySelector(".room-btn__delete");

const room = document.querySelector(".modal-room");
const roomId: HTMLInputElement | null | undefined =
  room?.querySelector(".room_id");
const roomTitle: HTMLInputElement | null | undefined =
  room?.querySelector(".title");
const roomPw: HTMLInputElement | null | undefined =
  room?.querySelector(".room-pw");
const roomPersonnel: HTMLInputElement | null | undefined =
  room?.querySelector(".personnel");

const InputComment = document.querySelector(".modalComment");
const commentInfo = document.querySelector(".modalCommentInfo");
const commentInfoDelBtn = document.querySelector(
  ".modalCommentInfo .commentForm__btn--submit"
);
class Modal {
  constructor() {}
  CreateRoomShow() {
    if (createRoom instanceof HTMLElement) {
      createRoom.style.display = "block";
    }
  }
  CreateRoomHidden() {
    if (createRoom instanceof HTMLElement) {
      createRoom.style.display = "none";
    }
  }
  MyRoomInfoDelBtnShow() {
    if (myRoomDeleteBtnTag instanceof HTMLElement) {
      myRoomDeleteBtnTag.style.display = "block";
    }
  }
  MyRoomInfoDelBtnHidden() {
    if (myRoomDeleteBtnTag instanceof HTMLElement) {
      myRoomDeleteBtnTag.style.display = "none";
    }
  }
  MyRoomInfoShow() {
    if (myRoom instanceof HTMLElement) {
      myRoom.style.display = "block";
      if (
        myRoomCheif instanceof HTMLInputElement &&
        myRoomCheif.value === "1"
      ) {
        return this.MyRoomInfoDelBtnShow();
      }
      this.MyRoomInfoDelBtnHidden();
    }
  }
  MyRoomInfoHidden() {
    if (myRoom instanceof HTMLElement) {
      myRoom.style.display = "none";
    }
  }
  MyRoomSetInfo(e: any) {
    if (
      myRoomId instanceof HTMLInputElement &&
      myRoomCheif instanceof HTMLInputElement &&
      myRoomTitle instanceof HTMLElement &&
      myRoomPw instanceof HTMLInputElement &&
      myRoomPersonnel instanceof HTMLElement
    ) {
      myRoomId.value = e?.target?.previousSibling?.previousSibling?.value;
      myRoomCheif.value = e?.target?.previousSibling?.value;
      myRoomTitle.innerHTML = e?.target?.innerHTML;
      myRoomPw.value = "";
      myRoomPersonnel.innerHTML = e?.target?.nextSibling?.innerHTML;
    }
  }

  RoomHidden() {
    if (room instanceof HTMLElement) {
      room.style.display = "none";
    }
  }
  RoomShow() {
    if (room instanceof HTMLElement) {
      room.style.display = "block";
    }
  }
  RoomSetInfo(e: any) {
    if (
      roomId instanceof HTMLInputElement &&
      roomTitle instanceof HTMLElement &&
      roomPw instanceof HTMLInputElement &&
      roomPersonnel instanceof HTMLElement
    ) {
      roomId.value = e.target.previousSibling.value;
      roomTitle.innerHTML = e.target.innerHTML;
      roomPw.value = "";
      roomPersonnel.innerHTML = e.target.nextSibling.innerHTML;
    }
  }
  MenuBarShow() {
    if (menuBarTag instanceof HTMLElement) {
      menuBarTag.style.display = "block";
    }
  }
  MenuBarHidden() {
    if (menuBarTag instanceof HTMLElement) {
      menuBarTag.style.display = "none";
    }
  }

  InputCommentShow() {
    if (InputComment instanceof HTMLElement) {
      InputComment.style.display = "block";
    }
  }
  InputCommentHidden() {
    if (InputComment instanceof HTMLElement) {
      InputComment.style.display = "none";
    }
  }
  CommnetInfoShow() {
    if (commentInfo instanceof HTMLElement) {
      commentInfo.style.display = "block";
    }
  }
  CommentInfoHidden() {
    if (commentInfo instanceof HTMLElement) {
      commentInfo.style.display = "none";
    }
  }
  CommentSetInfo(e: any) {
    let imgTag = e.target.parentNode.querySelector(".contentInfo__img");
    let nameTag = e.target.parentNode.querySelector(".contentInfo__name");
    let contentIdTag = e.target.parentNode.querySelector(
      ".contentInfo__contentId"
    );
    let textTag = e.target.parentNode.querySelector(".contentInfo__content");
    let dayTag = e.target.parentNode.parentNode?.querySelector(".day");
    let date;
    if (dayTag instanceof HTMLElement) {
      date = headerTag?.innerHTML + dayTag.innerHTML + "일";
    }
    if (
      commentDate instanceof Element &&
      date &&
      commentParagraph instanceof Element &&
      commnetUserName instanceof Element
    ) {
      commentId?.setAttribute("value", contentIdTag.value);
      commentUserImg?.setAttribute("src", imgTag?.src);
      commentDate.innerHTML = date;
      commentParagraph.innerHTML = textTag?.value;
      commnetUserName.innerHTML = nameTag?.innerHTML;
    }
  }
  CommentInfoDelBtnShow() {
    if (commentInfoDelBtn instanceof HTMLElement) {
      commentInfoDelBtn.style.display = "block";
    }
  }
  CommentInfoDelBtnHidden() {
    if (commentInfoDelBtn instanceof HTMLElement) {
      commentInfoDelBtn.style.display = "none";
    }
  }
}

export default Modal;
