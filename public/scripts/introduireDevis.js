"use strict";
import { getData, postData, specialGetData } from "./utilsAPI.js";

import notify from "./utils.js";
import { homeWorker, HomeUser } from "./index.js";

let idClient = -1;


function displayAmenagements(response) {
    let amenagements = response.amenagements;
    let divAmenagements = $("#amenagements")[0];
    $("#amenagements").text("");
    
    for (let i = 0; i < amenagements.length; i++) {
        let id = "amenagement" + amenagements[i]["id"];

        let div = document.createElement("div");
        div.className = " col-md-6 mt-3";
        let label = document.createElement("label");
        label.className = "customcheck";
        label.htmlFor = id;
        label.innerHTML = amenagements[i]["libelle"];

        let span = document.createElement("span");
        span.className = "checkmark";

        let input = document.createElement("input");
        input.type = "checkbox";
        input.className = " amenagements ml-2";
        input.id = id;
        input.value = amenagements[i]["id"];



        label.appendChild(input);
        label.appendChild(span);
        div.appendChild(label);
        divAmenagements.appendChild(div);
    }


}

function onError(err) {
    console.log(err);
    $('#loader').hide();
    console.log(err.responseText);
    notify("error", err.responseText);
}

$(document).ready(function (e) {

    $("#indroduire_devis").click(function (e) {
        e.preventDefault();
        homeWorker();
        $("#introduireDevis").show();
        $("#searchCard").show();
        $("#card").show();
        $("#searchContent").show();
        $("#filtre_client").show();
        $("#filtre_user").hide();
        $("#filtre_amenagement").hide();

        $("#titre-page").text("Introduire devis");
        getData("/amenagement", null, localStorage.getItem("token"), displayAmenagements, onError);
        getListClient();
    });

    $("#insererDevis").click(function (e) {
        e.preventDefault();
        let divAmenagement = document.getElementById("amenagements");
        let tabAmenagements = divAmenagement.querySelectorAll('input[type="checkbox"]');
        let amenagements = new Array();
        for (let i = 0; i < tabAmenagements.length; i++) {
            if (tabAmenagements[i].checked === true) {
                amenagements.push(tabAmenagements[i].value);
            }

        }
        let photos = new Array();
        let images = $("#drop-container")[0].getElementsByTagName("img");
        for (let i = 0; i < images.length; i++) {
            let im = images[i].src;
            im = im.replace(",", "?????");
            photos.push(im);
        }
        console.log(amenagements);
        console.log(photos);
        let data = {
            action: "insererDevis",
            idClient: $("#idClient").val(),
            dateDebut: $("#date").val(),
            montant: $("#montant").val(),
            nbJours: $("#dureeTravaux").val(),
            amenagements: amenagements,
            photos: photos,
        };
        postData("/devis", data, localStorage.getItem("token"), onPost, onError);
    })

    function onPost(response) {
        $(':input').val('');
        getData("/amenagement", null, localStorage.getItem("token"), displayAmenagements, onError);
        notify("success", "Devis Introduit");
        
    }
    $("#ajouterClient").click(function (e) {
        e.preventDefault();
        // TODO à revoir
        if (!$("#nomC")[0].checkValidity()) {
            notify("error", "Erreur champ nom invalide");
        } else if (!$("#prenomC")[0].checkValidity()) {
            notify("error", "Erreur champ prenom invalide");
        } else if (!$("#rueC")[0].checkValidity()) {
            notify("error", "Erreur champ rue invalide");
        } else if (!$("#numC")[0].checkValidity()) {
            notify("error", "Erreur champ numéro invalide");
        } else if (!$("#cpC")[0].checkValidity()) {
            notify("error", "Erreur champ code postal invalide");
        } else if (!$("#villeC")[0].checkValidity()) {
            notify("error", "Erreur champ ville invalide");
        } else if (!$("#emailC")[0].checkValidity()) {
            notify("error", "Erreur champ email invalide");
        } else if (!$("#telC")[0].checkValidity()) {
            notify("error", "Erreur champ téléphone invalide");
        } else {
            let data = {
                action: "ajouterClient",
                nom: $("#nomC").val(),
                prenom: $("#prenomC").val(),
                rue: $("#rueC").val(),
                numero: $("#numC").val(),
                //  boite: $("#boiteC").val(),
                cp: $("#cpC").val(),
                ville: $("#villeC").val(),
                email: $("#emailC").val(),
                telephone: $("#telC").val(),
            };
            postData("/client", data, localStorage.getItem("token"), onPostSuccess, onPostError);
        }

    });

    function onPostSuccess(response) {
        getListClient();
        $("#myModal").modal('hide');
        let client = response.client;
        idClient = client.idClient;
        $("#idClient").val(idClient);
        $("#nomInfo").val(client.nom);
        $("#prenomInfo").val(client.prenom);
        notify("success", "Client ajouté");
    }

    function onPostError(response) {
        console.log(response.error);
    }




    $("#drop-container").on('dragenter', function (e) {
        e.preventDefault();
        console.log("dragenter");
        $(this).css('border', '#39b311 2px dashed');
        $(this).css('background', '#f1ffef');
    });

    /* cassé
    $("#drop-container").on('dragleave', function(e) {
        e.preventDefault();
        console.log("dragleave");
        $(this).css('border', '#07c6f1 2px dashed');
        $(this).css('background', '#ffffff');
    });*/


    $("#drop-container").on('drop', function (element) {
        element.preventDefault();
        console.log("drop");
        $(this).css('border', '#07c6f1 2px dashed');
        $(this).css('background', '#FFF');
        let fileList = element.originalEvent.dataTransfer.files;
        console.log(fileList);
        for (let x = 0; x < fileList.length; x++) {
            let file = fileList[x];
            converFile(file);
        }

    });

    function converFile(file) {
        var reader = new FileReader();
        reader.onloadend = function () {
            let img = document.createElement("img");
            img.className = "image rounded imageAvant";
            $("#drop-container").append(img);
            $(img).attr("src", reader.result);
        }
        reader.readAsDataURL(file);
    }
    $("#inputFile").on("change",function (e) {
        var file = e.target.files[0];
        converFile(file);
    });
    $("#drop-container").on('dragover', function (e) {
        e.preventDefault();
    })

    $("#selectImage").click(function (e) {
        e.preventDefault();
        $("#inputFile").trigger('click');
    });



});
function getListClient() {
    let data = {
        action: "listerClients",

    };
    getData("/client", data, localStorage.getItem("token"), displayClient, onError);
}



function displayClient(response) {


    let div_container = $("#tableClients")[0];
    div_container.innerHTML = "";
    let table = document.createElement("table");
    table.className = "table table-bordered mt-0";

    div_container.appendChild(table);
    let thead = document.createElement("thead");
    table.appendChild(thead);


    let tr = document.createElement("th");
    tr.innerHTML = "Nom";
    thead.appendChild(tr);


    tr = document.createElement("th");
    tr.innerHTML = "Prénom";
    thead.appendChild(tr);

    tr = document.createElement("th");
    tr.innerHTML = "e-mail";
    thead.appendChild(tr);

    tr = document.createElement("th");
    tr.innerHTML = "Ville";
    thead.appendChild(tr);

    tr = document.createElement("th");
    tr.innerHTML = "Code Postal";
    thead.appendChild(tr);

    tr = document.createElement("th");
    tr.innerHTML = "N° téléphone";
    thead.appendChild(tr);

    tr = document.createElement("th");
    tr.innerHTML = "";
    thead.appendChild(tr);

    response = response.clients;

    let tbody = document.createElement("tbody");

    table.appendChild(tbody);

    for (let i = 0; i < response.length; i++) {
        let trData = document.createElement("tr");
        tbody.appendChild(trData);
        const element = response[i];

        let champ = document.createElement("td");
        champ.innerHTML = element["Nom"];
        trData.appendChild(champ);
        champ.className = "nom";

        champ = document.createElement("td");
        champ.innerHTML = element["Prénom"];
        trData.appendChild(champ);
        champ.className = "prenom";

        champ = document.createElement("td");
        champ.innerHTML = element["Email"];
        trData.appendChild(champ);

        champ = document.createElement("td");
        champ.innerHTML = element["Ville"];
        trData.appendChild(champ);

        champ = document.createElement("td");
        champ.innerHTML = element["Code postal"];
        trData.appendChild(champ);

        champ = document.createElement("td");
        champ.innerHTML = element["Téléphone"];
        trData.appendChild(champ);

        champ = document.createElement("td");
        let button = document.createElement("button");
        button.innerHTML = "Sélectionner";
        button.className = "btn btn-primary"
        button.value = element["N° client"];
        champ.appendChild(button);
        trData.appendChild(champ);

        button.addEventListener('click', function (e) {
            e.preventDefault();
            idClient = e.target.value;
            $("#idClient").val(idClient);
            let tr = e.target.parentNode.parentNode;
            let nom = tr.getElementsByClassName('nom');
            let prenom = tr.getElementsByClassName('prenom');
            $("#nomInfo").val(nom[0].innerHTML);
            $("#prenomInfo").val(prenom[0].innerHTML);
        });
    }

}
export { displayClient } 