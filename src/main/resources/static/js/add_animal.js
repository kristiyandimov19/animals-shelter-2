async function POST_addAnimal(){
    let url = 'http://localhost:8080/animal';

    let data = {
        'name': document.getElementById("name").value,
        'type': document.getElementById("type").value,
    };

    //Get result
    var res = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),

        },
        body: JSON.stringify(data)
    });

    //Check if OK
    if (res.ok) {

        return "OK"
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

function addAnimal(){
    //Get name
    var name = document.getElementById("name").value;

    //Check if name is right
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