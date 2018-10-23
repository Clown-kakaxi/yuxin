/**
 * @description 企商金客户营销流程 -  已核批动拨页面
 * @author luyy
 * @since 2014-07-26
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldPP.js'	//客户放大镜
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

var db_amt_selected;
var url = basepath + '/mktApprovedC.json';

var lookupTypes = ['IF_FLAG','CHECK_STAT','CASE_TYPE','GRADE_PERSECT','ACC0600002','COMP_TYPE','SP_LEVEL',
	'UNISSUE_REASON_APPROVED_C','UNRECEPT_REASON_APPROVED_C',
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
var num=0;//判断是否“系统额度启用日”后30天且已超过“客户预计用款日期” 
var fields = [{name:'CUST_NAME',text:'客户名称',xtype:'customerquerypp',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'AREA_NAME',text:'区域中心',translateType:"AREA",
	              resutlWidth:80,searchField: true, showField:'text',allowBlank:false,valueField:"value",listeners:{
				  select:function(a,b){
				  	a.setValue(b.data.value);
				}
				}},
              {name:'DEPT_NAME',text:'营业单位名称',xtype:'orgchoose',hiddenName:'DEPT_ID',allowBlank:false,searchField: true},
              {name:'RM',text:'RM',xtype:'userchoose',hiddenName:'RM_ID',resutlWidth:150,singleSelect: false,searchField: true},
			  {name:'CASE_TYPE',text:'案件类型',translateType:'CASE_TYPE',allowBlank:false,searchField: true,resizable:true,editable:true},
			  {name:'APPLY_AMT',text:'申请额度(折人民币/千元)',dataType:'money',maxLength:24},
			  {name:'IF_ADD',text:'若为旧案是否为增贷',gridField:true,translateType:'IF_FLAG' },
			  {name:'ADD_AMT',text:'增贷金额(折人民币/千元)',gridField:true,dataType:'money',maxLength:24 },
              {name:'INSURE_AMT',text:'核准金额(折人民币/千元)',gridField:true,dataType:'money',maxLength:24,allowBlank:false },
			  {name:'INSURE_MONEY',text:'核准金额(原币金额/千元)',gridField:true,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24,allowBlank:false },
			  {name:'DB_AMTS',text:'动拨金额(折人民币/千元)',gridField:true,dataType: 'decimal', viewFn: money('0,000.00'),minValue: 0,maxLength:24},
			  {name:'INSURE_CURRENCY',text:'核准金额币别',gridField:true,translateType:'CURRENCY',allowBlank:false},
              {name:'IF_ACCEPT',text:'客户是否接受核准额度',gridField:true,translateType:'IF_FLAG',searchField: true},
              {name:'XD_CHECK_DATE',text:'信贷系统核准日期',gridField:true,dataType:'date'},
              {name:'USE_DATE_P',text:'客户预计用款日期',gridField:true,dataType:'date'},
              {name:'UNRECEPT_REASON',text:'客户不接受核准额度原因',translateType:'UNRECEPT_REASON_APPROVED_C' ,gridField:true,editable: true},
              {name:'UNISSUE_REASON',text:'未动拨原因',translateType:'UNISSUE_REASON_APPROVED_C' ,gridField:true,editable: true,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("UNISSUE_REASON").getValue();
					if(value == '6'){
						getCurrentView().contentPanel.form.findField("REASON_REMARK2").show();
						getCurrentView().contentPanel.form.findField("REASON_REMARK2").allowBlank = false;
					}else{
						getCurrentView().contentPanel.form.findField("REASON_REMARK2").hide();
						getCurrentView().contentPanel.form.findField("REASON_REMARK2").setValue('');
						getCurrentView().contentPanel.form.findField("REASON_REMARK2").allowBlank = true;
					}}
				}},
              {name:'CTR_C_DATE',text:'合同出具日期',gridField:true,dataType:'date'},
              {name:'CTR_S_DATE',text:'合同签约日期',gridField:true,dataType:'date'},
              {name:'MORTGAGE_DATE',text:'抵质押登记日期',gridField:true,dataType:'date'},
              {name:'FILE_UP_DATE',text:'RM提交所有对保文件至授信合同部日期',gridField:true,dataType:'date'},
              {name:'SX_CTR_DATE',text:'授信合同部出具瑕疵单或启用单日期',gridField:true,dataType:'date'},
              {name:'CTR_PROBLEM',text:'合同若有瑕疵请列明',gridField:true,xtype:'textarea',maxLength:400},
              {name:'PROBLEM_DATE',text:'RM反馈瑕疵日期',gridField:true,dataType:'date'},
              {name:'AMT_USE_DATE',text:'系统额度启用日期',gridField:true,dataType:'date',listeners:{
//				  change:function(a,newValue,oldValue){
//				  	if(newValue!='' && newValue!=null){
//				    var	num=Date.parseDate(newValue,"Y-m-d").getDayOfYear()-getCurrentView().contentPanel.form.findField("USE_DATE_P").getValue().getDayOfYear();
//				  	if(num>0){
//					  	if(num>30&&getCurrentView().contentPanel.form.findField("PAY_DATE").getValue()==''){
//					  			getCurrentView().contentPanel.form.findField("UNISSUE_REASON").show();
//					  			getCurrentView().contentPanel.form.findField("REASON_REMARK2").show();
//					  			
//					  	}}
//				}}
				}},
              {name:'ACCOUNT_DATE',text:'开户日期',gridField:true,dataType:'date'},
              {name:'PAY_DATE',text:'实际拨款日期',gridField:true,dataType:'date',listeners:{
				  change:function(a,newValue,oldValue){
				  	if(newValue!=null){
				  		getCurrentView().contentPanel.form.findField("UNISSUE_REASON").hide();
				  		getCurrentView().contentPanel.form.findField("UNISSUE_REASON").setValue('');
			  			getCurrentView().contentPanel.form.findField("REASON_REMARK2").hide();
			  			getCurrentView().contentPanel.form.findField("REASON_REMARK2").setValue('');
				  	}
			  }}},
			  {name:'REASON_REMARK1',text:'客户不接受核准额度原因说明',gridField:true,xtype:'textarea',maxLength:500},
			  {name:'REASON_REMARK2',text:'未动拨原因说明',gridField:true,xtype:'textarea',maxLength:500},
			  {name:'RECORD_DATE',text:'首次进入本阶段日期',dataType:'string',gridField:true},
              {name:'TREAMENT_DAYS',text:'本阶段案件处理天数',dataType:'string',gridField:true},//新增
              {name:'USER_ID',text:'USER_ID',gridField:false},
              {name:'ID',text:'ID',gridField:false},
              {name:'CUST_ID',text:'客户编号',gridField:false},
	          {name:'RM_ID',text:'RM编号',gridField:false},
	          {name:'DEPT_ID',text:'DEPT_ID',gridField:false},
	          {name:'AREA_ID',text:'区域编号',gridField:false},
	          {name:'HP_ID',text:'HP_ID',gridField:false},
			  {name:'PIPELINE_ID',text:'PIPELINE_ID',gridField:false},			
			  {name:'LINE',gridField:false}
			  ];

var tbar = [{
	text:'删除',
	hidden:JsContext.checkGrant('mktApprovedCDelet'),
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
                url: basepath+'/mktApprovedC!batchDel.json?idStr='+ID,                                
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
        url :  basepath + '/mktApprovedC.json'
    })];
newSm = new Ext.grid.CheckboxSelectionModel();
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
var newCm =  new Ext.grid.ColumnModel([editRrownum,newSm,	
                                   {header:'主键ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                   {header:'pipelineId', dataIndex : 'PIPELINE_ID',sortable : true,width : 120,hidden:true},
                                   {header:'动拨金额(折人民币/千元)', dataIndex : 'DB_AMT',sortable : true,width : 120,hidden:false},
                                   {header:'动拨日期',dataIndex:'DB_DATE',sortable:true,width:120,dataType:'date'},
                                   {header:'动拨人',dataIndex:'DB_USER',sortable:true,width:120}
        	                                     ]); 
var newPanelStroe_1 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/mktApprovedC!queryDB.json' ,
				method:'get'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'PIPELINE_ID'},
			    {name:'DB_AMT'},
			    {name:'DB_DATE'},
			    {name:'DB_USER'},
			    {name:'DB_AMT_SUM'}
			     ])
});	  


var newPanel_1 = new Ext.grid.GridPanel({
	title : '动拨记录',
	autoScroll: true,
	height:300,
    tbar : [{
    	 text : '新增动拨记录',
		 handler:function() {
			showCustomerViewByTitle('新增动拨记录');
		}
    },{
    	text : '修改动拨记录',
		handler:function(){
		var selectLength = newPanel_1.getSelectionModel().getSelections().length;	
		if (selectLength != 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');
			return false;
		} 
		  showCustomerViewByTitle('修改动拨记录');
		}
    },{
    	text:'删除动拨记录',
    	handler :function(){
    		var selectLength = newPanel_1.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_1.getSelectionModel().getSelections();
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var ids='';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			if(i==0){
    			  ids+=selectRecord.data.ID;
    			}else{
    			  ids+=","+selectRecord.data.ID;
    			}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
	    		if(buttonId.toLowerCase() == 'no'){
	        		return false;
	    		}
    			Ext.Ajax.request({
    				url : basepath + '/mktApprovedC!batchDelDB.json',
    				method : 'GET',
    				params:{
    					'idStr':ids 
    				},
    				success : function() {
    					Ext.Msg.hide(); 
    					showCustomerViewByTitle('修改');
    					newPanelStroe_1.reload({params:{'pipelineId':selectRecord.data.PIPELINE_ID}});
    					newPanelStroe_1.reload({params:{'pipelineId':selectRecord.data.PIPELINE_ID},
  											callback : function() {
           										var dbAmts=0;
												for(var i=0;i<newPanelStroe_1.data.length;i++){
													dbAmts+=parseInt(newPanelStroe_1.data.items[i].data.DB_AMT);	
												}
												getCurrentView().contentPanel.form.findField("DB_AMTS").setValue(dbAmts);	
       								 }});
    					
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
  	store : newPanelStroe_1,
	frame : true,
	sm:newSm,
	cm : newCm,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
var newPanel_2 = new Ext.grid.GridPanel({
	title : '动拨记录',
	autoScroll: true,
	height:300,
  	store : newPanelStroe_1,
	frame : true,
	sm:newSm,
	cm : newCm,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
var customerView = [{
	title:'新增动拨记录',
	hideTitle:true,
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount:2,
		fields:[
		  {name:'DB_AMT',text:'动拨金额(折人民币/千元)',gridField:true,dataType:'money',maxLength:24,allowBlank:false },
		  {name:'DB_DATE',text:'动拨日期',gridField:true,dataType:'date',allowBlank:false},
		  {name:'PIPELINE_ID',hidden:true}
		]
	}],
	formButtons:[{
		text:'保存',
		fn:function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
		               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		               return false;
		        };
		    var myDate = new Date();     
		   if(formPanel.form.findField('DB_DATE').getValue() > myDate ){
		        	  Ext.MessageBox.alert('提示','动拨日期不能大于当前日期');
		        	  return false;
		          }  
		    var data = formPanel.getForm().getFieldValues();
					//var commintData = translateDataKey(data,1);
		      newPanelStroe_1.reload({params:{'pipelineId':data.PIPELINE_ID},
  				callback : function() {
		            var dbAmtsN=0;
					for(var i=0;i<newPanelStroe_1.data.length;i++){
						dbAmtsN+=parseInt(newPanelStroe_1.data.items[i].data.DB_AMT);	
					}
					dbAmtsN+=data.DB_AMT;
					var INSURE_AMT_TEMP =getSelectedData().data.INSURE_AMT;
//					if(dbAmtsN>INSURE_AMT_TEMP){
//						Ext.MessageBox.alert('提示','动拨总金额不能大于核准金额');
//		               		return false;
//					}
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/mktApprovedC!addDB.json',
							method : 'POST',
							params : {'DB_AMT':data.DB_AMT,
									  'PIPELINE_ID':data.PIPELINE_ID,
									  'DB_DATE':data.DB_DATE},
							success : function(response) {
									Ext.MessageBox.alert('提示','数据保存成功!');
									showCustomerViewByTitle('修改');
									newPanelStroe_1.reload({params:{'pipelineId':data.PIPELINE_ID},
  											callback : function() {
           										var dbAmts=0;
												for(var i=0;i<newPanelStroe_1.data.length;i++){
													dbAmts+=parseInt(newPanelStroe_1.data.items[i].data.DB_AMT);	
												}
												getCurrentView().contentPanel.form.findField("DB_AMTS").setValue(dbAmts);	
       								 }});
								}
							}); 
				}});			
		}
	},{
		text:'取消',
		fn:function(formPanel,basicForm){
			showCustomerViewByTitle('修改');
		}
		
	}]
},{
	title:'修改动拨记录',
	hideTitle:true,
	type:'form',
	groups:[{
		labelWidth:150,
		columnCount:2,
		fields:[
		  {name:'DB_AMT',text:'动拨金额(折人民币/千元)',gridField:true,dataType:'money',maxLength:24,allowBlank:false },
		  {name:'DB_DATE',text:'动拨日期',gridField:true,dataType:'date',allowBlank:false},
		  {name:'PIPELINE_ID',hidden:true},
		  {name:'ID',hidden:true}
		]
	}],
	formButtons:[{
		text:'保存',
		fn:function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
		               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		               return false;
		        };
		   var myDate = new Date();     
		   if(formPanel.form.findField('DB_DATE').getValue() > myDate ){
		        	  Ext.MessageBox.alert('提示','动拨日期不能大于当前日期');
		        	  return false;
		          }     
		    var data = formPanel.getForm().getFieldValues();
					//var commintData = translateDataKey(data,1);
		            newPanelStroe_1.reload({params:{'pipelineId':data.PIPELINE_ID},
						callback : function() {
							var dbAmtsM=0;
							for(var i=0;i<newPanelStroe_1.data.length;i++){
								dbAmtsM+=parseInt(newPanelStroe_1.data.items[i].data.DB_AMT);	
							}
							dbAmtsM=dbAmtsM-parseInt(db_amt_selected);
							dbAmtsM+=data.DB_AMT;
							var INSURE_AMT_TEMP =getSelectedData().data.INSURE_AMT;
//							if(dbAmtsM>INSURE_AMT_TEMP){
//								Ext.MessageBox.alert('提示','动拨总金额不能大于核准金额');
//		               			return false;
//							}
							Ext.Msg.wait('正在处理，请稍后......','系统提示');
							Ext.Ajax.request({
									url : basepath + '/mktApprovedC!updateDB.json',
									method : 'POST',
									params : {'DB_AMT':data.DB_AMT,
											  'PIPELINE_ID':data.PIPELINE_ID,
											  'DB_DATE':data.DB_DATE,
											  'ID':data.ID},
									success : function(response) {
											Ext.MessageBox.alert('提示','数据保存成功!');
											showCustomerViewByTitle('修改');
											newPanelStroe_1.reload({params:{'pipelineId':data.PIPELINE_ID},
		  											callback : function() {
		           										var dbAmts=0;
														for(var i=0;i<newPanelStroe_1.data.length;i++){
															dbAmts+=parseInt(newPanelStroe_1.data.items[i].data.DB_AMT);	
														}
														getCurrentView().contentPanel.form.findField("DB_AMTS").setValue(dbAmts);	
		       								 }});
										}
									}); 
							
       				}});
															
		}
	},{
		text:'取消',
		fn:function(formPanel,basicForm){
			showCustomerViewByTitle('修改');
		}
		
	}]
},{
	title:'修改',
	type:'form',
	hideTitle:JsContext.checkGrant('mktApprovedCEdit'),
	autoLoadSeleted : true,
	groups : [{
		labelWidth:150,
		columnCount:2,
		fields :[
		          {name:'CUST_NAME',text:'客户名称', allowBlank:false},
			      {name:'AREA_NAME',text:'区域中心', allowBlank:false},
			      {name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			      {name:'RM',text:'RM',resutlWidth:150},
			      'RECORD_DATE','TREAMENT_DAYS',//新增
			      'APPLY_AMT','CASE_TYPE',
			      {name:'IF_ADD',text:'若为旧案是否为增贷',translateType:'IF_FLAG',allowBalnk:false,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_ADD").getValue();
					if(value == '1'){
						getCurrentView().contentPanel.form.findField("ADD_AMT").show();
					}else if(value == '0'){
						getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
						
					}}
				}},
					{name:'ADD_AMT',text:'<font color="red">*</font>增贷金额(折人民币/千元)',dataType:'money'},
					{name:'IF_ACCEPT',text:'客户是否接受核准额度',translateType:'IF_FLAG',allowBlank:false,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("IF_ACCEPT").getValue();
					if(value == '1'){
						getCurrentView().contentPanel.form.findField("INSURE_AMT").show();
						getCurrentView().contentPanel.form.findField("UNRECEPT_REASON").hide();
						getCurrentView().contentPanel.form.findField("UNRECEPT_REASON").setValue('');
						getCurrentView().contentPanel.form.findField("REASON_REMARK1").hide();
						getCurrentView().contentPanel.form.findField("REASON_REMARK1").setValue('');
						getCurrentView().contentPanel.form.findField("CTR_C_DATE").show();
						getCurrentView().contentPanel.form.findField("CTR_S_DATE").show();
						getCurrentView().contentPanel.form.findField("MORTGAGE_DATE").show();
						getCurrentView().contentPanel.form.findField("FILE_UP_DATE").show();
						getCurrentView().contentPanel.form.findField("SX_CTR_DATE").show();
						getCurrentView().contentPanel.form.findField("CTR_PROBLEM").show();
						getCurrentView().contentPanel.form.findField("PROBLEM_DATE").show();
						getCurrentView().contentPanel.form.findField("AMT_USE_DATE").show();
						getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").show();
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").show();
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").show();
						getCurrentView().contentPanel.form.findField("PAY_DATE").show();
					}else if(value == '0'){
						getCurrentView().contentPanel.form.findField("INSURE_AMT").hide();
						getCurrentView().contentPanel.form.findField("INSURE_CURRENCY").hide();
						getCurrentView().contentPanel.form.findField("INSURE_MONEY").hide();
						getCurrentView().contentPanel.form.findField("UNRECEPT_REASON").show();
						getCurrentView().contentPanel.form.findField("CTR_C_DATE").hide();
						getCurrentView().contentPanel.form.findField("CTR_S_DATE").hide();
						getCurrentView().contentPanel.form.findField("MORTGAGE_DATE").hide();
						getCurrentView().contentPanel.form.findField("FILE_UP_DATE").hide();
						getCurrentView().contentPanel.form.findField("SX_CTR_DATE").hide();
						getCurrentView().contentPanel.form.findField("CTR_PROBLEM").hide();
						getCurrentView().contentPanel.form.findField("PROBLEM_DATE").hide();
						getCurrentView().contentPanel.form.findField("AMT_USE_DATE").hide();
						getCurrentView().contentPanel.form.findField("ACCOUNT_DATE").hide();
						getCurrentView().contentPanel.form.findField("PAY_DATE").hide();
					}}
				}
			},'USE_DATE_P','INSURE_AMT','CTR_S_DATE',
			{name:'UNRECEPT_REASON',text:'<font color="red">*</font>客户不接受核准额度原因',translateType:'UNRECEPT_REASON_APPROVED_C' ,editable: true,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("UNRECEPT_REASON").getValue();
					if(value == '6'){
						getCurrentView().contentPanel.form.findField("REASON_REMARK1").show();
						getCurrentView().contentPanel.form.findField("REASON_REMARK1").allowBlank = false;
					}else{
						getCurrentView().contentPanel.form.findField("REASON_REMARK1").hide();
						getCurrentView().contentPanel.form.findField("REASON_REMARK1").setValue('');
						getCurrentView().contentPanel.form.findField("REASON_REMARK1").allowBlank = true;
					}}
				}},'UNISSUE_REASON','INSURE_CURRENCY','CTR_C_DATE','INSURE_MONEY','DB_AMTS','XD_CHECK_DATE','MORTGAGE_DATE','FILE_UP_DATE','SX_CTR_DATE', 'PROBLEM_DATE','AMT_USE_DATE','ACCOUNT_DATE','PAY_DATE',
			'USER_ID','ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','HP_ID','PIPELINE_ID','LINE'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,APPLY_AMT,CASE_TYPE,IF_ADD
				,ADD_AMT,IF_ACCEPT,USE_DATE_P,INSURE_AMT,CTR_S_DATE,UNRECEPT_REASON,UNISSUE_REASON,INSURE_CURRENCY,CTR_C_DATE,INSURE_MONEY,DB_AMTS,XD_CHECK_DATE,MORTGAGE_DATE,FILE_UP_DATE,SX_CTR_DATE,PROBLEM_DATE,AMT_USE_DATE,
				ACCOUNT_DATE,PAY_DATE,USER_ID,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,HP_ID,PIPELINE_ID,LINE){
				DB_AMTS.readOnly = true;
				DB_AMTS.cls = 'x-readOnly';
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
				IF_ADD.readOnly = true;
				IF_ADD.cls = 'x-readOnly';
				ADD_AMT.readOnly = true;
				ADD_AMT.cls = 'x-readOnly';
				APPLY_AMT.readOnly = true;
				APPLY_AMT.cls = 'x-readOnly';
				INSURE_AMT.readOnly = true;
				INSURE_AMT.cls = 'x-readOnly';
				INSURE_CURRENCY.readOnly = true;
				INSURE_CURRENCY.cls = 'x-readOnly';
				INSURE_MONEY.readOnly = true;
				INSURE_MONEY.cls = 'x-readOnly';
//				IF_ADD.readOnly = true;
//				IF_ADD.cls = 'x-readOnly';
//				ADD_AMT.readOnly = true;
//				ADD_AMT.cls = 'x-readOnly';
				USER_ID.hidden = true;
				ID.hidden = true;
				CUST_ID.hidden = true;
				RM_ID.hidden = true;
				DEPT_ID.hidden = true;
				AREA_ID.hidden = true;
				HP_ID.hidden = true;
				PIPELINE_ID.hidden = true;
				LINE.hidden = true;
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,APPLY_AMT,CASE_TYPE,IF_ADD
				,ADD_AMT,IF_ACCEPT,USE_DATE_P,INSURE_AMT,CTR_S_DATE,UNRECEPT_REASON,UNISSUE_REASON,INSURE_CURRENCY,CTR_C_DATE,INSURE_MONEY,DB_AMTS,XD_CHECK_DATE,MORTGAGE_DATE,FILE_UP_DATE,SX_CTR_DATE,PROBLEM_DATE,AMT_USE_DATE,
				ACCOUNT_DATE,PAY_DATE,USER_ID,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,HP_ID,PIPELINE_ID,LINE];
			}
	},{
		labelWidth:150,  
		columnCount: 1,
		  fields : ['CTR_PROBLEM',
				  	{name:'REASON_REMARK1',text:'<font color="red">*</font>客户不接受核准额度原因说明',xtype:'textarea',maxLength:500},
				   'REASON_REMARK2'],
		  fn : function(CTR_PROBLEM,REASON_REMARK1,REASON_REMARK2){
			  return [CTR_PROBLEM,REASON_REMARK1,REASON_REMARK2];
		  }
		},{
			labelWidth:150,  
			columnCount: 1,
			fields : ['TEST'],
			fn : function(TEST){
				return [newPanel_1];//
			}
	   }],
		formButtons : [{
			text:'提交',
			fn : function(formPanel,basicForm){
				 if (!formPanel.getForm().isValid()) {
		               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		               return false;
		           };
		          if(formPanel.form.findField('IF_ACCEPT').getValue() == '0' &&
		        		  formPanel.form.findField('UNRECEPT_REASON').getValue()== ''){
		        	  Ext.MessageBox.alert('提示','请输入不接受核准额度原因');
		        	  return false;
		          }
		          if(num>30&&( formPanel.form.findField('UNISSUE_REASON').getValue()== ''||formPanel.form.findField('REASON_REMARK2').getValue()== '')
		          &&formPanel.form.findField('PAY_DATE').getValue()== '')
		          {
		          	Ext.MessageBox.alert('提示','请输入未动拨原因或者说明');
		        	  return false;
		          }
		         var data = formPanel.getForm().getFieldValues();
					var commintData = translateDataKey(data,1);
					Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
							url : basepath + '/mktApprovedC!save.json',
							method : 'POST',
							params : commintData,
							success : function(response) {
									Ext.MessageBox.alert('提示','数据保存成功!');
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
		labelWidth:150,
		columnCount:2,
		fields :[ {name:'CUST_NAME',text:'客户名称', allowBlank:false},
			      {name:'AREA_NAME',text:'区域中心', allowBlank:false},
			      {name:'DEPT_NAME',text:'营业部门',allowBlank:false},
			      {name:'RM',text:'RM',resutlWidth:150},
			      'RECORD_DATE','TREAMENT_DAYS',//新增
			      'APPLY_AMT','CASE_TYPE','IF_ADD'
				,{name:'ADD_AMT',text:'<font color="red">*</font>增贷金额(折人民币/千元)',dataType:'money'},
				{name:'IF_ACCEPT',text:'客户是否接受核准额度',translateType:'IF_FLAG'},'USE_DATE_P','INSURE_AMT','CTR_S_DATE',
			{name:'UNRECEPT_REASON',text:'<font color="red">*</font>客户不接受核准额度原因',translateType:'UNRECEPT_REASON_APPROVED_C' ,editable: true,listeners:{
				select:function(){
					var value = getCurrentView().contentPanel.form.findField("UNRECEPT_REASON").getValue();
					if(value == '6'){
						getCurrentView().contentPanel.form.findField("REASON_REMARK1").show();
					}else{
						getCurrentView().contentPanel.form.findField("REASON_REMARK1").hide();
					}}
				}},'UNISSUE_REASON','INSURE_CURRENCY','CTR_C_DATE','INSURE_MONEY','DB_AMTS','XD_CHECK_DATE','MORTGAGE_DATE','FILE_UP_DATE','SX_CTR_DATE', 'PROBLEM_DATE','AMT_USE_DATE','ACCOUNT_DATE','PAY_DATE',
			'USER_ID','ID','CUST_ID','RM_ID','DEPT_ID','AREA_ID','HP_ID','PIPELINE_ID','LINE'],
			fn:function(CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,APPLY_AMT,CASE_TYPE,IF_ADD
				,ADD_AMT,IF_ACCEPT,USE_DATE_P,INSURE_AMT,CTR_S_DATE,UNRECEPT_REASON,UNISSUE_REASON,INSURE_CURRENCY,CTR_C_DATE,INSURE_MONEY,DB_AMTS,XD_CHECK_DATE,MORTGAGE_DATE,FILE_UP_DATE,SX_CTR_DATE,PROBLEM_DATE,AMT_USE_DATE,
				ACCOUNT_DATE,PAY_DATE,USER_ID,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,HP_ID,PIPELINE_ID,LINE){
						DB_AMTS.readOnly = true;
				        DB_AMTS.cls = 'x-readOnly';
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
						INSURE_CURRENCY.readOnly = true;
						INSURE_CURRENCY.cls = 'x-readOnly';
						INSURE_MONEY.readOnly = true;
						INSURE_MONEY.cls = 'x-readOnly';
						IF_ADD.readOnly = true;
						IF_ADD.cls = 'x-readOnly';
						APPLY_AMT.readOnly = true;
						APPLY_AMT.cls = 'x-readOnly';
						ADD_AMT.readOnly = true;
						ADD_AMT.cls = 'x-readOnly';
						INSURE_AMT.readOnly = true;
						INSURE_AMT.cls = 'x-readOnly';
						IF_ACCEPT.readOnly = true;
						IF_ACCEPT.cls = 'x-readOnly';
						UNRECEPT_REASON.readOnly = true;
						UNRECEPT_REASON.cls = 'x-readOnly';
						UNISSUE_REASON.readOnly = true;
						UNISSUE_REASON.cls = 'x-readOnly';
						USE_DATE_P.readOnly = true;
						USE_DATE_P.cls = 'x-readOnly';
						
						CTR_C_DATE.readOnly = true;
						CTR_C_DATE.cls = 'x-readOnly';
						CTR_S_DATE.readOnly = true;
						CTR_S_DATE.cls = 'x-readOnly';
						XD_CHECK_DATE.readOnly = true;
						XD_CHECK_DATE.cls = 'x-readOnly';
						MORTGAGE_DATE.readOnly = true;
						MORTGAGE_DATE.cls = 'x-readOnly';
						FILE_UP_DATE.readOnly = true;
						FILE_UP_DATE.cls = 'x-readOnly';
						SX_CTR_DATE.readOnly = true;
						SX_CTR_DATE.cls = 'x-readOnly';
						PROBLEM_DATE.readOnly = true;
						PROBLEM_DATE.cls = 'x-readOnly';
						AMT_USE_DATE.readOnly = true;
						AMT_USE_DATE.cls = 'x-readOnly';
						ACCOUNT_DATE.readOnly = true;
						ACCOUNT_DATE.cls = 'x-readOnly';
						PAY_DATE.readOnly = true;
						PAY_DATE.cls = 'x-readOnly';
						USER_ID.hidden = true;
						ID.hidden = true;
						CUST_ID.hidden = true;
						RM_ID.hidden = true;
						DEPT_ID.hidden = true;
						AREA_ID.hidden = true;
						HP_ID.hidden = true;
						PIPELINE_ID.hidden = true;					
					    LINE.hidden = true;
				return [CUST_NAME,AREA_NAME,DEPT_NAME,RM,RECORD_DATE,TREAMENT_DAYS,APPLY_AMT,CASE_TYPE,IF_ADD
				,ADD_AMT,IF_ACCEPT,USE_DATE_P,INSURE_AMT,CTR_S_DATE,UNRECEPT_REASON,UNISSUE_REASON,INSURE_CURRENCY,CTR_C_DATE,INSURE_MONEY,DB_AMTS,XD_CHECK_DATE,MORTGAGE_DATE,FILE_UP_DATE,SX_CTR_DATE,PROBLEM_DATE,AMT_USE_DATE,
				ACCOUNT_DATE,PAY_DATE,USER_ID,ID,CUST_ID,RM_ID,DEPT_ID,AREA_ID,HP_ID,PIPELINE_ID,LINE];
			}
	},{   
		  labelWidth:150,
		  columnCount: 1,
		  fields : ['CTR_PROBLEM',
					{name:'REASON_REMARK1',text:'<font color="red">*</font>客户不接受核准额度原因说明',xtype:'textarea',maxLength:500},
					'REASON_REMARK2'],
		  fn : function(CTR_PROBLEM,REASON_REMARK1,REASON_REMARK2){
			  	CTR_PROBLEM.readOnly = true;
			  	REASON_REMARK1.readOnly = true;
			  	REASON_REMARK2.readOnly = true;
			  	CTR_PROBLEM.cls = 'x-readOnly';
			  	REASON_REMARK1.cls = 'x-readOnly';
			  	REASON_REMARK2.cls = 'x-readOnly';
			  return [CTR_PROBLEM,REASON_REMARK1,REASON_REMARK2];
		  }
		},{
			labelWidth:150,  
			columnCount: 1,
			fields : ['TEST2'],
			fn : function(TEST2){
				return [newPanel_2];//
			}
	   }]
}];



var beforeviewshow = function(view){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
	}
	if(view._defaultTitle == '修改'||view._defaultTitle == '详情'){
		   newPanelStroe_1.reload(
				{params:{
					     'pipelineId':getSelectedData().data.PIPELINE_ID
			            }
			     });
			     if(getSelectedData().data.LINE=='3'){
			     	newPanel_1.show();
			     }else{
			     	newPanel_1.hide();
			     }
			view.contentPanel.form.findField("UNISSUE_REASON").show();
			if(getSelectedData().data.UNISSUE_REASON=='6'){
				view.contentPanel.form.findField("REASON_REMARK2").show();
			}else{
				view.contentPanel.form.findField("REASON_REMARK2").hide();
			}
			if(getSelectedData().data.IF_ACCEPT == '1'||getSelectedData().data.IF_ACCEPT == ''){
				view.contentPanel.form.findField("UNRECEPT_REASON").hide();
				view.contentPanel.form.findField("REASON_REMARK1").hide();
				view.contentPanel.form.findField("CTR_C_DATE").show();
				view.contentPanel.form.findField("CTR_S_DATE").show();
				view.contentPanel.form.findField("MORTGAGE_DATE").show();
				view.contentPanel.form.findField("FILE_UP_DATE").show();
				view.contentPanel.form.findField("SX_CTR_DATE").show();
				view.contentPanel.form.findField("CTR_PROBLEM").show();
				view.contentPanel.form.findField("PROBLEM_DATE").show();
				view.contentPanel.form.findField("AMT_USE_DATE").show();
				view.contentPanel.form.findField("ACCOUNT_DATE").show();
				view.contentPanel.form.findField("PAY_DATE").show();
				view.contentPanel.form.findField("CTR_PROBLEM").show();
				view.contentPanel.form.findField("INSURE_CURRENCY").show();
				view.contentPanel.form.findField("INSURE_MONEY").show();
			}else if (getSelectedData().data.IF_ACCEPT == '0'){
				view.contentPanel.form.findField("UNRECEPT_REASON").show();
				if(getSelectedData().data.UNRECEPT_REASON=='6'){
					view.contentPanel.form.findField("REASON_REMARK1").show()
				}else{
					view.contentPanel.form.findField("REASON_REMARK1").hide()
				}
				view.contentPanel.form.findField("CTR_C_DATE").hide();
				view.contentPanel.form.findField("CTR_S_DATE").hide();
				view.contentPanel.form.findField("MORTGAGE_DATE").hide();
				view.contentPanel.form.findField("FILE_UP_DATE").hide();
				view.contentPanel.form.findField("SX_CTR_DATE").hide();
				view.contentPanel.form.findField("CTR_PROBLEM").hide();
				view.contentPanel.form.findField("PROBLEM_DATE").hide();
				view.contentPanel.form.findField("AMT_USE_DATE").hide();
				view.contentPanel.form.findField("ACCOUNT_DATE").hide();
				view.contentPanel.form.findField("PAY_DATE").hide();
				view.contentPanel.form.findField("CTR_PROBLEM").hide();
				view.contentPanel.form.findField("INSURE_CURRENCY").hide();
				view.contentPanel.form.findField("INSURE_MONEY").hide();
		}
	}
	if(view._defaultTitle =='修改动拨记录'){
		record = newPanel_1.getSelectionModel().getSelections()[0];	
		db_amt_selected=record.data.DB_AMT;
		view.contentPanel.form.findField('ID').setValue(record.data.ID);
		view.contentPanel.form.findField('PIPELINE_ID').setValue(record.data.PIPELINE_ID);
		view.contentPanel.form.findField('DB_AMT').setValue(record.data.DB_AMT);
		view.contentPanel.form.findField('DB_DATE').setValue(record.data.DB_DATE);
	
	}
	if(view._defaultTitle =='新增动拨记录'){
		 view.contentPanel.form.findField("PIPELINE_ID").setValue(getSelectedData().data.PIPELINE_ID);
	
	}
};

var viewshow = function(view){
		var CASE_TYPE =getCurrentView().contentPanel.form.findField("CASE_TYPE").getValue();
		if(CASE_TYPE != '2' && CASE_TYPE!='5' &&  CASE_TYPE!='6' &&  CASE_TYPE!='13' ){
			getCurrentView().contentPanel.form.findField("IF_ADD").hide();
			getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
		}else{
			getCurrentView().contentPanel.form.findField("IF_ADD").show();
			if(getCurrentView().contentPanel.form.findField("IF_ADD").getValue() == '1'){
				getCurrentView().contentPanel.form.findField("ADD_AMT").show();
			}else {
				getCurrentView().contentPanel.form.findField("ADD_AMT").hide();
			}
		}
};

