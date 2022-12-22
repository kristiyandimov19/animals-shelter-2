async function GET_Check(animal_id){
    let url = "http://localhost:8080/animal/isAvailable/"+animal_id;

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
        let text = await res.text();
        const obj = JSON.parse(text);
        if(obj.availability){
            return "OK";
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
}

function setMenu(){
    let menu_items = document.getElementsByClassName("nav-link");
    let auth = getAuth();


     if (auth === "guest") {
         //If guest make HOME and LOGIN clickable, remove LOGOUT button
         menu_items[1].classList.add("disabled");
         menu_items[2].classList.add("disabled");
         menu_items[3].classList.add("disabled");
         menu_items[5].parentElement.remove();

     } else if (auth === "admin") {
         //If admin set HISTORY to user_history.html, remove LOGIN button
         menu_items[2].setAttribute("href","./user_history.html");
         menu_items[4].parentElement.remove();


     } else {
         //If user set HOME, HISTORY and LOGOUT clickable, remove LOGIN button
         menu_items[1].classList.add("disabled");
         menu_items[3].classList.add("disabled");
         menu_items[4].parentElement.remove();
     }
 }

 function logout(){

    //Use Swal alert to ask for confirmation
     Swal.fire({
         title: 'Are you sure you want to log off?',

         showCancelButton: true,
         confirmButtonColor: '#3085d6',
         cancelButtonColor: '#d33',
         confirmButtonText: 'Yes!',
         cancelButtonText: 'No'
     }).then((result) => {

         if (result.isConfirmed) {

             //Remove token from localStorage
             localStorage.removeItem("token");

             Swal.fire({
                 imageUrl: '../images/bye-bye-bye.gif',
                 confirmButtonText: 'OK!'
             }).then(() => {
                 window.location.replace("./index.html");
             })
         }
     });
 }

 function parseJwt (token) {

    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
         return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
     }).join(''));

     return JSON.parse(jsonPayload);
 }

 function getAuth(){
     if(localStorage.getItem("token") != null)
         return parseJwt(localStorage.getItem("token")).auth;
     else
         return "guest"
}

function getUser_id(){
    if(localStorage.getItem("token") != null)
        return parseJwt(localStorage.getItem("token")).user_id;
    else
        return "-1";
}