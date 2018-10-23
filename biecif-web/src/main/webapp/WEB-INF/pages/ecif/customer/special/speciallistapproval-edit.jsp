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
		/* 	labelWidth : 80,
			inputWidth : 160,
			space : 30, */
			fields : [ {
				name : "ids",
				type : "hidden"
			}, {
				display : "审批状态<font color='red'>*</font>",
				name : "approvalStatBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "approvalStat",
					url : "${ctx}/ecif/customer/speciallistapproval/getComBoBox.json",
					ajaxType : "get"
				},
				validate : {
					required : true,
					messages : {
						required : "审批状态不能为空。"
					}
				}
			}, {
				display : "审批意见 ",
				name : "approvalNote",
				newline : true,
				type : "textarea",
				width : 475,
				validate : {
				    maxlength : 200
				}
		    }]
		});
		
		$("#mainform [name='ids']").val("${id}");
		
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick : cancleCallBack
		});
		buttons.push({
			text : '审批',
			onclick : validateUser
		});
		BIONE.addFormButtons(buttons);

		jQuery.metadata.setType("attr", "validate");
		BIONE.validate(mainform);
	});
	function save_test() {
		BIONE.submitForm($("#mainform"), 
			function() {
				BIONE.closeDialogAndReloadParent("testManage", "maingrid", "审批提交");
			}, 
			function() {
				BIONE.closeDialog("testManage", "审批失败");
			});
	}
	function cancleCallBack() {
		BIONE.closeDialog("testManage");
	}
	function validateUser(){
		var ids = $('#ids').val();
		if(ids != ""){
			var timestamp=new Date().getTime();
			$.ajax({
				url: '${ctx}/ecif/customer/speciallistapproval/validateuser?'+timestamp,
				async : false,
				type: 'get',
				data: {
					ids: ids
				},
				success: function (data){
					if(data != ""){
						alert(data);
					}else{
						save_test();
					}
				}
			});
		}else{
			alert("没有找到待审批信息");
		}
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/customer/speciallistapproval" method="post"></form>
	</div>
</body>
</html>