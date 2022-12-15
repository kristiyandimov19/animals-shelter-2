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
     localStorage.setItem('auth','guest');
     localStorage.setItem('user_id','0');
 }