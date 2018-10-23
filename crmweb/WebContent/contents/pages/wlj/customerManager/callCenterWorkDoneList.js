/**
 * 待办工作——>办结任务
 * 
 * @update 20141013
 */
//当前节点对象
var curNodeObj={
	instanceid:'',
	nodeid :'',
	windowid:'',
	nodeName:'',
	fOpinionFlag:'',
	approvalHistoryFlag:''
};

Ext.onReady(function() {
	Ext.QuickTips.init(); 
				
	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "办结任务列表",
		stUrl : basepath + '/callCenter.json?flag=done',
		primary:'INSTANCEID',
//		checkbox : true,
		dbclick:false,
		winHeight : 450,
		winWidth : 800,
		gclms : [
			{name : 'CUST_ID'},
			{name : 'UPDATE_FLAG'},
			{name : 'CUST_NAME',header : '客户名称'},
			{name : 'UPDATE_USER'},
			{name : 'USER_NAME',header : '提交人'},
			{name : 'UPDATE_DATE',header : '提交日期'},
			{name : 'APPR_FLAG',header :'审批状态'
				,renderer : function(value){
					if(value==0){//实际为审批中
						return '正常办理';
					}else if(value ==1){//同意
						return '同意';
					}else if(value ==2){//不同意
						return '否决';
					}
				}
			},
			{name : 'APPR_USER_NAME',header : '审批人'},
			{name : 'APPR_DATE',header : '审批日期'},
			{name : 'COMMENTCONTENT',header:'审批意见'}
		],
		pagesize : 20,
		// 查询字段
		selectItems : {
			layout : 'column',
			items : [{
				columnWidth : .25,
				layout : 'form',
				defaultType : 'textfield',
				border : false,
				items : [ 
					{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',anchor : '90%'}
				]
			},{
				columnWidth : .25,
				layout : 'form',
				defaultType : 'textfield',
				border : false,
				items : [
					{name : 'USER_NAME',xtype : 'textfield',fieldLabel : '提交人',labelStyle : 'text-align:right;',anchor : '90%'}
				]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [
				       {name : 'UPDATE_DATE',xtype : 'datefield',fieldLabel : '提交日期',format:'Y-m-d',labelStyle : 'text-align:right;',anchor : '90%'}
				]
			}]
		}
	});
	// 布局模型
	var viewport = new Ext.Viewport( {
		layout : 'fit',
		items : [ listPanel ]
	});
});