var args = require('system').args;
var page = require('webpage').create();
var url = 'http://scholar.google.com/citations?mauthors='+args[1]+'&hl=en&view_op=search_authors';

page.viewportSize = { width: 1280, height: 800 };

page.open(url, function(status) {
    setTimeout(getKeno, 2500);
});

function getKeno()
{
    var num=page.evaluate(function() {
        var k = document.querySelector(".gsc_1usr_emlb").textContent;
        return k;
    });
    console.log(num);
    phantom.exit();
}
