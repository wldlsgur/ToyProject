var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import ServerController from "../Common/address.js";
const serverController = new ServerController();
const server = serverController.GetServerAddress();
const roomIdTag = document.querySelector("#room_id");
class PersonnelController {
    constructor() { }
    Get() {
        return new Promise((resovle, reject) => __awaiter(this, void 0, void 0, function* () {
            axios
                .get(`${server}/calander/personnel`, {
                params: { roomId: roomIdTag === null || roomIdTag === void 0 ? void 0 : roomIdTag.value },
            })
                .then((result) => {
                if (!result.data[0]) {
                    return null;
                }
                return resovle(result.data);
            })
                .catch((err) => {
                console.log(err);
                return reject(err);
            });
        }));
    }
    SetPersonnelCalander(result) {
        let root = document.querySelector(".personnelList");
        for (let i in result) {
            let div1 = document.createElement("div");
            let input = document.createElement("input");
            let div2 = document.createElement("div");
            let img = document.createElement("img");
            let p1 = document.createElement("p");
            let p2 = document.createElement("p");
            div1.setAttribute("class", "personnel");
            input.setAttribute("class", "personnel__userId");
            input.setAttribute("type", "hidden");
            div2.setAttribute("class", "personnelUser");
            img.setAttribute("class", "personnelUser__img");
            img.setAttribute("src", `${server}/image/user/` + result[i].photo_path);
            p1.setAttribute("class", "personnelUser__name");
            p1.innerHTML = result[i].name;
            p2.setAttribute("class", "personnel__moderator");
            if (result[i].chief) {
                p2.innerHTML = "방장";
            }
            else {
                p2.innerHTML = "인원";
            }
            div2.appendChild(img);
            div2.appendChild(p1);
            div1.appendChild(input);
            div1.appendChild(div2);
            div1.appendChild(p2);
            root === null || root === void 0 ? void 0 : root.appendChild(div1);
        }
    }
}
export default PersonnelController;
