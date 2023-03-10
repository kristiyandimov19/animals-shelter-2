async function GET_Check(animal_id){
    var url = "http://localhost:8080/animal/isAvailable/"+animal_id;

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
        var text = await res.text();
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
    var menu_items = document.getElementsByClassName("nav-link");
    var auth = getAuth();


     if (auth === "guest") {
         //If guest make HOME and LOGIN clickable, remove LOGOUT button
         menu_items[1].classList.add("disabled");
         menu_items[2].classList.add("disabled");
         menu_items[3].classList.add("disabled");
         menu_items[5].parentElement.remove();
         menu_items[4].classList.remove("nonVisible");

     } else if (auth === "admin") {
         //If admin set HISTORY to user_history.html, remove LOGIN button
         menu_items[2].setAttribute("href","./user_history.html");
         menu_items[4].parentElement.remove();
         menu_items[4].classList.remove("nonVisible");

     } else {
         //If user set HOME, HISTORY and LOGOUT clickable, remove LOGIN button
         menu_items[1].classList.add("disabled");
         menu_items[3].classList.add("disabled");
         menu_items[4].parentElement.remove();
         menu_items[4].classList.remove("nonVisible");
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

    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
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

function getUser_id() {
    if (localStorage.getItem("token") != null)
        return parseJwt(localStorage.getItem("token")).user_id;
    else
        return "-1";
}

function setPaginationButtons(page) {
    var pagination_arrows = document.getElementsByClassName("page-link");

    if (numberOfPages === 0 || numberOfPages === 1) {
        pagination_arrows[0].classList.add("disabled");
        pagination_arrows[1].classList.add("disabled");
        return;
    }

    if (page == 1) {
        pagination_arrows[0].classList.add("disabled");
        pagination_arrows[1].classList.remove("disabled");
    } else if (page == numberOfPages) {
        pagination_arrows[1].classList.add("disabled");
        pagination_arrows[0].classList.remove("disabled");
    } else {
        pagination_arrows[0].classList.remove("disabled");
        pagination_arrows[1].classList.remove("disabled");
    }

}