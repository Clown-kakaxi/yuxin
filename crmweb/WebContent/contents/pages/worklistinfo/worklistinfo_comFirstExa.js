/**
 * @description 对公信息审批展现界面
 * @since ?
 * @update 2017.06.28
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
					return '第一页';
					break;
				case 1:
					return '第二页';
					break;
				case 2:
					return '第三页地址';
					break;
				case 3:
					return '第三页联系人';
					break;
				case 4:
					return '第三页联系信息';
					break;
				case 5:
					return '第三页证件信息';
					break;
				case 6:
					return '第四页';
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
	/**
	 * 证件类型
	 */
	var identTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	identTypeStore.load();
	/**
	 * 机构客户类型
	 */
	var orgCustTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000053'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	orgCustTypeStore.load();
	/**
	 * 企业规模
	 */
	var entScaleStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000019'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	entScaleStore.load();
	/**
	 * 企业所在国别\国别风险国别代码
	 */
	var comCountryStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	comCountryStore.load();
	/**
	 * 地区代码
	 */
	var dqStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	/**
	 * 投资主体
	 */
	var investTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000275'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	investTypeStore.load();
	/**
	 * 企业性质
	 */
	var entPropertyStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000059'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	entPropertyStore.load();
	/**
	 *经济类型
	 */
	var economicTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000062'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	economicTypeStore.load();
	/**
	 * 控股类型
	 */
	var comHoldTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000288'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	comHoldTypeStore.load();
	/**
	 * 关联人类型
	 */
	var staffInStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	staffInStore.load();
	/**
	 * 注册资金币种
	 */
	var saleCcyStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	saleCcyStore.load();
	/**
	 * 行业分类
	 */
	var inCllTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000002'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	inCllTypeStore.load();
	/**
	 * 合资类型
	 */
	var orgTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	/**
	 * 组织机构类别
	 */
	var zzOrgStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookupNew.json?name=COM_INS_TYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	zzOrgStore.load();
	/**
	 * 组织机构类别细分
	 */
	var zzOrgDetailStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookupNew.json?name=COM_INS_DETAIL'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	zzOrgDetailStore.load();
	/**
	 * 登记注册类型regCodeStore
	 */

	var regCodeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=REG_CODE_TYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	regCodeStore.load();

	/**
	 * 是否异地客户
	 */
	var isNotLocalEntStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000187'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		},['key','value'])
	});
	isNotLocalEntStore.load();
	/**
	 * 企业类型
	 */
	var comStore =  new Ext.data.Store({
		restful : true,
		autoLoad : true,
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
	comStore.load();
	/**
	 * 是否钢贸企业
	 */
	var isSteelEntStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000186'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	isSteelEntStore.load();
	/**
	 * 特种经营标识
	 */
	var comSpBusiStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000072'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	comSpBusiStore.load();
	/**
	 * 是否台资企业
	 */
	var ifTaiWanStore =  new Ext.data.Store( {
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
	ifTaiWanStore.load();
	/**
	 * 是否传真交易
	 */
	var ifFaxTransStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	ifFaxTransStore.load();
	/**
	 * 特殊监管区
	 */
	var orgSubTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	//AR客户标志
	var arCustFlagStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000277'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	arCustFlagStore.load();
	/**
	 * 境内外标志
	 */
	var inOutFlagStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	inOutFlagStore.load();
	/**
	 * AR客户类型
	 */
	var arCustTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000278'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	arCustTypeStore.load();
	/**
	 * 代理人国籍
	 */
	var countryStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	countryStore.load();
	/**
	 * 信用等级
	 */
	var gradeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000096'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	gradeStore.load();
	/**
	 * 隶属类型
	 */
	var subTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000064'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	subTypeStore.load();
	/**
	 * ORG_STATE机构状态
	 */
	var orgstateStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=ORG_STATE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	orgstateStore.load();
	/**
	 * 客户标识
	 */
	var custSignStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=CUST_SIGN'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	custSignStore.load();
	/**
	 * 客户类型XD000080
	 */
	var custTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	custTypeStore.load();
	var lookupTypes=[
	                 'XD000080',// 客户类别
	                 'FXQ_RISK_LEVEL',// 反洗钱风险等级
	                 'XD000081',// 客户状态
	                 'XD000084',// 是否潜在客户
	                 'XD000040',// 证件类型
	                 'XD000075',// 信贷客户状态
	                 'QUERY_AUTH',
	              	'XD000053'//客户类别
	              	,{
	 					TYPE : 'CUSTTYPE',//自定义企业类型
	 					url : '/searchCustTypeAction!searchS.json',
	 					key : 'KEY',
	 					value : 'VALUE',
	 					root : 'json.data'
	 				},{
	 					TYPE : 'CUSTTYPE2',//自定义企业类型
	 					url : '/searchCustTypeAction!searchNS.json',
	 					key : 'KEY',
	 					value : 'VALUE',
	 					root : 'json.data'
	 				},{
	 					TYPE : 'CUSTTYPEALL',//自定义企业类型
	 					url : '/searchCustTypeAction!searchALL.json',
	 					key : 'KEY',
	 					value : 'VALUE',
	 					root : 'json.data'
	 				},{
	 					TYPE : 'CUSTTYPEZM',//自定义企业类型
	 					url : '/searchCustTypeAction!searchZM.json',
	 					key : 'KEY',
	 					value : 'VALUE',
	 					root : 'json.data'
	 				},{
	 					TYPE : 'ADDRTYPE',//自定义地址类型（不要注册地址和经营地址）
	 					url : '/searchCustTypeAction!searchAddr.json',
	 					key : 'KEY',
	 					value : 'VALUE',
	 					root : 'json.data'
	 				},{
	 					TYPE : 'IDENTTYPE',//自定义证件类型（不要税务登记证、开户许可证、地税、国税）
	 					url : '/searchCustTypeAction!searchIdent.json',
	 					key : 'KEY',
	 					value : 'VALUE',
	 					root : 'json.data'
	 				},{
	 					TYPE : 'LINKTYPE',//自定义联系人类型（不要法人）
	 					url : '/searchCustTypeAction!searchLink.json',
	 					key : 'KEY',
	 					value : 'VALUE',
	 					root : 'json.data'
	 				},{
	 					TYPE : 'OPENIDENT',//开户证件类型（只有四个码值）
	 					url : '/searchCustTypeAction!searchOpen.json',
	 					key : 'KEY',
	 					value : 'VALUE',
	 					root : 'json.data'
	 				}
	];
	//产品（或服务）属于哪项《国家重点支持的高新技术领域》的范围
	var scienceRangeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=SCIENCE_RANGE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	scienceRangeStore.load();

	//企业类型(大中小微)SCIENCE_TYPE
	var scienceTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=SCIENCE_TYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	scienceTypeStore.load();
	//是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件
	var scienceHighStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=SCIENCE_HIGH'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	scienceHighStore.load();
	//科技型企业
	var scienceComStore=new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=SCIENCE_COM'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	scienceComStore.load();
	//上市地
	var marketplaceStore=new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=MARKET_PLACE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	marketplaceStore.load();
	/**
	 * 查询 第一页
	 */
	var qzComStore = new Ext.data.Store({
		restful:true,
		proxy : new Ext.data.HttpProxy({
			url:basepath+'/dealWithCom!queryComfsx.json',
			method:'get'
		}),
		reader: new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root:'json.data'
		}, ['CUST_ID', 'CORE_NO', 'LOAN_CUST_ID', 'CUST_TYPE', 'SHORT_NAME', 'CUST_NAME', 'IDENT_TYPE', 'RISK_NATION_CODE', 
		    'STAFFIN', 'CREATE_DATE', 'EN_NAME','IDENT_ID', 'IDENT_NO','IDENT_END_DATE', 'CREATE_BRANCH_NO', 'LOAN_CUST_STAT', 'SWIFT', 'LOAN_CUST_RANK','POTENTIAL_FLAG',
		    'OBU_ID','IDENT_NO2','OPEN_ID','REGIS_ID',
		    'ORG1_CUST_ID', 'ORG_CUST_TYPE', 'FLAG_CAP_DTL', 'LOAN_ORG_TYPE', 'NATION_CODE', 'IN_CLL_TYPE', 'EMPLOYEE_SCALE',
		    'INVEST_TYPE', 'ORG_TYPE', 'ENT_SCALE', 'MAIN_BUSINESS', 'CREDIT_CODE', 'HQ_NATION_CODE', 'ENT_PROPERTY',
		    'COM_HOLD_TYPE', 'BUILD_DATE', 'ENT_SCALE_CK', 'MINOR_BUSINESS', 'TOTAL_ASSETS', 
		     'ANNUAL_INCOME', 'LOAN_CARD_NO','AREA_CODE','BUSI_LIC_NO','REMARK',
		    'ORG_CUST_ID', 'LEGAL_LINKMAN_ID', 'LEGAL_REPR_IDENT_NO', 'LEGAL_REPR_IDENT_TYPE', 'LEGAL_IDENT_EXPIRED_DATE',
		    'LEGAL_REPR_NAME', 'LEGAL_LINKMAN_TYPE', 'LEGAL_LAST_UPDATE_SYS', 'LEGAL_LAST_UPDATE_TM', 'LEGAL_LAST_UPDATE_USER',
		    'BUSIINFO_CUST_ID' ,'SALE_CCY' ,'SALE_AMT' ,
		    'REGISTER_CUST_ID', 'REGISTER_DATE', 'REGISTER_AREA', 'REGISTER_CAPITAL_CURR', 'END_DATE', 'REGISTER_NO', 'REGISTER_ADDR',
		    'REG_CODE_TYPE', 'REGISTER_TYPE', 'REGISTER_CAPITAL',
		    'ADDRESS_CUST_ID0','ADDR_ID0','ADDR0','REGISTER_ADDR','ADDR_TYPE0','ADDRESS_LAST_UPDATE_SYS0',
		    'ADDRESS_LAST_UPDATE_USER0', 'ADDRESS_LAST_UPDATE_TM0',
		    'ADDRESS_CUST_ID1','ADDR_ID1','ADDR1','ADDR_TYPE1','ADDRESS_LAST_UPDATE_SYS1',
		    'ADDRESS_LAST_UPDATE_USER1','ADDRESS_LAST_UPDATE_TM1',
		    'SW_REGIS_CODE','ACC_OPEN_LICENSE','MEMBER_ID','GROUP_NO','BELONG_GROUP',
		    'NATION_REG_ID','NATION_REG_CODE','AREA_REG_ID','AREA_REG_CODE',
		    'MGR_ID','BELONG_RM','UNITID','BELONG_ORG','BELONG_BUSI_LINE',
		    'CREATE_TIME_LN','LAST_UPDATE_USER','LAST_UPDATE_TM','LAST_UPDATE_SYS'
		  ])
	});
//	// 基本信息部分 第一页（客户基础信息）
	var qzCombaseInfo = new Ext.form.FormPanel({
		 frame : true,
		 autoScroll : true,
		 title : '第一页（客户基础信息）',
		 labelWidth : 140,
		 buttonAlign : "center",
		 items:[
		        {
				xtype : 'fieldset',
				title : '基本信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[// 隐藏字段
						         {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'客户编号',name:'CUST_ID'},
						       	 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'核心客户号',name:'CORE_NO',hidden:true},
						       	 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'信贷客户号',name:'LOAN_CUST_ID',hidden:true},
						       	 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'信贷客户号',name:'LOAN_CUST_STAT',hidden:true},
						       	 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'潜在客户标识',name:'POTENTIAL_FLAG',hidden:true},
						    	 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'客户类型1',name:'CUST_TYPE',hidden:true},
						       // 显示字段
						       	 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'客户简称',name:'SHORT_NAME'},		                                           
					    		 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:50,fieldLabel:'<font color=red>\*</font>客户名称',name:'CUST_NAME',allowBlank:false,
					       			 emptyText:'请按照机构登记注册证上或批文上的名称填写',msgTarget:"side"},		                                           
					    		 {xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'<font color=red>\*</font>证件类型',name:'IDENT_TYPE',store:identTypeStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all',allowBlank:false},	
								 {xtype : 'textfield',fieldLabel : 'Obu Code',name : 'IDENT_NO2',anchor : '90%',maxLength:50,hidden:true},
								 {xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'客户类型',name:'ORG_CUST_TYPE',store:orgCustTypeStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all'},
					    		 {xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'组织机构类别',name:'LOAN_ORG_TYPE',store:zzOrgStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all'},
								 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:18,fieldLabel:'统一社会信用代码',name:'BUSI_LIC_NO' },
								 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'税务登记证编号',name:'SW_REGIS_CODE'},
								 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'AREA_REG_CODE',fieldLabel:'地税税务登记代码'}
						]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[
						       {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'所属集团',name:'BELONG_GROUP',hiddenName:'GROUP_NO',resutlWidth:80},		 		
						       {xtype:'textfield',id:'enNameId',anchor:'90%',maxLength:60,readOnly:true,cls:'x-readOnly',fieldLabel:'英文名称',name:'EN_NAME',emptyText:'如果没有英文名称，此项填写外文名称。',msgTarget:"side", blankText: '不可与存量正式客户之英文名称重名；当登记注册号类型为99（即境外机构）者，为必填项', allowBlank: true},				 
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'<font color=red>\*</font>证件号码',name:'IDENT_NO',allowBlank:false },	
				    		   {xtype:'datefield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'证件到期日',name:'IDENT_END_DATE',format:'Y-m-d'}, 
				    		   {xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'组织机构类别细分',name:'FLAG_CAP_DTL',store:zzOrgDetailStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all'},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'机构信用代码',name:'CREDIT_CODE' },  
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'开户许可证核准号',name:'ACC_OPEN_LICENSE'},
							   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'NATION_REG_CODE',fieldLabel:'国税税务登记代码'}]
					},{
						columnWidth:.5,  
						layout:'form',
						items:[		 	
						       // 主键
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'ORG1_CUST_ID',fieldLabel:'机构id',hidden:true},	
	// {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'LEGAL_ORG_CUST_ID',fieldLabel:'干系人id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'LEGAL_LINKMAN_ID',fieldLabel:'法人id',hidden:true},		       			 	
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'BUSIINFO_CUST_ID',fieldLabel:'经营id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'REGISTER_CUST_ID',fieldLabel:'注册id',hidden:true},
	// {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'ADDRESS_CUST_ID',fieldLabel:'地址客户id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'ADDR_ID0',fieldLabel:'地址0id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'ADDR_ID1',fieldLabel:'地址1id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'IDENT_ID',fieldLabel:'证件id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'MEMBER_ID',fieldLabel:'集团id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'OBU_ID',fieldLabel:'OBU证件id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'OPEN_ID',fieldLabel:'OPEN证件id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'REGIS_ID',fieldLabel:'REGIS证件id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'NATION_REG_ID',fieldLabel:'国税证件id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'AREA_REG_ID',fieldLabel:'地税证件id',hidden:true}
				    		   
				    	]
					}]
				}]
			},
			{
				xtype : 'fieldset',
				title : '分类信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[
					       		{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'企业所在国别',name:'NATION_CODE', id:'NATION_CODE',
					       			store:comCountryStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all'},
								{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'总部所在国别',name:'HQ_NATION_CODE',store:comCountryStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%' },				
								{xtype : 'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'行业类别',name:'IN_CLL_TYPE',hiddenName:'IN_CLL_TYPE_ID',maxLength:100},
								{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:6,fieldLabel:'从业人数',name:'EMPLOYEE_SCALE'},	
								{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'国别风险国别代码',name:'RISK_NATION_CODE',store:comCountryStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all'},								
								{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'投资主体',name:'INVEST_TYPE',store:investTypeStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all'}							
							   ]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[
						       	{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'AREA_CODE',fieldLabel:'地区代码',store:dqStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all'},											
						       	{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'关联人类型',name:'STAFFIN',store:staffInStore,resizable:true,valueField:'key',displayField:'value',
									mode:'local',forceSelection:true,triggerAction:'all'
									},
								{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'企业性质',name:'ENT_PROPERTY',store:entPropertyStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},	
								{xtype : 'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'经济类型',name:'REGISTER_TYPE',hiddenName:'REGISTER_TYPE_ID',maxLength:100,
									store:economicTypeStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},									
								{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'控股类型',name:'COM_HOLD_TYPE',store:comHoldTypeStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},	
								{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'企业规模(银监)',name:'ENT_SCALE',store:entScaleStore,resizable:true,valueField:'key',displayField:'value',
									mode:'local',forceSelection:true,triggerAction:'all'},
								{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'公司规模',name:'ENT_SCALE_CK',store:orgCustTypeStore,resizable:true,valueField:'key',displayField:'value',
									mode:'local',forceSelection:true,triggerAction:'all',hidden:true}														
											
								]
					}]
				}]
			},{
				xtype : 'fieldset',
				title : '注册信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[
						        {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'法定代表人姓名',name:'LEGAL_REPR_NAME' },		
						        {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:40,fieldLabel:'法定代表人证件号码',name:'LEGAL_REPR_IDENT_NO'},							
						        {xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'登记注册号类型',name:'REG_CODE_TYPE',store:regCodeStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},					    					    		
					    		{xtype:'datefield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:40,fieldLabel:'注册登记日期',name:'REGISTER_DATE',format:'Y-m-d'},						
					    		{xtype:'datefield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:40,fieldLabel:'注册登记有效期',name:'END_DATE',format:'Y-m-d',msgTarget:'side',blankText:'机构登记证书上记载的有效期最终到期日'},
					    		{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'行政区划名称',name:'REGISTER_AREA',hiddenName:'REGISTER_AREA_ID',maxLength:100 },	
					    		{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'注册资金币种',name:'REGISTER_CAPITAL_CURR',	store:saleCcyStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
								{xtype:'textarea',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:200,fieldLabel:'主营业务',name:'MAIN_BUSINESS'},
								{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'SwiftAddress',name:'SWIFT',hidden:true}	    
						]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[
						        {xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:40,fieldLabel:'法定代表人证件类型',name:'LEGAL_REPR_IDENT_TYPE',
						        	store:identTypeStore,mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',resizable : true,valueField : 'key',displayField : 'value'},			
								{xtype:'datefield',anchor:'90%',maxLength:30,fieldLabel:'法定代表人证件失效日期',name:'LEGAL_IDENT_EXPIRED_DATE',format:'Y-m-d',readOnly:true,cls:'x-readOnly'},												
								{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'登记注册号码',name:'REGISTER_NO'},		
								{xtype:'datefield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'成立日期',name:'BUILD_DATE',format:'Y-m-d',readOnly:true},
								{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:200,fieldLabel:'注册（登记）地址',name:'REGISTER_ADDR'  }	,			
								{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:200,fieldLabel:'实际经营地址',name:'ADDR0'},														
								{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'注册资金(万元)',name:'REGISTER_CAPITAL',maxLength:15	},
								{xtype:'textarea',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:200,fieldLabel:'兼营业务',name:'MINOR_BUSINESS'}
								]
					}]
				}]		
			},{
				xtype : 'fieldset',
				title : '经营信息',
				titleCollapse : false,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[
						       {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:15,fieldLabel:'预计营业收入',name:'ANNUAL_INCOME'},	
						       {xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'年销售额币别',name:'SALE_CCY',store:saleCcyStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
							]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[
						       {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:15,fieldLabel:'预计资产总额',name:'TOTAL_ASSETS'},	
								{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'年销售额(万元)',name:'SALE_AMT',maxLength:15}]
				}]		
			  }]	
				
			},{
				xtype : 'fieldset',
				title : '与我行往来信息',
				titleCollapse : false,
				collapsible : true,
//				collapsed:true,//收起
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[
						       {xtype:'datefield',anchor:'90%',maxLength:30,fieldLabel:'客户资料开立日',name:'CREATE_DATE',format:'Y-m-d',readOnly:true,cls:'x-readOnly'},
						       {xtype:'textfield',anchor:'90%',disabled:true,maxLength:30,fieldLabel:'客户归属业务条线',name:'BELONG_BUSI_LINE'},
						       {xtype:'textfield',resutlWidth:110,hiddenName:'MGR_ID',anchor:'90%',maxLength:30,fieldLabel:'归属客户经理',name:'BELONG_RM',disabled:true,cls:'x-readOnly'},
						       {xtype:'textfield',anchor:'90%',maxLength:30,fieldLabel:'最后更新系统',name:'LAST_UPDATE_SYS',readOnly:true,cls:'x-readOnly'},
						       {xtype:'textfield',resutlWidth:110,hiddenName:'MGR_ID1',anchor:'90%',maxLength:30,fieldLabel:'最后更新人',name:'LAST_UPDATE_USER',disabled:true,cls:'x-readOnly'}]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[
						       {xtype:'textfield',anchor:'90%',maxLength:30,fieldLabel:'开户行',name:'CREATE_BRANCH_NO',readOnly:true,cls:'x-readOnly'},
						       {xtype:'textfield',resutlWidth:110,hiddenName:'UNITID',anchor:'90%',maxLength:30,fieldLabel:'归属机构',name:'BELONG_ORG',disabled:true,cls:'x-readOnly'},
						       {xtype:'datefield',anchor:'90%',maxLength:30,fieldLabel:'潜在客户开户时间',name:'CREATE_TIME_LN',format:'Y-m-d',readOnly:true,cls:'x-readOnly'},
						       {xtype:'datefield',anchor:'90%',maxLength:30,fieldLabel:'最后更新时间',name:'LAST_UPDATE_TM',format:'Y-m-d',readOnly:true,cls:'x-readOnly'}
						       ]
					}]
				}]		
			},{
				xtype : 'fieldset',
				title : '中征码信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[ {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'中征码',name:'LOAN_CARD_NO' }]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[]
					}]
				}]		
			},{
				xtype : 'fieldset',
				title : '备注信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:1,  
						layout:'form',
						items:[ {xtype:'textarea',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:200,fieldLabel:'备注',name:'REMARK'}]
						}]
				}]		
			}],
		buttons:[{
			 text:'下一页',
			 handler:function(btn){
				qzComInfo.setActiveTab(1);
			 }
		}]
	});
	/**
	 * 查询客户信息 第一页
	 */

	qzComStore.load({
		params : {
			custId : custId
		},
		callback:function(){
			if(qzComStore.getCount()!=0){
				qzCombaseInfo.getForm().loadRecord(qzComStore.getAt(0));
			}
		}
	});
	/**
	 * 查询第二页
	 */
	var qzComListsStore = new Ext.data.Store({
		restful:true,
		proxy : new Ext.data.HttpProxy({
			url:basepath+'/dealWithCom!queryComsecond.json',
			method:'get'
		}),
		reader: new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root:'json.data'
		}, ['AR_CUST_FLAG','AR_CUST_TYPE','INOUT_FLAG',
		    'LNCUSTP','IF_ORG_SUB_TYPE','ORG_SUB_TYPE','ORG_TYPE','COM_SP_BUSINESS',
		    'KEY_CUST_ID','IS_LISTED_CORP','SCIENTIFIC_ENT','IS_NOT_LOCAL_ENT','IS_STEEL_ENT','IS_FAX_TRANS_CUST',
		    'IS_MATERIAL_RISK','IS_RURAL','IS_SCIENCE_TECH','ENERGY_SAVING','IS_TAIWAN_CORP','IS_NEW_CORP',
		    'SHIPPING_IND','ENVIRO_PENALTIES','IS_HIGH_POLLUTE',
		    'ISSUE_STOCK_ID','STOCK_CUST_ID','STOCK_CODE','MARKET_PLACE',
		    'COM_IDENT_TYPE','COM_SP_ORG','COM_SP_REG_DATE','COM_SP_CODE',
		    'COM_SP_SITU','COM_SP_END_DATE',  
		    'SCIENCE_CUST_ID','IF_SCIENCE','SCIENTIFIC_TERM','SCIENTIFIC_TYPE','SCIENTIFIC_RANGE','SCIENTIFIC_RATE'
	])
	});

	var qzComLists = new Ext.form.FormPanel({
		title : '第二页（客户识别）',
		frame : true,
		autoScroll : true,
		buttonAlign : "center",
		 labelWidth : 140,
		items : [{
		    items:[{
				xtype : 'fieldset',
				title : '上市信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[{xtype:'combo',id:'p2listedId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'上市公司标志',name:'IS_LISTED_CORP',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
								mode : 'local',forceSelection : true,triggerAction : 'all',emptyText : '未知'},
							{xtype:'textfield',id:'p2code',anchor:'90%',fieldLabel:'股票代码',name:'STOCK_CODE',readOnly:true,cls:'x-readOnly'}]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[ {xtype:'combo',id:'p2corpadd',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'上市地',name:'MARKET_PLACE',store:marketplaceStore,resizable : true,valueField : 'key',displayField : 'value',
					    	   mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all'}]
					}]
				}]		
			},{
				xtype : 'fieldset',
				title : '企业类型信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[{
							xtype:'combo',id:'p2lncusp',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'企业类型',name:'LNCUSTP',store:comStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',emptyText : '未知'},
							{xtype:'combo',id:'p2subId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否自贸区',name:'IF_ORG_SUB_TYPE',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'}]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[
						       {xtype:'combo',id:'p2orgsubId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'特殊监管区',name:'ORG_SUB_TYPE',store:orgSubTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						    	   mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'} ,
						       {xtype:'combo',anchor:'90%',id:'p2orgtype',readOnly:true,cls:'x-readOnly',maxLength:30,fieldLabel:'合资类型',name:'ORG_TYPE',store:orgTypeStore,resizable : true,valueField : 'key',displayField : 'value',
								   mode : 'local',forceSelection : true,maxLength:30,triggerAction : 'all',emptyText : '未知'}]
					}]
				}]		
			},{
				xtype : 'fieldset',
				title : '特种经营信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{ 
						columnWidth:.5,  
						layout:'form',
						items:[
						       {xtype:'combo',id:'p2spId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'特种经营标示',name:'COM_SP_BUSINESS',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
						    	   mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'} ,
						       {xtype:'textfield',id:'p2sporg',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'特种经营颁发机关',name:'COM_SP_ORG'},
						       {xtype:'datefield',id:'p2spregdate',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'特种经营登记日期',name:'COM_SP_REG_DATE',format:'Y-m-d'}
						      
						]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[ 
						       {xtype:'textfield',id:'p2spcode',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'特种经营许可证编号',name:'COM_SP_CODE'},
						       {xtype:'textfield',id:'p2spsitu',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'特种经营情况',name:'COM_SP_SITU'},
						       {xtype:'datefield',id:'p2spend',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'特种经营到期日期',name:'COM_SP_END_DATE',format:'Y-m-d'}
						       ]
					}]
				}]		
			},{
				xtype : 'fieldset',
				title : 'AR客户信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[{xtype:'combo',id:'p2arflagId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'AR客户标志(CSPS)',name:'AR_CUST_FLAG',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知',listeners:{}} ]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[{xtype:'combo',id:'p2artypeId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'AR客户类型(CSPS)',name:'AR_CUST_TYPE',hiddenName:'AR_CUST_TYPE',store:arCustTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',emptyText : '未知'}]
					}]
				}]		
			},{
				xtype : 'fieldset',
				title : '科技型企业信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				labelWidth : 140,
				items:[{
					layout:'column',
					items:[{
						columnWidth:0.5,  
						layout:'form',
						items:[
						       {xtype:'combo',id:'p2sciencerange',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'产品（或服务）属于哪项《国家重点支持的高新技术领域》的范围',name:'SCIENTIFIC_RANGE',store:scienceRangeStore,resizable : true,valueField : 'key',displayField : 'value',
						    	   mode : 'local',forceSelection : true,triggerAction : 'all'},
							   {xtype:'combo',id:'p2scienceorg',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'企业是否有原始性创新、集成创新、引进消化再创新等可持续的技术创新活动，而且有专门从事研发的部门或机构）',name:'IF_SCIENCE',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
						    	   mode : 'local',forceSelection : true,triggerAction : 'all'},
				    		   {xtype:'combo',id:'p2sciencetype',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'企业类型(大中小微)',name:'SCIENTIFIC_TYPE',store:scienceTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						    	   mode : 'local',forceSelection : true,triggerAction : 'all'}
							   ]
						},{
						columnWidth:0.5,  
						layout:'form',
						items:[
						       {xtype:'textfield',id:'p2sciencerate',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'企业年度研究开发费用占销售收入总额的比例(%)',name:'SCIENTIFIC_RATE',maxLength:15},					      
						       {xtype:'combo',id:'p2sciencehigh',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否具备高新企业认定、政府认定或经银行业筛查认定等其中一项条件',name:'SCIENTIFIC_TERM',store:scienceHighStore,resizable : true,valueField : 'key',displayField : 'value',
							    	 mode : 'local',forceSelection : true,triggerAction : 'all'},
							    {xtype:'combo',id:'p2science',anchor:'90%',readOnly:true,cls:'x-readOnly',cls:'x-readOnly',fieldLabel:'科技型企业类型',name:'SCIENTIFIC_ENT',store:scienceComStore,resizable : true,valueField : 'key',displayField : 'value',
								    	   mode : 'local',forceSelection : true,triggerAction : 'all'}  
						    ]
					}]
				}]		
			},{
				xtype : 'fieldset',
				title : '其他信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[
						        {xtype:'combo',id:'p2localId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否异地客户',name:'IS_NOT_LOCAL_ENT',store:comSpBusiStore,resizabe:true,valueField:'key',displayField:'value',
						        	mode:'local',forceSelection:true,triggerAction:'all',emptyText:'未知'},				        				       										        			
				        		{xtype:'combo',id:'p2steelId',readOnly:true,cls:'x-readOnly',fieldLabel:'是否钢贸行业',name:'IS_STEEL_ENT',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',emptyText:'未知'},							        					
				        											        			
						        {xtype:'combo',id:'p2faxlId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否传真交易指示标志',name:'IS_FAX_TRANS_CUST',store:ifFaxTransStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'},								        	
						        {xtype:'combo',id:'p2greenId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'节能环保项目及服务贷款',name:'ENERGY_SAVING',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'},
								{xtype:'combo',id:'p2materialId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否环境、安全等重大风险企业',name:'IS_MATERIAL_RISK',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'},
						        {xtype:'combo',id:'p2agribusilId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否为涉农贷款',name:'IS_RURAL',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'},
								{xtype:'combo',id:'p2scienceId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否科技金融行业',name:'IS_SCIENCE_TECH',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'}
						       ]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[
					            {xtype:'combo',id:'p2taiwanId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否台资企业',name:'IS_TAIWAN_CORP',store:ifTaiWanStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'},
								{xtype:'combo',id:'p2newId',anchor:'90%',readOnly:true,cls:'x-readOnly',cls:'x-readOnly',fieldLabel:'是否2年内新设立企业',name:'IS_NEW_CORP',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'},				
								{xtype:'combo',id:'p2inoutId',anchor:'90%',readOnly:true,cls:'x-readOnly',cls:'x-readOnly',fieldLabel:'境内境外标志',name:'INOUT_FLAG',store:inOutFlagStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'},							
								{xtype:'combo',id:'p2shipId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否为航运行业（银监统计）',name:'SHIPPING_IND',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'},					
								{xtype:'combo',id:'p2innoId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否发生过环保处罚事件',name:'ENVIRO_PENALTIES',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'},
								{xtype:'datefield',id:'p2pappendate',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'发生日期',name:'HAPPEN_DATE',format:'Y-m-d',hidden:true},
								{xtype:'combo',id:'p2highId',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'是否两高一剩',name:'IS_HIGH_POLLUTE',store:comSpBusiStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',emptyText:'未知'}]
					}]
				}]		
			},{
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[// 主键
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'KEY_CUST_ID',fieldLabel:'重要标识id',hidden:true},		       			 	
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'ISSUE_STOCK_ID',fieldLabel:'股票id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'SCIENCE_CUST_ID',fieldLabel:'科技型id',hidden:true},
				    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',maxLength:30,name:'STOCK_CUST_ID',fieldLabel:'股票客户id',hidden:true}   
				    		   ]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[]
					}]
				}]		
			}]
		}],
		buttons: [{
			 text:'上一页',
			 handler:function(){
				qzComInfo.setActiveTab(0);
			 }
		},{
			 text:'下一页',
			 handler:function(){
				qzComInfo.setActiveTab(2);
			 }
		}]
	});
	qzComListsStore.load({
		params : {
			custId : custId
		},
		callback:function(){
			if(qzComListsStore.getCount()!=0){
				qzComLists.getForm().loadRecord(qzComListsStore.getAt(0));
			}
		}
	});
		
//////////////////////////////////////////////地址信息///////////////////////////////////////////////////////////
	/**
	 * 地址类型
	 */
	var addrTypeStore =  new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	        field:'key',
	        direction:'ASC'
	    },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000192'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	addrTypeStore.load();
	/**
	 * 代理人国籍
	 */
	var countryStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	countryStore.load();
	
	var addrCustInfoRecord = new Ext.data.Record.create([
	    {name:'ADDR_ID'},
		{name:'ADDR_TYPE'},
		{name:'ADDR_TYPE_ORA'},
		{name:'ADDR'},
		{name:'ZIPCODE'},
		{name:'ADDRESS_CUST_ID'},
		{name:'ADDR_COUNTRY'},
		{name:'ADDR_COUNTRY_ORA'},
		{name:'ADMIN_ZONE'},
		{name:'ADMIN_ZONE_ID'},
		{name:'ADDRESS_LAST_UPDATE_SYS'},
		{name:'ADDRESS_LAST_UPDATE_USER'},
		{name:'ADDRESS_LAST_UPDATE_TM'},
		{name:'IS_ADD_FLAG'}
	]);
	
	var addrCustInfoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/dealWithComThree!queryAddr.json',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },addrCustInfoRecord)
	    
	});
	/**
	 * 潜在客户地址信息展示列
	 */
	var addrCustInfoRowNumber = new Ext.grid.RowNumberer({
	    header:'NO.',
	    width:35
	});
	var addrCustInfoCm = new Ext.grid.ColumnModel([
	                                               addrCustInfoRowNumber,
	                                               {dataIndex:'ADDR_TYPE_ORA',header:'地址类型',width : 120,sortable : true},
	                                               {dataIndex:'ADDR_COUNTRY_ORA',header:'国别',width : 120,sortable : true},
	                                               {dataIndex:'ADMIN_ZONE',header:'行政区划',width : 350,sortable : true},
	                                               {dataIndex:'ADDR',header:'详细地址',width : 350,sortable : true},
	                                               {dataIndex:'ZIPCODE',header:'邮政编码',width : 100,sortable : true},
	                                               {dataIndex:'ADDRESS_LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
	                                           ]);
	var addrGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: addrCustInfoStore,
	    cm : addrCustInfoCm,
//	    tbar: addrCustInfoTbar,
	    viewConfig : {
			getRowClass : function(record,rowIndex,rowParams,store){
				//根据是否修改状态修改背景颜色  
				if(record.data.IS_ADD_FLAG=='0'){//修改过
				  	return 'my_row_set_blue';
			  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
			  		return 'my_row_set_red';
			  	}
			}
		},
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});


	addrCustInfoStore.load({
		params : {
			custId : custId
			},
		callback: function(){
			if(!(custId==null||custId=='')){
				identGridPanel.grid.getStore();
			}
		}
	});
/////////////////////////////////////////联系人信息////////////////////////////////
	/**
	 * 联系方式类型
	 */
	var identTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	var genderTypeStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000016'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	//干系人类型
	var linkTypeStore1 = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	        field:'key',
	        direction:'ASC'
	    },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000339'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	var linkTitleStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	        field:'key',
	        direction:'ASC'
	    },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000250'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	}); 
	var comContactPersonRowNumber = new Ext.grid.RowNumberer({
	    header:'NO.',
	    width:35
	});
	// create the data columnModel
	var comContactPersonCm = new Ext.grid.ColumnModel([
	    comContactPersonRowNumber,
	    {dataIndex:'LINKMAN_TYPE_ORA',header:'干系人类型',width : 100,sortable : true},
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
	    {dataIndex:'PER_LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true},
	    {dataIndex:'PER_LAST_UPDATE_USER_NAME',header:'最后更新人',width : 100,sortable : true},
	    {dataIndex:'PER_LAST_UPDATE_TM',header:'最后修改日期',width : 120,sortable : true}
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
		{name :  'PER_IS_ADD_FLAG'},
		{name : 'PER_LAST_UPDATE_SYS'},
		{name : 'PER_LAST_UPDATE_USER'},
		{name : 'PER_LAST_UPDATE_USER_NAME'},
		{name : 'PER_LAST_UPDATE_TM'}
	]);
	// create the data store
	var comContactPersonStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath+'/dealWithComThree!queryComContactPerson.json',
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
//	    tbar: comContactPersonTbar,
	    viewConfig : {
			getRowClass : function(record,rowIndex,rowParams,store){
				//根据是否修改状态修改背景颜色  
				if(record.data.PER_IS_ADD_FLAG=='0'){//修改过
				  	return 'my_row_set_blue';
			  	}else if(record.data.PER_IS_ADD_FLAG == '1'){//新增
			  		return 'my_row_set_red';
			  	}
			}
		},
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});

	comContactPersonStore.load({
		params : {
			custId : custId
			},
		callback: function(){
			if(!(custId==null||custId=='')){
				identGridPanel.grid.getStore();
			}
		}
	});
	
/////////////////////////////////////////联系信息////////////////////////////////
	/**
	 * 联系方式类型
	 */
	var contmethTypesStore =  new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	        field:'key',
	        direction:'ASC'
	    },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=XD000193'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		},['key','value'])
	});
	//是否首选
	var isPrioriStore =  new Ext.data.Store({
		restful : true,
		autoLoad : true,
		sortInfo : {
	        field:'key',
	        direction:'ASC'
	    },
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=XD000332'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		},['key','value'])
	});
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
	    {dataIndex:'CONTMETH_LAST_UPDATE_SYS',header:'最后更新系统',width : 100,sortable : true}
	]);
	// create the data record
	var comContactInfoRecord = new Ext.data.Record.create([
	    {name:'CONTMETH_ID'},
		{name:'CONTMETH_CUST_ID'},
		{name:'CONTMETH_TYPE'},
		{name:'CONTMETH_TYPE_ORA'},
		{name:'IS_PRIORI'},
		{name:'IS_PRIORI_ORA'},
		{name:'CONTMETH_INFO'},
		{name:'CONTMETH_SEQ'},
		{name:'REMARK'},
		{name:'STAT'},
		{name:'CONTMETH_LAST_UPDATE_SYS'},
		{name:'CONTMETH_LAST_UPDATE_USER'},
		{name:'CONTMETH_LAST_UPDATE_TM'}
	]);
	// create the data store
	var comContactInfoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/dealWithComThree!queryContact.json?check=1',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },comContactInfoRecord)
	});
	var comContactGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: comContactInfoStore,
	    cm : comContactInfoCm,
	    viewConfig : {
			getRowClass : function(record,rowIndex,rowParams,store){
				//根据是否修改状态修改背景颜色  
				if(record.data.IS_ADD_FLAG=='0'){//修改过
				  	return 'my_row_set_blue';
			  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
			  		return 'my_row_set_red';
			  	}
			}
		},
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});

	comContactInfoStore.load({
		params : {
			custId : custId
			},
		callback: function(){
			if(!(custId==null||custId=='')){
				identGridPanel.grid.getStore();
			}
		}
	});
/////////////////////////////////////////证件信息////////////////////////////////
	/**
	 * 证件类型
	 */
	var identTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	/**
	 * 企业所在国别
	 */
	var comCountryStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
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
	/**
	 * 是否开户证件
	 */
	var isOpenStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000300'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});

	var identInfoRowNumber = new Ext.grid.RowNumberer({
	    header:'NO.',
	    width:35
	});

	/**
	 *证件信息展示列
	 */
	var identInfoCm = new Ext.grid.ColumnModel([
	    identInfoRowNumber,
	    {dataIndex:'LIST_IDENT_TYPE_ORA',header:'证件类型',width : 150,sortable : true},
	    {dataIndex:'LIST_IDENT_NO',header : '证件号码',width : 100,sortable : true}, 
	    {dataIndex:'IDEN_REG_DATE',header:'证件颁发日期',width : 100,sortable : true},
	    {dataIndex:'IDENT_VALID_PERIOD',header:'证件有效期',width : 100,sortable : true},
	    {dataIndex:'LIST_EXPIRED_DATE',header:'证件失效日期',width : 100,sortable : true},
	    {dataIndex:'COUNTRY_OR_REGION_ORA',header:'发证机关所在地',width : 100,sortable : true},
	    {dataIndex:'IDENT_ORG',header:'证件颁发机关',width : 100,sortable : true},
	    {dataIndex:'IDENT_CHECKING_DATE',header:'年检到期日',width : 100,sortable : true},
	    {dataIndex:'IS_OPEN_ACC_IDENT_ORA',header:'是否开户证件',width:100,sortable:true}
	]);
	/**
	 * 证件信息record
	 */
	var identInfoRecord = new Ext.data.Record.create([
			{name:'LIST_IDENT_ID'},
			{name:'IDENT_CUST_ID'},
			{name:'LIST_IDENT_TYPE'},
			{name:'LIST_IDENT_TYPE_ORA'},
			{name:'LIST_IDENT_NO'},
			{name:'IDENT_CUST_NAME'},
			{name:'IDENT_DESC'},
			{name:'COUNTRY_OR_REGION'},
			{name:'COUNTRY_OR_REGION_ORA'},
			{name:'IDENT_ORG'},
			{name:'IDENT_APPROVE_UNIT'},
			{name:'IDENT_CHECK_FLAG'},
			{name:'IDEN_REG_DATE'},
			{name:'IDENT_CHECKING_DATE'},
			{name:'IDENT_CHECKED_DATE'},
			{name:'IDENT_VALID_PERIOD'},
			{name:'IDENT_EFFECTIVE_DATE'},
			{name:'LIST_EXPIRED_DATE'},
			{name:'IDENT_VALID_FLAG'},
			{name:'IDENT_PERIOD'},
			{name:'IS_OPEN_ACC_IDENT'},
			{name:'IS_OPEN_ACC_IDENT_ORA'},
			{name:'OPEN_ACC_IDENT_MODIFIED_FLAG'},
			{name:'IDENT_MODIFIED_TIME'},
			{name:'VERIFY_DATE'},
			{name:'VERIFY_EMPLOYEE'},
			{name:'VERIFY_RESULT'},
			{name:'IDENT_LAST_UPDATE_SYS'},
			{name:'IDENT_LAST_UPDATE_USER'},
			{name:'IDENT_LAST_UPDATE_TM'},
			{name:'TX_SEQ_NO'},
			{name:'ETL_DATE'},
			{name:'IS_OPEN_ACC_IDENT_LN'}
	]);
	/**
	 * 证件信息store
	 */
	var identInfoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/dealWithComThree!queryIdent.json',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },identInfoRecord)
	});
	/**
	 * 证件信息grid
	 */
	var identGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: identInfoStore,
	    cm : identInfoCm,
	    viewConfig : {
			getRowClass : function(record,rowIndex,rowParams,store){
				//根据是否修改状态修改背景颜色  
				if(record.data.IS_ADD_FLAG=='0'){//修改过
				  	return 'my_row_set_blue';
			  	}else if(record.data.IS_ADD_FLAG == '1'){//新增
			  		return 'my_row_set_red';
			  	}
			}
		},
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	identInfoStore.load({
		params : {
			custId : custId
			},
		callback: function(){
			tempComIdentStore.removeAll();
			identInfoStore.each(function(record){
				var obj = {};
				Ext.apply(obj,record.data);
				var tempRecord = new Ext.data.Record(obj,null);
				tempComIdentStore.addSorted(tempRecord);
			});
		}
	});	
	/**
	 *  第三页（地址、联系人、证件信息）
	 */
	var qzComThreePanel = new Ext.form.FormPanel({
		frame : true,
		title : '第三页（地址、联系人、联系信息、证件信息）',
		autoScroll : true,
		buttonAlign : "center",
		items : [{
			xtype : 'fieldset',
			title : '地址信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [addrGridPanel]
		},{
			xtype : 'fieldset',
			title : '联系人信息'	,
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [comContactPersonGrid]
		},{
			xtype : 'fieldset',
			title : '联系信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [comContactGridPanel]
		},{
			xtype : 'fieldset',
			title : '证件信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [identGridPanel]
		}],
		buttons:[{
			text:'上一页',
			handler:function(){
				qzComInfo.setActiveTab(1);
			}
		},{
			text:'下一页',
			handler:function(){
				qzComInfo.setActiveTab(3);
			}
		}]
	});
	/**
	 * 第四页
	 */
	var qzComOther1InfoStore = new Ext.data.Store({
		restful:true,
		proxy : new Ext.data.HttpProxy({
			url:basepath+'/dealWithCom!queryComfourth.json',
			method:'get'
		}),
		reader: new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root:'json.data'
		}, ['GRADE_CUST_ID','AGENT_NATION_CODE','AGENT_ID','GRADE_IDENT_TYPE','TEL','AGENT_NAME',
		    'GRADE_IDENT_NO','ORG1_CUST_ID','SUPER_DEPT','ORG_STATE','YEAR_RATE','ENT_BELONG','BAS_CUS_STATE'])
	});

	// 其他信息部分 第四页（其他信息1）
	var qzComOther1Info = new Ext.form.FormPanel({
		 frame : true,
		 autoScroll : true,
		 title : '第四页（其他信息1）',
		 labelWidth : 140,
		 buttonAlign : "center",
		 items:[{
				xtype : 'fieldset',
				title : '代理人信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[
						       {xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'代理人国籍',name:'AGENT_NATION_CODE',readOnly:true,cls:'x-readOnly',store:countryStore,resizable : true,valueField : 'key',displayField : 'value',
						    	   mode : 'local',forceSelection : true,triggerAction : 'all'},
						       {xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'代理人证件类型',name:'GRADE_IDENT_TYPE',readOnly:true,cls:'x-readOnly',store:identTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						    	   mode : 'local',forceSelection : true,triggerAction : 'all'},
						       {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'代理人联系电话',name:'TEL',readOnly:true,cls:'x-readOnly'}]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[ {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'代理人户名',name:'AGENT_NAME',readOnly:true,cls:'x-readOnly'},
					    	    {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'代理人证件号码',name:'GRADE_IDENT_NO',readOnly:true,cls:'x-readOnly'}]
					}]
				}]		
			},{
				xtype : 'fieldset',
				title : '其他信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items:[{
					layout:'column',
					items:[{
						columnWidth:.5,  
						layout:'form',
						items:[	//
						       	{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'主管单位名称',name:'SUPER_DEPT'},
						       	{xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'机构状态(警示RM)',name:'ORG_STATE',readOnly:true,cls:'x-readOnly',store:orgstateStore,resizable : true,valueField : 'key',displayField : 'value',
							    	   mode : 'local',forceSelection : true,triggerAction : 'all'},
						       	{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'年化入账比例',name:'YEAR_RATE',hidden:true,maxLength:15}
					    		]
						},{
						columnWidth:.5,  
						layout:'form',
						items:[
						       {xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'隶属类型',name:'ENT_BELONG',store:subTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						    	   mode : 'local',forceSelection : true,triggerAction : 'all'},
						       {xtype:'combo',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'基本户状态',name:'BAS_CUS_STATE',readOnly:true,cls:'x-readOnly',store:orgstateStore,resizable : true,valueField : 'key',displayField : 'value',
							    	   mode : 'local',forceSelection : true,triggerAction : 'all'}
				    		  ]
					}]
				},{
					columnWidth:.5,  
					layout:'form',
					items:[ {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'代理人客户id',name:'GRADE_CUST_ID',hidden:true},
							{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'主管单位id',name:'ORG1_CUST_ID',hidden:true},
							{xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'代理人id',name:'AGENT_ID',hidden:true}]
				}]		
			}],
		buttons:[{
			 text:'上一页',
			 handler:function(btn){
				qzComInfo.setActiveTab(2);
			 }
		},{
			 text:'下一页',
			 handler:function(btn){
				qzComInfo.setActiveTab(4);
			 }
		}]
	});

	/**
	 * 查询对公的第四页信息
	 */
		qzComOther1InfoStore.load({
			params : {
				custId : custId
			},
			callback:function(){
				if(qzComOther1InfoStore.getCount()!=0){
					qzComOther1Info.getForm().loadRecord(qzComOther1InfoStore.getAt(0));
				}
			}
		});

	var qzComOther2InfoStore = new Ext.data.Store({
		restful:true,
		proxy : new Ext.data.HttpProxy({
			url:basepath+'/dealWithCom!queryComfifth.json',
			method:'get'
		}),
		reader: new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root:'json.data'
		}, ['TASK_NUMBER','INTERVIEWEE_NAME','RES_CUSTSOURCE','CUS_BUSISTATUS','CUS_OPERATEPTEL',
		    'CUS_MAJORPRODUCT','ACT_CTL_PHONE','ACT_CTL_WIFE','ACT_CTL_NAME'])
	});
	/**
	 * 第五页：持股人、供货商和买售商、抵押、关联企业、主要固定资产盈利获利情况、往来银行表
	 */
	var RowNumber = new Ext.grid.RowNumberer({
	    header:'NO.',
	    width:35
	});
	//////////////////////////////////////////////持股人信息///////////////////////////////////////////////////////////

	var addrCustInfoRecord = new Ext.data.Record.create([
	    {name:'PARTNER_ID'},
		{name:'CUST_ID'},
		{name:'PARTNER_RATE'},
		{name:'PARTNER_MONEY'},
		{name:'PARTNER'},
		{name:'LAST_UPDATE_SYS'}, 
		{name:'LAST_UPDATE_USER'},
		{name:'LAST_UPDATE_TM'}
	]);
	var addrPartnerStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/dealWithComFive!queryPartner.json',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },addrCustInfoRecord)
	    
	});
	/**
	 * 潜在客户持股人信息展示列
	 */

	var addrPartnerCm = new Ext.grid.ColumnModel([
	                                               RowNumber,
	                                               {dataIndex:'PARTNER',header:'持股人',width : 120,sortable : true},
	                                               {dataIndex:'PARTNER_RATE',header:'持股比例',width : 350,sortable : true},
	                                               {dataIndex:'PARTNER_MONEY',header:'持股金额',width : 100,sortable : true}
	                                           ]);

	/**
	 * 潜在客户持股人信息
	 */
	var partnerGroupGrid = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: addrPartnerStore,
	    cm : addrPartnerCm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	addrPartnerStore.on('beforeload',function(){
		addrPartnerStore.baseParams = {
			custId : custId
		};
	});
	//////////////////////////////////////////////供货商及买售商///////////////////////////////////////////////////////////
	/**
	 * 供货商及买售商类型
	 */
	var infSignStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=IF_SALE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	/**
	 * 供货商及买售商展示列
	 */
	var infComCm = new Ext.grid.ColumnModel([
	    RowNumber,
	    {dataIndex:'INF_TYPE',header:'类型',width : 100,sortable : true},
	    {dataIndex:'INF_RATE',header:'比例',width : 100,sortable : true}
	]);
	/**
	 * 供货商及买售商record
	 */
	var infComRecord = new Ext.data.Record.create([
		{name : 'INF_TYPE'},
		{name : 'INF_RATE'},
		{name:'CUST_ID'},
		{name:'LAST_UPDATE_SYS'}, 
		{name:'LAST_UPDATE_USER'},
		{name:'LAST_UPDATE_TM'}
	]);
	/**
	 * 供货商及买售商store
	 */
	var infComStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath+'/dealWithComFive!queryInOrOut.json',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },infComRecord)
	});

	/**
	 * 供货商及买售商grid
	 */
	var infComGrid = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: infComStore,
	    cm : infComCm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});

	infComStore.on('beforeload',function(){
		infComStore.baseParams = {
			custId : custId
		};
	});
	/////////////////////////////////////////抵押信息////////////////////////////////

	/**
	 * 抵押类型
	 */
	var arCustTypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000278'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});

	/**
	 * 抵押信息展示列
	 */
	var mortgageCm = new Ext.grid.ColumnModel([
	    RowNumber,
	    {dataIndex:'MORTGAGE_TYPE',header:'抵押类型',width : 150,sortable : true}
	]);
	/**
	 * 抵押信息record
	 */
	var mortgageRecord = new Ext.data.Record.create([
	    {name:'MORTGAGE_ID'},
		{name:'CUST_ID'},
		{name:'MORTGAGE_TYPE'},
		{name:'LAST_UPDATE_SYS'}, 
		{name:'LAST_UPDATE_USER'},
		{name:'LAST_UPDATE_TM'}
	]);
	/**
	 * 抵押信息store
	 */
	var mortgageStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/acrmFCiContmethInfo.json?check=1',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },mortgageRecord)
	});
	/**
	 * 抵押信息grid
	 */
	var mortgageGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: mortgageStore,
	    cm : mortgageCm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	mortgageStore.on('beforeload',function(){
		mortgageStore.baseParams = {
			custId : custId
		};
	});
	/////////////////////////////////////////关联企业信息////////////////////////////////

	/**
	 *关联企业信息展示列
	 */
	var relationComoCm = new Ext.grid.ColumnModel([
	    RowNumber,
	    {dataIndex:'CUST_ID',header:'编号',width : 150,sortable : true,hidden:true},
	    {dataIndex:'RELATION_COM_ID',header : '关联企业编号',width : 100,sortable : true,hidden:true}, 
	    {dataIndex:'RELATION_COM',header:'关联企业',width : 100,sortable : true},
	    {dataIndex:'COM_INS_CODE',header:'组织机构代码',width: 100,sortable:true}
	]);
	/**
	 * 关联企业信息record
	 */
	var relationComoRecord = new Ext.data.Record.create([
			{name:'CUST_ID'},
			{name:'RELATION_COM_ID'},
			{name:'RELATION_COM'},
			{name:'COM_INS_CODE'},
			{name:'LAST_UPDATE_SYS'}, 
			{name:'LAST_UPDATE_USER'},
			{name:'LAST_UPDATE_TM'}
	]);
	/**
	 * 关联企业信息store
	 */
	var relationComoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/acrmFCiContmethInfo.json?check=1',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },relationComoRecord)
	});

	/**
	 * 关联企业信息grid
	 */
	var relationGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: relationComoStore,
	    cm : relationComoCm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	relationComoStore.on('beforeload',function(){
		relationComoStore.baseParams = {
			custId : custId
		};
	});
	/////////////////////////////////////////主要固定资产信息////////////////////////////////
	/**
	 * 房产类型
	 */
	var fhtypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=F_HTYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	/**
	 * 持有类型
	 */
	var fotypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=F_OTYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	/**
	 * 使用情况
	 */
	var futypeStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=F_UTYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	/**
	 *主要固定资产展示列
	 */
	var fixedComoCm = new Ext.grid.ColumnModel([
	    RowNumber,
	    {dataIndex:'TASK_NUMBER',header:'拜访任务编号',width:150,sortable:true},
	    {dataIndex:'F_HTYPE',header:'房产类型',width:150,sortable:true},
	    {dataIndex:'F_OTYPE',header:'持有类型',width:150,sortable:true},
	    {dataIndex:'F_AREA',header:'面积（平方米）',width:150,sortable:true},
	    {dataIndex:'F_UTYPE',header:'使用状况',width:150,sortable:true},
	    {dataIndex:'F_ASSESS',header:'估价（rmb/千元）',width:150,sortable:true},
	    {dataIndex:'F_MEMO',header:'备注',width:150,sortable:true},
	    {dataIndex:'F_HOLDER',header:'持有人',width:150,sortable:true},
	    {dataIndex:'F_REGION',header:'所在区域',width:150,sortable:true},
	    {dataIndex:'F_SECURED',header:'是否已抵押',width:150,sortable:true}
	]);
	/**
	 * 主要固定资产record
	 */
	var fixedComoRecord = new Ext.data.Record.create([
			{name:'CUST_ID'},
			{name:'F_ID'},
			{name:'TASK_NUMBER'},
			{name:'F_HTYPE'},
			{name:'F_OTYPE'},
			{name:'F_AREA'},
			{name:'F_UTYPE'},
			{name:'F_ASSESS'},
			{name:'F_MEMO'},
			{name:'F_HOLDER'},
			{name:'F_REGION'},
			{name:'F_SECURED'},
			{name:'LAST_UPDATE_SYS'}, 
			{name:'LAST_UPDATE_USER'},
			{name:'LAST_UPDATE_TM'}
	]);
	/**
	 * 主要固定资产store
	 */
	var fixedComoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/acrmFCiContmethInfo.json?check=1',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },fixedComoRecord)
	});

	/**
	 * 主要固定资产信息grid
	 */
	var fixedGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: fixedComoStore,
	    cm : fixedComoCm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	fixedComoStore.on('beforeload',function(){
		relationComoStore.baseParams = {
			custId : custId
		};
	});

	/////////////////////////////////////////盈利获利情况信息////////////////////////////////

	/**
	 *盈利获利情况展示列
	 */
	var profitComoCm = new Ext.grid.ColumnModel([
	    RowNumber,
	    {dataIndex:'P_YEARS',header:'年份',width:150,sortable:true},
	    {dataIndex:'P_YEARS_END',header:'结束年份',width:150,sortable:true},
	    {dataIndex:'P_REVENUE',header:'营收',width:150,sortable:true},
	    {dataIndex:'P_GROSS',header:'毛利率',width:150,sortable:true},
	    {dataIndex:'P_PNET',header:'税后净利率',width:150,sortable:true},
	    {dataIndex:'P_MEMO',header:'备注',width:150,sortable:true}
	    
	]);
	/**
	 * 盈利获利情况record
	 */
	var profitComoRecord = new Ext.data.Record.create([
			{name:'CUST_ID'},
			{name:'P_ID'},
			{name:'TASK_NUMBER'},
			{name:'P_YEARS'},
			{name:'P_REVENUE'},
			{name:'P_GROSS'},
			{name:'P_PNET'},
			{name:'P_MEMO'},
			{name:'P_YEARS_END'},
			{name:'LAST_UPDATE_SYS'}, 
			{name:'LAST_UPDATE_USER'},
			{name:'LAST_UPDATE_TM'}
	]);
	/**
	 * 盈利获利情况store
	 */
	var profitComoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/acrmFCiContmethInfo.json?check=1',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },profitComoRecord)
	});


	/**
	 * 盈利获利情况信息grid
	 */
	var profitGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: profitComoStore,
	    cm : profitComoCm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	profitComoStore.on('beforeload',function(){
		relationComoStore.baseParams = {
			custId : custId
		};
	});

	/////////////////////////////////////////往来银行信息////////////////////////////////
	/**
	 * 往来银行类型
	 */
	var communiStore =  new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		sortInfo : {
	            field:'key',
	            direction:'ASC'
	        },
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=COM_BANK'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		},['key','value'])
	});
	/**
	 *往来银行展示列
	 */
	var commiComoCm = new Ext.grid.ColumnModel([
	    RowNumber,
	    {dataIndex:'COMMUNI_TYPE',header:'往来类型',width:150,sortable:true},
	    {dataIndex:'BANKNAME',header:'往来银行',width:150,sortable:true},
	    {dataIndex:'AVGDEPOSIT',header:'平均存款量(人民币/千元)',width:150,sortable:true}
	]);
	/**
	 * 往来银行record
	 */
	var commiComoRecord = new Ext.data.Record.create([
			{name:'CUST_ID'},
			{name:'COMMUNI_ID'},
			{name:'COMMUNI_TYPE'},
			{name:'BANKNAME'},
			{name:'AVGDEPOSIT'},
			{name:'LAST_UPDATE_SYS'}, 
			{name:'LAST_UPDATE_USER'},
			{name:'LAST_UPDATE_TM'}
	]);
	/**
	 * 往来银行store
	 */
	var commiComoStore = new Ext.data.Store({
	    restful:true,
	    proxy: new Ext.data.HttpProxy({
	        url: basepath + '/acrmFCiContmethInfo.json?check=1',
	        method:'GET'
	    }),
	    reader: new Ext.data.JsonReader({
	        root:'json.data',
	        totalProperty:'json.count'
	    },commiComoRecord)
	});

	/**
	 * 往来银行新增or修改form
	 */
	var commiComoForm = new Ext.FormPanel({
	    frame : true,
	    autoScroll : true,
	    split : true,
	    items : [
	             {xtype:'textfield',sortable:true,anchor:'90%',name:'COMMUNI_ID',hidden:true},
	             {xtype:'combo',sortable:true,anchor:'90%',name:'COMMUNI_TYPE',fieldLabel:'<font color="red">*</font>往来类型',store : communiStore,resizable : true,valueField : 'key',displayField : 'value',
	     			mode : 'local',forceSelection : true,triggerAction : 'all',allowBlank:false},
	             {xtype:'textfield',sortable:true,anchor:'90%',anchor:'90%',name:'BANKNAME',fieldLabel:'<font color="red">*</font>往来银行',allowBlank:false},
	             {xtype:'numberfield',sortable:true,anchor:'90%',anchor:'90%',name:'AVGDEPOSIT',fieldLabel:'<font color="red">*</font>平均存款量(人民币/千元)',allowBlank:false}]
	});
	/**
	 * 往来银行信息grid
	 */
	var commiGridPanel = new Ext.grid.GridPanel({
		height: 180,
	    region: 'center',
	    autoScroll: true,
	    stripeRows: true,
	    store: commiComoStore,
	    cm : commiComoCm,
	    loadMask: {
	        msg: '正在加载表格数据,请稍等...'
	    }
	});
	commiComoStore.on('beforeload',function(){
		relationComoStore.baseParams = {
			custId : custId
		};
	});
	// 其他信息部分2 第五页（其他信息2）
	var qzComOther2Info = new Ext.form.FormPanel({
		frame : true,
		title : '第五页（其他信息2）',
		autoScroll : true,
		labelWidth : 140,
		buttonAlign : "center",
		items : [{
			xtype : 'fieldset',
			title : '基础信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[	
				       		 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'客户来源 ',name:'CUS_RESOURCE'},		                                           
				    		 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'实际控制人姓名',id:'myname',name:'ACT_CTL_NAME'},		                                           
				    		 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'实际控制人电话',name:'ACT_CTL_PHONE'},	
							 {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'主要竞争对手',name:'CUS_MAJORRIVAL'}]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'行业地位',name:'CUS_BUSISTATUS'},		 		
					       {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'实际控制人配偶姓名',name:'ACT_CTL_WIFE'},				 
			    		   {xtype:'textfield',anchor:'90%',readOnly:true,cls:'x-readOnly',fieldLabel:'主要产品',name:'CUS_MAJORPRODUCT'}]
				}]
			}]		
		},{
			xtype : 'fieldset',
			title : '持股人',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [partnerGroupGrid]
		},{
			xtype : 'fieldset',
			title : '供货商及买售商',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [infComGrid]
		},{
			xtype : 'fieldset',
			title : '抵押',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [mortgageGridPanel]
		},{
			xtype : 'fieldset',
			title : '关联企业',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [relationGridPanel]	
		},{
			xtype : 'fieldset',
			title : '主要固定资产',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [fixedGridPanel]	
		},{
			xtype : 'fieldset',
			title : '盈利获利情况',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [profitGridPanel]	
		},{
			xtype : 'fieldset',
			title : '往来银行表',
			titleCollapse : true,
			collapsible : true,
			autoHeight : true,
			items : [commiGridPanel]	
		}],
		buttons:[{
			text:'上一页',
			handler:function(){
				qzComInfo.setActiveTab(3);
			}
		}]
	});

	/**
	 * 查询对公的第五页信息
	 */
	qzComOther2InfoStore.load({
		params : {
			custId : custId
		},
		callback:function(){
			if(qzComOther2InfoStore.getCount()!=0){
				qzComOther2Info.getForm().loadRecord(qzComOther2InfoStore.getAt(0));
			}
		}
	});

	/**
	 * 对公信息面板
	 */
	var qzComInfo = new Ext.TabPanel({
		activeItem : 0,
		defaults:{autoHeight: true},
		items:[qzCombaseInfo,qzComLists,qzComThreePanel,qzComOther1Info,qzComOther2Info]
	});
	var panel = new Ext.Panel({
		title:'客户复核前信息',
		autoHeight:true,
		autoWeight:true,
		items:[qzComInfo]
	});
//-------------------------------------------客户复核前信息end----------------------------------
    var bussFieldSetGrid = new Ext.form.FieldSet({
	    animCollapse :true,
	    collapsible:true,
	    title: '流程业务信息',
	    items:[grid ,panel ]
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
