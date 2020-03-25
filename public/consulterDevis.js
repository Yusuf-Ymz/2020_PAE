import { getData } from "./utilsAPI.js";
import { homeWorker } from "./index.js";

let token = localStorage.getItem("token");

function consulterDevisEntantQueOuvrier(url, data) {
    console.log(url);
    data["action"] = "consulterDevis";
    getData(url, data, token, onGetConsulterDevisOuvrier, onGetConsulterError);
    homeWorker();
    $("#searchCard").show();
    $('#searchContent').hide();
    $('#consulterDevis').show();
    $('#versionOuvrier').show();
    $('#versionClient').hide();
};

function consulterDevisEntantQueClient(url, data) {
    data["action"] = "consulterDevis";
    getData(url, data, token, onGetConsulterDevisClient, onGetConsulterError);
    homeWorker();
    $("#searchCard").show();
    $('#searchContent').hide();
    $('#consulterDevis').show();
    $('#versionClient').show();
    $('#versionOuvrier').hide();
};

function onGetConsulterDevisClient(response) {
    $('#nomVersionClient').html(response.devis["Nom du client"]);
    $('#prenomVersionClient').html(response.devis["Prénom du client"]);

    let types = response.devis["Types d'aménagements"];
    let amenagements = "";
    types = JSON.parse(types);
    for (let i = 0; i < types.length; i++) {
        amenagements += types[i].libelle + ",\n";
    }

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
        td.innerHTML = "<img class='image' src='" + photosApresJson[i].Photo + "'/>";
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
        td.innerHTML = "<img class='image' src='" + photosApresJson[i].Photo + "'/>";
        tr.appendChild(td);
    }
}

function onGetConsulterDevisOuvrier(response) {
    console.log(response);
    $('#nomVersionOuvrier').html(response.devis["Nom du client"]);
    $('#prenomVersionOuvrier').html(response.devis["Prénom du client"]);

    let amenagements = "";
    let types = response.devis["Types d'aménagements"];
    types = JSON.parse(types);
    for (let i = 0; i < types.length; i++) {
        amenagements += types[i].libelle + ",\n";
    }

    $('#typesVersionOuvrier').html(amenagements);
    $('#etatVersionOuvrier').html(response.devis["État d'avancement"]);

    console.log(response.devis["Photos Avant"]);
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
        td.innerHTML = "<img class='image' src='" + photosAvantJson[i].Photo + "'/>";
        tr.appendChild(td);
    }

    let photosApresJson = response.devis["Photos Apres"];
    photosApresJson = JSON.parse(photosApresJson);
    let tablePhotosApres = document.getElementById("photosApresVersionOuvrier");
    tablePhotosApres.innerHTML = "";
    for (let i = 0; i < photosApresJson.length; i++) {
        if (i % 3 == 0) {
            tr = document.createElement("tr");
            tablePhotosApres.appendChild(tr);
        }
        let td = document.createElement("td");
        td.innerHTML = "<img class='image' src='" + photosApresJson[i].Photo + "'/>";
        tr.appendChild(td);
    }
}

function onGetConsulterError(err) {
    console.error(err);
}

export { consulterDevisEntantQueClient, consulterDevisEntantQueOuvrier };