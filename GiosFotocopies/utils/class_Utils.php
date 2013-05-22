<?php
class Utils {
    public static function obtenerFechaEnTexto() {
    $tiempo = getdate(time());
    $dia = $tiempo['wday'];
    $dia_mes = $tiempo['mday'];
    $mes = $tiempo['mon'];
    $year = $tiempo['year'];
    $hora = $tiempo['hours'];
    $minutos = $tiempo['minutes'];
    $segundos = $tiempo['seconds'];

    switch ($dia) {
        case "1": $dia_nombre = "Lunes";
            break;
        case "2": $dia_nombre = "Martes";
            break;
        case "3": $dia_nombre = "Mi&eacute;rcoles";
            break;
        case "4": $dia_nombre = "Jueves";
            break;
        case "5": $dia_nombre = "Viernes";
            break;
        case "6": $dia_nombre = "S&aacute;bado";
            break;
        case "0": $dia_nombre = "Domingo";
            break;
    }
    switch ($mes) {
        case "1": $mes_nombre = "Enero";
            break;
        case "2": $mes_nombre = "Febrero";
            break;
        case "3": $mes_nombre = "Marzo";
            break;
        case "4": $mes_nombre = "Abril";
            break;
        case "5": $mes_nombre = "Mayo";
            break;
        case "6": $mes_nombre = "Junio";
            break;
        case "7": $mes_nombre = "Julio";
            break;
        case "8": $mes_nombre = "Agosto";
            break;
        case "9": $mes_nombre = "Septiembre";
            break;
        case "10": $mes_nombre = "Octubre";
            break;
        case "11": $mes_nombre = "Noviembre";
            break;
        case "12": $mes_nombre = "Diciembre";
            break;
    }
    return $dia_nombre . " " . $dia_mes . " de " . $mes_nombre . " de " . $year;
}
}

?>
