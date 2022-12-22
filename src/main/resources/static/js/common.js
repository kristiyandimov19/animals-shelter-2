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
        let text = await res.text();
        const obj = JSON.parse(text);
        if(obj.availability){
            return "OK";
        }
        else if(res.status == 401){
            window.location.replace("../html/login.html")
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

         menu_items[1].classList.add("disabled");
         menu_items[2].classList.add("disabled");
         menu_items[3].classList.add("disabled");
         menu_items[5].parentElement.remove();

     } else if (auth === "admin") {

         menu_items[2].setAttribute("href","./user_history.html");
         menu_items[4].parentElement.remove();


     } else {

         menu_items[1].classList.add("disabled");
         menu_items[3].classList.add("disabled");
         menu_items[4].parentElement.remove();
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
             localStorage.removeItem("token");
             Swal.fire({
                 imageUrl: '../images/bye-bye-bye.gif',
                 confirmButtonText: 'OK!'
             }).then(() => {
                 window.location.replace("./index.html");
             })
         }else {
             window.location.replace("../html/index.html");
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

function getUser_id(){
    if(localStorage.getItem("token") != null)
        return parseJwt(localStorage.getItem("token")).user_id;
    else
        return "-1";
}