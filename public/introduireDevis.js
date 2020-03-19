"use strict";
import { getData } from "./utilsAPI.js";

let i = 0;
function displayAmenagements(response){
    let amenagements = response.amenagements;
    let divAmenagements = $("#amenagements")[0];

    for(let i = 0;i<amenagements.length;i++){
        let div = document.createElement("div");
        div.className = "form-check col-md-6 mt-3";

        let input = document.createElement("input");
        input.type="checkbox";
        input.className ="form-check-input amenagements ml-2";
        let id = "amenagement"+amenagements[i]["id"]
        input.id = id;

        let label = document.createElement("label");
        label.className="form-check-label";
        label.htmlFor = id;
        label.innerHTML = amenagements[i]["libelle"];
    
        div.appendChild(label);
        div.appendChild(input);
        divAmenagements.appendChild(div);
    }


}

function onError(response){
    console.error(response);
}

$(document).ready(function (e) {
    $("#drop-container").on('dragenter', function (e) {
        e.preventDefault();
        console.log("dragenter");
        $(this).css('border', '#39b311 2px dashed');
        $(this).css('background', '#f1ffef');
    });

    /* cassé
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
        i+=1;
        reader.onloadend = function () {
            let img = document.createElement("img");
            img.id= "image"+i;
            img.className="image rounded";
            $("#drop-container").append(img);

            $("#image"+i).attr("src", reader.result);
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

    getData("/amenagement",null,localStorage.getItem("token"),displayAmenagements,onError);
    console.log("bala")
});