"use strict";
//import { getData, postData, deleteData, updateData } from "./utilsAPI.js";
let token = undefined;


$(document).ready(function () {

 token =  initialisation();

  //nav-bar 
  $('.leftmenu').on('click', function (e) {
    $('.slide-nav').toggleClass("active");
    e.preventDefault();
  });
  
  
  $(".home").on('click', function (e) {
    token = localStorage.getItem("token");
    if(token)
      HomeUser();
    else
      HideToHome();
  });



  $("#logout").click(e => {
    e.preventDefault();
    localStorage.removeItem("token");
    token = undefined;
    HideToHome();
  });
});


const  HideToHome = () =>{
  $("#nav_connect").show();
  $(".register").hide();
  $("#carouselExampleIndicators").show();
  $("#logo").show();
  $("#logout").hide();
}

const HomeUser = () =>{
  $("#logout").show();
  $("#nav_connect").hide();
  $("#login_message").html("");
  $("#nav_connect").hide();
  $(".register").hide();
  $("#carouselExampleIndicators").hide();
  $("#logo").hide();
}


const initialisation = () => {
  let token = localStorage.getItem("token");
  console.log(token);
  if (token) {
    HomeUser();
    return token;
 
  } else {
    HideToHome();
    return;
  }
};

export {HomeUser};


