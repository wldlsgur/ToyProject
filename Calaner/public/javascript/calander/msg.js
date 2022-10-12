import ServerController from "../Common/address.js";
const serverController = new ServerController();
const server = serverController.GetServerAddress();
const userId = document.querySelector("#user_id");
const roomId = document.querySelector("#room_id");
const userName = document.querySelector("#userName");
const userImg = document.querySelector(".user-info > img");
const chatScroll = document.querySelector(".chatList__msg");
const inputMsgTag = document.querySelector(".chatWrite__input");
class MsgController {
    constructor(socket) {
        this.socket = socket;
    }
    SocketJoin() {
        this.socket.emit("joinRoom", {
            roomId: roomId === null || roomId === void 0 ? void 0 : roomId.value,
            userName: userName === null || userName === void 0 ? void 0 : userName.value,
        });
    }
    SocketLeave() {
        this.socket.emit("leaveRoom", {
            roomId: roomId === null || roomId === void 0 ? void 0 : roomId.value,
            userName: userName === null || userName === void 0 ? void 0 : userName.value,
        });
    }
    ShowJoinUser(data) {
        let root = document.querySelector(".chatList__msg");
        let joinmsg = document.createElement("div");
        joinmsg.setAttribute("class", "joinAndLeave");
        joinmsg.innerHTML = `${data.userName}님 입장`;
        root === null || root === void 0 ? void 0 : root.appendChild(joinmsg);
    }
    ShowLeaveUser(data) {
        let root = document.querySelector(".chatList__msg");
        let joinmsg = document.createElement("div");
        joinmsg.setAttribute("class", "joinAndLeave");
        joinmsg.innerHTML = `${data.userName}님 퇴장`;
        root === null || root === void 0 ? void 0 : root.appendChild(joinmsg);
    }
    ShowMsg(data) {
        let root = document.querySelector(".chatList__msg");
        let msg = document.createElement("div");
        if (data.userId === (userId === null || userId === void 0 ? void 0 : userId.value)) {
            msg.setAttribute("class", "mymsg");
            let content = document.createElement("p");
            content.setAttribute("class", "mymsg__content");
            content.innerHTML = data.msg;
            msg.appendChild(content);
        }
        else {
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
        root === null || root === void 0 ? void 0 : root.appendChild(msg);
        if (chatScroll instanceof HTMLElement) {
            chatScroll.scrollTop = chatScroll === null || chatScroll === void 0 ? void 0 : chatScroll.scrollHeight;
        }
    }
    PostMsgSocket(e) {
        e.preventDefault();
        if (!(inputMsgTag === null || inputMsgTag === void 0 ? void 0 : inputMsgTag.value)) {
            return alert("메세지를 입력해주세요");
        }
        this.socket.emit("chat-msg", {
            roomId: roomId === null || roomId === void 0 ? void 0 : roomId.value,
            userName: userName === null || userName === void 0 ? void 0 : userName.value,
            msg: inputMsgTag === null || inputMsgTag === void 0 ? void 0 : inputMsgTag.value,
            imgSrc: userImg === null || userImg === void 0 ? void 0 : userImg.src,
            userId: userId === null || userId === void 0 ? void 0 : userId.value,
        });
        inputMsgTag.value = "";
        if (chatScroll instanceof HTMLElement) {
            chatScroll.scrollTop = chatScroll === null || chatScroll === void 0 ? void 0 : chatScroll.scrollHeight;
        }
    }
}
export default MsgController;
