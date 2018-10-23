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
				name : 'clientAuthId',
				type : 'hidden'
			    }, {
				display : "源系统代码<font color='red'>*</font>",
				name : "srcSysCdName",
				newline : true,
				group : "源系统信息",
				groupicon : groupicon,
				type : "select",
				options : {
					valueFieldID : "srcSysCd",
					url : "${ctx}/ecif/transaction/txclientauth/getComBoBox.json",
					ajaxType : "get"
				},
				validate : {
					required : true,
					maxlength : 32,
				    remote : {
				    	
						url : "${ctx}/ecif/transaction/txclientauth/resDefNoValid?id=${id}&srcSysCdVal="+$("srcSysCdName").val(),
						type : "POST"
					   },
					messages : {
						remote : "源系统代码重复"
					}
				}
			}, {
				display : "源系统凭证<font color='red'>*</font>",
				name : "username",
				newline : false,
				type : "text",
				validate : {
					required : true,
					maxlength : 32
				}
			}, {
				display : "源系统密码<font color='red'>*</font>",
				name : "password",
				newline : true,
				type : "password",
				validate : {
					required : true,
					maxlength : 128
				}
			}, {
				display : "重复密码<font color='red'>*</font>",
				name : "encPwd",
				newline : false,
				type : "password",
				validate : {
					required : true,
					maxlength : 128
				}
			}, {
				display : "IP地址",
				name : "ipaddr",
				newline : true,
				type : "text",
				validate : {
					required : false,
					maxlength : 64
				}
			}, {
				display : "IPv6地址",
				name : "ipv6addr",
				newline : false,
				type : "text",
				validate : {
					required : false,
					maxlength : 64
				}
			}, {
				display : "物理地址",
				name : "macaddr",
				newline : true,
				type : "text",
				validate : {
					required : false,
					maxlength : 64
				}
			/**
			}, {
				display : "开始日期",
				name : "startDt",
				newline : false,
				type : "text",
				validate : {
					required : false,
					maxlength : 50
				}
			}, {
				display : "结束日期",
				name : "endDt",
				newline : true,
				type : "text",
				validate : {
					required : false,
					maxlength : 50
				}
			**/
			}, {
				display : "有效性<font color='red'>*</font>",
				name : "flagbox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'flag',
					data : [{id : '1', text : '有效'}, {id : '0', text : '无效'}]
				},
				validate : {
					required : true,
					maxlength : 50
				}
			} ]
		});
		
		//debugger;
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/transaction/txclientauth/${id}"});
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
		<form id="mainform" action="${ctx}/ecif/transaction/txclientauth" method="post"></form>
	</div>
</body>
</html>