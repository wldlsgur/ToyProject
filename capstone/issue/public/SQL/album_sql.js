var pool = require('../../DB/db_config');

module.exports = function () {
    return {
        SELECT : function (query, callback) {
            pool.getConnection(function (err, con) {
                con.query(query, function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            });
        },
        DELETE : function (query, callback) {
            pool.getConnection(function (err, con) {
                con.query(query, function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            });
        },
        pool: pool
    }
};