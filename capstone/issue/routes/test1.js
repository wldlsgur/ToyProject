const express = require('express');
const router = express();

const db_test = require('../public/SQL/test_sql')();

const success_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};


router.post('/insert', function(req, res, next) {

    let id=req.body.id;
    let school=req.body.school;
    let room=req.body.room;
    let number=req.body.number;
    //var date = new DateTime.now();
   // var newDate = new DateTime(date.year, date.month, date.day+1);

    db_test.test(id, school, room, number,function(err,result){
         if(err){
                console.log(err);
                res.status(400).send(err);
          }
         else res.send(success_response);
  })
})


module.exports = router;