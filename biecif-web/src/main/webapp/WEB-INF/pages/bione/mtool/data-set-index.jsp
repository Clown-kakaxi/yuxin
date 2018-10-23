<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template7.jsp">
<head>
<script type="text/javascript">
	var leftTreeObj;
	var dialog;
	var currentNode = null;//选中树节点
	var datasetGrid = null;
	var catalogBox = null;;

	$(function() {

		var $centerDom = $(document);
		gridCenter = $centerDom.height() - 38;

		initTree();
		initTab();

		//渲染树
		function initTree() {
			//树
			var setting = {
				async : {
					enable : true,
					url : "${ctx}/bione/mtool/dataset/getTree.json?t="
							+ new Date().getTime(),
					autoParam : [ "realId" ],
					dataType : "json",
					type : "post",
					dataFilter : function(treeId, parentNode, childNodes) {
						if (childNodes) {
							for ( var i = 0; i < childNodes.length; i++) {
								childNodes[i].realId = childNodes[i].params.realId;
								childNodes[i].nodeType = childNodes[i].params.nodeType;
								childNodes[i].isParent = childNodes[i].params.isParent == "true" ? true
										: false;
							}
						}
						return childNodes;
					}
				},
				data : {
					key : {
						name : "text"
					}
				},
				view : {
					selectedMulti : false
				},
				callback : {
					onClick : zTreeOnClick,
					onNodeCreated : function(event, treeId, treeNode) {
						if (treeNode.id == "ROOT") {
							//若是根节点，展开下一级节点
							leftTreeObj.reAsyncChildNodes(treeNode, "refresh");
						}
					}
				}
			};
			leftTreeObj = $.fn.zTree.init($("#tree"), setting);
			$("#template.left.center").hide();

			//TreeToolBar
			$("#treeToolbar").ligerTreeToolBar({
				items : [ {
					icon : 'add',
					text : '添加',
					click : addCatalog
				}, {
					line : true
				}, {
					icon : 'modify',
					text : '修改',
					click : editCatalog
				}, {
					line : true
				}, {
					icon : 'delete',
					text : '删除',
					click : deleteCatalog
				} ],
				treemenu : false
			});
		}
	});

	//渲染选项卡
	function initTab() {
		window['maintab'] = $("#tab").ligerTab({
			contextmenu : true
		});
		maintab.addTabItem({
			tabid : "topTab",
			text : "数据集",
			showClose : false,
			content : "<div id='gridFrame' style='height:" + gridCenter
					+ "px;width:100%;'></div>"
		});
	}

	//树的点击事件
	function zTreeOnClick(event, treeId, treeNode) {
		if (currentNode != treeNode) {
			if (treeNode.id == "ROOT") {
				currentNode = null;
			} else {
				currentNode = treeNode;
			}
			if (!datasetGrid) {
				content = "<iframe frameborder='0' id='gridFrame' style='height:100%;width:100%;' src='${ctx}/bione/mtool/dataset/grid?catalogId="
						+ treeNode.realId
						+ "&catalogName="
						+ treeNode.text
						+ "'></iframe>";
				$("#gridFrame").html(content);
			} else {
				datasetGrid.set('parms', {
					catalogId : treeNode.realId,
					catalogName : treeNode.text,
					d : new Date().getTime()
				});
				if(catalogBox){
					catalogBox.val(treeNode.realId);
				}
				datasetGrid.loadData();
			}
		}
	}

	//刷新树
	function refreshTree() {
		if (leftTreeObj) {
			var selectedNodes = leftTreeObj.getSelectedNodes();
			if (selectedNodes != null && selectedNodes.length > 0) {
				//若树有选中节点,刷新指定节点
				leftTreeObj.reAsyncChildNodes(selectedNodes[0], "refresh");
			} else {
				//其他情况，刷新全部
				leftTreeObj.reAsyncChildNodes(null, "refresh");
			}
		}
	}

	//新增分类
	function addCatalog() {
		var url = "${ctx}/bione/mtool/dataset/catalog?upId=";
		if (currentNode != null && currentNode.realId != "ROOT")
			url += currentNode.realId;
		else
			url += "0";
		BIONE.commonOpenDialog("新增数据集目录", "editCatalogBox", 420, 270, url);
	}

	//修改分类
	function editCatalog() {
		if (!currentNode) {
			BIONE.tip("未选择任何目录。");
			return;
		}
		BIONE.commonOpenDialog("修改数据集目录", "editCatalogBox", 420, 270,
				"${ctx}/bione/mtool/dataset/catalog?catalogId="
						+ currentNode.realId);
	}

	//删除目录
	function deleteCatalog() {
		if (!currentNode || currentNode.id == 'ROOT') {
			BIONE.tip("未选择任何目录。");
			return;
		}
		$.ligerDialog.confirm("确定删除数据集目录 [ " + currentNode.text + " ] ？",
				function(flag) {
					if (flag) {
						$.ajax({
							type : "POST",
							url : "${ctx}/bione/mtool/dataset/deleteCatalog",
							data : {
								catalogId : currentNode.realId
							},
							success : function(res) {
								if (res) {
									BIONE.tip("删除成功！");
									//局部刷新树
									leftTreeObj.reAsyncChildNodes(currentNode
											.getParentNode(), "refresh");
									initTab();
									currentNode = null;
								} else {
									BIONE.tip("该目录下存在数据集，无法删除。");
								}
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								BIONE.tip('删除失败,错误信息:' + textStatus);
							}
						});
					}
				});
	}
</script>
</head>
<body>
	<div id="template.left.up.icon">
		<img src="${ctx}/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">
		<span style="font-size: 12" id="left_up_span">数据集目录</span>
	</div>
	<div id="template.left.down">
		<div id="btn"></div>
	</div>
</body>
</html>