const express = require("express");
const router = express.Router();
const userController = require("../controller/userController");

router.post("/", userController.doSignUp);
router.get("/sameid/:id", userController.doSameIdCheck);
router.post("/login", userController.doLoginCheck);

module.exports = router;
