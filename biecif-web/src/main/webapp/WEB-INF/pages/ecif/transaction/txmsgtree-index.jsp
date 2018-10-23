<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template7.jsp">
<script type="text/javascript">
var leftTree;
var grid;

var nodeId = "${id}";
//alert(nodeId);

$(function() {

	initTree();
	
	initTreeNodes();
	var height = $("#right").height();
	var frame = $('<iframe/>').attr({
		id : "frame",
		src : "${ctx}/ecif/transaction/txmsgnodeattr/nodeattr?nodeId="+nodeId ,
		frameborder : "0"
	}).css({
		width : "100%",
		height : height
	});
	$("#tab").append(frame);
});

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
			url : "${ctx}/ecif/transaction/txmsg/"+ nodeId +"/requestmsglist.json",
			dataType : 'json',
			type : "get",
			success : function(result) {
				//debugger;

				leftTree.addNodes(null, result, false);

				leftTree.expandAll(true);
			}
		});
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
	//debugger;
	if(clickFlag == "1") {
		var f = document.getElementById("frame");
		if("0" == treeNode.id) {
			f.contentWindow.changeNode();
			f.contentWindow.nodeId = "";
		} else {
			//document.getElementById("frame").contentWindow.changeNode(treeNode.id);
			f.contentWindow.changeNode(treeNode.id);
			f.contentWindow.nodeId = treeNode.id;
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
		<span style="font-size: 12">请求报文树</span>
	</div>
	<div>
	</div>
</body>
</html>