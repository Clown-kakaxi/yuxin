/**
 * 客户管理->客户群组管理->批量生成商机JS，wzy，2013-04-07
 */
Ext.QuickTips.init();

// 待选择成员列表中，已选中的“客户ID”的JSON对象
var custId_json = {
	'cust_id' : []
};
// 待选择成员列表中，已选中的“客户名称”的JSON对象
var custName_json = {
	'cust_zh_name' : []
};
// 待选择成员列表中，已选中的“证件类型”的JSON对象
var certType_json = {
	'certTypeOra' : []
};
// 待选择成员列表中，已选中的“证件号码”的JSON对象
var certNum_json = {
	'certNum' : []
};

// 已选择成员列表中，已选中的“客户ID”的JSON对象
var custId_json_Selected = {
	'cust_id' : []
};
// 已选择成员列表中，已选中的“客户名称”的JSON对象
var custName_json_Selected = {
	'cust_zh_name' : []
};
// 已选择成员列表中，已选中的“证件类型”的JSON对象
var certType_json_Selected = {
	'certTypeOra' : []
};
// 已选择成员列表中，已选中的“证件号码”的JSON对象
var certNum_json_Selected = {
	'certNum' : []
};

// 已选择成员(已经从左边选到右边的)的“客户ID”的JSON对象
var custId_json_ResultSelected = {
	'cust_id' : []
};
// 已选择成员(已经从左边选到右边的)的“客户名称”的JSON对象
var custName_json_ResultSelected = {
	'cust_zh_name' : []
};

// “商机类型”下拉框定义
var chanceStatStore = new Ext.data.Store({
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=BUSI_CHANCE_TYPE'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});

// 客户群组管理列表对象
var cusGroupGrid = null;

// 客户选择组件
var custSelectPartAdd = new Com.yucheng.bcrm.common.CustomerQueryField({
			fieldLabel : '*客户名称',
			name : 'custName',
			custtype : '',// 客户类型:1:对私,2:对公,不设默认全部
			custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
			singleSelected : true,// 单选复选标志
			editable : false,
			allowBlank : false,
			blankText : '此项为必填项，请检查！',
			anchor : '95%',
			hiddenName : 'custId',
			onTrigger2Click : function() {
				// 先清空列表中数据
				custGroupMemberStore.removeAll();
				// 打开列表
				openQueryCustGroupMember();
				// 打开页面就进行数据查询
				custGroupMemberStore.load({
							params : {
								start : 0,
								limit : parseInt(grid01_combo.getValue()),
								"custGroupID" : getCustGroupID(),
								"hadSelected" : getHadSelectMember()
							}
						});
			}
		});
// ============================定义客户群组成员查询界面==================开始============
// 定义自动当前页行号
var planRownum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

var sm_p = new Ext.grid.CheckboxSelectionModel();
// 注册列表复选框的“选中事件
sm_p.on('rowselect', function(sm_, rowIndex, record) {
			if (!isHadCustId(record.data.custId)) {
				custId_json.cust_id.push(record.data.custId);// 放入“客户ID”数据
				custName_json.cust_zh_name.push(record.data.custZhName);// 放入“客户名称”数据
				certType_json.certTypeOra.push(record.data.certTypeOra);// 放入“证件类型”数据
				certNum_json.certNum.push(record.data.certNum);// 放入“证件号码”数据
			} else {
				return false;
			}
		});
// 注册列表复选框的“取消选中”事件
sm_p.on('rowdeselect', function(sm_, rowIndex, record) {
			custId_json.cust_id.remove(record.data.custId);// 删除“客户ID”数据
			custName_json.cust_zh_name.remove(record.data.custZhName);// 删除“客户名称”数据
			certType_json.certTypeOra.remove(record.data.certTypeOra);// 删除“证件类型”数据
			certNum_json.certNum.remove(record.data.certNum);// 删除“证件号码”数据
		});

// 列表表头
var planProdColumns = new Ext.grid.ColumnModel([planRownum, sm_p, {
			header : '客户号',
			align : 'left',
			dataIndex : 'custId',
			width : 150,
			sortable : true
		}, {
			header : '客户名称',
			align : 'left',
			dataIndex : 'custZhName',
			width : 200,
			sortable : true
		}, {
			header : '证件类型',
			align : 'left',
			dataIndex : 'certTypeOra',
			width : 120,
			sortable : true
		}, {
			header : '证件号码',
			align : 'left',
			dataIndex : 'certNum',
			width : 150,
			sortable : true
		}, {
			header : '加入群组日期',
			align : 'left',
			dataIndex : 'crateDate',
			width : 130,
			sortable : true
		}]);

// 列表字段映射
var planProdRecord = Ext.data.Record.create([{
			name : 'custId',
			mapping : 'CUST_ID'
		}, {
			name : 'custZhName',
			mapping : 'CUST_ZH_NAME'
		}, {
			name : 'certTypeOra',
			mapping : 'CERT_TYPE_ORA'
		}, {
			name : 'certNum',
			mapping : 'CERT_NUM'
		}, {
			name : 'crateDate',
			mapping : 'CRATE_DATE'
		}]);

// 列表存储
var custGroupMemberStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/queryCustGroupMemberAction.json',
						failure : function(response) {
							var resultArray = Ext.util.JSON
									.decode(response.status);
							if (resultArray == 403) {
								Ext.Msg.alert('提示', response.responseText);
							}
						}
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'json.count',
						root : 'json.data'
					}, planProdRecord)
		});

// 数据查询前，传入查询条件：群组ID
custGroupMemberStore.on('beforeload', function() {
			this.baseParams = {
				"custGroupID" : getCustGroupID()
			};
		});

// 获取客户群组ID
function getCustGroupID() {
	var custGroupID = "";
	var _record = cusGroupGrid.getSelectionModel().getSelected();
	var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
	if (_record && checkedNodes.length == 1) {
		custGroupID = checkedNodes[0].data.id;
	}
	return custGroupID;
}

// 查询条件panel
var searchPanel = new Ext.form.FormPanel({
			title : "可选择客户群成员",
			labelWidth : 60,
			hight : 50,
			labelAlign : 'right',
			frame : true,
			region : 'north',
			autoScroll : true,
			layout : 'column',
			items : [{
						columnWidth : .33,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '客户编号',
									name : 'CUST_ID',
									anchor : '99%'
								}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '客户名称',
									name : 'CUST_ZH_NAME',
									anchor : '99%'
								}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [new Ext.form.ComboBox({
									hiddenName : 'CUST_TYP',
									fieldLabel : '客户类型',
									labelStyle : 'text-align:right;',
									triggerAction : 'all',
									store : customerTypeStore,
									displayField : 'value',
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText : '请选择',
									resizable : true,
									anchor : '99%'
								})]
					}],
			buttonAlign : 'center',
			buttons : [{
						text : '查询',
						handler : function() {
							if (!searchPanel.getForm().isValid()) {
								Ext.Msg.alert("提示", "请填写必填项！");
								return false;
							}
							var conditionStr = searchPanel.getForm()
									.getValues(false);
							custGroupMemberStore.on('beforeLoad', function() {
										this.baseParams = {
											"condition" : Ext
													.encode(conditionStr),
											"custGroupID" : getCustGroupID(),
											"hadSelected" : getHadSelectMember()
										};
									});
							custGroupMemberStore.load({
										params : {
											start : 0,
											limit : parseInt(grid01_combo
													.getValue()),
											"custGroupID" : getCustGroupID(),
											"hadSelected" : getHadSelectMember()
										}
									});
						}
					}, {
						text : '重置',
						handler : function() {
							searchPanel.getForm().reset();
						}
					}]
		});

// 每页显示条数下拉选择框
var grid01_combo = new Ext.form.ComboBox({
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
			editable : false,
			width : 85
		});

var grid01_number = parseInt(grid01_combo.getValue());

grid01_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
			grid01_Bar.pageSize = parseInt(grid01_combo.getValue());
			custGroupMemberStore.reload({
						params : {
							start : 0,
							limit : parseInt(grid01_combo.getValue())
						}
					});
		});

// 分页工具栏
var grid01_Bar = new Ext.PagingToolbar({
			pageSize : grid01_number,
			store : custGroupMemberStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', grid01_combo]
		});

// 查询结果列表布局
var mktAssuListPanel = new Ext.grid.GridPanel({
			autoScroll : true,
			region : 'center', // 返回给页面的div
			height : 400,
			store : custGroupMemberStore,
			frame : true,
			sm : sm_p,
			cm : planProdColumns,
			bbar : grid01_Bar,
			stripeRows : true,
			buttonAlign : "center",
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			tbar : [{
						'text' : '加入成员',
						iconCls : 'addIconCss',
						handler : function() {
							addMember();
						}
					}],
			buttons : [{
				text : '确定',
				handler : function() {
					var custIdS = custId_json_ResultSelected.cust_id + "";
					var custNameS = custName_json_ResultSelected.cust_zh_name
							+ "";
					addChanceForm02.form.findField('custName')
							.setValue(custNameS);// 设置客户名称
					addChanceForm02.form.findField('custId').setValue(custIdS);// 设置客户ID
					queryCustGroupMemberWindow.hide();
				}
			}, {
				text : '取消',
				handler : function() {
					addChanceForm02.form.findField('custName').setValue("");// 清空客户名称
					addChanceForm02.form.findField('custId').setValue("");// 清空客户ID
					queryCustGroupMemberWindow.hide();
				}
			}]
		});

custGroupMemberStore.on('load', function(store, records, options) {
			sm_p.clearSelections();
			if (records) {
				for (var i = 0; i < records.length; i++) {
					if (isHadCustId(records[i].get('custId'))) {
						sm_p.selectRow(i, true);
					}
				}
			}
		});

function isHadCustId(inCustId) {
	var result = false;
	var custId = custId_json.cust_id;
	if (custId != null && custId != "") {
		custId = custId + "";
		var arr = custId.split(",");
		if (arr != null && arr.length > 0) {
			for (var i = 0; i < arr.length; i++) {
				if (arr[i] == inCustId) {
					result = true;
					break;
				}
			}
		}
	}
	return result;
}

function addMember() {
	// 1、如果没有选择成员，给出提示
	if (custId_json.cust_id == null || custId_json.cust_id == ""
			|| custName_json.cust_zh_name == null
			|| custName_json.cust_zh_name == "") {
		Ext.Msg.alert('提示', '请先选择要加入的成员！');
		return false;
	}
	// 2、处理选择的结果
	dealResultSelectAdd();
	// 3、查询左边列表剩下的数据
	custGroupMemberStore.load({
				params : {
					start : 0,
					limit : parseInt(grid01_combo.getValue()),
					// "condition" : Ext.encode(conditionStr),
					"custGroupID" : getCustGroupID(),
					"hadSelected" : getHadSelectMember()
				}
			});
	// 4、右边表格加载数据
	var memberData = makeRightGridDataJson();// 获取右边表格数据的JSON对象
	selectedMemberStore.loadData(memberData);// 执行右边表格数据加载
	// 5、右边表格翻页工具栏不可用
	selectedMemberBar.setDisabled(true);
}

// 用户选择了成员后，将成员信息数据组合成右边列表需要的数据格式
function makeRightGridDataJson() {
	var memberData = {
		totalcount : 0,
		rows : []
	};
	var select_custId = custId_json.cust_id;
	var select_custName = custName_json.cust_zh_name;
	var select_certTypeOra = certType_json.certTypeOra;
	var select_certNum = certNum_json.certNum;
	if (select_custId && select_custId.length > 0) {
		memberData.totalcount = select_custId.length;
		for (var i = 0; i < select_custId.length; i++) {
			memberData.rows.push(getOneRec((i + 1), select_custId[i],
					select_custName[i], select_certTypeOra[i],
					select_certNum[i]));

		}
	}
	return memberData;
}

// 构造右边列表数据的一行记录的json对象
function getOneRec(seq, cust_id, cust_zh_name, cert_type_ora, cert_num) {
	var tempRec = {
		"rownum" : seq,
		"CUST_ID" : cust_id,
		"CUST_ZH_NAME" : cust_zh_name,
		"CERT_TYPE_ORA" : cert_type_ora,
		"CERT_NUM" : cert_num
	};
	return tempRec;
}

function dealResultSelectAdd() {
	var select_custId = custId_json.cust_id;
	var select_custName = custName_json.cust_zh_name;
	if (select_custId && select_custId.length > 0) {
		for (var i = 0; i < select_custId.length; i++) {
			if (!isHadResultSelected(select_custId[i])) {
				custId_json_ResultSelected.cust_id.push(select_custId[i]);
				custName_json_ResultSelected.cust_zh_name
						.push(select_custName[i]);
			}
		}
	}
}

function isHadResultSelected(inCustId) {
	var result = false;
	var hadCustId = custId_json_ResultSelected.cust_id;
	if (hadCustId && hadCustId.length > 0) {
		for (var i = 0; i < hadCustId.length; i++) {
			if (hadCustId[i] == inCustId) {
				result = true;
				break;
			}
		}
	}
	return result;
}

function dealResultSelectDel() {
	var select_custId = custId_json_Selected.cust_id;
	var select_custName = custName_json_Selected.cust_zh_name;
	var select_certType = certType_json_Selected.certTypeOra;
	var select_certNum = certNum_json_Selected.certNum;
	if (select_custId && select_custId.length > 0) {
		for (var i = 0; i < select_custId.length; i++) {
			custId_json_ResultSelected.cust_id.remove(select_custId[i]);
			custName_json_ResultSelected.cust_zh_name
					.remove(select_custName[i]);
		}
	}
	if (select_custId && select_custId.length > 0) {
		// 注意：此处循环变量没有自增
		for (var i = 0; i < select_custId.length;) {
			select_custId.remove(select_custId[i]);// 删除“客户ID”数据
			select_custName.remove(select_custName[i]);// 删除“客户名称”数据
			select_certType.remove(select_certType[i]);// 删除“证件类型”数据
			select_certNum.remove(select_certNum[i]);// 删除“证件号码”数据
		}
	}
}

function getHadSelectMember() {
	return JSON.stringify(custId_json_ResultSelected.cust_id);
}

// 客户群成员分页，列模型等
var sm_selected = new Ext.grid.CheckboxSelectionModel();
// 注册列表复选框的“选中”事件
sm_selected.on('rowselect', function(sm_, rowIndex, record) {
			if (!isHadCustId_Selected(record.data.CUST_ID)) {
				custId_json_Selected.cust_id.push(record.data.CUST_ID);// 放入“客户ID”数据
				custName_json_Selected.cust_zh_name
						.push(record.data.CUST_ZH_NAME);// 放入“客户名称”数据
				certType_json_Selected.certTypeOra
						.push(record.data.CERT_TYPE_ORA);// 放入“证件类型”数据
				certNum_json_Selected.certNum.push(record.data.CERT_NUM);// 放入“证件号码”数据
			} else {
				return false;
			}
		});
// 注册列表复选框的“取消选中”事件
sm_selected.on('rowdeselect', function(sm_, rowIndex, record) {
			custId_json_Selected.cust_id.remove(record.data.CUST_ID);// 删除“客户ID”数据
			custName_json_Selected.cust_zh_name
					.remove(record.data.CUST_ZH_NAME);// 删除“客户名称”数据
			certType_json_Selected.certTypeOra
					.remove(record.data.CERT_TYPE_ORA);// 删除“证件类型”数据
			certNum_json_Selected.certNum.remove(record.data.CERT_NUM);// 删除“证件号码”数据
		});

var selectedMemberNum = new Ext.grid.RowNumberer({
			header : 'No.',
			width : 28
		});

// 定义列模型
var selectedMemberCm = new Ext.grid.ColumnModel([selectedMemberNum,
		sm_selected, {
			header : '客户号',
			dataIndex : 'CUST_ID',
			sortable : true,
			width : 150
		}, {
			header : '客户名称',
			dataIndex : 'CUST_ZH_NAME',
			sortable : true,
			width : 200
		}, {
			header : '证件类型',
			align : 'left',
			dataIndex : 'CERT_TYPE_ORA',
			width : 120,
			sortable : true
		}, {
			header : '证件号码',
			align : 'left',
			dataIndex : 'CERT_NUM',
			width : 150,
			sortable : true
		}]);

// 数据存储
var selectedMemberStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/groupmemberedit.json'
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'totalcount',// 记录总数
						root : 'rows'// Json中的列表数据根节点
					}, ['CUST_ID', 'CUST_ZH_NAME', 'CERT_TYPE_ORA', 'CERT_NUM'])
		});

selectedMemberStore.on('load', function(store, records, options) {
			sm_selected.clearSelections();
			if (records) {
				for (var i = 0; i < records.length; i++) {
					if (isHadCustId_Selected(records[i].get('CUST_ID'))) {
						sm_selected.selectRow(i, true);
					}
				}
			}
		});

function isHadCustId_Selected(inCustId) {
	var result = false;
	var custId = custId_json_Selected.cust_id;
	if (custId != null && custId != "") {
		custId = custId + "";
		var arr = custId.split(",");
		if (arr != null && arr.length > 0) {
			for (var i = 0; i < arr.length; i++) {
				if (arr[i] == inCustId) {
					result = true;
					break;
				}
			}
		}
	}
	return result;
}

// 每页显示条数下拉选择框
var selectedMember_combo = new Ext.form.ComboBox({
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
			value : '',
			editable : false,
			width : 85
		});

var selectedMemberNum = parseInt(selectedMember_combo.getValue());

selectedMember_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
			selectedMemberBar.pageSize = parseInt(selectedMember_combo
					.getValue());
			selectedMemberStore.reload({
						params : {
							start : 0,
							limit : parseInt(selectedMember_combo.getValue())
						}
					});
		});

// 右边列表分页工具栏
var selectedMemberBar = new Ext.PagingToolbar({
	// pageSize : selectedMemberNum,
	store : selectedMemberStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录"// ,
		// items : ['-', '&nbsp;&nbsp;', selectedMember_combo]
	});

// 右边成员列表gridpanel
var selectedMemberGrid = new Ext.grid.GridPanel({
			height : 400,
			title : '已选择客户群成员',
			frame : true,
			autoScroll : true,
			store : selectedMemberStore, // 数据存储
			stripeRows : true, // 斑马线
			cm : selectedMemberCm, // 列模型
			sm : sm_selected, // 复选框
			bbar : selectedMemberBar,
			// bbar : '',
			buttonAlign : "center",
			viewConfig : {},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			tbar : [{
						'text' : '移除成员',
						iconCls : 'deleteIconCss',
						handler : function() {
							deleteSelectedMember();
						}
					}]
		});

// 移除成员
function deleteSelectedMember() {
	// 1、更新用户已选择数据JSON对象
	var select_custId = custId_json.cust_id;
	var select_custId_Selected = custId_json_Selected.cust_id;
	var select_custName_Selected = custName_json_Selected.cust_zh_name;
	var select_certTypeOra_Selected = certType_json_Selected.certTypeOra;
	var select_certNum_Selected = certNum_json_Selected.certNum;
	if (select_custId && select_custId.length > 0) {
		if (select_custId_Selected && select_custId_Selected.length > 0) {
			for (var i = 0; i < select_custId_Selected.length; i++) {
				custId_json.cust_id.remove(select_custId_Selected[i]);
				custName_json.cust_zh_name.remove(select_custName_Selected[i]);
				certType_json.certTypeOra
						.remove(select_certTypeOra_Selected[i]);
				certNum_json.certNum.remove(select_certNum_Selected[i]);
			}
		}
	}
	// 2、处理选择的结果
	dealResultSelectDel();
	// 3、更新左边列表数据
	custGroupMemberStore.load({
				params : {
					start : 0,
					limit : parseInt(grid01_combo.getValue()),
					"custGroupID" : getCustGroupID(),
					"hadSelected" : getHadSelectMember()
				}
			});
	// 4、更新右边列表数据
	var memberData = makeRightGridDataJson();// 获取右边表格数据的JSON对象
	selectedMemberStore.loadData(memberData);// 执行右边表格数据加载
	// 5、右边表格翻页工具栏不可用
	selectedMemberBar.setDisabled(true);
}

var viewPanel = new Ext.Panel({
			autoScroll : true,
			items : [{
						layout : 'column',
						border : false,
						items : [{
									columnWidth : .53,
									layout : 'form',
									border : false,
									items : [{
												region : 'north',
												height : 100,
												layout : 'fit',
												items : [searchPanel]
											}, {
												region : 'center',
												layout : 'fit',
												height : 320,
												items : [mktAssuListPanel]
											}]
								}, {
									columnWidth : .47,
									layout : 'form',
									border : false,
									items : [{
												region : 'center',
												layout : 'fit',
												height : 420,
												items : [selectedMemberGrid]
											}]
								}]
					}]
		});

// 定义查询客户群成员信息窗口
var queryCustGroupMemberWindow = new Ext.Window({
			title : '选择客户群成员',
			width : 1100,
			height : 450,
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
			items : [viewPanel]
		});

// 打开查询客户群成员信息窗体
function openQueryCustGroupMember() {
	queryCustGroupMemberWindow.show();
}

// 新增商机窗口From表单
var addChanceForm02 = new Ext.FormPanel({
	height : 380,
	frame : true,
	autoScroll : true,
	labelAlign : 'right',
	buttonAlign : "center",
	items : [{
		layout : 'column',
		items : [{
					columnWidth : .5,
					layout : 'form',
					items : [
							{
								xtype : 'textfield',
								fieldLabel : '*商机名称',
								allowBlank : false,
								blankText : '此项为必填项，请检查！',
								name : 'opporName',
								anchor : '90%'
							},
							new Com.yucheng.crm.common.ProductManage({
										xtype : 'productChoose',
										fieldLabel : '*商机产品',
										labelStyle : 'text-align:right;',
										name : 'prodName',
										hiddenName : 'prodId',
										singleSelect : false,
										allowBlank : false,
										blankText : '此项为必填项，请检查！',
										anchor : '90%'
									}),
							{
								xtype : 'datefield',
								fieldLabel : '*商机开始日期',
								format : 'Y-m-d',
								editable : true,
								name : 'opporStartDate',
								allowBlank : false,
								blankText : '此项为必填项，请检查！',
								anchor : '90%'
							},
							new Com.yucheng.bcrm.common.MktActivityCommonQuery(
									{
										xtype : 'activityQuery',
										fieldLabel : '营销活动名称',
										labelStyle : 'text-align:right;',
										name : 'mktActivName',
										hiddenName : 'mktActivId',
										singleSelect : false,
										anchor : '90%'
									}), {
								xtype : 'numberfield',
								fieldLabel : '预计金额(元)',
								name : 'planAmount',
								labelStyle : 'text-align:right;',
								value : '0',
								anchor : '90%'
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [
							new Ext.form.ComboBox({
										hiddenName : 'opporType',
										fieldLabel : '商机类型',
										labelStyle : 'text-align:right;',
										triggerAction : 'all',
										store : chanceStatStore,
										displayField : 'value',
										valueField : 'key',
										mode : 'local',
										forceSelection : true,
										emptyText : '请选择 ',
										resizable : true,
										anchor : '90%'
									}),
							{
								xtype : 'datefield',
								fieldLabel : '*商机有效期',
								format : 'Y-m-d',
								editable : true,
								name : 'opporDueDate',
								allowBlank : false,
								blankText : '此项为必填项，请检查！',
								anchor : '90%'
							},
							{
								xtype : 'datefield',
								fieldLabel : '*商机完成日期',
								format : 'Y-m-d',
								editable : true,
								name : 'opporEndDate',
								allowBlank : false,
								blankText : '此项为必填项，请检查！',
								anchor : '90%'
							},
							new Com.yucheng.bcrm.common.MktTaskTargetCommonQuery(
									{
										xtype : 'taskTargetQuery',
										fieldLabel : '营销任务指标',
										labelStyle : 'text-align:right;',
										name : 'mktTargetName',
										hiddenName : 'mktTargetId',
										singleSelect : true,
										anchor : '90%'
									}), {
								xtype : 'numberfield',
								fieldLabel : '费用预算(元)',
								name : 'planCost',
								value : '0',
								labelStyle : 'text-align:right;',
								anchor : '90%'
							}]
				}]
	}, {
		layout : 'form',
		items : [custSelectPartAdd, {
					xtype : 'textarea',
					fieldLabel : '商机内容',
					name : 'opporContent',
					anchor : '95%'
				}, {
					xtype : 'textarea',
					fieldLabel : '商机备注',
					name : 'memo',
					anchor : '95%'
				}]
	}]
});

// 定义新增窗口
var addChanceWindow02 = new Ext.Window({
			title : '批量创建商机',
			plain : true,
			layout : 'fit',
			width : 800,
			height : 380,
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
			buttonAlign : "center",
			items : [addChanceForm02],
			listeners : {
				"hide" : function() {
					addChanceForm02.getForm().reset();
				},
				"show" : function() {// 窗体显示时间，进行一些数据设置初始化操作
				}
			},
			buttons : [{
						text : '保存',
						handler : saveAddBusiOppor
					}, {
						text : '提交',
						handler : submitAddBusiOppor
					}, {
						text : '关闭',
						handler : function() {
							addChanceWindow02.hide();
						}
					}]
		});

// 保存商机
// 对商机数据做临时存储，只控制必须输入“商机名称”，在提交时，判断必填项是否完全填写
function saveAddBusiOppor() {
	var opporName = addChanceForm02.form.findField('opporName').getValue();
	if (opporName == null || opporName == "") {
		Ext.Msg.alert('提示', '商机名称不能为空！');
		return false;
	}
	var custId = addChanceForm02.form.findField('custId').getValue();
	if (custId == null || custId == "") {
		Ext.Msg.alert('提示', '客户名称不能为空！');
		return false;
	}
	if (!dateCheck()) {
		return false;
	}
	var saveUrl = basepath + '/mktBusiOpporOperationAction!'
			+ 'pitchCreateBusiOpporFromCustGroup.json';
	Ext.Ajax.request({
				url : saveUrl,
				method : 'POST',
				form : addChanceForm02.getForm().id,
				waitMsg : '正在保存数据,请等待...',
				success : function(response) {
					Ext.Msg.alert('提示', '商机保存成功！');
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '商机保存失败！');
				}
			});
	addChanceWindow02.hide();
}

// 提交商机
function submitAddBusiOppor() {
	if (!addChanceForm02.getForm().isValid()) {
		Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
		return false;
	}
	if (!dateCheck()) {
		return false;
	}
	var saveUrl = basepath + '/mktBusiOpporOperationAction!'
			+ 'pitchCreateSubmitBusiOpporFromCustGroup.json';
	Ext.Ajax.request({
				url : saveUrl,
				method : 'POST',
				form : addChanceForm02.getForm().id,
				waitMsg : '正在保存数据,请等待...',
				success : function(response) {
					Ext.Msg.alert('提示', '商机提交成功！');
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '商机提交失败！');
				}
			});
	addChanceWindow02.hide();
}

// 日期判断
function dateCheck() {
	var opporStartDate = addChanceForm02.form.findField('opporStartDate')
			.getValue();
	var opporEndDate = addChanceForm02.form.findField('opporEndDate')
			.getValue();
	var opporDueDate = addChanceForm02.form.findField('opporDueDate')
			.getValue();
	// 1、商机“开始日期”不能晚于“完成日期”
	if (opporStartDate >= opporEndDate) {
		Ext.Msg.alert('提示', '商机“开始日期”不能晚于或等于“完成日期”！');
		return false;
	}
	// 2、商机“开始日期”不能晚于“商机有效期”
	if (opporStartDate >= opporDueDate) {
		Ext.Msg.alert('提示', '商机“开始日期”不能晚于或等于“商机有效期”！');
		return false;
	}
	// 3、商机“完成日期”不能晚于“邮寄有效期”
	if (opporEndDate > opporDueDate) {
		Ext.Msg.alert('提示', '商机“完成日期”不能晚于或等于“商机有效期”！');
		return false;
	}
	return true;
}

// 打开 新增商机 窗口
function addMyBusOpportInit01(cusGroupGridIn) {
	cusGroupGrid = cusGroupGridIn;
	var _record = cusGroupGrid.getSelectionModel().getSelected();
	var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
	if (!_record) {
		Ext.MessageBox.alert('提示', '请先选择一个客户群！');
		return false;
	} else if (checkedNodes.length > 1) {
		Ext.MessageBox.alert('提示', '只能选择一个客户群！');
		return false;
	} else {
		addChanceWindow02.show();
	}
}

// 清空新增商机Form表单
function resetAddForm() {
	addChanceForm02.getForm().reset();
}