async function PUT_takeAnimalOnWalk(animal_id) {

    let url1 = "http://localhost:8080/users/takeOnWalk/" + document.getElementById("FormSelector").value + "/" + animal_id;

    let res = await fetch(url1, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

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
    let url1 = 'http://localhost:8080/users/available';
    let res = await fetch(url1, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });
    if(res.ok){
        let text = await res.text().then();
        const obj = JSON.parse(text);
        return obj;
    }
}
function setPage(){
    //Get Free Users
    GET_allAvailableUsers().then( obj =>{
        Object.entries(obj).forEach(([key,value]) => {

            const option = document.createElement("option");
            option.innerText= value.username;
            option.value = value.id;

            document.getElementById("FormSelector").appendChild(option);
        });
    })
}

function takeAnimalOnWalk() {
    let animal_id =new URLSearchParams(window.location.search).get("animal_id");
    //Check if animal is available

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