/**
 * @description 全行客户查询
 * @author luyy
 * @since 2014-07-18
 */
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/resource/ext3/ux/Ext.ux.Notification.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.BusiType.js'		//行业树放大镜
	,'/contents/pages/wlj/customerManager/queryAllCustomer/allLookup.js' //所有数据字典定义
	,'/contents/pages/wlj/customerManager/queryAllCustomer/updateHisCust.js'
	,'/contents/pages/wlj/customerManager/queryAllCustomer/fsxPerInfo.js'
	,'/contents/pages/wlj/customerManager/queryAllCustomer/fsxComInfo.js'
	,'/contents/pages/common/Com.yucheng.crm.common.EchainNextNodeUser.js'
]);
Ext.QuickTips.init();

//自定义全局变量
var custId = '';
var custName = '';
var custType = '';
var isSxCust = '';	//是否信贷客户
var _sysCurrDate = new Date().format('Y-m-d');

var roleCond = JsContext.checkGrant('all_query')?'1':'2'; 	//个金RM授权 选择2,否则选择2 ：1、4选1  2、4选3
var updateSxFlag = !JsContext.checkGrant('all_update_sx');	//true,是否允许修改授信      RM允许修改  
var updateFsxFlag = !JsContext.checkGrant('all_update_fsx');	//true,是否允许修改非授信   OP允许修改

//JS框架变量
var needGrid = false;
WLJUTIL.suspendViews = false;
var fields = [{name:'CUST_ID'}];

/**
 * 全行客户查询条件控制区域
 */
////////////////////////////////////////////////////////////////////////////////////////
/**
 * APP初始化之后触发
 * params ： app：当前APP对象；
 */
var afterinit = function(){
	//权限控制具体实现调用类
	//具体参照使用setFormDisabled方法
};
//全行客户查询面板
var searchPanel = new Ext.form.FormPanel({
	labelWidth : 80,
	frame : true,
	autoScroll : true,
	buttonAlign : "center",
	labelAlign : 'right',
	layout : 'form',
	items : [
		{xtype : 'textfield',name : 'CORE_NO',fieldLabel : '核心客户号',anchor : '90%',hidden: (roleCond == 1 ? false : true)},
		{xtype : 'textfield',name : 'CUST_NAME',fieldLabel : '客户名称',anchor : '90%'},
		{xtype : 'combo',name:'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '证件类型',triggerAction : 'all',
			store : indentStore,displayField : 'value',valueField : 'key',mode : 'local',resizable : true,anchor : '90%'},
		{xtype : 'textfield',name : 'IDENT_NO',fieldLabel : '证件号码',anchor : '90%'},
		{xtype : 'textfield',name: 'ACC_NO',fieldLabel:'账号',anchor : '90%'}
	],
	buttons : [{
		text : '查询',
		handler : function() {
			searchPanelResetFn();
			searchPanelFn(searchPanel);
		}
	},{
		text : '重置',
		handler : function() {
			searchPanelResetFn();
			searchPanel.form.reset();
		}
	},{
		text : '返回',
		handler : function() {
			infoPanel.layout.setActiveItem(0);
			fsxPerInfo.setActiveTab(0);
			fsxComInfo.setActiveTab(0);
		}
	}]
});
//全行客户查询store
var allCuststore =  new Ext.data.Store({
	restful:true,
	proxy : new Ext.data.HttpProxy({
		url:basepath+'/customerQueryAllNew.json',
		method:'GET'
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, [
		{name: 'CORE_NO'},
		{name: 'CUST_ID'},
	    {name: 'CUST_NAME'},
	    {name: 'CUST_TYPE_ORA'},
		{name: 'CUST_TYPE'},
		{name: 'IDENT_TYPE_ORA'},
		{name: 'IDENT_TYPE'},
		{name: 'IDENT_NO'},
		{name: 'IS_SX_CUST'},
		{name: 'INSTITUTION_CODE'},
		{name: 'ACT_ORG_IDS'},
		{name: 'MGR_ID'}
	])
});
allCuststore.on('beforeload',function(store){
	//未录入查询条件不允许查询
	if(store.baseParams == undefined || store.baseParams.condition == undefined){
		Ext.Msg.alert('提示','请输入查询条件！');
		return false;
	}
});
/**
 * 重置全行客户查询列表
 */
var searchPanelResetFn = function(){
	custId = '';
	custName = '';
	custType = '';
	allCuststore.removeAll();
	allCuststore.baseParams = {};
	infoPanel.layout.setActiveItem(0);
	fsxPerInfo.setActiveTab(0);
	fsxComInfo.setActiveTab(0);
};
/**
 * 全行客户查询执行fn
 * @param formPanel 查询panel
 * @param roleCond 查询角色 1 3选1,2 4选1
 * @type 
 */
var searchPanelFn = function(formPanel){
	var baseForm = formPanel.getForm();
	if(roleCond == '1'){
		if(baseForm.findField("CUST_NAME").getValue() == '' 
			&& baseForm.findField("CORE_NO").getValue() == ''  
			&& baseForm.findField("ACC_NO").getValue() == ''  
			&& (baseForm.findField("IDENT_TYPE").getValue() == ''   
			|| baseForm.findField("IDENT_NO").getValue() == '')){
			Ext.Msg.alert('提示','核心客户号、客户名称、(证件类型与证件号码)、账号必须录入其一');
			return false;
		}
	}else {
		if(baseForm.findField("IDENT_TYPE").getValue() == '' || baseForm.findField("IDENT_NO").getValue() == '' ){
			Ext.Msg.alert('提示','证件类型、证件号码、(客户名称或账号)三者必须输入');
			return false;
		}
		if(baseForm.findField("CUST_NAME").getValue() == '' && baseForm.findField("ACC_NO").getValue() == '' ){
			Ext.Msg.alert('提示','客户名称、账号必须输入其一');
			return false;
		}
	}
	var conditionStr = formPanel.getForm().getValues(false);
	conditionStr.ROLE_COND = roleCond;
	allCuststore.baseParams = {
		"condition" : Ext.encode(conditionStr)
	};
	allCuststore.load({
	    params : {
            start : 0,
            limit : parseInt(pagingComboAllCust.getValue())
        }
	});
};
/**
 * 是否能修改全行客户信息
 * @param custType 1 对公,2 对私
 * @param belongOrg 所属机构集合
 * @param belongMgr 所属客户经理
 */
var beforeSetAllCustIsUpdate = function(custType,belongOrg,belongMgr){
	Ext.Ajax.request({
    	url : basepath + '/customerQueryAllNew!isLockCust.json',
        method : 'GET',
        params : {custId: custId},
        success : function(response){
        	var ret = Ext.decode(response.responseText);
        	var isLock = false;
        	if(ret.json.data.length>0 && ret.json.data[0].AUTHOR != ''){
        		isLock = true;
        		showMsgNotification('您暂无修改权限，客户记录已被操作员'+ret.json.data[0].AUTHOR+'锁定！',300000);
        	}
        	setAllCustIsUpdate(custType,belongOrg,belongMgr,isLock);
        },
        failure : function(response){
			var isLock = true;
        	setAllCustIsUpdate(custType,belongOrg,belongMgr,isLock);
        }
	});
};
/**
 * 是否能修改全行客户信息
 * @param custType 1 对公,2 对私
 * @param belongOrg 所属机构集合
 * @param belongMgr 所属客户经理
 * @param isLock 是否锁定 true锁定,false未锁定
 */
var setAllCustIsUpdate = function(custType,belongOrg,belongMgr,isLock){
	var tempSxFlag = false;//是否有权限进行修改授信
	var tempFsxFlag = false;//是否有权限进行修改非授信
	
	// RM可修改授信界面,且只能修改归属自身的客户。
	if(updateSxFlag && belongMgr == JsContext._userId){
		tempSxFlag = true;
	}
	// OP可修改非授信界面，且法金客户要判断是否OP所在分支行（根据客户归属分支行或账号分支行）
	if(updateFsxFlag && custType == '2'){
		tempFsxFlag = true;
	}else if(updateFsxFlag && custType == '1' && belongOrg.indexOf(JsContext._orgId)> -1){
		tempFsxFlag = true;
	}else if(updateFsxFlag && custType == '1' && belongOrg.indexOf('500')> -1){
		tempFsxFlag = true;
	}
	
	//true锁定,false未锁定
	if(isLock){
		tempSxFlag = false;
		tempFsxFlag = false;
	}
	
	Ext.getCmp('pub_sx').setVisible(tempSxFlag);//对公授信
	Ext.getCmp('per_sx').setVisible(tempSxFlag);//对私授信
	
	Ext.getCmp('pub_fsx_all').setVisible(tempFsxFlag);//对公非授信all
	Ext.getCmp('pub_fsx_2-11').setVisible(tempFsxFlag);//对公非授信第二屏
	Ext.getCmp('pub_fsx_2-12').setVisible(tempFsxFlag);//对公非授信第二屏
	Ext.getCmp('pub_fsx_2-13').setVisible(tempFsxFlag);//对公非授信第二屏
	Ext.getCmp('pub_fsx_2-21').setVisible(tempFsxFlag);//对公非授信第二屏
	Ext.getCmp('pub_fsx_2-22').setVisible(tempFsxFlag);//对公非授信第二屏
	Ext.getCmp('pub_fsx_2-23').setVisible(tempFsxFlag);//对公非授信第二屏
	Ext.getCmp('pub_fsx_2-31').setVisible(tempFsxFlag);//对公非授信第二屏
	Ext.getCmp('pub_fsx_2-32').setVisible(tempFsxFlag);//对公非授信第二屏
	Ext.getCmp('pub_fsx_2-33').setVisible(tempFsxFlag);//对公非授信第二屏
	
	Ext.getCmp('per_fsx_all').setVisible(tempFsxFlag);//对私非授信all
	Ext.getCmp('per_fsx_2-11').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-12').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-13').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-21').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-22').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-23').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-31').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-32').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-33').setVisible(tempFsxFlag);//对私非授信第二屏
};

/**
 * 点击授信与非授信信息时查询数据
 * @param _custType 1表示企业,2表示个人
 */
var firstQueryFsxFn = function(custType){
	if(custType == '2'){
		//对私非授信第一屏
		searchFsxFn();
		//对私非授信第二屏
		window.queryFsxPerSecFn();
	}else{
		//对公非授信第一屏
		searchFsxComFn();
		//对公非授信第二屏
		window.queryFsxComSecFn();
 		//对公非授信第三屏
		searchFsxComThreeFn();	
	}
};

var allCustTbar = new Ext.Toolbar({
    items: [{
        text: '个人账户信息管理',
        handler: function(){
            var selectLength = allCustGrid.getSelectionModel().getSelections().length;
            var selectRecord = allCustGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            if(selectRecord.data.CORE_NO == '' ){
            	Ext.Msg.alert('提示','该客户无核心客户号,不允许进入非授信界面!');
                return false;
            }
            custId = selectRecord.data.CUST_ID;
			custName = selectRecord.data.CUST_NAME;
			custType = selectRecord.data.CUST_TYPE;
			isSxCust = selectRecord.data.IS_SX_CUST;
			var tempCustOrg = selectRecord.data.INSTITUTION_CODE;//客户归属机构
			var tempActOrg = selectRecord.data.ACT_ORG_IDS;//账号所属机构
			var tempMgr = selectRecord.data.MGR_ID;//所属客户经理
			beforeSetAllCustIsUpdate(custType,tempCustOrg+','+tempActOrg,tempMgr);
            if(custType == '2'){
				//firstQueryFsxFn(custType);
				infoPanel.layout.setActiveItem(1);
				edgeVies.left=false;
			}else{
				firstQueryFsxFn(custType);
				infoPanel.layout.setActiveItem(2);
			}
        }
    }]
});
var allCustRn = new Ext.grid.RowNumberer({
    header:'NO.',
    width:35
});
var allCustCm = new Ext.grid.ColumnModel([
    allCustRn,
    {dataIndex:'CUST_ID',header:'客户号',width : 120,sortable : true},
    {dataIndex:'CUST_NAME',header:'客户名称',width : 150,sortable : true},
    {dataIndex:'IDENT_TYPE_ORA',header:'证件类型',width : 120,sortable : true},
    {dataIndex:'IDENT_NO',header:'证件号码',width : 120,sortable : true},
    {dataIndex:'CUST_TYPE_ORA',header:'客户类型',width : 100,sortable : true},
    {dataIndex:'CORE_NO',header:'核心客户号',width : 120,sortable : true},
    {dataIndex:'MGR_ID',header:'归属客户经理',width : 120,hidden : true},
    {dataIndex:'INSTITUTION_CODE',header:'归属机构',width : 120,hidden : true},
    {dataIndex:'ACT_ORG_IDS',header:'账号所属机构集合',width : 120,hidden : true},
    {dataIndex:'IS_SX_CUST',header:'是否信贷客户',width: 100,hidden:true}
]);
var pagingComboAllCust =  new Ext.form.ComboBox({
    name : 'pagesize',
    triggerAction : 'all',
    mode : 'local',
    store : new Ext.data.ArrayStore({
        fields : ['value', 'text'],
        data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 250, '250条/页' ],
					[ 500, '500条/页' ] ]
    }),
    valueField : 'value',
    displayField : 'text',
    value: 20,
    editable : false,
    width : 85
});
var pagingbarAllCust = new Ext.PagingToolbar({
	pageSize : parseInt(pagingComboAllCust.getValue()),
	store : allCuststore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',       
	emptyMsg : "没有符合条件的记录",
	items : ['-', '&nbsp;&nbsp;', pagingComboAllCust]
});
pagingComboAllCust.on("select", function(comboBox) {
	pagingbarAllCust.pageSize = parseInt(comboBox.getValue()),
    allCuststore.reload({
        params : {
            start : 0,
            limit : parseInt(comboBox.getValue())
        }
    });
});
var allCustGrid = new Ext.grid.GridPanel({
	title: '个人客户查询',
	frame: true,
    autoScroll: true,
    stripeRows: true,
    store: allCuststore,
    cm : allCustCm,
    tbar: allCustTbar,
    bbar: pagingbarAllCust,
    viewConfig:{},
    loadMask: {
        msg: '正在加载表格数据,请稍等...'
    }
});


/**
 * 零售非授信维护界面
 */
////////////////////////////////////////////////////////////////////////////////////////
/**
 * 非授信查询
 */
var fsxStore = new Ext.data.Store({
	restful:true,
	proxy : new Ext.data.HttpProxy({
		url:basepath+'/dealWithFsx!queryPerfsx.json',
		method:'get'
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, [
		'CUST_ID','CUST_NAME','CUST_TYPE','UNIT_FEX',
		'IDENT_ID','IDENT_TYPE','IDENT_NO','IDENT_EXPIRED_DATE',
		'IDENT_ID1','IDENT_TYPE1','IDENT_NO1','IDENT_EXPIRED_DATE1',
		
		'STAFFIN','JOINT_CUST_TYPE','SWIFT','REMARK',
		'RECOMMENDER','RECOMMENDER_NAME','ORG_SUB_TYPE','EN_NAME','INOUT_FLAG','AR_CUST_FLAG','RISK_NATION_CODE','PER_CUST_TYPE','GENDER','BIRTHDAY','CITIZENSHIP','AREA_CODE','USA_TAX_IDEN_NO','BIRTHLOCALE','EMAIL','VIP_FLAG',
		'KEYFLAG_CUST_ID','IS_SEND_ECOMSTAT_FLAG','USA_TAX_FLAG','IS_FAX_TRANS_CUST',
		'BASIC_ACCT_BANK_NO','BASIC_ACCT_BANK_NAME','BASIC_ACCT_OPEN_DATE',
		'MGR_KEY_ID','MGR_ID','MGR_NAME','EFFECT_DATE',
		'VIP_GRADE_ID','VIP_CUST_GRADE','RISK_GRADE_ID','RISK_CUST_GRADE',
		'AGENT_ID','AGENT_NAME','AGENT_IDENT_TYPE','AGENT_IDENT_NO','AGENT_NATION_CODE','AGENT_TEL','AGENT_CUST_ID','IF_SIGN_SERVICE','SERVICE_CUST_ID'
	])
});
/**
 * 查询非授信客户信息
 */
var searchFsxFn = function(){
	if(fsxbaseInfo.getForm().getEl()){
		fsxbaseInfo.getForm().getEl().dom.reset();
	}
	fsxStore.load({
		params : {
			custId : custId
		},
		callback:function(){
			if(fsxStore.getCount()!=0){
				fsxbaseInfo.getForm().loadRecord(fsxStore.getAt(0));
			}
		}
	})
};
  

//基本信息部分  第一屏
var fsxbaseInfo = new Ext.form.FormPanel({
	 frame : true,
	 autoScroll : true,
	 title : '第一屏',
	 labelWidth : 140,
	 buttonAlign : "center",
	 items:[{
		layout : 'column',
	    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '<font color="red">*</font>中文名',name : 'CUST_NAME',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '<font color="red">*</font>证件类型1',store : indent00Store,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
		            {xtype : 'combo',name : 'IDENT_TYPE1',hiddenName : 'IDENT_TYPE1',fieldLabel : '证件类型2',store : indent01Store,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
		            {xtype : 'datefield',name : 'IDENT_EXPIRED_DATE',fieldLabel : '证件1失效日期',format:'Y-m-d',anchor : '90%'},
					{xtype : 'datefield',name : 'BIRTHDAY',fieldLabel : '<font color="red">*</font>个人生日',format:'Y-m-d',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'CITIZENSHIP',hiddenName : 'CITIZENSHIP',fieldLabel : '国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
		            {xtype : 'combo',name : 'USA_TAX_FLAG',hiddenName : 'USA_TAX_FLAG',fieldLabel : '<font color="red">*</font>是否美国纳税人',store : usaTaxStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
		            {xtype : 'combo',name : 'INOUT_FLAG',hiddenName : 'INOUT_FLAG',fieldLabel : '<font color="red">*</font>境内/外标志',store : inOrOutStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'VIP_FLAG',hiddenName : 'VIP_FLAG',fieldLabel : '<font color="red">*</font>VIP标志',store : vipFlagStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'textfield',name : 'BASIC_ACCT_BANK_NAME',fieldLabel : '开户行',anchor : '90%',disabled:true,cls:'x-readOnly'},
					{xtype : 'textfield',name : 'MGR_NAME',fieldLabel : '客户经理',anchor : '90%',disabled:true,cls:'x-readOnly'},
					{xtype : 'combo',name : 'IS_SEND_ECOMSTAT_FLAG',hiddenName : 'IS_SEND_ECOMSTAT_FLAG',fieldLabel : '<font color="red">*</font>综合对账单发送标志',store : isSendEcomstatFlagStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'IS_FAX_TRANS_CUST',hiddenName : 'IS_FAX_TRANS_CUST',fieldLabel : '<font color="red">*</font>是否传真交易指示标志',store : isFaxStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'STAFFIN',hiddenName : 'STAFFIN',fieldLabel : '关联人类型',store : staffinStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '代理人户名',name : 'AGENT_NAME',anchor : '90%',maxLength:40},
					{xtype : 'combo',name : 'AGENT_NATION_CODE',hiddenName : 'AGENT_NATION_CODE',fieldLabel : '代理人国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'combo',name : 'AGENT_IDENT_TYPE',hiddenName : 'AGENT_IDENT_TYPE',fieldLabel : '代理人证件类型',store : indentStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '代理人联系电话',name : 'AGENT_TEL',anchor : '90%',maxLength:20},
					{xtype : 'userchoose',name:'RECOMMENDER_NAME',hiddenName : 'RECOMMENDER',fieldLabel : '推荐人',searchType:'ALLORG',singleSelect:true,anchor : '90%',maxLength:20}
		       ]
	      	},{
	       		columnWidth : .5,
			    layout : 'form',
			    items :[
			    	{xtype : 'combo',name : 'PER_CUST_TYPE',hiddenName : 'PER_CUST_TYPE',fieldLabel : '个人客户类型',store : perTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',hidden:true},
			    	{xtype : 'combo',name : 'GENDER',hiddenName : 'GENDER',fieldLabel : '<font color="red">*</font>性别',store : sexStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
			    	{xtype : 'textfield',name : 'EN_NAME',fieldLabel : '<font color="red">*</font>英文名',anchor : '90%',allowBlank:false,maxLength:100},
			    	{xtype : 'textfield',fieldLabel : '<font color="red">*</font>证件号码1',name : 'IDENT_NO',anchor : '90%',allowBlank:false,maxLength:100},
			    	{xtype : 'textfield',fieldLabel : '证件号码2',name : 'IDENT_NO1',anchor : '90%',maxLength:100},
			    	{xtype : 'datefield',name : 'IDENT_EXPIRED_DATE1',fieldLabel : '证件2失效日期',format:'Y-m-d',anchor : '90%'},
		            {xtype : 'textfield',fieldLabel : '出生地',name : 'BIRTHLOCALE',anchor : '90%',maxLength:25},
		            {xtype : 'combo',name : 'AREA_CODE',hiddenName : 'AREA_CODE',fieldLabel : '地区代码',store : dqStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : 'USTIN',name : 'USA_TAX_IDEN_NO',anchor : '90%'}, 
		            {xtype : 'datefield',name : 'BASIC_ACCT_OPEN_DATE',fieldLabel : '<font color="red">*</font>客户资料开立日',format:'Y-m-d',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'AR_CUST_FLAG',hiddenName : 'AR_CUST_FLAG',fieldLabel : '是否AR客户标志',store : arCustFlagStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'combo',name : 'ORG_SUB_TYPE',hiddenName : 'ORG_SUB_TYPE',fieldLabel : '<font color=red>*</font>特殊监管区',store : orgSubTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'RISK_CUST_GRADE',hiddenName : 'RISK_CUST_GRADE',fieldLabel : '<font color=red></font>反洗钱风险等级',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:true,readOnly:true},
					{xtype : 'datefield',name : 'EFFECT_DATE',fieldLabel : '客户经理修改生效日',format:'Y-m-d',anchor : '90%',cls:'x-readOnly',disabled:true,allowBank:false,hidden:true},
					{xtype : 'textfield',fieldLabel : '电子邮件地址',name : 'EMAIL',vtype:'email',anchor : '90%',maxLength:40},
			        {xtype : 'textfield',fieldLabel : '传真号码',name : 'UNIT_FEX',anchor : '90%',maxLength:20},
			        {xtype : 'combo',name : 'RISK_NATION_CODE',hiddenName : 'RISK_NATION_CODE',fieldLabel : '国别风险国别代码',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : 'Swift Address',name : 'SWIFT',anchor : '90%',maxLength:20},
					{xtype : 'combo',name : 'JOINT_CUST_TYPE',hiddenName : 'JOINT_CUST_TYPE',fieldLabel : '联名户',store : jointCustTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '代理人证件号码',name : 'AGENT_IDENT_NO',anchor : '90%',maxLength:40},
					{xtype : 'combo',name : 'CUSTNM_IDENT_MODIFIED_FLAG',hiddenName : 'CUSTNM_IDENT_MODIFIED_FLAG',fieldLabel : 'CUSTNAME OR ID/REFNO CHANGE BY',store : custnmIdentModifiedStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'combo',name : 'IF_SIGN_SERVICE',hiddenName : 'IF_SIGN_SERVICE',fieldLabel : '已补签个人开户及综合服务协议书',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '已补签个人开户及综合服务协议书客户编号',name : 'SERVICE_CUST_ID',anchor : '90%',maxLength:40,hidden:true}
			]
		}]
	},{
		layout: 'form',
		items:[
			{xtype : 'textarea',fieldLabel : '备注',name : 'REMARK',maxLength: 100,anchor : '95%'},
		    {xtype : 'textfield',fieldLabel : '开户行',name : 'BASIC_ACCT_BANK_NO',hidden:true},
			{xtype : 'textfield',fieldLabel : '证件类型1ID',name : 'IDENT_ID',hidden:true},
			{xtype : 'textfield',fieldLabel : '证件类型2ID',name : 'IDENT_ID1',hidden:true},
			{xtype : 'textfield',fieldLabel : '归属客户经理表ID',name : 'MGR_KEY_ID',hidden:true},
			{xtype : 'textfield',fieldLabel : '重要标志ID',name : 'KEYFLAG_CUST_ID',hidden:true},
			{xtype : 'textfield',fieldLabel : '风险等级ID',name : 'RISK_GRADE_ID',hidden:true},
			{xtype : 'textfield',fieldLabel : '贵宾卡等级ID',name : 'VIP_GRADE_ID',hidden:true},
			{xtype : 'textfield',fieldLabel : '代理人ID',name : 'AGENT_ID',hidden:true}
		]
	}],
	buttons:[{
		text:'修改历史',
		handler:function(){
			updateHisWin.show();
		}
	},{
		 text: '下一屏',
		 handler: function(){
			fsxPerInfo.setActiveTab(1);
		 }
	}]
});
/**
 * 对私非授信第一屏校验
 * @return {Boolean}
 */
var validFsxPerFn = function(){
	var tempJson1 = fsxStore.getAt(0).json;
	var tempJson2 = fsxbaseInfo.getForm().getValues(false);
	//1． 综合对账单发送标志为“是”，则电子邮件地址为必输项
	if(tempJson2.IS_SEND_ECOMSTAT_FLAG == 'Y' && tempJson2.EMAIL == ''){
		Ext.Msg.alert('提示','非授信第一屏校验失败，综合对账单发送标志为“是”，则电子邮件地址为必输项！');
		return false;
	}
	//2． 是否为美国纳税人为“是”，则USTN为必输项
	if(tempJson2.USA_TAX_FLAG == 'Y' && tempJson2.USA_TAX_IDEN_NO == ''){
		Ext.Msg.alert('提示','非授信第一屏校验失败，是否为美国纳税人为“是”，则USTN为必输项！');
		return false;
	}
	//3． 客户证件或名称若有修改，则“CUSTNAME OR ID/REFNO CHANGE BY”为必选项
	if(tempJson2.CUSTNM_IDENT_MODIFIED_FLAG == ''
	    && (tempJson1.CUST_NAME != tempJson2.CUST_NAME
	    || tempJson1.IDENT_TYPE != tempJson2.IDENT_TYPE
		|| tempJson1.IDENT_NO != tempJson2.IDENT_NO
		|| tempJson1.IDENT_EXPIRED_DATE != tempJson2.IDENT_EXPIRED_DATE
		|| tempJson1.IDENT_TYPE1 != tempJson2.IDENT_TYPE1
		|| tempJson1.IDENT_NO1 != tempJson2.IDENT_NO1
		|| tempJson1.IDENT_EXPIRED_DATE1 != tempJson2.IDENT_EXPIRED_DATE1)){
		Ext.Msg.alert('提示','非授信第一屏校验失败，客户名称或证件修改，则“CUSTNAME OR ID/REFNO CHANGE BY”为必选项！');
		return false;
	}
	//4.必须保证证件2输入值后，证件类型和证件号码都存在
	if((tempJson2.IDENT_TYPE1 == '' || tempJson2.IDENT_NO1 == '')
		&& (tempJson2.IDENT_TYPE1 != '' || tempJson2.IDENT_NO1 != '' || tempJson2.IDENT_EXPIRED_DATE1 != '')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件2类型、号码、失效日期录入值后，必须保证证件类型2与证件号码2同时有值！');
		return false;
	}
	//5.证件1失效日期先前无值,录入值后必须校验大于经办日
	if(tempJson1.IDENT_EXPIRED_DATE == '' && tempJson2.IDENT_EXPIRED_DATE != '' && tempJson2.IDENT_EXPIRED_DATE <= _sysCurrDate){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1失效日期必须大于经办日期！');
		return false;
	}
	//6.证件2失效日期先前无值,录入值后必须校验大于经办日
	if(tempJson1.IDENT_EXPIRED_DATE1 == '' && tempJson2.IDENT_EXPIRED_DATE1 != '' && tempJson2.IDENT_EXPIRED_DATE1 <= _sysCurrDate){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件2失效日期必须大于经办日期！');
		return false;
	}
	//2.1个人标志：个人客户证件1类型不能为“境内组织机构代码”、“境外登记证件代码(赋码)”
	if(tempJson2.IDENT_TYPE == '20' || tempJson2.IDENT_TYPE == '2X'){
		Ext.Msg.alert('提示','非授信第一屏校验失败，个人客户证件1类型不能为“境内组织机构代码”、“境外登记证件代码(赋码)”！');
		return false;
	}
	//2.2、证件2类型：证件1类型为“台湾居民身份证”时，证件2类型只能为“台湾同胞来往内地通行证”、“临时台胞证”
	if(tempJson2.IDENT_TYPE == 'X2' && (tempJson2.IDENT_TYPE1 != '6' && tempJson2.IDENT_TYPE1 != 'X24')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“台湾居民身份证”时，证件2类型只能为“台湾同胞来往内地通行证”、“临时台胞证”！');
		return false;
	}
	//2.3、证件2类型：证件1类型为“港澳居民身份证”时，证件2类型只能为“港澳居民来往内地通行证”、“旅行证件”
	if(tempJson2.IDENT_TYPE == 'X1' && (tempJson2.IDENT_TYPE1 != '5' && tempJson2.IDENT_TYPE1 != 'X3')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“港澳居民身份证”时，证件2类型只能为“港澳居民来往内地通行证”、“旅行证件”！');
		return false;
	}
	//2.4、证件2类型：证件2类型为“台湾同胞来往内地通行证”、“临时台胞证”、“港澳居民来往内地通行证”、“旅行证件”时，证件2失效日期必须输入
	if((tempJson2.IDENT_TYPE1 == '6' || tempJson2.IDENT_TYPE1 == 'X24' || tempJson2.IDENT_TYPE1 == '5' || tempJson2.IDENT_TYPE1 == 'X3')
		&& tempJson2.IDENT_EXPIRED_DATE1 == ''){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件2类型为“台湾同胞来往内地通行证”、“临时台胞证”、“港澳居民来往内地通行证”、“旅行证件”时，证件2失效日期必须输入！');
		return false;
	}
	//2.5、证件1类型：证件1类型为“台湾同胞来往内地通行证”、“临时台胞证”、“港澳居民来往内地通行证”、“旅行证件”时，证件2失效日期必须输入
	if((tempJson2.IDENT_TYPE != 'X2' && tempJson2.IDENT_TYPE != 'X1') && tempJson2.IDENT_EXPIRED_DATE == ''){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型不为“台湾居民身份证”、“港澳居民身份证”时，证件1失效日期必须输入！');
		return false;
	}
	//7.1境内/外标志--：证件1类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，境内/外标志只能选择境内
	if((tempJson2.IDENT_TYPE == '20' || tempJson2.IDENT_TYPE == '0' || tempJson2.IDENT_TYPE == 'X4') && tempJson2.INOUT_FLAG != 'D'){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，境内/外标志只能选择境内！');
		return false;
	}
	//7.2境内/外标志--:证件1类型为“境外登记证件代码(赋码)”、“台湾居民身份证”、“港澳居民身份证”、“境外居民护照”、“旅行证件”时，境内/外标志只能选择境外
	if((tempJson2.IDENT_TYPE == '2X' || tempJson2.IDENT_TYPE == 'X2' || tempJson2.IDENT_TYPE == 'X1'
		|| tempJson2.IDENT_TYPE == '2' || tempJson2.IDENT_TYPE == 'X3') && tempJson2.INOUT_FLAG != 'F'){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“境外登记证件代码(赋码)”、“台湾居民身份证”、“港澳居民身份证”、“境外居民护照”、“旅行证件”时，境内/外标志只能选择境外！');
		return false;
	}
	//9.1所在国别：境内/外标志为“境内”时，所在国别只能选择“中国”且地区代码不能选择“台湾”、“香港”、“澳门”
	if(tempJson2.INOUT_FLAG == 'D' && (tempJson2.CITIZENSHIP != 'CHN' 
		|| tempJson2.AREA_CODE == '710000'
		|| tempJson2.AREA_CODE == '810000'
		|| tempJson2.AREA_CODE == '820000')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，境内/外标志为“境内”时，所在国别只能选择“中国”且地区代码不能选择“台湾”、“香港”、“澳门”！');
		return false;
	}
	//9.2所在国别：境内/外标志为“境外”且所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”
	if(tempJson2.INOUT_FLAG == 'F' && tempJson2.CITIZENSHIP == 'CHN'
		&& (tempJson2.AREA_CODE != '710000'
		&& tempJson2.AREA_CODE != '810000'
		&& tempJson2.AREA_CODE != '820000')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，境内/外标志为“境外”且所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”！');
		return false;
	}
	//9.3所在国别：证件1类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，所在国别必须为“中国”且地区代码不能选择“台湾”、“香港”、“澳门”
	if((tempJson2.IDENT_TYPE == '20' || tempJson2.IDENT_TYPE == '0' || tempJson2.IDENT_TYPE == 'X4')
		&& (tempJson2.CITIZENSHIP != 'CHN'
		 || (tempJson2.CITIZENSHIP == 'CHN' && (tempJson2.AREA_CODE == '710000'
			|| tempJson2.AREA_CODE == '810000'
			|| tempJson2.AREA_CODE == '820000')))){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，所在国别必须为“中国”且地区代码不能选择“台湾”、“香港”、“澳门”！');
		return false;
	}
	//10.1地区代码: 证件1类型为“境外登记证件代码(赋码)”、“境外居民护照”、“旅行证件”且所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”
	if((tempJson2.IDENT_TYPE == '2X' || tempJson2.IDENT_TYPE == '2' || tempJson2.IDENT_TYPE == 'X3')
		&& tempJson2.CITIZENSHIP == 'CHN' 
		&& (tempJson2.AREA_CODE != '710000' && tempJson2.AREA_CODE != '810000' && tempJson2.AREA_CODE != '820000')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“境外登记证件代码(赋码)”、“境外居民护照”、“旅行证件”且所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”！');
		return false;
	}
	//10.2地区代码: 证件1类型为“台湾居民身份证”且所在国别为“中国”时，地区代码只能选择“台湾”
	if((tempJson2.IDENT_TYPE == 'X2' || tempJson2.IDENT_TYPE == '2') && tempJson2.CITIZENSHIP == 'CHN' && tempJson2.AREA_CODE != '710000'){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“台湾居民身份证”或“境外居民护照”，且所在国别为“中国”时，地区代码只能选择“台湾”！');
		return false;
	}
	//10.3地区代码: 证件1类型为“港澳居民身份证”且所在国别为“中国”时，地区代码只能选择“香港”、“澳门”
	if(tempJson2.IDENT_TYPE == 'X1' && tempJson2.CITIZENSHIP == 'CHN'
		&& (tempJson2.AREA_CODE != '810000' && tempJson2.AREA_CODE != '820000')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“港澳居民身份证”且所在国别为“中国”时，地区代码只能选择“香港”、“澳门”！');
		return false;
	}
	
	//警告提示,不影响程序继续执行
	//证件1失效日期先前有值,且修改后值还是小于经办日,作警告提示
	if(tempJson1.IDENT_EXPIRED_DATE != '' && tempJson2.IDENT_EXPIRED_DATE != '' && tempJson2.IDENT_EXPIRED_DATE <= _sysCurrDate){
		showMsgNotification('注意：非授信第一屏证件1失效日期已小于或等于当前经办日期');
	}
	//证件2失效日期先前有值,且修改后值还是小于经办日,作警告提示
	if(tempJson1.IDENT_EXPIRED_DATE1 != '' && tempJson2.IDENT_EXPIRED_DATE1 != '' && tempJson2.IDENT_EXPIRED_DATE1 <= _sysCurrDate){
		showMsgNotification('注意：非授信第一屏证件2失效日期已小于或等于当前经办日期');
	}
	return true;
};
/**
 * 获取非授信第一屏变更历史
 * @return firstPerModel
 */
var getFsxFirstPerModel = function(){
 	var identArr0 = ['IDENT_TYPE', 'IDENT_NO', 'IDENT_EXPIRED_DATE', 'IDENT_ID'];
 	var identArr1 = ['IDENT_TYPE1', 'IDENT_NO1', 'IDENT_EXPIRED_DATE1', 'IDENT_ID1'];
 	var agentArr = ['AGENT_ID','AGENT_NAME','AGENT_IDENT_TYPE','AGENT_IDENT_NO','AGENT_NATION_CODE','AGENT_TEL','AGENT_CUST_ID'];
	var keyflagArr = ['KEYFLAG_CUST_ID','IS_SEND_ECOMSTAT_FLAG','USA_TAX_FLAG','IS_FAX_TRANS_CUST'];
	var serviceflagArr=['SERVICE_CUST_ID','IF_SIGN_SERVICE'];

	var identFlag0 = false;
 	var identFlag1 = false;
 	var agentFlag = false;
	var keyflag = false;
	var serviceflag=false;
	
    var json1 = fsxStore.getAt(0).json;
	var json2 = fsxbaseInfo.getForm().getValues(false);
	var tempCustId = custId;
	var perModel = [];
	for(var key in json2){
		var pcbhModel = {};
		var field = fsxbaseInfo.getForm().findField(key);
		
		//排除放大镜,放大镜必须单独处理
		if(key == 'RECOMMENDER'){
			continue;//放大镜隐藏字段不处理
		}else if(key == 'RECOMMENDER_NAME'){
			var tempkey = field.hiddenName?field.hiddenName:key;
			var tempField = tempkey == key ? field:fsxbaseInfo.getForm().findField(tempkey);
			if(!((json1[tempkey]==tempField.getValue()) || (null==json1[tempkey]&&null==tempField.getValue()))){
				var pcbhModel = {};
				pcbhModel.custId = tempCustId;
				pcbhModel.updateBeCont = json1[key];
				pcbhModel.updateAfCont = tempField.getValue();
				pcbhModel.updateAfContView = field.getValue();
				pcbhModel.updateItem = field.fieldLabel;
				pcbhModel.updateItemEn = field.hiddenName;
				pcbhModel.fieldType = '1';
				perModel.push(pcbhModel);
			}
			continue;
		}
		if(field.getXType() == 'combo'){
			var s = field.getValue();
			if(json1[key] != s){
				if(json1[key]!=null && s!=""){
					pcbhModel.custId = tempCustId;
					pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json1[key],'value');
					pcbhModel.updateAfCont = s;
					pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
					pcbhModel.updateItem = field.fieldLabel;
					pcbhModel.updateItemEn = field.name;
					pcbhModel.fieldType = '1';
					perModel.push(pcbhModel);
					//记录用于判断证件类型1与证件类型2是否修改
					if(identArr0.indexOf(key) > - 1){
						identFlag0 = true;
					}
					if(identArr1.indexOf(key) > - 1){
						identFlag1 = true;
					}
					if(agentArr.indexOf(key) > - 1){
						agentFlag = true;
					}
					if(keyflagArr.indexOf(key) > - 1){
						keyflag = true;
					}
					if(serviceflagArr.indexOf(key) > - 1){
						serviceflag = true;
					}
					//添加贵宾卡等级及反洗钱等级
					if(key == 'VIP_CUST_GRADE'){
						addFieldFn(perModel,tempCustId,'ID_SEQUENCE.NEXTVAL'
							,fsxbaseInfo.getForm().findField('VIP_GRADE_ID').getValue(),'VIP_GRADE_ID','1','1');
						addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'VIP_CUST_ID');
						addFieldFn(perModel,tempCustId,'04','04','CUST_GRADE_TYPE');
						addFieldFn(perModel,tempCustId,'',JsContext._userId,'VIP_LAST_UPDATE_USER','');
						addFieldFn(perModel,tempCustId,'',_sysCurrDate,'VIP_LAST_UPDATE_TM','','2');
					}
					if(key == 'RISK_CUST_GRADE'){
						addFieldFn(perModel,tempCustId,'ID_SEQUENCE.NEXTVAL'
							,fsxbaseInfo.getForm().findField('RISK_GRADE_ID').getValue(),'RISK_GRADE_ID','1','1');
						addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'RISK_CUST_ID');
						addFieldFn(perModel,tempCustId,'01','01','CUST_GRADE_TYPE1');
						addFieldFn(perModel,tempCustId,'',JsContext._userId,'RISK_LAST_UPDATE_USER','');
						addFieldFn(perModel,tempCustId,'',_sysCurrDate,'RISK_LAST_UPDATE_TM','','2');
					}
				}
			}
		}else{
			if(!((json1[key]==json2[key]) || (null==json1[key]&&null==json2[key]))){
				var pcbhModel = {};
				pcbhModel.custId = tempCustId;
				pcbhModel.updateBeCont = json1[key];
				pcbhModel.updateAfCont = json2[key];
				pcbhModel.updateAfContView = json2[key];
				pcbhModel.updateItem = field.fieldLabel;
				pcbhModel.updateItemEn = field.name;
				pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
				perModel.push(pcbhModel);
				//记录用于判断证件类型1与证件类型2是否修改
				if(identArr0.indexOf(key) > - 1){
					identFlag0 = true;
				}
				if(identArr1.indexOf(key) > - 1){
					identFlag1 = true;
				}
				if(agentArr.indexOf(key) > - 1){
					agentFlag = true;
				}
				if(keyflagArr.indexOf(key) > - 1){
					keyflag = true;
				}
				if(serviceflagArr.indexOf(key) > - 1){
					serviceflag = true;
				}
				//添加主管客户经理变更主键
				if(key == 'MGR_ID'){
					addKeyFn(perModel,tempCustId,fsxbaseInfo,'MGR_KEY_ID','主管客户经理ID');
					addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'MGR_CUST_ID');
				}
			}
		}
	}
	if(identFlag0){
		addKeyFn(perModel,tempCustId,fsxbaseInfo,'IDENT_ID','证件1ID');
		addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'IDENT_CUST_ID');
		addFieldFn(perModel,tempCustId,custName,fsxbaseInfo.getForm().findField('CUST_NAME').getValue(),'IDENT_CUST_NAME');
		//级联更新证件号码1与证件类型1
		addFieldFn(perModel,tempCustId,'',fsxbaseInfo.getForm().findField('IDENT_TYPE').getValue(),'IDENT_IDENT_TYPE');
		addFieldFn(perModel,tempCustId,'',fsxbaseInfo.getForm().findField('IDENT_NO').getValue(),'IDENT_IDENT_NO');
		addFieldFn(perModel,tempCustId,'','1','OPEN_ACC_IDENT_MODIFIED_FLAG');
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_MODIFIED_TIME','','2');
		addFieldFn(perModel,tempCustId,'','CRM','IDENT_LAST_UPDATE_SYS','');
		addFieldFn(perModel,tempCustId,'',JsContext._userId,'IDENT_LAST_UPDATE_USER','');
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_LAST_UPDATE_TM','','2');
	}
	if(identFlag1){
		addKeyFn(perModel,tempCustId,fsxbaseInfo,'IDENT_ID1','证件2ID');
		addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'IDENT_CUST_ID1');
		addFieldFn(perModel,tempCustId,custName,fsxbaseInfo.getForm().findField('CUST_NAME').getValue(),'IDENT_CUST_NAME1');
		//级联更新证件号码1与证件类型1
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_MODIFIED_TIME1','','2');
		addFieldFn(perModel,tempCustId,'','CRM','IDENT_LAST_UPDATE_SYS1','');
		addFieldFn(perModel,tempCustId,'',JsContext._userId,'IDENT_LAST_UPDATE_USER1','');
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_LAST_UPDATE_TM1','','2');
	}
	if(agentFlag){
		addFieldFn(perModel,tempCustId,'ID_SEQUENCE.NEXTVAL'
			,fsxbaseInfo.getForm().findField('AGENT_ID').getValue(),'AGENT_ID','1','1');
		addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'AGENT_CUST_ID');
	}
	if(keyflag){
		addFieldFn(perModel,tempCustId,tempCustId
			,fsxbaseInfo.getForm().findField('KEYFLAG_CUST_ID').getValue(),'KEYFLAG_CUST_ID','1','1');
	}
	if(serviceflag){
		addFieldFn(perModel,tempCustId,tempCustId
			,fsxbaseInfo.getForm().findField('SERVICE_CUST_ID').getValue(),'SERVICE_CUST_ID','1','1');
	}
	return perModel;
};

var fsxperLists = new Ext.Panel({
	frame : true,
	title : '第二屏',
	autoScroll : true,
	buttonAlign : "center",
	items : [{
		xtype : 'fieldset',
		title : '地址信息',
		items : [addrGridPanel]
	},{
		xtype : 'fieldset',
		title : '联系人信息',
		items : [contactPersonGrid]
	},{
		xtype : 'fieldset',
		title : '联系信息',
		items : [contactGridPanel]
	}],
	buttons:[{
		text:'修改历史',
		handler:function(){
			updateHisWin.show();
		}
	},{
		text:'上一屏',
		handler:function(){
			fsxPerInfo.setActiveTab(0);
		}
	},{
		id : 'per_fsx_all',
		text:'提交',
		hidden:true,
		handler:function(){
			Ext.Msg.wait('正在提交申请中...','提示');
			if (!fsxbaseInfo.getForm().isValid()) {
				Ext.Msg.alert('提示','校验失败，非授信第一屏输入有误或存在漏输入项,请检查输入项');
		        return false;
		    }
		    if(!validFsxPerFn()){
		    	return false;
		    }
		    
			var fsxFirst = getFsxFirstPerModel();
			var fsxSec_1 = window.getFsxSecPerModel_1();
			var fsxSec_2 = window.getFsxSecPerModel_2();
			var fsxSec_3 = window.getFsxSecPerModel_3();
			if(fsxFirst.length <1 && fsxSec_1.length < 1 && fsxSec_2.length < 1 && fsxSec_3.length < 1){
				Ext.Msg.alert('提示', '信息未作任何修改,不允许提交!');
				return false;
			}
			Ext.Ajax.request({
		    	url : basepath + '/dealWithFsx!commitFsxPerAll.json',
		        method : 'POST',
		        params : {
			    	'fsxFirst': Ext.encode(getFsxFirstPerModel()),
			    	'fsxSec_1': Ext.encode(window.getFsxSecPerModel_1()),
			    	'fsxSec_2': Ext.encode(window.getFsxSecPerModel_2()),
			    	'fsxSec_3': Ext.encode(window.getFsxSecPerModel_3()),
			    	'custId': custId,
			    	'custName': custName
				},
		        success : function(response) {
		         	var ret = Ext.decode(response.responseText);
					var instanceid = ret[0].instanceid;//流程实例ID
					var currNode = ret[0].currNode;//当前节点
					var nextNode = ret[0].nextNode;//下一步节点
					window.selectUserListNew(ret,instanceid,currNode,nextNode,function(){
						infoPanel.layout.setActiveItem(0);
						fsxPerInfo.setActiveTab(0);
						fsxComInfo.setActiveTab(0);
					});//选择下一步办理人
		        },
		        failure : function(response) {
		            Ext.Msg.alert('提示', '操作失败!');
		        }
		   });
		}
	}]
});

var fsxPerInfo = new Ext.TabPanel({
	activeItem : 0,
	items:[fsxbaseInfo,fsxperLists]
});


var perAccountBaseInfo=new Ext.Panel({
	 width: 2000,
     height: 1000,
	 layout : 'border',
	 defaults: {
        split: true,                 //是否有分割线
        collapsible: false,         //是否可以折叠
        bodyStyle: 'padding:15px'
     },
	 items: [ {
	 	width: 1000,
	 	layout:'fit',
        region: 'west',
        xtype: "panel",
        items:[fsxbaseInfo]
 
    },{
    	layout:'fit',
        region: 'center',
        title: '',
        xtype: "panel",
         items:[fsxperLists]
    }]
});

var perAccountInfo=  new Ext.Panel({
	 layout : 'accordion',
	 items: [{
        tools: [{ type: 'gear', handler: function () {
            		Ext.Msg.alert('提示', '配置按钮被点击。');
        			}
       			}, { type: 'refresh'}],
        title: '',
        xtype: "panel",
        items: [perAccountBaseInfo]
 
    }, {
        title: '',
        xtype: "panel",
        html: "子元素2"
    }]
});
/////////////////////////////////////////////////////////////////////////////////////////////////////


/**
 * 零售授信查询
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
var sxPerStore = new Ext.data.Store({
	restful:true,
	proxy : new Ext.data.HttpProxy({
		url:basepath+'/dealWithFsx!queryPersx.json',
		method:'get'
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, [
		'EN_NAME','INOUT_FLAG','VIP_FLAG',
		'CUST_ID','PER_CUST_TYPE','PERSONAL_NAME','GENDER','CITIZENSHIP','LOAN_CARD_NO','BANK_DUTY','HOLD_STOCK_AMT',
		'IDENT_ID','IDENT_TYPE','IDENT_NO','IDENT_EXPIRED_DATE','IDENT_ID1','IDENT_TYPE1','IDENT_NO1','IDENT_EXPIRED_DATE1',
	
		'NATIONALITY','NATIVEPLACE','HUKOU_PLACE','BIRTHDAY','POLITICAL_FACE','HIGHEST_SCHOOLING','HIGHEST_DEGREE','HEALTH',
		
		'MATEINFO_KEY_ID','PO_MARRIAGE','PO_IDENT_TYPE','PO_CUST_ID_MATE','PO_WORK_UNIT','PO_JOB_TITLE','PO_OFFICE_TEL','PO_WORK_START_DATE',
		'PO_MARR_CERT_NO','PO_IDENT_NO','PO_CAREER','PO_DUTY','PO_MOBILE','PO_ANNUAL_INCOME',
		
		'HOLD_ACCT','CUS_CORP_REL','FIRST_LOAN_DATE',
		'KEYFLAG_CUST_ID','FOREIGN_PASSPORT_FLAG',
		'CUST_GRADE_ID','CUST_GRADE','EVALUATE_DATE',
		
		'POST_ADDR','POST_ZIPCODE','HOME_ADDR','RESIDENCE','HOME_ZIPCODE','HOME_TEL','MOBILE_PHONE','EMAIL',
		
		'CAREER_TYPE','UNIT_NAME','UNIT_CHAR','PROFESSION','PROFESSION_NAME','UNIT_ADDR','UNIT_ZIPCODE','UNIT_TEL','UNIT_FEX','CNT_NAME','CAREER_START_DATE','DUTY','CAREER_TITLE','SALARY_ACCT_BANK','SALARY_ACCT_NO','RESUME',
		
		'BELONG_ORG','BELONG_ORG_NAME','ORG_KEY_ID',
		'MGR_ID','MGR_NAME','MGR_KEY_ID'
	])
});
/**
 * 查询授信客户信息
 */
var searchSxPerFn = function(){
	if(sxPerInfo.getForm().getEl()){
		sxPerInfo.getForm().getEl().dom.reset();
	}
	sxPerStore.load({
		params : {
			custId : custId
		},
		callback:function(){
			if(sxPerStore.getCount()!=0){
				sxPerInfo.getForm().loadRecord(sxPerStore.getAt(0));
			}
		}
	})
};
var sxPerInfo = new Ext.form.FormPanel({
	 frame : true,
	 autoScroll : true,
	 title : '对私授信',
	 labelWidth : 160,
	 buttonAlign : "center",
	 items:[{
	 	xtype : 'fieldset',
	 	title : '在我行基本信息',
	 	items : [{
			layout : 'column',
		    items:[{
		        	columnWidth : .5,
					layout : 'form',
					items :[
						{xtype: 'textfield',name : 'CUST_ID', fieldLabel : '<font color="red">*</font>客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '<font color="red">*</font>姓名',name : 'PERSONAL_NAME',anchor : '90%',allowBlank:false,maxLength:50},
						{xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '<font color="red">*</font>证件类型1',store : indent00Store,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
			            {xtype : 'combo',name : 'IDENT_TYPE1',hiddenName : 'IDENT_TYPE1',fieldLabel : '证件类型2',store : indent01Store,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
			            {xtype : 'datefield',name : 'IDENT_EXPIRED_DATE',fieldLabel : '<font color="red">*</font>证件1失效日期',format:'Y-m-d',anchor : '90%',allowBlank:false},
			            {xtype : 'combo',name : 'INOUT_FLAG',hiddenName : 'INOUT_FLAG',fieldLabel : '<font color="red">*</font>境内/外标志',store : inOrOutStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
			            {xtype : 'combo',name : 'CITIZENSHIP',hiddenName : 'CITIZENSHIP',fieldLabel : '<font color="red">*</font>国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
			            {xtype : 'combo',name : 'VIP_FLAG',hiddenName : 'VIP_FLAG',fieldLabel : '<font color="red">*</font>VIP标志',store : vipFlagStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
						{xtype : 'textfield',fieldLabel : '贷款卡号',name : 'LOAN_CARD_NO',anchor : '90%',maxLength:16}					
			       ]
		      	},{
		       		columnWidth : .5,
				    layout : 'form',
				    items :[
				    	{xtype : 'combo',name : 'PER_CUST_TYPE',hiddenName : 'PER_CUST_TYPE',fieldLabel : '对私客户类型',store : perTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
				    	{xtype : 'textfield',name : 'EN_NAME',fieldLabel : '英文名',anchor : '90%',maxLength:50},
				    	{xtype : 'textfield',fieldLabel : '<font color="red">*</font>证件号码1',name : 'IDENT_NO',anchor : '90%',allowBlank:false,maxLength:50},
				    	{xtype : 'textfield',fieldLabel : '证件号码2',name : 'IDENT_NO1',anchor : '90%',maxLength:50},
				    	{xtype : 'datefield',name : 'IDENT_EXPIRED_DATE1',fieldLabel : '证件2失效日期',format:'Y-m-d',anchor : '90%'},
				    	{xtype : 'combo',name : 'GENDER',hiddenName : 'GENDER',fieldLabel : '<font color="red">*</font>性别',store : sexStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
			           // {xtype : 'combo',name : 'CUS_BANK_REL',hiddenName : 'CUS_BANK_REL',fieldLabel : '<font color="red">*</font>与我行关系',store : cusCorpRelStore,resizable : true,valueField : 'key',displayField : 'value',
						//	mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
			            {xtype : 'combo',name : 'BANK_DUTY',hiddenName : 'BANK_DUTY',fieldLabel : '在我行职务',store : bankDutyStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
						{xtype : 'numberfield',fieldLabel : '拥有我行股份金额(元)',name : 'HOLD_STOCK_AMT',anchor : '90%',maxLength:17},
						{xtype : 'combo',name : 'CUSTNM_IDENT_MODIFIED_FLAG',hiddenName : 'CUSTNM_IDENT_MODIFIED_FLAG',fieldLabel : 'CUSTNAME OR ID/REFNO CHANGE BY',store : custnmIdentModifiedStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
				]
			}]
		}]
	 },{
	 	xtype : 'fieldset',
	 	title : '个人基本信息',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'combo',name : 'NATIONALITY',hiddenName : 'NATIONALITY',fieldLabel : '<font color=red>*</font>民族',store : nationnalityStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'textfield',fieldLabel : '<font color=red>*</font>户籍地址',name : 'HUKOU_PLACE',anchor : '90%',allowBlank:false,maxLength:60},
					{xtype : 'combo',name : 'POLITICAL_FACE',hiddenName : 'POLITICAL_FACE',fieldLabel : '<font color=red>*</font>政治面貌',store : politicalFaceStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'HIGHEST_DEGREE',hiddenName : 'HIGHEST_DEGREE',fieldLabel : '<font color=red>*</font>最高学位',store : highestDegreeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'textfield',fieldLabel : '籍贯 ',name : 'NATIVEPLACE',anchor : '90%',maxLength:100},
					{xtype : 'datefield',name : 'BIRTHDAY',fieldLabel : '<font color=red>*</font>出生日期',format:'Y-m-d',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'HIGHEST_SCHOOLING',hiddenName : 'HIGHEST_SCHOOLING',fieldLabel : '<font color=red>*</font>最高学历',store : highestSchoolingStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'HEALTH',hiddenName : 'HEALTH',fieldLabel : '<font color=red>*</font>健康状况',store : healthStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false}
				]
		    }]
		}]
	},{
	 	xtype : 'fieldset',
	 	title : '个人客户婚姻状况信息',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'combo',name : 'PO_MARRIAGE',hiddenName : 'PO_MARRIAGE',fieldLabel : '<font color=red>*</font>婚姻状况',store : marriageStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'PO_IDENT_TYPE',hiddenName : 'PO_IDENT_TYPE',fieldLabel : '配偶证件类型',store : indentStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '配偶客户号 ',name : 'PO_CUST_ID_MATE',anchor : '90%',maxLength:20},
					{xtype : 'textfield',fieldLabel : '配偶工作单位  ',name : 'PO_WORK_UNIT',anchor : '90%',maxLength:50},
					{xtype : 'combo',name : 'PO_JOB_TITLE',hiddenName : 'PO_JOB_TITLE',fieldLabel : '配偶职称',store : jobTitleStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '配偶单位电话',name : 'PO_OFFICE_TEL',anchor : '90%',maxLength:20},
					{xtype : 'datefield',name : 'PO_WORK_START_DATE',fieldLabel : '配偶参加工作年份 ',format:'Y-m-d',anchor : '90%'}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'textfield',fieldLabel : '结婚证号(户口簿号)',name : 'PO_MARR_CERT_NO',anchor : '90%',maxLength:40},
					{xtype : 'textfield',fieldLabel : '配偶证件号码 ',name : 'PO_IDENT_NO',anchor : '90%',maxLength:40},
					{xtype : 'combo',name : 'PO_CAREER',hiddenName : 'PO_CAREER',fieldLabel : '配偶职业',store : careerTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'combo',name : 'PO_DUTY',hiddenName : 'PO_DUTY',fieldLabel : '配偶职务',store : dutyStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '配偶手机号码',name : 'PO_MOBILE',anchor : '90%',maxLength:20},
					{xtype : 'numberfield',fieldLabel : '配偶年收入(元)',name : 'PO_ANNUAL_INCOME',anchor : '90%',maxLength:17}
				]
		    }]
		}]
	},{
	 	xtype : 'fieldset',
	 	title : '与我行合作关系',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					//{xtype : 'combo',name : 'CUS_CORP_REL',hiddenName : 'CUS_CORP_REL',fieldLabel : '<font color=red>*</font>与我行合作关系',store : cusCorpRelStore,resizable : true,valueField : 'key',displayField : 'value',
					//	mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'HOLD_ACCT',hiddenName : 'HOLD_ACCT',fieldLabel : '在我行开立账户情况',store : holdAcctStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'combo',name : 'CUST_GRADE',hiddenName : 'CUST_GRADE',fieldLabel : '信用等级',store : custGradeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'datefield',name : 'EVALUATE_DATE',fieldLabel : '信用评级日期   ',format:'Y-m-d',anchor : '90%'}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'datefield',name : 'FIRST_LOAN_DATE',fieldLabel : '建立信贷关系时间   ',format:'Y-m-d',anchor : '90%'},
					{xtype : 'combo',name : 'FOREIGN_PASSPORT_FLAG',hiddenName : 'FOREIGN_PASSPORT_FLAG',fieldLabel : '是否拥有外国护照或居住权',store : foreignPassportStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}					
				]
		    }]
		}]
	},{
	 	xtype : 'fieldset',
	 	title : '个人客户联系信息',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'textfield',fieldLabel : '<font color=red>*</font>通讯地址',name : 'POST_ADDR',anchor : '90%',allowBlank: false,maxLength:100},
					{xtype : 'textfield',fieldLabel : '居住地址 ',name : 'HOME_ADDR',anchor : '90%',maxLength:100},
					{xtype : 'textfield',fieldLabel : '居住地邮政编码',name : 'HOME_ZIPCODE',anchor : '90%',maxLength:20},
					{xtype : 'textfield',fieldLabel : '<font color=red>*</font>手机号码 ',name : 'MOBILE_PHONE',anchor : '90%',allowBlank: false,maxLength:20}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'textfield',fieldLabel : '邮政编码',name : 'POST_ZIPCODE',anchor : '90%',maxLength:10},
					{xtype : 'combo',name : 'RESIDENCE',hiddenName : 'RESIDENCE',fieldLabel : '<font color=red>*</font>居住状况',store : residenceStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'textfield',fieldLabel : '<font color=red>*</font>住宅电话',name : 'HOME_TEL',anchor : '90%',allowBlank: false,maxLength:20},
					{xtype : 'textfield',fieldLabel : 'Email地址',name : 'EMAIL',anchor : '90%',maxLength:40}
				]
		    }]
		}]
	},{
	 	xtype : 'fieldset',
	 	title : '个人客户单位信息',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'combo',name : 'CAREER_TYPE',hiddenName : 'CAREER_TYPE',fieldLabel : '<font color=red>*</font>职业',store : careerTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'UNIT_CHAR',hiddenName : 'UNIT_CHAR',fieldLabel : '单位性质',store : unitCharStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '单位地址 ',name : 'UNIT_ADDR',anchor : '90%',maxLength:100},
					{xtype : 'textfield',fieldLabel : '单位电话 ',name : 'UNIT_TEL',anchor : '90%',maxLength:25},
					{xtype : 'textfield',fieldLabel : '单位联系人 ',name : 'CNT_NAME',anchor : '90%',maxLength:30},
					{xtype : 'combo',name : 'DUTY',hiddenName : 'DUTY',fieldLabel : '职务',store : dutyStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '工资开户行 ',name : 'SALARY_ACCT_BANK',anchor : '90%',maxLength:40}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'textfield',fieldLabel : '工作单位  ',name : 'UNIT_NAME',anchor : '90%',maxLength:100},
					{xtype : 'busiType',fieldLabel : '所属行业',hiddenName:'PROFESSION',name : 'PROFESSION_NAME',readOnly : false,anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '单位邮编 ',name : 'UNIT_ZIPCODE',anchor : '90%',maxLength:20},
					{xtype : 'textfield',fieldLabel : '单位传真 ',name : 'UNIT_FEX',anchor : '90%',maxLength:20},
					{xtype : 'datefield',name : 'CAREER_START_DATE',fieldLabel : '单位起始工作年',format:'Y-m-d',anchor : '90%'},
					{xtype : 'combo',name : 'CAREER_TITLE',hiddenName : 'CAREER_TITLE',fieldLabel : '职称',store : jobTitleStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '工资账号 ',name : 'SALARY_ACCT_NO',anchor : '90%',maxLength:32}
					
				]
		    }]
		},
		{xtype : 'textarea',fieldLabel : '个人简历',name : 'RESUME',anchor : '95%',maxLength: 100}
		]
	},{
	 	xtype : 'fieldset',
	 	title : '管户人',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype:'textfield',name:'ORG_KEY_ID',hidden:true},
					{xtype:'textfield',name:'MATEINFO_KEY_ID',hidden:true},
					{xtype:'textfield',name:'KEYFLAG_CUST_ID',hidden:true},
					{xtype:'textfield',name:'CUST_GRADE_ID',hidden:true},
					{xtype:'textfield',name:'IDENT_ID',hidden:true},
					{xtype:'textfield',name:'IDENT_ID1',hidden:true},
					{xtype : 'textfield',name : 'BELONG_ORG_NAME',fieldLabel : '主管机构',anchor : '90%',disabled:true,cls:'x-readOnly'}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype:'textfield',name:'MGR_KEY_ID',hidden:true},
					{xtype : 'textfield',name : 'MGR_NAME',fieldLabel : '主管客户经理',anchor : '90%',disabled:true,cls:'x-readOnly'}
				]
		    }]
		}]
	}],
	 buttons:[{
		text:'修改历史',
		handler:function(){
			updateHisWin.show();
		}
	},{
		 text:'提交',
		 id:'per_sx',
		 hidden:true,
		 handler:function(){
			if (!sxPerInfo.getForm().isValid()) {
				Ext.MessageBox.alert('提示','校验失败，输入有误或存在漏输入项,请检查输入项');
		        return false;
		    }
		 	Ext.Msg.wait('正在提交，请稍后......','系统提示');
		 	if(!validSxPerFn()){
		 		return false;
		 	}
		 	var identArr0 = ['IDENT_TYPE', 'IDENT_NO', 'IDENT_EXPIRED_DATE', 'IDENT_ID'];
		 	var identArr1 = ['IDENT_TYPE1', 'IDENT_NO1', 'IDENT_EXPIRED_DATE1', 'IDENT_ID1'];
		 	var creditArr = ['CUST_GRADE_ID','CUST_GRADE','EVALUATE_DATE'];
		 	var keyflagArr = ['KEYFLAG_CUST_ID','FOREIGN_PASSPORT_FLAG'];
		 	var mateinfoArr = ['MATEINFO_KEY_ID','PO_ANNUAL_INCOME','PO_CAREER','PO_CUST_ID_MATE','PO_DUTY','PO_IDENT_NO','PO_IDENT_TYPE','PO_JOB_TITLE','PO_MARR_CERT_NO','PO_MOBILE','PO_OFFICE_TEL','PO_WORK_START_DATE','PO_WORK_UNIT'];
		 	
		 	var identFlag0 = false;
		 	var identFlag1 = false;
		 	var creditFlag = false;
		 	var keyFlag = false;
		 	var mateinfoFlag = false;
		 	
		    var json1 = sxPerStore.getAt(0).json;
			var json2 = sxPerInfo.form.getValues(false);
			var tempCustId = custId;
			var perModel = [];
			for(var key in json2){
				var pcbhModel = {};
				var field = sxPerInfo.getForm().findField(key);
				//排除放大镜,放大镜必须单独处理
				if(key == 'PROFESSION'){
					continue;//放大镜隐藏字段不处理
				}else if(key == 'PROFESSION_NAME'){
					var tempkey = field.hiddenName?field.hiddenName:key;
					var tempField = tempkey == key ? field:sxPerInfo.getForm().findField(tempkey);
					if(!((json1[tempkey]==tempField.getValue()) || (null==json1[tempkey]&&null==tempField.getValue()))){
						var pcbhModel = {};
						pcbhModel.custId = tempCustId;
						pcbhModel.updateBeCont = json1[key];
						pcbhModel.updateAfCont = tempField.getValue();
						pcbhModel.updateAfContView = field.getValue();
						pcbhModel.updateItem = field.fieldLabel;
						pcbhModel.updateItemEn = field.hiddenName;
						pcbhModel.fieldType = '1';
						perModel.push(pcbhModel);
					}
					continue;
				}
				if(field.getXType() == 'combo'){
					var s = field.getValue();
					if(json1[key] != s){
						if(json1[key]!=null && s!=""){
							pcbhModel.custId = tempCustId;
							pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json1[key],'value');
							pcbhModel.updateAfCont = s;
							pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
							pcbhModel.updateItem = field.fieldLabel;
							pcbhModel.updateItemEn = field.name;
							pcbhModel.fieldType = '1';
							perModel.push(pcbhModel);
							//记录用于判断证件类型1与证件类型2是否修改
							if(identArr0.indexOf(key) > - 1){
								identFlag0 = true;
							}
							if(identArr1.indexOf(key) > - 1){
								identFlag1 = true;
							}
							if(creditArr.indexOf(key) > -1){
								creditFlag = true;
							}
							if(keyflagArr.indexOf(key) > - 1){
								keyFlag = true;
							}
							if(mateinfoArr.indexOf(key) > -1){
								mateinfoFlag = true;
							}
						}
					}
				}else{
					if(!((json1[key]==json2[key]) || (null==json1[key]&&null==json2[key]))){
						var pcbhModel = {};
						pcbhModel.custId = tempCustId;
						pcbhModel.updateBeCont = json1[key];
						pcbhModel.updateAfCont = json2[key];
						pcbhModel.updateAfContView = json2[key];
						pcbhModel.updateItem = field.fieldLabel;
						pcbhModel.updateItemEn = field.name;
						pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
						perModel.push(pcbhModel);
						//记录用于判断证件类型1与证件类型2是否修改
						if(identArr0.indexOf(key) > - 1){
							identFlag0 = true;
						}
						if(identArr1.indexOf(key) > - 1){
							identFlag1 = true;
						}
						if(creditArr.indexOf(key) > -1){
							creditFlag = true;
						}
						if(keyflagArr.indexOf(key) > - 1){
							keyFlag = true;
						}
						if(mateinfoArr.indexOf(key) > -1){
							mateinfoFlag = true;
						}
						//添加主管机构变更主键
						if(key == 'BELONG_ORG'){
							addKeyFn(perModel,tempCustId,sxPerInfo,'ORG_KEY_ID','主管机构ID');
							addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'ORG_CUST_ID');
						}
						//添加主管客户经理变更主键
						if(key == 'MGR_ID'){
							addKeyFn(perModel,tempCustId,sxPerInfo,'MGR_KEY_ID','主管客户经理ID');
							addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'MGR_CUST_ID');
						}
					}
				}
			}
			if(identFlag0){
				addKeyFn(perModel,tempCustId,sxPerInfo,'IDENT_ID','证件1ID');
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'IDENT_CUST_ID');
				addFieldFn(perModel,tempCustId,custName,sxPerInfo.getForm().findField('PERSONAL_NAME').getValue(),'IDENT_CUST_NAME');
				//级联更新证件号码1与证件类型1
				addFieldFn(perModel,tempCustId,'',sxPerInfo.getForm().findField('IDENT_TYPE').getValue(),'IDENT_IDENT_TYPE');
				addFieldFn(perModel,tempCustId,'',sxPerInfo.getForm().findField('IDENT_NO').getValue(),'IDENT_IDENT_NO');
				addFieldFn(perModel,tempCustId,'','1','OPEN_ACC_IDENT_MODIFIED_FLAG');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_MODIFIED_TIME','','2');
				addFieldFn(perModel,tempCustId,'','CRM','IDENT_LAST_UPDATE_SYS','');
				addFieldFn(perModel,tempCustId,'',JsContext._userId,'IDENT_LAST_UPDATE_USER','');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_LAST_UPDATE_TM','','2');
			}
			if(identFlag1){
				addKeyFn(perModel,tempCustId,sxPerInfo,'IDENT_ID1','证件2ID');
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'IDENT_CUST_ID1');
				addFieldFn(perModel,tempCustId,custName,sxPerInfo.getForm().findField('PERSONAL_NAME').getValue(),'IDENT_CUST_NAME1');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_MODIFIED_TIME1','','2');
				addFieldFn(perModel,tempCustId,'','CRM','IDENT_LAST_UPDATE_SYS1','');
				addFieldFn(perModel,tempCustId,'',JsContext._userId,'IDENT_LAST_UPDATE_USER1','');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_LAST_UPDATE_TM1','','2');
			}
			if(creditFlag){
				addFieldFn(perModel,tempCustId,'ID_SEQUENCE.NEXTVAL'
					,sxPerInfo.getForm().findField('CUST_GRADE_ID').getValue(),'CUST_GRADE_ID','1','1');
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'GRADE_CUST_ID');
				addFieldFn(perModel,tempCustId,'03','03','CUST_GRADE_TYPE');
				addFieldFn(perModel,tempCustId,'',JsContext._userId,'GRADE_LAST_UPDATE_USER','');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'GRADE_LAST_UPDATE_TM','','2');
			}
			if(keyFlag){
				addFieldFn(perModel,tempCustId,tempCustId
					,sxPerInfo.getForm().findField('KEYFLAG_CUST_ID').getValue(),'KEYFLAG_CUST_ID','1','1');
			}
			if(mateinfoFlag){
				addFieldFn(perModel,tempCustId,tempCustId
					,sxPerInfo.getForm().findField('MATEINFO_KEY_ID').getValue(),'MATEINFO_KEY_ID','1','1');
			}
			if(perModel.length < 1){
				Ext.Msg.alert('提示', '未作任何修改,不允许提交!');
				return false;
			}
			Ext.Ajax.request({
	        	url : basepath + '/dealWithFsx!initFlowPersx.json',
	            method : 'POST',
	            params : {
			    	'perModel': Ext.encode(perModel),
			    	'custId': tempCustId,
			    	'custName': custName
				},
	            success : function(response) {
	             	var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode,function(){
						infoPanel.layout.setActiveItem(0);
						fsxPerInfo.setActiveTab(0);
						fsxComInfo.setActiveTab(0);						
					});//选择下一步办理人
	            },
	            failure : function(response) {
	                Ext.Msg.alert('提示', '操作失败!');
	            }
	       });
		 }
	 }]
});
/**
 * 对私授信信息校验
 * @return {Boolean}
 */
var validSxPerFn = function(){
	var tempJson1 = sxPerStore.getAt(0).json;
	var tempJson2 = sxPerInfo.getForm().getValues(false);
	//1． 客户证件或名称若有修改，则“CUSTNAME OR ID/REFNO CHANGE BY”为必选项
	if(tempJson2.CUSTNM_IDENT_MODIFIED_FLAG == ''
	    && (tempJson1.PERSONAL_NAME != tempJson2.PERSONAL_NAME
	    || tempJson1.IDENT_TYPE != tempJson2.IDENT_TYPE
		|| tempJson1.IDENT_NO != tempJson2.IDENT_NO
		|| tempJson1.IDENT_EXPIRED_DATE != tempJson2.IDENT_EXPIRED_DATE
		|| tempJson1.IDENT_TYPE1 != tempJson2.IDENT_TYPE1
		|| tempJson1.IDENT_NO1 != tempJson2.IDENT_NO1
		|| tempJson1.IDENT_EXPIRED_DATE1 != tempJson2.IDENT_EXPIRED_DATE1)){
		Ext.Msg.alert('提示','校验失败，客户名称或证件修改，则“CUSTNAME OR ID/REFNO CHANGE BY”为必选项！');
		return false;
	}
	//2.必须保证证件2输入值后，证件类型和证件号码都存在
	if((tempJson2.IDENT_TYPE1 == '' || tempJson2.IDENT_NO1 == '')
		&& (tempJson2.IDENT_TYPE1 != '' || tempJson2.IDENT_NO1 != '' || tempJson2.IDENT_EXPIRED_DATE1 != '')){
		Ext.Msg.alert('提示','校验失败，证件2类型、号码、失效日期录入值后，必须保证证件类型2与证件号码2同时有值！');
		return false;
	}
	
	//3.证件1失效日期先前无值,录入值后必须校验大于经办日
	if(tempJson1.IDENT_EXPIRED_DATE == '' && tempJson2.IDENT_EXPIRED_DATE != '' && tempJson2.IDENT_EXPIRED_DATE <= _sysCurrDate){
		Ext.Msg.alert('提示','校验失败，证件1失效日期必须大于经办日期！');
		return false;
	}
	//4.证件2失效日期先前无值,录入值后必须校验大于经办日
	if(tempJson1.IDENT_EXPIRED_DATE1 == '' && tempJson2.IDENT_EXPIRED_DATE1 != '' && tempJson2.IDENT_EXPIRED_DATE1 <= _sysCurrDate){
		Ext.Msg.alert('提示','校验失败，证件2失效日期必须大于经办日期！');
		return false;
	}
	
	//警告提示,不影响程序继续执行
	//2.1证件1失效日期先前有值,且修改后值还是小于经办日,作警告提示
	if(tempJson1.IDENT_EXPIRED_DATE != '' && tempJson2.IDENT_EXPIRED_DATE <= _sysCurrDate){
		showMsgNotification('注意：证件1失效日期已小于或等于当前经办日期');
	}
	//2.2证件2失效日期先前有值,且修改后值还是小于经办日,作警告提示
	if(tempJson1.IDENT_EXPIRED_DATE1 != '' && tempJson2.IDENT_EXPIRED_DATE1 <= _sysCurrDate){
		showMsgNotification('注意：证件2失效日期已小于或等于当前经办日期');
	}
	return true;
};
//////////////////////////////////////////////////////////////////////////////////////////////////

/**
 * 公司授信客户信息维护
 */
////////////////////////////////////////////////////////////////////////////////////////////////
var sxComStore = new Ext.data.Store({
	restful:true,
	proxy : new Ext.data.HttpProxy({
		url:basepath+'/dealWithFsx!queryComsx.json',
		method:'GET'
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, [
		'SHORT_NAME','EN_NAME','IDENT_ID1','IDENT_TYPE','IDENT_NO','INOUT_FLAG','AR_CUST_FLAG','AR_CUST_TYPE','FIRST_LOAN_DATE','LOAN_CUST_RANK',	
		
		'CUST_ID','ORG_CUST_TYPE','CUST_NAME','NATION_CODE','ENT_PROPERTY','INVEST_TYPE','COM_HOLD_TYPE','MAIN_INDUSTRY','MAIN_INDUSTRY_NAME',
		'INDUSTRY_CATEGORY','EMPLOYEE_SCALE','ENT_BELONG','ENT_SCALE','ENT_SCALE_RH','BUILD_DATE',
		'COM_HOLD_TYPE','ORG_CODE','ORG_EXP_DATE','ORG_CODE_ANN_DATE','ORG_REG_DATE','ORG_CODE_UNIT',
		'SUPER_DEPT','SUPER_DEPT_NAME',
		'MINOR_BUSINESS','MAIN_BUSINESS','COM_SP_BUSINESS','LOAN_CARD_FLAG','LOAN_CARD_NO','LOAN_CARD_STAT','LOAD_CARD_PWD',
		'LOAD_CARD_AUDIT_DT','LEGAL_REPR_IDENT_TYPE','LEGAL_REPR_IDENT_NO','LEGAL_REPR_NAME','ORG_ADDR','ORG_ZIPCODE',
		'ORG_FEX','ORG_HOMEPAGE','ORG_TEL','ORG_EMAIL','IS_BUILD_NEW','FACILITY_MAIN','PROD_CAPACITY','TOP_CORP_LEVEL',
		'COMP_ORG','FIN_REP_TYPE','HOLD_STOCK_AMT',
		
		'KEYFLAG_CUST_ID','IS_NOT_LOCAL_ENT','IS_RURAL_CORP','IS_LISTED_CORP','HAS_IE_RIGHT','IS_PREP_ENT','IS_AREA_IMP_ENT','IS_NTNAL_MACRO_CTRL','IS_HIGH_RISK_POLL','IS_STEEL_ENT',
		
		'REGISTER_CUST_ID','REGISTER_NO','REGISTER_DATE','REG_ORG','APPR_DOC_NO','REGISTER_ADDR','REGISTER_CAPITAL','REGISTER_TYPE','END_DATE','AUDIT_END_DATE','APPR_ORG','REGISTER_AREA','REGISTER_EN_ADDR','REGISTER_CAPITAL_CURR',
		
		'ADDR','IDENT_REG_ID','IDENT_REG_NO','IDENT_REG_ORG','IDEN_REG_DATE','IDENT_VALID_PERIOD','IDENT_REG_TYPE',
		'BUSI_CUST_ID','MAIN_PRODUCT','WORK_FIELD_AREA','MANAGE_STAT','WORK_FIELD_OWNERSHIP','LEGAL_CUST_ID','LEGAL_RESUME','SIGN_START_DATE','SIGN_END_DATE','PO_IDENT_TYPE','PO_IDENT_NO','PO_NAME','INDIV_CUS_ID',
		'CONTROL_IDENT_TYPE','CONTROL_IDENT_NO','CONTROL_CUST_ID','CONTROL_NAME','CONTROL_SIGN_START_DATE','CONTROL_SIGN_END_DATE','AUTH_START_DATE','AUTH_END_DATE','CONTROL_INDIV_CUS_ID','PO_IDENT_TYPE','CONTROL_PO_IDENT_NO','CONTROL_PO_NAME','CONTROL_RESUME',
		'CWFZ_LINKMAN_ID','CWFZ_PERSON','CWLX_LINKMAN_ID','CWLX_PERSON','CWLX_MOBILE',
		
		'CUST_GRADE_ID','CUST_GRADE','EVALUATE_DATE',
		
		'BELONG_ORG','BELONG_ORG_NAME','ORG_KEY_ID',
		'MGR_ID','MGR_NAME','MGR_KEY_ID'
	])
});

var searchSxComFn = function(){
	if(sxComInfo.getForm().getEl()){
		sxComInfo.getForm().getEl().dom.reset();
	}
	sxComStore.load({
		params : {
			custId : custId
		},
		callback:function(){
			if(sxComStore.getCount()!=0){
				sxComInfo.getForm().loadRecord(sxComStore.getAt(0));
			}
		}
	})
};
var sxComInfo = new Ext.form.FormPanel({
	 frame : true,
	 autoScroll : true,
	 title : '对公授信',
	 labelWidth : 160,
	 buttonAlign : "center",
	 items:[{
	 	xtype : 'fieldset',
	 	title : '客户基本信息',
	 	items : [{
			layout : 'column',
		    items:[{
		        	columnWidth : .5,
					layout : 'form',
					items :[
						{xtype: 'textfield',name : 'CUST_ID', fieldLabel : '<font color="red">*</font>客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%'},
						{xtype : 'textfield',name : 'CUST_NAME',fieldLabel : '<font color="red">*</font>客户名称',anchor : '90%',allowBlank:false,maxLength:40},
						{xtype : 'textfield',name : 'EN_NAME',fieldLabel : '外文名称',anchor : '90%',maxLength:50},
						{xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '<font color="red">*</font>证件类型',store : indent00Store,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
			            {xtype : 'combo',name : 'IS_NOT_LOCAL_ENT',hiddenName : 'IS_NOT_LOCAL_ENT',fieldLabel : '<font color="red">*</font>是否异地客户',store : isNotLocalEntStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
						{xtype : 'combo',name : 'INOUT_FLAG',hiddenName : 'INOUT_FLAG',fieldLabel : '<font color="red">*</font>境内境外标志',store : inOrOutStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
						{xtype : 'combo',name : 'INVEST_TYPE',hiddenName : 'INVEST_TYPE',fieldLabel : '<font color="red">*</font>投资主体',store : investTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
						{xtype : 'combo',name : 'COM_HOLD_TYPE',hiddenName : 'COM_HOLD_TYPE',fieldLabel : '<font color="red">*</font>控股类型',store : comholdtypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
						{xtype : 'busiType',fieldLabel : '行业主营',hiddenName:'MAIN_INDUSTRY',name : 'MAIN_INDUSTRY_NAME',readOnly : false,anchor : '90%'},
						{xtype : 'combo',name : 'INDUSTRY_CATEGORY',hiddenName : 'INDUSTRY_CATEGORY',fieldLabel : '<font color="red">*</font>行业分类（企业规模）',store : industryCategoryStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
						{xtype : 'combo',name : 'AR_CUST_FLAG',hiddenName : 'AR_CUST_FLAG',fieldLabel : '<font color="red">*</font>AR客户标志(CSPS)',store : arCustFlagStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
						{xtype : 'numberfield',fieldLabel : '从业人数',name : 'EMPLOYEE_SCALE',anchor : '90%',maxLength:17}
			       ]
		      	},{
		       		columnWidth : .5,
				    layout : 'form',
				    items :[
				    	{xtype : 'combo',name : 'ORG_CUST_TYPE',hiddenName : 'ORG_CUST_TYPE',fieldLabel : '公司客户类型',store : comTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
				    	{xtype : 'textfield',name : 'SHORT_NAME',fieldLabel : '客户简称',anchor : '90%',maxLength:40},
						{xtype : 'combo',name : 'NATION_CODE',hiddenName : 'NATION_CODE',fieldLabel : '<font color="red">*</font>国别',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
			            {xtype : 'textfield',fieldLabel : '<font color="red">*</font>证件号码',name : 'IDENT_NO',anchor : '90%',allowBlank:false,maxLength:50},
				    	{xtype : 'combo',name : 'ENT_PROPERTY',hiddenName : 'ENT_PROPERTY',fieldLabel : '<font color="red">*</font>企业性质',store : entPropertyStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
						{xtype : 'combo',name : 'IS_RURAL_CORP',hiddenName : 'IS_RURAL_CORP',fieldLabel : '城乡类型',store :isRuralCorpStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
						{xtype : 'combo',name : 'ENT_BELONG',hiddenName : 'ENT_BELONG',fieldLabel : '隶属关系',store : entbelongStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
						{xtype : 'combo',name : 'ENT_SCALE',hiddenName : 'ENT_SCALE',fieldLabel : '企业规模(银监)',store : entScaleStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly'},
						{xtype : 'combo',name : 'ENT_SCALE_RH',hiddenName : 'ENT_SCALE_RH',fieldLabel : '企业规模(人行)',store : entScaleRhStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly'},
						{xtype : 'datefield',name : 'BUILD_DATE',fieldLabel : '<font color="red">*</font>成立日期',format:'Y-m-d',anchor : '90%',allowBlank:false},
				    	{xtype : 'combo',name : 'AR_CUST_TYPE',hiddenName : 'AR_CUST_TYPE',fieldLabel : 'AR客户类型(CSPS)',store : arCustTypeStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
						{xtype : 'combo',name : 'CUSTNM_IDENT_MODIFIED_FLAG',hiddenName : 'CUSTNM_IDENT_MODIFIED_FLAG',fieldLabel : 'CUSTNAME OR ID/REFNO CHANGE BY',store : custnmIdentModifiedStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
					]
				}]
			}]
	 },{
	 	xtype : 'fieldset',
	 	title : '组织机构代码信息',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'textfield',name : 'ORG_CODE',fieldLabel : '<font color=red>*</font>组织机构代码 ',anchor : '90%',allowBlank:false},
					{xtype : 'datefield',name : 'ORG_EXP_DATE',fieldLabel : '<font color=red>*</font>组织机构登记有效期 ',format:'Y-m-d',anchor : '90%',allowBlank:false},
					{xtype : 'datefield',name : 'ORG_CODE_ANN_DATE',fieldLabel : '组织机构代码证年检到期日 ',format:'Y-m-d',anchor : '90%'}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'datefield',name : 'ORG_REG_DATE',fieldLabel : '<font color=red>*</font>组织机构登记日期 ',format:'Y-m-d',anchor : '90%',allowBlank:false},
					{xtype : 'textfield',name : 'ORG_CODE_UNIT',fieldLabel : '组织机构代码证颁发机关',anchor : '90%',maxLength:60}
				]
		    }]
		}]
	},{
	 	xtype : 'fieldset',
	 	title : '登记注册信息',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'textfield',name : 'REGISTER_NO',fieldLabel : '<font color=red>*</font>登记注册号',anchor : '90%',allowBlank:false,maxLength:35},
					{xtype : 'datefield',name : 'REGISTER_DATE',fieldLabel : '<font color=red>*</font>注册登记日期',format:'Y-m-d',anchor : '90%',allowBlank:false},
					{xtype : 'textfield',name : 'REG_ORG',fieldLabel : '注册登记机关',anchor : '90%',maxLength:80},
					{xtype : 'orgchoose',hiddenName:'SUPER_DEPT',name: 'SUPER_DEPT_NAME',fieldLabel:'主管单位',searchType:'ALLORG',checkBox: false,anchor : '90%',maxLength:60},
					{xtype : 'textfield',name : 'APPR_DOC_NO',fieldLabel : '批准文号',anchor : '90%',maxLength:80},
					{xtype : 'textfield',name : 'REGISTER_ADDR',fieldLabel : '<font color=red>*</font>注册登记地址',anchor : '90%',allowBlank:false,maxLength:100},
					{xtype : 'numberfield',name : 'REGISTER_CAPITAL',fieldLabel : '<font color=red>*</font>注册资金（万元）',anchor : '90%',allowBlank:false,maxLength:17},
					{xtype : 'textarea',name : 'MINOR_BUSINESS',fieldLabel : '<font color=red>*</font>兼营业务',anchor : '90%',allowBlank:false,maxLength:500}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'combo',name : 'REGISTER_TYPE',hiddenName : 'REGISTER_TYPE',fieldLabel : '<font color=red>*</font>登记注册类型',store : registerTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'datefield',name : 'END_DATE',fieldLabel : '<font color=red>*</font>注册登记有效期',format:'Y-m-d',anchor : '90%',allowBlank:false},
					{xtype : 'datefield',name : 'AUDIT_END_DATE',fieldLabel : '年检到期日',format:'Y-m-d',anchor : '90%'},
					{xtype : 'textfield',name : 'APPR_ORG',fieldLabel : '批准机关',anchor : '90%',maxLength:80},
					{xtype : 'combo',name : 'REGISTER_AREA',hiddenName : 'REGISTER_AREA',fieldLabel : '<font color=red>*</font>注册地行政区划',store : dqStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'textfield',name : 'REGISTER_EN_ADDR',fieldLabel : '外文注册登记地址',anchor : '90%',maxLength:250},
					{xtype : 'combo',name : 'REGISTER_CAPITAL_CURR',hiddenName : 'REGISTER_CAPITAL_CURR',fieldLabel : '<font color=red>*</font>注册资金币种',store : moneyStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'textarea',name : 'MAIN_BUSINESS',fieldLabel : '<font color=red>*</font>主营业务',anchor : '90%',allowBlank:false,maxLength:500}
				]
		    }]
		}]
	},{
	 	xtype : 'fieldset',
	 	title : '税务、特种经营、贷款卡信息',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'textfield',name : 'IDENT_REG_NO',fieldLabel : '<font color=red>*</font>税务登记代码',anchor : '90%',allowBlank:false,maxLength:50},
					{xtype : 'datefield',name : 'IDEN_REG_DATE',fieldLabel : '税务登记日期',format:'Y-m-d',anchor : '90%'},
					{xtype : 'combo',name : 'COM_SP_BUSINESS',hiddenName : 'COM_SP_BUSINESS',fieldLabel : '<font color=red>*</font>特种经营标识',store : comSpBusinessStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'textfield',name : 'LOAN_CARD_NO',fieldLabel : '贷款卡号',anchor : '90%',maxLength:16},
					{xtype : 'combo',name : 'LOAN_CARD_STAT',hiddenName : 'LOAN_CARD_STAT',fieldLabel : '贷款卡状态',store : loanCardStatStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
					
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'textfield',name : 'IDENT_REG_ORG',fieldLabel : '税务登记机关',anchor : '90%',maxLength:40},
					{xtype : 'numberfield',name : 'IDENT_VALID_PERIOD',fieldLabel : '税务登记有效期',anchor : '90%',maxLength:10},
					{xtype : 'combo',name : 'LOAN_CARD_FLAG',hiddenName : 'LOAN_CARD_FLAG',fieldLabel : '<font color=red>*</font>有无贷款卡',store : loanCardStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'textfield',name : 'LOAD_CARD_PWD',fieldLabel : '贷款卡密码',anchor : '90%',maxLength:250},
					{xtype : 'datefield',name : 'LOAD_CARD_AUDIT_DT',fieldLabel : '贷款卡年检到期日',format:'Y-m-d',anchor : '90%'}
				]
		    }]
		}]
	},{
	 	xtype : 'fieldset',
	 	title : '其他信息',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'textfield',fieldLabel : '<font color=red>*</font>财务负责人 ',name : 'CWFZ_PERSON',anchor : '90%',allowBlank: false,maxLength:100},
					{xtype : 'textfield',fieldLabel : '<font color=red>*</font>通讯地址',name : 'ORG_ADDR',anchor : '90%',allowBlank: false,maxLength:100},
					{xtype : 'textfield',fieldLabel : '<font color=red>*</font>邮政编码',name : 'ORG_ZIPCODE',anchor : '90%',allowBlank: false,maxLength:20},
					{xtype : 'textfield',fieldLabel : '传真',name : 'ORG_FEX',anchor : '90%',maxLength:20},
					{xtype : 'textfield',fieldLabel : '网址',name : 'ORG_HOMEPAGE',anchor : '90%',maxLength:100}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'textfield',fieldLabel : '财务联系人 ',name : 'CWLX_PERSON',anchor : '90%',maxLength:100},
					{xtype : 'textfield',fieldLabel : '财务部联系人电话 ',name : 'CWLX_MOBILE',anchor : '90%',maxLength:20},
					{xtype : 'textfield',fieldLabel : '联系电话',name : 'ORG_TEL',anchor : '90%',maxLength:35},
					{xtype : 'textfield',fieldLabel : 'Email',name : 'ORG_EMAIL',anchor : '90%',maxLength:40}
				]
		    }]
		}]
	},{
	 	xtype : 'fieldset',
	 	title : '对公客户概况信息',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'combo',name : 'IS_LISTED_CORP',hiddenName : 'IS_LISTED_CORP',fieldLabel : '<font color=red>*</font>上市公司标志',store : isListedCorpStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank: false},
					{xtype : 'combo',name : 'HAS_IE_RIGHT',hiddenName : 'HAS_IE_RIGHT',fieldLabel : '进出口权标识 ',store : hasIeRightStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'numberfield',fieldLabel : '经营场地面积(平方米)',name : 'WORK_FIELD_AREA',anchor : '90%',maxLength:100},
					{xtype : 'combo',name : 'MANAGE_STAT',hiddenName : 'MANAGE_STAT',fieldLabel : '<font color=red>*</font>经营状况',store : manageStatStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank: false},
					{xtype : 'combo',name : 'IS_PREP_ENT',hiddenName : 'IS_PREP_ENT',fieldLabel : '优势企业',store : isPrepEntStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'combo',name : 'IS_NTNAL_MACRO_CTRL',hiddenName : 'IS_NTNAL_MACRO_CTRL',fieldLabel : '<font color=red>*</font>国家宏观调控限控行业 ',store : isNtnalMacroCtrlStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank: false},
					{xtype : 'combo',name : 'TOP_CORP_LEVEL',hiddenName : 'TOP_CORP_LEVEL',fieldLabel : '龙头企业',store : topCorpLevelStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'combo',name : 'IS_BUILD_NEW',hiddenName : 'IS_BUILD_NEW',fieldLabel : '是否2年内新设立企业',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls: 'x-readOnly'},
					{xtype : 'textfield',fieldLabel : '主要产品情况',name : 'MAIN_PRODUCT',anchor : '90%',maxLength:30},
					{xtype : 'combo',name : 'WORK_FIELD_OWNERSHIP',hiddenName : 'WORK_FIELD_OWNERSHIP',fieldLabel : '<font color=red>*</font>经营场地所有权',store : workOwnershipStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank: false},
					{xtype : 'combo',name : 'IS_AREA_IMP_ENT',hiddenName : 'IS_AREA_IMP_ENT',fieldLabel : '地区重点企业',store : isAreaImpEntStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'combo',name : 'IS_HIGH_RISK_POLL',hiddenName : 'IS_HIGH_RISK_POLL',fieldLabel : '<font color=red>*</font>高环境风险高污染企业',store : isHighRiskPollStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank: false},
					{xtype : 'combo',name : 'IS_STEEL_ENT',hiddenName : 'IS_STEEL_ENT',fieldLabel : '<font color=red>*</font>是否钢贸行业',store : isSteelEntStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank: false},
					{xtype : 'combo',name : 'FIN_REP_TYPE',hiddenName : 'FIN_REP_TYPE',fieldLabel : '<font color=red>*</font>财务报表类型',store : finRepTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank: false}
				]
		    }]
		}]
	},{
	 	xtype : 'fieldset',
	 	title : '对公客户合作信息',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'datefield',name : 'FIRST_LOAN_DATE',fieldLabel : '建立信贷关系时间   ',format:'Y-m-d',anchor : '90%'},
					{xtype : 'combo',name : 'CUST_GRADE',hiddenName : 'CUST_GRADE',fieldLabel : '信用等级',store : custGradeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype : 'numberfield',fieldLabel : '拥有我行股份金额(元)',name : 'HOLD_STOCK_AMT',anchor : '90%',maxLength:17},
					{xtype : 'datefield',name : 'EVALUATE_DATE',fieldLabel : '信用评级日期   ',format:'Y-m-d',anchor : '90%'}
				]
		    }]
		}]
	},{
	 	xtype : 'fieldset',
	 	title : '管户人',
		items:[{
			layout : 'column',
		    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype:'textfield',name:'ORG_KEY_ID',hidden:true},
					{xtype : 'textfield',name : 'BELONG_ORG_NAME',fieldLabel : '主管机构',anchor : '90%',disabled:true,cls:'x-readOnly'}
				]
		    },{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype:'textfield',name:'MGR_KEY_ID',hidden:true},
					{xtype : 'textfield',name : 'MGR_NAME',fieldLabel : '主管客户经理',anchor : '90%',disabled:true,cls:'x-readOnly'}
				]
		    }]
		}]
	},{
		items:[
			{xtype:'textfield',name:'IDENT_REG_ID',hidden:true},//税务登记
			{xtype:'textfield',name:'IDENT_REG_TYPE',hidden:true},//税务登记类型
			{xtype:'textfield',name:'IDENT_ID1',hidden:true},//证件ID
			{xtype:'textfield',name:'REGISTER_CUST_ID',hidden:true},//注册ID
			{xtype:'textfield',name:'KEYFLAG_CUST_ID',hidden:true},//重要标志ID
			{xtype:'textfield',name:'CWFZ_LINKMAN_ID',hidden:true},//财务负责人ID
			{xtype:'textfield',name:'CWLX_LINKMAN_ID',hidden:true},//财务联系人ID
			{xtype:'textfield',name:'BUSI_CUST_ID',hidden:true},//经营ID
			{xtype:'textfield',name:'CUST_GRADE_ID',hidden:true}
		]
	}],
	 buttonAlign : "center",
	 buttons:[{
		text:'修改历史',
		handler:function(){
			updateHisWin.show();
		}
	},{
		 text:'提交',
		 id:'pub_sx',
		 hidden:true,
		 handler:function(){
		 	if (!sxComInfo.getForm().isValid()) {
		        Ext.MessageBox.alert('提示','校验失败，输入有误或存在漏输入项,请检查输入项');
		        return false;
		    }
		 	Ext.Msg.wait('正在提交，请稍后......','系统提示');
		 	if(!validSxComFn()){
		    	return false;
		    }
		    
		 	var identArr0 = ['IDENT_TYPE', 'IDENT_NO', 'IDENT_ID1'];
			var identArr1 = ['IDENT_REG_NO', 'IDENT_REG_TYPE', 'IDENT_REG_ORG', 'IDENT_VALID_PERIOD','IDEN_REG_DATE','IDENT_REG_ID'];
			var creditArr = ['CUST_GRADE_ID','CUST_GRADE','EVALUATE_DATE'];
			var lwlxArr = ['CWLX_LINKMAN_ID','CWLX_LINKMAN_TYPE','CWLX_PERSON','CWLX_MOBILE'];//财务联系人
			var registerArr = ['REGISTER_CUST_ID','APPR_DOC_NO','APPR_ORG','AUDIT_END_DATE','END_DATE','REGISTER_ADDR','REGISTER_AREA','REGISTER_CAPITAL','REGISTER_CAPITAL_CURR','REGISTER_DATE','REGISTER_EN_ADDR','REGISTER_NO','REGISTER_TYPE','REG_ORG'];
			var keyflagArr = ['KEYFLAG_CUST_ID','HAS_IE_RIGHT','IS_AREA_IMP_ENT','IS_HIGH_RISK_POLL','IS_LISTED_CORP','IS_NOT_LOCAL_ENT','IS_NTNAL_MACRO_CTRL','IS_PREP_ENT','IS_RURAL_CORP','IS_STEEL_ENT'];
			var busiArr = ['BUSI_CUST_ID','MAIN_PRODUCT','MANAGE_STAT','WORK_FIELD_AREA','WORK_FIELD_OWNERSHIP'];
			
			var identFlag0 = false;
			var identFlag1 = false;
			var creditFlag = false;
			var lwlxFlag = false;
			var registerFlag = false;
			var keyFlag = false;
			var busiFlag = false;

		    var json1 = sxComStore.getAt(0).json;
			var json2 = sxComInfo.form.getValues(false);
			var tempCustId = custId;
			var perModel = [];
			for(var key in json2){
				var pcbhModel = {};
				var field = sxComInfo.getForm().findField(key);
				//排除放大镜,放大镜必须单独处理
				if(key == 'MAIN_INDUSTRY' ||　key == 'SUPER_DEPT'){
					continue;//放大镜隐藏字段不处理
				}else if(key == 'MAIN_INDUSTRY_NAME' || key == 'SUPER_DEPT_NAME'){
					var tempkey = field.hiddenName?field.hiddenName:key;
					var tempField = tempkey == key ? field:sxComInfo.getForm().findField(tempkey);
					if(!((json1[tempkey]==tempField.getValue()) || (null==json1[tempkey]&&null==tempField.getValue()))){
						var pcbhModel = {};
						pcbhModel.custId = tempCustId;
						pcbhModel.updateBeCont = json1[key];
						pcbhModel.updateAfCont = tempField.getValue();
						pcbhModel.updateAfContView = field.getValue();
						pcbhModel.updateItem = field.fieldLabel;
						pcbhModel.updateItemEn = field.hiddenName;
						pcbhModel.fieldType = '1';
						perModel.push(pcbhModel);
					}
					continue;
				}
				if(field.getXType() == 'combo'){
					var s = field.getValue();
					if(json1[key]!=s){
						if(json1[key]!=null&&s!=""){
							pcbhModel.custId = tempCustId;
							pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json1[key],'value');
							pcbhModel.updateAfCont = s;
							pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
							pcbhModel.updateItem = field.fieldLabel;
							pcbhModel.updateItemEn = field.name;
							pcbhModel.fieldType = '1';
							perModel.push(pcbhModel);
							//记录用于判断证件类型1与证件类型2是否修改
							if(identArr0.indexOf(key) > - 1){
								identFlag0 = true;
							}
							if(identArr1.indexOf(key) > - 1){
								identFlag1 = true;
							}
							if(creditArr.indexOf(key) > -1){
								creditFlag = true;
							}
							if(registerArr.indexOf(key) > -1){
								registerFlag = true;
							}
							if(keyflagArr.indexOf(key) > -1){
								keyFlag = true;
							}
							if(busiArr.indexOf(key) > -1){
								busiFlag = true;
							}
						}
					}
				}else{
					if(!((json1[key]==json2[key]) || (null==json1[key]&&null==json2[key]))){
						var pcbhModel = {};
						pcbhModel.custId = tempCustId;
						pcbhModel.updateBeCont = json1[key];
						pcbhModel.updateAfCont = json2[key];
						pcbhModel.updateAfContView = json2[key];
						pcbhModel.updateItem = field.fieldLabel;
						pcbhModel.updateItemEn = field.name;
						pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
						perModel.push(pcbhModel);
						//记录用于判断证件类型1与证件类型2是否修改
						if(identArr0.indexOf(key) > - 1){
							identFlag0 = true;
						}
						if(identArr1.indexOf(key) > - 1){
							identFlag1 = true;
						}
						if(creditArr.indexOf(key) > -1){
							creditFlag = true;
						}
						if(lwlxArr.indexOf(key) > -1){
							lwlxFlag = true;
						}
						if(registerArr.indexOf(key) > -1){
							registerFlag = true;
						}
						if(keyflagArr.indexOf(key) > -1){
							keyFlag = true;
						}
						if(busiArr.indexOf(key) > -1){
							busiFlag = true;
						}
						//添加主管机构变更主键
						if(key == 'BELONG_ORG'){
							addKeyFn(perModel,tempCustId,sxComInfo,'ORG_KEY_ID','主管机构ID');
							addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'ORG_CUST_ID');
						}
						//添加主管客户经理变更主键
						if(key == 'MGR_ID'){
							addKeyFn(perModel,tempCustId,sxComInfo,'MGR_KEY_ID','主管客户经理ID');
							addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'MGR_CUST_ID');
						}
						if(key == 'CWFZ_PERSON'){
							addFieldFn(perModel,tempCustId,'ID_SEQUENCE.NEXTVAL'
								,sxComInfo.getForm().findField('CWFZ_LINKMAN_ID').getValue(),'CWFZ_LINKMAN_ID','1','1');
							addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'CWFZ_CUST_ID');
							addFieldFn(perModel,tempCustId,'10','10','CWFZ_LINKMAN_TYPE');
							//addFieldFn(perModel,tempCustId,'','CRM','CWFZ_LAST_UPDATE_SYS','');
							addFieldFn(perModel,tempCustId,'',JsContext._userId,'CWFZ_LAST_UPDATE_USER','');
							addFieldFn(perModel,tempCustId,'',_sysCurrDate,'CWFZ_LAST_UPDATE_TM','','2');
						}
					}
				}
			}
			if(identFlag0){
				addKeyFn(perModel,tempCustId,sxComInfo,'IDENT_ID1','证件1ID');
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'IDENT_CUST_ID1');
				addFieldFn(perModel,tempCustId,custName,sxComInfo.getForm().findField('CUST_NAME').getValue(),'IDENT_CUST_NAME1');
				//级联更新证件号码1与证件类型1
				addFieldFn(perModel,tempCustId,'',sxComInfo.getForm().findField('IDENT_TYPE').getValue(),'IDENT_TYPE1');
				addFieldFn(perModel,tempCustId,'',sxComInfo.getForm().findField('IDENT_NO').getValue(),'IDENT_NO1');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_MODIFIED_TIME1','','2');
				addFieldFn(perModel,tempCustId,'','CRM','IDENT_LAST_UPDATE_SYS1','');
				addFieldFn(perModel,tempCustId,'',JsContext._userId,'IDENT_LAST_UPDATE_USER1','');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_LAST_UPDATE_TM1','','2');
			}
			if(identFlag1){
				addKeyFn(perModel,tempCustId,sxComInfo,'IDENT_REG_ID','注册证件ID');
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'IDENT_REG_CUST_ID');
				addFieldFn(perModel,tempCustId,custName,sxComInfo.getForm().findField('CUST_NAME').getValue(),'IDENT_CUST_NAME');
				addFieldFn(perModel,tempCustId,'Y','Y','IDENT_REG_TYPE');//地税税务证件
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_MODIFIED_TIME','','2');
				addFieldFn(perModel,tempCustId,'','CRM','IDENT_LAST_UPDATE_SYS','');
				addFieldFn(perModel,tempCustId,'',JsContext._userId,'IDENT_LAST_UPDATE_USER','');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_LAST_UPDATE_TM','','2');
			}
			if(creditFlag){
				addFieldFn(perModel,tempCustId,'ID_SEQUENCE.NEXTVAL'
					,sxComInfo.getForm().findField('CUST_GRADE_ID').getValue(),'CUST_GRADE_ID','1','1');
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'GRADE_CUST_ID');
				addFieldFn(perModel,tempCustId,'03','03','CUST_GRADE_TYPE');
				addFieldFn(perModel,tempCustId,'',JsContext._userId,'GRADE_LAST_UPDATE_USER','');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'GRADE_LAST_UPDATE_TM','','2');
			}
			if(lwlxFlag){
				addFieldFn(perModel,tempCustId,'ID_SEQUENCE.NEXTVAL'
					,sxComInfo.getForm().findField('CWLX_LINKMAN_ID').getValue(),'CWLX_LINKMAN_ID','1','1');
				addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'CWLX_CUST_ID');
				addFieldFn(perModel,tempCustId,'13','13','CWLX_LINKMAN_TYPE');
				//addFieldFn(perModel,tempCustId,'','CRM','CWLX_LAST_UPDATE_SYS','');
				addFieldFn(perModel,tempCustId,'',JsContext._userId,'CWLX_LAST_UPDATE_USER','');
				addFieldFn(perModel,tempCustId,'',_sysCurrDate,'CWLX_LAST_UPDATE_TM','','2');
			}
			if(registerFlag){
				addFieldFn(perModel,tempCustId,tempCustId
					,sxComInfo.getForm().findField('REGISTER_CUST_ID').getValue(),'REGISTER_CUST_ID','1','1');
			}
			if(keyFlag){
				addFieldFn(perModel,tempCustId,tempCustId
					,sxComInfo.getForm().findField('KEYFLAG_CUST_ID').getValue(),'KEYFLAG_CUST_ID','1','1');
			}
			if(busiFlag){
				addFieldFn(perModel,tempCustId,tempCustId
					,sxComInfo.getForm().findField('BUSI_CUST_ID').getValue(),'BUSI_CUST_ID','1','1');
			}
			if(perModel.length < 1){
				Ext.Msg.alert('提示', '未作任何修改,不允许提交!');
				return false;
			}
			Ext.Ajax.request({
	            url : basepath + '/dealWithFsx!initFlowOrgsx.json',
	            method : 'POST',
	            params : {
			     	'orgModel':Ext.encode(perModel),
			        'custId': tempCustId,
			        'custName':custName
				},
	            success : function(response) {
	             	var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode,function(){
						infoPanel.layout.setActiveItem(0);
						fsxPerInfo.setActiveTab(0);
						fsxComInfo.setActiveTab(0);						
					});//选择下一步办理人
	            },
	            failure : function(response) {
	                 Ext.Msg.alert('提示', '操作失败!');
	            }
			});
		}
	}]
});
/**
 * 对公授信信息校验
 * @return {Boolean}
 */
var validSxComFn = function(){
	var tempJson1 = sxComStore.getAt(0).json;
	var tempJson2 = sxComInfo.getForm().getValues(false);
	//1． 客户证件或名称若有修改，则“CUSTNAME OR ID/REFNO CHANGE BY”为必选项
	if(tempJson2.CUSTNM_IDENT_MODIFIED_FLAG == ''
	    && (tempJson1.CUST_NAME != tempJson2.CUST_NAME
	    || tempJson1.IDENT_TYPE != tempJson2.IDENT_TYPE
		|| tempJson1.IDENT_NO != tempJson2.IDENT_NO)){
		Ext.Msg.alert('提示','校验失败，客户名称或证件修改，则“CUSTNAME OR ID/REFNO CHANGE BY”为必选项！');
		return false;
	}
	return true;
};
////////////////////////////////////////////////////////////////////////////////////////////////


/**
 * 公司非授信客户信息维护
 */
////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * 非授信查询
 */
var fsxComStore = new Ext.data.Store({
	restful:true,
	proxy : new Ext.data.HttpProxy({
		url:basepath+'/dealWithFsx!queryComfsx.json',
		method:'get'
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, [
		'CUST_ID','CUST_NAME','CUST_TYPE','ORG_FEX',
		'IDENT_ID','IDENT_TYPE','IDENT_NO','IDENT_EXPIRED_DATE',
		'IDENT_ID1','IDENT_TYPE1','IDENT_NO1','IDENT_EXPIRED_DATE1',
		'IDENT_ID2','IDENT_TYPE2','IDENT_NO2','IDENT_EXPIRED_DATE2',
		'RECOMMENDER','RECOMMENDER_NAME','EN_NAME','INOUT_FLAG','AR_CUST_FLAG','RISK_NATION_CODE','BUILD_DATE','ORG_TYPE','USA_TAX_FLAG','HQ_NATION_CODE','STAFFIN','SWIFT','JOINT_CUST_TYPE','ORG_SUB_TYPE',
		'ORG_EMAIL','BASIC_ACCT_BANK_NO','BASIC_ACCT_BANK_NAME','BASIC_ACCT_OPEN_DATE','IN_CLL_TYPE','IN_CLL_TYPE_NAME','LNCUSTP','ORG_CUST_TYPE','VIP_FLAG','CREDIT_CODE','NATION_CODE','AREA_CODE',
		'KEYFLAG_CUST_ID','IS_SEND_ECOMSTAT_FLAG','IS_FAX_TRANS_CUST',
		'MGR_KEY_ID','MGR_ID','MGR_NAME','EFFECT_DATE',
		'VIP_GRADE_ID','VIP_CUST_GRADE','RISK_GRADE_ID','RISK_CUST_GRADE',
		'AGENT_ID','AGENT_NAME','AGENT_IDENT_TYPE','AGENT_IDENT_NO','AGENT_NATION_CODE','AGENT_TEL','AGENT_CUST_ID'
	])
});
/**
 * 查询非授信客户信息
 */
var searchFsxComFn = function(){
	if(fsxCombaseInfo.getForm().getEl()){
		fsxCombaseInfo.getForm().getEl().dom.reset();
	}
	fsxComStore.load({
		params : {
			custId : custId
		},
		callback:function(){
			if(fsxComStore.getCount()!=0){
				fsxCombaseInfo.getForm().loadRecord(fsxComStore.getAt(0));
			}
		}
	})
};
  

//基本信息部分  第一屏
var fsxCombaseInfo = new Ext.form.FormPanel({
	 frame : true,
	 autoScroll : true,
	 title : '第一屏',
	 labelWidth : 140,
	 buttonAlign : "center",
	 items:[{
		layout : 'column',
	    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '<font color="red">*</font>中文名',name : 'CUST_NAME',anchor : '90%',allowBlank:false,maxLength:80},
					{xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '<font color="red">*</font>证件类型1',store : indent00Store,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
		            {xtype : 'combo',name : 'IDENT_TYPE1',hiddenName : 'IDENT_TYPE1',fieldLabel : '证件类型2',store : indent01Store,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
		            {xtype : 'datefield',name : 'IDENT_EXPIRED_DATE',fieldLabel : '<font color="red">*</font>证件1失效日期',format:'Y-m-d',anchor : '90%',allowBlank:false},
			    	{xtype : 'datefield',name : 'BUILD_DATE',fieldLabel : '<font color="red">*</font>公司成立日',format:'Y-m-d',anchor : '90%',allowBlank:false},
			        {xtype : 'combo',name : 'NATION_CODE',hiddenName : 'NATION_CODE',fieldLabel : '所在国别',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
		            {xtype : 'combo',name : 'VIP_FLAG',hiddenName : 'VIP_FLAG',fieldLabel : 'VIP标志',store : vipFlagStore,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',hidden:true},
		            {xtype : 'combo',name : 'INOUT_FLAG',hiddenName : 'INOUT_FLAG',fieldLabel : '<font color="red">*</font>境内/外标志',store : inOrOutStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'ORG_SUB_TYPE',hiddenName : 'ORG_SUB_TYPE',fieldLabel : '特殊监管区',store : orgSubTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'busiType',fieldLabel : '<font color="red">*</font>行业类别',hiddenName:'IN_CLL_TYPE',name : 'IN_CLL_TYPE_NAME',readOnly : false,anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'IS_SEND_ECOMSTAT_FLAG',hiddenName : 'IS_SEND_ECOMSTAT_FLAG',fieldLabel : '综合对账单发送标志',store : isSendEcomstatFlagStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',hidden:true},
					{xtype : 'combo',name : 'RISK_NATION_CODE',hiddenName : 'RISK_NATION_CODE',fieldLabel : '国别风险国别代码',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'combo',name : 'RISK_CUST_GRADE',hiddenName : 'RISK_CUST_GRADE',fieldLabel : '<font color=red></font>反洗钱风险等级',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:true,readOnly:true},
					{xtype : 'combo',name : 'HQ_NATION_CODE',hiddenName : 'HQ_NATION_CODE',fieldLabel : '总部所在国别',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : 'Obu Code',name : 'IDENT_NO2',anchor : '90%',maxLength:50},
					{xtype : 'textfield',fieldLabel : '机构信用代码',name : 'CREDIT_CODE',anchor : '90%',maxLength:35},
					{xtype : 'textfield',fieldLabel : 'Email',name : 'ORG_EMAIL',anchor : '90%',maxLength:40},
					{xtype : 'textfield',fieldLabel : '代理人户名',name : 'AGENT_NAME',anchor : '90%',maxLength:40},
		            {xtype : 'combo',name : 'AGENT_IDENT_TYPE',hiddenName : 'AGENT_IDENT_TYPE',fieldLabel : '代理人证件类型',store : indentStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '代理人证件号码',name : 'AGENT_IDENT_NO',anchor : '90%',maxLength:40},
					{xtype : 'userchoose',name:'RECOMMENDER_NAME',hiddenName : 'RECOMMENDER',fieldLabel : '推荐人',searchType:'ALLORG',singleSelect:true,anchor : '90%',maxLength:20}
		       ]
	      	},{
	       		columnWidth : .5,
			    layout : 'form',
			    items :[
			    	{xtype : 'combo',name : 'LNCUSTP',hiddenName : 'LNCUSTP',fieldLabel : '<font color="red">*</font>企业类型',store : lncustpStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'textfield',name : 'EN_NAME',fieldLabel : '<font color="red">*</font>英文名',anchor : '90%',allowBlank:false,maxLength:100},
			    	{xtype : 'combo',name : 'CUST_TYPE',hiddenName : 'CUST_TYPE',fieldLabel : '客户类型',store : typeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',hidden:true},
			    	{xtype : 'textfield',fieldLabel : '<font color="red">*</font>证件号码1',name : 'IDENT_NO',anchor : '90%',allowBlank:false,maxLength:50},
			    	{xtype : 'textfield',fieldLabel : '证件号码2',name : 'IDENT_NO1',anchor : '90%',maxLength:50},
			    	{xtype : 'datefield',name : 'IDENT_EXPIRED_DATE1',fieldLabel : '证件2失效日期',format:'Y-m-d',anchor : '90%'},
					{xtype : 'combo',name : 'ORG_TYPE',hiddenName : 'ORG_TYPE',fieldLabel : '合资类型',store : orgTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'combo',name : 'AREA_CODE',hiddenName : 'AREA_CODE',fieldLabel : '地区代码',store : dqStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'datefield',name : 'BASIC_ACCT_OPEN_DATE',fieldLabel : '<font color="red">*</font>客户资料开立日',format:'Y-m-d',anchor : '90%',allowBlank:false},
					{xtype : 'combo',name : 'AR_CUST_FLAG',hiddenName : 'AR_CUST_FLAG',fieldLabel : '是否AR客户标志',store : arCustFlagStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',name : 'BASIC_ACCT_BANK_NAME',fieldLabel : '开户行',anchor : '90%',disabled:true,cls:'x-readOnly'},	
					{xtype : 'textfield',name : 'MGR_NAME',fieldLabel : '客户经理',anchor : '90%',disabled:true,cls:'x-readOnly'},
					{xtype : 'datefield',name : 'EFFECT_DATE',fieldLabel : '客户经理修改生效日',format:'Y-m-d',anchor : '90%',cls:'x-readOnly',disabled:true,allowBank:false,hidden:true},
			        {xtype : 'combo',name : 'STAFFIN',hiddenName : 'STAFFIN',fieldLabel : '关联人类型',store : staffinStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : 'Swift Address',name : 'SWIFT',anchor : '90%',maxLength:20},
					{xtype : 'combo',name : 'JOINT_CUST_TYPE',hiddenName : 'JOINT_CUST_TYPE',fieldLabel : '联名户',store : jointCustTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
			        {xtype : 'combo',name : 'IS_FAX_TRANS_CUST',hiddenName : 'IS_FAX_TRANS_CUST',fieldLabel : '<font color="red">*</font>是否传真交易指示标志',store : isFaxStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank:false},
					{xtype : 'textfield',fieldLabel : '传真号码',name : 'ORG_FEX',anchor : '90%',maxLength:20},
					{xtype : 'combo',name : 'AGENT_NATION_CODE',hiddenName : 'AGENT_NATION_CODE',fieldLabel : '代理人国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'},
					{xtype : 'textfield',fieldLabel : '代理人联系电话',name : 'AGENT_TEL',anchor : '90%',maxLength:20},
					{xtype : 'combo',name : 'CUSTNM_IDENT_MODIFIED_FLAG',hiddenName : 'CUSTNM_IDENT_MODIFIED_FLAG',fieldLabel : 'CUSTNAME OR ID/REFNO CHANGE BY',store : custnmIdentModifiedStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
			]
		}]
	},{
		items:[
		    {xtype : 'textfield',fieldLabel : '客户经理ID',name : 'MGR_ID',hidden:true},
			{xtype : 'textfield',fieldLabel : '证件类型1ID',name : 'IDENT_ID',hidden:true},
			{xtype : 'textfield',fieldLabel : '证件类型2ID',name : 'IDENT_ID1',hidden:true},
			{xtype : 'textfield',fieldLabel : '证件类型3ID',name : 'IDENT_ID2',hidden:true},
			{xtype : 'textfield',fieldLabel : '归属客户经理表ID',name : 'MGR_KEY_ID',hidden:true},
			{xtype : 'textfield',fieldLabel : '代理人ID',name : 'AGENT_ID',hidden:true},
			{xtype : 'textfield',fieldLabel : '重要标志ID',name : 'KEYFLAG_CUST_ID',hidden:true},
			{xtype : 'textfield',fieldLabel : '贵宾卡ID',name : 'VIP_GRADE_ID',hidden:true},
			{xtype : 'textfield',fieldLabel : '风险等级ID',name : 'RISK_GRADE_ID',hidden:true}
		]
	}],
	buttons:[{
		text:'修改历史',
		handler:function(){
			updateHisWin.show();
		}
	},{
		 text:'下一屏',
		 handler:function(){
			fsxComInfo.setActiveTab(1);
		 }
	}]
});

var fsxComLists = new Ext.Panel({
	title : '第二屏',
	frame : true,
	autoScroll : true,
	buttonAlign : "center",
	items : [{
		xtype : 'fieldset',
		title : '地址信息',
		items : [comAddrGridPanel]
	},{
		xtype : 'fieldset',
		title : '联系人信息',
		items : [comContactPersonGrid]
	},{
		xtype : 'fieldset',
		title : '联系信息',
		items : [comContactGridPanel]
	}],
	buttons: [{
		text:'修改历史',
		handler:function(){
			updateHisWin.show();
		}
	},{
		 text:'上一屏',
		 handler:function(){
			fsxComInfo.setActiveTab(0);
		 }
	},{
		 text:'下一屏',
		 handler:function(){
			fsxComInfo.setActiveTab(2);
		 }
	}]
});



/**
 * 非授信查询
 */
var fsxComThreeStore = new Ext.data.Store({
	restful:true,
	proxy : new Ext.data.HttpProxy({
		url:basepath+'/dealWithFsx!queryComfsxThree.json',
		method:'get'
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, [
		'CUST_ID','CUST_NAME','EMPLOYEE_SCALE','ENT_SCALE_CK','REMARK',
		'IDENT_ID','IDENT_TYPE','IDENT_NO','IDENT_EXPIRED_DATE',
		'IDENT_ID1','IDENT_TYPE1','IDENT_NO1','IDENT_EXPIRED_DATE1',
		
		'TAX_REG_ID','TAX_REGISTRATION_NO','TAX_IDENT_EXPIRED_DATE',
		'LEGAL_REPR_NAME','LEGAL_REPR_IDENT_TYPE','LEGAL_REPR_IDENT_NO','LEGAL_IDENT_EXPIRED_DATE','LEGAL_ARTIFICIAL_PERSON',
		'BUSI_CUST_ID','SALE_CCY','SALE_AMT','REG_CUST_ID','REGISTER_CAPITAL','REGISTER_CAPITAL_CURR'
	])
});
/**
 * 查询非授信客户信息
 */
var searchFsxComThreeFn = function(){
	if(fsxComThreePanel.getForm().getEl()){
		fsxComThreePanel.getForm().getEl().dom.reset();
	}
	fsxComThreeStore.load({
		params : {
			custId : custId
		},
		callback:function(){
			if(fsxComThreeStore.getCount()!=0){
				fsxComThreePanel.getForm().loadRecord(fsxComThreeStore.getAt(0));
			}
		}
	});
};

//基本信息部分  第三屏
var fsxComThreePanel = new Ext.form.FormPanel({
	 frame : true,
	 autoScroll : true,
	 title : '第三屏',
	 labelWidth : 120,
	 buttonAlign : "center",
	 items:[{
		layout : 'column',
	    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
					{xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%'},
					{xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '证件类型1',store : indent00Store,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'},
		            {xtype : 'combo',name : 'IDENT_TYPE1',hiddenName : 'IDENT_TYPE1',fieldLabel : '证件类型2',store : indent01Store,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'},
		            {xtype : 'datefield',name : 'IDENT_EXPIRED_DATE',fieldLabel : '证件1失效日期',format:'Y-m-d',anchor : '90%',readOnly:true,cls:'x-readOnly'},
			    	{xtype : 'textfield',fieldLabel : '法人名',name : 'LEGAL_REPR_NAME',anchor : '90%',disabled:true,cls:'x-readOnly'},
			    	{xtype : 'combo',name : 'LEGAL_REPR_IDENT_TYPE',hiddenName : 'LEGAL_REPR_IDENT_TYPE',fieldLabel : '法人证件类型',store : indentStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',disabled:true,cls:'x-readOnly'},
			    	{xtype : 'datefield',name : 'LEGAL_IDENT_EXPIRED_DATE',fieldLabel : '法人证件失效日期',format:'Y-m-d',anchor : '90%',disabled:true,cls:'x-readOnly'},
			    	{xtype : 'textfield',fieldLabel : '税务登记证编码',name : 'TAX_REGISTRATION_NO',anchor : '90%',maxLength:50},
			    	{xtype : 'combo',name : 'REGISTER_CAPITAL_CURR',hiddenName : 'REGISTER_CAPITAL_CURR',fieldLabel : '注册资本币别',store : moneyStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',editable:true},
			        {xtype : 'combo',name : 'SALE_CCY',hiddenName : 'SALE_CCY',fieldLabel : '年销售额币别',store : moneyStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',editable:true},
		            {xtype : 'combo',name : 'ENT_SCALE_CK',hiddenName : 'ENT_SCALE_CK',fieldLabel : '公司规模',store : entScaleRhStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',editable:false}
		       ]
	      	},{
	       		columnWidth : .5,
			    layout : 'form',
			    items :[
			    	{xtype : 'textfield',fieldLabel : '中文名',name : 'CUST_NAME',anchor : '90%',readOnly:true,cls:'x-readOnly'},
			    	{xtype : 'textfield',fieldLabel : '证件号码1',name : 'IDENT_NO',anchor : '90%',readOnly:true,cls:'x-readOnly'},
			    	{xtype : 'textfield',fieldLabel : '证件号码2',name : 'IDENT_NO1',anchor : '90%',readOnly:true,cls:'x-readOnly'},
			    	{xtype : 'datefield',name : 'IDENT_EXPIRED_DATE1',fieldLabel : '证件2失效日期',format:'Y-m-d',anchor : '90%',readOnly:true,cls:'x-readOnly'},
					{xtype : 'datefield',name : 'LEGAL_ARTIFICIAL_PERSON',fieldLabel : '法人修改日',format:'Y-m-d',anchor : '90%',disabled:true,cls:'x-readOnly'},
			    	{xtype : 'textfield',fieldLabel : '法人证件号码',name : 'LEGAL_REPR_IDENT_NO',anchor : '90%',disabled:true,cls:'x-readOnly'},
			    	{xtype : 'numberfield',fieldLabel : '员工人数',name : 'EMPLOYEE_SCALE',minValue:'0',anchor : '90%',maxValue:99999999999},
					{xtype : 'datefield',name : 'TAX_IDENT_EXPIRED_DATE',fieldLabel : '税务登记证失效日期',format:'Y-m-d',anchor : '90%'},
					{xtype : 'numberfield',fieldLabel : '注册资本(万元)',name : 'REGISTER_CAPITAL',minValue:'0',anchor : '90%',maxValue:99999999999},
					{xtype : 'numberfield',fieldLabel : '年销售额(万元)',name : 'SALE_AMT',minValue:'0',anchor : '90%',maxValue:99999999999}
			]
		}]
	},
	{xtype : 'textarea',fieldLabel : '备注',name : 'REMARK',maxLength: 100,anchor : '95%'},
	{xtype : 'textfield',fieldLabel : '证件类型1ID',name : 'IDENT_ID',hidden:true},
	{xtype : 'textfield',fieldLabel : '证件类型2ID',name : 'IDENT_ID1',hidden:true},
	{xtype : 'textfield',fieldLabel : '税务登记ID',name : 'TAX_REG_ID',hidden:true},
	{xtype : 'textfield',fieldLabel : '注册ID',name : 'REG_CUST_ID',hidden:true},
	{xtype : 'textfield',fieldLabel : '经营ID',name : 'BUSI_CUST_ID',hidden:true}
	],
	 buttons:[{
		text:'修改历史',
		handler:function(){
			updateHisWin.show();
		}
	},{
		 text:'上一屏',
		 handler:function(){
			fsxComInfo.setActiveTab(1);
		 }
	},{
		text:'提交',
		id:'pub_fsx_all',
		hidden:true,
		handler:function(){
			Ext.Msg.wait('正在提交，请稍后......','系统提示');
			if (!fsxCombaseInfo.getForm().isValid()) {
		         Ext.MessageBox.alert('提示','校验失败，非授信第一屏输入有误或存在漏输入项,请检查输入项');
		         return false;
		    }
			if (!fsxComThreePanel.getForm().isValid()) {
		         Ext.MessageBox.alert('提示','校验失败，非授信第一屏输入有误或存在漏输入项,请检查输入项');
		         return false;
		    }
		    if(!validFsxComFirstFn() || !validFsxComThirdFn()){
		    	return false;
		    }
		    var fsxFirst = getFsxFirstComModel();
			var fsxSec_1 = window.getFsxSecComModel_1();
			var fsxSec_2 = window.getFsxSecComModel_2();
			var fsxSec_3 = window.getFsxSecComModel_3();
			var fsxThird = getFsxThirdComModel();
			if(fsxFirst.length <1 && fsxSec_1.length < 1 && fsxSec_2.length < 1 && fsxSec_3.length < 1 && fsxThird.length < 1){
				Ext.Msg.alert('提示', '信息未作任何修改,不允许提交!');
				return false;
			}
			Ext.Ajax.request({
	            url : basepath + '/dealWithFsx!commitFsxComAll.json',
	            method : 'POST',
	            params : {
			    	'fsxFirst':Ext.encode(fsxFirst),
			    	'fsxSec_1':Ext.encode(fsxSec_1),
			    	'fsxSec_2':Ext.encode(fsxSec_2),
			    	'fsxSec_3':Ext.encode(fsxSec_3),
			    	'fsxThird':Ext.encode(fsxThird),
			    	'custId': custId,
			    	'custName': custName
				},
	            success : function(response) {
	             	var ret = Ext.decode(response.responseText);
					var instanceid = ret[0].instanceid;//流程实例ID
					var currNode = ret[0].currNode;//当前节点
					var nextNode = ret[0].nextNode;//下一步节点
					window.selectUserListNew(ret,instanceid,currNode,nextNode,function(){
						infoPanel.layout.setActiveItem(0);
						fsxPerInfo.setActiveTab(0);
						fsxComInfo.setActiveTab(0);						
					});//选择下一步办理人
	            },
	            failure : function(response) {
	            	Ext.Msg.alert('提示', '操作失败!');
	            }
			});
		}
	 }]
});

/**
 * 对公非授信信息面板
 */
var fsxComInfo = new Ext.TabPanel({
	activeItem : 0,
	items:[fsxCombaseInfo,fsxComLists,fsxComThreePanel]
});

/**
 * 对公非授信第一屏界面字段校验
 * fsxCombaseInfo
 * @return false表示校验不通过,true表示校验通过
 */
var validFsxComFirstFn = function(){
	var tempJson1 = fsxComStore.getAt(0).json;
	var tempJson2 = fsxCombaseInfo.getForm().getValues(false);
	//1． 客户证件或名称若有修改，则“CUSTNAME OR ID/REFNO CHANGE BY”为必选项
	if(tempJson2.CUSTNM_IDENT_MODIFIED_FLAG == ''
	    && (tempJson1.CUST_NAME != tempJson2.CUST_NAME
	    || tempJson1.IDENT_TYPE != tempJson2.IDENT_TYPE
		|| tempJson1.IDENT_NO != tempJson2.IDENT_NO
		|| tempJson1.IDENT_EXPIRED_DATE != tempJson2.IDENT_EXPIRED_DATE
		|| tempJson1.IDENT_TYPE1 != tempJson2.IDENT_TYPE1
		|| tempJson1.IDENT_NO1 != tempJson2.IDENT_NO1
		|| tempJson1.IDENT_EXPIRED_DATE1 != tempJson2.IDENT_EXPIRED_DATE1)){
		Ext.Msg.alert('提示','校验失败，非授信第一屏，客户名称或证件修改，则“CUSTNAME OR ID/REFNO CHANGE BY”为必选项！');
		return false;
	}
	//2.必须保证证件2输入值后，证件类型和证件号码都存在
	if((tempJson2.IDENT_TYPE1 == '' || tempJson2.IDENT_NO1 == '')
		&& (tempJson2.IDENT_TYPE1 != '' || tempJson2.IDENT_NO1 != '' || tempJson2.IDENT_EXPIRED_DATE1 != '')){
		Ext.Msg.alert('提示','校验失败，非授信第一屏，证件2类型、号码、失效日期录入值后，必须保证证件类型2与证件号码2同时有值！');
		return false;
	}
	//2.1企业标志企业客户证件1类型不能为“境内居民身份证”、“台湾居民身份证”、“港澳居民身份证”、“境外居民护照”、“旅行证件”、“境内居民护照”
	if(tempJson2.IDENT_TYPE == '0' || tempJson2.IDENT_TYPE == 'X2' || tempJson2.IDENT_TYPE == 'X1'
		|| tempJson2.IDENT_TYPE == '2' || tempJson2.IDENT_TYPE == 'X3' || tempJson2.IDENT_TYPE == 'X4'){
		Ext.Msg.alert('提示','非授信第一屏校验失败，企业客户证件1类型不能为“境内居民身份证”、“台湾居民身份证”、“港澳居民身份证”、' +
				'“境外居民护照”、“旅行证件”、“境内居民护照”！');
		return false;
	}
	//2.2、证件2类型：证件1类型为“台湾居民身份证”时，证件2类型只能为“台湾同胞来往内地通行证”、“临时台胞证”
	if(tempJson2.IDENT_TYPE == 'X1' && (tempJson2.IDENT_TYPE1 != '6' && tempJson2.IDENT_TYPE1 != 'X24')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“台湾居民身份证”时，证件2类型只能为“台湾同胞来往内地通行证”、“临时台胞证”！');
		return false;
	}
	//2.3、证件2类型：证件1类型为“港澳居民身份证”时，证件2类型只能为“港澳居民来往内地通行证”、“旅行证件”
	if(tempJson2.IDENT_TYPE == 'X2' && (tempJson2.IDENT_TYPE1 != '5' && tempJson2.IDENT_TYPE1 != 'X3')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“港澳居民身份证”时，证件2类型只能为“港澳居民来往内地通行证”、“旅行证件”！');
		return false;
	}
	//2.4、证件2类型：证件2类型为“台湾同胞来往内地通行证”、“临时台胞证”、“港澳居民来往内地通行证”、“旅行证件”时，证件2失效日期必须输入
	if((tempJson2.IDENT_TYPE1 == '6' || tempJson2.IDENT_TYPE1 == 'X24' || tempJson2.IDENT_TYPE1 == '5' || tempJson2.IDENT_TYPE1 == 'X3')
		&& tempJson2.IDENT_EXPIRED_DATE1 == ''){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件2类型为“台湾同胞来往内地通行证”、“临时台胞证”、“港澳居民来往内地通行证”、“旅行证件”时，证件2失效日期必须输入！');
		return false;
	}
	//2.5、证件1类型：证件1类型为“台湾同胞来往内地通行证”、“临时台胞证”、“港澳居民来往内地通行证”、“旅行证件”时，证件2失效日期必须输入
	if((tempJson2.IDENT_TYPE != 'X2' && tempJson2.IDENT_TYPE != 'X1') && tempJson2.IDENT_EXPIRED_DATE == ''){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型不为“台湾居民身份证”、“港澳居民身份证”时，证件1失效日期必须输入！');
		return false;
	}
	//3.证件1失效日期先前无值,录入值后必须校验大于经办日
	if(tempJson1.IDENT_EXPIRED_DATE == '' && tempJson2.IDENT_EXPIRED_DATE != '' && tempJson2.IDENT_EXPIRED_DATE <= _sysCurrDate){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1失效日期必须大于经办日期！');
		return false;
	}
	//4.证件2失效日期先前无值,录入值后必须校验大于经办日
	if(tempJson1.IDENT_EXPIRED_DATE1 == '' && tempJson2.IDENT_EXPIRED_DATE1 != '' && tempJson2.IDENT_EXPIRED_DATE1 <= _sysCurrDate){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件2失效日期必须大于经办日期！');
		return false;
	}
	//5.特殊监管区：企业类型为“境内三资企业（保税区内）” 、“境内中资企业（保税区内） ”时，特殊监管区必须输入
	if(tempJson2.ORG_SUB_TYPE == '' && (tempJson2.LNCUSTP == '035' || tempJson2.LNCUSTP == '054')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，企业类型为“境内三资企业（保税区内）” 、“境内中资企业（保税区内） ”时，特殊监管区必须输入！');
		return false;
	}
	//6.合资类型:企业类型为“境内三资企业（保税区外） ” 、“境内三资企业（保税区内）  ”时，合资类型必须输入
	if(tempJson2.ORG_TYPE == '' && (tempJson2.LNCUSTP == '032' || tempJson2.LNCUSTP == '035')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，企业类型为“境内三资企业（保税区外） ” 、“境内三资企业（保税区内）  ”时，合资类型必须输入！');
		return false;
	}
	//7.1境内/外标志--：证件1类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，境内/外标志只能选择境内
	if((tempJson2.IDENT_TYPE == '20' || tempJson2.IDENT_TYPE == '0' || tempJson2.IDENT_TYPE == 'X4') && tempJson2.INOUT_FLAG != 'D'){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，境内/外标志只能选择境内！');
		return false;
	}
	//7.2境内/外标志--:证件1类型为“境外登记证件代码(赋码)”、“台湾居民身份证”、“港澳居民身份证”、“境外居民护照”、“旅行证件”时，境内/外标志只能选择境外
	if((tempJson2.IDENT_TYPE == '2X' || tempJson2.IDENT_TYPE == 'X2' || tempJson2.IDENT_TYPE == 'X1'
		|| tempJson2.IDENT_TYPE == '2' || tempJson2.IDENT_TYPE == 'X3') && tempJson2.INOUT_FLAG != 'F'){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“境外登记证件代码(赋码)”、“台湾居民身份证”、“港澳居民身份证”、“境外居民护照”、“旅行证件”时，境内/外标志只能选择境外！');
		return false;
	}
	//8.1总部所在国别：境内/外标志为“境外”时，总部所在国别必须输入
	if(tempJson2.INOUT_FLAG == 'F' && tempJson2.HQ_NATION_CODE == ''){
		Ext.Msg.alert('提示','非授信第一屏校验失败，境内/外标志为“境外”时，总部所在国别必须输入！');
		return false;
	}
	//8.2总部所在国别，警告不影响程序向下执行：境内/外标志为“境内”时，总部所在国别不允许输入，已设置为空
	if(tempJson2.INOUT_FLAG == 'D' &&　tempJson2.HQ_NATION_CODE != ''){
		fsxCombaseInfo.getForm().findField('HQ_NATION_CODE').setValue('');
		showMsgNotification('注意：非授信第一屏，境内/外标志为“境内”时，总部所在国别不允许输入，已设置为空！');
	}
	//9.1所在国别：境内/外标志为“境内”时，所在国别只能选择“中国”且地区代码不能选择“台湾”、“香港”、“澳门”
	if(tempJson2.INOUT_FLAG == 'D' && (tempJson2.NATION_CODE != 'CHN' 
		|| tempJson2.AREA_CODE == '710000'
		|| tempJson2.AREA_CODE == '810000'
		|| tempJson2.AREA_CODE == '820000')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，境内/外标志为“境内”时，所在国别只能选择“中国”且地区代码不能选择“台湾”、“香港”、“澳门”！');
		return false;
	}
	//9.2所在国别：境内/外标志为“境外”且所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”
	if(tempJson2.INOUT_FLAG == 'F' && tempJson2.NATION_CODE == 'CHN'
		&& (tempJson2.AREA_CODE != '710000'
		&& tempJson2.AREA_CODE != '810000'
		&& tempJson2.AREA_CODE != '820000')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，境内/外标志为“境外”且所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”！');
		return false;
	}
	//9.3所在国别：证件1类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，所在国别必须为“中国”且地区代码不能选择“台湾”、“香港”、“澳门”
	if((tempJson2.IDENT_TYPE == '20' || tempJson2.IDENT_TYPE == '0' || tempJson2.IDENT_TYPE == 'X4')
		&& (tempJson2.NATION_CODE != 'CHN'
		 || (tempJson2.NATION_CODE == 'CHN' && (tempJson2.AREA_CODE == '710000'
			|| tempJson2.AREA_CODE == '810000'
			|| tempJson2.AREA_CODE == '820000')))){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“境内组织机构代码”、“境内居民身份证”、“境内居民护照”时，所在国别必须为“中国”且地区代码不能选择“台湾”、“香港”、“澳门”！');
		return false;
	}
	//10.1地区代码: 证件1类型为“境外登记证件代码(赋码)”、“境外居民护照”、“旅行证件”且所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”
	if((tempJson2.IDENT_TYPE == '2X' || tempJson2.IDENT_TYPE == '2' || tempJson2.IDENT_TYPE == 'X3')
		&& tempJson2.NATION_CODE == 'CHN' 
		&& (tempJson2.AREA_CODE != '710000' && tempJson2.AREA_CODE != '810000' && tempJson2.AREA_CODE != '820000')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“境外登记证件代码(赋码)”、“境外居民护照”、“旅行证件”且所在国别为“中国”时，地区代码只能选择“台湾”、“香港”、“澳门”！');
		return false;
	}
	//10.2地区代码: 证件1类型为“台湾居民身份证”且所在国别为“中国”时，地区代码只能选择“台湾”
	if((tempJson2.IDENT_TYPE == 'X2' || tempJson2.IDENT_TYPE == '2') && tempJson2.NATION_CODE == 'CHN' && tempJson2.AREA_CODE != '710000'){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“台湾居民身份证”或“境外居民护照”，且所在国别为“中国”时，地区代码只能选择“台湾”！');
		return false;
	}
	//10.3地区代码: 证件1类型为“港澳居民身份证”且所在国别为“中国”时，地区代码只能选择“香港”、“澳门”
	if(tempJson2.IDENT_TYPE == 'X1' && tempJson2.NATION_CODE == 'CHN'
		&& (tempJson2.AREA_CODE != '810000' && tempJson2.AREA_CODE != '820000')){
		Ext.Msg.alert('提示','非授信第一屏校验失败，证件1类型为“港澳居民身份证”且所在国别为“中国”时，地区代码只能选择“香港”、“澳门”！');
		return false;
	}
 
	//警告提示,不影响程序继续执行
	//2.1证件1失效日期先前有值,且修改后值还是小于经办日,作警告提示
	if(tempJson1.IDENT_EXPIRED_DATE != '' && tempJson2.IDENT_EXPIRED_DATE != '' && tempJson2.IDENT_EXPIRED_DATE <= _sysCurrDate){
		showMsgNotification('注意：非授信第一屏证件1失效日期已小于或等于当前经办日期');
	}
	//2.2证件2失效日期先前有值,且修改后值还是小于经办日,作警告提示
	if(tempJson1.IDENT_EXPIRED_DATE1 != '' && tempJson2.IDENT_EXPIRED_DATE1 != '' && tempJson2.IDENT_EXPIRED_DATE1 <= _sysCurrDate){
		showMsgNotification('注意：非授信第一屏证件2失效日期已小于或等于当前经办日期');
	}
	return true;
};
/**
 * 获取对公非授信第一屏变更历史
 * @return firstPerModel
 */
var getFsxFirstComModel = function(){
	var identArr0 = ['IDENT_TYPE', 'IDENT_NO', 'IDENT_EXPIRED_DATE', 'IDENT_ID'];
	var identArr1 = ['IDENT_TYPE1', 'IDENT_NO1', 'IDENT_EXPIRED_DATE1', 'IDENT_ID1'];
	var identArr2 = ['IDENT_TYPE2', 'IDENT_NO2', 'IDENT_EXPIRED_DATE2', 'IDENT_ID2'];
	var agentArr = ['AGENT_ID','AGENT_NAME','AGENT_IDENT_TYPE','AGENT_IDENT_NO','AGENT_NATION_CODE','AGENT_TEL','AGENT_CUST_ID'];
	var keyflagArr = ['KEYFLAG_CUST_ID','IS_SEND_ECOMSTAT_FLAG','IS_FAX_TRANS_CUST'];
	
	var identFlag0 = false;
	var identFlag1 = false;
	var identFlag2 = false;
	var agentFlag = false;
	var keyflag = false;

    var json1 = fsxComStore.getAt(0).json;
	var json2 = fsxCombaseInfo.form.getValues(false);
	var tempCustId = custId;
	var perModel = [];
	for(var key in json2){
		var pcbhModel = {};
		var field = fsxCombaseInfo.getForm().findField(key);
		
		//排除放大镜,放大镜必须单独处理
		if(key == 'IN_CLL_TYPE' || key == 'RECOMMENDER'){
			continue;//放大镜隐藏字段不处理
		}else if(key == 'IN_CLL_TYPE_NAME' || key == 'RECOMMENDER_NAME'){
			var tempkey = field.hiddenName?field.hiddenName:key;
			var tempField = tempkey == key ? field:fsxCombaseInfo.getForm().findField(tempkey);
			if(!((json1[tempkey]==tempField.getValue()) || (null==json1[tempkey]&&null==tempField.getValue()))){
				var pcbhModel = {};
				pcbhModel.custId = tempCustId;
				pcbhModel.updateBeCont = json1[key];
				pcbhModel.updateAfCont = tempField.getValue();
				pcbhModel.updateAfContView = field.getValue();
				pcbhModel.updateItem = field.fieldLabel;
				pcbhModel.updateItemEn = field.hiddenName;
				pcbhModel.fieldType = '1';
				perModel.push(pcbhModel);
			}
			continue;
		}
		if(field.getXType() == 'combo'){
			var s = field.getValue();
			if(json1[key] != s){
				if(json1[key]!=null&&s!=""){
					pcbhModel.custId = tempCustId;
					pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json1[key],'value');
					pcbhModel.updateAfCont = s;
					pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
					pcbhModel.updateItem = field.fieldLabel;
					pcbhModel.updateItemEn = field.name;
					pcbhModel.fieldType = '1';
					perModel.push(pcbhModel);
					//记录用于判断证件类型1与证件类型2是否修改
					if(identArr0.indexOf(key) > - 1){
						identFlag0 = true;
					}
					if(identArr1.indexOf(key) > - 1){
						identFlag1 = true;
					}
					if(identArr2.indexOf(key) > - 1){
						identFlag2 = true;
					}
					if(agentArr.indexOf(key) > - 1){
						agentFlag = true;
					}
					if(keyflagArr.indexOf(key) > - 1){
						keyflag = true;
					}
					//添加贵宾卡等级及反洗钱等级
					if(key == 'VIP_CUST_GRADE'){
						addFieldFn(perModel,tempCustId,'ID_SEQUENCE.NEXTVAL'
							,fsxCombaseInfo.getForm().findField('VIP_GRADE_ID').getValue(),'VIP_GRADE_ID','1','1');
						addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'VIP_CUST_ID');
						addFieldFn(perModel,tempCustId,'04','04','CUST_GRADE_TYPE');
						addFieldFn(perModel,tempCustId,'',JsContext._userId,'VIP_LAST_UPDATE_USER','');
						addFieldFn(perModel,tempCustId,'',_sysCurrDate,'VIP_LAST_UPDATE_TM','','2');
					}
					if(key == 'RISK_CUST_GRADE'){
						addFieldFn(perModel,tempCustId,'ID_SEQUENCE.NEXTVAL'
							,fsxCombaseInfo.getForm().findField('RISK_GRADE_ID').getValue(),'RISK_GRADE_ID','1','1');
						addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'RISK_CUST_ID');
						addFieldFn(perModel,tempCustId,'01','01','CUST_GRADE_TYPE1');
						addFieldFn(perModel,tempCustId,'',JsContext._userId,'RISK_LAST_UPDATE_USER','');
						addFieldFn(perModel,tempCustId,'',_sysCurrDate,'RISK_LAST_UPDATE_TM','','2');
					}
				}
			}
		}else{
			if(!((json1[key]==json2[key]) || (null==json1[key]&&null==json2[key]))){
				var pcbhModel = {};
				pcbhModel.custId = tempCustId;
				pcbhModel.updateBeCont = json1[key];
				pcbhModel.updateAfCont = json2[key];
				pcbhModel.updateAfContView = json2[key];
				pcbhModel.updateItem = field.fieldLabel;
				pcbhModel.updateItemEn = field.name;
				pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
				perModel.push(pcbhModel);
				//记录用于判断证件类型1与证件类型2是否修改
				if(identArr0.indexOf(key) > - 1){
					identFlag0 = true;
				}
				if(identArr1.indexOf(key) > - 1){
					identFlag1 = true;
				}
				if(identArr2.indexOf(key) > - 1){
					identFlag2 = true;
				}
				if(agentArr.indexOf(key) > - 1){
					agentFlag = true;
				}
				if(keyflagArr.indexOf(key) > - 1){
					keyflag = true;
				}
				//添加主管客户经理变更主键
				if(key == 'MGR_ID'){
					addKeyFn(perModel,tempCustId,fsxCombaseInfo,'MGR_KEY_ID','主管客户经理ID');
					addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'MGR_CUST_ID');
				}
			}
		}
	}
	if(identFlag0){
		addKeyFn(perModel,tempCustId,fsxCombaseInfo,'IDENT_ID','证件1ID');
		addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'IDENT_CUST_ID');
		addFieldFn(perModel,tempCustId,custName,fsxCombaseInfo.getForm().findField('CUST_NAME').getValue(),'IDENT_CUST_NAME');
		//级联更新证件号码1与证件类型1
		addFieldFn(perModel,tempCustId,'',fsxCombaseInfo.getForm().findField('IDENT_TYPE').getValue(),'IDENT_IDENT_TYPE');
		addFieldFn(perModel,tempCustId,'',fsxCombaseInfo.getForm().findField('IDENT_NO').getValue(),'IDENT_IDENT_NO');
		addFieldFn(perModel,tempCustId,'','1','OPEN_ACC_IDENT_MODIFIED_FLAG');
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_MODIFIED_TIME','','2');
		addFieldFn(perModel,tempCustId,'','CRM','IDENT_LAST_UPDATE_SYS','');
		addFieldFn(perModel,tempCustId,'',JsContext._userId,'IDENT_LAST_UPDATE_USER','');
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_LAST_UPDATE_TM','','2');
	}
	if(identFlag1){
		addKeyFn(perModel,tempCustId,fsxCombaseInfo,'IDENT_ID1','证件2ID');
		addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'IDENT_CUST_ID1');
		addFieldFn(perModel,tempCustId,custName,fsxCombaseInfo.getForm().findField('CUST_NAME').getValue(),'IDENT_CUST_NAME1');
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_MODIFIED_TIME1','','2');
		addFieldFn(perModel,tempCustId,'','CRM','IDENT_LAST_UPDATE_SYS1','');
		addFieldFn(perModel,tempCustId,'',JsContext._userId,'IDENT_LAST_UPDATE_USER1','');
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_LAST_UPDATE_TM1','','2');
	}
	if(identFlag2){
		addKeyFn(perModel,tempCustId,fsxCombaseInfo,'IDENT_ID2','证件3ID');
		addFieldFn(perModel,tempCustId,'15X','15X','IDENT_TYPE2');
		addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'IDENT_CUST_ID2');
		addFieldFn(perModel,tempCustId,custName,fsxCombaseInfo.getForm().findField('CUST_NAME').getValue(),'IDENT_CUST_NAME2');
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_MODIFIED_TIME2','','2');
		addFieldFn(perModel,tempCustId,'','CRM','IDENT_LAST_UPDATE_SYS2','');
		addFieldFn(perModel,tempCustId,'',JsContext._userId,'IDENT_LAST_UPDATE_USER2','');
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'IDENT_LAST_UPDATE_TM2','','2');
	}
	if(agentFlag){
		addFieldFn(perModel,tempCustId,'ID_SEQUENCE.NEXTVAL'
			,fsxCombaseInfo.getForm().findField('AGENT_ID').getValue(),'AGENT_ID','1','1');
		addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'AGENT_CUST_ID');
	}
	if(keyflag){
		addFieldFn(perModel,tempCustId,tempCustId
			,fsxCombaseInfo.getForm().findField('KEYFLAG_CUST_ID').getValue(),'KEYFLAG_CUST_ID','1','1');
	}
	return perModel;
};

/**
 * 对公非授信第三屏界面字段校验
 * fsxComThreePanel
 * @return false表示校验不通过,true表示校验通过
 */
var validFsxComThirdFn = function(){
	var tempJson1 = fsxComThreeStore.getAt(0).json;
	var tempJson2 = fsxComThreePanel.getForm().getValues(false);
	
	//1.必须保证税务登记证编码和日期同时有值
	if((tempJson2.TAX_REGISTRATION_NO == '' || tempJson2.TAX_IDENT_EXPIRED_DATE == '')
		&& (tempJson2.TAX_REGISTRATION_NO != '' || tempJson2.TAX_IDENT_EXPIRED_DATE != '')){
		Ext.Msg.alert('提示','校验失败，非授信第三屏，必须保证税务登记证编码、税务登记证失效日期同时有值！');
		return false;
	}
	//2.必须保证注册资本币别、注册资本(万元)同时有值
	if((tempJson2.REGISTER_CAPITAL_CURR == '' || tempJson2.REGISTER_CAPITAL == '')
		&& (tempJson2.REGISTER_CAPITAL_CURR != '' || tempJson2.REGISTER_CAPITAL != '')){
		Ext.Msg.alert('提示','校验失败，非授信第三屏，必须保证注册资本币别、注册资本(万元)同时有值！');
		return false;
	}
	//3.必须保证年销售额币别、年销售额(万元)同时有值
	if((tempJson2.SALE_CCY == '' || tempJson2.SALE_AMT == '')
		&& (tempJson2.SALE_CCY != '' || tempJson2.SALE_AMT != '')){
		Ext.Msg.alert('提示','校验失败，非授信第三屏，必须保证年销售额币别、年销售额(万元)同时有值！');
		return false;
	}
	return true;
};
	
/**
 * 获取对公非授信第三屏变更历史
 * @return firstPerModel
 */
var getFsxThirdComModel = function(){
	var identArr0 = ['TAX_IDENT_EXPIRED_DATE', 'TAX_EFFECTIVE_DATE', 'TAX_IDENT_TYPE', 'TAX_REGISTRATION_NO','TAX_REG_ID'];
	var registerArr = ['REG_CUST_ID','REGISTER_CAPITAL_CURR','REGISTER_CAPITAL'];
	var busiinfoArr = ['BUSI_CUST_ID','SALE_CCY','SALE_AMT'];
	
	var identFlag0 = false;
	var registerFlag = false;
	var busiinfoFlag = false;
	
    var json1 = fsxComThreeStore.getAt(0).json;
	var json2 = fsxComThreePanel.form.getValues(false);
	var tempCustId = fsxComThreePanel.getForm().findField("CUST_ID").getValue();
	var perModel = [];
	for(var key in json2){
		var pcbhModel = {};
		var field = fsxComThreePanel.getForm().findField(key);
		if(field.getXType() == 'combo'){
			var s = field.getValue();
			if(json1[key] != s){
				if(json1[key]!=null&&s!=""){
					pcbhModel.custId = tempCustId;
					pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json1[key],'value');
					pcbhModel.updateAfCont = s;
					pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
					pcbhModel.updateItem = field.fieldLabel;
					pcbhModel.updateItemEn = field.name;
					pcbhModel.fieldType = '1';
					perModel.push(pcbhModel);
					//记录用于判断税务证件类型是否修改
					if(identArr0.indexOf(key) > - 1){
						identFlag0 = true;
					}
					if(registerArr.indexOf(key) > - 1){
						registerFlag = true;
					}
					if(busiinfoArr.indexOf(key) > - 1){
						busiinfoFlag = true;
					}
				}
			}
		}else{
			if(!((json1[key]==json2[key]) || (null==json1[key]&&null==json2[key]))){
				var pcbhModel = {};
				pcbhModel.custId = tempCustId;
				pcbhModel.updateBeCont = json1[key];
				pcbhModel.updateAfCont = json2[key];
				pcbhModel.updateAfContView = json2[key];
				pcbhModel.updateItem = field.fieldLabel;
				pcbhModel.updateItemEn = field.name;
				pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
				perModel.push(pcbhModel);
				//记录用于判断证件类型1与证件类型2是否修改
				if(identArr0.indexOf(key) > - 1){
					identFlag0 = true;
				}
				if(registerArr.indexOf(key) > - 1){
					registerFlag = true;
				}
				if(busiinfoArr.indexOf(key) > - 1){
					busiinfoFlag = true;
				}
			}
		}
	}
	if(identFlag0){
		addKeyFn(perModel,tempCustId,fsxComThreePanel,'TAX_REG_ID','证件1ID');
		addFieldFn(perModel,tempCustId,tempCustId,tempCustId,'TAX_CUST_ID');
		addFieldFn(perModel,tempCustId,custName,fsxCombaseInfo.getForm().findField('CUST_NAME').getValue(),'TAX_IDENT_CUST_NAME');
		addFieldFn(perModel,tempCustId,'V','V','TAX_IDENT_TYPE');
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'TAX_IDENT_MODIFIED_TIME','','2');
		addFieldFn(perModel,tempCustId,'','CRM','TAX_LAST_UPDATE_SYS','');
		addFieldFn(perModel,tempCustId,'',JsContext._userId,'TAX_LAST_UPDATE_USER','');
		addFieldFn(perModel,tempCustId,'',_sysCurrDate,'TAX_LAST_UPDATE_TM','','2');
	}
	if(registerFlag){
		addFieldFn(perModel,tempCustId,tempCustId
			,fsxComThreePanel.getForm().findField('REG_CUST_ID').getValue(),'REG_CUST_ID','1','1');
	}
	if(busiinfoFlag){
		addFieldFn(perModel,tempCustId,tempCustId
			,fsxComThreePanel.getForm().findField('BUSI_CUST_ID').getValue(),'BUSI_CUST_ID','1','1');
	}
	return perModel;
};
////////////////////////////////////////////////////////////////////////////////////////////////


var infoPanel = new Ext.Panel({
	layout : 'card',
	activeItem : 0,
	items:[allCustGrid,perAccountInfo,fsxComInfo,sxPerInfo,sxComInfo]
});

var edgeVies = {
	left : {
		width : 300,
		layout : 'form',
		title:'个人客户查询',
		items : [searchPanel]
	}
};
var customerView = [{
	title:'客户信息',
	xtype:'panel',
	items:[infoPanel]
}];

/**
 * 
 * helin
 * 添加隐藏主键字段
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} formpanel form面板
 * @param {} key 字段
 * @param {} fieldLabel 字段label
 */
var addKeyFn = function(perModel,_tempCustId,formpanel,key,fieldLabel){
	var field = formpanel.getForm().findField(key);
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = field.getValue();
	pcbhModel.updateAfCont = field.getValue();
	pcbhModel.updateAfContView = field.getValue();
	pcbhModel.updateItem = fieldLabel;
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = '1';
	pcbhModel.updateTableId = '1';
	perModel.push(pcbhModel);
};
/**
 * helin
 * 添加隐藏字段
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} beforeValue 修改前值
 * @param {} afterValue 修改后值
 * @param {} key 字段
 * @param {} updateTableId 是否主键字段:1是，''否
 * @param {} fieldType 字段类型:1文本框，'2'日期框
 */
var addFieldFn = function(perModel,_tempCustId,beforeValue,afterValue,key,updateTableId,fieldType){
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = beforeValue;
	pcbhModel.updateAfCont = afterValue;
	pcbhModel.updateAfContView = afterValue;
	pcbhModel.updateItem = '';
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = fieldType == "2"?"2":"1";
	pcbhModel.updateTableId = updateTableId == "1"?"1":"";
	perModel.push(pcbhModel);
};
/**
 * helin
 * @param store 要遍历的store
 * @param keyField 要比较的字段
 * @param keyValue 要比较的字段的值
 * @param valueField 要获取值的字段
 */
var getStoreFieldValue = function(store,keyField,keyValue,valueField){
	for(var i=0;i<store.getCount();i++){
		if(store.getAt(i).data[keyField] == keyValue){
			return store.getAt(i).data[valueField];
		}
	}
	return keyValue;
};


////////////////////////////////////////////字段级数据权限控制区域function
/**
 * @description 
 * 
 * 设置禁用启用起否示例
 * setFormDisabled(xxxPanel.getForm(),true,[
 *	'EN_NAME','CUST_ID','INOUT_FLAG','VIP_FLAG','PER_CUST_TYPE'
 * ]);
 * 设置可编辑字段
 * @param form formPanel.getForm()  
 * @param arr 数组
 * @param disable true禁用,false表示不禁用
 */
var setFormDisabled = function(form,disable,arr){
	for(var i=0;i<arr.length;i++){
		var tempfield = form.findField(arr[i]);
		tempfield.setDisabled(disable);
		if(disable){
			tempfield.addClass('x-readOnly');
		}else{
			tempfield.removeClass('x-readOnly');
		}
	}
};
/**
 * 右下角显示提示信息
 * @param {} msg 提示信息
 * @param {} error
 * @param {} hideDelay
 */
var showMsgNotification = function(msg, hideDelay, error) {
	new Ext.ux.Notification( {
		iconCls : 'ico-g-51',
		title : error ? '<font color=red>错误</font>' : '<font color=red>提示</font>',
		html : "<br><font style='font-weight:bold;' color=red text-align=left>" + msg
				+ '</font><br><br>',
		autoDestroy : true,
		plain : false,
		shadow : false,
		draggable : false,
		hideDelay : hideDelay || (error ? 30000 : 30000),
		width : 400
	}).show(document);
};