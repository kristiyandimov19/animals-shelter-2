function setPage(){

    //Get animals on walk
    const Http = new XMLHttpRequest();
    const url = 'http://localhost:8080/animal/onWalk';
    Http.open("GET", url);
    Http.send();
    Http.getResponseHeader("Content-type");

    Http.onload = function() {
        const obj = JSON.parse(this.responseText);

        Object.entries(obj).forEach(([key,value]) => {

            const obje = document.createElement("li");
            obje.classList.add("list-group-item");
            const para = document.createElement("p");

            para.innerText = value.name + " - " + value.type + " - " + value.username;
            obje.appendChild(para);

            document.getElementById("animals_list").appendChild(obje);
        });
    }
}