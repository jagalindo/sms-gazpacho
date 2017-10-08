var args = require('system').args;
var page = require('webpage').create();
var url = 'http://scholar.google.com/citations?mauthors='+args[1]+'&hl=en&view_op=search_authors';

page.open(url, function(status) {
  var title = page.evaluate(function() {
    return document.getElementsByClassName("gsc_oai_eml")[0].textContent;
	 
  });
  console.log(title);
  //print(title);
  phantom.exit();
});
