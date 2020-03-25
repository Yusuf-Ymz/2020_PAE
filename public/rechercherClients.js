import { getData } from "./utilsAPI.js";
import {printTable} from "./utilsHtml.js"
import {homeWorker} from "./index.js";

import  onGetMesDevisList   from "./rechercherDevis.js";

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
        $("#searchContent").show();
        $("#filtre_client").show();
        $("#filtre_utilisateur").hide();
        $("#filtre_amenagement").hide();
        let thtabClient =  new Array("N° utilisateur ","Nom", "prenom", "Email", "Rue", "N° porte","Boite" ,"Ville","Code postal","Telephone");
        
        let nombtnTab = ["visualiser devis"];
        console.log("fn gkx");
        printTable("listerClients", response.clients,nombtnTab,"N° client",doGetClientDevis,"/devis");

    }

    function doGetClientDevis(url,data){
        
        homeWorker();

        let token = localStorage.getItem("token");
        data["action"] ="devisDuClient";
        
        $("#listeDeMesDevis").show();
        getData(url, data, token, onGetMesDevisList, onClientListError);
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