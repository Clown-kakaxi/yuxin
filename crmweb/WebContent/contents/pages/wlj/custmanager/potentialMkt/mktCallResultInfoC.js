/**
 * @description 企商金客户营销流程 - 企商金拜访结果汇总信息页面
 * @author luyy
 * @since 2014-07-27
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
]);
//机构树加载条件
var condition = {
	searchType : 'SUBTREE' // 查询子机构
};

//加载机构树
var treeLoaders = [ {
	key : 'ORGTREELOADER',
	url : basepath + '/commsearch.json?condition=' + Ext.encode(condition),
	parentAttr : 'SUPERUNITID',
	locateAttr : 'UNITID',
	jsonRoot : 'json.data',
	rootValue : JsContext._orgId,
	textField : 'UNITNAME',
	idProperties : 'UNITID'
} ];


//树配置
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
}];

var autoLoadGrid = false;
var url = basepath + '/mktCallResult.json?type=2';


var fields = [{name:'ORG_ID',text:'机构',xtype : 'wcombotree',innerTree:'ORGTREE',
              resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',allowBlank:false,searchField:true,gridField:false},
              {name:'USER_NAME',text:'客户经理',xtype:'userchoose',hiddenName:'USER_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'DATE_S',text:'查询周期(开始)',searchField:true,gridField:false,cAllowBlank:false,dataType:'date'},
              {name:'DATE_E',text:'查询周期(结束)',searchField:true,gridField:false,cAllowBlank:false,dataType:'date'},
              {name:'TEAM_NAME',text:'团队名称'},
              {name:'CALL_NUM',text:'电访客户数',dataType:'numberNoDot',resutlWidth:90},
              {name:'COLD_CALL',text:'COLD_CALL客户数',dataType:'numberNoDot',resutlWidth:130},
              {name:'COLD_PER',text:'COLD_CALL成功率',dataType:'string',resutlFloat:'right',resutlWidth:120},
              {name:'VISIT',text:'约访客户数',dataType:'numberNoDot',resutlWidth:90},
              {name:'NEW_VISIT',text:'约访新客户',dataType:'numberNoDot',resutlWidth:90},
              {name:'PIPELINE',text:'转入PIPELINE客户数',dataType:'numberNoDot',resutlWidth:150},
              {name:'PIPE_PER',text:'PIPELINE成功率',dataType:'string',resutlFloat:'right',resutlWidth:100}
			  ];

var customerView = [{
	title:'客户经理拜访任务',
	type:'grid',
	url:basepath + '/mktCallResult.json?type=4',
	frame : true,
	fields:{fields:[
	                  {name:'CUST_NAME',text:'客户名称'},
					  {name:'VISIT_DATE',text:'拜访日期'},
					  {name:'IF_END',text:'是否已拜访'}
	                ]}
}];

var beforeviewshow = function(view){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
	}
	view.setParameters({
		USER_ID:getSelectedData().data.USER_ID,
		DATE_S:getConditionField("DATE_S").value,
		DATE_E:getConditionField("DATE_E").value
	});
};