const pool = require('../../DB/db_config');

module.exports = function () {
    return {
        insertCalendarInfo: function (id, school, title, content, startDate, dateDays, startTime, endTime , color, callback) {
            pool.getConnection(function (err, con) {
                for(let i = 0; i<=dateDays; ++i){
                    let sql=`INSERT INTO calendar (id, school, title, content, date, startTime, endTime , color)
                             VALUES('${id}', '${school}', '${title}', '${content}', date_format(DATE_ADD('${startDate}', INTERVAL ${i} DAY),'%Y-%m-%d'), '${startTime}', '${endTime}', '${color}')`;
                    con.query(sql,function(err,result,fields){
                        if(i == dateDays){
                            con.release();
                            if(err) callback(err,null);
                            else callback(null,result);
                        } 
                    })
                }
            })
        },
        selectCalendarInfo: function (school, callback) {   //title, content, date, startTime, endTime, color
            pool.getConnection(function (err, con) {
                let sql = `SELECT title, content, date, startTime, endTime, color
                           FROM calendar
                           WHERE school='${school}'`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            })
        },
        deleteCalendarInfo: function (id, school, title, content, callback) {
            pool.getConnection(function (err, con) {
                let sql=`DELETE FROM calendar
                         WHERE id = '${id}'
                         AND school = '${school}'
                         AND title = '${title}',
                         AND content = '${content}'`;
                con.query(sql,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            })
        },
        detaileinfo: function (query, callback) {
            pool.getConnection(function (err, con) {
                con.query(query,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            })
        },
        pool: pool
    }
};