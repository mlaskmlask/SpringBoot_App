function validate(){
    var login = document.forms["loginForm"]["login"].value;
    var pass = document.forms["loginForm"]["pass"].value;

    var regex = new RegExp(/.{3}.*/);
    var flag = true;
    if(!regex.test(login)) {
    document.forms["loginForm"]["login"].style.background="red";
    flag = false;
    }
    if(!regex.test(pass)) {
        document.forms["loginForm"]["pass"].style.background="red";
    flag = false;
    }
    return flag;
}