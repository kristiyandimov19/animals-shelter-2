async function GET_Comments(user_id){
    var url = "http://localhost:8080/users/comments/"+user_id;

    //Get result
    var res = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

    //Check if OK
    if(res.ok){
        var text = await res.text().then();
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
    var url = 'http://localhost:8080/users/all';

    //Get result
    var res = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

    //Check if OK
    if(res.ok){
        var text = await res.text().then();
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

function setPage(){

    document.getElementById("walks_title").style.display = "none";
    document.getElementById("comment_title").style.display = "none";

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

    //Clear previous history
    document.getElementById("walk-history").innerHTML="";
    document.getElementById("comment-history").innerHTML="";

    //Show current history
    showComments(user_id);
    showWalks(user_id);
}

function showComments(user_id){

    document.getElementById("comment_title").style.display = "block";

    GET_Comments(user_id).then( obj =>{
        Object.entries(obj).forEach(([key,value]) => {

            //Create HTML object
            var h5 = document.createElement("h5");
            h5.classList.add("card-title");
            h5.innerText = "By " + value.author + ":";


            var p = document.createElement("p");
            p.classList.add("card-text")
            p.innerText = value.description;


            var div = document.createElement("div");
            div.classList.add("card-body");
            div.appendChild(h5);
            div.appendChild(p);

            var li = document.createElement("li");
            li.classList.add("list-group-item");
            li.appendChild(div);

            document.getElementById("comment-history").appendChild(li);
        });
    });
}