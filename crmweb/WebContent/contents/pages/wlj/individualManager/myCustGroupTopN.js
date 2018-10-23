/**
 * @description 我的客户群列表TOP5(最新的5条)
 * @author luyy
 * @since 2014-08-04
 */
(function(){
	var grid = new Wlj.widgets.views.index.grid.TileGrid({
		dataSize : 5,
		title : '我的客户群列表（最新的5条）',
		url : basepath+'/customergroupinfo.json',
		columns : [
			{columnName : 'CUST_BASE_NUMBER',show : true},
			{columnName : 'CUST_BASE_NAME',show : true}
		]
	});
	return [grid];
})();