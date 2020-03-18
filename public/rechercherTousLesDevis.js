import { getData } from "./utilsAPI.js";

$(document).ready(function () {
    $('#recherches_tous_les_devis').onClick(function (e){
        let token = localStorage.getItem("token");
        getData("/devis",data , token, onGetDevisList, onDevisListError);
    });

    function onGetDevisList(response) {
        $("#listeUser").show();
        let thtabUser = new Array("Nom", "Prénom", "Pseudo", "Ville de résidence", "e-mail", "Date d'inscription");
        affichageListeDevis("listeDeTousLesDevis", response.listeUser, thtabUser);
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