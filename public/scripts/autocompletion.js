import { getData, chooseHeaderForRequest } from "./utilsAPI.js";
import { onGetClientList } from "./rechercherClients.js";
import { onGetUserList } from "./rechercherUtilisateur.js";
import { displayClient } from "./introduireDevis.js";
import { onGetTousLesDevisList, onGetMesDevisList } from "./rechercherDevis.js";

let action, url, currentRequestValue, idOfInputCurruntlyClicked;
const getVille = "getVille";
const getNom = "getNom";
const getPrenom = "getPrenom";
const getCP = "getCP";
const getType = "getTypes"
const urlClient = "/client";
const urlUser = "/user";
const urlDevis = "/devis";

function doResponse(response) {

    console.log(response);

    $("#result").html("Resultat(s) pour (" + currentRequestValue + ")");

    $(':input').val('');

    if ($("#introduireDevis").css('display') != 'none') {
        displayClient(response);
        return;
    }

    switch (url) {
        case urlClient:
            onGetClientList(response);
            break;
        case urlUser:
            onGetUserList(response);
            break;
        case urlDevis:
            if ($('#rechercher_tous_les_devis').css('display') != "none") {
                onGetTousLesDevisList(response);
            } else {
                onGetMesDevisList(response);
            }
            break;
        default:
            break;
    }

}

function onError(err) {
    console.log(err);
}

$(document).ready(function () {


    $("#btn_remove_filters").click(function (e) {

        e.preventDefault();

        $("#btn_remove_filters").hide();
        $("#result").hide();
        chooseUrl();

        let data;
        console.log("-> " + url);
        switch (url) {
            case urlClient:
                data = {
                    action: "listerClients"
                }
                break;
            case urlUser:
                data = {
                    action: "listeUser"
                }
                break;
            case urlDevis:

                if ($('#rechercher_tous_les_devis').css('display') != "none") {
                    data = {
                        action: "tousLesDevis"
                    }

                } else {
                    data = {
                        action: "devisDuClient"
                    }
                }

                break;
            default:
                break;
        }
        let token = localStorage.getItem("token");
        getData(url, data, token, doResponse, onError);
    })


    $("#rechercher").click(function (e) {
        e.preventDefault();

        chooseUrl();

        $("#btn_remove_filters").show();

        let data, values;
        console.log("url = " + url);
        switch (url) {
            case urlClient:
                action = "listClientsAffine";
                data = {
                    action: action,
                    nom: $("#nom_client").val(),
                    prenom: $("#prenom_client").val(),
                    ville: $("#ville_client").val(),
                    cp: $("#code_postal_client").val()
                }

                values = [$("#nom_client").val(), $("#prenom_client").val(), $("#ville_client").val(), $("#code_postal_client").val()];
                break;
            case urlUser:
                
                    data = {
                        action: "listeUtilisateursAffine",
                        nom: $("#nom_utilisateur").val(),
                        prenom: $("#prenom_utilisateur").val(),
                        ville: $("#ville_utilisateur").val(),
                    }

                    console.log($("#nom_utilisateur").val());
                    console.log($("#prenom_utilisateur").val());
                    console.log($("#ville_utilisateur").val());
                
                break;
            case urlDevis:


                if ($('#rechercher_tous_les_devis').css('display') != "none") {
                    action =  "listerTousLesDevisAffine";
                    data = {                   
                        action: action,
                        type: $("#types_amenagements").val(),
                        date: $("#date_du_devis").val(),
                        montantMin: $("#montant_min").val(),
                        montantMax: $("#montant_max").val(),
                        client: $("#nom_client_amenagement").val()
                    }

                } else {
                    action = "listerMesDevisAffine";
                    data = {
                        action: action,
                        type: $("#types_amenagements").val(),
                        date: $("#date_du_devis").val(),
                        montantMin: $("#montant_min").val(),
                        montantMax: $("#montant_max").val(),
                    }
                    console.log($("#date_du_devis").val());
                }
                break;

            default:
                break;

        }

        //currentRequestValue = printResult(...values);
        $("#result").show();
        let token = localStorage.getItem("token");

        getData(url, data, token, doResponse, onError);



    });


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

    let header = chooseHeaderForRequest(localStorage.getItem('token'));
    chooseUrl();
    $.ajax({

        type: "get",
        url: url,
        headers: header,
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


function chooseUrl() {
    if ($('#filtre_client').css('display') != 'none') {
        url = urlClient;
        console.log("client");
    } else if ($('#filtre_user').css('display') != 'none') {
        url = urlUser;
        console.log("user");
    } else if ($("#filtre_amenagement").css('display') != "none") {
        url = urlDevis;
        console.log("devis");
    }
}