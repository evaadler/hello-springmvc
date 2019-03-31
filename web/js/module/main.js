require.config({
    paths: {
        jquery: 'jquery-3.3.1.min'
    }
});

require(['jquery', 'window'], function ($, w) {
    $("#a").click(function () {
        new w.Window().alert("welcome!");
    })
})
