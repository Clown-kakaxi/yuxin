/**
 * 待办工作——>待办任务
 * 
 * @update 
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
		title : "待办任务列表",
		stUrl : basepath + '/callCenter.json',
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
					if(value==0){
						return '待审批';
					}
				}
			}
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
		},
		buts :[{
			text:'流程办理',
			iconCls:'dailyDetailIconCss',
			handler:function(){
				if (!listPanel.grid.selModel.hasSelection()) {
					Ext.Msg.alert("系统提示信息", "请选择一条记录！");
					return false;
				}
				var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen > 1||recordsLen <1) {
					Ext.Msg.alert("系统提示信息", "请选择一条记录！");
					return false;
				}
				var record = listPanel.grid.getSelectionModel().getSelected();
				if(__userId==record.get('UPDATE_USER')){
					Ext.Msg.alert("系统提示信息", "您是该流程提交人,不允许办理该流程！");
					return false;
				}
				curNodeObj.nodeName = record.get('NODENAME');
				curNodeObj.instanceid = 'CI_'+record.get('CUST_ID')+'_'+record.get('UPDATE_FLAG');
				curNodeObj.custId=record.get('CUST_ID');
				curNodeObj.custName=record.get('CUST_NAME');
				curNodeObj.nodeid = record.get('UPDATE_FLAG');
				curNodeObj.windowid='viewWindow';
				curNodeObj.fOpinionFlag = true;
				curNodeObj.approvalHistoryFlag = false;
				setTimeout(function(){
					Ext.ScriptLoader.loadScript({
						scripts: [basepath+'/contents/pages/worklistinfo/worklistinfo_callcenter.js'],   
						//scripts: [basepath+'/contents/pages/worklistinfo/worklistinfo_yjyy_sp.js'],   
						callback: function() {  
						}
					});
				},800);
				var viewWindow = new Ext.Window({
					layout : 'fit',
					id:'viewWindow',
					draggable : true,//是否可以拖动
					closable : true,// 是否可关闭
					modal : true,
					closeAction : 'close',
					maximized:true,
					listeners:{
						'close':function(){
							listPanel.loadCurrData();
						}
					},
					titleCollapse : true,
					buttonAlign : 'center',
					border : false,
					animCollapse : true,
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [{
						html:' <div style="width:100%;height:100%;"><div style="position:absolute; left:0px; top:0px; " id=\'viewEChian\'></div></div>'
					}]
				});
				viewWindow.title = '您查看的业务信息：CallCenter修改客户信息_'+curNodeObj.custName;
				viewWindow.show();
			}
		}]
	});
			
	// 布局模型
	var viewport = new Ext.Viewport( {
		layout : 'fit',
		items : [ listPanel ]
	});
	
});