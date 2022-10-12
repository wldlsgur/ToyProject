const calander = require("../model/calander");

module.exports = {
  doInsertCalanderInfo: (req, res, next) => {
    calander
      .InsertCalanderInfo(req)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        console.log(err);
        res.status(400).send(err);
      });
  },
  doDeleteCalanderInfo: (req, res, next) => {
    calander
      .DeleteCalanderInfo(req)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        console.log(err);
        res.status(400).send(err);
      });
  },
  doGetCalanderContent: (req, res, next) => {
    calander
      .GetCalanderContent(req)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        console.log(err);
        res.status(400).send(err);
      });
  },
  doGetCalanderPersonnel: (req, res, next) => {
    calander
      .GetCalanderPersonnel(req)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        console.log(err);
        res.status(400).send(err);
      });
  },
};
