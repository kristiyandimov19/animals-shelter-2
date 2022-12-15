async function POST_addComment(user_id){
    let url = 'http://localhost:8080/----';
    let data = {
        'user_id': user_id.toString(),
        'comment' : document.getElementById("Textarea").value,
    };

    let res = await fetch(url, {
        method: 'POST',
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
    let user_id =new URLSearchParams(window.location.search).get("user_id");
    POST_addComment(user_id).then(data=>{
        if(data == "OK"){
            window.location.replace("./index.html");
        }
        else console.log(data);
    });
}

function skip(){
    window.location.replace("./index.html");
}