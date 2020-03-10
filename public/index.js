"use strict";
import { getData, postData, deleteData, updateData } from "./utilsAPI.js";
let token=undefined;
const API_NAME = "/";

$(document).ready(function () {

  token = initialisation();
 
  //nav-bar 
  $('.leftmenu').on('click', function (e) {
    $('.slide-nav').toggleClass("active");
    e.preventDefault();
  });



  $('#redirection_inscrit').on('click', function (e) {
    e.preventDefault();
    RegisterForm();

  });

  $('.redirection_connect').on('click', function (e) {
    e.preventDefault();
    LoginForm();
  });
  

  $(".home").on('click', function (e) {
     HideToHome();
  });

  $("#login_btn").click(e => {
    e.preventDefault();
    if ($("#pseudo")[0].checkValidity() && $("#mdp")[0].checkValidity()) {
      const data = {
        pseudo: $("#pseudo").val(),
        password: $("#mdp").val()
      };
      postData("/authentification", data, token, onPostLogin, onErrorLogin);
    } else {
     // alert("Veuillez entrer des données valides.");
      $("#login_message").show();
      $("#login_message").html("Veuillez entrer des données valides!");
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
  $("#logout").hide();
}

const HomeUser = () =>{
  $("#logout").show();
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
  } 
}

function onErrorLogin(err) {
  console.error(err.responseJSON.error);
  console.error(err.detail);
  LoginForm(err.status);
 // LoginForm(err.detail);
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
