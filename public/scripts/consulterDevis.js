import { getData, postData } from "./utilsAPI.js";
import { homeWorker, HomeUser } from "./index.js";
import { ajouterPhotoApresAmenagement, ajouterPhoto, viderLesPhotoApresAmenagement, setAmenagements } from "./insererPhoto.js"
import notify from "./utils.js";

let etatDevis;
let token = localStorage.getItem("token");
let devisID = -1;

function consulterDevisEntantQueOuvrier(url, data) {
    homeWorker("");
    data["action"] = "consulterDevisEnTantQueOuvrier";
    token = localStorage.getItem("token");
    getData(url, data, token, onGetConsulterDevisOuvrier, onGetConsulterError);

    $('.ouvrier').val("");
    $("#searchCard").show();
    $('#searchContent').hide();
    $('#consulterDevis').show();
    $('#versionOuvrier').show();
    $('#versionClient').hide();
};

function consulterDevisEntantQueClient(url, data) {
    HomeUser("");
    data["action"] = "consulterDevisEnTantQueClient";
    token = localStorage.getItem("token");
    getData(url, data, token, onGetConsulterDevisClient, onGetConsulterError);

    $('.client').val("");
    $("#searchCard").show();
    $('#searchContent').hide();
    $('#consulterDevis').show();
    $('#versionClient').show();
    $('#versionOuvrier').hide();
};




function onGetConsulterDevisClient(response) {
    $('#nomVersionClient').html(response.devis["Nom du client"]);
    $('#prenomVersionClient').html(response.devis["Prénom du client"]);
    $("#titre-page").text("Détails du devis");
    let types = response.devis["Types d'aménagements"];
    let amenagements = "";
    devisID = response.devis["devisId"];
    types = JSON.parse(types);
    for (let i = 0; i < types.length; i++) {
        amenagements += types[i].libelle + ",\n";
    }

    $('#dateDebutVersionClient').html(response.devis["Date de début"]);
    $('#dateVersionClient').html(response.devis["Date devis"]);
    $('#dureeVersionClient').html(response.devis["duree"]);
    $('#typesVersionClient').html(amenagements);
    $('#etatVersionClient').html(response.devis["État d'avancement"]);

    let photosAvantJson = response.devis["Photos Avant"];
    photosAvantJson = JSON.parse(photosAvantJson);
    let tablePhotosAvant = document.getElementById("photosAvantVersionClient");
    tablePhotosAvant.innerHTML = "";
    let tr;


    for (let i = 0; i < photosAvantJson.length; i++) {
        if (i % 3 == 0) {
            tr = document.createElement("tr");
            tablePhotosAvant.appendChild(tr);
        }
        let td = document.createElement("td");
        td.innerHTML = "<img class='image' src='" + photosAvantJson[i].Photo + "'id='" + photosAvantJson[i]['Photo id'] + "'/>";
        tr.appendChild(td);
    }
    let photosApresJson = response.devis["Photos Apres"];
    photosApresJson = JSON.parse(photosApresJson);

    let tablePhotosApres = document.getElementById("photosApresVersionClient");
    tablePhotosApres.innerHTML = "";
    for (let i = 0; i < photosApresJson.length; i++) {
        if (i % 3 == 0) {
            tr = document.createElement("tr");
            tablePhotosApres.appendChild(tr);
        }
        let td = document.createElement("td");
        td.innerHTML = "<img class='image' src='" + photosApresJson[i].Photo + "'id='" + photosApresJson[i]['Photo id'] + "'/>";
        tr.appendChild(td);
    }
}

function hideTheRightCheckBoxForState(state) {
    $(".hideAllCheckBox").hide();
    $("#changeSizeRow").css("width", "100%");
    $("#annulerDemande").show();
    $("#hideDropContainer").hide();
    $("#noCheckbox").show();


    switch (state) {
        case "Commande confirmée":

            $("#hideCheckBox2").show();
            $("#hideCheckBox3").show();
            $("#hideCheckBox4").show();

            break;
        case "Absence du paiement de l'acompte":

            $("#hideCheckBox2").show();

            break;
        case "Acompte payé":

            $("#hideCheckBox5").show();
            $("#hideCheckBox6").show();

            break;
        case "Facture de milieu chantier envoyée":

            $("#hideCheckBox6").show();

            $("#annulerDemande").hide();
            break;
        case "Facture de fin de chantier envoyée":
            $("#hideCheckBox7").show();
            $("#annulerDemande").hide();
            break;
        case "Visible":
            $("#hideDropContainer").show();
            $("#annulerDemande").hide();
            $("#noCheckbox").hide();
            $("#changeSizeRow").css("width", "50%");
            $("#hideDropContainer").css("margin-top", "-15%");
            break;
        case "Annulé":
            $("#annulerDemande").hide();
            break;
        default:
            $("#hideCheckBox1").show();
            break;
    }
}

function onGetConsulterDevisOuvrier(response) {

    $("input[type=checkbox]").each(function () {
        $(this).prop('checked', false);
    });
    $("#titre-page").text("Détails du devis");
    $('#nomVersionOuvrier').html(response.devis["Nom du client"]);
    $('#prenomVersionOuvrier').html(response.devis["Prénom du client"]);
    devisID = response.devis["devisId"];
    let amenagements = "";
    let types = response.devis["Types d'aménagements"];
    types = JSON.parse(types);
    setAmenagements(types);
    for (let i = 0; i < types.length; i++) {
        amenagements += types[i].libelle + ",\n";
    }
    let etat = response.devis["État d'avancement"];

    hideTheRightCheckBoxForState(etat);

    $('#dateDebutVersionOuvrier').html(response.devis["Date de début"]);
    $('#dateVersionOuvrier').html(response.devis["Date devis"]);
    $('#dureeVersionOuvrier').html(response.devis["duree"]);
    $('#typesVersionOuvrier').html(amenagements);
    $('#etatVersionOuvrier').html(response.devis["État d'avancement"]);
    etatDevis = response.devis["État d'avancement"];
    let photosAvantJson = response.devis["Photos Avant"];
    photosAvantJson = JSON.parse(photosAvantJson);
    let tablePhotosAvant = document.getElementById("photosAvantVersionOuvrier");
    tablePhotosAvant.innerHTML = "";
    let tr;
    for (let i = 0; i < photosAvantJson.length; i++) {
        if (i % 3 == 0) {
            tr = document.createElement("tr");
            tablePhotosAvant.appendChild(tr);
        }
        let td = document.createElement("td");


        td.innerHTML = "<img class='image' src='" + photosAvantJson[i].Photo + "'id='" + photosAvantJson[i]['Photo id'] + "'/>";

        tr.appendChild(td);


    }

    let photosApresJson = response.devis["Photos Apres"];
    photosApresJson = JSON.parse(photosApresJson);
    let tablePhotosApres = document.getElementById("photosApresVersionOuvrier");
    tablePhotosApres.innerHTML = "";
    viderLesPhotoApresAmenagement();
    for (let i = 0; i < photosApresJson.length; i++) {
        if (i % 3 == 0) {
            tr = document.createElement("tr");
            tablePhotosApres.appendChild(tr);
        }
        let td = document.createElement("td");
        td.innerHTML = "<img class='image' src='" + photosApresJson[i].Photo + "'id='" + photosApresJson[i]['Photo id'] + "'/>";
        ajouterPhoto(photosApresJson[i]);
        tr.appendChild(td);
    }
}

function onGetConsulterError(err) {
    notify("error","Erreur");
    $('#loader').hide();
}

function onPostCheckBox(response) {
    notify("success", "L'état a bien été mis à jour");
    $('#etatVersionOuvrier').text(response.etat);
    etatDevis = response.etat;
    hideTheRightCheckBoxForState(etatDevis);
}

function onNewDate(response) {
    notify("success", "La date de début des travaux a bien été modifiée");
    $('#dateDebutVersionOuvrier').text(response.date);
    $('#etatVersionOuvrier').text(response.etat);
    etatDevis = response.etat;
    hideTheRightCheckBoxForState(etatDevis);
}

function onDeleteDate(response) {
    notify("success", "La date a bien été supprimée");
    $('#dateDebutVersionOuvrier').text("-");
    $('#etatVersionOuvrier').text(response.etat);
    etatDevis = response.etat;
    hideTheRightCheckBoxForState(etatDevis);
}

function onNewDateCommande(response) {
    notify("success", "L'état a bien été mis à jour");
    $('#dateDebutVersionOuvrier').text(response.date);
    $('#etatVersionOuvrier').text(response.etat);
    etatDevis = response.etat;
    hideTheRightCheckBoxForState(etatDevis);
}


function onCheckBoxError(response) {
    notify("error", "Les modifications n'ont pas pu être effectuées");
}



function onErrorAmenagements(response) {
    notify("error","Erreur dans la requête");
}

$(document).ready(function () {

    $("#drop-container-apres").on('dragenter', function (e) {
        e.preventDefault();
        $(this).css('border', '#39b311 2px dashed');
        $(this).css('background', '#f1ffef');
    });

    $("#drop-container-apres").on('drop', function (e) {
        e.preventDefault();
        $(this).css('border', '#07c6f1 2px dashed');
        $(this).css('background', '#FFF');
        let fileList = e.originalEvent.dataTransfer.files;
        if (etatDevis != "Visible") {
            notify("error", "Vous ne pouvez pas ajouter de photos après aménagements");
        } else {
            for (let x = 0; x < fileList.length; x++) {
                ajouterPhotoApresAmenagement(fileList[x], devisID);
            }
        }
    });

    $("#drop-container-apres").on('dragover', function (e) {
        e.preventDefault();
    })

    $("#selectImageApres").click(function (e) {
        e.preventDefault();
        $("#inputFileApres").trigger('click');
    });

    $("#inputFileApres").on("change", function (e) {
        var file = e.target.files[0];
        ajouterPhotoApresAmenagement(file, devisID);
    });

    $('#confirmerCommande').click(function () {

        if ($("#inputDateDebut").val() != "") {
            const data = {
                action: "confirmerCommande",
                date: $("#inputDateDebut").val(),
                id: devisID
            };
            postData("/devis", data, localStorage.getItem("token"), onNewDateCommande, onCheckBoxError);
        } else {
            notify("error", "Veuillez saisir une date valide");
        }

    });

    $("#supprimerDateDebut").click(function () {

        const data = {
            action: "supprimerDateDebut",
            id: devisID
        }

        postData("/devis", data, localStorage.getItem("token"), onDeleteDate, onCheckBoxError);

    });

    $('#confirmerDateDebut').click(function () {
        const data = {
            action: "confirmerDateDebut",
            id: devisID
        };
        postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);
    });

    $('#repousserDateDebut').click(function () {

        if ($("#inputRepousser").val() != "") {
            const data = {
                action: "repousserDateDebut",
                newDate: $("#inputRepousser").val(),
                id: devisID
            };
            postData("/devis", data, localStorage.getItem("token"), onNewDate, onCheckBoxError);
        } else {
            notify("error", "Veuillez saisir une date valide");
        }

    });

    $('#indiquerFactureMilieu').click(function () {

        const data = {
            action: "indiquerFactureMilieuEnvoyee",
            id: devisID
        };
        postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);

    })

    $('#indiquerFactureFin').click(function () {

        const data = {
            action: "indiquerFactureFinEnvoyee",
            id: devisID
        };
        postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);

    })

    $('#rendreVisible').click(function () {

        hideTheRightCheckBoxForState("Visible");

        const data = {
            action: "rendreVisible",
            id: devisID
        };
        postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);

    })

    $('#annulerDemande').click(function () {
        const data = {
            action: "annulerDemande",
            id: devisID
        };
        postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);
    })
});
export { consulterDevisEntantQueClient, consulterDevisEntantQueOuvrier };