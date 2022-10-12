const Page = require("../model/page");

const pageController = {
  mainPage: (req, res, next) => {
    Page.DeleteSession(req)
      .then(() => {
        res.status(200).render("index.ejs");
      })
      .catch((err) => {
        console.log(err);
        res.status(400).sned(err);
      });
  },

  signUpPage: (req, res, next) => {
    res.status(200).render("signup.ejs");
  },

  roomPage: (req, res, next) => {
    Page.GetUserInfo(req)
      .then((result) => {
        res.status(200).render("room.ejs", {
          user_id: result[0].user_id,
          id: result[0].id,
          name: result[0].name,
          photo_path: result[0].photo_path,
        });
      })
      .catch((err) => {
        console.log(err);
        res.status(400).send(err);
      });
  },

  calanderPage: (req, res, next) => {
    let roomId = req.session.room_id;
    Page.GetUserInfo(req)
      .then((result) => {
        res.render("calander.ejs", {
          user_id: result[0].user_id,
          id: result[0].id,
          name: result[0].name,
          photo_path: result[0].photo_path,
          room_id: roomId,
        });
      })
      .catch((err) => {
        console.log(err);
        res.status(400).send(err);
      });
  },
};

module.exports = pageController;
