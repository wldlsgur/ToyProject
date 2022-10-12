import { Socket } from "socket.io";
import ServerController from "../Common/address.js";
const serverController: ServerController = new ServerController();
const server = serverController.GetServerAddress();
const userId: HTMLInputElement | null = document.querySelector("#user_id");
const roomId: HTMLInputElement | null = document.querySelector("#room_id");
const userName: HTMLInputElement | null = document.querySelector("#userName");
const userImg: HTMLImageElement | null =
  document.querySelector(".user-info > img");
const chatScroll = document.querySelector(".chatList__msg");
const inputMsgTag: HTMLInputElement | null =
  document.querySelector(".chatWrite__input");

class MsgController {
  socket;
  constructor(socket: Socket) {
    this.socket = socket;
  }
  SocketJoin(): void {
    this.socket.emit("joinRoom", {
      roomId: roomId?.value,
      userName: userName?.value,
    });
  }
  SocketLeave(): void {
    this.socket.emit("leaveRoom", {
      roomId: roomId?.value,
      userName: userName?.value,
    });
  }
  ShowJoinUser(data: any) {
    let root = document.querySelector(".chatList__msg");
    let joinmsg = document.createElement("div");
    joinmsg.setAttribute("class", "joinAndLeave");
    joinmsg.innerHTML = `${data.userName}님 입장`;
    root?.appendChild(joinmsg);
  }
  ShowLeaveUser(data: any) {
    let root = document.querySelector(".chatList__msg");
    let joinmsg = document.createElement("div");
    joinmsg.setAttribute("class", "joinAndLeave");
    joinmsg.innerHTML = `${data.userName}님 퇴장`;
    root?.appendChild(joinmsg);
  }
  ShowMsg(data: {
    userName: string;
    userId: any;
    imgSrc: string;
    msg: string;
  }) {
    let root = document.querySelector(".chatList__msg");
    let msg = document.createElement("div");
    if (data.userId === userId?.value) {
      msg.setAttribute("class", "mymsg");

      let content = document.createElement("p");
      content.setAttribute("class", "mymsg__content");
      content.innerHTML = data.msg;

      msg.appendChild(content);
    } else {
      msg.setAttribute("class", "msg");

      let div2 = document.createElement("div");
      div2.setAttribute("class", "msg__NameAndContent");

      let img = document.createElement("img");
      img.setAttribute("class", "msg__img");
      img.setAttribute("src", `${server}/image/user/` + data.imgSrc);

      let name = document.createElement("p");
      name.setAttribute("class", "msg__name");
      name.innerHTML = data.userName;

      let content = document.createElement("p");
      content.setAttribute("class", "msg__content");
      content.innerHTML = data.msg;

      div2.appendChild(img);
      div2.appendChild(name);

      msg.appendChild(div2);
      msg.appendChild(content);
    }
    root?.appendChild(msg);
    if (chatScroll instanceof HTMLElement) {
      chatScroll.scrollTop = chatScroll?.scrollHeight;
    }
  }
  PostMsgSocket(e: { preventDefault: () => void }) {
    e.preventDefault();
    if (!inputMsgTag?.value) {
      return alert("메세지를 입력해주세요");
    }
    this.socket.emit("chat-msg", {
      roomId: roomId?.value,
      userName: userName?.value,
      msg: inputMsgTag?.value,
      imgSrc: userImg?.src,
      userId: userId?.value,
    });
    inputMsgTag.value = "";
    if (chatScroll instanceof HTMLElement) {
      chatScroll.scrollTop = chatScroll?.scrollHeight;
    }
  }
}

export default MsgController;
