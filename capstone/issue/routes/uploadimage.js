const express = require('express');
const router = express.Router();
const check_element = require('../Function/check_require_element');

const element_msg = "plz send require elements";
const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};

router.post('/uploadimage/:target/:key', function(req, res){
	let json_data = {
		file : req.file,
		target : req.params.target,
		key : req.params.key
	}
	if(check_element.check_require_element(json_data) === false) return res.send(element_msg);
	res.send(sucess_response);
});
module.exports = router;
