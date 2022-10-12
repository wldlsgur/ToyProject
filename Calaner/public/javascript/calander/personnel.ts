import ServerController from "../Common/address.js";
const serverController: ServerController = new ServerController();
const server = serverController.GetServerAddress();
const roomIdTag: HTMLInputElement | null = document.querySelector("#room_id");
class PersonnelController {
  constructor() {}

  Get(): Promise<object> {
    return new Promise(async (resovle, reject) => {
      axios
        .get(`${server}/calander/personnel`, {
          params: { roomId: roomIdTag?.value },
        })
        .then((result: { data: any }) => {
          if (!result.data[0]) {
            return null;
          }
          return resovle(result.data);
        })
        .catch((err: object) => {
          console.log(err);
          return reject(err);
        });
    });
  }

  SetPersonnelCalander(result: any) {
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
      } else {
        p2.innerHTML = "인원";
      }

      div2.appendChild(img);
      div2.appendChild(p1);

      div1.appendChild(input);
      div1.appendChild(div2);
      div1.appendChild(p2);

      root?.appendChild(div1);
    }
  }
}

export default PersonnelController;
