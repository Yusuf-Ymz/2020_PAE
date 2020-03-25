import { getData } from "./utilsAPI.js";
import { homeWorker } from "./index.js";

let token = localStorage.getItem("token");

function consulterDevisEntantQueOuvrier(url,data){
    console.log(url);
    data["action"] = "consulterDevis";
    getData(url,data,token,onGetConsulterDevisOuvrier,onGetConsulterError);
    homeWorker();
    $("#searchCard").show();
    $('#searchContent').hide();
    $('#consulterDevis').show();
    $('#versionOuvrier').show();
    $('#versionClient').hide();
};

function consulterDevisEntantQueClient(url,data){
    data["action"] = "consulterDevis";
    getData(url,data,token,onGetConsulterDevisClient,onGetConsulterError);
    homeWorker();
    $("#searchCard").show();
    $('#searchContent').hide();
    $('#consulterDevis').show();
    $('#versionClient').show();
    $('#versionOuvrier').hide();
};

function onGetConsulterDevisClient(response){
    console.log(response);
    /*$('#nomVersionClient').html(response.devis.Nom du client);
    $('#prenomVersionClient').html(response.devis.Prénom du client);
    $('#typesVersionClient').html(response.devis.Types d'aménagements);
    $('#etatVersionClient').html(response.devis.État d'avancement);*/
}

function onGetConsulterDevisOuvrier(response){
    console.log(response);
   /* $('#nomVersionClient').html(response.devis.Nom du client);
    $('#prenomVersionClient').html(response.devis.Prénom du client);
    $('#typesVersionClient').html(response.devis.Types d'aménagements);
    $('#etatVersionClient').html(response.devis.État d'avancement);*/
}

function onGetConsulterError(err){
    console.error(err);
}

export {consulterDevisEntantQueClient,consulterDevisEntantQueOuvrier};