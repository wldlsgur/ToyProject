module.exports = {
    check_require_element : function(json_data){
        for(var key in json_data){
            if(!json_data[key]) return false;
        }
        return true;
    }
}