/**
 * 客户营销信息，静态页面
 * wzy，2013-03-13
 */
var fields = [], columns = [], data = [], grid = null, viewport = null;

Ext.onReady(function() {
	// 定义自动当前页行号
	var planRownum = new Ext.grid.RowNumberer({
		header : 'NO',
		width : 28
	});

	var sm_p = new Ext.grid.CheckboxSelectionModel();
	var planProdColumns = new Ext.grid.ColumnModel([ planRownum, sm_p, {
		header : '接触渠道',
		align : 'left',
		dataIndex : 'f1',
		sortable : true,
		width : 100
	}, {
		header : '营销类型',
		align : 'left',
		dataIndex : 'f2',
		sortable : true,
		width : 80
	}, {
		header : '营销事件名称',
		align : 'left',
		dataIndex : 'f3',
		sortable : true,
		width : 120
	}, {
		header : '营销状态',
		align : 'left',
		dataIndex : 'f4',
		sortable : true,
		width : 80
	}, {
		header : '执行时间',
		align : 'left',
		dataIndex : 'f5',
		sortable : true
	}, {
		header : '执行人',
		align : 'left',
		dataIndex : 'f6',
		sortable : true,
		width : 80
	}, {
		header : '创建机构',
		align : 'left',
		dataIndex : 'f7',
		sortable : true
	}, {
		header : '创建人',
		align : 'left',
		dataIndex : 'f8',
		sortable : true,
		width : 80
	}, {
		header : '创建时间',
		align : 'left',
		dataIndex : 'f9',
		sortable : true
	} ]);

	var planProdRecord = Ext.data.Record.create([ {
		name : 'f1'
	}, {
		name : 'f2'
	}, {
		name : 'f3'
	}, {
		name : 'f4'
	}, {
		name : 'f5'
	}, {
		name : 'f6'
	}, {
		name : 'f7'
	}, {
		name : 'f8'
	}, {
		name : 'f9'
	} ]);

	var planProdStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/planProductQuery.json',
			failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if (resultArray == 403) {
					Ext.Msg.alert('提示', response.responseText);
				}
			}
		}),
		reader : new Ext.data.JsonReader({
			totalProperty : 'num',// 记录总数
			root : 'rows'// Json中的列表数据根节点
		}, planProdRecord)
	});

	var memberData = {
		TOTALCOUNT : 3,
		rows : [ {
			"rownum" : "1",
			"f1" : "手机银行",
			"f2" : "营销活动",
			"f3" : "信用卡营销",
			"f4" : "执行中",
			"f5" : "2013-03-23",
			"f6" : "李小明",
			"f7" : "成都分行",
			"f8" : "张强",
			"f9" : "2011-05-09"
		}, {
			"rownum" : "2",
			"f1" : "网银",
			"f2" : "销售活动",
			"f3" : "小额贷款营销",
			"f4" : "正常关闭",
			"f5" : "2013-03-03",
			"f6" : "吴东",
			"f7" : "内江支行",
			"f8" : "礼仪",
			"f9" : "2011-03-11"
		}, {
			"rownum" : "3",
			"f1" : "短信",
			"f2" : "销售活动",
			"f3" : "信用支付营销",
			"f4" : "到期关闭",
			"f5" : "2013-03-01",
			"f6" : "三和",
			"f7" : "自贡支行",
			"f8" : "威志",
			"f9" : "2011-02-07"
		}, {
			"rownum" : "4",
			"f1" : "柜面",
			"f2" : "营销活动",
			"f3" : "定期存款营销",
			"f4" : "执行中",
			"f5" : "2012-12-19",
			"f6" : "司马",
			"f7" : "乐山支行",
			"f8" : "又系",
			"f9" : "2011-04-26"
		}, {
			"rownum" : "5",
			"f1" : "邮件",
			"f2" : "营销活动",
			"f3" : "活期存款营销",
			"f4" : "已退回",
			"f5" : "2012-11-06",
			"f6" : "欧阳",
			"f7" : "雅安支行",
			"f8" : "林侃",
			"f9" : "2012-02-22"
		}, {
			"rownum" : "6",
			"f1" : "客户经理",
			"f2" : "销售活动",
			"f3" : "零存整取营销",
			"f4" : "已提交",
			"f5" : "2012-10-26",
			"f6" : "许劭区",
			"f7" : "绵阳支行",
			"f8" : "须有",
			"f9" : "2011-03-18"
		} ]
	};
	planProdStore.loadData(memberData);

	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 250, '250条/页' ], [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		// editable : false,
		width : 85
	});

	// 获取查询结果分页条数
	var number = parseInt(pagesize_combo.getValue());

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(comboBox.getValue());
		number = parseInt(comboBox.getValue());
		planProdStore.reload({
			params : {
				start : 0,
				limit : bbar.pageSize
			}
		});
	});

	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : planProdStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});

	var salePanel = new Ext.grid.GridPanel({
		height : document.body.scrollHeight - 40,
		width : document.body.scrollWidth - 10,
		title : '客户营销信息',
		layout : 'fit',
		autoScroll : true,
		region : 'center', // 返回给页面的div
		store : planProdStore,
		frame : true,
		sm : sm_p,
		cm : planProdColumns,
		bbar : bbar,// 分页工具栏
		stripeRows : true
	});

	// 拖动IE时.翻页条自适应
	Ext.EventManager.onWindowResize(function() {
		salePanel.setHeight(document.body.scrollHeight - 40);
		salePanel.setWidth(document.body.scrollWidth - 10);
		salePanel.getView().refresh();
	});

	new Ext.Panel({
		renderTo : 'viewport_center',
		labelWidth : 250,
		layout : 'fit',
		primary : "id",
		buttonAlign : "center",
		items : [ salePanel ]
	});
});