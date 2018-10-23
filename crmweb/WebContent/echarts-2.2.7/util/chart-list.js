var charts = [];
$(document).ready(function() {
	var a = $("#explore-container .chart-list-panel");
	for (var t in CHART_TYPES) a.append('<h3 class="chart-type-head" id="chart-type-' + t + '">' + CHART_TYPES[t] + "</h3>").append('<div class="row" id="chart-row-' + t + '"></div>');
	for (var e = 0, l = EXAMPLES.length; l > e; ++e) {
		var i = EXAMPLES[e].title || "未命名图表",
			r = EXAMPLES[e].subtitle || "点击查看详情",
			c = $('<div class="col-lg-3 col-md-4 col-sm-6"></div>'),
			n = $('<div class="chart"></div>');
		$("#chart-row-" + EXAMPLES[e].type).append(c.append(n)), $link = $('<a class="chart-link" href="CustomerAnalysisChart.jsp#' + EXAMPLES[e].id + '"></a>'), n.append($link), $link.append($('<div class="chart-hover"></div>').append('<h4 class="chart-title">' + i + "</h4>").append('<div class="chart-subtitle">' + r + "</div>")).append('<div class="chart-hover-bg"></div>'), $chartArea = $('<img class="chart-area" data-original="' + GALLERY_THUMB_PATH + EXAMPLES[e].id + '.png" />'), $link.append($chartArea)
	}
	$(".chart-type-head").waypoint(function(a) {
		var t = this.element.id.split("-");
		3 === t.length && ($("#left-chart-nav li").removeClass("active"), $("#left-chart-nav-" + t[2]).parent("li").addClass("active"))
	}, {
		offset: 70
	});
	window.addEventListener("hashchange", function() {
		scrollBy(0, -80);
		var a = location.hash.split("-");
		3 === a.length && ($("#left-chart-nav li").removeClass("active"), $("#left-chart-nav-" + a[2]).parent("li").addClass("active"))
	}), $("#left-chart-nav li").first().addClass("active"), $(".chart-area").lazyload()
});