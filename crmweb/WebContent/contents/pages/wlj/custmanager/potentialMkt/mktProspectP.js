/**
 * @description 个金客户营销流程 -  prospect信息页面
 * @author luyy
 * @since 2014-07-23
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.DeptQuery.js'
]);

var url = basepath + '/mktprospectP.json';

var lookupTypes = ['IF_FLAG','CUST_SOURCE','CHECK_STAT','VISIT_TYPE',{
	TYPE : 'CALL_CHOSE',//关联的电访记录
	url : '/mktprospectP!searchCalls.json',
	key : 'KEY',
	value : 'VALUE',
	root : 'data'
}];

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

var fields = [{name:'ID',text:'id',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerquery',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:"CUST_SOURCE",text:"客户来源",translateType:"CUST_SOURCE",allowBlank:false,searchField: true},
              {name:"CUST_SOURCE_DATE",text:"客户来源日期",dataType:'date',allowBlank:false},
              {name:'AREA_NAME',text:'区域中心',xtype : 'wcombotree',innerTree:'ORGTREE',
              resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',allowBlank:false},
              {name:'DEPT_NAME',text:'营业单位名称',dataType:'string',searchField: true},
              {name:"CHECK_STAT",text:"复核状态",translateType:"CHECK_STAT"},
              {name:'RM',dataType:'string',gridField:false},
              {name:'IF_TRANS_CUST',dataType:'string',gridField:false},
              {name:'IF_PIPELINE',dataType:'string',gridField:false},
              {name:'LINK_MAN',dataType:'string',gridField:false},
              {name:'LINK_PHONE',dataType:'string',gridField:false},
              {name:'VISIT_DATE',dataType:'string',gridField:false},
              {name:'VISIT_WAY',dataType:'string',gridField:false},
              {name:'CUST_ID',dataType:'string',gridField:false},
              {name:'CUST_SOURCE_INFO',text:'客户来源说明',dataType:'string',gridField:false},
              {name:'PRODUCT_NEED',text:'客户产品需求',dataType:'string',gridField:false},
              {name:'RUFUSE_REASON',text:'拒绝理由',dataType:'string',gridField:false}];

var customerView = [{
	title:'新增',
	type:'form',
	groups:[{
		columnCount : 2,
		fields:[{name:'CALL_ID',text:'关联电访记录',translateType:'CALL_CHOSE',listeners:{
			select:function(){
				var value = getCurrentView().contentPanel.form.findField("CALL_ID").getValue();
				maskRegion('resultDomain','正在查询电访信息，请稍后');
				//查询信息并反显
				Ext.Ajax.request({
					url : basepath + '/mktprospectP!getInfo.json',
					params : {
					'callId':value
					},
					success : function(response) {
						var info =  response.responseText;
						infoList = info.split('#');
						if(infoList.length == 6)
							getCurrentView().setValues({
								CUST_ID: infoList[0]=='null'?'':infoList[0],
								CUST_NAME: infoList[1]=='null'?'':infoList[1],
								CUST_SOURCE: infoList[2]=='null'?'':infoList[2],
								LINK_PHONE:infoList[3]=='null'?'':infoList[3],
								AREA_ID: infoList[4]=='null'?'':infoList[4],
								AREA_NAME: infoList[5]=='null'?'':infoList[5]
								
							});
						unmaskRegion('resultDomain');
					},
					failure : function(response) {
						Ext.Msg.alert('提示', '电访信息');
						unmaskRegion('resultDomain');
					}
				});
			}}},
			{name:'CUST_ID',text:'客户编号'},
			{name:'CUST_NAME',text:'客户名称',allowBlank:false},
			{name:'AREA_ID'},{name:'AREA_NAME',text:'区域中心',allowBlank:false},
			{name:'DEPT_NAME',text:'营业部门',xtype:'deptChose',hiddenName:'DEPT_ID',allowBlank:false},
			{name:'RM',text:'RM',xtype:'userchoose',singleSelect: true},
			'CUST_SOURCE','CUST_SOURCE_DATE',
			{name:'IF_TRANS_CUST',text:'若无法取得产品需求能否转为我行存款户',translateType:'IF_FLAG',allowBlank:false},
			{name:'IF_PIPELINE',text:'是否转入PIPELINE',translateType:'IF_FLAG',allowBlank:false,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_PIPELINE").getValue();
					if(value == '0')
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").show();
					else
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").hide();
				}}},
			{name:'LINK_MAN',text:'联系人'},
			{name:'LINK_PHONE',text:'联系电话'},
			{name:'VISIT_DATE',text:'约访日期',dataType:'date'},
			{name:'VISIT_WAY',text:'约访方式',translateType:'VISIT_TYPE'}],
		fn :function(CALL_ID,CUST_ID,CUST_NAME,AREA_ID,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,IF_TRANS_CUST,
				IF_PIPELINE,LINK_MAN,LINK_PHONE,VISIT_DATE,VISIT_WAY){
			CUST_ID.readOnly = true;
			CUST_NAME.readOnly = true;
			AREA_ID.readOnly = true;
			AREA_NAME.readOnly = true;
			CUST_SOURCE.readOnly = true;
			CUST_ID.cls = 'x-readOnly';
			CUST_NAME.cls = 'x-readOnly';
			AREA_ID.cls = 'x-readOnly';
			AREA_NAME.cls = 'x-readOnly';
			CUST_SOURCE.cls = 'x-readOnly';
			return [CALL_ID,CUST_ID,CUST_NAME,AREA_ID,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,IF_TRANS_CUST,
					IF_PIPELINE,LINK_MAN,LINK_PHONE,VISIT_DATE,VISIT_WAY];
		}
	},{
	  columnCount: 1,
	  fields : [{name:'CUST_SOURCE_INFO',text:'客户来源说明',xtype:'textarea'},
	            {name:'PRODUCT_NEED',text:'客户产品需求',xtype:'textarea'},
	            {name:'RUFUSE_REASON',text:'拒绝理由',xtype:'textarea'}],
	  fn : function(CUST_SOURCE_INFO,PRODUCT_NEED,RUFUSE_REASON){
		  return [CUST_SOURCE_INFO,PRODUCT_NEED,RUFUSE_REASON];
	  }
	}],
	formButtons : [{
		text:'提交',
		fn : function(formPanel,basicForm){
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
	           var value = formPanel.form.findField("IF_PIPELINE").getValue();
	         if(value == '0'){
	        	   if(formPanel.form.findField("RUFUSE_REASON").getValue() == ''){
	        		   Ext.MessageBox.alert('提示','请填写[拒绝理由]');
		               return false;
	        	   }
	        }
	         var data = formPanel.getForm().getFieldValues();
				var commintData = translateDataKey(data,1);
				Ext.Msg.wait('正在处理，请稍后......','系统提示');
				Ext.Ajax.request({
						url : basepath + '/mktprospectP!save.json',
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
	groups:[{
		columnCount : 2,
		fields:[
			{name:'CUST_ID',text:'客户编号'},
			{name:'CUST_NAME',text:'客户名称'},
			{name:'AREA_NAME',text:'区域中心'},
			{name:'DEPT_NAME',text:'营业部门'},
			{name:'RM',text:'RM'},
			'CUST_SOURCE','CUST_SOURCE_DATE',
			{name:'IF_TRANS_CUST',text:'若无法取得产品需求能否转为我行存款户',translateType:'IF_FLAG'},
			{name:'IF_PIPELINE',text:'是否转入PIPELINE',translateType:'IF_FLAG'},
			{name:'LINK_MAN',text:'联系人'},
			{name:'LINK_PHONE',text:'联系电话'},
			{name:'VISIT_DATE',text:'约访日期',dataType:'date'},
			{name:'VISIT_WAY',text:'约访方式',transalteType:'VISIT_TYPE'}],
		fn :function(CUST_ID,CUST_NAME,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,IF_TRANS_CUST,
				IF_PIPELINE,LINK_MAN,LINK_PHONE,VISIT_DATE,VISIT_WAY){
					CUST_ID.readOnly=true;  
					CUST_NAME.readOnly=true;
					AREA_NAME.readOnly=true;
					DEPT_NAME.readOnly=true;
					RM.readOnly=true;       
					CUST_SOURCE.readOnly=true;
					CUST_SOURCE_DATE.readOnly=true;
					IF_TRANS_CUST.readOnly=true;
					IF_PIPELINE.readOnly=true;
					LINK_MAN.readOnly=true; 
					LINK_PHONE.readOnly=true;
					VISIT_DATE.readOnly=true;
					VISIT_WAY.readOnly=true;
					CUST_ID.cls='readOnly';
					CUST_NAME.cls='readOnly';
					AREA_NAME.cls='readOnly';
					DEPT_NAME.cls='readOnly';
					RM.cls='readOnly';
					CUST_SOURCE.cls='readOnly';
					CUST_SOURCE_DATE.cls='readOnly';
					IF_TRANS_CUST.cls='readOnly';
					IF_PIPELINE.cls='readOnly';
					LINK_MAN.cls='readOnly';
					LINK_PHONE.cls='readOnly';
					VISIT_DATE.cls='readOnly';
					VISIT_WAY.cls='readOnly';
			return [CUST_ID,CUST_NAME,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,IF_TRANS_CUST,
					IF_PIPELINE,LINK_MAN,LINK_PHONE,VISIT_DATE,VISIT_WAY];
		}
	},{
	  columnCount: 1,
	  fields : [{name:'CUST_SOURCE_INFO',text:'客户来源说明',xtype:'textarea'},
	            {name:'PRODUCT_NEED',text:'客户产品需求',xtype:'textarea'},
	            {name:'RUFUSE_REASON',text:'拒绝理由',xtype:'textarea'}],
	  fn : function(CUST_SOURCE_INFO,PRODUCT_NEED,RUFUSE_REASON){
		  return [CUST_SOURCE_INFO,PRODUCT_NEED,RUFUSE_REASON];
	  }
	}]
}];

var beforeviewshow = function(view){
	if(view._defaultTitle == '查看'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	}
};

var viewshow = function(view){
	if(view._defaultTitle == '新增'){
		getCurrentView().contentPanel.form.findField("RUFUSE_REASON").hide();
	}else if(view._defaultTitle == '查看'){
		if(getSelectedData().data.IF_PIPELINE == '0'){
			getCurrentView().contentPanel.form.findField("RUFUSE_REASON").show();
		}else if(getSelectedData().data.IF_PIPELINE == '1'){
			getCurrentView().contentPanel.form.findField("RUFUSE_REASON").hide();
		}
	}
};