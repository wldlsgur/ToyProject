import ServerController from "./address.js";
const serverController = new ServerController();
const server = serverController.GetServerAddress();
class ImageController {
    constructor() { }
    ShowUserImage(target, src) {
        target.src = `${server}/image/user/${src}`;
    }
}
export default ImageController;
