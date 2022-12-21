async function DELETE_adopt(animal_id) {

    let url1 = "http://localhost:8080/animal/delete/" + animal_id;

    let res = await fetch(url1, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

    if(res.ok){
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

function adopt(){
    let animal_id =new URLSearchParams(window.location.search).get("animal_id");
    DELETE_adopt(animal_id).then(data =>{
        if(data === "OK") window.location.replace("./index.html");
        else console.log(data);
    })
}