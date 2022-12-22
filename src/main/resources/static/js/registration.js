async function POST_register(){
    let url = 'http://localhost:8080/register';
    let data = {
        'username': document.getElementById("username").value,
        'password': document.getElementById("password").value,
        'email': document.getElementById("email").value,
    };

    let res = await fetch(url, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    });

    if (res.ok) {
        res.text().then(data => {
            localStorage.setItem("token", data.toString().split('"')[3]);
            window.location.replace("../html/index.html");
        });
    } else {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
        }).then( data =>{
            window.location.replace("../html/index.html")
        });
    }
}

function register(){
    let username= document.getElementById("username").value;
    let email = document.getElementById("email").value;
    let pass = document.getElementById("password").value;
    let _pass = document.getElementById("confirm_password").value;
    let email_correct = true;
    let username_correct = true;

    const mail_format = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

    if(username.length == 0){
        document.getElementById("username_error").style.display = "block";
        username_correct = false;
    }
    else{
        document.getElementById("username_error").style.display = "none";
    }

    if(email.length == 0){
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

    if(checkPassword(pass,_pass)){

        if(email_correct && username_correct) {
            POST_register();
        }
    }
}
function checkPassword(pass,_pass){
    //Password to be 8 symbols, to have small letter, big letter, and number
    const pass_format = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

    let pass_correct=true;

    if(!pass_format.test(pass)){
        //Err password
        document.getElementById("pass_error").style.display = "block";
        console.log("Password not format");
        pass_correct =false;
    }
    else{
        document.getElementById("pass_error").style.display = "none";
    }
    if(pass !=_pass ){
        document.getElementById("confirm_error").style.display = "block";
        console.log("Password not equal");
        pass_correct =false;

    }
    else {
        document.getElementById("confirm_error").style.display = "none";
    }

    return pass_correct;
}