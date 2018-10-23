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
			labelWidth : 100,
			inputWidth : 140,
			space : 80, 
			fields : [ 
				{   display : "服务标识<font color='red'>*</font>",
					name : "serviceID",
					newline : true,
					group : "服务配置信息",
					groupicon : groupicon,
					type : "hidden"
					
				},{
				display : "节点名<font color='red'>*</font>",
				name : "nodeID",
				newline : true,
				groupicon : groupicon,
				type : "hidden",
				value : 1000,
				validate : {
					required : true,
					maxlength : 128
				}
			}, {
				display : "主机名<font color='red'>*</font>",
				name : "hostName",
				newline : false,
				type : "text",
				validate : {
					required : true,
					maxlength : 128
				}
			}, {
				display : "IP地址<font color='red'>*</font>",
				name : "ipAddr",
				newline : false,
				type : "text",
				validate : {
					required : true,
					//ip:true,
					maxlength : 128
				}
			}, {
				display : "服务名<font color='red'>*</font>",
				name : "serviceName",
				newline : true,
				type : "test",
				validate : {
					required : true,
					maxlength : 128
				}
			}, {
				display : "实例名<font color='red'>*</font>",
				name : "instName",
				newline : true,
				type : "hidden",
				validate : {
					required : false,
					maxlength : 128
				}
			}, {
				display : "端口号",
				name : "servicePort",
				newline : false,
				type : "text",
				validate : {
					required : false,
					maxlength : 10,
					range : [1, 65535]
				}
			}, {
				display : "服务状态",
				name : "serviceStart",
				newline : false,
				type : "hidden",
				validate : {
					required : false,
					maxlength : 50
				}
			}, {
				display : "进程id",
				name : "processID",
				newline : false,
				type : "hidden",
				validate : {
					required : false,
					maxlength : 50
				}
			} ]
		});
		
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/serviceinfo/${id}"});
			$("#mainform input[name=serviceID]").attr("readOnly", "readOnly").css("color", "black").removeAttr("validate");
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
			onclick : save_dqincrtabconf
		});
		BIONE.addFormButtons(buttons);

	});
	function save_dqincrtabconf() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("serviceinfoManage","maingrid" , "保存成功");
		}, function() {
			BIONE.closeDialog("serviceinfoManage", "保存失败");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("serviceinfoManage");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/serviceinfo" method="post"></form>
	</div>
</body>
</html>