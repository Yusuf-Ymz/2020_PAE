import { getData, chooseHeaderForRequest } from "./utilsAPI.js";
import { onGetClientList } from "./rechercherClients.js";
import { onGetUserList } from "./rechercherUtilisateur.js";
import { onGet, onGetLier } from "./confirmedInscription.js";
import { displayClient } from "./introduireDevis.js";
import { onGetTousLesDevisList, onGetMesDevisList } from "./rechercherDevis.js";

let action, url, currentRequestValue, idOfInputCurruntlyClicked;
const getVille = "getVille";
const getNom = "getNom";
const getPrenom = "getPrenom";
const getCP = "getCP";

const getType = "getTypes"

const getPrenomNonConfirme = "getPrenomNonConfirme";
const getVilleNonConfirme = "getVilleNonConfirme";
const getNomNonConfirme = "getNomNonConfirme";

const urlClient = "/client";
const urlUser = "/user";
const urlDevis = "/devis";

function doResponse(response) {

    console.log(response);
    $("#result").html("Resultat(s) pour (" + currentRequestValue + ")");

    // à ne pas faire car cela pourrait tout faire planter
    //$(':input').val('');

    if ($("#introduireDevis").css('display') != 'none') {
        displayClient(response);
        return;
    }

    switch (url) {
        case urlClient:
            if ($("#table_clients_noUsers").css('display') != 'none') {
                onGetLier(response);
            } else {
                onGetClientList(response);
            }

            break;
        case urlUser:

            if ($("#listeUser").css('display') != 'none') {
                onGetUserList(response);
            } else {
                onGet(response);
            }
            break;
        case urlDevis:
            if ($('#listeDeTousLesDevis').css('display') != "none") {
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

                if ($('#table_clients_noUsers').css('display') != 'none') {
                    action = "listeClientsPasUtilisateur";
                } else {
                    action = "listerClients";
                }

                break;
            case urlUser:
                if ($("#listeUser").css('display') != 'none') {
                    action = "listeUser"
                } else {
                    action = "confirmerInscription"
                }

                break;
            case urlDevis:

                if ($('#listeDeTousLesDevis').css('display') != "none") {
                    action = "tousLesDevis"

                } else {
                    action = "devisDuClient"
                }
                break;
            default:
                break;
        }


        let token = localStorage.getItem("token");
        data = {
            action: action
        }
        getData(url, data, token, doResponse, onError);
    })


    $("#rechercher").click(function (e) {
        e.preventDefault();

        chooseUrl();

        $("#btn_remove_filters").show();

        let data, values;

        switch (url) {
            case urlClient:
                if ($('#table_clients_noUsers').css('display') != 'none') {
                    action = "listClientsNonLieAffine";
                } else {
                    action = "listClientsAffine";
                }

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

                if ($("#listeUser").css('display') != 'none') {
                    action = "listeUtilisateursAffine";
                } else {
                    action = "listeUtilisateursNonConfirmeAffine";
                }

                data = {
                    action: action,
                    nom: $("#nom_utilisateur").val(),
                    prenom: $("#prenom_utilisateur").val(),
                    ville: $("#ville_utilisateur").val(),
                }
                console.log($("#nom_utilisateur").val());
                console.log($("#prenom_utilisateur").val());
                console.log($("#ville_utilisateur").val());

                break;
            case urlDevis:

                if ($('#listeDeTousLesDevis').css('display') != "none") {

                    action = "listerTousLesDevisAffine";
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

                }

                break;

            default:
                break;

        }

        //currentRequestValue = printResult(...values);
        // $("#result").show();
        let token = localStorage.getItem("token");

        getData(url, data, token, doResponse, onError);



    });


    //filtre client
    $("#ville_client").click(function (e) {
        e.preventDefault();
        if ($('#table_clients_noUsers').css('display') != 'none') {
            action = "getVillesClientNonLie";
        } else {
            action = getVille;
        }

        idOfInputCurruntlyClicked = "#ville_client";

        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });

        auto.autocomplete('search');

    });

    $("#code_postal_client").click(function (e) {
        e.preventDefault();
        if ($('#table_clients_noUsers').css('display') != 'none') {
            action = "getCPClientNonLie";
        } else {
            action = getCP;
        }

        idOfInputCurruntlyClicked = "#code_postal_client";
        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');

    });

    $("#nom_client").click(function (e) {
        e.preventDefault();

        if ($('#table_clients_noUsers').css('display') != 'none') {
            action = "getNomsClientNonLie";
        } else {
            action = getNom;
        }

        idOfInputCurruntlyClicked = "#nom_client";

        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');
    });

    $("#prenom_client").click(function (e) {
        e.preventDefault();

        if ($('#table_clients_noUsers').css('display') != 'none') {
            console.log("je passe")
            action = "getPrenomsClientNonLie";
        } else {
            action = getPrenom;
        }

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
        if ($("#listeUser").css('display') != 'none') {
            action = getVille;
        } else {
            action = getVilleNonConfirme;
        }
        console.log("action " + action);
        idOfInputCurruntlyClicked = "#ville_utilisateur";

        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');

    });
    $("#nom_utilisateur").click(function (e) {
        e.preventDefault();
        if ($("#listeUser").css('display') != 'none') {
            action = getNom;
        } else {
            action = getNomNonConfirme;
        }

        idOfInputCurruntlyClicked = "#nom_utilisateur";

        let auto = $(idOfInputCurruntlyClicked).autocomplete({
            minLength: 0,
            source: doAutocompleteRequest
        });
        auto.autocomplete('search');

    });
    $("#prenom_utilisateur").click(function (e) {
        e.preventDefault();
        if ($("#listeUser").css('display') != 'none') {
            action = getPrenom;
        } else {
            action = getPrenomNonConfirme;
        }

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

    } else if ($('#filtre_user').css('display') != 'none') {
        url = urlUser;

    } else if ($("#filtre_amenagement").css('display') != "none") {
        url = urlDevis;

    }
}