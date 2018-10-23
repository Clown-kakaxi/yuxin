/** *******************定义“指标”选择控件***********开始********* */
// 定义自动当前页行号
var planRownum = new Ext.grid.RowNumberer({
	header : 'NO',
	width : 28
});

var sm_p = new Ext.grid.CheckboxSelectionModel();
var planProdColumns = new Ext.grid.ColumnModel([ planRownum, sm_p, {
	header : '指标名称',
	align : 'left',
	dataIndex : 'fd1',
	sortable : true,
	width : 120
}, {
	header : '指标描述',
	align : 'left',
	dataIndex : 'fd2',
	sortable : true,
	width : 150
}, {
	header : '指标分类',
	align : 'left',
	dataIndex : 'fd3',
	sortable : true,
	width : 120
} ]);

var planProdRecord = Ext.data.Record.create([ {
	name : 'fd1'
}, {
	name : 'fd2'
}, {
	name : 'fd3'
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
	TOTALCOUNT : 8,
	rows : [ {
		"rownum" : "1",
		"fd1" : "评级得分",
		"fd2" : "评级得分",
		"fd3" : "基础指标"
	}, {
		"rownum" : "2",
		"fd1" : "信用等级得分",
		"fd2" : "信用等级得分",
		"fd3" : "基础指标"
	}, {
		"rownum" : "3",
		"fd1" : "授信额度得分",
		"fd2" : "授信额度得分",
		"fd3" : "基础指标"
	}, {
		"rownum" : "4",
		"fd1" : "模拟利润得分",
		"fd2" : "模拟利润得分",
		"fd3" : "基础指标"
	}, {
		"rownum" : "5",
		"fd1" : "合作年限得分",
		"fd2" : "合作年限得分",
		"fd3" : "基础指标"
	}, {
		"rownum" : "6",
		"fd1" : "是否股东得分",
		"fd2" : "是否股东得分",
		"fd3" : "基础指标"
	}, {
		"rownum" : "7",
		"fd1" : "年收入得分",
		"fd2" : "年收入得分",
		"fd3" : "基础指标"
	}, {
		"rownum" : "8",
		"fd1" : "五级分类得分",
		"fd2" : "五级分类得分",
		"fd3" : "基础指标"
	} ]
};
planProdStore.loadData(memberData);

var mktAssuListPanelQuota = new Ext.grid.GridPanel({
	layout : 'fit',
	autoScroll : true,
	region : 'center', // 返回给页面的div
	height : 245,
	store : planProdStore,
	frame : true,
	sm : sm_p,
	cm : planProdColumns,
	stripeRows : true,
	buttonAlign : "center",
	buttons : [ {
		text : '选择',
		handler : function() {
			var _record = mktAssuListPanelQuota.getSelectionModel()
					.getSelected();
			var s_name = "";
			if (_record) {
				s_name = _record.data.fd1;
			}
			if (Ext.getCmp('productSelect_close_quota_01')) {
				Ext.getCmp('productSelect_close_quota_01').setValue(s_name);
			}
			busOpportCloseQuotaWindow.hide();
		}
	} ]
});

// 定义详情查看窗口
var busOpportCloseQuotaWindow = new Ext.Window({
	title : '指标选择',
	plain : true,
	layout : 'fit',
	width : 500,
	height : 300,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'right',
	border : false,
	constrain : true,
	items : [ mktAssuListPanelQuota ]
});

function busOpportCloseQuotaView() {
	busOpportCloseQuotaWindow.show();
}
/** *******************定义“指标”选择控件***********开始********* */
