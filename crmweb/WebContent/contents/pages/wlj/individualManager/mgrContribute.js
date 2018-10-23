/**
 * @description 客户经理贡献度时点占比
 * @author helin
 * @since 2014-07-22
 */
(function(){
	var contentPanel = new Com.yucheng.crm.common.FusionChartPanel({
		title : '客户经理贡献度时点占比',
		maximize: true,
		swfFile : basepath + '/FusionCharts/Pie3D.swf',
		dataUrl : basepath + '/mgrContribute.json'
	});
	return [contentPanel];
})();