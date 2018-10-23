/**
 * @description 法金客户经理移交页面
 * @author xuhoufei
 * @since 2014-07-08
 */
imports([
    '/contents/pages/common/Com.yucheng.crm.common.managerSearchfj.js',
    '/contents/pages/common/LovCombo.js',
    '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'//导出
]);
	
var url = basepath+'/changeCustManager.json?type=mgr';

var createView = false;
var editView = false;
var detailView = false;

var autoLoadGrid = false;
var json = null;//保存选中的客户
var applyType = '';//移交类型 1：对私区域内移交，2：对私跨区域移交，3：对公支行移交，4：对公区域内移交
				 //5：对公跨区域移交，6：对私主管直接移交，7：对公主管直接移交（主办），8：对公主管直接移交（协办）
var lookupTypes = [
	'MAINTAIN_TYPE',
	'XD000080'
];

var fields = [
	{name:'ID',text:'ID',hidden:true},
	{name:'CUST_ID' ,text:'客户编号',dataType:'string',searchField:true},
	{name:'CUST_NAME',text:'客户名称',dataType:'string',searchField:true},
	{name:'CUST_TYPE',text:'客户类型',translateType:'XD000080',searchField:true,cAllowBlank:false},
	{name:'MGR_ID',hidden:true},
	{name:'MGR_NAME',text:'客户经理',hiddenName:'MGR_ID',dataType:'mangerchoose',searchField : true},
	{name:'MAIN_TYPE',text:'主协办类型',translateType:'MAINTAIN_TYPE',searchField:true},
	{name:'INSTITUTION',hidden:true},
	{name:'INSTITUTION_NAME',text:'机构名称',dataType:'string'},
	{name:'ASSIGN_USERNAME',text:'分配/移交人',dataType:'string'},
	{name:'ASSIGN_DATE',text:'分配/移交日期',dataType:'date'}
];

var extraParams = {
    impFlag : "0"
};

var conditionButtons = {
    impSearch : {
        text : '导入查询',
        hidden : true,
        cls:'simple-btn',
        overCls:'simple-btn-hover',
        fn : function(){
            extraParams.impFlag = "1";
            this.searchHandler();
        }
    }
};

Ext.apply(conditionButtons,WLJUTIL.conditionButtons);

WLJUTIL.conditionButtons = conditionButtons;

delete conditionButtons;

WLJUTIL.conditionButtons.search.fn = function(){
    extraParams.impFlag = "0";
    this.searchHandler();
};

WLJUTIL.BUTTON_TYPE.IMPSEARCH = 'impSearch';

var beforeinit = function(app){
    var SearchGrid =  Wlj.frame.functions.app.widgets.SearchGrid.prototype;
    SearchGrid.onBeforeLoad = SearchGrid.onBeforeLoad.createInterceptor(function(store, option){
       var _this = store.resultContainer;
        _this._APP.disableConditionButton(WLJUTIL.BUTTON_TYPE.IMPSEARCH);
    });
    SearchGrid.onExceptionLoad = SearchGrid.onExceptionLoad.createInterceptor(function(store, records, option){
       var _this = store.resultContainer;
        _this._APP.enableConditionButton(WLJUTIL.BUTTON_TYPE.IMPSEARCH);
    });
    SearchGrid.onDataLoad = SearchGrid.onDataLoad.createInterceptor(function(store, records, option){
       var _this = store.resultContainer;
        _this._APP.enableConditionButton(WLJUTIL.BUTTON_TYPE.IMPSEARCH);
    });
}
/**
 * 导入查询管理器
 */
var importSearchManager = new Com.yucheng.crm.common.ImportSearchManager({
    templateFile: 'fajinImport.xlsx',
    tradeCode   : 'importCustTransToManager',
    url         : basepath + '/custTrans.json',
    params      : {},
    fields       : [
        {text : '核心客户号' ,name   : 'CORE_NO'}, 
        {text : '校验信息' ,name : 'IMP_MSG'}
    ],
    listeners   : {
        beforeSearch : function(){
            var me = this;
            Ext.Msg.wait("系统消息","正在处理，请稍后。。。");
            //发送请求，查询当前导入 查询临时表里数据的客户类型，然后设置查询条件的值
            Ext.Ajax.request({
                url : basepath + '/changeCustManager!searchCustId.json',
                success : function(response){
                    Ext.Msg.hide();
                    var temp = Ext.decode(response.responseText);
                    //获取查询条件客户类型，并设置值
                    getConditionField('CUST_TYPE').setValue(temp.CUST_TYPE);
                    extraParams.impFlag = "1";//设置导入查询标志为1，即导入查询
                    _app.searchDomain.searchHandler();//执行查询
                }
            });
            return true;
        }
    }
});
var tbar = [{
	text:'个人客户移交',
	id:'trans1',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		applyType = '1';
		json={'transedData':[]};
		for (var i = 0; i < getAllSelects().length; i++) {
			var records = getAllSelects()[i].data;
			var temp = {};
			temp.RECORD_ID = records.ID;
			temp.CUST_ID = records.CUST_ID;
			temp.CUST_NAME = records.CUST_NAME;
			temp.MGR_ID = records.MGR_ID;
			temp.MGR_NAME = records.MGR_NAME;
			temp.MAIN_TYPE = records.MAIN_TYPE;
			temp.INSTITUTION = records.INSTITUTION;
			temp.INSTITUTION_NAME = records.INSTITUTION_NAME;
			json.transedData.push(temp);
		}
		transedStore.loadData(json);	
		transForm.form.reset();
		transForm.getForm().findField('type').setValue('3');
		showCustomerViewByIndex(0);
	}
},{
	text:'分行/区域移交',
	id:'trans2',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		applyType = '2';
		json={'transedData':[]};
		var records = getSelectedData().data;
		var temp = {};
		temp.RECORD_ID = records.ID;
		temp.CUST_ID = records.CUST_ID;
		temp.CUST_NAME = records.CUST_NAME;
		temp.MGR_ID = records.MGR_ID;
		temp.MGR_NAME = records.MGR_NAME;
		temp.MAIN_TYPE = records.MAIN_TYPE;
		temp.INSTITUTION = records.INSTITUTION;
		temp.INSTITUTION_NAME = records.INSTITUTION_NAME;
		json.transedData.push(temp);
		transedStore.loadData(json);	
		transForm.form.reset();
		transForm.getForm().findField('type').setValue('4');
		showCustomerViewByIndex(0);
	}
},{
//	text:'支行内移交',
	text:'行内移交',
	id:'trans3',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		applyType = '3';
		json={'transedData':[]};
		for (var i = 0; i < getAllSelects().length; i++) {
			var records = getAllSelects()[i].data;
			if(records.MAIN_TYPE == '2'){
				Ext.Msg.alert('提示','只能选择您作为[主办]关系的记录！');
				return false;
			}
			var temp = {};
			temp.RECORD_ID = records.ID;
			temp.CUST_ID = records.CUST_ID;
			temp.CUST_NAME = records.CUST_NAME;
			temp.MGR_ID = records.MGR_ID;
			temp.MGR_NAME = records.MGR_NAME;
			temp.MAIN_TYPE = records.MAIN_TYPE;
			temp.INSTITUTION = records.INSTITUTION;
			temp.INSTITUTION_NAME = records.INSTITUTION_NAME;
			json.transedData.push(temp);
		}
		transedStore.loadData(json);	
		transForm1.form.reset();
		transForm1.getForm().findField('type').setValue('1');
		
		Ext.MessageBox.confirm("系统消息", "请确认所需要移转的客户无信贷系统审批流程，且信贷系统客户归属与CRM一致！", function(button){
            if (button == "yes") {
                showCustomerViewByIndex(1);
            }
        }, this);
	}
},{
//	text:'分行/区域内移交',
	text:'区域内移交',
	id:'trans4',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		applyType = '4';
		json={'transedData':[]};
		for (var i = 0; i < getAllSelects().length; i++) {
			var records = getAllSelects()[i].data;
			if(records.MAIN_TYPE == '2'){
				Ext.Msg.alert('提示','只能选择您作为[主办]关系的记录！');
				return false;
			}
			var temp = {};
			temp.RECORD_ID = records.ID;
			temp.CUST_ID = records.CUST_ID;
			temp.CUST_NAME = records.CUST_NAME;
			temp.MGR_ID = records.MGR_ID;
			temp.MGR_NAME = records.MGR_NAME;
			temp.MAIN_TYPE = records.MAIN_TYPE;
			temp.INSTITUTION = records.INSTITUTION;
			temp.INSTITUTION_NAME = records.INSTITUTION_NAME;
			json.transedData.push(temp);
		}
		transedStore.loadData(json);	
		transForm1.form.reset();
		//transForm1.getForm().findField('type').setValue('2');
		transForm1.getForm().findField('type').setValue('3');
		Ext.MessageBox.confirm("系统消息", "请确认所需要移转的客户无信贷系统审批流程，且信贷系统客户归属与CRM一致！", function(button){
            if (button == "yes") {
                showCustomerViewByIndex(1);
            }
        }, this);
	}
},{
//	text:'跨分行/区域移交',
	text:'跨区域移交',
	id:'trans5',
	handler:function(){
//		debugger;
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}
		applyType = '5';
		json={'transedData':[]};
		for (var i = 0; i < getAllSelects().length; i++) {
			var records = getAllSelects()[i].data;
			if(records.MAIN_TYPE == '2'){
				Ext.Msg.alert('提示','只能选择您作为[主办]关系的记录！');
				return false;
			}
			var temp = {};
			temp.RECORD_ID = records.ID;
			temp.CUST_ID = records.CUST_ID;
			temp.CUST_NAME = records.CUST_NAME;
			temp.MGR_ID = records.MGR_ID;
			temp.MGR_NAME = records.MGR_NAME;
			temp.MAIN_TYPE = records.MAIN_TYPE;
			temp.INSTITUTION = records.INSTITUTION;
			temp.INSTITUTION_NAME = records.INSTITUTION_NAME;
			json.transedData.push(temp);
		}
		transedStore.loadData(json);
		transForm1.form.reset();
		transForm1.getForm().findField('type').setValue('4');
		Ext.MessageBox.confirm("系统消息", "请确认所需要移转的客户无信贷系统审批流程，且信贷系统客户归属与CRM一致！", function(button){
            if (button == "yes") {
                showCustomerViewByIndex(1);
            }
        }, this);
	}
},{
	text : '模板下载', 
	hidden:JsContext.checkGrant('custTransToManager_download'),
	handler : function(){
		importSearchManager.downloadTemplate();
	}
},{
	text : '导入查询',
	hidden:JsContext.checkGrant('custTransToManager_import'),
	handler : function(){
		importSearchManager.importSearch();
	}
}];

var handStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
        field:'key',
        direction:'ASC'
    },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=HAND_KIND'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var transReasonStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
        field:'key',
        direction:'ASC'
    },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=TRANS_REASON_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});
var transContentStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
        field:'key',
        direction:'ASC'
    },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=TRANS_CONTENT_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});


var mainStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=MAINTAIN_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var transForm = new Ext.FormPanel({
	frame : true,
	autoScroll: true,
	height: 160,
	region : 'north',
	items : [ {
		layout : 'column',
		items : [{
			layout : 'form',
			columnWidth : .5,
			labelWidth:100,
			items : [ new Com.yucheng.crm.common.managerSearch({
				xtype:'mangerchoose',
				fieldLabel : '<font color="red">*</font>接收客户经理', 
				labelStyle: 'text-align:right;',
				name : 'tMgrName',
				hiddenName:'tMgrId',
				allowBlank:false,
				anchor : '95%',
				callback:function(b){
					transForm.getForm().findField("tOrgId").setValue(b[0].data.orgId);
					transForm.getForm().findField("tOrgName").setValue(b[0].data.orgName);
				}
				}),
				{xtype:'numberfield',name:'oldAum',fieldLabel : '<font color="red">*</font>原AUM金额',labelStyle : 'text-align:right;',anchor : '95%',allowBlank:false},
				{xtype:'numberfield',name:'oldCredit',fieldLabel : '<font color="red">*</font>原授信金额',labelStyle : 'text-align:right;',anchor : '95%',allowBlank:false},
				{xtype:'lovcombo',name : 'transContent',hiddenName:'transContent',fieldLabel: '<font color="red">*</font>转移内容',labelStyle: 'text-align:right;',
             		forceSelection : true,triggerAction:'all',mode:'local',store:transContentStore,valueField:'key',displayField:'value',allowBlank:false,emptyText:'请选择',anchor : '95%',resizable:true},
            	{xtype:'combo',name : 'handKind',hiddenName:'handKind',fieldLabel: '<font color="red">*</font>转移原因',labelStyle: 'text-align:right;',
             		forceSelection : true,triggerAction:'all',mode:'local',store:transReasonStore,valueField:'key',displayField:'value',allowBlank:false,emptyText:'请选择',anchor : '95%',resizable:true}
				
			 ]
		},{
			columnWidth : .5,
			layout : 'form',
			labelWidth:100,
			items : [
				{xtype:'textfield',name:'tOrgName',fieldLabel : '<font color="red">*</font>接收机构',labelStyle : 'text-align:right;',readOnly:true,anchor : '95%'},
				{xtype:'hidden',name:'type',fieldLabel : '控制值',labelStyle : 'text-align:right;',anchor : '95%'},
				{xtype:'hidden',name:'tOrgId',fieldLabel : '<font color="red">*</font>接收机构',labelStyle : 'text-align:right;',anchor : '95%'},
				{xtype:'numberfield',name:'newAum',fieldLabel : '<font color="red">*</font>新AUM金额',labelStyle : 'text-align:right;',anchor : '95%',allowBlank:false},
				{xtype:'numberfield',name:'newCredit',fieldLabel : '<font color="red">*</font>新授信金额',labelStyle : 'text-align:right;',anchor : '95%',allowBlank:false},
				{xtype:'textfield',name:'transOther',fieldLabel : '转移内容(其它)',labelStyle : 'text-align:right;',anchor : '95%'},
				{name : 'workInterfixDt',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '<font color="red">*</font>工作交接日期',labelStyle : 'text-align:right;',allowBlank:false,anchor : '95%'}
			]
		},{
			layout : 'form',
			columnWidth : 1,
			labelWidth:100,
			items : [
				{xtype : 'textarea',name : 'handOverReason',fieldLabel : '其它原因',labelStyle : 'text-align:right;',anchor : '97.5%'}
			]
		}]
	}]
});

var transedRecord = Ext.data.Record.create([
                                            {name: 'recordId',mapping : 'RECORD_ID'},
                                            {name: 'custId',mapping : 'CUST_ID'},
                                            {name:'custName',mapping:'CUST_NAME'},
                                            {name: 'mgrId',mapping : 'MGR_ID'},
                                            {name: 'mgrName',mapping : 'MGR_NAME'},
                                            {name: 'mainType',mapping : 'MAIN_TYPE'},
                                            {name: 'institution',mapping:'INSTITUTION'},
                                            {name: 'institutionName',mapping:'INSTITUTION_NAME'},
                                            {name: 'mainTypeNew',mapping:'MAIN_TYPE_NEW'}
                                        ]);
 var transedStore = new Ext.data.Store({
        reader: new Ext.data.JsonReader({
           root : 'transedData'
       },transedRecord )
});
 var num = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
                                         
var cm = new Ext.grid.ColumnModel([
 	num,	// 定义列模型
    {header : '', dataIndex : 'recordId',hidden : true}, 
    {header:'客户编号',dataIndex:'custId',sortable : true,width : 100},
	{header:'客户名称',dataIndex:'custName',sortable : true,width : 100},
	{header:'原单位RM',dataIndex:'mgrName',sortable : true,width : 100},
	{header:'AO CODE',dataIndex:'mgrId',sortable : true,width : 100},
	{header:'原主协办关系',dataIndex:'mainType',sortable : true,width : 100,hidden:true,renderer:function(v){
		if(v == '1') return '主办';else if(v == '2') return '协办'; else return '';
	}},
	{header:'',dataIndex:'institution',sortable : true,width : 100,hidden:true},
	{header:'原营业单位',dataIndex:'institutionName',sortable : true,width : 100}
]);
var custGrid = new Ext.grid.GridPanel({
	title : '移交客户',
	autoScroll : true,
	height:130,
	region : 'center',
    store: transedStore,
	stripeRows : true, // 斑马线
	cm : cm,
	viewConfig : {}
});
var pTrans = new Ext.Panel({
	autoScroll : true,
	buttonAlign : "center",
	layout:'border',
	items:[transForm,custGrid],
	buttons:[{
		text:'提交',
		handler:function(){
			if (!transForm.getForm().isValid()) {
                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                return false;
            }
			var json1 = {'RECORD_ID':[]};
			var json2 = {'CUST_ID':[]};
			var json3 = {'CUST_NAME':[]};
			var json4 = {'MGR_ID':[]};
			var json5 = {'MGR_NAME':[]};
			var json6 = {'MAIN_TYPE':[]};
			var json7 = {'INSTITUTION':[]};
			var json8 = {'INSTITUTION_NAME':[]};
		
		 
			 for(var i=0;i<transedStore.getCount();i++){
	             var temp=transedStore.getAt(i);
                 json1.RECORD_ID.push(temp.data.recordId);
                 json2.CUST_ID.push(temp.data.custId);
                 json3.CUST_NAME.push(temp.data.custName);
                 json4.MGR_ID.push(temp.data.mgrId);
                 json5.MGR_NAME.push(temp.data.mgrName);
				 json6.MAIN_TYPE.push(temp.data.mainType);
				 json7.INSTITUTION.push(temp.data.institution);
				 json8.INSTITUTION_NAME.push(temp.data.institutionName);
	         }
			Ext.Msg.wait('正在保存，请稍后......','系统提示');
			Ext.Ajax.request({
	             url : basepath + '/changeCustManager!MgrTrans.json',
	             method : 'GET',
	             waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				 form:transForm.getForm().id,
	             params:{
	            	 RECORD_IDs:Ext.encode(json1),
					 CUST_IDs:Ext.encode(json2),
					 CUST_NAMEs:Ext.encode(json3),
					 MGR_IDs:Ext.encode(json4),
					 MGR_NAMEs:Ext.encode(json5),
					 MAIN_TYPEs:Ext.encode(json6),
					 INSTITUTIONs:Ext.encode(json7),
					 INSTITUTION_NAMEs:Ext.encode(json8),
					 tMgr:transForm.form.findField('tMgrId').getValue(),
					 type:applyType
	             },
	             success : function(response) {
//	                 Ext.Msg.alert('提示', '操作成功');
//	                 reloadCurrentData();
            	 	var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
	             },
	             failure : function(response) {
	                 Ext.Msg.alert('提示', '操作失败' );
	             }
	         });
		}
	}]
});


var transForm1 = new Ext.FormPanel({
	frame : true,
	height: 140,
	region : 'north',
	items : [ {
		layout : 'column',
		items : [{
			layout : 'form',
			columnWidth : .5,
			labelWidth:100,
			items : [ new Com.yucheng.crm.common.managerSearch({ 
				xtype:'mangerchoose',
				fieldLabel : '<font color="red">*</font>接收客户经理', 
				labelStyle: 'text-align:right;',
				name : 'tMgrName',
				hiddenName:'tMgrId',
				allowBlank:false,
				anchor : '95%',
				callback:function(b){
					transForm1.getForm().findField("tOrgId").setValue(b[0].data.orgId);
					transForm1.getForm().findField("tOrgName").setValue(b[0].data.orgName);
				}
				}),{
			    name : 'workInterfixDt',
			    xtype : 'datefield',
			    format : 'Y-m-d',
			    fieldLabel : '<font color="red">*</font>客户移交生效日',
			    labelStyle : 'text-align:right;',
			    allowBlank:false,
			    anchor : '95%'
			} ]
		},{
			columnWidth : .5,
			layout : 'form',
			labelWidth:100,
			items : [{xtype:'textfield',
					name:'tOrgName',
					fieldLabel : '<font color="red">*</font>接收机构',
				    labelStyle : 'text-align:right;',
				    readOnly:true,
				    anchor : '95%'
			},{xtype:'hidden',
				name:'type',
				fieldLabel : '控制值',
			    labelStyle : 'text-align:right;',
			    anchor : '95%'
		},{xtype:'hidden',
				name:'tOrgId',
				fieldLabel : '<font color="red">*</font>接收机构',
			    labelStyle : 'text-align:right;',
			    anchor : '95%'
		},{	
             fieldLabel: '<font color="red">*</font>客户移交类别',
             name : 'handKind',
             hiddenName:'handKind',
             forceSelection : true,
             xtype:'combo',
             labelStyle: 'text-align:right;',
             triggerAction:'all',
             mode:'local',
             store:handStore,
             valueField:'key',
             displayField:'value',
             allowBlank:false,
             emptyText:'请选择',
             anchor : '95%'
         }]
		},{
			layout : 'form',
			columnWidth : 1,
			labelWidth:100,
			items : [ {
			    name : 'handOverReason',
			    xtype : 'textarea',
			    fieldLabel : '<font color="red">*</font>考核指标移转',
			    labelStyle : 'text-align:right;',
			    allowBlank:false,
			    anchor : '97.5%'
			}]
		}]
	}]
});

 var num1 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
                                         
 var cm1 = new Ext.grid.ColumnModel([num1,	// 定义列模型
                                    {header : '', dataIndex : 'recordId',hidden : true}, 
                                    {header:'客户编号',dataIndex:'custId',sortable : true,width : 100},
									{header:'客户名称',dataIndex:'custName',sortable : true,width : 100},
									{header:'',dataIndex:'mgrId',sortable : true,width : 100,hidden:true},
									{header:'原客户经理',dataIndex:'mgrName',sortable : true,width : 100},
									{header:'原主协办关系',dataIndex:'mainType',hidden : true,sortable : true,width : 100,renderer:function(v){
										if(v == '1') return '主办';else if(v == '2') return '协办'; else return '';
									}},
									{header:'',dataIndex:'institution',sortable : true,width : 100,hidden:true},
									{header:'原归属机构',dataIndex:'institutionName',sortable : true,width : 100}
//									{header:'新主协办关系',dataIndex:'mainTypeNew',width:100,editor :{
//                      		        	xtype:'combo',
//                      		        	store : mainStore,
//                      		        	mode : 'local',
//                      		        	triggerAction : 'all',
//                      		        	valueField : 'key',
//                      		        	displayField : 'value',
//                      		        	forceSelection:true,
//                      					resizable:true,
//                      					typeAhead : true,
//                      					emptyText : '请选择',
//                      		        	listeners:{
//                      		        	select:function(){
//                      		        		var valuefind = this.value;
//                      		        		this.fireEvent('blur',this);
//                      		        	}
//                      		        	}},sortable : false,
//                      		        	renderer:function(val){
//                      		        	if(val!=''){
//                      		        		var stolength = mainStore.data.items;
//                      		        		var i=0;
//                      		        		for(i=0;i< stolength.length;i++){
//                      		        			if(stolength[i].data.key==val){
//                      		        					return stolength[i].data.value;
//                      		        			}
//                      		        		}
//                      		        	}
//                      		        return val;	
//                      		        }}
                                    ]);
var custGrid1 = new Ext.grid.EditorGridPanel({
	title : '移交客户',
	autoScroll : true,
	height:150,
	region : 'center',
    store: transedStore,
	stripeRows : true, // 斑马线
	cm : cm1,
	viewConfig : {}
});
var cTrans = new Ext.Panel({
	autoScroll : true,
	buttonAlign : "center",
	layout:'border',
	items:[transForm1,custGrid1],
	buttons:[{text:'提交',
		handler:function(){
			if (!transForm1.getForm().isValid()) {
                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                return false;
            }
			var json1 = {'RECORD_ID':[]};
			var json2 = {'CUST_ID':[]};
			var json3 = {'CUST_NAME':[]};
			var json4 = {'MGR_ID':[]};
			var json5 = {'MGR_NAME':[]};
			var json6 = {'MAIN_TYPE':[]};
			var json7 = {'INSTITUTION':[]};
			var json8 = {'INSTITUTION_NAME':[]};
			var json9 = {'MAIN_TYPE_NEW':[]};
		
		 
			 for(var i=0;i<transedStore.getCount();i++){
	             var temp=transedStore.getAt(i);
	             temp.data.mainTypeNew = '1';
	             if(temp.data.mainTypeNew == ''||temp.data.mainTypeNew == null ||temp.data.mainTypeNew == undefined){
	            	 Ext.MessageBox.alert('提示','请填写新[主协办关系]');
	                 return false;
	             }
	                 json1.RECORD_ID.push(temp.data.recordId);
	                 json2.CUST_ID.push(temp.data.custId);
	                 json3.CUST_NAME.push(temp.data.custName);
	                 json4.MGR_ID.push(temp.data.mgrId);
	                 json5.MGR_NAME.push(temp.data.mgrName);
					 json6.MAIN_TYPE.push(temp.data.mainType);
					 json7.INSTITUTION.push(temp.data.institution);
					 json8.INSTITUTION_NAME.push(temp.data.institutionName);
					 json9.MAIN_TYPE_NEW.push(temp.data.mainTypeNew);
	         }
			Ext.Msg.wait('正在保存，请稍后......','系统提示');
			Ext.Ajax.request({
	             url : basepath + '/changeCustManager!changeMgrTrans.json',
	             method : 'GET',
	             waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				 form:transForm1.getForm().id,
	             params:{
	            	 RECORD_IDs:Ext.encode(json1),
					 CUST_IDs:Ext.encode(json2),
					 CUST_NAMEs:Ext.encode(json3),
					 MGR_IDs:Ext.encode(json4),
					 MGR_NAMEs:Ext.encode(json5),
					 MAIN_TYPEs:Ext.encode(json6),
					 INSTITUTIONs:Ext.encode(json7),
					 INSTITUTION_NAMEs:Ext.encode(json8),
					 MAIN_TYPE_NEWs:Ext.encode(json9),
					 tMgr:transForm1.form.findField('tMgrId').getValue(),
					 type:applyType
	             },
	             success : function(response) {
//	                 Ext.Msg.alert('提示', '操作成功');
//	                 reloadCurrentData();
	            	 var ret = Ext.decode(response.responseText);
						var instanceid = ret.instanceid;//流程实例ID
						var currNode = ret.currNode;//当前节点
						var nextNode = ret.nextNode;//下一步节点
						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
	             },
	             failure : function(response) {
	                 Ext.Msg.alert('提示', '操作失败' );
	             }
	         });
		}}]
});


var customerView = [{
	title:'客户移交',
	hideTitle:true,
	type:'panel',
	items:[pTrans]
},{
	title:'客户移交',
	hideTitle:true,
	type:'panel',
	items:[cTrans]
},{
	title:'查看移交记录',
	type:'grid',
	pageable:true,
	url : basepath + '/custTransHis.json',
	fields : {
		fields : [{name:'CUST_NAME'	,text:'客户名称'	},
		          {name:'BEFORE_INST_NAME'	,text:'调整前归属机构'	},
		          {name:'AFTER_INST_NAME'	,text:'调整后归属机构'	},
		          {name:'BEFORE_MGR_NAME'	,text:'调整前归属客户经理'	},
		          {name:'AFTER_MGR_NAME'	,text:'调整后归属客户经理'	},
		          {name:'BEFORE_MAIN_TYPE_ORA'	,text:'调整前主协办类型'	},
		          {name:'AFTER_MAIN_TYPE_ORA'	,text:'调整后主协办类型'	},
		          {name:'ASSIGN_USERNAME'	,text:'分配人名称'	},
		          {name:'ASSIGN_DATE',text:'分配日期'	},
		          {name:'WORK_TRAN_REASON',text:'工作移交原因'	},
		          {name:'WORK_TRAN_LEVEL_ORA',text:'工作移交类别',renderer:function(val){
		          	if(val == '1'){
		          		val = '营业单位个金客户已销户，且销户满1个月并新增10万(含)RMB以上';
		          	}else if(val == '2'){
		          		val = '最近一年内未发生任何交易(除利息外)，并且有以下情形之一者：1、账户AUM余额低于RMB1000元（含）以下，实现AUM增量RMB50000元（含）以上, 2、账户AUM余额低于RMB10000元（含）以下，实现AUM增量RMB100000元（含）以上 ,3、账户AUM余额低于RMB50000元（含）以下，实现AUM增量RMB300000元（含）以上,4、 建立授信额度，动拨超过3个月，并动拨金额超过原AUM（客户原无授信）';
		          	}else if(val == '3'){
		          		val = '其它';
		          	}
		         	return val;
		          }},
		          {name:'WORK_TRAN_DATE'	,text:'工作交接日期'	}],
		fn : function(CUST_NAME,BEFORE_INST_NAME,AFTER_INST_NAME,BEFORE_MGR_NAME,AFTER_MGR_NAME,BEFORE_MAIN_TYPE,AFTER_MAIN_TYPE,ASSIGN_USERNAME,ASSIGN_DATE,WORK_TRAN_REASON,WORK_TRAN_LEVEL,WORK_TRAN_DATE){
			return [CUST_NAME,BEFORE_INST_NAME,AFTER_INST_NAME,BEFORE_MGR_NAME,AFTER_MGR_NAME,BEFORE_MAIN_TYPE,AFTER_MAIN_TYPE,ASSIGN_USERNAME,ASSIGN_DATE,WORK_TRAN_REASON,WORK_TRAN_LEVEL,WORK_TRAN_DATE];
		}
	}
}];

var beforeviewshow = function(view){
	if(view._defaultTitle == '查看移交记录'){
		view.setParameters (); 
	}
};

//将tbar全部隐藏
var afterinit = function(app){
	Ext.getCmp('trans1').hide();
	Ext.getCmp('trans2').hide();
	Ext.getCmp('trans3').show();
	Ext.getCmp('trans4').show();
	Ext.getCmp('trans5').show();
	app.setSearchParams = app.setSearchParams.createInterceptor(function(params, forceLoad, add, transType){
       Ext.apply(params,extraParams)
    },app);
};
//在设置查询条件之后,根据客户类型,展示不同的tbar
var setsearchparams = function(){
	var type = getConditionField('CUST_TYPE').getValue();
	if(type == '2'){
		Ext.getCmp('trans1').hide();
		Ext.getCmp('trans2').hide();
		Ext.getCmp('trans3').show();
		Ext.getCmp('trans4').show();
		Ext.getCmp('trans5').show();
	}else{
		Ext.getCmp('trans1').hide();
		Ext.getCmp('trans2').hide();
		Ext.getCmp('trans3').show();
		Ext.getCmp('trans4').show();
		Ext.getCmp('trans5').show();
	}
};