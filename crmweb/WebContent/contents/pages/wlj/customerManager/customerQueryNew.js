/**
 * 
* @Description: 客户查询
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

var url=basepath+"/customerQuery.json";
var autoLoadGrid = false;//取消自动加载
var _custId;
var comitUrl=false;
var createView = false;
var editView = false;
var detailView = false;

var addID = '';//保存带加入客户群的成员id
var memberType = '';//群成员类型
/**
 * 设置提示信息,设置定时器，每个0.5秒检查x-toolbar-left，
 * 这个是查询面板按钮前面的空白区域，如果渲染了，则设置提醒信息
 */
(function(){
    var runner = new Ext.util.TaskRunner();
    var task = {
        run: function(){
            var tbarLeft = Ext.DomQuery.selectNode("td[class='x-toolbar-left']");
            if(tbarLeft){
                runner.stop(task);
                var innerHTML = tbarLeft.innerHTML;
                innerHTML = '<div style="color:red;padding-right:10px;font-size:18px;float:right;">请选择查询条件后再查询</div>' + innerHTML;
                Ext.get(tbarLeft).update(innerHTML);
            }
        },
        interval: 500
    }
    runner.start(task);
})();
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
     'XD000082',
     'FXQ_RISK_LEVEL',//反洗钱风险等级
     'XD000081',//客户状态
     'CUST_RISK_CHARACT',
     'XD000096',
     'PRE_CUST_LEVEL',
     'CUSTOMER_GROUP_TYPE',
     'CUSTOMER_SOURCE_TYPE',
     'GROUP_MEMEBER_TYPE',
     'SHARE_FLAG',
     'XD000267',
     'XD000082',
     'XD000245',
     'XD000246',
     'XD000247',
     'IF_FLAG',
     'FXQ007',
     'FXQ010',
     'FXQ21006',
     'FXQ023',
     'FXQ025',
     'APP_STATUS'//特殊客户审核状态
  ];

var localLookup = {
	'ADD_WAY' : [
	   {key : '1',value : '加入已有客户群'},
	   {key : '2',value : '新建客户群'}
	],
	'LAST_PER':[
	  			{key : 'ETL',value : 'ETL'},
			  	{key : __userId,value : __userName}
			]
};

var fields=[
     {name:'CUST_TYPE',text:'客户类型',resutlWidth:60,translateType:'XD000080',searchField:true,
    	 listeners : {
			select : function(n) {
    	 if(n.value=='2'){//当客户类型为对私客户时，展示客户级别，风险等级（个金），隐藏信用评级和业务条线
        	 hideGridFields('BL_NAME');
        	 hideGridFields('CREDIT_LEVEL');
        	 removeConditionField('BL_NAME');
        	 removeConditionField('CREDIT_LEVEL');
        	 showGridFields('CUST_LEVEL');
        	 showGridFields('RISK_LEVEL');
        	 addConditionField('CUST_LEVEL');
    	 }else if(n.value=='1'){//当客户类型为对公客户时，展示信用评级和业务条线，隐藏客户级别,风险等级（个金）
    	 	 hideGridFields('CUST_LEVEL');
    	 	 hideGridFields('RISK_LEVEL');
    		 removeConditionField('CUST_LEVEL');
    		 showGridFields('BL_NAME');
    		 showGridFields('CREDIT_LEVEL');
    		 addConditionField('BL_NAME');
    		 addConditionField('CREDIT_LEVEL');
    	 }
     	}}},
     {name:'CUST_STAT',text:'客户状态',resutlWidth:80,translateType:'XD000081',searchField:true},
     {name:'CUST_ID',text:'客户编号',resutlWidth:120,searchField:true,dataType:'string'},
     {name:'CORE_NO',text:'核心客户号',resutlWidth:120,searchField:true,dataType:'string'},
     {name:'CUST_NAME',text:'客户名称',resutlWidth:150,searchField:true,dataType:'string'},
     {name:'IDENT_TYPE',text:'证件类型',resutlWidth:120,translateType:'XD000040',searchField:true,editable: true},
     {name:'IDENT_NO',text:'证件号码',resutlWidth:120,searchField:true,dataType:'string'},
     //{name:'ACCT_NO',text:'账号',resutlWidth:60,searchField: true,dataType:'string'},
     {name:'ORG_NAME',text:'归属机构',xtype : 'wcombotree',innerTree:'ORGTREE',resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',editable:false,allowBlank:false},	   
     {name: 'MGR_NAME',text:'所属客户经理',xtype:'userchoose',hiddenName:'MGR_ID',resutlWidth:150,singleSelect: false,searchField: true},
     {name: 'BELONG_TEAM_HEAD_NAME',text:'所属团队负责人',xtype:'userchoose',hiddenName:'MGR_ID1',resutlWidth:120,singleSelect: false,searchField: true},
     {name:'LINKMAN_NAME',text:'联系人姓名',resutlWidth:80,dataType:'string'},
     {name:'LINKMAN_TEL',text:'联系人电话',resutlWidth:80,dataType:'string'},
     {name:'EMAIL',text:'邮件地址',resutlWidth:120,dataType:'string'},
     {name:'FXQ_RISK_LEVEL',text:'反洗钱风险等级',resutlWidth:60,translateType:'FXQ_RISK_LEVEL',gridField:true,searchField: true},
     {name:'LAST_UPDATE_USER',text:'最后更新人',resultWidth:60,gridField: false},
     {name:'CREDIT_LEVEL',text:'信用评级(法金)',resutlWidth:80,searchField:true,translateType:'XD000096'},
     {name:'RISK_LEVEL',text:'风险等级(个金)',resutlWidth:80,translateType:'CUST_RISK_CHARACT'},
     {name:'BL_NAME',text:'业务条线',resutlWidth:80 ,xtype : 'wcombotree',innerTree:'BLTREE',searchField: true, showField:'text',hideField:'BL_ID',allowBlank:false},
     {name:'CUST_LEVEL',text:'客户级别',resutlWidth:60,searchField:true,translateType:'PRE_CUST_LEVEL'},
     {name:'TOTAL_DEBT',text:'总负债',resutlWidth:150,dataType:'number'},
     {name:'FAXTRADE_NOREC_NUM',text:'传真交易正本未回收数量',resutlWidth:150,dataType:'numberNoDot'},
     {name:'CURRENT_AUM',text:'管理总资产时点值',resutlWidth:150,dataType:'number'},
     
     {name:'FXQ006',text:'客户是否为代理开户',gridField:false,translateType:'IF_FLAG'},
     {name:'FXQ007',text:'客户办理的业务(对私)',gridField:false,translateType:'FXQ007',multiSelect:true},
     {name:'FXQ008',text:'是否涉及风险提示信息或权威媒体报道信息',gridField:false,translateType:'IF_FLAG'},
     {name:'FXQ009',text:'客户或其亲属、关系密切人等是否属于外国政要',gridField:false,translateType:'IF_FLAG'},
    
     {name:'FXQ010',text:'反洗钱交易监测记录',gridField:false,translateType:'FXQ010'},
     {name:'FXQ011',text:'是否被列入中国发布或承认的应实施反洗钱监控措施的名单',gridField:false,translateType:'IF_FLAG'},
     {name:'FXQ012',text:'是否发生具有异常特征的大额现金交易',gridField:false,translateType:'IF_FLAG'},
     {name:'FXQ013',text:'是否发生具有异常特征的非面对面交易',gridField:false,translateType:'IF_FLAG'},
     {name:'FXQ014',text:'是否存在多次涉及跨境异常交易报告',gridField:false,translateType:'IF_FLAG'},
     {name:'FXQ015',text:'代办业务是否存在异常情况',gridField:false,translateType:'IF_FLAG'},
     {name:'FXQ016',text:'是否频繁进行异常交易',gridField:false,translateType:'IF_FLAG'},
     
     {name:'FXQ021',text:'与客户建立业务关系的渠道',gridField:false,translateType:'FXQ21006'},
     {name:'FXQ022',text:'是否在规范证券市场上市',gridField:false,translateType:'IF_FLAG'},
     {name:'FXQ023',text:'客户的股权或控制权结构',gridField:false,translateType:'FXQ023'},
     {name:'FXQ024',text:'客户是否存在隐名股东或匿名股东',gridField:false,translateType:'IF_FLAG'},
     {name:'FXQ025',text:'客户办理的业务(对公)',gridField:false,translateType:'FXQ025',multiSelect:true},
     {name:'CREATE_DATE',gridField:false},
     {name:'FLAG',gridField:false,hidden:true},
     
     //特殊名单客户表
     {name:'SPECIAL_LIST_TYPE',text:'特殊名单类型',translateType:'XD000245', allowBlank: false,gridField:false},
     {name:'SPECIAL_LIST_KIND',text:'特殊名单类别', translateType:'XD000246', allowBlank: false,gridField:false},
     {name:'SPECIAL_LIST_FLAG',text:'特殊名单标志',translateType:'XD000247', allowBlank: false,gridField:false},
     {name:'ORIGIN',text:'数据来源', allowBlank: false,gridField:false},
     {name:'STAT_FLAG',text:'状态标志', allowBlank: true,gridField:false},
     {name:'APPROVAL_FLAG',text:'审核标志', allowBlank: true,gridField:false,readOnly:true,translateType:'APP_STATUS'},
     {name:'START_DATE',text:'起始日期',dataType:'date', allowBlank: false,gridField:false},
     {name:'END_DATE',text:'结束日期',dataType:'date', allowBlank: false,gridField:false},
     {name:'ENTER_REASON',text:'列入原因',xtype:'textarea', allowBlank: false,gridField:false}
];

var tbar=[{
	text:'客户视图',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		var custId = getSelectedData().data.CUST_ID;
		var custName = getSelectedData().data.CUST_NAME;
		custType = getAllSelects()[0].data.CUST_TYPE;
		if(custType=='2'){
			parent.Wlj.ViewMgr.openViewWindow(5,custId,custName,null,{'viewResId':__resId});
		}else{
			parent.Wlj.ViewMgr.openViewWindow(0,custId,custName);
		}
	}
},{
	text : '设定为我的关注客户',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{   
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				ID += getAllSelects()[i].data.CUST_ID;
				ID += ",";
			}
			ID = ID.substring(0, ID.length-1);
			Ext.Ajax.request({
				url : basepath + '/custConcernOper!create.json',
				method : 'GET',
				params:{
					'condition':ID
				},
				success:function(){
					Ext.Msg.alert("提示","操作成功");
				},
				failure:function(response){
					var resultArray=Ext.util.JSON.decode(response.status);
					if (resultArray == 403) {
							Ext.Msg.alert('提示',
									response.responseText);
						}else {
							Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
						}
				}
			});
		}
	}
},{
	text : '加入客户群',
	hidden:JsContext.checkGrant('_joinCustGroup'),
	handler : function() {
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择数据！');
			return false;
		} 
		memberType = getAllSelects()[0].data.CUST_TYPE;
		addID = '';
		for (var i=0;i<getAllSelects().length;i++){
			addID += getAllSelects()[i].data.CUST_ID;
			addID += ",";
			if(memberType != getAllSelects()[i].data.CUST_TYPE)
				memberType = '3';//公私联动
		}
		addID = addID.substring(0, addID.length-1);
		showCustomerViewByIndex(1);
	}
},{
	text:'个人账户新增/变更/销户申请书',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			custType = getAllSelects()[0].data.CUST_TYPE;
			if(custType != '2'){
				Ext.Msg.alert('提示','客户类型必须为个人！');
				return false;
			}else{
				var custId = getSelectedData().data.CUST_ID;
				var custName = getSelectedData().data.CUST_NAME;
				parent.Wlj.ViewMgr.openViewWindow(4,custId,custName);
			}
		}
	}
},{
	text:'调整反洗钱风险等级',
	hidden:JsContext.checkGrant('_editFsqLevel'),
	handler:function(){
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		} else {
			showCustomerViewByIndex(0);
		}
	}
},{
	text:'设为特殊名单客户',
	hidden:JsContext.checkGrant('tscustName'),
	handler:function(){
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		} else {
			showCustomerViewByTitle('设为特殊名单客户');
		}
	}
},{
	text:'录入客户反洗钱指标',
	hidden:JsContext.checkGrant('lrfxqLevel'),
	handler:function(){
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		} else {
			showCustomerViewByTitle('录入客户反洗钱指标');
		}
	}
}
//,{
//	text : '导入反洗钱初始化数据',
//	handler : function(){
//		Ext.Ajax.request({
//			url : basepath + '/acrmACustFxqIndex!importFxq.json',
//			method : 'POST',
//			success:function(){
//				Ext.Msg.alert("提示","操作成功");
//			}
//		});
//	}
//}
];


var customerView = [{
	title:'反洗钱风险等级调整',
	hideTitle:true,
	type:'form',
	autoLoadSeleted : true,
	groups:[{
		columnCount : 2,
		fields:[ {name:'CUST_GRADE_ID',hidden:true},
				'CUST_ID','CUST_NAME',
		        {name:'FXQ_RISK_LEVEL',text:'反洗钱风险等级',translateType:'FXQ_RISK_LEVEL',allowBlank:false},
		        {name:'LAST_UPDATE_USER',text:'最后更新人',translateType:'LAST_PER'},
		        'CUST_TYPE'
		],
		fn:function(CUST_GRADE_ID,CUST_ID,CUST_NAME,FXQ_RISK_LEVEL,LAST_UPDATE_USER,CUST_TYPE){
			CUST_ID.readOnly = true;
			CUST_ID.cls = 'x-readOnly';
			CUST_NAME.readOnly = true;
			CUST_NAME.cls = 'x-readOnly';
			CUST_TYPE.hidden = true;
			return [CUST_GRADE_ID,CUST_ID,CUST_NAME,FXQ_RISK_LEVEL,LAST_UPDATE_USER,CUST_TYPE];
		}
	}],
	formButtons:[{
		text:'保存',
		fn : function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                return false;
            }
			var riskLevel = this.contentPanel.getForm().getValues().FXQ_RISK_LEVEL;
			var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Ajax.request({
				url : basepath + '/acrmFCiGrade!save.json?riskLevel='+riskLevel,
				method : 'POST',
				params : commintData,
				success : function() {
					 Ext.Msg.alert('提示','操作成功！');
					 reloadCurrentData();
				}
			});
		}
	},{
		text:'关闭',
		fn : function(formPanel){
			 hideCurrentView();
		}
	}]
},{
	title:'加入客户群',
	hideTitle:true,
	type:'form',
	autoLoadSeleted : false,
	groups:[{
		columnCount:2,
		fields:[{name:'ADD_WAY',text:'加入方式',translateType:'ADD_WAY',allowBlank:false
			,listeners:{
				select:function(combo,record){
					if(record.data.key == '1'){
						getCurrentView().contentPanel.getForm().findField("custGroup").show();
					}else{
						getCurrentView().contentPanel.getForm().findField("custGroup").hide();
					}
				}
			}}],
		fn:function(ADD_WAY){
			var groups = new  Com.yucheng.bcrm.common.CustGroup({ 
				fieldLabel : '选择客户群', 
				labelStyle: 'text-align:right;',
				name : 'custGroup',
				allowDtGroup:false,//不能选择动态客户群
			    singleSelected:true,//单选复选标志
				editable : false,
				blankText:"请填写",
				anchor : '85%',
				hiddenName:'groupStr'
			});
			return [ADD_WAY,groups];
		}
	}],
	formButtons :[{
		text:'确定',
		fn:function(formPanel,basicForm){
			var addWay = formPanel.getForm().getValues().ADD_WAY;
			if(addWay == ''){
				Ext.MessageBox.alert('系统提示信息', '请先选择加入方式！');
        		 return false;
			}
			if(addWay == '1'){//直接加入
				var group = formPanel.getForm().getValues().groupStr;
				if(group == ''){
					 Ext.MessageBox.alert('系统提示信息', '请先选择客户群！');
	        		 return false;
				}
				//将选中的客户存入关联客户信息
				 Ext.Ajax.request({
	    				url : basepath + '/groupmemberedit!saveMemberBySearch.json',
	    				params:{'custId':addID,
	    					type:'add',
	    					'groupId':formPanel.form.findField('custGroup').custBaseId
	    					},
	    					method : 'GET',
	    					waitMsg : '正在保存,请等待...',
	    					success :checkResult,
							failure :checkResult
				 });
				 function checkResult(response){
					 var resultArray = Ext.util.JSON.decode(response.status);
						if (resultArray == 200 ||resultArray == 201) {
							var number =  Ext.util.JSON.decode(response.responseText).number;
	    					Ext.MessageBox.alert('系统提示信息', '操作成功，排除重复之后加入客户'+number+'位');
	    					 reloadCurrentData();
						}else if(resultArray == 403) {
 				           Ext.Msg.alert('系统提示', response.responseText);
	    				  } else{
	    					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	    				  }
				 }
			}else {//新建客户群
				showCustomerViewByIndex(2);
			}
			
		}},{
			text:'关闭',
			fn : function(formPanel){
				 hideCurrentView();
			}
		}]
},{
	title:'新建客户群',
	hideTitle:true,
	type:'form',
	groups:[{
		columnCount:2,
		fields:[{name:'CUST_BASE_NAME',text:'客户群名称',allowBlank:false},
		        {name:'SHARE_FLAG',text:'共享范围',translateType:'SHARE_FLAG',allowBlank:false},
		        {name:'CUST_FROM',text:'客户来源',translateType:'CUSTOMER_SOURCE_TYPE',allowBlank:false,readOnly:true,cls:'x-readOnly'},
		        {name:'GROUP_MEMBER_TYPE',text:'群成员类型',translateType:'GROUP_MEMEBER_TYPE',allowBlank:false},
		        {name:'GROUP_TYPE',text:'客户群分类',translateType:'CUSTOMER_GROUP_TYPE'}],
		fn:function(CUST_BASE_NAME,SHARE_FLAG,CUST_FROM,GROUP_MEMBER_TYPE,GROUP_TYPE){
			GROUP_TYPE.hidden = true;
			return [CUST_BASE_NAME,SHARE_FLAG,CUST_FROM,GROUP_MEMBER_TYPE,GROUP_TYPE];
		}},{
			columnCount:1,
			fields:[{name:'CUST_BASE_DESC',text:'客户群描述',xtype:'textarea'}],
			fn:function(CUST_BASE_DESC){
				CUST_BASE_DESC.anchor = '95%';
				return [CUST_BASE_DESC];
			}
		}],
	formButtons:[{
		text:'确定',
		fn:function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                return false;
            }
			var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			 Ext.Msg.wait('正在处理，请稍后......','系统提示');
			  Ext.Ajax.request({
					url : basepath + '/customergroupinfo!saveWithMenber.json?custId='+addID,
					method : 'POST',
					params : commintData,
					success : function(r) {
						Ext.Ajax.request({
    				         url: basepath +'/customergroupinfo!getPid.json',
    					         success:function(response){
    							 var groupId = Ext.util.JSON.decode(response.responseText).pid;
	    							 //将选中的客户存入关联客户信息
	    							 Ext.Ajax.request({
	    		    	    				url : basepath + '/groupmemberedit!saveMemberBySearch.json',
	    		    	    				params:{'custId':addID,
	    		    	    					type:'new',
	    		    	    					'groupId':groupId
	    		    	    					},
	    		    	    				success : function() {
	    		    	    					Ext.Msg.alert('提示','操作成功！');
	    			   							 reloadCurrentData();
	    		    	    				}
	    							 });
    						 	}
    						 });
					}
				}); 
		}
	},{
		text:'关闭',
		fn : function(formPanel){
			 hideCurrentView();
		}
	}]
},{
	title: '客户等级详情',
//	hideTitle: true,
	type: 'grid',
	url  : basepath+'/acrmFCiGrade.json?CUST_ID='+_custId,//客户等级信息访问后台数据的路径
	frame : true,
	isCsm:false,
	fields: {
		fields : [
		          'CUST_ID',
		          'CUST_NAME',
		          {name: 'CUST_GRADE_TYPE', text: '等级类型',translateType: 'XD000267', renderer:function(value){
				    	 return translateLookupByKey("XD000267",value);
				     }},
		          {name: 'CUST_GRADE', text: '客户等级',translateType: 'XD000082', renderer:function(value){
			    	 return translateLookupByKey("XD000082",value);
			     }},
		          {name: 'EVALUATE_DATE', text: '评定日期', format:'Y-m-d'}
				]
		}
},{
	title: '设为特殊名单客户',
	hideTitle: true,
	type: 'form',
	autoLoadSeleted : true,
	groups:[{
		fields:['CUST_ID','CUST_NAME','IDENT_TYPE','IDENT_NO','SPECIAL_LIST_TYPE','SPECIAL_LIST_KIND','SPECIAL_LIST_FLAG','ORIGIN','STAT_FLAG','APPROVAL_FLAG','START_DATE','END_DATE'],
		fn:function(CUST_ID,CUST_NAME,IDENT_TYPE,IDENT_NO,SPECIAL_LIST_TYPE,SPECIAL_LIST_KIND,SPECIAL_LIST_FLAG,ORIGIN,STAT_FLAG,APPROVAL_FLAG,START_DATE,END_DATE){
			CUST_ID.readOnly = true;
			CUST_NAME.readOnly = true;
			IDENT_TYPE.readOnly = true;
			IDENT_NO.readOnly = true;
			CUST_ID.cls = 'x-readOnly';
			CUST_NAME.cls = 'x-readOnly';
			IDENT_TYPE.cls = 'x-readOnly';
			IDENT_NO.cls = 'x-readOnly';
			STAT_FLAG.hidden = true;
			APPROVAL_FLAG.hidden = true;
			return [CUST_ID,CUST_NAME,IDENT_TYPE,IDENT_NO,SPECIAL_LIST_TYPE,SPECIAL_LIST_KIND,SPECIAL_LIST_FLAG,ORIGIN,STAT_FLAG,APPROVAL_FLAG,START_DATE,END_DATE];
		}},{
			columnCount:1,
			fields:['ENTER_REASON'],
			fn:function(ENTER_REASON){
				return [ENTER_REASON];
			}
		}],
	formButtons:[{
		id:'speSaveId',
		text:'保存',
		fn : function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                return false;
            }
			var custId = this.contentPanel.getForm().getValues().CUST_ID;
			var custName = this.contentPanel.getForm().getValues().CUST_NAME;
			var identType = this.contentPanel.getForm().getValues().IDENT_TYPE;
			var identNo = this.contentPanel.getForm().getValues().IDENT_NO;
			var specialListType = this.contentPanel.getForm().getValues().SPECIAL_LIST_TYPE;
			var specialListKind = this.contentPanel.getForm().getValues().SPECIAL_LIST_KIND;
			var specialListFlag = this.contentPanel.getForm().getValues().SPECIAL_LIST_FLAG;
			var origin = this.contentPanel.getForm().getValues().ORIGIN;
			var statFlag = this.contentPanel.getForm().getValues().STAT_FLAG;
			var approvalFlag = this.contentPanel.getForm().getValues().APPROVAL_FLAG;
			var startDate = this.contentPanel.getForm().getValues().START_DATE;
			var endDate = this.contentPanel.getForm().getValues().END_DATE;
			var enterReason = this.contentPanel.getForm().getValues().ENTER_REASON;
			if(startDate > endDate){
				Ext.MessageBox.alert('提示','起始日期应该小于等于结束日期');
                return false;
			}else if(origin.length>10){
				Ext.MessageBox.alert('提示','数据来源不能大于10字符');
                return false;
			}else if(statFlag.length>10){
				Ext.MessageBox.alert('提示','状态标记不能大于10字符');
                return false;
			}else if(enterReason.length > 500){
				Ext.MessageBox.alert('提示','列入原因不能大于500字符');
                return false;
			}else{
				Ext.Ajax.request({
					url : basepath + '/acrmFCiSpeciallist!save.json',
					method : 'POST',
					params : {
						'custId' :custId,
						'custName' : custName,
						'identType' : identType,
						'identNo' : identNo,
						'specialListType' : specialListType,
						'specialListKind' : specialListKind,
						'specialListFlag' : specialListFlag,
						'origin' : origin,
						'statFlag' : statFlag,
						'approvalFlag' : approvalFlag,
						'startDate' : startDate,
						'endDate' : endDate,
						'enterReason' : enterReason
				},
				success : function() {
					 Ext.Msg.alert('提示','操作成功！');
					 Ext.getCmp("speAuditId").setDisabled(false);
					 lockGrid();
				}
				});
			}
		}
	},{
		id:'speAuditId',
		text : '提交审批',
		fn : function(formPanel, baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示','输入格式错误或存在漏输入项,请重新输入！');
				return false;
			}
			var json2 = this.contentPanel.form.getValues(false);
			var perModel = [];
			for(var key in json2){
				var pcbhModel = {};
				if(this.contentPanel.getForm().findField(key).getXType()=='combo'){
					var s=this.contentPanel.getForm().findField(key).getValue();
					pcbhModel.custId =this.contentPanel.getForm().findField("CUST_ID").getValue();
					pcbhModel.updateBeCont = '';
					pcbhModel.updateAfCont = s;
					pcbhModel.updateItem = this.contentPanel.getForm().findField(key).fieldLabel.replace('<font color=red>*</font>', '');
					pcbhModel.updateItemEn=this.contentPanel.getForm().findField(key).name;
					perModel.push(pcbhModel);
				}else{
					pcbhModel.custId = this.contentPanel.getForm().findField("CUST_ID").getValue();
					pcbhModel.updateBeCont = '';
					pcbhModel.updateAfCont = json2[key];
					pcbhModel.updateItem = this.contentPanel.getForm().findField(key).fieldLabel.replace('<font color=red>*</font>', '');
					pcbhModel.updateItemEn=this.contentPanel.getForm().findField(key).name;
					perModel.push(pcbhModel);
				}
			}
			var custId = this.contentPanel.getForm().getValues().CUST_ID;
			var custName = this.contentPanel.getForm().getValues().CUST_NAME;
			var identType = this.contentPanel.getForm().getValues().IDENT_TYPE;
			var identNo = this.contentPanel.getForm().getValues().IDENT_NO;
			var specialListType = this.contentPanel.getForm().getValues().SPECIAL_LIST_TYPE;
			var specialListKind = this.contentPanel.getForm().getValues().SPECIAL_LIST_KIND;
			var specialListFlag = this.contentPanel.getForm().getValues().SPECIAL_LIST_FLAG;
			var origin = this.contentPanel.getForm().getValues().ORIGIN;
			var statFlag = this.contentPanel.getForm().getValues().STAT_FLAG;
			var approvalFlag = this.contentPanel.getForm().getValues().APPROVAL_FLAG;
			var startDate = this.contentPanel.getForm().getValues().START_DATE;
			var endDate = this.contentPanel.getForm().getValues().END_DATE;
			var enterReason = this.contentPanel.getForm().getValues().ENTER_REASON;
			if(startDate > endDate){
				Ext.MessageBox.alert('提示','起始日期应该小于等于结束日期');
                return false;
			}else if(enterReason.length > 500){
				Ext.MessageBox.alert('提示','列入原因不能大于500字符');
                return false;
			}else{
				 Ext.Msg.wait('正在提交，请稍后......','系统提示');
				 Ext.Ajax.request({
		             url : basepath + '/acrmFCiSpeciallist!initFlow.json',
		             method : 'GET',
		             params : {
				                'perModel':Ext.encode(perModel),
				                'custId' :custId,
								'custName' : custName,
								'identType' : identType,
								'identNo' : identNo,
								'specialListType' : specialListType,
								'specialListKind' : specialListKind,
								'specialListFlag' : specialListFlag,
								'origin' : origin,
								'statFlag' : statFlag,
								'approvalFlag' : approvalFlag,
								'startDate' : startDate,
								'endDate' : endDate,
								'enterReason' : enterReason
							},
		             success : function(response) {
		             	var ret = Ext.decode(response.responseText);
						var instanceid = ret.instanceid;//流程实例ID
						var currNode = ret.currNode;//当前节点
						var nextNode = ret.nextNode;//下一步节点
						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
		             },
		             failure : function(response) {
		                 Ext.Msg.alert('提示', '操作失败!');
		             }
				});
			}
			
		}
	},{
		text:'关闭',
		fn : function(formPanel){
			 hideCurrentView();
		}
	}]
},{
	title:'录入客户反洗钱指标',
	hideTitle:true,
	type:'form',
	autoLoadSeleted : true,
	groups:[{
		columnCount : 2,
		fields:['CUST_ID','CUST_NAME','FXQ006','FXQ007','FXQ008','FXQ009','FXQ021','FXQ022',
		        'FXQ023','FXQ024','FXQ025','CUST_TYPE','CREATE_DATE','FLAG'],
		fn:function(CUST_ID,CUST_NAME,FXQ006,FXQ007,FXQ008,FXQ009,FXQ021,FXQ022,
		        FXQ023,FXQ024,FXQ025,CUST_TYPE,CREATE_DATE,FLAG){
			CUST_ID.readOnly = true;
			CUST_ID.cls = 'x-readOnly';
			CUST_NAME.readOnly = true;
			CUST_NAME.cls = 'x-readOnly';
			CUST_TYPE.hidden = true;
			CREATE_DATE.hidden = true;
			FLAG.hidden = true;
			return [CUST_ID,CUST_NAME,FXQ006,FXQ007,FXQ008,FXQ009,FXQ021,FXQ022,
			        FXQ023,FXQ024,FXQ025,CUST_TYPE,CREATE_DATE,FLAG];
		}
	},{
		columnCount : 2,
		fields:['FXQ010','FXQ011','FXQ012','FXQ013','FXQ014','FXQ015','FXQ016'],
		fn:function(FXQ010,FXQ011,FXQ012,FXQ013,FXQ014,FXQ015,FXQ016){
			return [FXQ010,FXQ011,FXQ012,FXQ013,FXQ014,FXQ015,FXQ016];
		}
	}],
	formButtons:[{
		text:'保存',
		id:'fxqSave',
		fn : function(formPanel,basicForm){
			var custId = this.contentPanel.getForm().getValues().CUST_ID;
			var custName = this.contentPanel.getForm().getValues().CUST_NAME;
			var custType = this.contentPanel.getForm().getValues().CUST_TYPE;
			var fxq006 = this.contentPanel.getForm().getValues().FXQ006;
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
			var createDate = this.contentPanel.getForm().getValues().CREATE_DATE;
			var flag = this.contentPanel.getForm().getValues().FLAG;
			Ext.Msg.wait('正在保存数据，请稍后......','系统提示');
			Ext.Ajax.request({
				url : basepath + '/acrmACustFxqIndex!save.json',
				method : 'POST',
				params : {
					'custId' :custId,
					'custName' : custName,
					'custType':custType,
					'FXQ006':fxq006,
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
					'createDate':createDate,
					'flag':flag
				},
				success : function() {
					 Ext.Msg.alert('提示','操作成功！');
					 reloadCurrentData();
				}
			});
		}
	},{
		text:'关闭',
		fn : function(formPanel){
			 hideCurrentView();
		}
	}]
}];
/**
 * 設置其他字段為空值
 * @param val
 * @returns
 */
var setValueClick = function(val) {
	var fields = ['FXQ006','FXQ007','FXQ008','FXQ009','FXQ010','FXQ011','FXQ012','FXQ013','FXQ014',
	              'FXQ015','FXQ016','FXQ021','FXQ022','FXQ023','FXQ024','FXQ025']
	for(var i=0;i<fields.length;i++){
		if(fields[i] != val){
			getCurrentView().contentPanel.getForm().findField(fields[i]).setValue("");
		}
	}
}
var rowdblclick = function(tile, record){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择数据！');
		return false;
	}
	var custId = getSelectedData().data.CUST_ID;
	var custName = getSelectedData().data.CUST_NAME;
	parent.Wlj.ViewMgr.openViewWindow(0,custId,custName);
};
var afterinit = function(){
	//初始化面板，对中间面板添加蒙版限制
	 hideGridFields(['BL_NAME','CREDIT_LEVEL','CUST_LEVEL']);
	 removeConditionField('BL_NAME');
	 removeConditionField('CREDIT_LEVEL');
	 removeConditionField('CUST_LEVEL');
};

var beforeviewshow = function(view){
	if(view._defaultTitle == '客户等级详情'){
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		}else{
			view.setParameters({
				CUST_ID_P : getSelectedData().data.CUST_ID
			});
		}
	}else if(view._defaultTitle == '加入客户群'){
		view.contentPanel.getForm().findField("custGroup").hide();
		view.contentPanel.getForm().findField("ADD_WAY").setValue('');
	}else if(view._defaultTitle == '新建客户群'){
		view.contentPanel.getForm().reset();
		view.contentPanel.getForm().findField("CUST_FROM").setValue('1');
		view.contentPanel.getForm().findField("GROUP_MEMBER_TYPE").setValue(memberType);
		if(memberType == '3')
			view.contentPanel.getForm().findField("GROUP_MEMBER_TYPE").setDisabled(true);
		else
			view.contentPanel.getForm().findField("GROUP_MEMBER_TYPE").setDisabled(false);
	}else if(view._defaultTitle == '录入客户反洗钱指标'){
		view.contentPanel.getForm().findField("FXQ006").hide();
		view.contentPanel.getForm().findField("FXQ007").hide();
		view.contentPanel.getForm().findField("FXQ008").hide();
		view.contentPanel.getForm().findField("FXQ009").hide();
		
		view.contentPanel.getForm().findField("FXQ010").hide();
		view.contentPanel.getForm().findField("FXQ011").hide();
		view.contentPanel.getForm().findField("FXQ012").hide();
		view.contentPanel.getForm().findField("FXQ013").hide();
		view.contentPanel.getForm().findField("FXQ014").hide();
		view.contentPanel.getForm().findField("FXQ015").hide();
		view.contentPanel.getForm().findField("FXQ016").hide();
		view.contentPanel.getForm().findField("FXQ021").hide();
		view.contentPanel.getForm().findField("FXQ022").hide();
		view.contentPanel.getForm().findField("FXQ023").hide();
		view.contentPanel.getForm().findField("FXQ024").hide();
		view.contentPanel.getForm().findField("FXQ025").hide();
		
		var custType = getSelectedData().data.CUST_TYPE;
		var createDate = getSelectedData().data.CREATE_DATE;
		var flag = getSelectedData().data.FLAG;//新老客户标识 3 新客户 2 ,1老客户
		if(custType == '2'){//对私
//			if(flag == '3'){//新客户
//				Ext.getCmp('fxqSave').show();
//				view.contentPanel.getForm().findField("FXQ006").show();
//				view.contentPanel.getForm().findField("FXQ007").show();
//				view.contentPanel.getForm().findField("FXQ008").show();
//				view.contentPanel.getForm().findField("FXQ009").show();
//				hideGridFields(['FXQ010'],['FXQ011'],['FXQ012'],['FXQ013'],['FXQ014'],['FXQ015'],['FXQ016'],
//						['FXQ021'],['FXQ022'],['FXQ023'],['FXQ024'],['FXQ025']);
//				var roleCodes = __roleCodes;// 当前用户拥有的据角色编码 hideGridFields(['BL_NAME','CREDIT_LEVEL','CUST_LEVEL']);showGridFields
//				if (roleCodes != null && roleCodes != "") {
//					var roleArrs = roleCodes.split('$');
//					for ( var i = 0; i < roleArrs.length; i++) {
//								if (roleArrs[i] == "R115") {//合规处反洗钱部经办
//									view.contentPanel.getForm().findField("FXQ006").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ006").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ007").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ007").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
//								}else if(roleArrs[i] == "R116"){//合规处反洗钱部复核
//									view.contentPanel.getForm().findField("FXQ006").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ006").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ007").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ007").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
//								}
//								Ext.getCmp('fxqSave').hide();
//						}
//				}
//			}else{//老客户
				Ext.getCmp('fxqSave').show();
				view.contentPanel.getForm().findField("FXQ006").show();
				view.contentPanel.getForm().findField("FXQ007").show();
				view.contentPanel.getForm().findField("FXQ008").show();
				view.contentPanel.getForm().findField("FXQ009").show();
				hideGridFields(['FXQ010'],['FXQ011'],['FXQ012'],['FXQ013'],['FXQ014'],['FXQ015'],['FXQ016'],
						['FXQ021'],['FXQ022'],['FXQ023'],['FXQ024'],['FXQ025']);
				var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
				if (roleCodes != null && roleCodes != "") {
					var roleArrs = roleCodes.split('$');
					for ( var i = 0; i < roleArrs.length; i++) {
								if (roleArrs[i] == "R115") {//合规处反洗钱部经办
									view.contentPanel.getForm().findField("FXQ006").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ006").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ007").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ007").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
									
									view.contentPanel.getForm().findField("FXQ010").show();
									view.contentPanel.getForm().findField("FXQ011").show();
									view.contentPanel.getForm().findField("FXQ012").show();
									view.contentPanel.getForm().findField("FXQ013").show();
									view.contentPanel.getForm().findField("FXQ014").show();
									view.contentPanel.getForm().findField("FXQ015").show();
									view.contentPanel.getForm().findField("FXQ016").show();
								}else if(roleArrs[i] == "R116"){//合规处反洗钱部复核
									view.contentPanel.getForm().findField("FXQ006").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ006").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ007").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ007").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
									
									view.contentPanel.getForm().findField("FXQ010").show();
									view.contentPanel.getForm().findField("FXQ011").show();
									view.contentPanel.getForm().findField("FXQ012").show();
									view.contentPanel.getForm().findField("FXQ013").show();
									view.contentPanel.getForm().findField("FXQ014").show();
									view.contentPanel.getForm().findField("FXQ015").show();
									view.contentPanel.getForm().findField("FXQ016").show();
									
									view.contentPanel.getForm().findField("FXQ010").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ010").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ011").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ011").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ012").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ012").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ013").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ013").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ014").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ014").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ015").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ015").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ016").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ016").setReadOnly(true);
									
									Ext.getCmp('fxqSave').hide();
								}
						}
//				}
			}
		}else{//对公
//			if(flag == '3'){//新客户
//				Ext.getCmp('fxqSave').show();
//				view.contentPanel.getForm().findField("FXQ008").show();
//				view.contentPanel.getForm().findField("FXQ009").show();
//				view.contentPanel.getForm().findField("FXQ021").show();
//				view.contentPanel.getForm().findField("FXQ022").show();
//				view.contentPanel.getForm().findField("FXQ023").show();
//				view.contentPanel.getForm().findField("FXQ024").show();
//				view.contentPanel.getForm().findField("FXQ025").show();
//				hideGridFields(['FXQ010'],['FXQ011'],['FXQ012'],['FXQ013'],['FXQ014'],['FXQ015'],['FXQ016'],['FXQ006'],['FXQ007']);
//				var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
//				if (roleCodes != null && roleCodes != "") {
//					var roleArrs = roleCodes.split('$');
//					for ( var i = 0; i < roleArrs.length; i++) {
//								if (roleArrs[i] == "R115") {//合规处反洗钱部经办
//									view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ021").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ021").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ022").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ022").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ023").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ023").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ024").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ024").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ025").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ025").setReadOnly(true);
//									
//								}else if(roleArrs[i] == "R116"){//合规处反洗钱部复核
//									view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ021").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ021").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ022").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ022").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ023").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ023").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ024").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ024").setReadOnly(true);
//									view.contentPanel.getForm().findField("FXQ025").addClass("x-readOnly");
//									view.contentPanel.getForm().findField("FXQ025").setReadOnly(true);
//								}
//								Ext.getCmp('fxqSave').hide();
//						}
//				}
//			}else{
				Ext.getCmp('fxqSave').show();
				view.contentPanel.getForm().findField("FXQ008").show();
				view.contentPanel.getForm().findField("FXQ009").show();
				view.contentPanel.getForm().findField("FXQ021").show();
				view.contentPanel.getForm().findField("FXQ022").show();
				view.contentPanel.getForm().findField("FXQ023").show();
				view.contentPanel.getForm().findField("FXQ024").show();
				view.contentPanel.getForm().findField("FXQ025").show();
				hideGridFields(['FXQ010'],['FXQ011'],['FXQ012'],['FXQ013'],['FXQ014'],['FXQ015'],['FXQ016'],['FXQ006'],['FXQ007']);
				var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
				if (roleCodes != null && roleCodes != "") {
					var roleArrs = roleCodes.split('$');
					for ( var i = 0; i < roleArrs.length; i++) {
								if (roleArrs[i] == "R115") {//合规处反洗钱部经办
									view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ021").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ021").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ022").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ022").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ023").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ023").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ024").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ024").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ025").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ025").setReadOnly(true);
									
									view.contentPanel.getForm().findField("FXQ010").show();
									view.contentPanel.getForm().findField("FXQ011").show();
									view.contentPanel.getForm().findField("FXQ012").show();
									view.contentPanel.getForm().findField("FXQ013").show();
									view.contentPanel.getForm().findField("FXQ014").show();
									view.contentPanel.getForm().findField("FXQ015").show();
									view.contentPanel.getForm().findField("FXQ016").show();
								}else if(roleArrs[i] == "R116"){//合规处反洗钱部复核
									view.contentPanel.getForm().findField("FXQ008").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ008").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ009").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ009").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ021").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ021").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ022").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ022").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ023").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ023").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ024").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ024").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ025").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ025").setReadOnly(true);
									
									view.contentPanel.getForm().findField("FXQ010").show();
									view.contentPanel.getForm().findField("FXQ011").show();
									view.contentPanel.getForm().findField("FXQ012").show();
									view.contentPanel.getForm().findField("FXQ013").show();
									view.contentPanel.getForm().findField("FXQ014").show();
									view.contentPanel.getForm().findField("FXQ015").show();
									view.contentPanel.getForm().findField("FXQ016").show();
									
									view.contentPanel.getForm().findField("FXQ010").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ010").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ011").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ011").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ012").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ012").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ013").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ013").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ014").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ014").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ015").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ015").setReadOnly(true);
									view.contentPanel.getForm().findField("FXQ016").addClass("x-readOnly");
									view.contentPanel.getForm().findField("FXQ016").setReadOnly(true);
									
									Ext.getCmp('fxqSave').hide();
								}
						}
				}
//			}
		}
	}
};

var viewshow = function(view){
//	if(view._defaultTitle == '客户等级详情'){
//		view.setParameters({
//				CUST_ID_P : getSelectedData().data.CUST_ID
//			});
//	}
	if(view._defaultTitle=='设为特殊名单客户'){
		if(getSelectedData().data.APPROVAL_FLAG == "2"){//待审批，不能操作
			Ext.getCmp("speSaveId").setDisabled(true);
			Ext.getCmp("speAuditId").setDisabled(true);
		}else if(getSelectedData().data.APPROVAL_FLAG == ""){//属于新增
			Ext.getCmp("speAuditId").setDisabled(true);
		}else{
			Ext.getCmp("speSaveId").setDisabled(false);
			Ext.getCmp("speAuditId").setDisabled(false);
		}
	}
	
	if(view._defaultTitle=='反洗钱风险等级调整'){
		var custId=view.contentPanel.form.findField("CUST_ID").getValue();
		Ext.Ajax.request({
			url :basepath +'/acrmFCiGrade!searchGrade.json',
			method : 'GET',
			params : {custId:custId},
			success : function(response) {
				 var ret = Ext.decode(response.responseText);
				 view.contentPanel.form.findField("CUST_GRADE_ID").setValue(ret.data[0].CUST_GRADE_ID);
			}
		});
	}
};
var beforeviewhide = function(view){
	if(view._defaultTitle=='设为特殊名单客户'){
		reloadCurrentData();
	}
}
var beforeconditioninit = function(panel, app){
	app.pageSize = 100;
};