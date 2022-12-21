async function GET_Walks(user_id){
    let url1 = "http://localhost:8080/users/walks/" + user_id;

    let res = await fetch(url1, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem("token"),
        },
    });

    if(res.ok){
        let text = await res.text().then();
        const obj = JSON.parse(text);
        return obj;
    }
}
function showWalks(user_id){
    //Get walks
    GET_Walks(user_id).then( obj =>{
        Object.entries(obj).forEach(([key,value]) => {
            let p = document.createElement("p");
            p.innerText = value.animalName + " - " + value.animalType + " - " + value.localDate;

            let li = document.createElement("li");
            li.classList.add("list-group-item");

            li.appendChild(p);
            document.getElementById("walk-history").appendChild(li);
        });
    })
}
function setPage(){
    const urlParams = new URLSearchParams(window.location.search);
    let user_id=localStorage.getItem("user_id");
    showWalks(user_id);
}