/*******
 *@author : songxs
 *@since  : 2012-09-24
 *@constructor :对公客户基本信息
 * 
 */	
Ext.onReady(function() {
	var custid =oCustInfo.cust_id;//当前用户所查看的客户的客户号
	Ext.QuickTips.init(); 
/**********************************************码值store定义**************************************************************/
	
	var entMasterCretTypStore = new Ext.data.Store({//法定代表人证件类型store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var entHoldingTypeStore = new Ext.data.Store({//客户控股类型store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CDE0100015'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var entIndustryStore = new Ext.data.Store({//行业分类(主营)and 行业分类(副营)store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=PAR2100001'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var entScaleStore = new Ext.data.Store({//企业规模store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=DEM0200004'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var entBelongStore = new Ext.data.Store({//企业隶属store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CDE0100021'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var entEcomTypeStore = new Ext.data.Store({//企业经济性质store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=DEM0200002'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var entCustTypeStore = new Ext.data.Store({//对公客户类型store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CDE0100018'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var creditGradeStore = new Ext.data.Store({//信用评级store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CDE0100033'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var entCustGradeStore = new Ext.data.Store({//客户级别store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CDE0100016'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var ifFlagStore = new Ext.data.Store({//是否store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=IF_FLAG'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var entBusiTypeStore = new Ext.data.Store({//客户业务类型store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CUST_BSNESSTYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var entCountryStore = new Ext.data.Store({//所在国家(地区)store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=DEM0100011'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var entRegCurrStore = new Ext.data.Store({//注册资本币别store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=ACC1300012'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var busiPositrightsStore = new Ext.data.Store({//经营场地所有权store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CDE0100042'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var busiConditionStore = new Ext.data.Store({//经营状况store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=PAR0900005'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var certTypeStore = new Ext.data.Store({//证件类型store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=COM_CRET_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
/***************************************************************************************************************************/	
    var rootid = '0';
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
//		/**节点数组，可以改为从后台读取*/
		/**指向父节点的属性列*/
		parentAttr : 'PARENT',
		/**虚拟根节点id 如果select的值为root则为根节点*/
		rootValue : "0",
		/**用于展示节点名称的属性列*/
		textField : 'VALUE',
		/**指定节点ID的属性列*/
		idProperties : 'ID'
		/**节点点击事件句柄*/
	});
	Ext.Ajax.request({//请求产品树数据
	   	url : basepath + '/lookupEntIndustry.json?name=PAR2100001',
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText).JSON;
			loader.nodeArray = nodeArra;
			var children = loader.loadAll();
			Ext.getCmp('entMainIndustryTreeForShow').appendChild(children);
			Ext.getCmp('entSecondIndustryTreeForShow').appendChild(children);

		}
	});
    var entMainIndustryTreeForShow = new Com.yucheng.bcrm.TreePanel({//行业分类（主营）
    	id:'entMainIndustryTreeForShow',
		width:390,
		autoScroll:true,
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'0',
			text:'行业分类(主营 )主菜单',
			autoScroll:true,
	        expanded:true,
	        leaf:false,
			children:[]
		}),
		 resloader: loader,
		 split:true,
		 clickFn:function(node){
    		Ext.getCmp('entIndustry').setValue(node.text);
    		Ext.getCmp('entMainIndustry').setValue(node.id);
    },
    	animate : false,
    	useArrows : false,
    	border : false
    });

    var comboxWithTree = new Ext.form.ComboBox({
    	id : 'entIndustry', 
    	xtype:'combo',
		store : new Ext.data.SimpleStore({
				fields : [],
				data : [[]]
				}),
		labelStyle: 'text-align:right;',
		editable : false,
		emptyText : '请选择...',
		fieldLabel : '行业分类（主营）',
		anchor : '90%',
		mode : 'local',
		resizable :false,
		forceSelection:true,
		name:'entMainIndustryName',
		triggerAction : 'all',
		maxHeight : 390,
		tpl : "<tpl for='.'><div style='height:390px'><div id='addDeptTreeDiv_1s'></div></div></tpl>",
		allowBlank : false,
		onSelect : Ext.emptyFn,
		listeners:{
			'expand':function(combo){			
			entMainIndustryTreeForShow.render('addDeptTreeDiv_1s');
		},
			'collapse':function(combo){
			}
		}
	});
    var entSecondIndustryTreeForShow = new Com.yucheng.bcrm.TreePanel({
    	id:'entSecondIndustryTreeForShow',
    	width:390,
    	autoScroll:true,
		/**虚拟树形根节点*/
    	root: new Ext.tree.AsyncTreeNode({
			id:'0',
			text:'行业分类(主营 )主菜单',
			autoScroll:true,
	        expanded:true,
	        leaf:false,
			children:[]
		}),
		 resloader: loader,
		 split:true,
		 clickFn:function(node){
            Ext.getCmp('entIndustrySecond').setValue(node.text);
            Ext.getCmp('entSecondIndustry').setValue(node.id);
		},
	    animate : false,
	    useArrows : false,
	    border : false
	});
	var comboxWithTreeSecond = new Ext.form.ComboBox({
		id : 'entIndustrySecond', 
		xtype:'combo',
		store : new Ext.data.SimpleStore({
					fields : [],
					data : [[]]
				}),
		labelStyle: 'text-align:right;',
		editable : false,
		emptyText : '请选择...',
		fieldLabel : '行业分类（副营）',
		anchor : '90%',
		mode : 'local',
		resizable :false,
		forceSelection:true,
		name:'entSecondIndustryName',
		triggerAction : 'all',
		maxHeight : 390,
		tpl : "<tpl for='.'><div style='height:390px'><div id='addDeptTreeDiv_1second'></div></div></tpl>",
		allowBlank : false,
		onSelect : Ext.emptyFn,
		listeners:{
			'expand':function(combo){			
		  entSecondIndustryTreeForShow.render('addDeptTreeDiv_1second');
		},
			'collapse':function(combo){
			}
		}
	});
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var certInfogrid = new Ext.grid.ColumnModel([//gridtable中的列定义
	                                             rownum,
	                                             {header : 'ID',dataIndex : 'id',width : 100,sortable : true,hidden : true},
	                                             {header : '客户号',dataIndex : 'custId',width : 120,sortable : true,hidden : true},
	                                             {header : '证件类型',dataIndex : 'cretTypeOra',id : "cretType",width : 150,sortable : true},
	                                             {header : '证件号码',dataIndex : 'cretNo',width : 150,sortable : true},
	                                             {header : '证件登记日期',dataIndex : 'issueDate',width : 150,sortable : true},
	                                             {header : '证件到期日',dataIndex : 'lostDate',width : 150 ,sortable : true},
	                                             {header : '发证机构',dataIndex : 'tackInstn',width : 145,sortable : true},
	                                             {header : '年检标识',dataIndex : 'asAnnId',width : 145,sortable : true} 
	                                             ]);
   	var certInfoRecord = new Ext.data.Record.create([
 	                                               	{name : 'id',mapping : 'ID'},
 	                                               	{name : 'custId',mapping : 'CUST_ID'},
 	                                               	{name : 'cretType',mapping : 'CRET_TYPE'},
 	                                               	{name : 'cretTypeOra',mapping : 'CRET_TYPE_ORA'},
 	                                               	{name : 'cretNo',mapping : 'CRET_NO'},
 	                                               	{name : 'issueDate',mapping : 'ISSUE_DATE'},
 	                                               	{name : 'lostDate',mapping : 'LOST_DATE'},
 	                                               	{name : 'tackInstn',mapping : 'TACK_INSTN'},
 	                                               	{name : 'asAnnId',mapping : 'AS_ANN_ID'}
 	                                               	]);
	var certInfoReader = new Ext.data.JsonReader({//读取json数据的panel
       	totalProperty:'json.count',
       	root:'json.data'
       	},certInfoRecord);
 	var certInfoStore = new Ext.data.Store(
 			{
 				proxy:new Ext.data.HttpProxy({
 					url:basepath+'/certInfoQuery-Action.json',
 					failure : function(response){
 					var resultArray = Ext.util.JSON.decode(response.status);
 					if(resultArray == 403) {
 						Ext.Msg.alert('提示', response.responseText);
 					}
 				},
 				method:'GET'
 				}),
 				reader:certInfoReader
 			}
 	);
 	certInfoStore.load({
 		params : {
 			'custId' : custid
	}
 	});
 	var certBaseInfoGrid =  new Ext.grid.GridPanel({//主要证件信息列表数据grid
 		frame : true,
 		id : 'certBaseInfoGrid',
		height : 180,
 		store : certInfoStore,
 		loadMask : true,
 		cm  : certInfogrid,
 		loadMask : {
 		msg : '正在加载表格数据,请稍等...'
 	} 
 	});	

 	var comCustomerInfoRecord = new Ext.data.Record.create([
 	   	 	                                               	{name : 'custId',mapping : 'CUST_ID'},
 		 	                                               	{name : 'busiArea',mapping : 'BUSI_AREA'},
 		 	                                               	{name : 'busiCondition',mapping : 'BUSI_CONDITION'},
 		 	                                               	{name : 'busiIncome',mapping : 'BUSI_INCOME'},
 		 	                                               	{name : 'busiMain',mapping : 'BUSI_MAIN'},
 		 	                                               	{name : 'busiPositRights',mapping : 'BUSI_POSIT_RIGHTS'},
 		 	                                               	{name : 'busiRage',mapping : 'BUSI_RAGE'},
 		 	                                               	{name : 'creditGrade',mapping : 'CREDIT_GRADE'},
 		 	                                               	{name : 'creditGradeOra',mapping : 'CREDIT_GRADE_ORA'},
 		 	                                               	{name : 'custCnName',mapping : 'CUST_CN_NAME'},
 		 	                                               	{name : 'custEnName',mapping : 'CUST_EN_NAME'},
 		 	                                               	{name : 'entAssets',mapping : 'ENT_ASSETS'},
 		 	                                               	{name : 'entBelong',mapping : 'ENT_BELONG'},
 		 	                                               	{name : 'entBelongOra',mapping : 'ENT_BELONG_ORA'},
 		 	                                               	{name : 'entBusiType',mapping : 'ENT_BUSI_TYPE'},
 		 	                                               	{name : 'entCountry',mapping : 'ENT_COUNTRY'},
 		 	                                               	{name : 'entCustGrade',mapping : 'ENT_CUST_GRADE'},
 		 	                                               	{name : 'entCustGradeOra',mapping : 'ENT_CUST_GRADE_ORA'},
 		 	                                               	{name : 'entCustType',mapping : 'ENT_CUST_TYPE'},
 		 	                                               	{name : 'entCustTypeOra',mapping : 'ENT_CUST_TYPE_ORA'},
 		 	                                               	{name : 'entDebt',mapping : 'ENT_DEBT'},
 		 	                                               	{name : 'entEcomType',mapping : 'ENT_ECOM_TYPE'},
 		 	                                               	{name : 'entEcomTypeOra',mapping : 'ENT_ECOM_TYPE_ORA'},
 		 	                                               	{name : 'entEmail',mapping : 'ENT_EMAIL'},
 		 	                                               	{name : 'entEmployees',mapping : 'ENT_EMPLOYEES'},
 		 	                                               	{name : 'entEntAddr',mapping : 'ENT_ENT_ADDR'},
 		 	                                               	{name : 'entHoldingType',mapping : 'ENT_HOLDING_TYPE'},
 		 	                                               	{name : 'entHoldingTypeOra',mapping : 'ENT_HOLDING_TYPE_ORA'},
 		 	                                               	{name : 'entLinkfax',mapping : 'ENT_LINKFAX'},
 		 	                                               	{name : 'entLinkphone',mapping : 'ENT_LINKPHONE'},
 		 	                                               	{name : 'entMainIndustry',mapping : 'ENT_MAIN_INDUSTRY'},
 		 	                                               	{name : 'entMainIndustryName',mapping : 'ENT_MAIN_INDUSTRY_NAME'},
 		 	                                               	{name : 'entMainIndustryOra',mapping : 'ENT_MAIN_INDUSTRY_ORA'},
 		 	                                               	{name : 'entMaster',mapping : 'ENT_MASTER'},
 		 	                                               	{name : 'entMasterCretNum',mapping : 'ENT_MASTER_CRET_NUM'},
 		 	                                               	{name : 'entMasterCretTyp',mapping : 'ENT_MASTER_CRET_TYP'},
 		 	                                               	{name : 'entMasterCretTypOra',mapping : 'ENT_MASTER_CRET_TYP_ORA'},
 		 	                                               	{name : 'entOfficeAddr',mapping : 'ENT_OFFICE_ADDR'},
 		 	                                               	{name : 'entOfficePost',mapping : 'ENT_OFFICE_POST'},
 		 	                                               	{name : 'entProvince',mapping : 'ENT_PROVINCE'},
 		 	                                               	{name : 'entRegAddr',mapping : 'ENT_REG_ADDR'},
 		 	                                               	{name : 'entRegAmt',mapping : 'ENT_REG_AMT'},
 		 	                                               	{name : 'entRegCurr',mapping : 'ENT_REG_CURR'},
 		 	                                               	{name : 'entRegPost',mapping : 'ENT_REG_POST'},
 		 	                                               	{name : 'entScale',mapping : 'ENT_SCALE'},
 		 	                                               	{name : 'entScaleOra',mapping : 'ENT_SCALE_ORA'},
 		 	                                               	{name : 'entSecondIndustry',mapping : 'ENT_SECOND_INDUSTRY'},
 		 	                                               	{name : 'entSecondIndustryName',mapping : 'ENT_SECOND_INDUSTRY_NAME'},
 		 	                                               	{name : 'entSecondIndustryOra',mapping : 'ENT_SECOND_INDUSTRY'},
 		 	                                               	{name : 'entSetupDate',mapping : 'ENT_SETUP_DATE'},
 		 	                                               	{name : 'entSszbAmt',mapping : 'ENT_SSZB_AMT'},
 		 	                                               	{name : 'entSszbCurr',mapping : 'ENT_SSZB_CURR'},
 		 	                                               	{name : 'entWebsite',mapping : 'ENT_WEBSITE'},	 	                                               	
 		 	                                               	{name : 'groupFlag',mapping : 'GROUP_FLAG'},	 	                                              
 		 	                                               	{name : 'ifGeoponics',mapping : 'IF_GEOPONICS'},	 	                                             
 		 	                                               	{name : 'ifInout',mapping : 'IF_INOUT'},	 	                                               
 		 	                                               	{name : 'ifIpo',mapping : 'IF_IPO'},	 	                                            
 		 	                                               	{name : 'ifLimitIndustry',mapping : 'IF_LIMIT_INDUSTRY'},	 	                                             
 		 	                                               	{name : 'ifNetbank',mapping : 'IF_NETBANK'},	 	                                               	
 		 	                                               	{name : 'ifRelation',mapping : 'IF_RELATION'},	 	                                              
 		 	                                               	{name : 'ifSmallent',mapping : 'IF_SMALLENT'},	 	                                              
 		 	                                               	{name : 'remark',mapping : 'REMARK'},	 	                                              	
 		 	                                               	{name : 'updateDate',mapping : 'UPDATE_DATE'},	 	                                               
 		 	                                               	{name : 'updateOrg',mapping : 'UPDATE_ORG'},
 		 	                                               	{name : 'updateOrgName',mapping : 'UPDATE_ORG_NAME'},
 		 	                                               	{name : 'updateSys',mapping : 'UPDATE_SYS'},	 	                                              
 			                                              	{name : 'updateUser',mapping : 'UPDATE_USER'},
 			                                              	{name : 'updateUserName',mapping : 'UPDATE_USER_NAME'},
 			                                              	{name :  'certType',mapping : 'CERT_TYPE'},
 			                                              	{name : 'certNum',mapping : 'CERT_NUM'}
 		 	                                               	]);
 	var comCustomerInfoReader = new Ext.data.JsonReader({//读取json数据的panel
 		idProperties:'custId',
 		root : 'json.data'
 	},comCustomerInfoRecord);
 	
	var rownums = new Ext.grid.RowNumberer({//对公客户基本信息调整历史
		header : 'No.',
		width : 28
	});
	var comCusBaseHisCm = new Ext.grid.ColumnModel([rownums,//对公客户基本信息调整历史grid
	                                                {header : 'upId',dataIndex : 'upId',sortable :true,width : 100,hidden : true },
	                                                { header : '客户编号',dataIndex : 'custId', sortable : 100, width : 100,hidden : true },
	                                                { header : '修改项目', dataIndex : 'updateItem', sortable : true, width : 120 },
	                                                { header : '修改前内容', dataIndex : 'updateBeCont', sortable : true, width : 120 },
	                                                { header : '修改后内容', dataIndex : 'updateAfCont', sortable : true, width : 120},
	                                                { header : '修改人', dataIndex : 'userName',ortable : true, width : 100 },
	                                                { header : '修改时间',dataIndex : 'updateDate',sortable : true, width : 135}
	                                              ]);
  	var comCustBaseHisRecord = new Ext.data.Record.create([//对公客户基本信息调整历史record
  	                                                  {name:'upId',mapping:'UP_ID'},
  	                                                  {name:'custId',mapping:'CUST_ID'},
  	                                                  {name:'custName',mapping:'CUST_NAME'},
  	                                                  {name:'updateItem',mapping:'UPDATE_ITEM'},
  	                                                  {name:'updateBeCont',mapping:'UPDATE_BE_CONT'},
  	                                                  {name:'updateAfCont',mapping:'UPDATE_AF_CONT'},
  	                                                  {name:'updateUser',mapping:'UPDATE_USER'},
  	                                                  {name:'updateDate',mapping:'UPDATE_DATE'},
  	                                                  {name:'userName',mapping:'USER_NAME'}
  	                                              ]);
	var comCustBaseHisReader = new Ext.data.JsonReader({//对公客户借本信息历史调整读取json数据的panel
       	totalProperty:'json.count',
       	root:'json.data'
       	},comCustBaseHisRecord);
  	var comCustBaseHisStore = new Ext.data.Store(
           	{
           		proxy:new Ext.data.HttpProxy({
           		url:basepath+'/cusBaseAdujstHis-Action.json',
           		failure : function(response){
           			var resultArray = Ext.util.JSON.decode(response.status);
           			if(resultArray == 403) {
           				Ext.Msg.alert('提示', response.responseText);
           			}
           		},
           		method:'GET'
           		}),
           		reader:comCustBaseHisReader
           	}
           	);	
	var comCustAdjustHisGrid = new Ext.grid.EditorGridPanel({
		autoScroll : true,
		store : comCustBaseHisStore, 	// 数据存储
		cm : comCusBaseHisCm, 		// 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

	var comCustBaseHisWin = new Ext.Window({
		plain : true,
		layout : 'fit',
		resizable : true,
		draggable : true,
		closable : true,
		autoScroll : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		shadow : true,
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		width : 650,
		height : 400,
		buttonAlign : "center",
		title : '对公客户基本信息修改历史',
		items : comCustAdjustHisGrid,
		buttons : [{
			text : '返回',
			handler : function(){
			comCustBaseHisWin.hide();
			}
		}]
	});
	var comCustomerInfo = new Ext.FormPanel({//对公客户基本信息PANEL
 		title : '客户基本信息',
		reader : comCustomerInfoReader,
		frame : true,
		autoScroll : true,
		region:'center',
		buttonAlign:'center',
		items : [ {
				xtype : 'fieldset',
				title : '基本信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				anchor : '95%',
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',
						columnWidth : .50,
						labelWidth : 120,
						items : [
						         {	xtype:'textfield',fieldLabel:'客户号',name:'custId',labelStyle:'text-align:right;',readOnly:true,anchor:'90%'},
						         {  xtype : 'combo', store:certTypeStore,resizable : true,name : 'certType', fieldLabel :'证件类型',valueField : 'key',displayField : 'value',mode : 'local',editable : false,readOnly:true,
						         	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '90%'},
						         {	xtype:'textfield',fieldLabel:'客户英文名称',name : 'custEnName',labelStyle:'text-align:right;',anchor:'90%'},
						         {  xtype:'datefield',fieldLabel:'企业成立日期',name : 'entSetupDate',format : 'Y-m-d',labelStyle:'text-align:right;',anchor:'90%'},
						         {	xtype : 'combo', store:entHoldingTypeStore,resizable : true,name : 'entHoldingType',hiddenName : 'entHoldingType', fieldLabel :'客户控股类型',valueField : 'key',displayField : 'value',mode : 'local',editable : true,
						        	typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '90%'},
						        	comboxWithTree,
							     {	xtype:'textfield',fieldLabel:'行业分类（主营）码值',id:'entMainIndustry',name : 'entMainIndustry',labelStyle:'text-align:right;',anchor:'90%',hidden:true}

						        	]
					},{
						layout : 'form',
						columnWidth : .50,
						labelWidth : 120,
						items : [
						         { xtype:'textfield',fieldLabel:'客户中文名称',name:'custCnName',labelStyle:'text-align:right;',readOnly:true,anchor:'90%'},
						         { xtype:'textfield',fieldLabel:'证件号码',name:'certNum',labelStyle:'text-align:right;',readOnly:true,anchor:'90%'},
						         { xtype : 'combo', store:entScaleStore,resizable : true,name : 'entScale',hiddenName : 'entScale', fieldLabel :'企业规模',valueField : 'key',displayField : 'value',mode : 'local',editable : false,
							       typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',selectOnFocus : true,anchor : '90%'},
						         { xtype:'combo',store:entBelongStore,resizable : true,name : 'entBelong',hiddenName:'entBelong',fieldLabel:'企业隶属',valueField:'key',displayField:'value',mode:'local',editable:false,
						           typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
						         { xtype:'combo',store:entEcomTypeStore,resizable : true,name : 'entEcomType',hiddenName:'entEcomType',fieldLabel:'企业经济性质',valueField:'key',displayField:'value',mode:'local',editable:false,
							       typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
							       comboxWithTreeSecond,
							     { xtype:'textfield',fieldLabel:'行业分类（副营）码值',id:'entSecondIndustry',name:'entSecondIndustry',labelStyle:'text-align:right;',anchor:'90',hidden:true}  
							       ]
					}
					]
				}]
		},{
			xtype : 'fieldset',
			title : '证件信息',
			titleCollapse : false,
			collapsible : true,
			collapsed: true,
	        layout : 'fit',
	        anchor : '90%',
			items : [
			        certBaseInfoGrid
			]
		},{ 
			xtype : 'fieldset',
			title : '分类信息',
			titleCollapse : true,
			collapsible : true,
			collapsed: true,
			autoHeight : true,
			anchor : '95%',
			items : [{
				layout : 'column',
				items :[{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 120 ,
					items : [
					         { xtype:'combo',store:entCustTypeStore,resizable : true,name : 'entCustType',hiddenName:'entCustType',fieldLabel:'对公客户类型',valueField:'key',displayField:'value',mode:'local',editable:false,
						       typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
						     { xtype:'combo',store:creditGradeStore,resizable : true,name : 'creditGrade',hiddenName:'creditGrade',fieldLabel:'信用评级',valueField:'key',displayField:'value',mode:'local',editable:false,
			     	           typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},   
						     { xtype:'combo',store:ifFlagStore,resizable : true,name : 'ifIpo',hiddenName:'ifIpo',fieldLabel:'是否上市公司',valueField:'key',displayField:'value',mode:'local',editable:false,
					           typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'}, 
					         { xtype:'combo',store:ifFlagStore,resizable : true,name : 'ifInout',hiddenName:'ifInout',fieldLabel:'是否有进出口权',valueField:'key',displayField:'value',mode:'local',editable:false,
						       typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
				    	     { xtype:'combo',store:ifFlagStore,resizable : true,name : 'ifNetbank',hiddenName:'ifNetbank',fieldLabel:'是否网银签约客户',valueField:'key',displayField:'value',mode:'local',editable:false,
						       typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
				    	     { xtype:'combo',store:ifFlagStore,resizable : true,name : 'ifGeoponics',hiddenName:'ifGeoponics',fieldLabel:'是否涉农',valueField:'key',displayField:'value',mode:'local',editable:false,
						      typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'}
						       ]
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 120 ,
					items : [
					         { xtype:'combo',store:entBusiTypeStore,resizable : true,name : 'entBusiType',hiddenName:'entBusiType',fieldLabel:'客户业务类型',valueField:'key',displayField:'value',mode:'local',editable:false,
			   	        	   typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},  
			   	             { xtype:'combo',store:entCustGradeStore,resizable : true,name : 'entCustGrade',hiddenName:'entCustGrade',fieldLabel:'客户级别',valueField:'key',displayField:'value',mode:'local',editable:false,
						       typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'}, 
						     { xtype:'combo',store:ifFlagStore,resizable : true,name : 'ifSmallent',hiddenName:'ifSmallent',fieldLabel:'是否小企业',valueField:'key',displayField:'value',mode:'local',editable:false,
						       typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
						     { xtype:'combo',store:ifFlagStore,resizable : true,name : 'groupFlag',hiddenName:'groupFlag',fieldLabel:'集团客户标志',valueField:'key',displayField:'value',mode:'local',editable:false,
							   typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
			    		     { xtype:'combo',store:ifFlagStore,resizable : true,name : 'ifRelation',hiddenName:'ifRelation',fieldLabel:'是否我行关联方',valueField:'key',displayField:'value',mode:'local',editable:false,
						       typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
						     { xtype:'combo',store:ifFlagStore,resizable : true,name : 'ifLimitIndustry',hiddenName:'ifLimitIndustry',fieldLabel:'是否限制行业',valueField:'key',displayField:'value',mode:'local',editable:false,
						       typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'}
						       ]
				}]
			} ]
		},{
			xtype : 'fieldset',
			title : '联系信息',
			titleCollapse : true,
			collapsible : true,
			collapsed: true,
			autoHeight : true,
			anchor: '95%',
			items : [{
				layout : 'column',
				items : [{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 120,
					items : [
					         {	xtype:'textfield',fieldLabel:'法定代表人/负责人',name:'entMaster',labelStyle:'text-align:right;',anchor:'90%'},
					         {	xtype:'textfield',fieldLabel:'法定代表人证件号码',name:'entMasterCretNum',labelStyle:'text-align:right;',anchor:'90%'},
					         {	xtype:'numberfield',fieldLabel:'联系电话',name:'entLinkphone',labelStyle:'text-align:right;',anchor:'90%'},
					         {	xtype:'textfield',fieldLabel:'公司网址',name:'entWebsite',vtype:'url',labelStyle:'text-align:right;',anchor:'90%'},
				             {  xtype:'textfield',fieldLabel:'注册地址邮政编码',name:'entRegPost',maxLength:6,vtype:'postcode',labelStyle:'text-align:right;',anchor:'90%'},
					         {  xtype:'textfield',fieldLabel:'注册地省份、直辖市、自治区',name:'entProvince',labelStyle:'text-align:right;',anchor:'90%'},
					         {  xtype:'textarea',fieldLabel:'注册地址',name:'entRegAddr',labelStyle:'text-align:right;',anchor:'90%'}
					         ]
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 120,
					items : [
					         {  xtype:'combo',store:entMasterCretTypStore,resizable : true,name : 'entMasterCretTyp',hiddenName:'entMasterCretTyp',fieldLabel:'法定代表人证件类型',valueField:'key',displayField:'value',mode:'local',editable:false,
					       	    typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
					       	 {  xtype:'combo',store:entCountryStore,resizable : true,name : 'entCountry',hiddenName:'entCountry',fieldLabel:'所在国家(地区)',valueField:'key',displayField:'value',mode:'local',editable:false,
				                typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
				             {  xtype:'numberfield',fieldLabel:'传真电话',id:'entLinkfax',name:'entLinkfax',labelStyle:'text-align:right;',anchor:'90%'},
				             {  xtype:'textfield',fieldLabel:'公司EMIAL',name:'entEmail',vtype:'email',labelStyle:'text-align:right;',anchor:'90%'},
					         {	xtype:'textfield',fieldLabel:'办公地址邮政编码',name:'entOfficePost',maxLength:6,vtype:'postcode',labelStyle:'text-align:right;',anchor:'90%'},
				             {  xtype:'textarea',fieldLabel:'客户英文地址',name:'entEntAddr',vtype:'alphanum',labelStyle:'text-align:right;',anchor:'90%'},
				             {  xtype:'textarea',fieldLabel:'办公地址',name:'entOfficeAddr',labelStyle:'text-align:right;',anchor:'90%'}
				             ]
				}]
			}]
		},{
			xtype : 'fieldset',
			title : '经营信息',
			titleCollapse : true,
			collapsible : true,
			collapsed: true,
			autoHeight : true,
			anchor : '95%',
			items : [{
				layout : 'column',
				items : [{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 120,
					items : [
					         {  xtype:'combo',store:entRegCurrStore,resizable : true,name : 'entRegCurr',hiddenName:'entRegCurr',fieldLabel:'注册资本币别',valueField:'key',displayField:'value',mode:'local',editable:false,
				                typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
	                         {  xtype:'combo',store:entRegCurrStore,resizable : true,name : 'entSszbCurr',hiddenName:'entSszbCurr',fieldLabel:'实收资本币种',valueField:'key',displayField:'value',mode:'local',editable:false,
		                        typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
					         {  xtype:'numberfield',fieldLabel:'职工人数',id:'entEmployees',name:'entEmployees',labelStyle:'text-align:right;',anchor:'90%'},
					         {  xtype:'numberfield',fieldLabel:'负债总额',id:'entDebt',name:'entDebt',labelStyle:'text-align:right;',anchor:'90%'},
					         {  xtype:'numberfield',fieldLabel:'经营场地面积',id:'busiArea',name:'busiArea',labelStyle:'text-align:right;',anchor:'90%'},
					         {  xtype:'textfield',fieldLabel:'主营业务',name:'busiMain',labelStyle:'text-align:right;',anchor:'90%' },
					         {  xtype:'textfield',fieldLabel:'经营范围',name:'busiRage',labelStyle:'text-align:right;',anchor:'90%'}
					         ]		
				},{
					layout : 'form',
					columnWidth : .50,
					labelWidth : 120,
					items : [
					         {  xtype:'textfield',fieldLabel:'注册资本',id:'entRegAmt',name:'entRegAmt',labelStyle:'text-align:right;',anchor:'90%'},
					         {  xtype:'numberfield',fieldLabel:'实收资本',id:'entSszbAmt',name:'entSszbAmt',labelStyle:'text-align:right;',anchor:'90%'},
					         {  xtype:'numberfield',fieldLabel:'资产总额',id:'entAssets',name:'entAssets',labelStyle:'text-align:right;',anchor:'90%'},
					         {  xtype:'numberfield',fieldLabel:'销售收入',id:'busiIncome',name:'busiIncome',labelStyle:'text-align:right;',anchor:'90%'},
					         {  xtype:'combo',store:busiPositrightsStore,resizable : true,name : 'busiPositRights',hiddenName:'busiPositRights',fieldLabel:'经营场地所有权',valueField:'key',displayField:'value',mode:'local',editable:false,
					            typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'},
					         {  xtype:'combo',store:busiConditionStore,resizable : true,name : 'busiCondition',hiddenName:'busiCondition',fieldLabel:'经营状况',valueField:'key',displayField:'value',mode:'local',editable:false,
					            typeAhead:true,forceSelection:true,triggerAction:'all',emptyText:'请选择',labelStyle:'text-align:right;',selectOnFocus:true,anchor:'90%'}
					            ]
				}]
			}]
		},{
			xtype : 'fieldset',
			title : '其他信息',
			titleCollapse : true,
			collapsible : true,
			collapsed: true,
			autoHeight : true,
			anchor : '95%',
			items : [{
				layout : 'column',
	            items : [{
	            	layout : 'form',
					columnWidth : .50,
					labelWidth : 120,
					items : [
					         {  xtype:'textfield',fieldLabel:'最近更新系统',id:'updateSys',name:'updateSys',readOnly:true,labelStyle:'text-align:right;',anchor:'90%'},
					         {  xtype:'textfield',fieldLabel:'最近更新人',id:'updateUser',name:'updateUser',labelStyle:'text-align:right;',anchor:'90%',hidden:true},
					         {  xtype:'textfield',fieldLabel:'最近更新人',name:'updateUserName',readOnly:true,labelStyle:'text-align:right;',anchor:'90%'},
					         {  xtype:'textarea',fieldLabel:'备注',name:'remark',labelStyle:'text-align:right;',anchor:'90%'}
					         ]
	            },{
	            	layout : 'form',
	            	columnWidth : .50,
	            	labelWidth : 120,	
	            	items : [
	            	         {  xtype:'textfield',fieldLabel:'最近更新机构',id:'updateOrg',name:'updateOrg',labelStyle:'text-align:right;',anchor:'90%',hidden:true},
	            	         {  xtype:'textfield',fieldLabel:'最近更新机构',name:'updateOrgName',readOnly:true,labelStyle:'text-align:right;',anchor:'90%'},
	            	         {  xtype:'datefield',fieldLabel:'最近更新日期',id:'updateDate',name:'updateDate',readOnly:true,format:'Y-m-d',labelStyle:'text-align:right;',anchor:'90%'}
					         ]
	            }]
			}]
		}
		],
		buttons : [{
		 	text : '保存',
			handler : function(){
			if (!comCustomerInfo.getForm().isValid()) {
				Ext.Msg.alert("系统提示信息", "输入有误或存在漏输项，请重新输入!");
				return false;
				}
			 Ext.getCmp('updateUser').setValue(__userId);
			 Ext.getCmp('updateSys').setValue('CRM');
			 Ext.getCmp('updateOrg').setValue(__units);
			 Ext.getCmp('updateDate').setValue(new Date());
		 		json2 = comCustomerInfo.form.getValues(false);//在提交FORM前若该字段为空，切数据库类型为bigDecimal，则将该字段的值设为'0',json2为即将保存的form上的值
		 	 	if(json2.busiIncome == ""){ Ext.getCmp('busiIncome').setValue(0);}
		 	 	if(json2.entAssets == ""){ Ext.getCmp('entAssets').setValue(0);}
		 	 	if(json2.entDebt == ""){ Ext.getCmp('entDebt').setValue(0);}
		 	 	if(json2.entRegAmt == ""){ Ext.getCmp('entRegAmt').setValue(0);}
		 	 	if(json2.entSszbAmt == ""){ Ext.getCmp('entSszbAmt').setValue(0);}
		 		json1 = comCustomerInfo.reader.jsonData.json.data[0];
				var comModel = [];
		 		for(var key in json2){
		 			if(key != 'certType'){
		 			var ccbhModel = {};
		 			comCustomerInfoRecord.prototype.fields.items;
					Ext.each(comCustomerInfoRecord.prototype.fields.items,function(a){
						if(a.name==key){
							if(json2[key] != json1[a.mapping]){
								if(comCustomerInfo.getForm().findField(a.name).getXType()=='numberfield'){
									var t = parseFloat(json1[a.mapping]); 
									if(json2[key]!= t){
										ccbhModel.custId = custid;
										ccbhModel.updateBeCont = json1[key];
										ccbhModel.updateAfCont = json2[a.name];
										ccbhModel.updateItem = comCustomerInfo.getForm().findField(key).fieldLabel;
										comModel.push(ccbhModel);	
									}
								}else{
										ccbhModel.custId = custid;
										ccbhModel.updateBeCont = json1[a.mapping];
										ccbhModel.updateAfCont = json2[key];
										ccbhModel.updateItem = comCustomerInfo.getForm().findField(key).fieldLabel;
										comModel.push(ccbhModel);
									}
							}
							return;
						}
					});
		 			}
		 		}
			Ext.Ajax.request( {
				url : basepath + '/ComCustomerBaseInfo-action.json',
				method : 'POST',
				params : comCustomerInfo.getForm().getFieldValues(),
				success : checkResult,
				failure: checkResult
			});
			
			function checkResult(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				var resultError = response.responseText;
				if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
					Ext.Msg.alert('提示', '操作成功');
					comCustomerInfo.getForm().load({
						restful : true,
						url : basepath + '/ComCustomerBaseQuery-Action.json',
						method : 'GET',
						params : {
							'custId' : custid
					}
					});
					Ext.Ajax.request( {
						url : basepath + '/ComCustomerBaseInfo-action!save.json',
						method : 'POST',
					     params : {
 			                'comModel':Ext.encode(comModel)
								}
					});
				} else {
					if(resultArray == 403){
						Ext.Msg.alert('提示', response.responseText);
					}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
					}
				};
			}
		}	
		},'-',{
			text:'修改历史',
			handler:function(){
			comCustBaseHisWin.show();
			comCustBaseHisStore.load({
	    		params : {
	    		'custId':custid
			}
			});
		}
		}]
	});

	comCustomerInfo.getForm().load({//comCustomerInfo FormPanel加载数据
		restful : true,
		url : basepath + '/ComCustomerBaseQuery-Action.json',
		method : 'GET',
		params : {
			'custId' : custid
		}
	});
	var viewport_center = new Ext.Panel({
		renderTo:'viewport_center',
		height:document.body.scrollHeight-30,
		width : document.body.clientWidth-223,
		layout:'fit',
		autoScroll:true,
		items: [comCustomerInfo] 
	});
		

	
});