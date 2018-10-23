<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var stdCate = '${stdCate}';
	//alert(clientAuthId);
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
				name   : "stdCate",
				type : "hidden"
	        },
			{
				display : "标准码<font color='red'>*</font>",
				name : "stdCode",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 20
				},
				groupicon : groupicon
			}, {
				display : "标准码描述<font color='red'>*</font>",
				name : "stdCodeDesc",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 200
				},
				groupicon : groupicon
			}, {
				display : "展示顺序",
				name : "orderId",
				newline : true,
				type : "text",				
				validate : {
					maxlength : 10,
					range : [1, 100000]
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

		
		if("${stdCode}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/core/txstdcode/show?stdCate=${stdCate}&stdCode=${stdCode}"});
			
		}
		
		$("#mainform input[name=stdCate]").val(stdCate);
		
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
		<form id="mainform" action="${ctx}/ecif/core/txstdcode" method="post"></form>
	</div>
</body>
</html>