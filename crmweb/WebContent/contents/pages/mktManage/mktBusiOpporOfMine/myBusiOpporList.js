/**
 * 营销管理->商机管理->我的商机
 * 入口JS文件
 * wzy，2013-02-17
 */

/** ***************************全局变量定义*********开始**************** */
// 分页工具栏
var bbar = null;
// 每页显示条数下拉选择框
var pagesize_combo = null;
// 功能按钮
var tbar = null;
// 页面布局
var viewport = null;
// 查询结果表格
var grid = null;
// 查询结果分页条数
var number = null;
/** ***************************全局变量定义*********结束**************** */

/** ***************************下拉框对象定义*******开始**************** */
// 商机类型
var chanceStatStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=BUSI_CHANCE_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// 商机来源
var chanceSourceStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=BUSI_CHANCE_SOURCE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// 商机状态
var chanceStateStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=BUSI_CHANCE_STATUS'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// 达成概率
var chanceProbStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=BUSI_CHANCE_PROB'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// 商机阶段
var chanceStageStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=BUSI_CHANCE_STAGE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// 客户状态
var chanceTypeStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=CUSTOMER_STATUS'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
// 客户类别
var chanceCategoryStore = new Ext.data.Store({
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=XD000080'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
/** ***************************下拉框对象定义*******结束**************** */

/** ***************************业务逻辑执行*********开始**************** */
Ext.onReady(function() {

	// 提示信息组件初始化
	Ext.QuickTips.init();

	// 功能按钮定义
	tbar = new Ext.Toolbar({
		items : [ {
			text : '商机详情',
			iconCls : 'detailIconCss',
			handler : function() {
				viewInit();
			}
		}, '-', {
			text : '销售活动',
			iconCls : 'optionIconCss',
			handler : function() {
				salesActivInit();
			}
		}, '-', {
			text : '新增商机',
			iconCls : 'addIconCss',
			handler : function() {
				addInit();
			}
		}, '-', {
			text : '维护商机',
			iconCls : 'editIconCss',
			handler : function() {
				editInit();
			}
		}, '-', {
			text : '删除商机',
			iconCls : 'deleteIconCss',
			handler : function() {
				deleteInit();
			}
		}, '-', {
			text : '关闭商机',
			iconCls : 'custGroupMemIconCss',
			handler : function() {
				closeInit();
			}
		}, '-', {
			text : '退回商机',
			iconCls : 'closeIconCss',
			handler : function() {
				backInit();
			}
		}, '-', {
			text : '商机跟踪',
			iconCls : 'resetIconCss',
			handler : function() {
				followInit();
			}
		} ]
	});

	// 每页显示条数下拉选择框
	pagesize_combo = new Ext.form.ComboBox({
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
	number = parseInt(pagesize_combo.getValue());

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(comboBox.getValue());
		number = parseInt(comboBox.getValue());
		store.reload({
			params : {
				start : 0,
				limit : bbar.pageSize
			}
		});
	});

	// 分页工具栏
	bbar = new Ext.PagingToolbar({
		pageSize : number,
		store : store,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});

	// 定义查询结果表格
	grid = new Ext.grid.GridPanel({
		// height : document.body.scrollHeight-107,
		// width : document.body.scrollWidth-10,
		title : '我的商机列表',
		frame : true,
		autoScroll : true,
		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store : store, // 数据存储
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		sm : sm, // 复选框
		tbar : tbar, // 表格工具栏
		bbar : bbar,// 分页工具栏
		viewConfig : {
		// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		// forceFit : true
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

	// 拖动IE时.翻页条自适应
	Ext.EventManager.onWindowResize(function() {
		grid.setHeight(document.body.scrollHeight - 107);
		grid.setWidth(document.body.scrollWidth - 10);
		grid.getView().refresh();
	});

	// 页面布局
	viewport = new Ext.Viewport({
		layout : 'fit',
		items : [ {
			layout : 'border',
			items : [ qForm, grid ]
		} ]
	});

	// 商机详情
	function viewInit() {
		viewMyBusOpportInit();
	}

	// 商机跟踪
	function followInit() {
		busiOpportFollowWindowInit();
	}

	// 新增商机
	function addInit() {
		busiOpportAddWindowInit();
	}

	// 维护商机
	function editInit() {
		busiOpportEditWindowInit();
	}

	// 删除商机
	function deleteInit() {
		delRec();
	}

	// 关闭商机
	function closeInit() {
		busiOpportCloseWindowInit();
	}

	// 退回商机
	function backInit() {
		busiOpportReturnWindowInit();
	}

	// 销售活动管理
	function salesActivInit() {
		openSalesActivInit();
	}

	// 删除商机数据
	// 1、手动创建的商机，只能由创建人和执行人删除；
	// 2、系统创建的商机，只能由待分配机构主管删除；
	// 3、只有暂存、退回、到期退回的商机才能删除；
	function delRec() {
		var record = grid.getSelectionModel().getSelected();
		if (record == null) {
			Ext.Msg.alert('提示', '请先选择要删除的商机！');
			return false;
		}
		var checkedNodes = grid.getSelectionModel().selections.items;
		var oppor_stat = null;// 商机状态
		var opporIdS = '';// 选中的商机记录ID的集合
		var opporId = null;// 商机ID
		var opporSource = null;// 商机来源
		var createrId = null;// 创建人ID
		var executeUserId = null;// 执行人ID
		var assingOrgId = null;// 待分配机构ID
		for ( var i = 0; i < checkedNodes.length; i++) {
			// 1、控制只能删除“暂存(0)、退回(5)、到期退回(6)”状态的商机
			oppor_stat = checkedNodes[i].data.opporStat;
			if (oppor_stat != "0" && oppor_stat != "5" && oppor_stat != "6") {
				Ext.Msg.alert('提示', '只能删除“暂存、退回、到期退回”状态的商机！');
				return false;
			}
			opporId = checkedNodes[i].data.opporId;
			opporIdS += opporId;
			if (i < checkedNodes.length - 1) {
				opporIdS += ",";
			}
			// 2、控制删除权限：
			// “暂存”状态的商机，由“创建人”删除
			// 非“暂存”状态的商机，有执行人的商机只能由执行人删除，没有执行人的商机只能由待分配机构主管删除；
			opporSource = checkedNodes[i].data.opporSource;
			createrId = checkedNodes[i].data.createrId;
			executeUserId = checkedNodes[i].data.executeUserId;
			if (oppor_stat == "0") {
				// “暂存”状态的商机
				if (createrId != __userId) {
					// 当前用户不是商机创建人
					Ext.Msg.alert('提示', '你没有权限删除当前选中的商机！');
					return false;
				}
			} else {
				// 非“暂存”状态的商机
				if (opporSource == "0") {
					// 商机为手动创建
					if (createrId != __userId && executeUserId != __userId) {
						Ext.Msg.alert('提示', '你没有权限删除当前选中的商机！');
						return false;
					}
				} else {
					// 商机为系统创建
					assingOrgId = checkedNodes[0].data.assingOrgId;
					var queryUrl = basepath + '/mktBusiOpporOperationAction!'
							+ 'getOrgManager.json';
					Ext.Ajax.request({
						url : queryUrl,
						method : 'POST',
						sync : true,// 同步
						waitMsg : '正在查询数据,请等待...',
						params : {
							'orgId' : assingOrgId
						},
						success : function(response) {
							var orgManagerId = response.responseText;
							if (orgManagerId != __userId) {
								// 当前用户不是商机待分配机构主管
								Ext.Msg.alert('提示', '你没有权限删除当前选中的商机！');
								return false;
							}
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '你没有权限删除当前选中的商机！');
							return false;
						}
					});
				}
			}
		}
		// 3、执行删除操作
		doDelete(opporIdS);
	}

	// 执行商机删除操作
	function doDelete(opporIdS) {
		var delUrl = basepath
				+ '/mktBusiOpporOperationAction!delBusiOpporById.json?';
		Ext.MessageBox.confirm('提示', '您确定要删除吗？', function(buttonId) {
			if (buttonId.toLowerCase() == "no") {// 不删除
				return false;
			} else {// 要删除
				Ext.Ajax
						.request({
							url : delUrl,
							params : {
								'opporIdS' : opporIdS
							},
							waitMsg : '正在删除数据,请等待...',
							success : function() {
								Ext.Msg.alert('提示', '操作成功！');
								store.reload();
							},
							failure : function(response) {
								var resultArray = Ext.util.JSON
										.decode(response.status);
								if (resultArray == 403) {
									Ext.Msg.alert('提示', response.responseText);
								} else {
									Ext.Msg.alert('提示', '操作失败，失败原因：'
											+ response.responseText);
									store.reload();
								}
							}
						});
			}
		});
	}

	// 打开页面就进行数据查询
	store.load({
		params : {
			start : 0,
			limit : bbar.pageSize
		}
	});
});
/** ***************************业务逻辑执行*********结束**************** */
