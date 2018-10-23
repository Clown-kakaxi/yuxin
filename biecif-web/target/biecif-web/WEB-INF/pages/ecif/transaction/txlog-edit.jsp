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
		 	labelWidth : 80,
			inputWidth : 560,
			space : 30, 
			fields : [ {
				name : 'txLogId',
				type : 'hidden'
			}, {
				display : "交易流水号",
				name : "txFwId",
				newline : false,
				type : "text",
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "请求报文",
				name : "reqMsg",
				type : "textarea",
				attr : {
					style : "height: 150px;"
				}
			}, {
				display : "响应报文",
				name : "resMsg",
				type : "textarea",
				attr : {
					style : "height: 150px;"
				}
			} ]
		});
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/transaction/txlog/${id}"});
			$("#mainform input[name=resDefNo]").attr("readOnly", "readOnly").css("color", "black").removeAttr("validate");
		}
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate(mainform);
		var buttons = [];
		buttons.push({
			text : '关闭',
			onclick : cancleCallBack
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
		BIONE.closeDialog("authResWin");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/transaction/txlog" method="post"></form>
	</div>
</body>
</html>