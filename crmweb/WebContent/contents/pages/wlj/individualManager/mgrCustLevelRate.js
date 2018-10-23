/**
 * @description 客户经理所辖客户等级占比
 * @author luyy
 * @since 2014-08-04
 */
(function(){
	var contentPanel = new Com.yucheng.crm.common.FusionChartPanel({
		title : '客户经理所辖客户等级占比',
		maximize: true,
		swfFile : basepath + '/FusionCharts/Pie3D.swf',
		dataUrl : basepath + '/custLevelRate.json?type=mgr'
	});
	return [contentPanel];
})();