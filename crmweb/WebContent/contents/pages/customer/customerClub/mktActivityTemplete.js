/**
 * 营销管理->营销活动管理->营销活动模板管理：主页面定义JS文件；wzy；2013-05-22
 */

var proxyIndex = new Ext.data.HttpProxy({
			url : basepath + '/noticequery.json?noticeTitle=asas（）'
		});

var TopicRecord = Ext.data.Record.create([{
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
		}]);

var reader = new Ext.data.JsonReader({
			totalProperty : 'num',// 记录总数
			root : 'rows'// Json中的列表数据根节点
		}, TopicRecord/** data record */
);

var writer = new Ext.data.JsonWriter({
			encode : false
		});

var sm = new Ext.grid.CheckboxSelectionModel();

var rownum = new Ext.grid.RowNumberer({
			header : 'NO',
			width : 28
		});

var cm1 = new Ext.grid.ColumnModel([rownum, sm, {
			header : '模板编号',
			dataIndex : 'f7',
			sortable : true,
			width : 100
		}, {
			header : '模板名称',
			dataIndex : 'f1',
			sortable : true,
			width : 180
		}, {
			header : '对应产品',
			dataIndex : 'f2',
			sortable : true,
			width : 100
		}, {
			header : '启用状态',
			sortable : true,
			dataIndex : 'f3',
			width : 80
		}, {
			header : '模板描述',
			sortable : true,
			dataIndex : 'f8',
			width : 260
		}]);

var restfulStore = new Ext.data.Store({
			id : 'notice',
			restful : true,
			proxy : proxyIndex,
			reader : reader,
			writer : writer,
			recordType : TopicRecord
		});

var pagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['value', 'text'],
						data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'],
								[100, '100条/页'], [250, '250条/页'],
								[500, '500条/页']]
					}),
			valueField : 'value',
			displayField : 'text',
			value : 20,
			editable : false,
			width : 85
		});

var bbar = new Ext.PagingToolbar({
			pageSize : parseInt(pagesize_combo.getValue()),
			store : restfulStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', pagesize_combo]
		});

pagesize_combo.on("select", function(comboBox) {
			bbar.pageSize = parseInt(pagesize_combo.getValue()), restfulStore
					.reload({
								params : {
									start : 0,
									limit : parseInt(pagesize_combo.getValue())
								}
							});
		});

// 河北银行对公POC，使用数据：和河北银行相关
var memberData = {
	TOTALCOUNT : 6,
	rows : [{
				"rownum" : "1",
				"f1" : "2013营销贷产品营销活动模板",
				"f2" : "营销贷",
				"f3" : "启用",
				"f4" : "2013-03-23",
				"f5" : "李小明",
				"f6" : "248",
				"f7" : "YXHDMB20130323",
				"f8" : "此模板是针对营销贷产品营销活动的模板"
			}, {
				"rownum" : "2",
				"f1" : "2012商圈贷产品营销活动模板",
				"f2" : "商圈贷",
				"f3" : "启用",
				"f4" : "2012-12-19",
				"f5" : "司马淘",
				"f6" : "432",
				"f7" : "YXHDMB20121219",
				"f8" : "此模板是针对商圈贷产品营销活动的模板"
			}, {
				"rownum" : "3",
				"f1" : "2012订单贷产品营销活动模板",
				"f2" : "订单贷",
				"f3" : "启用",
				"f4" : "2012-11-06",
				"f5" : "欧阳合一",
				"f6" : "848",
				"f7" : "YXHDMB20121106",
				"f8" : "此模板是针对订单贷产品营销活动的模板"
			}, {
				"rownum" : "4",
				"f1" : "2013订单贷产品营销活动模板",
				"f2" : "订单贷",
				"f3" : "启用",
				"f4" : "2013-03-03",
				"f5" : "吴东",
				"f6" : "654",
				"f7" : "YXHDMB20130303",
				"f8" : "此模板是针对订单贷产品营销活动的模板"
			}, {
				"rownum" : "5",
				"f1" : "2013商圈贷产品营销活动模板",
				"f2" : "商圈贷",
				"f3" : "启用",
				"f4" : "2013-03-01",
				"f5" : "单和",
				"f6" : "4242",
				"f7" : "YXHDMB20130301",
				"f8" : "此模板是针对商圈贷产品营销活动的模板"
			}, {
				"rownum" : "6",
				"f1" : "2013营销贷产品营销活动模板",
				"f2" : "营销贷",
				"f3" : "启用",
				"f4" : "2013-03-01",
				"f5" : "邱力",
				"f6" : "8432",
				"f7" : "YXHDMB20130301",
				"f8" : "此模板是针对营销贷产品营销活动的模板"
			}, {
				"rownum" : "7",
				"f1" : "2012商圈贷产品营销活动模板",
				"f2" : "商圈贷",
				"f3" : "启用",
				"f4" : "2012-10-26",
				"f5" : "许劭区",
				"f6" : "5639",
				"f7" : "YXHDMB20121026",
				"f8" : "此模板是针对商圈贷产品营销活动的模板"
			}]
};
restfulStore.loadData(memberData);

var grid_templete = new Ext.grid.GridPanel({
			frame : true,
			store : restfulStore,
			region : 'center',
			stripeRows : true,
			cm : cm1,
			sm : sm,
			bbar : bbar,
			viewConfig : {},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});