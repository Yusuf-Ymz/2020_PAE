import { getData } from "./utilsAPI.js";
import {printTable} from "./utilsHtml.js"
import {homeWorker,HomeUser} from "./index.js";
import {consulterDevisEntantQueClient,consulterDevisEntantQueOuvrier} from "./consulterDevis.js"

$(document).ready(function () {

    $('#rechercher_tous_les_devis').on('click',function (e) {
        console.log("cliquer ");
        homeWorker();
        let token = localStorage.getItem("token");
        const data = {
            action: "tousLesDevis"
        }
        getData("/devis", data, token, onGetTousLesDevisList, onDevisListError);
    });

    $('#rechercher_mes_devis').on('click',function (e) {
        console.log("cliquer ");
        homeWorker();
        let token = localStorage.getItem("token");
        const data = {
            action: "mesDevis"
        }
        getData("/devis", data, token, onGetMesDevisList, onDevisListError);
    });




    function onGetTousLesDevisList(response) {
        $("#listeDeTousLesDevis").show();
  
        $("#searchCard").show();// pas encore d'option de recherche pour devis
        let nombtnTab = ["visualiser devis"];
        printTable("listeDeTousLesDevis", response.devis, nombtnTab,"devisId",consulterDevisEntantQueOuvrier,"/devis");
    }

    function onGetMesDevisList(response) {
        $("#listeDeMesDevis").show();
  
        $("#searchCard").show();// pas encore d'option de recherche pour devis
        let nombtnTab = ["visualiser devis"];
        printTable("listeDeMesDevis", response.devis, nombtnTab,"devisId",consulterDevisEntantQueClient,"/devis");
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