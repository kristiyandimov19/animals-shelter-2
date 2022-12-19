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
            btn.setAttribute("onclick","showHistory('"+value.id+"')");
            btn.setAttribute("href","#");
            btn.innerText = value.username
            btn.classList.add("button");
            document.getElementById("users_list").appendChild(obje);
        });
    }
}

function showHistory(user_id) {
    //document.getElementById("walk-history").innerHTML="";
    document.getElementById("comment-history").innerHTML="";
    showComments(user_id);
    showWalks(user_id);
}

function showComments(user_id){
    const Http = new XMLHttpRequest();
    const url = "http://localhost:8080/users/comments/"+user_id;
    Http.open("GET", url);
    Http.send();
    Http.getResponseHeader("Content-type");

    Http.onload = function() {
        //Get all the animals in json
        const obj = JSON.parse(this.responseText);


        //Loop all the animals
        Object.entries(obj).forEach(([key,value]) => {
            console.log(value);

            let h5 = document.createElement("h5");
            h5.classList.add("card-title");
            h5.innerText = value.author;
            let p = document.createElement("p");
            p.classList.add("card-text")
            p.innerText = value.description;

            let div_inner = document.createElement("div");
            div_inner.classList.add("card-body");
            div_inner.appendChild(h5);
            div_inner.appendChild(p);

            let li = document.createElement("li");
            li.classList.add("list-group-item");
            li.appendChild(div_inner);

            document.getElementById("comment-history").appendChild(li);
        });
    }
}