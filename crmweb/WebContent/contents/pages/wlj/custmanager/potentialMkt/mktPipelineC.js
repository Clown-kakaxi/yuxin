/**
 * @description 企商金客户营销流程 - 企商金Pipeline汇总信息页面
 * @author luyy
 * @since 2014-07-26
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.DeptQuery.js'
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
}];


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


var url = basepath + '/mktPipelineC.json';

var lookupTypes = ['IF_FLAG','CASE_TYPE','ACC0600002','GRADE_PERSECT','COMP_TYPE','SP_LEVEL'];

var fields = [{name:'ID',text:'ID',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerquery',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'AREA_NAME',text:'区域中心',xtype : 'wcombotree',innerTree:'ORGTREE',
              resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',allowBlank:false},
              {name:'DEPT_NAME',text:'营业单位名称',dataType:'string',searchField: true},
              {name:'CASE_TYPE',text:'案件类型',translateType:'CASE_TYPE',searchField:true},
              {name:'APPLY_AMT',text:'申请额度',dataType:'number'},
              {name:'RM',text:'rm',dataType:'string',gridField:false},
              {name:'CASE_TYPE',text:'案件类型',translateType:'CASE_TYPE',gridField:true},
              {name:'VISIT_DATE',text:'第一次上门拜访日期',dataType:'date',gridField:true},
              {name:'MAIN_INSURE',text:'主担保方式',translateType:'ACC0600002',gridField:false},
              {name:'MAIN_AMT',text:'主授信额度',dataType:'number',gridField:false},
              {name:'COMBY_AMT',text:'搭配授信额度',dataType:'number',gridField:false},
              {name:'GRADE_PERSECT',text:'客户预评级',translateType:'GRADE_PERSECT',gridField:false},
              {name:'COMP_TYPE',text:'企业类型',translateType:'COMP_TYPE',gridField:true},
              {name:'HARD_INFO',text:'营销难点',xtype:'textarea',gridField:false},
              {name:'CP_HARD_INFO',text:'CP-Project营销难点',xtype:'textarea',gridField:false},
              {name:'DD_DATE',text:'DD文件收齐日期',dataType:'date',gridField:false},
              {name:'SX_DATE',text:'授信文件收齐日期',dataType:'date',gridField:false},
              {name:'GRADE_LEVEL',text:'客户最终评级',translateType:'GRADE_PERSECT',gridField:false},
              {name:'COCO_DATE',text:'CO-CO MEETING 日期',dataType:'date',gridField:false},
              {name:'COCO_INFO',text:'CO-CO MEETING结论',xtype:'textarea',gridField:false},
              {name:'XD_CA_DATE',text:'信贷系统CA提交日期',dataType:'date',gridField:false},
              {name:'CA_FORM',text:'信贷系统CA提交额度框架',dataType:'string',gridField:false},
              {name:'QA_DATE',text:'信审提问Q&A日期',dataType:'date',gridField:false},
              {name:'RM_DATE',text:'RM回复信审提问日期',dataType:'date',gridField:false},
              {name:'CC_DATE',text:'CreditCall日期',dataType:'date',gridField:false},
              {name:'XS_CC_DATE',text:'信审提出CreditCall意见日期',dataType:'date',gridField:false},
              {name:'RM_C_DATE',text:'RM反馈CreditCall意见日期',dataType:'date',gridField:false},
              {name:'CO',text:'对应CO',dataType:'string',gridField:false},
              {name:'XZ_CA_DATE',text:'信贷系统CA修正日期',dataType:'date',gridField:false},
              {name:'XZ_CA_FORM',text:'信贷系统CA修正额度框架',dataType:'string',gridField:false},
              {name:'USE_DATE_P',text:'客户预计用款日期',dataType:'date',gridField:false},
              {name:'SP_LEVEL',text:'审批层级',translateType:'SP_LEVEL',gridField:false},
              {name:'CC_OPEN_DATE',text:'CC召开日期',dataType:'date',gridField:false},
              {name:'IF_SURE',text:'额度是否核准',translateType:'IF_FLAG',gridField:false},
              {name:'INSURE_AMT',text:'核准金额',dataType:'number',gridField:false},
              {name:'INSURE_FORM',text:'最终核准额度框架',dataType:'string',gridField:false},
              {name:'DELINE_REASON',text:'未核准原因及说明',xtype:'textarea',gridField:false},
              {name:'XD_HZ_DATE',text:'信贷系统核准日期',dataType:'date',gridField:false},
              {name:'IF_ACCEPT',text:'客户是否接受核准额度',translateType:'IF_FLAG',gridField:false},
              {name:'NOACCEPT_REASON',text:'客户不接受核准额度原因',xtype:'textarea',gridField:false},
              {name:'CTR_C_DATE',text:'合同出具日期',dataType:'date',gridField:false},
              {name:'CTR_S_DATE',text:'合同签约日期',dataType:'date',gridField:false},
              {name:'MORTGAGE_DATE',text:'抵质押登记日期',dataType:'date',gridField:false},
              {name:'FILE_UP_DATE',text:'RM提交所有对保文件至授信合同部日期',dataType:'date',gridField:false},
              {name:'SX_CTR_DATE',text:'授信合同部出具瑕疵单或启用单日期',dataType:'date',gridField:false},
              {name:'CTR_PROBLEM',text:'合同若有瑕疵请列明',xtype:'textarea',gridField:false},
              {name:'PROBLEM_DATE',text:'RM反馈瑕疵日期',dataType:'date',gridField:false},
              {name:'AMT_USE_DATE',text:'系统额度启用日期',dataType:'date',gridField:false},
              {name:'ACCOUNT_DATE',text:'开户日期',dataType:'date',gridField:false},
              {name:'PAY_DATE',text:'实际拨款日期',dataType:'date',gridField:false}
			  ];


var customerView = [{
	title:'查看',
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount:2,
		fields :['CUST_NAME','GROUP_NAME','AREA_NAME','DEPT_NAME','RM','CASE_TYPE','APPLY_AMT','VISIT_DATE','MAIN_INSURE','MAIN_AMT',
		         'COMBY_AMT','GRADE_PERSECT','COMP_TYPE','DD_DATE','SX_DATE','GRADE_LEVEL','COCO_DATE','XD_CA_DATE','QA_DATE','RM_DATE',
		         'CC_DATE','XS_CC_DATE','RM_C_DATE','XZ_CA_DATE','USE_DATE_P','SP_LEVEL','CC_OPEN_DATE','IF_SURE','INSURE_AMT','XD_HZ_DATE',
		         'IF_ACCEPT','CTR_C_DATE','CTR_S_DATE','MORTGAGE_DATE','FILE_UP_DATE','SX_CTR_DATE','PROBLEM_DATE','AMT_USE_DATE','ACCOUNT_DATE','PAY_DATE'],
			fn:function(CUST_NAME,GROUP_NAME,AREA_NAME,DEPT_NAME,RM,CASE_TYPE,APPLY_AMT,VISIT_DATE,MAIN_INSURE,MAIN_AMT,
					COMBY_AMT,GRADE_PERSECT,COMP_TYPE,DD_DATE,SX_DATE,GRADE_LEVEL,COCO_DATE,XD_CA_DATE,QA_DATE,RM_DATE,
					CC_DATE,XS_CC_DATE,RM_C_DATE,XZ_CA_DATE,USE_DATE_P,SP_LEVEL,CC_OPEN_DATE,IF_SURE,INSURE_AMT,XD_HZ_DATE,
					IF_ACCEPT,CTR_C_DATE,CTR_S_DATE,MORTGAGE_DATE,FILE_UP_DATE,SX_CTR_DATE,PROBLEM_DATE,AMT_USE_DATE,ACCOUNT_DATE,PAY_DATE){
						
						CUST_NAME.readOnly = true;GROUP_NAME.readOnly = true;AREA_NAME.readOnly = true;DEPT_NAME.readOnly = true;RM.readOnly = true;CASE_TYPE.readOnly = true;APPLY_AMT.readOnly = true;VISIT_DATE.readOnly = true;MAIN_INSURE.readOnly = true;MAIN_AMT.readOnly = true;
					COMBY_AMT.readOnly = true;GRADE_PERSECT.readOnly = true;COMP_TYPE.readOnly = true;DD_DATE.readOnly = true;SX_DATE.readOnly = true;GRADE_LEVEL.readOnly = true;COCO_DATE.readOnly = true;XD_CA_DATE.readOnly = true;QA_DATE.readOnly = true;RM_DATE.readOnly = true;
					CC_DATE.readOnly = true;XS_CC_DATE.readOnly = true;RM_C_DATE.readOnly = true;XZ_CA_DATE.readOnly = true;USE_DATE_P.readOnly = true;SP_LEVEL.readOnly = true;CC_OPEN_DATE.readOnly = true;IF_SURE.readOnly = true;INSURE_AMT.readOnly = true;XD_HZ_DATE.readOnly = true;
					IF_ACCEPT.readOnly = true;CTR_C_DATE.readOnly = true;CTR_S_DATE.readOnly = true;MORTGAGE_DATE.readOnly = true;FILE_UP_DATE.readOnly = true;SX_CTR_DATE.readOnly = true;PROBLEM_DATE.readOnly = true;AMT_USE_DATE.readOnly = true;ACCOUNT_DATE.readOnly = true;PAY_DATE.readOnly = true;
					
					CUST_NAME.cls = 'readOnly';GROUP_NAME.cls = 'readOnly';AREA_NAME.cls = 'readOnly';DEPT_NAME.cls = 'readOnly';RM.cls = 'readOnly';CASE_TYPE.cls = 'readOnly';APPLY_AMT.cls = 'readOnly';VISIT_DATE.cls = 'readOnly';MAIN_INSURE.cls = 'readOnly';MAIN_AMT.cls = 'readOnly';
					COMBY_AMT.cls = 'readOnly';GRADE_PERSECT.cls = 'readOnly';COMP_TYPE.cls = 'readOnly';DD_DATE.cls = 'readOnly';SX_DATE.cls = 'readOnly';GRADE_LEVEL.cls = 'readOnly';COCO_DATE.cls = 'readOnly';XD_CA_DATE.cls = 'readOnly';QA_DATE.cls = 'readOnly';RM_DATE.cls = 'readOnly';
					CC_DATE.cls = 'readOnly';XS_CC_DATE.cls = 'readOnly';RM_C_DATE.cls = 'readOnly';XZ_CA_DATE.cls = 'readOnly';USE_DATE_P.cls = 'readOnly';SP_LEVEL.cls = 'readOnly';CC_OPEN_DATE.cls = 'readOnly';IF_SURE.cls = 'readOnly';INSURE_AMT.cls = 'readOnly';XD_HZ_DATE.cls = 'readOnly';
					IF_ACCEPT.cls = 'readOnly';CTR_C_DATE.cls = 'readOnly';CTR_S_DATE.cls = 'readOnly';MORTGAGE_DATE.cls = 'readOnly';FILE_UP_DATE.cls = 'readOnly';SX_CTR_DATE.cls = 'readOnly';PROBLEM_DATE.cls = 'readOnly';AMT_USE_DATE.cls = 'readOnly';ACCOUNT_DATE.cls = 'readOnly';PAY_DATE.cls = 'readOnly';
					
				return [CUST_NAME,GROUP_NAME,AREA_NAME,DEPT_NAME,RM,CASE_TYPE,APPLY_AMT,VISIT_DATE,MAIN_INSURE,MAIN_AMT,
						COMBY_AMT,GRADE_PERSECT,COMP_TYPE,DD_DATE,SX_DATE,GRADE_LEVEL,COCO_DATE,XD_CA_DATE,QA_DATE,RM_DATE,
						CC_DATE,XS_CC_DATE,RM_C_DATE,XZ_CA_DATE,USE_DATE_P,SP_LEVEL,CC_OPEN_DATE,IF_SURE,INSURE_AMT,XD_HZ_DATE,
						IF_ACCEPT,CTR_C_DATE,CTR_S_DATE,MORTGAGE_DATE,FILE_UP_DATE,SX_CTR_DATE,PROBLEM_DATE,AMT_USE_DATE,ACCOUNT_DATE,PAY_DATE];
			}
	},{
		  columnCount: 1,
		  fields : ['CA_FORM','CO','XZ_CA_FORM','INSURE_FORM','HARD_INFO','CP_HARD_INFO','COCO_INFO','DELINE_REASON','NOACCEPT_REASON','CTR_PROBLEM'],
		  fn : function(CA_FORM,CO,XZ_CA_FORM,INSURE_FORM,HARD_INFO,CP_HARD_INFO,COCO_INFO,DELINE_REASON,NOACCEPT_REASON,CTR_PROBLEM){
		  	
		  	CA_FORM.readOnly = tru;CO.readOnly = true;XZ_CA_FORM.readOnly = true;INSURE_FORM.readOnly = true;HARD_INFO.readOnly = true;CP_HARD_INFO.readOnly = true;
		  	COCO_INFO.readOnly = true;DELINE_REASON.readOnly = true;NOACCEPT_REASON.readOnly = true;CTR_PROBLEM.readOnly = true;
		  	
		  	CA_FORM.cls = 'readOnly';CO.cls = 'readOnly';XZ_CA_FORM.cls = 'readOnly';INSURE_FORM.cls = 'readOnly';HARD_INFO.cls = 'readOnly';CP_HARD_INFO.cls = 'readOnly';
		  	COCO_INFO.cls = 'readOnly';DELINE_REASON.cls = 'readOnly';NOACCEPT_REASON.cls = 'readOnly';CTR_PROBLEM.cls = 'readOnly';
		  	
			  return [CA_FORM,CO,XZ_CA_FORM,INSURE_FORM,HARD_INFO,CP_HARD_INFO,COCO_INFO,DELINE_REASON,NOACCEPT_REASON,CTR_PROBLEM];
		  }
		}]
}];

var beforeviewshow = function(view){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
	}
};

var viewshow = function(){
	if(getSelectedData().data.IF_SURE == '1'){
		getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
		getCurrentView().contentPanel.form.findField("INSURE_FORM").show();
		getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
	}else if(getSelectedData().data.IF_SURE == '0'){
		getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
		getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
		getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
	}
	if(getSelectedData().data.IF_ACCEPT == '1'){
		getCurrentView().contentPanel.form.findField("NOACCEPT_REASON").hide();
		getCurrentView().contentPanel.form.findField("CTR_C_DATE").show();
		getCurrentView().contentPanel.form.findField("CTR_S_DATE").show();
		getCurrentView().contentPanel.form.findField("MORTGAGE_DATE").show();
		getCurrentView().contentPanel.form.findField("FILE_UP_DATE").show();
		getCurrentView().contentPanel.form.findField("SX_CTR_DATE").show();
		getCurrentView().contentPanel.form.findField("CTR_PROBLEM").show();
		getCurrentView().contentPanel.form.findField("PROBLEM_DATE").show();
		getCurrentView().contentPanel.form.findField("AMT_USE_DATE").show();
		getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").show();
		getCurrentView().contentPanel.form.findField("PAY_DATE").show();
		getCurrentView().contentPanel.form.findField("CTR_PROBLEM").show();
	}else if (getSelectedData().data.IF_ACCEPT == '0'){
		getCurrentView().contentPanel.form.findField("NOACCEPT_REASON").show();
		getCurrentView().contentPanel.form.findField("CTR_C_DATE").hide();
		getCurrentView().contentPanel.form.findField("CTR_S_DATE").hide();
		getCurrentView().contentPanel.form.findField("MORTGAGE_DATE").hide();
		getCurrentView().contentPanel.form.findField("FILE_UP_DATE").hide();
		getCurrentView().contentPanel.form.findField("SX_CTR_DATE").hide();
		getCurrentView().contentPanel.form.findField("CTR_PROBLEM").hide();
		getCurrentView().contentPanel.form.findField("PROBLEM_DATE").hide();
		getCurrentView().contentPanel.form.findField("AMT_USE_DATE").hide();
		getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").hide();
		getCurrentView().contentPanel.form.findField("PAY_DATE").hide();
		getCurrentView().contentPanel.form.findField("CTR_PROBLEM").hide();
	}
};
