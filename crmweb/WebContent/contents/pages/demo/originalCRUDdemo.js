/**
 * 基础示例->CRUD原生例子
 * author weijl
 * since 2012-11-20
 */
Ext.onReady(function(){
	//性别数据源
	var sexStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
					url : basepath + '/lookup.json?name=sex'
				}),
		reader : new Ext.data.JsonReader({
					root : 'JSON'
				}, ['key', 'value'])
	});
	debugger;
	//条件查询面板
	var search_form = new Ext.form.FormPanel({
		//id : 'search_form',
		region: 'north',
	    id: 'north-panel',
	    title: "CRUD原生例子", 
	    //height: 148,
	    hidden:false,
	    margins: '0 0 0 0',
		labelWidth :60,
		frame : true,
		labelAlign : 'middle',
		buttonAlign : 'center',
		height : 100,
		items : [{
			layout : 'column',
			border : false,
			items : [{
				columnWidth : .5,
				layout : 'form',
				defaultType : 'textField',
				border : false,
				items : [{
					xtype:'textfield',
					id : 'user_name',
					name : 'user_name',
					fieldLabel : "姓名",
					labelAlign : 'text-align:right;',
					anchor : '90%'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				defaultType : 'textField',
				border : false,
				items : [{
					xtype : 'combo',
					id : 'sex',
					name : 'SEX',
					hiddenName : 'user_sex',
					store : sexStore,
					fieldLabel : "性别",
					labelAlign : 'text-align:right;',
					triggerAction : 'all',
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					resizable : true,
					anchor : '90%'
				}]
			}]
		}],
		buttons : [{
			text : "查询",
			handler : function(){
			
		//	var params = search_form.getForm().getValues(false);
			
		}
		},{
			text : "重置",
			handler : function(){
			search_form.getForm().reset();
		}
		}]
		
	});
	debugger;
	// 复选框
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	//列模型
	var columns = new Ext.grid.ColumnModel([ rownum,sm,{
		header : '隐藏id',
		dataIndex : 'id', 
		sortable : true,
		hidden : true,
		width : 120
	},{
        header : '姓名',
        dataIndex : 'name', 
        sortable : true,
        width : 120
    },{
        header : '性别',
        dataIndex : 'sex', 
        sortable : true,
        width : 120
    },{
        header : '年龄',
        dataIndex : 'age', 
        sortable : true,
        width : 120
    }]);
	
	var personProxy = new Ext.data.HttpProxy({
		url : basepath + '/person-info!indexPage.json'
	});
	
	var personRecord = new Ext.data.Record.create([
	    {name : 'id'},
	    {name : 'sex'},
	    {name : 'name'},
	    {name : 'age'}
	]);
	
	var personReader = new Ext.data.JsonReader({
		successProperty : 'success',
		root : 'json.data',
		totalProperty : 'json.count'
	},personRecord);
	
	//person查询数据源
	var personStore = new Ext.data.Store({
		restfull : true,
		proxy : personProxy,
		reader : personReader
	});
	
	//分页下拉框
	var pagesize_combo = new Ext.form.ComboBox({
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
	
	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()),
		personStore.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
	
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : personStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});
	debugger;
	var personPanel = new Ext.grid.GridPanel({
		store : personStore,
		frame : true,
		region:'center',
    	autoScroll:true,
	    //id: 'center-panel',
	    margins: '0 0 0 0',
		sm : sm,
		cm : columns,
		stripRows : true,
		tbar : [{
			
		},{
			
		},{
			
		},{
			
		}],
		bbar : bbar
	});
	 /*******************************************************************/
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items: [ 
			search_form,personPanel
	    ] 
	});
});
