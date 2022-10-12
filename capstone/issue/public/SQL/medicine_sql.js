const pool = require('../../DB/db_config');

module.exports = function () {
    return {
        insertMedicineInfoAll: function (id, child_name, m_name, morning, lunch, dinner, date, mPlace, content, school, room,callback) {
                    pool.getConnection(function (err, con) {
                        let insertMedicineInfo = `insert into medicine (id, child_name, m_name, morning, lunch, dinner, date, mPlace, content)
                                                  values('${id}','${child_name}','${m_name}','${morning}','${lunch}','${dinner}','${date}','${mPlace}','${content}')`;
                        let insertMedicineManagementInfo = `insert into medicinemanagement (id, school, room, child_name, m_name, date, mor, lun, din)
                                                            values('${id}', '${school}', '${room}', '${child_name}','${m_name}',date_format(now(),'%Y-%m-%d'),'false','false','false')`;
                        let multiQuery = `${insertMedicineInfo};${insertMedicineManagementInfo};`
                        con.query(multiQuery,function(err,result,fields){
                            con.release();
                            if(err) callback(err,null);
                            else callback(null,result);
                        })
                    });
                },
        selectMedicineInfo: function (id,child_name,m_name,callback) {
                    pool.getConnection(function (err, con) {
                        let sql=`select * from medicine where id='${id}' AND child_name='${child_name}' AND m_name='${m_name}'`;
                        con.query(sql,function(err,result,fields){
                            con.release();
                            if(err) callback(err,null);
                            else callback(null,result);
                        })
                    })
                },
        updateMedicineInfo: function(id, child_name, m_name, orgMname, morning, lunch, dinner, date, mPlace, content, callback){
            pool.getConnection(function(err, con){
                let sql=`update medicine AS m, medicinemanagement AS mm
                        SET m.m_name = '${m_name}',
                            m.morning = '${morning}',
                            m.lunch = '${lunch}',
                            m.dinner = '${dinner}',
                            m.date = '${date}',
                            m.mPlace = '${mPlace}',
                            m.content = '${content}',
                            mm.m_name = '${m_name}',
                            mm.mor = 'false',
                            mm.lun = 'false',
                            mm.din = 'false'
                        WHERE m.id = '${id}'
                        AND m.child_name = '${child_name}'
                        AND mm.id = '${id}'
                        AND mm.child_name = '${child_name}'
                        AND m.m_name = '${orgMname}'
                        AND mm.m_name= '${orgMname}'`;
                con.query(sql,function(err,result,fields){
                        con.release();
                        if(err) callback(err,null);
                        else callback(null,result);
                })
            })

        },
        deleteMedicineInfo: function(id, child_name, m_name, callback){
            pool.getConnection(function(err, con){
                let deleteMedicinesql=`DELETE FROM medicine
                                       WHERE id='${id}'
                                       AND child_name = '${child_name}'
                                       AND m_name = '${m_name}'`;
                let deleteMedicinemanagementsql=`DELETE FROM medicinemanagement
                                                 WHERE id='${id}'
                                                 AND child_name = '${child_name}'
                                                 AND m_name = '${m_name}'`;
                let multiQuery = `${deleteMedicinesql};${deleteMedicinemanagementsql};`
                con.query(multiQuery,function(err,result,fields){
                    con.release();
                    if(err) callback(err,null);
                    else callback(null,result);
                })
            })

        },
        selectMedicinemanageInfo: function (school,room,callback) {
                    pool.getConnection(function (err, con) {
                        let sql=`SELECT medicinemanagement.id, medicinemanagement.school, medicinemanagement.room, medicinemanagement.child_name,
                                medicinemanagement.m_name, medicinemanagement.date, medicinemanagement.mor, medicinemanagement.lun, medicinemanagement.din,
                                medicine.morning, medicine.lunch, medicine.dinner
                                FROM medicinemanagement, medicine
                                WHERE medicine.id IN (SELECT distinct id
                                                   FROM medicinemanagement
                                                   WHERE school='${school}'
                                                   AND room='${room}') 
                                AND medicine.child_name IN (SELECT distinct child_name
                                                         FROM medicinemanagement
                                                         WHERE school='${school}'
                                                         AND room='${room}')
                                AND medicine.m_name=medicinemanagement.m_name`;
                            con.query(sql,function(err,result,fields){
                                con.release();
                                if(err) callback(err,null);
                                else callback(null,result);
                        })
                    })
                },
        selectMedicinemanageInfo_useId_chNm: function (id,child_name,callback) {
                    pool.getConnection(function (err, con) {
                        let sql=`SELECT medicinemanagement.id, medicinemanagement.school, medicinemanagement.room, medicinemanagement.child_name,
                                medicinemanagement.m_name, medicinemanagement.date, medicinemanagement.mor, medicinemanagement.lun, 
                                medicinemanagement.din, medicine.morning, medicine.lunch, medicine.dinner                        
                                FROM medicinemanagement, medicine  
                                WHERE medicinemanagement.id='${id}'
                                AND medicinemanagement.child_name='${child_name}'
                                AND medicine.id='${id}'   AND medicine.child_name='${child_name}'
                                AND medicine.m_name=medicinemanagement.m_name`;
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