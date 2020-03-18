import { getData } from "./utilsAPI.js";
import printTable from "./utilsHtml.js"

$(document).ready(function () {

    $('#rechercher_tous_les_devis').on('click',function (e) {
        console.log("cliquer ")
        let token = localStorage.getItem("token");
        const data = {
            action: "tousLesDevis"
        }
        getData("/devis", data, token, onGetDevisList, onDevisListError);
    });




    function onGetDevisList(response) {
        $("#listeDeTousLesDevis").show();
        let thtabUser = new Array("Id", "Photo", "Client Id", "Date de début", "", "Date d'inscription");
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