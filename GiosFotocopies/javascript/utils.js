$(document).ready(function() {
    $(".checkbox:not(.checked)").click(function() {
        if ($(this).hasClass("checked")) {
            $(this).removeClass("checked");
            $(this).attr("src", "images/checkbox-unchecked.gif");
        } else {
            $(this).addClass("checked");
            $(this).attr("src", "images/checkbox.gif");
        }
    }).attr("src", "images/checkbox-unchecked.gif");

    $(".checkbox.checked").click(function() {
        if ($(this).hasClass("checked")) {
            $(this).removeClass("checked");
            $(this).attr("src", "images/checkbox-unchecked.gif");
        } else {
            $(this).addClass("checked");
            $(this).attr("src", "images/checkbox.gif");
        }
    }).attr("src", "images/checkbox.gif");

    $(".date").datepicker();
    if ($("#___inicio___").length === 0  && $("#___login___").length === 0) {
        $(".header .botonera").show();
    }
    $("body").fadeIn();
});
function log() {
    for (var x = 0; x < arguments.length; x++)
        console.log(arguments[x]);
    console.log("\n<<<" + new Date() + ">>>");
}

function checkMail() {
    log("consulta correo");
    $.ajax({
        url: "check_mail.php",
        type: "post",
        dataType: "html",
        timeout: 15000,
        success: function(data) {
            try {
                var json = eval("(" + data + ")");
                if (json.cantidad > 0)
                    correoNoLeido(json.cantidad);
                else
                    correoLeido();
                log("consulta correo exitosa", json);
            }
            catch (ex) {
                log(ex,data);
            }
        },
        error: function(algo) {
            log("error en la wea", algo)
        }
    });
}
___checkMailIniciado = false;
function initCheckMail(){
    console.log("Servicio de correo iniciado");
    if(!___checkMailIniciado){
        setInterval(function() {
            checkMail();
        }, 5 * 60 * 1000);
        checkMail();
    }
    ___checkMailIniciado = true
}

function correoNoLeido(cant) {
    $(".header img[src*=mail]:first").slideUp(200);
    $(".header img[src*=mail]:last").slideDown(200);
    $(".pila").attr("title", "Hay " + cant + " correos no leidos");
}

function correoLeido() {
    $(".header img[src*=mail]:last").slideUp(200);
    $(".header img[src*=mail]:first").slideDown(200);
    $(".pila").attr("title", "No hay correos por leer");
}

function login(){
    var user = $(".login input[type=text].user").val();
    var pass = $(".login input[type=password]").val();
    $(".login .error").html("");
    $.ajax({
       url:"../login.php",
       type: "post",
       dataType: "html",
       data: {
           user: user,
           pass: pass
       },
      complete: function(jqXHR,status){
          if(status !== "success")
              alert("Ha ocurrido un error en el servidor, intentelo mas tarde :C");
          else{
              log(jqXHR.responseText);
              json = eval("("+jqXHR.responseText+")");
              if(json.estado === "success"){
                  location.href="?inicio";
              }
              else{
                  $(".login .error").html(json.estado);
              }
          }
      }
    });
}
