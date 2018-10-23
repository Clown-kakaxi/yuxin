/**
 * 待办工作——>待办任务
 * 
 * @update 20141013,增加查询控制-撤办处理
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
	
	//首页磁贴数据刷新
	var workTodo = function(infoId){
		var len = parent.parent.Wlj.TileMgr.tiles.items.length;
		for(i=0;i<len;i++){
			var tileName = parent.parent.Wlj.TileMgr.tiles.items[i].tileName;
			if(tileName == '待办工作'){
				var gridQ = parent.parent.Wlj.TileMgr.tiles.items[i].get('listPanelApplywait');
				if(gridQ != null && gridQ != undefined){
					var div = gridQ.el.dom.document.getElementById("demoWorkTodo");
					if(div != null && div != undefined){
						var ul = gridQ.el.dom.document.getElementById("ulWorkTodo");
						var ulNew = ul;
						var liId = "workTodo"+infoId;
						var children = ul.children;
						var childLen = children.length;
						var newChild;
						Ext.Ajax.request({
							url : basepath + '/queryrestapplywaitNew.json',
							method : 'GET',
							success : function(response) {
								var ret = Ext.decode(response.responseText);
								var data = ret.json.data;
								var len = data.length;

								//判断所选数据是否已被处理，不在待办工作中
								var flg = 0;
								var id;
								for(var n=0;n<len;n++){
									id = data[n].INSTANCEID;
									if(id == infoId){
										flg = 1;
										break;
									}
								}
								if(flg == 0){
									for(var j=0;j<childLen;j++){
										var liN = children.item(j);
										var childLiId = liN.id;
										if(childLiId == '' || childLiId == 'marqueeWorkTodo'){
											if(len == 0){
												marquee.stop();
											}else if(len < 8){
												var marquee = gridQ.el.dom.document.getElementById("marqueeWorkTodo");
												ulNew.removeChild(marquee);
												var childMarquee = liN.children;
												var lenMarquee = childMarquee.length;
												var m = 0;
												for(var k=0;k<lenMarquee;k++){
													var liMarquee = childMarquee.item(m);
													var liIdMarquee = liMarquee.id;
													if(liId == liIdMarquee){
														m++;
													}else{
														newChild = liMarquee;
														ulNew.appendChild(newChild);
													}
												}
//												div.appendChild(ulNew);
											}else{
												var marquee = gridQ.el.dom.document.getElementById("marqueeWorkTodo");
												var childMarquee = liN.children;
												var lenMarquee = childMarquee.length;
												for(var k=0;k<lenMarquee;k++){
													var liMarquee = childMarquee.item(k);
													var liIdMarquee = liMarquee.id;
													if(liId == liIdMarquee){
														liN.removeChild(liMarquee);
														break;
													}
												}
											}
										}else{
											if(liId == childLiId){
												ul.removeChild(liN);
												break;
											}
										}
									}
								}
							}
						});
					}
				}
//				break;
			}
		}
	};				
	// 最终展现的panel
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "待办任务列表",
		stUrl : basepath + '/queryrestapplywait.json',
		primary:'INSTANCEID',
//		checkbox : true,
		dbclick:false,
		winHeight : 450,
		winWidth : 800,
		gclms : [
			{name : 'INSTANCEID'},
			{name : 'WFJOBNAME',header : '工作名称'},
			{name : 'WFNAME',header : '流程名称'},
			{name : 'CUST_NAME',header : '客户名称'},
			{name : 'AUTHOR',header : '发起人id',hidden:true},
			{name : 'AUTHOR_NAME',header : '发起人'},
			{name : 'ORG_NAME',header : '发起人所属分支行'},
			{name : 'T_MGR_ID',header : '接收人id',hidden:true},
			{name : 'T_MGR_NAME',header : '接收人'},
			{name : 'T_ORG_NAME',header : '接收人所属分支行'},
			{name : 'PRENODENAME',header : '上一办理人'},
			{name : 'NODENAME',header : '当前环节'},
			{name : 'NODESTATUS',header :'节点状态'
				,renderer : function(value){
					if(value==0){
						return '正常办理';
					}else if(value==1){
						return '催办';
					}else if(value==2){
						return '办理结束';
					}else if(value==3){
						return '待签收';
					}else if(value==4){
						return '重办';
					}else if(value==5){
						return '退回';
					}else if(value==6){
						return '挂起';
					}else if(value==7){
						return '打回';
					}else{
						return '未知状态';
					}
				}
			},
			{name : 'NODEPLANENDTIME',header : '办理时限'},
			{name : 'NODEID'},
			{name : 'WFSIGN'}
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
			},{
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
				var wfsigntype = record.get('WFSIGN');
				var wfjobname = record.get('WFJOBNAME');
				curNodeObj.nodeName = record.get('NODENAME');
				curNodeObj.instanceid = record.get('INSTANCEID');
				var instanceid = record.get('INSTANCEID');
				curNodeObj.nodeid = record.get('NODEID');
				curNodeObj.windowid='viewWindow';
				curNodeObj.fOpinionFlag = true;
				curNodeObj.approvalHistoryFlag = false;
				setTimeout(function(){
					Ext.ScriptLoader.loadScript({
						scripts: [basepath+'/contents/pages/worklistinfo/worklistinfo_'+wfsigntype+'.js'],   
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
							workTodo(instanceid);
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
				viewWindow.title = '您查看的业务信息：'+wfjobname;
				viewWindow.show();
			}
		},{
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
				var wfjobname  = record.get('WFJOBNAME');
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
							workTodo(instanceid);
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