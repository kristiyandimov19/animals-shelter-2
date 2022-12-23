async function GET_Walks(user_id){
    var url = "http://localhost:8080/users/walks/" + user_id;

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
        return obj;
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

function showWalks(user_id){
    //Show title
    document.getElementById("walks_title").style.display="block";

    //Get all walks of user
    GET_Walks(user_id).then( obj =>{
        Object.entries(obj).forEach(([key,value]) => {

            //Create HTML structure
            var p = document.createElement("p");
            p.innerText = value.animalName + " - " + value.animalType + " - " + value.localDate;
            p.classList.add("inline_text");

            const img = document.createElement("img");
            if(value.animalType === "Dog")
                img.setAttribute("src","../images/icons8-dog-48.png");
            else if(value.animalType === "Cat")
                img.setAttribute("src","../images/icons8-cat-face-48.png");
            else if(value.animalType === "Cow")
                img.setAttribute("src","../images/icons8-cow-48.png");
            else if(value.animalType === "Sheep")
                img.setAttribute("src","../images/icons8-lamb-48.png");
            else
                img.setAttribute("src","../images/icons8-european-dragon-48.png");

            const div = document.createElement("div");
            div.appendChild(img);
            div.appendChild(p);

            var li = document.createElement("li");
            li.classList.add("list-group-item");

            li.appendChild(div);
            document.getElementById("walk-history").appendChild(li);
        });
    })
}

function setPage(){
    var user_id= getUser_id();
    showWalks(user_id);
}