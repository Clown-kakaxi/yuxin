/**
 * @description '集团客户管理列表（最新的5条）
 * @author wangmk1
 * @since 2014-08-19
 */
(function(){
	var grid = new Wlj.widgets.views.index.grid.TileGrid({
		dataSize : 5,
		title : '集团客户管理列表（最新的5条）',
		url : basepath+'/groupCustomer.json',
		columns : [
		           {columnName : 'GROUP_NO',show : true},
					{columnName : 'GROUP_NAME',show : true}
		]
	});
	return [grid];
})();