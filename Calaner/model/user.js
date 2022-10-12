const bcrypt = require("bcrypt");
const db = require("../DB/db");
const successRes = { res: true, msg: "success" };
const failedRes = { res: false, msg: "failed" };

module.exports = {
  SignUp: (body) => {
    return new Promise((resolve, reject) => {
      let { id, pw, name } = body;
      const hash_pw = bcrypt.hashSync(pw, 10); //암호화
      let query = `INSERT INTO user(id, pw, name) VALUES('${id}', '${hash_pw}', '${name}')`;

      db.query(query, function (err, result) {
        if (err) {
          return reject(err);
        }
        return resolve(successRes);
      });
    });
  },
  SameIdCheck: (params) => {
    return new Promise((resolve, reject) => {
      let user_id = params;
      let query = `SELECT id FROM user WHERE id='${user_id}'`;

      db.query(query, function (err, result) {
        if (err) {
          return reject(err);
        }
        if (!result[0]) {
          return resolve(successRes);
        } else {
          return resolve(failedRes);
        }
      });
    });
  },
  LoginCheck: (req) => {
    return new Promise((resolve, reject) => {
      let { user_id, user_pw } = req.body;
      let query = `SELECT * FROM user WHERE id='${user_id}'`;

      db.query(query, function (err, result) {
        if (err) {
          return reject(err);
        }
        if (!result[0]) {
          return resolve({ res: false, msg: "not found" });
        }
        bcrypt.compare(user_pw, result[0].pw, (err, same) => {
          if (same) {
            if (!req.session.user_id) {
              req.session.user_id = result[0].user_id;
              req.session.login = true;
            }
            return resolve(successRes);
          } else {
            return resolve(failedRes);
          }
        });
      });
    });
  },
};
