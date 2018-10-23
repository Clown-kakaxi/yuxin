/**
 * 营销管理->商机管理->商机池：商机历史记录JS文件；wzy；2013-02-25
 */

var followStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/mktBusiOpporHisQueryAction.json'
	}),
	reader : new Ext.data.JsonReader({
		totalProperty : 'json.count',
		root : 'json.data'
	}, [ {
		name : 'OPR_USER_NAME'
	}, {
		name : 'OPR_ORG_NAME'
	}, {
		name : 'OPR_DATE_TIME'
	}, {
		name : 'OPR_CONTENT'
	} ])
});

var sm_follow = new Ext.grid.CheckboxSelectionModel();
var rownum_follow = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var cm_follow = new Ext.grid.ColumnModel([ rownum_follow, sm_follow, // 定义列模型
{
	header : '操作人名称',
	dataIndex : 'OPR_USER_NAME',
	sortable : true,
	width : 100
}, {
	header : '操作人机构',
	dataIndex : 'OPR_ORG_NAME',
	sortable : true,
	width : 140
}, {
	header : '操作时间',
	dataIndex : 'OPR_DATE_TIME',
	sortable : true,
	width : 130
}, {
	header : '操作内容',
	dataIndex : 'OPR_CONTENT',
	sortable : true,
	width : 350
} ]);

var listPanel_follow = new Ext.grid.GridPanel({
	layout : 'fit',
	frame : true,
	autoScroll : true,
	region : 'center', // 返回给页面的div
	store : followStore,
	stripeRows : true, // 斑马线
	sm : sm_follow,
	cm : cm_follow,
	height : 180,
	viewConfig : {},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});