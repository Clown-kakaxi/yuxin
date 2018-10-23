/**
 *@description 法金客户查询
 *@author likai3
 *@since 2015/03/02
 **/

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js', // 机构放大镜
	'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',	//用户放大镜
	'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'
]);

var url=basepath+"/comCustomerSearch.json";

var _custId;
var needCondition = true;
var needGrid = true;

var addID = '';//保存带加入客户群的成员id
var memberType = '';//群成员类型

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
}];
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
     'APP_STATUS'//特殊客户审核状态
  ];

var localLookup = {
	'ADD_WAY' : [
	   {key : '1',value : '加入已有客户群'},
	   {key : '2',value : '新建客户群'}
	]
};

var fields=[
     {name:'CUST_ID',text:'客户编号',resutlWidth:120,searchField:true,dataType:'string'},
     {name:'CORE_NO',text:'核心客户号',resutlWidth:120,searchField:true,dataType:'string',gridField:false},
     {name:'CUST_NAME',text:'客户名称',resutlWidth:150,searchField:true,dataType:'string'},
     {name:'CUST_TYPE',text:'客户类型',resutlWidth:60,translateType:'XD000080',searchField:true},
     {name:'BL_NAME',text:'客户属性',resutlWidth:80 ,xtype : 'wcombotree',innerTree:'BLTREE',searchField: false, showField:'text',hideField:'BL_ID',allowBlank:false},
     {name:'CUST_STAT',text:'客户状态',resutlWidth:80,translateType:'XD000081',searchField:true},
     {name:'LINKMAN_NAME',text:'联系人姓名',resutlWidth:60,dataType:'string'},
     {name:'LINKMAN_TEL',text:'联系人电话',resutlWidth:80,dataType:'string'},	   
     {name:'MGR_NAME',text:'所属客户经理',xtype:'userchoose',hiddenName:'MGR_ID',resutlWidth:80,singleSelect: false,searchField: true},
     {name:'BELONG_TEAM_HEAD_NAME',text:'所属团队负责人',xtype:'userchoose',hiddenName:'MGR_ID1',resutlWidth:80,singleSelect: false,searchField: true},
     {name:'ORG_NAME',text:'归属机构',xtype : 'wcombotree',innerTree:'ORGTREE',resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',editable:false,allowBlank:false},
     {name:'IDENT_TYPE',text:'证件类型',resutlWidth:120,translateType:'XD000040',searchField:true,editable: true},
     {name:'IDENT_NO',text:'证件号码',resutlWidth:120,searchField:true,dataType:'string'},
     {name:'FXQ_RISK_LEVEL',text:'反洗钱风险等级',resutlWidth:80,translateType:'FXQ_RISK_LEVEL',gridField:true,searchField: true},
     {name:'CREDIT_LEVEL',text:'法金信用评级',resutlWidth:80,searchField:false},
     {name:'COMP_LEVEL',text:'商金企业评级',resutlWidth:80,gridField:true},
     {name:'COMP_TYPE',text:'商金企业类型',resutlWidth:80,gridField:true},
     {name:'GROUP_NAME',text:'所属集团名称',resutlWidth:80,gridField:true},
     {name:'FAXTRADE_NOREC_NUM',text:'传真交易正本未回收数量',resutlWidth:150,dataType:'numberNoDot'},
     
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
		parent.Wlj.ViewMgr.openViewWindow(0,custId,custName,1);
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
	hidden:true,
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
		showCustomerViewByTitle('加入客户群');
	}
},{
	text:'设为特殊名单客户',
	hidden:true,
	handler:function(){
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		} else {
			showCustomerViewByTitle('设为特殊名单客户');
		}
	}
}];


var customerView = [{
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
				showCustomerViewByIndex(1);
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
		             url : basepath + '/acrmFCiSpeciallist!initFlow1.json',
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
}];

var rowdblclick = function(tile, record){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择数据！');
		return false;
	}
	var custId = getSelectedData().data.CUST_ID;
	var custName = getSelectedData().data.CUST_NAME;
	parent.Wlj.ViewMgr.openViewWindow(0,custId,custName,1);
};
/**
 * 查询条件域对象渲染之后触发；
 * params ：con：查询条件面板对象；
 *    app：当前APP对象；
 */
//var afterconditionrender = function() {
////	getConditionField('CUST_TYPE').setValue('1');
//	this.findField('CUST_TYPE').setValue('1');
//};

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
	}
};

var viewshow = function(view){
	if(view._defaultTitle=='设为特殊名单客户'){
		if(getSelectedData().data.APPROVAL_FLAG == "2"){//待审批，不能操作
			Ext.getCmp("speSaveId").setDisabled(true);
			Ext.getCmp("speAuditId").setDisabled(true);
		}else if(getSelectedData().data.APPROVAL_FLAG == ""){//属于新增
			Ext.getCmp("speAuditId").setDisabled(true);
			Ext.getCmp("speSaveId").setDisabled(false);
		}else{
			Ext.getCmp("speSaveId").setDisabled(false);
			Ext.getCmp("speAuditId").setDisabled(false);
		}
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