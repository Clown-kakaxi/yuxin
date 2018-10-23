/**
 * @description 对公客户基本信息
 * 
 * @author wangmk1
 * @since 2014-08-06
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.BusiType.js',
	'/contents/pages/wlj/customerManager/publicCustView/orgLookup.js', //所有数据字典定义
	'/contents/pages/wlj/customerManager/queryAllCustomer/updateHisCust.js'
]);
Ext.QuickTips.init();

var custId = _custId;
var custName = "";
var _sysCurrDate = new Date().format('Y-m-d');

var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动
var fields = [{name:'XX',text:'XX'}];

//金额验证
Ext.apply(Ext.form.VTypes, {
	money : function(val) {
		return /^([1-9]\d{0,7}|0)(\.\d{1,4})?$/.test(val);
	},
	moneyText : '请输入正确的金额'
});

var record = Ext.data.Record.create([
  //ACRM_F_CI_ORG  机构客户表
   {name:'CUST_ID'}//客户号
  ,{name:'FXQ_RISK_LEVEL'}//反洗钱风险等级  OCRM_F_CI_ANTI_CUST_LIST	反洗钱风险客户
  ,{name:'CUST_NAME'}//客户中文名称     取机构名称
  ,{name:'ENT_SCALE'}//企业规模     企业规模（银监）  XD000019
  ,{name:'BUILD_DATE'}//企业成立日期
  ,{name:'ENT_BELONG'}//企业隶属
  ,{name:'COM_HOLD_TYPE'}//客户控股类型
  ,{name:'ECONOMIC_TYPE'}//企业经济性质       经济类型
  ,{name:'MAIN_INDUSTRY'}//行业分类（主营）
  ,{name:'MAIN_INDUSTRY_NAME'}
  ,{name:'MINOR_INDUSTRY'}//行业分类（副营）
  ,{name:'MINOR_INDUSTRY_NAME'}//行业分类（副营）
  ,{name:'LEGAL_REPR_NAME'}//法定代表人/负责人
  ,{name:'LEGAL_REPR_IDENT_TYPE'}//法定代表人证件类型
  ,{name:'LEGAL_REPR_IDENT_NO'}//法定代表人证件号码
  ,{name:'LEGAL_REPR_NATION_CODE'}//所在国家(地区)    法定代表人所在国家（地区）
  ,{name:'ORG_ADDR'}//办公地址      通讯地址
  ,{name:'ORG_ZIPCODE'}//办公地址邮政编码     邮政编码
  ,{name:'ORG_CUS'}//联系人
  ,{name:'ORG_TEL'}//联系电话
  ,{name:'ORG_FEX'}//传真电话
  ,{name:'ORG_HOMEPAGE'}//公司网址
  ,{name:'ORG_EMAIL'}//公司EMAIL
  ,{name:'MAIN_BUSINESS'}//主营业务
  ,{name:'LOAN_CARD_NO'}//贷款卡号
  ,{name:'TOTAL_ASSETS'}//资产总额
  ,{name:'TOTAL_DEBT'}//负债总额
  ,{name:'ANNUAL_INCOME'}//销售收入
  ,{name:'EMPLOYEE_SCALE'}//职工人数      员工规模
  ,{name:'REMARK'}//备注
  ,{name:'LAST_UPDATE_SYS'}//最近更新系统
  ,{name:'LAST_UPDATE_TM'}//最近更新日期
  ,{name:'LAST_UPDATE_USER'}//最近更新人
  //ACRM_F_CI_CUSTOMER  客户表
  ,{name:'IDENT_TYPE'}//证件类型
  ,{name:'IDENT_NO'}//证件号码
  ,{name:'EN_NAME'}//客户英文名称
  ,{name:'RISK_LEVEL'}//风险等级
  //ACRM_F_CI_CUST_IDENTIFIER	客户证件信息表
  ,{name:'IDENT_VALID_PERIOD'}//证件有效期
  
  ,{name:'ORG_AUTH_ID'}
  ,{name:'AUTH_TYPE'}//企业资质-资质表
  
  ,{name:'BELONG_LINE_TYPE'}//客户业务类型-业务条线表
  ,{name:'GRADE_RESULT'}//客户评级   OCRM_F_CI_GRADE_RESULT	客户评级结果表	GRADE_RESULT	评级结果	VARCHAR(20)
  //ACRM_F_CI_ORG_KEYFLAG	机构客户重要标志表
  ,{name:'KEYFLAG_CUST_ID'}
  ,{name:'IS_LISTED_CORP'}//是否上市公司     
  ,{name:'IS_SMALL_CORP'}//是否小企业
  ,{name:'HAS_IE_RIGHT'}//是否有进出口权
  ,{name:'IS_GROUP_CUST'}//集团客户标志
  ,{name:'IS_EBANK_SIGN_CUST'}//是否网银签约客户
  ,{name:'IS_ASSOCIATED_PARTY'}//是否我行联方
  ,{name:'IS_RURAL'}//是否涉农
  ,{name:'IS_LIMIT_INDUSTRY'}//是否限制行业
  ,{name:'UDIV_FLAG'}//是否政府融资平台     地方政府融资平台标志
  ,{name:'IS_SOE'}//是否央企国企
  ,{name:'IS_TAIWAN_CORP'}//是否台资企业
  //ACRM_F_CI_ORG_REGISTERINFO	注册信息
  ,{name:'REGISTER_AREA'}//注册地省份、直辖市、自治区       行政区划
  ,{name:'REGISTER_ADDR'}//注册地址
  ,{name:'REGISTER_ZIPCODE'}//注册地址邮政编码
  ,{name:'REGISTER_CAPITAL_CURR'}//注册资本币别
  ,{name:'REGISTER_CAPITAL'}//注册资本
  ,{name:'IS_BUILD_NEW'}//是否2年内新设立企业
  ,{name:'FACT_CAPITAL_CURR'}//实收资本币种
  ,{name:'FACT_CAPITAL'}//实收资本
  ,{name:'BUSINESS_SCOPE'}//经营范围
  //ACRM_F_CI_ADDRESS	地址信息
  ,{name:'FIN_ID'}
  ,{name:'CUST_EN_ADDR'}//客户英文地址
  //ACRM_F_CI_ORG_BUSIINFO	机构经营信息
  ,{name:'WORK_FIELD_AREA'}//经营场地面积
  ,{name:'WORK_FIELD_OWNERSHIP'}//经营场地所有权
  ,{name:'MANAGE_STAT'}//经营状况
  ,{name:'BELONG_LINE'}//客户归属业务条线     归属条线类型
  ,{name:'BELONG_ORG'}//客户归属分行/区域中心
  //OCRM_F_CI_BELONG_CUSTMGR	归属客户经理信息
  ,{name:'EMP_NAME'}//客户归属Team Head  归属客户经理名称
// 	  ,{name:'CUST_MANAGER_NO'}//归属客户经理编号
  ,{name:'CUST_MANAGER_NAME'}//客户归属RM --归属客户经理表--CUST_MANAGER_ID 客户经理ID--OCRM_F_CM_CUST_MGR_INFO
  ,{name:'GROUP_NAME'}//客户归属集团    //OCRM_F_CI_GROUP_MEMBER	集团成员表    OCRM_F_CI_GROUP_INFO	集团信息表
  ,{name:'CUST_BASE_NAME'}//客户归属关联客户群        OCRM_F_CI_RELATE_CUST_BASE	客户群成员表     OCRM_F_CI_BASE	客户群表
]);

var basreader = new Ext.data.JsonReader({
	successProperty : 'success',
	idProperty : 'custId',
	messageProperty : 'message',
	root : 'data'
}, record);

var custBaseInfoPanel=new Ext.FormPanel({
	title : '客户信息',
	reader : basreader,
	collapsible : true,
	autoWidth:true,
	autoHeight:true,
	labelWidth:150,//label的宽度
	labelAlign:'right',
	frame:false,
	autoScroll : true,
	buttonAlign:'center',
	items : [{
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
				items:[
					 {xtype:'textfield',anchor:'90%',readOnly:true,fieldLabel:'客户编号',name:'CUST_ID',cls:'x-readOnly'},
					 {xtype:'textfield',anchor:'90%',readOnly:true,fieldLabel:'客户中文名称',name:'CUST_NAME',cls:'x-readOnly'},
					 {xtype:'combo',anchor:'90%',fieldLabel:'反洗钱风险等级',name:'FXQ_RISK_LEVEL',store:fxqRiskStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,cls:'x-readOnly',readOnly:true},
					 {xtype:'combo',anchor:'90%',readOnly:true,fieldLabel:'证件类型',name:'IDENT_TYPE',cls:'x-readOnly',
					 	store:cardTypeStore,valueField : 'key',displayField : 'value',mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
					 {xtype:'textfield',anchor:'90%',readOnly:true,fieldLabel:'证件号码',name:'IDENT_NO',cls:'x-readOnly'},
					 {xtype:'textfield',anchor:'90%',fieldLabel:'证件有效期',name:'IDENT_VALID_PERIOD',readOnly:true,cls:'x-readOnly'},
					 {xtype:'textfield',anchor:'90%',fieldLabel:'客户英文名称',name:'EN_NAME', readOnly:true,cls:'x-readOnly'}
		              ]
				},{
				columnWidth:.5,  
				layout:'form',
				items:[				       
				  	 {xtype:'combo',anchor:'90%',fieldLabel:'企业规模',name:'ENT_SCALE',
				  	 	  store:corStore,valueField : 'key',displayField : 'value',mode : 'local', readOnly:true,cls:'x-readOnly',typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
					 {xtype:'datefield',anchor:'90%',fieldLabel:'企业成立日期',name:'BUILD_DATE',format:'Y-m-d', readOnly:true,cls:'x-readOnly'},
					 {xtype:'combo',anchor:'90%',fieldLabel:'企业隶属',name:'ENT_BELONG',readOnly:true,cls:'x-readOnly',
						 store:belongStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
					 {xtype:'combo',anchor:'90%',fieldLabel:'客户控股类型',name:'COM_HOLD_TYPE',readOnly:true,cls:'x-readOnly',
		            	  store:holdingStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
					 {xtype:'combo',anchor:'90%',fieldLabel:'企业经济性质',name:'ECONOMIC_TYPE',readOnly:true,cls:'x-readOnly',
		            	  store:ecoStore,valueField : 'key',displayField : 'value',mode : 'local', readOnly:true,cls:'x-readOnly',typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
			         new Com.yucheng.bcrm.common.BusiType({
        				xtype : 'busiType',
        				fieldLabel : '行业分类（主营）',
        				name : 'MAIN_INDUSTRY_NAME',
        				hiddenName:'MAIN_INDUSTRY',
        				readonly : true,
        				disabled:true,
        				cls:'x-readOnly',
        				anchor : '90%'
        			}),     
        			new Com.yucheng.bcrm.common.BusiType({
        				xtype : 'busiType',
        				fieldLabel : '行业分类（副营）',
        				name : 'MINOR_INDUSTRY_NAME',
        				hiddenName:'MINOR_INDUSTRY',
        				readonly : true,
        				disabled:true,
        				cls:'x-readOnly',
        				anchor : '90%'
        			})     
				]
			}]
		}]
	},{
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
//				   {xtype:'combo',anchor:'90%',fieldLabel:'风险评级',name:'RISK_LEVEL',store:riskStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
//		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,cls:'x-readOnly',readOnly:true}, //由风险管理处最终核定后，不可更改
				   {xtype:'combo',anchor:'90%',fieldLabel:'企业资质',name:'AUTH_TYPE',store:certificateStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
				   {xtype:'combo',anchor:'90%',fieldLabel:'客户业务类型',name:'BELONG_LINE_TYPE',store:belongLineTypeStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,cls:'x-readOnly',readOnly:true},
				   {xtype:'combo',anchor:'90%',fieldLabel:'客户评级',name:'GRADE_RESULT',store:custGradeStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,readOnly:true,cls:'x-readOnly'},   //由部室领导确定评级标准
				   {xtype:'combo',anchor:'90%',fieldLabel:'是否上市公司',name:'IS_LISTED_CORP',store:isOnMarStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
		           {xtype:'combo',anchor:'90%',fieldLabel:'组织机构细分',name:'IS_SOE',store:isSoEStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
				   {xtype:'combo',anchor:'90%',fieldLabel:'是否政府融资平台',name:'UDIV_FLAG',store:isLocGStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
	              {xtype:'combo',anchor:'90%',fieldLabel:'集团客户标志',name:'IS_GROUP_CUST',store:isCorStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
	                  typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,cls:'x-readOnly',readOnly:true},
	              {xtype:'textfield',anchor:'90%',fieldLabel:'开户许可证号',name:'CUST_EN_ADDR',maxLength:200}
	              ]
			},{
				columnWidth:.5,  
				layout:'form',
				items:[
			       {xtype:'combo',anchor:'90%',fieldLabel:'经济类型',name:'IS_SMALL_CORP',store:isMcorStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
		           {xtype:'combo',anchor:'90%',fieldLabel:'是否台资企业',name:'IS_TAIWAN_CORP',store:isTaiStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
			       {xtype:'combo',anchor:'90%',fieldLabel:'是否有进出口权',name:'HAS_IE_RIGHT',store:hasIOPStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
		           {xtype:'combo',anchor:'90%',fieldLabel:'是否我行关联方',name:'IS_ASSOCIATED_PARTY',store:isRelaStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
		           {xtype:'combo',anchor:'90%',fieldLabel:'组织机构类型',name:'IS_RURAL',store:isRurStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true},
			       {xtype:'combo',anchor:'90%',fieldLabel:'是否网银签约客户',name:'IS_EBANK_SIGN_CUST',store:isNetStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,cls:'x-readOnly',readOnly:true},
			     
			       {xtype:'combo',anchor:'90%',fieldLabel:'是否限制行业',name:'IS_LIMIT_INDUSTRY',store:isLimtStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true}
				]
			}]
		}]
	},{
		xtype : 'fieldset',
		title : '联系信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items:[{
			layout:'column',
			items:[{
				columnWidth:.5,  
				layout:'form',
				items:[
					   {xtype:'textfield',anchor:'90%',fieldLabel:'法定代表人/负责人',name:'LEGAL_REPR_NAME',readOnly:true,cls:'x-readOnly'},
					   {xtype:'combo',anchor:'90%',fieldLabel:'法定代表人证件类型',name:'LEGAL_REPR_IDENT_TYPE',store:cardTypeStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,readOnly:true,cls:'x-readOnly'},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'法定代表人证件号码',name:'LEGAL_REPR_IDENT_NO',readOnly:true,cls:'x-readOnly'},
					   {xtype:'combo',anchor:'90%',fieldLabel:'法人所在国家(地区)',name:'LEGAL_REPR_NATION_CODE',store:countryStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,readOnly:true,cls:'x-readOnly'
//		              		listeners:{
//								select : function(combo,record){
//									var nation = record.data.value;
//									var result = nation+"/"+custBaseInfoPanel.getForm().findField('REGISTER_ADDRX').getValue();
//									custBaseInfoPanel.getForm().findField('REGISTER_ADDR').setValue(result);
//								}
//							}
			              },
					   {xtype:'combo',anchor:'90%',fieldLabel:'注册地省份、直辖市、自治区',name:'REGISTER_AREA',store:admdivStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
			              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,readOnly:true,cls:'x-readOnly'},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'注册地址',name:'REGISTER_ADDR',readOnly:true,cls:'x-readOnly'
//					   		listeners:{
//								blur : function(){
//									var nation = custBaseInfoPanel.getForm().findField('LEGAL_REPR_NATION_CODE').getRawValue();
//									var result = nation+"/"+custBaseInfoPanel.getForm().findField('REGISTER_ADDRX').getValue();
//									custBaseInfoPanel.getForm().findField('REGISTER_ADDR').setValue(result);
//								}
//							}
					   },//输入方向
					   //modify by liuming 20170612
					   //{xtype:'textfield',anchor:'90%',fieldLabel:'注册地址',name:'REGISTER_ADDR',hidden:true,readOnly:true,cls:'x-readOnly'},//储存方向
					   {xtype:'textfield',anchor:'90%',fieldLabel:'注册地址邮政编码',name:'REGISTER_ZIPCODE',readOnly:true,cls:'x-readOnly'},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'公司网址',name:'ORG_HOMEPAGE',readOnly:true,cls:'x-readOnly'},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'公司EMAIL',name:'ORG_EMAIL',readOnly:true,cls:'x-readOnly'}
		              ]
			},{
				columnWidth:.5,  
				layout:'form',
				items:[
					   {xtype:'textfield',anchor:'90%',fieldLabel:'办公地址国家地区',name:'ORG_ADDRN',readOnly:true,cls:'x-readOnly'
//					   		listeners:{
//								blur : function(){
//									var reg = /^\D{1,20}$/;
//									var nation = custBaseInfoPanel.getForm().findField('ORG_ADDRN').getValue();
//									if(!reg.test(nation)){
//										Ext.Msg.alert('提示','请输入正确格式的国家或地区！');
//										custBaseInfoPanel.getForm().findField('ORG_ADDRN').setValue("")
//										return false;
//									}
//									var result = nation+"/"+custBaseInfoPanel.getForm().findField('ORG_ADDRX').getValue();
//									custBaseInfoPanel.getForm().findField('ORG_ADDR').setValue(result);
//								}
//							}
					   },//国家方向
					   {xtype:'textfield',anchor:'90%',fieldLabel:'办公地址',name:'ORG_ADDRX',readOnly:true,cls:'x-readOnly'
//					   		listeners:{
//								blur : function(){
//									var nation = custBaseInfoPanel.getForm().findField('ORG_ADDRN').getValue();
//									var result = nation+"/"+custBaseInfoPanel.getForm().findField('ORG_ADDRX').getValue();
//									custBaseInfoPanel.getForm().findField('ORG_ADDR').setValue(result);
//								}
//							}
					   },//输入方向
					   {xtype:'textfield',anchor:'90%',fieldLabel:'办公地址',name:'ORG_ADDR',hidden:true},//储存方向
					   {xtype:'textfield',anchor:'90%',fieldLabel:'办公地址邮政编码',name:'ORG_ZIPCODE',vtype:'postcode',readOnly:true,cls:'x-readOnly'},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'联系人',name:'ORG_CUS',readOnly:true,cls:'x-readOnly'},
					   {xtype:'textfield',anchor:'90%',fieldLabel:'联系电话国家地区区号',name:'ORG_TELN',readOnly:true,cls:'x-readOnly'
//					   		listeners:{
//								blur : function(){
//									var reg = /^[0\+]\d{2,3}$/;
//									var nation = custBaseInfoPanel.getForm().findField('ORG_TELN').getValue();
//									if(!reg.test(nation)){
//										Ext.Msg.alert('提示','请输入正确格式的区号！');
//										custBaseInfoPanel.getForm().findField('ORG_TELN').setValue("")
//										return false;
//									}
//									var result = nation+"/"+custBaseInfoPanel.getForm().findField('ORG_TELX').getValue();
//									custBaseInfoPanel.getForm().findField('ORG_TEL').setValue(result);
//								}
//							}
					   },//国家方向
					   {xtype:'textfield',anchor:'90%',fieldLabel:'联系电话',name:'ORG_TEL',readOnly:true,cls:'x-readOnly'
//					   		listeners:{
//								blur : function(){
//									var nation = custBaseInfoPanel.getForm().findField('ORG_TELN').getValue();
//									var result = nation+"/"+custBaseInfoPanel.getForm().findField('ORG_TELX').getValue();
//									custBaseInfoPanel.getForm().findField('ORG_TEL').setValue(result);
//								}
//							}
					   },//输入方向
					   {xtype:'textfield',anchor:'90%',fieldLabel:'联系电话',name:'ORG_TEL',hidden:true},//储存方向
					   {xtype:'textfield',anchor:'90%',fieldLabel:'传真电话',name:'ORG_FEX',readOnly:true,cls:'x-readOnly'}
					  
				]
			}]
		}]
	},{
		xtype : 'fieldset',
		title : '经营信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [{
			layout : 'column',
            items : [{
            	layout : 'form',
				columnWidth : .50,
				items : [
				   {xtype:'combo',anchor:'90%',fieldLabel:'注册资本币别',name:'REGISTER_CAPITAL_CURR',store:regCStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,cls:'x-readOnly',readOnly:true},
				   {xtype:'textfield',anchor:'90%',fieldLabel:'注册资本(万元)',name:'REGISTER_CAPITAL',viewFn: money('0,000.00'),cls:'x-readOnly',readOnly:true},
				   {xtype:'combo',anchor:'90%',fieldLabel:'实收资本币种',name:'FACT_CAPITAL_CURR',store:regCStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,cls:'x-readOnly',readOnly:true},
				   {xtype:'textfield',anchor:'90%',fieldLabel:'实收资本(元)',name:'FACT_CAPITAL',viewFn: money('0,000.00'),vtype:'number',cls:'x-readOnly',readOnly:true},//（显示金额单位为元）
				   {xtype:'textfield',anchor:'90%',fieldLabel:'职工人数',name:'EMPLOYEE_SCALE',vtype:'number',readOnly:true,cls:'x-readOnly'},
				   {xtype : 'combo',anchor:'90%',fieldLabel : '是否2年内新设立企业',name : 'IS_BUILD_NEW',store : ifStore, valueField : 'key',displayField : 'value',mode : 'local',editable : true,
					   forceSelection : true,triggerAction : 'all',emptyText : '未知',disabled:true,cls:'x-readOnly',readOnly:true},
				   {xtype:'textfield',anchor:'90%',fieldLabel:'资产总额(元)',name:'TOTAL_ASSETS',viewFn: money('0,000.00'),vtype:'number',cls:'x-readOnly',readOnly:true},//（显示金额单位为万元）
				   {xtype:'textfield',anchor:'90%',fieldLabel:'负债总额(千元)',name:'TOTAL_DEBT',viewFn: money('0,000.00'),vtype:'number',cls:'x-readOnly',readOnly:true}//（显示金额单位为万元）
				]
            },{
            	layout : 'form',
            	columnWidth : .50,
            	items : [
				   {xtype:'textfield',anchor:'90%',fieldLabel:'销售收入(元)',name:'ANNUAL_INCOME',viewFn: money('0,000.00'),vtype:'number',readOnly:true,cls:'x-readOnly'},//（显示金额单位为万元）
				   {xtype:'textfield',anchor:'90%',fieldLabel:'经营范围',name:'BUSINESS_SCOPE',cls:'x-readOnly',readOnly:true},
				   {xtype:'textfield',anchor:'90%',fieldLabel:'主营业务',name:'MAIN_BUSINESS',readOnly:true,cls:'x-readOnly'},
				   {xtype:'textfield',anchor:'90%',fieldLabel:'经营场地面积',name:'WORK_FIELD_AREA',viewFn: money('0,000.00'),vtype:'number',readOnly:true,cls:'x-readOnly'},
				   {xtype:'combo',anchor:'90%',fieldLabel:'经营场地所有权',name:'WORK_FIELD_OWNERSHIP',store:manaPStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,cls:'x-readOnly',readOnly:true},
				   {xtype:'combo',anchor:'90%',fieldLabel:'经营状况',name:'MANAGE_STAT',store:manaStore,valueField : 'key',displayField : 'value',mode : 'local',editable : true,
		              typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '未知',selectOnFocus : true,cls:'x-readOnly',readOnly:true},
		           {xtype:'textfield',anchor:'90%',fieldLabel:'贷款卡号',name:'LOAN_CARD_NO',vtype:'number',cls:'x-readOnly',readOnly:true}
				]
            }]
		}]
	},{
		xtype : 'fieldset',
		title : '其他信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight : true,
		items : [{
			layout : 'column',
            items : [{
            	layout : 'form',
				columnWidth : .50,
				items : [
					 {xtype:'textfield',fieldLabel:'客户归属业务条线',name:'BELONG_LINE',anchor:'90%',readOnly:true,cls:'x-readOnly'},
        	         {xtype:'textfield',fieldLabel:'客户归属分行/区域中心',name:'BELONG_ORG',anchor:'90%',readOnly:true,cls:'x-readOnly'},
        	         {xtype:'textfield',fieldLabel:'客户归属Team Head',name:'EMP_NAME',anchor:'90%',readOnly:true,cls:'x-readOnly'},
        	         {xtype:'textfield',fieldLabel:'客户归属RM',name:'CUST_MANAGER_NAME',anchor:'90%',readOnly:true,cls:'x-readOnly'},
        	         {xtype:'textfield',fieldLabel:'客户归属集团',name:'GROUP_NAME',anchor:'90%',readOnly:true,cls:'x-readOnly'},
        	         {xtype:'textfield',fieldLabel:'客户归属关联客户群',name:'CUST_BASE_NAME',anchor:'90%',readOnly:true,cls:'x-readOnly'}
				]
            },{
            	layout : 'form',
            	columnWidth : .50,
            	items : [
            		{xtype:'textfield',name:'FIN_ID',hidden:true},//财务营销ID
            		{xtype:'textfield',name:'ORG_AUTH_ID',hidden:true},//资质认证ID
            		{xtype:'textfield',name:'KEYFLAG_CUST_ID',hidden:true},//对公客户标志
        	        {xtype:'textfield',fieldLabel:'最近更新系统',name:'LAST_UPDATE_SYS',anchor:'90%',readOnly:true,cls:'x-readOnly'},
				    {xtype:'textfield',fieldLabel:'最近更新日期',name:'LAST_UPDATE_TM',anchor:'90%',readOnly:true,cls:'x-readOnly'},
				    {xtype:'textfield',fieldLabel:'最近更新人',name:'LAST_UPDATE_USER',anchor:'90%',readOnly:true,cls:'x-readOnly'},
				    {xtype:'textarea',fieldLabel:'备注',name:'REMARK',height:90,anchor:'90%',maxLength:200}
				]
            }]
		}]
	}]
});

/**
 * 股东信息
 */
/////////////////////////////////////////////////////////////////////////////////////////////////
var shareholderrownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
});
var shareholdercm = new Ext.grid.ColumnModel([
    shareholderrownum,
    {header:'客户号', dataIndex:'CUST_ID', sortable:true, align:'left', width : 180},
    {header:'股东名称', dataIndex:'HOLDER_NAME', sortable:true, align:'left', width : 180},
    {header:'股东证件类型', dataIndex:'IDENT_TYPE', sortable:true, align:'left', width : 180},
    {header:'股东证件号码', dataIndex:'IDENT_NO', sortable:true, align:'left', width : 180}
]);
var shareholderStore = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/publicCustInfo!searchShareholder.json?custId='+_custId,
		method: 'GET'
	}),
	reader : new Ext.data.JsonReader( {
		successProperty : 'success',
		root : 'data'
	}, [//ACRM_F_CI_ORG_HOLDERINFO	股东信息
	    {name : 'CUST_ID'},
		{name : 'HOLDER_NAME'},//股东名称
		{name : 'IDENT_TYPE',mapping:'IDENT_TYPE_ORA'},//股东证件类型
		{name : 'IDENT_NO'}//股东证件号码
	])
});
var shareholderGrid = new Ext.grid.GridPanel({
	title : '股东信息',
	collapsible : true,
	autoWidth:true,
	autoHeight:true,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	store : shareholderStore, // 数据存储
	stripeRows : true, // 斑马线
	cm : shareholdercm, // 列模型
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//加载股东信息数据
shareholderStore.load();

var customerView =[{
	title:'基本信息',
	hideTitle:true,
	type: 'form',
	items:[custBaseInfoPanel,shareholderGrid],
	formButtons:[{
		text:'修改历史',
		hidden:JsContext.checkGrant("_comQueryHis"),
		fn:function(){
			custId = _custId;
			custName = custBaseInfoPanel.getForm().findField("CUST_NAME").getValue();
			updateHisWin.show();
		}
	},{
		text:'提交',
		hidden:JsContext.checkGrant("publicBaseInfo_commit"),
		fn : function(){
			if(!custBaseInfoPanel.getForm().isValid()){
				Ext.Msg.alert('提示','输入有误或存在漏输入项,请检查输入项');
				return false;
			}
			var keyflagArr = ['KEYFLAG_CUST_ID','IS_LISTED_CORP','IS_SOE','UDIV_FLAG','IS_SMALL_CORP','IS_TAIWAN_CORP','HAS_IE_RIGHT','IS_ASSOCIATED_PARTY','IS_RURAL','IS_LIMIT_INDUSTRY'];
			var keyflag = false;
			
			var json1 = custBaseInfoPanel.reader.jsonData.data[0];//读取时，原数据
			var json2 = custBaseInfoPanel.form.getValues(false);//(返回键值对)提交时的数据
			var perModel = [];//perModel是一个数组/集合
			for(var key in json2){
				var pcbhModel = {};
				var field = custBaseInfoPanel.getForm().findField(key);
				if(field.getXType() == 'combo'){
					var s=field.getValue();
					if(json1[key] != s){
						if(json1[key]!=null && s!=""){
							pcbhModel.custId = _custId;
							pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json1[key],'value');
							pcbhModel.updateAfCont = s;
							pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
							pcbhModel.updateItem = field.fieldLabel;
							pcbhModel.updateItemEn = field.name;
							pcbhModel.fieldType = '1';
							perModel.push(pcbhModel);
							if(keyflagArr.indexOf(key) > - 1){
								keyflag = true;
							}
							if(key == 'AUTH_TYPE'){
								addFieldFn(perModel,_custId,'ID_SEQUENCE.NEXTVAL'
								,custBaseInfoPanel.getForm().findField('ORG_AUTH_ID').getValue(),'ORG_AUTH_ID','1','1');
								addFieldFn(perModel,_custId,_custId,_custId,'AUTH_CUST_ID');
								addFieldFn(perModel,_custId,'','CRM','AUTH_LAST_UPDATE_SYS','');
								addFieldFn(perModel,_custId,'',JsContext._userId,'AUTH_LAST_UPDATE_USER','');
								addFieldFn(perModel,_custId,'',_sysCurrDate,'AUTH_LAST_UPDATE_TM','','2');
							}
						}
					}
				}else{
					if(!((json1[key]==json2[key]) || (null==json1[key]&&null==json2[key]))){
						var pcbhModel = {};
						pcbhModel.custId = _custId;
						pcbhModel.updateBeCont = json1[key];
						pcbhModel.updateAfCont = json2[key];
						pcbhModel.updateAfContView = json2[key];
						pcbhModel.updateItem = field.fieldLabel;
						pcbhModel.updateItemEn = field.name;
						pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
						perModel.push(pcbhModel);
						if(keyflagArr.indexOf(key) > - 1){
							keyflag = true;
						}
						if("CUST_EN_ADDR"==key){
							addFieldFn(perModel,_custId,'ID_SEQUENCE.NEXTVAL'
								,custBaseInfoPanel.getForm().findField('FIN_ID').getValue(),'FIN_ID','1','1');
							addFieldFn(perModel,_custId,_custId,_custId,'FIN_CUST_ID');
						}
					}
				}
			}
			if(keyflag){
				addFieldFn(perModel,_custId,_custId
					,custBaseInfoPanel.getForm().findField('KEYFLAG_CUST_ID').getValue(),'KEYFLAG_CUST_ID','1','1');
			}
			if(perModel.length < 1){
				Ext.Msg.alert('提示', '未作任何修改,不允许提交!');
				return false;
			}
			
			var custId_v=custBaseInfoPanel.getForm().findField("CUST_ID").getValue();
            var custName_v=custBaseInfoPanel.getForm().findField("CUST_NAME").getValue();
			Ext.Ajax.request({
				url:basepath+'/publicBaseInfo!initFlowBF.json',//触发提交流程
				method:'POST',
				params : {
	                'perModel':Ext.encode(perModel),
	                'custId':custId_v,
	                'custName':custName_v
				},
				waitMsg : '正在提交申请,请等待...',
				success : function(response) {
					var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
				},
				failure: function() {
					Ext.Msg.alert('提示', '操作失败');
        		}
			});
		}
	}]
}];

//customerBaseInfoPanel加载数据
custBaseInfoPanel.getForm().load({
	url: basepath + '/publicCustInfo!searchBaseInfo.json',
	method:'get',
	params : {
		'custId':_custId
    },
    success: function(form,action){
    	var flag = form.findField('IS_BUILD_NEW').getValue();
    	if(flag = '1'){
    		form.findField('ANNUAL_INCOME').setVisible(true);
    		form.findField('TOTAL_ASSETS').setVisible(true);
    	}else{
    		form.findField('ANNUAL_INCOME').setVisible(false);
    		form.findField('TOTAL_ASSETS').setVisible(false);
    	}
//    	//注册地址与国家地区名分发
//    	var regADD = form.findField('REGISTER_ADDR').getValue();
//    	if(regADD.indexOf("/",0)>0){
//    		var regARR = regADD.split("/");
//	    	form.findField('REGISTER_ADDRX').setValue(regARR[1]);
//    	}else{
//    		form.findField('REGISTER_ADDRX').setValue(regADD);
//    	}
//    	//办公地址与国家地区名分发
//    	var ofcADD = form.findField('ORG_ADDR').getValue();
//    	if(ofcADD.indexOf("/",0)>0){
//    		var ofcARR = ofcADD.split("/");
//	    	form.findField('ORG_ADDRN').setValue(ofcARR[0]);
//	    	form.findField('ORG_ADDRX').setValue(ofcARR[1]);
//    	}else{
//    		form.findField('ORG_ADDRX').setValue(ofcADD);
//    	}
//    	//联系电话与国家地区区号分发
//    	var conTEL = form.findField('ORG_TEL').getValue();
//    	if(conTEL.indexOf("/",0)>0){
//    		var conARR = conTEL.split("/");
//	    	form.findField('ORG_TELN').setValue(conARR[0]);
//	    	form.findField('ORG_TELX').setValue(conARR[1]);
//    	}else{
//    		form.findField('ORG_TELX').setValue(conTEL);
//    	}
    }
});

/**
 * 结果域面板滑入前触发：
 * params：theview : 当前滑入面板；
 * return： false，阻止面板滑入操作；默认为true；
 */
var beforeviewshow = function(view){
	if(view._defaultTitle == '基本信息'){
//		var _pob_ao = JsContext.checkGrant('publicOverviewBaseInfo_AO');
//		var _pob_op = JsContext.checkGrant('publicOverviewBaseInfo_OP');
//		if(_pob_ao && !_pob_op){
//			setViewOP(true)
//		}else if(!_pob_ao && _pob_op)(
//			setViewAO(true)
//		)
	}
}

/**
 * 设置OP可编辑权限
 * @param {} stim
 */
//var setViewOP = function(stim){
//	setFormDisabled(custBaseInfoPanel.getForm(),stim,[
//		//基本信息
//		'CUST_ID','EN_NAME','CUST_NAME','BUILD_DATE','ENT_SCALE',
//		'ECONOMIC_TYPE','MAIN_INDUSTRY','MINOR_INDUSTRY',
//		//联系信息
//		'ORG_ADDR','ORG_ZIPCODE',
//		'ORG_FEX','LEGAL_REPR_NAME','LEGAL_REPR_IDENT_TYPE','LEGAL_REPR_IDENT_NO','ORG_HOMEPAGE',
//		'EN_ADDR','LEGAL_REPR_NATION_CODE','REGISTER_AREA',
//		//分类信息
//		'RISK_LEVEL','IS_GROUP_CUST','IS_LISTED_CORP','IS_RURAL','IS_TAIWAN_CORP','IS_EBANK_SIGN_CUST',
//		'IS_ASSOCIATED_PARTY','IS_LIMIT_INDUSTRY','IS_SMALL_CORP','IS_SOE','HAS_IE_RIGHT','UDIV_FLAG'
//	]);
//	setFormDisabled(custBaseInfoPanel.getForm(),!stim,[
//		//经营信息
//		'TOTAL_DEBT','WORK_FIELD_AREA',
//		'WORK_FIELD_OWNERSHIP','BUSINESS_SCOPE','MANAGE_STAT','FACT_CAPITAL','FACT_CAPITAL_CURR',
//		'ANNUAL_INCOME','EMPLOYEE_SCALE','MAIN_BUSINESS','TOTAL_ASSETS',
//		//其它信息
//		'REMARK','LAST_UPDATE_SYS','LAST_UPDATE_TM','LAST_UPDATE_USER'
//	]);
//};
//
///**
// * 设置AO可编辑权限
// * @param {} stim
// */
//var setViewAO = function(stim){
//	setFormDisabled(custBaseInfoPanel.getForm(),stim,[
//		//基本信息
//		'CUST_ID','EN_NAME','CUST_NAME','BUILD_DATE','ENT_SCALE',
//		'ECONOMIC_TYPE','MAIN_INDUSTRY','MINOR_INDUSTRY',
//		'IDENT_TYPE','IDENT_NO','IDENT_VALID_PERIOD',
//		//经营信息
//		'TOTAL_DEBT','WORK_FIELD_AREA',
//		'WORK_FIELD_OWNERSHIP','BUSINESS_SCOPE','MANAGE_STAT','FACT_CAPITAL','FACT_CAPITAL_CURR',
//		'ANNUAL_INCOME','EMPLOYEE_SCALE','MAIN_BUSINESS','REGISTER_CAPITAL','REGISTER_CAPITAL_CURR','TOTAL_ASSETS',
//		//联系信息
//		'ORG_EMAIL','ORG_TEL','ORG_TELX','ORG_CUS','REGISTER_ADDR','REGISTER_ZIPCODE',
//		//分类信息
//		'RISK_LEVEL','IS_GROUP_CUST','IS_LISTED_CORP','IS_RURAL','IS_TAIWAN_CORP','IS_EBANK_SIGN_CUST',
//		'IS_ASSOCIATED_PARTY','IS_LIMIT_INDUSTRY','IS_SMALL_CORP','IS_SOE','HAS_IE_RIGHT','UDIV_FLAG'
//	]);
//};

/**
 * 
 * helin
 * 添加隐藏主键字段
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} formpanel form面板
 * @param {} key 字段
 * @param {} fieldLabel 字段label
 */
var addKeyFn = function(perModel,_tempCustId,formpanel,key,fieldLabel){
	var field = formpanel.getForm().findField(key);
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = field.getValue();
	pcbhModel.updateAfCont = field.getValue();
	pcbhModel.updateAfContView = field.getValue();
	pcbhModel.updateItem = fieldLabel;
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = '1';
	pcbhModel.updateTableId = '1';
	perModel.push(pcbhModel);
};
/**
 * helin
 * 添加隐藏字段
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} beforeValue 修改前值
 * @param {} afterValue 修改后值
 * @param {} key 字段
 * @param {} fieldLabel 字段label
 * @param {} updateTableId 是否主键字段:1是，''否
 * @param {} fieldType 字段类型:1文本框，'2'日期框
 */
var addFieldFn = function(perModel,_tempCustId,beforeValue,afterValue,key,updateTableId,fieldType){
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = beforeValue;
	pcbhModel.updateAfCont = afterValue;
	pcbhModel.updateAfContView = afterValue;
	pcbhModel.updateItem = '';
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = fieldType == "2"?"2":"1";
	pcbhModel.updateTableId = updateTableId == "1"?"1":"";
	perModel.push(pcbhModel);
};
/**
 * helin
 * @param store 要遍历的store
 * @param keyField 要比较的字段
 * @param keyValue 要比较的字段的值
 * @param valueField 要获取值的字段
 */
var getStoreFieldValue = function(store,keyField,keyValue,valueField){
	for(var i=0;i<store.getCount();i++){
		if(store.getAt(i).data[keyField] == keyValue){
			return store.getAt(i).data[valueField];
		}
	}
	return keyValue;
};

////////////////////////////////////////////字段级数据权限控制区域function
/**
 * @description 
 * 
 * 设置禁用启用起否示例
 * setFormDisabled(xxxPanel.getForm(),true,[
 *	'EN_NAME','CUST_ID','INOUT_FLAG','VIP_FLAG','PER_CUST_TYPE'
 * ]);
 * 设置可编辑字段
 * @param form formPanel.getForm()  
 * @param arr 数组
 * @param disable true禁用,false表示不禁用
 */
var setFormDisabled = function(form,disable,arr){
	for(var i=0;i<arr.length;i++){
		var tempfield = form.findField(arr[i]);
		tempfield.setDisabled(disable);
		if(disable){
			tempfield.addClass('x-readOnly');
		}else{
			tempfield.removeClass('x-readOnly');
		}
	}
};