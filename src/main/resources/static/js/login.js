function login(){

    localStorage.setItem('auth','admin');
    localStorage.setItem('user_id','1');

    window.location.replace("./index.html");
}