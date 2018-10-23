/**
 * 专业化营销工具->产品精准营销工具->产品智能推荐：功能页面定义JS文件；wzy；2013-05-27
 */
// ======维度选择页面======
/** ******************行业******************** */
var hyStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['采矿业', '1'], ['制造业', '2'], ['建筑业', '3'],
					['电力、然气及水的生产和供应业', '4'], ['信息传输、计算机服务和软件业', '5'],
					['批发和零售业', '6'], ['住宿和餐饮业', '7'], ['金融业', '8'], ['教育', '9']]
		});
/** ******************规模******************** */
var gmStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['特大型', '1'], ['大一型', '2'], ['大二型', '3'], ['中一型', '4'],
					['中二型', '5'], ['小型', '6']]
		});
/** ******************地域******************** */
var dyStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['华北', '1'], ['东北', '2'], ['华东', '3'], ['华中', '4'],
					['华南', '5'], ['西南', '6'], ['西北', '7']]
		});
/** ******************总资产******************** */
var zzcStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['50万以下', '1'], ['50~100万', '2'], ['100~200万', '3'],
					['200~500万', '4'], ['500~1000万', '5'], ['1000~5000万', '6'],
					['5000万以上', '7']]
		});
/** ******************员工人数******************** */
var ygrsStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['50人以下', '1'], ['50~100人', '2'], ['100~200人', '3'],
					['200~500人', '4'], ['500~1000人', '5'], ['1000人以上', '6']]
		});
/** ******************主管机构指标******************** */
var zgjgzbStore = new Ext.data.ArrayStore({
			fields : ['key', 'value'],
			data : [['完全达标', '1'], ['基本达标', '2'], ['不达标', '3']]
		});
var form_wd = new Ext.FormPanel({
			formId : 'newNotice',
			frame : true,
			border : false,
			labelAlign : 'right',
			standardSubmit : false,
			layout : 'form',
			width : 640,
			items : [{
						layout : 'column',
						items : [{
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '行业',
												editable : false,
												emptyText : '请选择',
												name : 'f2',
												mode : 'local',
												anchor : '100%',
												triggerAction : 'all',
												store : hyStore,
												value:'1',
												valueField : 'value',
												displayField : 'key'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '规模',
												editable : false,
												emptyText : '请选择',
												name : 'f2',
												value:'2',
												mode : 'local',
												anchor : '100%',
												triggerAction : 'all',
												store : gmStore,
												valueField : 'value',
												displayField : 'key'
											}]
								}]
					}, {
						layout : 'column',
						items : [{
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '地域',
												mode : 'local',
												emptyText : '请选择',
												store : dyStore,
												value:'1',
												triggerAction : 'all',
												valueField : 'value',
												editable : false,
												displayField : 'key',
												name : 'f3',
												anchor : '100%'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '总资产',
												mode : 'local',
												emptyText : '请选择',
												store : zzcStore,
												triggerAction : 'all',
												valueField : 'value',
												editable : false,
												value:'2',
												displayField : 'key',
												name : 'f9',
												anchor : '100%'
											}]
								}]
					}, {
						layout : 'column',
						items : [{
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '员工人数',
												editable : false,
												emptyText : '请选择',
												name : 'f2',
												mode : 'local',
												anchor : '100%',
												value:'1',
												triggerAction : 'all',
												store : ygrsStore,
												valueField : 'value',
												displayField : 'key'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'combo',
												fieldLabel : '主管机构指标',
												editable : false,
												emptyText : '请选择',
												name : 'f2',
												mode : 'local',
												anchor : '100%',
												value:'2',
												triggerAction : 'all',
												store : zgjgzbStore,
												valueField : 'value',
												displayField : 'key'
											}]
								}]
					}]
		});

var win_wd = new Ext.Window({
			autoScroll : true,
			resizable : true,
			collapsible : true,
			maximizable : true,
			draggable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口
			animCollapse : true,
			border : false,
			loadMask : true,
			closable : true,
			constrain : true,
			width : 660,
			height : 180,
			title : '客户洞察',
			items : [form_wd],
			buttonAlign : 'center',
			buttons : [{
						text : '确定',
						handler : function() {
							// win_wd.hide();
							selectCp();
						}
					}, {
						text : '关闭',
						handler : function() {
							win_wd.hide();
						}
					}]
		});

// 打开选择维度窗体
function selectWd() {
	win_wd.show();
	form_wd.getForm().reset();
}

// ======产品选择并创建营销活动、商机页面======
var tbar = new Ext.Toolbar({
	items : [{
				text : '创建商机',
				iconCls : 'addIconCss',
				handler : function() {
					resetAddForm();
					addMyBusOpportInit02();
				}
			}, {
				text : '生成营销活动',
				iconCls : 'addIconCss',
				handler : function() {

					addActivityForm.form.reset();
					addActivityProdForm.form.reset();
					addActivityCustForm.form.reset();
					addActivityForm.form.findField('createUser')
							.setValue(__userId);
					addActivityForm.form.findField('test').setValue(__userName);
					addActivityForm.form.findField('createDate')
							.setValue(new Date());
					addActivityForm.form.findField('mktActiStat').setValue(1);
					addActivityForm.form.findField('mktActiName')
							.setValue('小企业扶持贷款推广');
					addActivityForm.form.findField('mktActiType')
							.setValue('推广活动');
					addActivityForm.form.findField('mktActiMode')
							.setValue('宣传');
					addActivityForm.form.findField('mktActiTeam')
							.setValue('小企业贷款组');
					addActivityForm.form.findField('mktActiCost')
							.setValue('1000');
					addActivityForm.form.findField('mktActiAddr')
							.setValue('南京市建邺区应天西路所叶路20号');
					addActivityForm.form.findField('mktActiCont')
							.setValue('宣传小企业的扶持贷款政策，吸引贷款');
					addActivityForm.form.findField('actiCustDesc')
							.setValue('该工业园区的小企业');
					addActivityForm.form.findField('actiOperDesc')
							.setValue('本行支行客户经理');
					addActivityForm.form.findField('actiProdDesc')
							.setValue('小企业扶持到款');
					addActivityForm.form.findField('mktActiAim').setValue('推广');
					addActivityForm.form.findField('actiRemark').setValue('无');

					addActivityWindow.show();

				}
			}]
});
// 列选择模型
var sm = new Ext.grid.CheckboxSelectionModel();
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
			header : 'NO',
			width : 28
		});
var store = new Ext.data.Store({
			reader : new Ext.data.JsonReader({
						root : 'rows',
						totalProperty : 'num'
					}, [{
								name : 'id'
							}, {
								name : 'productId'
							}, {
								name : 'productName'
							}, {
								name : 'productType'
							}, {
								name : 'order'
							}, {
								name : 'fitRate'
							}])
		});
var tb_memberData = {
	num : 1,
	rows : [{
				"id" : "1",
				"productId" : "701",
				"productName" : "订单融资业务",
				"productType" : "贵金属",
				"order" : "1",
				"fitRate" : "90%"
			}, {
				"id" : "2",
				"productId" : "702",
				"productName" : "厂商银",
				"productType" : "基金",
				"order" : "2",
				"fitRate" : "80%"
			}, {
				"id" : "3",
				"productId" : "703",
				"productName" : "法人账户透支业务",
				"productType" : "贵金属",
				"order" : "3",
				"fitRate" : "70%"
			}, {
				"id" : "4",
				"productId" : "704",
				"productName" : "国内保理",
				"productType" : "理财",
				"order" : "4",
				"fitRate" : "60%"
			}, {
				"id" : "5",
				"productId" : "705",
				"productName" : "保兑仓",
				"productType" : "贵金属",
				"order" : "5",
				"fitRate" : "54%"
			}, {
				"id" : "6",
				"productId" : "706",
				"productName" : "动产质押融资",
				"productType" : "贵金属",
				"order" : "6",
				"fitRate" : "47%"
			}, {
				"id" : "7",
				"productId" : "707",
				"productName" : "专利权质押贷款",
				"productType" : "理财",
				"order" : "7",
				"fitRate" : "43%"
			}, {
				"id" : "8",
				"productId" : "708",
				"productName" : "政府采购贷",
				"productType" : "证券",
				"order" : "8",
				"fitRate" : "37%"
			}, {
				"id" : "9",
				"productId" : "709",
				"productName" : "知识产权质押贷款",
				"productType" : "贵金属",
				"order" : "9",
				"fitRate" : "28%"
			}, {
				"id" : "0",
				"productId" : "710",
				"productName" : "软件著作权质押贷款",
				"productType" : "贵金属",
				"order" : "10",
				"fitRate" : "13%"
			}]
};
store.loadData(tb_memberData);
var productInfoColumns = new Ext.grid.ColumnModel([sm, rownum, {
			header : 'ID',
			dataIndex : 'id',
			id : "id",
			width : 150,
			sortable : true,
			hidden : true
		}, {
			header : '产品编号',
			dataIndex : 'productId',
			id : "productId",
			width : 150,
			sortable : true
		}, {
			header : '产品名称',
			dataIndex : 'productName',
			id : 'productName',
			width : 200,
			sortable : true
		}, {
			header : '产品类别',
			dataIndex : 'productType',
			id : 'productType',
			width : 150,
			sortable : true,
			hidden : true
		}, {
			header : '排名',
			dataIndex : 'order',
			id : 'order',
			width : 150,
			sortable : true,
			align : 'right'
		}, {
			header : '适合率',
			dataIndex : 'fitRate',
			id : 'fitRate',
			width : 150,
			sortable : true,
			align : 'right'
		}]);

// 每页显示条数下拉选择框
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
			value : '10',
			forceSelection : true,
			width : 85
		});

var number = parseInt(pagesize_combo.getValue());
pagesize_combo.on("select", function(comboBox) {
			bbar.pageSize = parseInt(pagesize_combo.getValue()), store.load({
						params : {
							start : 0,
							limit : parseInt(pagesize_combo.getValue())
						}
					});
		});
var bbar = new Ext.PagingToolbar({
			pageSize : number,
			store : store,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', pagesize_combo]
		});
var productGrid = new Ext.grid.GridPanel({// 用户列表数据grid
	frame : true,
	width : '100%',
	height : 320,
	id : 'productGrid',
	autoScroll : true,
	tbar : tbar,
	bbar : bbar,
	stripeRows : true, // 斑马线
	store : store,
	loadMask : true,
	cm : productInfoColumns,
	sm : sm,
	viewConfig : {
		forceFit : false,
		autoScroll : true
	},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var win_cp = new Ext.Window({
			autoScroll : true,
			resizable : true,
			collapsible : true,
			maximizable : true,
			draggable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口
			animCollapse : true,
			border : false,
			loadMask : true,
			closable : true,
			constrain : true,
			width : 760,
			height : 350,
			title : '产品选择',
			items : [productGrid],
			buttonAlign : 'center'
		});

// 打开产品选择窗体
function selectCp() {
	win_cp.show();
}
