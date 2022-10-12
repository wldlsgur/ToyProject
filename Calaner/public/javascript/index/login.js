import Nav from "../Common/nav.js";
import ServerController from "../Common/address.js";
const nav = new Nav();
const serverController = new ServerController();
const server = serverController.GetServerAddress();
const idInput = document.querySelector(".login-form__id");
const pwInput = document.querySelector(".login-form__pw");
class Login {
    constructor() { }
    DoLoginCheck(event) {
        event.preventDefault();
        if (!(idInput === null || idInput === void 0 ? void 0 : idInput.value) || !(pwInput === null || pwInput === void 0 ? void 0 : pwInput.value)) {
            return alert("정보를 모두 입력해주세요");
        }
        axios
            .post(`${server}/user/login`, {
            user_id: idInput === null || idInput === void 0 ? void 0 : idInput.value,
            user_pw: pwInput === null || pwInput === void 0 ? void 0 : pwInput.value,
        })
            .then((result) => {
            var _a;
            if ((_a = result === null || result === void 0 ? void 0 : result.data) === null || _a === void 0 ? void 0 : _a.msg) {
                switch (result.data.msg) {
                    case "not found":
                        alert("등록된 사용가 없습니다");
                        break;
                    case "success":
                        alert("로그인 성공!");
                        nav.MovePageRoom();
                        break;
                    case "failed":
                        alert("로그인 실패");
                        break;
                }
            }
        })
            .catch((err) => {
            return console.log(err);
        });
    }
}
export default Login;
