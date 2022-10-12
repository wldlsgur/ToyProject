const express = require('express');
const router = express();
const bcrypt = require('bcrypt')
const db_user_sql = require('../public/SQL/user_sql')();
const check_element = require('../Function/check_require_element');
const make_query = require('../Function/make_query');

const element_msg = "plz send require elements";
const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};

router.get('/info/:id', function(req, res){
	let json_data = {
		id : req.params.id
	}
	let target_array = [
		'name',
		'job'
	]
	if(check_element.check_require_element(json_data) === false){
		res.send(element_msg);
		return;
	}

	let query = make_query.SELECT(target_array, 'user', json_data, '', 0);
	db_user_sql.SELECT(query, function(err, result){
		if(err){
			res.status(400).send(err);
			return;
		}
		res.send(result[0]);
	})
})

router.post('/update/info', function(req, res){//user는 pw만 변경
	let json_data = {
		id : req.body.id,
	}
	let pw = bcrypt.hashSync(req.body.pw, 10);
	if(check_element.check_require_element(json_data) === false || !pw)	{return res.send(element_msg);}
	let target = `pw='${pw}'`;
	let query = make_query.UPDATE(target, 'user', json_data, '', 0);
	
	db_user_sql.UPDATE(query, function(err, result){
		if(err){
			res.status(400).send(err);
			return;
		}
		res.status(200).send(sucess_response);
	})
})

router.post('/delete/info', function(req, res){//다른 테이블 연쇄 삭제 고려
	let json_data = {
		id : req.body.id,
		job : req.body.job,
	}
	if(check_element.check_require_element(json_data) === false){
		res.sned(element_msg);
		return;
	}

	let query = ``;
	switch(json_data.job){
		case '선생님' :
			query = `DELETE u, t FROM user as u, teacherinfo as t WHERE u.id='${json_data.id}' AND t.id='${json_data.id}'`;
			break;
		case '부모님' :
			query = `DELETE u, p FROM user as u, parentinfo as p WHERE u.id='${json_data.id}' AND p.id='${json_data.id}'`;
			break;
		case '원장님' :
			query = 
				`
					DELETE FROM album WHERE school IN (SELECT school FROM presidentinfo WHERE id='${json_data.id}');
					DELETE FROM food_list WHERE school IN (SELECT school FROM presidentinfo WHERE id='${json_data.id}');
					DELETE FROM medicinemanagement WHERE school IN (SELECT school FROM presidentinfo WHERE id='${json_data.id}');
					DELETE FROM schoolmanagement WHERE school IN (SELECT school FROM presidentinfo WHERE id='${json_data.id}');
					DELETE FROM calendar WHERE school IN (SELECT school FROM presidentinfo WHERE id='${json_data.id}');		  
					DELETE FROM medicine WHERE child_name IN (SELECT p.child_name FROM parentinfo as p WHERE p.school='${json_data.school}');
					DELETE u, p FROM user as u, presidentinfo as p WHERE u.id='${json_data.id}' AND p.id='${json_data.id}';
				`;
			break;
	}
	console.log(query);
	db_user_sql.DELETE(query, function(err, result){
		if(err){
			res.status(400).send(err);
			return;
		}
		res.send(sucess_response);
	})
})
module.exports = router;
