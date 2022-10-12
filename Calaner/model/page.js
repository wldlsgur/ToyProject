const db = require("../DB/db");
const successRes = { res: true, msg: "success" };
const failedRes = { res: false, msg: "failed" };

module.exports = {
  DeleteSession: (req) => {
    return new Promise((resolve, reject) => {
      req.session.destroy((err) => {
        if (err) {
          return reject(err);
        }
        return resolve();
      });
    });
  },
  GetUserInfo: (req) => {
    return new Promise((resolve, reject) => {
      let userId = req.session.user_id;
      let query = `select * from user where user_id='${userId}'`;

      db.query(query, (err, result) => {
        if (err) {
          return reject(err);
        }
        return resolve(result);
      });
    });
  },
};
