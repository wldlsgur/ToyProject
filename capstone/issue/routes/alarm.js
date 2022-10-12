const express = require('express');
const router = express.Router();

const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};

const db_alarm = require('../public/SQL/alarm_sql')();

//const db_ = require('../public/SQL/medicine_sql')();

const admin = require('firebase-admin')

let serAccount = require('../firebase-pk.json')

admin.initializeApp({
  credential: admin.credential.cert(serAccount),
})

function job(token, title, body){
  let target_token = token;
    //target_token은 푸시 메시지를 받을 디바이스의 토큰값

  let message = {
    notification: {
      title: title,
      body: body
    },
    token: target_token,
    android: {
      priority: "high"
    }
  }

  console.log(target_token);

  admin
    .messaging()
    .send(message)
    .then(function (response) {
      console.log('Successfully sent message: : ', response)
      return true;
    })
    .catch(function (err) {
      console.log('Error Sending message!!! : ', err)
      return false;
    })
}

router.post('/insertTokenInfo', function (req, res, next) {   //id, school, room, target_name, token
  let id = req.body.id;
  let school = req.body.school;
  let room = req.body.room;
  let target_name = req.body.target_name;
  let token = req.body.token;
    
  db_alarm.insertTokenInfo(id, school, room, target_name, token, function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else{
      res.send(sucess_response);
    } 
  })   
})

router.post('/allAlarm', function (req, res, next) {   //school, title, body
  let school = req.body.school;
  let title = req.body.title;
  let body = req.body.body;

  let teacher_name = req.body.teacher_name;
    
  db_alarm.selectTokenForAll(school, teacher_name, function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else{
      if(job(result[0].token, title, body) === false){
        res.send(failed_response);
      }
      res.send(sucess_response);
    } 
  })   
})

router.post('/approveAlarmPtoT', function (req, res, next) {   //id, school, room, target_name, title, body
  let id = req.body.id;
  let school = req.body.school;
  let room = req.body.room;
  let title = req.body.title;
  let body = req.body.body;
    
  db_alarm.selectTokenForTeacher(school, room, function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else{
      if(job(result[0].token, title, body) === false){
        res.send(failed_response);
      }
      res.send(sucess_response);
    } 
  })   
})

router.post('/approveAlarmTtoP', function (req, res, next) {   //id, school, room, target_name, title, body
  let id = req.body.id;
  let school = req.body.school;
  let room = req.body.room;
  let target_name = req.body.target_name;
  let title = req.body.title;
  let body = req.body.body;
    
  db_alarm.selectTokenForStu(id, school, room, target_name, function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else{
      if(job(result[0].token, title, body) === false){
        res.send(failed_response);
      }
      res.send(sucess_response);
    } 
  })   
})


  module.exports = router;