async function GET_Check(animal_id){
    let url1 = "http://localhost:8080/animal/isAvailable/"+animal_id;

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
        if(obj.availability){
            return "OK";
        }
        else {
            return "ERROR";
        }
    }
}

function setMenu(){
     if (localStorage.getItem('auth') === "guest") {
         let menu_items = document.getElementsByClassName("nav-link");

         menu_items[1].classList.add("disabled");
         menu_items[2].classList.add("disabled");
         menu_items[3].classList.add("disabled");
         menu_items[5].style.display = "none";
     } else if (localStorage.getItem('auth') === "admin") {
         let menu_items = document.getElementsByClassName("nav-link");
         menu_items[2].setAttribute("href","./user_history.html");
         menu_items[4].style.display = "none";
     } else {
         let menu_items = document.getElementsByClassName("nav-link");
         menu_items[1].classList.add("disabled");
         menu_items[3].classList.add("disabled");
         menu_items[4].style.display = "none";
     }
 }

 function logout(){

     Swal.fire({
         title: 'Are you sure you want to log off?',

         showCancelButton: true,
         confirmButtonColor: '#3085d6',
         cancelButtonColor: '#d33',
         confirmButtonText: 'Yes!',
         cancelButtonText: 'No'
     }).then((result) => {
         if (result.isConfirmed) {
             localStorage.setItem('auth','guest');
             localStorage.setItem('user_id','-1');
             localStorage.removeItem("token");
             window.location.replace("../html/index.html");
         }
     });

 }

 function parseJwt (token) {
    console.log(token.split('.')[1]);

    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
         return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
     }).join(''));

     return JSON.parse(jsonPayload);
 }