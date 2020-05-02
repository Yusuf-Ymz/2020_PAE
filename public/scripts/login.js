import { postData } from "./utilsAPI.js";
import { HomeUser, homeWorker, fillCardUserInfos, firstViewWorker, firstViewUser } from "./index.js";
import notify from "./utils.js";


const LoginForm = (errorMessage = "") => {
  $("#login_message").html("<i class='far fa-frown'></i>  " + errorMessage);
  if (errorMessage === "") $("#login_message").hide();
  else $("#login_message").show();
  $(".register").show();
  $("#nav_connect").hide();
  $("#logo").hide();
  $("#carouselContent").hide();
  $('#redirection_connect').hide();
  $('#redirection_inscrit').show();
  $("#inscription_form").addClass("d-none");
  $("#login_form").show();
  $("#confirmedInscriptionContent").hide();
  $("#card").show();
  $("#selectAmenagementAccueil").hide();
};



function onPostLogin(response) {
  $("#pseudo").val("");
  $("#mdp").val("");

  localStorage.setItem("token", response.token);
  
  window.glob = response.user;
  console.log(window.glob);
  let user = window.glob;
  fillCardUserInfos(user.pseudo, user.prenom + " " + user.nom, user.ville, user.email, user.dateInscription);
  if (window.glob.ouvrier === true) {
    firstViewWorker();
  } else {
    firstViewUser();
  }
}

function onErrorLogin(err) {
  console.error(err.responseJSON.error);
  $('#loader').hide();
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
  notify("success", "Vous avez bien été enregistré");
  LoginForm();
}

function onErrorInscription(err) {
  console.error("Error :" + err.responseJSON.error);
  RegisterForm(err.responseJSON.error);
  $('#loader').hide();
}


const RegisterForm = (errorMessage = "") => {
  $("#inscription_message").html(errorMessage);
  if (errorMessage === "") $("#inscription_message").hide();
  else $("#inscription_message").show();

  $(".register").show();
  $("#logo").hide();
  $("#carouselContent").hide();
  $('#redirection_connect').show();
  $('#redirection_inscrit').hide();
  $("#nav_connect").hide();
  $("#inscription_form").removeClass("d-none");
  $("#login_form").hide();
  $("#confirmedInscriptionContent").hide();
  $("#card").show();
  $("#selectAmenagementAccueil").hide();

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

  $("#login_btn").on('click', function (e) {
    e.preventDefault();
    if ($("#pseudo")[0].checkValidity() && $("#mdp")[0].checkValidity()) {
      const data = {
        pseudo: $("#pseudo").val(),
        password: $("#mdp").val(),
        action: "connection"
      };
      postData("/authentification", data, null, onPostLogin, onErrorLogin);
    } else {
      $("#login_message").show();

      $("#login_message").html("<i class='far fa-frown'></i>   Veuillez entrer des données valides!");
    }
  });

  $("#inscription").on('click', function (e) {

    e.preventDefault();

    if ($("#mdp_inscription")[0].checkValidity() && $("#re_mdp_inscription")[0] === $("#mdp_inscription")) {
      alert("Le mot de passe est invalideS");
    }
    if ($("#nom")[0].checkValidity() && $("#prenom")[0].checkValidity()
      && $("#pseudo_inscription")[0].checkValidity() && $("#email")[0].checkValidity()
      && $("#ville")[0].checkValidity()) {
      const data = {
        nom: $("#nom").val(),
        prenom: $("#prenom").val(),
        pseudo: $("#pseudo_inscription").val(),
        email: $("#email").val(),
        ville: $("#ville").val(),
        mdp: $("#mdp_inscription").val(),
        action: "register"
      };
      //postData("/inscription", data, token, onPostInscription, onErrorInscription);
      postData("/authentification", data, null, onPostInscription, onErrorInscription);
    } else {
      $("#inscription_message").show();
      $("#inscription_message").html("<i class='far fa-frown'></i>   Veuillez entrer des données valides.");

    }
  });
});
