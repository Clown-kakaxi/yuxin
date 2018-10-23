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
				name : "testId",
				type : "hidden"
			}, {
				display : "名称<font color='red'>*</font>",
				name : "testName",
				newline : true,
				type : "text",
				group : "信息",
				groupicon : groupicon,
				validate : {
					required : true,
					maxlength : 100/*,
				 	remote: {
						url:"${ctx}/bione/admin/module/moduleNoValid"
					},
					messages:{
						remote : "名称重复"
					}*/
				}
			}, {
				display : "类型<font color='red'>*</font>",
				name : "testTypeBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "testType",
					url : "${ctx}/ecif/test/getComBoBox.json",
					ajaxType : "get"
				}
			}, {
				display : "标志<font color='red'>*</font>",
				name : "testFlagBox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'testFlag',
					data : [{id : '1', text : '启用'}, {id : '0', text : '停用'}]
				}
			}]
		});
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}//ecif/test/${id}.json"});
			$("#mainform input[name=testId]").attr("readOnly", "readOnly").css("color", "black").removeAttr("validate");
		}
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick : cancleCallBack
		});
		buttons.push({
			text : '保存',
			onclick : save_test
		});
		BIONE.addFormButtons(buttons);

		jQuery.metadata.setType("attr", "validate");
		BIONE.validate(mainform);
	});
	function save_test() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("testManage", "maingrid", "保存成功");
		}, function() {
			BIONE.closeDialog("testManage", "保存失败");
		});
	}

	function cancleCallBack() {
		BIONE.closeDialog("testManage");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/test" method="post"></form>
	</div>
</body>
</html>