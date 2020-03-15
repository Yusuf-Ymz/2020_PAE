"use strict";
//import { getData, postData, deleteData, updateData } from "./utilsAPI.js";
let token = undefined;
$("#HeaderContent").load("header.html");
$("#carouselContent").load("carousel.html"); 
$("#loginContent").load("login.html");
$("#inscriptionContent").load("inscription.html");
$("#confirmedInscriptionContent").load("confirmedInscription.html");

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
      HomeUserWhenAuthentified();
    else
      HideToHome();
  });



  $("#logout").click(e => {
    e.preventDefault();
    localStorage.removeItem("token");
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
  $("#users_preinscrit_component").hide();
  $("#card").hide();
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
  $("#users_preinscrit_component").hide();
  $("#card").show();
}

const HomeUserWhenAuthentified = ()=>{
  $("#logout").show();
  $("#card").hide();
  $("#nav_connect").hide();
  $("#login_message").html("");
  $("#nav_connect").hide();
  $(".register").hide();
  $("#carouselExampleIndicators").show();
  $("#logo").hide();
  $("#listeUser").hide();
  $("#users_preinscrit_component").hide();
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

export {HomeUser};
