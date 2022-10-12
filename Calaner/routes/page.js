var express = require("express");
var router = express.Router();
const pageController = require("../controller/pageController");

router.get("/", pageController.mainPage);
router.get("/signup", pageController.signUpPage);
router.get("/room", pageController.roomPage);
router.get("/calander", pageController.calanderPage);

module.exports = router;
