function setPage(){

    //Get All Users
    const Http = new XMLHttpRequest();
    const url = 'http://localhost:8080/users/all';
    Http.open("GET", url);
    Http.send();
    Http.getResponseHeader("Content-type");

    Http.onload = function() {
        const obj = JSON.parse(this.responseText);
        Object.entries(obj).forEach(([key,value]) => {

            const obje = document.createElement("li");
            const btn = document.createElement("a");
            obje.appendChild(btn);
            obje.classList.add("list-group-item");
            btn.setAttribute("href","history.html?user="+value.id);
            btn.innerText = value.username
            document.getElementById("users_list").appendChild(obje);
        });
    }
}