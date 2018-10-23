<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template10.jsp">
<head>
<script type="text/javascript">
	var logicSysNo = "${logicSysNo}";
	var leftTreeObj;
	var rightTreeObj;
	var indexPageId ='';
	var groupicon = "${ctx}/images/classics/icons/find.png";
	var nodeArray = new Array();
	
	
	$(function() {
		
		$("#rightTreeToolbar").ligerTreeToolBar({
			items : [{
				icon:'settings',
				text : '设置首页',
				click : setIndexPage
			},{
				line : true
			},{
				icon:'delete',
				text : '删除节点',
				click : treeRemoveNode
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
						pIdKey: "upId",
						rootPId:0
					}
				},
				edit:{
					enable: true,
					showRemoveBtn: false,
					showRenameBtn: false,
					drag:{
						isCopy: true,
						isMove: false,
						prev:false,
						next:false,
						inner:false
					}
				},
				callback: {
					beforeDrop: beforeDrop
				}
			};
		
		var setting2 = {
				view: {
					nameIsHTML: true,
					showTitle:false
				},
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
					beforeDrop: rightBeforeDrop
				}
			};
		leftTreeObj =$.fn.zTree.init($("#leftTree"), setting);
		
		//加载数据
		loadTree("${ctx}/bione/admin/logicSys/getFuncList.json",leftTreeObj);
		
		rightTreeObj = $.fn.zTree.init($("#rightTree"),setting2);
		//加载数据
		loadTree("${ctx}/bione/admin/logicSys/getMenuToTree.json?logicSysNo="+logicSysNo,rightTreeObj);
		
		BIONE.createButton({
			text : '保存',
			width : '80px',
			appendTo : '#bottom',
			operNo:'saveButton',
			icon:'save',
			click : function() {
				var nodes = rightTreeObj.transformToArray(rightTreeObj.getNodes());
				var paramStr = "";
				for(var i = 0;i<nodes.length;i++){
					var node = nodes[i];
					
					var param = node.id+":";
					if(node.getParentNode()){
						param += node.getParentNode().id+";";
					}else{
						param += 0+";";
					}
					if(paramStr.indexOf(";"+param)==-1||paramStr ==""){
						if(node.id !='0'){
							paramStr += param;
						}
					}
					
				}
				var url = "${ctx}/bione/admin/logicSys/saveMenu.json";
				
				var dataParams = {
						logicSysNo:logicSysNo,
						params:paramStr,
						indexPageId:indexPageId
				} ;
				
				f_admin_save(url,dataParams);
			}
		});
		
		BIONE.createButton({
			text : '取消',
			width : '80px',
			appendTo : '#bottom',
			operNo:'resetButton',
			icon:'refresh',
			click : function() {
				BIONE.closeDialog("logicMenuWin");
			}
		});
		
	});
	
	//拖拽 放开前
	function beforeDrop(treeId, treeNodes, targetNode, moveType) {
		for(var i=0;i<treeNodes.length;i++){
			addTreeNode(rightTreeObj,treeNodes[i],targetNode);
		}
		return false;
	}
	
	//拖拽 放开前
	function rightBeforeDrop(treeId, treeNodes, targetNode, moveType) {
		if(targetNode.id=='0'){
			if(moveType=='inner'){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	}
	
	//删除节点
	function treeRemoveNode() {
		var nodes = rightTreeObj.getSelectedNodes();
		if(nodes.length>0){
			for(var i=0;i<nodes.length;i++ ){
				var node = nodes[i];
				if(node.id == indexPageId){
					$.ligerDialog.confirm("此节点是首页,确定删除？",function (yes) {
						if(yes){
							indexPageId ="";
							rightTreeObj.removeNode(node,false);
						}
					});
				}else{
					rightTreeObj.removeNode(node,false);
				}
			}
		}else{
			BIONE.tip("请选择节点");
		}
	}
	
	/**添加节点信息
	*tree 目标树对象
	*treeNode 拖拽节点
	*targetNode 目标节点
	*/
	function addTreeNode(tree,treeNode,targetNode){
		
		if(treeNode.params.type=="M"){
			BIONE.tip("模块节点不允许被拖拽");
			return;
		}
		
		getNodeArray(treeNode);
		
		var haveNode;
		for(var i = 0; i<nodeArray.length;i++){
			var currNode = nodeArray[i];
			haveNode = tree.getNodeByParam("id", currNode.id, null);
			if(haveNode){
				BIONE.tip("该节点子节点已经存在");
				return ;
			}
		}
		
		if(targetNode){
			
			if(treeNode.id == targetNode.id){
				BIONE.tip("该节点不能添加自身");
				return;
			}
			tree.addNodes(targetNode,treeNode,false);
		}else{
			treeNode.upId = '0';
			var rootNode = tree.getNodeByParam("id", '0', null);
			tree.addNodes(rootNode,treeNode,false);
		}
	}

	//获取拖拽节点的所有字节点
		function getNodeArray(treeNode){
			nodeArray = [];
			nodeArray.push(treeNode);
			var nodes = treeNode.children;
			if(nodes){
				for(var i =0;i<nodes.length;i++){
					getNodeArray(nodes[i]);
				}
			}
		}
	
	//加载树中数据
	function loadTree(url,component,data){
		$.ajax({
			cache : false,
			async : true,
			url : url,
			dataType : 'json',
			type : "post",
			data:data,
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
				if(result.nodes.length>0){
					var resultNodes = result.nodes;
					component.addNodes(null,resultNodes,false);
					component.expandAll(false);	
				}
				if(result.indexPage!=null&&result.indexPage!=""){
					indexPageId = result.indexPage;
				}
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}
	
	//搜索 功能点
	function searchfunc(){
		var searchValue = $("input[id='searchText']").val();
		
		var data = "funcName="+searchValue;
		
		//加载数据
		loadTree("${ctx}/bione/logicSys/logic-sys!getFuncList.json",leftTreeObj,data);
	}
	
	//保存数据
	function f_admin_save(url,data){
		$.ajax({
			cache : false,
			async : true,
			url : url,
			data:data,
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
			success : function() {
				BIONE.closeDialog("logicMenuWin","保存成功！");
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}
	
	//设置首页
	function setIndexPage(){
		var nodes = rightTreeObj.getSelectedNodes();
		var isCanSetting = true;
		if(nodes.length !=1){
			isCanSetting = false;
		}
		if(rightTreeObj.getNodeByParam("id",nodes[0].upId)){
			isCanSetting = false;
		}
		if(isCanSetting){
			if(indexPageId!=""){
				var indexNode = rightTreeObj.getNodeByParam("id",indexPageId);
				$.ligerDialog.confirm('已有首页'+indexNode.text+',是否替换', function (yes) {
					if(yes){
						rightTreeObj.removeNode(indexNode);
						indexNode.text = indexNode.params.realName;
						nodes[0].text = '<font color="red">'+nodes[0].params.realName+'</font>';
						indexPageId = nodes[0].id;
						rightTreeObj.selectNode(nodes[0],false);
						rightTreeObj.addNodes(indexNode.getParentNode(),indexNode);
					}
				});
			}else{
				nodes[0].text = '<font color="red">'+nodes[0].params.realName+'</font>';
				indexPageId = nodes[0].id;
				rightTreeObj.selectNode(nodes[0],false);
			}
		}else{
			BIONE.tip("请选择功能根节点");
		}
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
	<div id="template.left.up">功能列表树</div>
	<div id="template.left.up.right">
		<input type='text' id="searchText" style="width:80%;">
		<a href='javascript:searchfunc()'><img src='${ctx}/images/classics/icons/find.png'></a>
	</div>
	<div id="template.right.up">逻辑系统功能</div>
</body>
</html>