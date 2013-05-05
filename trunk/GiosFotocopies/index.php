<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='http://fonts.googleapis.com/css?family=Carrois+Gothic+SC' rel='stylesheet' type='text/css'>
        <title></title>
        <link rel="stylesheet" href="style.css" />
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.js" type="text/javascript"></script>
        <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js" type="text/javascript"></script>
        
        <script type="text/javascript" src="javascript/utils.js"></script>
        <script type="text/javascript" src="javascript/impresora.js"></script>
        <style type="text/css">
            #menu{
                border-spacing:10px;
                margin: auto;
            }
            #footer{
                margin-bottom: 0;
                height: 50px;
                color: white;
                background: black;
            }
        </style>
    </head>
    <body style="display: none">
        <?php
        include_once './header.php';
        if(isset($_GET["impresora"]))
            include_once './impresora.php';
        else if(isset($_GET["pedidos"]))
            include_once './pedidos.php';
        else if(isset($_GET["reportes"]))
            include_once './reportes.php';
        else if(isset($_GET["libros"]))
            include_once './libros.php';
        else if(isset($_GET["promos"]))
            include_once './promociones.php';
        else if(isset($_GET["config"]))
            include_once './config.php';
        else include_once 'inicio.php';
        include_once 'footer.php';
        ?>
    </body>
</html>