<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template10.jsp">
<head>
<script type="text/javascript">
	var id = "${logicSysNo}";
	
	var leftTreeObj;//左侧树
	var rightTreeObj;//右侧树
	
	var groupicon = "${ctx}/images/classics/icons/find.png";
	
	$(function() {
		
		$("#rightTreeToolbar").ligerTreeToolBar({
			items : [{
				icon:'delete',
				text : '删除节点',
				click : treeRemoveNode
			},{
				line : true
			}]
		});
		
		var setting = {
				data:{
					key:{
						name:'text'
					},
					simpleData:{
						enable:true,
						idKey: "id",
						pIdKey: "upId"
					}
				},
				edit:{
					enable: true,
					showRemoveBtn: false,
					showRenameBtn: false
				},callback: {
					beforeDrop: beforeDrop
				}
			};
		
		var setting2 = {
				data:{
					key:{
						name:'text'
					},
					simpleData:{
						enable:true,
						idKey: "id",
						pIdKey: "upId"
					}
				},
				edit:{
					drag: {
						isCopy: false,
						isMove: false
					},
					enable: true,
					showRemoveBtn: false,
					showRenameBtn: false
				},
				callback: {
					beforeDrag: beforeDrag
				}
			};
		leftTreeObj =$.fn.zTree.init($("#leftTree"), setting);
		//加载数据
		loadTree("${ctx}/bione/admin/logicSys/getObjList.json?logicSysNo="+id,leftTreeObj);
		
		rightTreeObj = $.fn.zTree.init($("#rightTree"),setting2);
		//加载数据
		loadTree("${ctx}/bione/admin/logicSys/getAuthObjList.json?logicSysNo="+id,rightTreeObj);
		
		BIONE.createButton({
			text : '保存',
			width : '80px',
			appendTo : '#bottom',
			operNo:'saveButton',
			icon:'save',
			click : function() {
				var nodes = rightTreeObj.getNodes();
				var paramStr = "";
				for(var i = 0;i<nodes.length;i++){
					paramStr += nodes[i].id+";";
				}
				var url = "${ctx}/bione/admin/logicSys/saveAuthObj?logicSysNo="+id+"&params="+paramStr;
				f_admin_save(url);
			}
		});
		
		BIONE.createButton({
			text : '取消',
			width : '80px',
			appendTo : '#bottom',
			operNo:'resetButton',
			icon:'refresh',
			click : function() {
				BIONE.closeDialog("authObjWin");
			}
		});
		
	});
	//拖拽前
	function beforeDrag(treeId, treeNodes) {
		for (var i=0,l=treeNodes.length; i<l; i++) {
			if (treeNodes[i].drag === false) {
				return false;
			}
		}
		return true;
	}
	
	//拖拽 放开前
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		var isDrop = true;
		if(targetNode&&moveType=="inner"){
			isDrop = false;
		}
		return isDrop;
	}
	
	//删除节点
	function treeRemoveNode() {
		var nodes = rightTreeObj.getSelectedNodes();
		if(nodes.length>0){
			for(var i=0;i<nodes.length;i++ ){
				var node = nodes[i];
				var isCanRemove = false;
				var nodeRealId = node.id;
				$.ajax({
					cache : false,
					async : false,
					url : "${ctx}/bione/admin/logicSys/checkObj",
					data:{"logicSysNo":id,"nodeRealId":nodeRealId},
					type : "post",
					success : function(result) {
						isCanRemove = result;
						if(!isCanRemove){
							BIONE.tip('节点已经被引用，无法删除！');
						}
					},
					error : function(result, b) {
						isCanRemove = true;
					}
				});
				if(isCanRemove){
					rightTreeObj.removeNode(node,false);
					leftTreeObj.addNodes(null,node,false);
				}
			}
		}else{
			BIONE.tip("请选择节点");
		}
	}
	
	//加载树中数据
	function loadTree(url,component,data){
		$.ajax({
			cache : false,
			async : true,
			url : url,
			dataType : 'json',
			data:data,
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
				var nodes = component.getNodes();
				var num = nodes.length;
				for(var i=0;i<num;i++){
					component.removeNode(nodes[0],false);
				}
				if(result.length>0){
					component.addNodes(null,result,false);
					component.expandAll(true);	
				}
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}
	
	//保存数据
	function f_admin_save(url){
		$.ajax({
			cache : false,
			async : true,
			url : url,
			type : "get",
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("正在加载数据中...");
			},
			complete : function() {
				BIONE.loading = false;
				BIONE.hideLoading();
			},
			success : function() {
				BIONE.closeDialog("authObjWin","保存成功！");
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}
</script>
</head>
<body>
	<div id="template.left.up.icon">
		<img src="${ctx}/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.right.up.icon">
		<img src="${ctx}/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">未选择对象</div>
	<div id="template.right.up">已选择对象</div>

</body>
</html>