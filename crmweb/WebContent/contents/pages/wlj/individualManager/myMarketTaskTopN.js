/**
 * @description 客户经理任务(最新的5条)
 * @author helin
 * @since 2014-08-04
 */
(function(){
	var grid = new Wlj.widgets.views.index.grid.TileGrid({
		dataSize : 5,
		title : '我的营销任务（最新的5条）',
		url : basepath+'/myMarketTask.json',
		columns : [
			{columnName : 'TASK_ID',show : true},
			{columnName : 'TASK_NAME',show : true}
		]
	});
	return [grid];
})();