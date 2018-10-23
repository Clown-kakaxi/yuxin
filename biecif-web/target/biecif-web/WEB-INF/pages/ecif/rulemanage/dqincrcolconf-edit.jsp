<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var tid = '${tid}';
	//alert(tid);

	var txNameGloable = '';
	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
		 	labelWidth : 140,
			inputWidth : 300,
			space : 60, 
			fields : [ 
			 {
				display : "TID",
				name : "tid",
				newline : false,
				type : "hidden"
			}, {
				display : "目标表字段名<font color='red'>*</font>",
				name : "dstCol",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 64			
				}
			}, {
				display : "源表字段名或常量<font color='red'>*</font>",
				name : "srcCol",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 256			
				}
			}, {
				display : "源表字段常量变量标识<font color='red'>*</font>",
				name : "insOnl",
				newline : true,
				type : "text"
			}, {
				display : "系统优先级",
				name : "sys",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 256			
				}
			} ]
		});
		if("${dstCol}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/rulemanage/dqincrcolconf/show?tid=${tid}&dstCol=${dstCol}"});
		}
		$("#mainform input[name=tid]").val(tid);
		
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
			BIONE.closeDialogAndReloadParent("dqincrcolconfManage","maingrid" , "保存成功");
		}, function() {
			BIONE.closeDialog("resDefManage", "保存失败");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("dqincrcolconfManage");
	}
	
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/rulemanage/dqincrcolconf" method="post"></form>
	</div>
</body>
</html>