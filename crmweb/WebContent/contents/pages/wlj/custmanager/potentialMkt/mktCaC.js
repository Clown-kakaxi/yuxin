/**
 * @description 企商金客户营销流程 - 企商金CA准备页面
 * @author luyy
 * @since 2014-07-25
 * @modify  dongyi 2014-12-02
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


var url = basepath + '/mktCaC.json';

var lookupTypes = ['IF_FLAG','CHECK_STAT','CASE_TYPE','COMP_TYPE','GRADE_LEVEL_FINAL','ACC0600002','IF_THIRD_STEP','CA_HARD_INFO','IF_COCOMEETING',
	{   TYPE : 'AREA',//区域中心数据字典
		url : '/mktprospectC!searchArea.json',//此url为pipeline阶段所有区域中心字段的数据字典访问地址，后台逻辑存放于mktprospectCAction中
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
			{name:"IF_THIRD_STEP",text:"是否进入第三阶段",translateType:"IF_THIRD_STEP",allowBlank:false,searchField: true},
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
			  {name:"CASE_TYPE",text:"案件类型",translateType:"CASE_TYPE",allowBlank:false,searchField: true,editable:true,resizable:true,listeners:{
            	  select:function(){
            		  var flag=this.value;
            		  if(flag != '2' && flag!='5' &&  flag!='6' ){
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
			  {name:'IF_ADD',text:'若为旧案是否为增贷',gridField:true,allowBlank:false,translateType:'IF_FLAG' },
			  {name:'ADD_AMT',text:'增贷金额(折人民币/千元)',gridField:true,dataType:'money' ,maxLength:24,minValue:0},
			  {name:'DD_DATE',text:'DD文件收齐日期',gridField:true ,dataType:'date'},
			  {name:'COMP_TYPE',text:'企业类型',translateType:'COMP_TYPE',gridField:true,allowBlank:false},
			  {name:'GRADE_LEVEL',text:'客户最终评级',translateType:'GRADE_LEVEL_FINAL',allowBlank:false,gridField:true,editable:true},
			  {name:'IF_COCO',text:'是否已开CO-CO MEETING',gridField:true,translateType:'IF_COCOMEETING',allowBlank:false },
			  {name:'COCO_DATE',text:'CO-CO MEETING 日期',gridField:true,dataType:'date',allowBlank:false},
			  {name:'COCO_INFO',text:'CO-CO MEETING结论',gridField:true ,xtype:'textarea',maxLength:400,allowBlank:false},
			  {name:'CA_DATE_P',text:'预计CA提交日期',gridField:true ,dataType:'date',allowBlank:false},
			  {name:'SUC_PROBABILITY',text:'预计核案概率(%)',translateType:'SUC_PROBABILITY',allowBlank:false,searchField: true,editable:true},			
			  {name:'CA_HARD_INFO',text:'CA准备阶段难点',translateType:'CA_HARD_INFO',gridField:true,editable:true},
			  {name:'HARD_REMARK',text:'难点补充说明',xtype:'textarea',gridField:true,maxLength:500},
			  {name:'RECORD_DATE',text:'首次进入本阶段日期',dataType:'string',gridField:true},
              {name:'TREAMENT_DAYS',text:'本阶段案件处理天数',dataType:'string',gridField:true},//新增
			  {name:'ID',text:'ID',gridField:false},
              {name:'CUST_ID',text:'客户编号',gridField:false},
	          {name:'RM_ID',text:'RM编号',gridField:false},
	          {name:'DEPT_ID',text:'DEPT_ID',gridField:false},
	          {name:'AREA_ID',text:'区域编号',gridField:false},
	          {name:'INTENT_ID',text:'INTENT_ID',gridField:false},
			  {name:'PIPELINE_ID',text:'PIPELINE_ID',gridField:false}			 
			  ];

var tbar = [{
	text:'删除',
	hidden:JsContext.checkGrant('mktCaCDelet'),
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
                url: basepath+'/mktCaC!batchDel.json?idStr='+ID,                                
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
        url :  basepath + '/mktCaC.json'
    })];

var customerView = [{
	title:'修改',
	hideTitle:JsContext.checkGrant('mktCacEdit'),
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		labelWidth:160,
		columnCount:2,
		fields :[
		         {name:'CUST_NAME',text:'客户名称', allowBlank:false},
		         {name:'AREA_NAME',text:'区域中心', allowBlank:false},
		         {name:'DEPT_NAME',text:'营业部门',allowBlank:false},
		         {name:'RM',text:'RM',resutlWidth:150},
		         'RECORD_DATE','TREAMENT_DAYS',//新增
		         'FOREIGN_MONEY','DD_DATE','CURRENCY'
				,{name:'IF_ADD',text:'若为旧案是否为增贷',translateType:'IF_FLAG',allowBlank:false,listeners:{
					select:function(){
						var value = getCurrentView().contentPanel.form.findField("IF_ADD").getValue();
						if(value == '1'){
							getCurrentView().contentPanel.form.findField("ADD_AMT").show();
						}else if(value == '0'){
							getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
							getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
							
						}}
				}}, 'APPLY_AMT',{name:'ADD_AMT',text:'<font color="red">*</font>增贷金额(折人民币/千元)',dataType:'money',minValue:0},
				'CASE_TYPE','COMP_TYPE','GRADE_LEVEL',
				{name:'IF_COCO',text:'是否已开CO-CO MEETING',translateType:'IF_COCOMEETING',allowBlank:false,listeners:{
					select:function(){
						var value = getCurrentView().contentPanel.form.findField("IF_COCO").getValue();
						if(value == '1'){
							getCurrentView().contentPanel.form.findField("COCO_DATE").show();
							getCurrentView().contentPanel.form.findField("COCO_INFO").show();
							getCurrentView().contentPanel.form.findField("COCO_DATE").allowBlank=false;
							if(getCurrentView().contentPanel.form.findField("IF_THIRD_STEP").getValue()=='2'){
								getCurrentView().contentPanel.form.findField("COCO_INFO").allowBlank=true;
							}else{
								getCurrentView().contentPanel.form.findField("COCO_INFO").allowBlank=false;
							}
						}else{
							getCurrentView().contentPanel.form.findField("COCO_DATE").allowBlank=true;
							getCurrentView().contentPanel.form.findField("COCO_INFO").allowBlank=true;
							getCurrentView().contentPanel.form.findField("COCO_DATE").hide();
							getCurrentView().contentPanel.form.findField("COCO_INFO").hide();
							getCurrentView().contentPanel.form.findField("COCO_DATE").setValue('');
							getCurrentView().contentPanel.form.findField("COCO_INFO").setValue('');
							
						}}
				}},
				'COCO_DATE','CA_DATE_P','SUC_PROBABILITY',
				{name:"IF_THIRD_STEP",text:"是否进入第三阶段",translateType:"IF_THIRD_STEP",allowBlank:false,searchField: true,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_THIRD_STEP").getValue();
					if(value == '0')
					{
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").allowBlank = false;
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").show();
					}else { 
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").hide();
						getCurrentView().contentPanel.form.findField("HARD_REMARK").hide();
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").setValue('');
						getCurrentView().contentPanel.form.findField("HARD_REMARK").setValue('');
						getCurrentView().contentPanel.form.findField("CA_HARD_INFO").allowBlank = true;
						getCurrentView().contentPanel.form.findField("HARD_REMARK").allowBlank = true;
					}
					if(value == '2'){
						getCurrentView().contentPanel.form.findField("GRADE_LEVEL").allowBlank = true;
						getCurrentView().contentPanel.form.findField("CA_DATE_P").allowBlank = true;
					}else{
						getCurrentView().contentPanel.form.findField("GRADE_LEVEL").allowBlank = false;
						getCurrentView().contentPanel.form.findField("CA_DATE_P").allowBlank = false;
					}
					}
				}},
				{name:'CA_HARD_INFO',text:'<font color="red">*</font>CA准备阶段难点',translateType:'CA_HARD_INFO',editable:true,listeners:{
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
				}},'ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','INTENT_ID','PIPELINE_ID'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,FOREIGN_MONEY,DD_DATE,CURRENCY,IF_ADD,APPLY_AMT ,ADD_AMT,CASE_TYPE,COMP_TYPE,GRADE_LEVEL,IF_COCO,
				COCO_DATE,CA_DATE_P,SUC_PROBABILITY,IF_THIRD_STEP,CA_HARD_INFO,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,INTENT_ID,PIPELINE_ID){
				
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
//				CASE_TYPE.readOnly = true;
//				CASE_TYPE.cls = 'x-readOnly';
				ID.hidden = true;
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				DEPT_ID.hidden = true;
				AREA_ID.hidden = true;
				INTENT_ID.hidden = true;
				PIPELINE_ID.hidden = true;
				CA_HARD_INFO.hidden = true;
				COCO_DATE.hidden = true;
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,FOREIGN_MONEY,DD_DATE,CURRENCY,IF_ADD,APPLY_AMT ,ADD_AMT,CASE_TYPE,COMP_TYPE,GRADE_LEVEL,IF_COCO,
				COCO_DATE,CA_DATE_P,SUC_PROBABILITY,IF_THIRD_STEP,CA_HARD_INFO,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,INTENT_ID,PIPELINE_ID];
			}
	},{
		  columnCount: 1,
		  fields : ['COCO_INFO',
					{name:'HARD_REMARK',text:'<font color="red">*</font>难点补充说明',xtype:'textarea'}],
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
		          if(formPanel.getForm().findField("IF_ADD").getValue()== '1'){
		        	  if(formPanel.getForm().findField("ADD_AMT").getValue()== ''){
		        		  Ext.MessageBox.alert('提示','请填写增贷金额');
			               return false;
		        	  }
		          }
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

			        var COCO_DATE=formPanel.getForm().findField("COCO_DATE").getValue();
					var CA_DATE_P=formPanel.getForm().findField("CA_DATE_P").getValue();
					if(CA_DATE_P!='' && COCO_DATE!=''){
						if(CA_DATE_P < COCO_DATE){
							Ext.MessageBox.alert('提示','预计CA提交日期应该晚于或者等于CO-CO MEETING日期');
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
		         	if(((data.APPLY_AMT==0||data.APPLY_AMT=='')&&(data.FOREIGN_MONEY!=0||data.FOREIGN_MONEY!='')&&data.IF_THIRD_STEP=='1')
			        		||((data.APPLY_AMT!=0||data.APPLY_AMT!='')&&(data.FOREIGN_MONEY==0||data.FOREIGN_MONEY=='')&&data.IF_THIRD_STEP=='1')){
			        	Ext.MessageBox.alert('提示','申请额度（折人民币/千元）或申请额度（原币金额/千元）不允许为零！');
			               return false;
			        }
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/mktCaC!save.json',
							method : 'GET',
							params : commintData,
							success : function(response) {
									if(ifThirdStep=='1'){//需要在下一阶段进行数据存储
										var ret = Ext.decode(response.responseText);
										Ext.Ajax.request({
											url : basepath + '/mktCheckC!save.json',//把数据转入信用审查阶段，并把数据保存进信用审查阶段
											method : 'GET',
											params : ret,
											success : function(response) {
												var ret = Ext.decode(response.responseText);
												Ext.MessageBox.alert('提示','保存数据成功,请在信用审查阶段查看数据!');
											}
										})
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
	hideTitle:JsContext.checkGrant('mktCacDetil'),
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
			      'APPLY_AMT','DD_DATE','CURRENCY'
			,{name:'IF_ADD',text:'若为旧案是否为增贷',translateType:'IF_FLAG',allowBlank:false,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_ADD").getValue();
					if(value == '1'){
						getCurrentView().contentPanel.form.findField("ADD_AMT").show();
					}else if(value == '0'){
						getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
						
					}}
				}},'FOREIGN_MONEY',{name:'ADD_AMT',text:'<font color="red">*</font>增贷金额(折人民币/千元)',dataType:'money',minValue:0 },'CASE_TYPE','COMP_TYPE',
				{name:'GRADE_LEVEL',text:'客户最终评级',translateType:'GRADE_LEVEL_FINAL'},
				{name:'IF_COCO',text:'是否已开CO-CO MEETING',gridField:false,translateType:'IF_COCOMEETING',allowBlank:false,listeners:{
					select:function(){
						var value = getCurrentView().contentPanel.form.findField("IF_COCO").getValue();
						if(value == '1'){
							getCurrentView().contentPanel.form.findField("COCO_DATE").show();
							getCurrentView().contentPanel.form.findField("COCO_INFO").show();
						}else{
							getCurrentView().contentPanel.form.findField("COCO_DATE").hide();
							getCurrentView().contentPanel.form.findField("COCO_INFO").hide();
						}}
				}},'COCO_DATE',
				{name:'CA_DATE_P',text:'预计CA提交日期',dataType:'date'},
				{name:'SUC_PROBABILITY',text:'预计核案概率(%)',translateType:'SUC_PROBABILITY'},
				{name:'IF_THIRD_STEP',text:'是否进入第三阶段',translateType:'IF_THIRD_STEP',allowBlank:false,searchField: true,listeners:{
					select:function(){
						var value = getCurrentView().contentPanel.form.findField("IF_THIRD_STEP").getValue();
						if(value == '0'){
							getCurrentView().contentPanel.form.findField("CA_HARD_INFO").show();
						}else { 
							getCurrentView().contentPanel.form.findField("CA_HARD_INFO").hide();
							getCurrentView().contentPanel.form.findField("HARD_REMARK").hide();
						}
					}
				}},
				{name:'CA_HARD_INFO',text:'<font color="red">*</font>CA准备阶段难点',translateType:'CA_HARD_INFO',editable:true,listeners:{
					select:function(){
						var value = getCurrentView().contentPanel.form.findField("CA_HARD_INFO").getValue();
						if(value == '4'){
							getCurrentView().contentPanel.form.findField("HARD_REMARK").show();
						}else { 
							getCurrentView().contentPanel.form.findField("HARD_REMARK").hide();
						}
					}
				}},'ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','INTENT_ID','PIPELINE_ID'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,APPLY_AMT,DD_DATE,CURRENCY,IF_ADD,FOREIGN_MONEY,ADD_AMT,CASE_TYPE,COMP_TYPE,GRADE_LEVEL,IF_COCO,
				COCO_DATE,CA_DATE_P,SUC_PROBABILITY,IF_THIRD_STEP,CA_HARD_INFO,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,INTENT_ID,PIPELINE_ID){
				
				
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
				APPLY_AMT.readOnly = true;
				APPLY_AMT.cls = 'x-readOnly';
				CURRENCY.readOnly = true;
				CURRENCY.cls = 'x-readOnly';
				CASE_TYPE.readOnly = true;
				CASE_TYPE.cls = 'x-readOnly';
				IF_ADD.readOnly = true;
				IF_ADD.cls = 'x-readOnly';
				FOREIGN_MONEY.readOnly = true;
				FOREIGN_MONEY.cls = 'x-readOnly';
				ADD_AMT.readOnly = true;
				ADD_AMT.cls = 'x-readOnly';
				DD_DATE.readOnly = true;
				DD_DATE.cls = 'x-readOnly';
				COMP_TYPE.readOnly = true;
				COMP_TYPE.cls = 'x-readOnly';
				GRADE_LEVEL.readOnly = true;
				GRADE_LEVEL.cls = 'x-readOnly';
				IF_COCO.readOnly = true;
				IF_COCO.cls = 'x-readOnly';
				COCO_DATE.readOnly = true;
				COCO_DATE.cls = 'x-readOnly';
				CA_DATE_P.readOnly = true;
				CA_DATE_P.cls = 'x-readOnly';
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
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,APPLY_AMT,DD_DATE,CURRENCY,IF_ADD,FOREIGN_MONEY,ADD_AMT,CASE_TYPE,COMP_TYPE,GRADE_LEVEL,IF_COCO,
				COCO_DATE,CA_DATE_P,SUC_PROBABILITY,IF_THIRD_STEP,CA_HARD_INFO,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,INTENT_ID,PIPELINE_ID];
			}
	},{
		  columnCount: 1,
		  fields : ['COCO_INFO',
					{name:'HARD_REMARK',text:'<font color="red">*</font>难点补充说明',xtype:'textarea'}],
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
			view.contentPanel.form.findField("CA_HARD_INFO").show();
			if(getSelectedData().data.CA_HARD_INFO == '4'){
				view.contentPanel.form.findField("HARD_REMARK").show();
			}else{
				view.contentPanel.form.findField("HARD_REMARK").hide();
			}
		}else if(getSelectedData().data.IF_SECOND_STEP == '1'||getSelectedData().data.IF_SECOND_STEP == null||getSelectedData().data.IF_SECOND_STEP == '2'){
			view.contentPanel.form.findField("CA_HARD_INFO").hide();
			view.contentPanel.form.findField("HARD_REMARK").hide();

	   }
    }
};
var viewshow = function(view){
	if(view._defaultTitle == '修改'){
//		if(getCurrentView().contentPanel.form.findField("IF_ADD").getValue()=='0'
//					||getCurrentView().contentPanel.form.findField("IF_ADD").getValue()==''){
//			getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
//		}
		if(getCurrentView().contentPanel.form.findField("IF_THIRD_STEP").getValue()==''){
			getCurrentView().contentPanel.form.findField("IF_THIRD_STEP").setValue('2');
			getCurrentView().contentPanel.form.findField("GRADE_LEVEL").allowBlank = true;
			getCurrentView().contentPanel.form.findField("CA_DATE_P").allowBlank = true;
			getCurrentView().contentPanel.form.findField("CA_HARD_INFO").hide();
		}
		if(getCurrentView().contentPanel.form.findField("IF_THIRD_STEP").getValue()=='2'){
			getCurrentView().contentPanel.form.findField("GRADE_LEVEL").allowBlank = true;
			getCurrentView().contentPanel.form.findField("CA_DATE_P").allowBlank = true;
		}
		if(getCurrentView().contentPanel.form.findField("IF_THIRD_STEP").getValue()=='0'){
			getCurrentView().contentPanel.form.findField("CA_HARD_INFO").show();
			getCurrentView().contentPanel.form.findField("HARD_REMARK").hide();
		}
		if(getSelectedData().data.IF_COCO  == '1'){
			getCurrentView().contentPanel.form.findField("COCO_DATE").show();
			getCurrentView().contentPanel.form.findField("COCO_INFO").show();
			getCurrentView().contentPanel.form.findField("COCO_DATE").allowBlank=false;
			if(getSelectedData().data.IF_THIRD_STEP  == '2'){
				getCurrentView().contentPanel.form.findField("COCO_INFO").allowBlank=true;
			}else{
				getCurrentView().contentPanel.form.findField("COCO_INFO").allowBlank=false;
			}
		}else{
			getCurrentView().contentPanel.form.findField("COCO_DATE").hide();
			getCurrentView().contentPanel.form.findField("COCO_INFO").hide();
			getCurrentView().contentPanel.form.findField("COCO_DATE").allowBlank=true;
			getCurrentView().contentPanel.form.findField("COCO_INFO").allowBlank=true;
		}
		// CA准备阶段的案件类型如果是‘旧户非CP专案’,‘旧户组合存贷’,‘现有授信户转入CB’,才需要显示‘若为旧案是否为增贷’  否则不显示
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
	}else if(view._defaultTitle == '详情'){
		if(getSelectedData().data.IF_ADD == '1'){
			getCurrentView().contentPanel.form.findField("ADD_AMT").show();
		}else {
			getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
		}
		if(getSelectedData().data.IF_COCO  == '1'){
			getCurrentView().contentPanel.form.findField("COCO_DATE").show();
			getCurrentView().contentPanel.form.findField("COCO_INFO").show();
		}else{
			getCurrentView().contentPanel.form.findField("COCO_DATE").hide();
			getCurrentView().contentPanel.form.findField("COCO_INFO").hide();
		}
		if(getCurrentView().contentPanel.form.findField("IF_THIRD_STEP").getValue()=='0'){
			getCurrentView().contentPanel.form.findField("CA_HARD_INFO").show();
			if(getCurrentView().contentPanel.form.findField("CA_HARD_INFO").getValue()!='4'){
				getCurrentView().contentPanel.form.findField("HARD_REMARK").hide();
			}else{
				getCurrentView().contentPanel.form.findField("HARD_REMARK").show();
			}
		}
		if(getCurrentView().contentPanel.form.findField("IF_THIRD_STEP").getValue()=='2'){
			getCurrentView().contentPanel.form.findField("CA_HARD_INFO").hide();
			getCurrentView().contentPanel.form.findField("HARD_REMARK").hide();
		}
		// CA准备阶段的案件类型如果是‘旧户非CP专案’,‘旧户组合存贷’,‘现有授信户转入CB’,才需要显示‘若为旧案是否为增贷’  否则不显示
			var CASE_TYPE =getCurrentView().contentPanel.form.findField("CASE_TYPE").getValue();
			if(CASE_TYPE != '2' && CASE_TYPE!='5' &&  CASE_TYPE!='6' &&  CASE_TYPE!='13' ){
				
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
