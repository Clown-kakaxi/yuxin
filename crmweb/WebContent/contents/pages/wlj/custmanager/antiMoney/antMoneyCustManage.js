/**
 * 
 * @Description: 客户反洗钱查询
 * @author wangmk
 * @date 2014-7-8
 * 
 */
imports([ '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js', // 机构放大镜
		'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js', // 用户放大镜
		'/contents/pages/wlj/customerManager/queryAllCustomer/allLookup.js', //所有数据字典定义
		'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'// ,
// '/contents/pages/common/Com.yucheng.crm.common.ImpExp.js'
]);

var url = basepath + "/customerAntiMoneyQuery.json";

var _custId;
var comitUrl = false;
var createView = false;
var editView = false;
var detailView = false;

var addID = '';// 保存带加入客户群的成员id
var memberType = '';// 群成员类型



//联系人身份 对公--数据字典
var zlFXQ_DQSH028Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ_DQSH028'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

//行业分类（主营）--数据字典
var zlXD000055Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=XD000055'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

//客户的股权或控制权结构--数据字典
var zlFXQ023Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ023'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

//与客户建立业务关系的渠道--数据字典
var zlFXQ021Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ021'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

//  --数据字典
var zlFXQ025Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ025'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
//  --数据字典
var zlFXQ010Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ010'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
//开户行 对私  --数据字典
var zlXD000271Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=XD000271'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
//客户办理业务 对私  --数据字典
var zlFXQ007Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ007'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
// 机构树加载条件
var condition = {
	searchType : 'SUBTREE' // 查询子机构
};

// 加载机构树
var treeLoaders = [ {
	key : 'ORGTREELOADER',
	url : basepath + '/commsearch.json?condition=' + Ext.encode(condition),
	parentAttr : 'SUPERUNITID',
	locateAttr : 'UNITID',
	jsonRoot : 'json.data',
	rootValue : JsContext._orgId,
	textField : 'UNITNAME',
	idProperties : 'UNITID'
}, {
	key : 'BLINETREELOADER',
	url : basepath + '/businessLineTree.json',
	parentAttr : 'PARENT_ID',
	locateAttr : 'BL_ID',
	jsonRoot : 'json.data',
	rootValue : '0',
	textField : 'BL_NAME',
	idProperties : 'BL_ID'
} ];

// 树配置
var treeCfgs = [ {
	key : 'ORGTREE',
	loaderKey : 'ORGTREELOADER',
	autoScroll : true,
	rootCfg : {
		expanded : true,
		id : JsContext._orgId,
		text : JsContext._unitname,
		autoScroll : true,
		children : [],
		UNITID : JsContext._orgId,
		UNITNAME : JsContext._unitname
	}
}, {
	key : 'BLTREE',
	loaderKey : 'BLINETREELOADER',
	autoScroll : true,
	rootCfg : {
		expanded : true,
		id : '0',
		text : '归属业务条线',
		autoScroll : true,
		children : []
	}
} ];
var lookupTypes = [ 'XD000080',// 客户类别
'XD000040', // 证件类别
'XD000082', 'FXQ_RISK_LEVEL',// 反洗钱风险等级
'XD000081',// 客户状态
'CUST_RISK_CHARACT', 'XD000096', 'PRE_CUST_LEVEL', 'CUSTOMER_GROUP_TYPE',
		'CUSTOMER_SOURCE_TYPE', 'GROUP_MEMEBER_TYPE', 'SHARE_FLAG', 'XD000267',
		'XD000082', 'XD000245', 'XD000246', 'XD000247', 'IF_FLAG', 'FXQ007',
		'FXQ010', 'FXQ21006', 'FXQ023', 'FXQ025', 'APP_STATUS'// 特殊客户审核状态
];

var localLookup = {
	'ADD_WAY' : [ {
		key : '1',
		value : '加入已有客户群'
	}, {
		key : '2',
		value : '新建客户群'
	} ],
	'LAST_PER' : [ {
		key : 'ETL',
		value : 'ETL'
	}, {
		key : __userId,
		value : __userName
	} ]
};

var fields = [
		{
			name : 'CUST_TYPE',
			text : '客户类型',
			resutlWidth : 60,
			translateType : 'XD000080',
			searchField : true
		},/*
		{
			name : 'CUST_STAT',
			text : '客户状态',
			resutlWidth : 80,
			translateType : 'XD000081',
			searchField : true
		},*/
		{
			name : 'CUST_ID',
			text : '客户编号',
			resutlWidth : 120,
			searchField : true,
			dataType : 'string'
		},
		{
			name : 'CORE_NO',
			text : '核心客户号',
			resutlWidth : 120,
			searchField : true,
			dataType : 'string'
		},
		{
			name : 'CUST_NAME',
			text : '客户名称',
			resutlWidth : 150,
			searchField : true,
			dataType : 'string'
		},
		/*{
			name : 'IDENT_TYPE',
			text : '证件类型',
			resutlWidth : 120,
			translateType : 'XD000040',
			searchField : true,
			editable : true
		},
		{
			name : 'IDENT_NO',
			text : '证件号码',
			resutlWidth : 120,
			searchField : true,
			dataType : 'string'
		},*/
		// {name:'ACCT_NO',text:'账号',resutlWidth:60,searchField:
		// true,dataType:'string'},
		
		{
			name : 'FXQ_RISK_LEVEL',
			text : '反洗钱风险等级',
			resutlWidth : 60,
			translateType : 'FXQ_RISK_LEVEL',
			gridField : true,
			searchField : true
		},
		{
			name : 'IDENT_TYPE1',
			text : '证件1类型',
			resutlWidth : 120,
			searchField : true,
			dataType : 'string'
		},
		{
			name : 'INDENT_NO1',
			text : '证件1号码',
			resutlWidth : 120,
			searchField : true,
			dataType : 'string'
		},
		{
			name : 'IDENT_EXPIRED_DATE1',
			text : '证件1有效期',
			resutlWidth : 120,
			searchField : true,
			dataType : 'string'
		},
		{
			name : 'IDENT_TYPE2',
			text : '证件2类型',
			resutlWidth : 120,
			searchField : true,
			dataType : 'string'
		},
		{
			name : 'INDENT_NO2',
			text : '证件2号码',
			resutlWidth : 120,
			searchField : true,
			dataType : 'string'
		},
		{
			name : 'IDENT_EXPIRED_DATE2',
			text : '证件2有效期',
			resutlWidth : 120,
			searchField : true,
			dataType : 'string'
		},
		
		{
			name : 'ORG_NAME',
			text : '归属机构',
			xtype : 'wcombotree',
			innerTree : 'ORGTREE',
			resutlWidth : 80,
			searchField : true,
			showField : 'text',
			hideField : 'UNITID',
			editable : false
		},
		{
			name : 'MGR_NAME',
			text : '所属客户经理',
			xtype : 'userchoose',
			hiddenName : 'MGR_ID',
			resutlWidth : 150,
			singleSelect : false,
			searchField : true
		},
		/*{
			name : 'BELONG_TEAM_HEAD_NAME',
			text : '所属团队负责人',
			xtype : 'userchoose',
			hiddenName : 'MGR_ID1',
			resutlWidth : 120,
			singleSelect : false,
			searchField : true
		},*/
		{
			name : 'LINKMAN_NAME',
			text : '联系人姓名',
			resutlWidth : 80,
			gridField : false,
			dataType : 'string'
		},
		{
			name : 'LINKMAN_TEL',
			text : '联系人电话',
			resutlWidth : 80,
			gridField : false,
			dataType : 'string'
		},
		/*{
			name : 'EMAIL',
			text : '邮件地址',
			resutlWidth : 120,
			dataType : 'string'
		},*/
		
		{
			name : 'OLD_FXQ_RISK_LEVEL', 
			text : '当前反洗钱风险等级',
			resutlWidth : 60,
			translateType : 'FXQ_RISK_LEVEL',
			gridField:false,
		},
		{
			name : 'LAST_UPDATE_USER',
			text : '最后更新人',
			resultWidth : 60,
			gridField : false
		},/*
		{
			name : 'CREDIT_LEVEL',
			text : '信用评级(法金)',
			resutlWidth : 80,
			searchField : true,
			translateType : 'XD000096'
		},
		{
			name : 'RISK_LEVEL',
			text : '风险等级(个金)',
			resutlWidth : 80,
			translateType : 'CUST_RISK_CHARACT'
		},
		{
			name : 'BL_NAME',
			text : '业务条线',
			resutlWidth : 80,
			xtype : 'wcombotree',
			innerTree : 'BLTREE',
			searchField : true,
			showField : 'text',
			hideField : 'BL_ID',
			allowBlank : false
		},
		{
			name : 'CUST_LEVEL',
			text : '客户级别',
			resutlWidth : 60,
			searchField : true,
			translateType : 'PRE_CUST_LEVEL'
		},
		{
			name : 'TOTAL_DEBT',
			text : '总负债',
			resutlWidth : 150,
			dataType : 'number'
		},
		{
			name : 'FAXTRADE_NOREC_NUM',
			text : '传真交易正本未回收数量',
			resutlWidth : 150,
			dataType : 'numberNoDot'
		},
		{
			name : 'CURRENT_AUM',
			text : '管理总资产时点值',
			resutlWidth : 150,
			dataType : 'number'
		},*/
		{
			name : 'if_org_sub_type_ORG',
			text : '客户是否为自贸区客户',
			gridField : false,
			//allowBlank:false,
			translateType : 'IF_FLAG'
		},{
			name : 'if_org_sub_type_per',
			text : '客户是否为自贸区客户',
			gridField : false,
			//allowBlank:false,
			translateType : 'IF_FLAG'
		},
		{
			name : 'FLAG_AGENT',
			text : '客户是否为代理开户',
			gridField : false,
			//allowBlank:false,
			translateType : 'IF_FLAG'
		},
		{
			name : 'FXQ007',
			text : '客户办理的业务(对私)',
			gridField : false,
			translateType : 'FXQ007',
			//allowBlank:false,
			multiSelect : true
		},
		{
			name : 'FXQ008',
			text : '是否涉及风险提示信息或权威媒体报道信息',
			gridField : false,
			//allowBlank:false,
			translateType : 'IF_FLAG'
		},
		{
			name : 'FXQ009',
			text : '客户或其亲属、关系密切人等是否属于外国政要',
			gridField : false,
			//allowBlank:false,
			translateType : 'IF_FLAG'
		},

		{
			name : 'FXQ010',
			text : '反洗钱交易监测记录',
			gridField : false,
			allowBlank:false,
			translateType : 'FXQ010',
			listeners : {
				select : function() {
					
					var value = getCurrentView().contentPanel.form.findField(
							"FXQ010").getValue();
					if (value == '2'||value == '3'||value == '4') {
						getCurrentView().contentPanel.form.findField(
								"KYJYBG_SBSJ").show();
						getCurrentView().contentPanel.form.findField(
						"KYJYBG_SBSJ").allowBlank=false;
					} else {
						getCurrentView().contentPanel.form.findField(
							"KYJYBG_SBSJ").hide();

						getCurrentView().contentPanel.form.findField(
						"KYJYBG_SBSJ").allowBlank=true;
					}
				}
			}
		}, {
			name : 'FXQ011',
			text : '是否被列入中国发布或承认的应实施反洗钱监控措施的名单',
			gridField : false,
			allowBlank:false,
			translateType : 'IF_FLAG'
		}, {
			name : 'FXQ012',
			text : '是否发生具有异常特征的大额现金交易',
			gridField : false,
			allowBlank:false,
			translateType : 'IF_FLAG'
		}, {
			name : 'FXQ013',
			text : '是否发生具有异常特征的非面对面交易',
			gridField : false,
			allowBlank:false,
			translateType : 'IF_FLAG'
		}, {
			name : 'FXQ014',
			text : '是否存在多次涉及跨境异常交易报告',
			gridField : false,
			allowBlank:false,
			translateType : 'IF_FLAG'
		}, {
			name : 'FXQ015',
			text : '代办业务是否存在异常情况',
			gridField : false,
			allowBlank:false,
			translateType : 'IF_FLAG'
		}, {
			name : 'FXQ016',
			text : '是否频繁进行异常交易',
			gridField : false,
			allowBlank:false,
			translateType : 'IF_FLAG'
		}, {
			name : 'FXQ026',
			text : '客户所在行政区域是否存在严重犯罪',
			gridField : false,
			allowBlank:false,
			translateType : 'IF_FLAG'
		},	
		{
			name : 'FXQ021',
			text : '与客户建立业务关系的渠道',
			gridField : false,
			//allowBlank:false,
			translateType : 'FXQ21006'
		}, {
			name : 'FXQ022',
			text : '是否在规范证券市场上市',
			gridField : false,
			translateType : 'IF_FLAG'
		}, {
			name : 'FXQ023',
			text : '客户的股权或控制权结构',
			gridField : false,
			translateType : 'FXQ023'
		}, {
			name : 'FXQ024',
			text : '客户是否存在隐名股东或匿名股东',
			gridField : false,
			//allowBlank:false,
			translateType : 'IF_FLAG'
		}, {
			name : 'FXQ025',
			text : '客户办理的业务(对公)',
			gridField : false,
			//allowBlank:false,
			translateType : 'FXQ025',
			multiSelect : true
		}, {
			name : 'CREATE_DATE',
			gridField : false
		}, {
			name : 'FLAG',
			gridField : false,
			//allowBlank:false,
			hidden : true
		},
		
		{
			name : 'FLAG_FP',//反洗钱复评状态 
			gridField : false,
			//allowBlank:false,
			hidden : true
		},
		// 特殊名单客户表
		{
			name : 'SPECIAL_LIST_TYPE',
			text : '特殊名单类型',
			translateType : 'XD000245',
			//allowBlank : false,
			gridField : false
		}, {
			name : 'SPECIAL_LIST_KIND',
			text : '特殊名单类别',
			translateType : 'XD000246',
			//allowBlank : false,
			gridField : false
		}, {
			name : 'SPECIAL_LIST_FLAG',
			text : '特殊名单标志',
			translateType : 'XD000247',
			//allowBlank : false,
			gridField : false
		}, {
			name : 'ORIGIN',
			text : '数据来源',
			//allowBlank : false,
			gridField : false
		}, {
			name : 'STAT_FLAG',
			text : '状态标志',
			//allowBlank : true,
			gridField : false
		}, {
			name : 'APPROVAL_FLAG',
			text : '审核标志',
			//allowBlank : true,
			gridField : false,
			readOnly : true,
			translateType : 'APP_STATUS'
		}, {
			name : 'START_DATE',
			text : '起始日期',
			dataType : 'date',
			//allowBlank : false,
			gridField : false
		}, {
			name : 'END_DATE',
			text : '结束日期',
			dataType : 'date',
			//allowBlank : false,
			gridField : false
		}, {
			name : 'ENTER_REASON',
			text : '列入原因',
			xtype : 'textarea',
			//allowBlank : false,
			gridField : false
		}, {
			name : 'INSTRUCTION_CONTENT1',
			text : '<font color="red">*</font>说明',
			xtype : 'textarea',
			//allowBlank : false,
			gridField : false
		}, {
			name : 'KYJYBG_SBSJ',
			text : '<font color="red">*</font>上报时间',
			xtype : 'datefield',
			//hideField:true;
			//allowBlank : false,
			gridField : false,
			format:'Y-m-d'
		}];

// *********************grid*****************************
// 产品的特征信息，在产品详情中展示
var productCharactrownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
	header : 'No.',
	width : 28
});
var productCharactcolumnmodel = new Ext.grid.ColumnModel(
		[
		productCharactrownum, {
			header : '说明ID',
			dataIndex : 'INSTR_ID',
			width : 100,
			sortable : true,
			hidden : true
		}, {
			header : '客户编号',
			dataIndex : 'CUST_ID',
			width : 100,
			sortable : true,
			hidden : true
		}, {
			header : '说明内容',
			dataIndex : 'INSTRUCTION_CONTENT',
			width : 280 // 5列设置 280 
		}, {
			header : '录入时间',
			dataIndex : 'INSTR_TIME',
			renderer:function(value, p, record){
                if(typeof value =='string'){
	                    return value.substring(0,19);
                }else{
                    return value.format('Y-m-d H:i:s');
                }
            },
			width : 130
		}, {
			header : '用户编号',
			dataIndex : 'INSTR_USER',
			width : 100,
			sortable : true,
			hidden : true
		}, {
			header : '上报可疑交易报告时间',
			dataIndex : 'KYJYBG_SBSJ',
			width : 130,
			sortable : true,
		}, {
			header : '录入人员',
			dataIndex : 'USER_NAME',
			width : 100,
			sortable : true,
		} ]);

var productCharactRecord = new Ext.data.Record.create([ {
	name : 'INSTR_ID'
},// 说明ID
{
	name : 'CUST_ID'
},// 客户编号

{
	name : 'INSTRUCTION_CONTENT'
},// 说明内容
{
	name : 'INSTR_TIME'
},// 新建说明时间
{
	name : 'INSTR_USER'
},// 新建说明用户
{
	name : 'USER_NAME'
} // 新建说明用户
,{
	name :'KYJYBG_SBSJ'
}
]);
var productCharactInfoReader = new Ext.data.JsonReader({
	totalProperty : 'json.count',
	root : 'json.data'
}, productCharactRecord);
var productCharactInfoProxy = new Ext.data.HttpProxy({
	url : basepath + '/CustomerAntiMoneyQueryInstruction.json'
});
var productCharactInfoStore = new Ext.data.Store({
	restful : true,
	proxy : productCharactInfoProxy,
	reader : productCharactInfoReader
});
// ----------------------------------------------------------------- 分页工具
var productCharactpagesize_combo = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
		fields : [ 'value', 'text' ],
		data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
				[ 100, '100条/页' ], [ 250, '250条/页' ], [ 500, '500条/页' ] ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '20',
	forceSelection : true,
	width : 85
});
var productCharactnumber = parseInt(productCharactpagesize_combo.getValue());

/**
 * 监听分页下拉框选择事件
 */
productCharactpagesize_combo.on("select", function(comboBox) {
	productCharactbbar.pageSize = parseInt(productCharactpagesize_combo
			.getValue());
	searchrelateData(productCharactgrid, productCharactpagesize_combo);// 不同分页加载数据

});

// --分页工具定义
var productCharactbbar = new Ext.PagingToolbar({
	pageSize : productCharactnumber,
	store : productCharactInfoStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', productCharactpagesize_combo ]
});

// -- gridpanel  反洗钱
var productCharactgrid = new Ext.grid.GridPanel({
	title : '反洗钱监测记录说明',
	collapsible : true,
	autoHeight : true,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : productCharactInfoStore, // creditStore, // 数据存储
	stripeRows : true, // 斑马线
	cm : productCharactcolumnmodel,// creditcm, // 列模型
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}

})
var productCharactgrid1 = new Ext.grid.GridPanel({
	title : '反洗钱监测记录说明',
	collapsible : true,
	autoHeight : true,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : productCharactInfoStore, // creditStore, // 数据存储
	stripeRows : true, // 斑马线
	cm : productCharactcolumnmodel,// creditcm, // 列模型
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}

})
// ***************************************************
var tbar = [ {
	text : '调整反洗钱风险等级',
	hidden:JsContext.checkGrant('_fxqfxdj'),//检查当前角色是否有当前功能权限
	handler : function() {
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		} else {
			showCustomerViewByIndex(0);
		}
	}
}, {
	text : '客户反洗钱指标查看',
	hidden:JsContext.checkGrant('_fxqzbcx'),
	handler : function() {
		debugger;
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		} else {
			var custId005 = getSelectedData().data.CUST_ID;
		
				var fxqIndexInfoStore = new Ext.data.Store({
					restful:true,
					proxy : new Ext.data.HttpProxy({
						url:basepath + '/customerAntiMoneyQuery!getFXQIndexInfo.json',
						method:'get',
					}),
					reader: new Ext.data.JsonReader({
						totalProperty : 'json.count',
						root:'json.data'
					}, [
							"CUST_ID"   //   --客户号  对公对私
							,"CORE_NO"   //   --核心客户号 对公对私
							,"CUST_NAME"   //  --客户名称 对公对私
							,"CUST_TYPE"   //   --客户类型                               
							,"CITIZENSHIP"   //  --国籍  对私
							,"CAREER_TYPE"   //  --职业  对私
							,"BIRTHDAY"   //     --出生日期  对私
							,"IDENT_TYPE1"   // --证件类型1  对公对私
							,"INDENT_NO1"   //  --证件号1 对公对私对公对私
							,"IDENT_EXPIRED_DATE1"   //   --证件1到期日 对公对私
							,"IDENT_TYPE2"   // --证件类型2 对公对私
							,"INDENT_NO2"   //  --证件号2 对公对私
							,"IDENT_EXPIRED_DATE2"   //  --证件2到期日 对公对私
							//,"if_org_sub_type_per"   //  --是否自贸区(对私) 
							//,"if_org_sub_type_ORG"   //   --是否自贸区(对私) 
							,"IF_ORG_SUB_TYPE_PER"
							,"IF_ORG_SUB_TYPE_ORG"
							,"BUILD_DATE"   //   --成立日期  --对公
							,"NATION_CODE"   //  --国家或地区代码 --注册地 对公
							,"FLAG_AGENT"   //   --客户是否为代理开户  对公对私
							,"AGENT_NAME"   //  --代理人姓名  对公对私
							,"AGENT_NATION_CODE"   //  --代理人国家代码  对公对私
							,"AGE_IDENT_TYPE"   //  --代理人证件类型   对公对私
							,"AGE_IDENT_NO"   //   --代理人证件号码  对公对私
							,"TEL"   //     --代理人联系电话  对公对私                                                                                              
							 //--保留客户是否涉及反洗钱黑名单   对公对私
							// * 合规处调整指标
							,"FXQ010" //--反洗钱交易监测记录
							,"FXQ012" //--是否发生具有异常特征的大额现金交易
							,"FXQ013" //--是否发生具有异常特征的非面对面交易
							,"FXQ014" //--是否存在多次涉及跨境异常交易报告
							,"FXQ015" //--代办业务是否存在异常情况
							,"FXQ016" //--是否频繁进行异常交易
							,"FXQ026"	//	客户所在行政区域是否存在严重犯罪
							
							,"FXQ007"   //   --客户办理的业务(对私) 对公对私
							,"FXQ008"   //   --是否涉及风险提示信息或权威媒体报道信息  对公对私
							,"FXQ009"   //   --客户或其亲属、关系密切人等是否属于外国政要 对公对私
							,"FXQ021"   //   --与客户建立业务关系的渠道  对公
							,"FXQ022"   //   --是否在规范证券市场上市  对公 
							,"FXQ023"   //   --客户的股权或控制权结构  对公 
							,"FXQ024"   //   --客户是否存在隐名股东或匿名股东 对公 
							,"FXQ025"   //   --客户办理的业务(对公)  对公 
							//,"In_Cll_Type"   // --行业分类  对公
							,"IN_CLL_TYPE"
							,"ENT_SCALE_CK"   //  --企业规模  对公
							,"DQSH001"   //  --证件是否过期   对私
							,"DQSH002"   //  --客户是否无法取得联系   对私
							,"DQSH003"   //  --联系时间   对私
							,"DQSH004"   //  --联系人与帐户持有人的关系 对公对私 
							,"DQSH005"   //  --预计证件更新时间 对公对私
							,"DQSH006"   //  --未及时更新证件的理由  对公对私
							,"DQSH007"   //  --客户是否无正当理由拒绝更新证件  对公对私
							,"DQSH008"   //  --客户留存的证件及信息是否存在疑点或矛盾 对公对私 
							,"DQSH009"   //  --账户是否频繁发生大额现金交易  对公对私
							,"DQSH010"   //  --账户是否频繁发生外币现钞存取业务  对公对私
							,"DQSH011"   //  --账户现金交易是否与客户职业特性不符  对公对私
							,"DQSH012"   //  --账户是否频繁发生大额的网上银行交易  对公对私
							,"DQSH013"   //  --账户是否与公司账户之间发生频繁或大额的交易  对公对私
							,"DQSH014"   //  --账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符  对公对私
							,"DQSH015"   //  --账户资金是否快进快出，不留余额或少留余额  对公对私
							,"DQSH016"   //  --账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准 对公对私 
							,"DQSH017"   //  --账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付 对公对私 
							,"DQSH018"   //  --账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付  对公对私
							,"DQSH019"   //  --账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心  对公对私
							,"DQSH020"   //  --账户是否频繁发生跨境交易，且金额大于1万美元 对公对私
							,"DQSH021"   //  --账户是否经常由他人代为办理业务  对公对私
							,"DQSH022"   //  --客户是否提前偿还贷款，且与其财务状况明显不符  对公对私
							,"DQSH023"   //  --当前账户状态是否正常  对公对私
							
							,"CURRENT_AUM"//AUM(人民币)(20160318新增)
			        		,"DQSH024" //	--AUM(人民币)          改为  客户是否涉及反洗钱黑名单
					
							,"DQSH025"   //  --企业证件是否过期  对公 
							,"DQSH026"   //  --法定代表人证件是否过期  对公 
							,"DQSH027"   //  --联系人证件是否过期  对公 
							,"DQSH028"   //  --联系人的身份  对公 
							,"DQSH029"   //  --账户是否与自然人账户之间发生频繁或大额的交易  对公 
							,"DQSH030"   //  --账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符  对公 
							,"DQSH031"   //  --账户是否频繁收取与其经营业务明显无关的汇款  对公
							,"DQSH032"   //  --账户资金交易频度、金额是否与其经营背景不符  对公
							,"DQSH033"   //  --账户交易对手及资金用途是否与其经营背景不符  对公
							,"DQSH034"   //  --账户是否与关联企业之间频繁发生大额交易   对公
							,"DQSH0352"   //  --客户行为是否存在异常   对私 低风险
							,"DQSH0362"   //  --账户交易是否存在异常    对私 低风险
							,"DQSH0351"   //  --客户行为是否存在异常   对公 低风险
							,"DQSH0361"   //  --账户交易是否存在异常    对公 低风险   
							,"DQSH037" //	--联系人与帐户持有人的关系说明
							,"DQSH038" //	--联系人的身份说明
							,"CUST_GRADE"   //  --客户反洗钱等级                                
							,"AUDIT_END_DATE"   // --审核截止日期

					])
				});
				
				
				
				
				
				
				fxqIndexInfoStore.load({
					params : {
							'CUST_ID':custId005
					},
					callback:function(){
						if(fxqIndexInfoStore.getCount()!=0){
							fxqIndexInfoPanel.getForm().loadRecord(fxqIndexInfoStore.getAt(0));
						}
					}
				});
	
			productCharactInfoStore.load({
				params : {
					CUST_ID : custId005,
					start : 0,
					limit : parseInt(productCharactpagesize_combo.getValue())
				}
			});
			showCustomerViewByTitle('客户反洗钱指标查看');
		}
	}
}, {
	text : '录入客户反洗钱指标',
	hidden:JsContext.checkGrant('_fxqjczb'),
	handler : function() {
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		} else {
			var custId005 = getSelectedData().data.CUST_ID;
			
			
			productCharactInfoStore.load({
				params : {
					CUST_ID : custId005,
					start : 0,
					limit : parseInt(productCharactpagesize_combo.getValue())
				}
			});
			showCustomerViewByTitle('录入客户反洗钱指标');

		}
	}
}
,{
    text:'客户反洗钱指标录入',//AO录入
    hidden:JsContext.checkGrant('_fxqzblr_ao'),
    handler:function(){
        if (getSelectedData() == false) {
            Ext.Msg.alert('提示', '请选择一条数据！');
            return false;
        } else {
            showCustomerViewByTitle('客户反洗钱指标录入');
        }
    }
}



 ];
/**
 * 反洗钱指标信息查看FormPanel
 */
var fxqIndexInfoPanel = new Ext.form.FormPanel({

	id : 'fxqLevelAuditPanel',//当前组件唯一的id
	frame: true,	// 是否渲染表单面板背景色
	labelAlign: 'middle',	// 标签对齐方式
	autoScroll:true,
	labelWidth:160,
	//autoHeight:false, //使用固定高度 
	Layout:'FIT',
	items:[

	       {//公共1 开始
			layout : 'column',//横排列
		    items:[
		           {
		        	   columnWidth : .25,
						layout : 'form',
						items :[
						        {	
						        	name : 'CUST_ID', fieldLabel : '客户号',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        	,{name : 'FLAG_AGENT', fieldLabel : '客户是否为代理开户',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
						        	,{name : 'CUST_GRADE', fieldLabel : '当前客户洗钱风险等级',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value'}
						         ]
		           },
		           {
		        	   columnWidth : .25,
						layout : 'form',
						items :[  
						          {name : 'CORE_NO', fieldLabel : '核心客户号',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						          ,{name : 'IDENT_TYPE1', fieldLabel : '证件1类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						          ,{name : 'IDENT_TYPE2', fieldLabel : '证件2类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
							   	
						    	]
		           },
		           {
		        	   columnWidth : .25,
						layout : 'form',
						items :[
						        {name : 'CUST_NAME', fieldLabel : '客户姓名',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{name : 'INDENT_NO1', fieldLabel : '证件1号码',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{name : 'INDENT_NO2', fieldLabel : '证件2号码',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						       
					   ]
		           
		           },
		           {
		        	   columnWidth : .25,
						layout : 'form',
						items :[
					        	{name : 'CUST_TYPE', fieldLabel : '客户类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store:typeStore,valueField : 'key',displayField : 'value'}
						        ,{name : 'IDENT_EXPIRED_DATE1', fieldLabel : '证件1到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{name : 'IDENT_EXPIRED_DATE2', fieldLabel : '证件2到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
					        	 
					        	]
		           }
		           
		    ]}//公共1 结束
	       ,{//企业(高中低)1开始
				layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        	{name : 'BUILD_DATE', fieldLabel : '成立日期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
										,{name : 'IF_ORG_SUB_TYPE_ORG', fieldLabel : '客户是否为自贸区客户',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							         ]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[  
									{name : 'NATION_CODE', fieldLabel : '注册地',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : conStore,valueField : 'key',displayField : 'value'}
									,{name : 'FXQ021', fieldLabel : '与客户建立业务关系的渠道',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'lovcombo',store : zlFXQ021Store,resizable : true,valueField : 'key',displayField : 'value'}
							    	]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'ENT_SCALE_CK', fieldLabel : '企业规模',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : entScaleRhStore,resizable : true,valueField : 'key',displayField : 'value'}
									,{name : 'FXQ025', fieldLabel : '客户在我行办理的业务包括',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'lovcombo',store : zlFXQ025Store,resizable : true,valueField : 'key',displayField : 'value'}
						   ]
			           
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'IN_CLL_TYPE', fieldLabel : '行业分类',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'lovcombo',store : zlXD000055Store, resizable: true,valueField : 'key',displayField : 'value'}
									,{name : 'FXQ022', fieldLabel : '客户是否在规范证券市场上市',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
									]
			           }
			           
			    ]}//企业(高中低)1结束
	       ,{//个人(高中低)1开始
				layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        	{name : 'CITIZENSHIP', fieldLabel : '国籍',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : conStore,valueField : 'key',displayField : 'value'}
							        	,{name : 'IF_ORG_SUB_TYPE_PER', fieldLabel : '客户是否为自贸区客户',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							         ]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[  
									{name : 'CAREER_TYPE', fieldLabel : '职业',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : careerTypeStore,resizable : true,valueField : 'key',displayField : 'value'}
									,{name : 'FXQ007', fieldLabel : '客户在我行办理的业务包括',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'lovcombo',store : zlFXQ007Store,resizable : true,valueField : 'key',displayField : 'value'}
							    	]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'BIRTHDAY', fieldLabel : '出生年月日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
									,{name : 'DQSH001', fieldLabel : '证件是否过期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
									,{name : 'DQSH0362', fieldLabel : '账户交易是否存在异常',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							    	
									]
			           
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'DQSH0352', fieldLabel : '客户行为是否存在异常',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
									
									//,{name : '', fieldLabel : '',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        	]
			           }
			           
			    ]}//个人（高中低）结束
		    
	       ,{//公共2 开始
				layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        	
							        	{name : 'AGENT_NAME', fieldLabel : '代理人姓名',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
							        	,{name : 'AGENT_NATION_CODE', fieldLabel : '代理人国籍',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : conStore,valueField : 'key',displayField : 'value'}
							         ]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[  
							       {name : 'AGE_IDENT_TYPE', fieldLabel : '代理人证件类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
									 ,{name : 'TEL', fieldLabel : '代理人联系电话',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
							        	
							    	]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							       
							       {name : 'FXQ008', fieldLabel : '客户是否涉及风险提示信息或权威媒体报道信息',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							        ,{name : 'DQSH024', fieldLabel : '客户是否涉及反洗钱黑名单',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
									  
						   ]
			           
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
                                     {name : 'AGE_IDENT_NO', fieldLabel : '代理人证件号码',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        	//,{name : '', fieldLabel : '',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
                                     ,{name : 'FXQ009', fieldLabel : '客户或其亲属、关系密切人是否属于外国政要',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
						        	]
			           }
			           
			    ]}//公共2 结束
	       ,{//企业(高中)1开始
				layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        	{name : 'DQSH025', fieldLabel : '企业证件是否过期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							         ]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[  
									{name : 'DQSH026', fieldLabel : '法定代表人证件是否过期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							    	]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'DQSH027', fieldLabel : '联系人证件是否过期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
						   ]
			           
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'DQSH028', fieldLabel : '联系人的身份',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : zlFXQ_DQSH028Store,resizable : true,valueField : 'key',displayField : 'value'}
						        	]
			           }
			           
			    ]}//企业（高中）1结束
		    ,{//公共（高中）开始
				layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        	{name : 'DQSH002', fieldLabel : '客户是否无法取得联系',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
										,{name : 'DQSH022', fieldLabel : '客户是否提前偿还贷款，且与其财务状况明显不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
										,{name : 'DQSH016', fieldLabel : '账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							        	,{name : 'DQSH005', fieldLabel : '预计证件更新时间',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
							        	
							         ]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[  
							        {name : 'DQSH003', fieldLabel : '联系时间',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
							        , {name : 'DQSH008', fieldLabel : '客户留存的证件及信息是否存在疑点或矛盾',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							        ,{name : 'DQSH017', fieldLabel : '账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							        ,{name : 'DQSH006', fieldLabel : '未及时更新证件的理由',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        	
							            
							    	]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        {name : 'DQSH004', fieldLabel : '联系人与帐户持有人的关系',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
							       ,{name : 'DQSH009', fieldLabel : '账户是否频繁发生大额现金交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							       ,{name : 'DQSH018', fieldLabel : '账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							       ,{name : 'DQSH007', fieldLabel : '客户是否无正当理由拒绝更新证件',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
						        	
						        	    
						   ]
			           
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        {name : 'DQSH023', fieldLabel : '当前账户状态是否正常',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							        ,{name : 'DQSH015', fieldLabel : '账户资金是否快进快出，不留余额或少留余额',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							        ,{name : 'DQSH019', fieldLabel : '账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							        ,{name : 'CURRENT_AUM', fieldLabel : 'AUM(人民币)',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
									 
						        	]
			           }
			           
			    ]}//公共（高中）结束
		    ,{//个人(高中)2开始
				layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        	{name : 'DQSH010', fieldLabel : '账户是否频繁发生外币现钞存取业务',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
										,{name : 'DQSH013', fieldLabel : '账户是否与公司账户之间发生频繁或大额的交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							         ]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[  
									{name : 'DQSH011', fieldLabel : '账户现金交易是否与客户职业特性不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
									,{name : 'DQSH020', fieldLabel : '账户是否频繁发生跨境交易，且金额大于1万美元',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							    	]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'DQSH012', fieldLabel : '账户是否频繁发生大额的网上银行交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
									,{name : 'DQSH014', fieldLabel : '账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
						   ]
			           
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'DQSH021', fieldLabel : '账户是否经常由他人代为办理业务',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
									//,{name : '', fieldLabel : '',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        	]
			           }
			           
			    ]}//个人（高中）2结束
		  
		  
		    ,{//企业(高中)2开始
				layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        	{name : 'DQSH029', fieldLabel : '账户是否与自然人账户之间发生频繁或大额的交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							         ]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[  
									{name : 'DQSH031', fieldLabel : '账户是否频繁收取与其经营业务明显无关的汇款',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							    	]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'DQSH032', fieldLabel : '账户资金交易频度、金额是否与其经营背景不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
						   ]
			           
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'DQSH033', fieldLabel : '账户交易对手及资金用途是否与其经营背景不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
						        	]
			           }
			           
			    ]}//企业（高中）2结束
		    ,{//企业(高中低)2开始  （后边高中）
				layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        	{name : 'FXQ023', fieldLabel : '客户的股权或控制权结构',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : zlFXQ023Store,resizable : true,valueField : 'key',displayField : 'value'}
							         ]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[  
									{name : 'FXQ024', fieldLabel : '客户是否存在隐名股东或匿名股东',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							    	]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'DQSH034', fieldLabel : '账户是否与关联企业之间频繁发生大额交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
						        	,{name : 'DQSH0351', fieldLabel : '客户行为是否存在异常',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}

									]
			           
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									{name : 'DQSH030', fieldLabel : '账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
									,{name : 'DQSH0361', fieldLabel : '账户交易是否存在异常',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
									]
			           }
			           
			    ]}//企业（高中低）2结束
		    ,{//公共（低）开始
				layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        	//{name : 'DQSH0352', fieldLabel : '客户行为是否存在异常',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
							         ]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[  
									//{name : 'DQSH0362', fieldLabel : '账户交易是否存在异常',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
							    	]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									//{name : '', fieldLabel : '',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						   ]
			           
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
									//{name : '', fieldLabel : '',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        	]
			           }
			           
			    ]}//公共（低）结束
		    ,{//合规处（权限）开始
		    	
				layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        {name : 'FXQ012', fieldLabel : '是否发生具有异常特征的大额现金交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							       ,{name : 'FXQ010', fieldLabel : '反洗钱交易监测记录',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : zlFXQ010Store,resizable : true,valueField : 'key',displayField : 'value'}
							    	
							         ]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[  {name : 'FXQ013', fieldLabel : '是否发生具有异常特征的非面对面交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
									,{name : 'FXQ016', fieldLabel : '是否频繁进行异常交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
						        
							    	]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[
							        {name : 'FXQ014', fieldLabel : '是否存在多次涉及跨境异常交易报告',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							        ,{name : 'FXQ015', fieldLabel : '代办业务是否存在异常情况',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
								      ]
			           },
			           {
			        	   columnWidth : .25,
							layout : 'form',
							items :[ 
							          {name : 'FXQ026', fieldLabel : '客户所在行政区域是否存在严重犯罪',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
							        ,{name : 'FXQ011', fieldLabel : '是否被列入中国发布或承认的应实施反洗钱监控措施的名单',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
									 ]
			           }
			           
			    ]}//合规处（权限）结束
		    
		    
		    
	       ]});//fxqIndexInfoPanel 结束
//	items:[
//	       {
//	    	
//			layout : 'column',//横排列
//		    items:[
//		           {
//		        	   columnWidth : .25,
//						layout : 'form',
//						items :[
//						        {	
//						        	name : 'CUST_ID', fieldLabel : '用户编号',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
//						        	,{name : 'CITIZENSHIP', fieldLabel : '国籍',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : conStore,valueField : 'key',displayField : 'value'}
//					        	  	,{name : 'NATION_CODE', fieldLabel : '客户注册地',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : conStore,valueField : 'key',displayField : 'value'}
//					        	  	,{name : 'CREATE_BRANCH_NO', fieldLabel : '开户行',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : zlXD000271Store,valueField : 'key',displayField : 'value'}
//					        		,{name : 'FXQ021', fieldLabel : '与客户建立业务关系的渠道',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//									,{name : 'FLAG_AGENT', fieldLabel : '客户是否为代理开户',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//									,{name : 'DQSH036', fieldLabel : '账户交易是否存在异常',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        	,{name : 'DQSH011', fieldLabel : '账户现金交易是否与客户职业特性不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        	,{name : 'DQSH032', fieldLabel : '账户资金交易频度、金额是否与其经营背景不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        	,{name : 'FXQ008', fieldLabel : '是否涉及风险提示信息或权威媒体报道信息',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        	,{name : 'DQSH008', fieldLabel : '客户留存的证件及信息是否存在疑点或矛盾',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        	,{name : 'CURRENT_AUM', fieldLabel : 'AUM(人民币)',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
//							     	,{name : 'DQSH010', fieldLabel : '账户是否频繁发生外币现钞存取业务',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//								    ,{name : 'FXQ022', fieldLabel : '是否在规范证券市场上市',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//									,{name : 'DQSH028', fieldLabel : '联系人的身份',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : zlFXQ_DQSH028Store,resizable : true,valueField : 'key',displayField : 'value'}
//									//,{name : 'DQSH004', fieldLabel : '联系人与帐户持有人的关系',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
//								 	 
//						         ]
//		           },
//		           {
//		        	   columnWidth : .25,
//						layout : 'form',
//						items :[  {name : 'CUST_TYPE', fieldLabel : '客户类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store:typeStore,valueField : 'key',displayField : 'value'}
//								,{name : 'BIRTHDAY', fieldLabel : '出生日期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
//								,{name : 'INDUST_TYPE', fieldLabel : '行业分类',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'lovcombo',store : zlXD000055Store, resizable: true,valueField : 'key',displayField : 'value'}
//								,{name : 'FXQ007', fieldLabel : '客户办理的业务',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'lovcombo',store : zlFXQ007Store,resizable : true,valueField : 'key',displayField : 'value'}
//								,{name : 'DQSH026', fieldLabel : '法定代表人证件是否过期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//								,{name : 'DQSH002', fieldLabel : '客户是否无法取得联系',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH035', fieldLabel : '客户行为是否存在异常',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH012', fieldLabel : '账户是否频繁发生大额的网上银行交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						    	,{name : 'DQSH033', fieldLabel : '账户交易对手及资金用途是否与其经营背景不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH007', fieldLabel : '客户是否无正当理由拒绝更新证件',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'FXQ011', fieldLabel : '是否被列入中国发布或承认的应实施反洗钱监控措施的名单',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH017', fieldLabel : '账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH021', fieldLabel : '账户是否经常由他人代为办理业务',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//								,{name : 'FXQ024', fieldLabel : '客户是否存在隐名股东或匿名股东',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//								,{name : 'FXQ023', fieldLabel : '客户的股权或控制权结构',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : zlFXQ023Store,resizable : true,valueField : 'key',displayField : 'value'}
//						    	,{name : '', fieldLabel : '',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield',hidden:true}
//						    	]
//		           },
//		           {
//		        	   columnWidth : .25,
//						layout : 'form',
//						items :[
//						        {name : 'CUST_NAME', fieldLabel : '客户名称',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
//						        ,{name : 'CAREER_TYPE', fieldLabel : '客户职业',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : careerTypeStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'ENT_SCALE_CK', fieldLabel : '企业规模',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : entScaleRhStore,resizable : true,valueField : 'key',displayField : 'value'}
//					        	//,{name : 'DQSH002', fieldLabel : '客户是否无法取得联系',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH004', fieldLabel : '联系人与帐户持有人的关系',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
//						        ,{name : 'DQSH027', fieldLabel : '联系人证件是否过期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//								,{name : 'DQSH003', fieldLabel : '联系时间',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
//						        ,{name : 'DQSH023', fieldLabel : '当前账户状态是否正常',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH013', fieldLabel : '账户是否与公司账户之间发生频繁或大额的交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH034', fieldLabel : '账户是否与关联企业之间频繁发生大额交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH009', fieldLabel : '账户是否频繁发生大额现金交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH015', fieldLabel : '账户资金是否快进快出，不留余额或少留余额',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH016', fieldLabel : '账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'FXQ009', fieldLabel : '客户或其亲属、关系密切人等是否属于外国政要',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						    	,{name : 'DQSH031', fieldLabel : '账户是否频繁收取与其经营业务明显无关的汇款',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						    	,{name : 'FXQ025', fieldLabel : '客户办理的业务',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'lovcombo',store : zlFXQ025Store,resizable : true,valueField : 'key',displayField : 'value'}
//						       ,{name : '', fieldLabel : '',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield',hidden:true}
//								]
//		           },
//		           {
//		        	   columnWidth : .25,
//						layout : 'form',
//						items :[
//						        {name : 'MGR_ID', fieldLabel : '客户归属客户经理',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
//						        ,{name : 'IDENT_TYPE_DS', fieldLabel : '身份证明文件种类',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : indentStore,resizable : true,valueField : 'key',displayField : 'value'}
//					        	,{name : 'IDENT_TYPE_DG', fieldLabel : '客户证件类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : indentStore,resizable : true,valueField : 'key',displayField : 'value'}
//					        	,{name : 'DQSH001', fieldLabel : '证件是否过期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//					        	,{name : 'DQSH025', fieldLabel : '企业证件是否过期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//								,{name : 'DQSH005', fieldLabel : '预计证件更新时间',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH006', fieldLabel : '未及时更新证件的理由',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH020', fieldLabel : '账户是否频繁发生跨境交易，且金额大于1万美元',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH029', fieldLabel : '账户是否与自然人账户之间发生频繁或大额的交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH022', fieldLabel : '客户是否提前偿还贷款，且与其财务状况明显不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH019', fieldLabel : '账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH018', fieldLabel : '账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        ,{name : 'DQSH014', fieldLabel : '账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						    	,{name : 'DQSH030', fieldLabel : '账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						    	,{name : '', fieldLabel : '',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield',hidden:true}
//						    	,{name : '', fieldLabel : '',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield',hidden:true}
//								      ]
//		           }
//		           
//		    ]},{
//		    	
//				layout : 'column',//横排列
//			    items:[
//			           {
//			        	   columnWidth : .5,
//							layout : 'form',
//							items :[
//							        {name : 'DQSH037', fieldLabel : '联系人与帐户持有人的关系说明',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textarea'}
//							        ,{name : 'DQSH038', fieldLabel : '联系人的身份说明',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textarea'}
//							         
//							        ]
//			           }
//			           ]
//		    },
//		    {
//		    	
//				layout : 'column',//横排列
//			    items:[
//			           {
//			        	   columnWidth : .25,
//							layout : 'form',
//							items :[
//							        {name : 'FXQ012', fieldLabel : '是否发生具有异常特征的大额现金交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//							       ,{name : 'FXQ010', fieldLabel : '反洗钱交易监测记录',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : zlFXQ010Store,resizable : true,valueField : 'key',displayField : 'value'}
//							    	
//							         ]
//			           },
//			           {
//			        	   columnWidth : .25,
//							layout : 'form',
//							items :[  {name : 'FXQ013', fieldLabel : '是否发生具有异常特征的非面对面交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//									,{name : 'FXQ016', fieldLabel : '是否频繁进行异常交易',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//						        
//							    	]
//			           },
//			           {
//			        	   columnWidth : .25,
//							layout : 'form',
//							items :[
//							        {name : 'FXQ014', fieldLabel : '是否存在多次涉及跨境异常交易报告',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//							        ,{name : '', fieldLabel : '',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield',hidden:true}
//									]
//			           },
//			           {
//			        	   columnWidth : .25,
//							layout : 'form',
//							items :[
//							        {name : 'FXQ015', fieldLabel : '代办业务是否存在异常情况',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'combo',store : ifStore,resizable : true,valueField : 'key',displayField : 'value'}
//							        ,{name : '', fieldLabel : '',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield',hidden:true}
//									 ]
//			           }
//			           
//			    ]}
//			           /*,
//		    {
//		    	
//				layout : 'column',//横排列
//			    items:[
//			           {
//					
//			           }]
//		    }*/
//	]






//*******************************


var customerView = [
		{
			title : '反洗钱风险等级调整',
			hideTitle : true,
			type : 'form',
			autoLoadSeleted : true,
			groups : [ {
				columnCount : 2,
				fields : [ {
					name : 'CUST_GRADE_ID',
					hidden : true
				}, 'CUST_ID', 'CUST_NAME', {
					name : 'FXQ_RISK_LEVEL',
					text : '反洗钱风险等级',
					translateType : 'FXQ_RISK_LEVEL',
					allowBlank : false
				}, {
					name : 'LAST_UPDATE_USER',
					text : '最后更新人'
				}, 'CUST_TYPE','FLAG_FP','OLD_FXQ_RISK_LEVEL'
				
				],
				fn : function(CUST_GRADE_ID, CUST_ID, CUST_NAME,
						FXQ_RISK_LEVEL, LAST_UPDATE_USER, CUST_TYPE,FLAG_FP,OLD_FXQ_RISK_LEVEL) {
					
					LAST_UPDATE_USER.readOnly = true;
					LAST_UPDATE_USER.cls = 'x-readOnly';
					CUST_ID.readOnly = true;
					CUST_ID.cls = 'x-readOnly';
					CUST_NAME.readOnly = true;	
					CUST_NAME.cls = 'x-readOnly';
					CUST_TYPE.hidden = true;
					FLAG_FP.hidden=true;
					OLD_FXQ_RISK_LEVEL.hidden=true;
					return [ CUST_GRADE_ID, CUST_ID, CUST_NAME, FXQ_RISK_LEVEL,
							LAST_UPDATE_USER, CUST_TYPE,FLAG_FP,OLD_FXQ_RISK_LEVEL];
				}
			},
			{
				columnCount:1,
				fields:[{
					name : 'INSTRUCTION',
					text : '调整说明',
					xtype:'textarea',
					allowBlank: false
				}],
				fn:function(INSTRUCTION){
					return [INSTRUCTION];
				}
			}],
			formButtons : [
					{
						text : '提交',
						fn : function(formPanel, basicForm) {
							if (!formPanel.getForm().isValid()) {
								Ext.MessageBox.alert('提示', '输入有误,请检查输入项');
								return false;
							}
							
							//修改后反洗钱等级
							var riskLevel = this.contentPanel.getForm()
									.getValues().FXQ_RISK_LEVEL;
							//修改前反洗钱等级
							var oldRiskLevel = this.contentPanel.getForm()
							.getValues().OLD_FXQ_RISK_LEVEL;
							var flag_fp=this.contentPanel.getForm()
							.getValues().FLAG_FP;

							var flagRole=true;
							var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
							if (roleCodes != null && roleCodes != "") {	
								var roleArrs = roleCodes.split('$');
								for ( var i = 0; i < roleArrs.length; i++) {
									if (roleArrs[i] == "R115") {// 合规处反洗钱部经办
											flagRole=false;//合规处经办不用复评
											break;
										}
									}
							}
							
							if(flag_fp=="0"&&flagRole){
								Ext.MessageBox.alert('提示', '该客户还未进行复评，请先进行复评');
								return false;
							}
							Ext.MessageBox.confirm('提示','确定执行吗?',function(buttonId){
								if(buttonId.toLowerCase() == "no"){
									return;
								} 
							//FLAG_FP
								
							var data = formPanel.getForm().getFieldValues();
							var commintData = translateDataKey(data, 1);
							Ext.Ajax.request({
								url : basepath
										+ '/customerAntMoneyRiskGradeAdjust!save.json?riskLevel='
										+ riskLevel,
								method : 'POST',
								params : commintData,
								success : function(response) {
									
								
									
									var ret = Ext.decode(response.responseText);
									if(ret.existTask == 'existTask'){
										Ext.Msg.alert('提示', "当前客户已存在审核流程！");
									}else{
										var arrayRole=JsContext._roleId;
										var role1="R303";
										arrayRole.forEach(function(r){
											if(r=='303'){
												role1="R303";
											}else
											if(r=="305")
											{
												role1="R305";
											}else
											if(r=="115"){
												role1="R115";
											}
										});
										var instanceid = ret.instanceid;//流程实例ID
										if(role1=="R115"){
											selectUserList(instanceid,"133_a3","133_a6");//经办发起
										}else if(oldRiskLevel == 'H' || riskLevel == 'H'){
												selectUserList(instanceid,"133_a3","133_a5");//高风险
										}else{
											selectUserList(instanceid,"133_a3","133_a4");//中的风险
										}
									}
									
									
									//reloadCurrentData();
								
									//selectUserList("410883198911041012","133_a3","133_a5");//高风险
									},failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									if(resultArray == 403) {
										Ext.Msg.alert('提示', response.responseText);
									} else {
										Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
										reloadCurrentData();
									}
								}
							});
							});
							
						}},{
						text : '关闭',
						fn : function(formPanel) {
							hideCurrentView();
						}
					} 
					
					]
	},
		{
			title : '录入客户反洗钱指标',// '客户反洗钱指标查看',
			hideTitle : true,
			type : 'form',
			autoLoadSeleted : true,
			groups : [
					{
						columnCount : 2,
						fields : [ 'CUST_ID', 'CUST_NAME', 'CORE_NO',
								'CUST_TYPE', 'IDENT_TYPE', 'IDENT_NO',
								'ORG_NAME', {
									name : 'BELONG_TEAM_HEAD_NAME',
									text : '所属团队负责人',
									hiddenName : 'MGR_ID1'
								} ],
						fn : function(CUST_ID, CUST_NAME, CORE_NO, CUST_TYPE,
								IDENT_TYPE, IDENT_NO, ORG_NAME,
								BELONG_TEAM_HEAD_NAME) {
							CUST_ID.readOnly = true;
							CUST_ID.cls = 'x-readOnly';
							CUST_NAME.readOnly = true;
							CUST_NAME.cls = 'x-readOnly';
							CORE_NO.readOnly = true;
							CORE_NO.cls = 'x-readOnly';
							IDENT_TYPE.readOnly = true;
							IDENT_TYPE.cls = 'x-readOnly';
							IDENT_NO.readOnly = true;
							IDENT_NO.cls = 'x-readOnly';
							CUST_TYPE.readOnly = true;
							CUST_TYPE.cls = 'x-readOnly';
							BELONG_TEAM_HEAD_NAME.readOnly = true;
							BELONG_TEAM_HEAD_NAME.cls = 'x-readOnly';
							BELONG_TEAM_HEAD_NAME.searchField = false;
							ORG_NAME.readOnly = true;
							ORG_NAME.cls = 'x-readOnly';
							return [ CUST_ID, CUST_NAME, CORE_NO, CUST_TYPE,
									IDENT_TYPE, IDENT_NO, ORG_NAME,
									BELONG_TEAM_HEAD_NAME ];
						}
					},
					{
						columnCount : 2,
						fields : [  'FXQ007', 'FXQ008', 'FXQ009',
								'FXQ021', 'FXQ022', 'FXQ023', 'FXQ024',
								'FXQ025', 'CREATE_DATE', 'FLAG' ],
						fn : function(  FXQ007,
								FXQ008, FXQ009, FXQ021, FXQ022, FXQ023, FXQ024,
								FXQ025, CUST_TYPE, CREATE_DATE, FLAG) {
							
							FXQ007.hidden = true;
							FXQ008.hidden = true;
							FXQ009.hidden = true;
							FXQ021.hidden = true;
							FXQ022.hidden = true;
							FXQ023.hidden = true;
							FXQ024.hidden = true;
							FXQ025.hidden = true;
							CUST_TYPE.hidden = true;
							CREATE_DATE.hidden = true;
							FLAG.hidden = true;
							return [  FXQ007, FXQ008, FXQ009, FXQ021,
									FXQ022, FXQ023, FXQ024, FXQ025, CUST_TYPE,
									CREATE_DATE, FLAG ];
						}
					},
					{
						columnCount : 2,
						fields : [ 'FXQ016', 'FXQ015', 'FXQ012', 'FXQ013',
								'FXQ014','FXQ026', 'FXQ010', 'FXQ011' ],
						fn : function(FXQ016,FXQ015, FXQ012, FXQ013, FXQ014,
								FXQ026,FXQ010,FXQ011) {
							return [ FXQ016, FXQ015, FXQ012, FXQ013, FXQ014,
							         FXQ026, FXQ010,FXQ011];
						}
					}
					, {
						columnCount : 2,
						fields : [ 'KYJYBG_SBSJ' ],
						fn : function(KYJYBG_SBSJ) {
							KYJYBG_SBSJ.hidden = true;
							return [ KYJYBG_SBSJ ];
						}
					}, {
						columnCount : 1,
						fields : [ 'INSTRUCTION_CONTENT1' ],
						fn : function(INSTRUCTION_CONTENT1) {
							//INSTRUCTION_CONTENT1.hidden = true;
							return [ INSTRUCTION_CONTENT1 ];
						}
					}, {
						columnCount : 1,
						fields : [ 'productCharactgrid1' ],
						fn : function() {
							return [ productCharactgrid1 ];
						}
					} ],
			formButtons : [
			               {
			            	   text:'添加说明',
			            	   id : 'fxqAdd',
			            	   fn : function(formPanel,basicForm){
			            		   
			            		/* getCurrentView().contentPanel.form.findField(
									"KYJYBG_SBSJ").show();*/
			            		   getCurrentView().contentPanel.form.findField(
									"INSTRUCTION_CONTENT1").show();
			            		   getCurrentView().contentPanel.form.findField(
									"INSTRUCTION_CONTENT1").allowBlank=false;
			            	   }
			            		   
			               },	
					{
						text : '保存',
						id : 'fxqSave',
						fn : function(formPanel, basicForm) {
							if (!formPanel.getForm().isValid()) {
				                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
				                return false;
				            }
							debugger;
							var custId = this.contentPanel.getForm()
									.getValues().CUST_ID;
							var custName = this.contentPanel.getForm()
									.getValues().CUST_NAME;
							var custType = this.contentPanel.getForm()
									.getValues().CUST_TYPE;
							var fxq007 = this.contentPanel.getForm()
									.getValues().FXQ007;
							var fxq008 = this.contentPanel.getForm()
									.getValues().FXQ008;
							var fxq009 = this.contentPanel.getForm()
									.getValues().FXQ009;
							var fxq010 = this.contentPanel.getForm()
									.getValues().FXQ010;
							var fxq011 = this.contentPanel.getForm()
									.getValues().FXQ011;
							var fxq012 = this.contentPanel.getForm()
									.getValues().FXQ012;
							var fxq013 = this.contentPanel.getForm()
									.getValues().FXQ013;
							var fxq014 = this.contentPanel.getForm()
									.getValues().FXQ014;
							var fxq015 = this.contentPanel.getForm()
									.getValues().FXQ015;
							var fxq016 = this.contentPanel.getForm()
									.getValues().FXQ016;
							var fxq021 = this.contentPanel.getForm()
									.getValues().FXQ021;
							var fxq022 = this.contentPanel.getForm()
									.getValues().FXQ022;
							var fxq023 = this.contentPanel.getForm()
									.getValues().FXQ023;
							var fxq024 = this.contentPanel.getForm()
									.getValues().FXQ024;
							var fxq025 = this.contentPanel.getForm()
									.getValues().FXQ025;
							var fxq026=this.contentPanel.getForm().getValues().FXQ026;
							var createDate = this.contentPanel.getForm()
									.getValues().CREATE_DATE;
							var flag = this.contentPanel.getForm().getValues().FLAG;
							var instructtonContent1 = this.contentPanel
									.getForm().getValues().INSTRUCTION_CONTENT1;
							var kyjybgSbsj = this.contentPanel
								.getForm().getValues().KYJYBG_SBSJ
							Ext.Msg.wait('正在保存数据，请稍后......', '系统提示');
							Ext.Ajax
									.request({
										url : basepath
												+ '/acrmACustFxqIndexZL!save.json',
										method : 'POST',
										params : {
											'custId' : custId,
											'custName' : custName,
											'custType' : custType,
											'FXQ007' : fxq007,
											'FXQ008' : fxq008,
											'FXQ009' : fxq009,
											'FXQ010' : fxq010,
											'FXQ011' : fxq011,
											'FXQ012' : fxq012,
											'FXQ013' : fxq013,
											'FXQ014' : fxq014,
											'FXQ015' : fxq015,
											'FXQ016' : fxq016,
											'FXQ021' : fxq021,
											'FXQ022' : fxq022,
											'FXQ023' : fxq023,
											'FXQ024' : fxq024,
											'FXQ025' : fxq025,
											'FXQ026' : fxq026,
											'createDate' : createDate,
											'flag' : flag,
											'instructtonContent1' : instructtonContent1,
											'kyjybgSbsj':kyjybgSbsj
										},
										success : function() {
											Ext.Msg.alert('提示', '操作成功！');
											reloadCurrentData();//重新加载当前数据
										}
									});
						}
					}, {
						text : '关闭',
						id : 'fxqClose',
						fn : function(formPanel) {
							hideCurrentView();
						}
					} ]
		},
		{
			title : '客户反洗钱指标查看',// '客户反洗钱指标查看',
			hideTitle : true,
			type : 'form',
			autoLoadSeleted : true,
			suspendWidth: 1,
			//Layout:'FIT',
			items : [
					fxqIndexInfoPanel,productCharactgrid
					],
			formButtons : [{
							text : '打印预览',
							id : 'fxqPrint',
							fn : function() {
								/*if (getCustomerViewByTitle('录入客户反洗钱指标').isValid()) {
									Ext.Msg.alert("提示", "查询条件格式错误，请重新输入！");
									return false;
								}*/
								printUpdateHis();
							}
				}]
		} 
		,{
		    title:'客户反洗钱指标录入',//AO录入
		    hideTitle:true,
		    type:'form',
		    autoLoadSeleted : true,
		    groups:[{
		        columnCount : 2,
		        fields:['CUST_ID','CUST_NAME','FXQ021','FXQ022',
		                'FXQ023','FXQ024','FXQ009','FXQ007','FXQ008','FXQ025','CUST_TYPE','CREATE_DATE','FLAG'],
		        fn:function(CUST_ID,CUST_NAME,FXQ021,FXQ022,
		                FXQ023,FXQ024,FXQ009,FXQ007,FXQ008,FXQ025,CUST_TYPE,CREATE_DATE,FLAG){
		            CUST_ID.readOnly = true;
		            CUST_ID.cls = 'x-readOnly';
		            CUST_NAME.readOnly = true;
		            CUST_NAME.cls = 'x-readOnly';
		            CUST_TYPE.hidden = true;
		            CREATE_DATE.hidden = true;
		            FLAG.hidden = true;
		            return [CUST_ID,CUST_NAME,FXQ021,FXQ022,
		                    FXQ023,FXQ024,FXQ009,FXQ007,FXQ008,FXQ025,CUST_TYPE,CREATE_DATE,FLAG];
		        }
		    },{
		        columnCount : 2,
		        fields:['FXQ010','FXQ011','FXQ012','FXQ013','FXQ014','FXQ015','FXQ016','FXQ026'],
		        fn:function(FXQ010,FXQ011,FXQ012,FXQ013,FXQ014,FXQ015,FXQ016,FXQ026){
		            return [FXQ010,FXQ011,FXQ012,FXQ013,FXQ014,FXQ015,FXQ016,FXQ026];
		        }
		    }],
		    formButtons:[{
		        text:'保存',
		        id:'fxqSave',
		        fn : function(formPanel,basicForm){
		            var custId = this.contentPanel.getForm().getValues().CUST_ID;
		            var custName = this.contentPanel.getForm().getValues().CUST_NAME;
		            var custType = this.contentPanel.getForm().getValues().CUST_TYPE;
		            var fxq007 = this.contentPanel.getForm().getValues().FXQ007;
		            var fxq008 = this.contentPanel.getForm().getValues().FXQ008;
		            var fxq009 = this.contentPanel.getForm().getValues().FXQ009;
		            var fxq010 = this.contentPanel.getForm().getValues().FXQ010;
		            var fxq011 = this.contentPanel.getForm().getValues().FXQ011;
		            var fxq012 = this.contentPanel.getForm().getValues().FXQ012;
		            var fxq013 = this.contentPanel.getForm().getValues().FXQ013;
		            var fxq014 = this.contentPanel.getForm().getValues().FXQ014;
		            var fxq015 = this.contentPanel.getForm().getValues().FXQ015;
		            var fxq016 = this.contentPanel.getForm().getValues().FXQ016;
		            var fxq026 = this.contentPanel.getForm().getValues().FXQ026;
		            
		            var fxq021 = this.contentPanel.getForm().getValues().FXQ021;
		            var fxq022 = this.contentPanel.getForm().getValues().FXQ022;
		            var fxq023 = this.contentPanel.getForm().getValues().FXQ023;
		            var fxq024 = this.contentPanel.getForm().getValues().FXQ024;
		            var fxq025 = this.contentPanel.getForm().getValues().FXQ025;
		            var createDate = this.contentPanel.getForm().getValues().CREATE_DATE;
		            var flag = this.contentPanel.getForm().getValues().FLAG;
		            Ext.Msg.wait('正在保存数据，请稍后......','系统提示');
		            Ext.Ajax.request({
		                url : basepath + '/acrmACustFxqIndex!save.json',
		                method : 'POST',
		                params : {
		                    'custId' :custId,
		                    'custName' : custName,
		                    'custType':custType,
		                    'FXQ007':fxq007,
		                    'FXQ008':fxq008,
		                    'FXQ009':fxq009,
		                    'FXQ010':fxq010,
		                    'FXQ011':fxq011,
		                    'FXQ012':fxq012,
		                    'FXQ013':fxq013,
		                    'FXQ014':fxq014,
		                    'FXQ015':fxq015,
		                    'FXQ016':fxq016,
		                    'FXQ021':fxq021,
		                    'FXQ022':fxq022,
		                    'FXQ023':fxq023,
		                    'FXQ024':fxq024,
		                    'FXQ025':fxq025,
		                    'FXQ026':fxq026,
		                    'createDate':createDate,
		                    'flag':flag
		                },
		                success : function() {
		                     Ext.Msg.alert('提示','操作成功！');
		                     reloadCurrentData();
		                }
		            });
		        }
		    },{
		        text:'关闭',
		        fn : function(formPanel){
		             hideCurrentView();
		        }
		    }]
		}	
		];

/********************
 * 
 */





//********************打印预览开始*************************
var printUpdateHis = function() {
	
	var taskMgr = parent.Wlj ? parent.Wlj.TaskMgr : undefined;
	var p = parent;
	for ( var i = 0; i < 10 && !taskMgr; i++) {
		p = p.parent;
		taskMgr = p.Wlj ? p.Wlj.TaskMgr : undefined;
	}
	if (taskMgr.getTask('task_print_1')) {
		taskMgr.getTask('task_print_1').close();
	}
	//获取-- 反洗钱指标查看面板中fxqIndexInfoPanel
	var cond = fxqIndexInfoPanel.getForm().getValues();
	var custId=cond.CUST_ID;
	var custName=cond.CUST_NAME;
	
	var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
	/*if (roleCodes != null && roleCodes != "") {
		
		var roleArrs = roleCodes.split('$');
		roleCodes="";
		for ( var i = 0; i < roleArrs.length; i++) {
			if (roleArrs[i] == "R115") {// 合规处反洗钱部经办
				roleCodes+=roleArrs[i]+"zl";
				}
			}
	}*/
	var cond=fxqIndexInfoPanel.getForm().getValues();
	debugger;
	
	var turl = '?custId=' + custId+'&custName='+custName+'&roleCodes='+roleCodes;//+'&UPDATE_ITEM='+cond.UPDATE_ITEM+'&UPDATE_USER='+cond.UPDATE_USER+'&START_DATE='+cond.START_DATE+'&END_DATE='+cond.END_DATE;
	turl +='&CUST_ID='+cond.CUST_ID+
		'&CORE_NO='+cond.CORE_NO+
		'&CUST_NAME='+cond.CUST_NAME+
		'&CUST_TYPE='+cond.CUST_TYPE+
		'&IDENT_TYPE1='+cond.IDENT_TYPE1+
		'&INDENT_NO1='+cond.INDENT_NO1+
		'&IDENT_EXPIRED_DATE1='+cond.IDENT_EXPIRED_DATE1+
		'&IDENT_TYPE2='+cond.IDENT_TYPE2+
		'&INDENT_NO2='+cond.INDENT_NO2+
		'&IDENT_EXPIRED_DATE2='+cond.IDENT_EXPIRED_DATE2+
		'&FLAG_AGENT='+cond.FLAG_AGENT+
		'&AGENT_NAME='+cond.AGENT_NAME+
		'&AGENT_NATION_CODE='+cond.AGENT_NATION_CODE+
		'&AGE_IDENT_TYPE='+cond.AGE_IDENT_TYPE+
		'&AGE_IDENT_NO='+cond.AGE_IDENT_NO+
		'&TEL='+cond.TEL+
		'&FXQ008='+cond.FXQ008+
		'&CUST_GRADE='+cond.CUST_GRADE+
		'&DQSH001='+cond.DQSH001+
		'&DQSH002='+cond.DQSH002+
		'&DQSH003='+cond.DQSH003+
		'&DQSH004='+cond.DQSH004+
		'&DQSH005='+cond.DQSH005+
		'&DQSH006='+cond.DQSH006+
		'&DQSH007='+cond.DQSH007+
		'&DQSH008='+cond.DQSH008+
		'&DQSH009='+cond.DQSH009+
		'&DQSH010='+cond.DQSH010+
		'&DQSH011='+cond.DQSH011+
		'&DQSH012='+cond.DQSH012+
		'&DQSH013='+cond.DQSH013+
		'&DQSH014='+cond.DQSH014+
		'&DQSH015='+cond.DQSH015+
		'&DQSH016='+cond.DQSH016+
		'&DQSH017='+cond.DQSH017+
		'&DQSH018='+cond.DQSH018+
		'&DQSH019='+cond.DQSH019+
		'&DQSH020='+cond.DQSH020+
		'&DQSH021='+cond.DQSH021+
		'&DQSH022='+cond.DQSH022+
		'&DQSH023='+cond.DQSH023+
		'&CURRENT_AUM='+cond.CURRENT_AUM+

		'&DQSH024='+cond.DQSH024+
		'&DQSH025='+cond.DQSH025+
		'&DQSH026='+cond.DQSH026+
		'&DQSH027='+cond.DQSH027+
		'&DQSH028='+cond.DQSH028+
		'&DQSH029='+cond.DQSH029+
		'&DQSH030='+cond.DQSH030+
		'&DQSH031='+cond.DQSH031+
		'&DQSH032='+cond.DQSH032+
		'&DQSH033='+cond.DQSH033+
		'&DQSH034='+cond.DQSH034+
		'&DQSH0351='+cond.DQSH0351+
		'&DQSH0352='+cond.DQSH0352+
		'&DQSH0361='+cond.DQSH0361+
		'&DQSH0362='+cond.DQSH0362+
		'&CITIZENSHIP='+cond.CITIZENSHIP+
		'&CAREER_TYPE='+cond.CAREER_TYPE+
		'&BIRTHDAY='+cond.BIRTHDAY+
		'&IF_ORG_SUB_TYPE_PER='+cond.IF_ORG_SUB_TYPE_PER+
		'&FXQ007='+cond.FXQ007+
		'&FXQ009='+cond.FXQ009+
		'&BUILD_DATE='+cond.BUILD_DATE+
		'&IF_ORG_SUB_TYPE_ORG='+cond.IF_ORG_SUB_TYPE_ORG+
		'&NATION_CODE='+cond.NATION_CODE+
		'&ENT_SCALE_CK='+cond.ENT_SCALE_CK+
		'&IN_CLL_TYPE='+cond.IN_CLL_TYPE+
		'&FXQ021='+cond.FXQ021+
		'&FXQ022='+cond.FXQ022+
		'&FXQ023='+cond.FXQ023+
		'&FXQ024='+cond.FXQ024+
		'&FXQ025='+cond.FXQ025+
		'&FXQ010='+cond.FXQ010+
		'&FXQ012='+cond.FXQ012+
		'&FXQ013='+cond.FXQ013+
		'&FXQ014='+cond.FXQ014+
		'&FXQ015='+cond.FXQ015+
		'&FXQ016='+cond.FXQ016+
		'&FXQ026='+cond.FXQ026;
		
	
	
	var tempApp = parent._APP ? parent._APP : parent.parent._APP;
	tempApp.openWindow({
		name : '打印预览',
		action : basepath
		+'/contents/pages/wlj/custmanager/antiMoney/printCustAntiMoneyCustIndex.jsp'
		//+'/contents/pages/wlj/printManager/printCustUpdateHis.jsp'
				+ turl,
		resId : 'task_print_1',
		id : 'task_print_1',
		serviceObject : false
	});
};

// ********************打印预览结束**************************




/**
 * 設置其他字段為空值
 * 
 * @param val
 * @returns
 */
/**
 * 双击事件
 */
var rowdblclick = function(tile, record) {
	if (getSelectedData() == false) {
		Ext.Msg.alert('提示', '请选择数据！');
		return false;
	}
	var custId = getSelectedData().data.CUST_ID;
	var custName = getSelectedData().data.CUST_NAME;
	parent.Wlj.ViewMgr.openViewWindow(0, custId, custName);// 客户详细信息视图
};

var beforeviewshow = function(view) {
	if (view._defaultTitle == '录入客户反洗钱指标') {//
		productCharactgrid1.show();
		view.contentPanel.getForm().findField("INSTRUCTION_CONTENT1").hide();
		view.contentPanel.getForm().findField("KYJYBG_SBSJ").hide();
		view.contentPanel.getForm().findField(
		"INSTRUCTION_CONTENT1").allowBlank=true;
		view.contentPanel.getForm().findField(
		"KYJYBG_SBSJ").allowBlank=true;
	}
	
	
	if(view._defaultTitle == '客户反洗钱指标录入'){//AO录入
       
        view.contentPanel.getForm().findField("FXQ007").hide();
        view.contentPanel.getForm().findField("FXQ008").hide();
       // view.contentPanel.getForm().findField("FXQ009").hide();

        view.contentPanel.getForm().findField("FXQ010").hide();
        view.contentPanel.getForm().findField("FXQ011").hide();
        view.contentPanel.getForm().findField("FXQ012").hide();
        view.contentPanel.getForm().findField("FXQ013").hide();
        view.contentPanel.getForm().findField("FXQ014").hide();
        view.contentPanel.getForm().findField("FXQ015").hide();
        view.contentPanel.getForm().findField("FXQ016").hide();
        view.contentPanel.getForm().findField("FXQ026").hide();
        
        
        view.contentPanel.getForm().findField("FXQ021").hide();
        view.contentPanel.getForm().findField("FXQ022").hide();
        view.contentPanel.getForm().findField("FXQ023").hide();
        view.contentPanel.getForm().findField("FXQ024").hide();
        view.contentPanel.getForm().findField("FXQ025").hide();

        var custType = getSelectedData().data.CUST_TYPE;
        var createDate = getSelectedData().data.CREATE_DATE;
        var flag = getSelectedData().data.FLAG;//新老客户标识 3 新客户 2 ,1老客户
        if(custType == '2'){//对私
//            if(flag == '3'){//新客户
//                Ext.getCmp('fxqSave').show();
//                view.contentPanel.getForm().findField("FLAG_AGENT").show();
//                view.contentPanel.getForm().findField("FXQ007").show();
//                view.contentPanel.getForm().findField("FXQ008").show();
//                view.contentPanel.getForm().findField("FXQ009").show();
//                hideGridFields(['FXQ010'],['FXQ011'],['FXQ012'],['FXQ013'],['FXQ014'],['FXQ015'],['FXQ016'],
//                        ['FXQ021'],['FXQ022'],['FXQ023'],['FXQ024'],['FXQ025']);
//                var roleCodes = __roleCodes;// 当前用户拥有的据角色编码 hideGridFields(['BL_NAME','CREDIT_LEVEL','CUST_LEVEL']);showGridFields
//                if (roleCodes != null && roleCodes != "") {
//                    var roleArrs = roleCodes.split('$');
//                    for ( var i = 0; i < roleArrs.length; i++) {
//                                if (roleArrs[i] == "R115") {//合规处反洗钱部经办
//                                    view.contentPanel.getForm().findField("FLAG_AGENT").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FLAG_AGENT").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ007").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ007").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
//                                }else if(roleArrs[i] == "R116"){//合规处反洗钱部复核
//                                    view.contentPanel.getForm().findField("FLAG_AGENT").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FLAG_AGENT").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ007").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ007").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
//                                }
//                                Ext.getCmp('fxqSave').hide();
//                        }
//                }
//            }else{//老客户
                Ext.getCmp('fxqSave').show();
                view.contentPanel.getForm().findField("FXQ007").show();
                view.contentPanel.getForm().findField("FXQ008").show();
                view.contentPanel.getForm().findField("FXQ009").show();
                hideGridFields(['FXQ010'],['FXQ011'],['FXQ012'],['FXQ013'],['FXQ014'],['FXQ015'],['FXQ016'],['FXQ026'],
                        ['FXQ021'],['FXQ022'],['FXQ023'],['FXQ024'],['FXQ025']);
        }else{//对公
//            if(flag == '3'){//新客户
//                Ext.getCmp('fxqSave').show();
//                view.contentPanel.getForm().findField("FXQ008").show();
//                view.contentPanel.getForm().findField("FXQ009").show();
//                view.contentPanel.getForm().findField("FXQ021").show();
//                view.contentPanel.getForm().findField("FXQ022").show();
//                view.contentPanel.getForm().findField("FXQ023").show();
//                view.contentPanel.getForm().findField("FXQ024").show();
//                view.contentPanel.getForm().findField("FXQ025").show();
//                hideGridFields(['FXQ010'],['FXQ011'],['FXQ012'],['FXQ013'],['FXQ014'],['FXQ015'],['FXQ016'],['FLAG_AGENT'],['FXQ007']);
//                var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
//                if (roleCodes != null && roleCodes != "") {
//                    var roleArrs = roleCodes.split('$');
//                    for ( var i = 0; i < roleArrs.length; i++) {
//                                if (roleArrs[i] == "R115") {//合规处反洗钱部经办
//                                    view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ021").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ021").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ022").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ022").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ023").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ023").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ024").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ024").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ025").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ025").setReadOnly(true);
//                                    
//                                }else if(roleArrs[i] == "R116"){//合规处反洗钱部复核
//                                    view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ021").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ021").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ022").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ022").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ023").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ023").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ024").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ024").setReadOnly(true);
//                                    view.contentPanel.getForm().findField("FXQ025").addClass("x-readOnly");
//                                    view.contentPanel.getForm().findField("FXQ025").setReadOnly(true);
//                                }
//                                Ext.getCmp('fxqSave').hide();
//                        }
//                }
//            }else{
                Ext.getCmp('fxqSave').show();
                view.contentPanel.getForm().findField("FXQ008").show();
                view.contentPanel.getForm().findField("FXQ021").show();
                view.contentPanel.getForm().findField("FXQ022").show();
                view.contentPanel.getForm().findField("FXQ023").show();
                view.contentPanel.getForm().findField("FXQ024").show();
                view.contentPanel.getForm().findField("FXQ025").show();
                hideGridFields(['FXQ010'],['FXQ011'],['FXQ012'],['FXQ013'],['FXQ014'],['FXQ015'],['FXQ016'],['FXQ026'],['FLAG_AGENT'],['FXQ007']);
                
//            }
        }
    }	

debugger;
	if (view._defaultTitle == '客户反洗钱指标查看'){
		
		// 企业个人客户共有字段（高中低）
		fxqIndexInfoPanel.form.findField("CUST_ID").show();		// 	--客户号
		fxqIndexInfoPanel.form.findField("CORE_NO").show();		// --核心客户号
		fxqIndexInfoPanel.form.findField("CUST_NAME").show(); 	//--客户姓名
		fxqIndexInfoPanel.form.findField("CUST_TYPE").show();	//--客户类型
		fxqIndexInfoPanel.form.findField("IDENT_TYPE1").show();	//--证件1类型
		fxqIndexInfoPanel.form.findField("INDENT_NO1").show();  //--证件1号码 
		fxqIndexInfoPanel.form.findField("IDENT_EXPIRED_DATE1").show();		//--证件1到期日
		fxqIndexInfoPanel.form.findField("IDENT_TYPE2").show();		//证件2类型 
		fxqIndexInfoPanel.form.findField("INDENT_NO2").show();		//--证件2号码
		fxqIndexInfoPanel.form.findField("IDENT_EXPIRED_DATE2").show();		//--证件2到期日
		fxqIndexInfoPanel.form.findField("FLAG_AGENT").show();		//客户是否为代理开户 
		fxqIndexInfoPanel.form.findField("AGENT_NAME").show();	//--代理人姓名 
		fxqIndexInfoPanel.form.findField("AGENT_NATION_CODE").show();	//	--代理人国家代码  
		fxqIndexInfoPanel.form.findField("AGE_IDENT_TYPE").show();		//	--代理人证件类型  
		fxqIndexInfoPanel.form.findField("AGE_IDENT_NO").show();		//	--代理人证件号码
		fxqIndexInfoPanel.form.findField("TEL").show();				//     	--代理人联系电话  
		fxqIndexInfoPanel.form.findField("DQSH024").show();				//--客户是否涉及反洗钱黑名单
		fxqIndexInfoPanel.form.findField("FXQ008").show();			//   --客户是否涉及风险提示信息或权威媒体报道信息
		fxqIndexInfoPanel.form.findField("CUST_GRADE").show();		//当前客户洗钱风险等级  
		
		
		//--企业个人客户共有字段 (高中)
		fxqIndexInfoPanel.form.findField("DQSH002").hide();    	//  --客户是否无法取得联系
		fxqIndexInfoPanel.form.findField("DQSH003").hide();    	//  --联系时间
		fxqIndexInfoPanel.form.findField("DQSH004").hide();    	//  --联系人与帐户持有人的关系
		fxqIndexInfoPanel.form.findField("DQSH005").hide();    	//  --预计证件更新时间
		fxqIndexInfoPanel.form.findField("DQSH006").hide();   	//  --未及时更新证件的理由
		fxqIndexInfoPanel.form.findField("DQSH007").hide();    	//  --客户是否无正当理由拒绝更新证件
		fxqIndexInfoPanel.form.findField("DQSH008").hide();    	//  --客户留存的证件及信息是否存在疑点或矛盾 
		fxqIndexInfoPanel.form.findField("DQSH009").hide();    	//  --账户是否频繁发生大额现金交易
		fxqIndexInfoPanel.form.findField("DQSH015").hide();  	//  --账户资金是否快进快出，不留余额或少留余额
		fxqIndexInfoPanel.form.findField("DQSH016").hide();   	//  --账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准
		fxqIndexInfoPanel.form.findField("DQSH017").hide();    	//  --账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付
		fxqIndexInfoPanel.form.findField("DQSH018").hide();    	//  --账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付
		fxqIndexInfoPanel.form.findField("DQSH019").hide();    	//  --账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心
		fxqIndexInfoPanel.form.findField("DQSH022").hide();    	//  --客户是否提前偿还贷款，且与其财务状况明显不符
		fxqIndexInfoPanel.form.findField("DQSH023").hide();    	//  --当前账户状态是否正常
		fxqIndexInfoPanel.form.findField("CURRENT_AUM").hide();    	//  --AUM(人民币) 对公对私
		
		//--企业个人客户共有字段 (低)
		fxqIndexInfoPanel.form.findField("DQSH0351").hide();    //  --客户行为是否存在异常   对公对私 低风险
		fxqIndexInfoPanel.form.findField("DQSH0361").hide();    //  --账户交易是否存在异常    对公对私 低风险    
		fxqIndexInfoPanel.form.findField("DQSH0352").hide();    //  --客户行为是否存在异常   对公对私 低风险
		fxqIndexInfoPanel.form.findField("DQSH0362").hide();    //  --账户交易是否存在异常    对公对私 低风险    
		
		
		
		
		//个人高中低
		fxqIndexInfoPanel.form.findField("CITIZENSHIP").hide();    	//  --国籍  对私
		fxqIndexInfoPanel.form.findField("CAREER_TYPE").hide();		//  --职业  对私
		fxqIndexInfoPanel.form.findField("BIRTHDAY").hide();   		//出生年月日     对私
		fxqIndexInfoPanel.form.findField("IF_ORG_SUB_TYPE_PER").hide();   //  --是否自贸区(对私) 客户是否为自贸区客户
		fxqIndexInfoPanel.form.findField("FXQ007").hide(); 			//   --客户办理的业务(对私)    客户在我行办理的业务包括：
		fxqIndexInfoPanel.form.findField("FXQ009").hide(); 	  	//   --客户或其亲属、关系密切人是否属于外国政要
		//---------个人高中	
		fxqIndexInfoPanel.form.findField("DQSH001").hide();    	//  --证件是否过期
		fxqIndexInfoPanel.form.findField("DQSH010").hide();    	//  --账户是否频繁发生外币现钞存取业务  对公对私
		fxqIndexInfoPanel.form.findField("DQSH011").hide();   	//  --账户现金交易是否与客户职业特性不符  对公对私
		fxqIndexInfoPanel.form.findField("DQSH012").hide();   	//  --账户是否频繁发生大额的网上银行交易  对公对私
		fxqIndexInfoPanel.form.findField("DQSH013").hide();  	//  --账户是否与公司账户之间发生频繁或大额的交易  对公对私
		fxqIndexInfoPanel.form.findField("DQSH014").hide();   	//  --账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符  对公对私
		fxqIndexInfoPanel.form.findField("DQSH020").hide();   	//  --账户是否频繁发生跨境交易，且金额大于1万美元 对公对私
		fxqIndexInfoPanel.form.findField("DQSH021").hide();   	//  --账户是否经常由他人代为办理业务  对公对私	
		
		
		
		
		//企业高中低
		fxqIndexInfoPanel.form.findField("BUILD_DATE").hide(); 	   //   --成立日期  --对公
		fxqIndexInfoPanel.form.findField("IF_ORG_SUB_TYPE_ORG").hide(); 	   //   --是否自贸区(对私)  客户是否为自贸区客户
		fxqIndexInfoPanel.form.findField("NATION_CODE").hide(); 	   //  --国家或地区代码 --注册地 对公 注册地
		fxqIndexInfoPanel.form.findField("ENT_SCALE_CK").hide(); 	  //  --企业规模  对公
		fxqIndexInfoPanel.form.findField("IN_CLL_TYPE").hide(); 	  // --行业分类  对公
		fxqIndexInfoPanel.form.findField("FXQ021").hide(); 	   //   --与客户建立业务关系的渠道  对公
		fxqIndexInfoPanel.form.findField("FXQ022").hide(); 	   //   --是否在规范证券市场上市  对公  客户是否在规范证券市场上市
		fxqIndexInfoPanel.form.findField("FXQ023").hide(); 	   //   --客户的股权或控制权结构  对公 
		fxqIndexInfoPanel.form.findField("FXQ024").hide(); 	   //   --客户是否存在隐名股东或匿名股东 对公 
		fxqIndexInfoPanel.form.findField("FXQ025").hide(); 	  //   --客户办理的业务(对公)  客户在我行办理的业务包括：

		//企业高中
		fxqIndexInfoPanel.form.findField("DQSH025").hide(); 	   //  --企业证件是否过期  对公 
		fxqIndexInfoPanel.form.findField("DQSH026").hide(); 	   //  --法定代表人证件是否过期  对公 
		fxqIndexInfoPanel.form.findField("DQSH027").hide(); 	   //  --联系人证件是否过期  对公 
		fxqIndexInfoPanel.form.findField("DQSH028").hide(); 	   //  --联系人的身份  对公 
		fxqIndexInfoPanel.form.findField("DQSH029").hide(); 	   //  --账户是否与自然人账户之间发生频繁或大额的交易  对公 
		fxqIndexInfoPanel.form.findField("DQSH030").hide(); 	  //  --账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符  对公 
		fxqIndexInfoPanel.form.findField("DQSH031").hide(); 	  //  --账户是否频繁收取与其经营业务明显无关的汇款  对公
		fxqIndexInfoPanel.form.findField("DQSH032").hide(); 	  //  --账户资金交易频度、金额是否与其经营背景不符  对公
		fxqIndexInfoPanel.form.findField("DQSH033").hide(); 	   //  --账户交易对手及资金用途是否与其经营背景不符  对公
		fxqIndexInfoPanel.form.findField("DQSH034").hide(); 	   //  --账户是否与关联企业之间频繁发生大额交易   对公

		//合规处权限
		fxqIndexInfoPanel.form.findField("FXQ010").hide();
		fxqIndexInfoPanel.form.findField("FXQ011").hide();
		fxqIndexInfoPanel.form.findField("FXQ012").hide();
		fxqIndexInfoPanel.form.findField("FXQ013").hide();
		fxqIndexInfoPanel.form.findField("FXQ014").hide();
		fxqIndexInfoPanel.form.findField("FXQ015").hide();
		fxqIndexInfoPanel.form.findField("FXQ016").hide();
		fxqIndexInfoPanel.form.findField("FXQ026").hide();
		
		//指标说明隐藏    当前角色是合规处经办 /复核时开启
		productCharactgrid.hide();
		
		var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
		if (roleCodes != null && roleCodes != "") {
			var roleArrs = roleCodes.split('$');
			for ( var i = 0; i < roleArrs.length; i++) {
				if (roleArrs[i] == "R115"||roleArrs[i] == "R116") {
					//合规处权限
					productCharactgrid.show();
					
					fxqIndexInfoPanel.form.findField("FXQ010").show();
					fxqIndexInfoPanel.form.findField("FXQ011").show();
					fxqIndexInfoPanel.form.findField("FXQ012").show();
					fxqIndexInfoPanel.form.findField("FXQ013").show();
					fxqIndexInfoPanel.form.findField("FXQ014").show();
					fxqIndexInfoPanel.form.findField("FXQ015").show();
					fxqIndexInfoPanel.form.findField("FXQ016").show();
					fxqIndexInfoPanel.form.findField("FXQ026").show();
				}
			}
		}
		

		var custType = getSelectedData().data.CUST_TYPE;//客户类型
		var custGrade= getSelectedData().data.OLD_FXQ_RISK_LEVEL;//当前等级
		
		if(custType=="2"){ //个人客户
			

			//个人高中低
			fxqIndexInfoPanel.form.findField("CITIZENSHIP").show();    	//  --国籍  对私
			fxqIndexInfoPanel.form.findField("CAREER_TYPE").show();		//  --职业  对私
			fxqIndexInfoPanel.form.findField("BIRTHDAY").show();   		//出生年月日     对私
			fxqIndexInfoPanel.form.findField("IF_ORG_SUB_TYPE_PER").show();   //  --是否自贸区(对私) 客户是否为自贸区客户
			fxqIndexInfoPanel.form.findField("FXQ007").show(); 			//   --客户办理的业务(对私)    客户在我行办理的业务包括：
			fxqIndexInfoPanel.form.findField("FXQ009").show(); 	  	//   --客户或其亲属、关系密切人是否属于外国政要
			
			if(custGrade=="H"||custGrade=="M"){
				//--企业个人客户共有字段 (高中)
				fxqIndexInfoPanel.form.findField("DQSH002").show();    	//  --客户是否无法取得联系
				fxqIndexInfoPanel.form.findField("DQSH003").show();    	//  --联系时间
				fxqIndexInfoPanel.form.findField("DQSH004").show();    	//  --联系人与帐户持有人的关系
				fxqIndexInfoPanel.form.findField("DQSH005").show();    	//  --预计证件更新时间
				fxqIndexInfoPanel.form.findField("DQSH006").show();   	//  --未及时更新证件的理由
				fxqIndexInfoPanel.form.findField("DQSH007").show();    	//  --客户是否无正当理由拒绝更新证件
				fxqIndexInfoPanel.form.findField("DQSH008").show();    	//  --客户留存的证件及信息是否存在疑点或矛盾 
				fxqIndexInfoPanel.form.findField("DQSH009").show();    	//  --账户是否频繁发生大额现金交易
				fxqIndexInfoPanel.form.findField("DQSH015").show();  	//  --账户资金是否快进快出，不留余额或少留余额
				fxqIndexInfoPanel.form.findField("DQSH016").show();   	//  --账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准
				fxqIndexInfoPanel.form.findField("DQSH017").show();    	//  --账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付
				fxqIndexInfoPanel.form.findField("DQSH018").show();    	//  --账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付
				fxqIndexInfoPanel.form.findField("DQSH019").show();    	//  --账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心
				fxqIndexInfoPanel.form.findField("DQSH022").show();    	//  --客户是否提前偿还贷款，且与其财务状况明显不符
				fxqIndexInfoPanel.form.findField("DQSH023").show();    	//  --当前账户状态是否正常
				fxqIndexInfoPanel.form.findField("CURRENT_AUM").show();    	//  --AUM(人民币) 对公对私
				
				
				//---------个人高中
				fxqIndexInfoPanel.form.findField("DQSH001").show();    	//  --证件是否过期
				fxqIndexInfoPanel.form.findField("DQSH010").show();    	//  --账户是否频繁发生外币现钞存取业务  对公对私
				fxqIndexInfoPanel.form.findField("DQSH011").show();   	//  --账户现金交易是否与客户职业特性不符  对公对私
				fxqIndexInfoPanel.form.findField("DQSH012").show();   	//  --账户是否频繁发生大额的网上银行交易  对公对私
				fxqIndexInfoPanel.form.findField("DQSH013").show();  	//  --账户是否与公司账户之间发生频繁或大额的交易  对公对私
				fxqIndexInfoPanel.form.findField("DQSH014").show();   	//  --账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符  对公对私
				fxqIndexInfoPanel.form.findField("DQSH020").show();   	//  --账户是否频繁发生跨境交易，且金额大于1万美元 对公对私
				fxqIndexInfoPanel.form.findField("DQSH021").show();   	//  --账户是否经常由他人代为办理业务  对公对私	
				
			}else
			if(custGrade=="L"){

				fxqIndexInfoPanel.form.findField("DQSH0352").show();    //  --客户行为是否存在异常   对公对私 低风险
				fxqIndexInfoPanel.form.findField("DQSH0362").show();    //  --账户交易是否存在异常    对公对私 低风险   
			}
			
		}else if(custType=="1"){//企业客户
			
			
			
			//企业高中低
			fxqIndexInfoPanel.form.findField("BUILD_DATE").show(); 	   //   --成立日期  --对公
			fxqIndexInfoPanel.form.findField("IF_ORG_SUB_TYPE_ORG").show(); 	   //   --是否自贸区(对私)  客户是否为自贸区客户
			fxqIndexInfoPanel.form.findField("NATION_CODE").show(); 	   //  --国家或地区代码 --注册地 对公 注册地
			fxqIndexInfoPanel.form.findField("ENT_SCALE_CK").show(); 	  //  --企业规模  对公
			fxqIndexInfoPanel.form.findField("IN_CLL_TYPE").show(); 	  // --行业分类  对公
			fxqIndexInfoPanel.form.findField("FXQ021").show(); 	   //   --与客户建立业务关系的渠道  对公
			fxqIndexInfoPanel.form.findField("FXQ022").show(); 	   //   --是否在规范证券市场上市  对公  客户是否在规范证券市场上市
			fxqIndexInfoPanel.form.findField("FXQ023").show(); 	   //   --客户的股权或控制权结构  对公 
			fxqIndexInfoPanel.form.findField("FXQ024").show(); 	   //   --客户是否存在隐名股东或匿名股东 对公 
			fxqIndexInfoPanel.form.findField("FXQ025").show(); 	  //   --客户办理的业务(对公)  客户在我行办理的业务包括：

			if(custGrade=="H"||custGrade=="M"){
				//--企业个人客户共有字段 (高中)
				fxqIndexInfoPanel.form.findField("FXQ009").show(); 	  	//   --客户或其亲属、关系密切人是否属于外国政要
				fxqIndexInfoPanel.form.findField("DQSH002").show();    	//  --客户是否无法取得联系
				fxqIndexInfoPanel.form.findField("DQSH003").show();    	//  --联系时间
				fxqIndexInfoPanel.form.findField("DQSH004").show();    	//  --联系人与帐户持有人的关系
				fxqIndexInfoPanel.form.findField("DQSH005").show();    	//  --预计证件更新时间
				fxqIndexInfoPanel.form.findField("DQSH006").show();   	//  --未及时更新证件的理由
				fxqIndexInfoPanel.form.findField("DQSH007").show();    	//  --客户是否无正当理由拒绝更新证件
				fxqIndexInfoPanel.form.findField("DQSH008").show();    	//  --客户留存的证件及信息是否存在疑点或矛盾 
				fxqIndexInfoPanel.form.findField("DQSH009").show();    	//  --账户是否频繁发生大额现金交易
				fxqIndexInfoPanel.form.findField("DQSH015").show();  	//  --账户资金是否快进快出，不留余额或少留余额
				fxqIndexInfoPanel.form.findField("DQSH016").show();   	//  --账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准
				fxqIndexInfoPanel.form.findField("DQSH017").show();    	//  --账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付
				fxqIndexInfoPanel.form.findField("DQSH018").show();    	//  --账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付
				fxqIndexInfoPanel.form.findField("DQSH019").show();    	//  --账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心
				fxqIndexInfoPanel.form.findField("DQSH022").show();    	//  --客户是否提前偿还贷款，且与其财务状况明显不符
				fxqIndexInfoPanel.form.findField("DQSH023").show();    	//  --当前账户状态是否正常
				fxqIndexInfoPanel.form.findField("CURRENT_AUM").show();    	//  --AUM(人民币) 对公对私
				
				//企业高中
				fxqIndexInfoPanel.form.findField("DQSH025").show(); 	   //  --企业证件是否过期  对公 
				fxqIndexInfoPanel.form.findField("DQSH026").show(); 	   //  --法定代表人证件是否过期  对公 
				fxqIndexInfoPanel.form.findField("DQSH027").show(); 	   //  --联系人证件是否过期  对公 
				fxqIndexInfoPanel.form.findField("DQSH028").show(); 	   //  --联系人的身份  对公 
				fxqIndexInfoPanel.form.findField("DQSH029").show(); 	   //  --账户是否与自然人账户之间发生频繁或大额的交易  对公 
				fxqIndexInfoPanel.form.findField("DQSH030").show(); 	  //  --账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符  对公 
				fxqIndexInfoPanel.form.findField("DQSH031").show(); 	  //  --账户是否频繁收取与其经营业务明显无关的汇款  对公
				fxqIndexInfoPanel.form.findField("DQSH032").show(); 	  //  --账户资金交易频度、金额是否与其经营背景不符  对公
				fxqIndexInfoPanel.form.findField("DQSH033").show(); 	   //  --账户交易对手及资金用途是否与其经营背景不符  对公
				fxqIndexInfoPanel.form.findField("DQSH034").show(); 	   //  --账户是否与关联企业之间频繁发生大额交易   对公
				
			}else if(custGrade=="L"){

				//--企业个人客户共有字段 (低)
				fxqIndexInfoPanel.form.findField("DQSH0351").show();    //  --客户行为是否存在异常   对公对私 低风险
				fxqIndexInfoPanel.form.findField("DQSH0361").show();    //  --账户交易是否存在异常    对公对私 低风险
			}
		}
	}
	
};



var beforeconditioninit = function(panel, app) {
	
	app.pageSize = 100;
};