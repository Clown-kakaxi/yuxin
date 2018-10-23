<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	//debugger;
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
				name : 'userId',
				type : 'hidden'
			}, {
				display : "登录名称<font color='red'>*</font>",
				name : "loginNameBox",
				newline : true,
				group : "用户信息",
				groupicon : groupicon,
				type : "select",
				options : {
					valueFieldID : "loginName",
					url : "${ctx}/ecif/alarm/txalarmuser/getComBoBox.json",
					ajaxType : "get"
				},
				validate : {
					required : true,
					maxlength : 40
				}
			}, {
				display : "用户名称<font color='red'>*</font>",
				name : "userName",
				newline : false,
				type : "text",
				validate : {
					required : true,
					maxlength : 40
				}
			}, {
				display : "用户称谓",
				name : "userTitle",
				newline : true,
				type : "text",
				validate : {
					maxlength : 20
				}
			}, {
				display : "用户描述",
				name : "userDesc",
				newline : false,
				type : "text",
				validate : {
					maxlength : 255
				}
			}, {
				display : "所属机构",
				name : "userBranch",
				newline : true,
				type : "text",
				validate : {
					maxlength : 20
				}
			}, {
				display : "所属部门",
				name : "userDept",
				newline : false,
				type : "text",
				validate : {
					maxlength : 20
				}
			}
		 ]
		});
		
		//debugger;
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/alarm/txalarmuser/${id}"});
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
		<form id="mainform" action="${ctx}/ecif/alarm/txalarmuser" method="post"></form>
	</div>
</body>
</html>