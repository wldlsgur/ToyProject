const db_create_sql = require('../public/SQL/create_sql')();
const make_query = require('./make_query');

const element_msg = "plz send require elements";
const sucess_response = {res : true, msg : 'success'};
const failed_response = {res : false, msg : "failed"};

module.exports = {
    update_image_url : function(target, key, file_url){
		let query = ``;
		switch(target){
			case 'parent':
				query = `UPDATE parentinfo SET image_url='${file_url}' WHERE key_id = '${key}';`
				break;
			case 'food':
				query = `UPDATE food_list SET image_url='${file_url}' WHERE key_id = '${key}';`
				break;
			case 'teacher':
				query = `UPDATE teacherinfo SET image_url='${file_url}' WHERE id = '${key}';`
				break;
			case 'president':
				query = `UPDATE presidentinfo SET image_url='${file_url}' WHERE id = '${key}';`
				break;
		}
        console.log(query);
		db_create_sql.UPDATE(query, function(err, result){
			if(err){
				return false;
			}
			return true;
		})
    },
	insert_image_array_album : function(file_url, album_school, album_room , album_title, album_date){
		let json_data = {
			school : album_school,
			room : album_room,
			title : album_title,
			date : album_date,
			image_url : file_url
		};
	
		let query = make_query.INSERT('album', json_data);
		db_create_sql.INSERT(query, function(err, result){
			if(err){
				return false;
			}
			return true;
		})
	}
}