/**
 * @description 中小企客户营销流程 - 信用审查页面
 * @author denghj
 * @since 2015-08-06
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
//	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
//	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldQZ.js'	//客户放大镜（企商金营销用）
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldPP.js'
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


var url = basepath + '/smeCheckE.json';

var lookupTypes = ['IF_FLAG','CHECK_STAT','CUST_TYPE_SME','CASE_TYPE_SME','ACC0600002','IF_FOURTH_STEP','REFUSE_REASON_CHECK_SME',
		{TYPE : 'AREA',//区域中心数据字典
			url : '/smeProspectE!searchArea.json',//此url为pipeline阶段所有区域中心字段的数据字典访问地址，后台逻辑存放于smeProspectEAction中
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
		]
};

var ifFourthStep=0;//此标志用于判断是否需要把信息录入第四阶段;值为1则需要值为5退回CA准备阶段
var fields = [
			  {name:'IF_FOURTH_STEP',text:'是否进入第四阶段',translateType:'IF_FOURTH_STEP',allowBlank:false,searchField: true,listeners:{
					select:function(){
						var flag = getCurrentView().contentPanel.form.findField("IF_FOURTH_STEP").getValue();						
						
						if(flag == '3' || flag == '4' || flag == '5'){//第四阶段为退案,撤案,退回CA准备阶段时 CO需填信息 隐藏
							getCurrentView().contentPanel.form.findField("XD_CA_DATE").hide();
							getCurrentView().contentPanel.form.findField("CA_FINISH_DATE").hide();
							getCurrentView().contentPanel.form.findField("TO_CO_DATE").hide();
							getCurrentView().contentPanel.form.findField("CO").hide();
							getCurrentView().contentPanel.form.findField("DOCU_CHECK").hide();
							getCurrentView().contentPanel.form.findField("QA_DATE").hide();
							getCurrentView().contentPanel.form.findField("RM_DATE").hide();
							getCurrentView().contentPanel.form.findField("VISIT_FACTORY_DATE").hide();
							getCurrentView().contentPanel.form.findField("REQUIRE_CASE_DATE").hide();
							getCurrentView().contentPanel.form.findField("ADD_CASE_DATE").hide();
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").hide();
							
							
							getCurrentView().contentPanel.form.findField("XD_CA_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CA_FINISH_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("TO_CO_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CO").allowBlank = true;
							getCurrentView().contentPanel.form.findField("DOCU_CHECK").allowBlank = true;
							getCurrentView().contentPanel.form.findField("QA_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("RM_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("VISIT_FACTORY_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("REQUIRE_CASE_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("ADD_CASE_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank = true;
						}else{
							getCurrentView().contentPanel.form.findField("XD_CA_DATE").show();
							getCurrentView().contentPanel.form.findField("CA_FINISH_DATE").show();
							getCurrentView().contentPanel.form.findField("TO_CO_DATE").show();
							getCurrentView().contentPanel.form.findField("CO").show();
							getCurrentView().contentPanel.form.findField("DOCU_CHECK").show();
							getCurrentView().contentPanel.form.findField("QA_DATE").show();
							getCurrentView().contentPanel.form.findField("RM_DATE").show();
							getCurrentView().contentPanel.form.findField("VISIT_FACTORY_DATE").show();
							getCurrentView().contentPanel.form.findField("REQUIRE_CASE_DATE").show();
							getCurrentView().contentPanel.form.findField("ADD_CASE_DATE").show();
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").show();
							
							getCurrentView().contentPanel.form.findField("XD_CA_DATE").allowBlank = false;
							getCurrentView().contentPanel.form.findField("CA_FINISH_DATE").allowBlank = false;
							getCurrentView().contentPanel.form.findField("TO_CO_DATE").allowBlank = false;
							getCurrentView().contentPanel.form.findField("CO").allowBlank = false;
							getCurrentView().contentPanel.form.findField("DOCU_CHECK").allowBlank = false;
							getCurrentView().contentPanel.form.findField("QA_DATE").allowBlank = false;
							getCurrentView().contentPanel.form.findField("RM_DATE").allowBlank = false;
							getCurrentView().contentPanel.form.findField("REQUIRE_CASE_DATE").allowBlank = false;
							getCurrentView().contentPanel.form.findField("ADD_CASE_DATE").allowBlank = false;
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank = false;
							
							if(getCurrentView().contentPanel.form.findField("CASE_TYPE").value == '1'){//案件类型为银票贴现时，访厂日期改为非必填
								getCurrentView().contentPanel.form.findField("VISIT_FACTORY_DATE").allowBlank = true;
							}else{
								if(flag == '1'){
									getCurrentView().contentPanel.form.findField("VISIT_FACTORY_DATE").allowBlank = false;
								}else{
									getCurrentView().contentPanel.form.findField("VISIT_FACTORY_DATE").allowBlank = true;
								}
							}
						}
						
						if(flag == '6' || flag == '2'){//第四阶段为 暂时维持本阶段 所填信息 可为空

							getCurrentView().contentPanel.form.findField("XD_CA_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CA_FINISH_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("TO_CO_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CO").allowBlank = true;
							getCurrentView().contentPanel.form.findField("DOCU_CHECK").allowBlank = true;
							getCurrentView().contentPanel.form.findField("QA_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("RM_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("VISIT_FACTORY_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("REQUIRE_CASE_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("ADD_CASE_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank = true;
							getCurrentView().contentPanel.form.findField("REFUSE_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").allowBlank = true;
							getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = true;
						}
						
						if(flag == '2' || flag == '3'|| flag == '4'|| flag == '5'){//第四阶段为否决,退案,撤案,退回CA准备阶段时 退件或拒绝原因 日期 显示
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").allowBlank = false;
							
							getCurrentView().contentPanel.form.findField("REFUSE_DATE").show();
							getCurrentView().contentPanel.form.findField("REFUSE_DATE").allowBlank = false;
						}else{
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").setValue('');
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").allowBlank = true;
							
							getCurrentView().contentPanel.form.findField("REFUSE_DATE").hide();
							getCurrentView().contentPanel.form.findField("REFUSE_DATE").setValue('');
							getCurrentView().contentPanel.form.findField("REFUSE_DATE").allowBlank = true;
							
							getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
							getCurrentView().contentPanel.form.findField("REASON_REMARK").setValue('');
							getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = true;
						}
					}}},
			  {name:'CUST_NAME',text:'客户名称',xtype:'customerquerypp',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'AREA_NAME',text:'区域中心',translateType:"AREA",
	              resutlWidth:80,searchField: true, showField:'text',allowBlank:false,valueField:"value",listeners:{
				  select:function(a,b){
				  	a.setValue(b.data.value);
				}
				}},
			  {name:'RM',text:'RM',xtype:'userchoose',hiddenName:'RM_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'DEPT_NAME',text:'营业单位名称',xtype:'orgchoose',hiddenName:'DEPT_ID',allowBlank:false,searchField: true},
              {name:'FOREIGN_MONEY',text:'申请额度(原币金额/千元)',gridField:true,dataType: 'decimal',allowBlank:false,viewFn: money('0,000.00'),minValue: 0,maxLength:24},
              	{name:'CURRENCY',text:'申请额度币别',gridField:true,translateType:'CURRENCY',allowBlank:false,listeners:{
			  	select:function(){
			  		var flag=this.value;
			  		if(flag=='13'){
			  			var  FOREIGN_MONEY=getCurrentView().contentPanel.form.findField("FOREIGN_MONEY").getValue();
			  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(FOREIGN_MONEY*6);
			  		}else{
			  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue('');
			  		}
			  	}}},			  
              {name:'APPLY_AMT',text:'申请额度(折人民币/千元)',dataType:'money',allowBlank:false,maxLength:24},
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
			  {name:'CASE_TYPE',text:'案件类型',translateType:'CASE_TYPE_SME',allowBlank:false,searchField: true,editable:true,resizable:true},
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
			  {name:'ADD_AMT',text:'增贷金额(折人民币/千元)',gridField:true,dataType:'money' ,maxLength:24,minValue:0},			 
			  {name:'XD_CA_DATE',text:'RM信贷系统提交日期',gridField:true,dataType:'date',allowBlank:false },
			  {name:'CA_FINISH_DATE',text:'CA准备完成时间',gridField:true,dataType:'date',allowBlank:false},
			  {name:'TO_CO_DATE',text:'系统分配至CO日期',gridField:true,dataType:'date',allowBlank:false},
			  {name:'CO',text:'对应CO',gridField:true,allowBlank:false,searchField: true},
			  {name:'DOCU_CHECK',text:'文件审查',gridField:true,allowBlank:false,dataType:'date'},
			  {name:'QA_DATE',text:'信审提问Q&A日期',gridField:true,dataType:'date',allowBlank:false },
			  {name:'RM_DATE',text:'RM回复信审提问日期',gridField:true,dataType:'date',allowBlank:false },
			  {name:'VISIT_FACTORY_DATE',text:'<font color=red>*</font>访厂完成日期(若为银票贴现，不必输)',gridField:true,dataType:'date'},
			  {name:'REQUIRE_CASE_DATE',text:'信审要求补件日期',gridField:true,dataType:'date',allowBlank:false },
			  {name:'ADD_CASE_DATE',text:'补件完成日期',dataType:'date',gridField:true},
			  {name:'MEMO',text:'备注',xtype:'textarea',gridField:true,maxLength:100},
			  {name:'CHECK_PROGRESS',text:'信审进度',xtype:'textarea',allowBlank:false,maxLength:500},	
			  {name:'REFUSE_DATE',text:'退件或拒绝日期',gridField:true,dataType:'date'},
			  {name:'REFUSE_REASON',text:'退件或拒绝原因',translateType:'REFUSE_REASON_CHECK_SME',listeners:{
					select:function(){
						var value = getCurrentView().contentPanel.form.findField("REFUSE_REASON").getValue();
						if(value == '5'){
							getCurrentView().contentPanel.form.findField("REASON_REMARK").show();
							getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = false;
						}else{
							getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
							getCurrentView().contentPanel.form.findField("REASON_REMARK").setValue('');
							getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank = true;
						}}
				}},	
			  {name:'REASON_REMARK',text:'退件或拒绝原因说明',xtype:'textarea',gridField:true,maxLength:500},
              {name:'USER_ID',text:'USER_ID',gridField:false},
              {name:'ID',text:'ID',gridField:false},
              {name:'CUST_ID',text:'客户编号',gridField:false},
	          {name:'RM_ID',text:'RM编号',gridField:false},
	          {name:'DEPT_ID',text:'DEPT_ID',gridField:false},
	          {name:'AREA_ID',text:'区域编号',gridField:false},
	          {name:'CA_ID',text:'CA_ID',gridField:false},
	          {name:'PIPELINE_ID',text:'PIPELINE编号',gridField:false,searchField: true},
			  {name:'RECORD_DATE',text:'RECORD_DATE',gridField:false},
			  {name:'IF_BACK',text:'IF_BACK',gridField:false}
			  ];

var tbar = [{
	text:'删除',
	hidden:JsContext.checkGrant('smeCheckEDelet'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}

		var ID = '';
		for (var i=0;i<getAllSelects().length;i++){
			ID += getAllSelects()[i].data.ID;
			ID += ",";
		}
		ID = ID.substring(0, ID.length-1);
		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
			return false;
			} 
		    Ext.Ajax.request({
                url: basepath+'/smeCheckE!batchDel.json?idStr='+ID,                                
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
        url :  basepath + '/smeCheckE.json'
    }),{
	
	text:'恢复',
	hidden:JsContext.checkGrant('smeCheckERecover'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(getSelectedData().data.IF_FOURTH_STEP=='3'||getSelectedData().data.IF_FOURTH_STEP=='4'){

		    Ext.Ajax.request({
                url: basepath+'/smeCheckE!changeStat.json',   
                method : 'POST',
                params : {
						id : getSelectedData().data.ID
					},                             
                success : function(){
                    Ext.Msg.alert('提示', '恢复成功');
                    reloadCurrentData();
                },
                failure : function(){
                    Ext.Msg.alert('提示', '恢复失败');
                    reloadCurrentData();
                }
            });
		}else{
		 Ext.Msg.alert('提示', '此状态禁止恢复！');
	}	
}
}];

var customerView = [{
	title:'修改',
	hideTitle:JsContext.checkGrant('smeCheckEEdit'),
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		labelWidth:160,
		columnCount:2,
		fields :[ 
				 {name:'CUST_NAME',text:'客户名称', allowBlank:false},
		         {name:'AREA_NAME',text:'区域中心', allowBlank:false},
		         {name:'DEPT_NAME',text:'营业部门',allowBlank:false},
		         {name:'RM',text:'RM',resutlWidth:150,allowBlank:false},
		         'CASE_TYPE','CURRENCY','CUST_TYPE','FOREIGN_MONEY','IF_ADD','APPLY_AMT','ADD_AMT','XD_CA_DATE',
		         'CA_FINISH_DATE','DOCU_CHECK','CO','REQUIRE_CASE_DATE','TO_CO_DATE','ADD_CASE_DATE',
		         'QA_DATE','IF_FOURTH_STEP','RM_DATE','REFUSE_DATE','VISIT_FACTORY_DATE','REFUSE_REASON',		        								
				'ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','CA_ID','PIPELINE_ID','RECORD_DATE','IF_BACK'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,
					  	CASE_TYPE,CURRENCY,CUST_TYPE,FOREIGN_MONEY,IF_ADD,APPLY_AMT,ADD_AMT,XD_CA_DATE,
				        CA_FINISH_DATE,DOCU_CHECK,CO,REQUIRE_CASE_DATE,TO_CO_DATE,ADD_CASE_DATE,
				        QA_DATE,IF_FOURTH_STEP,RM_DATE,REFUSE_DATE,VISIT_FACTORY_DATE,REFUSE_REASON,
				        ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,CA_ID,PIPELINE_ID,RECORD_DATE,IF_BACK){
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
				CURRENCY.readOnly = true;
				CURRENCY.cls = 'x-readOnly';
				APPLY_AMT.readOnly = true;
				APPLY_AMT.cls = 'x-readOnly';
				CUST_TYPE.readOnly = true;
				CUST_TYPE.cls = 'x-readOnly';
				CASE_TYPE.readOnly = true;
				CASE_TYPE.cls = 'x-readOnly';
				IF_ADD.readOnly = true;
				IF_ADD.cls = 'x-readOnly';
				ADD_AMT.readOnly = true;
				ADD_AMT.cls = 'x-readOnly';
				XD_CA_DATE.readOnly = true;
				XD_CA_DATE.cls = 'x-readOnly';
				
				ID.hidden = true;
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				DEPT_ID.hidden = true;
				AREA_ID.hidden = true;
				CA_ID.hidden = true;
				PIPELINE_ID.hidden = true;
				RECORD_DATE.hidden = true;
				IF_BACK.hidden = true;
				REFUSE_REASON.hidden = true;
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,
					  	CASE_TYPE,CURRENCY,CUST_TYPE,FOREIGN_MONEY,IF_ADD,APPLY_AMT,ADD_AMT,XD_CA_DATE,
				        CA_FINISH_DATE,DOCU_CHECK,CO,REQUIRE_CASE_DATE,TO_CO_DATE,ADD_CASE_DATE,
				        QA_DATE,IF_FOURTH_STEP,RM_DATE,REFUSE_DATE,VISIT_FACTORY_DATE,REFUSE_REASON,
				        ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,CA_ID,PIPELINE_ID,RECORD_DATE,IF_BACK];
			}
	},{
		  columnCount: 1,
		  fields : ['CHECK_PROGRESS','REASON_REMARK','MEMO'],
		  fn : function(MEMO,REASON_REMARK,CHECK_PROGRESS){
		  	REASON_REMARK.hidden = true;
			  return [MEMO,REASON_REMARK,CHECK_PROGRESS];
		  }
		}],
		formButtons : [{
			text:'提交',
			fn : function(formPanel,basicForm){
				 if (!formPanel.getForm().isValid()) {
		               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		               return false;
		           };
	             var date1 = formPanel.form.findField("QA_DATE").getValue();
	             var date2 = formPanel.form.findField("RM_DATE").getValue();
	             if(date1!='' && date2 !=''){
	            	 if(date1 > date2){
	 	             	Ext.MessageBox.alert('提示','RM回复信审问题日期应该晚于或等于信审提问Q&A日期！');
	 	             	return false;
	 	             }
	             }
	             
	             //申请额度（折人民币/千元）’与‘申请额度（原币金额/千元）’如果‘申请额度币别’是RMB那么两个额度应该相同
	             var CURRENCY=formPanel.form.findField("CURRENCY").getValue();
	             var APPLY_AMT=formPanel.form.findField("APPLY_AMT").getValue();//申请额度折人民币
	             var FOREIGN_MONEY=formPanel.form.findField("FOREIGN_MONEY").getValue();//申请额度原币别
	             if(CURRENCY=='RMB'){
	            	 if(APPLY_AMT!=FOREIGN_MONEY){
	            		 Ext.MessageBox.alert('提示','申请额度币别为RMB，申请额度（折人民币/千元）应与申请额度（原币金额/千元）相等');
	            		 return false;
	            	 }
	             }
		         var data = formPanel.getForm().getFieldValues();
		         if(((data.APPLY_AMT==0||data.APPLY_AMT=='')&&(data.FOREIGN_MONEY!=0||data.FOREIGN_MONEY!='')&&data.IF_FOURTH_STEP=='1')
			        		||((data.APPLY_AMT!=0||data.APPLY_AMT!='')&&(data.FOREIGN_MONEY==0||data.FOREIGN_MONEY=='')&&data.IF_FOURTH_STEP=='1')){
			        	Ext.MessageBox.alert('提示','申请额度（折人民币/千元）或申请额度（原币金额/千元）不允许为零！');
			               return false;
			      }		        		       
		         
					var commintData = translateDataKey(data,1);
					ifFourthStep=commintData.ifFourthStep;
					if(ifFourthStep == '2'){
		        	   if(formPanel.form.findField("REFUSE_REASON").getValue() == ''){
		        		   Ext.MessageBox.alert('提示','请填写[未核准原因]');
			               return false;
		        	   }
			        }
					
					  if(data.ADD_CASE_DATE == '' && ifFourthStep == '2'){//提交至下一阶段时，补件完成日期不能为空
				        	 Ext.MessageBox.alert('提示','请填写[补件完成日期]');
				               return false;
				         }
					
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/smeCheckE!save.json',
							method : 'GET',
							params : commintData,
							success : function(response) {
									if(ifFourthStep=='1'){//需要在下一阶段进行数据存储
										var ret = Ext.decode(response.responseText);
										Ext.Ajax.request({
											url : basepath + '/smeApprovlE!save.json',//把数据转入核批阶段，并把数据保存进核批阶段
											method : 'GET',
											params : ret,
											success : function(response) {
												Ext.MessageBox.alert('提示','保存数据成功,请在核批阶段查看数据!');
											}
										})
										
									}else if(ifFourthStep=='5'){
											if(data.CASE_TYPE = '16'){
												Ext.MessageBox.alert('提示','数据已经被退回至CA及文件准备阶段!');
											}else{
												Ext.MessageBox.alert('提示','数据已经被退回至文件准备阶段!');
											}											
										}else{
												Ext.MessageBox.alert('提示','数据保存成功!');
											}
									hideCurrentView(); 
									reloadCurrentData();
								}
							}); 
		        	   
			}
		}]
},{
	title:'详情',
	hideTitle:JsContext.checkGrant('smeCheckEDetail'),
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		labelWidth:160,
		columnCount:2,
		fields :[ 
				 {name:'CUST_NAME',text:'客户名称', allowBlank:false},
		         {name:'AREA_NAME',text:'区域中心', allowBlank:false},
		         {name:'DEPT_NAME',text:'营业部门',allowBlank:false},
		         {name:'RM',text:'RM',resutlWidth:150,allowBlank:false},
		         'CASE_TYPE','CURRENCY','CUST_TYPE','FOREIGN_MONEY','IF_ADD','APPLY_AMT','ADD_AMT','XD_CA_DATE',
		         'CA_FINISH_DATE','DOCU_CHECK','CO','REQUIRE_CASE_DATE','TO_CO_DATE','ADD_CASE_DATE',
		         'QA_DATE','IF_FOURTH_STEP','RM_DATE','REFUSE_DATE','VISIT_FACTORY_DATE','REFUSE_REASON',		        								
				'ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','CA_ID','PIPELINE_ID','RECORD_DATE','IF_BACK'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,
				  		CASE_TYPE,CURRENCY,CUST_TYPE,FOREIGN_MONEY,IF_ADD,APPLY_AMT,ADD_AMT,XD_CA_DATE,
				  		CA_FINISH_DATE,DOCU_CHECK,CO,REQUIRE_CASE_DATE,TO_CO_DATE,ADD_CASE_DATE,
				  		QA_DATE,IF_FOURTH_STEP,RM_DATE,REFUSE_DATE,VISIT_FACTORY_DATE,REFUSE_REASON,
				  		ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,CA_ID,PIPELINE_ID,RECORD_DATE,IF_BACK){
						
						CUST_NAME.readOnly = true;
						CUST_NAME.cls = 'x-readOnly';
						AREA_NAME.readOnly = true;
						AREA_NAME.cls = 'x-readOnly';
						DEPT_NAME.readOnly = true;
						DEPT_NAME.cls = 'x-readOnly';
						RM.readOnly = true;
						RM.cls = 'x-readOnly';
						IF_FOURTH_STEP.readOnly = true;
						IF_FOURTH_STEP.cls = 'x-readOnly';
						FOREIGN_MONEY.readOnly = true;
						FOREIGN_MONEY.cls = 'x-readOnly';
						CURRENCY.readOnly = true;
						CURRENCY.cls = 'x-readOnly';						
						APPLY_AMT.readOnly = true;
						APPLY_AMT.cls = 'x-readOnly';
						CUST_TYPE.readOnly = true;
						CUST_TYPE.cls = 'x-readOnly';
						CASE_TYPE.readOnly = true;
						CASE_TYPE.cls = 'x-readOnly';														
						IF_ADD.readOnly = true;
						IF_ADD.cls = 'x-readOnly';
						ADD_AMT.readOnly = true;
						ADD_AMT.cls = 'x-readOnly';
						XD_CA_DATE.readOnly = true;
						XD_CA_DATE.cls = 'x-readOnly';
						CA_FINISH_DATE.readOnly = true;
						CA_FINISH_DATE.cls = 'x-readOnly';
						TO_CO_DATE.readOnly = true;
						TO_CO_DATE.cls = 'x-readOnly';
						CO.readOnly = true;
					  	CO.cls = 'x-readOnly';
						DOCU_CHECK.readOnly = true;
						DOCU_CHECK.cls = 'x-readOnly';						
						QA_DATE.readOnly = true;
						QA_DATE.cls = 'x-readOnly';
						RM_DATE.readOnly = true;
						RM_DATE.cls = 'x-readOnly';
						VISIT_FACTORY_DATE.readOnly = true;
						VISIT_FACTORY_DATE.cls = 'x-readOnly';
						REQUIRE_CASE_DATE.readOnly = true;
						REQUIRE_CASE_DATE.cls = 'x-readOnly';
						ADD_CASE_DATE.readOnly = true;
						ADD_CASE_DATE.cls = 'x-readOnly';
						REFUSE_DATE.readOnly = true;
						REFUSE_DATE.cls = 'x-readOnly';						
						REFUSE_REASON.readOnly = true;
						REFUSE_REASON.cls = 'x-readOnly';				
												
						ID.hidden = true;
						CUST_ID.hidden = true;
						RM_ID.hidden = true;
						DEPT_ID.hidden = true;
						AREA_ID.hidden = true;
						CA_ID.hidden = true;
						PIPELINE_ID.hidden = true;
						RECORD_DATE.hidden = true;
						IF_BACK.hidden = true;
			  			
					
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,
					  	CASE_TYPE,CURRENCY,CUST_TYPE,FOREIGN_MONEY,IF_ADD,APPLY_AMT,ADD_AMT,XD_CA_DATE,
				        CA_FINISH_DATE,DOCU_CHECK,CO,REQUIRE_CASE_DATE,TO_CO_DATE,ADD_CASE_DATE,
				        QA_DATE,IF_FOURTH_STEP,RM_DATE,REFUSE_DATE,VISIT_FACTORY_DATE,REFUSE_REASON,
				        ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,CA_ID,PIPELINE_ID,RECORD_DATE,IF_BACK];
			}
	},{
		  columnCount: 1,
		  fields : ['CHECK_PROGRESS','REASON_REMARK','MEMO'],
		  fn : function(CHECK_PROGRESS,REASON_REMARK,MEMO){
		  	MEMO.readOnly = true;
		  	MEMO.cls = 'x-readOnly';
		  	REASON_REMARK.readOnly = true;
		  	REASON_REMARK.cls = 'x-readOnly';
		  	CHECK_PROGRESS.readOnly = true;
		  	CHECK_PROGRESS.cls = 'x-readOnly';
			  return [CHECK_PROGRESS,REASON_REMARK,MEMO];
		  }
		}]
}];

var beforeviewshow = function(view){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
	}
	if(view._defaultTitle == '修改'){
		if(getSelectedData().data.IF_FOURTH_STEP == '2'){
			Ext.Msg.alert('提示','案件已否决，不能修改');
			return false;
		}
	}
	if(view._defaultTitle == '修改'){
		if(getSelectedData().data.IF_FOURTH_STEP != '1'&&getSelectedData().data.IF_FOURTH_STEP != ''){
			view.contentPanel.form.findField("REFUSE_REASON").show();
		}else if(getSelectedData().data.IF_FOURTH_STEP == '1'||getSelectedData().data.IF_FOURTH_STEP == ''){
			view.contentPanel.form.findField("REFUSE_REASON").hide();
		}
		if(getSelectedData().data.IF_FOURTH_STEP=='3'
		      ||getSelectedData().data.IF_FOURTH_STEP == '4'){
		      	Ext.Msg.alert('提示','请先恢复案件,再修改！');
		      	return false;
		}
		if(getSelectedData().data.IF_FOURTH_STEP != '3'||getSelectedData().data.IF_FOURTH_STEP != '4'||getSelectedData().data.IF_FOURTH_STEP != '5'){
			view.contentPanel.form.findField("CASE_TYPE").show();
			view.contentPanel.form.findField("XD_CA_DATE").show();
			view.contentPanel.form.findField("QA_DATE").show();
			view.contentPanel.form.findField("RM_DATE").show();
			view.contentPanel.form.findField("CO").show();
			view.contentPanel.form.findField("APPLY_AMT").show();
		}
	}
};

var viewshow = function(view){
	
		if(view.contentPanel.form.findField("CO").value == ''){//对应CO 默认为 当前用户
			view.contentPanel.form.findField("CO").setValue(__userName);	
		}
		
		if(view.contentPanel.form.findField("IF_FOURTH_STEP").value == ''){//是否进入第四阶段 默认为 暂时维持本阶段
			view.contentPanel.form.findField("IF_FOURTH_STEP").setValue('6');	
		}
		
		if(view.contentPanel.form.findField().value == '1'){//案件类型为银票贴现时，访厂日期改为非必填
			
		}
		
		
		if(getSelectedData().data.CUST_TYPE == '2'){
			getCurrentView().contentPanel.form.findField("IF_ADD").show();
			getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = false;
			
			if(getSelectedData().data.IF_ADD == '1'){
				getCurrentView().contentPanel.form.findField("ADD_AMT").show();
				getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = false;
			}else {
				getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
				getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
				getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
			}
		}else{
			getCurrentView().contentPanel.form.findField("IF_ADD").hide();
			getCurrentView().contentPanel.form.findField("IF_ADD").setValue('');
			getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = true;
			
			getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
			getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
			getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
		}
	
	
					
			if(view.contentPanel.form.findField("IF_FOURTH_STEP").value == '3' 
			|| view.contentPanel.form.findField("IF_FOURTH_STEP").value == '4' 
			|| view.contentPanel.form.findField("IF_FOURTH_STEP").value == '5'){//第四阶段为退案,撤案,退回CA准备阶段时 CO需填信息 隐藏
				
				view.contentPanel.form.findField("XD_CA_DATE").hide();
				view.contentPanel.form.findField("CA_FINISH_DATE").hide();
				view.contentPanel.form.findField("TO_CO_DATE").hide();
				view.contentPanel.form.findField("CO").hide();
				view.contentPanel.form.findField("DOCU_CHECK").hide();
				view.contentPanel.form.findField("QA_DATE").hide();
				view.contentPanel.form.findField("RM_DATE").hide();
				view.contentPanel.form.findField("VISIT_FACTORY_DATE").hide();
				view.contentPanel.form.findField("REQUIRE_CASE_DATE").hide();
				view.contentPanel.form.findField("ADD_CASE_DATE").hide();
				view.contentPanel.form.findField("CHECK_PROGRESS").hide();				
				
				view.contentPanel.form.findField("XD_CA_DATE").allowBlank = true;
				view.contentPanel.form.findField("CA_FINISH_DATE").allowBlank = true;
				view.contentPanel.form.findField("TO_CO_DATE").allowBlank = true;
				view.contentPanel.form.findField("CO").allowBlank = true;
				view.contentPanel.form.findField("DOCU_CHECK").allowBlank = true;
				view.contentPanel.form.findField("QA_DATE").allowBlank = true;
				view.contentPanel.form.findField("RM_DATE").allowBlank = true;
				view.contentPanel.form.findField("VISIT_FACTORY_DATE").allowBlank = true;
				view.contentPanel.form.findField("REQUIRE_CASE_DATE").allowBlank = true;
				view.contentPanel.form.findField("ADD_CASE_DATE").allowBlank = true;
				view.contentPanel.form.findField("CHECK_PROGRESS").allowBlank = true;
			}else{
				view.contentPanel.form.findField("XD_CA_DATE").show();
				view.contentPanel.form.findField("CA_FINISH_DATE").show();
				view.contentPanel.form.findField("TO_CO_DATE").show();
				view.contentPanel.form.findField("CO").show();
				view.contentPanel.form.findField("DOCU_CHECK").show();
				view.contentPanel.form.findField("QA_DATE").show();
				view.contentPanel.form.findField("RM_DATE").show();
				view.contentPanel.form.findField("VISIT_FACTORY_DATE").show();
				view.contentPanel.form.findField("REQUIRE_CASE_DATE").show();
				view.contentPanel.form.findField("ADD_CASE_DATE").show();
				view.contentPanel.form.findField("CHECK_PROGRESS").show();
				
				view.contentPanel.form.findField("XD_CA_DATE").allowBlank = false;
				view.contentPanel.form.findField("CA_FINISH_DATE").allowBlank = false;
				view.contentPanel.form.findField("TO_CO_DATE").allowBlank = false;
				view.contentPanel.form.findField("CO").allowBlank = false;
				view.contentPanel.form.findField("DOCU_CHECK").allowBlank = false;
				view.contentPanel.form.findField("QA_DATE").allowBlank = false;
				view.contentPanel.form.findField("RM_DATE").allowBlank = false;
				view.contentPanel.form.findField("REQUIRE_CASE_DATE").allowBlank = false;
				view.contentPanel.form.findField("ADD_CASE_DATE").allowBlank = false;
				view.contentPanel.form.findField("CHECK_PROGRESS").allowBlank = false;
				
				if(view.contentPanel.form.findField("CASE_TYPE").value == '1'){//案件类型为银票贴现时，访厂日期改为非必填
					view.contentPanel.form.findField("VISIT_FACTORY_DATE").allowBlank = true;
				}else{
					if(view.contentPanel.form.findField("IF_FOURTH_STEP").value == '1'){
						view.contentPanel.form.findField("VISIT_FACTORY_DATE").allowBlank = false;
					}else{
						view.contentPanel.form.findField("VISIT_FACTORY_DATE").allowBlank = true;
					}
				}
			}
			
			if(view.contentPanel.form.findField("IF_FOURTH_STEP").value == '6'
				||view.contentPanel.form.findField("IF_FOURTH_STEP").value == '2'){//第四阶段为 暂时维持本阶段 所填信息 可为空
				view.contentPanel.form.findField("XD_CA_DATE").allowBlank = true;
				view.contentPanel.form.findField("CA_FINISH_DATE").allowBlank = true;
				view.contentPanel.form.findField("TO_CO_DATE").allowBlank = true;
				view.contentPanel.form.findField("CO").allowBlank = true;
				view.contentPanel.form.findField("DOCU_CHECK").allowBlank = true;
				view.contentPanel.form.findField("QA_DATE").allowBlank = true;
				view.contentPanel.form.findField("RM_DATE").allowBlank = true;
				view.contentPanel.form.findField("VISIT_FACTORY_DATE").allowBlank = true;
				view.contentPanel.form.findField("REQUIRE_CASE_DATE").allowBlank = true;
				view.contentPanel.form.findField("ADD_CASE_DATE").allowBlank = true;
				view.contentPanel.form.findField("CHECK_PROGRESS").allowBlank = true;
				view.contentPanel.form.findField("REFUSE_DATE").allowBlank = true;
				view.contentPanel.form.findField("REFUSE_REASON").allowBlank = true;
				view.contentPanel.form.findField("REASON_REMARK").allowBlank = true;
			}
			
			if(view.contentPanel.form.findField("IF_FOURTH_STEP").value == '2' 
			|| view.contentPanel.form.findField("IF_FOURTH_STEP").value == '3'
			|| view.contentPanel.form.findField("IF_FOURTH_STEP").value == '4'
			|| view.contentPanel.form.findField("IF_FOURTH_STEP").value == '5'){//第四阶段为否决,退案,撤案,退回CA准备阶段时 退件或拒绝原因 日期 显示
				
				view.contentPanel.form.findField("REFUSE_REASON").show();
				view.contentPanel.form.findField("REFUSE_REASON").allowBlank = false;
				
				view.contentPanel.form.findField("REFUSE_DATE").show();
				view.contentPanel.form.findField("REFUSE_DATE").allowBlank = false;
			}else{
				view.contentPanel.form.findField("REFUSE_REASON").hide();
				view.contentPanel.form.findField("REFUSE_REASON").setValue('');
				view.contentPanel.form.findField("REFUSE_REASON").allowBlank = true;
				
				view.contentPanel.form.findField("REFUSE_DATE").hide();
				view.contentPanel.form.findField("REFUSE_DATE").setValue('');
				view.contentPanel.form.findField("REFUSE_DATE").allowBlank = true;
				
				view.contentPanel.form.findField("REASON_REMARK").hide();
				view.contentPanel.form.findField("REASON_REMARK").setValue('');
				view.contentPanel.form.findField("REASON_REMARK").allowBlank = true;
			}				
};
var afterconditionrender = function(panel, app) {
	app.searchDomain.searchPanel.getForm()
	.findField("PIPELINE_ID").setValue(parent.window.document.getElementById('condition').value);
};
