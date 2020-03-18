"use strict";
$(document).ready(function(e){
    $("#drop-container").on('dragenter', function(e) {
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

    $("#drop-container").on('drop', function(e) {
        e.preventDefault();
        console.log("drop");
        $(this).css('border', '#07c6f1 2px dashed');
        $(this).css('background', '#FFF');
        var image = e.originalEvent.dataTransfer.files;
    });

    $("#selectImage").click(function(e){
        e.preventDefault();
        $("#inputFile").trigger('click');
    });
});