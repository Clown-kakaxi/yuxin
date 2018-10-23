/**
 * @description 中小企客户营销流程 -  核批阶段页面
 * @author denghj
 * @since 2015-08-06
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
//	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
//	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldQZ.js'	//客户放大镜（企商金营销用）
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldPP.js'
	,'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
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


var url = basepath + '/smeApprovlE.json';

var lookupTypes = ['IF_FLAG','CHECK_STAT','CUST_TYPE_SME','CASE_TYPE_SME','GRADE_PERSECT','ACC0600002','SP_LEVEL','IF_FIFTH_STEP','REFUSE_REASON_CHECK_SME',
		'DELINE_REASON_APPROVL_C',
		{TYPE : 'AREA',//区域中心数据字典
			url : '/smeProspectE!searchArea.json',//此url为pipeline阶段所有区域中心字段的数据字典访问地址，后台逻辑存放于smeProspectEAction中
			key : 'KEY',
			value : 'VALUE',
			root : 'data'}];
			
var localLookup = {
	'CURRENCY' : [
		    {key : '10',value : 'RMB'},      
		    {key : '13',value : 'USD'},
		    {key : '5',value : 'EUR'},    
		    {key : '8',value : 'JPY'},      
		    {key : '7',value : 'HKD'},      
		    {key : '6',value : 'GBP'},  
			{key : '1',value : 'AUD'},
			{key : '2',value : 'CAD'},  
			{key : '3',value : 'CHF'},  
			{key : '9',value : 'NZD'},      
			{key : '11',value : 'SGD'},    
			{key : '12',value : 'TWD'}  
		]
};

var ifFifthStep=0;//此标志用于判断是否需要把信息录入第五阶段;值为1则需要值为5退回审查准备阶段
			
var fields = [
              {name:'IF_FIFTH_STEP',text:'是否进入第五阶段',translateType:'IF_FIFTH_STEP',allowBlank:false,searchField: true,listeners:{
					select:function(){

						var flag = getCurrentView().contentPanel.form.findField("IF_FIFTH_STEP").getValue();
						if(flag == '1'){//是否进入第五阶段 是 
							
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
							getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").hide();
							
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").setValue('');
							getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").setValue('');
							
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").allowBlank = true;
							getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").allowBlank = true;
							
							getCurrentView().contentPanel.form.findField("TRAIL_COMPLETION_DATE").show();
							getCurrentView().contentPanel.form.findField("CHECK_DATE").show();
							getCurrentView().contentPanel.form.findField("SP_LEVEL").show();
							getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").show();
							getCurrentView().contentPanel.form.findField("IF_SURE").show();
//							getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
//							getCurrentView().contentPanel.form.findField("INSURE_FORM").show();							
//							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
//							getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
//							getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").show();
							getCurrentView().contentPanel.form.findField("SC_DATE").show();
							getCurrentView().contentPanel.form.findField("CC_DATE").show();
							getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
							getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
							getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
							getCurrentView().contentPanel.form.findField("CHECK_DATE").show();
							
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank = false;
							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank = false;
//							getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").allowBlank = false;
							getCurrentView().contentPanel.form.findField("TRAIL_COMPLETION_DATE").allowBlank = false;
											
														
							if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == ''){//签核层级 默认为 SC
								getCurrentView().contentPanel.form.findField("SP_LEVEL").setVlaue('1');								
								getCurrentView().contentPanel.form.findField("SC_DATE").show();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
								
								getCurrentView().contentPanel.form.findField("SC_DATE").allowBlank = false;
								getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '1'){//SC 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").show();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
								
								getCurrentView().contentPanel.form.findField("SC_DATE").allowBlank = false;
								getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '2'){//CC 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
								
								getCurrentView().contentPanel.form.findField("SC_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank = false;
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '3'){//一级审批 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
								
								getCurrentView().contentPanel.form.findField("SC_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").allowBlank = false;
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '4'){//二级审批 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
								
								getCurrentView().contentPanel.form.findField("SC_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").allowBlank = false;
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '5'
								|| getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '6'){//三，四级审批 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
								
								getCurrentView().contentPanel.form.findField("SC_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").allowBlank = false;
							}else{
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
								
								getCurrentView().contentPanel.form.findField("SC_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
							}
							
							if(getCurrentView().contentPanel.form.findField("IF_SURE").getValue() == '1'){//额度是否核准 为是时
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
								getCurrentView().contentPanel.form.findField("INSURE_FORM").show();
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = false;
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = false;
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
								getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = false;
								
								getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
								getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
								
								getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
								getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
																													
							}else if(getCurrentView().contentPanel.form.findField("IF_SURE").getValue() == '0'){//额度是否核准 为否时
								getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_FORM").setValue('');
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
								
								getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
								getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = false;
								if(getCurrentView().contentPanel.form.findField("DELINE_REASON").getValue == '4'){//拒绝批核 原因为4 说明显示
									getCurrentView().contentPanel.form.findField("RESON_REMARK").show();
									getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = false;
								}else{
									getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
									getCurrentView().contentPanel.form.findField("RESON_REMARK").setValue('');
									getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
								}
							}else{
								getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
								getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
								getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_FORM").setValue('');
								getCurrentView().contentPanel.form.findField("DELINE_REASON").setValue('');
								getCurrentView().contentPanel.form.findField("RESON_REMARK").setValue('');
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
								getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
								getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
							}
							
						}else if(flag == '2'){//是否进入第五阶段 否决
							
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").allowBlank = false;
							
							getCurrentView().contentPanel.form.findField("TRAIL_COMPLETION_DATE").show();
							getCurrentView().contentPanel.form.findField("CHECK_DATE").show();
							getCurrentView().contentPanel.form.findField("SP_LEVEL").show();
							getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").show();
							getCurrentView().contentPanel.form.findField("IF_SURE").show();
//							getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
//							getCurrentView().contentPanel.form.findField("INSURE_FORM").show();
//							getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
//							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
//							getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").show();
							getCurrentView().contentPanel.form.findField("SC_DATE").show();
							getCurrentView().contentPanel.form.findField("CC_DATE").show();
							getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
							getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
							getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
							getCurrentView().contentPanel.form.findField("CHECK_DATE").show();
														
							getCurrentView().contentPanel.form.findField("TRAIL_COMPLETION_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank = true;
//							getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").allowBlank = true;
							
							getCurrentView().contentPanel.form.findField("IF_SURE").allowBlank = true;		
							getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
							getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
							getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
							
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank = true;
							getCurrentView().contentPanel.form.findField("SC_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank = true;
							
							if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == ''){//签核层级 默认为 SC
								getCurrentView().contentPanel.form.findField("SP_LEVEL").setVlaue('1');
								getCurrentView().contentPanel.form.findField("SC_DATE").show();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '1'){//SC 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").show();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '2'){//CC 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
						
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '3'){//一级审批 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
													
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '4'){//二级审批 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();

							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '5'
								|| getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '6'){//三，四级审批 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
								
							}else{
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
								
							}
							
							if(getCurrentView().contentPanel.form.findField("IF_SURE").getValue() == '1'){//额度是否核准 为是时
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
								getCurrentView().contentPanel.form.findField("INSURE_FORM").show();
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = false;
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = false;
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
								getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = false;
								
								getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
								getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
								
								getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
								getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
															
							}else if(getCurrentView().contentPanel.form.findField("IF_SURE").getValue() == '0'){//额度是否核准 为否时
								getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_FORM").setValue('');
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
								
								getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
								getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = false;
								if(getCurrentView().contentPanel.form.findField("DELINE_REASON").getValue == '4'){//拒绝批核 原因为4 说明显示
									getCurrentView().contentPanel.form.findField("RESON_REMARK").show();
									getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = false;
								}else{
									getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
									getCurrentView().contentPanel.form.findField("RESON_REMARK").setValue('');
									getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
								}
							}else{
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
								getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
								getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_FORM").setValue('');
								getCurrentView().contentPanel.form.findField("DELINE_REASON").setValue('');
								getCurrentView().contentPanel.form.findField("RESON_REMARK").setValue('');
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
								getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
								getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
							}
							
							
							
						}else if(flag == '3' || flag == '5'){//是否进入第五阶段 撤案，退回信用审查阶段 需审核填写内容隐藏
							
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").allowBlank = false;
							
							getCurrentView().contentPanel.form.findField("TRAIL_COMPLETION_DATE").hide();
							getCurrentView().contentPanel.form.findField("CHECK_DATE").hide();
							getCurrentView().contentPanel.form.findField("SP_LEVEL").hide();
							getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").hide();
							
							getCurrentView().contentPanel.form.findField("IF_SURE").hide();
							getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
							getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
							getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
							getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
							getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
							
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").hide();
							getCurrentView().contentPanel.form.findField("SC_DATE").hide();
							getCurrentView().contentPanel.form.findField("CC_DATE").hide();
							getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
							getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
							getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							getCurrentView().contentPanel.form.findField("CHECK_DATE").hide();
							
							getCurrentView().contentPanel.form.findField("TRAIL_COMPLETION_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank = true;
//							getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("IF_SURE").allowBlank = true;
							
							getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
							getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
							getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
							
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank = true;
							getCurrentView().contentPanel.form.findField("SC_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank = true;
							
							
						}else if(flag == '6') {//是否进入第五阶段 暂时维持本阶段 需审核内容可为空
							
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
							getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").hide()
							
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").setValue('');
							getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").setValue('');
							
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").allowBlank = true;
							getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").allowBlank = true;
							
							getCurrentView().contentPanel.form.findField("TRAIL_COMPLETION_DATE").show();
							getCurrentView().contentPanel.form.findField("CHECK_DATE").show();
							getCurrentView().contentPanel.form.findField("SP_LEVEL").show();
							getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").show();
							getCurrentView().contentPanel.form.findField("IF_SURE").show();
//							getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
//							getCurrentView().contentPanel.form.findField("INSURE_FORM").show();
//							getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
//							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
//							getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").show();
							getCurrentView().contentPanel.form.findField("SC_DATE").show();
							getCurrentView().contentPanel.form.findField("CC_DATE").show();
							getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
							getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
							getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
							getCurrentView().contentPanel.form.findField("CHECK_DATE").show();
							
							
							getCurrentView().contentPanel.form.findField("TRAIL_COMPLETION_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank = true;
//							getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("IF_SURE").allowBlank = true;
							
							getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
							getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
							getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
							
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank = true;
							getCurrentView().contentPanel.form.findField("SC_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank = true;
							
							if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == ''){//签核层级 默认为 SC
								getCurrentView().contentPanel.form.findField("SP_LEVEL").setVlaue('1');
								getCurrentView().contentPanel.form.findField("SC_DATE").show();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '1'){//SC 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").show();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '2'){//CC 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '3'){//一级审批 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '4'){//二级审批 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '5'
								|| getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '6'){//三，四级审批 显示
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
							}else{
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}
							
							if(getCurrentView().contentPanel.form.findField("IF_SURE").getValue() == '1'){	//额度是否核准 为是时
														
								getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
								getCurrentView().contentPanel.form.findField("INSURE_FORM").show();
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = false;
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = false;
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
								getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = false;
								
								getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
								getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
								
								getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
								getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;														

							}else if(getCurrentView().contentPanel.form.findField("IF_SURE").getValue() == '0'){//额度是否核准 为否时
								getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_FORM").setValue('');
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
								
								getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
								getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = false;
								if(getCurrentView().contentPanel.form.findField("DELINE_REASON").getValue == '4'){//拒绝批核 原因为4 说明显示
									getCurrentView().contentPanel.form.findField("RESON_REMARK").show();
									getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = false;
								}else{
									getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
									getCurrentView().contentPanel.form.findField("RESON_REMARK").setValue('');
									getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
								}
							}else{
								getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
								getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
								getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
								getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
								getCurrentView().contentPanel.form.findField("INSURE_FORM").setValue('');
								getCurrentView().contentPanel.form.findField("DELINE_REASON").setValue('');
								getCurrentView().contentPanel.form.findField("RESON_REMARK").setValue('');
								
								getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
								getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
								getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
								getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
							}
						}
				
					}}},
			  {name:'CUST_NAME',text:'客户名称',xtype:'customerquerypp',hiddenName:'CUST_ID',allowBlank:false,resutlWidth:150,singleSelect: false,searchField: true},
              {name:'AREA_NAME',text:'区域中心',translateType:"AREA",
	              resutlWidth:80,searchField: true, showField:'text',allowBlank:false,valueField:"value",listeners:{
				  select:function(a,b){
				  	a.setValue(b.data.value);
				}
				}},
              {name:'DEPT_NAME',text:'营业单位名称',xtype:'orgchoose',hiddenName:'DEPT_ID',allowBlank:false,searchField: true,readOnly:true},
              {name:'RM',text:'RM',xtype:'userchoose',hiddenName:'RM_ID',resutlWidth:150,allowBlank:false,singleSelect: false,searchField: true,readOnly:true},             
              {name:'CUST_TYPE',text:'客户类型',translateType:'CUST_TYPE_SME',searchField: true,editable:true,resizable:true,allowBlank:false,listeners:{
			  		select : function(){
			  			var flag = getCurrentView().contentPanel.form.findField("CUST_TYPE").value;
			  			if(flag == '2'){
			  				getCurrentView().contentPanel.form.findField("IF_ADD").show();	
//			  				getCurrentView().contentPanel.form.findField("IF_ADD").setValue('');
			  			}else{
			  				getCurrentView().contentPanel.form.findField("IF_ADD").hide();
			  				getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = true;
			  				getCurrentView().contentPanel.form.findField("IF_ADD").setValue('');
			  				
			  				getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
							getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
							getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
			  			}
			  		}
			  }},
              {name:'CASE_TYPE',text:'案件类型',translateType:'CASE_TYPE_SME',allowBlank:false,searchField: true,editable:true,resizable:true},
              {name:'FOREIGN_MONEY',text:'申请额度(原币金额/千元)',gridField:true,allowBlank:false,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24 },
			  {name:'CURRENCY',text:'申请额度币别',gridField:true,allowBlank:false,translateType:'CURRENCY',listeners:{
			  	select:function(){
			  		var flag=this.value;
			  		var  FOREIGN_MONEY=getCurrentView().contentPanel.form.findField("FOREIGN_MONEY").getValue();
			  		if(flag=='13'){			  			
			  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(FOREIGN_MONEY*6);
			  		}else if(flag == '10'){
			  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(FOREIGN_MONEY);
			  		}else{
			  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue('');
			  		}
			  	}
			  }},
			 {name:'APPLY_AMT',text:'申请额度(折人民币/千元)',dataType:'money',allowBlank:false,maxLength:24},			 
             {name:'IF_ADD',text:'若为旧案是否为增贷',gridField:true,allowBlank:false,translateType:'IF_FLAG',listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_ADD").getValue();
					if(value == '1'){
						getCurrentView().contentPanel.form.findField("ADD_AMT").show();
						getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = false;
					}else if(value == '0'){
						
						getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
						getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
						getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
						
					}}
			  }},			  
             {name:'ADD_AMT',text:'增贷金额(折人民币/千元)',gridField:true,dataType:'money',allowBlank:false,maxLength:24,minValue:0},
             {name:'TRAIL_COMPLETION_DATE',text:'初审完成日期',gridField:true,dataType:'date',allowBlank:false},
             {name:'CO',text:'对应CO',gridField:true,allowBlank:false,editable:true},
             {name:'SP_LEVEL',text:'信贷审批签核层级',translateType:'SP_LEVEL',allowBlank:false,gridField:true,editable:true,listeners:{
						select:function(){
							var value = getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue();
							if(value == '1'){
								getCurrentView().contentPanel.form.findField("SC_DATE").show();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '2'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '3'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '4'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '5'||value == '6'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
							}else{
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}}
					  }},
			 {name:'CHECK_DATE',text:'信审提交相应层级审批官日期',dataType:'date',allowBlank:false},
			 {name:'LEVEL34_DATE',text:'3,4级资深审批官批复日期',dataType:'date',hidden:true},
			 {name:'LEVEL2_DATE',text:'2级资深审批官批复日期',dataType:'date',hidden:true},
			 {name:'LEVEL1_DATE',text:'1级审批官批复日期',dataType:'date',hidden:true},
			 {name:'CC_OPEN_DATE',text:'CC召开日期',gridField:true,dataType:'date'},
			 {name:'CC_DATE',text:'CC批复日期',dataType:'date',hidden:true},
			 {name:'SC_DATE',text:'SC批复日期',dataType:'date',hidden:true},
			 {name:'IF_SURE',text:'额度是否核准',gridField:true,allowBlank:false,translateType:'IF_FLAG',listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_SURE").getValue();
					if(value == '1'){
						getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
						getCurrentView().contentPanel.form.findField("INSURE_FORM").show();
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
						getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = false;
						getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = false;
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = false;
						
						getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
						getCurrentView().contentPanel.form.findField("DELINE_REASON").setValue('');
						getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
						
						getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
						getCurrentView().contentPanel.form.findField("RESON_REMARK").setValue('');
						getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
					}else if(value == '0'){
						getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
						getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
						
						getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
						getCurrentView().contentPanel.form.findField("INSURE_FORM").setValue('');					
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
												
						getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
						getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
						
						getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
						getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = false;
						
					}else{//为空时 全部隐藏
						getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
						getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
						getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
						
						getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
						getCurrentView().contentPanel.form.findField("INSURE_FORM").setValue('');					
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
						getCurrentView().contentPanel.form.findField("DELINE_REASON").setValue('');
												
						getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
						getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
						getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
					}}
				}},
			 {name:'INSURE_MONEY',text:'核准金额(原币金额/千元)',dataType:'decimal',allowBlank:false,viewFn: money('0,000.00'),minValue: 0,maxLength:24},
			 {name:'INSURE_CURRENCY',text:'核准金额币别',translateType:'CURRENCY',allowBlank:false,listeners:{
						     select:function(){
						     	var flag=this.value;
						     	var INSURE_MONEY=getCurrentView().contentPanel.form.findField("INSURE_MONEY").getValue();
						     	if(flag=='13'){						     		
						     		getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue(INSURE_MONEY*6);
						     	}else if(flag == '10'){
						     		getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue(INSURE_MONEY);
						     	}else{
						     		getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
						     	}
						     }
			  		}},
			 {name:'INSURE_AMT',text:'核准金额(折人民币/千元)',gridField:true,dataType:'money',allowBlank:false,maxLength:24 },
			 {name:'INSURE_FORM',text:'最终核准额度框架',gridField:true,xtype:'textarea',allowBlank:false,maxLength:100},
			 {name:'DELINE_REASON',text:'未核准原因',translateType:'DELINE_REASON_APPROVL_C',editable: true,listeners:{
						select:function(){
							var value = getCurrentView().contentPanel.form.findField("DELINE_REASON").getValue();
							if(value == '4'){
								getCurrentView().contentPanel.form.findField("RESON_REMARK").show();
								getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = false;
							}else{
								getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
								getCurrentView().contentPanel.form.findField("RESON_REMARK").setValue('');
								getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
								
								getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").hide();
				 				getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").setValue('');
				 				getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").allowBlank = true;
							}}
				}},	
			 {name:'RESON_REMARK',text:'未核准原因说明',gridField:true,xtype:'textarea',maxLength:500},
			 {name:'CHECK_PROGRESS',text:'信审进度',allowBlank:false,allowBlank:false,xtype:'textarea',maxLength:500},
			 {name:'REFUSE_REASON',text:'退件或拒绝原因',translateType:'REFUSE_REASON_CHECK_SME',gridField:true,allowBalnk: false,listeners:{
			 		select  : function(){
			 			var flag = getCurrentView().contentPanel.form.findField("REFUSE_REASON").getValue();
			 			if(flag == '5'){
			 				getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").show();
			 				getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").allowBlank = false;
			 			}else{
			 				getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").hide();
			 				getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").setValue('');
			 				getCurrentView().contentPanel.form.findField("REFUSE_REASON_REMARK").allowBlank = true;
			 			}
			 		}
			 }},						  			  
			 {name:'REFUSE_REASON_REMARK',text:'退件或拒绝原因说明',gridField:true,xtype:'textarea',maxLength:500},
             {name:'USER_ID',text:'USER_ID',gridField:false},
             {name:'ID',text:'ID',gridField:false},
             {name:'CUST_ID',text:'客户编号',gridField:false},
	         {name:'RM_ID',text:'RM编号',gridField:false},
	         {name:'DEPT_ID',text:'DEPT_ID',gridField:false},
	         {name:'AREA_ID',text:'区域编号',gridField:false},
	         {name:'SC_ID',text:'SC_ID',gridField:false},
	         {name:'PIPELINE_ID',text:'PIPELINE编号',gridField:false,searchField: true},
			 {name:'RECORD_DATE',text:'RECORD_DATE',gridField:false},
			 {name:'IF_BACK',text:'IF_BACK',gridField:false}
			  ];

var tbar = [{
	text:'删除',
	hidden:JsContext.checkGrant('smeApprovlEDelet'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}

		var ID = '';
		for (var i=0;i<getAllSelects().length;i++){
			ID += getAllSelects()[i].data.ID;
			ID += ",";
		}
		ID = ID.substring(0, ID.length-1);
		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
			return false;
			} 
		    Ext.Ajax.request({
                url: basepath+'/smeApprovlE!batchDel.json?idStr='+ID,                                
                success : function(){
                    Ext.Msg.alert('提示', '删除成功');
                    reloadCurrentData();
                },
                failure : function(){
                    Ext.Msg.alert('提示', '删除失败');
                    reloadCurrentData();
                }
            });
		});			
	
	}
},
	new Com.yucheng.crm.common.NewExpButton({
        formPanel : 'searchCondition',
        url :  basepath + '/smeApprovlE.json'
    }),{	
	text:'恢复',
	hidden:JsContext.checkGrant('smeApprovlERecover'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(getSelectedData().data.IF_FIFTH_STEP=='3'||getSelectedData().data.IF_FIFTH_STEP=='4'){

		    Ext.Ajax.request({
                url: basepath+'/smeApprovlE!changeStat.json',   
                method : 'POST',
                params : {
						id : getSelectedData().data.ID
					},                             
                success : function(){
                    Ext.Msg.alert('提示', '恢复成功');
                    reloadCurrentData();
                },
                failure : function(){
                    Ext.Msg.alert('提示', '恢复失败');
                    reloadCurrentData();
                }
            });
		}else{
		 Ext.Msg.alert('提示', '此状态禁止恢复！');
	}	
}
}];

var customerView = [{
	title:'修改',
	hideTitle:JsContext.checkGrant('smeApprovlEEdit'),
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		labelWidth:170,
		fields :[
		         {name:'CUST_NAME',text:'客户名称', allowBlank:false},
		         {name:'AREA_NAME',text:'区域中心', allowBlank:false},
		         {name:'DEPT_NAME',text:'营业部门',allowBlank:false},
		         {name:'RM',text:'RM',resutlWidth:150,allowBlank:false},		       
		         'CURRENCY','CASE_TYPE','FOREIGN_MONEY','CUST_TYPE','APPLY_AMT','IF_ADD','CO','ADD_AMT',				 					
		         'CHECK_DATE','TRAIL_COMPLETION_DATE','SP_LEVEL','IF_SURE','SC_DATE','INSURE_MONEY','CC_DATE','INSURE_CURRENCY',
				  'LEVEL1_DATE','INSURE_AMT','LEVEL2_DATE','DELINE_REASON','LEVEL34_DATE','IF_FIFTH_STEP','CC_OPEN_DATE',
				 'REFUSE_REASON','REFUSE_REASON_REMARK',
				 'USER_ID','ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','SC_ID','PIPELINE_ID','RECORD_DATE','IF_BACK'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,
						CURRENCY,CASE_TYPE,FOREIGN_MONEY,CUST_TYPE,APPLY_AMT,IF_ADD,CO,ADD_AMT,
						CHECK_DATE,TRAIL_COMPLETION_DATE,SP_LEVEL,IF_SURE,SC_DATE,INSURE_MONEY,CC_DATE,INSURE_CURRENCY,
						LEVEL1_DATE,INSURE_AMT,LEVEL2_DATE,DELINE_REASON,LEVEL34_DATE,IF_FIFTH_STEP,CC_OPEN_DATE,				
						REFUSE_REASON,USER_ID,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,SC_ID,PIPELINE_ID,RECORD_DATE,IF_BACK){
				
				CUST_NAME.readOnly = true;
				CUST_NAME.cls = 'x-readOnly';
				AREA_NAME.readOnly = true;
				AREA_NAME.cls = 'x-readOnly';
				DEPT_NAME.readOnly = true;
				DEPT_NAME.cls = 'x-readOnly';
				RM.readOnly = true;
				RM.cls = 'x-readOnly';
				CUST_TYPE.readOnly = true;
				CUST_TYPE.cls = 'x-readOnly';
				CASE_TYPE.readOnly = true;
				CASE_TYPE.cls = 'x-readOnly';
				FOREIGN_MONEY.readOnly = true;
				FOREIGN_MONEY.cls = 'x-readOnly';
				CURRENCY.readOnly = true;
				CURRENCY.cls = 'x-readOnly';
				APPLY_AMT.readOnly = true;
				APPLY_AMT.cls = 'x-readOnly';
				IF_ADD.readOnly = true;
				IF_ADD.cls = 'x-readOnly';
				ADD_AMT.readOnly = true;
				ADD_AMT.cls = 'x-readOnly';
			  	CO.cls = 'x-readOnly';
			  	CO.readOnly = true;
			  	
				USER_ID.hidden = true;
				ID.hidden = true;
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				DEPT_ID.hidden = true;
				AREA_ID.hidden = true;
				SC_ID.hidden = true;
				PIPELINE_ID.hidden = true;
				RECORD_DATE.hidden = true;
				IF_BACK.hidden = true;
				SC_DATE.hidden = true;
				CC_DATE.hidden = true;
				LEVEL1_DATE.hidden = true;
				LEVEL2_DATE.hidden = true;
				LEVEL34_DATE.hidden = true;
				
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,
				        CURRENCY,CASE_TYPE,FOREIGN_MONEY,CUST_TYPE,APPLY_AMT,IF_ADD,CO,ADD_AMT,
				        CHECK_DATE,TRAIL_COMPLETION_DATE,SP_LEVEL,IF_SURE,SC_DATE,INSURE_MONEY,CC_DATE,INSURE_CURRENCY,
						LEVEL1_DATE,INSURE_AMT,LEVEL2_DATE,DELINE_REASON,LEVEL34_DATE,IF_FIFTH_STEP,CC_OPEN_DATE,				
						REFUSE_REASON,USER_ID,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,SC_ID,PIPELINE_ID,RECORD_DATE,IF_BACK];
			}
	},{
		  columnCount: 1,
		  fields : ['INSURE_FORM','CHECK_PROGRESS'],
		  fn : function(INSURE_FORM,CHECK_PROGRESS){
			  return [INSURE_FORM,CHECK_PROGRESS];
		  }
		},{
		  columnCount: 1,
		  fields : [
		  	'RESON_REMARK','REFUSE_RESON_REMARK'],
		  fn : function(RESON_REMARK,REFUSE_RESON_REMARK){
			  return [RESON_REMARK,REFUSE_RESON_REMARK];
		  }
		}],
		formButtons : [{
			text:'提交',
			fn : function(formPanel,basicForm){
				 if (!formPanel.getForm().isValid()) {
		               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		               return false;
		           };
		         var data = formPanel.getForm().getFieldValues();
					var commintData = translateDataKey(data,1);
					ifFifthStep=commintData.ifFifthStep;
//					if(ifFifthStep == '2'){//是否进入第五阶段 为否决时  额度是否核准  必须为否
//		        	   if(formPanel.form.findField("DELINE_REASON").getValue() == ''){
//		        		   Ext.MessageBox.alert('提示','请填写[未核准原因]');
//			               return false;
//		        	   }
//			        }
					
					var ADD_AMT=formPanel.form.findField("ADD_AMT").getValue();//核准增贷金额
					var APPLY_AMT=formPanel.form.findField("APPLY_AMT").getValue();//申请额度（折人民币/千元）
					if(ADD_AMT!='' && APPLY_AMT!=''){
						if(ADD_AMT > APPLY_AMT){
							 Ext.MessageBox.alert('提示','核准增贷金额（折人民币/千元）应小于等于申请额度（折人民币/千元）');
							 return false;
						}
					}
					
					//申请额度（折人民币/千元）’与‘申请额度（原币金额/千元）’如果‘申请额度币别’是RMB那么两个额度应该相同
					var CURRENCY=formPanel.form.findField("CURRENCY").getValue();
					var APPLY_AMT=formPanel.form.findField("APPLY_AMT").getValue();
					var FOREIGN_MONEY=formPanel.form.findField("FOREIGN_MONEY").getValue();
					if(CURRENCY=='RMB'){
						if(APPLY_AMT!=FOREIGN_MONEY){
							 Ext.MessageBox.alert('提示','申请额度币别为RMB，申请额度（折人民币/千元）应与申请额度（原币金额/千元）相等');
		            		 return false;
						}
					}
					if(((data.APPLY_AMT==0||data.APPLY_AMT=='')&&(data.FOREIGN_MONEY!=0||data.FOREIGN_MONEY!='')&&data.IF_FIFTH_STEP=='1')
			        		||((data.APPLY_AMT!=0||data.APPLY_AMT!='')&&(data.FOREIGN_MONEY==0||data.FOREIGN_MONEY=='')&&data.IF_FIFTH_STEP=='1')){
			        	Ext.MessageBox.alert('提示','申请额度（折人民币/千元）或申请额度（原币金额/千元）不允许为零！');
			               return false;
			     	}
			    	if(((data.INSURE_AMT==0||data.INSURE_AMT=='')&&(data.INSURE_MONEY!=0||data.INSURE_MONEY!='')&&data.IF_FIFTH_STEP=='1')
			        		||((data.INSURE_AMT!=0||data.INSURE_AMT!='')&&(data.INSURE_MONEY==0||data.INSURE_MONEY=='')&&data.IF_FIFTH_STEP=='1')){
			        	Ext.MessageBox.alert('提示','核准金额（折人民币/千元）或核准金额（原币金额/千元）不允许为零！');
			               return false;
		         	}
			    	
			    	if(ifFifthStep == '1' && data.IF_SURE != '1'){//额度未核准不能进入下一阶段
			    		Ext.MessageBox.alert('提示','额度未核准不能进入下一阶段');
			    		return false;
			    	}
			    	
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/smeApprovlE!save.json',
							method : 'GET',
							params : commintData,
							success : function(response) {
									if((ifFifthStep == '1') && (data.IF_SURE == '1')){//需要在下一阶段进行数据存储
										var ret = Ext.decode(response.responseText);
										Ext.Ajax.request({
											url : basepath + '/smeApprovedE!save.json',//把数据转入核批阶段，并把数据保存进核批阶段
											method : 'GET',
											params : ret,
											success : function(response) {
												Ext.MessageBox.alert('提示','保存数据成功,请在已核批动拨阶段查看数据!');
											}
										})
									}else if(ifFifthStep=='5'){
										Ext.MessageBox.alert('提示','数据已经被退回至信用审查阶段!');
									}else{
										Ext.MessageBox.alert('提示','数据保存成功!');
									}
									hideCurrentView(); 
									reloadCurrentData();
								}
							}); 
		        	   
			}
		}]
},{
	title:'详情',
	hideTitle:JsContext.checkGrant('smeApprovlEDetail'),
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		labelWidth:160,
		fields :[
		         {name:'CUST_NAME',text:'客户名称', allowBlank:false},
		         {name:'AREA_NAME',text:'区域中心', allowBlank:false},
		         {name:'DEPT_NAME',text:'营业部门',allowBlank:false},
		         {name:'RM',text:'RM',resutlWidth:150,allowBlank:false},		       
		         'CURRENCY','CASE_TYPE','FOREIGN_MONEY','CUST_TYPE','APPLY_AMT','IF_ADD','CO','ADD_AMT',				 					
		         'CHECK_DATE','TRAIL_COMPLETION_DATE','SP_LEVEL','IF_SURE','SC_DATE','INSURE_MONEY','CC_DATE','INSURE_CURRENCY',
				  'LEVEL1_DATE','INSURE_AMT','LEVEL2_DATE','DELINE_REASON','LEVEL34_DATE','IF_FIFTH_STEP','CC_OPEN_DATE',
				 'REFUSE_REASON','REFUSE_REASON_REMARK',
				 'USER_ID','ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','SC_ID','PIPELINE_ID','RECORD_DATE','IF_BACK'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,
			        CURRENCY,CASE_TYPE,FOREIGN_MONEY,CUST_TYPE,APPLY_AMT,IF_ADD,CO,ADD_AMT,
			        CHECK_DATE,TRAIL_COMPLETION_DATE,SP_LEVEL,IF_SURE,SC_DATE,INSURE_MONEY,CC_DATE,INSURE_CURRENCY,
					LEVEL1_DATE,INSURE_AMT,LEVEL2_DATE,DELINE_REASON,LEVEL34_DATE,IF_FIFTH_STEP,CC_OPEN_DATE,				
					REFUSE_REASON,USER_ID,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,SC_ID,PIPELINE_ID,RECORD_DATE,IF_BACK){
				
				CUST_NAME.readOnly = true;
				CUST_NAME.cls = 'x-readOnly';
				AREA_NAME.readOnly = true;
				AREA_NAME.cls = 'x-readOnly';
				DEPT_NAME.readOnly = true;
				DEPT_NAME.cls = 'x-readOnly';
				RM.readOnly = true;
				RM.cls = 'x-readOnly';
				IF_FIFTH_STEP.readOnly = true;
				IF_FIFTH_STEP.cls = 'x-readOnly';
				CUST_TYPE.readOnly = true;
				CUST_TYPE.cls = 'x-readOnly';
				CASE_TYPE.readOnly = true;
				CASE_TYPE.cls = 'x-readOnly';
				FOREIGN_MONEY.readOnly = true;
				FOREIGN_MONEY.cls = 'x-readOnly';
				CURRENCY.readOnly = true;
				CURRENCY.cls = 'x-readOnly';			
				APPLY_AMT.readOnly = true;
				APPLY_AMT.cls = 'x-readOnly';						
				IF_ADD.readOnly = true;
				IF_ADD.cls = 'x-readOnly';
				ADD_AMT.readOnly = true;
				ADD_AMT.cls = 'x-readOnly';
				TRAIL_COMPLETION_DATE.readOnly = true;
				TRAIL_COMPLETION_DATE.cls = 'x-readOnly';
				CO.readOnly = true;
				CO.cls = 'x-readOnly';
				SP_LEVEL.readOnly = true;
				SP_LEVEL.cls = 'x-readOnly';
				CHECK_DATE.readOnly = true;
				CHECK_DATE.cls = 'x-readOnly';
				LEVEL34_DATE.readOnly = true;
				LEVEL34_DATE.cls = 'x-readOnly';
				LEVEL2_DATE.readOnly = true;
				LEVEL2_DATE.cls = 'x-readOnly';
				LEVEL1_DATE.readOnly = true;
				LEVEL1_DATE.cls = 'x-readOnly';
				CC_OPEN_DATE.readOnly = true;
				CC_OPEN_DATE.cls = 'x-readOnly';
				CC_DATE.readOnly = true;
				CC_DATE.cls = 'x-readOnly';
				SC_DATE.readOnly = true;
				SC_DATE.cls = 'x-readOnly';
				IF_SURE.readOnly = true;
				IF_SURE.cls = 'x-readOnly';
				INSURE_MONEY.readOnly = true;
				INSURE_MONEY.cls = 'x-readOnly';
				INSURE_CURRENCY.readOnly = true;
				INSURE_CURRENCY.cls = 'x-readOnly';
				INSURE_AMT.readOnly = true;
				INSURE_AMT.cls = 'x-readOnly';																			
				DELINE_REASON.readOnly = true;
				DELINE_REASON.cls = 'x-readOnly';
				REFUSE_REASON.readOnly = true;
				REFUSE_REASON.cls = 'x-readOnly';				
				USER_ID.hidden = true;
				ID.hidden = true;
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				DEPT_ID.hidden = true;
				AREA_ID.hidden = true;
				SC_ID.hidden = true;
				PIPELINE_ID.hidden = true;
				RECORD_DATE.hidden = true;
				IF_BACK.hidden = true;
	
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,
				        CURRENCY,CASE_TYPE,FOREIGN_MONEY,CUST_TYPE,APPLY_AMT,IF_ADD,CO,ADD_AMT,
				        CHECK_DATE,TRAIL_COMPLETION_DATE,SP_LEVEL,IF_SURE,SC_DATE,INSURE_MONEY,CC_DATE,INSURE_CURRENCY,
						LEVEL1_DATE,INSURE_AMT,LEVEL2_DATE,DELINE_REASON,LEVEL34_DATE,IF_FIFTH_STEP,CC_OPEN_DATE,				
						REFUSE_REASON,USER_ID,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,SC_ID,PIPELINE_ID,RECORD_DATE,IF_BACK];
			}
	},{
		  columnCount: 1,
		  fields : [
            'CHECK_PROGRESS','INSURE_FORM','RESON_REMARK','REFUSE_RESON_REMARK'],
		  fn : function(CHECK_PROGRESS,INSURE_FORM,RESON_REMARK,REFUSE_RESON_REMARK){
				INSURE_FORM.readOnly = true;
				INSURE_FORM.cls = 'x-readOnly';
				RESON_REMARK.readOnly = true;
				RESON_REMARK.cls = 'x-readOnly';
				REFUSE_RESON_REMARK.readOnly = true;
				REFUSE_RESON_REMARK.cls = 'x-readOnly';
				CHECK_PROGRESS.readOnly = true;
				CHECK_PROGRESS.cls = 'x-readOnly';
			  return [CHECK_PROGRESS,INSURE_FORM,RESON_REMARK,REFUSE_RESON_REMARK];
		  }
		}]
}];

var beforeviewshow = function(view){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
	}
	if(view._defaultTitle == '修改'){
		if(getSelectedData().data.IF_FIFTH_STEP=='2'){
				Ext.Msg.alert('提示','案件已否决，不能修改');
		      	return false;
		}
		if(getSelectedData().data.IF_FIFTH_STEP=='3'
		      ||getSelectedData().data.IF_FIFTH_STEP == '4'){
		      	Ext.Msg.alert('提示','请先恢复案件,再修改！');
		      	return false;
		}

	}
};

var viewshow = function(view){
	
	if(view.contentPanel.form.findField("IF_FIFTH_STEP").value == ''){//是否进入第五阶段 默认为 暂时维持本阶段
		view.contentPanel.form.findField("IF_FIFTH_STEP").setValue('6');
	}
	
	if(view.contentPanel.form.findField("SP_LEVEL").value == ''){//信贷审批签核层级 默认为 SC
		view.contentPanel.form.findField("SP_LEVEL").setValue('1');
	}
	
	
	if(getSelectedData().data.CUST_TYPE == '2'){//客户类型为 旧户增贷 显示是否为增贷
		getCurrentView().contentPanel.form.findField("IF_ADD").show();
		getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = false;
	}else{
		getCurrentView().contentPanel.form.findField("IF_ADD").hide();
		getCurrentView().contentPanel.form.findField("IF_ADD").setValue('');
		getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = true;
		
		getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
		getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
		getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
	}
	
	if(getSelectedData().data.IF_ADD == '1'){//是否为增贷 是 显示 增贷金额
		getCurrentView().contentPanel.form.findField("ADD_AMT").show();
		getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = false;
	}else {
		getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
		getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
		getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
	}
		
		if(view.contentPanel.form.findField("IF_FIFTH_STEP").getValue() == '1'){//是否进入第五阶段 是 
			
			view.contentPanel.form.findField("REFUSE_REASON").hide();
			view.contentPanel.form.findField("REFUSE_REASON").allowBlank = false;
			view.contentPanel.form.findField("REFUSE_REASON_REMARK").hide();
			view.contentPanel.form.findField("REFUSE_REASON_REMARK").allowBlank = false;
			
			view.contentPanel.form.findField("TRAIL_COMPLETION_DATE").show();
			view.contentPanel.form.findField("CHECK_DATE").show();
			view.contentPanel.form.findField("SP_LEVEL").show();
			view.contentPanel.form.findField("CC_OPEN_DATE").show();
			view.contentPanel.form.findField("IF_SURE").show();
//			view.contentPanel.form.findField("INSURE_AMT").show();
//			view.contentPanel.form.findField("INSURE_FORM").show();
//			view.contentPanel.form.findField("DELINE_REASON").show();
//			view.contentPanel.form.findField("INSURE_CURRENCY").show();
//			view.contentPanel.form.findField("INSURE_MONEY").show();
			view.contentPanel.form.findField("CHECK_PROGRESS").show();
			view.contentPanel.form.findField("SC_DATE").show();
			view.contentPanel.form.findField("CC_DATE").show();
			view.contentPanel.form.findField("LEVEL1_DATE").show();
			view.contentPanel.form.findField("LEVEL2_DATE").show();
			view.contentPanel.form.findField("LEVEL34_DATE").show();
			view.contentPanel.form.findField("CHECK_DATE").show();
			
			view.contentPanel.form.findField("CHECK_PROGRESS").allowBlank = false;
			view.contentPanel.form.findField("CHECK_DATE").allowBlank = false;
//			view.contentPanel.form.findField("CC_OPEN_DATE").allowBlank = false;
			view.contentPanel.form.findField("TRAIL_COMPLETION_DATE").allowBlank = false;
			
			if(view.contentPanel.form.findField("SP_LEVEL").getValue() == ''){//签核层级 默认为 SC
				view.contentPanel.form.findField("SP_LEVEL").setVlaue('1');
				view.contentPanel.form.findField("SC_DATE").show();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
				
				view.contentPanel.form.findField("SC_DATE").allowBlank = false;
				view.contentPanel.form.findField("CC_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '1'){//SC 显示
				view.contentPanel.form.findField("SC_DATE").show();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
				
				view.contentPanel.form.findField("SC_DATE").allowBlank = false;
				view.contentPanel.form.findField("CC_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '2'){//CC 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").show();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
				
				view.contentPanel.form.findField("SC_DATE").allowBlank = true;
				view.contentPanel.form.findField("CC_DATE").allowBlank = false;
				view.contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '3'){//一级审批 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").show();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
				
				view.contentPanel.form.findField("SC_DATE").allowBlank = true;
				view.contentPanel.form.findField("CC_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL1_DATE").allowBlank = false;
				view.contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '4'){//二级审批 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").show();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
				
				view.contentPanel.form.findField("SC_DATE").allowBlank = true;
				view.contentPanel.form.findField("CC_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL2_DATE").allowBlank = false;
				view.contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '5'
				|| view.contentPanel.form.findField("SP_LEVEL").getValue() == '6'){//三，四级审批 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").show();
				
				view.contentPanel.form.findField("SC_DATE").allowBlank = true;
				view.contentPanel.form.findField("CC_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL34_DATE").allowBlank = false;
			}else{
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
				
				view.contentPanel.form.findField("SC_DATE").allowBlank = true;
				view.contentPanel.form.findField("CC_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
				view.contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
			}
			
			if(view.contentPanel.form.findField("IF_SURE").getValue() == '1'){//额度是否核准 为是时
								
				view.contentPanel.form.findField("INSURE_AMT").show();
				view.contentPanel.form.findField("INSURE_CURRENCY").show();
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_FORM").show();
				
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = false;
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_FORM").allowBlank = false;
				
				view.contentPanel.form.findField("DELINE_REASON").hide();
				view.contentPanel.form.findField("RESON_REMARK").hide();
				
				view.contentPanel.form.findField("DELINE_REASON").allowBlank = true;
				view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;
										
			}else if(view.contentPanel.form.findField("IF_SURE").getValue() == '0'){//额度是否核准 为否时
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_FORM").hide();
				
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
				view.contentPanel.form.findField("INSURE_FORM").setValue('');
				
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_FORM").allowBlank = true;
				
				view.contentPanel.form.findField("DELINE_REASON").show();
				view.contentPanel.form.findField("DELINE_REASON").allowBlank = false;
				if(view.contentPanel.form.findField("DELINE_REASON").getValue == '4'){//拒绝批核 原因为4 说明显示
					view.contentPanel.form.findField("RESON_REMARK").show();
					view.contentPanel.form.findField("RESON_REMARK").allowBlank = false;
				}else{
					view.contentPanel.form.findField("RESON_REMARK").hide();
					view.contentPanel.form.findField("RESON_REMARK").setValue('');
					view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;
				}
			}else{
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_FORM").hide();
				view.contentPanel.form.findField("DELINE_REASON").hide();
				view.contentPanel.form.findField("RESON_REMARK").hide();
				
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
				view.contentPanel.form.findField("INSURE_FORM").setValue('');
				view.contentPanel.form.findField("DELINE_REASON").setValue('');
				view.contentPanel.form.findField("RESON_REMARK").setValue('');
				
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_FORM").allowBlank = true;
				view.contentPanel.form.findField("DELINE_REASON").allowBlank = true;
				view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;
			}
			
		}else if(view.contentPanel.form.findField("IF_FIFTH_STEP").getValue() == '2'){//是否进入第五阶段 否决
			
			view.contentPanel.form.findField("REFUSE_REASON").show();
			view.contentPanel.form.findField("REFUSE_REASON").allowBlank = false;
			
			view.contentPanel.form.findField("TRAIL_COMPLETION_DATE").show();
			view.contentPanel.form.findField("CHECK_DATE").show();
			view.contentPanel.form.findField("SP_LEVEL").show();
			view.contentPanel.form.findField("CC_OPEN_DATE").show();
			view.contentPanel.form.findField("IF_SURE").show();
//			view.contentPanel.form.findField("INSURE_AMT").show();
//			view.contentPanel.form.findField("INSURE_FORM").show();
//			view.contentPanel.form.findField("DELINE_REASON").show();
//			view.contentPanel.form.findField("INSURE_CURRENCY").show();
//			view.contentPanel.form.findField("INSURE_MONEY").show();
			view.contentPanel.form.findField("CHECK_PROGRESS").show();
			view.contentPanel.form.findField("SC_DATE").show();
			view.contentPanel.form.findField("CC_DATE").show();
			view.contentPanel.form.findField("LEVEL1_DATE").show();
			view.contentPanel.form.findField("LEVEL2_DATE").show();
			view.contentPanel.form.findField("LEVEL34_DATE").show();
			view.contentPanel.form.findField("CHECK_DATE").show();
			
			view.contentPanel.form.findField("TRAIL_COMPLETION_DATE").allowBlank = true;
			view.contentPanel.form.findField("CHECK_DATE").allowBlank = true;
			view.contentPanel.form.findField("SP_LEVEL").allowBlank = true;
//			view.contentPanel.form.findField("CC_OPEN_DATE").allowBlank = true;
			view.contentPanel.form.findField("IF_SURE").allowBlank = true;
			
			view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
			view.contentPanel.form.findField("INSURE_FORM").allowBlank = true;
			view.contentPanel.form.findField("DELINE_REASON").allowBlank = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
			view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;
			
			view.contentPanel.form.findField("CHECK_PROGRESS").allowBlank = true;
			view.contentPanel.form.findField("SC_DATE").allowBlank = true;
			view.contentPanel.form.findField("CC_DATE").allowBlank = true;
			view.contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
			view.contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
			view.contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
			view.contentPanel.form.findField("CHECK_DATE").allowBlank = true;
			
			if(view.contentPanel.form.findField("SP_LEVEL").getValue() == ''){//签核层级 默认为 SC
				view.contentPanel.form.findField("SP_LEVEL").setVlaue('1');
				view.contentPanel.form.findField("SC_DATE").show();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '1'){//SC 显示
				view.contentPanel.form.findField("SC_DATE").show();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '2'){//CC 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").show();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '3'){//一级审批 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").show();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '4'){//二级审批 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").show();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '5'
				|| view.contentPanel.form.findField("SP_LEVEL").getValue() == '6'){//三，四级审批 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").show();
			}else{
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}
			
			if(view.contentPanel.form.findField("IF_SURE").getValue() == '1'){//额度是否核准 为是时
				
				view.contentPanel.form.findField("INSURE_AMT").show();
				view.contentPanel.form.findField("INSURE_CURRENCY").show();
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_FORM").show();
				
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = false;
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_FORM").allowBlank = false;
				
				view.contentPanel.form.findField("DELINE_REASON").hide();
				view.contentPanel.form.findField("RESON_REMARK").hide();
				
				view.contentPanel.form.findField("DELINE_REASON").allowBlank = true;
				view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;

			}else if(view.contentPanel.form.findField("IF_SURE").getValue() == '0'){//额度是否核准 为否时
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_FORM").hide();
				
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
				view.contentPanel.form.findField("INSURE_FORM").setValue('');
				
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_FORM").allowBlank = true;
				
				view.contentPanel.form.findField("DELINE_REASON").show();
				view.contentPanel.form.findField("DELINE_REASON").allowBlank = false;
				if(view.contentPanel.form.findField("DELINE_REASON").getValue == '4'){//拒绝批核 原因为4 说明显示
					view.contentPanel.form.findField("RESON_REMARK").show();
					view.contentPanel.form.findField("RESON_REMARK").allowBlank = false;
				}else{
					view.contentPanel.form.findField("RESON_REMARK").hide();
					view.contentPanel.form.findField("RESON_REMARK").setValue('');
					view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;
				}
			}else{	
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_FORM").hide();
				view.contentPanel.form.findField("DELINE_REASON").hide();
				view.contentPanel.form.findField("RESON_REMARK").hide();
				
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
				view.contentPanel.form.findField("INSURE_FORM").setValue('');
				view.contentPanel.form.findField("DELINE_REASON").setValue('');
				view.contentPanel.form.findField("RESON_REMARK").setValue('');
				
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_FORM").allowBlank = true;
				view.contentPanel.form.findField("DELINE_REASON").allowBlank = true;
				view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;
			}
			
		}else if(view.contentPanel.form.findField("IF_FIFTH_STEP").getValue() == '3' 
				|| view.contentPanel.form.findField("IF_FIFTH_STEP").getValue() == '5'){//是否进入第五阶段 撤案，退回信用审查阶段 需审核填写内容隐藏
			
			view.contentPanel.form.findField("REFUSE_REASON").show();
			
			view.contentPanel.form.findField("TRAIL_COMPLETION_DATE").hide();
			view.contentPanel.form.findField("CHECK_DATE").hide();
			view.contentPanel.form.findField("SP_LEVEL").hide();
			view.contentPanel.form.findField("CC_OPEN_DATE").hide();
			view.contentPanel.form.findField("IF_SURE").hide();
			view.contentPanel.form.findField("INSURE_AMT").hide();
			view.contentPanel.form.findField("INSURE_FORM").hide();
			view.contentPanel.form.findField("DELINE_REASON").hide();
			view.contentPanel.form.findField("INSURE_CURRENCY").hide();
			view.contentPanel.form.findField("INSURE_MONEY").hide();
			view.contentPanel.form.findField("CHECK_PROGRESS").hide();
			view.contentPanel.form.findField("SC_DATE").hide();
			view.contentPanel.form.findField("CC_DATE").hide();
			view.contentPanel.form.findField("LEVEL1_DATE").hide();
			view.contentPanel.form.findField("LEVEL2_DATE").hide();
			view.contentPanel.form.findField("LEVEL34_DATE").hide();
			view.contentPanel.form.findField("CHECK_DATE").hide();
			
			view.contentPanel.form.findField("TRAIL_COMPLETION_DATE").allowBlank = true;
			view.contentPanel.form.findField("CHECK_DATE").allowBlank = true;
			view.contentPanel.form.findField("SP_LEVEL").allowBlank = true;
//			view.contentPanel.form.findField("CC_OPEN_DATE").allowBlank = true;
			
			view.contentPanel.form.findField("IF_SURE").allowBlank = true;
			view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
			view.contentPanel.form.findField("INSURE_FORM").allowBlank = true;
			view.contentPanel.form.findField("DELINE_REASON").allowBlank = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
			view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;
			
			view.contentPanel.form.findField("CHECK_PROGRESS").allowBlank = true;
			view.contentPanel.form.findField("SC_DATE").allowBlank = true;
			view.contentPanel.form.findField("CC_DATE").allowBlank = true;
			view.contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
			view.contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
			view.contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
			view.contentPanel.form.findField("CHECK_DATE").allowBlank = true;
			
		}else if(view.contentPanel.form.findField("IF_FIFTH_STEP").getValue() == '6') {//是否进入第五阶段 暂时维持本阶段 需审核内容可为空
			
			view.contentPanel.form.findField("REFUSE_REASON").hide();
			view.contentPanel.form.findField("REFUSE_REASON").allowBlank = true;
			
			view.contentPanel.form.findField("TRAIL_COMPLETION_DATE").show();
			view.contentPanel.form.findField("CHECK_DATE").show();
			view.contentPanel.form.findField("SP_LEVEL").show();
			view.contentPanel.form.findField("CC_OPEN_DATE").show();
			view.contentPanel.form.findField("IF_SURE").show();
//			view.contentPanel.form.findField("INSURE_AMT").show();
//			view.contentPanel.form.findField("INSURE_FORM").show();
//			view.contentPanel.form.findField("DELINE_REASON").show();
//			view.contentPanel.form.findField("INSURE_CURRENCY").show();
//			view.contentPanel.form.findField("INSURE_MONEY").show();
			view.contentPanel.form.findField("CHECK_PROGRESS").show();
			view.contentPanel.form.findField("SC_DATE").show();
			view.contentPanel.form.findField("CC_DATE").show();
			view.contentPanel.form.findField("LEVEL1_DATE").show();
			view.contentPanel.form.findField("LEVEL2_DATE").show();
			view.contentPanel.form.findField("LEVEL34_DATE").show();
			view.contentPanel.form.findField("CHECK_DATE").show();
			
			view.contentPanel.form.findField("TRAIL_COMPLETION_DATE").allowBlank = true;
			view.contentPanel.form.findField("CHECK_DATE").allowBlank = true;
			view.contentPanel.form.findField("SP_LEVEL").allowBlank = true;
//			view.contentPanel.form.findField("CC_OPEN_DATE").allowBlank = true;
			view.contentPanel.form.findField("IF_SURE").allowBlank = true;
			
			view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
			view.contentPanel.form.findField("INSURE_FORM").allowBlank = true;
			view.contentPanel.form.findField("DELINE_REASON").allowBlank = true;
			view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
			view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;
			
			view.contentPanel.form.findField("CHECK_PROGRESS").allowBlank = true;
			view.contentPanel.form.findField("SC_DATE").allowBlank = true;
			view.contentPanel.form.findField("CC_DATE").allowBlank = true;
			view.contentPanel.form.findField("LEVEL1_DATE").allowBlank = true;
			view.contentPanel.form.findField("LEVEL2_DATE").allowBlank = true;
			view.contentPanel.form.findField("LEVEL34_DATE").allowBlank = true;
			view.contentPanel.form.findField("CHECK_DATE").allowBlank = true;
			
			if(view.contentPanel.form.findField("SP_LEVEL").getValue() == ''){//签核层级 默认为 SC
				view.contentPanel.form.findField("SP_LEVEL").setVlaue('1');
				view.contentPanel.form.findField("SC_DATE").show();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '1'){//SC 显示
				view.contentPanel.form.findField("SC_DATE").show();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '2'){//CC 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").show();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '3'){//一级审批 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").show();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '4'){//二级审批 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").show();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(view.contentPanel.form.findField("SP_LEVEL").getValue() == '5'
				|| view.contentPanel.form.findField("SP_LEVEL").getValue() == '6'){//三，四级审批 显示
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").show();
			}else{
				view.contentPanel.form.findField("SC_DATE").hide();
				view.contentPanel.form.findField("CC_DATE").hide();
				view.contentPanel.form.findField("LEVEL1_DATE").hide();
				view.contentPanel.form.findField("LEVEL2_DATE").hide();
				view.contentPanel.form.findField("LEVEL34_DATE").hide();
			}
			
			if(view.contentPanel.form.findField("IF_SURE").getValue() == '1'){//额度是否核准 为是时
				
				view.contentPanel.form.findField("INSURE_AMT").show();
				view.contentPanel.form.findField("INSURE_CURRENCY").show();
				view.contentPanel.form.findField("INSURE_MONEY").show();
				view.contentPanel.form.findField("INSURE_FORM").show();
				
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = false;
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
				view.contentPanel.form.findField("INSURE_FORM").allowBlank = false;
				
				view.contentPanel.form.findField("DELINE_REASON").hide();
				view.contentPanel.form.findField("RESON_REMARK").hide();
				
				view.contentPanel.form.findField("DELINE_REASON").allowBlank = true;
				view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;
				
			}else if(view.contentPanel.form.findField("IF_SURE").getValue() == '0'){//额度是否核准 为否时
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_FORM").hide();
				
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
				view.contentPanel.form.findField("INSURE_FORM").setValue('');
				
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_FORM").allowBlank = true;
				
				view.contentPanel.form.findField("DELINE_REASON").show();
				view.contentPanel.form.findField("DELINE_REASON").allowBlank = false;
				if(view.contentPanel.form.findField("DELINE_REASON").getValue == '4'){//拒绝批核 原因为4 说明显示
					view.contentPanel.form.findField("RESON_REMARK").show();
					view.contentPanel.form.findField("RESON_REMARK").allowBlank = false;
				}else{
					view.contentPanel.form.findField("RESON_REMARK").hide();
					view.contentPanel.form.findField("RESON_REMARK").setValue('');
					view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;
				}
			}else{			
				
				view.contentPanel.form.findField("INSURE_AMT").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").hide();
				view.contentPanel.form.findField("INSURE_FORM").hide();
				view.contentPanel.form.findField("DELINE_REASON").hide();
				view.contentPanel.form.findField("RESON_REMARK").hide();
				
				view.contentPanel.form.findField("INSURE_AMT").setValue('');
				view.contentPanel.form.findField("INSURE_CURRENCY").setValue('');
				view.contentPanel.form.findField("INSURE_MONEY").setValue('');
				view.contentPanel.form.findField("INSURE_FORM").setValue('');
				view.contentPanel.form.findField("DELINE_REASON").setValue('');
				view.contentPanel.form.findField("RESON_REMARK").setValue('');
				
				view.contentPanel.form.findField("INSURE_AMT").allowBlank = true;
				view.contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
				view.contentPanel.form.findField("INSURE_FORM").allowBlank = true;
				view.contentPanel.form.findField("DELINE_REASON").alllowBlank = true;
				view.contentPanel.form.findField("RESON_REMARK").allowBlank = true;
			}
		}						
};
var afterconditionrender = function(panel, app) {
	app.searchDomain.searchPanel.getForm()
	.findField("PIPELINE_ID").setValue(parent.window.document.getElementById('condition').value);
};
