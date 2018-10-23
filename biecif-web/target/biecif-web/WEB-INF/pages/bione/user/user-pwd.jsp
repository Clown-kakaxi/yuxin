<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
		/* 	inputWidth : 190,
			labelWidth : 100,
			space : 30, */
			fields : [ {
				name : "userId",
				type : "hidden"
			}, {
				display : "请输入新密码",
				name : "userPwd_1",
				newline : true,
				type : "password",
				group : "修改密码",
				groupicon : groupicon,
				validate : {
					required : true,
					maxlength : 100
				}
			}, {
				display : "确认密码",
				name : "userPwd_2",
				newline : true,
				type : "password",
				validate : {
					required : true,
					maxlength : 100,
					equalTo : "#userPwd_1"
				}
			} ]
		});
		if ("${id}") {
			BIONE.loadForm(mainform, {
				url : "${ctx}/bione/admin/user/${id}"
			});
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
			onclick : save_user
		});
		BIONE.addFormButtons(buttons);
		//客户 在弹出窗口中选择
	});
	
	// 保存提交方法
	function save_user() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("userManage", "maingrid", "保存成功");
		}, function() {
			BIONE.closeDialog("userManage", "保存失败");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("userManage");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/bione/admin/user/updatePwd"
			method="post"></form>
	</div>
</body>
</html>