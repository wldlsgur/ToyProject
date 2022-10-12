const mysql = require('mysql');
var db =  mysql.createConnection({
	host: '49.50.164.199',
        user: 'root',
	password: '0000',
        database: 'issue'
});
db.connect();

module.exports = db;
