$(document).ready(function(){
    $(".checkbox:not(.checked)").click(function (){
        if($(this).hasClass("checked")){
            $(this).removeClass("checked");
            $(this).attr("src","images/checkbox-unchecked.gif");
        }else{
            $(this).addClass("checked");
            $(this).attr("src","images/checkbox.gif");
        }
    }).attr("src","images/checkbox-unchecked.gif");
    
    $(".checkbox.checked").click(function (){
        if($(this).hasClass("checked")){
            $(this).removeClass("checked");
            $(this).attr("src","images/checkbox-unchecked.gif");
        }else{
            $(this).addClass("checked");
            $(this).attr("src","images/checkbox.gif");
        }
    }).attr("src","images/checkbox.gif");
    
    $(".date").datepicker();
});


