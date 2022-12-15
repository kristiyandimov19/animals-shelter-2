async function PUT_takeAnimalOnWalk(animal_id) {

    let url1 = "http://localhost:8080/users/takeOnWalk/" + document.getElementById("FormSelector").value + "/" + animal_id;

    await fetch(url1, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
    });
}

function setPage(){
    //Get Free Users
    const Http = new XMLHttpRequest();
    const url = 'http://localhost:8080/users/available';
    Http.open("GET", url);
    Http.send();
    Http.getResponseHeader("Content-type");

    Http.onload = function() {
        const obj = JSON.parse(this.responseText);

        Object.entries(obj).forEach(([key,value]) => {

            const opti = document.createElement("option");
            opti.innerText= value.username;
            opti.value = value.id;

            document.getElementById("FormSelector").appendChild(opti);
        });
    }
}

function takeAnimalOnWalk() {
    let animal_id =new URLSearchParams(window.location.search).get("animal_id");
    //Check if animal is available
    const Http1 = new XMLHttpRequest();

    const url1 = "http://localhost:8080/animal/isAvailable/"+animal_id;
    Http1.open("GET", url1);
    Http1.send();
    Http1.getResponseHeader("Content-type");


    Http1.onload = function() {
        const obj = JSON.parse(this.responseText);
        if(obj.availability){
            PUT_takeAnimalOnWalk(animal_id).then( ()=>{
                window.location.replace("./index.html");
            });
        }else{
            console.log("Error");

        }
    }



}