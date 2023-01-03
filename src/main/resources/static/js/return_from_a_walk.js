async function PUT_addComment(user_id){
    var url = 'http://localhost:8080/comment';
    var data = {
        'authorId': getUser_id(),
        'userId': user_id.toString(),
        'description' : document.getElementById("comment").value,
    };

    //Get result
    var res = await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
        body: JSON.stringify(data)
    });
    //Check if OK
    if (res.ok) {
        return "OK";
    }
    else {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
        }).then( data =>{
            window.location.replace("../html/index.html")
        });
    }
}

function addComment(){
    //Get the comment
    var comment = document.getElementById("comment").value;

    //Check if the comment is correct
    if(comment.length == 0){
        document.getElementById("comment_error").style.display = "block";
        return;
    }
    else{
        document.getElementById("comment_error").style.display = "none";
    }

    //Get user for the comment
    var user_id =new URLSearchParams(window.location.search).get("user_id");

    //Put comment
    PUT_addComment(user_id).then(data=>{
        if(data === "OK"){
            window.location.replace("./index.html");
        }
        else console.log(data);
    });
}

function skip(){
    window.location.replace("./index.html");
}