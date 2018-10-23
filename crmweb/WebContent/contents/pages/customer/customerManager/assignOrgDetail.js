/**
 * 分配机构和分配客户经理--机构
 * @author songxs
 * @since 2012-12-28
 */
	var selRecord = "";
	var OrgPanel = new Ext.form.FormPanel({
		id:'orgPanel',
		frame:true,
		region:'north',
		height : 80,
		autoScroll : true,
		split:true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.85,
				layout: 'form',
				items: [{
					id:'id',
					xtype:'textfield',
					fieldLabel: 'ID',
					name: 'id',
					hidden : true,
					anchor:'99%'
				},{
					id:'institutionCode',
					xtype:'textfield',
					fieldLabel:'机构ID',
					name:'institutionCode',
					hidden:true,
					anchor:'99%'
				},{
					id:'institutionName',
					xtype:'textfield',
					labelStyle: 'text-align:right;',
					fieldLabel:'主办机构名称',
					name:'institutionName',
					anchor:'80%',
					listeners:{
					render: function(obj) {
					var button=document.createElement("button");
					var redStar=document.createTextNode('清空');
					 button.setAttribute("className","x-btn");//IE设置class的方法    
					 button.setAttribute("backgroundColor","blue");//IE设置class的方法    
					 button.appendChild(redStar);
					 obj.el.dom.parentNode.appendChild(button);
					 button.onclick = (function(a){
							OrgPanel.getForm().reset();
							if(selRecord!=""){
								filterByStore();
								selRecord = "";
							}
					 });
					}
				}
				}]
			}]
		}],
		buttonAlign:'center'
	});
	
	
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var sm_1 = new Ext.grid.CheckboxSelectionModel();

	
	var cm = new Ext.grid.ColumnModel([ sm_1,   //机构分配列表的模型
	                                   {header : '机构编号',dataIndex : 'institutionCode',sortable : true,width : 175},
	                                   {header : '机构名称',dataIndex : 'institutionName',sortable : true,width : 175}
	                                   ]);
	
	var orgRecoder = new Ext.data.Record.create([
	                                             {name:'id',mapping:'ID'},
	                                             {name:'institutionCode',mapping:'INSTITUTION_CODE'},
	                                             {name:'institutionName',mapping:'INSTITUTION_NAME'}
	                                             ]);
		
	var orgReader = new Ext.data.JsonReader({
		totalProperty:'json.count',
		root:'json.data'
	},orgRecoder);
	
	var orgStore = new Ext.data.Store(
 			{	
 				proxy:new Ext.data.HttpProxy({
 					url:basepath+'/orgAssignQuery-Action.json',
 					failure : function(response){
 					var resultArray = Ext.util.JSON.decode(response.status);
 					if(resultArray == 403) {
 						Ext.Msg.alert('提示', response.responseText);
 					}
 				},
 				method:'GET'
 				}),
 				reader:orgReader
 			});
	
	var orgGrid =  new Ext.grid.GridPanel({	//机构分配数据表
		id : 'orgGrid',
		title:'协办机构',
		enableDragDrop   : true,
		ddGroup:'gridDDGroups',
		region:"center",
		store : orgStore,
		loadMask:true,
		ddText :'若请将选择的数据托到上边则设置为主办机构，托到左侧则移除该协办机构',
		cm : cm, 
		sm:sm_1,
		stripeRows : true,
		tbar:[{
			text:'移除',
			handler:function(){
				var records = orgGrid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if(recordsLen >=1){
					orgGrid.store.remove(records);
					filterByStore();
				}else{
					Ext.Msg.alert("提示", "请选择要移除的协办信息!");
					return false;
					
				}
		}
		}],
		loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
	});
/*	var rownum1 = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});*/
	var sm_orgInfo = new Ext.grid.CheckboxSelectionModel();

	var cm1 = new Ext.grid.ColumnModel([sm_orgInfo,//协办机构列表的模型
	                                    {header : 'ID',dataIndex : 'id',hidden:true},
	                                    {header : '机构编号',dataIndex : 'institutionCode',hidden:true},
	                                    {header : '机构名称',dataIndex : 'institutionName',sortable : true,width : 140}
	                                    ]);
	var orgInfoStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/commsearch.json?condition='+Ext.encode({searchType : 'SUBORGS'})
		}),
		reader : new Ext.data.JsonReader( {
			root : 'json.data'
		},[{name:'id',mapping:'ID'},
		   {name:'institutionCode',mapping : 'UNITID'},
		   {name:'institutionName',mapping:'UNITNAME'}])
	});


	var orgInfoGrid =  new Ext.grid.GridPanel({	//机构分配数据表
		id : 'orgInfoGrid',
		title:'下级机构列表',
		enableDragDrop : true,
		sm:sm_orgInfo,//		deferRowRender:false,
		ddGroup:'gridDDGroup1',
		region:'west',//布局位置设置
		width:'200',
		store : orgInfoStore,
		stripeRows : true,
		frame:true,
		cm : cm1,
		ddText :'请将选择的数据拖到右侧，给主办机构或协办机构赋值',
		tbar:[{
			id:'mainButton',
			text:'设为主办',
			handler:function(){
				var records = orgInfoGrid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if(recordsLen == 1){
					OrgPanel.getForm().findField('institutionName').setValue(records[0].json.UNITNAME);
					OrgPanel.getForm().findField('institutionCode').setValue(records[0].json.UNITID);
				}else if(recordsLen >1){
					Ext.Msg.alert("提示", "只可选择一条信息设置为主办机构!");
					return false;
				}else{
					Ext.Msg.alert("提示", "请选择要设置的机构信息!");
					return false;
				}
				filterByStore();
		}
		},{
			id:'oMainButton',
			text:'设为协办',
			handler:function(){
				var records = orgInfoGrid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if(recordsLen>=1){
					orgGrid.store.add(records);
					filterByStore();
				}else{
					Ext.Msg.alert("提示", "请选择要设置的机构信息!");
					return false;
				}
		}
		}],
		loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
	
	});
	function filterByStore(){
		orgInfoStore.clearFilter(true);
		var selrecordt = orgStore.data.items;
		orgInfoStore.filterBy(function(record,id) { 
			for(var i = 0;i<selrecordt.length;i++){
			if(record.get('institutionCode') == selrecordt[i].data.institutionCode)  		   
			return false;
			}
			if(record.get('institutionCode') == Ext.getCmp('institutionCode').getValue())
				return false;
			return true;
		});
	}
	function dropTargetEl(){//各种拖拽
		var orgInfoGridDropTargetE1 =  orgInfoGrid.getView().scroller.dom;//orgInfoGrid的作用域
		var orgGridDropTargetE1 = orgGrid.getView().scroller.dom;//orgGrid的作用域
		var orgPanelDropTargetEl = OrgPanel.getEl().dom;//orgPanel的作用域
		var orgInfoGridDropTarget1 = new Ext.dd.DropTarget(orgGridDropTargetE1, {
			ddGroup : 'gridDDGroup1',
			notifyEnter : function(ddSource, e, data) {
				data.ddel.innerText = "设置为协办机构";
			},
			notifyOut : function(ddSource,e,data){
			data.ddel.innerHTML = "若请将选择的数据托到上边则设置为主办机构，托到左侧则移除该协办机构";
			},
			notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
			var selectedRecord = ddSource.dragData.selections;//拖拽的那条记录
			orgGrid.store.add(selectedRecord);
			orgGrid.store.sort('institutionCode', 'ASC');
			orgInfoGrid.store.sort('institutionCode','ASC');
			filterByStore();
			return true;
		}			
		});
		
		var orgGridDropTarget1 = new Ext.dd.DropTarget(orgInfoGridDropTargetE1, {
			ddGroup : 'gridDDGroups',
			notifyEnter : function(ddSource, e, data) {
			data.ddel.innerText = "移除协办机构";
			orgGrid.body.stopFx();
			orgInfoGrid.body.highlight();//将所选择的节点拖拽到页面的时候，页面变亮
		},	
		notifyOut : function(ddSource,e,data){
			data.ddel.innerHTML = "请将选择的数据拖到右侧，给主办机构或协办机构赋值";
			},
		notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
			var selectedRecord = ddSource.dragData.selections;//拖拽的那条记录
			Ext.each(selectedRecord, ddSource.grid.store.remove, ddSource.grid.store);
			filterByStore();
			orgGrid.store.sort('institutionCode', 'ASC');
			orgInfoGrid.store.sort('institutionCode','ASC');
			return true;
		}			
		});
		
		var orgPanelDropTarget = new Ext.dd.DropTarget(orgPanelDropTargetEl, {
			ddGroup : 'gridDDGroup1',
			notifyEnter : function(ddSource, e, data) {
			data.ddel.innerHTML = "设置为<b>主办</b>机构";
			OrgPanel.body.stopFx();
			OrgPanel.body.highlight();//将所选择的节点拖拽到页面的时候，页面变亮
			},
			notifyOut : function(ddSource,e,data){
				data.ddel.innerHTML = "请将选择的数据拖到右侧，给主办机构或协办机构赋值";

			},
			notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
			var selectedRecord = ddSource.dragData.selections;//拖拽的那条记录
			OrgPanel.getForm().findField('institutionName').setValue(selectedRecord[0].data.institutionName);
			OrgPanel.getForm().findField('institutionCode').setValue(selectedRecord[0].data.institutionCode);
			selRecord = selectedRecord[0];
			if(ddSource.grid.id == "orgGrid"){
				orgGrid.store.remove(selectedRecord[0]);
			}
			filterByStore();
			orgGrid.store.sort('institutionCode', 'ASC');
			orgInfoGrid.store.sort('institutionCode','ASC');
			return true;
		}
		});
		
		var orgPanelDropTarget_1 = new Ext.dd.DropTarget(orgPanelDropTargetEl, {
			ddGroup : 'gridDDGroups',
			notifyEnter : function(ddSource, e, data) {
			OrgPanel.body.stopFx();
			OrgPanel.body.highlight();//将所选择的节点拖拽到页面的时候，页面变亮
		}
		});
	}


 	var orgWin = new Ext.Window({
		layout : 'fit',
		width : 720,
		height : 416,
		closable : true,
		autoScroll : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		animCollapse : false,
		animateTarget : Ext.getBody(),
		border : false,
		buttonAlign : "center",
		title : '机构分配',
		items : [{
			layout : 'border',
			items : [orgInfoGrid, 
			         {
						region:'center',
						layout:'fit',
						items:[ {
							layout:'border',
							items:[OrgPanel,orgGrid ]
						}]
			         }]
		}],
		buttons : [{
			text:'保存',
			handler:function(){
			var mainCode = "";//主办机构的Code
			var mainCodeName = "";//主办机构的Name
			var mainFlag = OrgPanel.getForm().findField('institutionCode').getValue();
			var mainFlagName = OrgPanel.getForm().findField('institutionName').getValue();
			if(mainFlag != "" && mainFlag != __units){
				mainCode = mainFlag;
				mainCodeName = mainFlagName;
			}
			var t = orgWin.custid_1;
			var delIDs = orgWin.delID;
			if(orgWin.mainType_1 == 2){
				var OmainLength = orgGrid.store.data.length;//协办机构的信息长度
				var tmd = OmainLength;
				var OmainFlag = '';
				if(tmd != 0){
					var Model = [];//协办机构的CODE和协办机构的NAME
					for(var i = 0;i<OmainLength;i++){
						var createModel = {};
						OmainFlag = orgGrid.store.data.items[i];
						createModel.institutionCode = OmainFlag.data.institutionCode;
						createModel.institutionName = OmainFlag.data.institutionName;
						Model.push(createModel);
					}
				}else{
					Ext.Msg.alert('系统提示信息', '您当前未做任何操作，请确认！');
					return false;
				}
				Ext.Ajax.request( {
					url : basepath + '/customer_assign!assignoMainOrg.json',
					method : 'POST',
					params : {
						'models' : Ext.encode(Model),//要保存的协办信息
						'custIds' :orgWin.custid_1,//所选择的客户的custId
						'delIDs' : delIDs
				},
				success : function(){
					Ext.Msg.alert('系统提示信息', '操作成功');
					orgStore.removeAll();
					listPanel.loadCurrData();
					orgWin.hide();
				},
				failure : function(){
					Ext.Msg.alert('系统提示信息','操作失败');
				}
				}); 				
			//保存协办信息，删除本机构的 协办归属信息
			}else if(orgWin.mainType_1 == 1){
				var newRecords = orgStore.data.items;
				var newRecords_1 = newRecords;
				var oldRecords = orgStore.reader.jsonData.json.data;
				var oldLength = oldRecords.length;
				var newLength = newRecords.length;
				var tempId;
				var idStr = '';
				var tid;
				var noId = '';
				var tidName;
				var removeOmain = [];
				var addModel = [];
				if(newLength!=0){//如果store里的数据不为空
					for(var h=0;h<newLength;h++){//取出未修改的记录，若没有则idStr为空
						for(var k=0;k<oldLength;k++){
							if(oldRecords[k].INSTITUTION_CODE == newRecords_1[h].data.institutionCode){
								tempId = newRecords_1[h].data.institutionCode;
								idStr += tempId;
								if( h != newLength-1)
									idStr += ',';
							
							}
						}
					}
					var a = idStr.split(",");
					var t = 0;
					for(var i=0;i<newLength;i++){//取出作为新增的记录
						for(var j=0;j<a.length;j++){
							if(newRecords[i].data.institutionCode != a[j]){
								t=1;
								continue;}
							else{
								t=0;
								break;
							}
						}
						if(t==1){
							var prepareModel = {};
							prepareModel.institutionCode = newRecords[i].data.institutionCode;
							prepareModel.institutionName = newRecords[i].data.institutionName;
							addModel.push(prepareModel);
						}
					}
					var v = 0;
				if(idStr != ""){
					for(var i =0;i<oldLength;i++){
						for(var j=0;j<a.length;j++){
							if(oldRecords[i].INSTITUTION_CODE != a[j]){
								v = 1;
								continue;
							}else{
								v=0;
								break;
							}
						}
						if(v == 1){
							var xModel = {};
							tid = oldRecords[i].INSTITUTION_CODE;
							tidName = oldRecords[i].INSTITUTION_NAME;
							xModel.institutionCode = oldRecords[i].INSTITUTION_CODE;
							xModel.institutionName = oldRecords[i].INSTITUTION_NAME;
							removeOmain.push(xModel);
							if(noId != ""){
								noId += ',';
								noId += tid;
							}else{
								noId += tid;
								
							}
						}
					}
				}
				}
				if(newLength == 0 && idStr == "" && oldLength != 0){
					for(var h = 0;h<oldLength;h++){
						var xModel = {};

						tid = oldRecords[h].INSTITUTION_CODE;
						tidName = oldRecords[h].INSTITUTION_NAME;
						noId += tid;
						if( h != oldLength-1)
							noId += ',';
						xModel.institutionCode = oldRecords[h].INSTITUTION_CODE;
						xModel.institutionName = oldRecords[h].INSTITUTION_NAME;
						removeOmain.push(xModel);
					}
				}
				if(mainCode == "" && addModel =="" && removeOmain == ""){
					Ext.Msg.alert('系统提示信息', '您当前未做任何操作，请确认！');
					return false;
				}
				Ext.Ajax.request({//修改主办信息，保存协办信息
					url : basepath + '/customer_assign!assignMainOrg.json',
					method : 'POST',
					params : {
						'mainCode':mainCode,//主办机构CODE
						'mainName':mainCodeName,//主板机构NAME
						'models' : Ext.encode(addModel),//新增的协办信息
						"idStr"  : noId,//保留已有的协办ID
						'custIds' :orgWin.custid_1,//当前选中的客户ID
						'delIDs' : delIDs,//当前选择客户的信息的ID，用来修改主办信息
						'removeOmain' : Ext.encode(removeOmain)
				},
				success : function(){
					Ext.Msg.alert('系统提示信息', '操作成功');
					orgStore.removeAll();
					OrgPanel.getForm().reset();
					listPanel.loadCurrData();
					orgWin.hide();
				},
				failure :function(){	
					Ext.Msg.alert('系统提示信息','操作失败');}
				
				}); 
			}
		}
		},'-',{
			text:'取消',
			handler:function(){
			orgInfoStore.clearFilter(true);
			OrgPanel.getForm().reset();
			orgWin.hide();
		}
		}]
 	});
