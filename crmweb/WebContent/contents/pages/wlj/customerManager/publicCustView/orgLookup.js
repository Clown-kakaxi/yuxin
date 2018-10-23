/**
 * @description 对公基本信息数据字典加载
 * @author ?
 * @since 2014-11-03
 */

 /**数据字典**/
var Store = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000083'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	}); 
//反洗钱风险等级
var fxqRiskStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=FXQ_RISK_LEVEL'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//企业资质
var certificateStore = new Ext.data.Store({
	sortInfo: {
    	field: 'key',
    	direction: 'ASC' 
	},
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=CUST_CETIFICATE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
	
//企业规模（银监）	XD000019
var corStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000019'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//风险等级  XD000083
var riskStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000083'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//证件类型
var cardTypeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000040'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});

//注册资本币种
var capitalCurr = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000027'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//企业隶属 XD000064
var belongStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000064'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//控股类型XD000288
var holdingStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000288'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//经济类型XD000062
var ecoStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000062'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//行业分类-主营XD000055
var indmStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000055'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});	
	
//行业分类-副营XD000056同XD000055
var indaStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000055'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});	
	
//客户评级 ---客户等级XD000082
var custGradeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000082'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
	
//是否上市 XD000343为空，使用是否上市企业：XD000156
var isOnMarStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000156'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//是否央企国企XD000181
var isSoEStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000181'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//地方政府融资平台标志XD000173
var isLocGStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000173'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//是否集团客户XD000162
var isCorStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000162'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//是否小企业XD000159
var isMcorStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000159'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//是否台资XD000182
var isTaiStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000182'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//有无进出口权XD000170
var hasIOPStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000170'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//是否我行关联方XD000167
var isRelaStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000167'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});	
//是否网银签约客户XD000168
var isNetStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000168'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});	
//是否涉农XD000164
var isRurStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000164'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});	
//是否限制行业XD000165
var isLimtStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000165'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});	
//国别代码XD000025
var countryStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000025'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});	
//行政区划XD000001
var admdivStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000001'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});	
//注册资本币种XD000027
var regCStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000027'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
//经营状况XD000240
var manaStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000240'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
	
//客户业务类型
var belongLineTypeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000309'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	});
/**
 * 是否
 */
var ifStore  = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
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
var manaPStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=XD000039'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});