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

    setInterval(function() {
        checkMail();
    }, 30000);
    checkMail();
    if ($("#___inicio___").length === 0) {
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
                log(ex);
            }
        },
        error: function(algo) {
            log("error en la wea", algo)
        }
    });
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