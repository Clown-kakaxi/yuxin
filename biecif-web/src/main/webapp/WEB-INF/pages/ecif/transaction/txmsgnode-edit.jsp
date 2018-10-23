<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var msgId = '${msgId}';
	var upNodeId = '${upNodeId}';

	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
		/* 	labelWidth : 80,
			inputWidth : 160,
			space : 30, */
	        fields: [
		 	            {name : "nodeId", type : "hidden"},
		 	            {name : "msgId", type : "hidden"},
		 	            {name : "upNodeId", type : "hidden"},
		 	            { display: "节点代码", name: "nodeCode", newline: true, type: "text", group: "节点信息", groupicon: groupicon, 
		 	            	validate : {
		 						required : true,
		 						maxlength : 50
		 					} 
		 	            }, 
		 	            { display: "节点名称", name: "nodeNamexx", newline: false, type: "text", 
		 	            	validate : {
		 						required : true,
		 						maxlength : 40 
		 					} 	
		 	            },
		 	            
		 	            { display: "节点类型 ", name: "nodeTpBox", newline: true,
		 					type : "select",
		 					options : {
		 						valueFieldID : 'nodeTp',
		 						data : [{id : 'C', text : '普通'}, {id : 'T', text : '基于数据库表'}]
		 					},
		 	            	validate : {
		 						required : true,
		 						maxlength : 32
		 					}	
		 	            },
		 	            { display: "是否节点组", name: "nodeGroupBox", newline: false, 
		 	            	type: "select", 
		 					options : {
		 						valueFieldID : 'nodeGroup',
		 						data : [{id : '0', text : '否'}, {id : '1', text : '是'}]
		 					},	 	            	
		 					validate : {
		 						maxlength : 50
		 					} 	
		 	            },
		 	            { display: "是否带节点组标签", name: "nodeLabelBox", newline: true, 
		 	            	type: "select", 
		 					options : {
		 						valueFieldID : 'nodeLabel',
		 						data : [{id : '0', text : '否'}, {id : '1', text : '是'}]
		 					},	 	            	
		 					validate : {
		 						maxlength : 100
		 					} 	
		 	            },
		 	            { display: "是否 显示节点标签", name: "nodeDisplayBox", newline: true, 
		 	            	type: "select", 
		 					options : {
		 						valueFieldID : 'nodeDisplay',
		 						data : [{id : '0', text : '否'}, {id : '1', text : '是'}]
		 					},	 	            	
		 					validate : {
		 						maxlength : 100
		 					} 	
		 	            }
		 	        ]
		});
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/transaction/txmsgnode/${id}"});
		}
		
		$("#mainform input[name=msgId]").val(msgId);
		$("#mainform input[name=upNodeId]").val(upNodeId);

		jQuery.metadata.setType("attr", "validate");
		BIONE.validate(mainform);
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick : cancleCallBack
		});
		buttons.push({
			text : '保存',
			onclick : save_resDef
		});
		BIONE.addFormButtons(buttons);

	});
	function save_resDef() {
		BIONE.submitForm($("#mainform"), function() {
			//BIONE.closeDialogAndReloadParent("resDefManage","maingrid" , "保存成功");
		
			var main = parent || window;
			var dialog = main.jQuery.ligerui.get("resDefManage");
			dialog.close();
			//main.jQuery.ligerui.get(gridName).loadData();
			//main.currentNode = null;
			main.initTree();
			
		}, function() {
			BIONE.closeDialog("resDefManage", "保存失败");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("resDefManage");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/transaction/txmsgnode" method="post"></form>
	</div>
</body>
</html>