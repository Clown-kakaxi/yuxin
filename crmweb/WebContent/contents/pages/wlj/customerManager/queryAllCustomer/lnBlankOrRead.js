/**
 * 客户信息管理页面的必输项与只读项的控制
 * @author sunjing5
 * 2017-05-25
 */
//潜在客户不必输项
var qzBlank=function(){
	//第一页必输项：
	if(qzCombaseInfo.form.findField('SHORT_NAME').label!=undefined){
		qzCombaseInfo.form.findField('SHORT_NAME').label.dom.innerHTML='客户简称:';
		qzCombaseInfo.form.findField('ORG_CUST_TYPE').label.dom.innerHTML='客户类型:';
		qzCombaseInfo.form.findField('LOAN_ORG_TYPE').label.dom.innerHTML='组织机构类别:';
		qzCombaseInfo.form.findField('FLAG_CAP_DTL').label.dom.innerHTML='组织机构类别细分:';
		qzCombaseInfo.form.findField('NATION_CODE').label.dom.innerHTML='企业所在国别:';
		qzCombaseInfo.form.findField('IN_CLL_TYPE').label.dom.innerHTML='行业类别:';
		qzCombaseInfo.form.findField('EMPLOYEE_SCALE').label.dom.innerHTML='从业人数:';
		qzCombaseInfo.form.findField('ENT_SCALE').label.dom.innerHTML='企业规模(银监):';
		qzCombaseInfo.form.findField('ENT_PROPERTY').label.dom.innerHTML='企业性质:';
		qzCombaseInfo.form.findField('REGISTER_TYPE').label.dom.innerHTML='经济类型:';
		qzCombaseInfo.form.findField('REG_CODE_TYPE').label.dom.innerHTML='登记注册号类型:';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').label.dom.innerHTML='注册资金币种:';
		qzCombaseInfo.form.findField('REGISTER_NO').label.dom.innerHTML='登记注册号码:';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL').label.dom.innerHTML='注册资金(万元):';
		qzCombaseInfo.form.findField('ANNUAL_INCOME').label.dom.innerHTML='预计营业收入(万元):';
		qzCombaseInfo.form.findField('TOTAL_ASSETS').label.dom.innerHTML='预计资产总额(万元):';
		qzCombaseInfo.form.findField('LOAN_CARD_NO').label.dom.innerHTML='中征码:';
		qzCombaseInfo.form.findField('CREDIT_CODE').label.dom.innerHTML='机构信用代码:';
		qzCombaseInfo.form.findField('LEGAL_REPR_NAME').label.dom.innerHTML='法定代表人姓名:';
		qzCombaseInfo.form.findField('INVEST_TYPE').label.dom.innerHTML='投资主体:';
		qzCombaseInfo.form.findField('REGISTER_DATE').label.dom.innerHTML='注册登记日期:';
		qzCombaseInfo.form.findField('END_DATE').label.dom.innerHTML='注册登记证到期日:';
		qzCombaseInfo.form.findField('REGISTER_ADDR').label.dom.innerHTML='注册（登记）地址:';
		qzCombaseInfo.form.findField('ADDR0').label.dom.innerHTML='实际经营地址:';
		qzCombaseInfo.form.findField('REGISTER_AREA').label.dom.innerHTML='行政区划名称:';
		qzCombaseInfo.form.findField('MAIN_BUSINESS').label.dom.innerHTML='主营业务:';
		qzCombaseInfo.form.findField('MINOR_BUSINESS').label.dom.innerHTML='兼营业务:';
		qzCombaseInfo.form.findField('BUILD_DATE').label.dom.innerHTML='成立日期:';
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').label.dom.innerHTML='控股类型:';
		
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').label.dom.innerHTML='控股类型:';
		qzCombaseInfo.form.findField('END_DATE').label.dom.innerHTML='注册登记证有效期:';
		qzCombaseInfo.form.findField('AREA_REG_CODE').label.dom.innerHTML='地税税务登记代码:';
		qzCombaseInfo.form.findField('NATION_REG_CODE').label.dom.innerHTML='国税税务登记代码:';
	}else{
		qzCombaseInfo.form.findField('SHORT_NAME').fieldLabel='客户简称';
		qzCombaseInfo.form.findField('ORG_CUST_TYPE').fieldLabel='客户类型';
		qzCombaseInfo.form.findField('LOAN_ORG_TYPE').fieldLabel='组织机构类别';
		qzCombaseInfo.form.findField('FLAG_CAP_DTL').fieldLabel='组织机构类别细分';
		qzCombaseInfo.form.findField('NATION_CODE').fieldLabel='企业所在国别';
		qzCombaseInfo.form.findField('IN_CLL_TYPE').fieldLabel='行业类别';
		qzCombaseInfo.form.findField('EMPLOYEE_SCALE').fieldLabel='从业人数';
		qzCombaseInfo.form.findField('ENT_SCALE').fieldLabel='企业规模(银监)';
		qzCombaseInfo.form.findField('ENT_PROPERTY').fieldLabel='企业性质';
		qzCombaseInfo.form.findField('REGISTER_TYPE').fieldLabel='经济类型';
		qzCombaseInfo.form.findField('REG_CODE_TYPE').fieldLabel='登记注册号类型';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').fieldLabel='注册资金币种';
		qzCombaseInfo.form.findField('REGISTER_NO').fieldLabel='登记注册号码';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL').fieldLabel='注册资金(万元)';
		qzCombaseInfo.form.findField('ANNUAL_INCOME').fieldLabel='预计营业收入(万元)';
		qzCombaseInfo.form.findField('TOTAL_ASSETS').fieldLabel='预计资产总额(万元)';
		qzCombaseInfo.form.findField('LOAN_CARD_NO').fieldLabel='中征码';
		qzCombaseInfo.form.findField('CREDIT_CODE').fieldLabel='机构信用代码';
		qzCombaseInfo.form.findField('LEGAL_REPR_NAME').fieldLabel='法定代表人姓名';
		qzCombaseInfo.form.findField('INVEST_TYPE').fieldLabel='投资主体';
		qzCombaseInfo.form.findField('REGISTER_DATE').fieldLabel='注册登记日期';
		qzCombaseInfo.form.findField('END_DATE').fieldLabel='注册登记证到期日';
		qzCombaseInfo.form.findField('REGISTER_ADDR').fieldLabel='注册（登记）地址';
		qzCombaseInfo.form.findField('ADDR0').fieldLabel='实际经营地址';
		qzCombaseInfo.form.findField('REGISTER_AREA').fieldLabel='行政区划名称';
		qzCombaseInfo.form.findField('MAIN_BUSINESS').fieldLabel='主营业务';
		qzCombaseInfo.form.findField('MINOR_BUSINESS').fieldLabel='兼营业务';
		qzCombaseInfo.form.findField('BUILD_DATE').fieldLabel='成立日期';
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').fieldLabel='控股类型';
		
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').fieldLabel='控股类型';
		qzCombaseInfo.form.findField('END_DATE').fieldLabel='注册登记证有效期';
		qzCombaseInfo.form.findField('AREA_REG_CODE').fieldLabel='地税税务登记代码';
		qzCombaseInfo.form.findField('NATION_REG_CODE').fieldLabel='国税税务登记代码';
	}
	qzCombaseInfo.form.findField('SHORT_NAME').allowBlank=true;	
	qzCombaseInfo.form.findField('ORG_CUST_TYPE').allowBlank=true;
	qzCombaseInfo.form.findField('LOAN_ORG_TYPE').allowBlank=true;	
	qzCombaseInfo.form.findField('FLAG_CAP_DTL').allowBlank=true;	
	qzCombaseInfo.form.findField('NATION_CODE').allowBlank=true;	
	qzCombaseInfo.form.findField('IN_CLL_TYPE').allowBlank=true;
	qzCombaseInfo.form.findField('EMPLOYEE_SCALE').allowBlank=true;	
	qzCombaseInfo.form.findField('ENT_SCALE').allowBlank=true;	
	qzCombaseInfo.form.findField('ENT_PROPERTY').allowBlank=true;
	qzCombaseInfo.form.findField('REGISTER_TYPE').allowBlank=true;	
	qzCombaseInfo.form.findField('REG_CODE_TYPE').allowBlank=true;	
	qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').allowBlank=true;	
	qzCombaseInfo.form.findField('REGISTER_NO').allowBlank=true;	
	qzCombaseInfo.form.findField('REGISTER_CAPITAL').allowBlank=true;
	qzCombaseInfo.form.findField('ANNUAL_INCOME').allowBlank=true;	
	qzCombaseInfo.form.findField('TOTAL_ASSETS').allowBlank=true;
	qzCombaseInfo.form.findField('LOAN_CARD_NO').allowBlank=true;
	qzCombaseInfo.form.findField('CREDIT_CODE').allowBlank=true;
	qzCombaseInfo.form.findField('LEGAL_REPR_NAME').allowBlank=true;
	qzCombaseInfo.form.findField('INVEST_TYPE').allowBlank=true;
	qzCombaseInfo.form.findField('REGISTER_DATE').allowBlank=true;
	qzCombaseInfo.form.findField('END_DATE').allowBlank=true;
	qzCombaseInfo.form.findField('REGISTER_ADDR').allowBlank=true;
	qzCombaseInfo.form.findField('ADDR0').allowBlank=true;
	qzCombaseInfo.form.findField('REGISTER_AREA').allowBlank=true;
	qzCombaseInfo.form.findField('MAIN_BUSINESS').allowBlank=true;
	qzCombaseInfo.form.findField('MINOR_BUSINESS').allowBlank=true;
	qzCombaseInfo.form.findField('BUILD_DATE').allowBlank=true;
	qzCombaseInfo.form.findField('COM_HOLD_TYPE').allowBlank=true;
	
	qzCombaseInfo.form.findField('COM_HOLD_TYPE').allowBlank=true;
	qzCombaseInfo.form.findField('END_DATE').allowBlank=true;
	qzCombaseInfo.form.findField('AREA_REG_CODE').allowBlank=true;
	qzCombaseInfo.form.findField('NATION_REG_CODE').allowBlank=true;
	setMust(qzComLists,'IS_TAIWAN_CORP','是否台资企业',false);//是否台资企业非必输
	//第二页不必输项：
	if(qzComLists.form.findField('IS_NOT_LOCAL_ENT').label!=undefined){
		qzComLists.form.findField('IS_NOT_LOCAL_ENT').label.dom.innerHTML='是否异地客户:';
		qzComLists.form.findField('IS_LISTED_CORP').label.dom.innerHTML='上市公司标志:';
		qzComLists.form.findField('IS_STEEL_ENT').label.dom.innerHTML='是否钢贸行业:';
		qzComLists.form.findField('AR_CUST_FLAG').label.dom.innerHTML='AR客户标志(CSPS):';
		qzComLists.form.findField('SHIPPING_IND').label.dom.innerHTML='是否为航运行业（银监统计）:';
		qzComLists.form.findField('AR_CUST_TYPE').label.dom.innerHTML='AR客户类型(CSPS):';
		qzComLists.form.findField('IS_NEW_CORP').label.dom.innerHTML='是否2年内新设立企业:';
		qzComLists.form.findField('LNCUSTP').label.dom.innerHTML='企业类型:';
		qzComLists.form.findField('ORG_SUB_TYPE').label.dom.innerHTML='特殊监管区:';
		qzComLists.form.findField('COM_SP_BUSINESS').label.dom.innerHTML='特种经营标示:';
		qzComLists.form.findField('IS_FAX_TRANS_CUST').label.dom.innerHTML='是否传真交易指示标志:';
		qzComLists.form.findField('INOUT_FLAG').label.dom.innerHTML='境内境外标志:';
	}else{
		qzComLists.form.findField('IS_NOT_LOCAL_ENT').fieldLabel='是否异地客户';
		qzComLists.form.findField('IS_LISTED_CORP').fieldLabel='上市公司标志';
		qzComLists.form.findField('IS_STEEL_ENT').fieldLabel='是否钢贸行业';
		qzComLists.form.findField('AR_CUST_FLAG').fieldLabel='AR客户标志(CSPS)';
		qzComLists.form.findField('SHIPPING_IND').fieldLabel='是否为航运行业（银监统计）';
		qzComLists.form.findField('AR_CUST_TYPE').fieldLabel='AR客户类型(CSPS)';
		qzComLists.form.findField('IS_NEW_CORP').fieldLabel='是否2年内新设立企业';
		qzComLists.form.findField('LNCUSTP').fieldLabel='企业类型';
		qzComLists.form.findField('ORG_SUB_TYPE').fieldLabel='特殊监管区';
		qzComLists.form.findField('COM_SP_BUSINESS').fieldLabel='特种经营标示';
		qzComLists.form.findField('IS_FAX_TRANS_CUST').fieldLabel='是否传真交易指示标志';
		qzComLists.form.findField('INOUT_FLAG').fieldLabel='境内境外标志';
	}
	qzComLists.form.findField('IS_NOT_LOCAL_ENT').allowBlank=true;	
	qzComLists.form.findField('IS_LISTED_CORP').allowBlank=true;	
	qzComLists.form.findField('IS_STEEL_ENT').allowBlank=true;	
	qzComLists.form.findField('AR_CUST_FLAG').allowBlank=true;	
	qzComLists.form.findField('SHIPPING_IND').allowBlank=true;
	qzComLists.form.findField('AR_CUST_TYPE').allowBlank=true;
	qzComLists.form.findField('IS_NEW_CORP').allowBlank=true;
	qzComLists.form.findField('LNCUSTP').allowBlank=true;
	qzComLists.form.findField('ORG_SUB_TYPE').allowBlank=true;
	qzComLists.form.findField('COM_SP_BUSINESS').allowBlank=true;
	qzComLists.form.findField('IS_FAX_TRANS_CUST').allowBlank=true;
	qzComLists.form.findField('INOUT_FLAG').allowBlank=true;
}
//临时户必输项与不必输项
var lnLsBlank=function(){
	//第一页必输项：
	if(qzCombaseInfo.form.findField('SHORT_NAME').label!=undefined){
		qzCombaseInfo.form.findField('SHORT_NAME').label.dom.innerHTML='<font color="red">*</font>客户简称:';
		qzCombaseInfo.form.findField('ORG_CUST_TYPE').label.dom.innerHTML='<font color="red">*</font>客户类型:';
		qzCombaseInfo.form.findField('LOAN_ORG_TYPE').label.dom.innerHTML='<font color="red">*</font>组织机构类别:';
		qzCombaseInfo.form.findField('FLAG_CAP_DTL').label.dom.innerHTML='<font color="red">*</font>组织机构类别细分:';
		qzCombaseInfo.form.findField('NATION_CODE').label.dom.innerHTML='<font color="red">*</font>企业所在国别:';
		qzCombaseInfo.form.findField('IN_CLL_TYPE').label.dom.innerHTML='<font color="red">*</font>行业类别:';
		qzCombaseInfo.form.findField('EMPLOYEE_SCALE').label.dom.innerHTML='<font color="red">*</font>从业人数:';
//		qzCombaseInfo.form.findField('ENT_SCALE').label.dom.innerHTML='<font color="red">*</font>企业规模(银监):';
		qzCombaseInfo.form.findField('ENT_PROPERTY').label.dom.innerHTML='<font color="red">*</font>企业性质:';
		qzCombaseInfo.form.findField('REGISTER_TYPE').label.dom.innerHTML='<font color="red">*</font>经济类型:';
		qzCombaseInfo.form.findField('REG_CODE_TYPE').label.dom.innerHTML='<font color="red">*</font>登记注册号类型:';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').label.dom.innerHTML='<font color="red">*</font>注册资金币种:';
		qzCombaseInfo.form.findField('REGISTER_NO').label.dom.innerHTML='<font color="red">*</font>登记注册号码:';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL').label.dom.innerHTML='<font color="red">*</font>注册资金(万元):';
		qzCombaseInfo.form.findField('ANNUAL_INCOME').label.dom.innerHTML='<font color="red">*</font>预计营业收入(万元):';
		qzCombaseInfo.form.findField('TOTAL_ASSETS').label.dom.innerHTML='<font color="red">*</font>预计资产总额(万元):';
//		qzCombaseInfo.form.findField('LOAN_CARD_NO').label.dom.innerHTML='<font color="red">*</font>中征码:';
		qzCombaseInfo.form.findField('LEGAL_REPR_NAME').label.dom.innerHTML='<font color="red">*</font>法定代表人姓名:';
		qzCombaseInfo.form.findField('REGISTER_ADDR').label.dom.innerHTML='<font color="red">*</font>注册（登记）地址:';
		qzCombaseInfo.form.findField('BUILD_DATE').label.dom.innerHTML='<font color="red">*</font>成立日期:';
		qzCombaseInfo.form.findField('MAIN_BUSINESS').label.dom.innerHTML='<font color="red">*</font>主营业务:';
		
		qzCombaseInfo.form.findField('INVEST_TYPE').label.dom.innerHTML='投资主体:';
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').label.dom.innerHTML='控股类型:';
		qzCombaseInfo.form.findField('REGISTER_DATE').label.dom.innerHTML='注册登记日期:';
		qzCombaseInfo.form.findField('END_DATE').label.dom.innerHTML='注册登记证到期日:';
		qzCombaseInfo.form.findField('ADDR0').label.dom.innerHTML='实际经营地址:';
		qzCombaseInfo.form.findField('REGISTER_AREA').label.dom.innerHTML='<font color="red">*</font>行政区划:';
		qzCombaseInfo.form.findField('MINOR_BUSINESS').label.dom.innerHTML='兼营业务:';
		
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').label.dom.innerHTML='控股类型:';
		qzCombaseInfo.form.findField('END_DATE').label.dom.innerHTML='注册登记证有效期:';
		qzCombaseInfo.form.findField('AREA_REG_CODE').label.dom.innerHTML='地税税务登记代码:';
		qzCombaseInfo.form.findField('NATION_REG_CODE').label.dom.innerHTML='国税税务登记代码:';
	}else{
		qzCombaseInfo.form.findField('SHORT_NAME').fieldLabel='<font color="red">*</font>客户简称';
		qzCombaseInfo.form.findField('ORG_CUST_TYPE').fieldLabel='<font color="red">*</font>客户类型';
		qzCombaseInfo.form.findField('LOAN_ORG_TYPE').fieldLabel='<font color="red">*</font>组织机构类别';
		qzCombaseInfo.form.findField('FLAG_CAP_DTL').fieldLabel='<font color="red">*</font>组织机构类别细分';
		qzCombaseInfo.form.findField('NATION_CODE').fieldLabel='<font color="red">*</font>企业所在国别';
		qzCombaseInfo.form.findField('IN_CLL_TYPE').fieldLabel='<font color="red">*</font>行业类别';
		qzCombaseInfo.form.findField('EMPLOYEE_SCALE').fieldLabel='<font color="red">*</font>从业人数';
//		qzCombaseInfo.form.findField('ENT_SCALE').fieldLabel='<font color="red">*</font>企业规模(银监)';
		qzCombaseInfo.form.findField('ENT_PROPERTY').fieldLabel='<font color="red">*</font>企业性质';
		qzCombaseInfo.form.findField('REGISTER_TYPE').fieldLabel='<font color="red">*</font>经济类型';
		qzCombaseInfo.form.findField('REG_CODE_TYPE').fieldLabel='<font color="red">*</font>登记注册号类型';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').fieldLabel='<font color="red">*</font>注册资金币种';
		qzCombaseInfo.form.findField('REGISTER_NO').fieldLabel='<font color="red">*</font>登记注册号码';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL').fieldLabel='<font color="red">*</font>注册资金(万元)';
		qzCombaseInfo.form.findField('ANNUAL_INCOME').fieldLabel='<font color="red">*</font>预计营业收入(万元)';
		qzCombaseInfo.form.findField('TOTAL_ASSETS').fieldLabel='<font color="red">*</font>预计资产总额(万元)';
//		qzCombaseInfo.form.findField('LOAN_CARD_NO').fieldLabel='<font color="red">*</font>中征码';
		qzCombaseInfo.form.findField('LEGAL_REPR_NAME').fieldLabel='<font color="red">*</font>法定代表人姓名';
		qzCombaseInfo.form.findField('BUILD_DATE').fieldLabel='<font color="red">*</font>成立日期';
		qzCombaseInfo.form.findField('REGISTER_ADDR').fieldLabel='<font color="red">*</font>注册（登记）地址';

		
		qzCombaseInfo.form.findField('INVEST_TYPE').fieldLabel='投资主体';
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').fieldLabel='控股类型';
		qzCombaseInfo.form.findField('REGISTER_DATE').fieldLabel='注册登记日期';
		qzCombaseInfo.form.findField('END_DATE').fieldLabel='注册登记证到期日';
		
		qzCombaseInfo.form.findField('ADDR0').fieldLabel='实际经营地址';
		qzCombaseInfo.form.findField('REGISTER_AREA').fieldLabel='<font color="red">*</font>行政区划名称';
		qzCombaseInfo.form.findField('MAIN_BUSINESS').fieldLabel='<font color="red">*</font>主营业务';
		qzCombaseInfo.form.findField('MINOR_BUSINESS').fieldLabel='兼营业务';
		
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').fieldLabel='控股类型';
		qzCombaseInfo.form.findField('END_DATE').fieldLabel='注册登记证有效期';
		qzCombaseInfo.form.findField('AREA_REG_CODE').fieldLabel='地税税务登记代码';
		qzCombaseInfo.form.findField('NATION_REG_CODE').fieldLabel='国税税务登记代码';
	}
	qzCombaseInfo.form.findField('SHORT_NAME').allowBlank=false;	
	qzCombaseInfo.form.findField('ORG_CUST_TYPE').allowBlank=false;
	qzCombaseInfo.form.findField('LOAN_ORG_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('FLAG_CAP_DTL').allowBlank=false;	
	qzCombaseInfo.form.findField('NATION_CODE').allowBlank=false;	
	qzCombaseInfo.form.findField('IN_CLL_TYPE').allowBlank=false;
	qzCombaseInfo.form.findField('EMPLOYEE_SCALE').allowBlank=false;	
//	qzCombaseInfo.form.findField('ENT_SCALE').allowBlank=false;	
	qzCombaseInfo.form.findField('ENT_PROPERTY').allowBlank=false;
	qzCombaseInfo.form.findField('REGISTER_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('REG_CODE_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_NO').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_CAPITAL').allowBlank=false;
	qzCombaseInfo.form.findField('ANNUAL_INCOME').allowBlank=false;	
	qzCombaseInfo.form.findField('TOTAL_ASSETS').allowBlank=false;
//	qzCombaseInfo.form.findField('LOAN_CARD_NO').allowBlank=false;
	qzCombaseInfo.form.findField('LEGAL_REPR_NAME').allowBlank=false;
	qzCombaseInfo.form.findField('BUILD_DATE').allowBlank=false;
	qzCombaseInfo.form.findField('REGISTER_ADDR').allowBlank=false;
	
	qzCombaseInfo.form.findField('INVEST_TYPE').allowBlank=true;
	qzCombaseInfo.form.findField('COM_HOLD_TYPE').allowBlank=true;
	qzCombaseInfo.form.findField('REGISTER_DATE').allowBlank=true;
	qzCombaseInfo.form.findField('END_DATE').allowBlank=true;
	qzCombaseInfo.form.findField('ADDR0').allowBlank=true;
	qzCombaseInfo.form.findField('REGISTER_AREA').allowBlank=false;//必输
	qzCombaseInfo.form.findField('MAIN_BUSINESS').allowBlank=false;//必输
	qzCombaseInfo.form.findField('MINOR_BUSINESS').allowBlank=true;

	qzCombaseInfo.form.findField('COM_HOLD_TYPE').allowBlank=true;
	qzCombaseInfo.form.findField('END_DATE').allowBlank=true;
	qzCombaseInfo.form.findField('AREA_REG_CODE').allowBlank=true;
	qzCombaseInfo.form.findField('NATION_REG_CODE').allowBlank=true;
	
	//第二页必输项：
	if(qzComLists.form.findField('IS_NOT_LOCAL_ENT').label!=undefined){
		qzComLists.form.findField('IS_NOT_LOCAL_ENT').label.dom.innerHTML='<font color="red">*</font>是否异地客户:';
		qzComLists.form.findField('IS_LISTED_CORP').label.dom.innerHTML='<font color="red">*</font>上市公司标志:';
		qzComLists.form.findField('IS_STEEL_ENT').label.dom.innerHTML='<font color="red">*</font>是否钢贸行业:';
		qzComLists.form.findField('AR_CUST_FLAG').label.dom.innerHTML='<font color="red">*</font>AR客户标志(CSPS):';
		qzComLists.form.findField('SHIPPING_IND').label.dom.innerHTML='<font color="red">*</font>是否为航运行业（银监统计）:';
//		qzComLists.form.findField('IS_NEW_CORP').label.dom.innerHTML='<font color="red">*</font>是否2年内新设立企业:';
	}
	else{
		qzComLists.form.findField('IS_NOT_LOCAL_ENT').fieldLabel='<font color="red">*</font>是否异地客户';
		qzComLists.form.findField('IS_LISTED_CORP').fieldLabel='<font color="red">*</font>上市公司标志';
		qzComLists.form.findField('IS_STEEL_ENT').fieldLabel='<font color="red">*</font>是否钢贸行业';
		qzComLists.form.findField('AR_CUST_FLAG').fieldLabel='<font color="red">*</font>AR客户标志(CSPS)';
		qzComLists.form.findField('SHIPPING_IND').fieldLabel='<font color="red">*</font>是否为航运行业（银监统计）';
//		qzComLists.form.findField('IS_NEW_CORP').fieldLabel='<font color="red">*</font>是否2年内新设立企业:';
	}
	qzComLists.form.findField('IS_NOT_LOCAL_ENT').allowBlank=false;	
	qzComLists.form.findField('IS_LISTED_CORP').allowBlank=false;	
	qzComLists.form.findField('IS_STEEL_ENT').allowBlank=false;	
	qzComLists.form.findField('AR_CUST_FLAG').allowBlank=false;	
	qzComLists.form.findField('SHIPPING_IND').allowBlank=false;
//	qzComLists.form.findField('IS_NEW_CORP').allowBlank=false;
	setMust(qzComLists,'IS_TAIWAN_CORP','是否台资企业',false);//是否台资企业非必输
    setMust(qzComLists,'IS_FAX_TRANS_CUST','是否传真交易指示标志',false);//是否传真交易指示标志非必输
};
//准正式户必输项
var lnZzsBlank=function(){
	//第一页必输项：
	if(qzCombaseInfo.form.findField('SHORT_NAME').label!=undefined){
		qzCombaseInfo.form.findField('SHORT_NAME').label.dom.innerHTML='<font color="red">*</font>客户简称:';
		qzCombaseInfo.form.findField('LOAN_ORG_TYPE').label.dom.innerHTML='<font color="red">*</font>组织机构类别:';
		qzCombaseInfo.form.findField('FLAG_CAP_DTL').label.dom.innerHTML='<font color="red">*</font>组织机构类别细分:';
		qzCombaseInfo.form.findField('NATION_CODE').label.dom.innerHTML='<font color="red">*</font>企业所在国别:';
		qzCombaseInfo.form.findField('IN_CLL_TYPE').label.dom.innerHTML='<font color="red">*</font>行业类别:';
		qzCombaseInfo.form.findField('EMPLOYEE_SCALE').label.dom.innerHTML='<font color="red">*</font>从业人数:';
//		qzCombaseInfo.form.findField('ENT_SCALE').label.dom.innerHTML='<font color="red">*</font>企业规模(银监):';
		qzCombaseInfo.form.findField('ENT_PROPERTY').label.dom.innerHTML='<font color="red">*</font>企业性质:';
		qzCombaseInfo.form.findField('REGISTER_TYPE').label.dom.innerHTML='<font color="red">*</font>经济类型:';
		qzCombaseInfo.form.findField('REG_CODE_TYPE').label.dom.innerHTML='<font color="red">*</font>登记注册号类型:';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').label.dom.innerHTML='<font color="red">*</font>注册资金币种:';
		qzCombaseInfo.form.findField('REGISTER_NO').label.dom.innerHTML='<font color="red">*</font>登记注册号码:';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL').label.dom.innerHTML='<font color="red">*</font>注册资金(万元):';
		qzCombaseInfo.form.findField('ANNUAL_INCOME').label.dom.innerHTML='<font color="red">*</font>预计营业收入(万元):';
		qzCombaseInfo.form.findField('TOTAL_ASSETS').label.dom.innerHTML='<font color="red">*</font>预计资产总额(万元):';
//		qzCombaseInfo.form.findField('LOAN_CARD_NO').label.dom.innerHTML='<font color="red">*</font>中征码:';
		qzCombaseInfo.form.findField('BUILD_DATE').label.dom.innerHTML='<font color="red">*</font>成立日期:';
		qzCombaseInfo.form.findField('INVEST_TYPE').label.dom.innerHTML='<font color="red">*</font>投资主体:';
		
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').label.dom.innerHTML='<font color="red">*</font>控股类型:';
		qzCombaseInfo.form.findField('REGISTER_DATE').label.dom.innerHTML='<font color="red">*</font>注册登记日期:';
		qzCombaseInfo.form.findField('END_DATE').label.dom.innerHTML='<font color="red">*</font>注册登记证有效期:';
		qzCombaseInfo.form.findField('REGISTER_ADDR').label.dom.innerHTML='<font color="red">*</font>注册（登记）地址:';
		qzCombaseInfo.form.findField('ADDR0').label.dom.innerHTML='<font color="red">*</font>实际经营地址:';
		qzCombaseInfo.form.findField('REGISTER_AREA').label.dom.innerHTML='<font color="red">*</font>行政区划名称:';
		qzCombaseInfo.form.findField('MAIN_BUSINESS').label.dom.innerHTML='<font color="red">*</font>主营业务:';
		qzCombaseInfo.form.findField('MINOR_BUSINESS').label.dom.innerHTML='<font color="red">*</font>兼营业务:';
		qzCombaseInfo.form.findField('LEGAL_REPR_NAME').label.dom.innerHTML='<font color="red">*</font>法定代表人姓名:';
		
		qzCombaseInfo.form.findField('AREA_REG_CODE').label.dom.innerHTML='地税税务登记代码:';
		qzCombaseInfo.form.findField('NATION_REG_CODE').label.dom.innerHTML='国税税务登记代码:';
	}else{
		qzCombaseInfo.form.findField('SHORT_NAME').fieldLabel='<font color="red">*</font>客户简称';
		qzCombaseInfo.form.findField('LOAN_ORG_TYPE').fieldLabel='<font color="red">*</font>组织机构类别';
		qzCombaseInfo.form.findField('FLAG_CAP_DTL').fieldLabel='<font color="red">*</font>组织机构类别细分';
		qzCombaseInfo.form.findField('NATION_CODE').fieldLabel='<font color="red">*</font>企业所在国别';
		qzCombaseInfo.form.findField('IN_CLL_TYPE').fieldLabel='<font color="red">*</font>行业类别';
		qzCombaseInfo.form.findField('EMPLOYEE_SCALE').fieldLabel='<font color="red">*</font>从业人数';
//		qzCombaseInfo.form.findField('ENT_SCALE').fieldLabel='<font color="red">*</font>企业规模(银监)';
		qzCombaseInfo.form.findField('ENT_PROPERTY').fieldLabel='<font color="red">*</font>企业性质';
		qzCombaseInfo.form.findField('REGISTER_TYPE').fieldLabel='<font color="red">*</font>经济类型';
		qzCombaseInfo.form.findField('REG_CODE_TYPE').fieldLabel='<font color="red">*</font>登记注册号类型';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').fieldLabel='<font color="red">*</font>注册资金币种';
		qzCombaseInfo.form.findField('REGISTER_NO').fieldLabel='<font color="red">*</font>登记注册号码';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL').fieldLabel='<font color="red">*</font>注册资金(万元)';
		qzCombaseInfo.form.findField('ANNUAL_INCOME').fieldLabel='<font color="red">*</font>预计营业收入(万元)';
		qzCombaseInfo.form.findField('TOTAL_ASSETS').fieldLabel='<font color="red">*</font>预计资产总额(万元)';
//		qzCombaseInfo.form.findField('LOAN_CARD_NO').fieldLabel='<font color="red">*</font>中征码';
		qzCombaseInfo.form.findField('BUILD_DATE').fieldLabel='<font color="red">*</font>成立日期';
		qzCombaseInfo.form.findField('INVEST_TYPE').fieldLabel='<font color="red">*</font>投资主体';
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').fieldLabel='<font color="red">*</font>控股类型';
		qzCombaseInfo.form.findField('REGISTER_DATE').fieldLabel='<font color="red">*</font>注册登记日期:';
		qzCombaseInfo.form.findField('END_DATE').fieldLabel='<font color="red">*</font>注册登记证到期日';
		qzCombaseInfo.form.findField('REGISTER_ADDR').fieldLabel='<font color="red">*</font>注册（登记）地址';
		qzCombaseInfo.form.findField('ADDR0').fieldLabel='<font color="red">*</font>实际经营地址';
		qzCombaseInfo.form.findField('REGISTER_AREA').fieldLabel='<font color="red">*</font>行政区划名称';
		qzCombaseInfo.form.findField('MAIN_BUSINESS').fieldLabel='<font color="red">*</font>主营业务';
		qzCombaseInfo.form.findField('MINOR_BUSINESS').fieldLabel='<font color="red">*</font>兼营业务';
		qzCombaseInfo.form.findField('LEGAL_REPR_NAME').fieldLabel='<font color="red">*</font>法定代表人姓名';
		
		qzCombaseInfo.form.findField('AREA_REG_CODE').fieldLabel='地税税务登记代码';
		qzCombaseInfo.form.findField('NATION_REG_CODE').fieldLabel='国税税务登记代码';
	}
	qzCombaseInfo.form.findField('SHORT_NAME').allowBlank=false;	
	qzCombaseInfo.form.findField('LOAN_ORG_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('FLAG_CAP_DTL').allowBlank=false;	
	qzCombaseInfo.form.findField('NATION_CODE').allowBlank=false;	
	qzCombaseInfo.form.findField('IN_CLL_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('EMPLOYEE_SCALE').allowBlank=false;	
//	qzCombaseInfo.form.findField('ENT_SCALE').allowBlank=false	
	qzCombaseInfo.form.findField('ENT_PROPERTY').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('REG_CODE_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').allowBlank=false;
	qzCombaseInfo.form.findField('REGISTER_NO').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_CAPITAL').allowBlank=false;	
	qzCombaseInfo.form.findField('ANNUAL_INCOME').allowBlank=false;	
	qzCombaseInfo.form.findField('TOTAL_ASSETS').allowBlank=false;
//	qzCombaseInfo.form.findField('LOAN_CARD_NO').allowBlank=false;
	qzCombaseInfo.form.findField('BUILD_DATE').allowBlank=false;
	qzCombaseInfo.form.findField('INVEST_TYPE').allowBlank=false;
	qzCombaseInfo.form.findField('COM_HOLD_TYPE').allowBlank=false;
	qzCombaseInfo.form.findField('REGISTER_DATE').allowBlank=false;
	qzCombaseInfo.form.findField('END_DATE').allowBlank=false;
	qzCombaseInfo.form.findField('REGISTER_ADDR').allowBlank=false;
	qzCombaseInfo.form.findField('ADDR0').allowBlank=false;
	qzCombaseInfo.form.findField('REGISTER_AREA').allowBlank=false;
	qzCombaseInfo.form.findField('MAIN_BUSINESS').allowBlank=false;
	qzCombaseInfo.form.findField('MINOR_BUSINESS').allowBlank=false;
	qzCombaseInfo.form.findField('LEGAL_REPR_NAME').allowBlank=false;
	
	qzCombaseInfo.form.findField('AREA_REG_CODE').allowBlank=true;
	qzCombaseInfo.form.findField('NATION_REG_CODE').allowBlank=true;
	
	//第二页必输项：
	if(qzComLists.form.findField('IS_NOT_LOCAL_ENT').label!=undefined){
		qzComLists.form.findField('IS_NOT_LOCAL_ENT').label.dom.innerHTML='<font color="red">*</font>是否异地客户:';
		qzComLists.form.findField('IS_LISTED_CORP').label.dom.innerHTML='<font color="red">*</font>上市公司标志:';
		qzComLists.form.findField('IS_STEEL_ENT').label.dom.innerHTML='<font color="red">*</font>是否钢贸行业:';
		qzComLists.form.findField('AR_CUST_FLAG').label.dom.innerHTML='<font color="red">*</font>AR客户标志(CSPS):';
//		qzComLists.form.findField('IS_NEW_CORP').label.dom.innerHTML='<font color="red">*</font>是否2年内新设立企业:';
		qzComLists.form.findField('INOUT_FLAG').label.dom.innerHTML='<font color="red">*</font>境内境外标志:';
		qzComLists.form.findField('SHIPPING_IND').label.dom.innerHTML='<font color="red">*</font>是否为航运行业（银监统计）:';
	}
	else{
		qzComLists.form.findField('IS_NOT_LOCAL_ENT').fieldLabel='<font color="red">*</font>是否异地客户';
		qzComLists.form.findField('IS_LISTED_CORP').fieldLabel='<font color="red">*</font>上市公司标志';
		qzComLists.form.findField('IS_STEEL_ENT').fieldLabel='<font color="red">*</font>是否钢贸行业';
		qzComLists.form.findField('AR_CUST_FLAG').fieldLabel='<font color="red">*</font>AR客户标志(CSPS)';
//		qzComLists.form.findField('IS_NEW_CORP').fieldLabel='<font color="red">*</font>是否2年内新设立企业';
		qzComLists.form.findField('INOUT_FLAG').fieldLabel='<font color="red">*</font>境内境外标志';
		qzComLists.form.findField('SHIPPING_IND').fieldLabel='<font color="red">*</font>是否为航运行业（银监统计）';
	}
	qzComLists.form.findField('IS_NOT_LOCAL_ENT').allowBlank=false;	
	qzComLists.form.findField('IS_LISTED_CORP').allowBlank=false;	
	qzComLists.form.findField('IS_STEEL_ENT').allowBlank=false;	
	qzComLists.form.findField('AR_CUST_FLAG').allowBlank=false;	
//	qzComLists.form.findField('IS_NEW_CORP').allowBlank=false;	
	qzComLists.form.findField('INOUT_FLAG').allowBlank=false;	
	qzComLists.form.findField('SHIPPING_IND').allowBlank=false;
	setMust(qzComLists,'IS_TAIWAN_CORP','是否台资企业',false);//是否台资企业非必输
    setMust(qzComLists,'IS_FAX_TRANS_CUST','是否传真交易指示标志',false);//是否传真交易指示标志非必输
};

//正式户必输项
var lnZshBlank=function(){
	//第一页必输项：
	if(qzCombaseInfo.form.findField('SHORT_NAME').label!=undefined){
		qzCombaseInfo.form.findField('SHORT_NAME').label.dom.innerHTML='<font color="red">*</font>客户简称:';
		qzCombaseInfo.form.findField('LOAN_ORG_TYPE').label.dom.innerHTML='<font color="red">*</font>组织机构类别:';
		qzCombaseInfo.form.findField('FLAG_CAP_DTL').label.dom.innerHTML='<font color="red">*</font>组织机构类别细分:';
		qzCombaseInfo.form.findField('NATION_CODE').label.dom.innerHTML='<font color="red">*</font>企业所在国别:';
		qzCombaseInfo.form.findField('IN_CLL_TYPE').label.dom.innerHTML='<font color="red">*</font>行业类别:';
		qzCombaseInfo.form.findField('EMPLOYEE_SCALE').label.dom.innerHTML='<font color="red">*</font>从业人数:';
//		qzCombaseInfo.form.findField('ENT_SCALE').label.dom.innerHTML='<font color="red">*</font>企业规模(银监):';
		qzCombaseInfo.form.findField('ENT_PROPERTY').label.dom.innerHTML='<font color="red">*</font>企业性质:';
		qzCombaseInfo.form.findField('REGISTER_TYPE').label.dom.innerHTML='<font color="red">*</font>经济类型:';
		qzCombaseInfo.form.findField('REG_CODE_TYPE').label.dom.innerHTML='<font color="red">*</font>登记注册号类型:';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').label.dom.innerHTML='<font color="red">*</font>注册资金币种:';
		qzCombaseInfo.form.findField('REGISTER_NO').label.dom.innerHTML='<font color="red">*</font>登记注册号码:';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL').label.dom.innerHTML='<font color="red">*</font>注册资金(万元):';
		qzCombaseInfo.form.findField('ANNUAL_INCOME').label.dom.innerHTML='<font color="red">*</font>预计营业收入(万元):';
		qzCombaseInfo.form.findField('TOTAL_ASSETS').label.dom.innerHTML='<font color="red">*</font>预计资产总额(万元):';
//		qzCombaseInfo.form.findField('LOAN_CARD_NO').label.dom.innerHTML='<font color="red">*</font>中征码:';
		qzCombaseInfo.form.findField('INVEST_TYPE').label.dom.innerHTML='<font color="red">*</font>投资主体:';
		qzCombaseInfo.form.findField('REGISTER_DATE').label.dom.innerHTML='<font color="red">*</font>注册登记日期:';
		qzCombaseInfo.form.findField('REGISTER_AREA').label.dom.innerHTML='<font color="red">*</font>行政区划名称:';
		qzCombaseInfo.form.findField('MAIN_BUSINESS').label.dom.innerHTML='<font color="red">*</font>主营业务:';
		qzCombaseInfo.form.findField('REGISTER_ADDR').label.dom.innerHTML='<font color="red">*</font>注册（登记）地址:';
		qzCombaseInfo.form.findField('ADDR0').label.dom.innerHTML='<font color="red">*</font>实际经营地址:';
		qzCombaseInfo.form.findField('MINOR_BUSINESS').label.dom.innerHTML='<font color="red">*</font>兼营业务:';
		
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').label.dom.innerHTML='<font color="red">*</font>控股类型:';
		qzCombaseInfo.form.findField('END_DATE').label.dom.innerHTML='<font color="red">*</font>注册登记证有效期:';
		qzCombaseInfo.form.findField('AREA_REG_CODE').label.dom.innerHTML='<font color="red">*</font>地税税务登记代码:';
		qzCombaseInfo.form.findField('NATION_REG_CODE').label.dom.innerHTML='<font color="red">*</font>国税税务登记代码:';
		qzCombaseInfo.form.findField('BUILD_DATE').label.dom.innerHTML='<font color="red">*</font>成立日期:';
		
	}
	else{
		qzCombaseInfo.form.findField('SHORT_NAME').fieldLabel='<font color="red">*</font>客户简称';
		qzCombaseInfo.form.findField('LOAN_ORG_TYPE').fieldLabel='<font color="red">*</font>组织机构类别';
		qzCombaseInfo.form.findField('FLAG_CAP_DTL').fieldLabel='<font color="red">*</font>组织机构类别细分';
		qzCombaseInfo.form.findField('NATION_CODE').fieldLabel='<font color="red">*</font>企业所在国别';
		qzCombaseInfo.form.findField('IN_CLL_TYPE').fieldLabel='<font color="red">*</font>行业类别';
		qzCombaseInfo.form.findField('EMPLOYEE_SCALE').fieldLabel='<font color="red">*</font>从业人数';
//		qzCombaseInfo.form.findField('ENT_SCALE').fieldLabel='<font color="red">*</font>企业规模(银监)';
		qzCombaseInfo.form.findField('ENT_PROPERTY').fieldLabel='<font color="red">*</font>企业性质';
		qzCombaseInfo.form.findField('REGISTER_TYPE').fieldLabel='<font color="red">*</font>经济类型';
		qzCombaseInfo.form.findField('REG_CODE_TYPE').fieldLabel='<font color="red">*</font>登记注册号类型';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').fieldLabel='<font color="red">*</font>注册资金币种';
		qzCombaseInfo.form.findField('REGISTER_NO').fieldLabel='<font color="red">*</font>登记注册号码';
		qzCombaseInfo.form.findField('REGISTER_CAPITAL').fieldLabel='<font color="red">*</font>注册资金(万元)';
		qzCombaseInfo.form.findField('ANNUAL_INCOME').fieldLabel='<font color="red">*</font>预计营业收入(万元)';
		qzCombaseInfo.form.findField('TOTAL_ASSETS').fieldLabel='<font color="red">*</font>预计资产总额(万元)';
//		qzCombaseInfo.form.findField('LOAN_CARD_NO').fieldLabel='<font color="red">*</font>中征码';
		qzCombaseInfo.form.findField('INVEST_TYPE').fieldLabel='<font color="red">*</font>投资主体';
		qzCombaseInfo.form.findField('REGISTER_DATE').fieldLabel='<font color="red">*</font>注册登记日期';
		qzCombaseInfo.form.findField('REGISTER_AREA').fieldLabel='<font color="red">*</font>行政区划名称';
		qzCombaseInfo.form.findField('MAIN_BUSINESS').fieldLabel='<font color="red">*</font>主营业务';
		qzCombaseInfo.form.findField('REGISTER_ADDR').fieldLabel='<font color="red">*</font>注册（登记）地址';
		qzCombaseInfo.form.findField('ADDR0').fieldLabel='<font color="red">*</font>实际经营地址';
		qzCombaseInfo.form.findField('MINOR_BUSINESS').fieldLabel='<font color="red">*</font>兼营业务';
		
		qzCombaseInfo.form.findField('COM_HOLD_TYPE').fieldLabel='<font color="red">*</font>控股类型';
		qzCombaseInfo.form.findField('END_DATE').fieldLabel='<font color="red">*</font>注册登记证有效期';
		qzCombaseInfo.form.findField('AREA_REG_CODE').fieldLabel='<font color="red">*</font>地税税务登记代码';
		qzCombaseInfo.form.findField('NATION_REG_CODE').fieldLabel='<font color="red">*</font>国税税务登记代码';
		qzCombaseInfo.form.findField('BUILD_DATE').fieldLabel='<font color="red">*</font>成立日期';
	}
	qzCombaseInfo.form.findField('SHORT_NAME').allowBlank=false;	
	qzCombaseInfo.form.findField('LOAN_ORG_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('FLAG_CAP_DTL').allowBlank=false;	
	qzCombaseInfo.form.findField('NATION_CODE').allowBlank=false;	
	qzCombaseInfo.form.findField('IN_CLL_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('EMPLOYEE_SCALE').allowBlank=false;	
//	qzCombaseInfo.form.findField('ENT_SCALE').allowBlank=false;	
	qzCombaseInfo.form.findField('ENT_PROPERTY').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('REG_CODE_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_CAPITAL_CURR').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_NO').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_CAPITAL').allowBlank=false;	
	qzCombaseInfo.form.findField('ANNUAL_INCOME').allowBlank=false;	
	qzCombaseInfo.form.findField('TOTAL_ASSETS').allowBlank=false;	
//	qzCombaseInfo.form.findField('LOAN_CARD_NO').allowBlank=false;
	qzCombaseInfo.form.findField('INVEST_TYPE').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_DATE').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_AREA').allowBlank=false;		
	qzCombaseInfo.form.findField('MAIN_BUSINESS').allowBlank=false;	
	qzCombaseInfo.form.findField('REGISTER_ADDR').allowBlank=false;	
	qzCombaseInfo.form.findField('ADDR0').allowBlank=false;	
	qzCombaseInfo.form.findField('MINOR_BUSINESS').allowBlank=false;
	
	qzCombaseInfo.form.findField('COM_HOLD_TYPE').allowBlank=false;
	qzCombaseInfo.form.findField('END_DATE').allowBlank=false;
	qzCombaseInfo.form.findField('AREA_REG_CODE').allowBlank=false;
	qzCombaseInfo.form.findField('NATION_REG_CODE').allowBlank=false;
	qzCombaseInfo.form.findField('BUILD_DATE').allowBlank=false;
	
	//第二页必输项：
	if(qzComLists.form.findField('IS_NOT_LOCAL_ENT').label!=undefined){
		qzComLists.form.findField('IS_NOT_LOCAL_ENT').label.dom.innerHTML='<font color="red">*</font>是否异地客户:';
		qzComLists.form.findField('IS_LISTED_CORP').label.dom.innerHTML='<font color="red">*</font>上市公司标志:';
		qzComLists.form.findField('IS_STEEL_ENT').label.dom.innerHTML='<font color="red">*</font>是否钢贸行业:';
		qzComLists.form.findField('AR_CUST_FLAG').label.dom.innerHTML='<font color="red">*</font>AR客户标志(CSPS):';
		qzComLists.form.findField('SHIPPING_IND').label.dom.innerHTML='<font color="red">*</font>是否为航运行业（银监统计）:';
		
		qzComLists.form.findField('COM_SP_BUSINESS').label.dom.innerHTML='<font color="red">*</font>特种经营标示:';
//		qzComLists.form.findField('IS_NEW_CORP').label.dom.innerHTML='<font color="red">*</font>是否2年内新设立企业:';
	
	}
	else{
		qzComLists.form.findField('IS_NOT_LOCAL_ENT').fieldLabel='<font color="red">*</font>是否异地客户';
		qzComLists.form.findField('IS_LISTED_CORP').fieldLabel='<font color="red">*</font>上市公司标志';
		qzComLists.form.findField('IS_STEEL_ENT').fieldLabel='<font color="red">*</font>是否钢贸行业';
		qzComLists.form.findField('AR_CUST_FLAG').fieldLabel='<font color="red">*</font>AR客户标志(CSPS)';
		qzComLists.form.findField('SHIPPING_IND').fieldLabel='<font color="red">*</font>是否为航运行业（银监统计）';
		
		qzComLists.form.findField('COM_SP_BUSINESS').fieldLabel='<font color="red">*</font>特种经营标示';
//		qzComLists.form.findField('IS_NEW_CORP').fieldLabel='<font color="red">*</font>是否2年内新设立企业';
	}
	qzComLists.form.findField('IS_NOT_LOCAL_ENT').allowBlank=false;	
	qzComLists.form.findField('IS_LISTED_CORP').allowBlank=false;	
	qzComLists.form.findField('IS_STEEL_ENT').allowBlank=false;	
	qzComLists.form.findField('AR_CUST_FLAG').allowBlank=false;	
	qzComLists.form.findField('SHIPPING_IND').allowBlank=false;
		
	qzComLists.form.findField('COM_SP_BUSINESS').allowBlank=false;	
	qzComLists.form.findField('IS_NOT_LOCAL_ENT').allowBlank=false;
};

//既有客户必输项
var opBlank=function(){
	if(qzCombaseInfo.form.findField('IN_CLL_TYPE').label!=undefined){
		qzCombaseInfo.form.findField('IN_CLL_TYPE').label.dom.innerHTML='<font color="red">*</font>行业类别:';
		qzComLists.form.findField('LNCUSTP').label.dom.innerHTML='<font color="red">*</font>企业类型:';
		qzComLists.form.findField('IS_FAX_TRANS_CUST').label.dom.innerHTML='<font color="red">*</font>是否传真交易指示标志:';
//		qzComLists.form.findField('ORG_SUB_TYPE').label.dom.innerHTML='<font color="red">*</font>特殊监管区:';
		qzComLists.form.findField('INOUT_FLAG').label.dom.innerHTML='<font color="red">*</font>境内境外标志:';
	}
	else{
		qzCombaseInfo.form.findField('IN_CLL_TYPE').fieldLabel='<font color="red">*</font>行业类别';
		qzComLists.form.findField('LNCUSTP').fieldLabel='<font color="red">*</font>企业类型';
		qzComLists.form.findField('IS_FAX_TRANS_CUST').fieldLabel='<font color="red">*</font>是否传真交易指示标志';
//		qzComLists.form.findField('ORG_SUB_TYPE').fieldLabel='<font color="red">*</font>特殊监管区';
		qzComLists.form.findField('INOUT_FLAG').fieldLabel='<font color="red">*</font>境内境外标志';
	}
	//第一页：行业类别
	qzCombaseInfo.form.findField('IN_CLL_TYPE').allowBlank=false;
	
	//第二页：企业类型
	qzComLists.form.findField('LNCUSTP').allowBlank=false;
	
	//是否传真交易指示标志
	qzComLists.form.findField('IS_FAX_TRANS_CUST').allowBlank=false;
	
	//特殊监管区
//	qzComLists.form.findField('ORG_SUB_TYPE').allowBlank=false;
	
	//境内境外标志
	qzComLists.form.findField('INOUT_FLAG').allowBlank=false;
	
	setMust(qzCombaseInfo,'BUILD_DATE','成立日期',true);//成立日期必输
//	setMust(qzCombaseInfo,'EN_NAME','英文名称',true);//英文名称必输
	//modified by liuyx 20171101 caron需求，因为保存时没有校验，因此取消
	setMust(qzComLists,'IS_TAIWAN_CORP','是否台资企业',false);//是否台资企业非必输
	//setMust(qzComLists,'IS_TAIWAN_CORP','是否台资企业',true);//是否台资企业必输
		
};
/**
 * 潜在客户非只读项
 */
var opNoRead=function(){
//	第一页：非只读
//	行业类别
//	qzCombaseInfo.form.findField('IN_CLL_TYPE').setDisabled(false);
//	qzCombaseInfo.form.findField('IN_CLL_TYPE').removeClass('x-readOnly');
	setReadOrNo(qzCombaseInfo,'IN_CLL_TYPE',false,'3');
//	证件类型、证件号码
	setReadOrNo(qzCombaseInfo,'IDENT_TYPE',false,'1');
	setReadOrNo(qzCombaseInfo,'IDENT_NO',false,'2');
//	客户名称
	setReadOrNo(qzCombaseInfo,'CUST_NAME',false,'2');
//	英文名称
	setReadOrNo(qzCombaseInfo,'EN_NAME',false,'2');
//	客户类型
	setReadOrNo(qzCombaseInfo,'ORG_CUST_TYPE',false,'1');
//	企业性质
	setReadOrNo(qzCombaseInfo,'ENT_PROPERTY',false,'1');
//	企业规模（银监）
//	setReadOrNo(qzCombaseInfo,'ENT_SCALE',false,'1');
//	法定代表人姓名
	setReadOrNo(qzCombaseInfo,'LEGAL_REPR_NAME',false,'2');
//	法定代表人证件号码
	setReadOrNo(qzCombaseInfo,'LEGAL_REPR_IDENT_NO',false,'2');
//	法定代表人证件类型
	setReadOrNo(qzCombaseInfo,'LEGAL_REPR_IDENT_TYPE',false,'1');
//	法定代表人证件失效日期
	setReadOrNo(qzCombaseInfo,'LEGAL_IDENT_EXPIRED_DATE',false,'4');
//	注册登记地址
	setReadOrNo(qzCombaseInfo,'REGISTER_ADDR',false,'1');
//	实际经营地址
	setReadOrNo(qzCombaseInfo,'ADDR0',false,'1');
//	经济类型
	setReadOrNo(qzCombaseInfo,'REGISTER_TYPE',false,'1');
//	登记注册号码
	setReadOrNo(qzCombaseInfo,'REGISTER_NO',false,'2');
//	注册资金币种
	setReadOrNo(qzCombaseInfo,'REGISTER_CAPITAL_CURR',false,'2');
//	注册资金(万元)
	setReadOrNo(qzCombaseInfo,'REGISTER_CAPITAL',false,'2');
//	证件到期日
	setReadOrNo(qzCombaseInfo,'IDENT_END_DATE',false,'4');
//	机构信用代码
	setReadOrNo(qzCombaseInfo,'CREDIT_CODE',false,'2');
//	统一社会信用代码
	setReadOrNo(qzCombaseInfo,'BUSI_LIC_NO',true,'2');
//	税务登记证编号
	setReadOrNo(qzCombaseInfo,'SW_REGIS_CODE',false,'2');
//	地税税务登记代码
	setReadOrNo(qzCombaseInfo,'AREA_REG_CODE',false,'2');
//	国税税务登记代码
	setReadOrNo(qzCombaseInfo,'NATION_REG_CODE',false,'2');
	
//	企业所在国别
	setReadOrNo(qzCombaseInfo,'NATION_CODE',false,'1');
//	地区代码
	setReadOrNo(qzCombaseInfo,'AREA_CODE',false,'1');
//	总部所在国别
	setReadOrNo(qzCombaseInfo,'HQ_NATION_CODE',false,'1');
//	关联人类型
	setReadOrNo(qzCombaseInfo,'STAFFIN',false,'1');
//	从业人数
	setReadOrNo(qzCombaseInfo,'EMPLOYEE_SCALE',false,'2');
//	成立日期	
	setReadOrNo(qzCombaseInfo,'BUILD_DATE',false,'4');
//	国别风险国别代码
	setReadOrNo(qzCombaseInfo,'RISK_NATION_CODE',false,'1');
//	年销售额币别
	setReadOrNo(qzCombaseInfo,'SALE_CCY',false,'1');
//	年销售额
	setReadOrNo(qzCombaseInfo,'SALE_AMT',false,'2');
//	第二页：
//	上市公司标志
	setReadOrNo(qzComLists,'IS_LISTED_CORP',false,'1');
//	上市地
	setReadOrNo(qzComLists,'MARKET_PLACE',false,'1');
//	股票代码
	setReadOrNo(qzComLists,'STOCK_CODE',false,'2');
//	第二页：企业类型
	setReadOrNo(qzComLists,'LNCUSTP',false,'1');
//	特殊监管区
	setReadOrNo(qzComLists,'ORG_SUB_TYPE',false,'1');
//	是否自贸区
	setReadOrNo(qzComLists,'IF_ORG_SUB_TYPE',false,'1');
//	合资类型
	setReadOrNo(qzComLists,'ORG_TYPE',false,'1');
//	是否传真交易指示标志
	setReadOrNo(qzComLists,'IS_FAX_TRANS_CUST',false,'1');
//	是否台资企业
	setReadOrNo(qzComLists,'IS_TAIWAN_CORP',false,'1');
//	境内境外标志
//	setReadOrNo(qzComLists,'INOUT_FLAG',true,'1');
//	是否2年内新设立企业
//	setReadOrNo(qzComLists,'IS_NEW_CORP',true,'1');
//	第三页
//	代理人国籍
	setReadOrNo(qzComOther1Info,'AGENT_NATION_CODE',false,'1');
//	代理人证件类型
	setReadOrNo(qzComOther1Info,'GRADE_IDENT_TYPE',false,'1');
//	代理人户名
	setReadOrNo(qzComOther1Info,'AGENT_NAME',false,'2');
//	代理人联系电话
	setReadOrNo(qzComOther1Info,'TEL',false,'2');
//	代理人证件号码
	setReadOrNo(qzComOther1Info,'GRADE_IDENT_NO',false,'2');

};
//既有客户只读项
var opRead=function(){
//	潜在客户非只读
	opNoRead();
	
//	第一页：只读
//	客户名称
	setReadOrNo(qzCombaseInfo,'CUST_NAME',true,'2');
//	英文名称
	setReadOrNo(qzCombaseInfo,'EN_NAME',true,'2');
//	证件类型、证件号码
	setReadOrNo(qzCombaseInfo,'IDENT_TYPE',true,'1');
	setReadOrNo(qzCombaseInfo,'IDENT_NO',true,'2');
//	机构信用代码
	setReadOrNo(qzCombaseInfo,'CREDIT_CODE',true,'2');
//	统一社会信用代码
	setReadOrNo(qzCombaseInfo,'BUSI_LIC_NO',true,'2');
//	税务登记证编号
	setReadOrNo(qzCombaseInfo,'SW_REGIS_CODE',true,'2');
//	地税税务登记代码
//	setReadOrNo(qzCombaseInfo,'AREA_REG_CODE',true,'2');
//	国税税务登记代码
//	setReadOrNo(qzCombaseInfo,'NATION_REG_CODE',true,'2');
//	证件到期日
	setReadOrNo(qzCombaseInfo,'IDENT_END_DATE',true,'4');	
//	企业所在国别
	setReadOrNo(qzCombaseInfo,'NATION_CODE',true,'1');
//	行业类别
	setReadOrNo(qzCombaseInfo,'IN_CLL_TYPE',true,'3');
//	国别风险国别代码
	setReadOrNo(qzCombaseInfo,'RISK_NATION_CODE',true,'1');
//	地区代码
	setReadOrNo(qzCombaseInfo,'AREA_CODE',true,'1');
//	关联人类型
	setReadOrNo(qzCombaseInfo,'STAFFIN',true,'1');
//	总部所在国别
	setReadOrNo(qzCombaseInfo,'HQ_NATION_CODE',true,'1');

//	从业人数
//	setReadOrNo(qzCombaseInfo,'EMPLOYEE_SCALE',true,'2');
//	成立日期
	setReadOrNo(qzCombaseInfo,'BUILD_DATE',true,'4');

//	企业规模（银监）
	setReadOrNo(qzCombaseInfo,'ENT_SCALE',true,'1');
//	法定代表人姓名
	setReadOrNo(qzCombaseInfo,'LEGAL_REPR_NAME',true,'2');
//	法定代表人证件号码
	setReadOrNo(qzCombaseInfo,'LEGAL_REPR_IDENT_NO',true,'2');
//	法定代表人证件类型
	setReadOrNo(qzCombaseInfo,'LEGAL_REPR_IDENT_TYPE',true,'1');
//	法定代表人证件失效日期
	setReadOrNo(qzCombaseInfo,'LEGAL_IDENT_EXPIRED_DATE',true,'4');
//	注册登记地址
//	setReadOrNo(qzCombaseInfo,'REGISTER_ADDR',true,'1');
//	实际经营地址
//	setReadOrNo(qzCombaseInfo,'ADDR0',true,'1');
//	注册资金币种
	setReadOrNo(qzCombaseInfo,'REGISTER_CAPITAL_CURR',true,'2');
//	注册资金(万元)
	setReadOrNo(qzCombaseInfo,'REGISTER_CAPITAL',true,'2');
//	年销售额币别
	setReadOrNo(qzCombaseInfo,'SALE_CCY',true,'1');
//	年销售额
	setReadOrNo(qzCombaseInfo,'SALE_AMT',true,'2');
	
//  第二页只读：
//	setReadOrNo(qzComLists,'IS_FAX_TRANS_CUST',true,'1');
//	是否传真交易指示标志
	setReadOrNo(qzComLists,'IS_FAX_TRANS_CUST',true,'1');
//	境内境外标志
	setReadOrNo(qzComLists,'INOUT_FLAG',true,'1');
//	是否台资企业
	setReadOrNo(qzComLists,'IS_TAIWAN_CORP',true,'1');
	
//	第一页：非只读
//	客户类型
	setReadOrNo(qzCombaseInfo,'ORG_CUST_TYPE',false,'1');
//	企业性质
	setReadOrNo(qzCombaseInfo,'ENT_PROPERTY',false,'1');
//	经济类型
	setReadOrNo(qzCombaseInfo,'REGISTER_TYPE',false,'1');
//	登记注册号码
	setReadOrNo(qzCombaseInfo,'REGISTER_NO',false,'2');
//	第二页：企业类型
	setReadOrNo(qzComLists,'LNCUSTP',true,'1');
//	特殊监管区
	setReadOrNo(qzComLists,'ORG_SUB_TYPE',true,'1');
//	是否自贸区
	setReadOrNo(qzComLists,'IF_ORG_SUB_TYPE',true,'1');
//	合资类型
	setReadOrNo(qzComLists,'ORG_TYPE',true,'1');
//	是否2年内新设立企业
//	setReadOrNo(qzComLists,'IS_NEW_CORP',true,'1');
//	第三页
//	代理人国籍
	setReadOrNo(qzComOther1Info,'AGENT_NATION_CODE',true,'1');
//	代理人证件类型
	setReadOrNo(qzComOther1Info,'GRADE_IDENT_TYPE',true,'1');
//	代理人户名
	setReadOrNo(qzComOther1Info,'AGENT_NAME',true,'2');
//	代理人联系电话
	setReadOrNo(qzComOther1Info,'TEL',true,'2');
//	代理人证件号码
	setReadOrNo(qzComOther1Info,'GRADE_IDENT_NO',true,'2');
};
var qzlsReadAndNoRead=function(){
//	潜在客户非只读
	opNoRead();
//	第一页：只读
//	证件类型、证件号码
	setReadOrNo(qzCombaseInfo,'IDENT_TYPE',true,'1');
	setReadOrNo(qzCombaseInfo,'IDENT_NO',true,'2');
};
//潜在客户-信贷临时户只读与非只读设置(新增)
var qzlsReadAndNoReadForAdd=function(){
//	潜在客户非只读
	opNoRead();
};

var zzsReadAndNoRead=function(){//信贷准正式户:只读与非只读设置
//	信贷准正式户只读设置
	setReadOrNo(qzCombaseInfo,'IDENT_TYPE',true,'1');//证件类型
	setReadOrNo(qzCombaseInfo,'IDENT_NO',true,'2');//证件号码
	setReadOrNo(qzCombaseInfo,'CUST_NAME',true,'2');//客户名称
	setReadOrNo(qzCombaseInfo,'ORG_CUST_TYPE',true,'1');//客户类型
	setReadOrNo(qzCombaseInfo,'ENT_PROPERTY',true,'1');//企业性质
	setReadOrNo(qzCombaseInfo,'LEGAL_REPR_NAME',true,'2');//法定代表人姓名
	setReadOrNo(qzCombaseInfo,'IN_CLL_TYPE',true,'3');//行业类别
	setReadOrNo(qzCombaseInfo,'REGISTER_TYPE',true,'1');//经济类型
	setReadOrNo(qzCombaseInfo,'REGISTER_NO',true,'2');//登记注册号码
	setReadOrNo(qzCombaseInfo,'REGISTER_CAPITAL_CURR',true,'2');//注册资金币种
	setReadOrNo(qzCombaseInfo,'REGISTER_CAPITAL',true,'2');	//注册资金(万元)
//	setReadOrNo(qzCombaseInfo,'ENT_SCALE',true,'1');//企业规模（银监）
//	第二页：
	setReadOrNo(qzComLists,'IS_LISTED_CORP',true,'1');//上市公司标志
	setReadOrNo(qzComLists,'MARKET_PLACE',true,'1');//上市地
	setReadOrNo(qzComLists,'STOCK_CODE',true,'1');//股票代码
};

var qzzzsReadAndNoRead=function(){//潜在客户-信贷准正式户只读与非只读设置
	opNoRead();//潜在客户:非只读设置
	zzsReadAndNoRead();//信贷准正式户:只读设置
};

var gyzzsReadAndNoRead=function(){//既有客户-信贷准正式户:只读与非只读设置
	opNoRead();//潜在客户:非只读设置
	opRead();//既有客户:只读设置
	zzsReadAndNoRead();//信贷准正式户:只读设置
};

var setReadOrNo = function(form1,fieldName,isRead,type){
	if(isRead){//只读
		if(type == '1'){//下拉选项
//			if(fieldName =='LNCUSTP' || fieldName =='IF_ORG_SUB_TYPE' || fieldName == 'ORG_SUB_TYPE' || fieldName =='ORG_TYPE'){
//			 if(!form1.form.findField(fieldName).readOnly){
//				form1.form.findField(fieldName).readOnly=true;
//				form1.form.findField(fieldName).cls='x-readOnly';
//				form1.form.findField(fieldName).setReadOnly(true);
//				form1.form.findField(fieldName).addClass('x-readOnly');
//			  }
//			 }else{
				if(!form1.form.findField(fieldName).disabled){
					form1.form.findField(fieldName).setDisabled(true);
					form1.form.findField(fieldName).addClass('x-readOnly');
				}
//			}
		}else if(type == '2'){//文本域
			if(!form1.form.findField(fieldName).readOnly){
				form1.form.findField(fieldName).setReadOnly(true);
				form1.form.findField(fieldName).addClass('x-readOnly');
			}
		}else if(type == '3' || type == '4'){//3放大镜,4日期
			if(!form1.form.findField(fieldName).disabled){
				form1.form.findField(fieldName).setDisabled(true);
				form1.form.findField(fieldName).addClass('x-readOnly');
			}
		}
	}else{//非只读
		if(type == '3' || type == '4'){//3放大镜,4日期
			if(form1.form.findField(fieldName).disabled){
				form1.form.findField(fieldName).setDisabled(false);
				form1.form.findField(fieldName).removeClass('x-readOnly');
			}
		}else if (type == '1'){
//			if(fieldName =='LNCUSTP' || fieldName =='IF_ORG_SUB_TYPE' || fieldName == 'ORG_SUB_TYPE' || fieldName =='ORG_TYPE'){
//				if(form1.form.findField(fieldName).readOnly){
//				form1.form.findField(fieldName).setReadOnly(false);
//				form1.form.findField(fieldName).removeClass('x-readOnly');
//			 }
//			}else{
				if(form1.form.findField(fieldName).disabled){
					form1.form.findField(fieldName).setDisabled(false);
					form1.form.findField(fieldName).removeClass('x-readOnly');
				}
//			}
		}else if (type == '2'){
			if(form1.form.findField(fieldName).readOnly){
				form1.form.findField(fieldName).setReadOnly(false);
				form1.form.findField(fieldName).removeClass('x-readOnly');
			}
		}
	}
};


//详情页面的所有字段只读：
var setDisabledFun=function(form1){
//	var json2=form1.form.getFieldValues(false);
//	for ( var key in json2) {
//		if(!form1.form.findField(key).disabled){
//			form1.form.findField(key).setDisabled(true);
//			form1.form.findField(key).addClass('x-readOnly');
//		}
//	}
};

//只读项全部清除：
var setNotDisabledFun=function(form1){
//	var json2=form1.form.getFieldValues(false);
//	var allFieldKeys = form1.form.items.keys;
//	for(var k in allFieldKeys){
//		var currFieldDom = document.getElementById(allFieldKeys[k]);
//		if(currFieldDom){
//			currFieldDom.classList.remove("x-readOnly");
//			currFieldDom.classList.remove("x-item-disabled");
//		}
//	}
//	for ( var key in json2) {
////		form1.form.findField(key).setDisabled(false);
//		form1.form.findField(key).removeClass('x-item-disabled');
////		form1.form.findField(key).setReadOnly(false);
//		form1.form.findField(key).removeClass('x-readOnly');
//		form1.form.findField(key).disabled=false;
////		form1.form.findField(key).readOnly=false;
//	}
};
