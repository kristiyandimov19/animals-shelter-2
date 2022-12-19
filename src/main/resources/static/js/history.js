function showWalks(user_id){

}

function setPage(){
    const urlParams = new URLSearchParams(window.location.search);
    let user_id=localStorage.getItem("user_id");
    showWalks(user_id);
}