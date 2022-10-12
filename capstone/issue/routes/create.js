const express = require('express');
const router = express.Router();
const bcrypt = require('bcrypt')
const db_create_sql = require('../public/SQL/create_sql')();
const check_element = require('../Function/check_require_element');
const make_query = require('../Function/make_query');

const element_msg = "plz send require elements";
const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};

router.post('/user', function(req, res){
	let json_data = {
		id : req.body.id,
		pw : bcrypt.hashSync(req.body.pw, 10), //비밀번호 암호화
		name : req.body.name,
		job : req.body.job
	};//json 형식

    if(check_element.check_require_element(json_data) === false){
		res.send(element_msg);
		return;
	}
	let query = make_query.INSERT('user', json_data);
	db_create_sql.INSERT(query, function(err, result){
		if(err){
			res.status(400).send(err);
			return;
		}
		res.send(sucess_response);
	})
});

router.post('/schoolmanagement', function(req, res){
	let json_data = {
		menu : req.body.menu,
		writer : req.body.writer,
		school : req.body.school,
		title : req.body.title,
		content : req.body.content,
		date : req.body.date,
		room : req.body.room
	};

    if(check_element.check_require_element(json_data) === false){
		res.send(element_msg);
		return;
	}
	let query = make_query.INSERT('schoolmanagement', json_data);
	db_create_sql.INSERT(query, function(err, result){
		if(err){
			res.status(400).send(err);
			return;
		}
		res.send(sucess_response);
	})
});

router.post('/presidentinfo', function(req, res){
	let json_data = {
		id : req.body.id,
		school : req.body.school,
		room : req.body.room,
		number : req.body.number,
		image_url : 'default'
	};

	if(check_element.check_require_element(json_data) === false){
		res.send(element_msg);
		return;
	}
	let query = make_query.INSERT('presidentinfo', json_data);
	db_create_sql.INSERT(query, function(err, result){
		if(err){
			res.status(400).send(err);
			return;
		}
		res.send(sucess_response);
	})
});

router.post('/parentinfo', function(req, res){
	let json_data = {
		id : req.body.id,
		school : req.body.school,
		room : req.body.room,
		number : req.body.number,
		child_name : req.body.name,
		child_age : req.body.age,
		image_url : 'default',
		spec : req.body.spec,
		agree : 'no'
	};

	if(check_element.check_require_element(json_data) === false){
		res.send(element_msg);
		return;
	}
	let query = make_query.INSERT('parentinfo', json_data);
	db_create_sql.INSERT(query, function(err, result){
		if(err){
			res.status(400).send(err);
			return;
		}
		res.send(sucess_response);
	})
});

router.post('/teacherinfo', function(req, res){
	let json_data = {
		id : req.body.id,
		school : req.body.school,
		room : req.body.room,
		number : req.body.number,
		image_url : 'default',
		agree : 'no'
	};

	if(check_element.check_require_element(json_data) === false){
		res.send(element_msg);
		return;
	}
	let query = make_query.INSERT('teacherinfo', json_data);
	db_create_sql.INSERT(query, function(err, result){
		if(err){
			res.status(400).send(err);
			return;
		}
		res.send(sucess_response);
	})
});

router.post('/food_list', function(req, res){
	let json_data = {
		school : req.body.school,
		date : req.body.date,
		image_url : 'default'
	};

	if(check_element.check_require_element(json_data) === false){
		res.send(element_msg);
		return;
	}
	let query = make_query.INSERT('food_list', json_data);
	db_create_sql.INSERT(query, function(err, result){
		if(err){
			res.status(400).send(err);
			return;
		}
		res.send(sucess_response);
	})
})
module.exports = router;
