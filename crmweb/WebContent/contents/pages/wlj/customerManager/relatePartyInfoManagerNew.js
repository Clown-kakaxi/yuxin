/**
*@description 关联方信息维护，添加,申报
*@author:dongyi
*@since:2014-07-16
*@checkedby:
*/

imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.Declarantname.js' //申报人放大镜
	,'/contents/pages/wlj/customerManager/queryAllCustomer/htmlForRelate.js'//存储一些html片段，包括htmlpiece1，htmlpiece3
]);
var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动
//var url = "/sdaf";

	
var createView=false;
var editView=false;
var recorddata=0;//此变量用于存储申报人的信息
var lookupTypes=[
	 'REL_WITH_BANK',//主申报人与银行的关联关系
	 'REL_WITH_BANK1',//关系人与银行的关联关系
	 'XD000040',//证件类型
     'DECLARAE_STATE',//申报状态
     'CANCEL_STATE',//注销状态
     'RELATE_PARTY_ATTR',//关联方属性
     'XD000365',//关系人属性
     'RELATE_DECLARANT_REL1',//关联人与主申报人关系
     'XD000346'//是否为商业银行
];
var tempMaindeclarantData=null;//用于临时存储主申报人保存的信息
var newDeclarntflag=0;//设置标志，用于判断点击新增或详情申报人时，MainDeclantInfoForm面板是否重置。0表示需要，1表示不需要
var declarantId=null;//此变量用于保存申报人创建后的ID，便于添加关系人时，与此ID绑定在后台查询申报人信息，一起存入后台
var delarantData=null;//此变量用于保存申报人创建后的申报人名称，便于添加关系人时，与此名称绑定在后台查询申报人信息，一起存入后台
var flag=0;//该标志用于区别在show新增关联方界面时，具体是哪一个界面。0表示declarantgrid的新增按钮，1表示新增申报人grid时的新增按钮
var fieldvalue='';//此变量用于点击时间触发时，所传递的字段值
var tmprecords = '';//获取点击数据
//var relte_lock_flag=0;//此变量用于当前用户修改新增关系人后，锁定表格并提醒用户提交审批流程，0为不需要提醒提交，1为需要提醒用户提交进行审批
var main_ids='';//此变量用于选中关联方之后，根据此id查询对应的关系人信息
var fields=[
          {name:'MAIN_ID',text:'关联方ID',hidden:true}//此文件fields必须要有一个无用字段

];
//----------------------------------------------
//特殊的数据字典处理方法:关联人与主申报人关系


/**
 * 树形结构的loader对象配置
 */
var treeLoaders = [{
	key : 'RELATE_DECLARANT_LOADER',
	url : basepath + '/DeclarantBankRelAction.json',
	parentAttr : 'PARENT_CODE',
	locateAttr : 'F_CODE',
	jsonRoot:'json.data',
	rootValue : '0',
	textField : 'F_VALUE',
	idProperties : 'F_CODE'
}];
/**
 * 树形面板对象预配置
 */
var treeCfgs = [{
	key : 'RELATE_DECLARANT_TREE',
	loaderKey : 'RELATE_DECLARANT_LOADER',
	autoScroll:true,
	rootCfg : {
		expanded:true,
		id:'0',
		text:'关联人与主申报人关系',
		autoScroll:true,
		children:[]
	},
	clickFn : function(node){
		if(node.attributes.f_code == undefined){
			setSearchParams({
				f_code : node.attributes.f_code
			});
		}else{
			//首先处理字段展示
			//然后重新查询
			setSearchParams({
				f_code : node.attributes.f_code
			});
		}
	}
},{
	key : 'RELATE_DECLARANT_REL_TREE',
	loaderKey : 'RELATE_DECLARANT_LOADER',
	autoScroll:true,
	rootCfg : {
		expanded:true,
		id:'root',
		text:'关联人与主申报人关系',
		autoScroll:true,
		children:[]
	},
	clickFn : function(node){
		if(!node.isLeaf()){
			Ext.Msg.alert('提示','请选择子选项！');
			getCurrentView().contentPanel.form.findField('RELATE_DECLARANT_REL').setValue('');
		}
	}
}];
//---------------------------------------------------------------
//关系人grid
var relaterownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
			header : 'No.',
			width : 28
		});
var relatecolumnmodel = new Ext.grid.ColumnModel([
    relaterownum,
    {header:'关系人编号',dataIndex:'RELATE_ID',width:100,sortable : true,hidden:true},
    {header:'关系人名称',dataIndex:'PRIVY_NAME',width:100,sortable : true},
    {header:'关系人属性',dataIndex:'PRIVY_ATTRIBUTE',width:100,renderer:function(value){
				var val = translateLookupByKey("XD000365",value);
				return val?val:"";
				},sortable : true},
    {header:'证件类型',dataIndex:'IDENT_TYPE',width:150,renderer:function(value){
				var val = translateLookupByKey("XD000040",value);
				return val?val:"";
				},sortable : true},
	{header:'证件号码',dataIndex:'IDENT_NO',width:100,sortable : true},
    {header:'电话号码',dataIndex:'TEL',width:100,sortable : true},
    {header:'邮箱',dataIndex:'EMAIL',width:100,sortable : true},
    {header:'联系地址',dataIndex:'CONTACT_ADDR',width:100,sortable : true},
    {header:'与申报人关系',dataIndex:'RELATE_DECLARANT_REL',width:100,renderer:function(value){
				var val = translateLookupByKey("RELATE_DECLARANT_REL1",value);
				return val?val:"";
				},sortable : true},
    {header:'与银行关系',dataIndex:'DECLARANT_BANK_REL',width:100,renderer:function(value){
				var val = translateLookupByKey("REL_WITH_BANK",value);
				return val?val:"";
				},sortable : true},
    {header:'是否为商业银行',dataIndex:'IS_COMMECIAL_BANK',width:100,hidden:true,renderer:function(value){
				var val = translateLookupByKey("XD000346",value);
				return val?val:"";
				},sortable : true},
	{header:'持股比例(%)',dataIndex:'STOCK_RATIO',width:100,sortable : true,hidden:true},			
    {header:'关联人编号',dataIndex:'MAIN_ID',width:100,hidden:true,sortable : true},
    {header:'生效日',dataIndex:'EFFECT_DATE',width:100,hidden:true,sortable : true},
    {header:'备注',dataIndex:'REMARK',width:100,hidden:true,sortable : true}
]);
var relateRecord = new Ext.data.Record.create([
		{name:'RELATE_ID'},
	    {name:'PRIVY_NAME'},
	    {name:'PRIVY_ATTRIBUTE'},
	    {name:'IDENT_TYPE'},
	    {name:'IDENT_NO'},
	    {name:'TEL'},
	    {name:'EMAIL'},
	    {name:'CONTACT_ADDR'},
	    {name:'RELATE_DECLARANT_REL'},
	    {name:'DECLARANT_BANK_REL'},
	    {name:'IS_COMMECIAL_BANK'},
	    {name:'STOCK_RATIO'},
	    {name:'MAIN_ID'},
	    {name:'EFFECT_DATE'},
	    {name:'REMARK'}
	]);
var relateInfoReader = new Ext.data.JsonReader({
		totalProperty:'json.count',
		root:'json.data'
	},relateRecord);
var relateInfoProxy = new Ext.data.HttpProxy({
			url:basepath+'/AcrmFCrRelatePrivyInfo.json'
		});	
var	relateInfoStore = new Ext.data.Store({
		restful : true,
		proxy : relateInfoProxy,
		reader :relateInfoReader,
		recordType:relateRecord
	});	
relateInfoStore.on('beforeload', function(relateInfoStore) { 
       relateInfoStore.baseParams = {'MAIN_ID':main_ids}; 
}); 
var	relatepagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
			         [ 100, '100条/页' ], [ 250, '250条/页' ],
			         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85	
	});
var searchrelateData = function(grid,cobo){//根据不同的分页加载数据
			grid.store.removeAll();
			grid.store.load({
				params:{
				    'MAIN_ID':main_ids,
					start:0,
					limit: parseInt(cobo.getValue())
				}
			});
	};	
var relatenumber = parseInt(relatepagesize_combo.getValue());
		/**
		 * 监听分页下拉框选择事件
		 */
		relatepagesize_combo.on("select", function(comboBox) {
			relatebbar.pageSize = parseInt(relatepagesize_combo.getValue()),
			searchrelateData(relategrid,relatepagesize_combo);//不同分页加载数据
		});
		//分页工具条定义
var	relatebbar = new Ext.PagingToolbar({
			pageSize : relatenumber,
			store : relateInfoStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', relatepagesize_combo]
		});		
var relateTbar=new Ext.Toolbar({
	items:[
		{
			text : '新增',
			hidden:JsContext.checkGrant('relatePrivyAdd'),
			handler : function(){
				if(recorddata.length != 1){
					Ext.Msg.alert('提示','请选择关联方信息新增！');
					return false;
				}
				if(recorddata[0].data.CANCEL_STATE=="2"){
					Ext.Msg.alert('提示','请选择一条未注销的关联方进行新增！');
					return false;
				}
				else{
				flag=0;
				showCustomerViewByTitle('新增关联方信息');
				}
			}
		},{	
			text : '修改',
			hidden:JsContext.checkGrant('relatePrivyUpdate'),
			handler : function(){
				var tempdata = relategrid.getSelectionModel().getSelections();
				if(tempdata.length != 1){
					Ext.Msg.alert('提示','请选择一条关系人信息修改！');
					return false;
				}if(recorddata[0].data.CANCEL_STATE=="2"){
					Ext.Msg.alert('提示','请选择一条未注销的关联方进行新增！');
					return false;
				}else{
					flag=0;
					showCustomerViewByTitle('修改关系人信息');
				}
			}
		}
//		,{
//			text:'提交',
//			hidden:JsContext.checkGrant('relatePrivyCommit'),
//			handler : function(){
//				//main_ids
////				if(relte_lock_flag==0){
////					Ext.Msg.alert('提示','请对关系人作出变更后才能提交关系人审批！');
////					return false;
////				}
////				relte_lock_flag=0;//解锁
//				Ext.Ajax.request({
//					url : basepath + '/AcrmFCrRelatePrivyInfoTemp!initFlow1.json',//关系人审批流程发起
//					method : 'GET',
//					params : {'MAIN_ID':main_ids},
//					waitMsg : '正在提交申请,请等待...',										
//					success : function(response) {
//						var ret = Ext.decode(response.responseText);
//						var instanceid = ret.instanceid;//流程实例ID
//						var currNode = ret.currNode;//当前节点
//						var nextNode = ret.nextNode;//下一步节点
//						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
//					},
//					failure : function() {
//						Ext.Msg.alert('提示', '操作失败');
//						reloadCurrentData();
//					}
//					});
//				}
//			}
		]
})		
var relategrid =new Ext.grid.GridPanel({//
			title:'关系人信息',
			frame:true,
			height:200,
			autoScroll : true,
			bbar:relatebbar,
			tbar:relateTbar,
			stripeRows : true, // 斑马线
			store:relateInfoStore,
			loadMask:true,
			cm :relatecolumnmodel,
			region:'center',
			viewConfig:{
				forceFit:false,
				autoScroll:true
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
})
//---------------------------------------------------------------
//关联方grid
var declarantrownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
			header : 'No.',
			width : 28
		});
var declarantcolumnmodel = new Ext.grid.ColumnModel([
    declarantrownum,
    {header:'关联方编号',dataIndex:'MAIN_ID',width:100,sortable : true,hidden:true},
    {header:'关联方名称',dataIndex:'DECLARANT_NAME',width:100,sortable : true},
    {header:'关联方属性',dataIndex:'DECLARANT_ATTR',width:100,renderer:function(value){
				var val = translateLookupByKey("RELATE_PARTY_ATTR",value);
				return val?val:"";
				},sortable : true},
    {header:'证件类型',dataIndex:'IDENT_TYPE',width:150,renderer:function(value){
				var val = translateLookupByKey("XD000040",value);
				return val?val:"";
				},sortable : true},
    {header:'证件号码',dataIndex:'IDENT_NO',width:100,sortable : true},
    {header:'创建日期',dataIndex:'CREATE_DATE',width:100,sortable : true},
	{header:'创建人',dataIndex:'CREATOR',width:100,sortable : true},
//	{header:'申报状态',dataIndex:'DECLARE_STATUS',width:100,renderer:function(value){
//				var val = translateLookupByKey("DECLARAE_STATE",value);
//				return val?val:"";
//				},sortable : true},
	{header:'申报日期',dataIndex:'DECLARE_DATE',width:100,sortable : true},
	 {header:'生效日',dataIndex:'EFFECT_DATE',width:100,sortable : true},
	{header:'注销状态',dataIndex:'CANCEL_STATE',width:100,renderer:function(value){
				var val = translateLookupByKey("CANCEL_STATE",value);
				return val?val:"";
				},sortable : true},
    {header:'与银行关系',dataIndex:'DECLARANT_BANK_REL',width:100,renderer:function(value){
				var val = translateLookupByKey("REL_WITH_BANK",value);
				return val?val:"";
				},sortable : true},
    {header:'电话号码',dataIndex:'TEL',width:100,sortable : true,hidden:true},
    {header:'邮件地址',dataIndex:'EMAIL',width:100,sortable : true,hidden:true},
    {header:'联系地址',dataIndex:'CONTACT_ADDR',width:100,sortable : true,hidden:true},
    {header:'是否为商业银行',dataIndex:'IS_COMMECIAL_BANK',width:100,renderer:function(value){
				var val = translateLookupByKey("XD000346",value);
				return val?val:"";
				},sortable : true,hidden:true},
    {header:'关联起始日期',dataIndex:'START_DATE',width:100,sortable : true,hidden:true},
    {header:'持股比例(%)',dataIndex:'STOCK_RATIO',width:100,sortable : true,hidden:true},
    {header:'备注',dataIndex:'REMARK',width:100,sortable : true,hidden:true}
   
]);
var declarantRecord = new Ext.data.Record.create([
			{name:'MAIN_ID'},
		    {name:'DECLARANT_NAME'},
		    {name:'DECLARANT_ATTR'},
		    {name:'IDENT_TYPE'},
		    {name:'IDENT_NO'},
		    {name:'CREATE_DATE'},
		    {name:'CREATOR'},
//		     {name:'DECLARE_STATUS'},
		    {name:'DECLARE_DATE'},
		    {name:'EFFECT_DATE'},
		    {name:'CANCEL_STATE'},
		    {name:'DECLARANT_BANK_REL'},
		    {name:'TEL'},
		    {name:'EMAIL'},
		    {name:'CONTACT_ADDR'},
		    {name:'IS_COMMECIAL_BANK'},
		    {name:'START_DATE'},
		    {name:'STOCK_RATIO'},
		    {name:'REMARK'}
		]);
var declarantInfoReader = new Ext.data.JsonReader({
			totalProperty:'json.count',
			root:'json.data'
		},declarantRecord);
var declarantInfoProxy = new Ext.data.HttpProxy({
			url:basepath+'/acrmfcrdeclarantinfo.json'
		});	
var	declarantInfoStore = new Ext.data.Store({
			restful : true,
			proxy : declarantInfoProxy,
			reader :declarantInfoReader,
			recordType:declarantRecord
		});	
var	declarantpagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
				fields : [ 'value', 'text' ],
				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
				         [ 100, '100条/页' ], [ 250, '250条/页' ],
				         [ 500, '500条/页' ] ]
			}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			forceSelection : true,
			width : 85
		});
var declarantnumber = parseInt(declarantpagesize_combo.getValue());
		/**
		 * 监听分页下拉框选择事件
		 */
		declarantpagesize_combo.on("select", function(comboBox) {
			declarantbbar.pageSize = parseInt(declarantpagesize_combo.getValue()),
			declarantInfoStore.load({
			params:{
				'DECLARANT_NAME':'',
				start:0,
				limit: parseInt(declarantpagesize_combo.getValue())
			}
		});
		});
		//分页工具条定义
var	declarantbbar = new Ext.PagingToolbar({
			pageSize : declarantnumber,
			store : declarantInfoStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', declarantpagesize_combo]
		});	
var declarantTbar=new Ext.Toolbar({
		items:[
		{	
			text : '注销',
			hidden:JsContext.checkGrant('declarantCancle'),//系统管理员才能使用此功能
			handler : function(){
				var tempdata = declarantgrid.getSelectionModel().getSelections();
				if(tempdata.length != 1){
					Ext.Msg.alert('提示','请选择一条数据！');
					return false;
				}else{
					if(tempdata[0].data.CANCEL_STATE == '2'){
						Ext.Msg.alert('提示','只能修改未注销状态的关联方信息！');
						return false;
					}else{
						Ext.Ajax.request({
							url:basepath + '/acrmfcrdeclarantinfo!updateCancelStat.json',
							method:"POST",
							params : {"MAIN_ID":tempdata[0].data.MAIN_ID},
							waitMsg : '正在保存数据,请等待...', 
							success:function(){
								Ext.Msg.alert("提示","操作成功");
								declarantInfoStore.remove(tempdata);//删除行 
							   	declarantInfoStore.load({
									params:{
										'DECLARANT_NAME':'',
										start:0,
										limit: parseInt(declarantpagesize_combo.getValue())
									}
							   	})
							},failure:function(response){
									Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
								}
						});
					}
				}
			}
		},{
			text : '关联方申报',
			hidden:JsContext.checkGrant('declarntRelate'),
			handler : function(){
				falg=0;
				showCustomerViewByTitle('关联方申报');
			}
		}
		]
})		
var declarantgrid =new Ext.grid.GridPanel({//
			title:'关联方信息',
			frame:true,
			height:240,
			autoScroll : true,
			bbar:declarantbbar,
			tbar:declarantTbar,
			stripeRows : true, // 斑马线
			store:declarantInfoStore,
			loadMask:true,
			cm :declarantcolumnmodel,
			region:'center',
			viewConfig:{
				forceFit:false,
				autoScroll:true
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
})
//---------------------------------------------------------------
//已注销的关系人
var rownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
			header : 'No.',
			width : 28
		});
var columnmodel = new Ext.grid.ColumnModel([
    rownum,
    {header:'关系人编号',dataIndex:'RELATE_ID',width:100,sortable : true,hidden:true},
    {header:'关联人编号(外键关联）',dataIndex:'MAIN_ID',width:100,sortable : true,hidden:true},
    
    {header:'关系人属性',dataIndex:'PRIVY_ATTRIBUTE',width:100,renderer:function(value){
				var val = translateLookupByKey("XD000365",value);
				return val?val:"";
				},sortable : true},
	{header:'姓名',dataIndex:'PRIVY_NAME',width:100,sortable : true},			
    {header:'证件类型',dataIndex:'IDENT_TYPE',width:150,renderer:function(value){
				var val = translateLookupByKey("XD000040",value);
				return val?val:"";
				},sortable : true},
    {header:'证件号码',dataIndex:'IDENT_NO',width:100,sortable : true},
    {header:'与申报人关系',dataIndex:'RELATE_DECLARANT_REL',width:100,renderer:function(value){
				var val = translateLookupByKey("RELATE_DECLARANT_REL1",value);
				return val?val:"";
				},sortable : true},
    {header:'与银行关系',dataIndex:'DECLARANT_BANK_REL',width:100,renderer:function(value){
				var val = translateLookupByKey("REL_WITH_BANK",value);
				return val?val:"";
				},sortable : true},
    {header:'是否为商业银行',dataIndex:'IS_COMMECIAL_BANK',width:100,renderer:function(value){
				var val = translateLookupByKey("XD000346",value);
				return val?val:"";
				},width:100,sortable : true},
    {header:'生效日',dataIndex:'EFFECT_DATE',width:100,sortable : true}
]);
var cancelRecord = new Ext.data.Record.create([
			{name:'RELATE_ID'},
			{name:'MAIN_ID'},
		    {name:'PRIVY_ATTRIBUTE'},
		    {name:'PRIVY_NAME'},
		    {name:'IDENT_TYPE'},
		    {name:'IDENT_NO'},
		    {name:'RELATE_DECLARANT_REL'},
		    {name:'DECLARANT_BANK_REL'},
		    {name:'IS_COMMECIAL_BANK'},
		    {name:'EFFECT_DATE'}
		]);
var cancelInfoReader = new Ext.data.JsonReader({
			totalProperty:'json.count',
			root:'json.data'
		},cancelRecord);
var cancelInfoProxy = new Ext.data.HttpProxy({
			url:basepath+'/HavaCacelRelateInfo.json'
		});	
var	cancelInfoStore = new Ext.data.Store({
			restful : true,
			proxy : cancelInfoProxy,
			reader :cancelInfoReader,
			recordType:cancelRecord
		});	
var	pagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
				fields : [ 'value', 'text' ],
				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
				         [ 100, '100条/页' ], [ 250, '250条/页' ],
				         [ 500, '500条/页' ] ]
			}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			forceSelection : true,
			width : 85
		});
var number = parseInt(pagesize_combo.getValue());
		/**
		 * 监听分页下拉框选择事件
		 */
		pagesize_combo.on("select", function(comboBox) {
			bbar.pageSize = parseInt(pagesize_combo.getValue()),
			searchFunction();
		});
		//分页工具条定义
var	bbar = new Ext.PagingToolbar({
			pageSize : number,
			store : cancelInfoStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', pagesize_combo]
		});		
var cancelgrid =new Ext.grid.GridPanel({//注销表格
			title:'已注销',
			height:180,
			frame:true,
			autoScroll : true,
			bbar:bbar,
			stripeRows : true, // 斑马线
			store:cancelInfoStore,
			loadMask:true,
			cm :columnmodel,
			region:'center',
			viewConfig:{
				forceFit:false,
				autoScroll:true
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
})
//---------------------------------------------------------------
//关联方查询面板
var searchFunctiondeclarant = function(){
			var parameters = declarantForm.getForm().getFieldValues(false);//查询框的值
			declarantgrid.store.removeAll();
			declarantgrid.store.load({
				params:{
					'condition':Ext.encode(parameters)
				}
			});
	};
var declarantForm = new Ext.form.FormPanel({
	height:90,
	labelWidth:100,//label的宽度
	labelAlign:'right',
	frame:true,
	autoScroll : true,
	region:'north',
	split:true,
	buttonAlign:'center',
	items:[{
				layout:'column',
				items:[{
					columnWidth:.3,
					layout:'form',
					items:[
						{xtype:'textfield',name:'DECLARANT_NAME',fieldLabel:'关联方名称',anchor:'60%'}
					]
				},{
					columnWidth:.3,
					layout:'form',
					items:[
						{name : 'DECLARANT_ATTR',xtype : 'combo',fieldLabel:'关联方属性',width: 200,mode : 'local',
		    			store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',anchor : '60%'}
					]
				}]
			}],
	buttons:[{
				text:'查询',
				handler:searchFunctiondeclarant
				},{
					text:'重置',
					handler:function(){
						declarantForm.getForm().reset();
						searchFunctiondeclarant();
					}
					}]
	});	

var panel1 = new Ext.Panel({
		height:450,
		html:'<div style="line-height:150%"> &ensp;&ensp;&ensp;&ensp;本人/本企业为贵行的关联人，本人/本企业承诺如下：<p/>'+
			'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;1）上述申报内容均为真实、准确、完整、有效； <p/>'+
			'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;2）若申报内容有任何变更，本人应于变更事项发生后十个工作日内向贵行申报。<p/>'+
			'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;本人若违反上述承诺，愿承担相应的法律后果，并对由此造成贵行直接和间接的损失予以赔偿。<p/></div>'});
var panel2 = new Ext.Panel({
		height:450,
		html:'<div style="line-height:150%"> &ensp;&ensp;&ensp;<p/>&ensp;&ensp;&ensp;填表人在此无条件并不可撤销地声明：<p/>'+
			'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;1. 本人系基于作为富邦华一银行的员工（含劳务派遣）身份（在任何情况下非基于富邦华一银行客户身份）完全自愿披露前述信息； <p/>'+
			'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;2. 本人同意富邦华一银行向其控股股东、实际控制人及其控制的其他企业披露前述信息，并授权富邦华一银行、富邦华一银行控股股东、实际控制人及其控制的其他企业在各自住所地法律法规允许的范围内使用前述信息,<p/>'+
			'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;  包括但不限于审查关联交易，向主管机关报送等； <p/>'+
			'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;3. 本人保证前述信息真实、完整、准确，不存在任何虚假记载、误导性陈述或遗漏；前述信息发生变化的，本人将及时告知富邦华一银行并不时更新本信息披露表；<p/>'+
			'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;4. 本人保证前述信息不涉及可能泄露国家秘密、危害国家安全、损害社会公共利益等的情形，不属于依法不得公开或对外提供的信息；<p/>'+
			'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;5. 本人保证前述信息的披露已取得信息权利人的同意（若需要），不会侵犯他人的合法权益；<p/>'+
			'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;6. 本人保证富邦华一银行、富邦华一银行控股股东、实际控制人及其控制的其他企业不会因合法使用前述信息而承担任何责任或受到任何损害；<p/>'+
			'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;7. 本人违反上述承诺与保证，自愿承担一切法律责任。<p/></div>'});	

var htmlpiece2='';
var getHtmlPice=function(record){//此方法用来拼接打印的html部分片段,changeType通过申报人的更改类型不同来控制不同的关系人表格类型
	if(record==null){
		return false;
	}
	var tempgrid;
	var checkbox1="";
	var checkbox2="";
	var allrecords="";
	var tempPice='';
	if(record.changeType=='2')//变更类型为修改
	{
		checkbox2="checked=true";
		tempgrid=getCustomerViewByTitle('申报人详情').grid;
		allrecords=tempgrid.getStore().data.items;//获取跟申报人有关的关系人所有记录
	}
		
	if(record.changeType=='1'){
	 	checkbox1="checked=true";
	 	tempgrid=getCustomerViewByTitle('新增申报人').grid;
	 	allrecords=tempgrid.getStore().data.items;//获取跟申报人有关的关系人所有记录
		}
	
	
	var val = translateLookupByKey("REL_WITH_BANK1",record.declarantBankRel);
	var htmlpice='<div class="yc-pfList">'+'<input type="checkbox" id="ck_01" '+checkbox1+'/><label for="ck_01">新增</label>'+
	'	</div>'+
	'	<div class="yc-pfList">'+
	'		<input type="checkbox" id="ck_02" '+checkbox2+'/><label for="ck_02">变更</label>'+
	'	</div>'+
	'</div>'+
	'<div class="yc-printForm">'+
	'	   <div class="yc-pflKey">关联人名称：</div>'+
	'	   <div class="yc-pflUL">'+record.declarantName+'</div>	'+
	'	   <div class="yc-pflKey">；关联人证件号：</div>'+
	'	   <div class="yc-pflUL">'+record.identNo+'</div>'+
	'</div>'+
	'<div class="yc-printForm">'+
	'	   <div class="yc-pflKey">与银行的关联关系：</div>'+
	'	   <div class="yc-pflUL">'+val+'</div>'+
	'	   <div class="yc-pflKey">；生效日期：</div>'+
	'	   <div class="yc-pflUL">'+Ext.util.Format.date(record.startDate,"Y-m-d")+'</div>'+
	'</div>	'+
	'<div class="yc-printForm">'+
	'	<table class="yc-pflTalbe">'+
	'		<tr>'+
	'			<th>关联人关联方属性</th>'+
	'			<th>姓名（名称）</th>'+
	'			<th>证件类型</th>'+
	'			<th>证件号（或法人代码）</th>'+
	'			<th>与申报人关联</th>'+
	'			<th>生效日期</th>'+
	'		</tr>';
	if(allrecords!=""){
	if(allrecords.length<5){
		for(var i=0;i<5;i++){
			if(i<allrecords.length){
			 	tempPice=tempPice+'<tr><td>'+ translateLookupByKey("XD000365",allrecords[i].data.PRIVY_ATTRIBUTE)+'</td><td>'+allrecords[i].data.PRIVY_NAME+'</td><td>'+translateLookupByKey("XD000040",allrecords[i].data.IDENT_TYPE)+'</td><td>'+allrecords[i].data.IDENT_NO+'</td><td>'+translateLookupByKey("RELATE_DECLARANT_REL1",allrecords[i].data.RELATE_DECLARANT_REL)+'</td><td>'+allrecords[i].data.EFFECT_DATE+'</td></tr>';
			}else{
				tempPice=tempPice+'	<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>';
			}
		}
	}
	if(allrecords.length>=5){
		for(var i=0;i<allrecords.length;i++){
			 	tempPice=tempPice+'<tr><td>'+ translateLookupByKey("XD000365",allrecords[i].data.PRIVY_ATTRIBUTE)+'</td><td>'+allrecords[i].data.PRIVY_NAME+'</td><td>'+translateLookupByKey("XD000040",allrecords[i].data.IDENT_TYPE)+'</td><td>'+allrecords[i].data.IDENT_NO+'</td><td>'+translateLookupByKey("RELATE_DECLARANT_REL1",allrecords[i].data.RELATE_DECLARANT_REL)+'</td><td>'+allrecords[i].data.EFFECT_DATE+'</td></tr>';
	}
	}
	}else{
		for(var i=0;i<5;i++){
			tempPice=tempPice+'	<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>';
		}
		
	}
	tempPice=tempPice+'</table></div>';
	htmlpice=htmlpice+tempPice;
	return htmlpice;
};
	
var panel3 = new Ext.FormPanel({

height:600,
autoScroll : true,
suspendWidth:150,
html:[htmlpiece1+htmlpiece2+htmlpiece3]

});				
				
				
var customerView = [{
		title:'关联方信息查询',
		hideTitle: true,
		layout: 'form',
		items: [declarantForm,declarantgrid,relategrid]
},{
	/**
	 * 自定义新增面板
	 */
	title:'新增关联方信息',
//	hideTitle:true,
	type: 'form',
	width: 300, 
	groups:[{
		columnCount : 2,
		fields : [
			{name:'PRIVY_NAME',allowBlank: false,text:'关系人姓名'},
			{name:'PRIVY_ATTRIBUTE',text:'关系人属性',allowBlank: false,translateType:'XD000365',listeners:{
							select:function(combo,record){
								var g= getCurrentView().contentPanel;
								declarntAttrpeSelect2(record.data.key,getCurrentView().contentPanel);
								}
							}},
			{name:'IDENT_TYPE',text:'证件类型',allowBlank: false,translateType:'XD000040'},//加载自然人
			{name:'IDENT_NO',allowBlank: false,text:'证件号码'},
			{name:'IDENT_TYPE2',text:'证件类型',allowBlank: false,translateType:'XD000040'},//加载法人
			{name:'TEL',vtype:'alphanum',text:'电话号码'},
			{name: 'RELATE_DECLARANT_REL', text : '与关联人关系', xtype: 'wcombotree',innerTree:'RELATE_DECLARANT_REL_TREE',allowBlank:false,showField:'text',hideField:'F_CODE',editable:false},
			{name:'EMAIL',vtype:'email',text:'邮件地址'},
			{name:'CONTACT_ADDR',text:'联系地址'},
			{name:'DECLARANT_BANK_REL',text:'与银行关联关系',translateType:'REL_WITH_BANK',allowBlank: false},
			{name :'IS_COMMECIAL_BANK',text:'是否为商业银行',allowBlank: false,translateType:'XD000346'},
			{name : 'STOCK_RATIO',text:'持股比例(%)',hidden:true},
			{name: 'DECLARANT_NAME',text:'关联方名称',singleSelect: false,allowBlank: false,callback : function(checkedNodes) {
				var tempform=getCurrentView().contentPanel.getForm();
				setfield(tempform,checkedNodes[0].data);//设置其他回填数据项
			}},
			{name:'DECLARANT_ATTR',text:'关联方（申报人）属性',translateType:'RELATE_PARTY_ATTR'},
			{name:'MAIN_ID',text:'主键ID',hidden:true},
			{name:'IDENT_TYPE1',text:'申报人证件类型',allowBlank:false,resutlFloat:'right',translateType:'XD000040'},
			{name:'IDENT_NO1',text:'申报人证件号码'},
			{name:'TEL1',text:'申报人电话号码'},
			{name:'EMAIL1',text:'申报人电子邮件'},
			{name:'CONTACT_ADDR1',text:'申报人联系地址'},
			{name:'DECLARANT_BANK_REL1',text:'申报人与银行关联关系',allowBlank: false,translateType:'REL_WITH_BANK1'}
			
//			{name:'RELATE_DECLARANT_REL',text:'与关联人关系',allowBlank: false,allowbank:false,translateType:'RELATE_DECLARANT_REL1'}
		],
		/**
		 *关联关系创建面板
		 */
		fn : function(PRIVY_NAME,PRIVY_ATTRIBUTE,IDENT_TYPE,IDENT_NO,IDENT_TYPE2,RELATE_DECLARANT_REL,TEL,EMAIL,
					  CONTACT_ADDR,DECLARANT_BANK_REL,IS_COMMECIAL_BANK,STOCK_RATIO,DECLARANT_NAME,DECLARANT_ATTR,
					  MAIN_ID,IDENT_TYPE1,IDENT_NO1,TEL1,EMAIL1,CONTACT_ADDR1,DECLARANT_BANK_REL1){
			DECLARANT_NAME.readOnly= true;
			DECLARANT_NAME.cls='x-readOnly';
			DECLARANT_ATTR.readOnly= true;
			DECLARANT_ATTR.cls='x-readOnly';
			IDENT_TYPE2.hidden=true;
			IDENT_TYPE1.readOnly= true;
			IDENT_TYPE1.cls='x-readOnly';
			IDENT_NO1.readOnly= true;
			IDENT_NO1.cls='x-readOnly';
			TEL1.readOnly= true;
			TEL1.cls='x-readOnly';
			EMAIL1.readOnly= true;
			EMAIL1.cls='x-readOnly';
			CONTACT_ADDR1.readOnly= true;
			CONTACT_ADDR1.cls='x-readOnly';
			DECLARANT_BANK_REL1.readOnly= true;
			DECLARANT_BANK_REL1.cls='x-readOnly';
			MAIN_ID.hidden= true;
			STOCK_RATIO.hidden=true;
			return [PRIVY_NAME,PRIVY_ATTRIBUTE,IDENT_TYPE,IDENT_NO,IDENT_TYPE2,RELATE_DECLARANT_REL,TEL,EMAIL,
					  CONTACT_ADDR,DECLARANT_BANK_REL,IS_COMMECIAL_BANK,STOCK_RATIO,DECLARANT_NAME,DECLARANT_ATTR,MAIN_ID,
					  IDENT_TYPE1,IDENT_NO1,TEL1,EMAIL1,CONTACT_ADDR1,DECLARANT_BANK_REL1
					];
					  }
	},{
		columnCount : 1,
		fields : [
			{name:'REMARK',text:'备注',xtype:'textarea'}],
		/**
		 *关联方关系备注
		 */
		fn : function(REMARK){
			REMARK.anchor = '97%';
			return [REMARK];
		}
	}],
	formButtons:[{
		/**
		 * 新增关联信息-保存按钮
		 */
		text : '保存',
		fn : function(contentPanel, baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示','输入格式错误或存在漏输入项,请重新输入！');
				return false;
			}
			if(flag==0){
//			relte_lock_flag=1;//在开始界面上新增关系人是，设置锁表，提醒用户提交信息
			}
			saverelatePartyInfoFn(baseform,'/AcrmFCrRelatePrivyInfoTemp.json');
		}
	},{
		 text : ' 返  回 ',
        	fn : function(contentPanel, baseform) {
         	if(flag==0){
            showCustomerViewByTitle('关联方信息查询');
			}if(flag==1){
			showCustomerViewByTitle('申报人详情');
			}if(flag==2){
				
			showCustomerViewByTitle('新增申报人');
			}
        }
        
	}]
},{

	/**
	 * 自定义新增面板
	 */
	title:'修改关系人信息',
//	hideTitle:true,
	type: 'form',
	groups:[{
		columnCount : 2,
		fields : [
			{name:'RELATE_ID',text:'关系人ID',hidden:true},
			{name:'PRIVY_NAME',allowBlank: false,text:'关系人姓名'},
			{name:'PRIVY_ATTRIBUTE',text:'关系人属性',allowBlank: false,translateType:'XD000365',listeners:{
				select:function(combo,record){
					var g= getCurrentView().contentPanel;
					declarntAttrpeSelect2(record.data.key,getCurrentView().contentPanel);
				}
			}},
			{name:'IDENT_TYPE',text:'证件类型',allowBlank: false,translateType:'XD000040'},
			{name:'IDENT_NO',allowBlank: false,text:'证件号码'},
			{name:'IDENT_TYPE2',text:'证件类型',allowBlank: false,translateType:'XD000040'},
			{name:'TEL',vtype:'alphanum',text:'电话号码'},
			{name: 'RELATE_DECLARANT_REL', text : '与关联人关系', xtype: 'wcombotree',innerTree:'RELATE_DECLARANT_REL_TREE',hideField:'F_CODE',allowBlank:false,showField:'text',editable:false},
			{name:'EMAIL',vtype:'email',text:'邮件地址'},
			{name:'CONTACT_ADDR',text:'联系地址'},
			{name:'DECLARANT_BANK_REL',allowBlank: false,text:'与银行关联关系',translateType:'REL_WITH_BANK'},
			{name :'IS_COMMECIAL_BANK',text:'是否为商业银行',translateType:'XD000346',hidden:true},
			{name : 'STOCK_RATIO',text:'持股比例(%)',hidden:true},
			{name: 'DECLARANT_NAME',text:'关联方名称',singleSelect: false,allowBlank: false,callback : function(checkedNodes) {
				var tempform=getCurrentView().contentPanel.getForm();
				setfield(tempform,checkedNodes[0].data);//设置其他回填数据项
			}},
			{name:'DECLARANT_ATTR',text:'关联方（申报人）属性',translateType:'RELATE_PARTY_ATTR'},
			{name:'MAIN_ID',text:'主键ID',hidden:true},
			{name:'IDENT_TYPE1',text:'申报人证件类型',allowBlank:false,resutlFloat:'right',translateType:'XD000040'},
			{name:'IDENT_NO1',text:'申报人证件号码'},
			{name:'TEL1',text:'申报人电话号码'},
			{name:'EMAIL1',text:'申报人电子邮件'},
			{name:'CONTACT_ADDR1',text:'申报人联系地址'},
			{name:'DECLARANT_BANK_REL1',allowBlank: false,text:'申报人与银行关联关系',translateType:'REL_WITH_BANK1'}
		],
		/**
		 *关联关系创建面板
		 */
		fn : function(RELATE_ID,PRIVY_NAME,PRIVY_ATTRIBUTE,IDENT_TYPE,IDENT_NO,IDENT_TYPE2,RELATE_DECLARANT_REL,TEL,EMAIL,
					  CONTACT_ADDR,DECLARANT_BANK_REL,IS_COMMECIAL_BANK,STOCK_RATIO,DECLARANT_NAME,DECLARANT_ATTR,
					  MAIN_ID,IDENT_TYPE1,IDENT_NO1,TEL1,EMAIL1,CONTACT_ADDR1,DECLARANT_BANK_REL1){
			DECLARANT_NAME.readOnly= true;
			DECLARANT_NAME.cls='x-readOnly';
			DECLARANT_ATTR.readOnly= true;
			DECLARANT_ATTR.cls='x-readOnly';
			IDENT_TYPE1.readOnly= true;
			IDENT_TYPE1.cls='x-readOnly';
			IDENT_TYPE2.hidden=true;
			IDENT_NO1.readOnly= true;
			IDENT_NO1.cls='x-readOnly';
			TEL1.readOnly= true;
			TEL1.cls='x-readOnly';
			EMAIL1.readOnly= true;
			EMAIL1.cls='x-readOnly';
			CONTACT_ADDR1.readOnly= true;
			CONTACT_ADDR1.cls='x-readOnly';
			DECLARANT_BANK_REL1.readOnly= true;
			DECLARANT_BANK_REL1.cls='x-readOnly';
			RELATE_ID.hidden= true;
			MAIN_ID.hidden= true;
			IS_COMMECIAL_BANK.hidden=true;
			STOCK_RATIO.hidden=true;
			return [RELATE_ID,PRIVY_NAME,PRIVY_ATTRIBUTE,IDENT_TYPE,IDENT_NO,IDENT_TYPE2,RELATE_DECLARANT_REL,TEL,EMAIL,
					  CONTACT_ADDR,DECLARANT_BANK_REL,IS_COMMECIAL_BANK,STOCK_RATIO,DECLARANT_NAME,DECLARANT_ATTR,MAIN_ID,
					  IDENT_TYPE1,IDENT_NO1,TEL1,EMAIL1,CONTACT_ADDR1,DECLARANT_BANK_REL1
					];
					  }
	},{
		columnCount : 1,
		fields : [
			{name:'REMARK',text:'备注',xtype:'textarea'}],
		/**
		 *关联方关系备注
		 */
		fn : function(REMARK){
			REMARK.anchor = '97%';
			return [REMARK];
		}
	}],
	formButtons:[{
		/**
		 * 新增关联信息-保存按钮
		 */
		text : '保存',
		fn : function(contentPanel, baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示','输入格式错误或存在漏输入项,请重新输入！');
				return false;
			}
			if(flag==0){
//			relte_lock_flag=1;//在开始界面上修改关系人是，设置锁表，提醒用户提交信息
			}
			saverelatePartyInfoFn(baseform,'/AcrmFCrRelatePrivyInfoTemp!saveData.json');
		}
	},{
		 text : ' 返  回 ',
         fn : function(contentPanel, baseform) {
         	if(flag==0){
            showCustomerViewByTitle('关联方信息查询');
			}if(flag==1){
			showCustomerViewByTitle('申报人详情');
			}if(flag==2){
			showCustomerViewByTitle('新增申报人');
			}
        }
	}]

},{
	title:'关联方申报',
//	hideTitle:JsContext.checkGrant('_dyDeclarant'),
	suspendWidth:700,
	type:'grid',
	url : basepath + '/DeclarantNameQuery.json',
	fields : {
		fields:[
			{name:'MAIN_ID',text:'关联方ID',hidden:true},
			{name: 'DECLARANT_NAME', text : '主申报人'},
			{name: 'DECLARANT_ATTR', text : '关联方属性',hidden:true},  
			{name: 'DECLARANT_BANK_REL', text : '与银行关联关系',renderer:function(value){
				var val = translateLookupByKey("REL_WITH_BANK",value);
				return val?val:"";
				}
			},  
			{name: 'IDENT_TYPE', text : '证件类型',hidden:true}, 
			{name: 'IDENT_NO', text : '申报人证件号码',hidden:true},
			{name: 'EMAIL', text : '电子邮箱'},
			{name: 'TEL', text : '电话号码',hidden:true},
			{name: 'CONTACT_ADDR', text : '联系地址',hidden:true},
			{name: 'IS_COMMECIAL_BANK', text : '是否为商业银行',renderer:function(value){
				var val = translateLookupByKey("XD000346",value);
				return val?val:"";
				},hidden:true},
			{name: 'STOCK_RATIO', text : '持股比例(%)',hidden:true},  
			{name: 'START_DATE', text : '生效日'},
			{name: 'CANCEL_STATE', text : '注销状态',hidden:true}
		]
	},
	gridButtons :[
	{
	text:'返回',
	fn:function(grid){
		showCustomerViewByTitle('关联方信息查询');
	}
	
	
	},{
		text : '新增',
		fn:function(grid){
			newaddMainDeclantInfoForm.getForm().findField('MAIN_ID').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('DECLARANT_ATTR').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('IDENT_TYPE').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('IDENT_TYPE2').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('DECLARANT_BANK_REL').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('IS_COMMECIAL_BANK').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('DECLARANT_NAME').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('IDENT_NO').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('EMAIL').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('CONTACT_ADDR').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('TEL').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('STOCK_RATIO').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('START_DATE').setValue('');
			newaddMainDeclantInfoForm.getForm().findField('CANCLE_CAUSE').setValue('');
			flag=0;
			Ext.getCmp('newadd').hide();
			showCustomerViewByTitle('新增申报人');
		}			
   },{
   		text:'主申报人详情',
   		fn:function(grid){
  			tmprecords =grid.getSelectionModel().getSelections();
  			if(tmprecords.length != 1){
					Ext.Msg.alert('提示','请选择一条数据！');
					return false;
				}
			Ext.getCmp('newaddetail').hide();
			MainDeclantInfoForm.getForm().findField('CANCLE_CAUSE').allowBlank=true;
  			showCustomerViewByTitle('申报人详情');
   		}
	}]
},{
	title:'申报人详情',
	hideTitle: true,
	height:520,
	type:'grid',
	autoScroll : true,

	url:basepath+'/AcrmFCrRelatePrivyInfoTemp.json',//主申报人信息查询url********************
	fields : {
		fields:[
			{name:'RELATE_ID',hidden:true},
			{name: 'PRIVY_ATTRIBUTE', text : '关系人属性',renderer:function(value){
				var val = translateLookupByKey("XD000365",value);
				return val?val:"";
				}
			},  
			{name: 'PRIVY_NAME', text : '姓名'}, 
			{name: 'IDENT_TYPE', text : '证件类型',renderer:function(value){
				var val = translateLookupByKey("XD000040",value);
				return val?val:"";
				}
			},
			{name: 'IDENT_NO', text : '证件号码'}, 
			{name: 'RELATE_DECLARANT_REL', text : '与申报人关系',renderer:function(value){
				var val = translateLookupByKey("RELATE_DECLARANT_REL1",value);
				return val?val:"";
				}
			}, 
			{name: 'DECLARANT_BANK_REL', text : '与银行关联关系',renderer:function(value){
				var val = translateLookupByKey("REL_WITH_BANK",value);
				return val?val:"";
				}
			}, 
			{name: 'CANCEL_STATE', text : '注销状态',hidden:true,renderer:function(value){
				var val = translateLookupByKey("CANCEL_STATE",value);
				return val?val:"";
				}
			},
			{name: 'IS_COMMECIAL_BANK',renderer:function(value){
				var val = translateLookupByKey("XD000346",value);
				return val?val:"";
				}, text : '是否为商业银行'}, 
			{name: 'EFFECT_DATE', text : '生效日'} 
		]
	},gridButtons :[{
		text : '新增',
		id:'newaddetail',
		fn:function(grid){
			flag=1;
			showCustomerViewByTitle('新增关联方信息');
		}
		},{
		text : '注销',//注销关联人
		id:'canceldetail',
		hidden:JsContext.checkGrant('relateprivyCancel'),//系统管理员才能使用此功能,注销关系人
		fn:function(grid){
				var tempdata = grid.getSelectionModel().getSelections();
				if(tempdata.length != 1){
					Ext.Msg.alert('提示','请选择一条数据！');
					return false;
				}
//				else{
//					if(tempdata[0].data.CANCEL_STATE == '2'){
//						Ext.Msg.alert('提示','只能修改未注销状态的关联方信息！');
//						return false;
//					}
					else{
						Ext.Ajax.request({
							url:basepath + '/AcrmFCrRelatePrivyInfoTemp!updateCancelStat.json',
							method:"POST",
							params : {"RELATE_ID":tempdata[0].data.RELATE_ID},
							waitMsg : '正在保存数据,请等待...', 
							success:function(){
								var view =getCurrentView();
								view.setParameters({
									MAIN_ID:tmprecords[0].data.MAIN_ID
								});
								cancelInfoStore.load({
									params:{
										'MAIN_ID':tmprecords[0].data.MAIN_ID,
										start:0,
										limit: parseInt(pagesize_combo.getValue())
									}
								});
								Ext.Msg.alert("提示","操作成功");
							},
							failure:function(response){
									Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
								}
						});
						
					}
				}
			
//		}
   }
   ]

},{
	title:'新增申报人',
	hideTitle: true,
	height:850,
	type:'grid',
	url:basepath+'/AcrmFCrRelatePrivyInfoTemp.json',//主申报人信息查询url
	fields : {
		fields:[
			{name:'RELATE_ID',hidden:true},
			{name: 'PRIVY_ATTRIBUTE', text : '关系人属性',renderer:function(value){
				var val = translateLookupByKey("XD000365",value);
				return val?val:"";
				}
			},  
			{name: 'PRIVY_NAME', text : '姓名'}, 
			{name: 'IDENT_TYPE', text : '证件类型',renderer:function(value){
				var val = translateLookupByKey("XD000040",value);
				return val?val:"";
				}
			},
			{name: 'IDENT_NO', text : '证件号码'}, 
			{name: 'RELATE_DECLARANT_REL', text : '与申报人关系',renderer:function(value){
				var val = translateLookupByKey("RELATE_DECLARANT_REL1",value);
				return val?val:"";
				}
			}, 
			{name: 'DECLARANT_BANK_REL', text : '与银行关联关系',renderer:function(value){
				var val = translateLookupByKey("REL_WITH_BANK",value);
				return val?val:"";
				}
			},  
			{name: 'IS_COMMECIAL_BANK', renderer:function(value){
				var val = translateLookupByKey("XD000346",value);
				return val?val:"";
				},text : '是否为商业银行'}, 
			{name: 'EFFECT_DATE', text : '生效日'} 
		]
	},gridButtons :[{
		text : '增加',
		hidden:true,
		id:'newadd',
		fn:function(grid){
			flag=2;
			showCustomerViewByTitle('新增关联方信息');
		}
		}]
	
},{
	title:'确认面板1',
	suspendWidth:860,
	hideTitle: true,
	height:850,
	items:[	panel1],
	buttonAlign:'center',
		buttons:[{
			text:'同意',
			handler:function(){
				Ext.getCmp('print').hide();
				Ext.getCmp('return').hide();
				Ext.getCmp('agree').show();
				Ext.getCmp('refuse').show();
				showCustomerViewByTitle('确认面板2');
			}
		},{
			text:'不同意',
			handler:function(){
				showCustomerViewByTitle('关联方申报');
				
			}
		}]
},{
	title:'确认面板2',
	hideTitle: true,
	suspendWidth:860,
	height:850,
	items:[	panel2],
	buttonAlign:'center',
		buttons:[{
			text:'同意',
			id:'agree',
			handler:function(){
//					Ext.Ajax.request({
//					url : basepath + '/AcrmFCrRelatePrivyInfoTemp!initFlow.json',
//					method : 'GET',
//					params : {'MAIN_ID':declarantId},
//					waitMsg : '正在提交申请,请等待...',										
//					success : function(response) {
//						var ret = Ext.decode(response.responseText);
//						var instanceid = ret.instanceid;//流程实例ID
//						var currNode = ret.currNode;//当前节点
//						var nextNode = ret.nextNode;//下一步节点
//						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
//					},
//					failure : function() {
//						Ext.Msg.alert('提示', '操作失败');
//						reloadCurrentData();
//					}
//					});
					Ext.getCmp('agree').hide();
					Ext.getCmp('refuse').hide();
					Ext.getCmp('print').show();
					Ext.getCmp('return').show();
			}
		},{
			text:'不同意',
			id:'refuse',
			handler:function(){
				showCustomerViewByTitle('关联方申报');
			}
		},{
			text:'打印预览',
			id:'print',
			handler:function(){
				showCustomerViewByTitle('确认面板3');
			}
		},{
			text:'返回',
			id:'return',
			handler:function(){
				showCustomerViewByTitle('关联方申报');
				}
		}]
},{
	title:'确认面板3',
	suspendWidth:150,
	type: 'form',
	hideTitle: true,
	items:[	panel3],
	buttonAlign:'center',
		buttons:[{
			text:'打印',
			id:'realprint',
			handler:function(){
				//真正的打印功能
			}
		},{
			text:'返回',
			handler:function(){
				showCustomerViewByTitle('关联方申报');
				}
		}]

}];




var viewshow = function(theView){
	if(theView._defaultTitle == '关联方信息查询'){
		declarantgrid.on('rowclick',function(grid,row,e){
//			if(relte_lock_flag==1){
//				Ext.Msg.alert('提示','您对部分关系人操作需进行提交！');
//					return false;
//			}
			main_ids = grid.getSelectionModel().getSelections()[0].data.MAIN_ID;
			recorddata =grid.getSelectionModel().getSelections();
			if(recorddata[0].data.CANCEL_STATE=='2'){//所选择的申报人为已注销的
				Ext.Msg.alert('提示','您所选择的关联方已注销！');
				return false;
			}
			relateInfoStore.load({
					params:{
						'MAIN_ID':main_ids,
						start:0,
						limit: parseInt(relatepagesize_combo.getValue())
					}
			});
		})
		
	}
	if(theView._defaultTitle=='关联方申报'){
			theView.grid.on('cellclick',function(grid,row,column,e){
				if(column == 3){
					fieldvalue =grid.getView().getCell(row ,column).innerText;
					tmprecords =grid.getSelectionModel().getSelections();
					Ext.getCmp('newaddetail').hide();
					Ext.getCmp('canceldetail').hide();
					MainDeclantInfoForm.getForm().findField('CANCLE_CAUSE').allowBlank=true;
					showCustomerViewByTitle('申报人详情');
//					cancelInfoStore.load({
//						params:{
//							'DECLARANT_NAME':fieldvalue,
//							start:0,
//							limit: parseInt(pagesize_combo.getValue())
//						}
//				});
				}
		});
	}

};
//关联方式申报人查询面板
var searchFunction = function(){
			var parameters = relqueryForm.getForm().getFieldValues(false);//查询框的值
			var theView=getCustomerViewByTitle('关联方申报');
			theView.grid.store.removeAll();
			theView.grid.store.load({
				params:{
				    'condition':Ext.encode(parameters)
					
				}
			});
	};
var newaddMainDeclantInfoForm = new Ext.form.FormPanel({
	title : '主申报人信息',
	frame : true,
	height:240,
	autoScroll : true,
	split : true,
	items : [{
	    layout : 'column',
	    items : [
	    	{
	    		layout:'form',
	    		columnWidth:.5,
    			items:[
    				{name:'MAIN_ID',xtype:'textfield',hidden:true},
			    	{name : 'DECLARANT_ATTR',xtype : 'combo',fieldLabel:'<font color="red">*</font>关联方属性',readOnly:true,width: 280,cls:'x-readOnly',mode : 'local',
		    			store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',width: 280,allowBlank:false,anchor : '60%',
			    		listeners:{
							select:function(combo,record){
								declarntAttrpeSelect1(record.data.key,newaddMainDeclantInfoForm);
								}
							}
		    			},
			    	{name : 'IDENT_TYPE',xtype : 'combo',fieldLabel:'<font color="red">*</font>证件类型',readOnly:true,width: 280,cls:'x-readOnly',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',allowBlank:false,anchor : '60%'},
			    	{name : 'IDENT_TYPE2',xtype : 'combo',fieldLabel:'证件类型',readOnly:true,width: 280,cls:'x-readOnly',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',anchor : '60%',hidden:true},
			    	{name : 'DECLARANT_BANK_REL',xtype : 'combo',fieldLabel:'<font color="red">*</font>与银行关联关系',readOnly:true,cls:'x-readOnly',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',allowBlank:false,anchor : '60%'},
			    	{name : 'IS_COMMECIAL_BANK',xtype : 'combo',width: 280,fieldLabel:'是否为商业银行',readOnly:true,cls:'x-readOnly',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',hidden:true,anchor : '60%'},
	    			{name : 'TEL',xtype : 'textfield',fieldLabel:'电话号码',vtype:'alphanum',readOnly:true,cls:'x-readOnly',anchor : '60%'}
	    			]
	    			},{
	    			layout:'form',
	    			columnWidth:.5,
	    			items:[
		    		 {name : 'DECLARANT_NAME',xtype : 'textfield',fieldLabel:'<font color="red">*</font>申报人名字',width: 280,allowBlank:false,anchor : '60%'},
		    		 {name : 'IDENT_NO',xtype : 'textfield',fieldLabel:'<font color="red">*</font>证件号码',allowBlank:false,width: 280,anchor : '60%'},
		    		 {name : 'STOCK_RATIO',xtype : 'textfield',fieldLabel:'持股比例(%)',width: 280,hidden:true,anchor : '60%'},
		    		 {name : 'EMAIL',xtype : 'textfield',fieldLabel:'邮件地址',vtype:'email',anchor : '60%'},
		    		 {name : 'CONTACT_ADDR',xtype : 'textfield',fieldLabel:'联系地址',width: 280,anchor : '60%'},
		    		 {name : 'START_DATE', xtype: 'datefield',fieldLabel : '<font color="red">*</font>关联起始日期',width: 280,format:'Y-m-d',allowBlank:false,anchor : '60%'},
		    		 {name:'CHANGE_TYPE',xtype:'textfield',value:'1',hidden:true}
		    		 ]
	    		},{
				layout:'form',
				columnWidth : 1,
				items:[
					{name :'CANCLE_CAUSE',xtype:'textarea',fieldLabel:'变更说明',hidden:true,anchor:'80%'}
					]
				}	
		]
	}
	],
    buttonAlign : 'center',
    buttons : [{
        text : ' 保  存 ',
        id:'newsave',
        handler : function() {
        	//点了保存才能显示grid的新增按钮
            if (!newaddMainDeclantInfoForm.getForm().isValid()) {
                Ext.MessageBox.alert('提示', '请正确输入各项必要信息！');
                return false;
            }
        	 tmprecords = translateDataKey(newaddMainDeclantInfoForm.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);
        	delarantData =tmprecords;
        	Ext.Ajax.request({
                url : basepath + '/acrmfcrdeclarantinfoTemp!saveData.json',
                method : 'POST',
                params :tmprecords,
                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
              	success : function(){
          			Ext.Ajax.request({
						url : basepath+'/acrmfcrdeclarantinfoTemp!getPid.json',
						method : 'GET',
						success : function(response) {
							var nodeArra = Ext.util.JSON.decode(response.responseText);
							declarantId = nodeArra.pid;
							Ext.getCmp('newadd').show();
							newaddMainDeclantInfoForm.buttons[0].hide();
							newaddMainDeclantInfoForm.buttons[2].show();
							setEditableN(newaddMainDeclantInfoForm);
							Ext.Msg.alert('提示', '操作成功！');
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败');
							hideCurrentView();
							}
					});
				},
				failure : function() {
							Ext.Msg.alert('提示', '操作失败');
							hideCurrentView();
							}
       		 })
        }
	},{
		 text : ' 返  回 ',
        handler : function(contentPanel, baseform) {
        	showCustomerViewByTitle('关联方申报');
        }
	},{
        	text:'确   认',
        	id:'confirm',
        	handler:function(){
        		showCustomerViewByTitle('确认面板1');
//        		弹出确认框。。。
        	}
        }
	]
	
});		
var MainDeclantInfoForm = new Ext.form.FormPanel({

	title : '主申报人信息',
	frame : true,
	height:200,
	autoScroll : true,
	split : true,
	items : [{
	    layout : 'column',
	    items : [
	    	{
	    		layout:'form',
	    		columnWidth:.5,
    			items:[
    				{name:'MAIN_ID',xtype:'textfield',hidden:true},
			    	{name : 'DECLARANT_ATTR',xtype : 'combo',fieldLabel:'<font color="red">*</font>关联方属性',readOnly:true,cls:'x-readOnly',mode : 'local',
		    			store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',allowBlank:false,anchor : '60%',
			    		listeners:{
							select:function(combo,record){
								declarntAttrpeSelect1(record.data.key,MainDeclantInfoForm);//主申报人属性变更事件
								}
							}
		    			},
			    	{name : 'IDENT_TYPE',xtype : 'combo',fieldLabel:'<font color="red">*</font>证件类型',readOnly:true,width: 280,cls:'x-readOnly',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',allowBlank:false,anchor : '60%'},
			    	{name : 'IDENT_TYPE2',xtype : 'combo',fieldLabel:'证件类型',readOnly:true,width: 280,cls:'x-readOnly',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',anchor : '60%',hidden:true},
			    	{name : 'DECLARANT_BANK_REL',xtype : 'combo',fieldLabel:'<font color="red">*</font>与银行关联关系',readOnly:true,cls:'x-readOnly',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',allowBlank:false,anchor : '60%'},
			    	{name : 'IS_COMMECIAL_BANK',xtype : 'combo',fieldLabel:'是否为商业银行',width: 280,valueField:'key',readOnly:true,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',cls:'x-readOnly',hidden:true,anchor : '60%'},
	    			{name : 'TEL',xtype : 'textfield',fieldLabel:'电话号码',vtype:'alphanum',width: 280,readOnly:true,cls:'x-readOnly',anchor : '60%'},
	    			{name : 'CANCEL_STATE',xtype : 'textfield',fieldLabel:'注销状态',width: 280,readOnly:true,cls:'x-readOnly',anchor : '60%',hidden:true}
	    		]
	    	},{
	    		layout:'form',
	    		columnWidth:.5,
	    		items:[
	    		 {name : 'DECLARANT_NAME',xtype : 'textfield',fieldLabel:'<font color="red">*</font>申报人名字',readOnly:true,cls:'x-readOnly',allowBlank:false,anchor : '60%'},
	    		 {name : 'IDENT_NO',xtype : 'textfield',fieldLabel:'<font color="red">*</font>证件号码',readOnly:true,cls:'x-readOnly',allowBlank:false,anchor : '60%'},
	    		 {name : 'STOCK_RATIO',xtype : 'textfield',fieldLabel:'持股比例%',readOnly:true,cls:'x-readOnly',hidden:true,anchor : '60%'},
	    		 {name : 'EMAIL',xtype : 'textfield',fieldLabel:'邮件地址',vtype:'email',readOnly:true,cls:'x-readOnly',anchor : '60%'},
	    		 {name : 'CONTACT_ADDR',xtype : 'textfield',fieldLabel:'联系地址',readOnly:true,cls:'x-readOnly',anchor : '60%'},
	    		 {name : 'START_DATE', xtype: 'datefield',fieldLabel : '<font color="red">*</font>关联起始日期',readOnly:true,cls:'x-readOnly',format:'Y-m-d',allowBlank:false,anchor : '60%'},
	    		 {name:'CHANGE_TYPE',xtype:'textfield',value:'2',hidden:true}
	    		 ]
	    	},{
				layout:'form',
				columnWidth : 1,
				items:[
					{name :'CANCLE_CAUSE',xtype:'textarea',fieldLabel:'变更说明',readOnly:true,cls:'x-readOnly',hidden:true,width: 480,anchor:'80%'}
					]
				}	
		]
	}
	],
    buttonAlign : 'center',
    buttons : [{
        text : ' 保  存 ',
        id:'newsave',
        handler : function() {
        	//点了保存才能显示grid的新增按钮
            if (!MainDeclantInfoForm.getForm().isValid()) {
                Ext.MessageBox.alert('提示', '请正确输入各项必要信息！');
                return false;
            }
        	var tempData = translateDataKey(MainDeclantInfoForm.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);
        	delarantData =tempData;
        	declarantId = tempData.mainId;
        	Ext.Ajax.request({
                url : basepath + '/acrmfcrdeclarantinfoTemp!saveData.json',
                method : 'POST',
                params :tempData,
                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
              	success : function(){
          			Ext.Ajax.request({
						url : basepath+'/acrmfcrdeclarantinfoTemp!getPid.json',
						method : 'GET',
						success : function(response) {
							var nodeArra = Ext.util.JSON.decode(response.responseText);
							Ext.getCmp('newaddetail').show();
							Ext.getCmp('canceldetail').show();
							MainDeclantInfoForm.buttons[0].hide();
							MainDeclantInfoForm.buttons[3].show();
							setEditableN(MainDeclantInfoForm);
							Ext.Msg.alert('提示', '操作成功！');
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败');
							hideCurrentView();
							}
					});
				},
				failure : function() {
							Ext.Msg.alert('提示', '操作失败');
							hideCurrentView();
							}
				
       		 })
        }
	},{
		 text : ' 返  回 ',
        handler : function(contentPanel, baseform) {
        	showCustomerViewByTitle('关联方申报');
        }
	},{
		text : ' 变  更',
        handler : function() {
        	MainDeclantInfoForm.buttons[0].show();//在点击变更时，确认按钮显示
        	MainDeclantInfoForm.getForm().findField('START_DATE').allowBlank=false;
        	MainDeclantInfoForm.getForm().findField('CANCLE_CAUSE').allowBlank=false;
        	MainDeclantInfoForm.getForm().findField('CANCLE_CAUSE').setVisible(true);
        	MainDeclantInfoForm.buttons[2].hide();
        	MainDeclantInfoForm.buttons[3].hide();
//        	cancelgrid.hide();
        	setEditableY(MainDeclantInfoForm);
        	
        }
	},{
        	text:'确   认',
        	handler:function(){
        		showCustomerViewByTitle('确认面板1');
	//        		弹出确认框。。。
        	}
	}]
});	
var relqueryForm = new Ext.form.FormPanel({
	height:80,
	labelWidth:100,//label的宽度
	labelAlign:'right',
	frame:true,
	autoScroll : true,
	region:'north',
	split:true,
	buttonAlign:'center',
	items:[{
		layout:'form',
		items:[{
			layout:'form',
			items:[
				{xtype:'textfield',name:'DECLARANT_NAME',fieldLabel:'申报人'}
			]
		}
		]
	}],
	buttons:[{
				text:'查询',
				handler:searchFunction
				},{
					text:'重置',
					handler:function(){
						relqueryForm.getForm().reset();
						searchFunction();
					}
					}]
	});
/**

 * 结果域面板滑入前触发,系统提供listener事件方法
 * @param {} theView
 * @return {Boolean}
 */
var firstLayout=-1;
var secendLayout=-1;
var thridLayout=-1;
var beforeviewshow = function(theView){
	if(theView._defaultTitle == '关联方信息查询'){
		declarantForm.getForm().findField('DECLARANT_ATTR').bindStore(findLookupByType('RELATE_PARTY_ATTR'));
		declarantInfoStore.load({
			params:{
				'DECLARANT_NAME':'',
				start:0,
				limit: parseInt(declarantpagesize_combo.getValue())
			}
		});
	}
	if(theView._defaultTitle == '修改关系人信息'){
		var tempdata = relategrid.getSelectionModel().getSelections()[0];
		theView.contentPanel.getForm().loadRecord(tempdata);
		var privyattribute=tempdata.data.PRIVY_ATTRIBUTE;
		if(privyattribute=='A'||privyattribute=='R'){//若当前信息为法人
			tempFn(true,theView.contentPanel);
			theView.contentPanel.getForm().findField('IDENT_TYPE2').setValue(tempdata.data.IDENT_TYPE);
			theView.contentPanel.getForm().findField('IDENT_TYPE').setValue('');
		}else{
			tempFn(false,theView.contentPanel);
			theView.contentPanel.getForm().findField('IDENT_TYPE').setValue(tempdata.data.IDENT_TYPE);
			theView.contentPanel.getForm().findField('IDENT_TYPE2').setValue('');
		}
		setfield(theView.contentPanel.getForm(),recorddata[0].data);
		theView.contentPanel.getForm().findField('DECLARANT_NAME').setValue(recorddata[0].data.DECLARANT_NAME);
		
		}
	if(theView._defaultTitle == '关联方申报'){
		if(firstLayout < 0){
			firstLayout++;
			theView.remove(theView.grid,false);
			theView.doLayout();
			theView.grid.setHeight(theView.grid.getHeight() - 90);
			theView.add({
				xtype:'panel',
				layout:'form',
				items:[relqueryForm,theView.grid]
			});
			theView.doLayout();
		}
		theView.setParameters({
			MAIN_ID:'MAIN_ID'
		});
	}
	if(theView._defaultTitle=='新增申报人'){
		if(secendLayout < 0){
			secendLayout++;
			theView.remove(theView.grid,false);
			theView.doLayout();
			theView.add({
			xtype:'panel',
			layout:'form',
			items:[newaddMainDeclantInfoForm,theView.grid]
			});
			theView.doLayout();
		}
		if(flag==2){//由新增关系人后返回界面
			Ext.getCmp('newadd').show();
				newaddMainDeclantInfoForm.buttons[0].hide();
				newaddMainDeclantInfoForm.buttons[2].show();
				setEditableN(newaddMainDeclantInfoForm);
			}else{
				newaddMainDeclantInfoForm.buttons[0].show();
				newaddMainDeclantInfoForm.buttons[2].hide();
				setEditableY(newaddMainDeclantInfoForm);
			}
		
		newaddMainDeclantInfoForm.getForm().findField('DECLARANT_ATTR').bindStore(findLookupByType('RELATE_PARTY_ATTR'));
		newaddMainDeclantInfoForm.getForm().findField('IDENT_TYPE').bindStore(findLookupByType('XD000040'));
		newaddMainDeclantInfoForm.getForm().findField('IDENT_TYPE2').bindStore(findLookupByType('XD000040'));
		newaddMainDeclantInfoForm.getForm().findField('DECLARANT_BANK_REL').bindStore(findLookupByType('REL_WITH_BANK'));
		newaddMainDeclantInfoForm.getForm().findField('IS_COMMECIAL_BANK').bindStore(findLookupByType('XD000346'));
		
		newaddMainDeclantInfoForm.getForm().findField('CANCLE_CAUSE').setVisible(false);
		if(flag!=2){
			declarantId=null;
		}
		theView.setParameters({
		MAIN_ID:declarantId
	});
}
	if(theView._defaultTitle=='申报人详情'){
		if(thridLayout < 0){
			thridLayout++;
			theView.remove(theView.grid,false);
			theView.doLayout();
			theView.add({
			xtype:'panel',
			layout:'form',
			items:[MainDeclantInfoForm,theView.grid,cancelgrid]
			});
			theView.grid.setHeight(theView.grid.getHeight() - 380);
			theView.grid.setAutoScroll(true);
			theView.doLayout();
			theView.setAutoScroll(true);
		}
		Ext.getCmp('newaddetail').hide();
		Ext.getCmp('canceldetail').hide();
		MainDeclantInfoForm.getForm().findField('DECLARANT_ATTR').bindStore(findLookupByType('RELATE_PARTY_ATTR'));
		MainDeclantInfoForm.getForm().findField('IDENT_TYPE').bindStore(findLookupByType('XD000040'));
		MainDeclantInfoForm.getForm().findField('IDENT_TYPE2').bindStore(findLookupByType('XD000040'));
		MainDeclantInfoForm.getForm().findField('DECLARANT_BANK_REL').bindStore(findLookupByType('REL_WITH_BANK'));
		MainDeclantInfoForm.getForm().findField('IS_COMMECIAL_BANK').bindStore(findLookupByType('XD000346'));
		if(flag==1){//由新增关系人后返回界面
			MainDeclantInfoForm.buttons[0].hide();//在隐藏保存按钮
			MainDeclantInfoForm.buttons[1].show();
			MainDeclantInfoForm.buttons[2].hide();
			MainDeclantInfoForm.buttons[3].show();
			}else{
				MainDeclantInfoForm.buttons[0].hide();//在隐藏保存按钮
				MainDeclantInfoForm.buttons[1].show();
				MainDeclantInfoForm.buttons[2].show();
				MainDeclantInfoForm.buttons[3].hide();
			}
		MainDeclantInfoForm.getForm().loadRecord(tmprecords[0]);
		var declarntattr=MainDeclantInfoForm.getForm().findField('DECLARANT_ATTR').getValue();
		if(declarntattr=='21'||declarntattr=='22'){//若当前信息为法人
			tempFn(true,MainDeclantInfoForm);
			MainDeclantInfoForm.getForm().findField('IDENT_TYPE2').setValue(tmprecords[0].data.IDENT_TYPE);
			MainDeclantInfoForm.getForm().findField('IDENT_TYPE').setValue('');
		}else{
			tempFn(false,MainDeclantInfoForm);
			MainDeclantInfoForm.getForm().findField('IDENT_TYPE').setValue(tmprecords[0].data.IDENT_TYPE);
			MainDeclantInfoForm.getForm().findField('IDENT_TYPE2').setValue('');
		}
		setEditableN(MainDeclantInfoForm);
		theView.setParameters({
			MAIN_ID:tmprecords[0].data.MAIN_ID
		});
		cancelInfoStore.load({
		params:{
			'MAIN_ID':tmprecords[0].data.MAIN_ID,
			start:0,
			limit: parseInt(pagesize_combo.getValue())
		}
		});
	}
	if(theView._defaultTitle=='新增关联方信息'){
		if(flag!=0){
		theView.contentPanel.getForm().findField('MAIN_ID').setValue(declarantId);
		theView.contentPanel.getForm().findField('DECLARANT_NAME').setValue(delarantData.declarantName);
		theView.contentPanel.getForm().findField('DECLARANT_ATTR').setValue(delarantData.declarantAttr);
		if(delarantData.declarantAttr=='21'||delarantData.declarantAttr=='22'){//如果属性为法人
			theView.contentPanel.getForm().findField('IDENT_TYPE1').setValue(delarantData.identType2);
			}else{
				theView.contentPanel.getForm().findField('IDENT_TYPE1').setValue(delarantData.identType);
			}
		theView.contentPanel.getForm().findField('IDENT_NO1').setValue(delarantData.identNo);
		theView.contentPanel.getForm().findField('TEL1').setValue(delarantData.tel);
		theView.contentPanel.getForm().findField('EMAIL1').setValue(delarantData.email);
		theView.contentPanel.getForm().findField('CONTACT_ADDR1').setValue(delarantData.contactAddr);
		theView.contentPanel.getForm().findField('DECLARANT_BANK_REL1').setValue(delarantData.declarantBankRel);
		theView.contentPanel.getForm().findField('REMARK').setValue(delarantData.REMARK);
		}
		if(flag==0){
		theView.contentPanel.getForm().findField('MAIN_ID').setValue(recorddata[0].data.MAIN_ID);
		theView.contentPanel.getForm().findField('DECLARANT_NAME').setValue(recorddata[0].data.DECLARANT_NAME);
		theView.contentPanel.getForm().findField('DECLARANT_ATTR').setValue(recorddata[0].data.DECLARANT_ATTR);
		if(recorddata[0].data.DECLARANT_ATTR=='21'||recorddata[0].data.DECLARANT_ATTR=='22'){//如果属性为法人
			theView.contentPanel.getForm().findField('IDENT_TYPE1').setValue(recorddata[0].data.IDENT_TYPE);
			}else{
				theView.contentPanel.getForm().findField('IDENT_TYPE1').setValue(recorddata[0].data.IDENT_TYPE);
			}
		theView.contentPanel.getForm().findField('IDENT_NO1').setValue(recorddata[0].data.IDENT_NO);
		theView.contentPanel.getForm().findField('TEL1').setValue(recorddata[0].data.TEL);
		theView.contentPanel.getForm().findField('EMAIL1').setValue(recorddata[0].data.EMAIL);
		theView.contentPanel.getForm().findField('CONTACT_ADDR1').setValue(recorddata[0].data.CONTACT_ADDR);
		theView.contentPanel.getForm().findField('DECLARANT_BANK_REL1').setValue(recorddata[0].data.DECLARANT_BANK_REL);
		theView.contentPanel.getForm().findField('REMARK').setValue(recorddata[0].data.REMARK);
		}
	}
	if(theView._defaultTitle=='确认面板3'){//打开打印预览按钮
		htmlpiece2=getHtmlPice(delarantData);
			theView.removeAll();
			theView.doLayout();
		var panel4 =new Ext.FormPanel({//重新构造打印Html代码，拼接成打印页面
			height:450,
			autoScroll : true,
			suspendWidth:150,
			html:[htmlpiece1+htmlpiece2+htmlpiece3]
			});
			theView.add({
			xtype:'panel',
			layout:'form',
			items:[panel4],
			buttonAlign:'center',
			buttons:[{
				text:'打印',
				id:'realprint',
				handler:function(){
				//真正的打印功能
			}
		},{
		text:'返回',
		handler:function(){
			showCustomerViewByTitle('关联方信息查询');
			}
		}]
			});
			theView.doLayout();
	}
};
var saverelatePartyInfoFn=function(baseform,saveUrl){
	var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
	Ext.Msg.wait('正在保存，请稍等...', '提示');
	Ext.Ajax.request({
		url : basepath + saveUrl,
		method : 'POST',
		params :commintData,
		waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		success : function() {
			Ext.Msg.alert('提示', '操作成功！');
			if(flag==0){
			relateInfoStore.load({
					params:{
						'MAIN_ID':recorddata[0].data.MAIN_ID,
						start:0,
						limit: parseInt(relatepagesize_combo.getValue())
					}
			});	
            showCustomerViewByTitle('关联方信息查询');
			}if(flag==1){
				showCustomerViewByTitle('申报人详情');
				}if(flag==2){
					showCustomerViewByTitle('新增申报人');
					}
		},
		failure : function() {
			Ext.Msg.alert('提示', '操作失败');
			showCustomerViewByTitle('关联方信息查询');
		}
	})
};
var setfield = function(form,checkedNodes){//选定申报人，回填与申报人有关的其他字段
	form.findField('MAIN_ID').setValue(checkedNodes.MAIN_ID);
	form.findField('DECLARANT_ATTR').setValue(checkedNodes.DECLARANT_ATTR);
	form.findField('IDENT_TYPE1').setValue(checkedNodes.IDENT_TYPE);
	form.findField('IDENT_NO1').setValue(checkedNodes.IDENT_NO);
	form.findField('TEL1').setValue(checkedNodes.TEL);
	form.findField('EMAIL1').setValue(checkedNodes.EMAIL);
	form.findField('CONTACT_ADDR1').setValue(checkedNodes.CONTACT_ADDR);
	form.findField('DECLARANT_BANK_REL1').setValue(checkedNodes.DECLARANT_BANK_REL);
	//form.findField('REMARK').setValue(checkedNodes.REMARK);
}
var setEditableY=function(form){
		form.getForm().findField('DECLARANT_ATTR').setReadOnly(false);
		form.getForm().findField('DECLARANT_ATTR').removeClass('x-readOnly');	
		form.getForm().findField('IDENT_TYPE').setReadOnly(false);
		form.getForm().findField('IDENT_TYPE').removeClass('x-readOnly');
		form.getForm().findField('IDENT_TYPE2').setReadOnly(false);
		form.getForm().findField('IDENT_TYPE2').removeClass('x-readOnly');
		form.getForm().findField('DECLARANT_BANK_REL').setReadOnly(false);
		form.getForm().findField('DECLARANT_BANK_REL').removeClass('x-readOnly');
		form.getForm().findField('IS_COMMECIAL_BANK').setReadOnly(false);
		form.getForm().findField('IS_COMMECIAL_BANK').removeClass('x-readOnly');
		form.getForm().findField('DECLARANT_NAME').setReadOnly(false);
		form.getForm().findField('DECLARANT_NAME').removeClass('x-readOnly');
		form.getForm().findField('IDENT_NO').setReadOnly(false);
		form.getForm().findField('IDENT_NO').removeClass('x-readOnly');
		form.getForm().findField('STOCK_RATIO').setReadOnly(false);
		form.getForm().findField('STOCK_RATIO').removeClass('x-readOnly');
		form.getForm().findField('START_DATE').setReadOnly(false);
		form.getForm().findField('START_DATE').removeClass('x-readOnly');
		form.getForm().findField('CANCLE_CAUSE').setReadOnly(false);
		form.getForm().findField('CANCLE_CAUSE').removeClass('x-readOnly');
		form.getForm().findField('TEL').setReadOnly(false);
		form.getForm().findField('TEL').removeClass('x-readOnly');
		form.getForm().findField('EMAIL').setReadOnly(false);
		form.getForm().findField('EMAIL').removeClass('x-readOnly');
		form.getForm().findField('CONTACT_ADDR').setReadOnly(false);
		form.getForm().findField('CONTACT_ADDR').removeClass('x-readOnly');
};

var setEditableN=function(form){
		form.getForm().findField('DECLARANT_ATTR').setReadOnly(true);
		form.getForm().findField('DECLARANT_ATTR').addClass('x-readOnly');	
		form.getForm().findField('IDENT_TYPE').setReadOnly(true);
		form.getForm().findField('IDENT_TYPE').addClass('x-readOnly');
		form.getForm().findField('IDENT_TYPE2').setReadOnly(true);
		form.getForm().findField('IDENT_TYPE2').addClass('x-readOnly');
		form.getForm().findField('DECLARANT_BANK_REL').setReadOnly(true);
		form.getForm().findField('DECLARANT_BANK_REL').addClass('x-readOnly');
		form.getForm().findField('IS_COMMECIAL_BANK').setReadOnly(true);
		form.getForm().findField('IS_COMMECIAL_BANK').addClass('x-readOnly');
		form.getForm().findField('DECLARANT_NAME').setReadOnly(true);
		form.getForm().findField('DECLARANT_NAME').addClass('x-readOnly');
		form.getForm().findField('IDENT_NO').setReadOnly(true);
		form.getForm().findField('IDENT_NO').addClass('x-readOnly');
		form.getForm().findField('STOCK_RATIO').setReadOnly(true);
		form.getForm().findField('STOCK_RATIO').addClass('x-readOnly');
		form.getForm().findField('START_DATE').setReadOnly(true);
		form.getForm().findField('START_DATE').addClass('x-readOnly');
		form.getForm().findField('CANCLE_CAUSE').setReadOnly(true);
		form.getForm().findField('CANCLE_CAUSE').addClass('x-readOnly');
		form.getForm().findField('TEL').setReadOnly(true);
		form.getForm().findField('TEL').addClass('x-readOnly');
		form.getForm().findField('EMAIL').setReadOnly(true);
		form.getForm().findField('EMAIL').addClass('x-readOnly');
		form.getForm().findField('CONTACT_ADDR').setReadOnly(true);
		form.getForm().findField('CONTACT_ADDR').addClass('x-readOnly');
}
var tempFn = function(a,form){//根据a的值变换一些字段是否可见和为空；
		form.getForm().findField('IS_COMMECIAL_BANK').setVisible(a);
		form.getForm().findField('STOCK_RATIO').setVisible(a);
		form.getForm().findField('IDENT_TYPE').setVisible(!a);
		form.getForm().findField('IDENT_TYPE2').setVisible(a);
		
		form.getForm().findField('IDENT_TYPE').allowBlank = a;
		form.getForm().findField('IDENT_TYPE2').allowBlank = !a;
		form.getForm().findField('IS_COMMECIAL_BANK').allowBlank = !a;
	};
var declarntAttrpeSelect1 = function(selectValue,tempform){//主申报人属性更改触发事件
	/**
	 * 设置可见及是否允许为空  自然人 1，法人 2
	 */
	tempform.getForm().findField('IS_COMMECIAL_BANK').setValue('');
	tempform.getForm().findField('STOCK_RATIO').setValue('');
	if(selectValue == '22'||selectValue == '21'){//法人
		
		tempFn(true,tempform);
	}else{
		tempFn(false,tempform);
	}
}
var declarntAttrpeSelect2 = function(selectValue,tempform){//关系人属性更改触发事件
	/**
	 * 设置可见及是否允许为空  自然人 1，法人 2
	 */
	
	tempform.getForm().findField('IS_COMMECIAL_BANK').setValue('');
	tempform.getForm().findField('STOCK_RATIO').setValue('');
	if(selectValue == 'A'||selectValue == 'R'){//法人
		
		tempFn(true,tempform);
	}else{
		tempFn(false,tempform);
	}
}
