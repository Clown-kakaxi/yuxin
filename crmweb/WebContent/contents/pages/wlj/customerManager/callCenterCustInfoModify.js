/**
 * @description 
 * @author 
 * @since 
 */
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/resource/ext3/ux/Ext.ux.Notification.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.BusiType.js'		//行业树放大镜
	,'/contents/pages/wlj/customerManager/queryAllCustomer/allLookup.js' //所有数据字典定义
	,'/contents/pages/wlj/customerManager/queryAllCustomer/updateHisCust.js'
	,'/contents/pages/wlj/customerManager/callCenterCustInfo.js'
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
		{xtype : 'textfield',name: 'ACC_NO',fieldLabel:'账号',anchor : '90%'},
		{xtype : 'textfield',name: 'CONTMETH_INFO',fieldLabel:'联系电话',anchor : '90%'}
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
			
		}
	}]
});

//全行客户查询store
var allCuststore =  new Ext.data.Store({
	restful:true,
	proxy : new Ext.data.HttpProxy({
		url:basepath+'/customerQueryAllNew.json?FLAG=cc',
		method:'GET'
	}),
	reader: new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root:'json.data'
	}, [
		{name: 'CORE_NO'},
		{name: 'CUST_ID'},
		{name: 'CUST_NAME'},
		{name: 'IDENT_TYPE_ORA'},
		{name: 'IDENT_TYPE'},
		{name: 'IDENT_NO'},
		{name: 'CONTMETH_INFO'}
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
	
};
/**
 * 全行客户查询执行fn
 * @param formPanel 查询panel
 * @param roleCond 查询角色 1 3选1,2 4选1
 * @type 
 */
var searchPanelFn = function(formPanel){
	var baseForm = formPanel.getForm();
	if(baseForm.findField("CONTMETH_INFO").getValue() == ''){
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
    	url : basepath + '/callCenter!isLockCust.json',
        method : 'GET',
        params : {custId: custId},
        success : function(response){
        	var ret = Ext.decode(response.responseText);
        	var isLock = false;
        	if(ret.json.data.length>0 && ret.json.data[0].AUTHOR != ''){
        		isLock = true;
        		showMsgNotification('您暂无修改权限，客户记录已被操作员'+ret.json.data[0].AUTHOR+'锁定！',300000);
        	}
        	setAllCustIsUpdate(isLock);
        },
        failure : function(response){
			var isLock = true;
        	setAllCustIsUpdate(isLock);
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
var setAllCustIsUpdate = function(isLock){
	var tempFsxFlag = true;//是否有权限进行修改非授信
	//true锁定,false未锁定
	if(isLock){
		tempFsxFlag = false;
	}
	Ext.getCmp('per_fsx_all').setVisible(tempFsxFlag);//对私非授信all
	Ext.getCmp('per_fsx_2-11').setVisible(false);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-12').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-13').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-21').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-22').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-23').setVisible(tempFsxFlag);//对私非授信第二屏
	Ext.getCmp('per_fsx_2-31').setVisible(false);//对私非授信第二屏
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
var baseStore = new Ext.data.Store({
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
		'CORE_NO',
		'STAFFIN','JOINT_CUST_TYPE','SWIFT','REMARK',
		'RECOMMENDER','RECOMMENDER_NAME','ORG_SUB_TYPE','EN_NAME','INOUT_FLAG','AR_CUST_FLAG','RISK_NATION_CODE','PER_CUST_TYPE','GENDER','BIRTHDAY','CITIZENSHIP','AREA_CODE','USA_TAX_IDEN_NO','BIRTHLOCALE','EMAIL','VIP_FLAG',
		'KEYFLAG_CUST_ID','IS_SEND_ECOMSTAT_FLAG','USA_TAX_FLAG','IS_FAX_TRANS_CUST',
		'BASIC_ACCT_BANK_NO','BASIC_ACCT_BANK_NAME','BASIC_ACCT_OPEN_DATE',
		'MGR_KEY_ID','MGR_ID','MGR_NAME','EFFECT_DATE',
		'VIP_GRADE_ID','VIP_CUST_GRADE','RISK_GRADE_ID','RISK_CUST_GRADE',
		'AGENT_ID','AGENT_NAME','AGENT_IDENT_TYPE','AGENT_IDENT_NO','AGENT_NATION_CODE','AGENT_TEL','AGENT_CUST_ID','IF_SIGN_SERVICE','SERVICE_CUST_ID'
	])
});
var allCustTbar = new Ext.Toolbar({
    items: [
    	{
        text: '修改',
        handler: function(){
        	var selectLength = allCustGrid.getSelectionModel().getSelections().length;
            var selectRecord = allCustGrid.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条记录!');
                return false;
            }
            custId = selectRecord.data.CUST_ID;
			custName = selectRecord.data.CUST_NAME;
			custType = selectRecord.data.CUST_TYPE;
			var tempCustOrg = selectRecord.data.INSTITUTION_CODE;//客户归属机构
			var tempActOrg = selectRecord.data.ACT_ORG_IDS;//账号所属机构
			var tempMgr = selectRecord.data.MGR_ID;//所属客户经理
			beforeSetAllCustIsUpdate(custType,tempCustOrg+','+tempActOrg,tempMgr);
			if(baseInfo.getForm().getEl()){
				baseInfo.getForm().getEl().dom.reset();
			}
			baseStore.load({
				params : {
					custId : custId
				},
				callback:function(){
					if(baseStore.getCount()!=0){
						baseInfo.getForm().loadRecord(baseStore.getAt(0));
					}
				}
			})
			infoPanel.layout.setActiveItem(1);
			window.queryFsxPerSecFn();
			
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
    {dataIndex:'IDENT_NO',header:'证件号码',width : 120,sortable : true}
   
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
	title: 'CALLCENTER客户查询',
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
var baseInfo = new Ext.form.FormPanel({
	 frame : true,
	 autoScroll : true,
	 labelWidth : 140,
	 buttonAlign : "center",
	 items:[{
		layout : 'column',
	    items:[{
	        	columnWidth : .5,
				layout : 'form',
				items :[
				    {xtype: 'textfield',name : 'CORE_NO', fieldLabel : '核心客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%'},
					{xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%',hidden:true},
					{xtype : 'combo',name : 'IDENT_TYPE',hiddenName : 'IDENT_TYPE',fieldLabel : '证件类型1',store : indent00Store,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'},
					{xtype : 'combo',name : 'IDENT_TYPE1',hiddenName : 'IDENT_TYPE1',fieldLabel : '证件类型2',store : indent01Store,resizable : true,valueField : 'key',displayField : 'value',
							mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
					
		       ]
	      	},{
	      		columnWidth : .5,
				layout : 'form',
				items :[
				    {xtype : 'textfield',fieldLabel : '中文名',name : 'CUST_NAME',anchor : '90%',readOnly:true,cls:'x-readOnly'},
				    {xtype : 'textfield',fieldLabel : '证件号码1',name : 'IDENT_NO',anchor : '90%',maxLength:100,readOnly:true,cls:'x-readOnly'},
				    {xtype : 'textfield',fieldLabel : '证件号码2',name : 'IDENT_NO1',anchor : '90%',maxLength:100,readOnly:true,cls:'x-readOnly'}
				    //{xtype : 'textfield',fieldLabel : '电子邮件地址',name : 'EMAIL',vtype:'email',anchor : '90%',maxLength:40}
					
		       ]
	      	}]
	}]
});
var getFsxFirstPerModel = function(){
    var json1 = baseStore.getAt(0).json;
	var json2 = baseInfo.getForm().getValues(false);
	var tempCustId = custId;
	var perModel = [];
	for(var key in json2){
		var pcbhModel = {};
		var field = baseInfo.getForm().findField(key);

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
					
				}
			}
		}else{
			debugger;
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
			}
		}
	}
	return perModel;
};
var infoList = new Ext.Panel({
	frame : true,
	title : '客户信息',
	autoScroll : true,
	buttonAlign : "center",
	items : [{
		xtype : 'fieldset',
		items : [baseInfo]
	},{
		xtype : 'fieldset',
		title : '地址信息',
		items : [addrGridPanel]
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
		id : 'per_fsx_all',
		text:'提交',
		hidden:true,
		handler:function(){
			Ext.Msg.wait('正在提交申请中...','提示');
			if (!baseInfo.getForm().isValid()) {
				Ext.Msg.alert('提示','校验失败，非授信第一屏输入有误或存在漏输入项,请检查输入项');
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
		    	url : basepath + '/callCenter!commitAll.json',
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
		        	Ext.Msg.hide();
		        	Ext.Msg.alert('提示', '已提交!');
		        	infoPanel.layout.setActiveItem(0);
					fsxPerInfo.setActiveTab(0);
		        	 
		        },
		        failure : function(response) {
		            Ext.Msg.alert('提示', '操作失败!');
		        }
		   });
		}
	}]
});
var infoPanel = new Ext.Panel({
	layout : 'card',
	activeItem : 0,
	items:[allCustGrid,infoList]
});
var edgeVies = {
		left : {
			width : 300,
			layout : 'form',
			title:'CALLCENTER客户查询',
			items : [searchPanel]
		}
	};
var customerView = [{
	title:'客户信息',
	xtype:'panel',
	items:[infoPanel]
}];

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
