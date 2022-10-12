const express = require('express');
const router = express.Router();
const bcrypt = require('bcrypt')
const fs = require('fs');
const db_check_sql = require('../public/SQL/check_sql')();
const check_element = require('../Function/check_require_element');
const make_query = require('../Function/make_query');

const element_msg = "plz send require elements";
const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};

router.get('/login', function(req, res){
    let json_data = {
        id : req.query.id
    }
    let pw = req.query.pw;
	let target_array = [
		'*'
	]

    if(check_element.check_require_element(json_data) === false || !pw){
        res.send(element_msg);
        return;
    }
	let query = make_query.SELECT(target_array, 'user', json_data, '', 0);//target, table, json, or_and, cnt
    db_check_sql.SELECT(query, function(err, result){
        if(err){
            res.status(400).send(err);
            return;
        }
        if(!result[0]){
            res.send({res : false, msg : "not found"});
            return;
        }
        bcrypt.compare(pw, result[0].pw, (err, same) => {
        console.log(same); //=> true
        if (same) {
          res.send(sucess_response);
        } else {
          res.send(failed_response);
        }
      });
    })
});

router.get('/sameid', function(req, res){
    let json_data = {
        id : req.query.id
    };
	let target_array = [
		'*'
	]

    if(check_element.check_require_element(json_data) === false){
        res.send(element_msg);
        return;
    }
    let query = make_query.SELECT(target_array, 'user', json_data, '', 0);//target, table, json, or_and, cnt
    db_check_sql.SELECT(query, function(err, result){
        if(err){
            res.status(400).send(err);
            return;
        }
        if(!result[0]){
            res.send({res : false, msg : 'not found'});
            return;
        }
        res.send({res : true, msg : 'found'});
    })
})

router.post('/delete/image', function(req, res){
    let json_data = {
        target : req.body.target,
        image_url : req.body.image_url,
        key : req.body.key
    }

    if(check_element.check_require_element(json_data) === false){
        res.send(element_msg);
        return;
    }

    fs.unlink(`uploads/${json_data.target}` + json_data.image_url, function(err){//앨범은 한번에 여라장 삭제로 수정생각
        if(err){
            res.status(400).send(err);
            return;
        }
        else{
            let query = ``;
            switch(json_data.target){
                case 'parent':
                    query = `UPDATE parentinfo SET image_url='default' WHERE key_id = '${json_data.key}';`
                    break;
                case 'food':
                    query = `DELETE FROM food_list WHERE image_url = '${json_data.image_url}';`
                    break;
                case 'teacher':
                    query = `UPDATE teachertinfo SET image_url='default' WHERE id = '${key}';`
                    break;
                case 'president':
                    query = `UPDATE presidentinfo SET image_url='default' WHERE id = '${key}';`
                    break;
                // case 'album':
                //     query = `DELETE FROM album WHERE image_url = '${json_data.image_url}';`
                //     break;
                default :
                    break;
		    }
            console.log(query);
		    db_check_sql.image_delete(query, function(err, result){
			    if(err){
			        res.status(400).send(err);
                    return;
			    }
			    res.send(sucess_response);
		    })
        }
    })
})
module.exports = router;
