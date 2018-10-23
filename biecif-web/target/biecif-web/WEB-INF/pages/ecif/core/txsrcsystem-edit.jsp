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
				display : "源系统代码<font color='red'>*</font>",
				name : "srcSysCd",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 20,
				    remote : {
						url : "${ctx}/ecif/core/txsrcsystem/resDefNoValid?id=${id}",
						type : "POST"
					   },
					messages : {
						remote : "源系统代码重复"
					}
				}
			}, {
				display : "源系统简称<font color='red'>*</font>",
				name : "srcSysNm",
				newline : false,
				type : "text",
				validate : {
					required : true,
					maxlength : 20
				}
			}, {
				display : "ESB简称",
				name : "esbSysNm",
				newline : true,
				type : "text",
				validate : {
					required : false,
					maxlength : 20
				}
			}, {
				display : "EDW简称",
				name : "edwSysNm",
				newline : false,
				type : "text",
				validate : {
					required : false,
					maxlength : 20
				}
			}, {
				display : "源系统名称",
				name : "srcSysName",
				newline : true,
				type : "text",
				validate : {
					required : false,
					maxlength : 30
				}
			}, {
				display : "源系统描述",
				name : "srcSysDesc",
				newline : false,
				type : "text",
				validate : {
					required : false,
					maxlength : 80
				}
			}, {
				display : "记录状态<font color='red'>*</font>",
				name : "flagbox",
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
		
		//debugger;
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/core/txsrcsystem/${id}"});
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
		<form id="mainform" action="${ctx}/ecif/core/txsrcsystem" method="post"></form>
	</div>
</body>
</html>