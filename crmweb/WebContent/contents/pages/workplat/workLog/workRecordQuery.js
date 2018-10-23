Ext.onReady(function() {
	Ext.QuickTips.init();  
	var cust_name="";
	var worklog_stat="";
	var work_date="";
	//频度Store
	 var selFreqStore = new Ext.data.ArrayStore({
	        fields:['key','value'],
	        data:[['D','日工作记录'],['W','周工作记录'],['M','月工作记录'],['Q','季度工作记录'],['Y','年度工作记录']]
	        });
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	//工作记录查询条件
	var qForm = new Ext.form.FormPanel({
		title : "日志记录检查",
		labelWidth : 100, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		buttonAlign : 'center',
		region : 'north',
		split : true,
		height : 120,
		width :1500,
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					fieldLabel : '选择频度',
					xtype : 'combo',
					id : 'selFrequent ',
					name : 'WORKLOG_TYPE',
					hiddenName : 'WORKLOG_TYPE',
					store : selFreqStore,
					triggerAction : 'all',
					valueField : 'key',
					displayField : 'value',
					editable : false,
					emptyText : '请选择',
					mode : 'local',
					anchor : '90%'
				} ]
			},{
					columnWidth : .25,
					layout : 'form',
					items : [ {
						fieldLabel : '起始日期',
						xtype : 'datefield',
						format : 'Y-m-d',
						name : 'START_DATE',
						anchor : '90%'
					} ]
			},{
				columnWidth : .25,
				layout : 'form',
				items : [ {
					fieldLabel : '截至日期',
					xtype : 'datefield',
					format : 'Y-m-d',
					name : 'END_DATE',
					anchor : '90%'
			}]
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {
				var conditionStr = qForm.getForm().getValues(false);
				store.baseParams = {
					"condition" : Ext.encode(conditionStr)
				};
				store.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			}
		}, {
			text : '重置',
			handler : function() {
				qForm.getForm().reset();
			}
		} ]
	});
	//工作记录检查record
	var record = Ext.data.Record.create([{
		name : 'countId',
		mapping : 'COUNT_ID'
	},{
		name : 'userName',
		mapping : 'USERNAME'
	},{
		name : 'xcNo',
		mapping : 'USERCODE'
	},{
		name : 'superUnitName',
		mapping : 'SUPER_UNIT_NAME'
	},{
		name : 'unitName',
		mapping : 'UNITNAME'
	},{
		name : 'worklogStat',
		mapping : 'WORKLOG_STAT_ORA'
	},{
		name : 'successNum',
		mapping : 'UNITNAME'
	},{
		name : 'lostNum',
		mapping : 'UNITNAME'
	},{
		name : 'workDate',
		mapping : 'ETLDATE'
	},{name:'logid',mapping:'LOGID'},
	{name:'owenerid',mapping:'OWENERID'},
	{name:'owenername',mapping:'OWENERNAME'},
	{name:'newcustname',mapping:'NEWCUSTNAME'},
	{name:'newctvitme',mapping:'NEWCTVITME'},
	{name:'newcusttel',mapping:'NEWCUSTTEL'},
	{name:'newcustvtman',mapping:'NEWCUSTVTMAN'},
	{name:'newcustthing',mapping:'NEWCUSTTHING'},
	{name:'oldcustname',mapping:'OLDCUSTNAME'},
	{name:'oldctvitme',mapping:'OLDCTVITME'},
	{name:'oldcusttel',mapping:'OLDCUSTTEL'},
	{name:'oldcustvtman',mapping:'OLDCUSTVTMAN'},
	{name:'oldcustthing',mapping:'OLDCUSTTHING'},
	{name:'monweekplan',mapping:'MONWEEKPLAN'},
	{name:'loancomname',mapping:'LOANCOMNAME'},
	{name:'loancombal',mapping:'LOANCOMBAL'},
	{name:'loanproce',mapping:'LOANPROCE'},
	{name:'needtodo',mapping:'NEEDTODO'},
	{name:'etldate',mapping:'ETLDATE'},
	{name:'adddate',mapping:'ADDDATE'}]);

	// 工作记录检查定义列模型
	var cm = new Ext.grid.ColumnModel([ rownum,{
		header : '姓名',
		width : 190,
		align : 'center',
		dataIndex : 'userName',
		sortable : true
	}, {
		header : '吸存号',
		width : 190,
		align : 'center',
		dataIndex : 'xcNo',
		sortable : true
	}, {
		header : '所在分行',
		width : 180,
		align : 'center',
		dataIndex : 'superUnitName',
		sortable : true
	},{
		header : '所在单位',
		width : 180,
		align : 'center',
		dataIndex : 'unitName',
		sortable : true
	}, {
		header : '9月1日',
		width : 180,
		align : 'center',
		dataIndex : 'worklogStat',
		sortable : true
	},{
		header : '9月2日',
		width : 180,
		align : 'center',
		cellclick:true,
		dataIndex : 'worklogStat',
		sortable : true
	}, {
		header : '9月3日',
		width : 180,
		align : 'center',
		cellclick:true,
		dataIndex : 'worklogStat',
		sortable : true
	}, {
		header : '9月4日',
		width : 180,
		align : 'center',
		cellclick:true,
		dataIndex : 'worklogStat',
		sortable : true
	},{
		header : '9月5日',
		width : 180,
		align : 'center',
		cellclick:true,
		dataIndex : 'worklogStat',
		sortable : true
	}, {
		header : '成功记录',
		width : 180,
		align : 'center',
		dataIndex : 'successNum',
		sortable : true
	}, {
		header : '缺失记录',
		width : 180,
		align : 'center',
		dataIndex : 'lostNum',
		sortable : true
	}, {
		header : '工作日期',
		width : 180,
		align : 'right',
		hidden : true,
		dataIndex : 'workDate',
		sortable : true
	}]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
		restful : true,
		autoLoad:false,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/workRecordQuery.json?cust_name='+cust_name+"&worklog_stat="+worklog_stat+"&work_date="+work_date
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'COUNT_ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, record)
	});
	
	store.on('beforeload', function(){
    	this.baseParams = {
        	"cust_name":cust_name,
        	"worklog_stat":worklog_stat,
        	"work_date":work_date
      };
	});
			
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
					[ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		editable : false,
		width : 85
	});

	// 默认加载数据
	store.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()), store
				.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});

	// 表格实例
	var grid = new Ext.grid.GridPanel({
		title : '日工作记录登记情况列表',
		frame : true,
		layout : 'fit',
		region : 'center',
		store : store,
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		bbar : bbar,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

//*******************************************************日志详细信息**********************************************
	var colModle = new Ext.grid.ColumnModel([rownum,
			{header :'日工作记录',dataIndex:'logid',id:"logid",width:100,sortable : true},
			{header :'客户经理编号',dataIndex:'owenerid',width:125,sortable : true},
			{header : '客户经理姓名',dataIndex:'owenername',width:125,sortable : true},
			{header :'新客户名称',dataIndex:'newcustname',width:120,sortable : true},
			{header :'新客户拜访方式',dataIndex:'newctvitme',width:120,sortable : true},
			{header :'新客户联系电话 ',dataIndex:'newcusttel',width:120,sortable : true},
			{header :'新客户拜访人',dataIndex:'newcustvtman',width:120,sortable : true},
			{header :'新客户拜访联系和拜访情况',dataIndex:'newcustthing',width:180,sortable:true},
			{header :'老客户名称',dataIndex:'oldcustname',width:120,sortable:true},
			{header :'老客户联系电话',dataIndex:'oldcusttel',width:120,sortable:true},
			{header :'拜访方式',dataIndex:'oldctvitme',width:120,sortable:true},
			{header :'老客户拜访人',dataIndex:'oldcustvtman',width:120,sortable:true},
			{header :'老客户拜访联系和 拜访情况',dataIndex:'oldcustthing',width:150,sortable:true},
			{header :'月度，周计划进度和完成情况',dataIndex:'monweekplan',width:180,sortable:true},
			{header :'贷后检查企业名称',dataIndex:'loancomname',width:150,sortable:true},
			{header :'贷后检查贷款余额',dataIndex:'loancombal',width:150,sortable:true},
			{header :'贷后检查落实情况',dataIndex:'loanproce',width:150,sortable:true},
			{header :'关注和需要解决的问题以及分行的建议',dataIndex:'needtodo',width:220,sortable:true},
			{header :'工作日期',dataIndex:'etldate',width:100,sortable:true},
			{header :'补录日期',dataIndex:'adddate',width:105,sortable:true}
			]);

 	var tempDailyWorkGrid =  new Ext.grid.GridPanel({//产品列表数据grid
 		frame:true,
 		id:'tempDailyWorkGrid',
 		width : 890,
 		height: 420,
 		autoScroll : true,
 		store:store,
 		loadMask:true,
 		cm :colModle,
 		bbar:bbar,
 		loadMask : {
 	    	msg : '正在加载表格数据,请稍等...'
 		}
 });	
	
	// 定义缺失状态日志的窗口
	var tempDetailWindow = new Ext.Window({
		title : '缺失日志详细信息',
		plain : true,
		//layout : 'fit',
		width : 900,
		height : 450,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ tempDailyWorkGrid ]
	});
	
 	var passDailyWorkGrid =  new Ext.grid.GridPanel({//产品列表数据grid
 		frame:true,
 		id:'passDailyWorkGrid',
 		width : 890,
 		height: 420,
 		autoScroll : true,
 		store:store,
 		loadMask:true,
 		cm :colModle,
 		bbar:bbar,
 		loadMask : {
 	    	msg : '正在加载表格数据,请稍等...'
 		}
 });
 	
	// 定义提交状态日志的窗口
	var passDetailWindow = new Ext.Window({
		title : '提交日志详细信息',
		plain : true,
		//layout : 'fit',
		width : 900,
		height : 450,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ passDailyWorkGrid ]
	});
	
	grid.on('cellclick',function(grid,row,col,sm){//获取编辑的行数，从0开始，
		if(col==5||col==6||col==7||col==8||col==9){
			record=grid.store.getAt(row);
			worklog_stat=record.data['worklogStat'];
			alert(worklog_stat);
			if(worklog_stat=='0'){
				cust_name=record.data['userName'];
				work_date=record.data['workDate'];
//				alert(cust_name);
//				alert(work_date);
				tempDailyWorkGrid.store.reload();
				tempDetailWindow.show();
			}else{
				passDetailWindow.show();
			}
		}
	});	
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [ qForm, grid ]
	});

});