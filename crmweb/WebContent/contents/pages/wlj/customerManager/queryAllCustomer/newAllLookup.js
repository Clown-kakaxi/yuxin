//注：此日期校验不通过的提示信息,不能移除
if(Ext.form.DateField){
   Ext.apply(Ext.form.DateField.prototype, {
      invalidText : "{0} 是无效的日期 - 必须符合格式： yyyy-mm-dd"
   });
}
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
		url : basepath + '/lookup.json?name=COM_INS_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
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
		url : basepath + '/lookup.json?name=COM_INS_DETAIL'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
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
/**
 * 隶属类型
 */
var subTypeStore =  new Ext.data.Store({
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
///////////////////////////////////////////////////////////////////////需后期添加码值/////////////////////////////////////////////
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
		TYPE : 'CUSTTYPE',//自定义企业类型，特殊监管区：保税内  的码值
		url : '/searchCustTypeAction!searchS.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'json.data'
	},{
		TYPE : 'CUSTTYPE2',//自定义企业类型，特殊监管区：保税区外的码值
		url : '/searchCustTypeAction!searchNS.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'json.data'
	},{
		TYPE : 'CUSTTYPEALL',//自定义企业类型， 特殊监管区：所有的码值
		url : '/searchCustTypeAction!searchALL.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'json.data'
	},{
		TYPE : 'CUSTTYPEZM1',//自定义企业类型，特殊监管区：是否自贸区为“是”，且企业类型为“保税区内”时的码值
		url : '/searchCustTypeAction!searchZM1.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'json.data'
	},{
		TYPE : 'CUSTTYPEZM2',//自定义企业类型，特殊监管区：是否自贸区为“是”，且企业类型为“保税区外”时的码值
		url : '/searchCustTypeAction!searchZM2.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'json.data'
	},{
		TYPE : 'CUSTTYPENZM1',//自定义企业类型，特殊监管区：是否自贸区为“否”，且企业类型为“保税区内”时的码值
		url : '/searchCustTypeAction!searchNZM1.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'json.data'
	},{
		TYPE : 'CUSTTYPENZM2',//自定义企业类型，特殊监管区：是否自贸区为“否”，且企业类型为“保税区外”时的码值
		url : '/searchCustTypeAction!searchNZM2.json',
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
	},{
		TYPE : 'REGISTERCUR',//注册资金币种
		url : '/searchCustTypeAction!searchIcon.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'json.data'
	},{
		TYPE : 'COMTYPE',//企业类型去除个金
		url : '/searchCustTypeAction!searchcomType.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'json.data'
	},{//add by liuming 20170816
		TYPE : 'OPENIDENTALL',//开户证件类型
		url : '/searchCustTypeAction!searchAll.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'json.data'
	},{//add by liuming 20170817
		TYPE : 'REGISTERCURALL',//注册资金币种
		url : '/searchCustTypeAction!searchIconAll.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'json.data'
	},{//add by liuming 20170820
		TYPE : 'IDENTTYPE',//查询OP页面的证件类型1、证件类型2的交集(除个人证件类型)
		url : '/searchCustTypeAction!searchIdentType.json',
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
