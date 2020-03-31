"use strict";
let token = undefined;
$("#HeaderContent").load("header.html");
$("#loader").load("loader.html");
$("#carouselContent").load("carousel.html");
$("#loginContent").load("login.html");
$("#inscriptionContent").load("inscription.html");
$("#confirmedInscriptionContent").load("confirmedInscription.html");
$("#linkUserClientContent").load("linkUserClient.html");
$("#searchContent").load("searchBar.html");
$("#introduireDevis").load("introduireDevis.html");
$('#consulterDevis').load("consulterDevis.html");

$(document).ready(function () {

  token = initialisation();

  //nav-bar 
  $('.leftmenu').on('click', function (e) {
    $('.slide-nav').toggleClass("active");
    e.preventDefault();
  });



  $(".home").on('click', function (e) {
    token = localStorage.getItem("token");
   
    if (token)
      HideToHomeWhenConnect();
    else
      HideToHomeWhenNotConnect();
  });



  $("#logout").click(e => {
    e.preventDefault();
    localStorage.removeItem("token");
    localStorage.removeItem("ouvrier");
    token = undefined;
    Swal.fire({
      position: 'buttom-end',
      icon: 'success',
      timerProgressBar: true,
      title: "Déconnexion réussie",
      showConfirmButton: false,
      timer: 1500
  })
    HideToHomeWhenNotConnect();
  });
});

//UTILISER CES FONCTIONS PLS....

// vue vers le menu quand on est un QUIDAM!!!
const HideToHomeWhenNotConnect = () => {

  $("#nav_connect").show();
  $(".register").hide();
  $("#carouselContent").show();
  $("#logo").show();
  $("#logout").hide();
  $("#listeUser").hide();
  $("#linkUserClientContent").hide();
  $("#confirmedInscriptionContent").hide();
  $("#card").hide();
  $("#slide-menu").hide();
  $("#rechercher_mes_devis").hide();
  $("#searchCard").hide();
}



//Vue vers le Home quand on est connecté(menu,carousel);
const HideToHomeWhenConnect = () => {

  $("#logout").show();
  $("#card").hide();

  $("#login_message").html("");
  $("#nav_connect").hide();
  $(".register").hide();
  $("#carouselContent").show();
  $("#logo").hide();
  $("#listeUser").hide();
  $("#introduireDevis").hide();
  $("#linkUserClientContent").hide();
  $("#confirmedInscriptionContent").hide();
  $("#listeDeTousLesDevis").hide();
  $("#listerClients").hide();
  $("#searchCard").hide();

  let user = localStorage.getItem('ouvrier');
    console.log("user ==> " + user);
    if (user === "true") {
      $("#slide-menu").show();
      $('#rechercher_mes_devis').hide();
      }else {
      $('#rechercher_mes_devis').show();
  }

}

const SameHide = () => {
  $("#logout").show();
  $("#nav_connect").hide();
  $("#login_message").html("");

  $(".register").hide();
  $("#carouselContent").hide();
  $("#logo").hide();
  $("#listeUser").hide();
  $("#linkUserClientContent").hide();
  $("#confirmedInscriptionContent").hide();

  $("#listeDeTousLesDevis").hide();
  $("#card").show();
  $("#listerClients").hide();
  $("#searchCard").hide();
  $("#introduireDevis").hide();
  $('#consulterDevis').hide();
  $('#listeDeMesDevis').hide();
}

//premier page que l'utilisateur voit quand il se connecte!!!

const HomeUser = () => {
  SameHide();
  $('#rechercher_mes_devis').show();

}


//Premier vu que l'ouvrier voit quand il se connecte !!!
const homeWorker = () => {

  SameHide();

  $('#rechercher_mes_devis').hide();
 
  let user = localStorage.getItem('ouvrier');

  if (user === "true") {
    $("#slide-menu").show();
  }

}


const initialisation = () => {
  $('#loader').hide();
  let token = localStorage.getItem("token");
  if (token) {
    HideToHomeWhenConnect();
    return token;
  } else {
    HideToHomeWhenNotConnect();
    return;
  }
};

export { HomeUser, homeWorker };
