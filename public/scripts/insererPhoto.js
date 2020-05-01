"use strict";
import { postData, chooseHeaderForRequest } from "./utilsAPI.js";
import notify from "./utils.js";

let z = 0;
let modals = new Array();
let idxDernierModalTraite = 0;
let aAfficheLePremier = false;
let numModal = 1;
let devis;
let amenagements = new Array();
let photos = new Array();

function setAmenagements(amenagementsDuDevis){
    amenagements = amenagementsDuDevis;
}

function viderLesPhotoApresAmenagement() {
    photos = Array();
}

function ajouterPhoto(photo) {
    photos.push(photo);
}

function displayPhotoApresAmenagement() {
    let tablePhotosApres = document.getElementById("photosApresVersionOuvrier");
    tablePhotosApres.innerHTML = "";
    let tr;
    for (let i = 0; i < photos.length; i++) {
        if (i % 3 == 0) {
            tr = document.createElement("tr");
            tablePhotosApres.appendChild(tr);
        }
        let td = document.createElement("td");
        td.innerHTML = "<img class='image' src='" + photos[i].Photo + "'id='" + photos[i]['Photo id'] + "'/>";
        tr.appendChild(td);
    }
}

function ajouterPhotoApresAmenagement(image, idDevis) {
    var reader = new FileReader();
    devis = idDevis;
    reader.onloadend = function () {
        z += 2
        let modal = createModal(reader.result, idDevis);
        numModal += 1;
        $("#versionOuvrier").append(modal);
        modals.push(modal);
        if (!aAfficheLePremier) {
            afficherModalPhotoSuivant();
            aAfficheLePremier = true;
        }
    }
    reader.readAsDataURL(image);
}

function afficherModalPhotoSuivant() {
    if (modals[idxDernierModalTraite]) {
        $(modals[idxDernierModalTraite]).modal('show');
        idxDernierModalTraite += 1;
        return true;
    } else {
        aAfficheLePremier = false;
        return false;
    }
}

function createCheckBox(text, id, className) {
    let div = document.createElement("div");
    div.className = "mt-3";
    let label = document.createElement("label");
    label.className = "customcheck";
    label.htmlFor = "check" + id;
    label.innerHTML = text;

    let span = document.createElement("span");
    span.className = "checkmark";
    span.style.border = "1px solid black";
    let input = document.createElement("input");
    input.type = "checkbox";
    input.className = "ml-2 " + className;
    input.id = "check" + id;

    label.appendChild(input);
    label.appendChild(span);
    div.appendChild(label);

    return div;
}


function converFileToImg(file, div, className) {
    var reader = new FileReader();
    reader.onloadend = function () {
        let img = document.createElement("img");
        img.className = "image rounded " + className;
        div.append(img);
        img.src = reader.result;
    }
    reader.readAsDataURL(file);
}

function createOptionSelect() {
    let div = document.createElement("div");
    div.className = "form-group mt-3";

    let label = document.createElement("label");
    label.innerHTML = "Séléctionner un type d'aménagement";
    label.htmlFor = "amenagementsPhoto";

    let selectOpt = document.createElement("select");
    selectOpt.className = "form-control optionSelect";
    selectOpt.id = "amenagementsPhoto";

    for (let i = 0; i < amenagements.length; i++) {
            let option = document.createElement("option");
            option.value = amenagements[i].id;
            option.innerHTML = amenagements[i].libelle;
            selectOpt.appendChild(option);
        
    }
    div.appendChild(label);
    div.appendChild(selectOpt);
    return div;
}

function createModal(image, idDevis) {
    let divModal = document.createElement("div");
    divModal.className = "modal fade";
    divModal.id = "modal" + numModal;

    let modalDialog = document.createElement("div");
    modalDialog.className = "modal-dialog modal-dialog-centered modal-lg";

    let modalContent = document.createElement("div");
    modalContent.className = "modal-content";

    let modalHeader = document.createElement("div");
    modalHeader.className = "modal-header";
    let header = document.createElement("h4");
    header.innerHTML = "Inserer une photo après aménagements";
    modalHeader.appendChild(header);

    let modalBody = document.createElement("div");
    modalBody.className = "modal-body container-fluid";
    let row = document.createElement("div");
    row.className = "row";

    let divPhoto = document.createElement("div");
    divPhoto.className = "col-md-5 ";
    let img = document.createElement("img");
    img.className = "image-modal";
    img.src = image;

    let inputs = document.createElement("div");
    inputs.className = "col-md-7 ";

    let checkbox = createCheckBox("Photo préférée ?", z - 1, "isPhotoPref");
    let checkbox2 = createCheckBox("Photo visible ?", z, "isPhotoVisible");

    let optionSelect = createOptionSelect();

    let modalFooter = document.createElement("div");
    modalFooter.className = "modal-footer";

    let divAjouter = document.createElement("div");
    divAjouter.className = "position-absolute top-right mr-3";
    let buttonAjouter = document.createElement("button");
    buttonAjouter.innerHTML = "Ajouter";
    buttonAjouter.className = "btn btn-success";
    buttonAjouter.value = "modal" + numModal;
    divAjouter.appendChild(buttonAjouter);

    let divCancel = document.createElement("div");
    divCancel.className = "position-absolute top-left ml-3";
    let buttonCancel = document.createElement("button");
    buttonCancel.innerHTML = "Annuler";
    buttonCancel.className = "btn btn-danger";
    divCancel.appendChild(buttonCancel);

    divPhoto.appendChild(img);
    modalBody.appendChild(row);
    row.appendChild(divPhoto);
    inputs.appendChild(optionSelect);
    inputs.appendChild(checkbox);
    inputs.appendChild(checkbox2);
    row.appendChild(inputs);

    divModal.appendChild(modalDialog);
    modalDialog.appendChild(modalContent);
    modalContent.appendChild(modalHeader);
    modalContent.appendChild(modalBody);
    modalContent.appendChild(modalFooter);
    modalFooter.appendChild(divAjouter);
    modalFooter.appendChild(divCancel);
    modalFooter.style.height = "50px";

    buttonCancel.setAttribute("data-dismiss", "modal");
    divModal.setAttribute("role", "dialog");
    divModal.setAttribute("aria-hidden", "true");

    $(divModal).on("hidden.bs.modal", function () {
        setTimeout(passerALaProchainePhoto, 500);
    });

    $(buttonAjouter).click(function (e) {
        e.preventDefault();
        let currentModalOpen = $("#" + e.target.value);
        console.log(idDevis);
        let data = {
            action: "ajouterPhotoApresAmenagement",
            idDevis: idDevis,
            image: currentModalOpen.find("img").attr('src'),
            typeAmenagement: currentModalOpen.find(".optionSelect").find("option:selected").val(),
            photoPreferee: currentModalOpen.find(".isPhotoPref").is(":checked"),
            photoVisible: currentModalOpen.find(".isPhotoVisible").is(":checked"),
        };
        let headers = chooseHeaderForRequest(localStorage.getItem("token"));
        $.ajax({
            contentType: "json",
            type: "POST",
            url: "/photo",
            headers: headers,
            data: JSON.stringify(data),
            dataType: "json",
            beforeSend: function () {
                $('#body').hide();
                $('#loader').show();
            },
            complete: function () {
                $('#loader').hide();
                $('#body').show();
            },
            success: function (response) {
                notify("success", "Photo ajouté");
                console.log(response);
                console.log(response.photo);
                ajouterPhoto(response.photo);
                currentModalOpen.modal('hide');
            },
            error: onError
        });
    });
    return divModal;
}



function onError(response) {
    console.log(response);
    notify("error", response.responseJSON.error);
}

function passerALaProchainePhoto() {
    if (!afficherModalPhotoSuivant()) {
        displayPhotoApresAmenagement();
    }
}
export { converFileToImg, ajouterPhotoApresAmenagement,viderLesPhotoApresAmenagement,ajouterPhoto,setAmenagements };

