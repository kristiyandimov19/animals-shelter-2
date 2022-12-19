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

            const p = document.createElement("p");
            p.innerText = value.name + " - " + value.type + " - " + value.username;
            li.appendChild(p);

            const li = document.createElement("li");
            li.classList.add("list-group-item");
            if(key%2==0){
                li.classList.add("list-group-item-light");
            }
            else{
                li.classList.add("list-group-item-dark");
            }

            document.getElementById("animals_list").appendChild(li);
        });
    }
}