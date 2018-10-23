/**
 * @description '客户关系管理列表（最新的5条）
 * @author wangmk1
 * @since 2014-08-18
 */
(function(){
	var grid = new Wlj.widgets.views.index.grid.TileGrid({
		dataSize : 5,
		title : '客户关系管理列表（最新的5条）',
		url : basepath+'/customerRelationship.json',
		columns : [
		           {columnName : 'GRAPH_NAME',show : true},
					{columnName : 'GRAPH_DESCRIBE',show : true}
		]
	});
	return [grid];
})();