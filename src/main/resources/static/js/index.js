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

            //Create objects
            const obje = document.createElement("li");
            obje.classList.add("list-group-item");
            if(key%2==0){
                obje.classList.add("list-group-item-light");
            }
            else{
                obje.classList.add("list-group-item-dark");
            }
            const para = document.createElement("p");
            if(value.availability){
                para.innerText = value.name + " - " + value.type + " - Available";
            }
            else para.innerText = value.name + " - " + value.type + " - On a walk";
            obje.appendChild(para);

            if(localStorage.getItem('auth') === "admin"){
                const walk = document.createElement("a");
                const retu = document.createElement("a");
                const adop = document.createElement("a");


                walk.setAttribute("href","#");
                walk.innerText = "Take on walk";
                walk.setAttribute("onclick","takeOnAWalk('"+ value.id +"')");

                walk.classList.add("btn","btn-secondary","right_margin");
                if(!value.availability){
                    walk.style.display="none";
                }

                retu.setAttribute("href","#");
                retu.innerText = "Return";
                retu.setAttribute("onclick","returnFromAWalk('" + value.id + "')");

                retu.classList.add("btn","btn-secondary","right_margin");
                if(value.availability){
                    retu.style.display="none";
                }


                adop.setAttribute("href","#");
                adop.innerText = "Adopt";
                adop.setAttribute("onclick","adopt('" + value.id + "')");

                adop.classList.add("btn","btn-secondary","right_margin");
                if(!value.availability){
                    adop.style.display="none";
                }

                para.after(walk);
                walk.after(retu);
                retu.after(adop);
            }

            //Add object in the existing html code
            document.getElementById("animals_list").appendChild(obje);
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
