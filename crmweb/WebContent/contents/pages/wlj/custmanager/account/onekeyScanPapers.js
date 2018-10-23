

/**
 * [scanPapers 证件扫描]
 * @return {[type]} [description]
 */
function scanPapers(){
	
	var iframeHtml = '<iframe src="/crmweb/contents/pages/wlj/custmanager/account/custInfoScan2.html" id="iframe_scanpapers"></iframe>';
	$("body").append(iframeHtml);
	var w_body = window.screen.width;
	var h_body = $("body").height();
	$("#iframe_scanpapers").css({
		'position' : 'absolute',
		'z-index' : '999',
		'left' : '10px',
		'top' : '10px',
		'width' : w_body-50,
		'height' : h_body-50,
		'background-color' : '#eee'
	});
	$("#iframe_scanpapers").show();
}