const mysql = require("mysql2");
var db = mysql.createConnection({
  host: "13.209.148.137",
  user: "wldlsgur",
  password: "1003",
  database: "calander",
  multipleStatements: true, // 다중쿼리용 설정
});
db.connect();

module.exports = db;
