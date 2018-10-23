<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var userId = '${userId}';
	var txNameGloable = '';
	//alert(userId);
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
				display : "联系方式标识",
				name : "contmethId",
				newline : true,
				type : "hidden",
				groupicon : groupicon
			}, {
				display : "客户标识",
				name : "userId",
				newline : true,
				type : "hidden",
				groupicon : groupicon
			}, {
				display : "联系方式类型<font color='red'>*</font>",
				name : "contmethTypeBox",
				newline : true,
				type : "select",
				options : {
					valueFieldID : 'contmethType',
					data : [{id : '1', text : '邮件'}, {id : '0', text : '网页'}, {id : '2', text : '短信'}]
				},
				validate : {
					required : true,
					maxlength : 20
				}
			}, {
				display : "联系方式内容<font color='red'>*</font>",
				name : "contmethInfo",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 40
				}
			} ]
		});
		if("${id}"!="") {
			//alert("${id}");

			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/alarm/txalarmusercont/${id}"});
		}
		
		$("#mainform input[name=userId]").val(userId);
		
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
		<form id="mainform" action="${ctx}/ecif/alarm/txalarmusercont" method="post"></form>
	</div>
</body>
</html>