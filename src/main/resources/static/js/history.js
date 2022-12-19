function showWalks(user_id){
    //Get walks
    const Http = new XMLHttpRequest();
    const url = "http://localhost:8080/users/walks/" + user_id;
    Http.open("GET", url);
    Http.send();
    Http.getResponseHeader("Content-type");

    Http.onload = function() {
        //Get all the animals in json
        const obj = JSON.parse(this.responseText);
        //Loop all the animals
        Object.entries(obj).forEach(([key,value]) => {

            let p = document.createElement("p");
            p.innerText = value.animalName + " - " + value.animalType + " - " + value.localDate;

            let li = document.createElement("li");
            li.classList.add("list-group-item");

            li.appendChild(p);
            document.getElementById("walk-history").appendChild(li);
        });
    }

}
function setPage(){
    const urlParams = new URLSearchParams(window.location.search);
    let user_id=localStorage.getItem("user_id");
    showWalks(user_id);
}