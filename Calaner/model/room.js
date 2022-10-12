const db = require("../DB/db");
const successRes = { res: true, msg: "success" };
const failedRes = { res: false, msg: "failed" };

module.exports = {
  InsertRoomInfo: (req) => {
    return new Promise((resolve, reject) => {
      let { user_id, title, pw, people } = req.body;
      let query = `insert into room(title, pw, people) value('${title}', '${pw}', '${people}')`;

      db.query(query, (err, result) => {
        if (err) {
          return reject(err);
        }
        let room_ai = String(result.insertId);
        let query2 = `insert into intoroom(room_id, user_id, chief) value('${room_ai}', '${user_id}', true)`;

        db.query(query2, (err, result) => {
          if (err) {
            return reject(err);
          }
          return resolve(successRes);
        });
      });
    });
  },

  JoinRoom: (req) => {
    return new Promise((resolve, reject) => {
      let { room_id, user_id } = req.body;
      let query2 = `insert into intoroom(room_id, user_id, chief) value('${room_id}', '${user_id}', false)`;

      db.query(query2, (err, result) => {
        if (err) {
          return reject(err);
        }
        return resolve(successRes);
      });
    });
  },

  ShowAllRoomInfo: () => {
    return new Promise((resolve, reject) => {
      let query = `select room_id, title, people from room`;

      db.query(query, async (err, result) => {
        if (err) {
          return reject(err);
        }
        for (let i = 0; i < result.length; i++) {
          let query2 = `select count(*) as nowpeople from intoroom where room_id=${result[i].room_id}`;

          await new Promise((resolve, reject) => {
            db.query(query2, (err, result2) => {
              if (err) {
                return reject(err);
              }
              resolve(result2);
            });
          })
            .then((result2) => {
              result[i].nowpeople = String(result2[0].nowpeople);
            })
            .catch((err) => {
              console.log(err);
              return reject(err);
            });
        }
        return resolve(result);
      });
    });
  },

  ShowAllMyRoomInfo: (req) => {
    return new Promise((resolve, reject) => {
      let user_id = req.session.user_id;
      let query = `select i.room_id, i.chief, r.title, r.people from intoroom as i inner join room as r on i.room_id = r.room_id where i.user_id = '${user_id}'`;

      db.query(query, async (err, result) => {
        if (err) {
          return reject(err);
        }
        for (let i = 0; i < result.length; i++) {
          let query2 = `select count(*) as nowpeople from intoroom where room_id=${result[i].room_id}`;
          await new Promise((resolve, reject) => {
            db.query(query2, (err, result2) => {
              if (err) {
                console.log(err);
                return reject(err);
              }
              resolve(result2);
            });
          })
            .then((result2) => {
              result[i].nowpeople = String(result2[0].nowpeople);
            })
            .catch((err) => {
              console.log(err);
              return reject(err);
            });
        }
        return resolve(result);
      });
    });
  },
  DeleteMyRoom: (req) => {
    return new Promise((resolve, reject) => {
      const key = req.query.key;
      const query = `delete from room where room_id = ${key}`;
      db.query(query, (err, result) => {
        if (err) {
          return reject(err);
        }
        return resolve(successRes);
      });
    });
  },
  CheckRoomInfo: (req) => {
    return new Promise((resolve, reject) => {
      const { room_id, pw } = req.body;
      const query = `select * from room where room_id='${room_id}' AND pw='${pw}'`;

      db.query(query, (err, result) => {
        if (err) {
          return reject(err);
        }
        if (!result[0]) {
          return resolve(failedRes);
        }
        req.session.room_id = room_id;
        return resolve(successRes);
      });
    });
  },
};
