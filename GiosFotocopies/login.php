<?php
if(isset($_POST["user"]) && isset($_POST["pass"])){
    session_start();
    $valido = $_POST["user"] == "admin" && $_POST["pass"] == "chichi";
    if($valido)$_SESSION["login"] = true;
    echo "{'estado':'".($valido?"success":"Usuario o clave incorrecta.")."'}";
    exit();
}
?>
<style type="text/css">
    .login{
        width: 70%;
        margin: 3% auto;
    }
    
    .login input[type=password]{  
        background-position: 5px 6px !important;
        background: url("images/password.png") no-repeat;
        background-size: 1em 1em;
        background-color: white;
        padding-left: 1.5em;
        width: 90%;
        border: 1px solid #aaa;
    }
    .login .user{
        background-position: 5px 6px !important;
        background: url("images/user.png") no-repeat;
        background-size: 1em 1em;
        background-color: white;
        padding-left: 1.5em;
        width: 90%;
        border: 1px solid #aaa; 
    }
    
    .login .error{
        color:red;
    }
</style>

<div id="___login___"></div>
<div class="login" style="">
    <h1>Inicio de sesión</h1>
    <table style="margin: 0 auto;border-spacing: 20px">
        <tr>
            <td class="agrandar"><span>Usuario: </span></td>
            <td class="agrandar"><input class="user" type="text" placeholder="nombre de usuario"/></td>
        </tr>
        <tr>
            <td class="agrandar"><span>Contraseña:</span></td>
            <td class="agrandar"><input type="password" placeholder="••••••••••"/></td>
        </tr>
    </table>
    <p>
        <a href="javascript:login()" class="boton-form">
            <img src="images/login.png"/>
            <label>Iniciar Sesion</label>
        </a>
    </p>
    <p class="error"></p>
</div>