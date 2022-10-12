module.exports = {
    INSERT : function(table, json_data){
        let make_query = `INSERT INTO ${table} (`;
        
        for(key of Object.keys(json_data)){//파싱해도 key는 Object 문자열을 넣을려면 이 반복문을 고정적으로 사용해야한다
            make_query += `${key},`;
        }
        make_query = make_query.substring(0, make_query.length - 1);//마지막, 제거
        make_query += `) VALUES(`;

        for(key of Object.keys(json_data)){
            make_query += `'${json_data[key]}',`;
        }
        make_query = make_query.substring(0, make_query.length - 1);//마지막, 제거
        make_query += `)`;

        console.log(make_query);
        return make_query;
    },
    SELECT : function(target, table, json_data, or_and, cnt){
        let make_query = `SELECT `;

        for(let i=0 ; i<target.length ; i++){
            make_query += `${target[i]},`;
        }
        make_query = make_query.substring(0, make_query.length - 1);//마지막, 제거

        make_query += ` FROM ${table} WHERE `;

        let or_and_cnt = 0;
        for(key of Object.keys(json_data)){
            make_query += `${key}='${json_data[key]}' `;
            if(or_and_cnt != cnt){
                make_query += `${or_and} `;
                or_and_cnt++;
            }
        }
        make_query = make_query.substring(0, make_query.length - 1);//마지막 공백 제거
        console.log(make_query);
        return make_query;
    },
    UPDATE : function(target, table, json_data, or_and, cnt){
        let make_query = `UPDATE ${table} SET ${target} WHERE `;
        
        let or_and_cnt = 0;
        for(key of Object.keys(json_data)){
            make_query += `${key}='${json_data[key]}' `;
            if(or_and_cnt != cnt){
                make_query += `${or_and} `;
                or_and_cnt++;
            }
        }
        make_query = make_query.substring(0, make_query.length - 1);//마지막 공백 제거
        console.log(make_query);
        return make_query;
    },
    DELETE : function(table, json_data, or_and, cnt){
        let make_query = `DELETE FROM ${table} WHERE `;

        let or_and_cnt = 0;
        for(key of Object.keys(json_data)){
            make_query += `${key}='${json_data[key]}' `;
            if(or_and_cnt != cnt){
                make_query += `${or_and} `;
                or_and_cnt++;
            }
        }
        make_query = make_query.substring(0, make_query.length - 1);//마지막 공백 제거
        console.log(make_query);
        return make_query;
    }
}