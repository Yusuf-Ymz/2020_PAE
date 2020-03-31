
import { getData,postData } from "./utilsAPI.js";
import {printTable} from "./utilsHtml.js";
import {homeWorker} from "./index.js";

const propriete_utilisateur = ["nom", "prenom", "pseudo","email","ville"];
const propriete_client = ["nom", "prenom","email","ville","codePostal","telephone"];
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

function afficherVueConfirmerUtilisateur(){
  homeWorker();
        const data = {
          action: 'confirmerInscription'
        };
        getData("/user",data, token, onGet,onError); 
}

$(document).ready(function(){
    $("#confirmed_inscriptions").on("click",function(){
      afficherVueConfirmerUtilisateur();
     /* homeWorker();
        const data = {
          action: 'confirmerInscription'
        };
        getData("/user",data, token, onGet,onError); */
    });

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
  const actions = {
    "action" : 'listeClientsPasUtilisateur'
  };
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
        printTable("table_users_preinscrit",response.data,["Valider ouvrier","Lier à un client"], "N° utilisateur", [confirmerOuvrier,lierUtilisateurClient],  "/user");
      }
    } else $("#table_users_preinscrit").text(JSON.stringify(response.error));
  }

  function onError(err) {
    console.log(err);
    $("#table_users_preinscrit").html("<i class='far fa-frown'></i>  "  + err.text);
}

const confirmerOuvrier = (url, data) => {
  data["action"]= 'confirmerInscription/worker';
    postData(url, data, token,onPost,onPostError);
    location.reload();
}
    
const lierUtilisateurClient = (url, data) => {
  console.log(data);
  console.log(url);
        homeWorker();
        data["action"] = 'recupererUtilisateur';
        console.log(data);
        getData(url,data,token,onGetUtilisateur,onErrorGetUtilisateur);
}

function onGetUtilisateur(response){
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
        getData("/client",actions, token, onGetLier,onErrorLier); 
}

function onErrorGetUtilisateur(err){

}

function onGetLier(response) {
    console.log(response.data);
    if (response.data) { 
      $("#linkUserClientContent").show();
      $("#searchCard").show();
        $("#searchContent").show();
        $("#filtre_client").show();
        $("#filtre_utilisateur").hide();
        $("#filtre_amenagement").hide();
      if (response.data.length > 0) {
        let id;
        printTable(
          "table_clients_noUsers",
        response.data,
        ["Lier"],
        "N° client",
        [lierUtilisateurClientTable],
        "/user"
        );
      }
    } else $("#table_clients_noUsers").text(JSON.stringify(response.error));
  }

  function onErrorLier(err) {
    console.log(err);
    $("#table_clients_noUsers").html("<i class='far fa-frown'></i>  "  + err.text);
}

const lierUtilisateurClientTable = (url, data) => {
    data["action"]= 'confirmerInscription/lierUtilisateurClient'
    data["idUser"] = $("#idUserLier").text();
      postData(url, data, token,afficherVueConfirmerUtilisateur,onErrorLier);
  }


