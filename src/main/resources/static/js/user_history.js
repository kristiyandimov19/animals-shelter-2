async function GET_Comments(user_id){
    let url = "http://localhost:8080/"+user_id+"/comments";

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

async  function GET_Users(){
    let url = 'http://localhost:8080/all';

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

function setPagination(user_id){
    //Change user
    var pagination_arrows = document.getElementsByClassName("page-link");
    pagination_arrows[0].setAttribute("onclick","showWalks('"+user_id+"',-1)");
    pagination_arrows[1].setAttribute("onclick","showWalks('"+user_id+"',1)");
}

function setPage(){

    document.getElementById("walks_title").style.display = "none";
    document.getElementById("comment_title").style.display = "none";
    document.getElementById("pagination-container").style.display = "none";

    //Get All Users
    GET_Users().then( obj =>{
        Object.entries(obj).forEach(([key,value]) => {

            //Create HTML object
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

    document.getElementById("comment_title").style.display = "block";
    document.getElementById("walks_title").style.display="block";
    document.getElementById("pagination-container").style.display = "block";


    setPagination(user_id);
    //Clear previous history
    document.getElementById("walk-history").innerHTML="";
    document.getElementById("comment-history").innerHTML="";

    //Show current history

    showComments(user_id);
    showWalks(user_id,0);
}

function showComments(user_id){

    GET_Comments(user_id).then( obj =>{
        Object.entries(obj).forEach(([key,value]) => {

            //Create HTML object
            let h5 = document.createElement("h5");
            h5.classList.add("card-title");
            h5.innerText = "By " + value.author + ":";


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