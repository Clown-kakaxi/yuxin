/**
 * @description 机构存款产品占比（时点）
 * @author helin
 * @since 2014-07-24
 */
(function(){
	var contentPanel = new Com.yucheng.crm.common.FusionChartPanel({
		title : '机构存款产品占比（时点）',
		maximize: true,
		swfFile : basepath + '/FusionCharts/Pie3D.swf',
		dataUrl : basepath + '/orgDepRate.json'
	});
	return [contentPanel];
})();