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
			name : "asd",
			type : "hidden"
			
		}, {
			display : "审批状态",
			name : "approvalStatBox",
			newline : true,
			type : "select",
			cssClass : "field",
			options : {
				valueFieldID : 'approvalStat',
				data : [{id : '02', text : '审批通过'}, {id : '03', text : '审批未通过'}]
			},
			validate : {
				required : true,
				messages : {
					required : "审批状态不能为空。"
				}
			}
		}, {
			display : "审批意见",
			name : "approvalNote",
			newline : true,
			type : "textarea",
			width : 475,
			validate : {
			    maxlength : 200
			}
		}]
	});

	$("#mainform input[name=asd]").val("${custrelApprovalId}");
	
	var buttons = [];
	buttons.push({
		text : '取消',
		onclick : cancleCallBack
	});
	buttons.push({
		text : '保存',
// 		onclick : save_test
		onclick : isSameUser
	});
	BIONE.addFormButtons(buttons);

	jQuery.metadata.setType("attr", "validate");
	BIONE.validate(mainform);
});

function save_test() {
	
	BIONE.submitForm($("#mainform"), function() {
		BIONE.closeDialogAndReloadParent("testManage", "maingrid", "审批提交");
	}, function() {
		BIONE.closeDialog("testManage", "审批失败");
	});
}

function isSameUser(){
	var ids = $("#mainform input[name=asd]").val();
	
	var url='${ctx}/ecif/custrelapproval/isSameUser.json?ids='+ids;
	$.get(url, {}, function(str, status) {
// 		alert("str"+str);
		if('' != str){
			alert("此用户的操作者和审批者为同一用户");
		}else{
			save_test();
		}
	});
}

function cancleCallBack() {
	BIONE.closeDialog("testManage");
}

</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/custrelapproval" method="post"></form>
	</div>
</body>
</html>