/**
 * 营销活动管理界面
 * hujun
 * 2014-06-26
 * @modify ： luyy  2014-07-28
 * 	helin,20140829,bug fixed and code format
 */
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',
	'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldForSource.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js',
	'/contents/pages/common/LovCombo.js'
]);

var ifappl = JsContext.checkGrant('app_mktActivi');//是否需要审批   在afterinit中查询系统参数获得   默认为true
var opWay = '';//操作方式  add ：新增,   update 修改,    info 查看	
var needReset = false;//用于判断新增时是否需要重置基本信息页面    （因为上一步时不需要）
var tempBaseInfo = {data:{}};//临时保存基本信息

var createView=false;//是否需要新增form
var editView=false;//是否需要修改form
var detailView=false;//是否需要详情form

var appAnna=false;//附件部分
var custId = '' ;
var custName = '';
var mktActiveId='';
var mktActiveName='';

//参数
var _SOURCE_CUST=false;//客户来源于客户查询
var _SOURCE_GROUP=false;//客户来源于客户群
var _SOURCE_PORD=false;//客户来源于产品的目标客户
var _IS_SAVE_CUST = false;//是否保存产品的目标客户
	
	
var url=basepath + '/market-activity.json';
//需要用到的数据字典
var lookupTypes=[
//	                 {TYPE : 'CHANNEL_NAME',
//	                	 url : '/chaneltypeinfo.json?tableName="OCRM_F_MM_CHANNEL_INFO"',
//	                	 key : 'CHANNEL_ID',
//	                	 value : 'CHANNEL_NAME',
//	                	 root : 'json.data'
//	                },
	'MKT_CHANEL',
    'FWQD',
    'MACTI_APPROVE_STAT',
    'MKT_TARGET_CUSTOMER',
    'MKT_COST_SOURCES',
    'ACTI_TYPE',
    'P_CUST_GRADE',
    'MODEL_TYPE'
];
	
//自定义数据字典
var localLookup = {
	'AIM_CUST_SOURCE' : [
		{key : '1',value : '自定义筛选'},
	    {key : '2',value : '客户群导入'}
	]
};

var fields=[
	{name:'COMMIT_COUNT',text:'流程提交次数',hidden:true},
    {name:'MKT_ACTI_ID',text:'营销活动ID',hidden:true},
    {name:'MKT_ACTI_CODE',text:'营销活动编码'},
    {name:'MKT_ACTI_NAME',text:'营销活动名称',searchField:true},
    {name:'MKT_ACTI_STAT',text:'目标客群',searchField:true,resutlWidth:150,translateType : 'MKT_TARGET_CUSTOMER'},
    {name:'MKT_APP_STATE',text:'活动审批状态',gridField:true,resutlWidth:150,translateType : 'MACTI_APPROVE_STAT'},
    {name:'PSTART_DATE',text:'计划开始时间',searchField:true,dataType:'date'},
    {name:'PEND_DATE',text:'计划结束时间',searchField:true,dataType:'date'},
    {name:'PSTART_DATE_S',text:'计划开始时间从',dataType:'date',gridField:false},
    {name:'PSTART_DATE_E',text:'至',gridField:false,dataType:'date'},
    {name:'PEND_DATE_S',text:'计划结束时间从',dataType:'date',gridField:false},
    {name:'PEND_DATE_E',text:'至',gridField:false,dataType:'date'},
    {name:'ASTART_DATE',text:'实际开始日期',dataType:'date',gridField:false},
    {name:'AEND_DATE',text:'实际结束日期',dataType:'date',gridField:false},
    {name:'MKT_ACTI_COST',text:'费用预算',dataType:'money'},
    {name:'MKT_ACTI_ADDR',text:'活动地点',xtype:'textarea',resultWidth:350},
	{name:'CREATE_DATE',text:'创建日期',dataType:'date'},
	{name:'CREATE_USER',text:'创建人编号',gridField:false},
	{name:'USERNAME',text:'创建人'},
	{name:'MKT_CHANEL',text:'营销渠道',gridField:false,multiSelect:true,translateType:'MKT_CHANEL'},
	{name:'MKT_ACTI_TYPE',text:'营销活动目的',hidden:true,allowBlank:false,resutlWidth:150,translateType : 'ACTI_TYPE'},	
  	{name:'MKT_ACTI_MODE',text:'费用来源',hidden:true,allowBlank:false,resutlWidth:150,translateType : 'MKT_COST_SOURCES'},
	{name:'ACTI_CUST_DESC',text:'宣传品类型',hidden:true,allowBlank:false,xtype:'textarea',resultWidth:350},		
	{name:'ACTI_OPER_DESC',text:'预计参与人数',hidden:true,allowBlank:false,xtype:'textfield',resultWidth:350},		
	{name:'ACTI_PROD_DESC',text:'涉及产品描述',hidden:true,xtype:'textarea',resultWidth:350},
	{name:'MKT_ACTI_ADDR',text:'活动地点',hidden:true,allowBlank:false,xtype:'textarea',resultWidth:350},
	{name:'ACTI_REMARK',text:'备注',hidden:true,xtype:'textarea',resultWidth:350},
	{name:'MKT_ACTI_CONT',text:'营销活动内容',hidden:true,allowBlank:false,xtype:'textarea',resultWidth:350}
];
//获取来源渠道 按机构  活动编号 最大编号
function getlargestNumberByOrder(orgId,p){
	Ext.Ajax.request({
		url : basepath + '/market-activity!getlargestNumberByorgid.json',
		method : 'GET',
		params :{
			orgId:orgId
		}, 
		waitMsg : '正在获取数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		success : function(response) {
			var ret = Ext.decode(response.responseText);
			var larges;
			if(p!='1'){
				larges=parseInt(ret.larges)+1;
				getCurrentView().contentPanel.getForm().findField('LARGEST_NUMBER').setValue(larges);
			}
			var str1=getCurrentView().contentPanel.getForm().findField('CODE').getValue();
				var str2=getCurrentView().contentPanel.getForm().findField('CODE_F').getValue();
				var str3=getCurrentView().contentPanel.getForm().findField('LARGEST_NUMBER').getValue();
				if(str1!=""&&str2!=""&&str3!=""){
					str1=Ext.util.Format.date(getCurrentView().contentPanel.getForm().findField('CODE').getValue(),'Ymd');
					getCurrentView().contentPanel.getForm().findField('MKT_ACTI_CODE').setValue(str1+str2+str3);
				}
				
		},
		failure : function() {
			Ext.Msg.alert('提示', '获取编号失败');
			updateChannewindow.hide();
			reloadCurrentData();
		}
	})
}
/**
 * 总行新增直接同步到数据字典
 */
function updateMarketActivity(mktActiId,mktActiCode,mktActiName,actiRemark){
	Ext.Ajax.request({
		url : basepath + '/market-activity!updateMarketActivityql.json',
		method : 'POST',
		params :{
			mktActiId:mktActiId,
			mktActiCode:mktActiCode,
			mktActiName:mktActiName,
			actiRemark:actiRemark
		}, 
		success : function(response) {
		},
		failure : function() {
		}
	})
}
//自定义新增，修改删除按钮
var tbar=[{
	text:'新增',
	hidden:JsContext.checkGrant('add_mktActivi'),
	handler:function(){//控制，保存，新增，删除按钮；
		opWay = "add";
		needReset = true;
		hideCurrentView();
		//getSelectedData().data.MKT_ACTI_ID;
		showCustomerViewByTitle('基本信息');
		 getCurrentView().contentPanel.getForm().findField('CODE').removeClass('x-readOnly');
		 getCurrentView().contentPanel.getForm().findField('CODE_F_NAME').removeClass('x-readOnly');
	}
},{
	text:'修改',
	hidden:JsContext.checkGrant('edit_mktActivi'),
	handler:function(){
		hideCurrentView();
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}else{
			var	createUser = getSelectedData().data.CREATE_USER;
			if(createUser!=__userId){
				Ext.Msg.alert('提示','只能选择自己创建的数据修改!');
				return false;
			}
			var state=getSelectedData().data.MKT_ACTI_STAT;
			var appStatus = getSelectedData().data.MKT_APP_STATE;
			if(appStatus == '2'){
				Ext.Msg.alert('提示','审批中的活动不能修改!');
				return false;
			}
			//if(state =="1"||state =="6"){
				mktActiveId=getSelectedData().data.MKT_ACTI_ID;
				opWay = 'update';
                showCustomerViewByTitle('基本信息');
                var strtmp=getSelectedData().data.MKT_ACTI_CODE;
    			var code=strtmp.substr(0, 8);
    			var code_f=strtmp.substr(8,3);
    			getlargestNumberByOrder(code_f,1);
    			var largest_number=strtmp.substr(11);
    			getCurrentView().contentPanel.getForm().findField('CODE').setValue(code);
    			getCurrentView().contentPanel.getForm().findField('CODE_F_NAME').setValue(code_f);
    			getCurrentView().contentPanel.getForm().findField('CODE_F').setValue(code_f);
    			getCurrentView().contentPanel.getForm().findField('LARGEST_NUMBER').setValue(largest_number);
    			var value=getSelectedData().data.MKT_ACTI_NAME;
	    		var valuev=value.split('-');
	    		getCurrentView().contentPanel.getForm().findField('MKT_ACTI_NAME').setValue(valuev[1]);
    			
    			getCurrentView().contentPanel.getForm().findField('CODE').addClass('x-readOnly');
    			getCurrentView().contentPanel.getForm().findField('CODE_F_NAME').addClass('x-readOnly');
/*                getCurrentView().contentPanel.form.findField("MKT_ACTI_STAT").readOnly = false;
        		getCurrentView().contentPanel.form.findField("MKT_ACTI_STAT").cls = 'x-readOnly';
*/			/*}else{
				Ext.Msg.alert("提示", "只能修改状态为[暂存]或[已退回]的营销活动!");
				return false;
			}*/
		}
	}
},{
	text:'删除',
	hidden:JsContext.checkGrant('dele_mktActivi'),
	handler:function(){
		var records =getAllSelects();// 得到被选择的行的数组
		var selectLength = getAllSelects().length;// 得到行数组的长度
		if (selectLength < 1) {
			Ext.Msg.alert('提示信息', '请至少选择一条记录！');
		} else {
			var serviceId;
			var idStr = '';
			for ( var i = 0; i < selectLength; i++) {
				selectRe = records[i];
				serviceId = selectRe.data.MKT_ACTI_ID;// 获得选中记录的id
				idStr += serviceId;
				if(selectRe.data.CREATE_USER!=__userId){
					Ext.Msg.alert('提示',selectRe.data.MKT_ACTI_NAME+'不是自己创建的数据!');
				   break;
				}
				if(selectRe.data.MKT_APP_STATE!='1'||selectRe.data.MKT_APP_STATE!='4'){
					Ext.Msg.alert("提示", selectRe.data.MKT_ACTI_NAME+"不是[未提交]或者[审批未通过]的营销活动!");
					break;
				}
				if (i != selectLength - 1)
					idStr += ',';
				Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
					return;
					} 
					Ext.Ajax.request({
								url : basepath
								+ '/market-activity!batchDestroy.json?idStr='+ idStr,
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									reloadCurrentData();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									if(resultArray == 403) {
								           Ext.Msg.alert('提示', response.responseText);
								  } else {
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									reloadCurrentData();
								  }
								}
							});
				});
			};
		}
	}
},{
	text:'详情',
	handler:function(){
		hideCurrentView();
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}else{
			opWay = 'info';
			mktActiveId=getSelectedData().data.MKT_ACTI_ID;
            showCustomerViewByTitle('基本信息详情');
            var strtmp=getSelectedData().data.MKT_ACTI_CODE;
			var code=strtmp.substr(0, 8);
			var code_f=strtmp.substr(8,3);
			getlargestNumberByOrder(code_f,1);
			var largest_number=strtmp.substr(11);
			getCurrentView().contentPanel.getForm().findField('CODE').setValue(code);
			getCurrentView().contentPanel.getForm().findField('CODE_F_NAME').setValue(code_f);
			getCurrentView().contentPanel.getForm().findField('CODE_F').setValue(code_f);
			getCurrentView().contentPanel.getForm().findField('LARGEST_NUMBER').setValue(largest_number);
			var value=getSelectedData().data.MKT_ACTI_NAME;
    		var valuev=value.split('-');
    		getCurrentView().contentPanel.getForm().findField('MKT_ACTI_NAME').setValue(valuev[1]);
		}
	}
},{
	text:'提交审批',
	id:'ifappl',
	hidden:JsContext.checkGrant('app_mktActivi'),
	handler:function(){
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}else{
			var	createUser = getSelectedData().data.CREATE_USER;
			if(createUser!=__userId){
				Ext.Msg.alert('提示','只能选择自己创建的数据!');
				return false;
			}
			var appStatus = getSelectedData().data.MKT_APP_STATE;
			var id=getSelectedData().data.MKT_ACTI_ID;
			if(appStatus=='1'){
				var id=getSelectedData().data.MKT_ACTI_ID;
				Ext.MessageBox.confirm('提示','确定执行吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
						return;
					} 
					Ext.Ajax.request({
						url : basepath
						+ '/market-activity!applySave.json',
						params:{
							'instanceid':id,
							'name':getSelectedData().data.MKT_ACTI_NAME,
							'mktActiMode':getSelectedData().data.MKT_ACTI_MODE
						},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function(response) {
							var ret = Ext.decode(response.responseText);
							var instanceid = ret.instanceid;//流程实例ID
	 						var currNode = ret.currNode;//当前节点
	 						var nextNode = ret.nextNode;//下一步节点
	 						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
						
						},
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
							if(resultArray == 403) {
						           Ext.Msg.alert('提示', response.responseText);
						  } else {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							reloadCurrentData();
						  }
						}
					});
				});
			}else{
				Ext.Msg.alert('提示','只能选择未提交的数据!');
				return false;
			}
		}
	}
}];




var customerView=[{
	title : '基本信息',
	type : 'form',
	hideTitle : true,
	groups:[{
		columnCount :3,
		fields : [
			
			{name: 'CODE',text:'活动开始日期',resutlWidth:60,format:'Ymd',xtype:'datefield',allowBlank: false, listeners : {
  				"select" : function() {
  					var OrgId=getCurrentView().contentPanel.getForm().findField('CODE_F').getValue();
  					if(OrgId!=''&&OrgId!=null&&OrgId!=undefined){
  						getlargestNumberByOrder(OrgId);
  					}
  					
				}
  			}},
			{name:'CODE_F_NAME',text:'所属分支行',hiddenName:'CODE_F',allowBlank:false,xtype:'orgchooseSource',singleSelected:true},
			{name: 'LARGEST_NUMBER',text : '活动场次',xtype:'textfield',resutlWidth:50},
			{name:'CODE_F',text:'-',allowBlank:false,hidden:true}
			
		  ],
		fn : function(CODE,CODE_F_NAME,LARGEST_NUMBER,CODE_F){
			CODE_F.hidden = true;
			LARGEST_NUMBER.readOnly = true;
			LARGEST_NUMBER.cls = 'x-readOnly';
			LARGEST_NUMBER.anchor = '85%';
			return [CODE,CODE_F_NAME,LARGEST_NUMBER,CODE_F];
		}
	  },{
			columnCount : 1 ,
			fields : [
                {name: 'MKT_ACTI_CODE',text:'营销活动编码',xtype:'textfield',  resutlWidth:350}
			  ],
			fn : function(MKT_ACTI_CODE){
				MKT_ACTI_CODE.readOnly = true;
				MKT_ACTI_CODE.cls = 'x-readOnly';
				MKT_ACTI_CODE.anchor = '95%';
				return [MKT_ACTI_CODE];
			}
		},{
		columnCount : 2 ,
		fields : [	
          	{name:'MKT_ACTI_NAME',text:'营销活动名称',allowBlank:false}
          	,'MKT_CHANEL',
          	{name:'MKT_ACTI_TYPE',text:'营销活动目的',allowBlank:false,resutlWidth:150,translateType : 'ACTI_TYPE'},	
          	{name:'MKT_ACTI_STAT',text:'目标客群',editable : false,translateType : 'MKT_TARGET_CUSTOMER'},
          	{name:'MKT_ACTI_MODE',text:'费用来源',allowBlank:false,resutlWidth:150,translateType : 'MKT_COST_SOURCES'},
          	{name:'MKT_ACTI_COST',text:'费用预算',allowBlank:false,viewFn:money('0,000.00')},
          	{name:'PSTART_DATE',text:'计划开始时间',allowBlank:false,xtype:'datefield',format:'Y-m-d'},
          	{name:'PEND_DATE',text:'计划结束时间',allowBlank:false,xtype:'datefield',format:'Y-m-d'},
          	{name:'ASTART_DATE',text:'实际开始日期',xtype:'datefield',format:'Y-m-d',hidden:true},
          	{name:'AEND_DATE',text:'实际结束日期',xtype:'datefield',format:'Y-m-d',hidden:true},
			{name:'CREATE_DATE',text:'创建日期',xtype:'datefield',format:'Y-m-d',hidden:true},
			{name:'MKT_ACTI_ID',text:'营销活动ID',hidden:true},
			{name:'CREATE_USER',text:'创建人编号',hidden:true},
			{name:'CREATE_ORG',text:'创建机构',hidden:true},
			{name:'USERNAME',text:'创建人',hidden:true},
			{name:'MKT_APP_STATE',id:'MKT_APP_STATE',text:'审批状态',translateType : 'MACTI_APPROVE_STAT',hidden:true},
			{name:'UPDATE_DATE',text:'更新时间',xtype:'datefield',format:'Y-m-d',hidden:true},
			{name:'UPDATE_USER',text:'更新人',hidden:true}
		],
		fn : function(MKT_ACTI_NAME,MKT_CHANEL,MKT_ACTI_TYPE,MKT_ACTI_STAT,MKT_ACTI_MODE,MKT_ACTI_COST,PSTART_DATE,PEND_DATE,ASTART_DATE,AEND_DATE,
			CREATE_DATE,MKT_ACTI_ID,CREATE_USER,CREATE_ORG,USERNAME,MKT_APP_STATE,UPDATE_DATE,UPDATE_USER){
//				MKT_ACTI_NAME.readOnly = true;
//				MKT_ACTI_NAME.cls = 'x-readOnly';
//				MKT_CHANEL.readOnly = true;
//				MKT_CHANEL.cls = 'x-readOnly';
//				MKT_ACTI_TYPE.readOnly = true;
//				MKT_ACTI_TYPE.cls = 'x-readOnly';
//				MKT_ACTI_STAT.readOnly = true;
//	 			MKT_ACTI_STAT.cls = 'x-readOnly';
//				MKT_ACTI_MODE.readOnly = true;
//				MKT_ACTI_MODE.cls = 'x-readOnly';
//				MKT_ACTI_COST.readOnly = true;
//				MKT_ACTI_COST.cls = 'x-readOnly';
//				PSTART_DATE.readOnly = true;
//				PSTART_DATE.cls = 'x-readOnly';
//				PEND_DATE.readOnly = true;
//				PEND_DATE.cls = 'x-readOnly';
				CREATE_DATE.hidden=true;
				MKT_ACTI_ID.hidden = true;
				CREATE_USER.hidden=true;
				CREATE_ORG.hidden=true;
				USERNAME.hidden=true;
				MKT_APP_STATE.hidden=true;
				UPDATE_DATE.hidden=true;
				UPDATE_USER.hidden=true;
				ASTART_DATE.hidden=true;
				AEND_DATE.hidden=true;
			return [MKT_ACTI_NAME,MKT_CHANEL,MKT_ACTI_TYPE,MKT_ACTI_STAT,MKT_ACTI_MODE,MKT_ACTI_COST,PSTART_DATE,PEND_DATE,ASTART_DATE,AEND_DATE,
			CREATE_DATE,MKT_ACTI_ID,CREATE_USER,CREATE_ORG,USERNAME,MKT_APP_STATE,UPDATE_DATE,UPDATE_USER];
		}
	},{
		columnCount : 1 ,
		fields : [
	        {name:'MKT_ACTI_ADDR',text:'活动地点',allowBlank:false,xtype:'textarea',resultWidth:350},
          	{name:'MKT_ACTI_CONT',text:'营销活动内容',allowBlank:false,xtype:'textarea',resultWidth:350},
          	{name:'ACTI_CUST_DESC',text:'宣传品类型',allowBlank:false,xtype:'checkboxgroup',vertical : true,columns: 7
          	,items:[{boxLabel:'易拉宝',name:'ACTI_CUST_DESC1',inputValue:'易拉宝'},
          	        {boxLabel:'二维码',name:'ACTI_CUST_DESC2',inputValue:'二维码'},
          	        {boxLabel:'海报',name:'ACTI_CUST_DESC3',inputValue:'海报'},
          	        {boxLabel:'杂志内页',name:'ACTI_CUST_DESC4',inputValue:'杂志内页'},
          	        {boxLabel:'立牌',name:'ACTI_CUST_DESC5',inputValue:'立牌'},
          	        {boxLabel:'其他',name:'ACTI_CUST_DESC6',inputValue:'其他'},
          	        {boxLabel:'微信文章宣传',name:'ACTI_CUST_DESC7',inputValue:'微信文章宣传'}
          	      ]
          	},
			{name:'ACTI_OPER_DESC',text:'预计参与人数',allowBlank:false,xtype:'numberfield',resultWidth:350},		
			{name:'ACTI_PROD_DESC',text:'涉及产品描述',xtype:'textarea',resultWidth:350},
			{name:'ACTI_REMARK',text:'备注',xtype:'textarea',resultWidth:350}
		],
		fn : function(MKT_ACTI_ADDR, MKT_ACTI_CONT,ACTI_CUST_DESC,ACTI_OPER_DESC,
			ACTI_PROD_DESC,ACTI_REMARK){
//				MKT_ACTI_ADDR.readOnly = true;
//				MKT_ACTI_ADDR.cls = 'x-readOnly';
//				MKT_ACTI_CONT.readOnly = true;
//				MKT_ACTI_CONT.cls = 'x-readOnly';
//				ACTI_CUST_DESC.readOnly = true;
//				ACTI_CUST_DESC.cls = 'x-readOnly';
//				ACTI_OPER_DESC.readOnly = true;
//				ACTI_OPER_DESC.cls = 'x-readOnly';
//				ACTI_PROD_DESC.readOnly = true;
//				ACTI_PROD_DESC.cls = 'x-readOnly';
//				MKT_ACTI_AIM.readOnly = true;
//				MKT_ACTI_AIM.cls = 'x-readOnly';
//				ACTI_REMARK.readOnly = true;
//				ACTI_REMARK.cls = 'x-readOnly';
			MKT_ACTI_ADDR.anchor = '95%';
			MKT_ACTI_CONT.anchor = '95%';
			ACTI_CUST_DESC.anchor = '96%';
			ACTI_OPER_DESC.anchor = '95%';
			ACTI_PROD_DESC.anchor = '95%';
			ACTI_REMARK.anchor = '95%';
			return [MKT_ACTI_ADDR, MKT_ACTI_CONT,ACTI_CUST_DESC,ACTI_OPER_DESC,
			ACTI_PROD_DESC,ACTI_REMARK];
		}
	}],
	formButtons:[{
		text : '保存',
		id:'saveAdd',
		fn : function(formPanel,basicForm) {
			needReset = false;
				if(!basicForm.isValid()){
					Ext.Msg.alert('提示','请输入完整！');
					return false;
				}
                var _date = new Date();
                var _pstartDate = basicForm.findField('PSTART_DATE').getValue();
                var _pendDate   = basicForm.findField('PEND_DATE').getValue();
                if(_pstartDate.format('Y-m-d')<_date.format('Y-m-d')){
                	Ext.MessageBox.alert('提示','计划开始时间不能小于今天');
                	return false;
                }if(_pendDate<_pstartDate){
                	Ext.MessageBox.alert('提示','计划开始时间不能小于计划结束时间');
                	return false;
                }
    			Ext.MessageBox.confirm('提示','你填写的记录将要被保存，确定要执行吗?',function(buttonId){
    				if(buttonId.toLowerCase() == "no"){
    					return;
    				}
        			var commintData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
        			delete commintData.createDate;
        			var tempstr='';
        			 var ss = basicForm.findField('ACTI_CUST_DESC').getValue();
					 for(var i=0;ss.length>i;i++){
						 if(tempstr==''){
							 tempstr=ss[i].inputValue;
						 }else{
						 tempstr = tempstr+','+ ss[i].inputValue;
						 }
					 }
					 commintData.actiCustDesc=tempstr;
        			var tempstr=commintData.mktActiName;
        			commintData.mktActiName=commintData.mktActiCode+"-"+tempstr;
        			
        			Ext.Ajax.request({
        				url : basepath + '/market-activity.json',
        				params :commintData,
        				method : 'POST',
        				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
        				success : function(re) {
        					 Ext.Ajax.request({
        				     	url: basepath +'/market-activity!getPid.json',
        				        waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
        					    success:function(response){
        							 var mktActStr = Ext.util.JSON.decode(response.responseText).pid;
        							 basicForm.findField('MKT_ACTI_ID').setValue(mktActStr);
        							 uploadForm.relaId = mktActStr;
    				    			 mktActiveId=basicForm.findField('MKT_ACTI_ID').getValue();
    				    			 mktActiCode=basicForm.findField('MKT_ACTI_CODE').getValue();
    				    			 mktActiveName=basicForm.findField('MKT_ACTI_NAME').getValue();
    				    			 actiRemark=basicForm.findField('ACTI_REMARK').getValue();
    				    			 tempBaseInfo.data = basicForm.getFieldValues();
    				    			 var mktActiveNamev=mktActiCode+"-"+mktActiveName;
    				    			 updateMarketActivity(mktActiveId,mktActiCode,mktActiveNamev,actiRemark);
    				    			 Ext.Msg.alert('提示', '操作成功'); 
    				    			 hideCurrentView();
    				    		     reloadCurrentData();
    					        }
        					});
        				},
        				failure : function(response) {
        					var resultArray = Ext.util.JSON.decode(response.status);
        				    if(resultArray == 403) {
        				        Ext.Msg.alert('系统提示', response.responseText);
        				  	} else{
        						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
        					}
        				}
        			});
        		});
    		
    	}
	},{
		text : '暂存',
		id:'tempAdd',
		fn : function(formPanel,basicForm) {
			needReset = false;
				if(!basicForm.isValid()){
					Ext.Msg.alert('提示','请输入完整！');
					return false;
				}
                var _date = new Date();
                var _pstartDate = basicForm.findField('PSTART_DATE').getValue();
                var _pendDate   = basicForm.findField('PEND_DATE').getValue();
                if(_pstartDate.format('Y-m-d')<_date.format('Y-m-d')){
                	Ext.MessageBox.alert('提示','计划开始时间不能小于今天');
                	return false;
                }if(_pendDate<_pstartDate){
                	Ext.MessageBox.alert('提示','计划开始时间不能小于计划结束时间');
                	return false;
                }
    			Ext.MessageBox.confirm('提示','你填写的记录将要被保存，确定要执行吗?',function(buttonId){
    				if(buttonId.toLowerCase() == "no"){
    					return;
    				}
        			var commintData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
        			delete commintData.createDate;
        			var tempstr='';
        			 var ss = basicForm.findField('ACTI_CUST_DESC').getValue();
					 for(var i=0;ss.length>i;i++){
						 if(tempstr==''){
							 tempstr=ss[i].inputValue;
						 }else{
						 tempstr = tempstr+','+ ss[i].inputValue;
						 }
					 }
					 commintData.actiCustDesc=tempstr;
        			var tempstr=commintData.mktActiName;
        			commintData.mktActiName=commintData.mktActiCode+"-"+tempstr;
        			Ext.Ajax.request({
        				url : basepath + '/market-activity.json',
        				params :commintData,
        				method : 'POST',
        				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
        				success : function(re) {
        					 Ext.Ajax.request({
        				     	url: basepath +'/market-activity!getPid.json',
        				        waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
        					    success:function(response){
        							 var mktActStr = Ext.util.JSON.decode(response.responseText).pid;
        							 basicForm.findField('MKT_ACTI_ID').setValue(mktActStr);
        							 uploadForm.relaId = mktActStr;
    								 Ext.Msg.alert('提示', '操作成功'); 
    				    			 mktActiveId=basicForm.findField('MKT_ACTI_ID').getValue();
    				    			 mktActiveName=basicForm.findField('MKT_ACTI_NAME').getValue();
    				    			 tempBaseInfo.data = basicForm.getFieldValues();
    					    		 //showCustomerViewByTitle('关联产品信息');
    				    			 hideCurrentView();
    				    		     reloadCurrentData();
    					        }
        					});
        				},
        				failure : function(response) {
        					var resultArray = Ext.util.JSON.decode(response.status);
        				    if(resultArray == 403) {
        				        Ext.Msg.alert('系统提示', response.responseText);
        				  	} else{
        						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
        					}
        				}
        			});
        		});
    	}
	},{
		text:'提交审批',
		id:'ifappl1',
		fn : function(formPanel,basicForm) {
			needReset = false;
			
				if(!basicForm.isValid()){
					Ext.Msg.alert('提示','请输入完整！');
					return false;
				}
                var _date = new Date();
                var _pstartDate = basicForm.findField('PSTART_DATE').getValue();
                var _pendDate   = basicForm.findField('PEND_DATE').getValue();
                if(_pstartDate.format('Y-m-d')<_date.format('Y-m-d')){
                	Ext.MessageBox.alert('提示','计划开始时间不能小于今天');
                	return false;
                }if(_pendDate<_pstartDate){
                	Ext.MessageBox.alert('提示','计划开始时间不能小于计划结束时间');
                	return false;
                }
    			Ext.MessageBox.confirm('提示','你填写的记录将要被提交审批，确定要执行吗?',function(buttonId){
    				if(buttonId.toLowerCase() == "no"){
    					return;
    				}
        			var commintData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
        			delete commintData.createDate;
        			var tempstr='';
        			 var ss = basicForm.findField('ACTI_CUST_DESC').getValue();
					 for(var i=0;ss.length>i;i++){
						 if(tempstr==''){
							 tempstr=ss[i].inputValue;
						 }else{
						 tempstr = tempstr+','+ ss[i].inputValue;
						 }
					 }
					 commintData.actiCustDesc=tempstr;
        			var tempstr=commintData.mktActiName;
        			commintData.mktActiName=commintData.mktActiCode+"-"+tempstr;
        			Ext.Ajax.request({
        				url : basepath + '/market-activity.json',
        				params :commintData,
        				method : 'POST',
        				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
        				success : function(re) {
        					 Ext.Ajax.request({
        				     	url: basepath +'/market-activity!getPid.json',
        				        waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
        					    success:function(response){
        							 var mktActStr = Ext.util.JSON.decode(response.responseText).pid;
        							 basicForm.findField('MKT_ACTI_ID').setValue(mktActStr);
        							 uploadForm.relaId = mktActStr;
    								 Ext.Msg.alert('提示', '操作成功'); 
    				    			 mktActiveId=basicForm.findField('MKT_ACTI_ID').getValue();
    				    			 mktActiveName=basicForm.findField('MKT_ACTI_NAME').getValue();
    				    			 var mktActiMode=basicForm.findField('MKT_ACTI_MODE').getValue();
    				    			 tempBaseInfo.data = basicForm.getFieldValues();
    				    			 Ext.Ajax.request({
    				 					url : basepath+ '/market-activity!applySave.json',
    				 					params:{
    				 						'instanceid':mktActiveId,
    				 						'name':mktActiveName,
    				 						'mktActiMode':mktActiMode
    				 					},
    				 					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
    				 					success : function(response) {
    				 						var ret = Ext.decode(response.responseText);
    				 						var instanceid = ret.instanceid;//流程实例ID
    				 						var currNode = ret.currNode;//当前节点
    				 						var nextNode = ret.nextNode;//下一步节点
    				 						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
    				 					},
    				 					failure : function(response) {
    				 						var resultArray = Ext.util.JSON.decode(response.status);
    				 						if(resultArray == 403) {
    				 					        Ext.Msg.alert('提示', response.responseText);
    				 					  	} else {
    				 							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    				 							reloadCurrentData();
    				 					  	}
    				 					}
    				 				});
    				    			 hideCurrentView();
    				    		     reloadCurrentData();
    					        }
        					});
        				},
        				failure : function(response) {
        					var resultArray = Ext.util.JSON.decode(response.status);
        				    if(resultArray == 403) {
        				        Ext.Msg.alert('系统提示', response.responseText);
        				  	} else{
        						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
        					}
        				}
        			});
        		});
    		
    	}
	}]
},{
	
	title : '基本信息详情',
	type : 'form',
	hideTitle : true,
	groups:[{
		columnCount :3,
		fields : [
			
			{name: 'CODE',text:'活动开始日期',resutlWidth:60,format:'Ymd',xtype:'datefield',allowBlank: false, listeners : {
  				"select" : function() {
  					var OrgId=getCurrentView().contentPanel.getForm().findField('CODE_F').getValue();
  					if(OrgId!=''&&OrgId!=null&&OrgId!=undefined){
  						getlargestNumberByOrder(OrgId);
  					}
  					
				}
  			}},
			{name:'CODE_F_NAME',text:'所属分支行',hiddenName:'CODE_F',allowBlank:false,xtype:'orgchooseSource',singleSelected:true},
			{name: 'LARGEST_NUMBER',text : '活动场次',xtype:'textfield',resutlWidth:50},
			{name:'CODE_F',text:'-',allowBlank:false,hidden:true}
			
		  ],
		fn : function(CODE,CODE_F_NAME,LARGEST_NUMBER,CODE_F){
			CODE_F.hidden = true;
			CODE.readOnly = true;
			CODE.cls = 'x-readOnly';
			CODE_F_NAME.readOnly = true;
			CODE_F_NAME.cls = 'x-readOnly';
			LARGEST_NUMBER.readOnly = true;
			LARGEST_NUMBER.cls = 'x-readOnly';
			LARGEST_NUMBER.anchor = '85%';
			return [CODE,CODE_F_NAME,LARGEST_NUMBER,CODE_F];
		}
	  },{
			columnCount : 1 ,
			fields : [
                {name: 'MKT_ACTI_CODE',text:'营销活动编码',xtype:'textfield',  resutlWidth:350}
			  ],
			fn : function(MKT_ACTI_CODE){
				MKT_ACTI_CODE.readOnly = true;
				MKT_ACTI_CODE.cls = 'x-readOnly';
				MKT_ACTI_CODE.anchor = '95%';
				return [MKT_ACTI_CODE];
			}
		},{
		columnCount : 2 ,
		fields : [	
          	{name:'MKT_ACTI_NAME',text:'营销活动名称',allowBlank:false}
          	,'MKT_CHANEL',
          	{name:'MKT_ACTI_TYPE',text:'营销活动目的',allowBlank:false,resutlWidth:150,translateType : 'ACTI_TYPE'},	
          	{name:'MKT_ACTI_STAT',text:'目标客群',editable : false,translateType : 'MKT_TARGET_CUSTOMER'},
          	{name:'MKT_ACTI_MODE',text:'费用来源 ',allowBlank:false,resutlWidth:150,translateType : 'MKT_COST_SOURCES'},
          	{name:'MKT_ACTI_COST',text:'费用预算',allowBlank:false,viewFn:money('0,000.00')},
          	{name:'PSTART_DATE',text:'计划开始时间',allowBlank:false,xtype:'datefield',format:'Y-m-d'},
          	{name:'PEND_DATE',text:'计划结束时间',allowBlank:false,xtype:'datefield',format:'Y-m-d'},
          	{name:'ASTART_DATE',text:'实际开始日期',xtype:'datefield',format:'Y-m-d',hidden:true},
          	{name:'AEND_DATE',text:'实际结束日期',xtype:'datefield',format:'Y-m-d',hidden:true},
			{name:'CREATE_DATE',text:'创建日期',xtype:'datefield',format:'Y-m-d',hidden:true},
			{name:'MKT_ACTI_ID',text:'营销活动ID',hidden:true},
			{name:'CREATE_USER',text:'创建人编号',hidden:true},
			{name:'CREATE_ORG',text:'创建机构',hidden:true},
			{name:'USERNAME',text:'创建人',hidden:true},
			{name:'MKT_APP_STATE',id:'MKT_APP_STATE',text:'审批状态',translateType : 'MACTI_APPROVE_STAT',hidden:true},
			{name:'UPDATE_DATE',text:'更新时间',xtype:'datefield',format:'Y-m-d',hidden:true},
			{name:'UPDATE_USER',text:'更新人',hidden:true}
		],
		fn : function(MKT_ACTI_NAME,MKT_CHANEL,MKT_ACTI_TYPE,MKT_ACTI_STAT,MKT_ACTI_MODE,MKT_ACTI_COST,PSTART_DATE,PEND_DATE,ASTART_DATE,AEND_DATE,
			CREATE_DATE,MKT_ACTI_ID,CREATE_USER,CREATE_ORG,USERNAME,MKT_APP_STATE,UPDATE_DATE,UPDATE_USER){
			    MKT_ACTI_NAME.readOnly = true;
				MKT_ACTI_NAME.cls = 'x-readOnly';
				MKT_CHANEL.readOnly = true;
				MKT_CHANEL.cls = 'x-readOnly';
				MKT_ACTI_TYPE.readOnly = true;
				MKT_ACTI_TYPE.cls = 'x-readOnly';
				MKT_ACTI_STAT.readOnly = true;
				MKT_ACTI_STAT.cls = 'x-readOnly';
				MKT_ACTI_MODE.readOnly = true;
				MKT_ACTI_MODE.cls = 'x-readOnly';
				MKT_ACTI_COST.readOnly = true;
				MKT_ACTI_COST.cls = 'x-readOnly';
				PSTART_DATE.readOnly = true;
				PSTART_DATE.cls = 'x-readOnly';
				PEND_DATE.readOnly = true;
				PEND_DATE.cls = 'x-readOnly';
				CREATE_DATE.hidden=true;
				MKT_ACTI_ID.hidden = true;
				CREATE_USER.hidden=true;
				CREATE_ORG.hidden=true;
				USERNAME.hidden=true;
				MKT_APP_STATE.hidden=true;
				UPDATE_DATE.hidden=true;
				UPDATE_USER.hidden=true;
				ASTART_DATE.hidden=true;
				AEND_DATE.hidden=true;
			return [MKT_ACTI_NAME,MKT_CHANEL,MKT_ACTI_TYPE,MKT_ACTI_STAT,MKT_ACTI_MODE,MKT_ACTI_COST,PSTART_DATE,PEND_DATE,ASTART_DATE,AEND_DATE,
			CREATE_DATE,MKT_ACTI_ID,CREATE_USER,CREATE_ORG,USERNAME,MKT_APP_STATE,UPDATE_DATE,UPDATE_USER];
		}
	},{
		columnCount : 1 ,
		fields : [
	        {name:'MKT_ACTI_ADDR',text:'活动地点',allowBlank:false,xtype:'textarea',resultWidth:350},
          	{name:'MKT_ACTI_CONT',text:'营销活动内容',allowBlank:false,xtype:'textarea',resultWidth:350},
        	{name:'ACTI_CUST_DESC',text:'宣传品类型',allowBlank:false,xtype:'checkboxgroup',vertical : true,columns: 7
              	,items:[{boxLabel:'易拉宝',name:'ACTI_CUST_DESC1',inputValue:'易拉宝'},
              	        {boxLabel:'二维码',name:'ACTI_CUST_DESC2',inputValue:'二维码'},
              	        {boxLabel:'海报',name:'ACTI_CUST_DESC3',inputValue:'海报'},
              	        {boxLabel:'杂志内页',name:'ACTI_CUST_DESC4',inputValue:'杂志内页'},
              	        {boxLabel:'立牌',name:'ACTI_CUST_DESC5',inputValue:'立牌'},
              	        {boxLabel:'其他',name:'ACTI_CUST_DESC6',inputValue:'其他'},
              	        {boxLabel:'微信文章宣传',name:'ACTI_CUST_DESC7',inputValue:'微信文章宣传'}
              	      ]
              	},		
			{name:'ACTI_OPER_DESC',text:'预计参与人数',allowBlank:false,xtype:'textfield',resultWidth:350},		
			{name:'ACTI_PROD_DESC',text:'涉及产品描述',xtype:'textarea',resultWidth:350},
			{name:'ACTI_REMARK',text:'备注',xtype:'textarea',resultWidth:350}
		],
		fn : function(MKT_ACTI_ADDR, MKT_ACTI_CONT,ACTI_CUST_DESC,ACTI_OPER_DESC,
			ACTI_PROD_DESC,ACTI_REMARK){
				MKT_ACTI_ADDR.readOnly = true;
				MKT_ACTI_ADDR.cls = 'x-readOnly';
				MKT_ACTI_CONT.readOnly = true;
				MKT_ACTI_CONT.cls = 'x-readOnly';
				ACTI_CUST_DESC.readOnly = true;
				ACTI_CUST_DESC.cls = 'x-readOnly';
				ACTI_OPER_DESC.readOnly = true;
				ACTI_OPER_DESC.cls = 'x-readOnly';
				ACTI_PROD_DESC.readOnly = true;
				ACTI_PROD_DESC.cls = 'x-readOnly';
				ACTI_REMARK.readOnly = true;
				ACTI_REMARK.cls = 'x-readOnly';
			MKT_ACTI_ADDR.anchor = '95%';
			MKT_ACTI_CONT.anchor = '95%';
			ACTI_CUST_DESC.anchor = '96%';
			ACTI_OPER_DESC.anchor = '95%';
			ACTI_PROD_DESC.anchor = '95%';
			ACTI_REMARK.anchor = '95%';
			return [MKT_ACTI_ADDR, MKT_ACTI_CONT,ACTI_CUST_DESC,ACTI_OPER_DESC,
			ACTI_PROD_DESC,ACTI_REMARK];
		}
	}],
	formButtons:[{
		text:'导出详情',
		fn:function(formPanel,basicForm){
			Ext.Ajax.request({
				url : basepath + '/market-activity!createWordReport.json',
				method : 'POST',
				params : {
					mktActiId:formPanel.getForm().getFieldValues().MKT_ACTI_ID,
					reportType:'marketActivitY',
					username:formPanel.getForm().getFieldValues().USERNAME,
					createDate:formPanel.getForm().getFieldValues().CREATE_DATE
				},
				success : function() {
					top.window.open( basepath+'/TempDownload?filename=marketActivity'+formPanel.getForm().getFieldValues().MKT_ACTI_ID+'.doc','', 
							'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
				},	
				failure : function() {
					Ext.Msg.alert('提示', '生成营销活动详情文档失败！');	
				}
			});
		}
	},{
		text : '关  闭',
		fn : function() {
			hideCurrentView();
		}
	}]
}];
	
	

	
var viewshow = function(view){
	if(view._defaultTitle == '基本信息详情'){
		view.contentPanel.form.loadRecord(getSelectedData());
	}
	if(view._defaultTitle == '基本信息'){
		var roleStr=__roleCodes.toString();
		if(roleStr.indexOf("admin")>=0){
			ifappl=true;
		}
		if(opWay == 'add'){
			if(needReset){
				if(view.contentPanel.form.getEl()){
					view.contentPanel.form.getEl().dom.reset();
				}
				if(ifappl){//不用审批
					view.setValues({
						//MKT_ACTI_STAT:'1',
						MKT_APP_STATE:'3'
					});
				}else{
					view.setValues({
						//MKT_ACTI_STAT:'1',
						MKT_APP_STATE:'1'
					});
				}
			}else{
				//基本信息
				view.contentPanel.form.loadRecord(tempBaseInfo);
			}
			
			
			if(ifappl){//不用审批
				Ext.getCmp('ifappl1').hide();
				Ext.getCmp('tempAdd').hide();
				Ext.getCmp('saveAdd').show();
			}else{
				if(opWay != 'info'){
					Ext.getCmp('ifappl1').show();
					Ext.getCmp('tempAdd').show();
					Ext.getCmp('saveAdd').hide();
				}else{
					Ext.getCmp('ifappl1').hide();
				}
			}
		}else{
			if(opWay == 'update'){
				if(ifappl){//不用审批
					Ext.getCmp('ifappl1').hide();
					Ext.getCmp('tempAdd').hide();
					Ext.getCmp('saveAdd').show();
				}else{
					if(opWay != 'info'){
						Ext.getCmp('ifappl1').show();
						Ext.getCmp('tempAdd').show();
						Ext.getCmp('saveAdd').hide();
					}else{
						Ext.getCmp('ifappl1').hide();
					}
				}	
			}
			view.contentPanel.form.loadRecord(getSelectedData());
		}
	}

};



	
var beforeviewshow =function(view){
	
};

