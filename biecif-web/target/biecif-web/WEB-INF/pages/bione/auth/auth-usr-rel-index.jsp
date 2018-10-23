<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template6.jsp">
<head>
<script type="text/javascript">
    var auth_obj_root_icon = "${ctx}/images/classics/icons/house.png";
    var groupicon = "${ctx}/images/classics/icons/find.png";

    //展开全部节点
    function openAllNodes(treeObj) {
	if (treeObj) {
	    treeObj.expandAll(true);
	}
    }

    //折叠全部节点
    function closeAllNodes(treeObj) {
	if (treeObj) {
	    treeObj.expandAll(false);
	}
    }

    //初始化授权对象树
    function initAuthObjTree(objDefNo, treeId) {
	$.ajax({
	    cache : false,
	    async : true,
	    url : "${ctx}/bione/admin/authUsrRel/getAuthObjTree.json",
	    dataType : 'json',
	    data : {
		objDefNo : objDefNo
	    },
	    type : "post",
	    beforeSend : function() {
		BIONE.loading = true;
		BIONE.showLoading("正在加载数据中...");
	    },
	    complete : function() {
		BIONE.loading = false;
		BIONE.hideLoading();
	    },
	    success : function(result) {
		if (!result)
		    return;
		var curTree = eval(treeId);
		if (curTree) {
		    for ( var i = 0; i < result.length; i++) {
			if (result[i].id != "0") {
			    result[i].objId = result[i].params.id;
			    if (result[i].params.cantClick) {
				result[i].nocheck = true;
			    }
			}
		    }
		    curTree.addNodes(curTree.getNodeByParam("id", "0", null),
			    result, false);
		    curTree.expandAll(true);
		}
	    },
	    error : function(result, b) {
		BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
	    }
	});
    }

    $(function() {

	BIONE
		.createButton({
		    text : '保 存',
		    width : '80px',
		    appendTo : '#bottom',
		    operNo : 'saveButton',
		    icon : 'save',
		    click : function() {
			if (leftTree) {
			    var leftSelNodes = leftTree.getSelectedNodes();
			    if (!leftSelNodes.length
				    || leftSelNodes.length <= 0) {
				//若没有选择授权对象
				$.ligerDialog.warn('请选择用户');
				return;
			    } else {
				var leftSelNode = leftSelNodes[0];
				if (leftSelNode.id == '0') {
				    //若选择的是根节点
				    $.ligerDialog.warn('请选择用户');
				    return;
				} else {
				    if (navtab) {
					var objs = new Array();
					var tabIdList = navtab.getTabidList();
					for ( var i = 0; i < tabIdList.length; i++) {
					    var objDefNo = tabIdList[i];
					    var objIds = "";
					    if (eval(objDefNo + "_tree")) {
						var checkedNodes = eval(
							objDefNo + "_tree")
							.getCheckedNodes(true);
						var checkedNodesArray = eval(
							objDefNo + "_tree")
							.transformToArray(
								checkedNodes);
						for ( var j = 0; j < checkedNodesArray.length; j++) {
						    if (checkedNodesArray[j].id == "0"
							    || checkedNodesArray[j].params.cantClick
							    || !checkedNodesArray[j].checked) {
							//若是树根节点，或者节点设置了不能点击，或者是未勾选节点
							continue;
						    }
						    if (objIds != "") {
							objIds += ",";
						    }
						    objIds += checkedNodesArray[j].params.id;
						}
						if (objIds != "") {
						    objs.push({
							objDefNo : objDefNo,
							objIds : objIds
						    });
						}
					    }
					}
					var relObjs = {
					    userId : leftSelNode.params.id,
					    objs : objs
					};
					$
						.ajax({
						    cache : false,
						    async : true,
						    url : "${ctx}/bione/admin/authUsrRel/saveObjUserRel",
						    dataType : 'json',
						    type : "post",
						    data : {
							relObjs : JSON2
								.stringify(relObjs)
						    },
						    beforeSend : function() {
							BIONE.loading = true;
							BIONE
								.showLoading("正在加载数据中...");
						    },
						    complete : function() {
							BIONE.loading = false;
							BIONE.hideLoading();
						    },
						    success : function(result) {
							BIONE.tip('保存成功!');
						    },
						    error : function(result, b) {
							BIONE
								.tip('保存失败,发现系统错误 <BR>错误码：'
									+ result.status);
						    }
						});

				    }
				}
			    }
			}
		    }
		});

	window["leftTree"] = $.fn.zTree
		.init(
			$("#leftTree"),
			{
			    data : {
				keep : {
				    parent : true
				},
				key : {
				    name : "text"
				},
				simpleData : {
				    enable : true,
				    idKey : "id",
				    pIdKey : "upId",
				    rootPId : null
				}
			    },
			    view : {
				selectedMulti : false,
				showLine : false
			    },
			    callback : {
				beforeClick : function(treeId, treeNode,
					clickFlag) {
				    if (treeNode.id == "0") {
					//若是根节点
					return false;
				    }
				},
				onClick : function(event, treeId, treeNode,
					clickFlag) {
				    //获取指定用户关系勾选
				    var objId = treeNode.params.id;
				    $
					    .ajax({
						cache : false,
						async : true,
						url : "${ctx}/bione/admin/authUsrRel/getObjUserRel.json",
						dataType : 'json',
						data : {
						    objId : objId
						},
						type : "post",
						beforeSend : function() {
						    BIONE.loading = true;
						    BIONE
							    .showLoading("正在加载数据中...");
						},
						complete : function() {
						    BIONE.loading = false;
						    BIONE.hideLoading();
						},
						success : function(result) {
						    //清除所有树之前选中
						    if (navtab) {
							var tabIdList = navtab
								.getTabidList();
							for ( var i = 0; i < tabIdList.length; i++) {
							    var objDefNo = tabIdList[i];
							    if (eval(objDefNo
								    + "_tree")) {
								eval(
									objDefNo
										+ "_tree")
									.checkAllNodes(
										false);
							    }
							}
						    }
						    if (!result)
							return;
						    for ( var i = 0; i < result.length; i++) {
							var objDefNo = result[i].id.objDefNo;
							if (document.getElementById(objDefNo+"_tree")!=null) {
							    //勾选相应节点
							    var node = eval(
								    objDefNo
									    + "_tree")
								    .getNodeByParam(
									    "objId",
									    result[i].id.objId,
									    null);
							    if (!node
								    || typeof node == 'undefined') {
								continue;
							    }
							    eval(
								    objDefNo
									    + "_tree")
								    .checkNode(
									    node,
									    true,
									    true,
									    false);
							}
						    }
						},
						error : function(result, b) {
						    BIONE.tip('发现系统错误 <BR>错误码：'
							    + result.status);
						}
					    });
				}
			    }
			}, [ {
			    id : "0",
			    text : "用户树",
			    icon : auth_obj_root_icon
			} ]);

	//初始化用户树
	function initUserTree() {
	    $.ajax({
		cache : false,
		async : true,
		url : "${ctx}/bione/admin/authUsrRel/getUserTree.json",
		dataType : 'json',
		type : "post",
		beforeSend : function() {
		    BIONE.loading = true;
		    BIONE.showLoading("正在加载数据中...");
		},
		complete : function() {
		    BIONE.loading = false;
		    BIONE.hideLoading();
		},
		success : function(result) {
		    if (!result)
			return;
		    if (leftTree) {
			//先移除所有旧节点
			var oldTreeNodes = leftTree.getNodes();
			var oldTreeNodesArray = leftTree
				.transformToArray(oldTreeNodes);
			for ( var j = 0; j < oldTreeNodesArray.length; j++) {
			    if (oldTreeNodesArray[j].id == '0') {
				continue;
			    }
			    leftTree.removeNode(oldTreeNodesArray[j]);
			}
			leftTree.addNodes(leftTree.getNodeByParam("id", "0",
				null), result, false);
			leftTree.expandAll(true);
			var nodesArray = leftTree.transformToArray(leftTree
				.getNodes());
			for ( var i = 0; i < nodesArray.length; i++) {
			    if (nodesArray[i].id != "0") {
				nodesArray[i].objId = nodesArray[i].params.id;
			    }
			}
		    }
		},
		error : function(result, b) {
		    BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
		}
	    });
	}
	initUserTree();
	//初始化左侧菜单
	$("#treeToolbar").ligerTreeToolBar({
	    items : [ {
		icon : 'refresh',
		text : '刷新',
		click : initUserTree
	    }, {
		line : true
	    }, {
		icon : 'plus',
		text : '展开',
		click : function() {
		    openAllNodes(leftTree);
		}
	    }, {
		line : true
	    }, {
		icon : 'lock',
		text : '折叠',
		click : function() {
		    closeAllNodes(leftTree);
		}
	    } ],
	    treemenu : false
	});
	window['navtab'] = $("#navtab").ligerTab();
	//循环tab构建相应树
	var tabIds = navtab.getTabidList();
	if (tabIds && tabIds.length > 0) {
	    for ( var i = 0; i < tabIds.length; i++) {
		var objDefNo = tabIds[i];
		var treeId = tabIds[i] + "_tree";
		if ($("#" + treeId)) {
		    window[treeId] = $.fn.zTree.init($("#" + treeId), {
			data : {
			    keep : {
				parent : true
			    },
			    key : {
				name : "text"
			    },
			    simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "upId",
				rootPId : null
			    }
			},
			view : {
			    selectedMulti : false,
			    showLine : true
			},
			check : {
			    chkStyle : 'checkbox',
			    enable : true,
			    chkboxType : {
				"Y" : "",
				"N" : ""
			    }
			}
		    }, [ {
			id : "0",
			text : "授权对象树",
			icon : auth_obj_root_icon,
			nocheck : true
		    } ]);
		    //初始化树
		    initAuthObjTree(objDefNo, treeId);
		}
	    }
	}
    })
</script>
</head>
<body>
	<div id="template.left.up.icon">
		<img src="${ctx}/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">授权用户</div>
	<div id="template.right">
		<div id="navtab" style="overflow: hidden;">
			<div tabid="AUTH_OBJ_ROLE" title="角色" lselected="true"
				style="overflow: auto;">
				<div id="container1"
					style="width: 100%; overflow: auto; clear: both; background-color: #FFFFFF;">
					<div id="AUTH_OBJ_ROLE_tree" class="ztree"
						style="font-size: 12; background-color: #FFFFFF; width: 95%"></div>
				</div>
			</div>
			<%--
			<div tabid="AUTH_OBJ_DEPT" title="部门" lselected="false"
				style="overflow: auto;">
				<div id="container2"
					style="width: 100%; overflow: auto; clear: both; background-color: #FFFFFF;">
					<div id="AUTH_OBJ_DEPT_tree" class="ztree"
						style="font-size: 12; background-color: #FFFFFF; width: 95%"></div>
				</div>
			</div>
			<div tabid="AUTH_OBJ_ORG" title="机构" lselected="true"
				style="overflow: auto;">
				<div id="container3"
					style="width: 100%; overflow: auto; clear: both; background-color: #FFFFFF;">
					<div id="AUTH_OBJ_ORG_tree" class="ztree"
						style="font-size: 12; background-color: #FFFFFF; width: 95%"></div>
				</div>
			</div>
			<div tabid="AUTH_OBJ_GROUP" title="对象组" lselected="true"
				style="overflow: auto;">
				<div id="container4"
					style="width: 100%; overflow: auto; clear: both; background-color: #FFFFFF;">
					<div id="AUTH_OBJ_GROUP_tree" class="ztree"
						style="font-size: 12; background-color: #FFFFFF; width: 95%"></div>
				</div>
			</div>
			 --%>
		</div>
	</div>
</body>
</html>