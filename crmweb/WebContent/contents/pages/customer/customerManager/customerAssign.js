
var orgWin;
var custMgrWin;
var listPanel;
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var mainTypeStore = new Ext.data.ArrayStore({
		fields:['myId','displayText'],
		data:[['1','主办'],['2','协办']]
	});
	//证件类型
	var certTypStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	certTypStore.load();
	//客户类型
	var custTypStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	custTypStore.load();
	//客户级别
	var custLevStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=CDE0100016'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	custLevStore.load();
	var sId;
	var mainType;

	
	// 最终展现的panel
	listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "客户管理->待分配客户查询",
		stUrl : basepath + '/customer_assign.json?custManagerType='+__custManagerType,
		primary : "CUST_ID",
		checkbox : true,
		// 定义查询条件Form的高度
		seFormHeight : 80,
		// 定义增删详情页面弹出窗口高度
		winHeight : 250,
		//宽度
		winWidth : 600,
		dbclick : false,
		buts : [
		        {
		        	id : 'assignOrg',
		        	xtype : 'button',
		        	tooltip : '分配机构',
		        	text : '分配机构',
		        	iconCls:'resetIconCss ',
		        	disabled:JsContext.checkGrant('assignOrg'),
		        	handler:function() {
		        	if (listPanel.grid.selModel.hasSelection()) {
		        		var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
		        		var recordsLen = records.length;// 得到行数组的长度
		        		var mainType_1 = '';//选择客户的类型
		        		if(recordsLen >1){//判断所选择的客户的类型是否一致
		        			for(var i = 0;i<recordsLen-1;i++){
		        				var t = records[i].json.MAIN_TYPE;
		        				var t1 = records[i+1].json.MAIN_TYPE;
		        				if(t!=t1){
		        					Ext.Msg.alert("提示", "请选择均为主办或是协办的客户!");
		        					return false;
		        				}else{
		        					mainType_1 = records[i].json.MAIN_TYPE;
		        				}
		        				}
		        		}else{
		        			mainType_1 = records[0].json.MAIN_TYPE;
		        		}
		        		var tempCode;
		        		var custid_1 = '';
		        		for(var j = 0;j<recordsLen;j++){//获取选择客户的custId
		        			tempCode = records[j].json.CUST_ID;
		        			custid_1 += tempCode;
		        			if( j!= recordsLen-1)
		        				custid_1 += ',';
		        		}
		        		var tempDelID;
		        		var delID = '';
		        		for(var k = 0;k<recordsLen;k++){//获取所选择的机构分配表里的ID
		        			tempDelID = records[k].json.DEL_ID;
		        			delID += tempDelID;
		        			if( k!= recordsLen-1)
		        				delID += ',';
		        		}
		        		orgWin.custid_1 =  custid_1;//选择的客户custId
		        		orgWin.delID = delID;//本机构在机构分配表里的ID
		        		orgWin.mainType_1 = mainType_1;//选择的客户相对于本机构的类型
		        		orgWin.show();

		        		function muLoader(){//弹出窗口后的数据加载
		        			orgStore.load({
		        				params : {
		        					'custId' : orgWin.custid_1
		        			},callback:function(){
		        				filterByStore();
		        			}
		        			});
		        		}
		        		if(mainType_1 == 2){//如果所选择的客户为协办客户，则只能配置协办机构
		        			Ext.getCmp('orgPanel').hide();
		        			Ext.getCmp('orgGrid').show();
			        		Ext.getCmp("institutionName").setValue(__unitname);
			        		Ext.getCmp("institutionCode").setValue(__units);
			        		Ext.getCmp('mainButton').hide();
			        		Ext.getCmp('oMainButton').show();
		        			muLoader();
		        		}else {
		        			var showt = __custManagerType;
		        			if(showt == 3 || showt == 4){//如果为单一模式的话，只能设置主办机构
		        				Ext.getCmp('orgPanel').show();
				        		Ext.getCmp("institutionName").setValue(__unitname);
				        		Ext.getCmp("institutionCode").setValue(__units);
		        				Ext.getCmp('orgGrid').hide();
				        		Ext.getCmp('mainButton').show();
				        		Ext.getCmp('oMainButton').hide();
				        		muLoader();
		        			}else{
		        				Ext.getCmp('orgPanel').show();
		        				Ext.getCmp('mainButton').show();
				        		Ext.getCmp('oMainButton').show();
				        		Ext.getCmp("institutionName").setValue(__unitname);
				        		Ext.getCmp("institutionCode").setValue(__units);
		        				 muLoader();
		        			}
		        		}
		        		dropTargetEl();
		        	} else {
		        		Ext.Msg.alert("提示", "请先选择要分配的记录!");
		        	}
		        }
		        },'-',{
		        	id : 'assignCustmgr',
		        	xtype : 'button',
		        	tooltip : '分配客户经理',
		        	text : '分配客户经理',
		        	iconCls:'resetIconCss ',
		        	disabled:JsContext.checkGrant('assignCustmgr'),
		        	handler: function() {
		        		if (listPanel.grid.selModel.hasSelection()) {
		        			var records = listPanel.grid.selModel.getSelections();// 得到被选择的行的数组
		        			var recordsLen = records.length;// 得到行数组的长度
		        			var mainType_2 = '';//选择客户的类型
		        			if(recordsLen >1){//判断所选择的客户的类型是否一致
		        				for(var i = 0;i<recordsLen-1;i++){
		        					var t = records[i].json.MAIN_TYPE;
		        					var t1 = records[i+1].json.MAIN_TYPE;
		        					if(t!=t1){
		        						Ext.Msg.alert("提示", "请选择均为主办或是协办的客户!");
		        						return false;
		        					}else{
		        						mainType_2 = records[i].json.MAIN_TYPE;
		        					}
		        				}}else{
		        					mainType_2= records[0].json.MAIN_TYPE;
		        				}
		        			var tempCode;
		        			var custid_1 = '';
		        			for(var j = 0;j<recordsLen;j++){//获取选择客户的custId
		        				tempCode = records[j].json.CUST_ID;
		        				custid_1 += tempCode;
		        				if( j!= recordsLen-1)
		        					custid_1 += ',';
		        			}
		        			custMgrWin.custid_1 = custid_1;
		        			custMgrWin.mainType_2= mainType_2;
		        			if(mainType_2 == 2){
		        				Ext.getCmp('custMgrPanel').hide();
		        				Ext.getCmp('custMgrGrid').show();
		        				Ext.getCmp('mainMgrButton').hide();
		        				Ext.getCmp('omainMgrButton').show();
		        			}else {
		        				var belongt = __custManagerType;
		        				if(belongt == 2 || belongt == 4){
		        					Ext.getCmp('custMgrPanel').show();
		        					custMgrPanel.getForm().reset();
		        					Ext.getCmp('custMgrGrid').hide();
		        					Ext.getCmp('mainMgrButton').show();
			        				Ext.getCmp('omainMgrButton').hide();
		        				}else{
		        					Ext.getCmp('custMgrPanel').show();
		        					custMgrPanel.getForm().reset();
		        					Ext.getCmp('mainMgrButton').show();
			        				Ext.getCmp('omainMgrButton').show();
		        				}
		        			}
		        			custMgrStore.load({
		        				params : {
		        					'custId' : custid_1
		        			},callback:function(){
		        				filterByCustMgrStore();
		        				
		        			}
		        			});
		        			custMgrWin.show();
		        			dropTargetEl_1();
		        		} else {
		        			Ext.Msg.alert("提示", "请先选择要分配的记录!");
		        		}
		        }
		        },'-',{
		        	text:'查看分配历史',
		        	iconCls:'detailIconCss',
		        	handler:function(){
		        	customerAssignHistWin.show();
		        	custAssignTypeStore.reload();
		        }
		        
		        }],
		        // 查询字段定义，若不定义则不出现查询条件From
        selectItems : {
		layout : 'column',
		items : [ {
			columnWidth : .25,
			layout : 'form',
			labelWidth : 90,
			border : false,
			items : [ {name : 'CUST_ZH_NAME',xtype : 'textfield',fieldLabel : '客户中文名称',width : '100',anchor : '90%'
			} ]
		},{
			columnWidth : .25,
			layout : 'form',
			border : false,
			labelWidth : 90,
			items : [ {store : certTypStore,xtype : 'combo',resizable : true,fieldLabel : '证件类型',name : 'CERT_TYPE',hiddenName : 'CERT_TYPE',
						valueField : 'key',displayField : 'value',mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',
						emptyText : '请选择',selectOnFocus : true,width : '100',anchor : '90%'} ]
		},{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 90,
			border : false,
			items : [ {name : 'CERT_NUM',xtype : 'textfield',fieldLabel : '证件号码',width : '100',anchor : '90%'
			} ]
		},{
			columnWidth : .25,
			layout : 'form',
			labelWidth : 90,
			border : false,
			items : [ {store : custTypStore,xtype : 'combo',resizable : true,fieldLabel : '客户类型',name : 'CUST_TYP',hiddenName : 'CUST_TYP',
						valueField : 'key',displayField : 'value',mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',
						emptyText : '请选择',selectOnFocus : true,width : '100',anchor : '90%'} ]
		} ]
	},
	gclms : [ {
		name : 'org_id'
	},{
		name:'delId'
	},{ 
		name : 'INSTITUTION_NAME',
		sortable : true,
		header : '所属机构'
	},{
		name : 'CUST_ID',
		sortable : true,
		header : '客户编号'
	}, {
		name : 'CUST_ZH_NAME',
		sortable : true,
		header : '客户中文名称'
	}, {
		name : 'CERT_TYPE',
		header : '证件类型',
		type : 'mapping',
		store : certTypStore,
		mappingkey : 'key',
		mappingvalue : 'value'
	}, {
		name : 'CERT_NUM',
		sortable : true,
		header : '证件号码'
	}, {
		name : 'CUST_TYP',
		header : '客户类型',
		type : 'mapping',
		store : custTypStore,
		mappingkey : 'key',
		mappingvalue : 'value'
	}, {
		name : 'CUST_LEV',
		sortable : true,
		header : '客户级别',
		type : 'mapping',
		store : custLevStore,
		mappingkey : 'key',
		mappingvalue : 'value'
	},{
		name:'MAIN_TYPE',
		sortable : true,
		header : '主协办类型', 
		type : 'mapping',
		store : mainTypeStore,
		mappingkey : 'myId',
		mappingvalue : 'displayText'
	}],
	pagesize : 20
	});

	// 布局模型
	var viewport = new Ext.Viewport( {
		id:'viewport',
		layout : 'fit',
		items : [ listPanel ]
	});
});