import { getData } from "./utilsAPI.js";
import { homeWorker } from "./index.js";

function consulterDevisEntantQueOuvrier(url,data){
    data["action"] = "consulterDevisEntantQueOuvrier";
    //getData(url,data,token,onGetConsulterDevisOuvrier,onGetConsulterError);
    homeWorker();
    $("#searchCard").show();
    $('#searchContent').hide();
    $('#consulterDevis').show();
    $('#versionOuvrier').show();
    $('#versionClient').hide();
};

function consulterDevisEntantQueClient(url,data){
    data["action"] = "consulterDevisEntantQueOuvrier";
    //getData(url,data,token,onGetConsulterDevisClient,onGetConsulterError);
    homeWorker();
    $("#searchCard").show();
    $('#searchContent').hide();
    $('#consulterDevis').show();
    $('#versionClient').show();
    $('#versionOuvrier').hide();
};

function onGetConsulterDevisClient(response){

}

function onGetConsulterDevisOuvrier(response){

}

function onGetConsulterError(response){

}

export {consulterDevisEntantQueClient,consulterDevisEntantQueOuvrier};