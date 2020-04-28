import { getData } from "./utilsAPI.js";
import { printTable } from "./utilsHtml.js"
import { homeWorker } from "./index.js";

import { onGetMesDevisListOuvrier } from "./rechercherDevis.js";

function onGetClientList(response) {

    $("#titre-page").text("Liste des clients");
    $("#listerClients").show();
    $("#searchCard").show();
    $("#searchContent").show();
    $("#filtre_client").show();
    $("#filtre_user").hide();
    $("#filtre_amenagement").hide();

    printTable("listerClients", response.clients, [], "N° client", [doGetClientDevis], "/devis");

}

function doGetClientDevis(url, data) {

    homeWorker();
    let token = localStorage.getItem("token");
    data["action"] = "devisDuClient";
    console.log(data["N° client"]);
    $("#listeDeMesDevis").show();
    getData(url, data, token, onGetMesDevisListOuvrier, onClientListError);
}

function onClientListError(err) {
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

export { onGetClientList, onClientListError };