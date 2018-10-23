<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var groupId = '${groupId}';
	var txNameGloable = '';
	//alert(groupId);
	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
		/* 	labelWidth : 80,
			inputWidth : 160,
			space : 30, */
			fields : [ 
			{
				display : "标识",
				name : "relId",
				newline : true,
				type : "hidden",
				groupicon : groupicon
			}, {
				display : "组标识",
				name : "groupId",
				newline : true,
				type : "hidden",
				groupicon : groupicon
			}, {
				display : "用户标识",
				name : "userIdBox",
				newline : true,
				type : "select",
				options : {
					valueFieldID : "userId",
					url : "${ctx}/ecif/alarm/txalarmusergrouprel/getComBoBox.json",
					ajaxType : "get"
				},

				validate : {
					required : true,
					maxlength : 40
				}
			} ]
		});
		if("${id}") {
			//alert("${id}");

			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/alarm/txalarmusergrouprel/${id}"});
		}
		
		$("#mainform input[name=groupId]").val(groupId);
		
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
			BIONE.closeDialogAndReloadParent("resDefManage","maingrid" , "保存成功");
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
		<form id="mainform" action="${ctx}/ecif/alarm/txalarmusergrouprel" method="post"></form>
	</div>
</body>
</html>