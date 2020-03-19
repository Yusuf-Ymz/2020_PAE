
import { getData,postData } from "./utilsAPI.js";
import create_dynamic_HTML_table from "./tableWithButton.js";
import {homeWorker} from "./index.js";

const propriete_utilisateur = ["email","nom", "prenom", "pseudo","ville"];
const propriete_client = ["codePostal","email", "nom", "prenom","telephone","ville"];
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
  
    if (response.success) { 
      $("#confirmedInscriptionContent").show();
      if (response.data.length > 0) {
        let id;
        let thtabUser = new Array("Email", "Nom", "Prénom", "Pseudo", "Ville");
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
  data["action"]= 'confirmerInscription/onlyUser';
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
  data["action"]= 'confirmerInscription/lienClient'
  data["id"] = id;
  postData("/user", data, token);

   /* homeWorker();
        const actions = {
          action: 'linkUserClient'
        };
        getData("/client",actions, token, onGetLier,onErrorLier); */
}

function onGetLier(response) {
  
    if (response.success) { 
      $("#linkUserClientContent").show();
      if (response.data.length > 0) {
        let id;
        let thtabUser = new Array("Code-postal","Email", "Nom", "Prénom", "Telephone", "Ville");
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


