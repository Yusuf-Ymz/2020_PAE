import { getData } from "./utilsAPI.js";
import  printTable  from "./utilsHtml.js"
import {homeWorker} from "./index.js";
$(document).ready(function () {

    $('#rechercher_tous_les_clients').on('click',function (e) {
       homeWorker();
    
        let token = localStorage.getItem("token");
        const data = {
            action: "/listerClients"
        }
        getData("/client", data, token, onGetClientList, onClientListError);
    });




    function onGetClientList(response) {
        $("#listerClients").show();
        $("#searchCard").show();
        $("#filtre_client").show();
        $("#filtre_utilisateur").hide();
        $("#filtre_amenagement").hide();
        let thtabClient = new Array("Code postal", "e-mail", "N°", "Nom", "Prénom","N° Telephone", "Devis");
        printTable("listerClients", response.clients, thtabClient);
    }

    function onClientListError(err) {
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