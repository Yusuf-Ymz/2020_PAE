"use strict";
import { getData, postData, deleteData, updateData } from "./utilsAPI.js";
import affichageListeUser from "./listeUser.js";
let token = undefined;
let thtabUser = ["date_inscription","prenom","nom","pseudo","ville"];

$(document).ready(function () {

  token = initialisation();

  //nav-bar 
  $('.leftmenu').on('click', function (e) {
    $('.slide-nav').toggleClass("active");
    e.preventDefault();
  });

  //mettre dans un autre ficher 
  $("#rechercher_user").on('click', function (e) {
    console.log(token);
    token = localStorage.getItem("token");
    getData("/listeUser", token, onGetUserList, onUserListError);
  
  });


  $(".home").on('click', function (e) {
    token = localStorage.getItem("token");
    if (token)
      HomeUser();
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

$("#HeaderContent").load("header.html");
$("#carouselContent").load("carousel.html");
$("#loginContent").load("login.html");

const HideToHome = () => {
  $("#nav_connect").show();
  $(".register").hide();
  $("#carouselContent").show();
  $("#logo").show();
  $("#logout").hide();
  $("#listeUser").hide();
}

const HomeUser = () => {
  $("#nav_connect").hide();
  $("#logout").show();
 
  $("#login_message").html("");
  $("#nav_connect").hide();
  $(".register").hide();
  $("#carouselContent").hide();
  $("#logo").hide();
  $("#listeUser").hide();
}


const initialisation = () => {
  let token = localStorage.getItem("token");

  console.log(token);
  if (token) {
    HomeUser();
    return token;

  } else {
    HideToHome();
    return;
  }
};

/**
 * A bouger   
 */
function onGetUserList(response) {
  $("#listeUser").show();
  affichageListeUser("listeUser", response.listeUser,thtabUser);
 
}


/**
 * A bouger 
 */
function onUserListError(err) {
  alert(err);
}
export { HomeUser };


