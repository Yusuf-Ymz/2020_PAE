"use strict";
import { getData, postData, specialGetData } from "./utilsAPI.js";
//import printTable from "./utilsHtml.js"
import {homeWorker,HomeUser} from "./index.js";
var searchVille = document.getElementById('villeSearch'),
    searchCP = document.getElementById('codePostalSearch'),
    searchName = document.getElementById('clientName'),
    searchPrenom = document.getElementById('clientPrenom'),
    resultsPrenom = document.getElementById('resultsPrenom'),
    resultsVille = document.getElementById('resultsVille'),
    resultsCP = document.getElementById('resultsCP'),
    resultsName = document.getElementById('resultsName'),
    selectedResultVille = -1,
    selectedResultCP = -1,
    selectedResultName = -1,
    selectedResultPrenom = -1,
    previousRequestPrenom,
    previousRequestVille,
    previousRequestCP,
    previousRequestName,
    previousValuePrenom = searchPrenom.value,
    previousValueVille = searchVille.value,
    previousValueCP = searchCP.value,
    previousValueName = searchName.value;
const getCp = "getCp";
const getNames = "getNom";
const getVille = "getVille";
const getPrenom = "getPrenom";
let idClient = -1;
let i = 0;
function displayAmenagements(response) {
    let amenagements = response.amenagements;
    let divAmenagements = $("#amenagements")[0];

    for (let i = 0; i < amenagements.length; i++) {
        let div = document.createElement("div");
        div.className = "form-check col-md-6 mt-3";

        let input = document.createElement("input");
        input.type = "checkbox";
        input.className = "form-check-input amenagements ml-2";
        let id = "amenagement" + amenagements[i]["id"];
        input.id = id;

        let label = document.createElement("label");
        label.className = "form-check-label";
        label.htmlFor = id;
        label.innerHTML = amenagements[i]["libelle"];

        div.appendChild(label);
        div.appendChild(input);
        divAmenagements.appendChild(div);
    }


}

function onError(response) {

}

function afficherNotif(msg){
    Swal.fire({
        position: 'center',
        icon: 'error',
        timerProgressBar: true,
        title: msg,
        showConfirmButton: false,
        timer: 1500
      })
}

$(document).ready(function (e) {

    $("#indroduire_devis").click(function(){
     	homeWorker();
        $("#introduireDevis").show();
        $("#searchContent").hide();
		$("#searchCard").show();
      
     
        $("#card").show();
        getData("/amenagement", null, localStorage.getItem("token"), displayAmenagements, onError);
        getListClient();
    });
    $("#ajouterClient").click(function (e) {
        e.preventDefault();

        if(!$("#nomC")[0].checkValidity()){
            afficherNotif("Erreur champ nom");
        }else if(!$("#prenomC")[0].checkValidity()){
            afficherNotif("Erreur champ prenom");
        }else if(!$("#rueC")[0].checkValidity()){
            afficherNotif("Erreur champ rue");
        }else if(!$("#numC")[0].checkValidity()){
            afficherNotif("Erreur champ numéro");
        }else if(!$("#boiteC")[0].checkValidity()){
            afficherNotif("Erreur champ boite");
        }else if(!$("#cpC")[0].checkValidity()){
            afficherNotif("Erreur champ nom");
        }else if(!$("#villeC")[0].checkValidity()){
            afficherNotif("Erreur champ nom");
        }else if(!$("#emailC")[0].checkValidity()){
            afficherNotif("Erreur champ nom");
        }else if(!$("#telC")[0].checkValidity()){
            afficherNotif("Erreur champ nom");
        }else{
            let data = {
                action: "ajouterClient",
                nom: $("#nomC").val(),
                prenom: $("#prenomC").val(),
                rue: $("#rueC").val(),
                numero: $("#numC").val(),
                boite: $("#boiteC").val(),
                cp: $("#cpC").val(),
                ville: $("#villeC").val(),
                email: $("#emailC").val(),
                telephone: $("#telC").val(),
            };
            $("input").val("");
            postData("/client", data, localStorage.getItem("token"), onPostSuccess, onPostError);
        }
        
    });

    function onPostSuccess(response) {
        getListClient();
        $("#myModal").modal('hide');
        let client = response.client;
        idClient = client.idClient;
        $("#nomInfo").val(client.nom);
        $("#prenomInfo").val(client.prenom);
    }

    function onPostError(response) {
        console.log(response.error);
    }
    window.addEventListener('click', function (e) {
        if (!resultsCP.contains(e.target) || !searchCP.contains(e.target) || !resultsName.contains(e.target)
            || !searchName.contains(e.target) || !resultsVille.contains(e.target) || !searchVille.contains(e.target)
            || !resultsPrenom.contains(e.target) || !searchPrenom.contains(e.target)) {
            resultsName.style.display = 'none';
            resultsCP.style.display = 'none';
            resultsVille.style.display = 'none';
            resultsPrenom.style.display = 'none';
        }
    });
    searchCP.addEventListener('click', function (e) {
        if (resultsCP.childElementCount > 0) {
            setTimeout(() => {
                resultsCP.style.display = 'block';
            }, 0.2);
        }

    });
    searchPrenom.addEventListener('click', function (e) {
        if (resultsPrenom.childElementCount > 0)
            setTimeout(() => {
                resultsPrenom.style.display = 'block';
            }, 0.2);

    });
    searchName.addEventListener('click', function (e) {
        if (resultsName.childElementCount > 0)
            setTimeout(() => {
                resultsName.style.display = 'block';
            }, 0.2);

    });
    searchVille.addEventListener('click', function (e) {
        if (resultsVille.childElementCount > 0) {
            setTimeout(() => {
                resultsVille.style.display = 'block';
            }, 0.2);
        }
    });
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
            i+=1;
            converFile(file,i);
        }

    });
    function converFile(file,i) {
        var reader = new FileReader();
        reader.onloadend = function () {
            let img = document.createElement("img");
            img.id = "image" + i;
            img.className = "image rounded imageAvant";
            $("#drop-container").append(img);
            $("#image" + i).attr("src", reader.result);
        }

        reader.readAsDataURL(file);
    }
    $("#inputFile")[0].onchange = function (e) {
        var file = e.target.files[0];
        converFile(file);
    }
    $("#drop-container").on('dragover', function (e) {
        e.preventDefault();
    })

    $("#selectImage").click(function (e) {
        e.preventDefault();
        $("#inputFile").trigger('click');
    });

    function getResults(keywords, action) { 
        let ajx;
        let data = {
            action: action,
            keyword: keywords,
        };
        let previousRequest;
        if (action === getCp) {
            previousRequest = previousRequestCP;
            ajx = specialGetData("/client", data, localStorage.getItem("token"), previousRequest, displayResultsCp, onError);
        } else if (action === getNames) {
            previousRequest = previousRequestName;
            ajx = specialGetData("/client", data, localStorage.getItem("token"), previousRequest, displayResultsNames, onError);
        } else if (action === getVille) {
            previousRequest = previousRequestVille;
            ajx = specialGetData("/client", data, localStorage.getItem("token"), previousRequest, displayResultsVille, onError);
        } else if (action === getPrenom) {
            previousRequest = previousRequestPrenom;
            ajx = specialGetData("/client", data, localStorage.getItem("token"), previousRequest, displayResultsPrenom, onError);
        }

        return ajx;

    }

    function displayResultsPrenom(response) {
        response = response.prenoms;
        resultsPrenom.style.display = response.length ? 'block' : 'none';

        if (response.length) {

            var responseLen = response.length;
            if (responseLen === 1) {
                if (searchPrenom.value === response[0]) {
                    resultsPrenom.style.display = 'none';
                    return;
                }
            }
            resultsPrenom.innerHTML = ''; 

            for (var i = 0, div; i < responseLen; i++) {

                div = resultsPrenom.appendChild(document.createElement('div'));
                div.innerHTML = response[i];

                div.addEventListener('click', function (e) {
                    chooseResult(e.target, searchPrenom, previousValuePrenom, selectedResultPrenom, getPrenom);
                });

            }

        }
    }

    function displayResultsCp(response) {
        response = response.cpx;
        resultsCP.style.display = response.length ? 'block' : 'none'; 

        if (response.length) { 

            var responseLen = response.length;
            if (responseLen === 1) {
                if (searchCP.value === response[0]) {
                    resultsCP.style.display = 'none';
                    return;
                }
            }
            resultsCP.innerHTML = '';

            for (var i = 0, div; i < responseLen; i++) {

                div = resultsCP.appendChild(document.createElement('div'));
                div.innerHTML = response[i];

                div.addEventListener('click', function (e) {
                    chooseResult(e.target, searchCP, previousValueCP, selectedResultCP, getCp);
                });

            }

        }
    }
    function displayResultsNames(response) {
        response = response.names;
        resultsName.style.display = response.length ? 'block' : 'none';

        if (response.length) { 

            var responseLen = response.length;
            if (responseLen === 1) {
                if (searchName.value === response[0]) {
                    resultsName.style.display = 'none';
                    return;
                }
            }
            resultsName.innerHTML = '';

            for (var i = 0, div; i < responseLen; i++) {

                div = resultsName.appendChild(document.createElement('div'));
                div.innerHTML = response[i];

                div.addEventListener('click', function (e) {
                    chooseResult(e.target, searchName, previousValueName, selectedResultName, getNames);
                });

            }

        }
    }

    function displayResultsVille(response) {
        response = response.villes;
        resultsVille.style.display = response.length ? 'block' : 'none'; 

        if (response.length) { 

            var responseLen = response.length;
            if (responseLen === 1) {
                if (searchVille.value === response[0]) {
                    resultsVille.style.display = 'none';
                    return;
                }
            }
            resultsVille.innerHTML = ''; 

            for (var i = 0, div; i < responseLen; i++) {

                div = resultsVille.appendChild(document.createElement('div'));
                div.innerHTML = response[i];

                div.addEventListener('click', function (e) {
                    chooseResult(e.target, searchVille, previousValueVille, selectedResultVille, getVille);
                });

            }

        }
    }

    function chooseResult(result, searchElement, previousValue, selectedResult, action) { 

        searchElement.value = previousValue = result.innerHTML;
        result.style.display = 'none';
        result.className = '';
        selectedResult = -1;
        searchElement.focus();
        previousRequestCP = getResults(previousValue, action);
        getListClient();
    }

    function getListClient() {
        let data = {
            action: "listClientsAffine",
            cp: searchCP.value,
            ville: searchVille.value,
            nom: searchName.value,
            prenom: searchPrenom.value
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
        tr.innerHTML = "Code Postal";
        thead.appendChild(tr);

        tr = document.createElement("th");
        tr.innerHTML = "Ville";
        thead.appendChild(tr);

        tr = document.createElement("th");
        tr.innerHTML = "e-mail";
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
            champ.innerHTML = element["nom"];
            trData.appendChild(champ);
            champ.className = "nom";

            champ = document.createElement("td");
            champ.innerHTML = element["prenom"];
            trData.appendChild(champ);
            champ.className = "prenom";

            champ = document.createElement("td");
            champ.innerHTML = element["codePostal"];
            trData.appendChild(champ);

            champ = document.createElement("td");
            champ.innerHTML = element["ville"];
            trData.appendChild(champ);

            champ = document.createElement("td");
            champ.innerHTML = element["email"];
            trData.appendChild(champ);

            champ = document.createElement("td");
            champ.innerHTML = element["telephone"];
            trData.appendChild(champ);

            champ = document.createElement("td");
            let button = document.createElement("button");
            button.innerHTML = "Sélectionner";
            button.className = "btn btn-primary"
            button.value = element["idClient"];
            champ.appendChild(button);
            trData.appendChild(champ);
            button.addEventListener('click', function (e) {
                e.preventDefault();
                idClient = e.target.value;
                let tr = e.target.parentNode.parentNode;
                let nom = tr.getElementsByClassName('nom');
                let prenom = tr.getElementsByClassName('prenom');
                $("#nomInfo").val(nom[0].innerHTML);
                $("#prenomInfo").val(prenom[0].innerHTML);
            });
        }
    }


    searchVille.addEventListener('keyup', function (e) {

        var divs = resultsVille.getElementsByTagName('div');

        if (e.keyCode == 38 && selectedResultVille > -1) {

            divs[selectedResultVille--].className = '';

            if (selectedResultVille > -1) {
                divs[selectedResultVille].className = 'result_focus';
            }

        }

        else if (e.keyCode == 40 && selectedResultVille < divs.length - 1) {

            resultsVille.style.display = 'block';

            if (selectedResultVille > -1) {
                divs[selectedResultVille].className = '';
            }

            divs[++selectedResultVille].className = 'result_focus';

        } else if (e.keyCode == 13) {
            if (selectedResultVille > -1) {
                chooseResult(divs[selectedResultVille], searchVille, previousRequestVille, selectedResultVille, getVille);
            } else {
                getListClient();
            }

        } else if (searchVille.value != previousValueVille) {

            previousValueVille = searchVille.value;
            previousRequestVille = getResults(previousValueVille, getVille);
            selectedResultVille = -1;

        }

    });

    searchName.addEventListener('keyup', function (e) {

        var divs = resultsName.getElementsByTagName('div');

        if (e.keyCode == 38 && selectedResultName > -1) { 

            divs[selectedResultName--].className = '';

            if (selectedResultName > -1) {
                divs[selectedResultName].className = 'result_focus';
            }

        }

        else if (e.keyCode == 40 && selectedResultName < divs.length - 1) {

            resultsName.style.display = 'block';

            if (selectedResultName > -1) {
                divs[selectedResultName].className = '';
            }

            divs[++selectedResultName].className = 'result_focus';

        }

        else if (e.keyCode == 13) { 
            console.log('ici');
            if (selectedResultName > -1) {
                chooseResult(divs[selectedResultName], searchName, previousValueName, selectedResultName, getNames);
            } else {
                getListClient();
            }
        } else if (searchName.value != previousValueName) {
            previousValueName = searchName.value;
            previousRequestName = getResults(previousValueName, getNames);
            selectedResultName = -1;
        }

    });
    searchPrenom.addEventListener('keyup', function (e) {

        var divs = resultsPrenom.getElementsByTagName('div');

        if (e.keyCode == 38 && selectedResultPrenom > -1) { 

            divs[selectedResultPrenom--].className = '';

            if (selectedResultPrenom > -1) {
                divs[selectedResultPrenom].className = 'result_focus';
            }

        }

        else if (e.keyCode == 40 && selectedResultPrenom < divs.length - 1) {

            resultsPrenom.style.display = 'block';

            if (selectedResultPrenom > -1) {
                divs[selectedResultPrenom].className = '';
            }

            divs[++selectedResultPrenom].className = 'result_focus';

        }

        else if (e.keyCode == 13) {
            console.log("ici");
            if (selectedResultPrenom > -1) {
                chooseResult(divs[selectedResultPrenom], searchPrenom, previousValuePrenom, selectedResultPrenom, getPrenom);
            } else {
                getListClient();
            }


        }

        else if (searchPrenom.value != previousValuePrenom) {

            previousValuePrenom = searchPrenom.value;
            previousRequestPrenom = getResults(previousValuePrenom, getPrenom);
            selectedResultPrenom = -1;

        }

    });

    searchCP.addEventListener('keyup', function (e) {

        var divs = resultsCP.getElementsByTagName('div');

        if (e.keyCode == 38 && selectedResultCP > -1) {

            divs[selectedResultCP--].className = '';

            if (selectedResultCP > -1) {
                divs[selectedResultCP].className = 'result_focus';
            }

        }

        else if (e.keyCode == 40 && selectedResultCP < divs.length - 1) { 

            resultsCP.style.display = 'block'; 

            if (selectedResultCP > -1) {
                divs[selectedResultCP].className = '';
            }

            divs[++selectedResultCP].className = 'result_focus';

        }

        else if (e.keyCode == 13) { 
            if (selectedResultCP > -1) {
                chooseResult(divs[selectedResultCP], searchCP, previousValueCP, selectedResultCP, getCp);
            } else {
                getListClient();
            }
        }

        else if (searchCP.value != previousValueCP) {

            previousValueCP = searchCP.value;
            previousRequestCP = getResults(previousValueCP, getCp);
            selectedResultCP = -1;

        }

    });

});