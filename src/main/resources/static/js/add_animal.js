async function POST_addAnimal(){
    let url = 'http://localhost:8080/animal/create';
    let data = {
        'name': document.getElementById("name").value,
        'type': document.getElementById("type").value,
    };

    let res = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    });

    if (res.ok) {
        return "OK"
    } else {
        return `HTTP error: ${res.status}`;
    }
}

function addAnimal(){
    let name = document.getElementById("name").value;

    if(name.length == 0 ){
        document.getElementById("name_error").style.display="block";
        document.getElementById("name_length_error").style.display="none";
        return;
    }
    else if(name.length <2){
        document.getElementById("name_length_error").style.display="block";
        document.getElementById("name_error").style.display="none";
        return;
    }
    else{
        document.getElementById("name_error").style.display="none";
        document.getElementById("name_length_error").style.display="none";
    }

    POST_addAnimal().then(data=>{
        if(data == "OK") {
            window.location.replace("./index.html");

        }
        else console.log(data);
    });
}