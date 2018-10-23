/**
 * 集团成员维护
 */
Ext.onReady(function() {
	var sujian = groupNo;
	var beforeRemoveId;
	var afterRemoveId;
	var treeId;
	var treeName;
	Ext.QuickTips.init();
	// var memberTypeStore = new Ext.data.Store({
	// restful:true,
	// autoLoad :true,
	// /*listeners:{
	// 'beforeload':function(){
	// // Ext.getCmp('memberType').focus();
	// // Ext.getCmp('memberType').triggerBlur();
	// // Ext.getCmp('memberType').reset();
	// }
	// },*/
	// proxy : new Ext.data.HttpProxy({
	// url :basepath+'/lookup.json?name=MEMBER_TYPE'
	// }),
	// reader : new Ext.data.JsonReader({
	// root : 'JSON'
	// }, [ 'key', 'value' ])
	// });
	//	
	var memberTypeStore = new Ext.data.ArrayStore({
				fields : ['key', 'value'],
				data : [['1', '标准成团'], ['2', '对外担保成员']]
			});

	// var relationIdStore = new Ext.data.Store({
	// restful:true,
	// autoLoad :true,
	// proxy : new Ext.data.HttpProxy({
	// url :basepath+'/lookup.json?name=RELATION_ID'
	// }),
	// reader : new Ext.data.JsonReader({
	// root : 'JSON'
	// }, [ 'key', 'value' ])
	// });
	//	
	var relationIdStore = new Ext.data.ArrayStore({
				fields : ['key', 'value'],
				data : [['1', '分公司'], ['2', '子公司']]
			});

	var memberRelation = new Ext.form.FormPanel({// 成员关系维护表单
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		collapsible : true,// 是否可收缩
		// region:'center',
		autoScroll : true,
		title : '成员关系信息-设置关系界面',
		buttonAlign : 'center',
		height : 135,
		width : document.body.clientWidth - 205,
		items : [{
			layout : 'column',
			// border : false,
			items : [{
						// fieldLabel : '<span
						// style="color:red">*</span>集团客户编号',
						name : 'groupNo',
						id : 'groupNo',
						xtype : 'textfield',
						hidden : true
					}, {
						// fieldLabel : '企业id',
						name : 'custId',
						id : 'custId',
						xtype : 'textfield',
						hidden : true
					}, {
						name : 'parentId',
						id : 'parentId',
						xtype : 'textfield',
						hidden : true
					}, {
						columnWidth : .25,
						layout : 'form',
						defaultType : 'textfield',
						labelWidth : 90, // 标签宽度
						// border : false,
						items : [{
							fieldLabel : '<span style="color:red">*</span>上级单位',
							xtype : 'textfield', // 设置为数字输入框类型
							name : 'parentName',
							labelStyle : 'text-align:right;',
							id : 'parentName',
							readOnly : true,
							allowBlank : false,
							anchor : '90%'
						}]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90, // 标签宽度
						items : [{
							store : memberTypeStore,
							xtype : 'combo',
							name : 'memberType',
							id : 'memberType',
							fieldLabel : '<span style="color:red">*</span>成员类型',
							valueField : 'key',
							labelStyle : 'text-align:right;',
							allowBlank : false,
							displayField : 'value',
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							width : 100,
							selectOnFocus : true,
							anchor : '100%'
						}

						]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90, // 标签宽度
						items : [{
							store : relationIdStore,
							xtype : 'combo',
							name : 'relationId',
							// hiddenName : 'relationId',
							id : 'relationId',
							fieldLabel : '<span style="color:red">*</span>成员关系',
							valueField : 'key',
							displayField : 'value',
							labelStyle : 'text-align:right;',
							allowBlank : false,
							mode : 'local',
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							width : 100,
							selectOnFocus : true,
							anchor : '100%'
						}

						]
					}, {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90, // 标签宽度
						defaultType : 'textfield',
						// border : false,
						items : [{
							fieldLabel : '<span style="color:red">*</span>持股比例(%)',
							name : 'stockRate',
							id : 'stockRate',
							xtype : 'numberfield', // 设置为数字输入框类型
							disabled : false,
							decimalPrecision : 2,
							maxValue : 100,
							allowBlank : false,
							align : 'right',
							labelStyle : 'text-align:right;',
							anchor : '100%'
						}]
					}]
		}, {
			columnWidth : .25,
			layout : 'form',
			labelWidth : 90, // 标签宽度
			height : 60,
			defaultType : 'textfield',
			// border : false,
			items : [{
						xtype : 'textarea',
						fieldLabel : '备  注',
						labelStyle : 'text-align:right;',
						name : 'remark',
						id : 'remark',
						maxLength : 150,
						anchor : '100%'
					}]
		}]
	});
	var windowFormSearchMember = new Ext.form.FormPanel({
				title : '待加入集团成员搜索-查询条件',
				frame : true, // 是否渲染表单面板背景色
				labelAlign : 'right', // 标签对齐方式
				collapsible : true,// 是否可收缩
				region : 'north',
				autoScroll : true,
				layout : 'fit',
				buttonAlign : 'center',
				height : 100,
				width : document.body.clientWidth - 205,
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .25,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										fieldLabel : '组织机构代码',
										id : 'CUST_ZZDMM',
										name : 'CUST_ZZDMM',
										anchor : '100%'
									}]
						}, {
							columnWidth : .25,
							layout : 'form',
							items : [{
										xtype : 'textfield',
										fieldLabel : '客户名称',
										id : 'CUST_ZH_NAME',
										name : 'CUST_ZH_NAME',
										anchor : '100%'
									}]
						}],
				buttons : [{
					text : '查询',
					handler : function() {
						var tempNO = Ext.getCmp("memberType").getValue();
						if (tempNO == "" || tempNO == null) {
							Ext.Msg.alert("系统提示", "请选择成员类型！");
							return false;
						}
						// var ageD = Ext.getCmp("AGE").getValue();
						// //alert(ageD);
						// if(ageD<0||ageD>200){
						// alert("年龄输入不合理，请重新输入！");
						// }else{
						var conditionStr = windowFormSearchMember.getForm()
								.getFieldValues();
						windowstore.baseParams = {
							"condition" : Ext.encode(conditionStr),
							'tempType' : Ext.getCmp("memberType").getValue(),
							'tempGroupNo' : sujian
						};
						windowstore.load({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});

					}
				}, {
					text : '重置',
					handler : function() {
						windowFormSearchMember.getForm().reset();
					}
				}, {
					text : '返回',
					handler : function() {
						window.location = "groupClientMaintenance1.jsp";
					}
				}]

			});

	// 定义自动当前页行号
	var rownum1 = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 复选框
	var windowsm1 = new Ext.grid.CheckboxSelectionModel();

	// 定义列模型
	var columns = new Ext.grid.ColumnModel([rownum1, windowsm1, {
		header : '客户编号', // 列标题
		dataIndex : 'custId', // 数据索引:和Store模型对应
		sortable : true,
		// hidden : true,
		width : 250
			// 是否可排序
		}, {
		header : '组织机构代码',
		sortable : true,
		hidden : true,
		dataIndex : 'custZhdm',
		width : 150
	}, {
		header : '客户名称', // 列标题
		dataIndex : 'custZhName', // 数据索引:和Store模型对应
		sortable : true,
		width : 250
			// 是否可排序
		}]);

	var record = Ext.data.Record.create([{
				name : 'custId',
				mapping : 'CUST_ID'
			}, {
				name : 'custZhName',
				mapping : 'CUST_ZH_NAME'
			}, {
				name : 'custZhdm',
				mapping : 'CUST_ZZDM'
			}]);

	/**
	 * 数据存储
	 */
	var windowstore = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/addgroupcustquery.json'/*
																 * , params :{
																 * 'groupNo':sujian,
																 * start:0,
																 * limit:parseInt(pagesize_combo.getValue()) }
																 */

						// success : function(response) {
						// Ext.Msg.alert('提示', response.responseText);
						// },
						// failure : function(response) {
						// Ext.Msg.alert('提示','加入失败' );
						// }

					}),
				reader : new Ext.data.JsonReader({
							successProperty : 'success',
							idProperty : 'ID',
							messageProperty : 'message',
							root : 'json.data',
							totalProperty : 'json.count'
						}, record)
			});

	// **********************
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10条/页'], [20, '20条/页'],
									[50, '50条/页'], [100, '100条/页'],
									[250, '250条/页'], [500, '500条/页']]
						}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				forceSelection : true,
				width : 85
			});

	// // 默认加载数据
	// windowstore.load({
	// params : {
	// start : 0,
	// limit : parseInt(pagesize_combo.getValue())
	// }
	// });

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()), windowstore
						.reload({
									params : {
										start : 0,
										limit : parseInt(pagesize_combo
												.getValue())
									}
								});
			});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : windowstore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', pagesize_combo]
			});

	// **********************

	// 表格实例
	var windowgrid2 = new Ext.grid.GridPanel({
		layout : 'fit',
		frame : true,
		autoScroll : true,
		region : 'center', // 返回给页面的div
		stripeRows : true, // 斑马线

		height : document.body.clientHeight + 230,
		width : document.body.clientWidth - 205,
		title : '待加入集团成员搜索-查询结果',
		bbar : bbar,// 分页工具栏
		// ddGroup:'gridDDGroup',
		// enableDragDrop : true,//定义数否可以取消选择
		store : windowstore, // 数据存储
		cm : columns, // 列模型
		sm : windowsm1,// 复选框
		tbar : [{
			text : '加入集团',
			iconCls : 'addIconCss',
			handler : function() {

				var selectLength = windowgrid2.getSelectionModel()
						.getSelections().length;
				if (selectLength < 1) {
					Ext.Msg.alert("系统提醒", "请选择一记录");
				} else {
					var custId = windowgrid2.getSelectionModel()
							.getSelections()[0].data.custId;
					memberRelation.getForm().findField("custId")
							.setValue(custId);
					memberRelation.getForm().findField("groupNo")
							.setValue(sujian);
					// Ext.MessageBox.alert('提示', "保存成功,请填写成员维护信息!");
				}
				// *********************************************
				var groupNo = Ext.getCmp('groupNo').getValue();
				var parentId = Ext.getCmp('parentId').getValue();
				var memberType = Ext.getCmp('memberType').getValue();
				var relationId = Ext.getCmp('relationId').getValue();
				var stockRate = Ext.getCmp('stockRate').getValue();
				var remark = Ext.getCmp('remark').getValue();
				debugger;
				if (groupNo == null || groupNo == "") {
					Ext.Msg.alert("提醒", "请先选择要加入的客户");
				} else {
					if (parentId == null || parentId == "") {
						Ext.Msg.alert("提醒", "请先选择该客户的上级企业");
					} else {
						if (!memberRelation.getForm().isValid()) {
							Ext.Msg.alert("提醒", "输入有误");
						} else {
							var checkedNodes = windowgrid2.getSelectionModel().selections.items;
							var json = {
								'custId' : []
							};
							var json1 = {
								'appStatus' : []
							};
							var json2 = {
								'groupNo' : []
							};
							var json3 = {
								'parentId' : []
							};
							var json4 = {
								'relationId' : []
							};
							var json5 = {
								'memberType' : []
							};
							var json6 = {
								'stockRate' : []
							};
							var json7 = {
								'remark' : []
							};

							for (var i = 0; i < checkedNodes.length; i++) {
								json.custId.push(checkedNodes[i].data.custId);
								// json1.appStatus.push(checkedNodes[i].data.appStatus);
								checkedNodes[i].data.groupNo = groupNo;
								json2.groupNo
										.push(checkedNodes[i].data.groupNo);
								checkedNodes[i].data.parentId = parentId;
								json3.parentId
										.push(checkedNodes[i].data.parentId);
								checkedNodes[i].data.relationId = Ext
										.getCmp('relationId').getValue();
								json4.relationId
										.push(checkedNodes[i].data.relationId);
								checkedNodes[i].data.memberType = Ext
										.getCmp('memberType').getValue();
								json5.memberType
										.push(checkedNodes[i].data.memberType);
								checkedNodes[i].data.stockRate = Ext
										.getCmp('stockRate').getValue();
								json6.stockRate
										.push(checkedNodes[i].data.stockRate);
								checkedNodes[i].data.remark = Ext
										.getCmp('remark').getValue();
								json7.remark.push(checkedNodes[i].data.remark);
							}

							Ext.Ajax.request({

										url : basepath
												+ '/GroupMemberAction.json?a=1',
										method : 'POST',
										// form : memberRelation.getForm().id,
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										success : checkResult1,
										failure : checkResult1,
										params : {
											'custId' : Ext.encode(json),
											'appStatus' : Ext.encode(json1),
											'groupNo' : Ext.encode(json2),
											'parentId' : Ext.encode(json3),
											'relationId' : Ext.encode(json4),
											'memberType' : Ext.encode(json5),
											'stockRate' : Ext.encode(json6),
											'remark' : Ext.encode(json7),
											'operate' : 'add'
										}
									});
						}
					}
				}
			}
		}, '-', {
			text : '成员申请与审批',
			iconCls : 'shenpiIconCss',
			handler : function() {
				submitAndAppInit();
			}
		}, '-', {
			text : '移出节点',// beforeRemoveId afterRemoveId
							// memberRelation.getForm().reset();
							// Ext.getCmp('parentId').getValue();
			iconCls : 'exportIconCss',
			handler : function() {
				if (Ext.getCmp('parentId').getValue() == null
						|| Ext.getCmp('parentId').getValue() == "") {
					Ext.Msg.alert("系统提醒", "请选择要移动的节点");
					return false;
				}
				var tempId = treeId + "";
				if (tempId.substring(5, 6) == "2" || treeName == "集团对外担保成员") {
					Ext.Msg.alert("系统提醒", "不能移动对外担保成员！");
					return false;
				}
				beforeRemoveId = Ext.getCmp('parentId').getValue();
				Ext.Msg.alert("系统提醒", "已经选定要移动的节点,请选择要移动到的目的节点！");
				memberRelation.getForm().reset();
			}
		}, '-', {
			text : '移入节点',// beforeRemoveId afterRemoveId
							// memberRelation.getForm().reset();
							// Ext.getCmp('parentId').getValue();
			iconCls : 'importIconCss',
			handler : function() {
				// if(__units!="00001"){
				// Ext.Msg.alert("系统提示","对不起，您不是总行人员，没有操作权限！");
				// return false;
				// }
				if (beforeRemoveId != null && beforeRemoveId != "") {
					if (Ext.getCmp('parentId').getValue() == null
							|| Ext.getCmp('parentId').getValue() == "") {
						Ext.Msg.alert("系统提醒", "请选择目的节点");
						return false;
					}
					afterRemoveId = Ext.getCmp('parentId').getValue();

				} else {
					Ext.Msg.alert("系统提醒", "请选择要移动的节点");
					return false;
				}
				var tempId = treeId + "";
				if (tempId.substring(5, 6) == "2" || treeName == "集团对外担保成员") {
					Ext.Msg.alert("系统提醒", "不能移动到对外担保成员下！");
					return false;
				}
				if (!(beforeRemoveId == null || beforeRemoveId == ""
						|| afterRemoveId == null || afterRemoveId == "")) {
					Ext.Msg.confirm('提示', '确定要移动吗?', function(buttonId) {
								if (buttonId.toLowerCase() == "no") {
									return;
								}
								Ext.Ajax.request({

											url : basepath
													+ '/GroupMemberAction.json?a=2',
											method : 'POST',
											waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
											success : checkResult,
											failure : checkResult,
											params : {
												'beforeRemoveId' : beforeRemoveId,
												'afterRemoveId' : afterRemoveId,
												'operate' : 'remove'
											}
										});

							})
					memberRelation.getForm().reset();
				}
			}
		}],
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}

	});

	var tempTreeNode;

	// 定义展示员工基本信息窗口
	var submitAndAppWindow = new Ext.Window({
				title : '集团申请与审批-客户列表',
				plain : true,
				layout : 'border',
				width : 800,
				height : 400,
				resizable : true,
				draggable : true,
				closable : true,
				closeAction : 'hide',
				modal : true, // 模态窗口
				loadMask : true,
				maximizable : true,
				collapsible : true,
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				items : [listPanel],
				buttons : [{
							text : '关闭',
							handler : function() {
								submitAndAppWindow.hide();
							}
						}]
			});

	function submitAndAppInit() {
		store.reload({
					params : {
						start : 0,
						limit : bbar.pageSize
					}
				});
		submitAndAppWindow.show();
	}

	var blocMemberTree = new Ext.tree.TreePanel({
				id : 'blocMemberTree',
				autoScroll : true,
				enableDD : true,
				height : parent.document.body.clientHeight - 60,
				split : true,
				width : 200,
				root : new Ext.tree.AsyncTreeNode({
							id : treeRootID,
							text : groupName,
							autoScroll : true,
							expanded : true,
							leaf : false,
							loader : new Ext.tree.TreeLoader({
										url : basepath
												+ '/client-group-tree.json',
										baseParams : {
											'condition' : '{"group_no":"'
													+ groupNo + '"}'
										},
										requestMethod : 'GET'
									})
						}),

				// 添加监听，获得上级企业ID和Name
				listeners : {
					'click' : function(node) {
						var node_id = node.id;
						treeId = node.id;
						treeName = node.text;
						// alert(node_id);
						// // var lastIndex = checkTextString.lastIndexOf(";");
						// alert(node.text);
						if (node.text != "集团对外担保成员") {
							if (node_id.length >= 5) {

								if (node_id.substring(4, 5) == "2") {
									Ext.Msg.alert("系统提醒", "不能以该成员为节点！");
									memberRelation.getForm().reset();
									return false;
								}
								var node_name = node.text;
								memberRelation.getForm().findField("parentId")
										.setValue(node_id.substring(0, 4));
								memberRelation.getForm()
										.findField("parentName")
										.setValue(node_name);
								memberRelation.getForm()
										.findField("memberType").setValue("1");
								Ext.getCmp('stockRate').setDisabled(false);
								Ext.getCmp('relationId').setDisabled(false);
								Ext.getCmp('parentName').setDisabled(false);
							} else {
								Ext.Msg.alert("系统提示", "不能选择根节点！");
								memberRelation.getForm().reset();
								return false;
							}
						} else {
							memberRelation.getForm().findField("parentId")
									.setValue(node_id.substring(0, 4));
							memberRelation.getForm().findField("parentName")
									.setValue("集团对外担保成员");
							memberRelation.getForm().findField("memberType")
									.setValue("2");
							Ext.getCmp('stockRate').setDisabled(true);
							Ext.getCmp('stockRate').reset();
							Ext.getCmp('relationId').setDisabled(true);
							Ext.getCmp('parentName').setDisabled(true);
							Ext.getCmp('relationId').reset();
						}
					}
				},
				animate : false,
				useArrows : false,
				border : false
			});

	var test = Ext.getCmp('memberType');
	test.addListener("select", function() {
				if (test.getValue() == '2') {
					var ss = treeRootID;
					var sss = parseInt(ss) + 2;
					Ext.getCmp('parentName').setValue("集团对外担保成员");
					Ext.getCmp('parentId').setValue(sss);
					Ext.getCmp('stockRate').setDisabled(true);
					Ext.getCmp('relationId').setDisabled(true);
					Ext.getCmp('parentName').setDisabled(true);
					Ext.getCmp('relationId').reset();
					Ext.getCmp('stockRate').reset();
					windowstore.removeAll();
				}
				// alert(test.getValue());
				// alert(Ext.getCmp('parentName'));
				if (test.getValue() == '1') {
					if (Ext.getCmp('parentName').getValue() == '集团对外担保成员') {
						Ext.getCmp('parentName').setValue("");
						Ext.getCmp('parentId').setValue("");
					}
					Ext.getCmp('stockRate').setDisabled(false);
					Ext.getCmp('relationId').setDisabled(false);
					Ext.getCmp('parentName').setDisabled(false);
					windowstore.removeAll();
				}
			});

	var contextMenu = new Ext.menu.Menu({
				id : 'deptTreeContextMenu'
			});

	blocMemberTree.on('contextmenu', function(node, e) {
				e.preventDefault();
				contextMenu.showAt(e.getXY());
			});

	var memWin = new Ext.Window({
				title : '<span style="font-weight:normal">集团成员维护</span>',
				layout : 'fit',
				draggable : true,// 是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'close',
				maximized : true,// 默认最大化
				titleCollapse : true,
				buttonAlign : 'center',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [{
					layout : 'border',
					items : [{
								region : 'west',
								width : 200,
								split : true,// 是否可拖动
								// items:windowgridBloc,
								collapsible : true,// 是否有伸缩按钮
								items : blocMemberTree,
								title : '集团树-信息项'
							}, {
								region : 'center',
								buttonAlign : 'center',
								items : [memberRelation,
										windowFormSearchMember, windowgrid2
								// listPanel
								]
							}]
				}]
			});
	memWin.show();
	memWin.on('hide', function() {
				window.location.href = 'groupClientMaintenance1.jsp';
			});

	// var memberManage = new Ext.Viewport(
	// {
	// layout : 'fit',
	// items : [{
	// layout : 'border',
	// // draggable : true,//是否可以拖动
	// closable : true,// 是否可关闭
	// modal : true,
	// closeAction : 'hide',
	// title : '<span style="font-weight:normal">集团成员维护</span>',
	// // collapsible : true,// 是否可收缩
	// titleCollapse : true,
	// buttonAlign : 'center',
	// animCollapse : true,
	// autoScroll:true,
	// animateTarget : Ext.getBody(),
	// constrain : true,
	// items : [
	// {
	// region:'center',
	// items:[
	//												
	// memberRelation,
	// windowFormSearchMember,
	// windowgrid2
	// // listPanel
	// ]
	// },
	// {
	// region:'west',
	// width:200,
	// layout:'fit',
	// split: true,//是否可拖动
	// // items:windowgridBloc,
	// collapsible: true,//是否有伸缩按钮
	// items:blocMemberTree,
	// title:'集团树-信息项'
	// }
	// ]
	// }],
	// buttons : [
	// {
	// text : '返 回',
	//						
	// handler : function() {
	// }
	// }
	// ]
	// });

	function checkResult1(response) {
		var resultArray = Ext.util.JSON.decode(response.status);
		var resultError = response.responseText;
		if ((resultArray == 200 || resultArray == 201) && resultError == '') {
			Ext.Msg.alert('提示', '操作成功');
			blocMemberTree.root.reload();
			// Ext.getCmp("CUST_ZZDMM").reset();
			memberRelation.getForm().reset();
			windowstore.reload({
						params : {
							start : 0,
							limit : parseInt(pagesize_combo.getValue())
						}
					});
		} else {
			if (resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			} else {
				Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
				windowstore.reload({
							params : {
								start : 0,
								limit : bbar.pageSize
							}
						});
			}
		}
	}

	function checkResult(response) {
		var resultArray = Ext.util.JSON.decode(response.status);
		var resultError = response.responseText;
		if ((resultArray == 200 || resultArray == 201) && resultError == '') {
			Ext.Msg.alert('提示', '操作成功');
			blocMemberTree.root.reload();
			memberRelation.getForm().reset();
			windowstore.reload({
						params : {
							start : 0,
							limit : bbar.pageSize
						}
					});
		} else {
			Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
			windowstore.reload({
						params : {
							start : 0,
							limit : bbar.pageSize
						}
					});
		}
	}
});