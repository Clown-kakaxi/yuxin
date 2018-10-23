/**
 * 客户管理->客户群组管理->群成员视图->成员关系图维护 入口JS文件 wzy，2013-04-09
 */

// 客户查询面板
var custSearchPanel_edit = new Ext.form.FormPanel({
			title : "群成员查询",
			labelWidth : 90, // 标签宽度
			frame : true, // 是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
			split : true,
			height : 120,
			items : [{
						layout : 'column',
						items : [{
									columnWidth : 1,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												Width : '100',
												name : 'CUST_ID',
												fieldLabel : '客户号',
												labelStyle : 'text-align:right;',
												anchor : '90%'
											}, {
												xtype : 'textfield',
												Width : '100',
												name : 'CUST_ZH_NAME',
												fieldLabel : '客户名称',
												labelStyle : 'text-align:right;',
												anchor : '90%'
											}]
								}]
					}],
			buttons : [{
				text : '查询',
				handler : function() {
				}
			}, {
				text : '重置',
				handler : function() {
					custSearchPanel_edit.getForm().reset();
				}
			}]
		});
var fields = [ {
	name : 'custId'
},{
	name : 'custZhName'
}, {
	name : 'certTypeOra'
}, {
	name : 'certNum'
}];

// 客户查询结果列表表头
var cm_edit = new Ext.grid.ColumnModel({
			columns : [{
						header : '客户号',
						dataIndex : 'custId',
						sortable : true,
						width : 140
					}, {
						header : '客户名称',
						dataIndex : 'custZhName',
						width : 150,
						sortable : true
					}, {
						header : '证件类型',
						dataIndex : 'certTypeOra',
						width : 100,
						sortable : true
					}, {
						header : '证件号码',
						dataIndex : 'certNum',
						width : 150,
						sortable : true
					}]
		});
var data = [
['CNJ2013021800090','河北金星日化用品厂','组织机构代码' ,'12345678-X'],
['CNJ2013021800091','河北金星日化采购中心','组织机构代码' ,'12345678-X'],
['CNJ2013021800092','河北金星日化产品原料厂','组织机构代码' ,'12345678-X'],
['CNJ2013021800093','河北金星日化分销公司','组织机构代码' ,'12345678-X'],
['CNJ2013021800094','河北金星日化零售公司','组织机构代码' ,'12345678-X']
		];


// 客户查询结果列表存储对象
var store_edit = new Ext.data.ArrayStore({
	fields : fields,
	data : data
});

// 翻页下拉框定义
var pagesize_combo_edit = new Ext.form.ComboBox({
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
			value : '20',
			editable : false,
			width : 85
		});

// 翻页工具栏定义
var bbar_edit = new Ext.PagingToolbar({
			pageSize : parseInt(pagesize_combo_edit.getValue()),
			store : store_edit,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			emptyMsg : "没有符合条件的记录"/*
									 * , items : ['-', '&nbsp;&nbsp;',
									 * pagesize_combo_edit]
									 */
		});

// 查询结果列表面板
var custGrid_edit = new Ext.grid.GridPanel({
			width : 400,
			height : 335,
			frame : true,
			autoScroll : true,
			ddGroup : 'custGroup',
			store : store_edit,
			stripeRows : true,
			enableDragDrop : true,
			cm : cm_edit,
			bbar : bbar_edit
		});

// 查询条件和查询结果布局
var custChoose_edit = new Ext.Panel({
			width : 400,
			items : [custSearchPanel_edit, custGrid_edit]
		});

// 页面左侧布局
var leftPanel_edit = new Ext.Panel({
			region : 'west',
			height : 520,
			width : 400,
			items : [custChoose_edit]
		});

// 装载关系图的容器对象
var containPanel_edit = new Ext.Panel({
			layout : 'fit',
			autoScroll : true,
			region : 'center',
			tbar : new Ext.Toolbar({
						items : []
					})
		});

// 整个页面布局
var mainGraphPanel_edit = new Ext.Panel({
			layout : 'border',
			items : [leftPanel_edit, containPanel_edit]
		});

// 窗体定义
var graphWindow_edit = new Ext.Window({
			title : '维护群成员关系图',
			layout : 'fit',
			draggable : true,// 是否可以拖动
			closable : true,// 是否可关闭
			modal : true,
			closeAction : 'hide',
			maximized : true,
			titleCollapse : true,
			buttonAlign : 'center',
			border : false,
			animCollapse : true,
			items : [mainGraphPanel_edit],
			listeners : {
				hide : function(){
					queryGraphData();
					}
			}
		});



// 成员关系下拉框定义
var r_boxstore = new Ext.data.Store({
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			restful : true,
			autoLoad : true,
			proxy : new Ext.data.HttpProxy({
						url : basepath + '/lookup.json?name=CUS0100038'
					}),
			reader : new Ext.data.JsonReader({
						root : 'JSON'
					}, ['key', 'value'])
		});

// 关系图对象
var gco_edit = new GraphCrmObject(containPanel_edit, null, false, false);

gco_edit.customerfull = function(o) {
	addCustomers_edit(o);
	if (o.opGrant) {
		customerTypeChoose_edit(o);
		gco_edit.addCellEvents(o);
	}
};

gco_edit.initDataFunc = function(o) {
	if (!o.graphData) {
		return;
	}
	var tBs = new Array();
	for (var v in o.graphData.vertexes) {
		if (o.graphData.vertexes[v].custId) {
			var tB = o.graph.insertVertex(o.graph.getDefaultParent(),
					o.graphData.vertexes[v].custId,
					o.graphData.vertexes[v].custName, 0, 0, 80, 30);
			tBs.push(tB);
		}
	}
	for (var e in o.graphData.edges) {
		if (o.graphData.edges[e].id) {
			var te = o.graph.insertEdge(o.graph.getDefaultParent(),
					o.graphData.edges[e].id, o.graphData.edges[e].relationName,
					getPointBaby(tBs, o.graphData.edges[e].fromVertex),
					getPointBaby(tBs, o.graphData.edges[e].toVertex));
			te._typecode = o.graphData.edges[e].relationCode;
		}
	}
}

// 客户关系类型选择
function customerTypeChoose_edit(o) {
	o.graph.addListener(mxEvent.DOUBLE_CLICK, function(sender, evt) {
		if (!o.opGrant) {
			return false;
		}
		var cell = evt.getProperty('cell');
		if (o.graph.getModel().isEdge(cell)) {
			var typediv = o.container
					.appendChild(document.createElement('div'));
			typediv.id = "___comb";
			typediv.style.cssText = "position:absolute;width:100px;height:25px;background:#000;";
			typediv.style.top = evt.properties.event.y;
			typediv.style.left = evt.properties.event.x;
			typediv.style.display = "block";
			var typecom = new Ext.form.ComboBox({
						hiddenName : 'HY_CLASS2',
						fieldLabel : '关系',
						labelStyle : 'text-align:right;',
						triggerAction : 'all',
						store : r_boxstore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						editable : false,
						emptyText : '请选择',
						resizable : true,
						anchor : '95%'
					});
			var tmpPanel = new Ext.Panel({
						height : 25,
						width : 150,
						layout : 'fit',
						items : [typecom]
					});
			typecom.expand();
			typecom.on('select', function() {
						if (typecom.getValue()) {
							cell.valueChanged(typecom.lastSelectionText);
							cell._typecode = typecom.getValue();
							o.graph.refresh(cell);
						}
						tmpPanel.destroy();
						typediv.parentNode.removeChild(typediv);
					});
			typecom.on('blur', function() {
						typediv.parentNode.removeChild(typediv);
					});
			if (!tmpPanel.rendered)
				tmpPanel.render(typediv);
			typecom.focus();
		}
	});
}

// 数据拖动
function addCustomers_edit(o) {
	var firstGridDropTargetEl = o.graph.container;
	var firstGridDropTarget = new Ext.dd.DropTarget(firstGridDropTargetEl, {
		ddGroup : 'custGroup',
		notifyDrop : function(ddSource, e, data) {
			var gf = o.graphIn(e.browserEvent);
			if (getPointBaby(o.graph.getModel().cells,
					data.selections[0].data.custId)) {
				Ext.Msg.alert("提示", "该客户已存在，不能重复拖入！");
				return false;
			}
			if (gf) {
				gf.insertVertex(o.graph.getDefaultParent(),
						data.selections[0].data.custId,
						data.selections[0].data.custZhName,
						e.browserEvent.offsetX, e.browserEvent.offsetY, 80, 30);
			}
			return true;
		}
	});
}

// 布局定义：星形布局
gco_edit.pushToolButton({
			id : 'hid',
			text : '星形布局',
			iconCls : 'treeIconCss',
			handler : function() {
				var starLayout = new mxStarLayout(gco_edit.graph, 150);
				starLayout.execute(gco_edit.graph.getDefaultParent());
			}
		});
// 功能操作定义
gco_edit.pushToolButton({
			text : '放大',
			iconCls : 'searchMaxMirrorIconCss',
			handler : function() {
				gco_edit.graph.zoomIn();
			}
		});
gco_edit.pushToolButton({
			text : '缩小',
			iconCls : 'searchMinMirrorIconCss',
			handler : function() {
				gco_edit.graph.zoomOut();
			}
		});
gco_edit.pushToolButton({
			text : '实际大小',
			iconCls : 'realDaXiaoIconCss',
			handler : function() {
				gco_edit.graph.zoomActual();
			}
		});
gco_edit.pushToolButton({
			text : '合适大小',
			iconCls : 'fitDaXiaoIconCss',
			handler : function() {
				gco_edit.graph.fit();
			}
		});
gco_edit.pushToolButton({
			text : '打印',
			iconCls : 'printIconCss',
			handler : function() {
				var scale = mxUtils.getScaleForPageCount(1, gco_edit.graph);
				var preview = new mxPrintPreview(gco_edit.graph, scale);
				preview.open();
			}
		});

// 保存操作
gco_edit.pushToolButton({
	text : '保存',
	iconCls : 'saveIconCss',
	withGrant : 'disabled',// 接受权限约束的属性
//	hidden : JsContext.checkGrant('__save'),// 如果当前用户没有“编辑、保存”权限，隐藏“保存”按钮
	handler : function() {
		var graphInfo = {
			"id" : oCustInfo.groupId
		};
		var vertexes = new Array();
		var edges = new Array();
		var model = gco_edit.graph.getModel();
		for (var i in model.cells) {
			if (model.isVertex(model.cells[i]))
				if (model.cells[i].edges) {
					var ver = {};
					ver.custId = model.cells[i].id;
					ver.custName = model.cells[i].value;
					vertexes.push(ver);
				}
		}
		for (var i in model.cells) {
			if (model.isEdge(model.cells[i])) {
				var edg = {};
				edg.fromVertex = model.cells[i].source.id;
				edg.toVertex = model.cells[i].target.id;
				if (model.cells[i].value) {
					edg.relationCode = model.cells[i]._typecode;
					edg.relationName = model.cells[i].value;
				}
				edges.push(edg);
			}
		}
		var infos = {
			"vertexes" : vertexes,
			"edges" : edges,
			"graph" : graphInfo
		};
		Ext.Ajax.request({
			url : basepath
					+ '/custGroupMemberGraphOperationAction!saveCustGroupMemberRelationGraph.json',
			method : 'POST',
			params : {
				infos : Ext.encode(infos)
			},
			success : function(a) {
				Ext.Msg.alert("提示", "保存成功！");
			},
			failure : function() {
				Ext.Msg.alert("提示", "保存失败！");
			}
		});
	}
});