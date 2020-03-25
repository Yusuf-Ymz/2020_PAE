
import { getData,postData } from "./utilsAPI.js";
import create_dynamic_HTML_table from "./tableWithButton.js";
import {homeWorker} from "./index.js";

const propriete_utilisateur = ["nom", "prenom", "pseudo","email","ville"];
const propriete_client = ["nom", "prenom","email","ville","codePostal","telephone"];
let token = null;

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

$(document).ready(function(){
    $("#confirmed_inscriptions").on("click",function(){
      homeWorker();
        token = localStorage.getItem("token");
        const data = {
          action: 'confirmerInscription'
        };
        getData("/user",data, token, onGet,onError); 
    });

    $("#ajouterClientLier").click(function (e) {
      e.preventDefault();
      console.log("test");
      if (!$("#nomCLier")[0].checkValidity()) {
          afficherNotif("Erreur champ nom");
      } else if (!$("#prenomCLier")[0].checkValidity()) {
          afficherNotif("Erreur champ prenom");
      } else if (!$("#rueCLier")[0].checkValidity()) {
          afficherNotif("Erreur champ rue");
      } else if (!$("#numCLier")[0].checkValidity()) {
          afficherNotif("Erreur champ numéro");
      } else if (!$("#boiteCLier")[0].checkValidity()) {
          afficherNotif("Erreur champ boite");
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
              boite: $("#boiteCLier").val(),
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
  getData("/client",actions, token, onGetLier,onErrorLier); 
}

function onPostError(response) {
  console.log(response.error);
}

function onGet(response) {
  
    if (response.data) {
      $("#confirmedInscriptionContent").show();
      $("#searchCard").show();
        $("#searchContent").show();
        $("#filtre_client").hide();
        $("#filtre_utilisateur").show();
        $("#filtre_amenagement").hide();
      if (response.data.length > 0) {
        let id;
        let thtabUser = new Array( "Nom", "Prénom", "Pseudo","Email", "Ville");
        create_dynamic_HTML_table(
          "table_users_preinscrit",
        response.data,
        confirmerOuvrier,
        lierUtilisateurClient,
        false,
        propriete_utilisateur,
        thtabUser
        );
      }
    } else $("#table_users_preinscrit").text(JSON.stringify(response.error));
  }

  function onError(err) {
    console.log(err);
    $("#table_users_preinscrit").html("<i class='far fa-frown'></i>  "  + err.text);
}

const confirmerOuvrier = (id, data) => {
  data["action"]= 'confirmerInscription/worker';
  data["id"] = id;
    postData("/user", data, token,onPost,onPostError);
    location.reload();
}
    
const lierUtilisateurClient = (id, data) => {
        homeWorker();
        $("#lastnameUser").text(data["nom"]);
        $("#firsnameUser").text(data["prenom"]);
        $("#emailUser").text(data["email"]);
        $("#cityUser").text(data["ville"]);
        $("#idUserLier").text(id);
        $("#prenomCLier").attr("placeholder",$("#firsnameUser").val());
        $("#nomCLier").attr("placeholder",$("#lastnameUser").val());
        $("#emailCLier").attr("placeholder",$("#emailUser").val());
        $("#villeCLier").attr("placeholder",$("#cityUser").val());
        const actions = {
          action: 'listeClientsPasUtilisateur'
        };
        getData("/client",actions, token, onGetLier,onErrorLier); 
}

function onGetLier(response) {
  
    if (response.data) { 
      $("#linkUserClientContent").show();
      $("#searchCard").show();
        $("#searchContent").show();
        $("#filtre_client").show();
        $("#filtre_utilisateur").hide();
        $("#filtre_amenagement").hide();
      if (response.data.length > 0) {
        let id;
        let thtabUser = new Array( "Nom", "Prénom","Email","Ville","Code-postal", "Telephone");
        create_dynamic_HTML_table(
          "table_clients_noUsers",
        response.data,
        false,
        false,
        lierUtilisateurClientTable,
        propriete_client,
        thtabUser
        );
      }
    } else $("#table_clients_noUsers").text(JSON.stringify(response.error));
  }

  function onErrorLier(err) {
    console.log(err);
    $("#table_clients_noUsers").html("<i class='far fa-frown'></i>  "  + err.text);
}

const lierUtilisateurClientTable = (id, data) => {
    data["action"]= 'confirmerInscription/lierUtilisateurClient'
    data["idClient"] = id;
    data["idUser"] = $("#idUserLier").text();
    console.log(data["idUser"]);
      postData("/user", data, token,onPost,onErrorLier);
  }

function onPost(response){
    location.reload();
}


