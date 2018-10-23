<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
var mainform;
var grpId = "${id}";
var buttons = [];
$(function(){
	initForm();
	initBtns();
});
function initForm(){
	mainform = $("#mainform").ligerForm({
		align:'center',
		inputWidth : 180,
		labelWidth : 100,
		space : 30,
		fields : [{
			name : "grpId",
			type:"hidden"
		}, {
			display : "分组名称",
			name : "grpName",
			newline : true,
			type : "text",
			group : "分组信息 ",
			groupicon : "${ctx}/images/classics/icons/communication.gif",
			validate : {
				required : true,
				maxlength : 32,
				remote : {
					url : "${ctx}/bione/admin/userattrgrp/checkGrpName",
					type : 'post',
					async : true,
					data : {
						grpId : grpId
					}
				},
				messages : {
					remote : "分组名称已存在"
				}
			}
		}, {
			display : "分组图标",
			name : "grpIcon",
			newline : false,
			type : "select",
			comboboxName : "IconBoxID",
			options : {
				valueFieldID : "grpIcon",
				url : "${ctx}/bione/module/func/buildIconCombox.json",
				ajaxType:"get"
			},
			validate : {
				required : false
			}
		},{
			display : '顺序编号',
			name : 'orderNo',
			type : 'number',
			newline: true,
			validate : {
				digits : true,
				maxlength : 5
			}
		}]
	});
	jQuery.metadata.setType("attr", "validate");
	BIONE.validate("#mainform");
	$.ligerui.get("IconBoxID").openSelect({
		url : '${ctx}/bione/admin/func/selectIcon.html',
		dialogname:'iconselector',
		title:'选择图标',
		comboxName:'IconBoxID',
		dialogType:'icon'
	});
	BIONE.loadForm($("mainform"), {
		url : "${ctx}/bione/admin/userattrgrp/info.json",
		data : {
			id : "${id}"
		}
	});
}
function initBtns() {
	buttons.push({
		text : '取消',
		onclick : f_close
	});
	buttons.push({
		text : '保存',
		onclick : f_save
	});
	BIONE.addFormButtons(buttons);
}

function f_close(){
	BIONE.closeDialog("addGrp");
}
function f_save(){
	BIONE.submitForm($("#mainform"), function() {
		window.parent.BIONE.tip("保存成功");
		window.parent.initTreeNodes();
		BIONE.closeDialog("addGrp");
	}, function() {
		window.parent.BIONE.tip("保存失败");
	});
}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/bione/admin/userattrgrp" method="post"></form>
	</div>
</body>
</html>