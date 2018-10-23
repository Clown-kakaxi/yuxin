/**
 * @description 客户经理活动（最新的5条）
 * @author luyy
 * @since 2014-08-04
 */
(function(){
	var grid = new Wlj.widgets.views.index.grid.TileGrid({
		dataSize : 5,
		title : '客户经理活动（最新的5条）',
		url : basepath+'/MktMyActiListAction.json',
		columns : [
			{columnName : 'MY_ACTI_NAME',show : true}
		]
	});
	return [grid];
})();