javascript:(function() {
    var s = document.createElement('script'); 
    s.setAttribute('src', 'http://code.jquery.com/jquery-1.9.1.min.js'); 
    document.getElementsByTagName('body')[0].appendChild(s);
    alert("jQuery inyectado!! ;)");
})();