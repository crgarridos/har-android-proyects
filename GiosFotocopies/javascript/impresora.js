var $tr = null;
function addImpresora(){
    if($tr==null)
        $tr = $(".like-excel tr:last").clone()[0];
    $(".like-excel tr:last").after($tr.outerHTML);
}

function eliminar(e){
    $(e).closest("tr").remove();
}

$(document).ready(function (){
    $tr = $(".like-excel tr:last").clone()[0];
});

