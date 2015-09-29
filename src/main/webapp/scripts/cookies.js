function GetCookie(name) {
    var arg=name+"=";
    var arglen=arg.length;
    var cookilen=document.cookie.length;
    var i=0;

    while (i<cookilen) {
        var j=i+arglen;

        if (document.cookie.substring(i,j)==arg)
            return "1";
        
        i=document.cookie.indexOf(" ",i)+1;
        
        if (i==0)
            break;
    }
    return null;
}

function iLikeCookies(){
    var expireDate=new Date();
    var twoHours = 1000*60*60*2;
    expireDate=new Date(expireDate.getTime()+twoHours);
    document.cookie="cookies_DP=aceptada; expires="+expireDate;
    var visit=GetCookie("cookies_DP");

    if (visit==1){
        popbox();
    }
}

$(function() {
    var visit=GetCookie("cookies_DP");
    if (visit==1){
    	popbox(); 
    }
});

function popbox() {
    $('#overbox').toggle();
}