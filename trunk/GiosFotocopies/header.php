<?php include_once 'utils/class_Utils.php'; ?>
<div class="header">
    <p>
        <span class="fecha"><?php echo Utils::obtenerFechaEnTexto();?></span>
        <?php if(isset($_SESSION["login"])){?>
            <a class="pila" href="http://gmail.com" target="_blank">
                <img class="icon48" src="images/mail.png" title="No hay correo recibido"/>
                <img class="icon48" src="images/mail-recibido.png" title="" style="display: none"/>
            </a>
            <br/>
            <a href="logout.php" class="logout">Cerrar sesion</a>
        <?php }?>
    </p>
    <div class="botonera" style="margin-top: -40px;display: none">
        <a class="boton mini" href="?inicio">
            <img src="images/go-home.png" alt="Inicio" />
            <span>Inicio</span>
        </a>
        <a class="boton mini" href="?impresora">
            <img src="images/printer.png" alt="Impresoras" />
            <span>Impresora</span>
        </a>
        <a class="boton mini" href="?pedidos">
            <img src="images/pedidos.png" alt="Pedidos" />
            <span>Pedidos</span>
        </a>
        <a class="boton mini" href="?reportes">
            <img src="images/chart.png" alt="Pedidos" />
            <span>Reportes</span>
        </a>
        <a class="boton mini" href="?libros">
            <img src="images/book.png" alt="Libros" />
            <span>Libros</span>
        </a>
        <a class="boton mini" href="?promos">
            <img src="images/promos2.png" alt="Promociones" />
            <span>Promociones</span>
        </a>
        <a class="boton mini" href="?config">
            <img src="images/settings.png" alt="Preferencias" />
            <span>Preferencias</span>
        </a>
    </div>
</div>