"use strict";
import { getData, getDataWithoutLoader } from "./utilsAPI.js";
import { onGetTousLesDevisList, onGetMesDevisList, onDevisListError } from "./rechercherDevis.js";
import { getListClient, displayAmenagementsDevis } from "./introduireDevis.js";
import { onGetClientList, onClientListError } from "./rechercherClients.js";
import { onGetUserList, onUserListError } from "./rechercherUtilisateur.js";
import { onGet } from "./confirmedInscription.js";
import notify from "./utils.js";
let previousRequest;
let token = undefined;
window.glob = "userInfo";


$(document).ready(function () {

  token = initialisation();

  //nav-bar 
  $('.leftmenu').on('click', function (e) {
    $('.slide-nav').toggleClass("active");
    e.preventDefault();
  });
  getDataWithoutLoader("/amenagement", null, token, displayAmenagements, onError);

  $(".home").on('click', function (e) {
    token = localStorage.getItem("token");
    getDataWithoutLoader("/amenagement", null, token, displayAmenagements, onError);
    remplirCarrousel(token);
    if (token)
      HideToCarousel();
    else {
      HideToHomeWhenNotConnect();
    }
  });






  $("#selectAmenagementAccueil").click(function (e) {
    e.preventDefault();
    console.log("ici");
    $("#amenagementsAccueil").fadeIn();
  })



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
  
  $('#rechercher_tous_les_devis').on('click', function (e) {
    homeWorker("");
    let token = localStorage.getItem("token");

    const data = {
      action: "tousLesDevis"
    }
    abortPreviousRequest();
    previousRequest = getData("/devis", data, token, onGetTousLesDevisList, onDevisListError);

  });

  $('#rechercher_mes_devis').on('click', function (e) {
    HomeUser("");
    let token = localStorage.getItem("token");
    const data = {
      action: "mesDevis"
    }
    abortPreviousRequest();
    previousRequest = getData("/devis", data, token, onGetMesDevisList, onDevisListError);
  });

  $("#indroduire_devis").click(function (e) {
    e.preventDefault();
    homeWorker("");
    $("#introduireDevis").show();
    $("#searchCard").show();
    $("#card").show();
    $("#selectAmenagementAccueil").hide();

    $("#filtre_client").show();
    $("#filtre_user").hide();
    $("#filtre_amenagement").hide();
    if (idClient > 0) {
      $("#searchContent").hide();
      $("#searchDisplayClient").hide();
    } else {
      $("#searchContent").show();
      $("#searchDisplayClient").show();
    }
    $("#titre-page").text("Introduire un nouveau devis");
    abortPreviousRequest();
    getDataWithoutLoader("/amenagement", null, localStorage.getItem("token"), displayAmenagementsDevis, onError);
    getListClient();
  });

  $('#rechercher_tous_les_clients').on('click', function (e) {

    homeWorker("");
    let token = localStorage.getItem("token");
    const data = {
      action: "listerClients"
    }
    abortPreviousRequest();
    previousRequest = getData("/client", data, token, onGetClientList, onClientListError);
  });

  $("#rechercher_user").on('click', function (e) {
    homeWorker("");
    let token = localStorage.getItem("token");
    const data = {
      action: 'listeUser'
    };
    abortPreviousRequest();
    previousRequest = getData("/user", data, token, onGetUserList, onUserListError);
  });

  $("#confirmed_inscriptions").on("click", function () {
    $("#titre-page").text("Confirmer inscription");
    $("#selectAmenagementAccueil").hide();

    afficherVueConfirmerUtilisateur();
  });

});

function afficherVueConfirmerUtilisateur() {

  homeWorker("");

  $("#searchCard").show();
  $("#searchContent").show();

  $("#filtre_user").show();
  $("#filtre_client").hide();
  $("#filtre_amenagement").hide();

  const data = {
    action: 'confirmerInscription'
  };

  token = localStorage.getItem("token");
  abortPreviousRequest();
  previousRequest = getData("/user", data, token, onGet, onError);

}

function abortPreviousRequest() {
  if (previousRequest) {
    previousRequest.abort();
    previousRequest = null;
  }
}

const HideToHomeWhenNotConnect = () => {
  remplirCarrousel(token);
  $(".user-info").hide();
  $("#nav_connect").show();
  $(".register").hide();
  //$("#selectAmenagementAccueil").fadeIn();
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

const firstViewWorker = () => {
  
  homeWorker("");
  let token = localStorage.getItem("token");
  console.log("token = " + token);
  const data = {
    action: "tousLesDevis"
  }
  abortPreviousRequest();
  previousRequest = getData("/devis", data, token, onGetTousLesDevisList, onDevisListError);
}

//premier page que l'utilisateur voit quand il se connecte!!!
const firstViewUser = () => {
  
  HomeUser("");
  let token = localStorage.getItem("token");

  const data = {
    action: "mesDevis"
  }
  abortPreviousRequest();
  previousRequest = getData("/devis", data, token, onGetMesDevisList, onDevisListError);

}

const HideToHomeWhenConnect = (response) => {

  if (response !== "") {
    window.glob = response.user;
    console.log(window.glob);
    let user = window.glob;
    fillCardUserInfos(user.pseudo, user.prenom + " " + user.nom, user.ville, user.email, user.dateInscription);
  }

  $("#logout").show();
  $("#card").hide();

  $("#login_message").html("");
  $("#nav_connect").hide();
  $(".register").hide();
  $("#carouselContent").hide();
  $("#logo").hide();
  $("#listeUser").hide();
  $("#introduireDevis").hide();
  $("#linkUserClientContent").hide();
  $("#confirmedInscriptionContent").hide();
  $("#listeDeTousLesDevis").hide();
  $("#listerClients").hide();
  $("#searchCard").hide();

  $("#selectAmenagementAccueil").hide();
  $("#selectAmenagementAccueil").css('display', 'none');

  if (window.glob.ouvrier === true) {
    $("#slide-menu").show();
    $('#rechercher_mes_devis').hide();
    firstViewWorker();
  } else {
    $('#rechercher_mes_devis').show();
    firstViewUser();
  }

}
const HideToCarousel = () => {
  remplirCarrousel(token);
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
  $("#selectAmenagementAccueil").show();



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
  $(".user-info").show();
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
  $("#searchContent").hide();
  $("#table_clients_noUsers").hide();

  $("#selectAmenagementAccueil").hide();
  $("#selectAmenagementAccueil").css('display', 'none');
}


const HomeUser = (response = "") => {


  if (response !== "") {
    window.glob = response.user;
  } else {
    SameHide();
    $('#rechercher_mes_devis').show();
    if (token) {
      const data = {
        action: "obtenirUser"
      };
      getDataWithoutLoader('/user', data, token, homeWorker, onErrorRefresh);
    }
  }

  
}

const homeWorker = (response = "") => {


  if (response !== "") {
    window.glob = response.user;
  } else {
    SameHide();
    $('#rechercher_mes_devis').hide();
    if (token) {
      const data = {
        action: "obtenirUser"
      };
      getDataWithoutLoader('/user', data, token, homeWorker, onErrorRefresh);
    }
  }

 

  if (window.glob.ouvrier === true) {
    $("#slide-menu").show();
  }

}


const initialisation = () => {
  $('#loader').hide();
  let token = localStorage.getItem("token");

  if (token) {
    const data = {
      action: "obtenirUser"
    };
    getDataWithoutLoader('/user', data, token, HideToHomeWhenConnect, onErrorRefresh);
    return token;
  } else {
    getDataWithoutLoader("/amenagement", null, token, displayAmenagements, onError);
    HideToHomeWhenNotConnect();
    return;
  }
};

function displayAmenagements(response) {
  let divAmenagements = $("#amenagementsAccueil");
  divAmenagements.text("");
  let amenagements = response.amenagements;
  console.log(amenagements);
  let row = document.createElement("div");
  row.className = "row mt-2";
  divAmenagements.append(row);
  for (let i = 0; i < amenagements.length; i++) {
    if (i % 3 == 0) {
      row = document.createElement("div");
      row.className = "row mt-2";
      divAmenagements.append(row);
    }
    let divButton = document.createElement("div");
    divButton.className = "col";

    let button = document.createElement("button");
    button.className = "btn btn-light";
    button.innerHTML = amenagements[i].libelle + " (" + amenagements[i].nbPhotos + ")";
    button.value = amenagements[i].id;

    button.addEventListener('click', function (e) {
      e.preventDefault();
      let id = e.target.value;
      console.log(id);
      let data = {
        action: "afficherPhotoAmenagement",
        amenagement: id,
      };
      getData("/photo", data, localStorage.getItem('token'), afficherCarrousel);
    });

    row.append(divButton)
    divButton.appendChild(button);
  }

}

function onError(response) {
  console.log(response);
}

function remplirCarrousel(token) {
  const data = {
    action: "afficherPhotoCarrousel"
  };
  previousRequest = getData('/photo', data, token, afficherCarrousel, onErrorRefresh);
}

function afficherCarrousel(response) {

  $("#selectAmenagementAccueil").show();

  let photosCarrousel = response.photosCarrousel;
  if (photosCarrousel.length == 0) {
    notify("info", "Pas de photo(s) après aménagement disponible pour le moment");
    return;
  }
  $("#amenagementsAccueil").fadeOut();
  let inner = $(".carousel-inner");
  let indicator = $(".carousel-indicators");
  indicator.text("");
  inner.text("");
  console.log(photosCarrousel);
  for (let i = 0; i < photosCarrousel.length; i++) {
    let div = document.createElement("div");
    let li = document.createElement("li");
    li.setAttribute("data-target", "#carouselExampleIndicators");
    li.setAttribute("data-slide-to", i);
    div.className = "carousel-item";

    let divDescription = document.createElement("div");
    divDescription.className = "carousel-caption d-none d-md-block";
    let title = document.createElement("h1");
    title.innerHTML = photosCarrousel[i].Amenagement;
    if (i == 0) {
      div.classList.add("active");
      li.classList.add("active");
    }
    div.innerHTML = "<img class='d-block w-100 img_size img-fluid' src='" + photosCarrousel[i].Photo + "'id='" + photosCarrousel[i]['Photo id'] + "'/>";
    div.appendChild(divDescription);
    divDescription.appendChild(title);
    inner.append(div);
    indicator.append(li);
  }
}

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

function fillCardUserInfos(pseudo, name, city, email, date) {

  $("#currentPseudo").html(pseudo);
  $("#currentName").html(name);
  $("#currentCity").html(city);
  $("#currentEmail").html(email);
  $("#currentDate").html(dateFormating(date));
}

function dateFormating(date) {

  let day = JSON.stringify(date.dayOfMonth);
  console.log("taille = " + day.length);
  if (day.length == 1) {
    day = "0" + day;
  }
  let month = JSON.stringify(date.monthValue);
  if (month.length == 1) {
    month = "0" + month;
  }

  return day + "/" + month + "/" + date.year;

}

export { HomeUser, homeWorker, fillCardUserInfos, afficherVueConfirmerUtilisateur ,firstViewWorker, firstViewUser};
