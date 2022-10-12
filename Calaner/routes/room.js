var express = require("express");
var router = express.Router();
const roomController = require("../controller/roomController");

router.post("/make", roomController.doInsertRoomInfo);
router.post("/join", roomController.doJoinRoom);
router.get("/show/all", roomController.doShowAllRoomInfo);
router.get("/show/my", roomController.doShowAllMyRoomInfo);
router.delete("/myroom", roomController.doDeleteMyroom);
router.post("/check", roomController.doCheckRoomInfo);

module.exports = router;
