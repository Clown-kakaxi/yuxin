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
			fields : [ {
				name : 'groupId',
				type : 'hidden'
			    }, {
				display : "分组名称<font color='red'>*</font>",
				name : "groupName",
				newline : true,
				group : "授权资源信息",
				groupicon : groupicon,
				type : "text",
				validate : {
					required : true,
					maxlength : 40
				}
			}, {
				display : "分组描述",
				name : "groupDesc",
				newline : false,
				type : "text",
				validate : {
					maxlength : 255
				}
			}
		 ]
		});
		
		//debugger;
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/alarm/txalarmgroup/${id}"});
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
		<form id="mainform" action="${ctx}/ecif/alarm/txalarmgroup" method="post"></form>
	</div>
</body>
</html>