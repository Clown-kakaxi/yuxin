/**
 * @description 客户经理客户数
 * @author helin
 * @since 2014-07-22
 */
(function(){
	var contentPanel = new Com.yucheng.crm.common.FusionChartPanel({
		title : '客户经理客户数',
		maximize: true,
		swfFile : basepath + '/FusionCharts/Pie2D.swf',
		dataUrl : basepath + '/mgrCustNum.json'
	});
	return [contentPanel];
})();