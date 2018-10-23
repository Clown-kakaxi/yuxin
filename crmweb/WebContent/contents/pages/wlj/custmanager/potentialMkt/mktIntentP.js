/**
 * @description 个金客户营销流程 - 合作意向信息页面
 * @author luyy
 * @since 2014-07-23
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


var url = basepath + '/mktIntentP.json';

var lookupTypes = ['IF_FLAG','CHECK_STAT','CASE_TYPE','RISK_CHARACT'];

var fields = [{name:'ID',text:'ID',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerquery',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'AREA_NAME',text:'区域中心',xtype : 'wcombotree',innerTree:'ORGTREE',
              resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',allowBlank:false},
              {name:'DEPT_NAME',text:'营业单位名称',dataType:'string',searchField: true},
			  {name:"CHECK_STAT",text:"复核状态",translateType:"CHECK_STAT",allowBlank:false},
			  {name:"CASE_TYPE",text:"案件类型",translateType:"CASE_TYPE",searchField: true,allowBlank:false},
			  {name:'VISIT_DATE',text:'第一次拜访日期',dataType:'date'},
			  {name:"IF_SECOND_STEP",text:"是否进入第二阶段",translateType:"IF_FLAG",allowBlank:false},
              {name:"CHECK_STAT",text:"复核状态",translateType:"CHECK_STAT"},
              {name:'USER_ID',text:'USER_ID',gridField:false},
              {name:'USER_ID',text:'USER_ID',gridField:false},
              {name:'PRODUCT_ID',text:'PRODUCT_ID',gridField:false},
              {name:'RM',text:'RM',dataType:'string',gridField:false},
              {name:'PRODUCT_NAME',text:'意向产品',gridField:false},
              {name:'RISK_LEVEL_PERSECT',text:'客户风险预估',translateType:'RISK_CHARACT',allowBlank:false},
              {name:'PRODUCT_NEED',text:'客户产品需求',xtype:'textarea',gridField:false},
	          {name:'HARD_INFO',text:'营销难点',xtype:'textarea',gridField:false}
			  ];

var tbar = [{
	text:'删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}

		var ID = '';
		for (var i=0;i<getAllSelects().length;i++){
			if(getAllSelects()[i].data.USER_ID != __userId || getAllSelects()[i].data.CHECK_STAT != '1'){
				Ext.Msg.alert('提示','只能选择本人[草稿]状态的拜访信息！');
				return false;
			}
			ID += getAllSelects()[i].data.ID;
			ID += ",";
		}
		ID = ID.substring(0, ID.length-1);
		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
			return false;
			} 
		    Ext.Ajax.request({
                url: basepath+'/mktIntentP!batchDel.json?idStr='+ID,                                
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
}];

var customerView = [{
	title:'修改',
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount:2,
		fields :['ID','CUST_NAME','AREA_NAME','DEPT_NAME','RM',
			{name:'PRODUCT_NAME',dataType:'productChoose',hiddenName:'PRODUCT_ID',singleSelect:true,text:"意向产品",allowBlank:false}
			,'VISIT_DATE','RISK_LEVEL_PERSECT','CASE_TYPE','IF_SECOND_STEP'],
			fn:function(ID,CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,VISIT_DATE,RISK_LEVEL_PERSECT,CASE_TYPE,IF_SECOND_STEP){
				ID.hidden = true;
				CUST_NAME.readOnly = true;
				AREA_NAME.readOnly = true;
				DEPT_NAME.readOnly = true;
				CUST_NAME.cls = 'x-readOnly';
				AREA_NAME.cls = 'x-readOnly';
				DEPT_NAME.cls = 'x-readOnly';
				RM.readOnly = true;
				RM.cls = 'x-readOnly';
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,VISIT_DATE,RISK_LEVEL_PERSECT,CASE_TYPE,IF_SECOND_STEP,ID];
			}
	},{
		  columnCount: 1,
		  fields : [{name:'PRODUCT_NEED',text:'客户产品需求',xtype:'textarea'},
		            {name:'HARD_INFO',text:'营销难点',xtype:'textarea'}],
		  fn : function(PRODUCT_NEED,HARD_INFO){
			  return [PRODUCT_NEED,HARD_INFO];
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
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/mktIntentP!save.json',
							method : 'GET',
							params : commintData,
							success : function(response) {
									 var ret = Ext.decode(response.responseText);
										var instanceid = ret.instanceid;//流程实例ID
										var currNode = ret.currNode;//当前节点
										var nextNode = ret.nextNode;//下一步节点
										selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
								}
							}); 
		        	   
			}
		}]
},{
	title:'查看',
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount:2,
		fields :['CUST_NAME','AREA_NAME','DEPT_NAME','RM',
				'PRODUCT_NAME','VISIT_DATE','RISK_LEVEL_PERSECT','CASE_TYPE','IF_SECOND_STEP'],
		fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,VISIT_DATE,RISK_LEVEL_PERSECT,CASE_TYPE,IF_SECOND_STEP){
			return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,PRODUCT_NAME,VISIT_DATE,RISK_LEVEL_PERSECT,CASE_TYPE,IF_SECOND_STEP];
		}
	},{
		  columnCount: 1,
		  fields : [{name:'PRODUCT_NEED',text:'客户产品需求',xtype:'textarea'},
		            {name:'HARD_INFO',text:'营销难点',xtype:'textarea'}],
		  fn : function(PRODUCT_NEED,HARD_INFO){
			  return [PRODUCT_NEED,HARD_INFO];
		  }
		}]
}];

var beforeviewshow = function(view){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
	}
	if(view._defaultTitle == '修改'){
		if(getSelectedData().data.CHECK_STAT != '1'){
			Ext.Msg.alert('提示','只能修改[草稿]状态的信息');
			return false;
		}
	}
};
