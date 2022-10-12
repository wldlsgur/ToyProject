const db = require("../DB/db");
const successRes = { res: true, msg: "success" };
const failedRes = { res: false, msg: "failed" };

module.exports = {
  InsertCalanderInfo: (req) => {
    return new Promise((resolve, reject) => {
      const { date, content } = req.body;
      const user_id = req.session.user_id;
      const room_id = req.session.room_id;
      let query = `insert into content(room_id,user_id,date,content) value('${room_id}', '${user_id}', '${date}', '${content}')`;

      db.query(query, (err, result) => {
        if (err) {
          return reject(err);
        }
        return resolve(successRes);
      });
    });
  },
  DeleteCalanderInfo: (req) => {
    return new Promise((resolve, reject) => {
      const { contentId } = req.body;
      let query = `delete from content where content_id = '${contentId}'`;

      db.query(query, (err, result) => {
        if (err) {
          return reject(err);
        }
        return resolve(successRes);
      });
    });
  },
  GetCalanderContent: (req) => {
    return new Promise((resolve, reject) => {
      const date = req.query.date;
      const room_id = req.session.room_id;
      let query = `select c.content_id, c.user_id, c.date, c.content, u.name, u.photo_path
  from content as c
  inner join user as u on c.user_id = u.user_id
  where c.room_id = ${room_id} AND c.date like '%${date}%'
  `;

      db.query(query, (err, result) => {
        if (err) {
          return reject(err);
        }
        return resolve(result);
      });
    });
  },
  GetCalanderPersonnel: (req) => {
    return new Promise((resolve, reject) => {
      const { roomId } = req.query;
      let query = `SELECT i.user_id, i.chief, u.name, u.photo_path
        FROM intoroom as i
        inner join user as u on i.user_id = u.user_id
        where i.room_id = '${roomId}'`;

      db.query(query, (err, result) => {
        if (err) {
          return reject(err);
        }
        return resolve(result);
      });
    });
  },
};
