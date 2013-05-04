<!--
To change this template, choose Tools | Templates
and open the template in the editor.
-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='http://fonts.googleapis.com/css?family=Carrois+Gothic+SC' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="style.css" />
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
        <script src="http://code.jquery.com/jquery-1.9.1.js" type="text/javascript"></script>
        <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js" type="text/javascript"></script>
        <script type="text/javascript" src="javascript/impresora.js"></script>
        <script type="text/javascript" src="javascript/utils.js"></script>
        <title>Impresora</title>
    </head>
    <body>
        <div style="height: 70px;background-color: rgb(94, 153, 206);box-shadow: 0 5px 15px #ddd"></div>
        <div class="botonera" style="margin-top: -40px;">
            <a class="boton mini">
                <img src="images/printer.png" alt="Impresoras" />
                <span>Impresora</span>
            </a>
            <a class="boton mini">
                <img src="images/pedidos.png" alt="Pedidos" />
                <span>Pedidos</span>
            </a>
            <a class="boton mini">
                <img src="images/book.png" alt="Libros" />
                <span>Libros</span>
            </a>
            <a class="boton mini">
                <img src="images/promos.png" alt="Promociones" />
                <span>Promociones</span>
            </a>
        </div>
        <?php echo "akjshgsjkdgbsadb"; ?>
<!--        <div class="contenido">
        </div>-->
        <div class="contenido">
            <h1>Pedidos</h1>
            <table class="like-excel agrandar">
                <tr>
                    <th><span>Fecha</span></th>
                    <th><span>Nombre cliente</span></th>
                    <th><span>Sacado</span></th>
                    <th><span>Libro</span></th>
                    <th><span>Pagado</span></th>
                    <th><span>Abonado</span></th>
                    <th><span>Por pagar</span></th>
                    <th><span>Retirado</span></th>
                </tr>
                <tr>
                    <td><input class="date" type="text" /></td>
                    <td><input type="text" /></td>
                    <td><img class="checkbox" src=""/></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><img class="checkbox checked" src=""/></td>
                </tr>
                <tr>
                    <td><input class="date" type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                </tr>
                <tr>
                    <td><input class="date" type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                </tr>
                <tr>
                    <td><input class="date" type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                    <td><input type="text" /></td>
                </tr>
            </table>
            <div class="botonera">
                <a href="javascript:void(0);" class="boton-form">
                    <img src="images/save.png" alt="Guarda cambios" />
                    <label>Guardar cambios</label>
                </a>
                <a href="javascript:addImpresora();" class="boton-form">
                    <img src="images/printer.png" alt="Guarda cambios" />
                    <label>Agregar Impresora</label>
                </a>
            </div>
        </div>
    </body>
</html>
