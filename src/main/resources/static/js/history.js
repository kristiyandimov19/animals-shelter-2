var numberOfPages =0;
var currentPage = 1;
async function GET_Walks(user_id,page){
    let url = "http://localhost:8080/"+ user_id +"/walks/page/" + page;

    //Get result
    let res = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

    //Check if OK
    if(res.ok){
        let text = await res.text().then();
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

function showWalks(user_id,page){
    //Get all walks of user
    document.getElementById("walk-history").innerHTML=""

    currentPage+=page;


    GET_Walks(user_id,currentPage-1).then( obj =>{
        //Set pagination
        numberOfPages = obj.totalPages;

        setPaginationButtons(currentPage);

        Object.entries(obj.content).forEach(([key,value]) => {

            //Create HTML structure
            let p = document.createElement("p");
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

            let li = document.createElement("li");
            li.classList.add("list-group-item");

            li.appendChild(div);
            document.getElementById("walk-history").appendChild(li);
        });
    })
}

function setPage(){
    let user_id= getUser_id();
    showWalks(user_id,0);

}