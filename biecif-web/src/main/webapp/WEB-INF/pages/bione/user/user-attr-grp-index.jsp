<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template7.jsp">
<script type="text/javascript">
var leftTree;
var grid;
$(function() {
	initTreeToolbar();
	initTree();
	initTreeNodes();
	var height = $("#right").height();
	var frame = $('<iframe/>').attr({
		id : "frame",
		src : "${ctx}/bione/admin/userattrgrp/userattr",
		frameborder : "0"
	}).css({
		width : "100%",
		height : height
	});
	$("#tab").append(frame);
});
/**
 * 初始化树工具栏
 */
function initTreeToolbar() {
	$("#treeToolbar").ligerTreeToolBar({
		items : [ {
			icon : 'add',
			text : '添加',
			click : f_addGrp
		}, {
			line : true
		},{
			icon : 'modify',
			text : '修改',
			click : f_mdfGrp
		}, {
			line : true
		},{
			icon : 'delete',
			text : '删除',
			click : f_delGrp
		}],
		treemenu : false
	});
}
function initTree() {
	leftTree = $.fn.zTree.init($("#tree"),{
		data:{
			keep:{
				parent : true
			},
			key : {
				name : "text"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "upId",
				rootPId : "0"
			}
		},
		view : {
			showLine : true,
			selectedMulti : true
		},
		edit: {
			enable: false,
			showRemoveBtn: false,
			showRenameBtn: false
		},
		callback: {
			beforeClick: f_onClick
		}
	});
}
function initTreeNodes() {
	if(leftTree) {
		removeAll(leftTree);
		$.ajax({
			cache : false,
			async : true,
			url : "${ctx}/bione/admin/userattrgrp/listgrps.json",
			dataType : 'json',
			type : "get",
			success : function(result) {
				var child = result[0].children;
				if (!child){
					return;
				} else {
					for(var i = 0; i < child.length; i++) {
						if(child[i].icon) {
							child[i].icon = "${ctx}/" + child[i].icon;
						}
					}
				}
				leftTree.addNodes(null,result);
				leftTree.expandAll(true);
			}
		});
	}
}
/**
 * 添加分组
 */
function f_addGrp(){
	BIONE.commonOpenLargeDialog("添加分组", "addGrp", "${ctx}/bione/admin/userattrgrp/new");
}
/**
 * 修改分组
 */
function f_mdfGrp(){
	var obj = leftTree.getSelectedNodes();
	if(obj.length == 1) {
		BIONE.commonOpenLargeDialog("修改分组", "addGrp", "${ctx}/bione/admin/userattrgrp/"+obj[0].id+"/edit");
	} else {
		BIONE.tip("请选择一个节点");
	}
	
}
/**
 * 删除分组
 */
function f_delGrp(){
	var obj = leftTree.getSelectedNodes();
	if(obj.length == 1){
		$.ligerDialog.confirm('确定删除？', function (flag) {
			if(flag){
				$.ajax({
					url : "${ctx}/bione/admin/userattrgrp/"+obj[0].id,
					type : 'DELETE',
					success : function(msg) {
						if(msg) {
							if(msg&&msg==true){								
								leftTree.removeNode(obj[0]);
								var grid = document.getElementById("frame").contentWindow.grid;
								if(grid){
									grid.loadData();
								}
								BIONE.tip("删除成功");
								return ;
							}						
						}
						BIONE.tip("删除失败");
					}
				});
			}
		});
	} else {
		BIONE.tip("请选择一个节点");
	}
}
/**
 * 移除所有树节点
 * @param {object} treeObj
 */
function removeAll(treeObj){
	if(treeObj){
		var nodes = treeObj.getNodes();
		var tIdArray = [];
		if(nodes != null){
			for(var i = 0 ; i < nodes.length ; i++){
				tIdArray.push(nodes[i].id);
			}
			for(var j = 0 ; j < tIdArray.length ; j++){
				var nodeReal = treeObj.getNodeByParam("id",tIdArray[j],null);
				if(nodeReal != null){
					treeObj.removeChildNodes(nodeReal);
					treeObj.removeNode(nodeReal);
				}
			}
		}
	}	
}
 
function f_onClick(treeId, treeNode, clickFlag) {
	if(clickFlag == "1") {
		var f = document.getElementById("frame");
		if("0" == treeNode.id) {
			f.contentWindow.changeGrp();
			f.contentWindow.grpId = "";
		} else {
			f.contentWindow.changeGrp(treeNode.id);
			f.contentWindow.grpId = treeNode.id;
		}
	}
	return true;
}
</script>
</head>
<body>
	<div id="template.left.up.icon">
		<img src="${ctx}/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">
		<span style="font-size: 12">用户信息属性分组</span>
	</div>
	<div>
	</div>
</body>
</html>