/**
*@description 360风险偏好信息  对私客户视图
*@author:xiebz
*@since:2014-07-19
*/
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
		]);

var detailView = !JsContext.checkGrant('riskPrefer_detail');
var needCondition = false;
var lookupTypes = ['CUST_RISK_CHARACT','IF_FLAG','RISK_EVA_STAT'];

var custId =_custId;
var url = basepath+'/riskPreferInfo.json?custId='+custId;



var fields = [
  		    {name: 'CUST_Q_ID',hidden : true},
  		    {name: 'CUST_NAME',gridField:false},
  		    {name: 'CUST_ID',hidden : true},
  		    {name: 'EVALUATE_DATE', text : '评测日期',xtype:'datefield',format:'Y-m-d',dataType:'date',readOnly:true,disabled:!JsContext.checkGrant('privateRiskPreferInfo_AOOP')},                                   
  		    {name: 'INDAGETE_QA_SCORING',text:'评测分数',resutlWidth:60,readOnly:true,disabled:!JsContext.checkGrant('privateRiskPreferInfo_AOOP')},
  		    {name: 'CUST_RISK_CHARACT', text : '风险偏好等级', translateType:'CUST_RISK_CHARACT',readOnly:true,disabled:!JsContext.checkGrant('privateRiskPreferInfo_AOOP')}, 
  		    {name: 'LIMIT_DATE',  text : '有效期',resutlWidth:70,xtype:'datefield',format:'Y-m-d',dataType:'date',readOnly:true,disabled:!JsContext.checkGrant('privateRiskPreferInfo_AOOP')},
  		    {name: 'EVALUATE_SOURCE', text : '评测来源',readOnly:true,disabled:!JsContext.checkGrant('privateRiskPreferInfo_AOOP')}, 
  		    {name: 'NAME', text : '评测人',readOnly:true,disabled:!JsContext.checkGrant('privateRiskPreferInfo_AOOP')}, 
  		    
  		    {name: 'Q_STAT', text : '评估状态',translateType:'RISK_EVA_STAT',gridField:false,readOnly:true},
  		    {name: 'CUST_OTHER_INFO', text : '客户其他信息',gridField:false,readOnly:true},
//  		    {name: 'ADJUSTMENT_VALUE', text : '调整值',resutlWidth:70,xtype:'datefield',format:'Y-m-d',dataType:'date'},
//  		    {name: 'RISK_CHARACT_TYPE', text : '风险特性分类',translateType:'CUSTOMER_SOURCE_TYPE',allowBlank:false},
//  		    {name: 'RISK_BEAR_ABILITY', text:'风险承受能力',translateType:'REMIND_TYPE',multiSelect:true,multiSeparator:',',resutlWidth: 100},
  		    {name: 'EVALUATE_RELAT_TELEPHONE',  text : '评估人联系电话',gridField:false,readOnly:true},
//  		    {name: 'EVALUATE_INST',  text : '评估人机构',gridField:false},
  		    {name: 'ORG_NAME',  text : '评估人机构',gridField:false,readOnly:true},
  		    {name: 'HIS_FLAG',  text : '历史标志',translateType:'IF_FLAG',gridField:false,readOnly:true}
  		   ];

var detailFormViewer = [{
	fields : ['CUST_Q_ID','CUST_ID','CUST_NAME','EVALUATE_DATE','INDAGETE_QA_SCORING','CUST_RISK_CHARACT','LIMIT_DATE','EVALUATE_SOURCE','NAME',
	          'Q_STAT','CUST_OTHER_INFO','EVALUATE_RELAT_TELEPHONE','ORG_NAME','HIS_FLAG'],
	fn : function(CUST_Q_ID,CUST_ID,CUST_NAME,EVALUATE_DATE,INDAGETE_QA_SCORING,CUST_RISK_CHARACT,LIMIT_DATE,EVALUATE_SOURCE,NAME,
	          Q_STAT,CUST_OTHER_INFO,EVALUATE_RELAT_TELEPHONE,ORG_NAME,HIS_FLAG){
		CUST_Q_ID.cls = 'x-readOnly';
		CUST_ID.cls = 'x-readOnly';
		CUST_NAME.cls = 'x-readOnly';
		EVALUATE_DATE.cls = 'x-readOnly';
		INDAGETE_QA_SCORING.cls = 'x-readOnly';
		CUST_RISK_CHARACT.cls = 'x-readOnly';
		LIMIT_DATE.cls = 'x-readOnly';
		EVALUATE_SOURCE.cls = 'x-readOnly';
		NAME.cls = 'x-readOnly';
        Q_STAT.cls = 'x-readOnly';
        CUST_OTHER_INFO.cls = 'x-readOnly';
        EVALUATE_RELAT_TELEPHONE.cls = 'x-readOnly';
        ORG_NAME.cls = 'x-readOnly';
        HIS_FLAG.cls = 'x-readOnly';
		return [CUST_Q_ID,CUST_ID,CUST_NAME,EVALUATE_DATE,INDAGETE_QA_SCORING,CUST_RISK_CHARACT,LIMIT_DATE,EVALUATE_SOURCE,NAME,
		          Q_STAT,CUST_OTHER_INFO,EVALUATE_RELAT_TELEPHONE,ORG_NAME,HIS_FLAG];
	}
}];
var tbar = [
	new Com.yucheng.crm.common.NewExpButton({
	    formPanel : 'searchCondition',
	    hidden:JsContext.checkGrant('riskPrefer_export'),
	    url : basepath+'/riskPreferInfo.json?custId='+custId
	})
];

/**修改和详情面板滑入之前判断是否选择了数据**/
var beforeviewshow = function(view){
	if(view == getDetailView()){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	}
};
var formCfgs = {
		formButtons : [{
			text : '关闭',
			fn : function(formPanel){
				 hideCurrentView();
			}
		}]
};