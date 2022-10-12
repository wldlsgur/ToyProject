var express = require("express");
var router = express.Router();
const calanderController = require("../controller/calanderController");

/* GET home page. */
router.post("/", calanderController.doInsertCalanderInfo);
router.delete("/", calanderController.doDeleteCalanderInfo);
router.get("/content", calanderController.doGetCalanderContent);
router.get("/personnel", calanderController.doGetCalanderPersonnel);

module.exports = router;
