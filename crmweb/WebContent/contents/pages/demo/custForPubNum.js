Ext.onReady(function() {
	Ext.QuickTips.init();   

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	//对公客户数量查询条件
	var qForm = new Ext.form.FormPanel({
		title : "对公客户数量查询",
		labelWidth : 100, // 标签宽度
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		buttonAlign : 'center',
		region : 'north',
		split : true,
		height : 120,
		width :1500,
		items : [ {
			layout : 'column',
			items : [ {
				columnWidth : .25,
				layout : 'form',
				items : [ {
					fieldLabel : '客户维护人',
					labelStyle: 'text-align:right;',
					xtype : 'textfield',
					Width : '100',
					name : 'CUST_MAINTAIN_NAME',
					anchor : '90%'
					//disabled:true   编辑区不能输入显灰色
				    //readOnly:true   只读
				} ]
			},{
					columnWidth : .25,
					layout : 'form',
					items : [ {
						fieldLabel : '账户吸存人',
						labelStyle: 'text-align:right;',
						xtype : 'textfield',
						Width : '100',
						name : 'ACCOUNT_XC_NAME',
						anchor : '90%'
					} ]
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

	//对公客户数量record
	var record = Ext.data.Record.create([{
		name : 'countId',
		mapping : 'COUNT_ID'
	},{
		name : 'custName',
		mapping : 'CUST_NAME'
	},{
		name : 'custMaintainName',
		mapping : 'CUST_MAINTAIN_NAME'
	},{
		name : 'nowAddDayAvg',
		mapping : 'NOW_ADD_DAY_AVG'
	},{
		name : 'accountNo',
		mapping : 'ACCOUNT_NO'
	},{
		name : 'addSaveDayAvg',
		mapping : 'ADD_SAVE_DAY_AVG'
	},{
		name : 'accountXcName',
		mapping : 'ACCOUNT_XC_NAME'
	},{
		name : 'dayAvgScaleCustNum',
		mapping : 'DAY_AVG_SCALE_CUST_NUM'
	}]);

	// 对公客户数量定义列模型
	var cm = new Ext.grid.ColumnModel([ rownum,{
		header : 'ID',
		width : 190,
		align : 'center',
		hidden:true,
		dataIndex : 'countId',
		sortable : true
	},{
		header : '客户名称',
		width : 190,
		align : 'center',
		dataIndex : 'custName',
		sortable : true
	}, {
		header : '客户维护人',
		width : 190,
		align : 'center',
		dataIndex : 'custMaintainName',
		sortable : true
	}, {
		header : '当前累计日均',
		width : 180,
		align : 'right',
		dataIndex : 'nowAddDayAvg',
		sortable : true
	},{
		header : '账号',
		width : 180,
		align : 'center',
		dataIndex : 'accountNo',
		sortable : true
	}, {
		header : '累计存款日均',
		width : 180,
		align : 'right',
		dataIndex : 'addSaveDayAvg',
		sortable : true
	},{
		header : '账户吸存人',
		width : 180,
		align : 'center',
		dataIndex : 'accountXcName',
		sortable : true
	}, {
		header : '按日均占比折算客户数（户）',
		width : 180,
		align : 'center',
		dataIndex : 'dayAvgScaleCustNum',
		sortable : true
	}]);

	/**
	 * 数据存储，对公客户数量
	 */
	var store = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/channelCust.json'
		}),
		reader : new Ext.data.JsonReader({
//			successProperty : 'success',
//			idProperty : 'COUNT_ID',
//			messageProperty : 'message',
//			root : 'json.data',
//			totalProperty : 'json.count'
			totalProperty:'num',// 记录总数
			root:'rows'// Json中的列表数据根节点
		}, record)
	});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","CUST_NAME":"广东宏泽集团有限公司","CUST_MAINTAIN_NAME":"周梦琪","NOW_ADD_DAY_AVG":"5,000","ACCOUNT_NO":"81222222","ADD_SAVE_DAY_AVG":"2,500","ACCOUNT_XC_NAME":"周梦琪","DAY_AVG_SCALE_CUST_NUM":"0.5"},
			{"rownum":"1","CUST_NAME":"嘉康利天然营养品保健公司","CUST_MAINTAIN_NAME":"杨攀山","NOW_ADD_DAY_AVG":"4,000","ACCOUNT_NO":"8122230","ADD_SAVE_DAY_AVG":"1,000","ACCOUNT_XC_NAME":"周梦琪","DAY_AVG_SCALE_CUST_NUM":"0.25"},
			{"rownum":"1","CUST_NAME":"北京汇智华威贸易有限公司","CUST_MAINTAIN_NAME":"赵新波","NOW_ADD_DAY_AVG":"8,000","ACCOUNT_NO":"8122233","ADD_SAVE_DAY_AVG":"6,000","ACCOUNT_XC_NAME":"梁瀚宇","DAY_AVG_SCALE_CUST_NUM":"0.75"},
			{"rownum":"1","CUST_NAME":" 北京东方情缘广告有限公司","CUST_MAINTAIN_NAME":"李梦曼","NOW_ADD_DAY_AVG":"3,000","ACCOUNT_NO":"8122249","ADD_SAVE_DAY_AVG":"1,500","ACCOUNT_XC_NAME":"郝红婵","DAY_AVG_SCALE_CUST_NUM":"0.5"},
			{"rownum":"1","CUST_NAME":"北京首禾环保科技有限公司","CUST_MAINTAIN_NAME":"曹乐","NOW_ADD_DAY_AVG":"6,000","ACCOUNT_NO":"81222343","ADD_SAVE_DAY_AVG":"6,000","ACCOUNT_XC_NAME":"曹乐","DAY_AVG_SCALE_CUST_NUM":"1.0"},
			{"rownum":"1","CUST_NAME":" 北京逐日文化传媒有限公司","CUST_MAINTAIN_NAME":"韩冰","NOW_ADD_DAY_AVG":"4,000","ACCOUNT_NO":"81222322","ADD_SAVE_DAY_AVG":"3,000","ACCOUNT_XC_NAME":"韩冰","DAY_AVG_SCALE_CUST_NUM":"0.75"}
			]
		};
	store.loadData(memberData);
			
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
		title : '对公客户数量统计列表',
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
	
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [ qForm, grid ]
	});

});