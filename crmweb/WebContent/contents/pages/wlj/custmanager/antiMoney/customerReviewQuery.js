/**
 * 
* @Description: 客户反洗钱复评
* 
* @author wangmk 
* @date 2014-7-8 
*
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js', // 机构放大镜
	'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',	//用户放大镜
	'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'
]);

var url=basepath+"/customerReviewQuery.json";

var _custId;
var comitUrl=false;
var createView = false;
var editView = false;
var detailView = false;

// 机构树加载条件
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
} ,{
	key : 'BLINETREELOADER',
	url : basepath + '/businessLineTree.json',
	parentAttr : 'PARENT_ID',
	locateAttr : 'BL_ID',
	jsonRoot : 'json.data',
	rootValue : '0',
	textField : 'BL_NAME',
	idProperties : 'BL_ID'
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
},{
	key : 'BLTREE',
	loaderKey : 'BLINETREELOADER',
	autoScroll : true,
	rootCfg : {
		expanded : true,
		id : '0',
		text : '归属业务条线',
		autoScroll : true,
		children : []
	}
} ];

var lookupTypes=[
     'XD000080',//客户类别
     'XD000040', //证件类别
     'FXQ_RISK_LEVEL',//反洗钱风险等级
     'XD000081',//客户状态
     'IF_FLAG',
     'FXQ007',
     'FXQ21006',
     'FXQ023',
     'FXQ025',
     'XD000025',
     'XD000287',
     'XD000018'
  ];

var localLookup = {
	'LAST_PER':[
	  			{key : 'ETL',value : 'ETL'},
			  	{key : __userId,value : __userName}
			]
	,'FXQ_FLAG':[
	     {key:'0',value:'未复评'},
	     {key:'1',value:'复评中'},
	     {key:'2',value:'已复评'}
	     ]
};

var fields=[
	{name:'DATE_DIFF',text:'预警状态',resutlWidth:80},
	//查询条件
	{name:'CUST_TYPE',text:'客户类型',resutlWidth:80,translateType:'XD000080',searchField:true},
	{name:'CUST_STAT',text:'客户状态',resutlWidth:80,translateType:'XD000081',gridField:false},
	{name:'CUST_ID',text:'客户编号',resutlWidth:120,searchField:true,dataType:'string'},
	{name:'CORE_NO',text:'核心客户号',resutlWidth:120,searchField:true,dataType:'string'},
	{name:'CUST_NAME',text:'客户名称',resutlWidth:150,searchField:true,dataType:'string'},
	{name:'IDENT_TYPE',text:'证件类型',resutlWidth:120,translateType:'XD000040',editable: true,gridField:false},
	{name:'IDENT_NO',text:'证件号码',resutlWidth:120,dataType:'string',gridField:false},
	{name:'ORG_NAME',text:'归属机构',xtype:'wcombotree',innerTree:'ORGTREE',resutlWidth:80,showField:'text',hideField:'UNITID',editable:false,allowBlank:false,gridField:false},	   
	{name:'MGR_NAME',text:'所属客户经理',xtype:'userchoose',hiddenName:'MGR_ID',resutlWidth:150,singleSelect: false,gridField:false},
	{name:'BELONG_TEAM_HEAD_NAME',text:'所属团队负责人',xtype:'userchoose',hiddenName:'MGR_ID1',resutlWidth:120,singleSelect: false,gridField:false},	 
	//{name:'FXQ_RISK_LEVEL',text:'当前洗钱风险等级',resutlWidth:120,translateType:'FXQ_RISK_LEVEL',gridField:false},
	
	{name:'CREATE_DATE',text:'开户日期',resutlWidth:100},
	{name:'CUST_GRADE',text:'系统预评等级',resutlWidth:100,translateType:'FXQ_RISK_LEVEL',searchField: true},
	{name:'STAT_FP',text:'复评状态',resutlWidth:120,translateType:'FXQ_FLAG',gridField:true},
	{name:'CUST_GRADE_ID',text:'客户等级编号',resultWidth:60,gridField:false},
    
	//个人客户
	{name:'CITIZENSHIP',text:'国籍',resutlWidth:100,gridField:false,translateType:'XD000025'},
	{name:'CAREER_TYPE',text:'职业',resutlWidth:100,gridField:false},
	{name:'BIRTHDAY',text:'出生日期',resutlWidth:100,gridField:false},
	
	//企业客户
	{name:'BUILD_DATE',text:'成立日期',resutlWidth:100,gridField:false},
	{name:'NATION_CODE',text:'注册地',resutlWidth:100,gridField:false,translateType:'XD000025'},
	{name:'ENT_SCALE_CK',text:'企业规模',resutlWidth:100,gridField:false,translateType:'XD000018'},
	{name:'IN_CLL_TYPE',text:'行业分类',resutlWidth:100,gridField:false,translateType:'XD000287'},
	//  ,'ENT_SCALE_CK'//--企业规模
    //,'IN_CLL_TYPE'//--行业分类
	
	{name:'IDENT_TYPE1',text:'证件1类型',resutlWidth:100,gridField:false},
	{name:'INDENT_NO1',text:'证件1号码',resutlWidth:100,gridField:false},
	{name:'IDENT_EXPIRED_DATE1',text:'证件1有效期',resutlWidth:100,gridField:false},
	{name:'IDENT_TYPE2',text:'证件2类型',resutlWidth:100,gridField:false},
	{name:'INDENT_NO2',text:'证件2号码',resutlWidth:100,gridField:false},
	{name:'IDENT_EXPIRED_DATE2',text:'证件2有效期',resutlWidth:100,gridField:false},
	
	//客户是否为代理开户
	{name:'FLAG_AGENT',text:'客户是否为代理开户',resutlWidth:100,translateType:'IF_FLAG',gridField:false},
		
	//个人反洗钱指标
	{name:'FXQ008',text:'客户是否涉及风险提示信息或权威媒体报道信息',resutlWidth:100,translateType:'IF_FLAG',gridField:false,allowBlank:false},
	{name:'FXQ009',text:'客户或其亲属、关系密切人是否属于外国政要',resutlWidth:100,translateType:'IF_FLAG',gridField:false,allowBlank:false},
	{name:'FXQ007',text:'客户在我行办理的业务包括',resutlWidth:100,translateType:'FXQ007',multiSelect:true,gridField:false,allowBlank:false},
	
	//代理人信息
	{name:'AGENT_NAME',text:'代理人姓名',resutlWidth:100,gridField:false},
	{name:'AGENT_NATION_CODE',text:'代理人国籍',resutlWidth:100,gridField:false,translateType:'XD000025'},
	{name:'AGE_IDENT_TYPE',text:'代理人证件类型',resutlWidth:100,gridField:false},
	{name:'AGE_IDENT_NO',text:'代理人证件号码',resutlWidth:100,gridField:false},
	{name:'TEL',text:'代理人联系方式',resutlWidth:100,gridField:false},
	
	//企业反洗钱指标
	{name:'FXQ021',text:'与客户建立业务关系的渠道',gridField:false,translateType:'FXQ21006',allowBlank:false},
    {name:'FXQ022',text:'是否在规范证券市场上市',gridField:false,translateType:'IF_FLAG',allowBlank:false},
    {name:'FXQ023',text:'客户的股权或控制权结构',gridField:false,translateType:'FXQ023',allowBlank:false},
    {name:'FXQ024',text:'客户是否存在隐名股东或匿名股东',gridField:false,translateType:'IF_FLAG',allowBlank:false},
    {name:'FXQ025',text:'客户办理的业务(对公)',gridField:false,translateType:'FXQ025',multiSelect:true,allowBlank:false},
    {name:'FLAG',gridField:false,hidden:true},
    //20160308 新增三项数据
    {name:'IF_ORG_SUB_TYPE_PER',text:'客户是否为自贸区客户',gridField:false,translateType:'IF_FLAG'},
    {name:'IF_ORG_SUB_TYPE_ORG',text:'客户是否为自贸区客户',gridField:false,translateType:'IF_FLAG'}
  
];

//按钮
var tbar=[{
	text:'客户复评',
	hidden:JsContext.checkGrant('_custReview'),
	handler:function(){
		if (getSelectedData() == false || getAllSelects().length > 1) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		} else {
			var cid = getSelectedData().data.CUST_ID;
			//企业
			if(cid.substr(0,1) == 1){
				showCustomerViewByIndex(0);
			//个人
			}else if(cid.substr(0,1) == 2){
				showCustomerViewByIndex(1);
			}
		}
	}
}];

var customerView = [{
	title:'企业客户复评',
	hideTitle:true,
	type:'form',
	autoLoadSeleted: true,
	suspendWidth: 1,
	groups:[{
		columnCount : 4,
		fields:["CUST_ID", "CORE_NO", "CUST_NAME", "BUILD_DATE"],
		fn:function(CUST_ID, CORE_NO, CUST_NAME, BUILD_DATE){
			CUST_ID.readOnly = true;
		    CUST_ID.cls = 'x-readOnly';
			CORE_NO.readOnly = true;
		    CORE_NO.cls = 'x-readOnly';
			CUST_NAME.readOnly = true;
		    CUST_NAME.cls = 'x-readOnly';
			BUILD_DATE.readOnly = true;
		    BUILD_DATE.cls = 'x-readOnly';
			return [CUST_ID, CORE_NO, CUST_NAME, BUILD_DATE];
		}
	},{
		columnCount : 4,
		fields:["NATION_CODE", "ENT_SCALE_CK", "IN_CLL_TYPE", "INDENT_NO1"],
		fn:function(NATION_CODE, ENT_SCALE_CK, IN_CLL_TYPE, INDENT_NO1){
			NATION_CODE.readOnly = true;
			NATION_CODE.cls = 'x-readOnly';
			ENT_SCALE_CK.readOnly = true;
			ENT_SCALE_CK.cls = 'x-readOnly';
			IN_CLL_TYPE.readOnly = true;
			IN_CLL_TYPE.cls = 'x-readOnly';
			INDENT_NO1.readOnly = true;
		    INDENT_NO1.cls = 'x-readOnly';
			return [NATION_CODE, ENT_SCALE_CK, IN_CLL_TYPE, INDENT_NO1];
		}
	},{
		columnCount : 4,
		fields:["IDENT_TYPE1", "IDENT_EXPIRED_DATE1", "IDENT_TYPE2", "INDENT_NO2"],
		fn:function(IDENT_TYPE1, IDENT_EXPIRED_DATE1, IDENT_TYPE2, INDENT_NO2){
			IDENT_TYPE1.readOnly = true;
		    IDENT_TYPE1.cls = 'x-readOnly';
			IDENT_EXPIRED_DATE1.readOnly = true;
		    IDENT_EXPIRED_DATE1.cls = 'x-readOnly';
			IDENT_TYPE2.readOnly = true;
		    IDENT_TYPE2.cls = 'x-readOnly';
			INDENT_NO2.readOnly = true;
		    INDENT_NO2.cls = 'x-readOnly';
			return [IDENT_TYPE1, IDENT_EXPIRED_DATE1, IDENT_TYPE2, INDENT_NO2];
		}
	},{
		columnCount : 4,
		fields:["IDENT_EXPIRED_DATE2", "AGENT_NAME", "AGENT_NATION_CODE","TEL"],
		fn:function(IDENT_EXPIRED_DATE2, AGENT_NAME, AGENT_NATION_CODE,TEL){
			IDENT_EXPIRED_DATE2.readOnly = true;
		    IDENT_EXPIRED_DATE2.cls = 'x-readOnly';
			AGENT_NAME.readOnly = true;
		    AGENT_NAME.cls = 'x-readOnly';
			AGENT_NATION_CODE.readOnly = true;
		    AGENT_NATION_CODE.cls = 'x-readOnly';
		    TEL.readOnly = true;
		    TEL.cls = 'x-readOnly';
			return [IDENT_EXPIRED_DATE2, AGENT_NAME, AGENT_NATION_CODE,TEL];
		}
	},{
		columnCount : 4,
		fields:["AGE_IDENT_TYPE", "AGE_IDENT_NO","FLAG_AGENT", "CUST_GRADE"],
		fn:function(AGE_IDENT_TYPE, AGE_IDENT_NO,FLAG_AGENT,CUST_GRADE){
			AGE_IDENT_TYPE.readOnly = true;
		    AGE_IDENT_TYPE.cls = 'x-readOnly';
			AGE_IDENT_NO.readOnly = true;
		    AGE_IDENT_NO.cls = 'x-readOnly';
		    CUST_GRADE.readOnly = true;
			CUST_GRADE.cls = 'x-readOnly';
			FLAG_AGENT.readOnly=true;
			FLAG_AGENT.cls='x-readOnly';
		    
			return [AGE_IDENT_TYPE, AGE_IDENT_NO,FLAG_AGENT,CUST_GRADE];
		}
	},{
		columnCount : 4,
		fields:["IF_ORG_SUB_TYPE_ORG","FXQ021", "FXQ022", "FXQ023"],
		fn:function( IF_ORG_SUB_TYPE_ORG,FXQ021, FXQ022, FXQ023){
			IF_ORG_SUB_TYPE_ORG.readOnly=true;
			IF_ORG_SUB_TYPE_ORG.cls='x-readOnly';
			return [IF_ORG_SUB_TYPE_ORG,FXQ021, FXQ022, FXQ023];
		}
	},{
		columnCount : 4,
		fields:["FXQ024", "FXQ008", "FXQ025","FXQ009"
		          ],
		fn:function(FXQ024, FXQ008, FXQ025,FXQ009){
			return [FXQ024, FXQ008, FXQ025,FXQ009];
		}
	},{
		columnCount : 4,
		fields:[{name:'CUST_GRADE_FP',text:'预评等级',translateType:'FXQ_RISK_LEVEL',searchField: true,allowBlank:false}
		          ],
		fn:function(CUST_GRADE_FP){
			return [CUST_GRADE_FP];
		}
	}
//	 {name:'IF_ORG_SUB_TYPE_PER',text:'客户是否为自贸区客户',gridField:false,translateType:'IF_FLAG',multiSelect:true,allowBlank:false},
//    {name:'IF_ORG_SUB_TYPE_ORG',text:'客户是否为自贸区客户',gridField:false,translateType:'IF_FLAG',multiSelect:true,allowBlank:false},
//    {name:'DQSH024',text:'客户是否涉及反洗钱黑名单',gridField:false,translateType:'IF_FLAG',multiSelect:true,allowBlank:false},
// 
	,{
		columnCount : 2,
		fields:["CUST_TYPE", "FLAG","STAT_FP"],
		fn:function(CUST_TYPE,FLAG,STAT_FP){
			 	CUST_TYPE.hidden = true;
			 	FLAG.hidden = true;
			 	STAT_FP.hidden=true;
			return [CUST_TYPE,FLAG,STAT_FP];
		}
	}
	
	],
	formButtons:[{
		
		text:'提交',
		id:'fxqSave',
		fn : function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                return false;
            }
			debugger;
			var custId = this.contentPanel.getForm().getValues().CUST_ID;
			var custName = this.contentPanel.getForm().getValues().CUST_NAME;
			var custType = this.contentPanel.getForm().getValues().CUST_TYPE;
			var fxq007 = this.contentPanel.getForm().getValues().FXQ007;
			var fxq008 = this.contentPanel.getForm().getValues().FXQ008;
			var fxq009 = this.contentPanel.getForm().getValues().FXQ009;
			var fxq010 = this.contentPanel.getForm().getValues().FXQ010;
			var fxq011 = this.contentPanel.getForm().getValues().FXQ011;
			var fxq012 = this.contentPanel.getForm().getValues().FXQ012;
			var fxq013 = this.contentPanel.getForm().getValues().FXQ013;
			var fxq014 = this.contentPanel.getForm().getValues().FXQ014;
			var fxq015 = this.contentPanel.getForm().getValues().FXQ015;
			var fxq016 = this.contentPanel.getForm().getValues().FXQ016;
			var fxq021 = this.contentPanel.getForm().getValues().FXQ021;
			var fxq022 = this.contentPanel.getForm().getValues().FXQ022;
			var fxq023 = this.contentPanel.getForm().getValues().FXQ023;
			var fxq024 = this.contentPanel.getForm().getValues().FXQ024;
			var fxq025 = this.contentPanel.getForm().getValues().FXQ025;
			//var createDate = this.contentPanel.getForm().getValues().CREATE_DATE;
			//这里和例子有所不同
			var flag = this.contentPanel.getForm().getValues().FLAG;
			var custGradeFp = this.contentPanel.getForm().getValues().CUST_GRADE_FP;

			var custGrade = this.contentPanel.getForm().getValues().CUST_GRADE;
			var statFp = this.contentPanel.getForm().getValues().STAT_FP;
			
			if(statFp=="1"){
				Ext.MessageBox.alert('提示','客户已在复评流程中，不允许修改记录');
				return;
			}
	
			//if(custGrade=="H"||custGradeFp=="H"){
				Ext.MessageBox.confirm('提示','需要提交流程，确定执行吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
						return;
					} 
					Ext.Msg.wait('请稍等候,正在提交中...','提示')
					Ext.Ajax.request({
						 url : basepath+ '/customerReviewQuery!savefp.json',
						 params : {
								'custId' :custId,
								'custName' : custName,
								'custType':custType,
								'FXQ007':fxq007,
								'FXQ008':fxq008,
								'FXQ009':fxq009,
								'FXQ010':fxq010,
								'FXQ011':fxq011,
								'FXQ012':fxq012,
								'FXQ013':fxq013,
								'FXQ014':fxq014,
								'FXQ015':fxq015,
								'FXQ016':fxq016,
								'FXQ021':fxq021,
								'FXQ022':fxq022,
								'FXQ023':fxq023,
								'FXQ024':fxq024,
								'FXQ025':fxq025,
								//'createDate':createDate,
								'flag':flag,
								'custGradeFp':custGradeFp,
								'custGrade':custGrade
							},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function(response) {
							debugger;
							var ret = Ext.decode(response.responseText);
							if(ret.existTask == 'existTask'){
								Ext.Msg.alert('提示', "当前客户已存在审核流程！");
							}else{
								var instanceid = ret.instanceid;//流程实例ID
								
								if(custGrade=="H"||custGradeFp=="H"){
									selectUserList(instanceid,"135_a3","135_a4");
								}else{
									selectUserList(instanceid,"135_a3","135_a16");
								}
							}
							
							
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
							if(resultArray == 403) {
								Ext.Msg.alert('提示', response.responseText);
							} else {
								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
								reloadCurrentData();
							}
						}
					});
				});
			/*
				
				
				Ext.Msg.wait('正在保存数据，请稍后......','系统提示');
				Ext.Ajax.request({
					url : basepath + '/customerReviewQuery!savefp.json',
					method : 'POST',
					params : {
						'custId' :custId,
						'custName' : custName,
						'custType':custType,
						'FXQ007':fxq007,
						'FXQ008':fxq008,
						'FXQ009':fxq009,
						'FXQ010':fxq010,
						'FXQ011':fxq011,
						'FXQ012':fxq012,
						'FXQ013':fxq013,
						'FXQ014':fxq014,
						'FXQ015':fxq015,
						'FXQ016':fxq016,
						'FXQ021':fxq021,
						'FXQ022':fxq022,
						'FXQ023':fxq023,
						'FXQ024':fxq024,
						'FXQ025':fxq025,
						//'createDate':createDate,
						'flag':flag,
						'custGradeFp':custGradeFp,
						'custGrade':custGrade
					},
					success : function() {
						 Ext.Msg.alert('提示','操作成功！');
						 reloadCurrentData();
					}
				});
			*/
			
		}
	},{
		text:'关闭',
		fn : function(formPanel){
			 hideCurrentView();
		}
	}]
},{
	title:'个人客户复评',
	hideTitle:true,
	type:'form',
	autoLoadSeleted: true,
	suspendWidth: 1,
	groups:[{
		columnCount : 4,
		fields:["CUST_ID", "CORE_NO", "CUST_NAME", "CITIZENSHIP"],
		fn:function(CUST_ID, CORE_NO, CUST_NAME, CITIZENSHIP){
			CUST_ID.readOnly = true;
		    CUST_ID.cls = 'x-readOnly';
			CORE_NO.readOnly = true;
		    CORE_NO.cls = 'x-readOnly';
			CUST_NAME.readOnly = true;
		    CUST_NAME.cls = 'x-readOnly';
			CITIZENSHIP.readOnly = true;
		    CITIZENSHIP.cls = 'x-readOnly';
			return [CUST_ID, CORE_NO, CUST_NAME, CITIZENSHIP];
		}
	},{
		columnCount : 4,
		fields:["CAREER_TYPE", "BIRTHDAY", "INDENT_NO1", "IDENT_TYPE1"],
		fn:function(CAREER_TYPE, BIRTHDAY, INDENT_NO1, IDENT_TYPE1){
			CAREER_TYPE.readOnly = true;
		    CAREER_TYPE.cls = 'x-readOnly';
			BIRTHDAY.readOnly = true;
		    BIRTHDAY.cls = 'x-readOnly';
			INDENT_NO1.readOnly = true;
		    INDENT_NO1.cls = 'x-readOnly';
			IDENT_TYPE1.readOnly = true;
		    IDENT_TYPE1.cls = 'x-readOnly';
			return [CAREER_TYPE, BIRTHDAY, INDENT_NO1, IDENT_TYPE1];
		}
	},{
		columnCount : 4,
		fields:["IDENT_EXPIRED_DATE1", "IDENT_TYPE2", "INDENT_NO2", "IDENT_EXPIRED_DATE2"],
		fn:function(IDENT_EXPIRED_DATE1, IDENT_TYPE2, INDENT_NO2, IDENT_EXPIRED_DATE2){
			IDENT_EXPIRED_DATE1.readOnly = true;
		    IDENT_EXPIRED_DATE1.cls = 'x-readOnly';
			IDENT_TYPE2.readOnly = true;
		    IDENT_TYPE2.cls = 'x-readOnly';
			INDENT_NO2.readOnly = true;
		    INDENT_NO2.cls = 'x-readOnly';
			IDENT_EXPIRED_DATE2.readOnly = true;
		    IDENT_EXPIRED_DATE2.cls = 'x-readOnly';
			return [IDENT_EXPIRED_DATE1, IDENT_TYPE2, INDENT_NO2, IDENT_EXPIRED_DATE2];
		}
	},{
		columnCount : 4,
		fields:["AGENT_NAME", "AGENT_NATION_CODE", "AGE_IDENT_TYPE", "AGE_IDENT_NO"],
		fn:function(AGENT_NAME, AGENT_NATION_CODE, AGE_IDENT_TYPE, AGE_IDENT_NO){
			AGENT_NAME.readOnly = true;
		    AGENT_NAME.cls = 'x-readOnly';
			AGENT_NATION_CODE.readOnly = true;
		    AGENT_NATION_CODE.cls = 'x-readOnly';
			AGE_IDENT_TYPE.readOnly = true;
		    AGE_IDENT_TYPE.cls = 'x-readOnly';
			AGE_IDENT_NO.readOnly = true;
		    AGE_IDENT_NO.cls = 'x-readOnly';
			return [AGENT_NAME, AGENT_NATION_CODE, AGE_IDENT_TYPE, AGE_IDENT_NO];
		}
	},{
		columnCount : 4,
		fields:["TEL","FLAG_AGENT","IF_ORG_SUB_TYPE_PER","CUST_GRADE"],
		fn:function(TEL,FLAG_AGENT,IF_ORG_SUB_TYPE_PER,CUST_GRADE){
			TEL.readOnly = true;
		    TEL.cls = 'x-readOnly';
		    CUST_GRADE.readOnly = true;
		    CUST_GRADE.cls = 'x-readOnly';
		    FLAG_AGENT.readOnly=true;
		    FLAG_AGENT.cls='x-readOnly';
		    IF_ORG_SUB_TYPE_PER.readOnly=true;
			IF_ORG_SUB_TYPE_PER.cls='x-readOnly';
			return [TEL,FLAG_AGENT,IF_ORG_SUB_TYPE_PER,CUST_GRADE];
		}
	},{
		columnCount : 4,
		fields:[ 
		         "FXQ008", "FXQ009", "FXQ007",{name:'CUST_GRADE_FP',text:'预评等级',translateType:'FXQ_RISK_LEVEL',searchField: true,allowBlank:false}
		         ],
		fn:function( FXQ008, FXQ009, FXQ007,CUST_GRADE_FP){
			return [ FXQ008, FXQ009, FXQ007,CUST_GRADE_FP];
		}
	}
//	 {name:'IF_ORG_SUB_TYPE_PER',text:'客户是否为自贸区客户',gridField:false,translateType:'IF_FLAG',multiSelect:true,allowBlank:false},
//	    {name:'IF_ORG_SUB_TYPE_PER',text:'客户是否为自贸区客户',gridField:false,translateType:'IF_FLAG',multiSelect:true,allowBlank:false},
//	    {name:'DQSH024',text:'客户是否涉及反洗钱黑名单',gridField:false,translateType:'IF_FLAG',multiSelect:true,allowBlank:false},
//	    
//	,{
//		columnCount : 4,
//		fields:[{name:'CUST_GRADE_FP',text:'预评等级',translateType:'FXQ_RISK_LEVEL',searchField: true,allowBlank:false}],
//		fn:function(CUST_GRADE_FP){
//			return [CUST_GRADE_FP];
//		}
//	}
	,{
		columnCount : 2,
		fields:["CUST_TYPE", "FLAG","STAT_FP"],
		fn:function(CUST_TYPE,FLAG,STAT_FP){
			 	CUST_TYPE.hidden = true;
			 	FLAG.hidden = true;
			 	STAT_FP.hidden=true;
			return [CUST_TYPE,FLAG,STAT_FP];
		}
	}],
	formButtons:[
	             {
		
		text:'提交',
		id:'fxqSave',
		fn : function(formPanel,basicForm){
			debugger;//geren
			if (!formPanel.getForm().isValid()) {
                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                return false;
            }
			var custId = this.contentPanel.getForm().getValues().CUST_ID;
			var custName = this.contentPanel.getForm().getValues().CUST_NAME;
			var custType = this.contentPanel.getForm().getValues().CUST_TYPE;
			var fxq007 = this.contentPanel.getForm().getValues().FXQ007;
			var fxq008 = this.contentPanel.getForm().getValues().FXQ008;
			var fxq009 = this.contentPanel.getForm().getValues().FXQ009;
			var fxq010 = this.contentPanel.getForm().getValues().FXQ010;
			var fxq011 = this.contentPanel.getForm().getValues().FXQ011;
			var fxq012 = this.contentPanel.getForm().getValues().FXQ012;
			var fxq013 = this.contentPanel.getForm().getValues().FXQ013;
			var fxq014 = this.contentPanel.getForm().getValues().FXQ014;
			var fxq015 = this.contentPanel.getForm().getValues().FXQ015;
			var fxq016 = this.contentPanel.getForm().getValues().FXQ016;
			var fxq021 = this.contentPanel.getForm().getValues().FXQ021;
			var fxq022 = this.contentPanel.getForm().getValues().FXQ022;
			var fxq023 = this.contentPanel.getForm().getValues().FXQ023;
			var fxq024 = this.contentPanel.getForm().getValues().FXQ024;
			var fxq025 = this.contentPanel.getForm().getValues().FXQ025;
			//var createDate = this.contentPanel.getForm().getValues().CREATE_DATE;
			//这里和例子有所不同
			var flag = this.contentPanel.getForm().getValues().FLAG;
			var custGrade=this.contentPanel.getForm().getValues().CUST_GRADE;
			var custGradeFp = this.contentPanel.getForm().getValues().CUST_GRADE_FP;
			var statFp = this.contentPanel.getForm().getValues().STAT_FP;
			
			
			if(statFp=="1"){
				Ext.MessageBox.alert('提示','客户已在复评流程中，不允许修改记录');
				return;
			}
			
		
				Ext.MessageBox.confirm('提示','需要提交流程，确定执行吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
						return;
					} 
					Ext.Msg.wait('请稍等候,正在提交中...','提示')
					Ext.Ajax.request({
						 url : basepath+ '/customerReviewQuery!savefp.json',
						 params : {
								'custId' :custId,
								'custName' : custName,
								'custType':custType,
								'FXQ007':fxq007,
								'FXQ008':fxq008,
								'FXQ009':fxq009,
								'FXQ010':fxq010,
								'FXQ011':fxq011,
								'FXQ012':fxq012,
								'FXQ013':fxq013,
								'FXQ014':fxq014,
								'FXQ015':fxq015,
								'FXQ016':fxq016,
								'FXQ021':fxq021,
								'FXQ022':fxq022,
								'FXQ023':fxq023,
								'FXQ024':fxq024,
								'FXQ025':fxq025,
								//'createDate':createDate,
								'flag':flag,
								'custGradeFp':custGradeFp,
								'custGrade':custGrade
							},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function(response) {
							debugger;
							var ret = Ext.decode(response.responseText);
							if(ret.existTask == 'existTask'){
								Ext.Msg.alert('提示', "当前客户已存在审核流程！");
							}else{
								var instanceid = ret.instanceid;//流程实例ID
								
//								if(custGrade == 'H' || custGradeFp == 'H'){
//									selectUserList(instanceid,"135_a3","135_a4");
//								}
//								
								if(custGrade=="H"||custGradeFp=="H"){
									selectUserList(instanceid,"135_a3","135_a4");
								}else{
									selectUserList(instanceid,"135_a3","135_a16");
								}
							}
							
							
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
							if(resultArray == 403) {
								Ext.Msg.alert('提示', response.responseText);
							} else {
								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
								reloadCurrentData();
							}
						}
					});
				});
			
			
		}
	},{
		text:'关闭',
		fn : function(formPanel){
			 hideCurrentView();
		}
	}]
}
];

var rowdblclick = function(tile, record){
	if (getSelectedData() == false || getAllSelects().length > 1) {
		Ext.Msg.alert('提示', '请选择一条数据！');
		return false;
	} else {
		var cid = getSelectedData().data.CUST_ID;
		//企业
		if(cid.substr(0,1) == 1){
			showCustomerViewByIndex(0);
		//个人
		}else if(cid.substr(0,1) == 2){
			showCustomerViewByIndex(1);
		}
	}
};

var afterinit = function(){
};

var beforeviewshow = function(view){
};

var viewshow = function(view){
};
var beforeconditioninit = function(panel, app){
	app.pageSize = 100;
};
