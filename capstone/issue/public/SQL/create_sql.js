var pool = require('../../DB/db_config');

module.exports = function(){
    return {
        INSERT : function(query, callback){
            pool.getConnection(function (err, con) {
                con.query(query, function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            });
        },
        UPDATE : function(query, callback){
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