async function GET_Comments(user_id){
    let url1 = "http://localhost:8080/users/comments/"+user_id;
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

async  function GET_Users(){
    let url1 = 'http://localhost:8080/users/all';
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
    //Get All Users
    GET_Users().then( obj =>{
        Object.entries(obj).forEach(([key,value]) => {

            const a = document.createElement("a");
            a.setAttribute("onclick","showHistory('"+value.id+"')");
            a.setAttribute("href","#");
            a.innerText = value.username
            a.classList.add("users_btn");

            const li = document.createElement("li");
            li.classList.add("list-group-item");
            li.appendChild(a);

            document.getElementById("users_list").appendChild(li);
        });
    });
}

function showHistory(user_id) {
    document.getElementById("walk-history").innerHTML="";
    document.getElementById("comment-history").innerHTML="";
    showComments(user_id);
    showWalks(user_id);
}

function showComments(user_id){
    GET_Comments(user_id).then( obj =>{
        Object.entries(obj).forEach(([key,value]) => {
            console.log(obj);
            let h5 = document.createElement("h5");
            h5.classList.add("card-title");
            h5.innerText = value.author;
            let p = document.createElement("p");
            p.classList.add("card-text")
            p.innerText = value.description;

            let div = document.createElement("div");
            div.classList.add("card-body");
            div.appendChild(h5);
            div.appendChild(p);

            let li = document.createElement("li");
            li.classList.add("list-group-item");
            li.appendChild(div);

            document.getElementById("comment-history").appendChild(li);
        });
    });
}