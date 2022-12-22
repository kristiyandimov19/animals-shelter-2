async function GET_onWalk(){
    let url = 'http://localhost:8080/animal/onWalk';

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

function setPage(){

    //Get animals on walk
   GET_onWalk().then( obj =>{
       Object.entries(obj).forEach(([key,value]) => {

           //Create HTML object
           const p = document.createElement("p");
           p.innerText = value.name + " - " + value.type + " - " + value.username;
           p.classList.add("inline_text");


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


           const li = document.createElement("li");
           li.classList.add("list-group-item");
           if(key%2===0){
               li.classList.add("list-group-item-light");
           }
           else{
               li.classList.add("list-group-item-dark");
           }
           li.appendChild(div);


           document.getElementById("animals_list").appendChild(li);
       });
   })
}