<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var tabId = '${tabId}';
	var colId = '${colId}';
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
				display : "字段编号",
				name : "colId",
				newline : true,
				type : "hidden",
				groupicon : groupicon
			}, {
				display : "表名",
				name : "tabId",
				newline : true,
				type : "hidden"
			}, {
				display : "字段名<font color='red'>*</font>",
				name : "colName",
				newline : true,
				type : "text",
				
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "字段中文描述<font color='red'>*</font>",
				name : "colChName",
				newline : true,
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "字段类型<font color='red'>*</font>",
				name : "dataType",
				newline : true,
				
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "长度<font color='red'>*</font>",
				name : "dataLen",
				newline : true,
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "精度",
				name : "dataPrec",
				newline : true,
				type : "text",
				validate : {
					maxlength : 50
				}
			}, {
				display : "可否为空",
				name : "nullsBox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'nulls',
					data : [{id : 'Y', text : 'Y'}, {id : 'N', text : 'N'}]
				},
				validate : {
					maxlength : 50
				}
			}, {
				display : "关键码类型",
				name : "keyType",
				newline : true,
				type : "text",
				validate : {
					maxlength : 50
				}
			}, {
				display : "是否转码",
				name : "isCodeBox",
				newline : true,
				type : "select",
				cssClass : "field",
				initValue : "1",
			    initText : "是",
				options : {
					valueFieldID : 'isCode',
					data : [{id : '1', text : '是'}, {id : '0', text : '否'}]
				},
				validate : {
					maxlength : 50
				}
			}, {
				display : "类别代码",
				name : "cateIdBox",
				newline : true,
				type : "select",
				options : {
					valueFieldID : "cateId",
					url : "${ctx}/ecif/core/coldef/getComBoBox.json",
					ajaxType : "get"
				},				
				validate : {
					maxlength : 50
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
					maxlength : 50
				}
			} ]
		});
		if("${colId}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/core/coldef/show?tabId="+tabId+"&colId="+colId});
		}
		
		$("#mainform input[name=tabId]").val(tabId);
		
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
		<form id="mainform" action="${ctx}/ecif/core/coldef" method="post"></form>
	</div>
</body>
</html>