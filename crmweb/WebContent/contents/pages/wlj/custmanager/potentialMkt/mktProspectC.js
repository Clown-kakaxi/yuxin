/**
 * @description 企商金客户营销流程 -  prospect信息页面
 * @author luyy
 * @since 2014-07-25
 * @modify dongyi 2014-11-28
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldQZ.js'	//客户放大镜（企商金营销用）
	,'/contents/pages/common/Com.yucheng.crm.common.DeptQuery.js'
	,'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);

var url = basepath + '/mktprospectC.json';

var lookupTypes = ['IF_FLAG','CUST_SOURCE','CHECK_STAT','VISIT_TYPE','RUFUSE_REASON_PROSPECT_C','IF_PIPELINE',
	{
	TYPE : 'AREA',//区域中心数据字典
	url : '/mktprospectC!searchArea.json',
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


var ifPipeline=0;//此标志用于判断是否需要把信息录入pipeline;值为1则需要
var fields = [
              {name:'IF_PIPELINE',text:'是否转入PIPELINE',translateType:'IF_PIPELINE',allowBlank:false,dataType:'string'},
			  {name:'ID',text:'id',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerqueryqz',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:"CUST_SOURCE",text:"客户来源",translateType:"CUST_SOURCE",allowBlank:false,searchField: true,editable:true},
              {name:"CUST_SOURCE_DATE",text:"客户来源日期",dataType:'date',allowBlank:false},
              {name:'AREA_NAME',text:'区域中心',translateType:"AREA",
	              resutlWidth:80,searchField: true, showField:'text',allowBlank:false,valueField:"value",listeners:{
				  select:function(a,b){
				  	a.setValue(b.data.value);
				}
				}},
              {name:'DEPT_NAME',text:'营业单位名称',xtype:'orgchoose',hiddenName:'DEPT_ID',allowBlank:false,searchField: true},
              {name:"CHECK_STAT",text:"复核状态",translateType:"CHECK_STAT",gridField:false},
              {name:'RM',text:'RM',dataType:'string',gridField:true},
              {name:'IF_TRANS_CUST',text:'若无法取得财务资料,能否转为我行存款户',translateType:'IF_FLAG',dataType:'string',gridField:true},
              {name:'IF_FILES',text:'是否能取得财务资料',dataType:'string',translateType:'IF_FLAG',gridField:true},
              {name:'LINK_MAN',text:'联系人',dataType:'string',gridField:true},
              {name:'LINK_PHONE',text:'联系电话',vtype:'phoneNumber',gridField:true},
              {name:'VISIT_DATE',text:'约访日期',dataType:'string',gridField:true},
              {name:'VISIT_WAY',text:'约访方式',translateType:'VISIT_TYPE',dataType:'string',gridField:true},             
              {name:'CUST_ID',dataType:'string',gridField:true},
              {name:'GROUP_NAME',dataType:'string',gridField:true},
              {name:'CUST_SOURCE_INFO',text:'客户来源说明',xtype:'textarea',gridField:true,maxLength:200},
              {name:'PRODUCT_NEED',text:'客户产品需求',xtype:'textarea',gridField:true,maxLength:200},
              {name:'RUFUSE_REASON',text:'拒绝理由',xtype:'string',translateType:'RUFUSE_REASON_PROSPECT_C',gridField:true,editable:true},
              {name:'REASON_REMARK',text:'拒绝原因说明',xtype:'textarea',gridField:true,maxLength:500},
              {name:'RECORD_DATE',text:'首次进入本阶段日期',dataType:'string',gridField:true},//新增
              {name:'TREAMENT_DAYS',text:'本阶段案件处理天数',dataType:'string',gridField:true},//新增
              {name:'AREA_ID'},
              {name:'DEPT_ID'}];

              
              
var tbar=[{
	text:'删除',
	hidden:JsContext.checkGrant('mktProspectCDelet'),
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			   Ext.Ajax.request({
					url : basepath + '/mktprospectC!batchDel.json',
					method : 'POST',
					params : {
						id : getSelectedData().data.ID
					},
					success : function() {
						Ext.Msg.alert('提示', '操作成功！');
						reloadCurrentData();
						
					},failure : function(response) {
						Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);}
				});
			});
	}
},
	new Com.yucheng.crm.common.NewExpButton({
        formPanel : 'searchCondition',
        url :  basepath + '/mktprospectC.json'
    })]              
var customerView = [{
	title:'新增',
	type:'form',
	hideTitle:JsContext.checkGrant('mktProspectCAdd'),
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[
			{name:'IF_PIPELINE',text:'是否转入PIPELINE',translateType:'IF_PIPELINE',allowBlank:false,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_PIPELINE").getValue();
					if(value == '0'){
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").show();
//						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").show();
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").allowBlank = false;
//						getCurrentView().contentPanel.form.findField("REASON_REMARK").show();
					}
					else{
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").hide();
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").setValue('');
//						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").hide();
//						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").setValue('');
						getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
						getCurrentView().contentPanel.form.findField("REASON_REMARK").setValue('');
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").allowBlank = true;
						getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = true;
					}
				}}},
			{name:'CUST_NAME',text:'客户名称',xtype:'customerqueryqz',hiddenName:'CUST_ID',allowBlank:false,custType:'1',
				singleSelected:true,newCust:true,callback:function(a){
					getCurrentView().contentPanel.form.findField("LINK_MAN").setValue(this.linkUser);}},
			{name:'AREA_NAME',text:'区域中心',allowBlank:false},
			{name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			{name:'RM',text:'RM',resutlWidth:150},
			'CUST_SOURCE','CUST_SOURCE_DATE',
			{name:'IF_FILES',text:'是否能取得财务资料',translateType:'IF_FLAG',allowBlank:false,editable:true,listeners:{
				select:function(){
					var flag=this.value;
					if(flag==0){
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").show();
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank=false;
					}else{
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").hide();
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").setValue('');
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank=true;
					}
				}
			}},
			{name:'IF_TRANS_CUST',text:'<font color="red">*</font>若无法取得财务资料,能否转为我行存款户',translateType:'IF_FLAG',hidden:true},
			{name:'LINK_MAN',text:'联系人',maxLength:100},
			{name:'LINK_PHONE',text:'联系电话',maxLength:32,vtype:'phoneNumber'},
			{name:'VISIT_DATE',text:'约访日期',dataType:'date'},
			{name:'VISIT_WAY',text:'约访方式',translateType:'VISIT_TYPE'},
			{name:'RUFUSE_REASON',text:'<font color="red">*</font>拒绝理由',translateType:'RUFUSE_REASON_PROSPECT_C',editable:true,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("RUFUSE_REASON").getValue();
					if(value == '3'){
						getCurrentView().contentPanel.form.findField("REASON_REMARK").show();
						getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = false;
					}
					else{
						getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
						getCurrentView().contentPanel.form.findField("REASON_REMARK").setValue('');
						getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = true;
					}
				}}},
			{name:'CUST_ID',text:'客户编号'},
			'AREA_ID',
			'DEPT_ID',
			{name:'RM_ID',text:'RM编号'}],
		fn :function(IF_PIPELINE,CUST_NAME,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,IF_FILES,IF_TRANS_CUST,
				LINK_MAN,LINK_PHONE,VISIT_DATE,VISIT_WAY,RUFUSE_REASON,CUST_ID,AREA_ID,DEPT_ID,RM_ID){
			
			AREA_NAME.readOnly = true;
			AREA_NAME.cls = 'x-readOnly';
			DEPT_NAME.readOnly = true;
			DEPT_NAME.cls = 'x-readOnly';
			RM.readOnly = true;
			RM.cls = 'x-readOnly';		
			CUST_ID.readOnly = true;
			CUST_ID.cls = 'x-readOnly';
			CUST_ID.hidden=true;
			AREA_ID.hidden=true;
			AREA_ID.readOnly = true;
			AREA_ID.cls = 'x-readOnly';
			DEPT_ID.hidden=true;
			RM_ID.hidden=true;
			RUFUSE_REASON.hidden=true;
			return [IF_PIPELINE,CUST_NAME,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,IF_FILES,IF_TRANS_CUST,
					LINK_MAN,LINK_PHONE,VISIT_DATE,VISIT_WAY,RUFUSE_REASON,CUST_ID,AREA_ID,DEPT_ID,RM_ID];
		}
	},{
	  columnCount: 1,
	  fields : [{name:'CUST_SOURCE_INFO',text:'客户来源说明',xtype:'textarea',maxLength:200},
	            {name:'PRODUCT_NEED',text:'客户产品需求',xtype:'textarea',maxLength:200},
	            {name:'REASON_REMARK',text:'<font color="red">*</font>拒绝原因说明',xtype:'textarea',maxLength:500}
	            ],
	  fn : function(CUST_SOURCE_INFO,PRODUCT_NEED,REASON_REMARK){
	  	REASON_REMARK.hidden = true;
		  return [CUST_SOURCE_INFO,PRODUCT_NEED,REASON_REMARK];
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
	         if(value=='1'){
	        	 if(formPanel.form.findField("IF_FILES").getValue() == '0'){
	        		   Ext.MessageBox.alert('提示','未取得财务资料，不能进入下一阶段');
		               return false;
	        	   }
	         }
				var data = formPanel.getForm().getFieldValues();
				var commintData = translateDataKey(data,1);
				ifPipeline=commintData.ifPipeline;
				Ext.Msg.wait('正在处理，请稍后......','系统提示');
				Ext.Ajax.request({
						url : basepath + '/mktprospectC!save.json',
						method : 'GET',
						params : commintData,
						success : function(response) {
							if(ifPipeline=='1'){
								var ret = Ext.decode(response.responseText);
								Ext.Ajax.request({
									url : basepath + '/mktIntentC!save.json',//如果要转入pipeline阶段，把数据保存进合作意向表内
									method : 'GET',
									params : ret,
									success : function(response) {
										var ret = Ext.decode(response.responseText);
										Ext.MessageBox.alert('提示','保存数据成功,请在合作意向阶段查看数据!');
										hideCurrentView(); 
										reloadCurrentData();
									}
								})
							}else {
									Ext.MessageBox.alert('提示','保存数据成功!');
									hideCurrentView(); 
									reloadCurrentData();
								}
							}
						}); 
	        	   
		}
	}]
},{	
	title:'修改',
	hideTitle:JsContext.checkGrant('mktProspectCEdit'),
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[
			{name:'IF_PIPELINE',text:'是否转入PIPELINE',translateType:'IF_PIPELINE',allowBlank:false,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_PIPELINE").getValue();
					if(value == '0'){
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").show();
//						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").show();
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").allowBlank = false;
					}
					else{
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").hide();
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").setValue('');
//						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").hide();
//						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").setValue('');
						getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
						getCurrentView().contentPanel.form.findField("REASON_REMARK").setValue('');
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").allowBlank = true;
						getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = true;
					}
					if(value == '2'){
						getCurrentView().contentPanel.form.findField("CUST_SOURCE").allowBlank = true;
						getCurrentView().contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = true;
						getCurrentView().contentPanel.form.findField("IF_FILES").allowBlank = true;
					}else{
						getCurrentView().contentPanel.form.findField("CUST_SOURCE").allowBlank = false;
						getCurrentView().contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = false;
						getCurrentView().contentPanel.form.findField("IF_FILES").allowBlank = false;
					}
				}}},
			{name:'CUST_NAME',text:'客户名称',allowBlank:false},
			{name:'AREA_NAME',text:'区域中心',allowBlank:false},
			{name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			{name:'RM',text:'RM',singleSelect: true},
//			'CUST_SOURCE','CUST_SOURCE_DATE',
			{name:"CUST_SOURCE",text:"客户来源",translateType:"CUST_SOURCE",allowBlank:false,searchField: true,editable:true},
            {name:"CUST_SOURCE_DATE",text:"客户来源日期",dataType:'date',allowBlank:false},
			{name:'IF_FILES',text:'是否能取得财务资料',translateType:'IF_FLAG',allowBlank:false,editable:true,listeners:{
				select:function(){
					var flag=this.value;
					if(flag =='0'){
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").show();
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank=false;
					}else{
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").hide();
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank=true;
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").setValue('');
					}
				}
			}},
			{name:'IF_TRANS_CUST',text:'<font color="red">*</font>若无法取得财务资料,能否转为我行存款户',translateType:'IF_FLAG'},
			{name:'LINK_MAN',text:'联系人',maxLength:100},
			{name:'LINK_PHONE',text:'联系电话',maxLength:32,vtype:'phoneNumber'},
			{name:'VISIT_DATE',text:'约访日期',dataType:'date'},
			{name:'VISIT_WAY',text:'约访方式',translateType:'VISIT_TYPE'},
			'TREAMENT_DAYS','RECORD_DATE',	//新增		          
			{name:'RUFUSE_REASON',text:'<font color="red">*</font>拒绝理由',translateType:'RUFUSE_REASON_PROSPECT_C',editable:true,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("RUFUSE_REASON").getValue();
					if(value == '3'){
						getCurrentView().contentPanel.form.findField("REASON_REMARK").show();
						getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = false;
					}
					else{
						getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
						getCurrentView().contentPanel.form.findField("REASON_REMARK").setValue('');
						getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = true;
					}
				}}},				
			{name:'CUST_ID',text:'客户编号'},
			{name:'AREA_ID'},
			{name:'DEPT_ID'},			
			{name:'RM_ID',text:'RM编号'},
			{name:'ID',text:'id'},
			{name:'PIPELINE_ID',text:'pipeline_id'}],
		fn :function(IF_PIPELINE,CUST_NAME,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,IF_FILES,IF_TRANS_CUST,
				LINK_MAN,LINK_PHONE,VISIT_DATE,VISIT_WAY,TREAMENT_DAYS,RECORD_DATE,RUFUSE_REASON,CUST_ID,AREA_ID,DEPT_ID,RM_ID,ID,PIPELINE_ID){
			CUST_NAME.readOnly = true;
			CUST_NAME.cls = 'x-readOnly';
			AREA_NAME.readOnly = true;
			AREA_NAME.cls = 'x-readOnly';
			RECORD_DATE.readOnly = true;
			RECORD_DATE.cls = 'x-readOnly';
			DEPT_NAME.readOnly = true;
			DEPT_NAME.cls = 'x-readOnly';
			TREAMENT_DAYS.readOnly = true;
			TREAMENT_DAYS.cls = 'x-readOnly';
			RM.readOnly = true;
			RM.cls = 'x-readOnly';
			CUST_ID.readOnly = true;
			CUST_ID.cls = 'x-readOnly';
			CUST_ID.hidden=true;
			AREA_ID.hidden=true;
			AREA_ID.readOnly = true;
			AREA_ID.cls = 'x-readOnly';		
			DEPT_ID.hidden=true;
			RM_ID.hidden=true;
			ID.hidden=true;
			PIPELINE_ID.hidden=true;
			RUFUSE_REASON.hidden = true;
			return [IF_PIPELINE,CUST_NAME,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,IF_FILES,IF_TRANS_CUST,
					LINK_MAN,LINK_PHONE,VISIT_DATE,VISIT_WAY,TREAMENT_DAYS,RECORD_DATE,RUFUSE_REASON,CUST_ID,AREA_ID,DEPT_ID,RM_ID,ID,PIPELINE_ID];
		}
	},{
	  columnCount: 1,
	  fields : [{name:'CUST_SOURCE_INFO',text:'客户来源说明',xtype:'textarea',maxLength:200},
	            {name:'PRODUCT_NEED',text:'客户产品需求',xtype:'textarea',maxLength:200},
	            {name:'REASON_REMARK',text:'<font color="red">*</font>拒绝原因说明',xtype:'textarea',maxLength:500}],
	  fn : function(CUST_SOURCE_INFO,PRODUCT_NEED,REASON_REMARK){
	  	  REASON_REMARK.hidden = true;
		  return [CUST_SOURCE_INFO,PRODUCT_NEED,REASON_REMARK];
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
	         if(value=='1'){
	        	 if(formPanel.form.findField("IF_FILES").getValue() == '0'){
	        		   Ext.MessageBox.alert('提示','未取得财务资料，不能进入下一阶段');
		               return false;
	        	   }
	         }
	         var data = formPanel.getForm().getFieldValues();
				var commintData = translateDataKey(data,1);
				ifPipeline=commintData.ifPipeline;
				Ext.Msg.wait('正在处理，请稍后......','系统提示');
				Ext.Ajax.request({
						url : basepath + '/mktprospectC!save.json',
						method : 'GET',
						params : commintData,
						success : function(response) {
							
							if(ifPipeline=='1'){
								var ret = Ext.decode(response.responseText);
								Ext.Ajax.request({
						url : basepath + '/mktIntentC!save.json',
						method : 'GET',
						params : ret,
						success : function(response) {
							var ret = Ext.decode(response.responseText);
								Ext.MessageBox.alert('提示','保存数据成功,请在合作意向阶段查看数据!');
								hideCurrentView(); 
								reloadCurrentData();
						}
								})
								
							}
								Ext.MessageBox.alert('提示','保存数据成功!');
								hideCurrentView();
								reloadCurrentData();
//								 var ret = Ext.decode(response.responseText);
//									var instanceid = ret.instanceid;//流程实例ID
//									var currNode = ret.currNode;//当前节点
//									var nextNode = ret.nextNode;//下一步节点
//									selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
							}
						}); 
	        	   
		}
	}]

},{
	title:'详情',
	hideTitle:JsContext.checkGrant('mktProspectCDetil'),
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[
			{name:'IF_PIPELINE',text:'是否转入PIPELINE',translateType:'IF_PIPELINE',allowBlank:false,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_PIPELINE").getValue();
					if(value == '0'){
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").show();
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").show();
						getCurrentView().contentPanel.form.findField("REASON_REMARK").show();
					}
					else{
						getCurrentView().contentPanel.form.findField("RUFUSE_REASON").hide();
						getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").hide();
						getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
					}
				}}},
			{name:'CUST_NAME',text:'客户名称',allowBlank:false},
			{name:'AREA_NAME',text:'区域中心',allowBlank:false},
			{name:'DEPT_NAME',text:'营业部门',xtype:'deptChose',hiddenName:'DEPT_ID',allowBlank:false},
			{name:'RM',text:'RM',singleSelect: true},
			{name:'CUST_SOURCE',text:'客户来源',translateType:'CUST_SOURCE'},
			{name:'CUST_SOURCE_DATE',text:'客户来源日期'},
			{name:'IF_FILES',text:'是否能取得财务资料',translateType:'IF_FLAG'},
			{name:'IF_TRANS_CUST',text:'<font color="red">*</font>若无法取得产品需求能否转为我行存款户',translateType:'IF_FLAG'},
			{name:'LINK_MAN',text:'联系人'},
			{name:'LINK_PHONE',text:'联系电话'},
			{name:'VISIT_DATE',text:'约访日期',dataType:'date'},
			{name:'VISIT_WAY',text:'约访方式',translateType:'VISIT_TYPE'},
			'TREAMENT_DAYS','RECORD_DATE',//新增
			{name:'RUFUSE_REASON',text:'<font color="red">*</font>拒绝理由',translateType:'RUFUSE_REASON_PROSPECT_C',listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("RUFUSE_REASON").getValue();
					if(value == '3'){
						getCurrentView().contentPanel.form.findField("REASON_REMARK").show();
					}
					else{
						getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
					}
				}}},
			{name:'CUST_ID',text:'客户编号'},
			{name:'AREA_ID'},
			{name:'RM_ID',text:'RM编号'},
			{name:'ID',text:'id'},
			{name:'PIPELINE_ID',text:'pipeline_id'}],
		fn :function(IF_PIPELINE,CUST_NAME,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,IF_FILES,IF_TRANS_CUST,
				LINK_MAN,LINK_PHONE,VISIT_DATE,VISIT_WAY,TREAMENT_DAYS,RECORD_DATE,RUFUSE_REASON,CUST_ID,AREA_ID,RM_ID,ID,PIPELINE_ID){
			CUST_NAME.readOnly = true;
			CUST_NAME.cls = 'x-readOnly';
			AREA_NAME.readOnly = true;
			AREA_NAME.cls = 'x-readOnly';
			DEPT_NAME.readOnly = true;
			DEPT_NAME.cls = 'x-readOnly';
			RM.readOnly = true;
			RM.cls = 'x-readOnly';
			CUST_SOURCE.readOnly = true;
			CUST_SOURCE.cls = 'x-readOnly';
			CUST_SOURCE_DATE.readOnly = true;
			CUST_SOURCE_DATE.cls = 'x-readOnly';
			IF_FILES.readOnly = true;
			IF_FILES.cls = 'x-readOnly';
			IF_TRANS_CUST.readOnly = true;
			IF_TRANS_CUST.cls = 'x-readOnly';
			IF_PIPELINE.readOnly = true;
			IF_PIPELINE.cls = 'x-readOnly';
			LINK_MAN.readOnly = true;
			LINK_MAN.cls = 'x-readOnly';
			LINK_PHONE.readOnly = true;
			LINK_PHONE.cls = 'x-readOnly';
			VISIT_DATE.readOnly = true;
			VISIT_DATE.cls = 'x-readOnly';
			TREAMENT_DAYS.readOnly = true;
			TREAMENT_DAYS.cls = 'x-readOnly';
			RECORD_DATE.readOnly = true;
			RECORD_DATE.cls = 'x-readOnly';			
			RUFUSE_REASON.readOnly = true;
			RUFUSE_REASON.cls = 'x-readOnly';
			VISIT_WAY.readOnly = true;
			VISIT_WAY.cls = 'x-readOnly';
			CUST_ID.readOnly = true;
			CUST_ID.cls = 'x-readOnly';
			CUST_ID.hidden=true;
			AREA_ID.hidden=true;
			AREA_ID.readOnly = true;
			AREA_ID.cls = 'x-readOnly';
			RM_ID.hidden=true;
			ID.hidden=true;
			PIPELINE_ID.hidden=true;
			return [IF_PIPELINE,CUST_NAME,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,IF_FILES,IF_TRANS_CUST,
					LINK_MAN,LINK_PHONE,VISIT_DATE,VISIT_WAY,TREAMENT_DAYS,RECORD_DATE,RUFUSE_REASON,CUST_ID,AREA_ID,RM_ID,ID,PIPELINE_ID];
		}
	},{
	  columnCount: 1,
	  fields : [{name:'CUST_SOURCE_INFO',text:'客户来源说明',xtype:'textarea'},
	            {name:'PRODUCT_NEED',text:'客户产品需求',xtype:'textarea'},
	            {name:'REASON_REMARK',text:'<font color="red">*</font>拒绝原因说明',xtype:'textarea',maxLength:500}],
	  fn : function(CUST_SOURCE_INFO,PRODUCT_NEED,REASON_REMARK){
	  		CUST_SOURCE_INFO.readOnly = true;
			CUST_SOURCE_INFO.cls = 'x-readOnly';
			PRODUCT_NEED.readOnly = true;
			PRODUCT_NEED.cls = 'x-readOnly';
			REASON_REMARK.readOnly = true;
			REASON_REMARK.cls = 'x-readOnly';
		  return [CUST_SOURCE_INFO,PRODUCT_NEED,REASON_REMARK];
	  }
	}]

}];

var beforeviewshow = function(view){
	if(view._defaultTitle == '修改' || view._defaultTitle == '详情'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	}
	if(view._defaultTitle == '详情'){
		if(getSelectedData().data.IF_PIPELINE == '2'){
			view.contentPanel.form.findField("REASON_REMARK").hide();
		}
	}
	if(view._defaultTitle == '修改'||view._defaultTitle == '详情'){
		store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktprospectC.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_NAME'},
		            {name:'AREA_NAME'},
		            {name:'DEPT_NAME'},
		            {name:'RM'},
		            {name:'CUST_SOURCE'},
		            {name:'CUST_SOURCE_DATE'},
		            {name:'IF_FILES'},
		            {name:'IF_TRANS_CUST'},
		            {name:'IF_PIPELINE'},
		            {name:'LINK_MAN'},
		            {name:'LINK_PHONE'},
		            {name:'VISIT_DATE'},
		            {name:'RECORD_DATE'},
		            {name:'TREAMENT_DAYS'},
		            {name:'VISIT_WAY'},
		            {name:'CUST_ID'},
		            {name:'AREA_ID'},
		            {name:'DEPT_ID'},
		            {name:'RM_ID'},
		            {name:'ID'},
		            {name:'PIPELINE_ID'},
		            {name:'CUST_SOURCE_INFO'},
		            {name:'PRODUCT_NEED'},
		            {name:'RUFUSE_REASON'},
		            {name:'REASON_REMARK'}]
			)
			});
		store.load({params : {
			id:getSelectedData().data.ID
        },
        callback:function(){
        	if(store.getCount()!=0){
        		if(store.getAt(0).data.IF_PIPELINE == '0'){
					getCurrentView().contentPanel.form.findField("RUFUSE_REASON").show();
					//getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").show();
					getCurrentView().contentPanel.form.findField("CUST_SOURCE").allowBlank = false;
					getCurrentView().contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = false;
					//getCurrentView().contentPanel.form.findField("IF_FILES").allowBlank = false;
				}else if(store.getAt(0).data.IF_PIPELINE == '2'){
					getCurrentView().contentPanel.form.findField("RUFUSE_REASON").hide();
					//getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").hide();
					getCurrentView().contentPanel.form.findField("RUFUSE_REASON").setValue('');
					//getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").setValue('');
					getCurrentView().contentPanel.form.findField("CUST_SOURCE").allowBlank = true;
					getCurrentView().contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = true;
					getCurrentView().contentPanel.form.findField("IF_FILES").allowBlank = true;
				} 
        		if(store.getAt(0).data.IF_FILES =='0' ){
					getCurrentView().contentPanel.form.findField("IF_FILES").allowBlank = false;
					getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").show();
					getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank = false;
				}else if(store.getAt(0).data.IF_FILES =='1' ){
					getCurrentView().contentPanel.form.findField("IF_FILES").allowBlank = false;
					getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").hide();
					getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank = true;
					getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").setValue('');
					
				}
        		view.contentPanel.getForm().loadRecord(store.getAt(0));
        	}
		}});
};
}
var viewshow = function(view){
	if(view._defaultTitle == '新增'){
		//进行返现RM，营业单位
		view.contentPanel.form.findField("RM_ID").setValue(__userId);
		view.contentPanel.form.findField("RM").setValue(__userName);
		view.contentPanel.form.findField("DEPT_NAME").setValue(__unitname);
		view.contentPanel.form.findField("DEPT_ID").setValue(__units);
		

		//通过营业单位查询出区域中心并返现
		Ext.Ajax.request({
			url :basepath +'/mktprospectC!searchAreaBack.json',
			method : 'GET',
			params : {deptId:__units},
			success : function(response) {
				 var ret = Ext.decode(response.responseText);
				 if(ret.data[0] != null && ret.data[0] != null){
					 view.contentPanel.form.findField("AREA_ID").setValue(ret.data[0].AREA_ID);
					 view.contentPanel.form.findField("AREA_NAME").setValue(ret.data[0].AREA_NAME);
				 }
			}
		})
		view.contentPanel.form.findField("IF_PIPELINE").setValue('2');
		getCurrentView().contentPanel.form.findField("CUST_SOURCE").allowBlank = true;
		getCurrentView().contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = true;
		getCurrentView().contentPanel.form.findField("IF_FILES").allowBlank = true;
		getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").hide();
	}
};