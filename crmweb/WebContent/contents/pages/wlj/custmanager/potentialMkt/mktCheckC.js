/**
 * @description 企商金客户营销流程 - 信用审查页面
 * @author luyy
 * @since 2014-07-25
 * @modify dongyi 2014-12-03
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


var url = basepath + '/mktCheckC.json';

var lookupTypes = ['IF_FLAG','CHECK_STAT','CASE_TYPE','GRADE_LEVEL_FINAL','ACC0600002','COMP_TYPE','SP_LEVEL','IF_FOURTH_STEP','REFUSE_REASON_CHECKC',
		{TYPE : 'AREA',//区域中心数据字典
			url : '/mktprospectC!searchArea.json',//此url为pipeline阶段所有区域中心字段的数据字典访问地址，后台逻辑存放于mktprospectCAction中
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
			  {name:"IF_FOURTH_STEP",text:"是否进入第四阶段",translateType:"IF_FOURTH_STEP",allowBlank:false,searchField: true},
			  {name:'CUST_NAME',text:'客户名称',xtype:'customerquerypp',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'AREA_NAME',text:'区域中心',translateType:"AREA",
	              resutlWidth:80,searchField: true, showField:'text',allowBlank:false,valueField:"value",listeners:{
				  select:function(a,b){
				  	a.setValue(b.data.value);
				}
				}},
              {name:'DEPT_NAME',text:'营业单位名称',xtype:'orgchoose',hiddenName:'DEPT_ID',allowBlank:false,searchField: true},
              {name:'RM',text:'RM',xtype:'userchoose',hiddenName:'RM_ID',resutlWidth:150,singleSelect: false,searchField: true},
			  {name:"CASE_TYPE",text:"案件类型",translateType:"CASE_TYPE",searchField: true,allowBlank:false,editable:true,resizable:true,listeners:{
            	  select:function(){
            		  var flag=this.value;
            		  if(flag != '2' && flag!='5' &&  flag!='6' &&  flag!='13'){
          				getCurrentView().contentPanel.form.findField("IF_ADD").hide();
          				getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank=true;
          				getCurrentView().contentPanel.form.findField("IF_ADD").setValue('');
          				getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
          				getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank=true;
          				getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
          			  }else{
          				getCurrentView().contentPanel.form.findField("IF_ADD").show();
          			  }
            	  }
              }},
			  {name:'APPLY_AMT',text:'申请额度(折人民币/千元)',dataType:'money',maxLength:24},
			  {name:'COMP_TYPE',text:'企业类型',translateType:'COMP_TYPE',gridField:true,allowBlank:false},
			  {name:'GRADE_LEVEL',text:'客户最终评级',translateType:'GRADE_LEVEL_FINAL',allowBlank:false,gridField:true},
			  {name:'IF_ADD',text:'若为旧案是否为增贷',gridField:true,translateType:'IF_FLAG',allowBlank:false,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_ADD").getValue();
					if(value == '1'){
						getCurrentView().contentPanel.form.findField("ADD_AMT").show();
						getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = false;
					}else if(value == '0'){
						getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
						getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
						getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
						
					}}
				}},
			  {name:'ADD_AMT',text:'增贷金额(折人民币/千元)',gridField:true,dataType:'money' ,maxLength:24,minValue:0},
			  {name:'CURRENCY',text:'申请额度币别',gridField:true,translateType:'CURRENCY',listeners:{
			  	select:function(){
			  		var flag=this.value;
			  		if(flag=='13'){
			  			var  FOREIGN_MONEY=getCurrentView().contentPanel.form.findField("FOREIGN_MONEY").getValue();
			  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(FOREIGN_MONEY*6);
			  		}else{
			  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue('');
			  		}
			  	}}},
			  {name:'FOREIGN_MONEY',text:'申请额度(原币金额/千元)',gridField:true,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24},
			  {name:'XD_CA_DATE',text:'信贷系统CA提交日期',gridField:true,dataType:'date',allowBlank:false },
			  {name:'CA_FORM',text:'信贷系统CA提交额度框架',gridField:true,allowBlank:false,xtype: 'textarea',maxLength:100},
			  {name:'QA_DATE',text:'信审提问Q&A日期',gridField:true,dataType:'date',allowBlank:false },
			  {name:'RM_DATE',text:'RM回复信审提问日期',gridField:true,dataType:'date',allowBlank:false },
			  {name:'CC_DATE',text:'CreditCall日期',gridField:true,dataType:'date',allowBlank:false }, 
			  {name:'XS_CC_DATE',text:'信审提出CreditCall意见日期',gridField:true,dataType:'date'},
			  {name:'RM_C_DATE',text:'RM反馈CreditCall意见日期',gridField:true,dataType:'date'},
			  {name:'CO',text:'对应CO',gridField:true,allowBlank:false,searchField: true},
			  {name:'MEMO',text:'备注',xtype:'textarea',gridField:true,maxLength:100},
			  {name:'ADD_CASE_DATE',text:'补件完成日期',dataType:'date',gridField:true},			
			  {name:'SP_LEVEL',text:'信贷审批签核层级',translateType:'SP_LEVEL',allowBlank:false,gridField:true,editable:true},
			  {name:'REFUSE_REASON',text:'未核准原因',translateType:'REFUSE_REASON_CHECKC',gridField:true,editable: true},	
			  {name:'REASON_REMARK',text:'未核准原因说明',xtype:'textarea',gridField:true,maxLength:500},
			  {name:'CHECK_PROGRESS',text:'信审进度',xtype:'textarea',allowBlank:false,maxLength:500},		
			  {name:'ADD_CASE_CONTENT',text:'补件主要内容',xtype:'textarea',allowBlank:false,maxLength:200},	
			  {name:'RECORD_DATE',text:'首次进入本阶段日期',dataType:'string',gridField:true},
              {name:'TREAMENT_DAYS',text:'本阶段案件处理天数',dataType:'string',gridField:true},//新增
              {name:'USER_ID',text:'USER_ID',gridField:false},
              {name:'ID',text:'ID',gridField:false},
              {name:'CUST_ID',text:'客户编号',gridField:false},
	          {name:'RM_ID',text:'RM编号',gridField:false},
	          {name:'DEPT_ID',text:'DEPT_ID',gridField:false},
	          {name:'AREA_ID',text:'区域编号',gridField:false},
	          {name:'CA_ID',text:'CA_ID',gridField:false},
			  {name:'PIPELINE_ID',text:'PIPELINE_ID',gridField:false},			 
			  {name:'IF_BACK',text:'IF_BACK',gridField:false}
			  ];

var tbar = [{
	text:'删除',
	hidden:JsContext.checkGrant('mktCheckCDelet'),
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
                url: basepath+'/mktCheckC!batchDel.json?idStr='+ID,                                
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
        url :  basepath + '/mktCheckC.json'
    }),{
	
	text:'恢复',
	hidden:JsContext.checkGrant('mktCheckCReview'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(getSelectedData().data.IF_FOURTH_STEP=='3'||getSelectedData().data.IF_FOURTH_STEP=='4'){

		    Ext.Ajax.request({
                url: basepath+'/mktCheckC!changeStat.json',   
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
	hideTitle:JsContext.checkGrant('mktCheckCEdit'),
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		labelWidth:160,
		columnCount:2,
		fields :[ {name:'CUST_NAME',text:'客户名称', allowBlank:false},
			      {name:'AREA_NAME',text:'区域中心', allowBlank:false},
			      {name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			      {name:'RM',text:'RM',resutlWidth:150},
			      'RECORD_DATE','TREAMENT_DAYS',//新增
			      'CASE_TYPE',
			      {name:"IF_FOURTH_STEP",text:"是否进入第四阶段",translateType:"IF_FOURTH_STEP",allowBlank:false,searchField: true,listeners:{
					select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_FOURTH_STEP").getValue();
					ifFourthStep=value;
					if(ifFourthStep == '1'||ifFourthStep == '6'){
						getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
						getCurrentView().contentPanel.form.findField("REFUSE_REASON").setValue('');
						getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
						getCurrentView().contentPanel.form.findField("REASON_REMARK").setValue('');
						getCurrentView().contentPanel.form.findField("REFUSE_REASON").allowBlank=true;;
						getCurrentView().contentPanel.form.findField("REASON_REMARK").allowBlank=true;;
					}else {
						getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
					}
					if(ifFourthStep == '5' || ifFourthStep == '4' || ifFourthStep == '3' ){//当不选择退回CA准备阶段时，去出某些字段不为空 验证条件
						getCurrentView().contentPanel.form.findField("CASE_TYPE").allowBlank=true;
						getCurrentView().contentPanel.form.findField("COMP_TYPE").allowBlank=true;
						getCurrentView().contentPanel.form.findField("GRADE_LEVEL").allowBlank=true;
						getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank=true;
						getCurrentView().contentPanel.form.findField("XD_CA_DATE").allowBlank=true;
						getCurrentView().contentPanel.form.findField("CA_FORM").allowBlank=true;
						getCurrentView().contentPanel.form.findField("QA_DATE").allowBlank=true;
						getCurrentView().contentPanel.form.findField("RM_DATE").allowBlank=true;
						getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank=true;
//						getCurrentView().contentPanel.form.findField("XS_CC_DATE").allowBlank=true;
//						getCurrentView().contentPanel.form.findField("RM_C_DATE").allowBlank=true;
						getCurrentView().contentPanel.form.findField("CO").allowBlank=true;
						getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank=true;
						getCurrentView().contentPanel.form.findField("APPLY_AMT").allowBlank=true;
						getCurrentView().contentPanel.form.findField("REFUSE_REASON").allowBlank=false;
						getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=true;
						getCurrentView().contentPanel.form.findField("ADD_CASE_CONTENT").allowBlank=true;
						
						getCurrentView().contentPanel.form.findField("CASE_TYPE").hide();
						getCurrentView().contentPanel.form.findField("COMP_TYPE").hide();
						getCurrentView().contentPanel.form.findField("GRADE_LEVEL").hide();
						getCurrentView().contentPanel.form.findField("IF_ADD").hide();
						getCurrentView().contentPanel.form.findField("XD_CA_DATE").hide();
						getCurrentView().contentPanel.form.findField("CA_FORM").hide();
						getCurrentView().contentPanel.form.findField("QA_DATE").hide();
						getCurrentView().contentPanel.form.findField("RM_DATE").hide();
						getCurrentView().contentPanel.form.findField("CC_DATE").hide();
						getCurrentView().contentPanel.form.findField("XS_CC_DATE").hide();
						getCurrentView().contentPanel.form.findField("RM_C_DATE").hide();
						getCurrentView().contentPanel.form.findField("CO").hide();
						getCurrentView().contentPanel.form.findField("SP_LEVEL").hide();
						getCurrentView().contentPanel.form.findField("APPLY_AMT").hide();
						getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").hide();
						getCurrentView().contentPanel.form.findField("ADD_CASE_CONTENT").hide();
						getCurrentView().contentPanel.form.findField("ADD_CASE_DATE").hide();
						
						getCurrentView().contentPanel.form.findField("XD_CA_DATE").setValue('');
						getCurrentView().contentPanel.form.findField("CA_FORM").setValue('');
						getCurrentView().contentPanel.form.findField("QA_DATE").setValue('');
						getCurrentView().contentPanel.form.findField("RM_DATE").setValue('');
						getCurrentView().contentPanel.form.findField("CC_DATE").setValue('');
						getCurrentView().contentPanel.form.findField("XS_CC_DATE").setValue('');
						getCurrentView().contentPanel.form.findField("RM_C_DATE").setValue('');
						getCurrentView().contentPanel.form.findField("CO").setValue('');
						getCurrentView().contentPanel.form.findField("SP_LEVEL").setValue('');
						getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").setValue();
						getCurrentView().contentPanel.form.findField("ADD_CASE_CONTENT").setValue();
						getCurrentView().contentPanel.form.findField("ADD_CASE_DATE").setValue();
					}else {
						getCurrentView().contentPanel.form.findField("CASE_TYPE").allowBlank=false;
						getCurrentView().contentPanel.form.findField("COMP_TYPE").allowBlank=false;
						getCurrentView().contentPanel.form.findField("GRADE_LEVEL").allowBlank=false;
						getCurrentView().contentPanel.form.findField("XD_CA_DATE").allowBlank=false;
						getCurrentView().contentPanel.form.findField("CA_FORM").allowBlank=false;
						getCurrentView().contentPanel.form.findField("QA_DATE").allowBlank=false;
						getCurrentView().contentPanel.form.findField("RM_DATE").allowBlank=false;
						getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank=false;
//						getCurrentView().contentPanel.form.findField("XS_CC_DATE").allowBlank=false;
//						getCurrentView().contentPanel.form.findField("RM_C_DATE").allowBlank=false;
						getCurrentView().contentPanel.form.findField("CO").allowBlank=false;
						getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank=false;
						getCurrentView().contentPanel.form.findField("REFUSE_REASON").allowBlank=true;
						getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=false;
						getCurrentView().contentPanel.form.findField("ADD_CASE_CONTENT").allowBlank=false;
						
						getCurrentView().contentPanel.form.findField("CASE_TYPE").show();
						getCurrentView().contentPanel.form.findField("COMP_TYPE").show();
						getCurrentView().contentPanel.form.findField("GRADE_LEVEL").show();
						var CASE_TYPE=getCurrentView().contentPanel.form.findField("CASE_TYPE").getValue();
						if(CASE_TYPE!='2' && CASE_TYPE!='5' && CASE_TYPE!='6' && CASE_TYPE!='13'){
							getCurrentView().contentPanel.form.findField("IF_ADD").hide();
							getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank=true;
						}else{
							getCurrentView().contentPanel.form.findField("IF_ADD").show();
							getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank=false;
						}
						getCurrentView().contentPanel.form.findField("XD_CA_DATE").show();
						getCurrentView().contentPanel.form.findField("CA_FORM").show();
						getCurrentView().contentPanel.form.findField("QA_DATE").show();
						getCurrentView().contentPanel.form.findField("RM_DATE").show();
						getCurrentView().contentPanel.form.findField("CC_DATE").show();
						getCurrentView().contentPanel.form.findField("XS_CC_DATE").show();
						getCurrentView().contentPanel.form.findField("RM_C_DATE").show();
						getCurrentView().contentPanel.form.findField("CO").show();
						getCurrentView().contentPanel.form.findField("SP_LEVEL").show();
						getCurrentView().contentPanel.form.findField("APPLY_AMT").show();
						getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").show();
						getCurrentView().contentPanel.form.findField("ADD_CASE_CONTENT").show();
						getCurrentView().contentPanel.form.findField("ADD_CASE_DATE").show();
					}
					if(value == '6'||value == '5'||value == '4'||value =='3'){
						getCurrentView().contentPanel.form.findField("CO").allowBlank=true;
						getCurrentView().contentPanel.form.findField("XD_CA_DATE").allowBlank=true;
						getCurrentView().contentPanel.form.findField("CA_FORM").allowBlank=true;
						getCurrentView().contentPanel.form.findField("QA_DATE").allowBlank=true;
						getCurrentView().contentPanel.form.findField("RM_DATE").allowBlank=true;
						getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank=true;
						getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank=true;
//						getCurrentView().contentPanel.form.findField("XS_CC_DATE").allowBlank=true;
//						getCurrentView().contentPanel.form.findField("RM_C_DATE").allowBlank=true;
						getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=true;
						getCurrentView().contentPanel.form.findField("ADD_CASE_CONTENT").allowBlank=true;
					}else{
						getCurrentView().contentPanel.form.findField("CO").allowBlank=false;
						getCurrentView().contentPanel.form.findField("XD_CA_DATE").allowBlank=false;
						getCurrentView().contentPanel.form.findField("CA_FORM").allowBlank=false;
						getCurrentView().contentPanel.form.findField("QA_DATE").allowBlank=false;
						getCurrentView().contentPanel.form.findField("RM_DATE").allowBlank=false;
						getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank=false;
						getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank=false;
//						getCurrentView().contentPanel.form.findField("XS_CC_DATE").allowBlank=false;
//						getCurrentView().contentPanel.form.findField("RM_C_DATE").allowBlank=false;
						getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=false;
						getCurrentView().contentPanel.form.findField("ADD_CASE_CONTENT").allowBlank=false;
					}
				}}},
				'FOREIGN_MONEY' ,'CO','CURRENCY','COMP_TYPE','APPLY_AMT','GRADE_LEVEL','IF_ADD',
				{name:'ADD_AMT',text:'<font color="red">*</font>增贷金额(折人民币/千元)',dataType:'money',minValue:0},
				'XD_CA_DATE','CA_FORM','QA_DATE','RM_DATE','CC_DATE','SP_LEVEL','XS_CC_DATE','RM_C_DATE','ADD_CASE_DATE',
				{name:'REFUSE_REASON',text:'<font color="red">*</font>未核准原因',translateType:'REFUSE_REASON_CHECKC',listeners:{
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
			'ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','CA_ID','PIPELINE_ID','IF_BACK'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,CASE_TYPE,IF_FOURTH_STEP,FOREIGN_MONEY,CO,CURRENCY,COMP_TYPE, APPLY_AMT,GRADE_LEVEL,IF_ADD
					,ADD_AMT,XD_CA_DATE,CA_FORM,QA_DATE,RM_DATE,CC_DATE,SP_LEVEL,XS_CC_DATE,RM_C_DATE,ADD_CASE_DATE,REFUSE_REASON,
					ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,CA_ID,PIPELINE_ID,IF_BACK){
				CUST_NAME.readOnly = true;
				CUST_NAME.cls = 'x-readOnly';
				AREA_NAME.readOnly = true;
				AREA_NAME.cls = 'x-readOnly';
				DEPT_NAME.readOnly = true;
				DEPT_NAME.cls = 'x-readOnly';
				RM.readOnly = true;
				RM.cls = 'x-readOnly';
				RECORD_DATE.readOnly = true;
				RECORD_DATE.cls = 'x-readOnly';
				TREAMENT_DAYS.readOnly = true;
				TREAMENT_DAYS.cls = 'x-readOnly';
				ID.hidden = true;
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				DEPT_ID.hidden = true;
				AREA_ID.hidden = true;
				CA_ID.hidden = true;
				PIPELINE_ID.hidden = true;
				IF_BACK.hidden = true;
				REFUSE_REASON.hidden = true;
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,CASE_TYPE,IF_FOURTH_STEP,FOREIGN_MONEY,CO,CURRENCY,COMP_TYPE, APPLY_AMT,GRADE_LEVEL,IF_ADD
					,ADD_AMT,XD_CA_DATE,CA_FORM,QA_DATE,RM_DATE,CC_DATE,SP_LEVEL,XS_CC_DATE,RM_C_DATE,ADD_CASE_DATE,REFUSE_REASON,
					ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,CA_ID,PIPELINE_ID,IF_BACK];
			}
	},{
		  columnCount: 1,
		  fields : ['MEMO',
					{name:'REASON_REMARK',text:'<font color="red">*</font>未核准原因说明',xtype:'textarea',maxLength:500},
					'CHECK_PROGRESS','ADD_CASE_CONTENT'],
		  fn : function(MEMO,REASON_REMARK,CHECK_PROGRESS,ADD_CASE_CONTENT){
		  	REASON_REMARK.hidden = true;
			  return [MEMO,REASON_REMARK,CHECK_PROGRESS,ADD_CASE_CONTENT];
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
	            
	             var XS_CC_DATE=formPanel.form.findField("XS_CC_DATE").getValue();//信审提出CreditCall日期
	             var CC_DATE=formPanel.form.findField("CC_DATE").getValue();//CreditCall日期
	             var RM_C_DATE=formPanel.form.findField("RM_C_DATE").getValue();//RM反馈CreditCall意见日期
	             if(XS_CC_DATE !='' && CC_DATE!='' ){
	            	 if(XS_CC_DATE < CC_DATE){
	            		 Ext.MessageBox.alert('提示','信审提出CreditCall日期应该大于等于CreditCall日期');
	            		 return false;
	            	 }
	             }
	             if(XS_CC_DATE!='' && RM_C_DATE!=''){
	            	 if(RM_C_DATE < XS_CC_DATE){
	            		 Ext.MessageBox.alert('提示','RM反馈CreditCall意见日期应该大于等于信审提出CreditCall日期');
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
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/mktCheckC!save.json',
							method : 'GET',
							params : commintData,
							success : function(response) {
									if(ifFourthStep=='1'){//需要在下一阶段进行数据存储
										var ret = Ext.decode(response.responseText);
										Ext.Ajax.request({
											url : basepath + '/mktApprovlC!save.json',//把数据转入核批阶段，并把数据保存进核批阶段
											method : 'GET',
											params : ret,
											success : function(response) {
												Ext.MessageBox.alert('提示','保存数据成功,请在核批阶段查看数据!');
											}
										})
										
									}else if(ifFourthStep=='5'){
											Ext.MessageBox.alert('提示','数据已经被退回至CA准备阶段!');
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
	hideTitle:JsContext.checkGrant('mktCheckCDetil'),
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		labelWidth:160,
		columnCount:2,
		fields :[ {name:'CUST_NAME',text:'客户名称', allowBlank:false},
			      {name:'AREA_NAME',text:'区域中心', allowBlank:false},
			      {name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			      {name:'RM',text:'RM',resutlWidth:150},
			      'RECORD_DATE','TREAMENT_DAYS',//新增
			      'CASE_TYPE',
			      {name:'CO',text:'对应CO'},
		         	{name:'IF_FOURTH_STEP',text:'是否进入第四阶段',translateType:'IF_FOURTH_STEP'},
		         	'APPLY_AMT',
		         	{name:'CO',text:'对应CO'},'CURRENCY','COMP_TYPE','FOREIGN_MONEY','GRADE_LEVEL','IF_ADD',
		         	{name:'ADD_AMT',text:'<font color="red">*</font>增贷金额(折人民币/千元)',dataType:'money',minValue:0},
					{name:'XD_CA_DATE',text:'信贷系统CA提交日期',dataType:'date'},
					{name:'CA_FORM',text:'信贷系统CA提交额度框架',xtype: 'textarea'},
					{name:'QA_DATE',text:'信审提问Q&A日期',gridField:true,dataType:'date'},
					{name:'RM_DATE',text:'RM回复信审提问日期',gridField:true,dataType:'date'},
					{name:'CC_DATE',text:'CreditCall日期',gridField:true,dataType:'date'}, 
			  		{name:"SP_LEVEL",text:"信贷审批签核层级",translateType:"SP_LEVEL"},
					{name:'XS_CC_DATE',text:'信审提出CreditCall意见日期',gridField:true,dataType:'date'},
					{name:'RM_C_DATE',text:'RM反馈CreditCall意见日期',gridField:true,dataType:'date' },
					'ADD_CASE_DATE',
					{name:'REFUSE_REASON',text:'<font color="red">*</font>未核准原因',translateType:'REFUSE_REASON_CHECKC',listeners:{
							select:function(){
								var value = getCurrentView().contentPanel.form.findField("REFUSE_REASON").getValue();
								if(value == '5'){
									getCurrentView().contentPanel.form.findField("REASON_REMARK").show();
								}else{
									getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
								}}
					}},	'ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','CA_ID','PIPELINE_ID','IF_BACK'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,CASE_TYPE,IF_FOURTH_STEP,APPLY_AMT,CO,CURRENCY,COMP_TYPE,FOREIGN_MONEY,GRADE_LEVEL,IF_ADD
					,ADD_AMT,XD_CA_DATE,CA_FORM,QA_DATE,RM_DATE,CC_DATE,SP_LEVEL,XS_CC_DATE,RM_C_DATE,ADD_CASE_DATE,REFUSE_REASON,
					ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,CA_ID,PIPELINE_ID,IF_BACK){
						
						CUST_NAME.readOnly = true;
						CUST_NAME.cls = 'x-readOnly';
						AREA_NAME.readOnly = true;
						AREA_NAME.cls = 'x-readOnly';
						DEPT_NAME.readOnly = true;
						DEPT_NAME.cls = 'x-readOnly';
						RM.readOnly = true;
						RM.cls = 'x-readOnly';
						RECORD_DATE.readOnly = true;
						RECORD_DATE.cls = 'x-readOnly';
						TREAMENT_DAYS.readOnly = true;
						TREAMENT_DAYS.cls = 'x-readOnly';
						CASE_TYPE.readOnly = true;
						CASE_TYPE.cls = 'x-readOnly';
						CURRENCY.readOnly = true;
						CURRENCY.cls = 'x-readOnly';
						FOREIGN_MONEY.readOnly = true;
						FOREIGN_MONEY.cls = 'x-readOnly';
						APPLY_AMT.readOnly = true;
						APPLY_AMT.cls = 'x-readOnly';
						COMP_TYPE.readOnly = true;
						COMP_TYPE.cls = 'x-readOnly';
						GRADE_LEVEL.readOnly = true;
						GRADE_LEVEL.cls = 'x-readOnly';
						IF_ADD.readOnly = true;
						IF_ADD.cls = 'x-readOnly';
						ADD_CASE_DATE.readOnly = true;
						ADD_CASE_DATE.cls = 'x-readOnly';
						REFUSE_REASON.readOnly = true;
						REFUSE_REASON.cls = 'x-readOnly';
						ADD_AMT.readOnly = true;
						ADD_AMT.cls = 'x-readOnly';
						XD_CA_DATE.readOnly = true;
						XD_CA_DATE.cls = 'x-readOnly';
						QA_DATE.readOnly = true;
						QA_DATE.cls = 'x-readOnly';
						RM_DATE.readOnly = true;
						RM_DATE.cls = 'x-readOnly';
						CC_DATE.readOnly = true;
						CC_DATE.cls = 'x-readOnly';
						XS_CC_DATE.readOnly = true;
						XS_CC_DATE.cls = 'x-readOnly';
						RM_C_DATE.readOnly = true;
						RM_C_DATE.cls = 'x-readOnly';
						SP_LEVEL.readOnly = true;
						SP_LEVEL.cls = 'x-readOnly';
						IF_FOURTH_STEP.readOnly = true;
						IF_FOURTH_STEP.cls = 'x-readOnly';
						ID.hidden = true;
						CUST_ID.hidden = true;
						RM_ID.hidden = true;
						DEPT_ID.hidden = true;
						AREA_ID.hidden = true;
						CA_ID.hidden = true;
						PIPELINE_ID.hidden = true;
						IF_BACK.hidden = true;
						CA_FORM.readOnly = true;
			  			CO.readOnly = true;
					  	CA_FORM.cls = 'x-readOnly';
					  	CO.cls = 'x-readOnly';
					
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,CASE_TYPE,IF_FOURTH_STEP,APPLY_AMT,CO,CURRENCY,COMP_TYPE,FOREIGN_MONEY,GRADE_LEVEL,IF_ADD
					,ADD_AMT,XD_CA_DATE,CA_FORM,QA_DATE,RM_DATE,CC_DATE,SP_LEVEL,XS_CC_DATE,RM_C_DATE,ADD_CASE_DATE,REFUSE_REASON,
					ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,CA_ID,PIPELINE_ID,IF_BACK];
			}
	},{
		  columnCount: 1,
		  fields : ['MEMO',
					{name:'REASON_REMARK',text:'<font color="red">*</font>未核准原因说明',xtype:'textarea',maxLength:500},
					{name:'CHECK_PROGRESS',text:'信审进度',xtype:'textarea'},
					{name:'ADD_CASE_CONTENT',text:'补件主要内容',xtype:'textarea'}],
		  fn : function(MEMO,REASON_REMARK,CHECK_PROGRESS,ADD_CASE_CONTENT){
			  	MEMO.readOnly = true;
			  	REASON_REMARK.readOnly = true;
			  	MEMO.cls = 'x-readOnly';
			  	REASON_REMARK.cls = 'x-readOnly';
			  	CHECK_PROGRESS.readOnly = true;
			  	CHECK_PROGRESS.cls = 'x-readOnly';
			  	ADD_CASE_CONTENT.readOnly = true;
			  	ADD_CASE_CONTENT.cls = 'x-readOnly';
			  return [MEMO,REASON_REMARK,CHECK_PROGRESS,ADD_CASE_CONTENT];
		  }
		}]
}];

var beforeviewshow = function(view){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
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
			view.contentPanel.form.findField("COMP_TYPE").show();
			view.contentPanel.form.findField("GRADE_LEVEL").show();
//			view.contentPanel.form.findField("IF_ADD").show();
			view.contentPanel.form.findField("XD_CA_DATE").show();
			view.contentPanel.form.findField("CA_FORM").show();
			view.contentPanel.form.findField("QA_DATE").show();
			view.contentPanel.form.findField("RM_DATE").show();
			view.contentPanel.form.findField("CC_DATE").show();
			view.contentPanel.form.findField("XS_CC_DATE").show();
			view.contentPanel.form.findField("RM_C_DATE").show();
			view.contentPanel.form.findField("CO").show();
			view.contentPanel.form.findField("SP_LEVEL").show();
			view.contentPanel.form.findField("APPLY_AMT").show();
			view.contentPanel.form.findField("CHECK_PROGRESS").show();
			view.contentPanel.form.findField("ADD_CASE_CONTENT").show();
			view.contentPanel.form.findField("ADD_CASE_DATE").show();
		}
	}
};

var viewshow = function(view){
		if(getSelectedData().data.IF_ADD == '1'){
			getCurrentView().contentPanel.form.findField("ADD_AMT").show();
		}else {
			getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
		}
		if(view._defaultTitle == '修改'){
			if(getCurrentView().contentPanel.form.findField("IF_FOURTH_STEP").getValue()==''){
				view.contentPanel.form.findField("IF_FOURTH_STEP").setValue('6');
				getCurrentView().contentPanel.form.findField("CO").allowBlank=true;
				getCurrentView().contentPanel.form.findField("XD_CA_DATE").allowBlank=true;
				getCurrentView().contentPanel.form.findField("CA_FORM").allowBlank=true;
				getCurrentView().contentPanel.form.findField("QA_DATE").allowBlank=true;
				getCurrentView().contentPanel.form.findField("RM_DATE").allowBlank=true;
				getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank=true;
				getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank=true;
//				getCurrentView().contentPanel.form.findField("XS_CC_DATE").allowBlank=true;
//				getCurrentView().contentPanel.form.findField("RM_C_DATE").allowBlank=true;
				getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=true;
				getCurrentView().contentPanel.form.findField("ADD_CASE_CONTENT").allowBlank=true;
			}
			if(getCurrentView().contentPanel.form.findField("IF_FOURTH_STEP").getValue()=='6'){
				getCurrentView().contentPanel.form.findField("CO").allowBlank=true;
				getCurrentView().contentPanel.form.findField("XD_CA_DATE").allowBlank=true;
				getCurrentView().contentPanel.form.findField("CA_FORM").allowBlank=true;
				getCurrentView().contentPanel.form.findField("QA_DATE").allowBlank=true;
				getCurrentView().contentPanel.form.findField("RM_DATE").allowBlank=true;
				getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank=true;
				getCurrentView().contentPanel.form.findField("CC_DATE").allowBlank=true;
//				getCurrentView().contentPanel.form.findField("XS_CC_DATE").allowBlank=true;
//				getCurrentView().contentPanel.form.findField("RM_C_DATE").allowBlank=true;
				getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
				getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=true;
				getCurrentView().contentPanel.form.findField("ADD_CASE_CONTENT").allowBlank=true;
			}
			// 信审阶段的案件类型如果是‘旧户非CP专案’,‘旧户组合存贷’,‘现有授信户转入CB’,才需要显示‘若为旧案是否为增贷’  否则不显示
			var CASE_TYPE =getCurrentView().contentPanel.form.findField("CASE_TYPE").getValue();
				if(CASE_TYPE != '2' && CASE_TYPE!='5' &&  CASE_TYPE!='6' &&  CASE_TYPE!='13'){
					getCurrentView().contentPanel.form.findField("IF_ADD").hide();
					getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank=true;
					getCurrentView().contentPanel.form.findField("IF_ADD").setValue('');
					getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
					getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank=true;
					getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
				}else{
					getCurrentView().contentPanel.form.findField("IF_ADD").show();
					if(getCurrentView().contentPanel.form.findField("IF_ADD").getValue()=='0'
						||getCurrentView().contentPanel.form.findField("IF_ADD").getValue()==''){
						getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
					}
				}
		}
		if(view._defaultTitle == '详情'){
			var j = getCurrentView().contentPanel.form.findField("REFUSE_REASON").getValue();
			if(j == '5'){
				getCurrentView().contentPanel.form.findField("REASON_REMARK").show();
			}
			else{
			    getCurrentView().contentPanel.form.findField("REASON_REMARK").hide();
			}
			if(getCurrentView().contentPanel.form.findField("IF_FOURTH_STEP").getValue()=='1'
					||getCurrentView().contentPanel.form.findField("IF_FOURTH_STEP").getValue()==''
							||getCurrentView().contentPanel.form.findField("IF_FOURTH_STEP").getValue()=='6'){
				getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
			}else{
				getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
			}
			// 信审阶段的案件类型如果是‘旧户非CP专案’,‘旧户组合存贷’,‘现有授信户转入CB’,才需要显示‘若为旧案是否为增贷’  否则不显示
			var CASE_TYPE =getCurrentView().contentPanel.form.findField("CASE_TYPE").getValue();
				if(CASE_TYPE != '2' && CASE_TYPE!='5' &&  CASE_TYPE!='6' &&  CASE_TYPE!='13'){
					getCurrentView().contentPanel.form.findField("IF_ADD").hide();
					getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank=true;
					getCurrentView().contentPanel.form.findField("IF_ADD").setValue('');
					getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
					getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank=true;
					getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
				}else{
					getCurrentView().contentPanel.form.findField("IF_ADD").show();
					if(getCurrentView().contentPanel.form.findField("IF_ADD").getValue()=='0'
						||getCurrentView().contentPanel.form.findField("IF_ADD").getValue()==''){
						getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
					}
				}
		}
};

