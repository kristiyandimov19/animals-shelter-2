async function PUT_addComment(user_id){
    let url = 'http://localhost:8080/users/comment/add';
    let data = {
        'authorId': localStorage.getItem("user_id"),
        'userId': user_id.toString(),
        'description' : document.getElementById("comment").value,
    };

    let res = await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    });

    if (res.ok) {
        return "OK";
    } else {
        return `HTTP error: ${res.status}`;
    }
}
function addComment(){
    let comment = document.getElementById("comment").value;
    if(comment.length == 0){
        document.getElementById("comment_error").style.display = "block";
        return;
    }
    else{
        document.getElementById("comment_error").style.display = "none";
    }
    let user_id =new URLSearchParams(window.location.search).get("user_id");
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