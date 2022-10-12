const express = require('express');
const res = require('express/lib/response');
const router = express.Router();
const Promise = require('promise');
const db_album_sql = require('../public/SQL/album_sql')();
const check_element = require('../Function/check_require_element');
const make_query = require('../Function/make_query');
const fs = require('fs');
const db = require('../DB/db');

const element_msg = "plz send require elements";
const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};

router.get('/info', function(req, res){
    const json_data = req.query;
    if(check_element.check_require_element(json_data) === false){
        return res.send(element_msg);
    }

    let array1 = {};
    let array2 = [];
    let query1 = `SELECT distinct title, date FROM album WHERE school='${json_data.school}' AND room='${json_data.room}'`;
    db_album_sql.SELECT(query1, async function(err, result){
        if(err){
            return res.status(400).send(err);
        }
        for(let i=0 ; i<result.length ; i++){
            array1 = {};
            array1.title = result[i].title;
            array1.date = result[i].date;
            let query2 = `SELECT image_url FROM album WHERE school='${json_data.school}' AND room='${json_data.room}' AND title = '${result[i].title}' AND date='${result[i].date}'`;
            await job2(query2); 
        }
        res.status(200).send(array2);
    })
    function job2(query2){
        return new Promise(function(resolve, rejected){
            db_album_sql.SELECT(query2, function(err, result){
                if(err){
                    rejected(err);
                }
                let img_url = [];
                for(let j =0 ; j < result.length; j++)
                {
                    img_url.push(result[j].image_url);
                }
                resolve(img_url);
            })
        })
        .then(function(img_url){
            array1.image_url = img_url;
            array2.push(array1);
        })
        .catch(function(err){
            res.status(400).send(err);
    })
    }
})

router.post('/delete/album', function(req, res){
    let images = req.body.key_images.trim(); 
    let image_array = images.split(',');

    for(let i=0 ; i<image_array.length ; i++){
        fs.unlink(`uploads/album` + image_array[i], async function(err){
            if(err){
                res.status(400).send(err);
                return;
            }
            else{
                let query = `DELETE FROM alubm WHERE image_url='${image_array[i]}'`
                await job(query);
            }
        })
    }
    res.send(sucess_response);

    async function job(query){
        db_album_sql.DELETE(query,function(err, result){
            if(err){
                res.status(400).send(err);
                return;
            }
        })
    }
})
module.exports = router;
