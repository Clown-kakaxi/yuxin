/**
 * @description 对私非授信信息审批展现界面
 * @since ?
 * @update 20140923
 * @modify 20160122
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	var custId = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;
	
//-------------------------------------------客户变更信息表begin----------------------------------------------
	var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({
        	url:basepath+'/perSxInfoReview.json',
        	method:'GET'
        }),
        reader: new Ext.data.JsonReader({
        	totalProperty : 'json.count',
        	root:'json.data'
        }, [
			{name:'custId',mapping:'CUST_ID'},
			{name:'CORE_NO'},
            {name:'updateItem',mapping:'UPDATE_ITEM'},
            {name:'updateBeCont',mapping:'UPDATE_BE_CONT'},
            {name:'updateAfCont',mapping:'UPDATE_AF_CONT'},
            {name:'updateUser',mapping:'UPDATE_USER'},
            {name:'userName',mapping:'USER_NAME'},
            {name:'updateDate',mapping:'UPDATE_DATE'},
            {name:'UPDATE_FLAG'},
            {name:'APPR_FLAG'}
		])
	});
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 35
	});
	
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum, 
     	{ header : '客户编号',dataIndex : 'custId', sortable : 100, width : 100},
     	{ header : '核心客户号',dataIndex : 'CORE_NO', sortable : 100, width : 100},
     	{ header : '变更项目', dataIndex : 'updateItem', sortable : true, width : 120 },
        { header : '变更前内容', dataIndex : 'updateBeCont', sortable : true, width : 120 },
        { header : '变更后内容', dataIndex : 'updateAfCont', sortable : true, width : 120},
        { header : '修改人', dataIndex : 'userName',sortable : true, width : 100 },
        { header : '修改时间',dataIndex : 'updateDate',sortable : true, width : 135},
        { header : '页面',dataIndex : 'UPDATE_FLAG',sortable:true,width : 150,renderer:function(val){
        	var tempflag = val?val.substr(val.length-4,1):'0';
			switch(Number(tempflag)){
				case 0:
					return '第一屏';
					break;
				case 1:
					return '第二屏地址';
					break;
				case 2:
					return '第二屏联系人';
					break;
				case 3:
					return '第二屏联系信息';
					break;
				case 4:
					return '第三屏';
					break;
			}
        }},
        { header : '状态',dataIndex : 'APPR_FLAG',sortable:true,width : 100,hidden:!curNodeObj.approvalHistoryFlag,renderer:function(val){
        	if(val == '1'){
        		return '同意';
        	}else if(val == '2'){
        		return '否决/撤办';
        	}else{
        		return '复核中';
        	}
        }}
	]);
	// 表格实例
	var rowClassFlag = false;
	var rowHisFlag = "";
	var grid = new Ext.grid.GridPanel({
		id:'viewgrid',
		title:'客户变更信息',
		frame : true,
		height:180,
		autoScroll : true,
		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store : store, // 数据存储
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		viewConfig : {
			getRowClass : function(record,rowIndex,rowParams,store){
				var tempflag = record.data.UPDATE_FLAG?record.data.UPDATE_FLAG.substr(record.data.UPDATE_FLAG.length-4,4):'0000';
				if(rowHisFlag != tempflag){
					rowClassFlag = !rowClassFlag;
					rowHisFlag = tempflag;
				}
				//根据是否修改状态修改背景颜色  
				if(rowClassFlag){
				  	return 'my_row_set_blue';
			  	}else{
			  		return 'my_row_set_red';
			  	}
			},
			forceFit:false,
			autoScroll:true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	//加载业务数据
	store.load({
		params:{
			'instanceId':instanceid
		}
	});
//-------------------------------------------客户变更信息表end--------------------------------
	
	
//-------------------------------------------客户复核前信息begin----------------------------------
	/**
	 * 数据字典store查询
	 */
	//证件类型
	var indentStore =  new Ext.data.Store({
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	indentStore.load();
	var indent00Store =  new Ext.data.Store({
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000361'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	indent00Store.load();
	var indent01Store =  new Ext.data.Store({
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000362'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	indent01Store.load();
	//国籍
	var conStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000025'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	conStore.load();
	//境内境外标识
	var inOrOutStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000022'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	inOrOutStore.load();
	//VIP标识
	var vipFlagStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000291'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	vipFlagStore.load();
	// 综合对账单发送标志
	var isSendEcomstatFlagStore = new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000297'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	isSendEcomstatFlagStore.load();
	//是否传真交易指示标志
	var isFaxStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000296'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	isFaxStore.load();
	//关联人类型
	var staffinStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000306'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	staffinStore.load();
	//零售客户类型
	var perTypeStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000041'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	perTypeStore.load();
	//性别
	var sexStore = new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000016'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	sexStore.load();
	//地区代码
	var dqStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000001'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	dqStore.load();
	//AR客户标识
	var arCustFlagStore =  new Ext.data.Store({
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=XD000277'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	arCustFlagStore.load();
	//自贸区类型
	var orgSubTypeStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000304'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	orgSubTypeStore.load();
	//反洗钱等级
	var riskgradeStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=FXQ_RISK_LEVEL'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	riskgradeStore.load();
	//是否美国纳税人标识
	var usaTaxStore  = new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000294'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	usaTaxStore.load();
	//是否联名户
	var jointCustTypeStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000307'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	jointCustTypeStore.load();
	//CUSTNAME OR ID/REFNO CHANGE BY
	var custnmIdentModifiedStore  = new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=CUSTNM_IDENT_MODIFIED_FLAG'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	custnmIdentModifiedStore.load();
	//是否
	var ifStore  = new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=IF_FLAG'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	ifStore.load();
	/**
	 * 第一屏
	 */
	var fsxbaseInfo = new Ext.form.FormPanel({
		 frame : true,
		 autoScroll : true,
		 title : '第一屏',
		 labelWidth : 140,
		 buttonAlign : "center",
		 items:[{
			layout : 'column',
		    items:[{
		        	columnWidth : .5,
					layout : 'form',
					items :[
						{xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '<font color="red">*</font>中文名',name : 'CUST_NAME',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '<font color="red">*</font>证件类型1',store : indent00Store,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly'},
			            {xtype : 'combo',name : 'IDENT_TYPE1',hiddenName : 'IDENT_TYPE1',fieldLabel : '证件类型2',store : indent01Store,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
			            {xtype : 'datefield',name : 'IDENT_EXPIRED_DATE',fieldLabel : '证件1失效日期',format:'Y-m-d',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'datefield',name : 'BIRTHDAY',fieldLabel : '<font color="red">*</font>个人生日',format:'Y-m-d',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'CITIZENSHIP',hiddenName : 'CITIZENSHIP',fieldLabel : '国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
			            {xtype : 'combo',name : 'USA_TAX_FLAG',hiddenName : 'USA_TAX_FLAG',fieldLabel : '<font color="red"></font>是否美国纳税人',store : usaTaxStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:true},
			            {xtype : 'combo',name : 'INOUT_FLAG',hiddenName : 'INOUT_FLAG',fieldLabel : '<font color="red">*</font>境内/外标志',store : inOrOutStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'VIP_FLAG',hiddenName : 'VIP_FLAG',fieldLabel : '<font color="red">*</font>VIP标志',store : vipFlagStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',name : 'BASIC_ACCT_BANK_NAME',fieldLabel : '开户行',anchor : '90%',disabled:true,cls:'x-readOnly'},
						{xtype : 'textfield',name : 'MGR_NAME',fieldLabel : '客户经理',anchor : '90%',disabled:true,cls:'x-readOnly'},
						{xtype : 'combo',name : 'IS_SEND_ECOMSTAT_FLAG',hiddenName : 'IS_SEND_ECOMSTAT_FLAG',fieldLabel : '<font color="red">*</font>综合对账单发送标志',store : isSendEcomstatFlagStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'IS_FAX_TRANS_CUST',hiddenName : 'IS_FAX_TRANS_CUST',fieldLabel : '<font color="red">*</font>是否传真交易指示标志',store : isFaxStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'STAFFIN',hiddenName : 'STAFFIN',fieldLabel : '关联人类型',store : staffinStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '代理人户名',name : 'AGENT_NAME',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:40},
						{xtype : 'combo',name : 'AGENT_NATION_CODE',hiddenName : 'AGENT_NATION_CODE',fieldLabel : '代理人国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'AGENT_IDENT_TYPE',hiddenName : 'AGENT_IDENT_TYPE',fieldLabel : '代理人证件类型',store : indentStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '代理人联系电话',name : 'AGENT_TEL',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:20},
						{xtype : 'userchoose',name:'RECOMMENDER_NAME',hiddenName : 'RECOMMENDER',fieldLabel : '推荐人',searchType:'ALLORG',singleSelect:true,disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:20}
			       ]
		      	},{
		       		columnWidth : .5,
				    layout : 'form',
				    items :[
				    	{xtype : 'combo',name : 'PER_CUST_TYPE',hiddenName : 'PER_CUST_TYPE',fieldLabel : '个人客户类型',store : perTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly',hidden:true},
				    	{xtype : 'combo',name : 'GENDER',hiddenName : 'GENDER',fieldLabel : '<font color="red">*</font>性别',store : sexStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly'},
				    	{xtype : 'textfield',name : 'EN_NAME',fieldLabel : '<font color="red">*</font>英文名',anchor : '90%',disabled:true,cls:'x-readOnly',maxLength:100},
				    	{xtype : 'textfield',fieldLabel : '<font color="red">*</font>证件号码1',name : 'IDENT_NO',anchor : '90%',disabled:true,cls:'x-readOnly',maxLength:100},
				    	{xtype : 'textfield',fieldLabel : '证件号码2',name : 'IDENT_NO1',anchor : '90%',disabled:true,cls:'x-readOnly',maxLength:100},
				    	{xtype : 'datefield',name : 'IDENT_EXPIRED_DATE1',fieldLabel : '证件2失效日期',format:'Y-m-d',disabled:true,cls:'x-readOnly',anchor : '90%'},
			            {xtype : 'textfield',fieldLabel : '出生地',name : 'BIRTHLOCALE',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:25},
			            {xtype : 'combo',name : 'AREA_CODE',hiddenName : 'AREA_CODE',fieldLabel : '地区代码',store : dqStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : 'USTIN',name : 'USA_TAX_IDEN_NO',disabled:true,cls:'x-readOnly',anchor : '90%'}, 
			            {xtype : 'datefield',name : 'BASIC_ACCT_OPEN_DATE',fieldLabel : '<font color="red">*</font>客户资料开立日',format:'Y-m-d',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'AR_CUST_FLAG',hiddenName : 'AR_CUST_FLAG',fieldLabel : '是否AR客户标志',store : arCustFlagStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'ORG_SUB_TYPE',hiddenName : 'ORG_SUB_TYPE',fieldLabel : '<font color=red>*</font>特殊监管区',store : orgSubTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'RISK_CUST_GRADE',hiddenName : 'RISK_CUST_GRADE',fieldLabel : '<font color=red></font>反洗钱风险等级',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly'},
						{xtype : 'datefield',name : 'EFFECT_DATE',fieldLabel : '客户经理修改生效日',format:'Y-m-d',anchor : '90%',cls:'x-readOnly',disabled:true,allowBank:false,hidden:true},
						{xtype : 'textfield',fieldLabel : '电子邮件地址',name : 'EMAIL',vtype:'email',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:40},
				        {xtype : 'textfield',fieldLabel : '传真号码',name : 'UNIT_FEX',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:20},
				        {xtype : 'combo',name : 'RISK_NATION_CODE',hiddenName : 'RISK_NATION_CODE',fieldLabel : '国别风险国别代码',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : 'Swift Address',name : 'SWIFT',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:20},
						{xtype : 'combo',name : 'JOINT_CUST_TYPE',hiddenName : 'JOINT_CUST_TYPE',fieldLabel : '联名户',store : jointCustTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '代理人证件号码',name : 'AGENT_IDENT_NO',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:40},
						{xtype : 'combo',name : 'CUSTNM_IDENT_MODIFIED_FLAG',hiddenName : 'CUSTNM_IDENT_MODIFIED_FLAG',fieldLabel : 'CUSTNAME OR ID/REFNO CHANGE BY',store : custnmIdentModifiedStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'IF_SIGN_SERVICE',hiddenName : 'IF_SIGN_SERVICE',fieldLabel : '已补签个人开户及综合服务协议书',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '已补签个人开户及综合服务协议书客户编号',disabled:true,cls:'x-readOnly',name : 'SERVICE_CUST_ID',anchor : '90%',maxLength:40,hidden:true}
				]
			}]
		},{
			layout: 'form',
			items:[
				{xtype : 'textarea',fieldLabel : '备注',name : 'REMARK',maxLength: 100,disabled:true,cls:'x-readOnly',anchor : '95%'},
			    {xtype : 'textfield',fieldLabel : '开户行',name : 'BASIC_ACCT_BANK_NO',hidden:true},
				{xtype : 'textfield',fieldLabel : '证件类型1ID',name : 'IDENT_ID',hidden:true},
				{xtype : 'textfield',fieldLabel : '证件类型2ID',name : 'IDENT_ID1',hidden:true},
				{xtype : 'textfield',fieldLabel : '归属客户经理表ID',name : 'MGR_KEY_ID',hidden:true},
				{xtype : 'textfield',fieldLabel : '重要标志ID',name : 'KEYFLAG_CUST_ID',hidden:true},
				{xtype : 'textfield',fieldLabel : '风险等级ID',name : 'RISK_GRADE_ID',hidden:true},
				{xtype : 'textfield',fieldLabel : '贵宾卡等级ID',name : 'VIP_GRADE_ID',hidden:true},
				{xtype : 'textfield',fieldLabel : '代理人ID',name : 'AGENT_ID',hidden:true}
			]
		}],
		buttons:[{
			 text: '下一屏',
			 handler: function(){
				fsxPerInfo.setActiveTab(1);
			 }
		}]
	});
	var fsxStore = new Ext.data.Store({
		restful:true,
		proxy : new Ext.data.HttpProxy({
			url:basepath+'/dealWithFsx!queryPerfsx.json',
			method:'get'
		}),
		reader: new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root:'json.data'
		}, [
			'CUST_ID','CUST_NAME','CUST_TYPE','UNIT_FEX',
			'IDENT_ID','IDENT_TYPE','IDENT_NO','IDENT_EXPIRED_DATE',
			'IDENT_ID1','IDENT_TYPE1','IDENT_NO1','IDENT_EXPIRED_DATE1',
			
			'STAFFIN','JOINT_CUST_TYPE','SWIFT','REMARK',
			'RECOMMENDER','RECOMMENDER_NAME','ORG_SUB_TYPE','EN_NAME','INOUT_FLAG','AR_CUST_FLAG','RISK_NATION_CODE','PER_CUST_TYPE','GENDER','BIRTHDAY','CITIZENSHIP','AREA_CODE','USA_TAX_IDEN_NO','BIRTHLOCALE','EMAIL','VIP_FLAG',
			'KEYFLAG_CUST_ID','IS_SEND_ECOMSTAT_FLAG','USA_TAX_FLAG','IS_FAX_TRANS_CUST',
			'BASIC_ACCT_BANK_NO','BASIC_ACCT_BANK_NAME','BASIC_ACCT_OPEN_DATE',
			'MGR_KEY_ID','MGR_ID','MGR_NAME','EFFECT_DATE',
			'VIP_GRADE_ID','VIP_CUST_GRADE','RISK_GRADE_ID','RISK_CUST_GRADE',
			'AGENT_ID','AGENT_NAME','AGENT_IDENT_TYPE','AGENT_IDENT_NO','AGENT_NATION_CODE','AGENT_TEL','AGENT_CUST_ID','IF_SIGN_SERVICE','SERVICE_CUST_ID'
		])
	});
	fsxStore.load({
		params : {
			custId : custId
		},
		callback:function(){
			if(fsxStore.getCount()!=0){
				fsxbaseInfo.getForm().loadRecord(fsxStore.getAt(0));
			}
		}
	});
	
	/**
	 * 地址类型
	 */
	var addrCustInfoRowNumber = new Ext.grid.RowNumberer({
	    header:'NO.',
	    width:35
	});
	// create the data columnModel
	var addrCustInfoCm = new Ext.grid.ColumnModel([
	    addrCustInfoRowNumber,
	    {dataIndex:'ADDR_TYPE_ORA',header:'地址类型',width : 120,sortable : true},
	    {dataIndex:'ADDR',header:'详细地址',width : 350,sortable : true},
	    {dataIndex:'ZIPCODE',header:'邮政编码',width : 100,sortable : true},
	    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
	]);
	// create the data record
	var addrCustInfoRecord = new Ext.data.Record.create([
	    {name:'ADDR_ID'},
		{name:'CUST_ID'},
		{name:'ADDR_TYPE'},
		{name:'ADDR_TYPE_ORA'},
		{name:'ADDR'},
		{name:'ZIPCODE'},
		{name:'LAST_UPDATE_SYS'},
		{name:'LAST_UPDATE_USER'},
		{name:'LAST_UPDATE_TM'},
		{name:'IS_ADD_FLAG'}
	]);
	// create the data store
	var addrCustInfoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/dealWithFsx!queryAddr.json',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },addrCustInfoRecord)
	    
	});
	// create the addrGridPanel
	var addrGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: addrCustInfoStore,
	    cm : addrCustInfoCm,
//	    viewConfig : {
//			getRowClass : function(record,rowIndex,rowParams,store){
//				//根据是否修改状态修改背景颜色  
//				if(record.data.IS_ADD_FLAG=='0'){//修改过
//				  	return 'my_row_set_blue';
//			  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
//			  		return 'my_row_set_red';
//			  	}
//			}
//		},
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	addrCustInfoStore.load({
		params : {
			custId : custId
		}
	});
	
	/**
	 * 联系人信息
	 */
	var contactPersonRowNumber = new Ext.grid.RowNumberer({
	    header:'NO.',
	    width:35
	});
	// create the data columnModel
	var contactPersonCm = new Ext.grid.ColumnModel([
	    contactPersonRowNumber,
	    {dataIndex:'LINKMAN_TYPE_ORA',header:'联系人类型',width : 100,sortable : true},
	    {dataIndex:'LINKMAN_NAME',header:'姓名',width : 100,sortable : true},
	    {dataIndex:'IDENT_TYPE_ORA',header:'证件类型',width : 100,sortable : true},
	    {dataIndex:'IDENT_NO',header:'证件号码',width : 100,sortable : true},
	    {dataIndex:'GENDER_ORA',header:'性别',width : 100,sortable : true},
	    {dataIndex:'BIRTHDAY',header:'出生日期',width : 100,sortable : true},
	    {dataIndex:'TEL',header:'联系电话',width : 100,sortable : true},
	    {dataIndex:'TEL2',header:'联系电话2',width : 100,sortable : true},
	    {dataIndex:'MOBILE',header:'手机号码',width : 100,sortable : true},
	    {dataIndex:'MOBILE2',header:'手机号码2',width : 100,sortable : true},
	    {dataIndex:'EMAIL',header:'邮件',width : 100,sortable : true},
	    {dataIndex:'ADDRESS',header:'地址',width : 100,sortable : true},
	    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
	]);
	// create the data record
	var contactPersonRecord = new Ext.data.Record.create([
	    {name : 'LINKMAN_ID'},
		{name : 'CUST_ID'},
		{name : 'LINKMAN_TYPE'},
		{name : 'LINKMAN_TYPE_ORA'},
		{name : 'LINKMAN_NAME'}, 
		{name : 'IDENT_TYPE'},
		{name : 'IDENT_TYPE_ORA'},
		{name : 'IDENT_NO'},
		{name : 'TEL'},
		{name : 'TEL2'},
		{name : 'MOBILE'},
		{name : 'MOBILE2'},
		{name : 'EMAIL'},
		{name : 'ADDRESS'},
		{name : 'GENDER'},
		{name : 'GENDER_ORA'},
		{name : 'BIRTHDAY'},
		{name : 'LAST_UPDATE_SYS'},
		{name : 'LAST_UPDATE_USER'},
		{name : 'LAST_UPDATE_TM'}
	]);
	// create the data store
	var contactPersonStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath+'/dealWithFsx!queryPerContactPerson.json',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },contactPersonRecord)
	});
	// create the contactPersonGrid
	var contactPersonGrid = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: contactPersonStore,
	    cm : contactPersonCm,
//	    viewConfig : {
//			getRowClass : function(record,rowIndex,rowParams,store){
//				//根据是否修改状态修改背景颜色  
//				if(record.data.IS_ADD_FLAG=='0'){//修改过
//				  	return 'my_row_set_blue';
//			  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
//			  		return 'my_row_set_red';
//			  	}
//			}
//		},
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	contactPersonStore.load({
		params : {
			custId : custId
		}
	});

	/**
	 * 联系信息
	 */
	var contactInfoRowNumber = new Ext.grid.RowNumberer({
	    header:'NO.',
	    width:35
	});
	// create the data columnModel
	var contactInfoCm = new Ext.grid.ColumnModel([
	    contactInfoRowNumber,
	    {dataIndex:'CONTMETH_TYPE_ORA',header:'联系方式类型',width : 150,sortable : true},
	    {dataIndex:'CONTMETH_INFO',header : '联系方式内容',width : 100,sortable : true}, 
	    //{dataIndex:'IS_PRIORI_ORA',header:'是否首选',width : 100,sortable : true},
	    {dataIndex:'REMARK',header:'备注',width : 100,sortable : true},
	    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
	]);
	// create the data record
	var contactInfoRecord = new Ext.data.Record.create([
	    {name:'CONTMETH_ID'},
		{name:'CUST_ID'},
		{name:'CONTMETH_TYPE'},
		{name:'CONTMETH_TYPE_ORA'},
		{name:'IS_PRIORI'},
		{name:'IS_PRIORI_ORA'},
		{name:'CONTMETH_INFO'},
		{name:'CONTMETH_SEQ'},
		{name:'REMARK'},
		{name:'STAT'},
		{name:'LAST_UPDATE_SYS'}
	]);
	// create the data store
	var contactInfoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/acrmFCiContmethInfo.json?check=1',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },contactInfoRecord)
	});
	// create the addrGridPanel
	var contactGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: contactInfoStore,
	    cm : contactInfoCm,
//	    viewConfig : {
//			getRowClass : function(record,rowIndex,rowParams,store){
//				//根据是否修改状态修改背景颜色  
//				if(record.data.IS_ADD_FLAG=='0'){//修改过
//				  	return 'my_row_set_blue';
//			  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
//			  		return 'my_row_set_red';
//			  	}
//			}
//		},
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	contactInfoStore.load({
		params : {
			custId : custId
		}
	});
	
	
	/**
	 * 第二屏
	 */
	var fsxperLists = new Ext.Panel({
		frame : true,
		title : '第二屏',
		autoScroll : true,
		buttonAlign : "center",
		items : [{
			xtype : 'fieldset',
			title : '地址信息',
			items : [addrGridPanel]
		},{
			xtype : 'fieldset',
			title : '联系人信息',
			items : [contactPersonGrid]
		},{
			xtype : 'fieldset',
			title : '联系信息',
			items : [contactGridPanel]
		}],
		buttons:[{
			text:'上一屏',
			handler:function(){
				fsxPerInfo.setActiveTab(0);
			}
		}]
	});
	
	var fsxPerInfo = new Ext.TabPanel({
		activeItem : 0,
		defaults:{autoHeight: true},
		items:[fsxbaseInfo,fsxperLists]
	});
	
	var panel = new Ext.Panel({
		title:'客户复核前信息',
		autoHeight:true,
		autoWeight:true,
		items:[fsxPerInfo]
	});
	
//-------------------------------------------客户复核前信息end------------------------------------
  
//-------------------------------------------代办任务全局信息展示begin--------------------------------	
	var bussFieldSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    autoScroll : true,
	    title: '流程业务信息',
	    items:[grid,panel]
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
	var view = new Ext.Panel({
		renderTo : 'viewEChian',
		frame : true,
		width : document.body.scrollWidth,
		height : document.body.scrollHeight-40,
		autoScroll : true,
		layout : 'form',
		items : [bussFieldSetGrid,EchainPanel]
	});
	
//-------------------------------------------代办任务全局信息展示end--------------------------------
});
