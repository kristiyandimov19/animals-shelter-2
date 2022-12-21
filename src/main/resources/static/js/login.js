
async function POST_login(email,pass){

    localStorage.removeItem("auth");
    localStorage.removeItem("token");
    localStorage.removeItem("user_id");

    let data = {
        'email': email,
        'password': pass,
    }
    let res = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });

    if (res.ok) {
        res.text().then(data => {
                localStorage.setItem("token", data.toString().split('"')[3]);
                localStorage.setItem("auth", parseJwt(localStorage.getItem("token")).auth);
                localStorage.setItem("user_id", parseJwt(localStorage.getItem("token")).user_id);
                window.location.replace("../html/index.html");
            }
        );
    } else {
        return "Error";
    }
}

async function login(){

    let email= document.getElementById("email").value;
    let pass = document.getElementById("password").value;
    let email_correct = true;
    let pass_correct = true;
    const mail_format = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

    if(email.length === 0){
        document.getElementById("email_req_error").style.display="block";
        document.getElementById("email_error").style.display="none";
        email_correct = false;
    }
    else if(!mail_format.test(email)){
        document.getElementById("email_error").style.display="block";
        document.getElementById("email_req_error").style.display="none";
        email_correct = false;
    }
    else{
        document.getElementById("email_error").style.display="none";
        document.getElementById("email_req_error").style.display="none";
    }

    if(pass.length == 0){
        document.getElementById("password_error").style.display= "block";
        pass_correct = false;
    }
    else{
        document.getElementById("password_error").style.display= "none";
    }



    if(email_correct && pass_correct){

        await POST_login(email, pass);
        //GET_login(username, password)
        //if admin
        // localStorage.setItem('auth','admin');
        // localStorage.setItem('user_id','1');
        // window.location.replace("./index.html");
        //
        // //else if user
        // localStorage.setItem('auth','user');
        // localStorage.setItem('user_id','1');
        // window.location.replace("./index.html");
        //
        // //else
        // console.log("Error");
    }




}