import Modal from "../Common/modal.js";
import Nav from "../Common/nav.js";
import ServerController from "../Common/address.js";

const serverController: ServerController = new ServerController();
const server = serverController.GetServerAddress();
const modal: Modal = new Modal();
const nav: Nav = new Nav();
const id: HTMLInputElement | null = document.querySelector("#user_id");
const roomTitleTag: HTMLInputElement | null = document.querySelector(
  ".create-form__title"
);
const roomPwTag: HTMLInputElement | null =
  document.querySelector(".create-form__pw");
const roomMaxPersonnelTag: HTMLInputElement | null =
  document.querySelector(".create-form__max");
const roomPersonnelTag = document.querySelector(".modal-room .personnel");
const allMyRoomKeyTag = document.querySelectorAll(".my-room .room_key");
const rootRoomTag = document.querySelector(".room-list");
const rootMyRoomTag = document.querySelector(".my-room-list");
class RoomController {
  constructor() {}

  Post(e: { preventDefault: () => void }) {
    if (
      !roomTitleTag?.value ||
      !roomPwTag?.value ||
      !roomMaxPersonnelTag?.value
    ) {
      return alert("모두 정보를 입력하세요");
    }
    if (!parseInt(roomMaxPersonnelTag?.value)) {
      return alert("인원은 숫자로 입력해주세요");
    }
    axios
      .post(`${server}/room/make`, {
        user_id: id?.value,
        title: roomTitleTag.value,
        pw: roomPwTag.value,
        people: roomMaxPersonnelTag.value,
      })
      .then((result: { data: { res: any } }) => {
        if (result?.data?.res) {
          modal.CreateRoomHidden();
          e.preventDefault();
          return location.reload();
        }
      })
      .catch((err: object) => {
        return console.log(err);
      });
  }
  Delete(e: { target: any }) {
    let target = e.target;
    let room_key = target.parentNode.parentNode.querySelector(".room_id").value;

    axios
      .delete(`${server}/room/myroom`, {
        params: {
          key: room_key,
        },
      })
      .then((result: { data: { res: any } }) => {
        if (result?.data?.res) {
          modal.MyRoomInfoHidden();
          return location.reload();
        }
      })
      .catch((err: object) => {
        console.log(err);
      });
  }

  MyRoomJoin(e: { target: any }) {
    let target = e.target;
    let room_id = target.parentNode.parentNode.querySelector(".room_id").value;
    let pw = target.parentNode.parentNode.querySelector(".room-pw").value;

    axios
      .post(`/room/check`, { room_id: room_id, pw: pw })
      .then((result: { data: { res: any } }) => {
        if (!result?.data?.res) {
          return alert("비밀번호를 잘못 입력하셧습니다");
        }
        return nav.MovePageCalander();
      })
      .catch((err: object) => {
        return console.log(err);
      });
  }

  RoomJoin(e: any) {
    let splitPersonnel = roomPersonnelTag?.innerHTML.split("/");
    if (!splitPersonnel) {
      return;
    }
    if (parseInt(splitPersonnel[0]) >= parseInt(splitPersonnel[1])) {
      return alert("정원초과");
    }

    let room_id =
      e.target.parentNode.parentNode.querySelector(".room_id").value;
    let pw = e.target.parentNode.parentNode.querySelector(".room-pw").value;

    axios
      .post(`${server}/room/check`, { room_id: room_id, pw: pw })
      .then((roomCheckresult: { data: { res: any } }) => {
        if (!roomCheckresult?.data?.res) {
          return alert("비밀번호가 틀립니다.");
        }
        for (let id of allMyRoomKeyTag) {
          if (id instanceof HTMLInputElement && id.value === room_id) {
            return nav.MovePageCalander();
          }
        }
        axios
          .post(`${server}/room/join`, {
            room_id: room_id,
            user_id: id?.value,
          })
          .then((roomJoinResult: { data: { res: any } }) => {
            if (roomJoinResult?.data?.res) {
              return nav.MovePageCalander();
            }
          })
          .catch((err: object) => {
            return console.log(err);
          });
      })
      .catch((err: object) => {
        return console.log(err);
      });
  }

  GetAllRoomList() {
    axios
      .get(`${server}/room/show/all`)
      .then((result: { data: string | any[] }) => {
        if (!result?.data) {
          return console.log(result);
        }
        for (let i = 0; i < result.data.length; i++) {
          let make_room = document.createElement("div");
          make_room.setAttribute("class", "room");
          make_room.innerHTML = `
        <input type="hidden" value="${result.data[i].room_id}" class="room_key" /><p class="room__name">${result.data[i].title}</p><p class="room__now">${result.data[i].nowpeople}/${result.data[i].people}</p>
        `;
          make_room
            .querySelector(".room__name")
            ?.addEventListener("click", (e) => {
              modal.RoomSetInfo(e);
              modal.RoomShow();
            });
          rootRoomTag?.appendChild(make_room);
        }
      })
      .catch((err: object) => {
        console.log(err);
      });
  }

  GetAllMyRoomList() {
    axios
      .get(`${server}/room/show/my`)
      .then((result: { data: string | any[] }) => {
        if (!result?.data) {
          return;
        }
        for (let i = 0; i < result.data.length; i++) {
          let make_room = document.createElement("div");
          make_room.classList.add("my-room");
          make_room.innerHTML = `
        <input type="hidden" value="${result.data[i].room_id}" class="room_key" /><input type="hidden" value="${result.data[i].chief}" class="room_chief" /><p class="my-room__name">${result.data[i].title}</p><p class="my-room__now">${result.data[i].nowpeople}/${result.data[i].people}</p>`;
          make_room
            .querySelector(".my-room__name")
            ?.addEventListener("click", (e) => {
              modal.MyRoomSetInfo(e);
              modal.MyRoomInfoShow();
            });
          rootMyRoomTag?.appendChild(make_room);
        }
      })
      .catch((err: object) => {
        return console.log(err);
      });
  }
}

export default RoomController;
