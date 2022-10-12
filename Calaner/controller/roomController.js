const Room = require("../model/room");

module.exports = {
  doInsertRoomInfo: (req, res, next) => {
    Room.InsertRoomInfo(req)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        console.log(err);
        return res.status(400).send(err);
      });
  },
  doJoinRoom: (req, res, next) => {
    Room.JoinRoom(req)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        console.log(err);
        return res.status(400).send(err);
      });
  },
  doShowAllRoomInfo: (req, res, next) => {
    Room.ShowAllRoomInfo()
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        console.log(err);
        return res.status(400).send(err);
      });
  },
  doShowAllMyRoomInfo: (req, res, next) => {
    Room.ShowAllMyRoomInfo(req)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        console.log(err);
        return res.status(400).send(err);
      });
  },
  doDeleteMyroom: (req, res, next) => {
    Room.DeleteMyRoom(req)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        console.log(err);
        return res.status(400).send(err);
      });
  },
  doCheckRoomInfo: (req, res, next) => {
    Room.CheckRoomInfo(req)
      .then((result) => {
        res.status(200).send(result);
      })
      .catch((err) => {
        console.log(err);
        return res.status(400).send(err);
      });
  },
};
