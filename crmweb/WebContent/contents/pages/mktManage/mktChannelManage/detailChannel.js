//Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var detailQForm = new Ext.form.FormPanel({
		title : "渠道查询",
		labelWidth : 90, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		border : false,
		//region : 'north',
		split : true,
		//height : 120,
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .50,
				layout : 'form',
				items : [ {
					fieldLabel : '渠道ID',
					labelStyle: 'text-align:right;',
					xtype : 'textfield',
					Width : '100',
					name : 'CHANNEL_ID',
					anchor : '90%'
				} ]
			},{
				columnWidth : .50,
				layout : 'form',
				items : [ {
					fieldLabel : '渠道名称',
					labelStyle: 'text-align:right;',
					xtype : 'textfield',
					Width : '100',
					name : 'CHANNEL_NAME',
					anchor : '90%'
					//disabled:true   编辑区不能输入显灰色
				    //readOnly:true   只读
				} ]
			}]
		} ],
		buttons : [ {
			text : '查询',
			handler : function() {debugger;
				var conditionStr = detailQForm.getForm().getValues(false);
//				store.baseParams = {
//					"condition" : Ext.encode(conditionStr)
//				};
//				store.load({
//					params : {
//						start : 0,
//						limit : parseInt(pagesize_combo.getValue())
//					}
//				});
				detailChannelStore.loadData(channelData);
			}
		}, {
			text : '重置',
			handler : function() {
				detailQForm.getForm().reset();
			}
		} ]
	});

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	var record = Ext.data.Record.create([ {
		name : 'channelId',
		mapping : 'CHANNEL_ID'
	}, {
		name : 'channelName',
		mapping : 'CHANNEL_NAME'
	}, {
		name : 'channelDesc',
		mapping : 'CHANNEL_DESC'
	}, {
		name : 'channelFitCustLevel',
		mapping : 'CHANNEL_FIT_CUST_LEVEL'
	}, {
		name : 'channelModelId',
		mapping : 'CHANNEL_MODE_ID'
	}, {
		name : 'channelModelName',
		mapping : 'CHANNEL_MODE_NAME'
	}, {
		name : 'channelModelContent',
		mapping : 'CHANNEL_MODE_CONTENT'
	}, {
		name : 'createUserName',
		mapping : 'CREATE_USER_NAME'
	}, {
		name : 'createDate',
		mapping : 'CREATE_DATE'
	}]);

	// 定义列模型

	var cm = new Ext.grid.ColumnModel([ rownum, {
		header : '渠道编号',
		width : 100,
		align : 'center',
		dataIndex : 'channelId',
		sortable : true
	}, {
		header : '渠道名称',
		width : 150,
		align : 'center',
		dataIndex : 'channelName',
		sortable : true
	}, {
		header : '渠道适用客户级别',
		width : 150,
		align : 'center',
		dataIndex : 'channelFitCustLevel',
		sortable : true
	}, {
		header : '渠道宣传模版编号',
		width : 150,
		align : 'center',
		dataIndex : 'channelModelId',
		sortable : true
	}, {
		header : '渠道宣传模版名称',
		width : 150,
		align : 'center',
		dataIndex : 'channelModelName',
		sortable : true
	}, {
		header : '渠道宣传模版内容',
		width : 150,
		align : 'center',
		dataIndex : 'channelModelContent',
		sortable : true
	}, {
		header : '创建人',
		width : 100,
		align : 'left',
		dataIndex : 'createUserName',
		sortable : true
	}, {
		header : '创建日期',
		width : 100,
		align : 'left',
		dataIndex : 'createDate',
		sortable : true
}]);

	/**
	 * 数据存储
	 */
	var detailChannelStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/channelQuery.json'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'CHANNEL_ID',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, record)
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
	detailChannelStore.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()), detailChannelStore
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
		store : detailChannelStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});

	// 表格实例
	var detailGrid = new Ext.grid.GridPanel({
		title : '渠道基本信息列表',
		width:700,
		frame : true,
		height:295,
		autoScroll : true,
		region : 'center',
		store : detailChannelStore,
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		bbar : bbar,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
//});