import Modal from "../Common/modal.js";
import Nav from "../Common/nav.js";
import ServerController from "../Common/address.js";
const serverController = new ServerController();
const server = serverController.GetServerAddress();
const modal = new Modal();
const nav = new Nav();
const id = document.querySelector("#user_id");
const roomTitleTag = document.querySelector(".create-form__title");
const roomPwTag = document.querySelector(".create-form__pw");
const roomMaxPersonnelTag = document.querySelector(".create-form__max");
const roomPersonnelTag = document.querySelector(".modal-room .personnel");
const allMyRoomKeyTag = document.querySelectorAll(".my-room .room_key");
const rootRoomTag = document.querySelector(".room-list");
const rootMyRoomTag = document.querySelector(".my-room-list");
class RoomController {
    constructor() { }
    Post(e) {
        if (!(roomTitleTag === null || roomTitleTag === void 0 ? void 0 : roomTitleTag.value) ||
            !(roomPwTag === null || roomPwTag === void 0 ? void 0 : roomPwTag.value) ||
            !(roomMaxPersonnelTag === null || roomMaxPersonnelTag === void 0 ? void 0 : roomMaxPersonnelTag.value)) {
            return alert("모두 정보를 입력하세요");
        }
        if (!parseInt(roomMaxPersonnelTag === null || roomMaxPersonnelTag === void 0 ? void 0 : roomMaxPersonnelTag.value)) {
            return alert("인원은 숫자로 입력해주세요");
        }
        axios
            .post(`${server}/room/make`, {
            user_id: id === null || id === void 0 ? void 0 : id.value,
            title: roomTitleTag.value,
            pw: roomPwTag.value,
            people: roomMaxPersonnelTag.value,
        })
            .then((result) => {
            var _a;
            if ((_a = result === null || result === void 0 ? void 0 : result.data) === null || _a === void 0 ? void 0 : _a.res) {
                modal.CreateRoomHidden();
                e.preventDefault();
                return location.reload();
            }
        })
            .catch((err) => {
            return console.log(err);
        });
    }
    Delete(e) {
        let target = e.target;
        let room_key = target.parentNode.parentNode.querySelector(".room_id").value;
        axios
            .delete(`${server}/room/myroom`, {
            params: {
                key: room_key,
            },
        })
            .then((result) => {
            var _a;
            if ((_a = result === null || result === void 0 ? void 0 : result.data) === null || _a === void 0 ? void 0 : _a.res) {
                modal.MyRoomInfoHidden();
                return location.reload();
            }
        })
            .catch((err) => {
            console.log(err);
        });
    }
    MyRoomJoin(e) {
        let target = e.target;
        let room_id = target.parentNode.parentNode.querySelector(".room_id").value;
        let pw = target.parentNode.parentNode.querySelector(".room-pw").value;
        axios
            .post(`/room/check`, { room_id: room_id, pw: pw })
            .then((result) => {
            var _a;
            if (!((_a = result === null || result === void 0 ? void 0 : result.data) === null || _a === void 0 ? void 0 : _a.res)) {
                return alert("비밀번호를 잘못 입력하셧습니다");
            }
            return nav.MovePageCalander();
        })
            .catch((err) => {
            return console.log(err);
        });
    }
    RoomJoin(e) {
        let splitPersonnel = roomPersonnelTag === null || roomPersonnelTag === void 0 ? void 0 : roomPersonnelTag.innerHTML.split("/");
        if (!splitPersonnel) {
            return;
        }
        if (parseInt(splitPersonnel[0]) >= parseInt(splitPersonnel[1])) {
            return alert("정원초과");
        }
        let room_id = e.target.parentNode.parentNode.querySelector(".room_id").value;
        let pw = e.target.parentNode.parentNode.querySelector(".room-pw").value;
        axios
            .post(`${server}/room/check`, { room_id: room_id, pw: pw })
            .then((roomCheckresult) => {
            var _a;
            if (!((_a = roomCheckresult === null || roomCheckresult === void 0 ? void 0 : roomCheckresult.data) === null || _a === void 0 ? void 0 : _a.res)) {
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
                user_id: id === null || id === void 0 ? void 0 : id.value,
            })
                .then((roomJoinResult) => {
                var _a;
                if ((_a = roomJoinResult === null || roomJoinResult === void 0 ? void 0 : roomJoinResult.data) === null || _a === void 0 ? void 0 : _a.res) {
                    return nav.MovePageCalander();
                }
            })
                .catch((err) => {
                return console.log(err);
            });
        })
            .catch((err) => {
            return console.log(err);
        });
    }
    GetAllRoomList() {
        axios
            .get(`${server}/room/show/all`)
            .then((result) => {
            var _a;
            if (!(result === null || result === void 0 ? void 0 : result.data)) {
                return console.log(result);
            }
            for (let i = 0; i < result.data.length; i++) {
                let make_room = document.createElement("div");
                make_room.setAttribute("class", "room");
                make_room.innerHTML = `
        <input type="hidden" value="${result.data[i].room_id}" class="room_key" /><p class="room__name">${result.data[i].title}</p><p class="room__now">${result.data[i].nowpeople}/${result.data[i].people}</p>
        `;
                (_a = make_room
                    .querySelector(".room__name")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", (e) => {
                    modal.RoomSetInfo(e);
                    modal.RoomShow();
                });
                rootRoomTag === null || rootRoomTag === void 0 ? void 0 : rootRoomTag.appendChild(make_room);
            }
        })
            .catch((err) => {
            console.log(err);
        });
    }
    GetAllMyRoomList() {
        axios
            .get(`${server}/room/show/my`)
            .then((result) => {
            var _a;
            if (!(result === null || result === void 0 ? void 0 : result.data)) {
                return;
            }
            for (let i = 0; i < result.data.length; i++) {
                let make_room = document.createElement("div");
                make_room.classList.add("my-room");
                make_room.innerHTML = `
        <input type="hidden" value="${result.data[i].room_id}" class="room_key" /><input type="hidden" value="${result.data[i].chief}" class="room_chief" /><p class="my-room__name">${result.data[i].title}</p><p class="my-room__now">${result.data[i].nowpeople}/${result.data[i].people}</p>`;
                (_a = make_room
                    .querySelector(".my-room__name")) === null || _a === void 0 ? void 0 : _a.addEventListener("click", (e) => {
                    modal.MyRoomSetInfo(e);
                    modal.MyRoomInfoShow();
                });
                rootMyRoomTag === null || rootMyRoomTag === void 0 ? void 0 : rootMyRoomTag.appendChild(make_room);
            }
        })
            .catch((err) => {
            return console.log(err);
        });
    }
}
export default RoomController;
