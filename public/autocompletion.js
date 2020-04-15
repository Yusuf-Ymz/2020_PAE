import { getData } from "./utilsAPI.js";
import { onGetClientList } from "./rechercherClients.js";
import { onGetUserList } from "./rechercherUtilisateur.js";
import { displayClient } from "./introduireDevis.js";

let action, url, headers, currentRequestValue, currentDisplayFunction, idOfInputCurruntlyClicked;
const getVille = "getVille";
const getNom = "getNom";
const getPrenom = "getPrenom";
const getCP = "getCP";

function doResponse(response) {

    $("#result").html("Resultat(s) pour (" + currentRequestValue + ")");

    $(':input').val('');

    if ($("#introduireDevis").css('display') != 'none') {
        displayClient(response);
        return;
    }

    switch (url) {
        case "/client":

            onGetClientList(response);
            break;
        case "/user":
            onGetUserList(response);
        default:
            break;
    }

}

function onError(err) {
    console.log(err);
}

$(document).ready(function () {

    if ($('filtre_client').css('display') != 'none') {
        url = "/client";
    } else if ($('filtre_user').css('display') != 'none') {
        url = "/user";
    }

    $("#btn_remove_filters").click(function (e) {
        e.preventDefault();

        $("#btn_remove_filters").hide();
        $("#result").hide();

        let token = localStorage.getItem("token");
        let data;

        if (url === "/client") {
            data = {
                action: "listerClients"
            }
        }

        getData(url, data, token, doResponse, onError);
    })


    $("#rechercher").click(function (e) {
        e.preventDefault();

        $("#btn_remove_filters").show();
        $("#result").show();

        let data;
        switch (url) {

            case "/client":
                action = "listClientsAffine";
                data = {
                    action: action,
                    nom: $("#nom_client").val(),
                    prenom: $("#prenom_client").val(),
                    ville: $("#ville_client").val(),
                    cp: $("#code_postal_client").val()
                }
                const values = [$("#nom_client").val(), $("#prenom_client").val(), $("#ville_client").val(), $("#code_postal_client").val()];
                currentRequestValue = printResult(...values);
                break;
            case "/user":
                data = {
                    //a faire
                }
                break;
            case "/devis":
                data = {
                    type: $("#types_amenagements").val(),
                    date: $("#date_du_devis").val(),
                    montantMin: $("#montant_min").val(),
                    montantMax: $("#montant_max").val(),
                    client: $("#nom_client_amenagement").val()
                }
                break;

            default:
                break;
        }

        let token = localStorage.getItem("token");
        getData(url, data, token, doResponse, onError);

    });

    let token = localStorage.getItem("token");

    if (token)
        headers = {
            "Content-Type": "application/json",
            Authorization: token
        };
    else
        headers = {
            "Content-Type": "application/json"
        };

    //filtre client
    $("#ville_client").click(function (e) {
        e.preventDefault();

        action = getVille;
        idOfInputCurruntlyClicked = "#ville_client";
        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');
    });

    $("#code_postal_client").click(function (e) {
        e.preventDefault();
        action = getCP;
        idOfInputCurruntlyClicked = "#code_postal_client";
        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');

    });

    $("#nom_client").click(function (e) {
        e.preventDefault();
        action = getNom;
        idOfInputCurruntlyClicked = "#nom_client";

        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');
    });

    $("#prenom_client").click(function (e) {
        e.preventDefault();

        action = getPrenom;

        idOfInputCurruntlyClicked = "#prenom_client";

        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');

    });


    //nom user
    $("#ville_utilisateur").click(function (e) {
        e.preventDefault();
        action = getVille;
        idOfInputCurruntlyClicked = "#ville_utilisateur";

        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');

    });
    $("#nom_utilisateur").click(function (e) {
        e.preventDefault();
        action = getNom;
        idOfInputCurruntlyClicked = "#nom_utilisateur";

        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');

    });
    $("#prenom_utilisateur").click(function (e) {
        e.preventDefault();

        action = getPrenom;
        idOfInputCurruntlyClicked = "#prenom_utilisateur";
        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');

    });


    $("#nom_client_amenagement").click(function (e) {
        e.preventDefault();
        action = getNom;
        idOfInputCurruntlyClicked = "#nom_client_amenagement";
        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');
    });

    $("#types_amenagements").click(function (e) {
        e.preventDefault();
        action = getType;
        idOfInputCurruntlyClicked = "#types_amenagements";
        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');
    });

});

function doAutocompleteRequest(request, reponse) {

    $.ajax({

        type: "get",
        url: url,
        headers: headers,
        data: {
            keyword: request.term,
            action: action
        },
        dataType: "json",
        success: function (donnees) {
            // var matcher = new RegExp("^" + $.ui.autocomplete.escapeRegex(request.term), "i");
            reponse($.map(donnees, function (item) {
                return item;
            }));
        },
        error: function (err) {
            console.log(err);
            $('#loader').hide();

        }
    });
}

function printResult(...tabInputValue) {
    let result = "";
    tabInputValue.forEach(inputValue => {

        if (inputValue !== "") {
            result += inputValue + " - ";
        }
    });

    return result;
}