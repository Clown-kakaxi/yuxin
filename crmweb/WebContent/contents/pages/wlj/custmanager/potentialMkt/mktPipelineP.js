/**
 * @description 个金客户营销流程 - 个金Pipeline汇总信息页面
 * @author luyy
 * @since 2014-07-24
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.DeptQuery.js'
	, '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
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
},{
	key : 'PROUDUCTLOADER',
	url : basepath + '/product-kinds1.json',//加载产品种类树
	parentAttr : 'CATL_PARENT',
	locateAttr : 'CATL_CODE',
	rootValue : '0',
	textField : 'CATL_NAME',
	idProperties : 'CATL_CODE',
	jsonRoot : 'json.data'
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
},{
	key : 'PROUDUCTTREE',
	loaderKey : 'PROUDUCTLOADER',
	autoScroll : true,
	rootCfg : {
		expanded : true,
		text : '银行产品树',
		autoScroll : true,
		children : []
	}
}];


var url = basepath + '/mktPipelineP.json';

var lookupTypes = ['IF_FLAG','CHECK_STAT','RISK_CHARACT'];

var fields = [{name:'ID',text:'ID',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerquery',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'AREA_NAME',text:'区域中心',xtype : 'wcombotree',innerTree:'ORGTREE',
              resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',allowBlank:false},
              {name:'DEPT_NAME',text:'营业单位名称',dataType:'string',searchField: true},
              
              {name:'PRODUCT_CATL',text:'产品种类',dataType:'string',gridField:false},
              {name:'PRODUCT_ID',text:'产品ID',dataType:'string',gridField:false},
              {name:'PRODUCT_CATL_NAME',text:'产品种类',xtype : 'wcombotree',innerTree:'PROUDUCTTREE',
                  resutlWidth:80,showField:'text',hideField:'CATL_CODE',allowBlank:false,searchField: true},
            
              {name:'IF_DEAL',text:'是否成交',translateType:'IF_FLAG',resutlWidth:'60'},    
              {name:'SALE_AMT',text:'预估销售金额',dataType:'money',resutlWidth:'60'},
              {name:'RM',text:'RM',dataType:'string'},
              {name:'BUY_AMT',text:'实际成交金额',dataType:'money',resutlWidth:'60'},
              {name: 'PRODUCT_NAME',dataType:'productChoose',hiddenName:'PRODUCT_ID',singleSelect:true,text:"购买产品"},
              {name:'VISIT_DATE',text:'第一次拜访日期',dataType:'date',resutlWidth:'80'},
              {name:'RISK_LEVEL_PERSECT',text:'客户风险预估',translateType:'RISK_CHARACT',resutlWidth:'80'},
              {name:'RISK_LEVEL',text:'实际客户风险',translateType:'RISK_CHARACT',resutlWidth:'80'},
              {name:'HARD_INFO',text:'销售难点',xtype:'textarea'},
              {name:'DEAL_DATE',text:'成交日期',dataType:'date',resutlWidth:'80'},
              {name:'ACCOUNT_DATE',text:'开户日期',dataType:'date',resutlWidth:'80'},
              {name:'REFUSE_REASON',text:'拒绝原因',dataType:'string'}
			  ];

var customerView = [{
	title:'查看',
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount:2,
		fields :['CUST_NAME','AREA_NAME','DEPT_NAME','RM','PRODUCT_CATL_NAME','SALE_AMT','BUY_AMT','PRODUCT_NAME','VISIT_DATE','RISK_LEVEL_PERSECT',
		'RISK_LEVEL','DEAL_DATE','ACCOUNT_DATE'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_CATL_NAME,SALE_AMT,
					BUY_AMT,PRODUCT_NAME,VISIT_DATE,RISK_LEVEL_PERSECT,RISK_LEVEL,DEAL_DATE,
					ACCOUNT_DATE){
				CUST_NAME.disabled=true;
				AREA_NAME.disabled=true;DEPT_NAME.disabled=true;RM.disabled=true;
				PRODUCT_CATL_NAME.disabled=true;
				SALE_AMT.disabled=true;BUY_AMT.disabled=true;
				PRODUCT_NAME.disabled=true;VISIT_DATE.disabled=true;RISK_LEVEL_PERSECT.disabled=true;
				RISK_LEVEL.disabled=true;DEAL_DATE.disabled=true;
				ACCOUNT_DATE.disabled=true;
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_CATL_NAME,SALE_AMT,BUY_AMT,PRODUCT_NAME,VISIT_DATE,RISK_LEVEL_PERSECT,RISK_LEVEL,DEAL_DATE,ACCOUNT_DATE];
			}
	},{
		  columnCount: 1,
		  fields : ['HARD_INFO','REFUSE_REASON'],
		  fn : function(HARD_INFO,REFUSE_REASON){
			  HARD_INFO.disabled=true;
			  return [HARD_INFO,REFUSE_REASON];
		  }
		}]
}];



var beforeviewshow = function(view){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
	}
};

var viewshow = function(view){
		var value = getSelectedData().data.IF_DEAL;
		if(value == '1'){
			getCurrentView().contentPanel.form.findField("DEAL_DATE").show();
			getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").show();
			getCurrentView().contentPanel.form.findField("BUY_AMT").show();
			getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
		}else if(value == '0'){
			getCurrentView().contentPanel.form.findField("DEAL_DATE").hide();
			getCurrentView().contentPanel.form.findField("BUY_AMT").hide();
			getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").hide();
			getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
		}		
};

