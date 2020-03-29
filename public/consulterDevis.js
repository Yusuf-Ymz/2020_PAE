import { getData, postData } from "./utilsAPI.js";
import { homeWorker } from "./index.js";

let token = localStorage.getItem("token");

function consulterDevisEntantQueOuvrier(url, data) {
    console.log(url);
    homeWorker();
    data["action"] = "consulterDevis";
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
    homeWorker();
    data["action"] = "consulterDevis";
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
    console.log(response);
    let types = response.devis["Types d'aménagements"];
    let amenagements = "";
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
        td.innerHTML = "<img class='image' src='" + photosAvantJson[i].Photo + "'/>";
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

    $('#dateDebutVersionOuvrier').html(response.devis["Date de début"]);
    $('#dateVersionOuvrier').html(response.devis["Date devis"]);
    $('#dureeVersionOuvrier').html(response.devis["duree"]);
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
    console.error(err.responseJSON.error);
}
//Eventuelles erreurs à corriger et ajouter taprès ceci!
$('#confirmerCommande').change(function(){
    if($(this).is(":checked")) {
      const data = {
        action: "confirmerCommande",
        etat: "accepte"
      };
      postData("/devis", data, null, null, null);

    } else {
      const data = {
        action: "confirmerCommande",
        etat: "nonAccepte"
      };
      postData("/devis", data, null, null, null);
    }
});

$('#confirmerDateDebut').change(function(){
    if ($(this).is(":checked")){
        console.log("confirmer date début des travaux checked");
        const data = {
        action: "confirmerDateDebut",
        etat: "accepte"
      };
      postData("/devis", data, null, null, null);
    } else {
      const data = {
        action: "confirmerDateDebut",
        etat: "nonAccepte"
      };
      postData("/devis", data, null, null, null);
    }
});

$('#repousserDateDebut').change(function(){
    if ($(this).is(":checked")){
        console.log("confirmer date début des travaux checked");
        const data = {
        action: "repousserDateDebut",
        etat: "accepte",
        newDate: $("#inputRepousser").val()
      };
      postData("/devis", data, null, null, null);
    } else {
      const data = {
        action: "repousserDateDebut",
        etat: "nonAccepte"
      };
      postData("/devis", data, null, null, null);
    }
});

export { consulterDevisEntantQueClient, consulterDevisEntantQueOuvrier };