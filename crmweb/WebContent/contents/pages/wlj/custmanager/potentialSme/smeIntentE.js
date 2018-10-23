/**
 * @description 中小企客户营销流程 - 合作意向信息页面
 * @author denghj
 * @since 2015-08-06
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldQZ.js'
	,'/contents/pages/common/Com.yucheng.crm.common.DeptQuery.js'
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

var ifSencondStep=0;//此标志用于判断是否需要把信息录入第二阶段;值为1则需要
var url = basepath + '/smeIntentE.json';

var lookupTypes = ['IF_FLAG_HZ','CHECK_STAT','CASE_TYPE_SME','CUST_TYPE_SME','HARD_INFO_INTENT_SME','IF_FLAG','REFUSE_REASON_SME',
		{
			TYPE : 'AREA',//区域中心数据字典
			url : '/smeProspectE!searchArea.json',//此url为pipeline阶段所有区域中心字段的数据字典访问地址，后台逻辑存放于mktprospectCAction中
			key : 'KEY',
			value : 'VALUE',
			root : 'data'}
		];
		
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
	],
	'SUC_PROBABILITY':[
			{key : '1',value : '无'},      
		    {key : '2',value : '10'},
		    {key : '3',value : '20'},    
		    {key : '4',value : '30'},      
		    {key : '5',value : '40'},      
		    {key : '6',value : '50'},  
			{key : '7',value : '60'},
			{key : '8',value : '70'},  
			{key : '9',value : '80'},  
			{key : '10',value : '90'},      
			{key : '11',value : '100'}  
	]
};		

var fields = [
              {name:'IF_SECOND_STEP',text:'是否进入第二阶段',translateType:'IF_FLAG_HZ',allowBlank:false,searchField: true,listeners:{
				select:function(){
					var flag = getCurrentView().contentPanel.form.findField("IF_SECOND_STEP").value;
					if(flag == '0'){//否
						getCurrentView().contentPanel.form.findField("CP_HARD_INFO").show();
						getCurrentView().contentPanel.form.findField("CP_HARD_INFO").allowBlank = false;
						
//						if(getCurrentView().contentPanel.form.findField("CP_HARD_INFO").value == '1'){
//							getCurrentView().contentPanel.form.findField("HARD_INFO").show();
//							getCurrentView().contentPanel.form.findField("HARD_INFO").allowBlank = false;
//						}else{
//							getCurrentView().contentPanel.form.findField("HARD_INFO").hide();
//							getCurrentView().contentPanel.form.findField("HARD_INFO").allowBlank = true;
//							getCurrentView().contentPanel.form.findField("HARD_INFO").setValue('');
//						}
						
						getCurrentView().contentPanel.form.findField("FOREIGN_MONEY").allowBlank = true;
						getCurrentView().contentPanel.form.findField("CURRENCY").allowBlank = true;
						getCurrentView().contentPanel.form.findField("APPLY_AMT").allowBlank = true;
						getCurrentView().contentPanel.form.findField("CUST_TYPE").allowBlank = true;
						getCurrentView().contentPanel.form.findField("CASE_TYPE").allowBlank = true;
						getCurrentView().contentPanel.form.findField("SUC_PROBABILITY").allowBlank = true;
						getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = true;
						getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
					}else if(flag == '1'){//是
						getCurrentView().contentPanel.form.findField("CP_HARD_INFO").hide();
						getCurrentView().contentPanel.form.findField("CP_HARD_INFO").allowBlank = true;
						getCurrentView().contentPanel.form.findField("CP_HARD_INFO").setValue('');
						
						getCurrentView().contentPanel.form.findField("HARD_INFO").hide();
						getCurrentView().contentPanel.form.findField("HARD_INFO").allowBlank = true;
						getCurrentView().contentPanel.form.findField("HARD_INFO").setValue('');
						
						getCurrentView().contentPanel.form.findField("FOREIGN_MONEY").allowBlank = false;
						getCurrentView().contentPanel.form.findField("CURRENCY").allowBlank = false;
						getCurrentView().contentPanel.form.findField("APPLY_AMT").allowBlank = false;
						getCurrentView().contentPanel.form.findField("CUST_TYPE").allowBlank = false;
						getCurrentView().contentPanel.form.findField("CASE_TYPE").allowBlank = false;
						getCurrentView().contentPanel.form.findField("SUC_PROBABILITY").allowBlank = false;
									 
			  			if(getCurrentView().contentPanel.form.findField("CUST_TYPE").value == '2'){			  				
			  				getCurrentView().contentPanel.form.findField("IF_ADD").show();	
			  				getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = false;
			  				if(getCurrentView().contentPanel.form.findField("IF_ADD").getValue() == '1'){
			  					getCurrentView().contentPanel.form.findField("ADD_AMT").show();
			  					getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = false;
			  				}else{
			  					getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
								getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
								getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
			  				}			  				
			  			}else{
			  				getCurrentView().contentPanel.form.findField("IF_ADD").hide();
			  				getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = true;
			  				getCurrentView().contentPanel.form.findField("IF_ADD").setValue('');
			  				
			  				getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
							getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
							getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
			  			}
			  		
					}else{//暂时维持本阶段
						getCurrentView().contentPanel.form.findField("CP_HARD_INFO").show();
						getCurrentView().contentPanel.form.findField("CP_HARD_INFO").allowBlank = false;
						
//						if(getCurrentView().contentPanel.form.findField("CP_HARD_INFO").value == '1'){
//							getCurrentView().contentPanel.form.findField("HARD_INFO").show();
//							getCurrentView().contentPanel.form.findField("HARD_INFO").allowBlank = false;
//						}else{
//							getCurrentView().contentPanel.form.findField("HARD_INFO").hide();
//							getCurrentView().contentPanel.form.findField("HARD_INFO").allowBlank = true;
//							getCurrentView().contentPanel.form.findField("HARD_INFO").setValue('');
//						}
						
						getCurrentView().contentPanel.form.findField("FOREIGN_MONEY").allowBlank = false;
						getCurrentView().contentPanel.form.findField("CURRENCY").allowBlank = false;
						getCurrentView().contentPanel.form.findField("APPLY_AMT").allowBlank = false;
						getCurrentView().contentPanel.form.findField("CUST_TYPE").allowBlank = false;
						getCurrentView().contentPanel.form.findField("CASE_TYPE").allowBlank = false;
						getCurrentView().contentPanel.form.findField("SUC_PROBABILITY").allowBlank = false;
						
						if(getCurrentView().contentPanel.form.findField("CUST_TYPE").value == '2'){			  				
			  				getCurrentView().contentPanel.form.findField("IF_ADD").show();	
			  				getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = false;
			  				if(getCurrentView().contentPanel.form.findField("IF_ADD").getValue() == '1'){
			  					getCurrentView().contentPanel.form.findField("ADD_AMT").show();
			  					getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = false;
			  				}else{
			  					getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
								getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
								getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
			  				}			  				
			  			}else{
			  				getCurrentView().contentPanel.form.findField("IF_ADD").hide();
			  				getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = true;
			  				getCurrentView().contentPanel.form.findField("IF_ADD").setValue('');
			  				
			  				getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
							getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
							getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
			  			}
						
//						getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = true;
//						getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;

						
					}
				}}},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerqueryqz',allowBlank:false,hiddenName:'CUST_ID',resutlWidth:150,
				singleSelect: false,custType:'1',searchField: true,isNew:true},
              {name:'AREA_ID',text:'区域编号',gridField:false},
              {name:'AREA_NAME',text:'区域中心',translateType:"AREA",
	              resutlWidth:80,searchField: true, showField:'text',allowBlank:false,valueField:"value",listeners:{
				  select:function(a,b){
				  	a.setValue(b.data.value);
				}
				}},
			  {name:'DEPT_NAME',text:'营业单位名称',xtype:'orgchoose',hiddenName:'DEPT_ID',allowBlank:false,searchField: true},
		      {name:'RM',text:'RM',xtype:'userchoose',hiddenName:'RM_ID',resutlWidth:150,singleSelect: false,allowBlank:false,searchField: true},		    
		      {name:'FOREIGN_MONEY',text:'申请额度(原币金额/千元)',gridField:true,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24,allowBlank:false},
		      {name:'CURRENCY',text:'申请额度币别',gridField:true,translateType:'CURRENCY',allowBlank:false,listeners:{
				  	select:function(){
				  		var flag=this.value;
				  		var  FOREIGN_MONEY=getCurrentView().contentPanel.form.findField("FOREIGN_MONEY").getValue();
				  		if(flag=='13'){				  			
				  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(FOREIGN_MONEY*6);
				  		}else if(flag == '10'){				  
				  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(FOREIGN_MONEY);
				  		}
				  		else{
				  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue('');
				  		}
				  	}}},
			  {name:'APPLY_AMT',text:'申请额度(折人民币/千元)',dataType:'money',maxLength:20,allowBlank:false},
			  {name:'CUST_TYPE',text:'客户类型',translateType:'CUST_TYPE_SME',searchField: true,editable:true,resizable:true,allowBlank:false,listeners:{
			  		select : function(){
			  			var flag = getCurrentView().contentPanel.form.findField("CUST_TYPE").value;
			  			if(flag == '2'){
			  				getCurrentView().contentPanel.form.findField("IF_ADD").show();			
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
			  {name:'CASE_TYPE',text:'案件类型',translateType:'CASE_TYPE_SME',searchField: true,editable:true,resizable:true,allowBlank:false},
			  {name:'IF_ADD',text:'若为旧案是否为增贷',gridField:true,translateType:'IF_FLAG',allowBlank:false,listeners:{
			  		select : function(){
			  			var flag = getCurrentView().contentPanel.form.findField("IF_ADD").value;
			  			if(flag == '1'){
			  				getCurrentView().contentPanel.form.findField("ADD_AMT").show();
			  			}else{
			  				getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
			  				getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
			  				getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
			  			}
			  		}
			  }},
			  {name:'ADD_AMT',text:'增贷金额(折人民币/千元)',gridField:true,dataType:'money',allowBlank:false,maxLength:24,minValue:0},
			  {name:'CP_HARD_INFO',text:'营销难点',translateType:'HARD_INFO_INTENT_SME',allowBlank:false,searchField:true,editable: true,listeners:{
				  select : function(){
					  var flag = getCurrentView().contentPanel.form.findField("CP_HARD_INFO").value;
					  if(flag == '1'){
							getCurrentView().contentPanel.form.findField("HARD_INFO").show();
							getCurrentView().contentPanel.form.findField("HARD_INFO").allowBlank = false;
					  }else{
						  	getCurrentView().contentPanel.form.findField("HARD_INFO").hide();
							getCurrentView().contentPanel.form.findField("HARD_INFO").allowBlank = true;
							getCurrentView().contentPanel.form.findField("HARD_INFO").setValue('');
					  }
				  }
			  }},
			  {name:'HARD_INFO',text:'营销难点说明',gridField:true,xtype: 'textarea',maxLength:400,allowBlank:false},
			  {name:'SUC_PROBABILITY',text:'预计可送审概率(%)',translateType:'SUC_PROBABILITY',allowBlank:false,searchField:true,editable:true},
	          {name:'CUST_ID',text:'客户编号',gridField:false,searchField:true},
	          {name:'RM_ID',text:'RM编号',gridField:false},
	          {name:'DEPT_ID',text:'DEPT_ID',gridField:false},
	          {name:'ID',text:'ID',gridField:false},
	          {name:'PROSPECT_ID',text:'PROSPECT_ID',gridField:false},
			  {name:'PIPELINE_ID',text:'PIPELINE编号',gridField:false,searchField:true},
			  {name:'RECORD_DATE',text:'RECORD_DATE',gridField:false}
			  ];
var tbar = [{
	text:'删除',
	hidden:JsContext.checkGrant('smeIntentEDelet'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}

		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
			return false;
			} 
		    Ext.Ajax.request({
                url: basepath+'/smeIntentE!batchDel.json',   
                method : 'POST',
                params : {
						idStr : getSelectedData().data.ID
					},
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
        url :  basepath + '/smeIntentE.json'
    })];

var customerView = [{
	title:'新增',
	hideTitle:JsContext.checkGrant('smeIntentEAdd'),
	type:'form',
	groups : [{
		labelWidth:150,
		columnCount:2,
		fields :[
			'CUST_NAME','AREA_NAME',
			{name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			{name:'RM',text:'RM',resutlWidth:150},
			'FOREIGN_MONEY','CASE_TYPE','CURRENCY',
			'CUST_TYPE','APPLY_AMT','IF_ADD','SUC_PROBABILITY','ADD_AMT','IF_SECOND_STEP',
			'CP_HARD_INFO','CUST_ID','RM_ID','AREA_ID','DEPT_ID','ID','PROSPECT_ID','PIPELINE_ID','RECORD_DATE'],
		fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,FOREIGN_MONEY,CASE_TYPE,CURRENCY,CUST_TYPE,APPLY_AMT,IF_ADD,
				SUC_PROBABILITY,ADD_AMT,IF_SECOND_STEP,CP_HARD_INFO,CUST_ID,RM_ID,AREA_ID,DEPT_ID,ID,PROSPECT_ID,PIPELINE_ID,RECORD_DATE){
				AREA_NAME.readOnly = true;
				AREA_NAME.cls = 'x-readOnly';
				DEPT_NAME.readOnly = true;
				DEPT_NAME.cls = 'x-readOnly';
				RM.readOnly = true;
				RM.cls = 'x-readOnly';
				
				IF_ADD.hidden = true;
				ADD_AMT.hidden = true;
//				CP_HARD_INFO.hidden = true;
				
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				AREA_ID.hidden = true;
				DEPT_ID.hidden = true;
				ID.hidden = true;
				PROSPECT_ID.hidden = true;
				PIPELINE_ID.hidden = true;
				RECORD_DATE.hidden = true;			
				
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,FOREIGN_MONEY,CASE_TYPE,CURRENCY,CUST_TYPE,APPLY_AMT,IF_ADD,
						SUC_PROBABILITY,ADD_AMT,IF_SECOND_STEP,CP_HARD_INFO,CUST_ID,RM_ID,AREA_ID,DEPT_ID,ID,PROSPECT_ID,PIPELINE_ID,RECORD_DATE];
			}
	},{
		  columnCount: 1,
		  fields : ['HARD_INFO',{name:'REMARK',text:'备注',xtype: 'textarea',maxLength:400}],
		  fn : function(HARD_INFO,REMARK){
			HARD_INFO.hidden = true;
		  	REMARK.hidden = false;
			  return [HARD_INFO,REMARK];
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
		         	 if(data.IF_SECOND_STEP == '0'){
		        	   if(formPanel.form.findField("CP_HARD_INFO").getValue() == ''){
		        		   Ext.MessageBox.alert('提示','请填写[营销难点]');
			               return false;
		        	   }
			        }
			        
			     if(formPanel.getForm().findField("IF_ADD").getValue()== '1'){
		        	  if(formPanel.getForm().findField("ADD_AMT").getValue()== ''){
		        		  Ext.MessageBox.alert('提示','请填写增贷金额');
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
			        if(((data.APPLY_AMT==0||data.APPLY_AMT=='')&&(data.FOREIGN_MONEY!=0||data.FOREIGN_MONEY!='')&&data.IF_SECOND_STEP=='1')
			        		||((data.APPLY_AMT!=0||data.APPLY_AMT!='')&&(data.FOREIGN_MONEY==0||data.FOREIGN_MONEY=='')&&data.IF_SECOND_STEP=='1')){
			        	Ext.MessageBox.alert('提示','申请额度（折人民币/千元）或申请额度（原币金额/千元）不允许为零！');
			               return false;
			        }
					var commintData = translateDataKey(data,1);
					var gradePersect=commintData.gradePersect;
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/smeIntentE!save.json',
							method : 'GET',
							params : commintData,
							success : function(response) {
									if(data.IF_SECOND_STEP=='1'){//需要在下一阶段进行数据存储
										var ret = Ext.decode(response.responseText);
										Ext.Ajax.request({
											url : basepath + '/smeCaE!save.json',//把数据转入文件收集阶段，把数据保存进合作意向表内
											method : 'GET',
											params : ret,
											success : function(response) {
												Ext.MessageBox.alert('提示','保存数据成功,请在文件收集阶段查看数据!');
											}
										})
									}else{
										Ext.MessageBox.alert('提示','保存数据成功!');
									}
								    hideCurrentView(); 
									reloadCurrentData();
								}
							}); 
		        	   
			}
		}]

},{
	title:'修改',
	hideTitle:JsContext.checkGrant('smeIntentEEdit'),
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		labelWidth:150,
		columnCount:2,
		fields :[
			{name:'CUST_NAME',text:'客户名称',allowBlank:false},
			{name:'AREA_NAME',text:'区域中心',allowBlank:false},
			{name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			{name:'RM',text:'RM',singleSelect: true},
			'FOREIGN_MONEY','CASE_TYPE','CURRENCY',
			'CUST_TYPE','APPLY_AMT','IF_ADD','SUC_PROBABILITY','ADD_AMT','IF_SECOND_STEP',
			'CP_HARD_INFO','CUST_ID','RM_ID','AREA_ID','DEPT_ID','ID','PROSPECT_ID','PIPELINE_ID','RECORD_DATE'],
		fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,FOREIGN_MONEY,CASE_TYPE,CURRENCY,CUST_TYPE,APPLY_AMT,IF_ADD,
		SUC_PROBABILITY,ADD_AMT,IF_SECOND_STEP,CP_HARD_INFO,CUST_ID,RM_ID,AREA_ID,DEPT_ID,ID,PROSPECT_ID,PIPELINE_ID,RECORD_DATE){
				CUST_NAME.readOnly = true;
				CUST_NAME.cls = 'x-readOnly';
				AREA_NAME.readOnly = true;
				AREA_NAME.cls = 'x-readOnly';
				DEPT_NAME.readOnly = true;
				DEPT_NAME.cls = 'x-readOnly';
				RM.readOnly = true;
				RM.cls = 'x-readOnly';
				
				IF_ADD.hidden = true;
				ADD_AMT.hidden = true;
				
				CP_HARD_INFO.hidden = true;
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				AREA_ID.hidden = true;
				DEPT_ID.hidden = true;
				ID.hidden = true;
				PROSPECT_ID.hidden = true;
				PIPELINE_ID.hidden = true;
				RECORD_DATE.hidden = true;			
			
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,FOREIGN_MONEY,CASE_TYPE,CURRENCY,CUST_TYPE,APPLY_AMT,IF_ADD,
						SUC_PROBABILITY,ADD_AMT,IF_SECOND_STEP,CP_HARD_INFO,CUST_ID,RM_ID,AREA_ID,DEPT_ID,ID,PROSPECT_ID,PIPELINE_ID,RECORD_DATE];
			}
	},{
		  columnCount: 1,
		  fields : ['HARD_INFO',{name:'REMARK',text:'备注',xtype: 'textarea',maxLength:400}],
		  fn : function(HARD_INFO,REMARK){
			  HARD_INFO.hidden = true;
		  	REMARK.hidden = false;
			  return [HARD_INFO,REMARK];
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
					ifSencondStep=commintData.ifSecondStep;
					 if(ifSencondStep == '0'){
		        	   if(formPanel.form.findField("CP_HARD_INFO").getValue() == ''){
		        		   Ext.MessageBox.alert('提示','请填写[营销难点]');
			               return false;
		        	   }
			        }
			        
			     if(formPanel.getForm().findField("IF_ADD").getValue()== '1'){
		        	  if(formPanel.getForm().findField("ADD_AMT").getValue()== ''){
		        		  Ext.MessageBox.alert('提示','请填写增贷金额');
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
			        if(((data.APPLY_AMT==0||data.APPLY_AMT=='')&&(data.FOREIGN_MONEY!=0||data.FOREIGN_MONEY!='')&&data.IF_SECOND_STEP=='1')
			        		||((data.APPLY_AMT!=0||data.APPLY_AMT!='')&&(data.FOREIGN_MONEY==0||data.FOREIGN_MONEY=='')&&data.IF_SECOND_STEP=='1')){
			        	Ext.MessageBox.alert('提示','申请额度（折人民币/千元）或申请额度（原币金额/千元）不允许为零！');
			               return false;
			        }
					var gradePersect=commintData.gradePersect;
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/smeIntentE!save.json',
							method : 'GET',
							params : commintData,
							success : function(response) {
									if(ifSencondStep=='1'){//需要在下一阶段进行数据存储
										var ret = Ext.decode(response.responseText);
										Ext.Ajax.request({
											url : basepath + '/smeCaE!save.json',//把数据转入文件收集阶段，把数据保存进合作意向表内
											method : 'GET',
											params : ret,
											success : function(response) {
												Ext.MessageBox.alert('提示','保存数据成功,请在文件收集阶段查看数据!');																								
											}
										})
									}else{
										Ext.MessageBox.alert('提示','保存数据成功!');
									}
									hideCurrentView(); 
									reloadCurrentData();
							}
							}); 
			}
		}]
},{	
	title:'详情',
	hideTitle:JsContext.checkGrant('smeIntentEDetail'),
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		labelWidth:188,
		columnCount:2,
		fields :[
			{name:'CUST_NAME',text:'客户名称',allowBlank:false},
			{name:'AREA_NAME',text:'区域中心',allowBlank:false},
			{name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			{name:'RM',text:'RM',singleSelect: true},
			'FOREIGN_MONEY','CASE_TYPE','CURRENCY',
			'CUST_TYPE','APPLY_AMT','IF_ADD','SUC_PROBABILITY','ADD_AMT','IF_SECOND_STEP',
			'CP_HARD_INFO','CUST_ID','RM_ID','AREA_ID','DEPT_ID','ID','PROSPECT_ID','PIPELINE_ID','RECORD_DATE'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,FOREIGN_MONEY,CASE_TYPE,CURRENCY,CUST_TYPE,APPLY_AMT,
					IF_ADD,SUC_PROBABILITY,ADD_AMT,IF_SECOND_STEP,CP_HARD_INFO,
						CUST_ID,RM_ID,AREA_ID,DEPT_ID,ID,PROSPECT_ID,PIPELINE_ID,RECORD_DATE){
				CUST_NAME.readOnly = true;
				CUST_NAME.cls = 'x-readOnly';
				AREA_NAME.readOnly = true;
				AREA_NAME.cls = 'x-readOnly';
				DEPT_NAME.readOnly = true;
				DEPT_NAME.cls = 'x-readOnly';
				RM.readOnly = true;
				RM.cls = 'x-readOnly';
				FOREIGN_MONEY.readOnly = true;
				FOREIGN_MONEY.cls = 'x-readOnly';
				CUST_TYPE.readOnly = true;
				CUST_TYPE.cls = 'x-readOnly';
				CURRENCY.readOnly = true;
				CURRENCY.cls = 'x-readOnly';			
				CASE_TYPE.readOnly = true;
				CASE_TYPE.cls = 'x-readOnly';
				APPLY_AMT.readOnly = true;
				APPLY_AMT.cls = 'x-readOnly';
				IF_ADD.readOnly = true;
				IF_ADD.cls = 'x-readOnly';
				ADD_AMT.readOnly = true;
				ADD_AMT.cls = 'x-readOnly';
				SUC_PROBABILITY.readOnly = true;
				SUC_PROBABILITY.cls = 'x-readOnly';
				IF_SECOND_STEP.readOnly = true;
				IF_SECOND_STEP.cls = 'x-readOnly';
				CP_HARD_INFO.readOnly = true;
				CP_HARD_INFO.cls = 'x-readOnly';
				
				IF_ADD.hidden = true;
				ADD_AMT.hidden = true;
				
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				AREA_ID.hidden = true;
				DEPT_ID.hidden = true;
				ID.hidden = true;
				PROSPECT_ID.hidden = true;
				PIPELINE_ID.hidden = true;
				RECORD_DATE.hidden = true;
				
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,FOREIGN_MONEY,CASE_TYPE,CURRENCY,CUST_TYPE,APPLY_AMT,
					IF_ADD,SUC_PROBABILITY,ADD_AMT,IF_SECOND_STEP,CP_HARD_INFO,
						CUST_ID,RM_ID,AREA_ID,DEPT_ID,ID,PROSPECT_ID,PIPELINE_ID,RECORD_DATE];
			}
	},{
		  columnCount: 1,
		  fields : ['HARD_INFO',{name:'REMARK',text:'<font color="red">*</font>备注',xtype: 'textarea',maxLength:400}],
		  fn : function(HARD_INFO,REMARK){
			HARD_INFO.hidden = true;
			HARD_INFO.readOnly = true;
			HARD_INFO.cls = 'x-readOnly';
		  	REMARK.readOnly = true;
		  	REMARK.cls = 'x-readOnly';
			  return [HARD_INFO,REMARK];
		  }
		}]
}];

var beforeviewshow = function(view){
	
	if(view._defaultTitle == '修改'||view._defaultTitle == '详情'){
		if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
		}
		if(view._defaultTitle == '修改'){
			var ifPipeline = getSelectedData().data.IF_SECOND_STEP;
			if(ifPipeline == '0'){
				Ext.Msg.alert('提示','案件已否决，不能修改');
				return false;
			}
		}
		if(getSelectedData().data.IF_SECOND_STEP == '1'||getSelectedData().data.IF_SECOND_STEP == null
			||getSelectedData().data.IF_SECOND_STEP == '2'){
			view.contentPanel.form.findField("CP_HARD_INFO").hide();
			view.contentPanel.form.findField("HARD_INFO").hide();
    	}
}
};
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
				 view.contentPanel.form.findField("AREA_ID").setValue(ret.data[0].AREA_ID);
				 view.contentPanel.form.findField("AREA_NAME").setValue(ret.data[0].AREA_NAME);
			}
		})
		
		view.contentPanel.form.findField("IF_SECOND_STEP").setValue('2');
		view.contentPanel.form.findField("CASE_TYPE").setValue('16');
		view.contentPanel.form.findField("CUST_TYPE").setValue('0');


		if(view.contentPanel.form.findField("IF_SECOND_STEP").value == '0'){//否
			view.contentPanel.form.findField("CP_HARD_INFO").show();
			view.contentPanel.form.findField("CP_HARD_INFO").allowBlank = false;
			
			if(view.contentPanel.form.findField("CP_HARD_INFO").value == '1'){
				view.contentPanel.form.findField("HARD_INFO").show();
				view.contentPanel.form.findField("HARD_INFO").allowBlank = false;
			}else{
				view.contentPanel.form.findField("HARD_INFO").hide();
				view.contentPanel.form.findField("HARD_INFO").allowBlank = true;
				view.contentPanel.form.findField("HARD_INFO").setValue('');
			}
			
			view.contentPanel.form.findField("FOREIGN_MONEY").allowBlank = true;
			view.contentPanel.form.findField("CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = true;
			view.contentPanel.form.findField("CUST_TYPE").allowBlank = true;
			view.contentPanel.form.findField("CASE_TYPE").allowBlank = true;	
			view.contentPanel.form.findField("IF_ADD").allowBlank = true;
			view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
			view.contentPanel.form.findField("SUC_PROBABILITY").allowBlank = true;
		}else if(view.contentPanel.form.findField("IF_SECOND_STEP").value == '1'){//是
			view.contentPanel.form.findField("CP_HARD_INFO").hide();
			view.contentPanel.form.findField("CP_HARD_INFO").allowBlank = true;
			view.contentPanel.form.findField("CP_HARD_INFO").setValue('');
			
			view.contentPanel.form.findField("HARD_INFO").hide();
			view.contentPanel.form.findField("HARD_INFO").allowBlank = true;
			view.contentPanel.form.findField("HARD_INFO").setValue('');
			
			view.contentPanel.form.findField("FOREIGN_MONEY").allowBlank = false;
			view.contentPanel.form.findField("CURRENCY").allowBlank = false;
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
			view.contentPanel.form.findField("CUST_TYPE").allowBlank = false;
			view.contentPanel.form.findField("CASE_TYPE").allowBlank = false;		
			view.contentPanel.form.findField("SUC_PROBABILITY").allowBlank = false;
			
			if(view.contentPanel.form.findField("CUST_TYPE").getValue() == '2'){//客户类型是否为 旧户增贷
				view.contentPanel.form.findField("IF_ADD").show();
				view.contentPanel.form.findField("IF_ADD").allowBlank = false;
				
				if(view.contentPanel.form.findField("IF_ADD").getValue() == '1'){
					view.contentPanel.form.findField("ADD_AMT").show();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = false;
				}else{
					view.contentPanel.form.findField("ADD_AMT").hide();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
					view.contentPanel.form.findField("ADD_AMT").setValue('');
				}
			}else{
				view.contentPanel.form.findField("IF_ADD").hide();
				view.contentPanel.form.findField("IF_ADD").allowBlank = true;
				view.contentPanel.form.findField("IF_ADD").setValue('');
				
				view.contentPanel.form.findField("ADD_AMT").hide();
				view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
				view.contentPanel.form.findField("ADD_AMT").setValue('');
			}
		}else{//暂时维持本阶段
			view.contentPanel.form.findField("CP_HARD_INFO").show();
			view.contentPanel.form.findField("CP_HARD_INFO").allowBlank = false;
			
			if(view.contentPanel.form.findField("CP_HARD_INFO").value == '1'){
				view.contentPanel.form.findField("HARD_INFO").show();
				view.contentPanel.form.findField("HARD_INFO").allowBlank = false;
			}else{
				view.contentPanel.form.findField("HARD_INFO").hide();
				view.contentPanel.form.findField("HARD_INFO").allowBlank = true;
				view.contentPanel.form.findField("HARD_INFO").setValue('');
			}
			
			view.contentPanel.form.findField("FOREIGN_MONEY").allowBlank = false;
			view.contentPanel.form.findField("CURRENCY").allowBlank = false;
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
			view.contentPanel.form.findField("CUST_TYPE").allowBlank = false;
			view.contentPanel.form.findField("CASE_TYPE").allowBlank = false;		
			view.contentPanel.form.findField("SUC_PROBABILITY").allowBlank = false;
			
			
			if(view.contentPanel.form.findField("CUST_TYPE").getValue() == '2'){//客户类型是否为 旧户增贷
				view.contentPanel.form.findField("IF_ADD").show();
				view.contentPanel.form.findField("IF_ADD").allowBlank = false;
				
				if(view.contentPanel.form.findField("IF_ADD").getValue() == '1'){
					view.contentPanel.form.findField("ADD_AMT").show();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = false;
				}else{
					view.contentPanel.form.findField("ADD_AMT").hide();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
					view.contentPanel.form.findField("ADD_AMT").setValue('');
				}
			}else{
				view.contentPanel.form.findField("IF_ADD").hide();
				view.contentPanel.form.findField("IF_ADD").allowBlank = true;
				view.contentPanel.form.findField("IF_ADD").setValue('');
				
				view.contentPanel.form.findField("ADD_AMT").hide();
				view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
				view.contentPanel.form.findField("ADD_AMT").setValue('');
			}

		}
		
	}
	if(view._defaultTitle == '修改'){
		
		if(view.contentPanel.form.findField("IF_SECOND_STEP").getValue() == ''){
			view.contentPanel.form.findField("IF_SECOND_STEP").setValue('2');
		}
		if(view.contentPanel.form.findField("CASE_TYPE").getValue() == ''){
			view.contentPanel.form.findField("CASE_TYPE").setValue('16');
		}
		if(view.contentPanel.form.findField("CUST_TYPE").getValue() == ''){
			view.contentPanel.form.findField("CUST_TYPE").setValue('0');
		}
		
		
		if(view.contentPanel.form.findField("IF_SECOND_STEP") == '0'){//是否进入第二阶段   否
			view.contentPanel.form.findField("CP_HARD_INFO").show();
			view.contentPanel.form.findField("CP_HARD_INFO").allowBlank = false;
			
			if(view.contentPanel.form.findField("CP_HARD_INFO").value == '1'){
				view.contentPanel.form.findField("HARD_INFO").show();
				view.contentPanel.form.findField("HARD_INFO").allowBlank = false;
			}else{
				view.contentPanel.form.findField("HARD_INFO").hide();
				view.contentPanel.form.findField("HARD_INFO").allowBlank = true;
				view.contentPanel.form.findField("HARD_INFO").setValue('');
			}
			
			view.contentPanel.form.findField("FOREIGN_MONEY").allowBlank = true;
			view.contentPanel.form.findField("CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = true;
			view.contentPanel.form.findField("CUST_TYPE").allowBlank = true;
			view.contentPanel.form.findField("CASE_TYPE").allowBlank = true;	
			view.contentPanel.form.findField("IF_ADD").allowBlank = true;
			view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
			view.contentPanel.form.findField("SUC_PROBABILITY").allowBlank = true;
		}else if(view.contentPanel.form.findField("IF_SECOND_STEP") == '1'){//是
			view.contentPanel.form.findField("CP_HARD_INFO").hide();
			view.contentPanel.form.findField("CP_HARD_INFO").allowBlank = true;
			view.contentPanel.form.findField("CP_HARD_INFO").setValue('');
			
			view.contentPanel.form.findField("HARD_INFO").hide();
			view.contentPanel.form.findField("HARD_INFO").allowBlank = true;
			view.contentPanel.form.findField("HARD_INFO").setValue('');
			
			view.contentPanel.form.findField("FOREIGN_MONEY").allowBlank = false;
			view.contentPanel.form.findField("CURRENCY").allowBlank = false;
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
			view.contentPanel.form.findField("CUST_TYPE").allowBlank = false;
			view.contentPanel.form.findField("CASE_TYPE").allowBlank = false;		
			view.contentPanel.form.findField("SUC_PROBABILITY").allowBlank = false;
			
			if(view.contentPanel.form.findField("CUST_TYPE").getValue() == '2'){//客户类型是否为 旧户增贷
				view.contentPanel.form.findField("IF_ADD").show();
				view.contentPanel.form.findField("IF_ADD").allowBlank = false;
				
				if(view.contentPanel.form.findField("IF_ADD").getValue() == '1'){
					view.contentPanel.form.findField("ADD_AMT").show();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = false;
				}else{
					view.contentPanel.form.findField("ADD_AMT").hide();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
					view.contentPanel.form.findField("ADD_AMT").setValue('');
				}
			}else{
				view.contentPanel.form.findField("IF_ADD").hide();
				view.contentPanel.form.findField("IF_ADD").allowBlank = true;
				view.contentPanel.form.findField("IF_ADD").setValue('');
				
				view.contentPanel.form.findField("ADD_AMT").hide();
				view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
				view.contentPanel.form.findField("ADD_AMT").setValue('');
			}
		}else{//暂时维持本阶段
			view.contentPanel.form.findField("CP_HARD_INFO").show();
			view.contentPanel.form.findField("CP_HARD_INFO").allowBlank = false;
			
			if(view.contentPanel.form.findField("CP_HARD_INFO").value == '1'){
				view.contentPanel.form.findField("HARD_INFO").show();
				view.contentPanel.form.findField("HARD_INFO").allowBlank = false;
			}else{
				view.contentPanel.form.findField("HARD_INFO").hide();
				view.contentPanel.form.findField("HARD_INFO").allowBlank = true;
				view.contentPanel.form.findField("HARD_INFO").setValue('');
			}
		
			view.contentPanel.form.findField("FOREIGN_MONEY").allowBlank = false;
			view.contentPanel.form.findField("CURRENCY").allowBlank = false;
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = false;
			view.contentPanel.form.findField("CUST_TYPE").allowBlank = false;
			view.contentPanel.form.findField("CASE_TYPE").allowBlank = false;		
			view.contentPanel.form.findField("SUC_PROBABILITY").allowBlank = false;		
			
			if(view.contentPanel.form.findField("CUST_TYPE").getValue() == '2'){//客户类型是否为 旧户增贷
				view.contentPanel.form.findField("IF_ADD").show();
				view.contentPanel.form.findField("IF_ADD").allowBlank = false;
				
				if(view.contentPanel.form.findField("IF_ADD").getValue() == '1'){
					view.contentPanel.form.findField("ADD_AMT").show();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = false;
				}else{
					view.contentPanel.form.findField("ADD_AMT").hide();
					view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
					view.contentPanel.form.findField("ADD_AMT").setValue('');
				}
			}else{
				view.contentPanel.form.findField("IF_ADD").hide();
				view.contentPanel.form.findField("IF_ADD").allowBlank = true;
				view.contentPanel.form.findField("IF_ADD").setValue('');
				
				view.contentPanel.form.findField("ADD_AMT").hide();
				view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
				view.contentPanel.form.findField("ADD_AMT").setValue('');
			}
		}
		
		if(view.contentPanel.form.findField("CUST_TYPE").value == '2'){//客户类型是否为 旧户增贷
			view.contentPanel.form.findField("IF_ADD").show();
		}else{
			view.contentPanel.form.findField("IF_ADD").hide();
			view.contentPanel.form.findField("IF_ADD").allowBlank = true;
			view.contentPanel.form.findField("IF_ADD").setValue('');
			
			view.contentPanel.form.findField("ADD_AMT").hide();
			view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
			view.contentPanel.form.findField("ADD_AMT").setValue('');
		}
		
		if(view.contentPanel.form.findField("IF_ADD").value == '1'){//若为旧户增贷
			view.contentPanel.form.findField("ADD_AMT").show();
		}else{
			view.contentPanel.form.findField("ADD_AMT").hide();
			view.contentPanel.form.findField("ADD_AMT").allowBlank = true;
			view.contentPanel.form.findField("ADD_AMT").setValue('');
		}
	}
	
	if(view._defaultTitle == '详情'){
		
		if(view.contentPanel.form.findField("IF_SECOND_STEP") == '1'){
			view.contentPanel.form.findField("CP_HARD_INFO").hide();
			view.contentPanel.form.findField("HARD_INFO").hide();	

		}else{
			view.contentPanel.form.findField("CP_HARD_INFO").show();
			if(view.contentPanel.form.findField("CP_HARD_INFO").value == '1'){
				view.contentPanel.form.findField("HARD_INFO").show();				
			}else{
				view.contentPanel.form.findField("HARD_INFO").hide();			
			}
		}
		
		if(view.contentPanel.form.findField("CUST_TYPE").value == '2'){//客户类型是否为 旧户增贷
			view.contentPanel.form.findField("IF_ADD").show();
		}else{
			view.contentPanel.form.findField("IF_ADD").hide();			
			view.contentPanel.form.findField("ADD_AMT").hide();

		}
		
		if(view.contentPanel.form.findField("IF_ADD").value == '1'){//若为旧户增贷
			view.contentPanel.form.findField("ADD_AMT").show();
		}else{
			view.contentPanel.form.findField("ADD_AMT").hide();
		}
	
	}
};

var beforeconditioninit = function(panel, app) {
	app.pageSize = 20;
};

var afterconditionrender = function(panel, app) {
	app.searchDomain.searchPanel.getForm()
	.findField("PIPELINE_ID").setValue(parent.window.document.getElementById('condition').value);
};


