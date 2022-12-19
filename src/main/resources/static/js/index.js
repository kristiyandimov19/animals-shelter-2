async function PUT_returnFromWalk(user_id,animal_id) {

    let url1 = "http://localhost:8080/users/returnFromWalk/" + user_id + "/" + animal_id;

    await fetch(url1, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
    });
}

async function DELETE_adopt(animal_id) {

    let url1 = "http://localhost:8080/animal/delete/" + animal_id;

    await fetch(url1, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    });
}

function setPage() {
    //Get All Animals
    const Http = new XMLHttpRequest();
    const url = 'http://localhost:8080/animal/all';
    Http.open("GET", url);
    Http.send();
    Http.getResponseHeader("Content-type");

    Http.onload = function() {
        //Get all the animals in json
        const obj = JSON.parse(this.responseText);


        //Loop all the animals
        Object.entries(obj).forEach(([key,value]) => {

            //Create list object
            const li = document.createElement("li");
            li.classList.add("list-group-item");
            if(key%2==0){
                li.classList.add("list-group-item-light");
            }
            else{
                li.classList.add("list-group-item-dark");
            }

            //Create text
            const p = document.createElement("p");
            if(value.availability){
                p.innerText = value.name + " - " + value.type + " - Available";
            }
            else p.innerText = value.name + " - " + value.type + " - On a walk";
            li.appendChild(p);

            //Create buttons
            if(localStorage.getItem('auth') === "admin"){
                const a_walk = document.createElement("a");
                const a_return = document.createElement("a");
                const a_adopt = document.createElement("a");


                a_walk.setAttribute("href","#");
                a_walk.innerText = "Take on walk";
                a_walk.setAttribute("onclick","takeOnAWalk('"+ value.id +"')");

                a_walk.classList.add("btn","btn-secondary","right_margin");
                if(!value.availability){
                    a_walk.style.display="none";
                }

                a_return.setAttribute("href","#");
                a_return.innerText = "Return";
                a_return.setAttribute("onclick","returnFromAWalk('" + value.id + "')");

                a_return.classList.add("btn","btn-secondary","right_margin");
                if(value.availability){
                    a_return.style.display="none";
                }


                a_adopt.setAttribute("href","#");
                a_adopt.innerText = "Adopt";
                a_adopt.setAttribute("onclick","adopt('" + value.id + "')");

                a_adopt.classList.add("btn","btn-secondary","right_margin");
                if(!value.availability){
                    a_adopt.style.display="none";
                }

                p.after(a_walk);
                a_walk.after(a_return);
                a_return.after(a_adopt);
            }

            //Add object in the existing html code
            document.getElementById("animals_list").appendChild(li);
        });
    }

}

function takeOnAWalk(animal_id) {

    const Http1 = new XMLHttpRequest();

    const url1 = "http://localhost:8080/animal/isAvailable/"+animal_id;
    Http1.open("GET", url1);
    Http1.send();
    Http1.getResponseHeader("Content-type");


    Http1.onload = function() {
        const obj = JSON.parse(this.responseText);
        if(obj.availability){
            window.location.replace("./take_on_a_walk.html?animal_id="+animal_id);
        }else{
            window.location.replace("./index.html");
        }
    }
}

function returnFromAWalk(animal_id){
    const Http1 = new XMLHttpRequest();

    const url1 = "http://localhost:8080/animal/volunteer/"+animal_id;
    Http1.open("GET", url1);
    Http1.send();
    Http1.getResponseHeader("Content-type");

    Http1.onload = function() {
        const obj = JSON.parse(this.responseText);
        PUT_returnFromWalk(obj.id,animal_id).then(()=>{
            window.location.replace("./return_from_a_walk.html?user_id="+obj.id);
        })
    }
}

function adopt(animal_id){

    const Http1 = new XMLHttpRequest();

    const url1 = "http://localhost:8080/animal/isAvailable/"+animal_id;
    Http1.open("GET", url1);
    Http1.send();
    Http1.getResponseHeader("Content-type");


    Http1.onload = function() {
        const obj = JSON.parse(this.responseText);
        if(obj.availability){
            Swal.fire({
                title: 'Are you sure?',

                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes!'
            }).then((result) => {
                if (result.isConfirmed) {

                    Swal.fire({
                        imageUrl: '../img/yesss-bye-beach.gif',
                        confirmButtonText: 'OK!'
                    }).then(() =>{
                        DELETE_adopt(animal_id).then(()=>{
                            window.location.replace("./index.html");
                        })
                    })
                }
            })
        }else{
            window.location.replace("./index.html");
        }
    }
}
