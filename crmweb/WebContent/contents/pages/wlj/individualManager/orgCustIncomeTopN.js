/**
 * @description 机构资金净流入TOP10
 * @author helin
 * @since 2014-07-21
 */
(function(){
	var grid = new Wlj.widgets.views.index.grid.MaxTileGrid({
		needheader :true,
		dataSize : 10,
		title : '机构资金净流入TOP10',
		url : basepath+'/orgCustIncomeTopN.json',
		columns : [
			{header : '客户号',columnName : 'CUST_ID',show : true},
			{header : '客户名称',columnName : 'CUST_NAME',show : true,width : 148},
			{header : '流入金额',columnName : 'BAL',show : true,align : 'right',convert : money('0,0.00')}
		]
	});
	return [grid];
})();