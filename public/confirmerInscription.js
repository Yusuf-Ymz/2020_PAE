"use strict";

import { getData,postData } from "./utilsAPI.js";
import create_dynamic_HTML_table from "./utilsHTML.js";

const propriete_utilisateur = ["nom", "prenom", "pseudo","ville", "email"];

$(document).ready(function(){
    $("#link_confirmer_inscription").on("click",function(){
        //mettre une condition pour les ouvriers
        getData(API_NAME, token, onGet,onError); 
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

const  confirmerInscriptionVue = () =>{
    $("#logout").show();
  $("#nav_connect").hide();
  $("#login_message").html("");
  $(".register").hide();
  $("#carouselExampleIndicators").hide();
  $("#logo").hide();
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

