"use strict";
import { getData,specialDoGet } from "./utilsAPI.js";
var searchVille = document.getElementById('villeSearch'),
    searchCP = document.getElementById('codePostalSearch'),
    searchName = document.getElementById('clientName'),
    resultsVille = document.getElementById('resultsVille'),
    resultsCP = document.getElementById('resultsCP'),
    resultsVille = document.getElementById('resultsName'),
    selectedResult = -1,
    previousRequest, 
    previousValueVille = searchVille.value,
    previousValueCP = searchCP.value,
    previousValueName = searchName.value; 

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
        let id = "amenagement" + amenagements[i]["id"]
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
    console.error(response);
}

$(document).ready(function (e) {
    $("#drop-container").on('dragenter', function (e) {
        e.preventDefault();
        console.log("dragenter");
        $(this).css('border', '#39b311 2px dashed');
        $(this).css('background', '#f1ffef');
    });

    /* cass�
    $("#drop-container").on('dragleave', function(e) {
        e.preventDefault();
        console.log("dragleave")
        $(this).css('border', '#07c6f1 2px dashed');
        $(this).css('background', '#ffffff');
    });*/


    $("#drop-container").on('drop', function (element) {
        element.preventDefault();
        console.log("drop");
        $(this).css('border', '#07c6f1 2px dashed');
        $(this).css('background', '#FFF');

        var file = element.originalEvent.dataTransfer.files[0];
        console.log(file instanceof File);
        var reader = new FileReader();
        i += 1;
        reader.onloadend = function () {
            let img = document.createElement("img");
            img.id = "image" + i;
            img.className = "image rounded";
            $("#drop-container").append(img);

            $("#image" + i).attr("src", reader.result);
        }

        reader.readAsDataURL(file);

    });
    $("#drop-container").on('dragover', function (e) {
        e.preventDefault();
    })

    $("#selectImage").click(function (e) {
        e.preventDefault();
        $("#inputFile").trigger('click');
    });

    getData("/amenagement", null, localStorage.getItem("token"), displayAmenagements, onError);




    function getResults(keywords) { // Effectue une requête et récupère les résultats

        var xhr = new XMLHttpRequest();
        xhr.open('GET', './search.php?s=' + encodeURIComponent(keywords));

        xhr.addEventListener('readystatechange', function () {
            if (xhr.readyState == XMLHttpRequest.DONE && xhr.status == 200) {

                displayResults(xhr.responseText);

            }
        });

        xhr.send(null);

        return xhr;

    }

    function displayResults(response) { // Affiche les résultats d'une requête

        results.style.display = response.length ? 'block' : 'none'; // On cache le conteneur si on n'a pas de résultats

        if (response.length) { // On ne modifie les résultats que si on en a obtenu

            response = response.split('|');
            var responseLen = response.length;

            results.innerHTML = ''; // On vide les résultats

            for (var i = 0, div; i < responseLen; i++) {

                div = results.appendChild(document.createElement('div'));
                div.innerHTML = response[i];

                div.addEventListener('click', function (e) {
                    chooseResult(e.target);
                });

            }

        }

    }

    function chooseResult(result) { // Choisi un des résultats d'une requête et gère tout ce qui y est attaché

        searchElement.value = previousValue = result.innerHTML; // On change le contenu du champ de recherche et on enregistre en tant que précédente valeur
        results.style.display = 'none'; // On cache les résultats
        result.className = ''; // On supprime l'effet de focus
        selectedResult = -1; // On remet la sélection à "zéro"
        searchElement.focus(); // Si le résultat a été choisi par le biais d'un clique alors le focus est perdu, donc on le réattribue

    }



    searchElement.addEventListener('keyup', function (e) {

        var divs = results.getElementsByTagName('div');

        if (e.keyCode == 38 && selectedResult > -1) { // Si la touche pressée est la flèche "haut"

            divs[selectedResult--].className = '';

            if (selectedResult > -1) { // Cette condition évite une modification de childNodes[-1], qui n'existe pas, bien entendu
                divs[selectedResult].className = 'result_focus';
            }

        }

        else if (e.keyCode == 40 && selectedResult < divs.length - 1) { // Si la touche pressée est la flèche "bas"

            results.style.display = 'block'; // On affiche les résultats

            if (selectedResult > -1) { // Cette condition évite une modification de childNodes[-1], qui n'existe pas, bien entendu
                divs[selectedResult].className = '';
            }

            divs[++selectedResult].className = 'result_focus';

        }

        else if (e.keyCode == 13 && selectedResult > -1) { // Si la touche pressée est "Entrée"

            chooseResult(divs[selectedResult]);

        }

        else if (searchVille.value != previousValueVille) { // Si le contenu du champ de recherche ville a changé

            previousValueVille = searchElement.value;

            if (previousRequest && previousRequest.readyState < XMLHttpRequest.DONE) {
                previousRequest.abort(); // Si on a toujours une requête en cours, on l'arrête
            }

            previousRequest = getResults(previousValue); // On stocke la nouvelle requête

            selectedResult = -1; // On remet la sélection à "zéro" à chaque caractère écrit

        }

    });

});