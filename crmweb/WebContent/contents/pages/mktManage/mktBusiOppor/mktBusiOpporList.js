/**
 * 营销管理->商机管理->商机池：布局入口JS文件；wzy；2013-02-22
 */

var view = null;// 页面布局对象
var listPanel = null;// 结果列表对象
Ext.onReady(function() {
	Ext.QuickTips.init();

	listPanel = new Ext.grid.GridPanel({
		layout : 'fit',
		frame : true,
		autoScroll : true,
		region : 'center', // 返回给页面的div
		store : store,
		stripeRows : true, // 斑马线
		sm : sm,
		cm : cm,
		tbar : [ {
			text : '商机详情',
			iconCls : 'detailIconCss',
			handler : function() {
				viewInit();
			}
		}, '-', {
			text : '商机跟踪',
			iconCls : 'resetIconCss',
			handler : function() {
				followInit();
			}
		}, '-', {
			text : '新增商机',
			iconCls : 'addIconCss',
			handler : function() {
				ifGroup = false;
				ifProd = false;
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
			text : '分配商机',
			iconCls : 'custGroupMemIconCss',
			handler : function() {
				allocatInit();
			}
		}, '-', {
			text : '认领商机',
			iconCls : 'optionIconCss',
			handler : function() {
				claimInit();
			}
		}, '-', {
			text : '审批商机',
			iconCls : 'ReadIconCss',
			handler : function() {
				claimAuditInit();
			}
		}, '-', {
			text : '退回商机',
			iconCls : 'closeIconCss',
			handler : function() {
				backInit();
			}
		} ],
		bbar : bbar,
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});

	// 页面布局
	view = new Ext.Viewport({
		layout : "fit",
		frame : true,
		items : [ {
			layout : 'border',
			items : [ {
				region : 'north',
				id : 'north-panel',
				title : "商机池查询",
				height : 155,
				layout : 'fit',
				items : [ searchPanel ]
			}, {
				region : 'center',
				id : 'center-panel',
				title : "商机池列表",
				layout : 'fit',
				items : [ listPanel ]
			} ]
		} ]
	});

	// 商机详情
	function viewInit() {
		busiOpportViewWindowInit();
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

	// 分配商机
	function allocatInit() {
		busiOpportAllocatWindowInit();
	}

	// 认领商机
	function claimInit() {
		busiOpportClaimWindowInit();
	}

	// 认领商机审核
	function claimAuditInit() {
		busiOpportClaimAuditWindowInit();
	}

	// 退回商机
	function backInit() {
		busiOpportReturnWindowInit();
	}

	// 删除商机数据
	// 1、手动创建的商机，只能由创建人和执行人删除；
	// 2、系统创建的商机，只能由待分配机构主管删除；
	// 3、只有暂存、退回、到期退回的商机才能删除；
	function delRec() {
		var record = listPanel.getSelectionModel().getSelected();
		if (record == null) {
			Ext.Msg.alert('提示', '请先选择要删除的商机！');
			return false;
		}
		var checkedNodes = listPanel.getSelectionModel().selections.items;
		var oppor_stat = null;// 商机状态
		var opporIdS = '';// 选中的商机记录ID的集合
		var opporId = null;// 商机ID
		var opporSource = null;// 商机来源
		var createrId = null;// 创建人ID
		var executeUserId = null;// 执行人ID
		var assingOrgId = null;// 待分配机构ID
		for ( var i = 0; i < checkedNodes.length; i++) {
			// 1、控制只能删除“暂存(0)、退回(5)、到期退回(6)”状态的商机
			oppor_stat = checkedNodes[i].data.OPPOR_STAT;
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