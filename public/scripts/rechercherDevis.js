import { getData } from "./utilsAPI.js";
import { printTable } from "./utilsHtml.js"
import { homeWorker, HomeUser } from "./index.js";
import { consulterDevisEntantQueClient, consulterDevisEntantQueOuvrier } from "./consulterDevis.js"


function onGetTousLesDevisList(response) {
    $("#listeDeTousLesDevis").show();
    $("#titre-page").text("Liste de tous les devis");
    $("#searchCard").show();// pas encore d'option de recherche pour devis

    $("#searchContent").show();
    $("#filtre_amenagement").show();
    $("#filtre_user").hide();
    $("#filtre_client").hide();
    $(".client_hidden").show();
    $("#form_recherche").css('width',"100%");
    printTable("listeDeTousLesDevis", response.devis, [], "devisId", [consulterDevisEntantQueOuvrier], "/devis");
}

function onGetMesDevisList(response) {

    $("#listeDeMesDevis").show();
    $("#titre-page").text("Mes devis");
    $("#searchCard").show();// pas encore d'option de recherche pour devis
    $("#searchContent").show();
    $("#filtre_amenagement").show();
    $("#filtre_user").hide();
    $("#filtre_client").hide();
    $(".client_hidden").hide();
    $("#form_recherche").css('width',"200%");
    printTable("listeDeMesDevis", response.devis, [], "devisId", [consulterDevisEntantQueClient], "/devis");
}


function onGetMesDevisListOuvrier(response) {

    $("#listeDeMesDevis").show();
    $("#titre-page").text("Liste devis d'un client");
    $("#searchCard").show();
    $("#filtre_amenagement").show();
    $("#filtre_user").hide();
    $("#filtre_client").hide();
    
    printTable("listeDeMesDevis", response.devis, [], "devisId", [consulterDevisEntantQueOuvrier], "/devis");

}

function onDevisListError(err) {
    console.error(err);
    $('#loader').hide();
    if (err.responseJSON) {
        Swal.fire({
            position: 'top-end',
            icon: 'error',
            timerProgressBar: true,
            title: err.responseJSON.error,
            showConfirmButton: false,
            timer: 1500
        });
    }
}

export { onGetTousLesDevisList, onGetMesDevisList, onDevisListError, onGetMesDevisListOuvrier };