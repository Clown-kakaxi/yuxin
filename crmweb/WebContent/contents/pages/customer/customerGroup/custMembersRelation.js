/**
 * 客户管理->客户群组管理->群成员视图->成员关系图查看 入口JS文件 wzy，2013-04-09
 */

// 装载关系图的容器对象
var containPanel_view = new Ext.Panel({
			title : '群成员关系图',
			renderTo : 'group_viewport_center',
			layout : 'fit',
			autoScroll : true,
			width : document.body.clientWidth - 225,
			height : document.body.clientHeight - 10,
			tbar : new Ext.Toolbar({
						items : []
					})
		});

// 当窗口大小改变时触发的事件：重新设置列表高度
window.onresize = function() {
	// 重新设置列表高度
	if (custGrid_edit) {
		custGrid_edit.setHeight(document.body.clientHeight - 160);
	}
	// 重新设置关系图展示区域高度
	if (containPanel_edit) {
		containPanel_edit.setHeight(document.body.clientHeight - 10);
	}
	containPanel_view.setHeight(document.body.clientHeight - 10);
};

// 关系图对象
var gco_view = new GraphCrmObject(containPanel_view, null, false, false);

gco_view.customerfull = function(o) {
	if (o.opGrant) {
		gco_view.addCellEvents(o);
	}
};

gco_view.initDataFunc = function(o) {
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

// 覆盖mxclient-ie1.8.js中的对应方法：只显示“删除”图标
function mxIconSet(state, opGrant) {
	this.images = [];
	this.md = (mxClient.IS_TOUCH) ? 'touchstart' : 'mousedown';
	var graph = state.view.graph;
	// Delete
	if (opGrant) {
		var img = mxUtils
				.createImage(mxClient.basePath + '/images/delete2.png');
		img.setAttribute('title', '删除');
		img.style.position = 'absolute';
		img.style.cursor = 'pointer';
		img.style.width = '16px';
		img.style.height = '16px';
		img.style.left = (state.x + state.width) + 'px';
		img.style.top = (state.y - 16) + 'px';
		mxEvent.addListener(img, this.md, mxUtils.bind(this, function(evt) {
							// Disables dragging the image
							mxEvent.consume(evt);
						}));
		mxEvent.addListener(img, 'click', mxUtils.bind(this, function(evt) {
							graph.removeCells([state.cell]);
							mxEvent.consume(evt);
							this.destroy();
						}));
		state.view.graph.container.appendChild(img);
		this.images.push(img);
	}
};

// 覆盖mxclient-ie1.8.js中的对应方法
mxIconSet.prototype.destroy = function() {
	if (this.images != null) {
		for (var i = 0; i < this.images.length; i++) {
			var img = this.images[i];
			img.parentNode.removeChild(img);
		}
	}
	this.images = null;
};

// 布局定义：星形布局
gco_view.pushToolButton({
			id : 'hid',
			text : '星形布局',
			iconCls : 'treeIconCss',
			handler : function() {
				var starLayout = new mxStarLayout(gco_view.graph, 150);
				starLayout.execute(gco_view.graph.getDefaultParent());
			}
		});
// 功能操作定义
gco_view.pushToolButton({
			text : '放大',
			iconCls : 'searchMaxMirrorIconCss',
			handler : function() {
				gco_view.graph.zoomIn();
			}
		});
gco_view.pushToolButton({
			text : '缩小',
			iconCls : 'searchMinMirrorIconCss',
			handler : function() {
				gco_view.graph.zoomOut();
			}
		});
gco_view.pushToolButton({
			text : '实际大小',
			iconCls : 'realDaXiaoIconCss',
			handler : function() {
				gco_view.graph.zoomActual();
			}
		});
gco_view.pushToolButton({
			text : '合适大小',
			iconCls : 'fitDaXiaoIconCss',
			handler : function() {
				gco_view.graph.fit();
			}
		});
gco_view.pushToolButton({
			text : '打印',
			iconCls : 'printIconCss',
			handler : function() {
				var scale = mxUtils.getScaleForPageCount(1, gco_view.graph);
				var preview = new mxPrintPreview(gco_view.graph, scale);
				preview.open();
			}
		});
// 维护关系图
gco_view.pushToolButton({
			text : '维护关系图',
			iconCls : 'editIconCss',
			hidden : JsContext.checkGrant('__save'),// 如果当前用户没有“编辑、保存”权限，隐藏此按钮
			handler : function() {
				var url_str = basepath
						+ '/custGroupMemberGraphOperationAction/'
						+ oCustInfo.groupId + "/edit.json";
				Ext.MessageBox.wait('正在查询数据，请稍后......', '提示', {
							interval : 1, // 进度条的时间间隔
							increment : 1, // 进度条的分段数量
							fn : function callback() {
							}
						});
				Ext.Ajax.request({
							url : url_str,
							method : 'GET',
							params : {
								'graphId' : oCustInfo.groupId
							},
							success : function(a) {
								// 查询数据
								store_edit.removeAll();
								store_edit.load();
								try {
									graphWindow_edit.show();
								} catch (e) {
									// alert(e.message);
									Ext.MessageBox.hide();// 清空提示
								}
								gco_edit.showData(Ext.decode(a.responseText),
										true);
								Ext.MessageBox.hide();// 清空提示
							},
							failure : function(a) {
								Ext.MessageBox.alert('提示', '查询关系图数据出错！');
								Ext.MessageBox.hide();// 清空提示
								return false;
							}
						});
			}
		});

// 查询关系图数据并进行展示
function queryGraphData() {
	var url_str = basepath + '/custGroupMemberGraphOperationAction/'
			+ oCustInfo.groupId + "/edit.json";
	Ext.Ajax.request({
				url : url_str,
				method : 'GET',
				params : {
					'graphId' : oCustInfo.groupId
				},
				success : function(a) {
					gco_view.showData(Ext.decode(a.responseText), true);
				},
				failure : function(a) {
					Ext.MessageBox.alert('提示', '查询关系图数据出错！');
					return false;
				}
			});
}

queryGraphData();
