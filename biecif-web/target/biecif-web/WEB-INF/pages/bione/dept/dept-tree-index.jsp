<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template12.jsp">
<script type="text/javascript">
	var url, deptTreeObj, treeNode_;
	$(function() {
 		url = "${ctx}/bione/admin/depttree/list.json?orgNo=${id}";
		searchForm();
		beforeTree();
		initTree();
		selectButton();
	});
	
	//搜索表单应用ligerui样式
	function searchForm() {
	 	$("#search").ligerForm({
			fields : [ {
				display : "部门标识",
				name : "deptNo",
				newline : true,
				labelWidth : 60,
				width : 120,
				space : 30,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "dept.deptNo"
				}
			}, {
				display : "部门名称",
				name : "deptName",
				newline : false,
				labelWidth : 60,
				width : 120,
				space : 30,
				type : "text",
				cssClass : "field",
				attr : {
					"op" : "like",
					field : "dept.deptName"
				}
			} ]
		});
	 	BIONE.createButton({
			text : '搜索',
			width : '50px',
			appendTo : '#searchbtn',
			operNo : 'user_query1',
			click : function() {
				refreshTree();
			}
		});
	}
	
	function refreshTree() {
		var rootTmp = deptTreeObj.getNodeByParam("id", "0", null);
		if (rootTmp) {
			deptTreeObj.removeNode(rootTmp);
		}
		initTree();
	}
	
	function beforeTree() {
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
				callback : {
					beforeClick : beforeClick,
					onClick : onClick
				}
		};
		deptTreeObj = $.fn.zTree.init($("#tree"), setting);
	}
	
	function initTree() {
		var rule = BIONE.bulidFilterGroup($("#search"));
		$.ajax({
			cache : false,
			async : false,
			url : url,
			type : "post",
			data : {condition : JSON2.stringify(rule)},
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("正在加载数据中...");
			},
			complete : function() {
				BIONE.loading = false;
				BIONE.hideLoading();
			},
			success : function(result) {
				if(result) {
					for(var i in result) {
						if(result[i].params.open) {
							result[i].open = true;
						} else {
							result[i].open = false;
						}
					}
				}
				deptTreeObj.addNodes(null, result, false);
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}
	
	function selectButton() {
		var buttons = [];
		buttons.push({
			text : '选择',
			onclick : selected
		});
		BIONE.addFormButtons(buttons);
	}
	
	function beforeClick(treeId, treeNode, clickFlag) {
		if(treeNode.id == "0") {
			BIONE.tip("该节点无效!");
			return false;
		}
	}
	
	function onClick(event, treeId, treeNode, clickFlag) {
		treeNode_ = treeNode;
	}
 	function selected() {
 		if(!treeNode_) {
 			BIONE.tip("请选择部门");
 			return;
 		}
		var dialogName = 'deptComBoBox';
		var main = parent || window;
		var dialog = main.jQuery.ligerui.get(dialogName);
		var c = main.jQuery.ligerui.get("deptNo_select");
		c._changeValue(treeNode_.id, treeNode_.text);
		dialog.close();
	}
</script>
</head>
<body>
</body>
</html>