var cal = {
    inputbox : document.querySelector("#input"),
    ouputbox : document.querySelector("#output"),
    lastdatatpye : "",
    add : function(data){
        if(typeof(data) === "string")
        {
            if(this.inputbox.value === "" || this.lastdatatpye === "string")
            {
                alert("숫자를 입력해주세요!!");
                return;
            }
        }
            console.log(this.lastdatatpye);
            this.lastdatatpye = typeof(data);
            this.inputbox.value += data;
    },
    clear : function(){
        this.inputbox.value = "";
        this.ouputbox.value = "";
        this.lastdatatpye = "";
    },
    result : function(){
        this.ouputbox.value = eval(this.inputbox.value);
    }
}