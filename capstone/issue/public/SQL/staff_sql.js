const pool = require('../../DB/db_config');

module.exports = function () {
    return {
        get_teacherinfo_use_school: function (school,callback) {
            pool.getConnection(function (err, con) {
                let sql=`select * from teacherinfo where school='${school}'`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            });
        },
        get_teacherinfo_use_id: function (id,callback) {
            pool.getConnection(function (err, con) {
                let sql=`select * from teacherinfo where id='${id}'`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            })
        },
        get_presidentinfo_use_id: function (id,callback) {
            pool.getConnection(function (err, con) {
                let sql=`select * from presidentinfo where id='${id}'`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            })
        },
        updateTeacherinfoAgree: function (id,callback) {
            pool.getConnection(function (err, con) {
                let sql=`UPDATE teacherinfo
                         SET agree='yes'
                         WHERE id = '${id}'`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            })
        },
        updateTeacherInfo: function (id, school, room, callback) {
            pool.getConnection(function (err, con) {
                let sql=`UPDATE teacherinfo
                         SET id = '${id}',
                             school = '${school}',
                             room = '${room}',
                             agree='no'  
                         WHERE id= '${id}'`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            })
        },
        deleteTeacherinfo: function (id,callback) {
            pool.getConnection(function (err, con) {
                let sql=`DELETE FROM teacherinfo
                         WHERE id = '${id}'`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            })
        },


        pool: pool
    }
};