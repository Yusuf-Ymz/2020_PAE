import { getData } from "./utilsAPI.js";
import printTable from "./utilsHtml.js"
import {homeWorker} from "./index.js";
$(document).ready(function () {

    $('#rechercher_tous_les_devis').on('click',function (e) {
        console.log("cliquer ");
        homeWorker();
        let token = localStorage.getItem("token");
        const data = {
            action: "tousLesDevis"
        }
        getData("/devis", data, token, onGetDevisList, onDevisListError);
    });

    $('#rechercher_mes_devis').on('click',function (e) {
        console.log("cliquer ");
        homeWorker();
        let token = localStorage.getItem("token");
        const data = {
            action: "mesDevis"
        }
        getData("/devis", data, token, onGetDevisList, onDevisListError);
    });




    function onGetDevisList(response) {
        $("#listeDeTousLesDevis").show();
  
        $("#searchCard").show();// pas encore d'option de recherche pour devis
        let thtabUser = new Array("Client Id", "Date de début", "Devis Id", "Durée", "État", "Montant Total", "Photo préférée");
        printTable("listeDeTousLesDevis", response.devis, thtabUser);
    }

    function onDevisListError(err) {
        console.error(err);
        Swal.fire({
            position: 'top-end',
            icon: 'error',
            timerProgressBar: true,
            title: err.responseJSON.error,
            showConfirmButton: false,
            timer: 1500
        })
    }
});