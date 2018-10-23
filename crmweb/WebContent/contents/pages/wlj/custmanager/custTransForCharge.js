/**
 * @description 主管直接移交页面
 * @author luyy
 * @since 2014-07-02
 */
 
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
    '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);
var url = basepath+'/custTrans.json?type=charge';

var createView = false;
var editView = false;
var detailView = false;

var autoLoadGrid = false;
var json = null;//保存选中的客户
var applyType = '';//移交类型 1：对私区域内移交，2：对私跨区域移交，3：对公支行移交，4：对公区域内移交
				 //5：对公跨区域移交，6：对私主管直接移交，7：对公主管直接移交（主办），8：对公主管直接移交（协办）

var lookupTypes = ['MAINTAIN_TYPE','XD000080'];

var fields = [
	{name:'ID',text:'ID',hidden:true},
    {name:'CUST_ID' ,text:'客户编号',dataType:'string',searchField:true},
    {name:'CUST_NAME',text:'客户名称',dataType:'string',searchField:true},
    {name:'CUST_TYPE',text:'客户类型',translateType:'XD000080',searchField:true,cAllowBlank:false},
    {name:'MGR_ID',hidden:true},
    {name:'MGR_NAME',text:'客户经理',hiddenName:'MGR_ID',dataType:'userchoose',searchField : true,singleSelect:true},
    {name:'MAIN_TYPE',text:'主协办类型',translateType:'MAINTAIN_TYPE',searchField:true},
    {name:'INSTITUTION',hidden:true},
    {name:'INSTITUTION_NAME',text:'机构名称',dataType:'string'},
    {name:'ASSIGN_USERNAME',text:'分配/移交人',dataType:'string'},
    {name:'ASSIGN_DATE',text:'分配/移交日期',dataType:'date'},
    {name:'EFFECT_DATE',text:'生效日期',dataType:'date'}
];

/**
 * 导入查询管理器
 */
var importSearchManager = new Com.yucheng.crm.common.ImportSearchManager({
	templateFile: 'managerImport.xlsx',
    tradeCode   : 'importCustTransForCharge',
    url         : basepath + '/custTrans.json',
    params      : {importFlag:"charge"},
    impSearch   : false,
    successMsg	: '移转成功!',
    fields	     : [
    	{text: '核心客户号',name: 'CORE_NO'},
		{text: '客户姓名',name: 'CUST_NAME'},
		{text: '客户类型',name: 'CUST_TYPE'},
		{text: '客户经理编号',name: 'MGR_ID'},
		{text: '客户经理名称',name: 'MGR_NAME'},
		{text: '接受客户经理编号',name: 'T_MGR_ID'},
		{text: '接受客户经理',name: 'T_MGR_NAME'},
		{text: '客户移交类别',name: 'HAND_KIND'},
		{text: '工作移交原因',name: 'HAND_OVER_REASON'},
		{text: '工作交接日期',name: 'WORK_INTERFIX_Dt'},
	    {text: '校验信息',name:'IMP_MSG'}
	],
	listeners:{
		beforeSerach:function(){
			this.progressWin.hide();
		}
	}
});

var tbar = [{
	text:'移交客户',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			var custType = getAllSelects()[0].data.CUST_TYPE;
			if(custType == '2'){//对私
				applyType = '6';//对私主管直接移交
				//将选中记录添加到客户列表
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
				showCustomerViewByIndex(0);
			}else{//处理对公客户移交
				//将选中记录添加到客户列表
				json={'transedData':[]};
				var mainType = getAllSelects()[0].data.MAIN_TYPE;
				for (var i = 0; i < getAllSelects().length; i++) {
					var records = getAllSelects()[i].data;
					if(mainType != records.MAIN_TYPE){
						Ext.Msg.alert('提示','[主办]和[协办]记录需要分开处理！');
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
				collapseSearchPanel();//收起查询面板
				transedStore.loadData(json);//将数据加载到移交客户表格数据集中
				if(mainType == '1'){//主办
					applyType = '7';//对公主管直接移交（主办）
					transForm1.form.reset();
					showCustomerViewByIndex(1);
				}else{//协办
					applyType = '8';//对公主管直接移交（协办）
					transForm2.form.reset();
					showCustomerViewByIndex(2);
				}
			}
		}
	}
},{
	text : '模板下载', 
	hidden:JsContext.checkGrant('custTransForCharge_download'),
	handler : function(){
		importSearchManager.downloadTemplate();
	}
//	handler : function(){
//		var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'
//						+ ' scrollbars=no, resizable=no,location=no, status=no';
//		var fileName = 'managerImport.xlsx';
//		var uploadUrl = basepath + '/TempDownload?filename=' + fileName;
//		window.open(uploadUrl, '', winPara);
//	}
},{
	text : '批量导入',
	hidden:JsContext.checkGrant('custTransForCharge_import'),
	handler : function(){
		importSearchManager.importSearch();
	}
//	handler : function(){
//		importForm.tradecode = 'importCustTransForCharge';
//		importWindow.show();
//	}
}];

var handStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=HAND_KIND'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var transForm = new Ext.FormPanel( {
	frame : true,
	height: 140,
	region : 'north',
	items : [ {
		layout : 'column',
		items : [{
			layout : 'form',
			columnWidth : .5,
			labelWidth:100,
			items : [ new Com.yucheng.crm.common.OrgUserManage({ 
				xtype:'userchoose',
				fieldLabel : '<font color="red">*</font>接收客户经理', 
				labelStyle: 'text-align:right;',
				name : 'tMgrName',
				hiddenName:'tMgrId',
				//searchRoleType:('304,100009'),  //指定查询角色属性 ,默认全部角色
				searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
				singleSelect:true,
				allowBlank:false,
				anchor : '95%',
				callback:function(b){
					transForm.getForm().findField("tOrgId").setValue(b[0].data.orgId);
					transForm.getForm().findField("tOrgName").setValue(b[0].data.orgName);
				}
				}),{
			    name : 'workInterfixDt',
			    xtype : 'datefield',
			    format : 'Y-m-d',
			    fieldLabel : '<font color="red">*</font>工作交接日期',
			    labelStyle : 'text-align:right;',
			    allowBlank:false,
			    anchor : '95%'
			} ]
		},{
			columnWidth : .5,
			layout : 'form',
			labelWidth:100,
			items : [
				{xtype:'textfield',name:'tOrgName',fieldLabel : '<font color="red">*</font>接收机构',labelStyle : 'text-align:right;',readOnly:true,anchor : '95%'},
				{xtype:'hidden',name:'tOrgId',fieldLabel : '<font color="red">*</font>接收机构',labelStyle : 'text-align:right;',anchor : '95%'},
				{xtype:'combo',hiddenName:'handKind',name : 'handKind',fieldLabel: '<font color="red">*</font>工作移交类别',forceSelection : true,labelStyle: 'text-align:right;',
	             	triggerAction:'all',mode:'local',store:handStore,valueField:'key',displayField:'value',allowBlank:false, emptyText:'请选择',anchor : '95%'}
             
         	]
		},{
			layout : 'form',
			columnWidth : 1,
			labelWidth:100,
			items : [ 
				{name : 'handOverReason',xtype : 'textarea',fieldLabel : '<font color="red">*</font>工作移交原因',labelStyle : 'text-align:right;',allowBlank:false,anchor : '97.5%'}
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
    {name:'institution',mapping:'INSTITUTION'},
    {name:'institutionName',mapping:'INSTITUTION_NAME'}
]);
/**
 * 客户移交表格数据集
 */
var transedStore = new Ext.data.Store({
    reader: new Ext.data.JsonReader({
       root : 'transedData'
   },transedRecord )
});
var num = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
});
                                         
var cm = new Ext.grid.ColumnModel([
 	num,	// 定义列模型
    {header : '', dataIndex : 'recordId',hidden : true}, 
    {header:'客户编号',dataIndex:'custId',sortable : true,width : 100},
	{header:'客户名称',dataIndex:'custName',sortable : true,width : 100},
	{header:'',dataIndex:'mgrId',sortable : true,width : 100,hidden:true},
	{header:'原客户经理',dataIndex:'mgrName',sortable : true,width : 100},
	{header:'原主协办关系',dataIndex:'mainType',sortable : true,width : 100,hidden:true,renderer:function(v){
		if(v == '1') return '主办';else if(v == '2') return '协办'; else return '';
	}},
	{header:'',dataIndex:'institution',sortable : true,width : 100,hidden:true},
	{header:'原归属机构',dataIndex:'institutionName',sortable : true,width : 100}
]);
var custGrid = new Ext.grid.GridPanel({
	title : '移交客户',
	autoScroll : true,
	height:150,
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
		text:'移交',
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
	             url : basepath + '/custTrans!pChargeTrans.json',
	             method : 'POST',
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
					 tMgr:transForm.form.findField('tMgrId').getValue()
	             },
	             success : function() {
	                 Ext.Msg.alert('提示', '操作成功');
	                 reloadCurrentData();
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
			items : [ new Com.yucheng.crm.common.OrgUserManage({ 
				xtype:'userchoose',
				fieldLabel : '<font color="red">*</font>接收客户经理', 
				labelStyle: 'text-align:right;',
				name : 'tMgrName',
				hiddenName:'tMgrId',
				//searchRoleType:('304,100009'),  //指定查询角色属性 ,默认全部角色
				searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
				singleSelect:true,
				allowBlank:false,
				anchor : '95%',
				callback:function(b){
					transForm1.getForm().findField("tOrgId").setValue(b[0].data.orgId);
					transForm1.getForm().findField("tOrgName").setValue(b[0].data.orgName);
				}
				}),
				{name : 'workInterfixDt',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '<font color="red">*</font>工作交接日期',labelStyle : 'text-align:right;',allowBlank:false,anchor : '95%'}
			]
		},{
			columnWidth : .5,
			layout : 'form',
			labelWidth:100,
			items : [
				{xtype:'textfield',name:'tOrgName',fieldLabel : '<font color="red">*</font>接收机构',labelStyle : 'text-align:right;',readOnly:true,anchor : '95%'},
				{xtype:'hidden',name:'tOrgId',fieldLabel : '<font color="red">*</font>接收机构',labelStyle : 'text-align:right;',anchor : '95%'},
				{xtype:'combo',hiddenName:'handKind',name : 'handKind',fieldLabel: '<font color="red">*</font>工作移交类别',forceSelection : true,labelStyle: 'text-align:right;',
             		triggerAction:'all',mode:'local',store:handStore,valueField:'key',displayField:'value',allowBlank:false, emptyText:'请选择',anchor : '95%'}
         	]
		},{
			layout : 'form',
			columnWidth : 1,
			labelWidth:100,
			items : [ 
				{name : 'handOverReason',xtype : 'textarea',fieldLabel : '<font color="red">*</font>工作移交原因',labelStyle : 'text-align:right;',allowBlank:false,anchor : '97.5%'}
			]
		}]
	}]
});

 var num1 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});
                                         
var cm1 = new Ext.grid.ColumnModel([
 	num1,	// 定义列模型
    {header : '', dataIndex : 'recordId',hidden : true}, 
    {header:'客户编号',dataIndex:'custId',sortable : true,width : 100},
	{header:'客户名称',dataIndex:'custName',sortable : true,width : 100},
	{header:'',dataIndex:'mgrId',sortable : true,width : 100,hidden:true},
	{header:'原客户经理',dataIndex:'mgrName',sortable : true,width : 100},
	{header:'原主协办关系',dataIndex:'mainType',sortable : true,width : 100,renderer:function(v){
		if(v == '1') return '主办';else if(v == '2') return '协办'; else return '';
	}},
	{header:'',dataIndex:'institution',sortable : true,width : 100,hidden:true},
	{header:'原归属机构',dataIndex:'institutionName',sortable : true,width : 100}
]);
var custGrid1 = new Ext.grid.GridPanel({
	title : '移交客户',
	height:150,
	autoScroll : true,
//	region : 'south',
	region : 'center',
    store: transedStore,
	stripeRows : true, // 斑马线
	cm : cm1,
	viewConfig : {}
});
var mgrRecord = Ext.data.Record.create([
    {name: 'mgrId',mapping : 'MGR_ID'},
    {name: 'mgrName',mapping : 'MGR_NAME'},
    {name:'institution',mapping:'INSTITUTION'},
    {name:'institutionName',mapping:'INSTITUTION_NAME'}
]);
var mgrStore = new Ext.data.Store({
   reader: new Ext.data.JsonReader({
       root : 'mgrData'
   },mgrRecord )
});

var mnum1 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
                                       
var mcm1 = new Ext.grid.ColumnModel([
	mnum1,	// 定义列模型
	{header:'',dataIndex:'mgrId',sortable : true,width : 100,hidden:true},
	{header:'客户经理',dataIndex:'mgrName',sortable : true,width : 100},
	{header:'',dataIndex:'institution',sortable : true,width : 100,hidden:true},
	{header:'归属机构',dataIndex:'institutionName',sortable : true,width : 100}
]);


var mgrWindow = new Ext.Window({
	layout : 'fit',
	title:'选择客户经理',
    autoScroll : true,
    draggable : true,
    closable : true,
    closeAction : 'hide',
    modal : true,
    width : 300,
    height : 120,
    loadMask : true,
    border : false,
    items : [{
    	xtype:'panel',
    	layout : 'form',
    	items:[new Com.yucheng.crm.common.OrgUserManage({ 
	    		xtype:'userchoose',
	    		fieldLabel : '协办客户经理', 
	    		labelStyle: 'text-align:right;',
	    		name : 'mgrName',
	    		//searchRoleType:('304,100009'),  //指定查询角色属性 ,默认全部角色
	    		searchType:'SUBTREE',
	    		singleSelect:true,
	    		labelWidth:100,
	    		anchor : '95%',
	    		callback:function(b){
	    			mgrStore.add(new mgrRecord({
	    				mgrId:b[0].data.userId,mgrName:b[0].data.userName,institution:b[0].data.orgId,institutionName:b[0].data.orgName
	    			}));
	    			this.setValue('');
	    			mgrWindow.hide();
	    		}
    		})
    	]
    }]
});


var mgrGrid = new Ext.grid.GridPanel({
	title : '协办经理',
	autoScroll : true,
	height:150,
	region : 'center',
	store: mgrStore,
	tbar:[{
		text:'新增',
		handler:function(){
			mgrWindow.show();
		}
	},{
		text:'删除',
		handler:function(){
			var selectLength = mgrGrid.getSelectionModel().getSelections().length;
			var checkedNodes = mgrGrid.getSelectionModel().selections.items;
			if(selectLength == 0){
				 Ext.MessageBox.alert('提示','请选择要删除的记录');
			}
			for (var i = 0; i < checkedNodes.length; i++) {
				mgrStore.remove(checkedNodes[i]);
			}
		}
	}],
	stripeRows : true, // 斑马线
	cm : mcm1,
	viewConfig : {}
});
var cTransM = new Ext.Panel({
	autoScroll : true,
	height:450,
	buttonAlign : "center",
	layout:'border',
//	items:[transForm1,mgrGrid,custGrid1],
	items:[transForm1,custGrid1],
	buttons:[{
		text:'移交',
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
            //循环被移转的客户信息
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
			 
			var json9 = {'MGR_ID':[]};
			var json10 = {'MGR_NAME':[]};
			var json11 = {'INSTITUTION':[]};
			var json12 = {'INSTITUTION_NAME':[]};
			
			//协办客户经理
			for(var i=0;i<mgrStore.getCount();i++){
	             var temp=mgrStore.getAt(i);
                 json9.MGR_ID.push(temp.data.mgrId);
                 json10.MGR_NAME.push(temp.data.mgrName);
				 json11.INSTITUTION.push(temp.data.institution);
				 json12.INSTITUTION_NAME.push(temp.data.institutionName);
	        }
			Ext.Msg.wait('正在保存，请稍后......','系统提示');
			Ext.Ajax.request({
	             url : basepath + '/custTrans!cChargeTransM.json',
	             method : 'POST',
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
					 MGR_IDss:Ext.encode(json9),
					 MGR_NAMEss:Ext.encode(json10),
					 INSTITUTIONss:Ext.encode(json11),
					 INSTITUTION_NAMEss:Ext.encode(json12),
					 tMgr:transForm1.form.findField('tMgrId').getValue()
	             },
	             success : function() {
	                 Ext.Msg.alert('提示', '操作成功');
	                 reloadCurrentData();
	             },
	             failure : function(response) {
	                 Ext.Msg.alert('提示', '操作失败' );
	             }
	         });
		}
	}]
});

var transForm2 = new Ext.FormPanel({
	frame : true,
	height: 140,
	region : 'north',
	items : [ {
		layout : 'column',
		items : [{
			layout : 'form',
			columnWidth : .5,
			labelWidth:100,
			items : [ 
				{name : 'workInterfixDt',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '<font color="red">*</font>工作交接日期',labelStyle : 'text-align:right;', allowBlank:false,anchor : '95%'}
			]
		},{
			columnWidth : .5,
			layout : 'form',
			labelWidth:100,
			items : [
				{xtype:'combo',name : 'handKind',hiddenName:'handKind', fieldLabel: '<font color="red">*</font>工作移交类别',labelStyle: 'text-align:right;',forceSelection : true,
             		triggerAction:'all', mode:'local',store:handStore,valueField:'key',displayField:'value', allowBlank:false,emptyText:'请选择',anchor : '95%'}
         	]
		},{
			layout : 'form',
			columnWidth : 1,
			labelWidth:100,
			items : [ 
				{name : 'handOverReason',xtype : 'textarea',fieldLabel : '<font color="red">*</font>工作移交原因',labelStyle : 'text-align:right;',allowBlank:false,anchor : '97.5%'}
			]
		}]
	}]
});

var num2 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});
                                         
var cm2 = new Ext.grid.ColumnModel([
 	num2,	// 定义列模型
    {header : '', dataIndex : 'recordId',hidden : true}, 
    {header:'客户编号',dataIndex:'custId',sortable : true,width : 100},
	{header:'客户名称',dataIndex:'custName',sortable : true,width : 100},
	{header:'',dataIndex:'mgrId',sortable : true,width : 100,hidden:true},
	{header:'原客户经理',dataIndex:'mgrName',sortable : true,width : 100},
	{header:'原主协办关系',dataIndex:'mainType',sortable : true,width : 100,renderer:function(v){
		if(v == '1') return '主办';else if(v == '2') return '协办'; else return '';
	}},
	{header:'',dataIndex:'institution',sortable : true,width : 100,hidden:true},
	{header:'原归属机构',dataIndex:'institutionName',sortable : true,width : 100}
]);
var custGrid2 = new Ext.grid.GridPanel({
	title : '移交客户',
	height:150,
	autoScroll : true,
//	region : 'south',
	region : 'center',
    store: transedStore,
	stripeRows : true, // 斑马线
	cm : cm2,
	viewConfig : {}
});


var mnum2 = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
});
                                       
var mcm2 = new Ext.grid.ColumnModel([
	mnum2,	// 定义列模型
	{header:'',dataIndex:'mgrId',sortable : true,width : 100,hidden:true},
	{header:'客户经理',dataIndex:'mgrName',sortable : true,width : 100},
	{header:'',dataIndex:'institution',sortable : true,width : 100,hidden:true},
	{header:'归属机构',dataIndex:'institutionName',sortable : true,width : 100}
]);


var mgrGrid2 = new Ext.grid.GridPanel({
	autoScroll : true,
	title : '协办经理',
	height:150,
	region : 'center',
	store: mgrStore,
	tbar:[{
		text:'新增',
		handler:function(){
			mgrWindow.show();
		}
	},{
		text:'删除',
		handler:function(){
			var selectLength = mgrGrid2.getSelectionModel().getSelections().length;
			var checkedNodes = mgrGrid2.getSelectionModel().selections.items;
			if(selectLength == 0){
				 Ext.MessageBox.alert('提示','请选择要删除的记录');
			}
			for (var i = 0; i < checkedNodes.length; i++) {
				mgrStore.remove(checkedNodes[i]);
			}
		}
	}],
	stripeRows : true, // 斑马线
	cm : mcm2,
	viewConfig : {}
});

var cTransF = new Ext.Panel({
	autoScroll : true,
	buttonAlign : "center",
	layout:'border',
//	items:[transForm2,mgrGrid2,custGrid2],
	items:[transForm2,custGrid2],
	buttons:[{
		text:'移交',
		handler:function(){
			if (!transForm2.getForm().isValid()) {
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
			 
			var json9 = {'MGR_ID':[]};
			var json10 = {'MGR_NAME':[]};
			var json11 = {'INSTITUTION':[]};
			var json12 = {'INSTITUTION_NAME':[]};
			
			 
			for(var i=0;i<mgrStore.getCount();i++){
	            var temp=mgrStore.getAt(i);
                json9.MGR_ID.push(temp.data.mgrId);
                json10.MGR_NAME.push(temp.data.mgrName);
			    json11.INSTITUTION.push(temp.data.institution);
				json12.INSTITUTION_NAME.push(temp.data.institutionName);
	        }
			Ext.Msg.wait('正在保存，请稍后......','系统提示');
			Ext.Ajax.request({
	             url : basepath + '/custTrans!cChargeTransF.json',
	             method : 'POST',
	             waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				 form:transForm2.getForm().id,
	             params:{
	            	 RECORD_IDs:Ext.encode(json1),
					 CUST_IDs:Ext.encode(json2),
					 CUST_NAMEs:Ext.encode(json3),
					 MGR_IDs:Ext.encode(json4),
					 MGR_NAMEs:Ext.encode(json5),
					 MAIN_TYPEs:Ext.encode(json6),
					 INSTITUTIONs:Ext.encode(json7),
					 INSTITUTION_NAMEs:Ext.encode(json8),
					 MGR_IDss:Ext.encode(json9),
					 MGR_NAMEss:Ext.encode(json10),
					 INSTITUTIONss:Ext.encode(json11),
					 INSTITUTION_NAMEss:Ext.encode(json12)
	             },
	             success : function() {
	                 Ext.Msg.alert('提示', '操作成功');
	                 reloadCurrentData();
	             },
	             failure : function(response) {
	                 Ext.Msg.alert('提示', '操作失败' );
	             }
	         });
		}}]
});

var customerView = [{
	title:'对私客户移交',
	hideTitle:true,
	type:'panel',
	items:[pTrans]
},{
	title:'对公客户移交(主办)',
	hideTitle:true,
	autoScroll : true,
	type:'panel',
	items:[cTransM]
},{
	title:'对公客户移交(协办)',
	hideTitle:true,
	autoScroll : true,
	type:'panel',
	items:[cTransF]
},{
	title:'查看移交记录',
	type:'grid',
	pageable:true,
	url : basepath + '/custTransHis.json',
	fields : {
		fields : [
			{name:'CUST_NAME'	,text:'客户名称'	},
            {name:'BEFORE_INST_NAME'	,text:'调整前归属机构'	},
            {name:'AFTER_INST_NAME'	,text:'调整后归属机构'	},
            {name:'BEFORE_MGR_NAME'	,text:'调整前归属客户经理'	},
            {name:'AFTER_MGR_NAME'	,text:'调整后归属客户经理'	},
            {name:'BEFORE_MAIN_TYPE_ORA'	,text:'调整前主协办类型'	},
            {name:'AFTER_MAIN_TYPE_ORA'	,text:'调整后主协办类型'	},
            {name:'ASSIGN_USERNAME'	,text:'分配人名称'	},
            {name:'ASSIGN_DATE'	,text:'分配日期'	},
            {name:'WORK_TRAN_REASON'	,text:'工作移交原因'	},
            {name:'WORK_TRAN_LEVEL_ORA'	,text:'工作移交类别'	},
            {name:'WORK_TRAN_DATE'	,text:'工作交接日期'	}
        ],
		fn : function(CUST_NAME,BEFORE_INST_NAME,AFTER_INST_NAME,BEFORE_MGR_NAME,AFTER_MGR_NAME,BEFORE_MAIN_TYPE,AFTER_MAIN_TYPE,ASSIGN_USERNAME,ASSIGN_DATE,WORK_TRAN_REASON,WORK_TRAN_LEVEL,WORK_TRAN_DATE){
			return [CUST_NAME,BEFORE_INST_NAME,AFTER_INST_NAME,BEFORE_MGR_NAME,AFTER_MGR_NAME,BEFORE_MAIN_TYPE,AFTER_MAIN_TYPE,ASSIGN_USERNAME,ASSIGN_DATE,WORK_TRAN_REASON,WORK_TRAN_LEVEL,WORK_TRAN_DATE];
		}
	}
}];

var beforeviewshow = function(view){
	if(view._defaultTitle == '查看移交记录'){
		 if(getSelectedData()!=null&&''!=getSelectedData()){
			 view.setParameters({
				 custId:getSelectedData().data.CUST_ID
			 });
		 }else{
		 	view.setParameters (); 
		 }
	}
};

var viewhide = function(){
	expandSearchPanel();//展开查询域
};