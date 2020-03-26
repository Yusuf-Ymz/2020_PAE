import { getData } from "./utilsAPI.js";
import {printTable} from "./utilsHtml.js";
import {homeWorker} from "./index.js";


$(document).ready(function () {
    $("#rechercher_user").on('click', function (e) {
        homeWorker();
        let token = localStorage.getItem("token");
        console.log(token);
        const data = {
            action: 'listeUser'
          };
        getData("/user",data , token, onGetUserList, onUserListError);
    });

    function onGetUserList(response) {
        $("#listeUser").show();     
        $("#searchCard").show();
        $("#searchContent").show();
        $("#filtre_utilisateur").show();
        $("#filtre_client").hide();
        $("#filtre_amenagement").hide();
        printTable("listeUser", response.listeUser);
        
    }

    

    function onUserListError(err) {
        console.error(err);
        Swal.fire({
            position: 'top-end',
            icon: 'error',
            timerProgressBar: true,
            title: err.responseJSON.error,
            showConfirmButton: false,
            timer: 1500
          })
    }


});