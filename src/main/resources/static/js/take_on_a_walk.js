async function PUT_takeAnimalOnWalk(animal_id) {

    var url = "http://localhost:8080/" + document.getElementById("FormSelector").value + "/animal/" + animal_id + "/walk";

    //Get result
    var res = await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

    //Check if OK
    if (res.ok){
        return "OK";
    }
    else{
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
        }).then( data =>{
            window.location.replace("../html/index.html")
        });
    }
}

async function GET_allAvailableUsers(){
    var url = 'http://localhost:8080/available';

    //Get result
    var res = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

    //Check if OK
    if(res.ok){
        var text = await res.text().then();
        const obj = JSON.parse(text);
        return obj;
    }
    else if(res.status == 401){
        localStorage.setItem("auth","guest");
        window.location.replace("../html/login.html")
    }
    else{
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
        }).then( data =>{
            window.location.replace("../html/index.html")
        });
    }
}

function setPage(){

    //Get Free Users
    GET_allAvailableUsers().then( obj =>{
        Object.entries(obj).forEach(([key,value]) => {

            //Create HTML object
            const option = document.createElement("option");
            option.innerText= value.username;
            option.value = value.id;

            document.getElementById("FormSelector").appendChild(option);
        });
    })
}

function takeAnimalOnWalk() {
    //Get user id
    var user_id = document.getElementById("FormSelector").value;

    if(user_id === ""){
        document.getElementById("user_error").style.display = "block";
        return;
    }

    var animal_id =new URLSearchParams(window.location.search).get("animal_id");

    //Check if animal is still available
    GET_Check(animal_id).then( data =>{
        if(data ==="OK"){
            PUT_takeAnimalOnWalk(animal_id).then( data=>{
                if(data === "OK")  window.location.replace("./index.html");
            });
        }else{
            window.location.replace("./index.html");
        }

    });
}