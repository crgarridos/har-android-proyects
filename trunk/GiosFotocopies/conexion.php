<?php
//$mysql_host = "mysql8.000webhost.com";
$mysql_host = "201.215.226.248:3306";
$mysql_database = "a8435298_db";
$mysql_user = "a8435298_db";
$mysql_password = "22oe9o";
error_reporting(E_ALL);
$query = $_GET["sql"];
$method = isset($_GET["method"])?$_GET["method"]:"SELECT";
mysql_connect($mysql_host, $mysql_user, $mysql_password) or die("no conect&oacute; ,". mysql_error());
mysql_select_db($mysql_database) or die("no se selecciono la base de datos ,". mysql_error());
$result = mysql_query($query) or die ("Fallo la consulta ,". mysql_error());
//echo "{";
if($method=="SELECT"){
    $arr = array();
    $i=0;
    while($row = mysql_fetch_array($result)){
        $arr["hola"] = $row;
        $arr[]= $row;
        $arr[] = $row;
    }
    echo $json = json_encode($arr);
//    echo str_replace("]","",str_replace("[", "", $json));
}
//echo "}";
?>