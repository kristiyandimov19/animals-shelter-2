async function PUT_takeAnimalOnWalk(animal_id) {

    let url = "http://localhost:8080/users/takeOnWalk/" + document.getElementById("FormSelector").value + "/" + animal_id;

    //Get result
    let res = await fetch(url, {
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
    else if(res.status == 401){
        console.log(res.status)
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
async function GET_allAvailableUsers(){
    let url = 'http://localhost:8080/users/available';

    //Get result
    let res = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

    //Check if OK
    if(res.ok){
        let text = await res.text().then();
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
    let user_id = document.getElementById("FormSelector").value;

    if(user_id === ""){
        document.getElementById("user_error").style.display = "block";
        return;
    }

    let animal_id =new URLSearchParams(window.location.search).get("animal_id");

    //Check if animal is still available
    GET_Check(animal_id).then( data =>{
        if(data ==="OK"){
            PUT_takeAnimalOnWalk(animal_id).then( data=>{
                if(data === "OK")  window.location.replace("./index.html");
                else console.log(data);
            });
        }else{
            window.location.replace("./index.html");
        }

    });
}