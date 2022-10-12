const express = require('express');
const router = express.Router();

const db_schoolmanage_sql = require('../public/SQL/schoolmanage')();
const check_element = require('../Function/check_require_element');
const make_query = require('../Function/make_query');

const element_msg = "plz send require elements";
const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};

router.get('/info', function(req, res){
	let	json_data = {
		menu : req.query.menu,
		school : req.query.school,
		room : req.query.room
	};
	let target_array = [
		'*'
	]

	if(check_element.check_require_element(json_data) === false) return res.send(element_msg);
	let query = make_query.SELECT(target_array, 'schoolmanagement', json_data, 'AND', 2);
	db_schoolmanage_sql.SELECT(query, function(err, result){
		if(err) return res.status(400).send(err);
		res.status(200).send(result);
	})
});

router.post('/deleteinfo', function(req, res){
	let json_data = {
		key_id : req.body.key_id
	}
	if(check_element.check_require_element(json_data) === false) return res.send(element_msg);
	let query = make_query.DELETE('schoolmanagement', json_data, '', 0);
	db_schoolmanage_sql.DELETE(query, function(err, result){
		if(err) return res.status(400).send(err);
		res.status(200).send(sucess_response);
	})
})

router.post('/updateinfo', function(req, res){
	let json_data = {
		key_id : req.body.key_id,
		title : req.body.title,
		content : req.body.content,
		date : req.body.date
	};
	if(check_element.check_require_element(json_data) === false) return res.send(element_msg);
	let query = `UPDATE schoolmanagement SET title='${json_data.title}', content='${json_data.content}', date='${json_data.date}' WHERE key_id='${json_data.key_id}'`;
	db_schoolmanage_sql.UPDATE(query, function(err, result){
		if(err) return res.status(400).send(err);
		res.status(200).send(sucess_response);
	})
})
module.exports = router;
