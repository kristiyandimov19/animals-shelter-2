async function DELETE_adopt(animal_id) {

    let url1 = "http://localhost:8080/animal/delete/" + animal_id;

    await fetch(url1, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    });
}

function adopt(){
    let animal_id =new URLSearchParams(window.location.search).get("animal_id");
    DELETE_adopt(animal_id).then(()=>{
        window.location.replace("./index.html");
    })
}