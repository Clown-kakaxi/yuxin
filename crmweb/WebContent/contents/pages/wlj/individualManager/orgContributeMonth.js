/**
 * @description 机构贡献度连续12个月趋势
 * @author helin
 * @since 2014-07-22
 */
(function(){
	var contentPanel = new Com.yucheng.crm.common.FusionChartPanel({
		title : '机构贡献度连续12个月趋势',
		maximize: true,
		swfFile : basepath + '/FusionCharts/MSLine.swf',
		dataUrl : basepath + '/orgContributeMonth.json'
	});
	return [contentPanel];
})();