const express = require('express');
const router = express();
const db = require('../DB/db');

router.get('/allschool', function(req, res){
	let query = `SELECT DISTINCT school FROM presidentinfo ORDER BY school asc`;
	db.query(query, function(err, result){
		if(err){
			res.status(400).send(err);
			return;
		}
		res.send(result);
	})
})

router.get('/allroom/:school', function(req, res){
	let school = req.params.school;

	if(!school){
		res.send('plz send require elements');
		return;
	}

	let query = `SELECT DISTINCT room FROM presidentinfo WHERE school='${school}' ORDER BY room asc`;
	db.query(query, function(err, result){
		if(err){
			res.status(400).send(err);
			return;
		}
		res.send(result);
	})
})

module.exports = router;
