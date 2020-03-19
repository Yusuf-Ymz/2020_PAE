
import { getData,postData } from "./utilsAPI.js";
import create_dynamic_HTML_table from "./tableConfirmerInscription.js";

const propriete_utilisateur = ["email","nom", "prenom", "pseudo","ville"];
let token = null;

$(document).ready(function(){
    $("#confirmed_inscriptions").on("click",function(){
        token = localStorage.getItem("token");
        const data = {
          action: 'confirmerInscription'
        };
        getData("/user",data, token, onGet,onError); 
        confirmerInscriptionVue();
    });

});

function onGet(response) {
  
    if (response.success) { 
      if (response.data.length > 0) { 
        let id;     
        create_dynamic_HTML_table(
          "table_users_preinscrit",
        response.data,
        confirmerInscription,
        confirmerOuvrier,
        lierUtilisateurClient,
        propriete_utilisateur
        );
      }
    } else $("#table_users_preinscrit").text(JSON.stringify(response.error));
  }

  function onError(err) {
    console.log(err);
    confirmerInscriptionVue(err.responseJSON.error);
}

const  confirmerInscriptionVue = (errorMessage = "") =>{
  $("#table_users_preinscrit").html("<i class='far fa-frown'></i>  "  + errorMessage);
  $("#table_users_preinscrit").show();
  $("#logout").show();
  $("#nav_connect").hide();
  $("#login_message").html("");
  $(".register").hide();
  $("#carouselExampleIndicators").hide();
  $("#logo").hide();
  $("#listeUser").hide();
  $("#users_preinscrit_component").show();
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
    location.reload();
}



