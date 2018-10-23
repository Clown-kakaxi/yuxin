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
				name : "moduleId",
				type : "hidden"
			}, {
				display : "模块标识<font color='red'>*</font>",
				name : "moduleNo",
				newline : true,
				type : "text",
				group : "模块信息",
				groupicon : groupicon,
				validate : {
					required : true,
					maxlength : 32,
				 	remote: {
						url:"${ctx}/bione/admin/module/moduleNoValid"
					},
					messages:{
						remote : "模块标识重复"
					}
				}
			}, {
				display : "模块名称<font color='red'>*</font>",
				name : "moduleName",
				newline : false,
				type : "text",
				validate : {
					required : true,
					maxlength : 50
				}
			},
			{
				display : "备注",
				name : "remark",
				newline : true,
				type : "textarea",
				width : 503,
				attr : {
					style : "resize: none;"
				},
				validate : {
					maxlength : 250
				}
			} ]
		});
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/bione/admin/module/${id}.json"});
			$("#mainform input[name=moduleNo]").attr("readOnly", "readOnly").css("color", "black").removeAttr("validate");
		}
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick : cancleCallBack
		});
		buttons.push({
			text : '保存',
			onclick : save_module
		});
		BIONE.addFormButtons(buttons);

		jQuery.metadata.setType("attr", "validate");
		BIONE.validate(mainform);
	});
	function save_module() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("moduleUpdate", "maingrid", "保存成功");
		}, function() {
			BIONE.closeDialog("moduleUpdate", "保存失败");
		});
	}

	function cancleCallBack() {
		BIONE.closeDialog("moduleUpdate");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/bione/admin/module" method="post"></form>
	</div>
</body>
</html>