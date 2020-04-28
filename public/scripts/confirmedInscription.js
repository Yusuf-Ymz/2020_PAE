
import { getData, postData } from "./utilsAPI.js";
import { printTable } from "./utilsHtml.js";
import { homeWorker } from "./index.js";
import {afficherVueConfirmerUtilisateur} from "./index.js";
import notify from "./utils.js";

let token = localStorage.getItem("token");

function afficherNotif(msg) {
  Swal.fire({
    position: 'center',
    icon: 'error',
    timerProgressBar: true,
    title: msg,
    showConfirmButton: false,
    timer: 1500
  })
}


$(document).ready(function () {

  
  
  $("#ajouterClientLier").click(function (e) {
    e.preventDefault();
    if (!$("#nomCLier")[0].checkValidity()) {
      afficherNotif("Erreur champ nom");
    } else if (!$("#prenomCLier")[0].checkValidity()) {
      afficherNotif("Erreur champ prenom");
    } else if (!$("#rueCLier")[0].checkValidity()) {
      afficherNotif("Erreur champ rue");
    } else if (!$("#numCLier")[0].checkValidity()) {
      afficherNotif("Erreur champ numéro");

    } else if (!$("#cpCLier")[0].checkValidity()) {
      afficherNotif("Erreur champ nom");
    } else if (!$("#villeCLier")[0].checkValidity()) {
      afficherNotif("Erreur champ nom");
    } else if (!$("#emailCLier")[0].checkValidity()) {
      afficherNotif("Erreur champ nom");
    } else if (!$("#telCLier")[0].checkValidity()) {
      afficherNotif("Erreur champ nom");
    } else {

      let data = {
        action: "ajouterClient",
        nom: $("#nomCLier").val(),
        prenom: $("#prenomCLier").val(),
        rue: $("#rueCLier").val(),
        numero: $("#numCLier").val(),
        //boite: $("#boiteCLier").val(),
        cp: $("#cpCLier").val(),
        ville: $("#villeCLier").val(),
        email: $("#emailCLier").val(),
        telephone: $("#telCLier").val(),
      };
      $("input").val("");
      postData("/client", data, localStorage.getItem("token"), onPostSuccess, onPostError);
    }

  });

});

function onPostSuccess(response) {
  $("#lierModel").modal('hide');
  notify("succes", "Le client a bien été ajouté");
  const actions = {
    "action": 'listeClientsPasUtilisateur'
  };
  getData("/client", actions, token, onGetLier, onErrorLier);
}

function onPostError(response) {
  console.log(response.error);
  $('#loader').hide();
  notify("error", "Le client n'a pas pu être ajouté");
}

function onGet(response) {
  
  $("#confirmedInscriptionContent").show();
  $("#searchCard").show();
  $("#searchContent").show();
  $("#filtre_user").show();
  $("#filtre_client").hide();
  $("#filtre_amenagement").hide();
  
  
  printTable("table_users_preinscrit", response.data, ["Valider ouvrier", "Lier à un client"], "N° utilisateur", [confirmerOuvrier, lierUtilisateurClient], "/user");
}

function onError(err) {
  console.log(err);
  $('#loader').hide();
  $("#table_users_preinscrit").html("<i class='far fa-frown'></i>  " + err.text);
}

const confirmerOuvrier = (url, data) => {
  data["action"] = 'confirmerInscription/worker';
  postData(url, data, token, onPostConfirmerOuvrierSuccess, onPostConfirmerOuvrierError);
}

function onPostConfirmerOuvrierSuccess() {
  notify("success", "L'utilisateur a bien été enregistré comme ouvrier");
  afficherVueConfirmerUtilisateur();
}

function onPostConfirmerOuvrierError() {
  notify("error", "L'utilisateur n'a pas pu être enregistré comme ouvrier");
  afficherVueConfirmerUtilisateur();
}

const lierUtilisateurClient = (url, data) => {

  homeWorker();
  $("#searchCard").show();
  $("#searchContent").show();
  $("#filtre_user").hide();
  $("#filtre_client").show();
  $("#filtre_amenagement").hide();
  data["action"] = 'recupererUtilisateur';
  
  $("#titre-page").text("Lier les utilisateurs aux clients");
  getData(url, data, token, onGetUtilisateur, onErrorGetUtilisateur);
}

function onGetUtilisateur(response) {
  $("#lastnameUser").text(response.data["Nom"]);
  $("#firsnameUser").text(response.data["Prénom"]);
  $("#emailUser").text(response.data["E-mail"]);
  $("#cityUser").text(response.data["Ville"]);
  $("#idUserLier").text(response.data["N° utilisateur"]);
  $("#idUserLier").hide();

  $("#prenomCLier").val($("#firsnameUser").text());
  $("#nomCLier").val($("#lastnameUser").text());
  $("#emailCLier").val($("#emailUser").text());
  $("#villeCLier").val($("#cityUser").text());
  const actions = {
    action: 'listeClientsPasUtilisateur'
  };
  getData("/client", actions, token, onGetLier, onErrorLier);
}

function onErrorGetUtilisateur(err) {
  $('#loader').hide();
}

function onGetLier(response) {
  console.log(response.data);
  $("#linkUserClientContent").show();
  $("#searchCard").show();
  $("#searchContent").show();
  $("#filtre_client").show();
  $("#filtre_utilisateur").hide();
  $("#filtre_amenagement").hide();
  let id;
  printTable("table_clients_noUsers", response.data, ["Lier"], "N° client", [lierUtilisateurClientTable], "/user");
}

function onErrorLier(err) {
  console.log(err);
  $('#loader').hide();
  $("#table_clients_noUsers").html("<i class='far fa-frown'></i>  " + err.text);
}

function onPostLier(response) {
  notify("success", "L'utilisateur a bien été lié");
  afficherVueConfirmerUtilisateur();
}

const lierUtilisateurClientTable = (url, data) => {
  
  data["action"] = 'confirmerInscription/lierUtilisateurClient'
  data["idUser"] = $("#idUserLier").text();
  postData(url, data, token, onPostLier, onErrorLier);
}


export {onGet, onGetLier};

