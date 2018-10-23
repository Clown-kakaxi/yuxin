/**
 * @description 企商金客户营销流程 -  核批阶段页面
 * @author luyy
 * @since 2014-07-26
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


var url = basepath + '/mktApprovlC.json';

var lookupTypes = ['IF_FLAG','CHECK_STAT','CASE_TYPE','GRADE_PERSECT','ACC0600002','COMP_TYPE','SP_LEVEL','IF_FIFTH_STEP',
		'GRADE_LEVEL_FINAL','DELINE_REASON_APPROVL_C',
		{TYPE : 'AREA',//区域中心数据字典
			url : '/mktprospectC!searchArea.json',//此url为pipeline阶段所有区域中心字段的数据字典访问地址，后台逻辑存放于mktprospectCAction中
			key : 'KEY',
			value : 'VALUE',
			root : 'data'}];
			
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

var ifFifthStep=0;//此标志用于判断是否需要把信息录入第五阶段;值为1则需要值为5退回审查准备阶段
			
var fields = [
              {name:'IF_FIFTH_STEP',text:'是否进入第五阶段',translateType:'IF_FIFTH_STEP',allowBlank:false,searchField: true},
			  {name:'CUST_NAME',text:'客户名称',xtype:'customerquerypp',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'AREA_NAME',text:'区域中心',translateType:"AREA",
	              resutlWidth:80,searchField: true, showField:'text',allowBlank:false,valueField:"value",listeners:{
				  select:function(a,b){
				  	a.setValue(b.data.value);
				}
				}},
              {name:'DEPT_NAME',text:'营业单位名称',xtype:'orgchoose',hiddenName:'DEPT_ID',allowBlank:false,searchField: true,readOnly:true},
              {name:'RM',text:'RM',xtype:'userchoose',hiddenName:'RM_ID',resutlWidth:150,singleSelect: false,searchField: true,readOnly:true},
              {name:'APPLY_AMT',text:'申请额度(折人民币/千元)',dataType:'money',maxLength:24},
              {name:"CASE_TYPE",text:"案件类型",translateType:"CASE_TYPE",allowBlank:false,searchField: true,editable:true,resizable:true,listeners:{
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
//          				getCurrentView().contentPanel.form.findField("ADD_AMT").show();
          			  }
            	  }
              }},
              {name:'IF_ADD',text:'若为旧案是否为增贷',gridField:true,translateType:'IF_FLAG',listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_ADD").getValue();
					if(value == '1'){
						getCurrentView().contentPanel.form.findField("ADD_AMT").show();
					}else if(value == '0'){
						getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
						getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
						
					}}
			  }},
              {name:'ADD_AMT',text:'核准增贷金额(折人民币/千元)',gridField:true,dataType:'money',maxLength:24,minValue:0 },
			  {name:'FOREIGN_MONEY',text:'申请额度(原币金额/千元)',gridField:true,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24 },
			  {name:'CURRENCY',text:'申请额度币别',gridField:true,translateType:'CURRENCY',listeners:{
			  	select:function(){
			  		var flag=this.value;
			  		if(flag=='13'){
			  			var  FOREIGN_MONEY=getCurrentView().contentPanel.form.findField("FOREIGN_MONEY").getValue();
			  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue(FOREIGN_MONEY*6);
			  		}else{
			  			getCurrentView().contentPanel.form.findField("APPLY_AMT").setValue('');
			  		}
			  	}
			  }},
              {name:'XZ_CA_DATE',text:'信贷系统CA修正日期',gridField:true,dataType:'date',allowBlank:false},
              {name:'XZ_CA_FORM',text:'信贷系统CA修正额度框架',gridField:true,allowBlank:false,xtype:'textarea',maxLength:100},
              {name:'SP_LEVEL',text:'信贷审批签核层级',translateType:'SP_LEVEL',allowBlank:false,gridField:true,editable:true,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue();
					if(value == '1'){
						getCurrentView().contentPanel.form.findField("SC_DATE").show();
					}else if(value == '2'){
						getCurrentView().contentPanel.form.findField("CC_DATE").show();
					}else if(value == '3'){
						getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
					}else if(value == '4'){
						getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
					}else if(value == '5'||value == '6'){
						getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
					}}
			  }},
              {name:'SC_DATE',text:'SC批复日期',dataType:'date',hidden:true},
              {name:'CC_DATE',text:'CC批复日期',dataType:'date',hidden:true},
              {name:'LEVEL1_DATE',text:'1级审批官批复日期',dataType:'date',hidden:true},
              {name:'LEVEL2_DATE',text:'2级资深审批官批复日期',dataType:'date',hidden:true},
              {name:'LEVEL34_DATE',text:'3,4级资深审批官批复日期',dataType:'date',hidden:true},
              {name:'CHECK_DATE',text:'信审提交相应层级审批官日期',dataType:'date',allowBlank:false},
              {name:'CC_OPEN_DATE',text:'CC召开日期',gridField:true,dataType:'date'},
              {name:'IF_SURE',text:'额度是否核准',gridField:true,translateType:'IF_FLAG',listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_SURE").getValue();
					if(value == '1'){
						getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
						getCurrentView().contentPanel.form.findField("INSURE_FORM").show();
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
						getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = false;
						getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = false;
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = false;
						getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
						getCurrentView().contentPanel.form.findField("DELINE_REASON").setValue('');
						getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
					}else if(value == '0'||value == ''){
						getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
						getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
						getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
						getCurrentView().contentPanel.form.findField("INSURE_FORM").setValue('');
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
						getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
						getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = false;
						getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
						getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
					}}
				}},
              {name:'INSURE_AMT',text:'核准金额(折人民币/千元)',gridField:true,dataType:'money',maxLength:24 },
			  {name:'INSURE_MONEY',text:'核准金额(原币金额/千元)',gridField:true,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24},
			  {name:'INSURE_CURRENCY',text:'核准金额币别',gridField:true,translateType:'CURRENCY'},
              {name:'INSURE_FORM',text:'最终核准额度框架',gridField:true,xtype:'textarea',maxLength:100},
			  {name:'DELINE_REASON',text:'未核准原因',translateType:"DELINE_REASON_APPROVL_C",gridField:true,editable: true},	
			  {name:'RESON_REMARK',text:'未核准原因说明',gridField:true,xtype:'textarea',maxLength:500},	
			  {name:'CHECK_PROGRESS',text:'信审进度',allowBlank:false,xtype:'textarea',maxLength:500},
			  {name:'RECORD_DATE',text:'首次进入本阶段日期',dataType:'string',gridField:true},
              {name:'TREAMENT_DAYS',text:'本阶段案件处理天数',dataType:'string',gridField:true},//新增
              {name:'USER_ID',text:'USER_ID',gridField:false},
              {name:'ID',text:'ID',gridField:false},
              {name:'CUST_ID',text:'客户编号',gridField:false},
	          {name:'RM_ID',text:'RM编号',gridField:false},
	          {name:'DEPT_ID',text:'DEPT_ID',gridField:false},
	          {name:'AREA_ID',text:'区域编号',gridField:false},
	          {name:'SC_ID',text:'SC_ID',gridField:false},
			  {name:'PIPELINE_ID',text:'PIPELINE_ID',gridField:false},			
			  {name:'IF_BACK',text:'IF_BACK',gridField:false},
			  {name:'CO',text:'对应 CO',gridField:false,searchField: true}
			  ];

var tbar = [{
	text:'删除',
	hidden:JsContext.checkGrant('mktApprovlCDelet'),
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
                url: basepath+'/mktApprovlC!batchDel.json?idStr='+ID,                                
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
        url :  basepath + '/mktApprovlC.json'
    }),{
	
	text:'恢复',
	hidden:JsContext.checkGrant('mktApprovlCReview'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(getSelectedData().data.IF_FIFTH_STEP=='3'||getSelectedData().data.IF_FIFTH_STEP=='4'){

		    Ext.Ajax.request({
                url: basepath+'/mktApprovlC!changeStat.json',   
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
	type:'form',
	hideTitle:JsContext.checkGrant('mktApprovlCEdit'),
	autoLoadSeleted : true,
	groups : [{
		labelWidth:170,
		fields :[
		          {name:'CUST_NAME',text:'客户名称', allowBlank:false},
			      {name:'AREA_NAME',text:'区域中心', allowBlank:false},
			      {name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			      {name:'RM',text:'RM',resutlWidth:150},
			      'RECORD_DATE','TREAMENT_DAYS',//新增
			      'CASE_TYPE',
			      {name:'IF_FIFTH_STEP',text:'是否进入第五阶段',translateType:'IF_FIFTH_STEP',allowBlank:false,searchField: true,listeners:{
					select:function(){
						var value = getCurrentView().contentPanel.form.findField("IF_FIFTH_STEP").getValue();
						ifFifthStep=value;
						if(ifFifthStep==5){//当不选择退回信用审查阶段时，去出某些字段不为空 验证条件
							getCurrentView().contentPanel.form.findField("CASE_TYPE").allowBlank=true;
							getCurrentView().contentPanel.form.findField("XZ_CA_DATE").allowBlank=true;
							getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank=true;
	//						getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").allowBlank=true;
							getCurrentView().contentPanel.form.findField("XZ_CA_FORM").allowBlank=true;
	//						getCurrentView().contentPanel.form.findField("IF_SURE").allowBlank=true;
							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank=true;
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=true;
							
							getCurrentView().contentPanel.form.findField("CASE_TYPE").hide();
							getCurrentView().contentPanel.form.findField("XZ_CA_DATE").hide();
							getCurrentView().contentPanel.form.findField("SP_LEVEL").hide();
							getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").hide();
							getCurrentView().contentPanel.form.findField("XZ_CA_FORM").hide();
//							getCurrentView().contentPanel.form.findField("IF_SURE").hide();
							getCurrentView().contentPanel.form.findField("APPLY_AMT").hide();
							getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
							getCurrentView().contentPanel.form.findField("IF_ADD").hide();
							getCurrentView().contentPanel.form.findField("CHECK_DATE").hide();
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").hide();
							getCurrentView().contentPanel.form.findField("SC_DATE").hide();
							getCurrentView().contentPanel.form.findField("CC_DATE").hide();
							getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
							getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
							getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							
						}else if(ifFifthStep==4){
							getCurrentView().contentPanel.form.findField("CASE_TYPE").allowBlank=false;
							getCurrentView().contentPanel.form.findField("XZ_CA_DATE").allowBlank=true;
							getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank=false;
							getCurrentView().contentPanel.form.findField("XZ_CA_FORM").allowBlank=true;
							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank=true;
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=true;
							
							getCurrentView().contentPanel.form.findField("CASE_TYPE").show();
							getCurrentView().contentPanel.form.findField("XZ_CA_DATE").show();
							getCurrentView().contentPanel.form.findField("SP_LEVEL").show();
							if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '1'){
								getCurrentView().contentPanel.form.findField("SC_DATE").show();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '2'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '3'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '4'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '5'
									||getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '6'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
							}else{
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}
							getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").show();
							getCurrentView().contentPanel.form.findField("XZ_CA_FORM").show();
							getCurrentView().contentPanel.form.findField("APPLY_AMT").show();
							if(getCurrentView().contentPanel.form.findField("IF_ADD").getValue()=='1'){
								getCurrentView().contentPanel.form.findField("ADD_AMT").show();
							}else{
								getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
							}
							
							var CASE_TYPE=getCurrentView().contentPanel.form.findField("CASE_TYPE").getValue();
							if(CASE_TYPE!='2' && CASE_TYPE!='5' && CASE_TYPE!='6'){
								getCurrentView().contentPanel.form.findField("IF_ADD").hide();
							}else{
								getCurrentView().contentPanel.form.findField("IF_ADD").show();
							}
							
							getCurrentView().contentPanel.form.findField("CHECK_DATE").show();
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").show();
						}else {
							getCurrentView().contentPanel.form.findField("CASE_TYPE").allowBlank=false;
							getCurrentView().contentPanel.form.findField("XZ_CA_DATE").allowBlank=false;
							getCurrentView().contentPanel.form.findField("SP_LEVEL").allowBlank=false;
	//						getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").allowBlank=false;
							getCurrentView().contentPanel.form.findField("XZ_CA_FORM").allowBlank=false;
	//						getCurrentView().contentPanel.form.findField("IF_SURE").allowBlank=false;
//							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank=false;
//							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=false;
							
							getCurrentView().contentPanel.form.findField("CASE_TYPE").show();
							getCurrentView().contentPanel.form.findField("XZ_CA_DATE").show();
							getCurrentView().contentPanel.form.findField("SP_LEVEL").show();
							if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '1'){
								getCurrentView().contentPanel.form.findField("SC_DATE").show();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '2'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '3'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '4'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '5'
									||getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue() == '6'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
							}else{
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}
							getCurrentView().contentPanel.form.findField("CC_OPEN_DATE").show();
							getCurrentView().contentPanel.form.findField("XZ_CA_FORM").show();
	//						getCurrentView().contentPanel.form.findField("IF_SURE").show();
							getCurrentView().contentPanel.form.findField("APPLY_AMT").show();
							if(getCurrentView().contentPanel.form.findField("IF_ADD").getValue()=='1'){
								getCurrentView().contentPanel.form.findField("ADD_AMT").show();
							}else{
								getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
							}
							var CASE_TYPE=getCurrentView().contentPanel.form.findField("CASE_TYPE").getValue();
							if(CASE_TYPE!='2' && CASE_TYPE!='5' && CASE_TYPE!='6'){
								getCurrentView().contentPanel.form.findField("IF_ADD").hide();
							}else{
								getCurrentView().contentPanel.form.findField("IF_ADD").show();
							}
							getCurrentView().contentPanel.form.findField("CHECK_DATE").show();
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").show();
						}
						if(ifFifthStep==1){
							getCurrentView().contentPanel.form.findField("IF_SURE").setValue('1');
							getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
							getCurrentView().contentPanel.form.findField("INSURE_FORM").show();
							getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
							getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = false;
							getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = false;
							getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = false;
							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = false;
							getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
							getCurrentView().contentPanel.form.findField("DELINE_REASON").setValue('');
//							getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
							getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
							getCurrentView().contentPanel.form.findField("RESON_REMARK").setValue('');
						}else{
							getCurrentView().contentPanel.form.findField("IF_SURE").setValue('0');
							getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
							getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
							getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
							getCurrentView().contentPanel.form.findField("INSURE_FORM").setValue('');
							getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
							getCurrentView().contentPanel.form.findField("INSURE_MONEY").setValue('');
							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").setValue('');
							getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
//							getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = false;
							getCurrentView().contentPanel.form.findField("INSURE_AMT").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_FORM").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_MONEY").allowBlank = true;
							getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").allowBlank = true;
						}
						if(ifFifthStep == 6||ifFifthStep == 5||ifFifthStep == 4){
							getCurrentView().contentPanel.form.findField("XZ_CA_DATE").allowBlank = true;
							getCurrentView().contentPanel.form.findField("XZ_CA_FORM").allowBlank = true;
							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank=true;
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=true;
						}else{
							getCurrentView().contentPanel.form.findField("XZ_CA_DATE").allowBlank = false;
							getCurrentView().contentPanel.form.findField("XZ_CA_FORM").allowBlank = false;
							getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank=false;
							getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=false;
						}
						if(ifFifthStep == 5||ifFifthStep == 1){
							getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = true;
						}else if(ifFifthStep == 2||ifFifthStep == 3||ifFifthStep == 4||ifFifthStep == 6){
							getCurrentView().contentPanel.form.findField("DELINE_REASON").allowBlank = false;
						}
					}}},
					'FOREIGN_MONEY'  ,'CO','CURRENCY','IF_ADD','APPLY_AMT',
					{name:'ADD_AMT',text:'<font color="red">*</font>核准增贷金额(折人民币/千元)',dataType:'money',minValue:0},
					'IF_SURE','XZ_CA_DATE',{name:'INSURE_MONEY',text:'<font color="red">*</font>核准金额(原币金额/千元)',dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24},
					{name:'SP_LEVEL',text:'信贷审批签核层级',translateType:'SP_LEVEL',allowBlank:false,gridField:true,editable:true,listeners:{
						select:function(){
							var value = getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue();
							if(value == '1'){
								getCurrentView().contentPanel.form.findField("SC_DATE").show();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '2'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '3'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '4'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '5'||value == '6'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
							}else{
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}}
					  }},{name:'INSURE_CURRENCY',text:'<font color="red">*</font>核准金额币别',translateType:'CURRENCY',listeners:{
						     select:function(){
						     	var flag=this.value;
						     	if(flag=='13'){
						     		var INSURE_MONEY=getCurrentView().contentPanel.form.findField("INSURE_MONEY").getValue();
						     		getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue(INSURE_MONEY*6);
						     	}else{
						     		getCurrentView().contentPanel.form.findField("INSURE_AMT").setValue('');
						     	}
						     }
			  		}},'SC_DATE','ID','CC_DATE','CUST_ID','LEVEL1_DATE','RM_ID','LEVEL2_DATE','DEPT_ID','LEVEL34_DATE',
						  {name:'INSURE_AMT',text:'<font color="red">*</font>核准金额(折人民币/千元)',dataType:'money'},
						'CHECK_DATE','USER_ID','CC_OPEN_DATE',
					{name:'DELINE_REASON',text:'未核准原因',translateType:"DELINE_REASON_APPROVL_C",editable: true,listeners:{
						select:function(){
							var value = getCurrentView().contentPanel.form.findField("DELINE_REASON").getValue();
							if(value == '4'){
								getCurrentView().contentPanel.form.findField("RESON_REMARK").show();
								getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = false;
							}else{
								getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
								getCurrentView().contentPanel.form.findField("RESON_REMARK").setValue('');
								getCurrentView().contentPanel.form.findField("RESON_REMARK").allowBlank = true;
							}}
				}},'AREA_ID','SC_ID','PIPELINE_ID','IF_BACK'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,CASE_TYPE,IF_FIFTH_STEP,FOREIGN_MONEY,CO,CURRENCY,IF_ADD, APPLY_AMT
				,ADD_AMT,IF_SURE,XZ_CA_DATE,INSURE_MONEY,SP_LEVEL,INSURE_CURRENCY,SC_DATE,ID,CC_DATE,CUST_ID,LEVEL1_DATE,RM_ID,LEVEL2_DATE,DEPT_ID,LEVEL34_DATE,
				 INSURE_AMT,CHECK_DATE,USER_ID,CC_OPEN_DATE,DELINE_REASON,AREA_ID,SC_ID,PIPELINE_ID,IF_BACK){
				
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
			  	CO.cls = 'x-readOnly';
			  	CO.readOnly = true;
				USER_ID.hidden = true;
				ID.hidden = true;
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				DEPT_ID.hidden = true;
				AREA_ID.hidden = true;
				SC_ID.hidden = true;
				PIPELINE_ID.hidden = true;				
				IF_BACK.hidden = true;
				SC_DATE.hidden = true;
				CC_DATE.hidden = true;
				LEVEL1_DATE.hidden = true;
				LEVEL2_DATE.hidden = true;
				LEVEL34_DATE.hidden = true;
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,CASE_TYPE,IF_FIFTH_STEP,FOREIGN_MONEY,CO,CURRENCY,IF_ADD, APPLY_AMT
				,ADD_AMT,IF_SURE,XZ_CA_DATE, INSURE_MONEY,SP_LEVEL,INSURE_CURRENCY,SC_DATE,ID,CC_DATE,CUST_ID,LEVEL1_DATE,RM_ID,LEVEL2_DATE,DEPT_ID,LEVEL34_DATE,INSURE_AMT
				,CHECK_DATE,USER_ID,CC_OPEN_DATE,DELINE_REASON,AREA_ID,SC_ID,PIPELINE_ID,IF_BACK];
			}
	},{
		  columnCount: 1,
		  fields : ['INSURE_FORM','CHECK_PROGRESS'],
		  fn : function(INSURE_FORM,CHECK_PROGRESS){
			  return [INSURE_FORM,CHECK_PROGRESS];
		  }
		},{
		  columnCount: 1,
		  fields : ['XZ_CA_FORM',
		  	{name:'RESON_REMARK',text:'<font color="red">*</font>未核准原因说明',xtype:'textarea',maxLength:500}],
		  fn : function(XZ_CA_FORM,RESON_REMARK){
			  return [XZ_CA_FORM,RESON_REMARK];
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
					ifFifthStep=commintData.ifFifthStep;
					if(ifFifthStep == '2'){
		        	   if(formPanel.form.findField("DELINE_REASON").getValue() == ''){
		        		   Ext.MessageBox.alert('提示','请填写[未核准原因]');
			               return false;
		        	   }
			        }
					
					var ADD_AMT=formPanel.form.findField("ADD_AMT").getValue();//核准增贷金额
					var APPLY_AMT=formPanel.form.findField("APPLY_AMT").getValue();//申请额度（折人民币/千元）
					if(ADD_AMT!='' && APPLY_AMT!=''){
						if(ADD_AMT > APPLY_AMT){
							 Ext.MessageBox.alert('提示','核准增贷金额（折人民币/千元）应小于等于申请额度（折人民币/千元）');
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
					if(((data.APPLY_AMT==0||data.APPLY_AMT=='')&&(data.FOREIGN_MONEY!=0||data.FOREIGN_MONEY!='')&&data.IF_FIFTH_STEP=='1')
			        		||((data.APPLY_AMT!=0||data.APPLY_AMT!='')&&(data.FOREIGN_MONEY==0||data.FOREIGN_MONEY=='')&&data.IF_FIFTH_STEP=='1')){
			        	Ext.MessageBox.alert('提示','申请额度（折人民币/千元）或申请额度（原币金额/千元）不允许为零！');
			               return false;
			     	}
			    	if(((data.INSURE_AMT==0||data.INSURE_AMT=='')&&(data.INSURE_MONEY!=0||data.INSURE_MONEY!='')&&data.IF_FIFTH_STEP=='1')
			        		||((data.INSURE_AMT!=0||data.INSURE_AMT!='')&&(data.INSURE_MONEY==0||data.INSURE_MONEY=='')&&data.IF_FIFTH_STEP=='1')){
			        	Ext.MessageBox.alert('提示','核准金额（折人民币/千元）或核准金额（原币金额/千元）不允许为零！');
			               return false;
		         	}
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/mktApprovlC!save.json',
							method : 'GET',
							params : commintData,
							success : function(response) {
									if(ifFifthStep=='1'){//需要在下一阶段进行数据存储
										var ret = Ext.decode(response.responseText);
										Ext.Ajax.request({
											url : basepath + '/mktApprovedC!save.json',//把数据转入核批阶段，并把数据保存进核批阶段
											method : 'GET',
											params : ret,
											success : function(response) {
												Ext.MessageBox.alert('提示','保存数据成功,请在已核批动拨阶段查看数据!');
											}
										})
									}else if(ifFifthStep=='5'){
											Ext.MessageBox.alert('提示','数据已经被退回至信用审查阶段!');
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
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		labelWidth:160,
		fields :[
		          {name:'CUST_NAME',text:'客户名称', allowBlank:false},
			      {name:'AREA_NAME',text:'区域中心', allowBlank:false},
			      {name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			      {name:'RM',text:'RM',resutlWidth:150},
			      'RECORD_DATE','TREAMENT_DAYS',//新增
			      'CASE_TYPE',
			      {name:'IF_FIFTH_STEP',text:'是否进入第五阶段',translateType:'IF_FIFTH_STEP'},
			      'APPLY_AMT','CO','CURRENCY','IF_ADD','FOREIGN_MONEY',
				  {name:'ADD_AMT',text:'<font color="red">*</font>核准增贷金额(折人民币/千元)',dataType:'money',minValue:0},
				  'IF_SURE',{name:'XZ_CA_DATE',text:'信贷系统CA修正日期',dataType:'date'},
                  'INSURE_AMT', 
                  	{name:'SP_LEVEL',text:'信贷审批签核层级',allowBlank:false,translateType:'SP_LEVEL',listeners:{
						select:function(){
							var value = getCurrentView().contentPanel.form.findField("SP_LEVEL").getValue();
							if(value == '1'){
								getCurrentView().contentPanel.form.findField("SC_DATE").show();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '2'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '3'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '4'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}else if(value == '5'||value == '6'){
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
							}else{
								getCurrentView().contentPanel.form.findField("SC_DATE").hide();
								getCurrentView().contentPanel.form.findField("CC_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
								getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
							}}
					  }},'INSURE_CURRENCY','SC_DATE','ID','CC_DATE','CUST_ID','LEVEL1_DATE','RM_ID','LEVEL2_DATE','DEPT_ID','LEVEL34_DATE','INSURE_MONEY',
					  {name:'CHECK_DATE',text:'信审提交相应层级审批官日期',dataType:'date'},'USER_ID','CC_OPEN_DATE',
				{name:'DELINE_REASON',text:'未核准原因',translateType:"DELINE_REASON_APPROVL_C",editable: true,listeners:{
					select:function(){
						var value = getCurrentView().contentPanel.form.findField("DELINE_REASON").getValue();
						if(value == '4'){
							getCurrentView().contentPanel.form.findField("RESON_REMARK").show();
						}else{
							getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
						}}
				}},'AREA_ID','SC_ID','PIPELINE_ID','IF_BACK'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,CASE_TYPE,IF_FIFTH_STEP,APPLY_AMT,CO,CURRENCY,IF_ADD,FOREIGN_MONEY
				,ADD_AMT,IF_SURE,XZ_CA_DATE,INSURE_AMT,SP_LEVEL,INSURE_CURRENCY,SC_DATE,ID,CC_DATE,CUST_ID,LEVEL1_DATE,RM_ID,LEVEL2_DATE,DEPT_ID,LEVEL34_DATE,
				INSURE_MONEY,CHECK_DATE,USER_ID,CC_OPEN_DATE,DELINE_REASON,AREA_ID,SC_ID,PIPELINE_ID,IF_BACK){
				
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
				CO.readOnly = true;
				CO.cls = 'x-readOnly';
				APPLY_AMT.readOnly = true;
				APPLY_AMT.cls = 'x-readOnly';
				CURRENCY.readOnly = true;
				CURRENCY.cls = 'x-readOnly';
				FOREIGN_MONEY.readOnly = true;
				FOREIGN_MONEY.cls = 'x-readOnly';
				IF_ADD.readOnly = true;
				IF_ADD.cls = 'x-readOnly';
				ADD_AMT.readOnly = true;
				ADD_AMT.cls = 'x-readOnly';
				XZ_CA_DATE.readOnly = true;
				XZ_CA_DATE.cls = 'x-readOnly';
				INSURE_CURRENCY.readOnly = true;
				INSURE_CURRENCY.cls = 'x-readOnly';
				INSURE_MONEY.readOnly = true;
				INSURE_MONEY.cls = 'x-readOnly';
				SP_LEVEL.readOnly = true;
				SP_LEVEL.cls = 'x-readOnly';
				CC_OPEN_DATE.readOnly = true;
				CC_OPEN_DATE.cls = 'x-readOnly';
				IF_SURE.readOnly = true;
				IF_SURE.cls = 'x-readOnly';
				INSURE_AMT.readOnly = true;
				INSURE_AMT.cls = 'x-readOnly';
				IF_FIFTH_STEP.readOnly = true;
				IF_FIFTH_STEP.cls = 'x-readOnly';
				DELINE_REASON.readOnly = true;
				DELINE_REASON.cls = 'x-readOnly';
				USER_ID.hidden = true;
				ID.hidden = true;
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				DEPT_ID.hidden = true;
				AREA_ID.hidden = true;
				SC_ID.hidden = true;
				PIPELINE_ID.hidden = true;			
				IF_BACK.hidden = true;
				SC_DATE.hidden = true;
				CC_DATE.hidden = true;
				LEVEL1_DATE.hidden = true;
				LEVEL2_DATE.hidden = true;
				LEVEL34_DATE.hidden = true;
				SC_DATE.readOnly = true;
				SC_DATE.cls = 'x-readOnly';
				CC_DATE.readOnly = true;
				CC_DATE.cls = 'x-readOnly';
				LEVEL1_DATE.readOnly = true;
				LEVEL1_DATE.cls = 'x-readOnly';
				LEVEL2_DATE.readOnly = true;
				LEVEL2_DATE.cls = 'x-readOnly';
				LEVEL34_DATE.readOnly = true;
				LEVEL34_DATE.cls = 'x-readOnly';
				CHECK_DATE.readOnly = true;
				CHECK_DATE.cls = 'x-readOnly';
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,CASE_TYPE,IF_FIFTH_STEP,APPLY_AMT,CO,CURRENCY,IF_ADD,FOREIGN_MONEY
				,ADD_AMT,IF_SURE,XZ_CA_DATE,INSURE_AMT,SP_LEVEL,INSURE_CURRENCY,SC_DATE,ID,CC_DATE,CUST_ID,LEVEL1_DATE,RM_ID,LEVEL2_DATE,DEPT_ID,LEVEL34_DATE,
				INSURE_MONEY,CHECK_DATE,USER_ID,CC_OPEN_DATE,DELINE_REASON,AREA_ID,SC_ID,PIPELINE_ID,IF_BACK];
			}
	},{
		  columnCount: 1,
		  fields : [
              {name:'XZ_CA_FORM',text:'信贷系统CA修正额度框架',xtype:'textarea'},'CHECK_PROGRESS'
              ,'INSURE_FORM',
		  	{name:'RESON_REMARK',text:'<font color="red">*</font>未核准原因说明',xtype:'textarea',maxLength:500}],
		  fn : function(XZ_CA_FORM,CHECK_PROGRESS,INSURE_FORM,RESON_REMARK){
		  		XZ_CA_FORM.readOnly = true;
				XZ_CA_FORM.cls = 'x-readOnly';
				INSURE_FORM.readOnly = true;
				INSURE_FORM.cls = 'x-readOnly';
				RESON_REMARK.readOnly = true;
				RESON_REMARK.cls = 'x-readOnly';
				CHECK_PROGRESS.readOnly = true;
				CHECK_PROGRESS.cls = 'x-readOnly';
			  return [XZ_CA_FORM,CHECK_PROGRESS,INSURE_FORM,RESON_REMARK];
		  }
		}]
}];

var beforeviewshow = function(view){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
	}
	if(view._defaultTitle == '修改'){
		if(getSelectedData().data.IF_FIFTH_STEP=='3'
		      ||getSelectedData().data.IF_FIFTH_STEP == '4'){
		      	Ext.Msg.alert('提示','请先恢复案件,再修改！');
		      	return false;
		}
		if(getSelectedData().data.IF_FIFTH_STEP!='5'){
			view.contentPanel.form.findField("CASE_TYPE").show();
			view.contentPanel.form.findField("XZ_CA_DATE").show();
			view.contentPanel.form.findField("SP_LEVEL").show();
			view.contentPanel.form.findField("CC_OPEN_DATE").show();
			view.contentPanel.form.findField("XZ_CA_FORM").show();
			view.contentPanel.form.findField("APPLY_AMT").show();
			view.contentPanel.form.findField("IF_ADD").show();
			if(view.contentPanel.form.findField("IF_ADD").getValue()=='1'){
				view.contentPanel.form.findField("ADD_AMT").show();
			}else{
				view.contentPanel.form.findField("ADD_AMT").hide();
			}
		}
		if(getSelectedData().data.IF_FIFTH_STEP=='2'){
			view.contentPanel.form.findField("XZ_CA_DATE").allowBlank = false;
			view.contentPanel.form.findField("XZ_CA_FORM").allowBlank = false;
			view.contentPanel.form.findField("CHECK_DATE").allowBlank=false;
			view.contentPanel.form.findField("CHECK_PROGRESS").allowBlank=false;
		}else if(getSelectedData().data.IF_FIFTH_STEP=='6'){
			view.contentPanel.form.findField("XZ_CA_DATE").allowBlank = true;
			view.contentPanel.form.findField("XZ_CA_FORM").allowBlank = true;
			view.contentPanel.form.findField("CHECK_DATE").allowBlank=true;
			view.contentPanel.form.findField("CHECK_PROGRESS").allowBlank=true;
		}
	}
};

var viewshow = function(view){
		if(getSelectedData().data.IF_ADD == '1'){
			getCurrentView().contentPanel.form.findField("ADD_AMT").show();
			getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = false;
		}else {
			getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
			getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
			getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank = true;
		}
		if(view._defaultTitle == '修改'){
			getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
			getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
//			getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
			if(getCurrentView().contentPanel.form.findField("DELINE_REASON").getValue()=='4'){
				getCurrentView().contentPanel.form.findField("RESON_REMARK").show();
			}else{
				getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
			}
			getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
			getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
			if(getCurrentView().contentPanel.form.findField("IF_FIFTH_STEP").getValue()==''){
				getCurrentView().contentPanel.form.findField("IF_FIFTH_STEP").setValue('6');
				getCurrentView().contentPanel.form.findField("IF_SURE").setValue('0');
				getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
				getCurrentView().contentPanel.form.findField("XZ_CA_DATE").allowBlank = true;
				getCurrentView().contentPanel.form.findField("XZ_CA_FORM").allowBlank = true;
				getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank=true;
				getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=true;
			}
			if(getCurrentView().contentPanel.form.findField("IF_FIFTH_STEP").getValue()=='6'){
				getCurrentView().contentPanel.form.findField("XZ_CA_DATE").allowBlank = true;
				getCurrentView().contentPanel.form.findField("XZ_CA_FORM").allowBlank = true;
				getCurrentView().contentPanel.form.findField("CHECK_DATE").allowBlank=true;
				getCurrentView().contentPanel.form.findField("CHECK_PROGRESS").allowBlank=true;
			}
			// 核批阶段的案件类型如果是‘旧户非CP专案’,‘旧户组合存贷’,‘现有授信户转入CB’,才需要显示‘若为旧案是否为增贷’  否则不显示
			var CASE_TYPE =getCurrentView().contentPanel.form.findField("CASE_TYPE").getValue();
			if(CASE_TYPE != '2' && CASE_TYPE!='5' &&  CASE_TYPE!='6' &&  CASE_TYPE!='13' ){
				getCurrentView().contentPanel.form.findField("IF_ADD").hide();
				getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank=true;
				getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
				getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank=true;
				
			}

		}
		if(view._defaultTitle == '修改'||view._defaultTitle == '详情'){
			if(getSelectedData().data.IF_FIFTH_STEP!='1'){
				getCurrentView().contentPanel.form.findField("IF_SURE").setValue('0');
				getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
				getCurrentView().contentPanel.form.findField("INSURE_FORM").hide();
				getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
				getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
				getCurrentView().contentPanel.form.findField("DELINE_REASON").show();
				if(getCurrentView().contentPanel.form.findField("DELINE_REASON").getValue()=='4'){
					getCurrentView().contentPanel.form.findField("RESON_REMARK").show();
				}else{
					getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
				}
			}else{
				getCurrentView().contentPanel.form.findField("IF_SURE").setValue('1');
				getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
				getCurrentView().contentPanel.form.findField("INSURE_FORM").show();
				getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
				getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
				getCurrentView().contentPanel.form.findField("DELINE_REASON").hide();
				getCurrentView().contentPanel.form.findField("RESON_REMARK").hide();
			}
			if(getSelectedData().data.SP_LEVEL=='1'){
				getCurrentView().contentPanel.form.findField("SC_DATE").show();
				getCurrentView().contentPanel.form.findField("CC_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(getSelectedData().data.SP_LEVEL == '2'){
				getCurrentView().contentPanel.form.findField("SC_DATE").hide();
				getCurrentView().contentPanel.form.findField("CC_DATE").show();
				getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(getSelectedData().data.SP_LEVEL == '3'){
				getCurrentView().contentPanel.form.findField("SC_DATE").hide();
				getCurrentView().contentPanel.form.findField("CC_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL1_DATE").show();
				getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(getSelectedData().data.SP_LEVEL == '4'){
				getCurrentView().contentPanel.form.findField("SC_DATE").hide();
				getCurrentView().contentPanel.form.findField("CC_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL2_DATE").show();
				getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
			}else if(getSelectedData().data.SP_LEVEL == '5'||getSelectedData().data.SP_LEVEL == '6'){
				getCurrentView().contentPanel.form.findField("SC_DATE").hide();
				getCurrentView().contentPanel.form.findField("CC_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL34_DATE").show();
			}else{
				getCurrentView().contentPanel.form.findField("SC_DATE").hide();
				getCurrentView().contentPanel.form.findField("CC_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL1_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL2_DATE").hide();
				getCurrentView().contentPanel.form.findField("LEVEL34_DATE").hide();
			}
			
			// 核批阶段的案件类型如果是‘旧户非CP专案’,‘旧户组合存贷’,‘现有授信户转入CB’,才需要显示‘若为旧案是否为增贷’  否则不显示
			// 核批阶段的案件类型如果是‘旧户非CP专案’,‘旧户组合存贷’,‘现有授信户转入CB’,才需要显示‘若为旧案是否为增贷’  否则不显示
			var CASE_TYPE =getCurrentView().contentPanel.form.findField("CASE_TYPE").getValue();
			if(CASE_TYPE != '2' && CASE_TYPE!='5' &&  CASE_TYPE!='6'  &&  CASE_TYPE!='13' ){
				getCurrentView().contentPanel.form.findField("IF_ADD").hide();
				getCurrentView().contentPanel.form.findField("IF_ADD").allowBlank=true;
				getCurrentView().contentPanel.form.findField("IF_ADD").setValue('');
				getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
				getCurrentView().contentPanel.form.findField("ADD_AMT").allowBlank=true;
				getCurrentView().contentPanel.form.findField("ADD_AMT").setValue('');
				
			}
		}
		
};

