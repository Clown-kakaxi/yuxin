/**
  * @description 分期还款计划
  * @author likai
  * @since 2014/08/13
  *
  */
  
imports([
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);
  
var _custId;
var url = basepath + '/custRepayPlan.json?CUST_ID='+_custId;;

var needCondition = false;
var needGrid = true;


// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var fields = [
	{name: 'CUST_ID', hidden: true},
	{name: 'PRINCIPAL', text: '本金(元)', dataType: 'decimal', viewFn: money('0,000.00'), allowNegative: false},
	{name: 'INTEREST', text: '利息(元)', dataType: 'decimal', viewFn: money('0,000.00'), allowNegative: false},
	{name: 'PERIOD', text: '期数(期)', vtype: 'number',allowDecimals: false, allowNegative: false},
	{name: 'AMT_PER', text: '每期还款额(元)', dataType: 'decimal', viewFn: money('0,000.00'), allowNegative: false},
	{name: 'BALANCE', text: '余额(元)', dataType: 'decimal', viewFn: money('0,000.00'), allowNegative: false}
];

var tbar = [
	//导出按钮
	new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('_repayPlanExcel'),
        formPanel : 'searchCondition',
        url :  basepath + '/custRepayPlan.json?CUST_ID='+_custId
    })
];
