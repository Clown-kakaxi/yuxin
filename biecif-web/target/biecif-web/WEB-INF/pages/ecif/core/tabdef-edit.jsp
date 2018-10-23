<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
		/* 	labelWidth : 80,
			inputWidth : 160,
			space : 30, */
			fields : [ {
				name : 'tabId',
				type : 'hidden'
			}, {
				display : "表名<font color='red'>*</font>",
				name : "tabName",
				newline : false,
				type : "text",
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "表中文描述<font color='red'>*</font>",
				name : "tabDesc",
				type : "text",
				validate : {
					required : true,
					maxlength : 100
				}
			} ]
		});
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/core/tabdef/${id}"});
			$("#mainform input[name=resDefNo]").attr("readOnly", "readOnly").css("color", "black").removeAttr("validate");
		}
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
		<form id="mainform" action="${ctx}/ecif/core/tabdef" method="post"></form>
	</div>
</body>
</html>