const express = require('express');
const router = express.Router();

const db_calendar = require('../public/SQL/calendar_sql')();

const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};

router.post('/insertCalendarInfo', function(req, res, next) {  
    let id = req.body.id;
    let school = req.body.school;
    let title = req.body.title;
    let content = req.body.content;
    let startDate = req.body.startDate;
    let endDate = req.body.endDate;
    let startTime = req.body.startTime;
    let endTime = req.body.endTime;
    let color = req.body.color;

    let date1 = new Date(startDate);
    let date2 = new Date(endDate);

    let diffDate = date1.getTime() - date2.getTime();
    let dateDays = Math.abs(diffDate / (1000 * 3600 * 24));

    db_calendar.insertCalendarInfo(id, school, title, content, startDate, dateDays, startTime, endTime , color,function(err,result){
         if(err){
                console.log(err);
                res.status(400).send(err);
          }
         else res.send(sucess_response);
  })
})

router.post('/selectCalendarInfo', function(req, res, next) {    
    let school = req.body.school;

    db_calendar.selectCalendarInfo(school, function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else res.send(result);
  })
})

router.post('/updateCalendarInfo', function(req, res){  
    let id = req.body.id;
    let school = req.body.school;
    let title = req.body.title;
    let content = req.body.content;

    let newTitle = req.body.newTitle;
    let newContent = req.body.newContent;
    let startDate = req.body.startDate;
    let endDate = req.body.endDate;
    let startTime = req.body.startTime;
    let endTime = req.body.endTime;
    let color = req.body.color;

    let date1 = new Date(startDate);
    let date2 = new Date(endDate);

    let diffDate = date1.getTime() - date2.getTime();
    let dateDays = Math.abs(diffDate / (1000 * 3600 * 24));
  
    db_calendar.deleteCalendarInfo(id, school, title, content, function(err,result){
      if(err){
        res.status(400).send(err);
      } else{
        db_calendar.insertCalendarInfo(id, school, newTitle, newContent, startDate, dateDays, startTime, endTime , color,function(err,result){
          if(err){
            console.log(err);
            res.status(400).send(err);
          }
          else res.send(sucess_response);
        })
      }
    })
  })

  router.post('/deleteCalendarInfo', function(req, res){    
    let id = req.body.id;
    let school = req.body.school;
    let title = req.body.title;
    let content = req.body.content;

    db_calendar.deleteCalendarInfo(id, school, title, content, function(err,result){
      if(err){
        res.status(400).send(err);
      } else{
        res.send(sucess_response);
      }
    })
  })

router.get('/info', function(req, res){
  let school = req.query.school;
  let date = req.query.date;

  if(!school || !date) return res.send('plz require elements!');
  
  let query = `SELECT id, title, content, startTime, endTime, color FROM calendar WHERE school = '${school}' AND date = '${date}'`;
  db_calendar.detaileinfo(query, function(err, result){
    if(err){
      res.status(400).send(err);
      return;
    }
    res.status(200).send(result);
  })
})

module.exports = router;