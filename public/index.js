"use strict";
//import { getData, postData, deleteData, updateData } from "./utilsAPI.js";
let token = undefined;
$("#HeaderContent").load("header.html");
$("#carouselContent").load("carousel.html"); 
$("#loginContent").load("login.html");
$("#inscriptionContent").load("inscription.html");
$("#confirmedInscriptionContent").load("confirmedInscription.html");
$("#linkUserClientContent").load("linkUserClient.html");
$("#searchContent").load("searchBar.html");
//$("#introduireDevis").load("introduireDevis.html");

$(document).ready(function () {
   
 token =  initialisation();

  //nav-bar 
  $('.leftmenu').on('click', function (e) {
    $('.slide-nav').toggleClass("active");
    e.preventDefault();
  });

  
  
  $(".home").on('click', function (e) {
    token = localStorage.getItem("token");
    if(token)
      HomeUser();
    else
      HideToHome();
  });



  $("#logout").click(e => {
    e.preventDefault();
    localStorage.removeItem("token");
    localStorage.removeItem("ouvrier");
    token = undefined;
    HideToHome();
  });
});


const HideToHome = () =>{
  
  $("#nav_connect").show();
  $(".register").hide();
  $("#carouselExampleIndicators").show();
  $("#logo").show();
  $("#logout").hide();
  $("#listeUser").hide();
  $("#linkUserClientContent").hide();
  $("#confirmedInscriptionContent").hide();
  $("#card").hide();
  $("#slide-menu").hide();
  $("#rechercher_mes_devis").hide();

}

const HomeUser = () =>{
  $("#logout").show();
  $("#nav_connect").hide();
  $("#login_message").html("");
  $("#nav_connect").hide();
  $(".register").hide();
  $("#carouselExampleIndicators").hide();
  $("#logo").hide();
  $("#listeUser").hide();
  $("#linkUserClientContent").hide();
  $("#confirmedInscriptionContent").hide();
  $("#card").show();
  $("#listerClients").hide();
  let user = localStorage.getItem('ouvrier');

   if(user === 'true'){
     console.log("je passe");
     $("#slide-menu").show();   
   }
   $('#rechercher_mes_devis').show();
}

const homeWorker = ()=>{

  $("#logout").show();
  $("#card").show();
  $("#nav_connect").hide();
  $("#login_message").html("");
  $("#nav_connect").hide();
  $(".register").hide();
  $("#carouselExampleIndicators").hide();
  $("#logo").hide();
  $("#listeUser").hide();
  $("#introduireDevis").hide();
  $("#linkUserClientContent").hide();
  $("#confirmedInscriptionContent").hide();
  $("#listeDeTousLesDevis").hide();
  $("#listerClients").hide();
  $("#searchCard").hide();

  let user = localStorage.getItem('user');
   if(user){
     $("#slide-menu").show(); 
   }
   
}


const initialisation = () => {
  let token = localStorage.getItem("token");
  if (token) {
    HomeUser();
    return token;
 
  } else {
    HideToHome();
    return;
  }
};

export {HomeUser,homeWorker};
