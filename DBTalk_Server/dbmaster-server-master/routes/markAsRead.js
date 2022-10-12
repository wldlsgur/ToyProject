var express = require("express");
var router = express.Router();
const db = require("../config/db");

router.get("/markAsRead", function (req, res, next) {
  const targetUserID = req.query.targetUserID;
  const sendUserID = req.query.sendUserID;

  //TODO: 인자들 필수로 입력받도록 하기
  if (!targetUserID || !sendUserID) {
    res.send("please send required element!");
    return;
  }

  db.query(
    `UPDATE ch_message SET isRead=0 WHERE targetUserID='${targetUserID}' AND sendUserID='${sendUserID}' OR targetUserID='${sendUserID}' AND sendUserID='${targetUserID}';`,
    function (error, results, fields) {
      // TODO: 기본에러처리
      if (error) {
        res.status(400).send(error);
        return;
      }
      res.json("읽음처리완료");
    }
  );
});

module.exports = router;
