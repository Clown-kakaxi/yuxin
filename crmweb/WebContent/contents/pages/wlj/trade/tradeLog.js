/***
 * 交易日志js
 */
var url = basepath+"/txLogAction.json";//交易日志查询
var lookupTypes = [{
	TYPE : 'LOOKUP_TYPE',
	url : '/txLogCodeAction.json',
	key : 'TX_CODE',
	value : 'TX_CODE',
	jsonRoot : 'json.data'
},{
	TYPE : 'LOOKUP_TYPE1',
	url : '/txLogCdAction.json',
	key : 'SRC_SYS_CD',
	value : 'SRC_SYS_CD',
	jsonRoot : 'json.data'
}];

var fields = [
	            {name: 'TX_LOG_ID', text : '交易日志标识', resutlWidth:20,hidden:true},
	  		    {name: 'TX_FW_ID', text : '交易流水号',resutlWidth:80,allowBlank:false},
	  		    {name: 'TX_ID', text : '交易标识',resutlWidth:80,allowBlank:false},
	  		    {name: 'TX_CODE', text : '交易代码',resutlWidth:80,searchField:true,translateType:'LOOKUP_TYPE',allowBlank:false},
	  		    {name: 'TX_NAME',text : '交易名称',resutlWidth:80,allowBlank:false},
	  		    {name: 'TX_CN_NAME',text : '交易中文名称',resutlWidth:80,searchField:true,allowBlank:false},	  		    
	  		    {name: 'TX_METHOD',text : '交易方式',resutlWidth:80,allowBlank:true},
	  		    {name: 'TX_DT',text : '交易日期',resutlWidth:80, xtype:'datefield',format:'Y-m-d',searchField:true,allowBlank:true},
	  		    {name: 'TX_REQ_TM',text : '交易请求时间戳',resutlWidth:80,allowBlank:true},
	  	        {name: 'TX_RES_TM',text : '交易响应时间戳',resutlWidth:80,allowBlank:true},
	  		    {name: 'TX_RESULT',text : '交易结果',resutlWidth:80,allowBlank:true},
	  		    {name: 'TX_RTN_CD',text : '交易返回码',resutlWidth:80,allowBlank:true},
	  		    {name: 'TX_RTN_MSG',text : '交易返回信息',resutlWidth:80,allowBlank:true},
	  		    {name: 'TX_SVR_IP',text : '交易服务IP地址',resutlWidth:80,allowBlank:true},
	  		    {name: 'SRC_SYS_CD',text : '源系统代码',resutlWidth:80,searchField:true,translateType:'LOOKUP_TYPE1',allowBlank:true},
	  		    {name: 'SRC_SYS_NM',text : '源系统简称',resutlWidth:80,searchField:true,allowBlank:true},
	  		    {name: 'REQ_MSG',text : '请求报文',resutlWidth:80,allowBlank:true},
	  		    {name: 'RES_MSG',text : '响应报文',resutlWidth:80,allowBlank:true}
	  		];
beforesetsearchparams=function(p){
	if(p.TX_DT)
		p.TX_DT = p.TX_DT.format('Y-m-d');
	
}
