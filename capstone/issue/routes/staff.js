const express = require('express');
const router = express.Router();

const db_staff = require('../public/SQL/staff_sql')();

const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};
//  teacherinfo

router.get('/teacherinfo/useschool', function(req, res, next) {
  db_staff.get_teacherinfo_use_school(req.query.school,function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else res.send(result);
  })
});
router.get('/teacherinfo/useid', function(req, res, next) {
  db_staff.get_teacherinfo_use_id(req.query.id,function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else res.send(result);
  })
})
router.post('/updateTeacherinfoAgree', function(req, res, next) {

  let id = req.body.id;

  db_staff.updateTeacherinfoAgree(id,function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else res.send(sucess_response);
  })
})
router.post('/updateTeacherInfo', function(req, res, next) {

  let id = req.body.id;
  let school = req.body.school;
  let room = req.body.room;

  db_staff.updateTeacherInfo(id, school, room ,function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else res.send(sucess_response);
  })
})
router.post('/deleteTeacherinfo', function(req, res, next) {

  let id = req.body.id;

  db_staff.deleteTeacherinfo(id,function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else res.send(sucess_response);
  })
})

//  teacherinfo end
//  presidentinfo
router.get('/presidentinfo/useid', function(req, res, next) {
  db_staff.get_presidentinfo_use_id(req.query.id,function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else res.send(result);
  })
})


//  presidentinfo end


module.exports = router;