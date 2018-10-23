/**
 * @description 中小企客户营销流程 - 文件收集阶段(CO)
 * @author denghj
 * @since 2015-08-07
 */
 imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
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

var url = basepath + '/smeCaCo.json';

var lookupTypes = ['IF_FLAG','CHECK_STAT','CUST_TYPE_SME','CASE_TYPE_SME','ACC0600002','IF_THIRD_STEP_SME','CA_HARD_INFO','IF_COCOMEETING','GRADE_PERSECT_SME',
	{   TYPE : 'AREA',//区域中心数据字典
		url : '/smeProspectE!searchArea.json',//此url为pipeline阶段所有区域中心字段的数据字典访问地址，后台逻辑存放于smeProspectEAction中
		key : 'KEY',
		value : 'VALUE',
		root : 'data'
	}];
	
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

var ifThirdStep=0;//此标志用于判断是否需要把信息录入第三阶段;值为1则需要
var fields = [
			{name:"IF_THIRD_STEP",text:"是否进入第三阶段",translateType:"IF_THIRD_STEP_SME",allowBlank:false,searchField: true,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_THIRD_STEP").getValue();
					if(value == '0'){//是否进入第三阶段   否
			
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").allowBlank = false;
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").show();
						getCurrentView().contentPanel.form.findField("GRADE_PERSECT").allowBlank = false;
				
						
					}else if(value == '1'){ //是
						
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").hide();
						getCurrentView().contentPanel.form.findField("HARD_REMARK").hide();
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").setValue('');
						getCurrentView().contentPanel.form.findField("HARD_REMARK").setValue('');
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").allowBlank = true;
						getCurrentView().contentPanel.form.findField("HARD_REMARK").allowBlank = true;
						
						getCurrentView().contentPanel.form.findField("CA_PP").allowBlank = false;
						getCurrentView().contentPanel.form.findField("DD_DATE").allowBlank = false;
						
					}else{//暂时维持本阶段
						
						getCurrentView().contentPanel.form.findField("GRADE_PERSECT").allowBlank = true;
						
						getCurrentView().contentPanel.form.findField("APPLY_AMT").allowBlank = true;
						getCurrentView().contentPanel.form.findField("CURRENCY").allowBlank = true;
						getCurrentView().contentPanel.form.findField("FOREIGN_MONEY").allowBlank = true;
						getCurrentView().contentPanel.form.findField("CUST_TYPE").allowBlank = true;
						getCurrentView().contentPanel.form.findField("FIRST_DOCU_DATE").allowBlank = true;
						getCurrentView().contentPanel.form.findField("GET_DOCU_DATE").allowBlank = true;
						getCurrentView().contentPanel.form.findField("SEND_DOCU_DATE").allowBlank = true;
					
						getCurrentView().contentPanel.form.findField("CA_PP").allowBlank = true;
						getCurrentView().contentPanel.form.findField("RM_REPLY_COCO").allowBlank = true;
						getCurrentView().contentPanel.form.findField("DD_DATE").allowBlank = true;
						getCurrentView().contentPanel.form.findField("IF_COCO").allowBlank = true;
						getCurrentView().contentPanel.form.findField("COCO_DATE").allowBlank = true;
					
						getCurrentView().contentPanel.form.findField("SUC_PROBABILITY").allowBlank = true;
						
						
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").hide();
						getCurrentView().contentPanel.form.findField("HARD_REMARK").hide();
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").setValue('');
						getCurrentView().contentPanel.form.findField("HARD_REMARK").setValue('');
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").allowBlank = true;
						getCurrentView().contentPanel.form.findField("HARD_REMARK").allowBlank = true;
						
					}
				}
			}},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerquery',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'AREA_NAME',text:'区域中心',translateType:"AREA",
	              resutlWidth:80,searchField: true, showField:'text',allowBlank:false,valueField:"value",listeners:{
				  select:function(a,b){
				  	a.setValue(b.data.value);
				}
				}},
              {name:'DEPT_NAME',text:'营业单位名称',xtype:'orgchoose',hiddenName:'DEPT_ID',allowBlank:false,searchField: true},
              {name:'RM',text:'RM',xtype:'userchoose',hiddenName:'RM_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'APPLY_AMT',text:'申请额度(折人民币/千元)',dataType:'money',maxLength:24,allowBlank:false},
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
			  {name:'FOREIGN_MONEY',text:'申请额度(原币金额/千元)',gridField:true,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24,allowBlank:false},
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
			  {name:'ADD_AMT',text:'增贷金额(折人民币/千元)',gridField:true,dataType:'money',allowBlank:false,maxLength:24,minValue:0},
			  {name:'FIRST_DOCU_DATE',text:'首次文件收集日期',gridField:true,allowBlank:false,dataType:'date'},
			  {name:'GET_DOCU_DATE',text:'文件收齐日期',gridField:true,allowBlank:false,dataType:'date'},
			  {name:'SEND_DOCU_DATE',text:'文件送出日期',gridField:true,allowBlank:false,dataType:'date'},
			  {name:'XD_CA_DATE',text:'RM信贷系统提交日期',gridField:true ,dataType:'date',allowBlank:false},
			  {name:'CA_PP',text:'CA准备人员',allowBlank:false,gridField:true},
			  {name:'GRADE_PERSECT',text:'客户预评级',gridField:true,translateType:'GRADE_PERSECT_SME',allowBlank:false},
			  {name:'DD_DATE',text:'DD文件收齐日期',gridField:true,allowBlank:false,dataType:'date'},
			  {name:'IF_COCO',text:'是否已开CO-CO MEETING',translateType:'IF_COCOMEETING',allowBlank:false,listeners:{
					select:function(){
						var value = getCurrentView().contentPanel.form.findField("IF_COCO").getValue();
						if(value == '1'){
							getCurrentView().contentPanel.form.findField("COCO_DATE").show();
							getCurrentView().contentPanel.form.findField("COCO_INFO").show();
							getCurrentView().contentPanel.form.findField("RM_REPLY_COCO").show();
							getCurrentView().contentPanel.form.findField("COCO_DATE").allowBlank = false;
							getCurrentView().contentPanel.form.findField("RM_REPLY_COCO").allowBlank = false;
							if(getCurrentView().contentPanel.form.findField("IF_THIRD_STEP").getValue()=='2'){
								getCurrentView().contentPanel.form.findField("COCO_INFO").allowBlank = true;
							}else{
								getCurrentView().contentPanel.form.findField("COCO_INFO").allowBlank=false;
							}
						}else{
							getCurrentView().contentPanel.form.findField("COCO_DATE").allowBlank=true;
							getCurrentView().contentPanel.form.findField("COCO_INFO").allowBlank=true;
							getCurrentView().contentPanel.form.findField("RM_REPLY_COCO").allowBlank=true;
							getCurrentView().contentPanel.form.findField("COCO_DATE").hide();
							getCurrentView().contentPanel.form.findField("COCO_INFO").hide();
							getCurrentView().contentPanel.form.findField("RM_REPLY_COCO").hide();
							getCurrentView().contentPanel.form.findField("COCO_DATE").setValue('');
							getCurrentView().contentPanel.form.findField("COCO_INFO").setValue('');
							getCurrentView().contentPanel.form.findField("RM_REPLY_COCO").setValue('');
						}}
				}},
			  {name:'COCO_DATE',text:'CO-CO MEETING 日期',gridField:true,dataType:'date',allowBlank:false},
			  {name:'COCO_INFO',text:'CO-CO MEETING结论',gridField:true ,xtype:'textarea',maxLength:400,allowBlank:false},
			  {name:'RM_REPLY_COCO',text:'RM回复COCO_MEETING提问日期',gridField:true,dataType:'date'},			
			  {name:'CA_HARD_INFO',text:'CA准备阶段难点',translateType:'CA_HARD_INFO',editable:true,listeners:{
				select:function(){
						var value = getCurrentView().contentPanel.form.findField("CA_HARD_INFO").getValue();
						if(value == '4'){
							getCurrentView().contentPanel.form.findField("HARD_REMARK").show();
							getCurrentView().contentPanel.form.findField("HARD_REMARK").allowBlank = false;
						}else { 
							getCurrentView().contentPanel.form.findField("HARD_REMARK").hide();
							getCurrentView().contentPanel.form.findField("HARD_REMARK").setValue('');
							getCurrentView().contentPanel.form.findField("HARD_REMARK").allowBlank = true;
						}
					}
				}},
			  {name:'HARD_REMARK',text:'CA准备阶段难点备注',xtype:'textarea',allowBlank:false,gridField:true,maxLength:500},
			  {name:'SUC_PROBABILITY',text:'预计核案概率(%)',translateType:'SUC_PROBABILITY',allowBlank:false,searchField: true,editable:true},			  
			  {name:'IF_SUMBIT_CO',text:'IF_SUMBIT_CO',gridField:false,value:'1'},
			  {name:'ID',text:'ID',gridField:false},
              {name:'CUST_ID',text:'客户编号',gridField:false},
	          {name:'RM_ID',text:'RM编号',gridField:false},
	          {name:'DEPT_ID',text:'DEPT_ID',gridField:false},
	          {name:'AREA_ID',text:'区域编号',gridField:false},
	          {name:'INTENT_ID',text:'INTENT_ID',gridField:false},
			  {name:'PIPELINE_ID',text:'PIPELINE编号',gridField:false,searchField: true},
			  {name:'RECORD_DATE',text:'RECORD_DATE',gridField:false}
			  ];

var tbar = [{
	text:'删除',
	hidden:JsContext.checkGrant('smeCaCoDelet'),
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
                url: basepath+'/smeCaCo!batchDel.json?idStr='+ID,                                
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
        url :  basepath + '/smeCaCo.json'
    })];
    
    
var customerView = [{
	title:'修改',
	hideTitle:JsContext.checkGrant('smeCaCoEdit'),
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
		         'CURRENCY','CASE_TYPE','FOREIGN_MONEY','CUST_TYPE','APPLY_AMT','IF_ADD','GRADE_PERSECT','ADD_AMT',
		         'FIRST_DOCU_DATE','XD_CA_DATE','GET_DOCU_DATE','SUC_PROBABILITY','SEND_DOCU_DATE','CA_PP',
		         'IF_COCO','DD_DATE','COCO_DATE','IF_THIRD_STEP','RM_REPLY_COCO','CA_HARD_INFO',		         				
				'ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','INTENT_ID','PIPELINE_ID','RECORD_DATE'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,
					    CURRENCY,CASE_TYPE,FOREIGN_MONEY,CUST_TYPE,APPLY_AMT,IF_ADD,GRADE_PERSECT,ADD_AMT,
			            FIRST_DOCU_DATE,XD_CA_DATE,GET_DOCU_DATE,SUC_PROBABILITY,SEND_DOCU_DATE,CA_PP,
			            IF_COCO,DD_DATE,COCO_DATE,IF_THIRD_STEP,RM_REPLY_COCO,CA_HARD_INFO,
			            ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,INTENT_ID,PIPELINE_ID,RECORD_DATE){
				
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
				FIRST_DOCU_DATE.readOnly = true;
				FIRST_DOCU_DATE.cls = 'x-readOnly';
				GET_DOCU_DATE.readOnly = true;
				GET_DOCU_DATE.cls = 'x-readOnly';
				SEND_DOCU_DATE.readOnly = true;
				SEND_DOCU_DATE.cls = 'x-readOnly';
				GRADE_PERSECT.readOnly = true;
				GRADE_PERSECT.cls = 'x-readOnly';
				SUC_PROBABILITY.readOnly = true;
				SUC_PROBABILITY.cls = 'x-readOnly';
				
				ID.hidden = true;
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				DEPT_ID.hidden = true;
				AREA_ID.hidden = true;
				INTENT_ID.hidden = true;
				PIPELINE_ID.hidden = true;
				RECORD_DATE.hidden = true;
				CA_HARD_INFO.hidden = true;
				COCO_DATE.hidden = true;
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,
					    CURRENCY,CASE_TYPE,FOREIGN_MONEY,CUST_TYPE,APPLY_AMT,IF_ADD,GRADE_PERSECT,ADD_AMT,
			            FIRST_DOCU_DATE,XD_CA_DATE,GET_DOCU_DATE,SUC_PROBABILITY,SEND_DOCU_DATE,CA_PP,
			            IF_COCO,DD_DATE,COCO_DATE,IF_THIRD_STEP,RM_REPLY_COCO,CA_HARD_INFO,
			            ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,INTENT_ID,PIPELINE_ID,RECORD_DATE];
			}
	},{
		  columnCount: 1,
		  fields : ['COCO_INFO','HARD_REMARK'],
		  fn : function(COCO_INFO,HARD_REMARK){
		  	COCO_INFO.hidden = true;
		  	HARD_REMARK.hidden = true;
			  return [COCO_INFO,HARD_REMARK];
		  }
	}],
		formButtons : [{
			text:'提交',
			fn : function(formPanel,basicForm){
				 if (!formPanel.getForm().isValid()) {
		               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		               return false;
		           };
//		          if(formPanel.getForm().findField("IF_ADD").getValue()== '1'){
//		        	  if(formPanel.getForm().findField("ADD_AMT").getValue()== ''){
//		        		  Ext.MessageBox.alert('提示','请填写增贷金额');
//			               return false;
//		        	  }
//		          }
		         var data = formPanel.getForm().getFieldValues();
					var commintData = translateDataKey(data,1);
					ifThirdStep=commintData.ifThirdStep;
					if(ifThirdStep == '0'){
		        	   if(formPanel.form.findField("CA_HARD_INFO").getValue() == ''){
		        		   Ext.MessageBox.alert('提示','请填写[CA准备阶段难点]');
			               return false;
		        	   }
			        }
			        var ifCoco=formPanel.getForm().findField("IF_COCO").getValue();
			        if(ifCoco=='0'&&ifThirdStep=='1'){
			        	Ext.MessageBox.alert('提示','是否已开CO-CO MEETING为否时，不能进入下一阶段！');
			            return false;
			        }

					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					
					Ext.Ajax.request({
							url : basepath + '/smeCaCo!save.json',
							method : 'GET',
							params : commintData,
							success : function(response) {	
//								debugger;
									if(ifThirdStep=='1'){//需要在下一阶段进行数据存储
										var ret = Ext.decode(response.responseText);										
										Ext.Ajax.request({
											url : basepath + '/smeCheckE!save.json',//把数据转入信用审查阶段，并把数据保存进信用审查阶段
											method : 'GET',
											params : ret,
											success : function(response) {
												var ret = Ext.decode(response.responseText);												
												Ext.MessageBox.alert('提示','保存数据成功,请在信用审查阶段查看数据!');																						
											}
										})
									}else if(ifThirdStep == '3'){
										Ext.MessageBox.alert('提示','已退回至RM!');
									}else {
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
	hideTitle:JsContext.checkGrant('smeCaCoDetail'),
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
		         'CURRENCY','CASE_TYPE','FOREIGN_MONEY','CUST_TYPE','APPLY_AMT','IF_ADD','GRADE_PERSECT','ADD_AMT',
		         'FIRST_DOCU_DATE','XD_CA_DATE','GET_DOCU_DATE','SUC_PROBABILITY','SEND_DOCU_DATE','CA_PP',
		         'IF_COCO','DD_DATE','COCO_DATE','IF_THIRD_STEP','RM_REPLY_COCO','CA_HARD_INFO',		         				
				'ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','INTENT_ID','PIPELINE_ID','RECORD_DATE'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,
				    CURRENCY,CASE_TYPE,FOREIGN_MONEY,CUST_TYPE,APPLY_AMT,IF_ADD,GRADE_PERSECT,ADD_AMT,
		            FIRST_DOCU_DATE,XD_CA_DATE,GET_DOCU_DATE,SUC_PROBABILITY,SEND_DOCU_DATE,CA_PP,
		            IF_COCO,DD_DATE,COCO_DATE,IF_THIRD_STEP,RM_REPLY_COCO,CA_HARD_INFO,
		            ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,INTENT_ID,PIPELINE_ID,RECORD_DATE){
				
				
				CUST_NAME.readOnly = true;
				CUST_NAME.cls = 'x-readOnly';
				AREA_NAME.readOnly = true;
				AREA_NAME.cls = 'x-readOnly';
				DEPT_NAME.readOnly = true;
				DEPT_NAME.cls = 'x-readOnly';
				RM.readOnly = true;
				RM.cls = 'x-readOnly';
				APPLY_AMT.readOnly = true;
				APPLY_AMT.cls = 'x-readOnly';
				CURRENCY.readOnly = true;
				CURRENCY.cls = 'x-readOnly';
				CUST_TYPE.readOnly = true;
				CUST_TYPE.cls = 'x-readOnly';
				CASE_TYPE.readOnly = true;
				CASE_TYPE.cls = 'x-readOnly';
				IF_ADD.readOnly = true;
				IF_ADD.cls = 'x-readOnly';
				ADD_AMT.readOnly = true;
				ADD_AMT.cls = 'x-readOnly';
				FOREIGN_MONEY.readOnly = true;
				FOREIGN_MONEY.cls = 'x-readOnly';		
				IF_COCO.readOnly = true;
				IF_COCO.cls = 'x-readOnly';
				FIRST_DOCU_DATE.readOnly = true;
				FIRST_DOCU_DATE.cls = 'x-readOnly';
				GET_DOCU_DATE.readOnly = true;
				GET_DOCU_DATE.cls = 'x-readOnly';
				SEND_DOCU_DATE.readOnly = true;
				SEND_DOCU_DATE.cls = 'x-readOnly';
			
				CA_PP.readOnly = true;
				CA_PP.cls = 'x-readOnly';
				GRADE_PERSECT.readOnly = true;
				GRADE_PERSECT.cls = 'x-readOnly';
				DD_DATE.readOnly = true;
				DD_DATE.cls = 'x-readOnly';
				COCO_DATE.readOnly = true;
				COCO_DATE.cls = 'x-readOnly';
				RM_REPLY_COCO.readOnly = true;
				RM_REPLY_COCO.cls = 'x-readOnly';
				XD_CA_DATE.readOnly = true;
				XD_CA_DATE.cls = 'x-readOnly';
				CA_HARD_INFO.readOnly = true;
				CA_HARD_INFO.cls = 'x-readOnly';
				SUC_PROBABILITY.readOnly = true;
				SUC_PROBABILITY.cls = 'x-readOnly';
				IF_THIRD_STEP.readOnly = true;
				IF_THIRD_STEP.cls = 'x-readOnly';
				ID.hidden = true;
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				DEPT_ID.hidden = true;
				AREA_ID.hidden = true;
				INTENT_ID.hidden = true;
				PIPELINE_ID.hidden = true;
				RECORD_DATE.hidden = true;
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,
					    CURRENCY,CASE_TYPE,FOREIGN_MONEY,CUST_TYPE,APPLY_AMT,IF_ADD,GRADE_PERSECT,ADD_AMT,
			            FIRST_DOCU_DATE,XD_CA_DATE,GET_DOCU_DATE,SUC_PROBABILITY,SEND_DOCU_DATE,CA_PP,
			            IF_COCO,DD_DATE,COCO_DATE,IF_THIRD_STEP,RM_REPLY_COCO,CA_HARD_INFO,
			            ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,INTENT_ID,PIPELINE_ID,RECORD_DATE];
			}
	},{
		  columnCount: 1,
		  fields : ['COCO_INFO','HARD_REMARK'],
		  fn : function(COCO_INFO,HARD_REMARK){
			  	COCO_INFO.readOnly = true;
			  	HARD_REMARK.readOnly = true;
			  	COCO_INFO.cls = 'x-readOnly';
			  	HARD_REMARK.cls = 'x-readOnly';
			  return [COCO_INFO,HARD_REMARK];
		  }
		}]
}];

var beforeviewshow = function(view){
	
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
	}
	if(view._defaultTitle == '修改'){
		if(getSelectedData().data.IF_THIRD_STEP == '0'){
			Ext.Msg.alert('提示','案件已否决，不能修改');
			return false;
		}
	}
	
};

var viewshow = function(view){

		if(view.contentPanel.form.findField("CA_PP").value == ''){//CA准备人员 默认为 当前用户
			view.contentPanel.form.findField("CA_PP").setValue(__userName);	
		}
		
		if(view.contentPanel.form.findField("IF_COCO").value == ''){//是否已开CO-CO MEETING 默认为 无需
			view.contentPanel.form.findField("IF_COCO").setValue('2');
		}
		if(view.contentPanel.form.findField("IF_THIRD_STEP").value == ''){//是否进入第三阶段 默认为 暂时维持本阶段
			view.contentPanel.form.findField("IF_THIRD_STEP").setValue('2');
		}
	
		if(view.contentPanel.form.findField("CUST_TYPE").value == '2'){//客户类型是否为旧户增贷
			getCurrentView().contentPanel.form.findField("IF_ADD").show();			
		}else{
			getCurrentView().contentPanel.form.findField("IF_ADD").hide();
			getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank = true;
			getCurrentView().contentPanel.form.findField("IF_ADD").setValue('');
			
			getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
			getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
			getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
		}			  		
		
		if(view.contentPanel.form.findField("IF_ADD").value == '1'){//是否为增贷
			getCurrentView().contentPanel.form.findField("ADD_AMT").show();
		}else{
			getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
			getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
			getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
		}
		
		if(view.contentPanel.form.findField("CA_HARD_INFO").value == '4'){//CA准备阶段难点
			view.contentPanel.form.findField("HARD_REMARK").show();
			view.contentPanel.form.findField("HARD_REMARK").allowBlank = false;
		}else { 
			view.contentPanel.form.findField("HARD_REMARK").hide();
			view.contentPanel.form.findField("HARD_REMARK").setValue('');
			view.contentPanel.form.findField("HARD_REMARK").allowBlank = true;
		}
	
		if(view.contentPanel.form.findField("IF_COCO").value == '1'){//是否已开CO-CO MEETING
			
			view.contentPanel.form.findField("COCO_DATE").show();
			view.contentPanel.form.findField("COCO_INFO").show();
			view.contentPanel.form.findField("RM_REPLY_COCO").show();
			view.contentPanel.form.findField("COCO_DATE").allowBlank=false;
			view.contentPanel.form.findField("RM_REPLY_COCO").allowBlank=false;
			if(view.contentPanel.form.findField("IF_THIRD_STEP").value =='2'){
				view.contentPanel.form.findField("COCO_INFO").allowBlank=true;
			}else{
				view.contentPanel.form.findField("COCO_INFO").allowBlank=false;
			}
		}else{
			
			view.contentPanel.form.findField("COCO_DATE").allowBlank=true;
			view.contentPanel.form.findField("COCO_INFO").allowBlank=true;
			view.contentPanel.form.findField("RM_REPLY_COCO").allowBlank=true;
			view.contentPanel.form.findField("COCO_DATE").hide();
			view.contentPanel.form.findField("COCO_INFO").hide();
			view.contentPanel.form.findField("RM_REPLY_COCO").hide();
			view.contentPanel.form.findField("COCO_DATE").setValue('');
			view.contentPanel.form.findField("COCO_INFO").setValue('');
			view.contentPanel.form.findField("RM_REPLY_COCO").setValue('');
		}
		
		if(view.contentPanel.form.findField("IF_THIRD_STEP").value == '0'){//是否进入第三阶段
			
			view.contentPanel.form.findField("CA_HARD_INFO").allowBlank = false;
			view.contentPanel.form.findField("CA_HARD_INFO").show();
			view.contentPanel.form.findField("GRADE_PERSECT").allowBlank = false;
		
			
		}else if(view.contentPanel.form.findField("IF_THIRD_STEP").value == '1'){ 
			
			view.contentPanel.form.findField("CA_HARD_INFO").hide();
			view.contentPanel.form.findField("HARD_REMARK").hide();
			view.contentPanel.form.findField("CA_HARD_INFO").setValue('');
			view.contentPanel.form.findField("HARD_REMARK").setValue('');
			view.contentPanel.form.findField("CA_HARD_INFO").allowBlank = true;
			view.contentPanel.form.findField("HARD_REMARK").allowBlank = true;
			
			view.contentPanel.form.findField("CA_PP").allowBlank = false;
			view.contentPanel.form.findField("DD_DATE").allowBlank = false;
			
		}else{
			
			view.contentPanel.form.findField("GRADE_PERSECT").allowBlank = true;
	
			view.contentPanel.form.findField("APPLY_AMT").allowBlank = true;
			view.contentPanel.form.findField("CURRENCY").allowBlank = true;
			view.contentPanel.form.findField("FOREIGN_MONEY").allowBlank = true;
			view.contentPanel.form.findField("CUST_TYPE").allowBlank = true;
			view.contentPanel.form.findField("FIRST_DOCU_DATE").allowBlank = true;
			view.contentPanel.form.findField("GET_DOCU_DATE").allowBlank = true;
			view.contentPanel.form.findField("SEND_DOCU_DATE").allowBlank = true;
		
			view.contentPanel.form.findField("CA_PP").allowBlank = true;
			view.contentPanel.form.findField("RM_REPLY_COCO").allowBlank = true;
			view.contentPanel.form.findField("DD_DATE").allowBlank = true;
			view.contentPanel.form.findField("IF_COCO").allowBlank = true;
			view.contentPanel.form.findField("COCO_DATE").allowBlank = true;
		
			view.contentPanel.form.findField("SUC_PROBABILITY").allowBlank = true;
			view.contentPanel.form.findField("IF_SUMBIT_CO").allowBlank = true;
						
			view.contentPanel.form.findField("CA_HARD_INFO").hide();
			view.contentPanel.form.findField("HARD_REMARK").hide();
			view.contentPanel.form.findField("CA_HARD_INFO").setValue('');
			view.contentPanel.form.findField("HARD_REMARK").setValue('');
			view.contentPanel.form.findField("CA_HARD_INFO").allowBlank = true;
			view.contentPanel.form.findField("HARD_REMARK").allowBlank = true;
			
		}												
};
var afterconditionrender = function(panel, app) {
	app.searchDomain.searchPanel.getForm()
	.findField("PIPELINE_ID").setValue(parent.window.document.getElementById('condition').value);
};
