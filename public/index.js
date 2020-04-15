"use strict";
let token = undefined;
window.glob = "userInfo";

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
      HideToHomeWhenConnect("");
    else
      HideToHomeWhenNotConnect();
  });



  $("#logout").click(e => {
    e.preventDefault();
    localStorage.removeItem("token");
    window.glob = "";
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


const HideToHomeWhenConnect = (response) => {

  if (response !== "") {
    window.glob = response.user;
    console.log(window.glob);

  }


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


  if (window.glob.ouvrier === true) {
    $("#slide-menu").show();
    $('#rechercher_mes_devis').hide();
  } else {
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
  $("#btn_remove_filters").hide();
  $("#result").hide();
  $("#listeDeTousLesDevis").hide();
  $("#card").show();
  $("#listerClients").hide();
  $("#searchCard").hide();
  $("#introduireDevis").hide();
  $('#consulterDevis').hide();
  $('#listeDeMesDevis').hide();
}


const HomeUser = () => {
  SameHide();

  $('#rechercher_mes_devis').show();

}

const homeWorker = () => {

  SameHide();

  $('#rechercher_mes_devis').hide();
  console.log(window.glob.ouvrier);
  if (window.glob.ouvrier === true) {
    $("#slide-menu").show();
  }

}


const initialisation = () => {

  $('#loader').hide();
  let token = localStorage.getItem("token");
  console.log(token);
  if (token) {

    const data = {
      action: "obtenirUser"
    };
    getData('/user', data, token, HideToHomeWhenConnect, onErrorRefresh);

    return token;
  } else {
    HideToHomeWhenNotConnect();
    return;
  }
};

function onErrorRefresh(err) {
  console.error(err);
  $('#loader').hide();
  Swal.fire({
    position: 'top-end',
    icon: 'error',
    timerProgressBar: true,
    title: err.responseJSON.error,
    showConfirmButton: false,
    timer: 1500
  })
}



function getData(url = "", data = "", token, onGet, onError) {
  let headers;
  if (token)
    headers = {
      "Content-Type": "application/json",
      Authorization: token
    };
  else
    headers = {
      "Content-Type": "application/json"
    };

  $.ajax({
    type: "get",
    url: url,
    headers: headers,
    data: data,
    dataType: "json",
    beforeSend: function () {
      $('#loader').show();
    },
    complete: function () {
      $('#loader').hide();
    },
    success: onGet,
    error: onError
  });
}

export { HomeUser, homeWorker };
