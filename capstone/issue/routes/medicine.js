const express = require('express');
const router = express.Router();

const db_medicine = require('../public/SQL/medicine_sql')();

const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};

// medicine, medicinemanagement 통일
// medicine
router.post('/insert/data', function(req, res, next) {  //medicine, medicinemanagement 공용 일단 완료.
    let id = req.body.id;
    let child_name = req.body.child_name;
    let m_name = req.body.m_name;
    let morning = req.body.morning;
    let lunch = req.body.lunch;
    let dinner = req.body.dinner;
    let date = req.body.date;
    let mPlace = req.body.mPlace;
    let content = req.body.content;
    

    //medicinemanagement 만 추가
    let school = req.body.school;
    let room = req.body.room;

    db_medicine.insertMedicineInfoAll(id, child_name, m_name, morning, lunch, dinner, date, mPlace, content, school, room,function(err,result){
         if(err){
                console.log(err);
                res.status(400).send(err);
          }
         else res.send(sucess_response);
  })
});

router.get('/select/get/data', function(req, res, next) {
    let id = req.query.id;
    let child_name = req.query.child_name;
    let m_name = req.query.m_name;

    db_medicine.selectMedicineInfo(id, child_name, m_name, function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else res.send(result);
  })
})

router.post('/updateMedicineInfo', function(req, res){
  let id = req.body.id;
  let child_name = req.body.child_name;
  let m_name = req.body.m_name;
  let orgMname = req.body.orgMname;
  let morning = req.body.morning;
  let lunch = req.body.lunch;
  let dinner = req.body.dinner;
  let date = req.body.date;
  let mPlace = req.body.mPlace;
  let content = req.body.content;

  db_medicine.updateMedicineInfo(id, child_name, m_name, orgMname, morning, lunch, dinner, date, mPlace, content, function(err,result){
    if(err){
      res.status(400).send(err);
    } else{
      res.send(sucess_response);
    }
  })
})

router.post('/deleteMedicineInfo', function(req, res){
  let id = req.body.id;
  let child_name = req.body.child_name;
  let m_name = req.body.m_name;

  db_medicine.deleteMedicineInfo(id, child_name, m_name, function(err,result){
    if(err){
      res.status(400).send(err);
    } else{
      res.send(sucess_response);
    }
  })
})

// medicine end
// medicinemanagement
router.get('/selectManage/get/data', function(req, res, next) {
    let school = req.query.school;
    let room = req.query.room;

    db_medicine.selectMedicinemanageInfo(school, room, function(err,result){
    if(err){
        console.log(err);
        res.status(400).send(err);
    }
    else res.send(result);
  })
})

router.get('/selectManage/get/data/useID', function(req, res, next) {
  let id = req.query.id;
  let child_name = req.query.child_name;

  db_medicine.selectMedicinemanageInfo_useId_chNm(id, child_name, function(err,result){
  if(err){
      console.log(err);
      res.status(400).send(err);
  }
  else res.send(result);
})
})
// medicinemanagement end
module.exports = router;