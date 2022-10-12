const express = require('express');
const router = express.Router();
const check_element = require('../Function/check_require_element');

const element_msg = "plz send require elements";
const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};

router.post('/uploadimages/:school/:room/:title/:date', function(req, res){
	console.log(req.files);
	let json_data = {
		files : req.files,
		school : req.params.school,
		room : req.params.room,
        title : req.params.title,
        date : req.params.date
	}
	if(check_element.check_require_element(json_data) === false){
		res.send(element_msg);
		return;
	}
	res.send(sucess_response);
});
module.exports = router;
