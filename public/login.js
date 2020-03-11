import { postData } from "./utilsAPI.js";
import {HomeUser} from "./index.js"

const LoginForm = (errorMessage = "") => {
  $("#login_message").html("<i class='far fa-frown'></i>  "  + errorMessage);
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



function onPostLogin(response) {
  console.log("onPostLogin");
  $("#pseudo").val("");
  $("#mdp").val("");
  localStorage.setItem("token", response.token);
  HomeUser();
}

function onErrorLogin(err) {
  console.log("onErrorLogin");
  console.error(err.responseJSON.error);
  LoginForm(err.responseJSON.error);
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
    localStorage.setItem("token", response.token);
    token = response.token;
    HomeUser();
  } else {
    console.error("Error:", response);

    RegisterForm(response.error);
  }
}
function onErrorInscription(err) {
  console.error("Error :", err);
  LoginForm(response.error);
}

const RegisterForm = (errorMessage = "") => {
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

$(document).ready(function () {
  $('#redirection_inscrit').on('click', function (e) {
    e.preventDefault();
    RegisterForm();
  });

  $('.redirection_connect').on('click', function (e) {
    e.preventDefault();
    LoginForm();
  });

  $("#login_btn").click(e => {
    e.preventDefault();
    if ($("#pseudo")[0].checkValidity() && $("#mdp")[0].checkValidity()) {
      const data = {
        pseudo: $("#pseudo").val(),
        password: $("#mdp").val()
      };
      console.log('envoi');
      postData("/authentification", data, null, onPostLogin, onErrorLogin);
    } else {
      console.log('else');
      $("#login_message").show();
     
      $("#login_message").html("<i class='far fa-frown'></i>   Veuillez entrer des données valides!");
    }
  });

  $("#inscription").click(e => {
    e.preventDefault();
    if ($("#mdp_inscription")[0].checkValidity() && $("#re_mdp_inscription")[0] === $("#mdp_inscription")) {
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
      alert("<i class='far fa-frown'></i>   Veuillez entrer des données valides.");
    }
  });
})
