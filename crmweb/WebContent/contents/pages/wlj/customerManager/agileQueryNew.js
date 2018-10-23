/**
 * @description 灵活查询
 * @author helin
 * @since 2014-07-15
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js', // 机构放大镜
    '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',	//用户放大镜
	'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'
]);

//初始化提示框
Ext.QuickTips.init();

var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动

var fields = [
	{name: 'TEST',text:'此文件fields必须要有一个无用字段', resutlWidth:80}
];

var groupMemberTypeStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=GROUP_MEMEBER_TYPE'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
var shareFlagStore =  new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC'
        },
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=SHARE_FLAG'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

/**
 * 灵活查询方案列表面板
 */

/**
 * 新增方案名称面板
 */
var search = new Ext.FormPanel({
	frame:true,
	width: 120,
	items: [{
		autoHeight:true,
		items :[ {
			layout : 'column',
			items : [ {
				layout : 'form',
				columnWidth : .99,
				labelWidth : 80,
				items : [ 
					{xtype : 'textfield',fieldLabel : '方案名称',name : 'solutionName',id:'solutionNameId',allowBlank : false,labelStyle : 'text-align:right;',anchor : '99%'}
				]
			}]
		} ]
	}]
});
/**
 * 新增方案名称窗口
 */
var addSolutionWindow = new Ext.Window({
	title:'保存查询方案',
	layout : 'fit',
	width : 400,
	height : 150,
	draggable : true,//是否可以拖动
	closable : true,// 是否可关闭
	modal : true,
	closeAction : 'hide',
	titleCollapse : true,
	buttonAlign : 'center',
	border : false,
	animCollapse : true,
	animateTarget : Ext.getBody(),
	constrain : true,
	items : [search],
	buttons : [ {
		text : '保存',
		handler : function() {
			if(!search.getForm().isValid()){
				Ext.Msg.alert('提示', '请输入名称！');
				return;
			}
			fnBatchSave();
			search.getForm().reset(); 
			addSolutionWindow.hide();
		}
	}, {
		text : '关闭',
		handler : function() {
			search.getForm().reset();
			addSolutionWindow.hide();
		}
	} ]
});

/**
 * 保存为客户群
 */
var custGroupPanel = new Ext.FormPanel({
	frame:true,
	width: 400,
	height : 260,
	items: [{
		autoHeight:true,
		items :[ {
			layout : 'column',
			items : [ {
				layout : 'form',
				columnWidth : .99,
				labelWidth : 80,
				items : [ 
					{xtype : 'textfield',name: 'CUST_FROM',hidden:true},
					{xtype : 'textfield',name : 'CUST_BASE_NAME',fieldLabel : '<font color=red>*</font>客户群名称',allowBlank : false,labelStyle : 'text-align:right;',anchor : '90%'},
					{xtype : 'combo',name : 'GROUP_MEMBER_TYPE',hiddenName : 'GROUP_MEMBER_TYPE',fieldLabel : '<font color=red>*</font>群成员类型',store : groupMemberTypeStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank : false},
		            {xtype : 'combo',name : 'SHARE_FLAG',hiddenName : 'SHARE_FLAG',fieldLabel : '<font color=red>*</font>共享范围',store : shareFlagStore,resizable : true,valueField : 'key',displayField : 'value',
						mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',allowBlank : false},
		            {xtype : 'textarea',name : 'CUST_BASE_DESC',fieldLabel : '客户群描述',labelStyle : 'text-align:right;',anchor : '90%'}
				]
			}]
		} ]
	}]
});
/**
 * 保存为客户群
 */
var addCustGroupWin = new Ext.Window({
	title : '新增自动筛选客户群',
	layout : 'fit',
	width : 400,
	height : 260,
	draggable : true,//是否可以拖动
	closable : true,// 是否可关闭
	modal : true,
	closeAction : 'hide',
	titleCollapse : true,
	buttonAlign : 'center',
	border : false,
	animCollapse : true,
	constrain : true,
	items : [custGroupPanel],
	buttons : [ {
		text : '保存',
		handler : function() {
			if(!custGroupPanel.getForm().isValid()){
				Ext.Msg.alert('提示', '输入有误,请检查输入项！');
				return;
			}
			//设置客户来源为自动筛选
			custGroupPanel.getForm().findField('CUST_FROM').setValue('2');
            var commintData = translateDataKey(custGroupPanel.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在保存，请稍后......','系统提示');
			Ext.Ajax.request({
				url : basepath + '/customergroupinfo.json?operate='+'add',
				params :commintData,
				method : 'POST',
				success : function() {
    				Ext.Ajax.request({
    					url: basepath +'/customergroupinfo!getPid.json',
    					success:function(response){
							var groupId = Ext.util.JSON.decode(response.responseText).pid;
							fnBatchSave2(groupId,custGroupPanel.getForm().findField('CUST_BASE_NAME').getValue());
						}
					});
				},
				failure : function(response) {
					Ext.Msg.alert("提示","操作失败");
				}
			});
			custGroupPanel.getForm().reset();
			addCustGroupWin.hide();
		}
	}, {
		text : '关闭',
		handler : function() {
			custGroupPanel.getForm().reset();
			addCustGroupWin.hide();
		}
	} ]
});

/**
 * 查询方案列表
 */
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28,
	hidden : true
});

var sm = new Ext.grid.CheckboxSelectionModel();

// 定义列模型
var cm = new Ext.grid.ColumnModel( [ rownum, sm,{
	header : '查询结果',
	dataIndex : 'SS_RESULT',
	hidden : true,
	hideable:false,
	sortable : true,
	width : 100
}, {
	header : '方案ID',
	dataIndex : 'ID',
	hideable:false,
	hidden : true,
	sortable : true,
	width : 100
}, {
	header : '方案名称',
	dataIndex : 'SS_NAME',
	sortable : true,
	width : 100
}, {
	header : '创建人',
	dataIndex : 'SS_USER',
	sortable : true,
	width : 75
}, {
	header : '创建机构',
	dataIndex : 'custId',
	sortable : true,
	width : 100
} ]);

/**
 * 数据存储
 */
var store = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath+'/queryagilequery!queryAgileSolution.json'
	}),
  reader: new Ext.data.JsonReader({
        root:'JSON.data'
        }, [
            {name: 'ID'},
			{name: 'SS_USER'},
			{name: 'SS_SORT'},
			{name:'SS_NAME'},
			{name:'SS_RESULT'}
			
		])
});

var tbar = new Ext.Toolbar({
	items : [ {
		text : '新增',
		iconCls:'addIconCss',
		handler : function() {
			simple.removeAllItems();
			simple2.removeAllItems();
			right_panel.currentSolutionsId = false;
		}
	}, '-', {
		text : '删除',
		iconCls:'deleteIconCss',
		handler : function() {
			Ext.MessageBox.confirm('确认删除', '确定要删除所选择的数据么？', function(e){
		         if ( e =='no') return;
		         fnConditionDelete();
			});
		}
	} ]
});

// 表格实例
var grid = new Ext.grid.GridPanel({
	width:'100%',
	frame : true,
	autoScroll : true,
	store : store, // 数据存储
	stripeRows : true, // 斑马线
	cm : cm, // 列模型
	sm : sm,
	tbar : tbar, // 表格工具栏
	viewConfig : {
		forceFit : false,
		autoScroll : true
	},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

grid.on('rowdblclick', function(grid, rowIndex, event) {
	
	var selectLength = grid.getSelectionModel().getSelections().length;
	var selectRe = grid.getSelectionModel().getSelections()[0];
	if (selectLength != 1) {
		Ext.Msg.alert('提示', '请选择一条记录!');
		return;
	}
	
	simple.removeAllItems();
	simple2.removeAllItems();
	var ssResult=selectRe.data.SS_RESULT.split(",");
	var rankResult = selectRe.data.SS_SORT.split(",");
	for(var rIndex = 0 ;rIndex<ssResult.length; rIndex++){
		var node = treeOfPoroduct.root.findChild("id", "b"+ssResult[rIndex], true);
		if(node){
			simple2.addItems(node,rankResult[rIndex]);
		}
	}
	id=0;
	right_panel.currentSolutionsId = selectRe.data.ID;
	Ext.Ajax.request({
		url:basepath+'/queryagilequery!queryAgileCondition.json?SS_ID='+selectRe.data.ID,
		method: 'GET',
		success : function(response) {
			var conditionData = Ext.util.JSON.decode(response.responseText);
			var conditionArray=conditionData.JSON.data;
			if(conditionArray.length>0){
				Ext.each(conditionArray,function(con){
					var node = treeOfPoroduct.root.findChild("id", "b"+con.SS_COL_ITEM, true);
					if(node){
						simple.addItems(node,con.SS_COL_OP,con.SS_COL_VALUE);
					}
				});
				if(conditionArray[0].SS_COL_JOIN=='true'){
					radio.items.items[0].items.items[0].setValue(true);
					right_panel.conditionJoinType = 'true';
				}else{
					radio.items.items[1].items.items[0].setValue(true);
					right_panel.conditionJoinType = 'false';
				}
			}
		},
		failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示','您没有此权限!');
			} else {
				Ext.Msg.alert('提示','操作失败!');
			}
		}
	});
});
store.load();
		
//保存方案
var fnBatchSave= function(){
	var selectRe = grid.getSelectionModel().getSelections()[0];
	var solutionID=  right_panel.currentSolutionsId? right_panel.currentSolutionsId:'';
	var solutionAttr = {};
	solutionAttr.solutionName= Ext.getCmp("solutionNameId").getValue();
	solutionAttr.ss_results = simple2.getResultsIds();
	solutionAttr.ss_sort = simple2.getSortTypes();
	var conditions = simple.getConditionsAttrs();
	Ext.Ajax.request({
		url:basepath+'/agilesearch.json',
		method: 'POST',
		success : function(response) {
			Ext.Msg.alert('提示', '操作成功');
			store.reload();
			Ext.Ajax.request({
				url: basepath+'/session-info!getPid.json',
				method:'GET',
				success:function(response){
					right_panel.currentSolutionsId = Ext.decode(response.responseText).pid;
				}
			});
		},
		failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示','您没有此权限!');
			} else {
				Ext.Msg.alert('提示','操作失败!');
			}
		},
		params : {
			solutionAttr:Ext.encode(solutionAttr),
			conditionCols : Ext.encode(conditions),
			solutionID:solutionID,
			'radio':right_panel.conditionJoinType
		}
	});
};


//删除方案
var fnConditionDelete= function(){
	var selectLength = grid.getSelectionModel().getSelections().length;
 	var checkedNodes = grid.getSelectionModel().selections.items;
		if(checkedNodes.length==0){
			Ext.Msg.alert('提示', '未选择任何客户');
			return ;
		}
		var json='';
		for(var i=0;i<checkedNodes.length;i++)
		{
			if(i==0){
				json = checkedNodes[i].data.ID;
			}else {
				json += ',' + checkedNodes[i].data.ID;
			}
		}
		Ext.Ajax.request({
			url:basepath+'/agilesearch.json',
            method: 'POST',
			success : function(response) {
				Ext.Msg.alert('提示', '操作成功!');
				store.reload();
			},
			failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if(resultArray == 403) {
					Ext.Msg.alert('提示','您没有此权限!');
				} else {
					Ext.Msg.alert('提示','加入失败!');
				}
			},
			params : {
				'solutionID': json
			}
		});
};

//保存为客户群
var fnBatchSave2= function(groupId,groupName){
	var solutionAttr = {};
	solutionAttr.ss_results = simple2.getResultsIds();
	solutionAttr.ss_sort = simple2.getSortTypes();
	var conditions = simple.getConditionsAttrs();
	Ext.Ajax.request({
		url:basepath+'/agilesearch!create1.json',
		success : function(response) {
			Ext.Msg.alert('提示', '操作成功');
		},
		failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示','您没有此权限!');
			} else {
				Ext.Msg.alert('提示','操作失败!');
			}
		},
		params : {
			solutionAttr:Ext.encode(solutionAttr),
			conditionCols : Ext.encode(conditions),
			group_id:groupId,
			group_name:groupName,
			flag:'true',
			'radio':right_panel.conditionJoinType
		}
	});
					
};

/**
 * 数据集加载器
 */	
var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
		parentAttr : 'PARENT_ID',
		locateAttr : 'NODEID',
		rootValue : '0',
		textField : 'NAME',
		idProperties : 'NODEID'
	});
	Ext.Ajax.request({
		url : basepath + '/queryagilequery.json',
		method : 'GET',
		success : function(response) {
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			loader.nodeArray = nodeArra.JSON.data;
			nodeArrays=nodeArra.JSON.data;
			for ( var item in loader.nodeArray) {
				if (typeof loader.nodeArray[item] === 'object') {
					if (loader.nodeArray[item].TABLES == '2')
						loader.nodeArray[item].NODEID = 'b' + loader.nodeArray[item].NODEID;
				}
			}
			var children = loader.loadAll();
			treeOfPoroduct.appendChild(children);
			treeOfPoroduct.expandAll();
		}
	});
	/**
	 * 数据集字段树
	 */
	var treeOfPoroduct = new Com.yucheng.bcrm.TreePanel({
		title : '条件字段',
		autoScroll : true,
		rootVisible : false,
		ddGroup : 'rightPanel',
		split : true,
		enableDrag:true,
		/** 虚拟树形根节点 */
		root : new Ext.tree.AsyncTreeNode({
			id : 'root',
			expanded : true,
			text : '客户视图',
			autoScroll : true,
			children : []
		}),
		resloader : loader
	});
	
	treeOfPoroduct.on('afterrender',function(tree){
		treeOfPoroduct.root.expand( true, false, function(a,b,c,d){
		});
	});
	

/**
 * 条件、查询项展示面板
 */


Ext.ns('Com.yucheng.crm.query');

/**
 * 查询条件常量
 */
Com.yucheng.crm.query.Util = {
	optypes :{
		INCLUDE : [['等于', '0002'],['包含', '0000']],
		COMPARE : [['大于', '0001'], ['等于', '0002'], ['小于', '0003'], ['大于等于', '0004'], ['小于等于', '0005']],
		EQUAL : [['等于', '0002']],
		ALL : [['包含', '0000'],['大于', '0001'], ['等于', '0002'], ['小于', '0003'], ['大于等于', '0004'], ['小于等于', '0005']]
	},
	types : {
		VARCHAR2:'QUERYUTIL.optypes.INCLUDE',
		DATE:'QUERYUTIL.optypes.COMPARE',
		NUMBER:'QUERYUTIL.optypes.COMPARE',
		DECIMAL:'QUERYUTIL.optypes.COMPARE',
		INTEGER:'QUERYUTIL.optypes.COMPARE',
		VARCHAR:'QUERYUTIL.optypes.INCLUDE',
		CHAR:'QUERYUTIL.optypes.INCLUDE',
		BIGINT:'QUERYUTIL.optypes.COMPARE'
	},
	orderTypes : new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['不排序', '0'],['正序', '1'],['逆序', '2']]
	}),
	custBaseInfo : {
		dbTable :'ACRM_F_CI_CUSTOMER',
		idColumn : 'CUST_ID',
		nameColumn : 'CUST_NAME',
		typeColumn : 'CUST_TYPE',
		dbNode : false,
		idNode : false,
		nameNode : false,
		typeNode : false
	}
};
QUERYUTIL = Com.yucheng.crm.query.Util;

Com.yucheng.crm.query.QeuryPanel = Ext.extend(Ext.FormPanel, {
	height :400,
	labelAlign: 'top',
	bodyStyle:'padding:0px 0px 0px 5px',
	autoHeight : true,
	autoWidth : false
});
/**
 * 查询条件面板类
 */
Com.yucheng.crm.query.SearchPanel = Ext.extend(Com.yucheng.crm.query.QeuryPanel, {
	
	conditions : new Array(),
	initComponent : function(){
		Com.yucheng.crm.query.SearchPanel.superclass.initComponent.call(this);
		this.add(new Ext.Panel({
			html:'<table><tr><td style= "text-align:center;width:80px;font-size:12px;">属性 </td><td style= "text-align:center;width:170px;font-size:12px;">操作符</td><td style= "text-align:center;width:170px;font-size:12px;">属性值</td><td></td></tr></table> '
		}));
	},
	/**
	 * 添加一个查询条件
	 * @param node:数据字段节点，从数据集树上获取
	 * @param op : 可选参数，查询条件操作符
	 * @param value : 可选参数，查询条件值
	 */
	addItems : function(node,op,value){
		
		for(var n=0;n<this.conditions.length;n++){
//			if(this.conditions[n].nodeInfo === node){
//				Ext.Msg.alert('提示','该列已选');
//				return false;
//			}
		}
		
		var si = new Com.yucheng.crm.query.SearchItem({
			nodeInfo:node,
			oprater :op,
			conditionValue : value
		});
		this.conditions.push(si);
		this.add(si);
		this.doLayout();
	},
	/**
	 * 移除一个查询条件项
	 * @param item:查询条件面板
	 */
	removeItem : function(item){
		this.conditions.remove(item);
		this.remove(item);
	},
	/**
	 * 移除所有查询条件
	 */
	removeAllItems : function(){
		var _this = this;
		while(_this.conditions.length>0){
			_this.removeItem(_this.conditions[0]);
		}
	},
	/**
	 * 获取查询条件
	 */
	getConditionsAttrs : function(){
		var _this = this;
		var conditions = new Array();
		Ext.each(_this.conditions, function(con){
			var conAtt = {};
			conAtt.ss_col_item = con.columnId.substring(1);
			conAtt.ss_col_op = con.oprater;
			conAtt.ss_col_value = con.conditionValue;
			conditions.push(conAtt);
		});
		return conditions;
	}
});
/**
 * 查询条件项面板
 */
Com.yucheng.crm.query.SearchItem = Ext.extend(Ext.Panel,{
	nodeInfo : false, //关联数据字段节点
	oprater: null,	//条件操作符
	conditionValue:null,//条件值
	
	valueStore : false,
	/**
	 * 对象构建方法，初始化面板各数据属性，根据字段类型以及字典编码，创建操作符、字段值下拉框数据源（store）
	 */
	initComponent : function(){
    	if(!this.nodeInfo){
    		return false;
    	}
    	this.textName = this.nodeInfo.text;
    	this.columnId = this.nodeInfo.id;
    	this.columnName = this.nodeInfo.attributes.ENAME;
    	this.columnType  = this.nodeInfo.attributes.CTYPE;
    	this.datasetId = this.nodeInfo.attributes.PARENT_ID;
    	this.opstore = new Ext.data.SimpleStore({
    		fields : ['name', 'code']
    	});
    	this.opstore.loadData(this.nodeInfo.attributes.NOTES?QUERYUTIL.optypes.EQUAL:eval(QUERYUTIL.types[this.columnType]));
    	if(this.nodeInfo.attributes.NOTES){
    		this.valueStore = new Ext.data.Store({
    			restful:true,
    			proxy : new Ext.data.HttpProxy({
    				url :basepath+'/lookup.json?name='+this.nodeInfo.attributes.NOTES
    			}),
    			reader : new Ext.data.JsonReader({
    				root : 'JSON'
    			}, [ 'key', 'value' ])
    		});
    	}
    	
    	Com.yucheng.crm.query.SearchItem.superclass.initComponent.call(this);
	},
	/**
	 * 销毁方法，对象销毁时触发，销毁对象所创建的数据源
	 */
	onDestroy :function(){
		var _this = this;
		if(_this.valueStore)
			_this.valueStore.destroy();
		if(_this.opstore){
			_this.opstore.destroy();
		}
		Com.yucheng.crm.query.SearchItem.superclass.onDestroy.call(this);
	},
	/**
	 * 渲染方法，对象渲染时（首次展示）触发，创建字段名、操作符、条件值展示框。
	 */
	onRender : function(ct, position){
		
		var _this = this;
		_this.nameField = new Ext.form.DisplayField({
			emptyText : '',
			editable : false,
			triggerAction : 'all',
			allowBlank : false,
			hideLabel:true,
			xtype : 'displayfield',
			name : 'attributeName_' + id,
			anchor : '95%',
			value : _this.textName
		});
		_this.opField = new Ext.form.ComboBox({
			hiddenName:'operateName_'+id,
			hideLabel:true,
			allowBlank : false,
			labelStyle: 'text-align:right;',
			triggerAction : 'all',
			store : _this.opstore,
			displayField : 'name',
			valueField : 'code',
			mode : 'local',
			forceSelection : true,
			typeAhead : true,
			emptyText:'请选择',
			resizable : true,
			anchor : '95%',
			value : _this.oprater,
			listeners : {
				select : function(combo,record,index){
					_this.oprater = record.data.code;
				}
			}
		});
		if(_this.nodeInfo.attributes.CTYPE == 'DATE'){
			_this.valueField = new Ext.form.DateField({
				allowBlank : false,
				hideLabel:true,
				labelStyle: 'text-align:right;',
				format:'Y-m-d', //日期格式化
				name : 'attributeValueName_' + id,
				anchor:'95%',
				value : _this.conditionValue,
				listeners : {
					select : function(combo,date){
						_this.conditionValue = date.format('Y-m-d');
					}
				}
			});
		} else if (_this.valueStore){
			_this.valueField = new Ext.form.ComboBox({
				hiddenName:'operateName_'+id,
				hideLabel:true,
				allowBlank : false,
				labelStyle: 'text-align:right;',
				triggerAction : 'all',
				store : _this.valueStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				typeAhead : true,
				emptyText:'请选择',
				resizable : true,
				anchor : '95%',
//				value : _this.conditionValue,
				listeners : {
					select : function(combo,record,index){
						_this.conditionValue = record.data.key;
					}
				}
			});
			_this.valueStore.load({callback:function(){
				_this.valueField.setValue( _this.conditionValue);
			}});
		} else {
			_this.valueField = new Ext.form.TextField({
				emptyText : '',
				editable : false,
				triggerAction : 'all',
				allowBlank : false,
				hideLabel : true,
				name : 'attributeValueName_' + id,
				labelStyle : 'text-align:right;',
				xtype : 'textfield',
				anchor : '95%',
				value : _this.conditionValue,
				listeners : {
					change : function(field,newValue,oldValue){
						_this.conditionValue = field.getValue();
					}
				}
			});
		}
		this.add(new Ext.Panel({
			items : [{
				layout : 'column',
				border : false,
				items : [{
					columnWidth : .15,
					layout : 'form',
					border : false,
					items : [_this.nameField]
				}, {
					columnWidth : .30,
					layout : 'form',
					border : false,
					items : [_this.opField]
				}, {
					columnWidth : .30,
					layout : 'form',
					border : false,
					items : [_this.valueField]
				}, {
					columnWidth : .021,
					layout : 'form',
					border : false,
					items : []
				}, {
					columnWidth : .079,
					layout : 'form',
					border : false,
					items : [{ 
						xtype: 'button', 
						text: '删除', 
						scope: this, 
						handler: function(){ 
							_this.ownerCt.removeItem(_this);
						} 
					}]
				}
			]}]
		}));
		
		Com.yucheng.crm.query.SearchItem.superclass.onRender.call(this, ct, position);
	}
});

/**
 * 查询结果列面板
 */
Com.yucheng.crm.query.ColumnsPanel = Ext.extend(Com.yucheng.crm.query.QeuryPanel, {
	
	resultColumns : new Array(),	//查询结果面板数组
	selectModel : new Ext.grid.CheckboxSelectionModel(),//公用复选框
    resultNumber : new Ext.grid.RowNumberer({	//数据行号框
        header : 'No.',
        width : 28
    }),
    /**
     * 构造方法，创建面板头
     */
    initComponent:function(){
		Com.yucheng.crm.query.ColumnsPanel.superclass.initComponent.call(this);
		this.add(new Ext.Panel({
			layout : 'column',
			border : false,
			items : [{
				html:'<table><tr><td style= "text-align:center;width:80px;font-size:12px;">名称 </td><td style= "text-align:center;width:170px;font-size:12px;">排序方式</td><td></td></tr></table> '
			}]
		}));
	},
	onRender : function(ct, position){
		Com.yucheng.crm.query.ColumnsPanel.superclass.onRender.call(this, ct, position);
	},
	
	/**
	 * 添加一个结果列
	 * @param node:字段节点对象；
	 * @param sortType:排序属性；
	 * @param hidden:是否隐藏字段；
	 * @param override:只有当值为：false时，不会改变hidden属性；
	 */
	addItems : function(node,sortType,hidden,override){
		
		var _this = this;
		for(var n=0;n<this.resultColumns.length;n++){
			if(this.resultColumns[n].nodeInfo === node){
				
				if(override === false){
					this.resultColumns[n].columnTotle = 'BASE';
				}else if(override !== false){
					this.resultColumns[n].show();
				}
				return false;
			}
		}
		var tSort = 0;
		if(sortType){
			tSort = sortType;
		}
		
		var columnTotle = 0;
		
		if( node === QUERYUTIL.custBaseInfo.idNode 
				|| node === QUERYUTIL.custBaseInfo.nameNode 
				|| node === QUERYUTIL.custBaseInfo.typeNode ){
			columnTotle = "BASE";
		}else {
			Ext.each(_this.resultColumns,function(rc){
				if(rc.nodeInfo.attributes.ENAME == node.attributes.ENAME){
					columnTotle ++;
				}
			});
		}
		var ci = new Com.yucheng.crm.query.ColumnsItem({
			nodeInfo:node,
			sortType:tSort,
			hidden:hidden,
			columnTotle:columnTotle
		});
		this.resultColumns.push(ci);
		this.add(ci);
		this.doLayout();
	},
	/**
	 * 移除一个结果字段面板
	 */
	removeItem : function(columnItem){
		this.resultColumns.remove(columnItem);
		this.remove(columnItem);
	},
	/**
	 * 移除所有结果字段
	 */
	removeAllItems : function(){
		var _this = this;
		while(_this.resultColumns.length>0){
			_this.removeItem(_this.resultColumns[0]);
		}
	},
	/**
	 * 获取查询结果字段属性，包括字段ID，排序类型，以及别名后缀，供后台使用
	 */
	getResults : function(){
		var _this = this;
		var rsults = [];
		Ext.each(_this.resultColumns,function(column){
			var r = {};
			r.columnId = column.nodeId.substring(1);
			r.sortType = column.sortType;
			r.columnTotle = column.columnTotle;
			rsults.push(r);
		});
		return rsults;
	},
	/**
	 * 获取查询结果字段ID拼串，保存查询方案时调用
	 */
	getResultsIds : function(){
		var resuldIds = [];
		var _this = this;
		Ext.each(_this.resultColumns,function(column){
			resuldIds.push(column.nodeId.substring(1));
		});
		return resuldIds.join(',');
	},
	/**
	 * 获取排序类型拼串，保存查询方案时调用
	 */
	getSortTypes : function(){
		var resuldIds = [];
		var _this = this;
		Ext.each(_this.resultColumns,function(column){
			resuldIds.push(column.sortType);
		});
		return resuldIds.join(',');
	},
	/**
	 * 获取查询结果reader属性，查询结果时调用；
	 * 有字典编码属性的字段讲生成两个字段。
	 */
	getResultReaderMetas : function(){
		var readerMetas = {
				successProperty: 'success',
				messageProperty: 'message',
				idProperty: 'CUST_ID',
				root:'json.data',
				totalProperty: 'json.count'
		};
		var readerFields = [];
		Ext.each(simple2.resultColumns,function(column){
			var t = {};
			t.name = column.columnName+'_'+column.columnTotle;
			readerFields.push(t);
			if(column.columnLookup){
				var t2 = {};
				t2.name = column.columnName+'_'+column.columnTotle+'_ORA';
				readerFields.push(t2);
			}
		});
		readerMetas.fields = readerFields;
		return readerMetas;
	},
	/**
	 * 获取查询结果列模型，查询结果时调用；
	 * 1、有字典编码属性的字段将生成两个字段；
	 * 2、隐藏字段将也包含在内。
	 */
	getResultColumnHeaders : function(){
		var _this = this;
		var columnHeaders = [];
		columnHeaders.push(_this.resultNumber);
		columnHeaders.push(_this.selectModel);
		Ext.each(simple2.resultColumns,function(column){
			
			var columnHead = {};
			
			columnHead.header = column.textName;
			columnHead.hidden = column.hidden;
			columnHead.hideable  = !column.hidden;
			columnHead.dataIndex = column.columnName+'_'+column.columnTotle;
			columnHeaders.push(columnHead);
			
			if(column.columnLookup){
				var columnHeadLooked = {};
				columnHeadLooked.header = column.textName;
				columnHeadLooked.hidden = column.hidden;
				columnHeadLooked.hideable  = !column.hidden;
				columnHeadLooked.dataIndex = column.columnName+'_'+column.columnTotle+'_ORA';
				columnHeaders.push(columnHeadLooked);
				columnHead.hidden = true;
				columnHead.hideable = false;
			}
		});
		return columnHeaders;
	},
	/**
	 *  获取可分组字段数据，排除隐藏字段，分组统计时调用
	 */
	getResultColumnHeaderByNodeId : function(id){
		var _this = this;
		var columnHeaders = [];
		for(var i=0;i<_this.resultColumns.length;i++){
			if(_this.resultColumns[i].nodeId == id){
				var header = {};
				header.header = _this.resultColumns[i].textName;
				if(_this.resultColumns[i].columnLookup){
					header.dataIndex = _this.resultColumns[i].columnName+'_'+_this.resultColumns[i].columnTotle+'_ORA';
				}else{
					header.dataIndex = _this.resultColumns[i].columnName+'_'+_this.resultColumns[i].columnTotle;
				}
				return header;
			}
		}
		return;
	},
	/**
	 * 获取可统计字段，排除隐藏字段，分组统计时调用
	 */
	getSumColumnsByNodeIds : function(ids){
		var _this = this;
		var sums = [];
		for(var i=0;i<_this.resultColumns.length;i++){
			if(ids.indexOf(_this.resultColumns[i].nodeId)>=0){
				var header = {};
				header.dataIndex = _this.resultColumns[i].columnName+'_'+_this.resultColumns[i].columnTotle+'_SUM';
				header.header = _this.resultColumns[i].textName+'(统计)';
				sums.push(header);
			}
		}
		return sums;
	},
	/**
	 * 获取当前展示列数
	 */
	viewedColumnsCount : function(){
		var _this = this;
		var count = 0;
		Ext.each(_this.resultColumns,function(column){
			if(!column.hidden){
				count ++ ;
			}
		});
		return count;
	}
});

/**
 * 查询结果列面板
 */
Com.yucheng.crm.query.ColumnsItem =  Ext.extend(Ext.Panel,{
	nodeInfo : false,//字段节点
	sortType : 0,//排序类型
	/**
	 * 构造方法，初始化面板各项数据信息
	 */
	initComponent : function(){
	
		if(!this.nodeInfo){
			return false;
		}
		this.textName = this.nodeInfo.text;
		this.nodeId = this.nodeInfo.id;
		this.columnName = this.nodeInfo.attributes.ENAME;
		this.columnType = this.nodeInfo.attributes.CTYPE;
		this.datasetId = this.nodeInfo.attributes.PARENT_ID;
		this.columnLookup = this.nodeInfo.attributes.NOTES;
		Com.yucheng.crm.query.ColumnsItem.superclass.initComponent.call(this);
	},
	
	/**
	 * 渲染方法，面板渲染时调用，构建结果列名、排序下拉框
	 */
	onRender : function(ct, position){
		
		var _this = this;
		_this.columnField = new Ext.form.DisplayField({
			emptyText : '',
			editable : false,
			triggerAction : 'all',
			allowBlank : false,
			hideLabel:true,
			xtype : 'displayfield',
			anchor : '95%',
			value : _this.textName
		});
		
		_this.orderField =  new Ext.form.ComboBox({
			hideLabel : true,
			labelStyle : 'text-align:right;',
			triggerAction : 'all',
			store : QUERYUTIL.orderTypes,
			value:_this.sortType,
			displayField : 'name',
			valueField : 'code',
			mode : 'local',
			forceSelection : true,
			typeAhead : true,
			emptyText : '请选择',
			resizable : true,
			anchor : '95%',
			listeners : {
				select : function(combo,record,index){
					_this.sortType = record.data.code;
				}
			}
		}) ;
		
		this.add(new Ext.Panel({
			items : [{
				layout : 'column',
				border : false,
				items : [{
					columnWidth : .15,
					layout : 'form',
					labelWidth : 60,
					border : false,
					items : [_this.columnField]
				}, {
					columnWidth : .30,
					layout : 'form',
					labelWidth : 60,
					border : false,
					items : [_this.orderField]
				}, {
					columnWidth : .30,
					layout : 'form',
					labelWidth : 60,
					border : false,
					items : []
				}, {
					columnWidth : .021,
					layout : 'form',
					labelWidth : 100,
					border : false,
					items : []
				}, {
					columnWidth : .079,
					layout : 'form',
					labelWidth : 100,
					border : false,
					items : [{ 
						xtype: 'button', 
						text: '删除', 
						scope: this, 
						handler: function(){ 
							_this.ownerCt.removeItem(_this);
						} 
					}]
				}]
			}]
		}));
		Com.yucheng.crm.query.ColumnsItem.superclass.onRender.call(this, ct, position);
	}
});
	//查询条件对象
	var simple = new Com.yucheng.crm.query.SearchPanel({
		title : '查询条件',
		listeners:{
			"activate":function(){
				this.doLayout();
			}
		}
	});
	//结果列对象
	var simple2 = new Com.yucheng.crm.query.ColumnsPanel({
		title : '显示列',
		listeners:{
			"activate":function(){
				this.doLayout();
			}
		}
	});
	
	
	var tabmain = new Ext.TabPanel({
		autoScroll : true,
		id : 'tabmain',		activeTab : 0,
		frame : true,
		height:400,
		defaults : {
			autoHeight : true
		},
		items : [simple,simple2]
	});
	//条件链接符面板
	var radio = new Ext.Panel({
		layout : 'column',
		border : false,
		items : [ {
			columnWidth : .09,
			layout : 'form',
			labelWidth : 8,
			border : false,
			items : [ new Ext.form.Radio({
				boxLabel : "与",
				labelStyle: 'text-align:right;',
				id : "Radio1",
				name : "a",
				checked : true,
				listeners : {
					check : function(r,v){
						if(v)
							right_panel.conditionJoinType = 'true';
						else
							right_panel.conditionJoinType = 'false';
					}
				}
			})]
		}, {
			columnWidth : .09,
			layout : 'form',
			labelWidth :8,
			border : false,
			items : [ new Ext.form.Radio({
				boxLabel : "或",
				labelStyle: 'text-align:right;',
				id : "Radio2",
				name : "a"
			}) ]
		} ]
	});
	//右侧面板
	var right_panel = new Ext.Panel({
		
		currentSolutionsId : false,//当前展示查询方案ID；
		conditionJoinType : 'true',//条件连接符数据，根据radio对象点选情况		frame : true,
		autoScroll : true,
		items : [ tabmain,radio],
		title : '查询设置',
		buttonAlign : 'center',
		buttons : [ {
			text : '保存',
			handler : function() {
				if(simple2.viewedColumnsCount()==0){
					Ext.Msg.alert('提示', '未加入任何显示列！');
					return;
				}
				
				if(simple.conditions.length==0){
					Ext.Msg.alert('提示', '未加入任何条件列！');
					return;
				}
				if(!simple.getForm().isValid()){
					Ext.Msg.alert('提示', '查询条件输入有误！');
					return;
				}
				if(store.find('ID',right_panel.currentSolutionsId)<0){
					right_panel.currentSolutionsId = false;
				}
				
				if(!right_panel.currentSolutionsId){
					addSolutionWindow.show();
				} else{
					fnBatchSave();
				}
			}
		},{
			text : '保存为客户群',
			handler : function() {
				if(simple.conditions.length==0){
					Ext.Msg.alert('提示', '未加入任何条件列！');
					return;
				}
				if(!simple.getForm().isValid()){
					Ext.Msg.alert('提示', '查询条件输入有误！');
					return;
				}
				if(store.find('ID',right_panel.currentSolutionsId)<0){
					right_panel.currentSolutionsId = false;
				}
				addCustGroupWin.show();
			}
		},{
			text : '查询结果',
			handler : function() {
				fnSearchResult();
			}
		}]
	});
	right_panel.on('afterrender',function(){
		/**数据字段拖拽代理*/
		new Ext.dd.DropTarget(right_panel.body.dom, {
	    	ddGroup : 'rightPanel',
	    	notifyDrop : function(ddSource, e, data) {
	    	if (!data.node.leaf) {
	    		return;
	    	}
	    	var changeFlag=false;
	    	if(tabmain.activeTab==simple){
	    		simple.addItems(data.node);
	    		tabmain.setActiveTab(1);	
	    		changeFlag = true;
	    	}
	    	simple2.addItems(data.node);
	    	if(changeFlag){
	    		tabmain.setActiveTab(0);	
	    	}
	    	return true;
	    	}
	    });
	});


/**
 *查询结果展示界面
 *
 */

//分组字段下拉框数据源
var _groupStore = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		successProperty: 'success',
		messageProperty: 'message',
		fields : [{
			name:"textName"
		},{
			name:"nodeId"
		}]
	})
});
//统计字段下拉框数据源
var _sumStore = new Ext.data.Store({
	reader:new Ext.data.JsonReader({
		successProperty: 'success',
		messageProperty: 'message',
		fields : [{
			name:"textName"
		},{
			name:"nodeId"
		}]
	})
});

var LovCombo1 = new Ext.form.ComboBox({
	fieldLabel : '第一分组字段',
	editable : false,
	triggerAction:'all',
	mode:'local',
	store : _groupStore,
	valueField : 'nodeId',
	displayField : 'textName',
	anchor:'90%',
	name : 'LovCombo1'
});
var LovCombo2 = new Ext.form.ComboBox({
	fieldLabel : '第二分组字段',
	editable : false,
	triggerAction:'all',
	mode:'local',
	store : _groupStore,
	valueField : 'nodeId',
	displayField : 'textName',
	anchor:'90%',
	name : 'LovCombo2'
});
var LovCombo3 = new Ext.form.ComboBox({
	fieldLabel : '第三分组字段',
	editable : false,
	triggerAction:'all',
	mode:'local',
	store : _groupStore,
	valueField : 'nodeId',
	displayField : 'textName',
	anchor:'90%',
	name : 'LovCombo3'
});     
var LovCombo4 = new Ext.ux.form.LovCombo({
	fieldLabel: '汇总字段',
	name:'LovCombo4',
	valueField : 'nodeId',
	displayField : 'textName',
	hideOnSelect:false,
	store : _sumStore,
	triggerAction:'all',
	mode:'local',
	anchor:'90%',
	editable:false
});
	
//查询结果列模型
var cmres = new Ext.grid.ColumnModel(simple2.getResultColumnHeaders());
cmres.on("configchange",function(){
	resultGrid.view.refresh(true);
}); 
/**
 * 查询结果分页
 */ 
var _lpagesize_combo = new Ext.form.ComboBox({
	  	name : 'pagesize',
	  	triggerAction : 'all',
	  	mode : 'local',
	  	store : new Ext.data.ArrayStore({
	  				fields : ['value', 'text'],
	  				data : [ 
	  						[ 20, '20条/页' ],[ 50, '50条/页' ],[ 100, '100条/页' ], [ 250, '250条/页' ],
	  						[ 500, '500条/页' ] ]
	  			}),
	  	valueField : 'value',
	  	displayField : 'text',
	  	value : '20',
	  	editable : false,
	  	width : 85,
	  	listeners : {
			select : function(combo){
				_lbbar.pageSize = parseInt(_lpagesize_combo.getValue());
				resultstore2.reload({
					params : {
						start : 0,
						limit : parseInt(_lpagesize_combo.getValue())
					}
				});
			}
		}
	});
	//查询结果数据源
	var resultstore2 = new Ext.data.Store({
		restful : true,
		url:basepath+'/queryagileresult.json',
		reader:new Ext.data.JsonReader({
			successProperty: 'success',
			messageProperty: 'message',
			fields : [{
				name:'CUST_ID'
			}]
		})
	});
	
	//分页工具栏
	var _lbbar = new Ext.PagingToolbar({
		pageSize : parseInt(_lpagesize_combo.getValue()),
		store : resultstore2,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', _lpagesize_combo]
	});
	 
	//查询结果列表面板
	var resultGrid = new Ext.grid.GridPanel({
		frame : true,
		region : 'center', // 返回给页面的div
		store : resultstore2, // 数据存储
		stripeRows : true, // 斑马线
		cm : cmres, // 列模型
		sm : simple2.selectModel,
		tbar : [{
			text : '客户视图',
			iconCls :'custGroupMemIconCss',
			handler : function(a,b,c,d){
				if(simple2.selectModel.getSelections().length == 0){
//					Ext.Msg.alert("meixuan");
					Ext.Msg.alert('提示','请选择数据！');
					return;
				}
				if(simple2.selectModel.getSelections().length > 1){
//					Ext.Msg.alert("duoxuan");
					Ext.Msg.alert('提示','重复选择数据！');
				}
				
				/*******************************************************/
				var custId = simple2.selectModel.getSelections()[0].data.CUST_ID_BASE;
				var custTyp = simple2.selectModel.getSelections()[0].data.CUST_TYPE_BASE;
				var custName = simple2.selectModel.getSelections()[0].data.CUST_NAME_BASE;
				
				parent.Wlj.ViewMgr.openViewWindow(0,custId,custName);
				
				/*******************************************************/
			}
		},'-',{
			id:'__expResult',
			iconCls:'exportIconCss',
			text : '导出',
			formPanel : '',
			xtype: 'bob.expbutton',
			exParams : resultstore2.baseParams,
			url: basepath+'/queryagileresult.json'
		},'-',{
			id:'addIconCss',
			iconCls:'addIconCss',
			text : '加入客户群',
			handler : function() {
				Ext.ScriptLoader.loadScript({
					scripts: [
						basepath+'/contents/pages/customer/customerClub/groupCustMgrEdit.js',
						basepath+'/contents/pages/customer/customerClub/groupLeaguerEdit.js',
						basepath+'/contents/pages/customer/customerClub/agileQuery.js',
						basepath+'/contents/pages/customer/customerClub/customerGroupEdit.js',
						basepath+'/contents/pages/customer/custSearchByDetailType/searchCustForGroup.js'
					],        
					finalCallback: function() {
    					var selectLength = resultGrid.getSelectionModel().getSelections().length;
    					if (selectLength < 1) {
    						Ext.Msg.alert('提示','请至少选择一个客户!');
    					} else {
							var selectRe;
    						var tempId;
    						var custType,m,n;
    						idStr = '';//需要先清空
    						for ( var i = 0; i < selectLength; i++) {
    							tempId = simple2.selectModel.getSelections()[i].data.CUST_ID_BASE;
    							custType = simple2.selectModel.getSelections()[i].data.CUST_TYPE_BASE;
    							if(custType == '2'){
    								m=1;
    							}else if(custType == '1'){
    								n=1;
    							}
    							idStr += tempId;
    							if (i != selectLength - 1)
    								idStr += ',';
    						}
							groupMemberType = custType;
							if(m==1 && n==1)
								groupMemberType = '3';
							choseWin.show();
							choseWayForm.form.findField('custGroup').setVisible(false);
							choseWayForm.getForm().reset();
    					}
					}
				});
			}
		}], // 表格工具栏
		bbar : _lbbar,// 分页工具栏
		viewConfig : {
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	//分组条件面板
	var _groupForm = new Ext.FormPanel({
		region:'north',
		labelWidth : 90,
		frame : true,
		autoScroll : true,
		buttonAlign:"center",
		height:80,
		width: '100%',
		labelAlign:'right',
		items:[{
			layout:'column',
	        items:[{
	        	columnWidth : .24,
	        	layout : 'form',
	        	items : [LovCombo1]
	        },{
	        	columnWidth : .24,
	        	layout : 'form',
	        	items : [LovCombo2]
	        },{
	        	columnWidth : .24,
	        	layout : 'form',
	        	items : [LovCombo3]
	        },{
	        	columnWidth : .24,
	        	layout : 'form',
	        	items : [LovCombo4]
	        }]
		}],
		buttons:[{
			text : '分组汇总统计',
			handler : function() {
				if(!LovCombo1.getValue() && !LovCombo2.getValue() && !LovCombo3.getValue()){
					Ext.Msg.alert("提示","请选择分组字段");
					return;
				}
				groupingPropere();
				groupingWindow.show();
			}
		},{
			text :'重置',
			handler : function(){
				_groupForm.getForm().reset();
			}
		}]
	});
	//查询结果窗口
	var resultWindow = new Ext.Window({
		layout : 'fit',
		maximized : true,
		width : document.body.scrollWidth,
    	height : document.body.scrollHeight-40,
		closable : true,// 是否可关闭
        title : '查询结果展示',
		modal : true,
		closeAction : 'hide',
		titleCollapse : true,
		buttonAlign : 'center',
		border : false,
		animCollapse : true,
		animateTarget : Ext.getBody(),
		constrain : true,
		items : [{
			layout:'border',
			items:[
				_groupForm,resultGrid
			]
		}],
		buttons : [{
			text : '关闭',
			handler : function() {
				resultWindow.hide();
				//重置分组面板
    			_groupForm.getForm().reset();
    			//移除条件查询结果列表
    			resultstore2.removeAll();
			}
		}]
	});
	
//查询结果方法
var fnSearchResult = function(){
	if(simple2.resultColumns.length==0){
		Ext.Msg.alert('提示', '未加入任何显示列！');
		return;
	}
	if(simple.conditions.length==0){
		Ext.Msg.alert('提示', '未加入任何条件列！');
		return;
	}
	if(!simple.getForm().isValid()){
		Ext.Msg.alert('提示', '查询条件输入有误！');
		return;
	}
	//初始化客户ID、客户名称、客户类型字段节点，从数据集树上获取
	if(QUERYUTIL.custBaseInfo.dbNode === false){
		QUERYUTIL.custBaseInfo.dbNode = treeOfPoroduct.root.findChild('VALUE',QUERYUTIL.custBaseInfo.dbTable,true);
		QUERYUTIL.custBaseInfo.idNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.idColumn);
		QUERYUTIL.custBaseInfo.nameNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.nameColumn);
		QUERYUTIL.custBaseInfo.typeNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.typeColumn);
	}
	//如结果列中无客户ID、名称、类型字段，则自动添加隐藏字段；
	simple2.addItems(QUERYUTIL.custBaseInfo.idNode,'0',true,false);
	simple2.addItems(QUERYUTIL.custBaseInfo.nameNode,'0',true,false);
	simple2.addItems(QUERYUTIL.custBaseInfo.typeNode,'0',true,false);
	//弹出窗口
	resultWindow.show();
	//根据查询结果列配置，修改查询结果数据源reader字段
	resultGrid.store.reader.onMetaChange(simple2.getResultReaderMetas());
	//根据查询结果列配置，修改查询结果列表面板列模型
	resultGrid.colModel.setConfig(simple2.getResultColumnHeaders(), false) ;
	
	//初始化统计字段下拉框数据，排除隐藏字段
	var sumColumnsData = [];
	Ext.each(simple2.resultColumns,function(column){
		if(column.hidden !== true){
			sumColumnsData.push(column);
		}
	});
	_sumStore.loadData(sumColumnsData);
	
	//初始化分组字段下拉框数据，排除隐藏字段
	var groupColumnsData = [];
	Ext.each(simple2.resultColumns,function(column){
		//if(column.nodeInfo.attributes.NOTES && column.hidden !== true){
		if(column.hidden !== true){
			groupColumnsData.push(column);
		}
	});
	_groupStore.loadData(groupColumnsData);
	
	//获取动态查询所需要的各个数据参数
	resultGrid.store.baseParams = {
		conditionAttrs : Ext.encode(simple.getConditionsAttrs()),
		results :  Ext.encode(simple2.getResults()),
		radio : right_panel.conditionJoinType
	};
	
	//设置导出参数，同查询参数
	Ext.getCmp("__expResult").exParams = resultGrid.store.baseParams ;
	
	//查询数据
	resultGrid.store.load({
		params: {
			start : 0,
			limit : _lpagesize_combo.getValue()
		}
	});
};


/**
 * 分组统计结果面板
 */
	/**
	 * 分组统计结果数据源
	 */
	var groupingStore = new Ext.data.Store({
		restful : true,
		url:basepath+'/queryagileresult.json',
		reader:new Ext.data.JsonReader({
			successProperty: 'success',
			messageProperty: 'message',
			fields : [{
				name:'CUST_ID'
			}]
		})
	});
	//分组统计结果列模型
	var groupingColumnModel = new Ext.grid.ColumnModel(simple2.getResultColumnHeaders);
	
	var selectModel = new Ext.grid.CheckboxSelectionModel();
    var resultNumber = new Ext.grid.RowNumberer({
        header : 'No.',
        width : 40
    });
	var _tbar2 = new Ext.Toolbar({
		items : [new Com.yucheng.bob.ExcelButton({
			  iconCls:'exportIconCss',
			  expGrid:'groupingGrid'
		})]});
	//分组统计列表
	var groupingGrid = new Ext.grid.GridPanel({
		title :'统计结果',
		frame : true,
		region : 'center', // 返回给页面的div
		store : groupingStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : groupingColumnModel, // 列模型
		sm : selectModel,
		tbar:_tbar2,
		viewConfig : {
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
//分组统计窗口
var groupingWindow = new Ext.Window({
	layout : 'fit',
	height : '400',
	width : '800',
	draggable : true,//是否可以拖动
	closable : true,// 是否可关闭
    title : '分组汇总统计展示',
	modal : true,
	closeAction : 'hide',
	titleCollapse : true,
	buttonAlign : 'center',
	border : false,
	animCollapse : true,
	animateTarget : Ext.getBody(),
	constrain : true,
	items : [groupingGrid],
	buttons : [{
		text : '关闭',
		handler : function() {
			groupingWindow.hide();
			groupingStore.removeAll() ;//移除分组统计数据结果
		}
	}]
});
//分组统计逻辑
var groupingPropere = function(){
	
	/**
	 * 准备表格列模型
	 */
	var groupingColumns = [];
	groupingColumns.push(resultNumber);
	groupingColumns.push(selectModel);
	
	var groupParams = "";//分组字段参数
	var sumParams = "";//统计字段参数
	
	if(simple2.getResultColumnHeaderByNodeId(LovCombo1.getValue())){
		groupingColumns.push(simple2.getResultColumnHeaderByNodeId(LovCombo1.getValue()));
		groupParams += LovCombo1.getValue().substring(1);
	}
	if(simple2.getResultColumnHeaderByNodeId(LovCombo2.getValue())){
		groupingColumns.push(simple2.getResultColumnHeaderByNodeId(LovCombo2.getValue()));
		if(!groupParams){
			groupParams +=LovCombo2.getValue().substring(1);
		}else {
			groupParams +=','+LovCombo2.getValue().substring(1);
		}
	}
	if(simple2.getResultColumnHeaderByNodeId(LovCombo3.getValue())){
		groupingColumns.push(simple2.getResultColumnHeaderByNodeId(LovCombo3.getValue()));
		if(!groupParams){
			groupParams +=LovCombo3.getValue().substring(1);
		} else {
			groupParams +=','+LovCombo3.getValue().substring(1);
		}
	}
	var sumColumns = simple2.getSumColumnsByNodeIds(LovCombo4.getValue());
	Ext.each(sumColumns,function(s){
		groupingColumns.push(s);
	});
	sumParams = LovCombo4.getValue().replace(/b/g, "");//剔除字段ID前缀：b
	
	
	groupingColumnModel.setConfig(groupingColumns,false);
	if(groupingGrid.rendered)
		groupingGrid.view.refresh(true);
	
	/**
	 * 准备数据列，store.reader
	 */
	var readerColumns = [];
	
	Ext.each(groupingColumns,function(col){
		var refield = {};
		refield.name = col.dataIndex;
		readerColumns.push(refield);
	});
	groupingStore.reader.onMetaChange({
		successProperty: 'success',
		messageProperty: 'message',
		idProperty: 'CUST_ID',
		root:'json.data',
		totalProperty: 'json.count',
		fields : readerColumns
	});
	
	groupingStore.baseParams = {
		conditionAttrs : Ext.encode(simple.getConditionsAttrs()),
		results : Ext.encode(simple2.getResults()),
		radio : right_panel.conditionJoinType,
		groupParams : groupParams,
		sumParams : sumParams
	};
	
	//分组字段数据，用于添加合计数据
	var groupCallback = [];
	if(simple2.getResultColumnHeaderByNodeId(LovCombo1.getValue())!=undefined)
		groupCallback.push(simple2.getResultColumnHeaderByNodeId(LovCombo1.getValue()));
	if(simple2.getResultColumnHeaderByNodeId(LovCombo2.getValue())!=undefined)
		groupCallback.push(simple2.getResultColumnHeaderByNodeId(LovCombo2.getValue()));
	if(simple2.getResultColumnHeaderByNodeId(LovCombo3.getValue())!=undefined)
		groupCallback.push(simple2.getResultColumnHeaderByNodeId(LovCombo3.getValue()));
	
	
	Ext.each(groupCallback,function(d){
		d.tmpData = false;
	});
	
	/**
	 * 根据指定开始、结束位置和字段名，返回统计值
	 */
	var sumField = function(firstIndex,index,field){
		var result = 0;
		for(var tIndex = firstIndex ; tIndex <= index; tIndex ++){
			var r = groupingStore.getAt(tIndex);
			var v = parseFloat(r.data[field]);
			if(!isNaN(v)){
				result += v;
			}
		}
		
		return result;
	}
	
	/**
	 * 判断数据是否为一个分组结束位置
	 */
	function isGroup(record){
		var changeLevel = -1;
		for(var i=groupCallback.length-2 ; i >= 0 ; i--){
			var value = record.data[groupCallback[i].dataIndex];
			var tValue = groupCallback[i].tmpData;
			if(tValue === false){
				groupCallback[i].tmpData = value;
				continue;
			}
			if(value == tValue){
				continue;
			} else {
				changeLevel = i;
				groupCallback[i].tmpData = value;
				return changeLevel;
			}
		}
		return changeLevel;
	};
	
	//加载数据，回调函数中动态添加合计数据
	groupingStore.load({
		callback:function(a,b,c,d,e){
			var counts = [];//小计数据集合
			var trIndex=0;//数据遍历游标
			var lastChangeLevels = [];//各级分组上以此分组结束位置
			
			var lastAbstractRecordData = {};//虚拟最后一条数据，用于最后一个分组统计，统计结束后被移除
			Ext.each(groupCallback,function(a){
				lastAbstractRecordData[a.dataIndex] = "lastAbstractRecord";
			});
			var lastAbstractRecord = new Ext.data.Record(lastAbstractRecordData);
			groupingStore.add(lastAbstractRecord);
			//遍历数据集，根据本条数据与上一条数据差异，确定该条数据是否一个分组的开始
			this.each(function(record){
				var changelevel = isGroup(record);
				while(changelevel!= -1){
					
					var dataField = {};
					dataField[groupCallback[changelevel].dataIndex] = "&nbsp;&nbsp;&nbsp;&nbsp;<font color='red'>小计</font>";
					Ext.each(sumColumns,function(sc){
						dataField[sc.dataIndex] = sumField(lastChangeLevels[changelevel]?lastChangeLevels[changelevel]:0,trIndex-1,sc.dataIndex);
					});
					dataField.dataLocation = trIndex;
					counts.push(dataField);
					lastChangeLevels[changelevel] = parseInt(trIndex);
					changelevel = parseInt(isGroup(record));
				}
				trIndex++;
			});
			groupingStore.remove(lastAbstractRecord);
			
			var totleRecord = {};
			totleRecord[groupCallback[0].dataIndex] = "<font color='red'>合计</font>";
			Ext.each(sumColumns,function(sc){
				totleRecord[sc.dataIndex] = sumField(0,groupingStore.getCount()-1,sc.dataIndex);
			});
			//加入最终合计项
			for(var reindex=0;reindex<counts.length;reindex++){
				var trecord = new Ext.data.Record(counts[reindex]);
				groupingStore.insert(counts[reindex].dataLocation + reindex,trecord);
			}
			groupingStore.add(new Ext.data.Record(totleRecord));
		}
	});
};
/**
 * 灵活查询面板入口
 */    

//结果域扩展功能面板
var customerView = [{
	/**
	 * 自定义灵活查询面板
	 */
	title:'灵活查询',
	hideTitle: true,
	layout:'fit',
	items: [treeOfPoroduct]
}];

//边缘面板配置
var edgeVies = {
	left : {
		width : 300,
		layout : 'fit',
		//collapsible : true,
		items : [grid]
	},
	right : {
		width : 600,
		//collapsible : false,
		items : [right_panel]
	}
};

