import { getData } from "./utilsAPI.js";
import { printTable } from "./utilsHtml.js"
import { homeWorker, HomeUser } from "./index.js";
import { consulterDevisEntantQueClient, consulterDevisEntantQueOuvrier } from "./consulterDevis.js"

$(document).ready(function () {

    $('#rechercher_tous_les_devis').on('click', function (e) {
        console.log("cliquer ");
        homeWorker();
        let token = localStorage.getItem("token");
        
        const data = {
            action: "tousLesDevis"
        }
        getData("/devis", data, token, onGetTousLesDevisList, onDevisListError);

    });

    $('#rechercher_mes_devis').on('click', function (e) {
        console.log("cliquer ");
        HomeUser();
        let token = localStorage.getItem("token");
        const data = {
            action: "mesDevis"
        }
        getData("/devis", data, token, onGetMesDevisList, onDevisListError);
    });

});

function onGetTousLesDevisList(response) {
    $("#listeDeTousLesDevis").show();
    $("#titre-page").text("Liste de tous les devis");
    $("#searchCard").show();// pas encore d'option de recherche pour devis
    
    $("#searchContent").show();
    $("#filtre_amenagement").show();
    $("#filtre_user").hide();
    $("#filtre_client").hide();
    $("#nom_client_hidden").show();
    printTable("listeDeTousLesDevis", response.devis,[], "devisId", [consulterDevisEntantQueOuvrier], "/devis");
}

function onGetMesDevisList(response) {

    $("#listeDeMesDevis").show();
    $("#titre-page").text("Mes devis");
    $("#searchCard").show();// pas encore d'option de recherche pour devis
    $("#searchContent").show();
    $("#filtre_amenagement").show();
    $("#filtre_user").hide();
    $("#filtre_client").hide();
    $("#nom_client_hidden").hide();
    printTable("listeDeMesDevis", response.devis,[], "devisId", [consulterDevisEntantQueClient], "/devis");
}


function onGetMesDevisListOuvrier(response) {

    $("#listeDeMesDevis").show();
    $("#titre-page").text("Liste devis d'un client");
    $("#searchCard").show();
    $("#filtre_amenagement").show();
    $("#filtre_user").hide();
    $("#filtre_client").hide();
    printTable("listeDeMesDevis", response.devis,[], "devisId", [consulterDevisEntantQueOuvrier], "/devis");

}

function onDevisListError(err) {
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

export { onGetTousLesDevisList, onGetMesDevisList, onDevisListError, onGetMesDevisListOuvrier };