var numberOfPages = 0;
var currentPage = 1;

async function PUT_returnFromWalk(user_id, animal_id) {

    var url = "http://localhost:8080/" + user_id + "/animal/" + animal_id + "/return";

    //Get result
    var res = await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

    //Check if OK
    if(res.ok){
        return "OK";
    } else {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
        }).then( data =>{
            window.location.replace("../html/index.html")
        });
    }
}

async function GET_walker(animal_id){
    var url = "http://localhost:8080/animal/"+animal_id +"/volunteer";

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
        return obj.id;
    } else{
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
        }).then( data =>{
            window.location.replace("../html/index.html")
        });
    }
}

async function GET_checkToken(){
    var url = "http://localhost:8080/check";

    //Get result
    var res = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

    if(!res.ok){
        localStorage.removeItem("token");
    }
}

async function DELETE_adopt(animal_id) {

    var url = "http://localhost:8080/animal/adopt/" + animal_id;

    //Get result
    var res = await fetch(url, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

    //Check if OK
    if(res.ok){
        return "OK";
    } else {
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
        }).then( data =>{
            window.location.replace("../html/index.html")
        });
    }
}

async function GET_allAnimals(page) {
    var url = "http://localhost:8080/animal/page/" + page;

    //Get result
    var res = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    //Check if OK
    if(res.ok){
        var text = await res.text();
        const obj = JSON.parse(text);
        return obj;
    } else{
        Swal.fire({
            icon: 'error',
            title: 'Oops...',
            text: 'Something went wrong!',
        }).then( data =>{
            window.location.replace("../html/index.html")
        });
    }
}

function setAuth(){
    if(localStorage.getItem("token") != null){
        GET_checkToken().then(r => {
            setPage(0);
            setMenu();
        });
    }
    else {
        setPage(0);
        setMenu();
    }
}
function setPage(page) {



    document.getElementById("animals_list").innerHTML = "";
    currentPage += page;

    GET_allAnimals(currentPage - 1).then(obj => {

        console.log(obj);
        numberOfPages = obj.totalPages;
        setPaginationButtons(currentPage);

        Object.entries(obj.content).forEach(([key, value]) => {


            //Create HTML object
            const li = document.createElement("li");
            li.classList.add("list-group-item");
            if (key % 2 === 0) {
                li.classList.add("list-group-item-light");
            } else {
                li.classList.add("list-group-item-dark");
            }

            //Create text
            const p = document.createElement("p");
            if(value.availability){
                p.innerText = value.name + " - " + value.type + " - Available";
            } else p.innerText = value.name + " - " + value.type + " - On a walk";
            p.classList.add("inline_text");

            //Create image
            const img = document.createElement("img");
            if(value.type === "Dog")
                img.setAttribute("src","../images/icons8-dog-48.png");
            else if(value.type === "Cat")
                img.setAttribute("src","../images/icons8-cat-face-48.png");
            else if(value.type === "Cow")
                img.setAttribute("src","../images/icons8-cow-48.png");
            else if(value.type === "Sheep")
                img.setAttribute("src","../images/icons8-lamb-48.png");
            else
                img.setAttribute("src","../images/icons8-european-dragon-48.png");

            const div = document.createElement("div");
            div.appendChild(img);
            div.appendChild(p);
            li.appendChild(div);

            var auth = getAuth();

            //Create buttons
            if(auth === "admin"){
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

                div.after(a_walk);
                a_walk.after(a_return);
                a_return.after(a_adopt);
            }

            //Add object in the existing html code
            document.getElementById("animals_list").appendChild(li);
        });
    })
}

function takeOnAWalk(animal_id) {

    //Check if animal still available
    GET_Check(animal_id).then( data =>{
        if(data == "OK"){
            window.location.replace("./take_on_a_walk.html?animal_id="+animal_id);
        } else {
            window.location.replace("./index.html");
        }
    });
}

function returnFromAWalk(animal_id){

    //Get walker that took the animal
    GET_walker(animal_id).then( id =>{

        //Return animal
        PUT_returnFromWalk(id,animal_id).then(data=>{
            if(data === "OK") window.location.replace("./return_from_a_walk.html?user_id="+id);
            else console.log(data);
        });
    });
}

function adopt(animal_id){

    //Check if animal still available
    GET_Check(animal_id).then( data =>{
        if (data ==="OK"){
            //Ask for confirmation
            Swal.fire({
                title: 'Are you sure?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes!',
                cancelButtonText: 'No'
            }).then((result) => {
                if (result.isConfirmed) {
                    //Delete animal from DB
                    DELETE_adopt(animal_id).then( data =>{
                        if(data === "OK"){
                            //Show that animal is adopted
                            Swal.fire({
                                imageUrl: '../images/yesss-bye-beach.gif',
                                confirmButtonText: 'OK!'
                            }).then( () =>{
                                window.location.replace("./index.html");
                            })
                        }
                    })
                }
            })
        } else{
            window.location.replace("./index.html");
        }
    })
}
