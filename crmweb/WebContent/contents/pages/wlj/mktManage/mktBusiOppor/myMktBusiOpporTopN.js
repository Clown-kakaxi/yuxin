/**
 * @description 客户经理的商机TOP5(最新的5条)
 * @author geyu
 * @since 2014-08-18
 */
(function(){
	var grid = new Wlj.widgets.views.index.grid.MaxTileGrid({
		needheader :true,
		dataSize : 5,
		title : '我的商机（最新的5条）',
		url : basepath+'/mktMyBusiOpporQueryAction.json',
		columns : [
			{header : '商机编号',columnName : 'OPPOR_ID',show : true},
			{header : '商机名称',columnName : 'OPPOR_NAME',show : true}
		]
					});
	return [grid];
})();