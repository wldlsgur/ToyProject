var express = require("express");
var router = express.Router();

router.post("/uploadimage/:id", function (req, res, next) {
  if (!req.file) {
    res.status(200).send({ res: false, msg: "failed" });
  }
  console.log(req.file);

  res.status(200).send({ res: true, msg: "success" });
});

module.exports = router;
