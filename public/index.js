"use strict";
import { getData, postData, deleteData, updateData } from "./utilsAPI.js";
let token=undefined;
const API_NAME = "/";

$(document).ready(function () {

  token = initialisation();
 
  $('.leftmenu').on('click', function (e) {
    $('.slide-nav').toggleClass("active");
    e.preventDefault();
  });

  /* Set the width of the sidebar to 250px and the left margin of the page content to 250px */
  $(".openbtn").on('click', function (e) {
    document.getElementById("mySidebar").style.width = "240px";
    //document.getElementById("main").style.marginLeft = "250px";
    $(".carousel-control-prev").hide();
    //$(".openbtn").hide();
  });

  /* Set the width of the sidebar to 0 and the left margin of the page content to 0 */
  $("#closebtn").on('click', function (e) {
    $(".carousel-control-prev").show();
    document.getElementById("mySidebar").style.width = "0";
    //document.getElementById("main").style.marginLeft = "0";
    //$(".openbtn").show();
  });



  $('#redirection_inscrit').on('click', function (e) {
    e.preventDefault();
    page = RegisterForm();

  });

  $('.redirection_connect').on('click', function (e) {
    e.preventDefault();
    LoginForm();
  });
  

  $("#home").on('click', function (e) {
     HideToHome();
  });

  $("#login_btn").submit(e => {
    e.preventDefault();
    if ($("#pseudo")[0].checkValidity() && $("#mdp")[0].checkValidity()) {
      const data = {
        pseudo: $("#pseudo").val(),
        mdp: $("#mdp").val()
      };
      postData("/authentification", data, token, onPostLogin, onErrorLogin);
    } else {
      alert("Veuillez entrer des données valides.");
    }
  });

  $("#inscription").submit(e => {
    e.preventDefault();
    if ($("#mdp_inscription")[0].checkValidity() && $("#re_mdp_inscription")[0] === $("#mdp_inscription")){
      alert("Le mot de passe est invalideS");
    }
    if ($("#nom")[0].checkValidity() && $("#prenom")[0].checkValidity() 
    && $("#pseudo_inscription")[0].checkValidity() && $("#email")[0].checkValidity() 
    && $("#ville")[0].checkValidity()) {
      const data = {
        nom: $("#nom").val(),
        mdp: $("#prenom").val(),
        pseudo: $("#pseudo_inscription").val(),
        email: $("#email").val(),
        ville: $("#ville").val(),
        mdp: $("#mdp_inscription").val()
      };
      postData("/inscription", data, token, onPostInscription, onErrorInscription);
    } else {
      alert("Veuillez entrer des données valides.");
    }
  });

  $("#logout").click(e => {
    e.preventDefault();
    //remove the token from localStorage
    localStorage.removeItem("token");
    token = undefined;
    HideToHome();
  });
});


const  HideToHome = () =>{

  $("#nav_connect").show();
  $(".register").hide();
  $("#carouselExampleIndicators").show();
  $("#logo").show();
}

const HomeUser = () =>{
  $("#login_message").html("");
  $("#nav_connect").hide();
  $(".register").hide();
  $("#carouselExampleIndicators").hide();
  $("#logo").hide();
}

const LoginForm = (errorMessage = "") =>{
  $("#login_message").html(errorMessage);
  if (errorMessage === "") $("#login_message").hide();
  else $("#login_message").show();
    $(".register").show();
    $("#nav_connect").hide();
    $("#logo").hide();
    $("#carouselExampleIndicators").hide();
    $('#redirection_connect').hide();
    $('#redirection_inscrit').show();
    $("#inscription_form").addClass("d-none");
    $("#login_form").show();
};

const RegisterForm = (errorMessage = "") =>{
  $("#inscription_message").html(errorMessage);
  if (errorMessage === "") $("#inscription_message").hide();
  else $("#inscription_message").show();
   $(".register").show();
   $("#logo").hide();
   $("#carouselExampleIndicators").hide();
   $('#redirection_connect').show();
   $('#redirection_inscrit').hide();
   $("#nav_connect").hide();
   $("#inscription_form").removeClass("d-none");
   $("#login_form").hide();
};

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

function onPostLogin(response) {
  $("#pseudo").val("");
  $("#mdp").val("");
  if (response.success === "true") {
    // store the jwt in localstorage
    localStorage.setItem("token", response.token);
    token = response.token;
    HomeUser();
  } else {
    //show error message
    console.error("Error:", response);
    LoginForm(response.error);
  }
}

function onErrorLogin(err) {
  console.error("Error :", err);
  LoginForm(response.error);
}

function onPostInscription(response) {
  $("#nom").val("");
  $("#prenom").val("");
  $("#pseudo_inscription").val("");
  $("#email").val("");
  $("#ville").val("");
  $("#mdp_inscription").val("");
  $("#re_mdp_inscription").val("");
  if (response.success === "true") {
    // store the jwt in localstorage
    localStorage.setItem("token", response.token);
    token = response.token;
    HomeUser();
  } else {
    //show error message
    console.error("Error:", response);
    RegisterForm(response.error);
  }
}

function onErrorInscription(err) {
  console.error("Error :", err);
  LoginForm(response.error);
}
