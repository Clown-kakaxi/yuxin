/**
 * 营销管理->商机管理->我的商机->关闭商机
 * 入口JS文件
 * wzy，2013-02-19
 */

//定义 商机分配窗口 From表单
var busiOpportCloseForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 250,
	frame : true,
	autoScroll : true,
	labelAlign : 'right',
	buttonAlign : "center",
	items : [{
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '达成金额',
				name : 'REACH_AMOUNT',
				anchor : '90%'
			},new Com.yucheng.crm.common.ProductManage({
				xtype:'productChoose',
				fieldLabel : '营销任务指标', 
				id:'productSelect_close_quota',
				labelStyle: 'text-align:right;',
				name : 'MKT_TARGET_NAME',
				hiddenName:'MKT_TARGET_ID',
				singleSelect:false,
				anchor : '90%',
				onTrigger2Click : function(){
					busOpportCloseQuotaView();
				}
			})]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [new Com.yucheng.crm.common.ProductManage({
				xtype:'productChoose',
				fieldLabel : '营销活动名称', 
				id:'productSelect_close_activ',
				labelStyle: 'text-align:right;',
				name : 'MKT_ACTIV_NAME',
				hiddenName:'MKT_ACTIV_ID',
				singleSelect:false,
				anchor : '90%',
				onTrigger2Click : function(){
					busOpportCloseActivView();
				}
			}),new Com.yucheng.crm.common.ProductManage({
				xtype:'productChoose',
				fieldLabel : '签约/销售产品', 
				id:'productSelect_close_pro',
				labelStyle: 'text-align:right;',
				name : 'PROD_NAME',
				hiddenName:'PROD_ID',
				singleSelect:false,
				anchor : '90%',
				onTrigger2Click : function(){
					busOpportCloseProView();
				}
			})]
		}]
	},{
		layout : 'form',
		items : [ {
			xtype : 'textarea',
			fieldLabel : '失败关闭理由',
			name : 'OPR_CONTENT',
			id : 'opr_content_close',
			anchor : '95%'
		}, {
			xtype : 'textarea',
			fieldLabel : '备注',
			name : 'MEMO',
			anchor : '95%'
		}]
	}],
	buttons:[{
  		text:'成功关闭',
  		handler:function(){
	  		if(!busiOpportCloseForm.getForm().isValid()){
	  			Ext.Msg.alert('提示','输入信息有误，请重新输入！');
	  			return false;
	  		}
	  		Ext.Msg.alert('提示', '关闭成功！');
	  		busiOpportCloseWindow.hide();
	  	}
  	},{
  		text:'失败关闭',
  		handler:function(){
	  		if(!busiOpportCloseForm.getForm().isValid()){
	  			Ext.Msg.alert('提示','输入信息有误，请重新输入！');
	  			return false;
	  		}
	  		//判断“失败关闭理由”是否填写
	  		var opr_content = Ext.getCmp('opr_content_close').getValue();
	  		if(opr_content == null || opr_content == ""){
	  			Ext.Msg.alert('提示','请填写“失败关闭理由”！');
	  			return false;
	  		}
	  		Ext.Msg.alert('提示', '关闭成功！');
	  		busiOpportCloseWindow.hide();
	  	}
  	},{
  		text: '取消',
	  	handler:function(){
	  		busiOpportCloseWindow.hide();
	  	}
  	}]
});

// 定义 商机关闭 窗口
var busiOpportCloseWindow = new Ext.Window( {
	title : '关闭商机',
	plain : true,
	layout : 'fit',
	width : 700,
	height : 300,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'right',
	border : false,
	constrain : true,
	items : [ busiOpportCloseForm ]
});

// 打开 商机关闭 窗口
function busiOpportCloseWindowInit() {
    var selectLength = grid.getSelectionModel().getSelections().length;
    if(selectLength == 0){
    	Ext.Msg.alert('提示', '请先选择要关闭的商机！');
        return false;
    }else if(selectLength > 1){
    	Ext.Msg.alert('提示', '只能选择一个商机进行关闭！');
        return false;
    }else{
    	busiOpportCloseForm.getForm().reset();
    	busiOpportCloseWindow.show();
    }
}

/*********************定义“营销活动”选择控件***********开始**********/
//定义自动当前页行号
var planRownum = new Ext.grid.RowNumberer({
	header : 'NO',
	width : 28
});

var sm_p = new Ext.grid.CheckboxSelectionModel();
var planProdColumns = new Ext.grid.ColumnModel([planRownum,sm_p,
	{
		header : '活动名称',
		align : 'left',
		dataIndex : 'productDetailId',
		sortable : true
	},{
		header : '活动类型',
		align : 'left',
		dataIndex : 'productId',
		sortable : true
	},{
		header : '开始日期',
		align : 'left',
		dataIndex : 'createUser',
		sortable : true
	}, {
		header : '结束日期',
		align : 'left',
		dataIndex : 'createDate',
		sortable : true
	}]
);

var planProdRecord = Ext.data.Record.create([
	{name : 'productDetailId',mapping : 'PPDE_ID'},
	{name : 'productId',mapping : 'PRODUCT_ID'},
	{name : 'productName',mapping : 'PRODUCT_NAME'},
	{name : 'createUser',mapping : 'USERNAME'},
	{name : 'createDate',mapping : 'CREATE_DATE'},
	{name : 'planId',mapping : 'PLAN_ID'}
]);

var planProdStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/planProductQuery.json',
		failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader({
		totalProperty:'num',// 记录总数
		root:'rows'// Json中的列表数据根节点
	},
	planProdRecord)
});

var memberData= {
	TOTALCOUNT:3,
	rows:[
	{"rownum":"1","PPDE_ID":"推销信用卡","PRODUCT_ID":"信用卡产品营销","USERNAME":"2012-03-23","CREATE_DATE":"2012-09-23"},
	{"rownum":"2","PPDE_ID":"农村个体贷款","PRODUCT_ID":"贷款产品营销","USERNAME":"2012-04-03","CREATE_DATE":"2012-10-03"},
	{"rownum":"3","PPDE_ID":"订单融资业务","PRODUCT_ID":"理财产品类营销","USERNAME":"2012-06-13","CREATE_DATE":"2012-12-13"},			
	{"rownum":"4","PPDE_ID":"小企业e助贷","PRODUCT_ID":"贷款产品营销","USERNAME":"2012-07-20","CREATE_DATE":"2013-01-20"},		
	{"rownum":"5","PPDE_ID":"保税仓","PRODUCT_ID":"理财产品类营销","USERNAME":"2012-09-11","CREATE_DATE":"2013-03-11"}	
	]
};
planProdStore.loadData(memberData);

var mktAssuListPanelActiv = new Ext.grid.GridPanel({
	layout:'fit',
	autoScroll : true,
	region : 'center', // 返回给页面的div
	height : 245,
	store : planProdStore,
	frame : true,
	sm : sm_p,
	cm : planProdColumns,
	stripeRows : true,
	buttonAlign : "center",
	buttons:[{
		text: '选择',
	  	handler:function(){
	  		if(Ext.getCmp('productSelect_close_activ')){
	  			Ext.getCmp('productSelect_close_activ').setValue("推销信用卡 | 信用卡产品营销 ");
	  		}
	  		if(Ext.getCmp('productSelect_close_activ_01')){
	  			Ext.getCmp('productSelect_close_activ_01').setValue("推销信用卡 | 信用卡产品营销 ");
	  		}
	  		if(Ext.getCmp('productSelect_close_activ_02')){
	  			Ext.getCmp('productSelect_close_activ_02').setValue("推销信用卡 | 信用卡产品营销 ");
	  		}
	  		if(Ext.getCmp('productSelect_close_activ_03')){
	  			Ext.getCmp('productSelect_close_activ_03').setValue("推销信用卡 | 信用卡产品营销 ");
	  		}
	  		if(Ext.getCmp('productSelect_close_activ_04')){
	  			Ext.getCmp('productSelect_close_activ_04').setValue("推销信用卡 | 信用卡产品营销 ");
	  		}
	  		busOpportCloseActivWindow.hide();
	  	}
	}]
});

//定义详情查看窗口
var busOpportCloseActivWindow = new Ext.Window( {
	title : '营销活动',
	plain : true,
	layout : 'fit',
	width : 500,
	height : 300,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'right',
	border : false,
	constrain : true,
	items : [ mktAssuListPanelActiv ]
});

function busOpportCloseActivView() {
	busOpportCloseActivWindow.show();
}
/*********************定义“营销活动”选择控件***********开始**********/

/*********************定义“营销任务指标”选择控件***********开始**********/
//定义自动当前页行号
var planRownum = new Ext.grid.RowNumberer({
	header : 'NO',
	width : 28
});

var sm_p = new Ext.grid.CheckboxSelectionModel();
var planProdColumns = new Ext.grid.ColumnModel([planRownum,sm_p,
	{
		header : '指标项名称',
		align : 'left',
		dataIndex : 'productDetailId',
		sortable : true
	},{
		header : '考核年度',
		align : 'left',
		dataIndex : 'productId',
		sortable : true
	},{
		header : '指标对象名称',
		align : 'left',
		dataIndex : 'createUser',
		sortable : true
	}, {
		header : '指标值',
		align : 'left',
		dataIndex : 'createDate',
		sortable : true
	}]
);

var planProdRecord = Ext.data.Record.create([
	{name : 'productDetailId',mapping : 'PPDE_ID'},
	{name : 'productId',mapping : 'PRODUCT_ID'},
	{name : 'productName',mapping : 'PRODUCT_NAME'},
	{name : 'createUser',mapping : 'USERNAME'},
	{name : 'createDate',mapping : 'CREATE_DATE'},
	{name : 'planId',mapping : 'PLAN_ID'}
]);

var planProdStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/planProductQuery.json',
		failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader({
		totalProperty:'num',// 记录总数
		root:'rows'// Json中的列表数据根节点
	},
	planProdRecord)
});

var memberData= {
	TOTALCOUNT:3,
	rows:[
	{"rownum":"1","PPDE_ID":"销售金额","PRODUCT_ID":"2011","USERNAME":"总体数量","CREATE_DATE":"15,000"},
	{"rownum":"2","PPDE_ID":"达成客户数","PRODUCT_ID":"2012","USERNAME":"总体数量","CREATE_DATE":"240"},
	{"rownum":"3","PPDE_ID":"增长率","PRODUCT_ID":"2011","USERNAME":"百分比","CREATE_DATE":"15%"},			
	{"rownum":"4","PPDE_ID":"盈利率","PRODUCT_ID":"2013","USERNAME":"百分比","CREATE_DATE":"10%"},		
	{"rownum":"5","PPDE_ID":"达成率","PRODUCT_ID":"2010","USERNAME":"百分比","CREATE_DATE":"30%"}	
	]
};
planProdStore.loadData(memberData);

var mktAssuListPanelQuota = new Ext.grid.GridPanel({
	layout:'fit',
	autoScroll : true,
	region : 'center', // 返回给页面的div
	height : 245,
	store : planProdStore,
	frame : true,
	sm : sm_p,
	cm : planProdColumns,
	stripeRows : true,
	buttonAlign : "center",
	buttons:[{
		text: '选择',
	  	handler:function(){
	  		if(Ext.getCmp('productSelect_close_quota')){
	  			Ext.getCmp('productSelect_close_quota').setValue("销售金额 | 2011 | 总体数量 | 15,000");
	  		}
	  		if(Ext.getCmp('productSelect_close_quota_01')){
	  			Ext.getCmp('productSelect_close_quota_01').setValue("销售金额 | 2011 | 总体数量 | 15,000");
	  		}
	  		if(Ext.getCmp('productSelect_close_quota_02')){
	  			Ext.getCmp('productSelect_close_quota_02').setValue("销售金额 | 2011 | 总体数量 | 15,000");
	  		}
	  		if(Ext.getCmp('productSelect_close_quota_03')){
	  			Ext.getCmp('productSelect_close_quota_03').setValue("销售金额 | 2011 | 总体数量 | 15,000");
	  		}
	  		if(Ext.getCmp('productSelect_close_quota_04')){
	  			Ext.getCmp('productSelect_close_quota_04').setValue("销售金额 | 2011 | 总体数量 | 15,000");
	  		}
	  		busOpportCloseQuotaWindow.hide();
	  	}
	}]
});

//定义详情查看窗口
var busOpportCloseQuotaWindow = new Ext.Window( {
	title : '营销指标',
	plain : true,
	layout : 'fit',
	width : 500,
	height : 300,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'right',
	border : false,
	constrain : true,
	items : [ mktAssuListPanelQuota ]
});

function busOpportCloseQuotaView() {
	busOpportCloseQuotaWindow.show();
}
/*********************定义“营销任务指标”选择控件***********开始**********/

/*********************定义“签约/销售产品”选择控件***********开始**********/
//定义自动当前页行号
var planRownum = new Ext.grid.RowNumberer({
	header : 'NO',
	width : 28
});

var sm_p = new Ext.grid.CheckboxSelectionModel();
var planProdColumns = new Ext.grid.ColumnModel([planRownum,sm_p,
	{
		header : '交易日期',
		align : 'left',
		dataIndex : 'productDetailId',
		sortable : true
	},{
		header : '交易时间',
		align : 'left',
		dataIndex : 'productId',
		sortable : true
	},{
		header : '交易金额',
		align : 'left',
		dataIndex : 'createUser',
		sortable : true
	}, {
		header : '币种',
		align : 'left',
		dataIndex : 'createDate',
		sortable : true
	}]
);

var planProdRecord = Ext.data.Record.create([
	{name : 'productDetailId',mapping : 'PPDE_ID'},
	{name : 'productId',mapping : 'PRODUCT_ID'},
	{name : 'productName',mapping : 'PRODUCT_NAME'},
	{name : 'createUser',mapping : 'USERNAME'},
	{name : 'createDate',mapping : 'CREATE_DATE'},
	{name : 'planId',mapping : 'PLAN_ID'}
]);

var planProdStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/planProductQuery.json',
		failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader({
		totalProperty:'num',// 记录总数
		root:'rows'// Json中的列表数据根节点
	},
	planProdRecord)
});

var memberData= {
	TOTALCOUNT:3,
	rows:[
	{"rownum":"1","PPDE_ID":"2012-03-23","PRODUCT_ID":"20120308"," PRODUCT_NAME":"002342","USERNAME":"1,000","CREATE_DATE":"人民币","PLAN_ID":"2342342"},
	{"rownum":"2","PPDE_ID":"2012-04-21","PRODUCT_ID":"20120407"," PRODUCT_NAME":"002342","USERNAME":"25,000","CREATE_DATE":"人民币","PLAN_ID":"2342342"},
	{"rownum":"3","PPDE_ID":"2012-11-03","PRODUCT_ID":"20121108"," PRODUCT_NAME":"002342","USERNAME":"1,329","CREATE_DATE":"人民币","PLAN_ID":"2342342"},			
	{"rownum":"4","PPDE_ID":"2012-12-10","PRODUCT_ID":"20121208"," PRODUCT_NAME":"002342","USERNAME":"2,432","CREATE_DATE":"人民币","PLAN_ID":"2342342"},		
	{"rownum":"5","PPDE_ID":"2013-01-20","PRODUCT_ID":"20130104"," PRODUCT_NAME":"003554","USERNAME":"99,876","CREATE_DATE":"人民币","PLAN_ID":"2342342"}	
	]
};
planProdStore.loadData(memberData);

var mktAssuListPanel = new Ext.grid.GridPanel({
	layout:'fit',
	autoScroll : true,
	region : 'center', // 返回给页面的div
  height : 245,
  store : planProdStore,
  frame : true,
	sm : sm_p,
  cm : planProdColumns,
  stripeRows : true,
	buttonAlign : "center",
  buttons:[{
		text: '选择',
	  	handler:function(){
	  		Ext.getCmp('productSelect_close_pro').setValue("2012-11-03 | 1329 | 人民币");
	  		busOpportCloseWindow.hide();
	  	}
	}]
});

//定义详情查看窗口
var busOpportCloseWindow = new Ext.Window( {
	title : '交易流水',
	plain : true,
	layout : 'fit',
	width : 500,
	height : 300,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'right',
	border : false,
	constrain : true,
	items : [ mktAssuListPanel ]
});

function busOpportCloseProView() {
	busOpportCloseWindow.show();
}
/*********************定义“签约/销售产品”选择控件***********开始**********/