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
				name: "srcSysCdName",
				display: "源系统<font color='red'>*</font>",
				newline: true,
				type : "select",
				options : {
					valueFieldID : "srcSysCd",
					url : "${ctx}/ecif/transaction/txclientauth/getComBoBox.json",
					ajaxType : "get"
				},				
				validate: {
					required : true,
					maxlength: 20
				}
			}, {
				display : "源代码<font color='red'>*</font>",
				name : "srcCode",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 20
				}
			}, {
				display : "源代码描述",
				name : "srcCodeDesc",
				newline: true,
				type : "text",
				validate : {
					maxlength : 200
				}
			}, {
				display : "分类代码<font color='red'>*</font>",
				name : "stdCate",
				newline: true,
				type : "text",
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "标准码",
				name : "stdCode",
				newline: true,
				type : "text",
				validate : {
					maxlength : 20
				}
			}, {
				display : "记录状态<font color='red'>*</font>",
				name : "stateBox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'state',
					data : [{id : '1', text : '有效'}, {id : '0', text : '无效'}]
				},
				validate : {
					required : true,
					maxlength : 10
				}
			
			} ]
		});
		if("${id}") {
			var idstr = "${id}";
			var params = idstr.split(",");
			sycSysCd = params[0];
			srcCode = params[1];
			stdCate = params[2];
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/core/txcodemap/show?sycSysCd="+sycSysCd+"&srcCode="+srcCode+"&stdCate="+stdCate});
			
			$("#mainform input[name=srcSysCdName]").attr("disabled", "true").css("color", "black").removeAttr("validate");
			$("#mainform input[name=srcCode]").attr("readOnly", "readOnly").css("color", "grey").removeAttr("validate");
			$("#mainform input[name=stdCate]").attr("readOnly", "readOnly").css("color", "grey").removeAttr("validate");
		
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
		<form id="mainform" action="${ctx}/ecif/core/txcodemap" method="post"></form>
	</div>
</body>
</html>