import { getData } from "./utilsAPI.js";


function affichageListeUser(containerElementId, arrayToPrint, thTab) {
    
    $("#carouselExampleIndicators").hide();
    console.log(arrayToPrint);
    let div_container = document.getElementById(containerElementId);
    div_container.innerHTML = "";
    $("#"+containerElementId).show();
    let table = document.createElement("table");
    table.className = "table";
    table.className = "mt-10";
    div_container.appendChild(table);

    let thead = document.createElement("thead");
    table.appendChild(thead);
    let tr = document.createElement("tr");
    thead.appendChild(tr);
    for (let i = 0; i < thTab.length; i++) {
        let th = document.createElement("th");
        th.innerHTML = thTab[i];
        tr.appendChild(th);
    }
    let tbody = document.createElement("tbody");
    table.appendChild(tbody);
    for (let x = 0; x < arrayToPrint.length; x++) {
        let trData = document.createElement("tr");
        tbody.appendChild(trData);
        const user = arrayToPrint[x];
        for (const propriete in user) {
            if (propriete === "nom" || propriete === "prenom" || propriete === "dateInscription"
                || propriete === "pseudo" || propriete === "ville" || propriete === "email") {
                let monChamp = document.createElement("td");
                monChamp.innerHTML = user[propriete];
                trData.appendChild(monChamp);

            }
        }


    }


}
$(document).ready(function () {
    $("#rechercher_user").on('click', function (e) {
        let token = localStorage.getItem("token");
        console.log(token);
        getData("/listeUser", token, onGetUserList, onUserListError);
    });
    function onGetUserList(response) {
        $("#listeUser").show();
        let thtabUser = new Array("Nom", "Prénom", "Pseudo", "Ville de résidence", "e-mail", "Date d'inscription");
        affichageListeUser("listeUser", response.listeUser, thtabUser);

    }

    function onUserListError(err) {
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