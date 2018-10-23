/**
 * @description 客户TOP10(客户名称、客户积分)
 * @author luyy
 * @since 2014-08-04
 */
(function(){
	var grid = new Wlj.widgets.views.index.grid.MaxTileGrid({
		needheader :true,
		dataSize : 10,
		title : '客户积分TOP10',
		url : basepath+'/custSocreTop.json',
		columns : [
			{header : '客户名称',columnName : 'CUST_NAME',show : true,width : 148},
			{header : '客户累计积分',columnName : 'COUNT_NUM',show : true},
			{header : '可用积分',columnName : 'SCORE_TOTAL',show : true}
		]
	});
	return [grid];
})();