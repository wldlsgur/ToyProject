const User = require("../model/user");

const userController = {
  doSignUp: (req, res, next) => {
    User.SignUp(req.body)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        res.status(400).send(err);
      });
  },
  doSameIdCheck: (req, res, next) => {
    User.SameIdCheck(req.params.id)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        res.status(400).send(err);
      });
  },
  doLoginCheck: (req, res, next) => {
    User.LoginCheck(req)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        res.status(400).send(err);
      });
  },
};

module.exports = userController;
