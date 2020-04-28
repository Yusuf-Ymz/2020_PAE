import { getData } from "./utilsAPI.js";
import { printTable } from "./utilsHtml.js";
import { homeWorker } from "./index.js";


$(document).ready(function () {
    $("#rechercher_user").on('click', function (e) {
        homeWorker();
        let token = localStorage.getItem("token");
        console.log(token);
        const data = {
            action: 'listeUser'
        };

        getData("/user", data, token, onGetUserList, onUserListError);
    });
});

function onGetUserList(response) {
    $("#listeUser").show();
    $("#searchCard").show();
    $("#searchContent").show();
    $("#filtre_user").show();
    $("#filtre_client").hide();
    $("#filtre_amenagement").hide();
    $("#selectAmenagementAccueil").hide();
    $("#titre-page").text("Liste des utilisateurs");
    printTable("listeUser", response.listeUser);

}



function onUserListError(err) {
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

export { onGetUserList, onUserListError }