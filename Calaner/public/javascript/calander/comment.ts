import Modal from "../Common/modal.js";
import ServerController from "../Common/address.js";
const serverController: ServerController = new ServerController();
const server = serverController.GetServerAddress();
const modal: Modal = new Modal();
const userIdAI = document.querySelector("#user_id");
const date: HTMLInputElement | null =
  document.querySelector(".commentForm__date");
const content: HTMLInputElement | null = document.querySelector(
  ".commentForm__content"
);

class CommentController {
  Post(): void {
    if (!date?.value || !content?.value) {
      return alert("요구사항을 모두 입력해주세요");
    }
    axios
      .post(`${server}/calander`, {
        date: date.value,
        content: content.value,
      })
      .then((response: { data: { res: any } }) => {
        if (!response?.data?.res) {
          alert("작성 실패");
        }
        modal.CreateRoomHidden();
        return location.reload();
      })
      .catch((err: object) => {
        console.log(err);
      });
  }

  Delete(e: any): void {
    axios
      .delete(`${server}/calander`, {
        contentId:
          e?.target?.parentNode?.parentNode?.querySelector(".contentId")?.value,
      })
      .then((response: { data: { res: any } }) => {
        if (response?.data?.res) {
          alert("삭제 실패");
        }
        modal.CommentInfoHidden();
        return location.reload();
      })
      .catch((err: object) => {
        console.log(err);
      });
  }

  Get(today: Date) {
    return new Promise((resolve, reject) => {
      let year: string = String(today.getFullYear()); // 년도
      let month: string = String(today.getMonth() + 1); // 월
      if (month.length < 2) {
        month = "0" + String(today.getMonth() + 1);
      }

      axios
        .get(`${server}/calander/content`, {
          params: {
            date: year + "-" + month,
          },
        })
        .then((result: { data: any }) => {
          resolve(result.data);
        })
        .catch((err: object) => {
          console.log(err);
          return reject(err);
        });
    });
  }

  SetCommentCalander(result: object) {
    let arrayDay = document.querySelectorAll(".day");

    for (let i in result) {
      let responseDay = result[i].date.split("-");
      for (let j in arrayDay) {
        let htmlDay: String = String(arrayDay[j].innerHTML);
        if (htmlDay.length < 2) {
          htmlDay = "0" + arrayDay[j].innerHTML;
        }
        if (responseDay[2] === htmlDay) {
          let div: Element = document.createElement("div");
          let img: Element = document.createElement("img");
          let name: Element = document.createElement("p");
          let contentId: Element = document.createElement("input");
          let userId: Element = document.createElement("input");
          let content: Element = document.createElement("input");

          div.setAttribute("class", "contentInfo");
          name.addEventListener("click", (e: any) => {
            let userIdTag = e.target.parentNode.querySelector(
              ".contentInfo__userId"
            );
            if (
              userIdAI instanceof HTMLInputElement &&
              userIdTag instanceof HTMLInputElement
            ) {
              if (userIdAI.value === userIdTag.value) {
                modal.CommentInfoDelBtnShow();
              } else {
                modal.CommentInfoDelBtnHidden();
              }
            }
            modal.CommentSetInfo(e);
            modal.CommnetInfoShow();
          });

          img.setAttribute("class", "contentInfo__img");
          img.setAttribute(
            "src",
            `${server}/image/user/` + result[i].photo_path
          );

          name.setAttribute("class", "contentInfo__name");
          name.innerHTML = result[i].name;

          contentId.setAttribute("class", "contentInfo__contentId");
          contentId.setAttribute("value", result[i].content_id);
          contentId.setAttribute("type", "hidden");

          userId.setAttribute("class", "contentInfo__userId");
          userId.setAttribute("value", result[i].user_id);
          userId.setAttribute("type", "hidden");

          content.setAttribute("class", "contentInfo__content");
          content.setAttribute("value", result[i].content);
          content.setAttribute("type", "hidden");

          div.appendChild(img);
          div.appendChild(name);
          div.appendChild(contentId);
          div.appendChild(userId);
          div.appendChild(content);
          arrayDay[j].parentNode?.appendChild(div);
        }
      }
    }
  }
}
export default CommentController;
