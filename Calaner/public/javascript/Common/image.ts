import ServerController from "./address.js";
const serverController: ServerController = new ServerController();
const server = serverController.GetServerAddress();
class ImageController {
  constructor() {}
  ShowUserImage(target: HTMLImageElement, src: string) {
    target.src = `${server}/image/user/${src}`;
  }
}

export default ImageController;
