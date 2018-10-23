/**
 * 待办工作——>已办任务
 * 
 * @update 20141013,增加查询控制-过滤撤办处理
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
		title : "已办任务列表",
		stUrl : basepath + '/queryWorkApproved.json',
		primary:'INSTANCEID',
//		checkbox : true,
		dbclick:false,
		winHeight : 450,
		winWidth : 800,
		gclms : [
			{name : 'INSTANCEID'},
			{name : 'WFJOBNAME',header : '工作名称'},
			{name : 'WFNAME',header : '流程名称'},
			{name : 'WFSTARTTIME',header : '流程开始时间'},
			{name : 'AUTHOR',header : '发起人ID',hidden:true},
			{name : 'AUTHOR_NAME',header : '发起人'},
			{name : 'PRENODENAME',header : '上一办理人'},
			{name : 'NODENAME',header : '当前环节'},
			{name : 'SPSTATUS',header :'审批状态'
				,renderer : function(value){
					if(value==0){//实际为审批中
						return '正常办理';
					}else if(value ==1){//同意
						return '同意';
					}else if(value ==2){//不同意
						return '否决';
					}else if(value ==3){//部分同意
						return '部分同意';
					}else if(value ==-1){//不明确
						return '撤销';
					}else{
						return '未知';
					}
				}
			},
			{name : 'WFSIGN'},
			{name : 'NODEID'}
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
					{name : 'WFNAME',xtype : 'textfield',fieldLabel : '流程名称',labelStyle : 'text-align:right;',anchor : '90%'}
				]
			} ,{
				columnWidth : .25,
				layout : 'form',
				defaultType : 'textfield',
				border : false,
				items : [ 
					{name : 'WFJOBNAME',xtype : 'textfield',fieldLabel : '工作名称',labelStyle : 'text-align:right;',anchor : '90%'}
				]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [
					new Com.yucheng.crm.common.OrgUserManage({ 
						xtype:'userchoose',
						fieldLabel : '发起人', 
						labelStyle: 'text-align:right;',
						name : 'AUTHOR_NAME',
						hiddenName:'AUTHOR',
						//searchRoleType:('127,47'),  //指定查询角色属性
						searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
						singleSelect:false,
						anchor : '90%'
					}) 
				]
			}]
		},
		buts :[{
			text:'撤销办理',
			iconCls:'dailyDetailIconCss',
			handler:function(){
				if (!listPanel.grid.selModel.hasSelection()) {
					Ext.Msg.alert("系统提示信息", "请选择一条记录！");
					return false;
				}
				var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen > 1) {
					Ext.Msg.alert("系统提示信息", "请选择一条记录！");
					return false;
				}
				var record = listPanel.grid.getSelectionModel().getSelected();
				var wfsigntype = record.get('WFSIGN');
				var wfjobname = record.get('WFJOBNAME');
				var instanceid = record.get('INSTANCEID');
				var nodeid = record.get('NODEID');
				var author = record.get('AUTHOR');
				if(__userId != author){
					Ext.Msg.alert("系统提示信息", "您不是流程发起人不允许撤销办理！");
					return false;
				}
				Ext.MessageBox.confirm('提示','确定撤销办理吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
						return;
					}
					Ext.Ajax.request({
						url : basepath + '/EchainCommon!wfCancel.json',
						method : 'GET',
						params:{
							instanceID:instanceid,
							nodeID:nodeid
					    },
						success : function(response) {
							Ext.Msg.alert("系统提示信息", Ext.util.JSON.decode(response.responseText).tip);
							listPanel.loadCurrData();
					    }
					});
				});
			}
		},{
			text:'审批历史',
			iconCls:'shenpiIconCss',
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
				var wfsigntype = record.get('WFSIGN');
				var wfjobname = record.get('WFJOBNAME');
				curNodeObj.nodeName = record.get('NODENAME');
				curNodeObj.instanceid = record.get('INSTANCEID');
				curNodeObj.nodeid = record.get('NODEID');
				curNodeObj.windowid='viewWindow';
				curNodeObj.fOpinionFlag = false;
				curNodeObj.approvalHistoryFlag = true;
				setTimeout(function(){
					Ext.ScriptLoader.loadScript({
						scripts: [basepath+'/contents/pages/worklistinfo/worklistinfo_'+wfsigntype+'.js'],   
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
				viewWindow.title = '正在查看的流程：'+wfjobname;
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