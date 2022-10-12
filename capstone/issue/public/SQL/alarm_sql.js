const pool = require('../../DB/db_config');

module.exports = function () {
    return {
        insertTokenInfo: function (id, school, room, target_name, token, callback) { //id, school, room, target_name, token
            pool.getConnection(function (err, con) {
                let sql=`INSERT INTO token (id, school, room, target_name, token)
                         VALUES('${id}', '${school}', '${room}', '${target_name}', '${token}')`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            });
        },
        selectTokenForAll: function (school, teacher_name, callback) {  
            pool.getConnection(function (err, con) {
                let sql=`SELECT token
                         FROM token
                         WHERE school='${school}'
                         AND target_name!='${teacher_name}'`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            });
        },
        selectTokenForStu: function (id, school, room, target_name, callback) {  
            pool.getConnection(function (err, con) {
                let sql=`SELECT token
                         FROM token
                         WHERE id='${id}'
                         AND school='${school}'
                         AND room='${room}'
                         AND target_name='${target_name}'`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            });
        },
        selectTokenForTeacher: function (school, room, callback) {  
            pool.getConnection(function (err, con) {
                let sql=`SELECT token
                         FROM token
                         WHERE id=(SELECT id
                                   FROM teacherinfo
                                   WHERE school='${school}'
                                   AND room='${room}')
                         AND school='${school}'
                         AND room='${room}'`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            });
        },


        pool: pool
    }
};