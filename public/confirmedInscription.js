
import { getData,postData } from "./utilsAPI.js";
import create_dynamic_HTML_table from "./tableWithButton.js";
import {homeWorker} from "./index.js";

const propriete_utilisateur = ["nom", "prenom", "pseudo","email","ville"];
const propriete_client = ["nom", "prenom","email","ville","codePostal","telephone"];
let token = null;

$(document).ready(function(){
    $("#confirmed_inscriptions").on("click",function(){
      homeWorker();
        token = localStorage.getItem("token");
        const data = {
          action: 'confirmerInscription'
        };
        getData("/user",data, token, onGet,onError); 
    });

});

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
        confirmerInscription,
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


const confirmerInscription = (id, data) => {
  data["action"]= 'confirmerInscription/lienClient';
  data["id"] = id;
    postData("/user", data, token);
    location.reload();
}

const confirmerOuvrier = (id, data) => {
  data["action"]= 'confirmerInscription/worker';
  data["id"] = id;
    postData("/user", data, token);
    location.reload();
}
    
const lierUtilisateurClient = (id, data) => {
        homeWorker();
        $("#lastnameUser").text(data["nom"]);
        $("#firsnameUser").text(data["prenom"]);
        $("#emailUser").text(data["email"]);
        $("#cityUser").text(data["ville"]);
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
    data["action"]= 'linkUserClient'
    data["id"] = id;
      postData("/client", data, token);
      location.reload();
  }


