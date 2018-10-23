/**
 * @description 机构可发贵宾卡客户
 * @author helin
 * @since 2014-07-24
 */
(function(){
	var contentPanel = new Com.yucheng.crm.common.FusionChartPanel({
		title : '机构可发贵宾卡客户',
		maximize: true,
		swfFile : basepath + '/FusionCharts/Pie2D.swf',
		dataUrl : basepath + '/orgCanCard.json'
	});
	return [contentPanel];
})();