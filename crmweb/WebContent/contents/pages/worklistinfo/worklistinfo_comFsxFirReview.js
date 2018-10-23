/**
 * @description 对公非授信信息审批展现界面
 * @since ?
 * @update 20140924
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	var instanceid = curNodeObj.instanceid;
	var custId = instanceid.split('_')[1];
	var nodeid = curNodeObj.nodeid;

	/**
	 * 是否台资企业
	 * add by liuming
	 */
	var isTaiStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000182'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	isTaiStore.load();
	
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
		width : 28
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
				case 5:
					return '第二屏股东';
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
	store.load({
		params:{
			'instanceId':instanceid
		}
	});
//-------------------------------------------客户变更信息表end--------------------------------
	
//-------------------------------------------客户复核前信息begin----------------------------------
////////////////////////////////////////////////////////////////////////////////////////////////
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
	//企业类型
	var lncustpStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000364'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	lncustpStore.load();
	//客户类型
	var typeStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	typeStore.load();
	//合资类型
	var orgTypeStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000054'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	orgTypeStore.load();
	//注册资金币种
	var moneyStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000027'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	moneyStore.load();
	//公司规模
	var entScaleRhStore =  new Ext.data.Store({
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=XD000018'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	entScaleRhStore.load();
	//行业类别
	var incllTypeStore =  new Ext.data.Store( {
		restful : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000286'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	incllTypeStore.load();
	
	/**
	 * 非授信查询 第一屏
	 */
	var fsxComStore = new Ext.data.Store({
		restful:true,
		proxy : new Ext.data.HttpProxy({
			url:basepath+'/dealWithFsx!queryComfsx.json',
			method:'get'
		}),
		reader: new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root:'json.data'
		}, [
			'CUST_ID','CUST_NAME','CUST_TYPE','ORG_FEX',
			'IDENT_ID','IDENT_TYPE','IDENT_NO','IDENT_EXPIRED_DATE',
			'IDENT_ID1','IDENT_TYPE1','IDENT_NO1','IDENT_EXPIRED_DATE1',
			'IDENT_ID2','IDENT_TYPE2','IDENT_NO2','IDENT_EXPIRED_DATE2',
			'RECOMMENDER','RECOMMENDER_NAME','EN_NAME','INOUT_FLAG','AR_CUST_FLAG','RISK_NATION_CODE','BUILD_DATE','ORG_TYPE','USA_TAX_FLAG','HQ_NATION_CODE','STAFFIN','SWIFT','JOINT_CUST_TYPE','ORG_SUB_TYPE',
			'ORG_EMAIL','BASIC_ACCT_BANK_NO','BASIC_ACCT_BANK_NAME','BASIC_ACCT_OPEN_DATE','IN_CLL_TYPE','IN_CLL_TYPE_NAME','LNCUSTP','ORG_CUST_TYPE','VIP_FLAG','CREDIT_CODE','NATION_CODE','AREA_CODE',
			'KEYFLAG_CUST_ID','IS_SEND_ECOMSTAT_FLAG','IS_FAX_TRANS_CUST',
			'MGR_KEY_ID','MGR_ID','MGR_NAME','EFFECT_DATE',
			'VIP_GRADE_ID','VIP_CUST_GRADE','RISK_GRADE_ID','RISK_CUST_GRADE',
			'AGENT_ID','AGENT_NAME','AGENT_IDENT_TYPE','AGENT_IDENT_NO','AGENT_NATION_CODE','AGENT_TEL','AGENT_CUST_ID'
			//add by liuming 20170629
			,'IS_TAIWAN_CORP'
		])
	});
	/**
	 * 查询非授信客户信息 第一屏
	 */
	//基本信息部分  第一屏
	var fsxCombaseInfo = new Ext.form.FormPanel({
		 frame : true,
		 autoScroll : true,
		 title : '第一屏',
		 labelWidth : 140,
		 buttonAlign : 'center',
		 items:[{
			layout : 'column',
		    items:[{
		        	columnWidth : .5,
					layout : 'form',
					items :[
						{xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '<font color="red">*</font>中文名',name : 'CUST_NAME',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:false,maxLength:80},
						{xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '<font color="red">*</font>证件类型1',store : indent00Store,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:false},
			            {xtype : 'combo',name : 'IDENT_TYPE1',hiddenName : 'IDENT_TYPE1',fieldLabel : '证件类型2',store : indent01Store,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
			            {xtype : 'datefield',name : 'IDENT_EXPIRED_DATE',fieldLabel : '<font color="red">*</font>证件1失效日期',format:'Y-m-d',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:false},
				    	{xtype : 'datefield',name : 'BUILD_DATE',fieldLabel : '<font color="red">*</font>公司成立日',format:'Y-m-d',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:false},
				        {xtype : 'combo',name : 'NATION_CODE',hiddenName : 'NATION_CODE',fieldLabel : '所在国别',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
			            {xtype : 'combo',name : 'VIP_FLAG',hiddenName : 'VIP_FLAG',fieldLabel : 'VIP标志',store : vipFlagStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%',hidden:true},
			            {xtype : 'combo',name : 'INOUT_FLAG',hiddenName : 'INOUT_FLAG',fieldLabel : '<font color="red">*</font>境内/外标志',store : inOrOutStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:false},
						{xtype : 'combo',name : 'ORG_SUB_TYPE',hiddenName : 'ORG_SUB_TYPE',fieldLabel : '特殊监管区',store : orgSubTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '<font color="red">*</font>行业类别',hiddenName:'IN_CLL_TYPE',name : 'IN_CLL_TYPE_NAME',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'IS_SEND_ECOMSTAT_FLAG',hiddenName : 'IS_SEND_ECOMSTAT_FLAG',fieldLabel : '综合对账单发送标志',store : isSendEcomstatFlagStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%',hidden:true},
						{xtype : 'combo',name : 'RISK_NATION_CODE',hiddenName : 'RISK_NATION_CODE',fieldLabel : '国别风险国别代码',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'RISK_CUST_GRADE',hiddenName : 'RISK_CUST_GRADE',fieldLabel : '<font color=red></font>反洗钱风险等级',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:true},
						{xtype : 'combo',name : 'HQ_NATION_CODE',hiddenName : 'HQ_NATION_CODE',fieldLabel : '总部所在国别',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : 'Obu Code',name : 'IDENT_NO2',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:50},
						{xtype : 'textfield',fieldLabel : '机构信用代码',name : 'CREDIT_CODE',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:35},
						{xtype : 'textfield',fieldLabel : 'Email',name : 'ORG_EMAIL',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:40},
						{xtype : 'textfield',fieldLabel : '代理人户名',name : 'AGENT_NAME',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:40},
			            {xtype : 'combo',name : 'AGENT_IDENT_TYPE',hiddenName : 'AGENT_IDENT_TYPE',fieldLabel : '代理人证件类型',store : indentStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '代理人证件号码',name : 'AGENT_IDENT_NO',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:40},
						{xtype : 'textfield',name:'RECOMMENDER_NAME',hiddenName : 'RECOMMENDER',fieldLabel : '推荐人',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:20},
						//add by liuming 20170612
						{xtype : 'combo',name : 'IS_TAIWAN_CORP',hiddenName : 'IS_TAIWAN_CORP',fieldLabel : '<font color="red">*</font>是否台资企业',store : isTaiStore,resizable : true,allowBlank : false,emptyText : '未知',valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly'}
			       ]
		      	},{
		       		columnWidth : .5,
				    layout : 'form',
				    items :[
				    	{xtype : 'combo',name : 'LNCUSTP',hiddenName : 'LNCUSTP',fieldLabel : '<font color="red">*</font>企业类型',store : lncustpStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:false},
						{xtype : 'textfield',name : 'EN_NAME',fieldLabel : '<font color="red">*</font>英文名',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:false,maxLength:100},
				    	{xtype : 'combo',name : 'CUST_TYPE',hiddenName : 'CUST_TYPE',fieldLabel : '客户类型',store : typeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly',hidden:true},
				    	{xtype : 'textfield',fieldLabel : '<font color="red">*</font>证件号码1',name : 'IDENT_NO',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:false,maxLength:50},
				    	{xtype : 'textfield',fieldLabel : '证件号码2',name : 'IDENT_NO1',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:50},
				    	{xtype : 'datefield',name : 'IDENT_EXPIRED_DATE1',fieldLabel : '证件2失效日期',format:'Y-m-d',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'ORG_TYPE',hiddenName : 'ORG_TYPE',fieldLabel : '合资类型',store : orgTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'AREA_CODE',hiddenName : 'AREA_CODE',fieldLabel : '地区代码',store : dqStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'datefield',name : 'BASIC_ACCT_OPEN_DATE',fieldLabel : '<font color="red">*</font>客户资料开立日',format:'Y-m-d',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:false},
						{xtype : 'combo',name : 'AR_CUST_FLAG',hiddenName : 'AR_CUST_FLAG',fieldLabel : '是否AR客户标志',store : arCustFlagStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',name : 'BASIC_ACCT_BANK_NAME',fieldLabel : '开户行',anchor : '90%',disabled:true,cls:'x-readOnly'},	
						{xtype : 'textfield',name : 'MGR_NAME',fieldLabel : '客户经理',anchor : '90%',disabled:true,cls:'x-readOnly'},
						{xtype : 'datefield',name : 'EFFECT_DATE',fieldLabel : '客户经理修改生效日',format:'Y-m-d',anchor : '90%',cls:'x-readOnly',disabled:true,allowBank:false,hidden:true},
				        {xtype : 'combo',name : 'STAFFIN',hiddenName : 'STAFFIN',fieldLabel : '关联人类型',store : staffinStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : 'Swift Address',name : 'SWIFT',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:20},
						{xtype : 'combo',name : 'JOINT_CUST_TYPE',hiddenName : 'JOINT_CUST_TYPE',fieldLabel : '联名户',store : jointCustTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
				        {xtype : 'combo',name : 'IS_FAX_TRANS_CUST',hiddenName : 'IS_FAX_TRANS_CUST',fieldLabel : '<font color="red">*</font>是否传真交易指示标志',store : isFaxStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%',allowBlank:false},
						{xtype : 'textfield',fieldLabel : '传真号码',name : 'ORG_FEX',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:20},
						{xtype : 'combo',name : 'AGENT_NATION_CODE',hiddenName : 'AGENT_NATION_CODE',fieldLabel : '代理人国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '代理人联系电话',name : 'AGENT_TEL',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:20},
						{xtype : 'combo',name : 'CUSTNM_IDENT_MODIFIED_FLAG',hiddenName : 'CUSTNM_IDENT_MODIFIED_FLAG',fieldLabel : 'CUSTNAME OR ID/REFNO CHANGE BY',store : custnmIdentModifiedStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%'}
				]
			}]
		},{
			items:[
			    {xtype : 'textfield',fieldLabel : '客户经理ID',name : 'MGR_ID',hidden:true},
				{xtype : 'textfield',fieldLabel : '证件类型1ID',name : 'IDENT_ID',hidden:true},
				{xtype : 'textfield',fieldLabel : '证件类型2ID',name : 'IDENT_ID1',hidden:true},
				{xtype : 'textfield',fieldLabel : '证件类型3ID',name : 'IDENT_ID2',hidden:true},
				{xtype : 'textfield',fieldLabel : '归属客户经理表ID',name : 'MGR_KEY_ID',hidden:true},
				{xtype : 'textfield',fieldLabel : '代理人ID',name : 'AGENT_ID',hidden:true},
				{xtype : 'textfield',fieldLabel : '重要标志ID',name : 'KEYFLAG_CUST_ID',hidden:true},
				{xtype : 'textfield',fieldLabel : '贵宾卡ID',name : 'VIP_GRADE_ID',hidden:true},
				{xtype : 'textfield',fieldLabel : '风险等级ID',name : 'RISK_GRADE_ID',hidden:true}
			]
		}],
		buttons:[{
			 text:'下一屏',
			 handler:function(){
				fsxComInfo.setActiveTab(1);
			 }
		}]
	});
	fsxComStore.load({
		params : {
			custId : custId
		},
		callback:function(){
			if(fsxComStore.getCount()!=0){
				fsxCombaseInfo.getForm().loadRecord(fsxComStore.getAt(0));
			}
		}
	});
	
	///////////////////////地址信息//////////////////////
	var comAddrCustInfoRowNumber = new Ext.grid.RowNumberer({
	    header:'NO.',
	    width:35
	});
	// create the data columnModel
	var comAddrCustInfoCm = new Ext.grid.ColumnModel([
	    comAddrCustInfoRowNumber,
	    {dataIndex:'ADDR_TYPE_ORA',header:'地址类型',width : 120,sortable : true},
	    {dataIndex:'ADDR',header:'详细地址',width : 350,sortable : true},
	    {dataIndex:'ZIPCODE',header:'邮政编码',width : 100,sortable : true},
	    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
	]);
	// create the data record
	var comAddrCustInfoRecord = new Ext.data.Record.create([
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
	var comAddrCustInfoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/dealWithFsx!queryAddr.json',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },comAddrCustInfoRecord)
	});
	// create the comAddrGridPanel
	var comAddrGridPanel = new Ext.grid.GridPanel({
		height: 200,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: comAddrCustInfoStore,
	    cm : comAddrCustInfoCm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	comAddrCustInfoStore.load({
		params : {
			custId : custId
		}
	});
	
	////////////////////////联系人信息///////////////////////////
	var comContactPersonRowNumber = new Ext.grid.RowNumberer({
	    header:'NO.',
	    width:35
	});
	// create the data columnModel
	var comContactPersonCm = new Ext.grid.ColumnModel([
	    comContactPersonRowNumber,
	    {dataIndex:'LINKMAN_TYPE_ORA',header:'联系人类型',width : 100,sortable : true},
	    {dataIndex:'LINKMAN_NAME',header:'姓名',width : 100,sortable : true},
	    {dataIndex:'IDENT_TYPE_ORA',header:'证件类型',width : 100,sortable : true},
	    {dataIndex:'IDENT_NO',header:'证件号码',width : 100,sortable : true},
	    {dataIndex:'IDENT_EXPIRED_DATE',header:'证件失效日期',width : 100,sortable : true},
	    {dataIndex:'GENDER_ORA',header:'性别',width : 100,sortable : true},
	    {dataIndex:'BIRTHDAY',header:'出生日期',width : 100,sortable : true},
	    {dataIndex:'LINKMAN_TITLE_ORA',header:'联系人称谓',width : 100,sortable : true},
	    {dataIndex:'OFFICE_TEL',header:'办公电话',width : 100,sortable : true},
	    {dataIndex:'HOME_TEL',header:'家庭电话',width : 100,sortable : true},
	    {dataIndex:'MOBILE',header:'手机号码',width : 100,sortable : true},
	    {dataIndex:'MOBILE2',header:'手机号码2',width : 100,sortable : true},
	    {dataIndex:'FEX',header:'传真号码',width : 100,sortable : true},
	    {dataIndex:'EMAIL',header:'邮件',width : 100,sortable : true},
	    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true},
	    {dataIndex:'LAST_UPDATE_USER',header:'最后更新人',width : 100,sortable : true},
	    {dataIndex:'LAST_UPDATE_TM',header:'最后修改日期',width : 120,sortable : true}
	]);
	// create the data record
	var comContactPersonRecord = new Ext.data.Record.create([
	    {name : 'LINKMAN_ID'},
	    {name : 'ORG_CUST_ID'},
	    {name : 'LINKMAN_TYPE'},
		{name : 'LINKMAN_TYPE_ORA'},
		{name : 'LINKMAN_NAME'},
		{name : 'IDENT_NO'},
		{name : 'IDENT_TYPE'},
		{name : 'IDENT_TYPE_ORA'},
		{name : 'IDENT_EXPIRED_DATE'},
		{name : 'GENDER'},
		{name : 'GENDER_ORA'},
		{name : 'BIRTHDAY'},
		{name : 'LINKMAN_TITLE'},
		{name : 'LINKMAN_TITLE_ORA'},
		{name : 'EMAIL'},
		{name : 'FEX'},
		{name : 'HOME_TEL'},
		{name : 'MOBILE'},
		{name : 'MOBILE2'},
		{name : 'OFFICE_TEL'},
		{name : 'LAST_UPDATE_SYS'},
		{name : 'LAST_UPDATE_USER'},
		{name : 'LAST_UPDATE_TM'}
	]);
	// create the data store
	var comContactPersonStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath+'/dealWithFsx!queryComContactPerson.json',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },comContactPersonRecord)
	});
	// create the comAddrGridPanel
	var comContactPersonGrid = new Ext.grid.GridPanel({
		height: 200,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: comContactPersonStore,
	    cm : comContactPersonCm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	comContactPersonStore.load({
		params : {
			custId : custId
		}
	});
	////////////////////////股东信息///////////////////////////
	var comHolderInfoRowNumber = new Ext.grid.RowNumberer({
	    header:'NO.',
	    width:35
	});
	// create the data columnModel
	var comHolderInfoCm = new Ext.grid.ColumnModel([
	    comHolderInfoRowNumber,
	    {dataIndex:'HOLDER_TYPE',header:'股东类型',width:100,sortable:true,hidden:true},
	    {dataIndex:'HOLDER_NAME',header:'姓名',width:100,sortable:true},
	    {dataIndex:'IDENT_TYPE',header:'证件类型',width:100,sortable:true,hidden:true}, 
	    {dataIndex:'IDENT_TYPE_ORA',header:'证件类型',width:100,sortable:true}, 
	    {dataIndex:'IDENT_NO',header:'证件号码',width:150,sortable:true}, 
	    {dataIndex:'IDENT_EXPIRED_DATE',header:'证件有效期限',width:100,sortable:true}, 
	    {dataIndex:'HOLDER_PER_MOBILE',header:'联系电话',width:100,sortable:true}, 
	    {dataIndex:'STOCK_PERCENT',header:'控股/表决权比例',width:100,sortable:true}, 
	    {dataIndex:'REMARK',header:'控制方式',width:100,sortable:true}, 
	    {dataIndex:'HOLDER_PER_IND_POS',header:'职务',width:100,sortable:true}, 
	    {dataIndex:'HOLDER_PER_POST_ADDR',header:'居住/联系地址',width:150,sortable:true}
	]);
	// create the data record
	var comHolderInfoRecord = new Ext.data.Record.create([
	    {name:'HOLDER_ID'},
	    {name:'CUST_ID'},
	    {name:'HOLDER_TYPE'},
	    {name:'HOLDER_TYPE_ORA'},
	    {name:'HOLDER_NAME'},
	    {name:'IDENT_TYPE'},
	    {name:'IDENT_TYPE_ORA'},
	    {name:'IDENT_NO'},
	    {name:'IDENT_EXPIRED_DATE'},
	    {name:'HOLDER_PER_MOBILE'},
	    {name:'STOCK_PERCENT'},
	    {name:'REMARK'},
	    {name:'HOLDER_PER_IND_POS'},
	    {name:'HOLDER_PER_POST_ADDR'},
	    {name:'LAST_UPDATE_SYS'},
	    {name:'LAST_UPDATE_USER'},
	    {name:'LAST_UPDATE_TM'}
	]);
	// create the data store
	var comHolderInfoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/acrmFCiOrgHolderinfo.json',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },comHolderInfoRecord)
	});
	comHolderInfoStore.load({
		params : {
			custId : custId
		}
	});
	// create the addrGridPanel
	var comHolderGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: comHolderInfoStore,
	    cm : comHolderInfoCm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	//////////////////////////////////////联系信息//////////////////////////////////
	var comContactInfoRowNumber = new Ext.grid.RowNumberer({
	    header:'NO.',
	    width:35
	});
	// create the data columnModel
	var comContactInfoCm = new Ext.grid.ColumnModel([
	    comContactInfoRowNumber,
	    {dataIndex:'CONTMETH_TYPE_ORA',header:'联系方式类型',width : 150,sortable : true},
	    {dataIndex:'CONTMETH_INFO',header : '联系方式内容',width : 100,sortable : true}, 
	    //{dataIndex:'IS_PRIORI_ORA',header:'是否首选',width : 100,sortable : true},
	    {dataIndex:'REMARK',header:'备注',width : 100,sortable : true},
	    {dataIndex:'LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
	]);
	// create the data record
	var comContactInfoRecord = new Ext.data.Record.create([
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
	var comContactInfoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/acrmFCiContmethInfo.json?check=1',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },comContactInfoRecord)
	});
	// create the addrGridPanel
	var comContactGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: comContactInfoStore,
	    cm : comContactInfoCm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	comContactInfoStore.load({
		params : {
			custId : custId
		}
	});

	var fsxComLists = new Ext.Panel({
		title : '第二屏',
		frame : true,
		autoScroll : true,
		buttonAlign : "center",
		items : [{
			xtype : 'fieldset',
			title : '地址信息',
			items : [comAddrGridPanel]
		},{
	        xtype : 'fieldset',
	        title : '股东信息',
	        items : [comHolderGridPanel]
	    },{
			xtype : 'fieldset',
			title : '联系人信息',
			items : [comContactPersonGrid]
		},{
			xtype : 'fieldset',
			title : '联系信息',
			items : [comContactGridPanel]
		}],
		buttons: [{
			 text:'上一屏',
			 handler:function(){
				fsxComInfo.setActiveTab(0);
			 }
		},{
			 text:'下一屏',
			 handler:function(){
				fsxComInfo.setActiveTab(2);
			 }
		}]
	});

	/**
	 * 非授信查询第三屏
	 */
	var fsxComThreeStore = new Ext.data.Store({
		restful:true,
		proxy : new Ext.data.HttpProxy({
			url:basepath+'/dealWithFsx!queryComfsxThree.json',
			method:'get'
		}),
		reader: new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root:'json.data'
		}, [
			'CUST_ID','CUST_NAME','EMPLOYEE_SCALE','ENT_SCALE_CK','REMARK',
			'IDENT_ID','IDENT_TYPE','IDENT_NO','IDENT_EXPIRED_DATE',
			'IDENT_ID1','IDENT_TYPE1','IDENT_NO1','IDENT_EXPIRED_DATE1',
			'TAX_REG_ID','TAX_REGISTRATION_NO','TAX_IDENT_EXPIRED_DATE',
			'LEGAL_REPR_NAME','LEGAL_REPR_IDENT_TYPE','LEGAL_REPR_IDENT_NO','LEGAL_IDENT_EXPIRED_DATE','LEGAL_ARTIFICIAL_PERSON',
			'BUSI_CUST_ID','SALE_CCY','SALE_AMT','REG_CUST_ID','REGISTER_CAPITAL','REGISTER_CAPITAL_CURR'
		])
	});
	/**
	 * 查询非授信客户信息第三屏
	 */
	//基本信息部分  第三屏
	var fsxComThreePanel = new Ext.form.FormPanel({
		 frame : true,
		 autoScroll : true,
		 title : '第三屏',
		 labelWidth : 120,
		 buttonAlign : "center",
		 items:[{
			layout : 'column',
		    items:[{
		        	columnWidth : .5,
					layout : 'form',
					items :[
						{xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '证件类型1',store : indent00Store,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly'},
			            {xtype : 'combo',name : 'IDENT_TYPE1',hiddenName : 'IDENT_TYPE1',fieldLabel : '证件类型2',store : indent01Store,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly'},
			            {xtype : 'datefield',name : 'IDENT_EXPIRED_DATE',fieldLabel : '证件1失效日期',format:'Y-m-d',anchor : '90%',disabled:true,cls:'x-readOnly'},
				    	{xtype : 'textfield',fieldLabel : '法人名',name : 'LEGAL_REPR_NAME',anchor : '90%',disabled:true,cls:'x-readOnly'},
				    	{xtype : 'combo',name : 'LEGAL_REPR_IDENT_TYPE',hiddenName : 'LEGAL_REPR_IDENT_TYPE',fieldLabel : '法人证件类型',store : indentStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly'},
				    	{xtype : 'datefield',name : 'LEGAL_IDENT_EXPIRED_DATE',fieldLabel : '法人证件失效日期',format:'Y-m-d',anchor : '90%',disabled:true,cls:'x-readOnly'},
				    	{xtype : 'textfield',fieldLabel : '税务登记证编码',name : 'TAX_REGISTRATION_NO',disabled:true,cls:'x-readOnly',anchor : '90%',maxLength:50},
				    	{xtype : 'combo',name : 'REGISTER_CAPITAL_CURR',hiddenName : 'REGISTER_CAPITAL_CURR',fieldLabel : '注册资本币别',store : moneyStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%',editable:true},
				        {xtype : 'combo',name : 'SALE_CCY',hiddenName : 'SALE_CCY',fieldLabel : '年销售额币别',store : moneyStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%',editable:true},
			            {xtype : 'combo',name : 'ENT_SCALE_CK',hiddenName : 'ENT_SCALE_CK',fieldLabel : '公司规模',store : entScaleRhStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',disabled:true,cls:'x-readOnly',anchor : '90%',editable:false}
			       ]
		      	},{
		       		columnWidth : .5,
				    layout : 'form',
				    items :[
				    	{xtype : 'textfield',fieldLabel : '中文名',name : 'CUST_NAME',anchor : '90%',disabled:true,cls:'x-readOnly'},
				    	{xtype : 'textfield',fieldLabel : '证件号码1',name : 'IDENT_NO',anchor : '90%',disabled:true,cls:'x-readOnly'},
				    	{xtype : 'textfield',fieldLabel : '证件号码2',name : 'IDENT_NO1',anchor : '90%',disabled:true,cls:'x-readOnly'},
				    	{xtype : 'datefield',name : 'IDENT_EXPIRED_DATE1',fieldLabel : '证件2失效日期',format:'Y-m-d',anchor : '90%',disabled:true,cls:'x-readOnly'},
						{xtype : 'datefield',name : 'LEGAL_ARTIFICIAL_PERSON',fieldLabel : '法人修改日',format:'Y-m-d',anchor : '90%',disabled:true,cls:'x-readOnly'},
				    	{xtype : 'textfield',fieldLabel : '法人证件号码',name : 'LEGAL_REPR_IDENT_NO',anchor : '90%',disabled:true,cls:'x-readOnly'},
				    	{xtype : 'numberfield',fieldLabel : '员工人数',name : 'EMPLOYEE_SCALE',minValue:'0',disabled:true,cls:'x-readOnly',anchor : '90%',maxValue:99999999999},
						{xtype : 'datefield',name : 'TAX_IDENT_EXPIRED_DATE',fieldLabel : '税务登记证失效日期',format:'Y-m-d',disabled:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'numberfield',fieldLabel : '注册资本(万元)',name : 'REGISTER_CAPITAL',minValue:'0',disabled:true,cls:'x-readOnly',anchor : '90%',maxValue:99999999999},
						{xtype : 'numberfield',fieldLabel : '年销售额(万元)',name : 'SALE_AMT',minValue:'0',disabled:true,cls:'x-readOnly',anchor : '90%',maxValue:99999999999}
				]
			}]
		},
		{xtype : 'textarea',fieldLabel : '备注',name : 'REMARK',maxLength: 100,disabled:true,cls:'x-readOnly',anchor : '95%'},
		{xtype : 'textfield',fieldLabel : '证件类型1ID',name : 'IDENT_ID',hidden:true},
		{xtype : 'textfield',fieldLabel : '证件类型2ID',name : 'IDENT_ID1',hidden:true},
		{xtype : 'textfield',fieldLabel : '税务登记ID',name : 'TAX_REG_ID',hidden:true},
		{xtype : 'textfield',fieldLabel : '注册ID',name : 'REG_CUST_ID',hidden:true},
		{xtype : 'textfield',fieldLabel : '经营ID',name : 'BUSI_CUST_ID',hidden:true}
		],
		 buttons:[{
			 text:'上一屏',
			 handler:function(){
				fsxComInfo.setActiveTab(1);
			 }
		}]
	});
	fsxComThreeStore.load({
		params : {
			custId : custId
		},
		callback:function(){
			if(fsxComThreeStore.getCount()!=0){
				fsxComThreePanel.getForm().loadRecord(fsxComThreeStore.getAt(0));
			}
		}
	});

	/**
	 * 对公非授信信息面板
	 */
	var fsxComInfo = new Ext.TabPanel({
		activeItem : 0,
		defaults:{autoHeight: true},
		items:[fsxCombaseInfo,fsxComLists,fsxComThreePanel]
	});
	var panel = new Ext.Panel({
		title:'客户复核前信息',
		autoHeight:true,
		autoWeight:true,
		items:[fsxComInfo]
	});
//-------------------------------------------客户复核前信息end----------------------------------
    var bussFieldSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
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
	var view = new Ext.Panel( {
		renderTo : 'viewEChian',
		frame : true,
		width : document.body.scrollWidth,
		height : document.body.scrollHeight-40,
		autoScroll : true,
		layout : 'form',
		items : [bussFieldSetGrid,EchainPanel]

	});
	
});
