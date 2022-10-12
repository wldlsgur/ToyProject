var mysql = require('mysql');

module.exports = function () {
    var pool = mysql.createPool({
        host: '49.50.164.199',
        port: 3306,
        user: 'root',
        password: '0000',
        database: 'issue',
        multipleStatements: true,
        connectionLimit: 50,
        queueLimit: 100
    });
    return {
        getConnection: function (callback){
            pool.getConnection(callback);
        },
        end : function (callback){
            pool.end(callback);
        }
    }
}();