

$(document).ready(function () {
 
  $('.leftmenu').on('click', function (e) {
    $('.slide-nav').toggleClass("active");
    e.preventDefault();
  });

  /* Set the width of the sidebar to 250px and the left margin of the page content to 250px */
  $(".openbtn").on('click', function (e) {
    document.getElementById("mySidebar").style.width = "240px";
    //document.getElementById("main").style.marginLeft = "250px";
    $(".carousel-control-prev").hide();
    //$(".openbtn").hide();
  });

  /* Set the width of the sidebar to 0 and the left margin of the page content to 0 */
  $("#closebtn").on('click', function (e) {
    $(".carousel-control-prev").show();
    document.getElementById("mySidebar").style.width = "0";
    //document.getElementById("main").style.marginLeft = "0";
    //$(".openbtn").show();
  });



  $('#redirection_inscrit').on('click', function (e) {
    e.preventDefault();
    page = RegisterForm();

  });

  $('.redirection_connect').on('click', function (e) {
    e.preventDefault();
    LoginForm();
  });
  

  $("#home").on('click', function (e) {
     HideToHome();
  });


});


const  HideToHome = () =>{
  $("#nav_connect").show();
  $(".register").hide();
  $("#carouselExampleIndicators").show();
  $("#logo").show();
}

const LoginForm = () => {
    $(".register").show();
    $("#nav_connect").hide();
    $("#logo").hide();
    $("#carouselExampleIndicators").hide();
    $('#redirection_connect').hide();
    $('#redirection_inscrit').show();
    $("#inscription_form").addClass("d-none");
    $("#login_form").show();
};

const RegisterForm = () =>{
   $(".register").show();
   $("#logo").hide();
   $("#carouselExampleIndicators").hide();
   $('#redirection_connect').show();
   $('#redirection_inscrit').hide();
   $("#nav_connect").hide();
   $("#inscription_form").removeClass("d-none");
   $("#login_form").hide();
};