
import { getData,postData } from "./utilsAPI.js";
import create_dynamic_HTML_table from "./tableConfirmerInscription.js";

const propriete_utilisateur = ["nom", "prenom", "pseudo","ville", "email"];

$(document).ready(function(){
    $("#confirmed_inscriptions").on("click",function(){
        let token = localStorage.getItem("token");
        const data = {
          action: 'confirmerInscription'
        };
        getData("/ouvrier",data, token, onGet,onError); 
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
    console.error(err);
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

const confirmerInscription = (id, item) => {
    postData("confirmerInscription/utilisateur/" + id, item, token);
}

const confirmerOuvrier = (id, item) => {
    postData("confirmerInscription/ouvrier/" + id, item, token);
}
    
const lierUtilisateurClient = (id, item) => {
    postData("confirmerInscription/lienClient/" + id, item, token);
}



