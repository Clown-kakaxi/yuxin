/**
 * 分配机构和分配客户经理--客户
 * @author songxs
 * @since 2012-1-10
 */
	var selRecord_1 = "";
	var custMgrPanel = new Ext.form.FormPanel({
		id:'custMgrPanel',
		frame:true,
		region:'north',
		height : 80,
		autoScroll : true,
		labelWidth:120,
		split:true,
		items:[{
			columnWidth:.99,
			layout: 'form',
			items:[{
				id:'id',
				xtype:'textfield',
				fieldLabel: 'ID',
				name: 'id',
				hidden : true,
				anchor:'99%'
			},{
				id:'mgrId',
				xtype:'textfield',
				fieldLabel:'主办客户经理编号',
				name:'mgrId',
				hidden:true,
				anchor:'99%'
			},{
				id:'mgrName',
				xtype:'textfield',
				fieldLabel:'主办客户经理名称',
				name:'mgrName',
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
					 custMgrPanel.getForm().reset();
						if(selRecord_1!=""){
							filterByCustMgrStore();
							selRecord_1 = "";
						}
				 });
				}
			}
			}]
		}],
		buttonAlign:'center'
	});
	

	var sm_2= new Ext.grid.CheckboxSelectionModel();

	var cm_1 = new Ext.grid.ColumnModel([sm_2,  //协办客户经理分配列表的模型
	                                     {header : '归属客户经理编号',dataIndex : 'mgrId',sortable : true,width : 200},
	                                     {header : '归属客户经理名称',dataIndex : 'mgrName',sortable : true,width : 200}
	                                     ]);
	var custMgrRecoder = new Ext.data.Record.create([//协办客户经理record
		 {name:'id',mapping:'ID'},
		 {name:'mgrId',mapping:'MGR_ID'},
		 {name:'mgrName',mapping:'MGR_NAME'}
		 ]);
	var custMgrReader = new Ext.data.JsonReader({//协办客户经理reader
		totalProperty:'json.count',
		root:'json.data'
	},custMgrRecoder);
		
	var custMgrStore = new Ext.data.Store(//协办客户经理列表store
			{	
				proxy:new Ext.data.HttpProxy({
					url:basepath+'/custMgrAssignQuery-Action.json',
					failure : function(response){
					var resultArray = Ext.util.JSON.decode(response.status);
					if(resultArray == 403) {
						Ext.Msg.alert('提示', response.responseText);
					}
				},
				method:'GET'
				}),
				reader:custMgrReader
			});
	
	var custMgrGrid =  new Ext.grid.GridPanel({	//协办客户经理分配数据表
		id : 'custMgrGrid',
		title:'协办客户经理',
		enableDragDrop   : true,
		ddGroup:'gridDDGroup_1',
		region:"center",
		height : 360,
		//layout:'fit',
		autoExpandMin:10,
		ddText:'若请将选择的数据托到上边则设置为主办客户经理，托到左侧则移除该协办客户经理',
		store : custMgrStore,
		cm : cm_1,
		sm:sm_2,
		tbar:[{
			text:'移除',
			handler:function(){
				var records = custMgrGrid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if(recordsLen >=1){
					custMgrGrid.store.remove(records);
					filterByCustMgrStore();
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
	var sm_info= new Ext.grid.CheckboxSelectionModel();

	var cm_2 = new Ext.grid.ColumnModel([sm_info,    //供选择的客户经理列表的模型
	                                     {header : '客户经理编号',dataIndex : 'mgrId',sortable : true,width : 140,hidden:true},
	                                     {header : '客户经理名称',dataIndex : 'mgrName',sortable : true,width : 140}
	                                     ]);
	
	var custMgrInfoStore = new Ext.data.Store( {//供选择的客户经理列表store
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/commsearch.json?condition='+Ext.encode({searchType : 'ORGUSER'})
		}),
		reader : new Ext.data.JsonReader( {//reader
			root : 'json.data'
		},[{name:'institution',mapping : 'UNITID'},{name:'mgrId',mapping:'USERID'},{name: 'mgrName',mapping : 'USERNAME'}])
	});

	var custMgrInfoGrid =  new Ext.grid.GridPanel({	//供选择的客户经理的数据表
		id : 'custMgrInfoGrid',
		title:'下级机构的客户经理列表',
		enableDragDrop : true,
		ddGroup:'gridDDGroup_2',
		ddText :'请将选择的数据拖到右侧，给主办客户经理或协办客户经理赋值',
		width:200,
		region:'west',
		store : custMgrInfoStore,
		cm : cm_2,
		sm:sm_info,
		tbar:[{
			id:'mainMgrButton',
			text:'设为主办',
			handler:function(){
				var records = custMgrInfoGrid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if(recordsLen == 1){
					custMgrPanel.getForm().findField('mgrName').setValue(records[0].json.USERNAME);
					custMgrPanel.getForm().findField('mgrId').setValue(records[0].json.USERID);
				}else if(recordsLen >1){
					Ext.Msg.alert("提示", "只可选择一条信息设置为主办客户经理!");
					return false;
				}else{
					Ext.Msg.alert("提示", "请选择要设置的客户经理信息!");
					return false;
				}
				filterByCustMgrStore();
		}
		},{
			id:'omainMgrButton',
			text:'设为协办',
			handler:function(){
				var records = custMgrInfoGrid.selModel.getSelections();// 得到被选择的行的数组
				var recordsLen = records.length;// 得到行数组的长度
				if(recordsLen>=1){
					if(__custManagerType == 2 ||__custManagerType==4 ){
						var dataLength = custMgrGrid.store.getCount();
						if(dataLength == 1){
							Ext.Msg.alert("提示", "该客户只可归属于一个客户经理，请先将已选择的客户经理托出后，再添加!");
						}else{
							custMgrGrid.store.add(records);
							filterByCustMgrStore();
						}
						}else{
							custMgrGrid.store.add(records);
					filterByCustMgrStore();}
					}else{
						Ext.Msg.alert("提示", "请选择要设置的客户经理信息!");
						return false;
					}
			}
		}],
		loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
	});
	function filterByCustMgrStore(){
		custMgrStore.clearFilter(true);
		var selrecordc = custMgrStore.data.items;
		custMgrInfoStore.filterBy(function(record,id) { 
			for(var i = 0;i<selrecordc.length;i++){
			if(record.get('mgrId') == selrecordc[i].data.mgrId)  		   
			return false;
			}
			if(record.get('mgrId') == Ext.getCmp('mgrId').getValue())
				return false;
			return true;
		});
	}
	function dropTargetEl_1(){//各种拖拽
		var custMgrInfoGridDropTargetE1 =  custMgrInfoGrid.getView().scroller.dom;//custMgrInfoGrid的作用域
		var custMgrGridDropTargetE1 = custMgrGrid.getView().scroller.dom;//custMgrGrid的作用域
		var custMgrPanelDropTargetEl = custMgrPanel.getEl().dom;//custMgrPanel的作用域

		var custMgrInfoGridDropTarget1 = new Ext.dd.DropTarget(custMgrGridDropTargetE1, {
			ddGroup : 'gridDDGroup_2',
			notifyEnter : function(ddSource, e, data) {
			data.ddel.innerText = "设置为协办客户经理";
			},
			notifyOut : function(ddSource,e,data){
				data.ddel.innerHTML = "若请将选择的数据托到上边则设置为主办客户经理，托到左侧则移除该协办客户经理";
			},
			notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
			var selectedRecord = ddSource.dragData.selections;//拖拽的那条记录
			var belongt = __custManagerType;//客户归属参数

			if(belongt == 2 || belongt == 4){//若该客户只可归属于一个客户经理，则要进行限制，当已经选择一个客户经理的话，就不允许将选择的信息拖进协办客户经理的grid
				var addLength = custMgrGrid.store.data.items.length;
				if(addLength >= 1){
    				Ext.Msg.alert("提示", "该客户只可归属于一个客户经理，请先将已选择的客户经理托出后，再添加!");
    				return false;
				}else{
					custMgrGrid.store.add(selectedRecord);
					custMgrGrid.store.sort('mgrId', 'ASC');
					custMgrInfoGrid.store.sort('mgrId','ASC');
					filterByCustMgrStore();
				}
				}
			else{
			custMgrGrid.store.add(selectedRecord);
			custMgrGrid.store.sort('mgrId', 'ASC');
			custMgrInfoGrid.store.sort('mgrId','ASC');
			filterByCustMgrStore();
			return true;}
		}			
		});
		
		var custMgrGridDropTarget1 = new Ext.dd.DropTarget(custMgrInfoGridDropTargetE1, {
			ddGroup : 'gridDDGroup_1',
			notifyEnter : function(ddSource, e, data) {
			data.ddel.innerText = "移除协办客户经理";

			custMgrGrid.body.stopFx();
			custMgrInfoGrid.body.highlight();//将所选择的节点拖拽到页面的时候，页面变亮
		},	
		notifyOut : function(ddSource,e,data){
			data.ddel.innerHTML = "请将选择的数据拖到右侧，给主办客户经理或协办客户经理赋值";
			},
		notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
			var selectedRecord = ddSource.dragData.selections;//拖拽的那条记录
			Ext.each(selectedRecord, ddSource.grid.store.remove, ddSource.grid.store);
			custMgrGrid.store.sort('mgrId', 'ASC');
			custMgrInfoGrid.store.sort('mgrId','ASC');
			filterByCustMgrStore();
			return true;
		}			
		});
		var custMgrPanelDropTarget = new Ext.dd.DropTarget(custMgrPanelDropTargetEl, {
			ddGroup : 'gridDDGroup_2',
			notifyEnter : function(ddSource, e, data) {
			data.ddel.innerHTML = "设置为<b>主办</b>客户经理";
			custMgrPanel.body.stopFx();
			custMgrPanel.body.highlight();//将所选择的节点拖拽到页面的时候，页面变亮
			},		
			notifyOut : function(ddSource,e,data){
				data.ddel.innerHTML = "请将选择的数据拖到右侧，给主办客户经理或协办客户经理赋值";

			},
			notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
				var selectedRecord = ddSource.dragData.selections;//拖拽的那条记录
				custMgrPanel.getForm().findField('mgrName').setValue(selectedRecord[0].data.mgrName);
				custMgrPanel.getForm().findField('mgrId').setValue(selectedRecord[0].data.mgrId);
				selRecord_1 = selectedRecord;
				if(ddSource.grid.id == "custMgrGrid"){
					custMgrGrid.store.remove(selectedRecord[0]);
				}
				custMgrGrid.store.sort('mgrId', 'ASC');
				custMgrInfoGrid.store.sort('mgrId','ASC');
				filterByCustMgrStore();
				return true;
			}	
		});
		
		var custMgrPanelDropTarget_1 = new Ext.dd.DropTarget(custMgrPanelDropTargetEl, {
			ddGroup : 'gridDDGroup_1',
			notifyEnter : function(ddSource, e, data) {
			custMgrPanel.body.stopFx();
			custMgrPanel.body.highlight();//将所选择的节点拖拽到页面的时候，页面变亮
		}
		});
	}
	var custMgrWin = new Ext.Window({//展示窗口
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
		title : '客户经理分配',
		items:[{
			layout:'border',
			items : [custMgrInfoGrid, 
			         {
						region:'center',
						layout:'fit',
						items:[ {
							layout:'border',
							items : [custMgrPanel,custMgrGrid]
						}]
			         }]
		}],
		buttons:[{
			text:'保存',
			handler:function(){
			if(custMgrWin.mainType_2 == 1){
				var mainMgrId = "";//已选择的主办客户经理的ID
				var mainMgrName = "";//已选的主办客户经理的NAME
				var mainMgrIdt = custMgrPanel.getForm().findField('mgrId').getValue();//获取当前主办客户经理的ID，用来判断是否选择主办客户经理
				if(mainMgrIdt == ""){
					Ext.Msg.alert('提示信息', '主办客户经理为空，请选择！');
					return false;
				}else{
					mainMgrId = mainMgrIdt;
				    mainMgrName = custMgrPanel.getForm().findField('mgrName').getValue();
					var oMainLength = custMgrGrid.store.data.length;//已选择协办客户经理的书目
					if(oMainLength !=0){//将选择的协办的客户经理的数据放入mgrModel,传给后台
						var mgrModel = [];
						for(var i=0;i<oMainLength;i++){
							var oModel = {};
							var oMaint = custMgrGrid.store.data.items[i];
							oModel.mgrId = oMaint.data.mgrId;
							oModel.mgrName = oMaint.data.mgrName;
							mgrModel.push(oModel);
						}
					}		
					
					Ext.Ajax.request( {
						url : basepath + '/customer_assign!assignMainCustMgr.json',
						method : 'POST',
						params : {
							'mgrModel' : Ext.encode(mgrModel),//要保存的协办信息
							'custIds' :custMgrWin.custid_1,//所选择的客户的custId
							'mainMgrId':mainMgrId,
							'mainMgrName':mainMgrName
					},
					success : function(){
						Ext.Msg.alert('系统提示信息', '操作成功');
						custMgrStore.removeAll();
						listPanel.loadCurrData();
						custMgrWin.hide();
					},
					failure : function(){
						Ext.Msg.alert('系统提示信息','操作失败');
					}
					}); 
				}
			}else if(custMgrWin.mainType_2 == 2){
				var oMainLength = custMgrGrid.store.data.length;
				if(oMainLength !=0){
					var mgrModelt = [];
					for(var i=0;i<oMainLength;i++){
						var oModel = {};
						var oMaint = custMgrGrid.store.data.items[i];
						oModel.mgrId = oMaint.data.mgrId;
						oModel.mgrName = oMaint.data.mgrName;
						mgrModelt.push(oModel);
					}
				}else{
					Ext.Msg.alert('系统提示信息', '您当前未做任何操作，请确认！');
					return false;
				}	
				Ext.Ajax.request( {
					url : basepath + '/customer_assign!assignOmainCustMgr.json',
					method : 'POST',
					params : {
					'mgrModel' : Ext.encode(mgrModelt),//要保存的协办信息
					'custIds' :custMgrWin.custid_1//所选择的客户的custId
				},
				success : function(){
					Ext.Msg.alert('系统提示信息', '操作成功');
					custMgrStore.removeAll();
					listPanel.loadCurrData();
					custMgrWin.hide();
				},
				failure : function(){
					Ext.Msg.alert('系统提示信息','操作失败');
				}
				}); 
			}
		}},'-',{
			text:'取消',
			handler:function(){
				custMgrInfoStore.clearFilter(true);
				custMgrPanel.getForm().reset();
				custMgrWin.hide();
		}
		}
		]
	});
