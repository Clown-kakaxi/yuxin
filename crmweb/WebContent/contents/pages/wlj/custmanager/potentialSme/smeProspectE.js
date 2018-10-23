/**
 * @description 中小企客户营销流程 -  prospect信息页面
 * @author denghj
 * @since 2015-08-06
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldQZ.js'	//客户放大镜（企商金营销用）
	,'/contents/pages/common/Com.yucheng.crm.common.DeptQuery.js'
	,'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.CallReportQueryFieldSME.js'//CallReport放大镜SME
]);

var url = basepath + '/smeProspectE.json';

var lookupTypes = ['IF_FLAG','CUST_SOURCE_SME','CHECK_STAT','VISIT_TYPE','RUFUSE_REASON_PROSPECT_SME','IF_PIPELINE',
	{
	TYPE : 'AREA',//区域中心数据字典
	url : '/smeProspectE!searchArea.json',
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

var CUST_ID = '';//定义全局变量，为CallReport放大镜 传递参数
var ifPipeline=0;//此标志用于判断是否需要把信息录入pipeline;值为1则需要
var fields = [
              {name:'IF_PIPELINE',text:'是否转入PIPELINE',translateType:'IF_PIPELINE',allowBlank:false,dataType:'string',listeners:{
              		select : function(){
              			var flag = getCurrentView().contentPanel.form.findField("IF_PIPELINE").value;
              			if(flag == '0'){
              				getCurrentView().contentPanel.form.findField("CUST_SOURCE").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("VISIT_DATE").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("TRANS_DATE").allowBlank = true;
              				
              				
              				getCurrentView().contentPanel.form.findField("PIPELINE_DATE").hide();
              				getCurrentView().contentPanel.form.findField("PIPELINE_DATE").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("PIPELINE_DATE").setValue('');
              				
              				getCurrentView().contentPanel.form.findField("RUFUSE_REASON").show();
              				getCurrentView().contentPanel.form.findField("RUFUSE_REASON").allowBlank = false;
              				if(getCurrentView().contentPanel.form.findField("RUFUSE_REASON").getValue == '3'){
              					getCurrentView().contentPanel.form.findField("REASON_REMARK").show();
              					getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = false;
              				}else{
              					getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
              					getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = true;
              					getCurrentView().contentPanel.form.findField("REASON_REMARK").setValue('');
              				}
              			}else if(flag == '1'){
              				getCurrentView().contentPanel.form.findField("CUST_SOURCE").allowBlank = false;
              				getCurrentView().contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = false;
              				getCurrentView().contentPanel.form.findField("VISIT_DATE").allowBlank = false;        
              				
              				getCurrentView().contentPanel.form.findField("PIPELINE_DATE").show();
              				getCurrentView().contentPanel.form.findField("PIPELINE_DATE").allowBlank = false;
              				
              				getCurrentView().contentPanel.form.findField("RUFUSE_REASON").hide();
              				getCurrentView().contentPanel.form.findField("RUFUSE_REASON").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("RUFUSE_REASON").setValue('');
              				
              				getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
              				getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("REASON_REMARK").setValue('');
              			}else{
              				
              				getCurrentView().contentPanel.form.findField("CUST_SOURCE").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("VISIT_DATE").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("TRANS_DATE").allowBlank = true;
              				
              				getCurrentView().contentPanel.form.findField("PIPELINE_DATE").hide();
              				getCurrentView().contentPanel.form.findField("PIPELINE_DATE").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("PIPELINE_DATE").setValue('');
              				
              				getCurrentView().contentPanel.form.findField("RUFUSE_REASON").hide();
              				getCurrentView().contentPanel.form.findField("RUFUSE_REASON").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("RUFUSE_REASON").setValue('');
              				
              				getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
              				getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("REASON_REMARK").setValue('');
              			}
              		}
              }},              			  
              {name:'AREA_NAME',text:'区域中心',translateType:"AREA",
	              resutlWidth:80,searchField: true, showField:'text',allowBlank:false,valueField:"value",listeners:{
				  select:function(a,b){
				  	a.setValue(b.data.value);
				}
				}},
              {name:'DEPT_NAME',text:'营业部门',xtype:'orgchoose',hiddenName:'DEPT_ID',allowBlank:false,gridField: true},
              {name:'RM',text:'RM',dataType:'string',allowBlank:false,gridField: true},              
              {name:'CUST_NAME',text:'客户名称',xtype:'customerqueryqz',hiddenName:'CUST_ID',allowBlank:false,custType:'1',
            	  singleSelected:true,newCust:true,callback:function(a){
            		  CUST_ID = a.customerId;
            		  var CUST_NAME = getCurrentView().contentPanel.form.findField("CUST_NAME").getValue();
            		  var VISIT_DATE = getCurrentView().contentPanel.form.findField("VISIT_DATE").getValue();
            		  var CUST_SOURCE = getCurrentView().contentPanel.form.findField("CUST_SOURCE").getValue();
            		  if(CUST_NAME !='' && (VISIT_DATE != '' || CUST_SOURCE != '')){
            			  getCurrentView().contentPanel.form.findField("VISIT_DATE").setValue('');
            			  getCurrentView().contentPanel.form.findField("CUST_SOURCE").setValue('');
            		  }
            	  }
              },
              {name:'CUST_SOURCE',text:"客户来源",translateType:'CUST_SOURCE_SME',allowBlank:false,searchField: true,editable:true},
              {name:'CUST_SOURCE_INFO',text:'客户来源说明',xtype:'textarea',gridField:true,maxLength:200},
              {name:'CUST_SOURCE_DATE',text:'客户来源日期',dataType:'date',allowBlank:false},
              {name:'VISIT_DATE',text:'拜访日期',xtype:'callreportquerysme',hiddenName:'CUST_ID',allowBlank:false, 
            	  singleSelected:true,listeners:{
            		  focus : function(){
            			  if(CUST_ID == ''){
            				  Ext.Msg.alert("提示","请先选择客户！");
            				  return false;
            			  }
            		  }
            	  },callback:function(a){
            		  var visitType = a.visitType;
            		  if(visitType =='1' || visitType =='3' || visitType =='4' || visitType =='5'){
            			  
            		  }else{
            			  getCurrentView().contentPanel.form.findField("CUST_SOURCE").setValue(a.resCustSource);
            		  }
    		  }},
    		  {name:'PRODUCT_NEED',text:'客户产品需求',xtype:'textarea',gridField:true,maxLength:200},
              {name:'IF_FILES',text:'是否能取得财务资料',dataType:'string',translateType:'IF_FLAG',allowBlank:false,gridField:true,listeners:{
              		select : function(){
              			var flag = getCurrentView().contentPanel.form.findField("IF_FILES").value;
              			if(flag == '0'){
              				getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").show();
              				getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank = false;
              			}else{
              				getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").hide();
              				getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").setValue('0');
              				
              				getCurrentView().contentPanel.form.findField("TRANS_DATE").hide();
              				getCurrentView().contentPanel.form.findField("TRANS_DATE").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("TRANS_DATE").setValue('');
              			}
              		}
              }},
              {name:'IF_TRANS_CUST',text:'若无法取得财务资料,能否转为我行存款户',translateType:'IF_FLAG',dataType:'string',gridField:true,listeners:{
              		select : function(){
              			var flag = getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").value;
              			if(flag == '1'){
              				getCurrentView().contentPanel.form.findField("TRANS_DATE").show();
              				getCurrentView().contentPanel.form.findField("TRANS_DATE").allowBlank = false;
              			}else{
              				getCurrentView().contentPanel.form.findField("TRANS_DATE").hide();
              				getCurrentView().contentPanel.form.findField("TRANS_DATE").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("TRANS_DATE").setValue('');
              			}
              		}
              }},                        
              {name:'TRANS_DATE',text:'转为存款户日期',dataType:'date',allowBlank:false,gridField:true},
              {name:'PIPELINE_DATE',text:'转为PIPELINE日期',dataType:'date',allowBlank:false,gridField:true},   
              {name:'RUFUSE_REASON',text:'拒绝理由',xtype:'string',translateType:'RUFUSE_REASON_PROSPECT_SME',allowBlank:false,gridField:true,editable:true,listeners:{
              		select : function(){
              			var flag = getCurrentView().contentPanel.form.findField("RUFUSE_REASON").value;
              			if(flag == '3'){
              				getCurrentView().contentPanel.form.findField("REASON_REMARK").show();
              				getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = false;
              			}else{
              				getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
              				getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = true;
              				getCurrentView().contentPanel.form.findField("REASON_REMARK").setValue('');
              			}
              		}
              }},
              {name:'REASON_REMARK',text:'拒绝原因说明',xtype:'textarea',allowBlank:false,gridField:true,maxLength:500},                                      
              {name:'CUST_ID',text:'客户编号',dataType:'string',gridField:true},
              {name:'ID',text:'id',gridField:false},
              {name:'GROUP_NAME',dataType:'string',gridField:true},
              {name:'PIPELINE_ID',text:'PIPELINE编号',gridField:false,searchField: true},
              {name:'RM_ID'},
              {name:'AREA_ID'},
              {name:'DEPT_ID'},
              {name:'RECORD_DATE'}];

              
           
var tbar=[{
	text:'删除',
	hidden:JsContext.checkGrant('smeProspectEDelet'),
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
					url : basepath + '/smeProspectE!batchDel.json',
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
        url :  basepath + '/smeProspectE.json'
    })
];              
var customerView = [{
	title:'新增',
	hideTitle:JsContext.checkGrant('smeProspectEAdd'),
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[
			'CUST_NAME','AREA_NAME',
			{name:'RM',text:'RM',resutlWidth:150},
			{name:'DEPT_NAME',text:'营业部门',allowBlank:false},			
			'CUST_SOURCE','CUST_SOURCE_DATE','VISIT_DATE',
			'IF_FILES','IF_TRANS_CUST','TRANS_DATE','IF_PIPELINE','PIPELINE_DATE','RUFUSE_REASON',
			'CUST_ID','AREA_ID','DEPT_ID','RM_ID','ID'],
		fn :function(CUST_NAME,AREA_NAME,RM,DEPT_NAME,CUST_SOURCE,CUST_SOURCE_DATE,VISIT_DATE,VISIT_DATEIF_FILES,IF_TRANS_CUST,TRANS_DATE,
				IF_PIPELINE,PIPELINE_DATE,RUFUSE_REASON,CUST_ID,AREA_ID,DEPT_ID,RM_ID,ID){
			
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
			RM_ID.hidden = true;
			ID.hidden = true;
			RUFUSE_REASON.hidden=true;
			
			return [CUST_NAME,AREA_NAME,RM,DEPT_NAME,CUST_SOURCE,CUST_SOURCE_DATE,VISIT_DATE,IF_FILES,IF_TRANS_CUST,TRANS_DATE,
				IF_PIPELINE,PIPELINE_DATE,RUFUSE_REASON,CUST_ID,AREA_ID,DEPT_ID,RM_ID,ID];
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
						url : basepath + '/smeProspectE!save.json',
						method : 'GET',
						params : commintData,
						success : function(response) {
							if(ifPipeline=='1'){
								var ret = Ext.decode(response.responseText);
								Ext.Ajax.request({
									url : basepath + '/smeIntentE!save.json',//如果要转入pipeline阶段，把数据保存进合作意向表内
									method : 'GET',
									params : ret,
									success : function(response) {
										var ret = Ext.decode(response.responseText);
										Ext.MessageBox.alert('提示','保存数据成功,请在合作意向阶段查看数据!');
										hideCurrentView(); 
										reloadCurrentData();
									}
								});
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
	hideTitle:JsContext.checkGrant('smeProspectEEdit'),
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[			
			{name:'CUST_NAME',text:'客户名称',allowBlank:false},
			{name:'AREA_NAME',text:'区域中心',allowBlank:false},
			{name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			{name:'RM',text:'RM',singleSelect: true},
			'CUST_SOURCE','CUST_SOURCE_DATE','VISIT_DATE',
			'IF_FILES','IF_TRANS_CUST','TRANS_DATE','IF_PIPELINE','PIPELINE_DATE','RUFUSE_REASON',
			'CUST_ID','AREA_ID','DEPT_ID','RM_ID','ID','PIPELINE_ID','RECORD_DATE'],
		fn :function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,VISIT_DATE,IF_FILES,IF_TRANS_CUST,TRANS_DATE,
				IF_PIPELINE,PIPELINE_DATE,RUFUSE_REASON,CUST_ID,AREA_ID,DEPT_ID,RM_ID,ID,PIPELINE_ID,RECORD_DATE){
			CUST_NAME.readOnly = true;
			CUST_NAME.cls = 'x-readOnly';
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
			ID.hidden = true;
			ID.readOnly = true;
			ID.cls = 'x-readOnly';
//			RECORD_DATE.hidden=true;
			DEPT_ID.hidden=true;
			RM_ID.hidden=true;
			TRANS_DATE.hidden = true;
			PIPELINE_DATE.hidden = true;
			
			RUFUSE_REASON.hidden = true;
			PIPELINE_ID.hidden=true;
			RECORD_DATE.hidden = true;
			return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,CUST_SOURCE,CUST_SOURCE_DATE,VISIT_DATE,IF_FILES,IF_TRANS_CUST,TRANS_DATE,
			        IF_PIPELINE,PIPELINE_DATE,RUFUSE_REASON,CUST_ID,AREA_ID,DEPT_ID,RM_ID,ID,PIPELINE_ID,RECORD_DATE];
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
						url : basepath + '/smeProspectE!save.json',
						method : 'GET',
						params : commintData,
						success : function(response) {							
							if(ifPipeline=='1'){
								var ret = Ext.decode(response.responseText);
								Ext.Ajax.request({
						url : basepath + '/smeIntentE!save.json',
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
							}
						}); 
	        	   
		}
	}]

},{
	title:'详情',
	hideTitle:JsContext.checkGrant('smeProspectEDetail'),
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount : 2,
		fields:[			
			{name:'CUST_NAME',text:'客户名称',allowBlank:false},
			{name:'AREA_NAME',text:'区域中心',allowBlank:false},
			{name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			{name:'RM',text:'RM',singleSelect: true},
			'CUST_SOURCE','CUST_SOURCE_DATE','VISIT_DATE',
			'IF_FILES','IF_TRANS_CUST','TRANS_DATE','IF_PIPELINE','PIPELINE_DATE','RUFUSE_REASON',
			'CUST_ID','AREA_ID','DEPT_ID','RM_ID','ID','PIPELINE_ID'],
		fn :function(CUST_NAME,AREA_NAME,RM,DEPT_NAME,CUST_SOURCE,CUST_SOURCE_DATE,VISIT_DATE,IF_FILES,IF_TRANS_CUST,TRANS_DATE,
				IF_PIPELINE,PIPELINE_DATE,RUFUSE_REASON,CUST_ID,AREA_ID,DEPT_ID,RM_ID,ID,PIPELINE_ID){
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
			VISIT_DATE.readOnly = true;
			VISIT_DATE.cls = 'x-readOnly';
			IF_FILES.readOnly = true;
			IF_FILES.cls = 'x-readOnly';
			IF_TRANS_CUST.readOnly = true;
			IF_TRANS_CUST.cls = 'x-readOnly';
			IF_PIPELINE.readOnly = true;
			IF_PIPELINE.cls = 'x-readOnly';
			PIPELINE_DATE.readOnly = true;
			PIPELINE_DATE.cls = 'x-readOnly';
			TRANS_DATE.readOnly = true;
			TRANS_DATE.cls = 'x-readOnly';
			
			RUFUSE_REASON.readOnly = true;
			RUFUSE_REASON.cls = 'x-readOnly';
		
			CUST_ID.readOnly = true;
			CUST_ID.cls = 'x-readOnly';
			CUST_ID.hidden=true;
			AREA_ID.hidden=true;
			AREA_ID.readOnly = true;
			AREA_ID.cls = 'x-readOnly';
			RM_ID.hidden=true;
			ID.hidden=true;
			PIPELINE_ID.hidden=true;
			return [CUST_NAME,AREA_NAME,RM,DEPT_NAME,CUST_SOURCE,CUST_SOURCE_DATE,VISIT_DATE,IF_FILES,IF_TRANS_CUST,TRANS_DATE,
			        IF_PIPELINE,PIPELINE_DATE,RUFUSE_REASON,CUST_ID,AREA_ID,DEPT_ID,RM_ID,ID,PIPELINE_ID];
		}
	},{
	  columnCount: 1,
	  fields : [{name:'CUST_SOURCE_INFO',text:'客户来源说明',xtype:'textarea'},
	            {name:'PRODUCT_NEED',text:'客户产品需求',xtype:'textarea'},
	            {name:'REASON_REMARK',text:'拒绝原因说明',xtype:'textarea',maxLength:500}],
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
	if(view._defaultTitle == '修改'){
		var ifPipeline = getSelectedData().data.IF_PIPELINE;
		if(ifPipeline == '0'){
			Ext.Msg.alert('提示','案件已否决，不能修改');
			return false;
		}
	}
	if(view._defaultTitle == '详情'){
		if(getSelectedData().data.IF_PIPELINE == '2'){
			view.contentPanel.form.findField("REASON_REMARK").hide();
		}
		if(getSelectedData().data.IF_PIPELINE != '1'){
			view.contentPanel.form.findField("PIPELINE_DATE").hide();
		}
	}
	if(view._defaultTitle == '修改'||view._defaultTitle == '详情'){
		
		store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/smeProspectE.json'
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
		            {name:'TRANS_DATE'},
		            {name:'IF_PIPELINE'},
		            {name:'VISIT_DATE'},
		            {name:'RECORD_DATE'},
		            {name:'CUST_ID'},
		            {name:'AREA_ID'},
		            {name:'DEPT_ID'},
		            {name:'RM_ID'},
		            {name:'ID'},
		            {name:'PIPELINE_ID'},
		            {name:'CUST_SOURCE_INFO'},
		            {name:'PRODUCT_NEED'},
		            {name:'RUFUSE_REASON'},		            
		            {name:'PIPELINE_DATE'},
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
					getCurrentView().contentPanel.form.findField("CUST_SOURCE").allowBlank = false;
					getCurrentView().contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = false;			
				}else if(store.getAt(0).data.IF_PIPELINE == '2'){
					getCurrentView().contentPanel.form.findField("RUFUSE_REASON").hide();
					getCurrentView().contentPanel.form.findField("RUFUSE_REASON").allowBlank = true,
					getCurrentView().contentPanel.form.findField("RUFUSE_REASON").setValue('');				
					getCurrentView().contentPanel.form.findField("CUST_SOURCE").allowBlank = true;
					getCurrentView().contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = true;
					getCurrentView().contentPanel.form.findField("IF_FILES").allowBlank = true;
				} 
        		if(store.getAt(0).data.IF_FILES =='0' ){
					getCurrentView().contentPanel.form.findField("IF_FILES").allowBlank = false;
					getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").show();
					getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank = false;

          			var flag = store.getAt(0).data.IF_TRANS_CUST;
          			if(flag == '1'){
          				getCurrentView().contentPanel.form.findField("TRANS_DATE").show();
          				getCurrentView().contentPanel.form.findField("TRANS_DATE").allowBlank = false;
          			}else{
          				getCurrentView().contentPanel.form.findField("TRANS_DATE").hide();
          				getCurrentView().contentPanel.form.findField("TRANS_DATE").allowBlank = true;
          				getCurrentView().contentPanel.form.findField("TRANS_DATE").setValue('');
          			}
				}else if(store.getAt(0).data.IF_FILES =='1' ){
					getCurrentView().contentPanel.form.findField("IF_FILES").allowBlank = false;
					getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").hide();
					getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").allowBlank = true;
					getCurrentView().contentPanel.form.findField("IF_TRANS_CUST").setValue('');					
				}
        		CUST_ID = store.getAt(0).data.CUST_ID;
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
			url :basepath +'/smeProspectE!searchAreaBack.json',
			method : 'GET',
			params : {deptId:__units},
			success : function(response) {
				 var ret = Ext.decode(response.responseText);
				 if(ret.data[0] != null && ret.data[0] != null){
					 view.contentPanel.form.findField("AREA_ID").setValue(ret.data[0].AREA_ID);
					 view.contentPanel.form.findField("AREA_NAME").setValue(ret.data[0].AREA_NAME);
				 }
			}
		});	
		
		view.contentPanel.form.findField("IF_PIPELINE").setValue('2');//是否转入PEPILINE默认为暂时维持本阶段		
		view.contentPanel.form.findField("IF_FILES").setValue('1');	  //是否能取得财务资料默认为是
		

		if(view.contentPanel.form.findField("IF_PIPELINE").value == '0'){
			view.contentPanel.form.findField("CUST_SOURCE").allowBlank = true;
			view.contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = true;
			view.contentPanel.form.findField("VISIT_DATE").allowBlank = true;
			view.contentPanel.form.findField("IF_TRANS_CUST").allowBlank = true;
			view.contentPanel.form.findField("TRANS_DATE").allowBlank = true;
			
			view.contentPanel.form.findField("PIPELINE_DATE").hide();
			view.contentPanel.form.findField("PIPELINE_DATE").allowBlank = true;
			view.contentPanel.form.findField("PIPELINE_DATE").setValue('');
			
			view.contentPanel.form.findField("RUFUSE_REASON").show();
			view.contentPanel.form.findField("RUFUSE_REASON").allowBlank = false;
			if(view.contentPanel.form.findField("RUFUSE_REASON").getValue == '3'){
				view.contentPanel.form.findField("REASON_REMARK").show();
				view.contentPanel.form.findField("REASON_REMARK").allowBlank = false;
			}else{
				view.contentPanel.form.findField("REASON_REMARK").hide();
				view.contentPanel.form.findField("REASON_REMARK").allowBlank = true;
				view.contentPanel.form.findField("REASON_REMARK").setValue('');
			}
		}else if(view.contentPanel.form.findField("IF_PIPELINE").value == '1'){
			view.contentPanel.form.findField("CUST_SOURCE").allowBlank = false;
			view.contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = false;
			view.contentPanel.form.findField("VISIT_DATE").allowBlank = false; 
		
			view.contentPanel.form.findField("PIPELINE_DATE").show();
			view.contentPanel.form.findField("PIPELINE_DATE").allowBlank = false;
			
			view.contentPanel.form.findField("RUFUSE_REASON").hide();
			view.contentPanel.form.findField("RUFUSE_REASON").allowBlank = true;
			view.contentPanel.form.findField("RUFUSE_REASON").setValue('');
			
			view.contentPanel.form.findField("REASON_REMARK").hide();
			view.contentPanel.form.findField("REASON_REMARK").allowBlank = true;
			view.contentPanel.form.findField("REASON_REMARK").setValue('');
		}else{
			
			view.contentPanel.form.findField("CUST_SOURCE").allowBlank = true;
			view.contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = true;
			view.contentPanel.form.findField("VISIT_DATE").allowBlank = true;
			view.contentPanel.form.findField("IF_TRANS_CUST").allowBlank = true;
			view.contentPanel.form.findField("TRANS_DATE").allowBlank = true;
			
			view.contentPanel.form.findField("PIPELINE_DATE").hide();
			view.contentPanel.form.findField("PIPELINE_DATE").allowBlank = true;
			view.contentPanel.form.findField("PIPELINE_DATE").setValue('');
			
			view.contentPanel.form.findField("RUFUSE_REASON").hide();
			view.contentPanel.form.findField("RUFUSE_REASON").allowBlank = true;
			view.contentPanel.form.findField("RUFUSE_REASON").setValue('');
			
			view.contentPanel.form.findField("REASON_REMARK").hide();
			view.contentPanel.form.findField("REASON_REMARK").allowBlank = true;
			view.contentPanel.form.findField("REASON_REMARK").setValue('');
		}
		
		
		if(view.contentPanel.form.findField("IF_FILES").value == '0'){
			view.contentPanel.form.findField("IF_TRANS_CUST").show();
			view.contentPanel.form.findField("IF_TRANS_CUST").allowBlank = false;
			if(view.contentPanel.form.findField("IF_TRANS_CUST").value == '1'){
				view.contentPanel.form.findField("TRANS_DATE").show();
  				view.contentPanel.form.findField("TRANS_DATE").allowBlank = false;
			}else{
				view.contentPanel.form.findField("TRANS_DATE").hide();
  				view.contentPanel.form.findField("TRANS_DATE").allowBlank = true;
  				view.contentPanel.form.findField("TRANS_DATE").setValue('');
			}
		}else{
			view.contentPanel.form.findField("IF_TRANS_CUST").hide();
			view.contentPanel.form.findField("IF_TRANS_CUST").allowBlank = true;
			view.contentPanel.form.findField("IF_TRANS_CUST").setValue('0');
			
			view.contentPanel.form.findField("TRANS_DATE").hide();
			view.contentPanel.form.findField("TRANS_DATE").allowBlank = true;
			view.contentPanel.form.findField("TRANS_DATE").setValue('');
		}		
	}
	
	if(view._defaultTitle == '修改' || view._defaultTitle == '详情'){	
		
		if(view.contentPanel.form.findField("IF_PIPELINE").value == '0'){
			view.contentPanel.form.findField("CUST_SOURCE").allowBlank = true;
			view.contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = true;
			view.contentPanel.form.findField("VISIT_DATE").allowBlank = true;
			view.contentPanel.form.findField("IF_TRANS_CUST").allowBlank = true;
			view.contentPanel.form.findField("TRANS_DATE").allowBlank = true;
			
			view.contentPanel.form.findField("PIPELINE_DATE").hide();
			view.contentPanel.form.findField("PIPELINE_DATE").allowBlank = true;
			view.contentPanel.form.findField("PIPELINE_DATE").setValue('');
			
			view.contentPanel.form.findField("RUFUSE_REASON").show();
			view.contentPanel.form.findField("RUFUSE_REASON").allowBlank = false;
			if(view.contentPanel.form.findField("RUFUSE_REASON").getValue == '3'){
				view.contentPanel.form.findField("REASON_REMARK").show();
				view.contentPanel.form.findField("REASON_REMARK").allowBlank = false;
			}else{
				view.contentPanel.form.findField("REASON_REMARK").hide();
				view.contentPanel.form.findField("REASON_REMARK").allowBlank = true;
				view.contentPanel.form.findField("REASON_REMARK").setValue('');
			}
		}else if(view.contentPanel.form.findField("IF_PIPELINE").value == '1'){
			view.contentPanel.form.findField("CUST_SOURCE").allowBlank = false;
			view.contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = false;
			view.contentPanel.form.findField("VISIT_DATE").allowBlank = false; 
			
			view.contentPanel.form.findField("PIPELINE_DATE").show();
			view.contentPanel.form.findField("PIPELINE_DATE").allowBlank = false;
			
			view.contentPanel.form.findField("RUFUSE_REASON").hide();
			view.contentPanel.form.findField("RUFUSE_REASON").allowBlank = true;
			view.contentPanel.form.findField("RUFUSE_REASON").setValue('');
			
			view.contentPanel.form.findField("REASON_REMARK").hide();
			view.contentPanel.form.findField("REASON_REMARK").allowBlank = true;
			view.contentPanel.form.findField("REASON_REMARK").setValue('');
		}else{
			
			view.contentPanel.form.findField("CUST_SOURCE").allowBlank = true;
			view.contentPanel.form.findField("CUST_SOURCE_DATE").allowBlank = true;
			view.contentPanel.form.findField("VISIT_DATE").allowBlank = true;
			view.contentPanel.form.findField("IF_TRANS_CUST").allowBlank = true;
			view.contentPanel.form.findField("TRANS_DATE").allowBlank = true;
			
			view.contentPanel.form.findField("PIPELINE_DATE").hide();
			view.contentPanel.form.findField("PIPELINE_DATE").allowBlank = true;
			view.contentPanel.form.findField("PIPELINE_DATE").setValue('');
			
			view.contentPanel.form.findField("RUFUSE_REASON").hide();
			view.contentPanel.form.findField("RUFUSE_REASON").allowBlank = true;
			view.contentPanel.form.findField("RUFUSE_REASON").setValue('');
			
			view.contentPanel.form.findField("REASON_REMARK").hide();
			view.contentPanel.form.findField("REASON_REMARK").allowBlank = true;
			view.contentPanel.form.findField("REASON_REMARK").setValue('');
		}
		
		
		if(view.contentPanel.form.findField("IF_FILES").value == '0'){
			view.contentPanel.form.findField("IF_TRANS_CUST").show();
			view.contentPanel.form.findField("IF_TRANS_CUST").allowBlank = false;
			if(view.contentPanel.form.findField("IF_TRANS_CUST").value == '1'){
				view.contentPanel.form.findField("TRANS_DATE").show();
					view.contentPanel.form.findField("TRANS_DATE").allowBlank = false;
			}else{
				view.contentPanel.form.findField("TRANS_DATE").hide();
					view.contentPanel.form.findField("TRANS_DATE").allowBlank = true;
					view.contentPanel.form.findField("TRANS_DATE").setValue('');
			}
		}else{
			view.contentPanel.form.findField("IF_TRANS_CUST").hide();
			view.contentPanel.form.findField("IF_TRANS_CUST").allowBlank = true;
			view.contentPanel.form.findField("IF_TRANS_CUST").setValue('0');
			
			view.contentPanel.form.findField("TRANS_DATE").hide();
			view.contentPanel.form.findField("TRANS_DATE").allowBlank = true;
			view.contentPanel.form.findField("TRANS_DATE").setValue('');
		}		
	}
};
var afterconditionrender = function(panel, app) {
	app.searchDomain.searchPanel.getForm()
	.findField("PIPELINE_ID").setValue(parent.window.document.getElementById('condition').value);
};
