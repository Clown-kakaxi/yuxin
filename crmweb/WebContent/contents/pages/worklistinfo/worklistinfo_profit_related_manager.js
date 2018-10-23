/**
 * @description 利润关联方管理-待办工作审批
 * @author luyy
 * @since 2014-07-09
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	var id = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
		
	var relateStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	        field:'key',
	        direction:'ASC'
	    },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=CUS0100038'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	relateStore.load();
	
	var stateStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	        field:'key',
	        direction:'ASC'
	    },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=STATE_TYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	stateStore.load();
		
	var store1 = new Ext.data.Store({
		restful:true,	
		proxy : new Ext.data.HttpProxy({
			url:basepath+'/profitRelate.json'
		}),
        reader: new Ext.data.JsonReader({
        	root : 'json.data'
        }, [
			{name:'id',mapping:'ID'},
    		{name:'custId',mapping:'CUST_ID'},
    		{name:'custName',mapping:'CUST_NAME'},
    		{name:'custNameR',mapping:'CUST_NAME_R'},
    		{name:'custNoR',mapping:'CUST_NO_R'},
    		{name: 'rType',mapping:'R_TYPE'},
    		{name:'relationship',mapping:'RELATIONSHIP_ORA'},
            {name: 'rDesc',mapping : 'R_DESC'},
            {name: 'createUserId',mapping : 'CREATE_USER_ID'},
			{name: 'createUserName',mapping : 'CREATE_USER_NAME'},
            {name: 'createDate',mapping : 'CREAT_DATE'},
            {name: 'rState',mapping : 'R_STATE'} 
		])
	});
	   
	var transedStore = new Ext.data.Store( {
		restful : true,
	    proxy : new Ext.data.HttpProxy( {
	    	url : basepath + '/profitRelate.json'
	    }),
    	reader : new Ext.data.JsonReader( {
    		totalProperty : 'json.count',
    		root : 'json.data'
    	}, [ 
			{name:'id',mapping:'ID'},
			{name:'custId',mapping:'CUST_ID'},
			{name:'custName',mapping:'CUST_NAME'},
			{name:'custNameR',mapping:'CUST_NAME_R'},
			{name:'custNoR',mapping:'CUST_NO_R'},
			{name: 'rType',mapping:'R_TYPE'},
			{name:'relationship',mapping:'RELATIONSHIP_ORA'},
			{name: 'rDesc',mapping : 'R_DESC'},
			{name: 'createUserId',mapping : 'CREATE_USER_ID'},
			{name: 'createUserName',mapping : 'CREATE_USER_NAME'},
			{name: 'createDate',mapping : 'CREAT_DATE'},
			{name: 'rState',mapping : 'R_STATE'} 
        ])
	});
		
	newSm = new Ext.grid.CheckboxSelectionModel();
	var num1 = new Ext.grid.RowNumberer({
		header : 'No.',
	  	width : 35
	});
	                                           
	var cm1 = new Ext.grid.ColumnModel([
//		num1,	// 定义列模型
	    {header : 'ID', dataIndex : 'id',hidden : true}, 
	    {header:'客户编号',dataIndex:'custId',sortable : true,width : 100},
	  	{header:'客户名称',dataIndex:'custName',sortable : true,width : 100},
		{header:'关联客户名称',dataIndex:'custNameR',sortable : true,width : 100},
		{header:'关联客户编号',dataIndex:'custNoR',sortable : true,width : 100},
		{header:'关联类型',dataIndex:'rType',hidden : true,sortable : true,width : 100},
		{header:'关联关系',dataIndex:'relationship',sortable : true,width : 100},
		{header:'关联描述',dataIndex:'rDesc',sortable : true,width : 100},
		{header:'创建者编号',dataIndex:'createUserId',width:100},
		{header:'创建者姓名',dataIndex:'createUserName',width:100},
		{header:'创建日期',dataIndex:'createDate',hidden : true,width:100},
		{header:'审批状态',dataIndex:'rState',hidden : true,width:100}
	]);
	var custGrid1 = new Ext.grid.EditorGridPanel({
		title : '利润关联方管理',
	  	autoScroll : true,
	  	height:150,
	  	region : 'center',
	    store: store1,
	  	stripeRows : true, // 斑马线
	  	sm:newSm,
	  	cm : cm1,
	  	viewConfig : {}
	});
	  
	var num = new Ext.grid.RowNumberer({
	  	header : 'No.',
	  	width : 35
	});
	                                           
	var cm = new Ext.grid.ColumnModel([
//	   	num,newSm,	// 定义列模型
	    {header : 'ID', dataIndex : 'id',hidden : true}, 
	    {header:'客户编号',dataIndex:'custId',hidden : true,sortable : true,width : 100},
	  	{header:'客户名称',dataIndex:'custName',hidden : true,sortable : true,width : 100},
		{header:'关联客户名称',dataIndex:'custNameR',sortable : true,width : 100},
		{header:'关联客户编号',dataIndex:'custNoR',hidden : true,sortable : true,width : 100},
		{header:'关联类型',dataIndex:'rType',hidden : true,sortable : true,width : 100},
		{header:'关联关系',dataIndex:'relationship',sortable : true,width : 100},
		{header:'关联描述',dataIndex:'rDesc',sortable : true,width : 100},
		{header:'创建者编号',dataIndex:'createUserId',width:100},
		{header:'创建者姓名',dataIndex:'createUserName',width:100},
		{header:'创建日期',dataIndex:'createDate',width:100,hidden : true},
		{header:'审批状态',dataIndex:'rState',hidden : true,width:100}
	]);
	var custGrid = new Ext.grid.EditorGridPanel({
	  	title : '利润关联方管理',
	  	autoScroll : true,
	  	height:150,
	  	region : 'center',
//	    tbar : [{
//	    	 text : '新增'
////			 handler:function() {
////				saveBaseValue(getCurrentView().contentPanel.getForm());
////				showCustomerViewByTitle('新增');
////			}
//	    },{
//	    	text : '修改'
////			handler:function(){
////			var selectLength = newPanel_1.getSelectionModel().getSelections().length;	
////			if (selectLength != 1) {
////				Ext.Msg.alert('提示', '请选择一条记录！');
////				return false;
////			} 
////			record = newPanel_1.getSelectionModel().getSelections()[0];	
////			saveBaseValue(getCurrentView().contentPanel.getForm());
////			showCustomerViewByTitle("修改关联客户");
////			getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
////			getCurrentView().contentPanel.getForm().findField('CUST_NO_R').setValue(record.data.CUST_NO_R);
////			getCurrentView().contentPanel.getForm().findField('CUST_NAME_R').setValue(record.data.CUST_NAME_R);
////			getCurrentView().contentPanel.getForm().findField('RELATIONSHIP').setValue(record.data.RELATIONSHIP);
////			getCurrentView().contentPanel.getForm().findField('R_DESC').setValue(record.data.R_DESC);
////			}
//	    },{
//	    	text:'删除'
////	    	handler :function(){
////	    		var selectLength = newPanel_1.getSelectionModel().getSelections().length;
////	    	 	var selectRecords = newPanel_1.getSelectionModel().getSelections();
////	    		if(selectLength < 1){
////	     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
////	    			return false;
////	    		}
////	    		var times = '';
////	    		var ids='';
////	    		for(var i=0; i < selectLength; i++){
////	    			var selectRecord = selectRecords[i];
////	    			if(i==0){
////	    			  times += "'"+selectRecord.data.CREATE_TIMES+"'";
////	    			  ids+=selectRecord.data.ID;
////	    			}else{
////	    			  times += ",'"+selectRecord.data.CREATE_TIMES+"'";
////	    			  ids+=","+selectRecord.data.ID;
////	    			}
////	    		 }
////	    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
////		    		if(buttonId.toLowerCase() == 'no'){
////		        		return false;
////		    		}
////	    			Ext.Ajax.request({
////	    				url : basepath + '/profitRelatedShip!batchDel.json',
////	    				method : 'GET',
////	    				params:{
////	    					'idStr':ids 
////	    				},
////	    				success : function() {
////	    					Ext.Msg.hide(); 
////	    					showCustomerViewByTitle('新增客户间关联关系');
////							setBaseValue(getCurrentView().contentPanel.getForm());
////	    					newPanelStroe_1.reload({params:{ID:times}});
////	    				},
////	    				failure : function(response) {
////	    					var resultArray = Ext.util.JSON.decode(response.status);
////	    			 		if(resultArray == 403) {
////	    		           		Ext.Msg.alert('提示', response.responseText);
////	    			 		}else{
////	    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
////	    	 				}
////	    				}
////	    			});
////	    	   });
////	    	}
//	      }],
	    store: transedStore,
	  	stripeRows : true, // 斑马线
	  	sm:newSm,
	  	cm : cm,
	  	viewConfig : {}
	});
	  
	var transForm1 = new Ext.FormPanel({
		frame : true,
	    height: 160,
	    region : 'north',
	    items : [{
	    	layout : 'column',
	    	items : [{
	    		layout : 'form',
	    		columnWidth : .5,
	    		labelWidth:100,
	    		items : [ 
	    			{xtype:'textfield',name : 'custName',fieldLabel : '客户名称',labelStyle: 'text-align:right;',readOnly:true,anchor : '95%',hidden:false},
	    			{xtype:'textfield',name:'custNameR',fieldLabel : '关联客户名称',labelStyle : 'text-align:right;',anchor : '95%',readOnly:true,hidden:false},
					{xtype:'combo',name : 'relationship',hiddenName:'relationship',fieldLabel: '关联关系',forceSelection : true,
	                 	labelStyle: 'text-align:right;',triggerAction:'all',mode:'local',store:relateStore,valueField:'key',displayField:'value',emptyText:'请选择',readOnly:true,anchor : '95%',hidden:false},
					{xtype:'textfield',name : 'rDesc',fieldLabel: '关联描述',labelStyle: 'text-align:right;',anchor : '95%',readOnly:true,hidden:false}
	    		]
	    	}]
	    }]
	});
    var cTrans = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: '流程业务信息',
	    items:[transForm1,custGrid,custGrid1]
   }); 
	var EchainPanel = new Mis.Echain.EchainPanel({
		instanceID:instanceid,
		nodeId:nodeid,
		nodeName:curNodeObj.nodeName,
		fOpinionFlag:curNodeObj.fOpinionFlag,
		approvalHistoryFlag:curNodeObj.approvalHistoryFlag,
		WindowIdclode:curNodeObj.windowid,
		callbackCustomFun:'3_a10##1'
	});
	var view = new Ext.Panel( {
		renderTo : 'viewEChian',
		 frame : true,
		width : document.body.scrollWidth,
		height : document.body.scrollHeight-40,
		autoScroll : true,
		layout : 'form',
		items : [cTrans,EchainPanel]

	});
			
	store1.load({
		params : {
			profitId:id
    	},
        callback:function(){
        	if(store1.getCount()!=0){
        		loadFormData();
        	}
		}
	});
	function loadFormData(){
		var type=instanceid.split('_')[4];
		if(type=='1'){//新增
			custGrid.show();
			custGrid1.hide();
			setFieldVisible(false);
			transForm1.getForm().loadRecord(transedStore.getAt(0));	
		}else if(type=='2'){//修改
			custGrid.hide();
			custGrid1.show();
			setFieldVisible(false);
			transForm1.getForm().loadRecord(store1.getAt(0));	
		}else if(type=='3'){//删除
			custGrid.hide();
			custGrid1.hide();
			setFieldVisible(true);
			transForm1.getForm().loadRecord(store1.getAt(0));
		}
		
	}
	
	function setFieldVisible(bool){
		var type=instanceid.split('_')[4];
		if(type=='1'){//新增
			transForm1.getForm().findField('custName').setValue(instanceid.split('_')[3]);
			transForm1.getForm().findField('custNameR').setVisible(bool);
			transForm1.getForm().findField('relationship').setVisible(bool);
			transForm1.getForm().findField('rDesc').setVisible(bool);
		}else if(type=='2'){//修改
			transForm1.getForm().findField('custName').setVisible(true);
			transForm1.getForm().findField('custNameR').setVisible(true);
			transForm1.getForm().findField('relationship').setVisible(true);
			transForm1.getForm().findField('rDesc').setVisible(true);
		}else if(type=='3'){//删除
			transForm1.getForm().findField('custName').setVisible(bool);
			transForm1.getForm().findField('custNameR').setVisible(bool);
			transForm1.getForm().findField('relationship').setVisible(bool);
			transForm1.getForm().findField('rDesc').setVisible(bool);
		}

	}
	
	transedStore.load({
		 params : {
			'profitId':id
		 }
	});
	
});
