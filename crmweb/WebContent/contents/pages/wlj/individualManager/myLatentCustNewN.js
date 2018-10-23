/**
 * @description 我的潜在客户
 * @author luyy
 * @since 2014-08-04
 */
(function(){
	var grid = new Wlj.widgets.views.index.grid.MaxTileGrid({
		needheader :true,
		dataSize : 10,
		title : '我的潜在客户(最新10条)',
		url : basepath+'/latentCustInfoQueryAction.json?type=sy',
		columns : [
			{header : '客户名称',columnName : 'CUST_NAME',show : true,width : 148},
			{header : '证件类型',columnName : 'IDENT_TYPE_ORA',show : true},
			{header : '证件号码',columnName : 'IDENT_NO',show : true}
		]
	});
	return [grid];
})();