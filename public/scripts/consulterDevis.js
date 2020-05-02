import { getData, postData } from "./utilsAPI.js";
import { homeWorker } from "./index.js";
import { ajouterPhotoApresAmenagement, ajouterPhoto, viderLesPhotoApresAmenagement, setAmenagements } from "./insererPhoto.js"
import notify from "./utils.js";
let checkBox;

let token = localStorage.getItem("token");
let devisID = -1;

function consulterDevisEntantQueOuvrier(url, data) {
    console.log(url);
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
    homeWorker("");
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
    console.log(response);
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

    $('#dateDebutVersionOuvrier').html(response.devis["Date de début"]);
    $('#dateVersionOuvrier').html(response.devis["Date devis"]);
    $('#dureeVersionOuvrier').html(response.devis["duree"]);
    $('#typesVersionOuvrier').html(amenagements);
    $('#etatVersionOuvrier').html(response.devis["État d'avancement"]);

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
    console.error(err.responseJSON.error);
    $('#loader').hide();
}

function onPostCheckBox(response) {
    //console.log(response.etat);
    notify("success", "L'état a bien été mis à jour");
    $('#etatVersionOuvrier').text(response.etat);

}

function onNewDate(response) {
    notify("success", "La date a bien été repoussée");
    $('#dateDebutVersionOuvrier').text(response.date);
    console.log(response.date);
}

function onCheckBoxError(response) {
    console.log(response);
    checkBox.prop('checked', false);
    notify("error", "Les modifications n'ont pas pu être effectuées");

}



function onErrorAmenagements(response) {
    console.error(response);
}

$(document).ready(function () {

    $("#drop-container-apres").on('dragenter', function (e) {
        e.preventDefault();
        console.log("dragenter");
        $(this).css('border', '#39b311 2px dashed');
        $(this).css('background', '#f1ffef');
    });

    $("#drop-container-apres").on('drop', function (e) {
        e.preventDefault();
        console.log("ici");
        $(this).css('border', '#07c6f1 2px dashed');
        $(this).css('background', '#FFF');
        let fileList = e.originalEvent.dataTransfer.files;
        for (let x = 0; x < fileList.length; x++) {
            ajouterPhotoApresAmenagement(fileList[x], devisID);
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

    $('#confirmerCommande').change(function () {
        if ($(this).is(":checked")) {
            checkBox = $(this);
            const data = {
                action: "confirmerCommande",
                id: devisID
            };
            postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);
        }
    });

    $("#supprimerDateDebut").change(function () {
        if ($(this).is(":checked")) {

            checkBox = ($(this));

            const data = {
                action: "supprimerDateDebut",
                id: devisID
            }

            postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);
        }

    });

    $('#confirmerDateDebut').change(function () {
        if ($(this).is(":checked")) {
            checkBox = $(this);
            const data = {
                action: "confirmerDateDebut",
                id: devisID
            };
            postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);
        }
    });

    $('#repousserDateDebut').change(function () {
        if ($(this).is(":checked")) {
            checkBox = $(this);
            const data = {
                action: "repousserDateDebut",
                newDate: $("#inputRepousser").val(),
                id: devisID
            };
            postData("/devis", data, localStorage.getItem("token"), onNewDate, onCheckBoxError);
        }
    });

    $('#indiquerFactureMilieu').change(function () {
        if ($(this).is(":checked")) {
            checkBox = $(this);
            const data = {
                action: "indiquerFactureMilieuPayee",
                id: devisID
            };
            postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);
        }
    })

    $('#indiquerFactureFin').change(function () {
        if ($(this).is(":checked")) {
            checkBox = $(this);
            const data = {
                action: "indiquerFactureFinPayee",
                id: devisID
            };
            postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);
        }
    })

    $('#rendreVisible').change(function () {
        if ($(this).is(":checked")) {
            checkBox = $(this);
            const data = {
                action: "rendreVisible",
                id: devisID
            };
            postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);
        }
    })

    $('#annulerDemande').click(function () {
        console.log("Click");
        checkBox = $(this);
        const data = {
            action: "annulerDemande",
            id: devisID
        };
        postData("/devis", data, localStorage.getItem("token"), onPostCheckBox, onCheckBoxError);
    })
});
export { consulterDevisEntantQueClient, consulterDevisEntantQueOuvrier };