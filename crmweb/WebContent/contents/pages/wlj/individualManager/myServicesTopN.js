/**
 * @description 客户经理服务计划（最新的5条）
 * @author luyy
 * @since 2014-08-04
 */
(function(){
	var grid = new Wlj.widgets.views.index.grid.TileGrid({
		dataSize : 5,
		title : '客户经理服务计划（最新的5条）',
		url : basepath+'/custServiceManage.json?type=sy',
		columns : [
			{columnName : 'CUST_NAME',show : true},
			{columnName : 'SERVICE_KIND_ORA',show : true}
		]
	});
	return [grid];
})();