/**
 * @description 机构贷趋势图
 * @author helin
 * @since 2014-07-21
 */
(function(){
	var contentPanel = new Com.yucheng.crm.common.FusionChartPanel({
		title : '机构贷款趋势图',
		maximize: true,
		swfFile : basepath + '/FusionCharts/MSLine.swf',
		dataUrl : basepath + '/orgCreMonth.json'
	});
	return [contentPanel];
})();