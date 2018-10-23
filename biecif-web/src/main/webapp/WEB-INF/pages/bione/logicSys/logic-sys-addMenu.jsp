<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
var upId ="${upId}";
var mainform;
var isCheck = "true";
$(function(){

	var groupicon = "${ctx}/images/classics/icons/communication.gif";

	mainform = $("#mainform").ligerForm({
		align:'center',
		fields : [{
			display : "菜单名",
			name : "menuName",
			newline : true,
			type : "text",
			group : "菜单信息",
			groupicon : groupicon
		},{
			display:"所属节点",
			name:"upId",
			newline:true,
			type:"text",
			initValue:"${upId}"
		}]
	
	});
	
	var buttons = [];
	buttons.push({
		text : '取消',
		onclick:f_close
	});
	buttons.push({
		text : '保存',
		onclick : f_save
	});
	
	BIONE.addFormButtons(buttons);
	
	
	jQuery.metadata.setType("attr", "validate");
	BIONE.validate("#mainform");
	
});

function f_save() {
	
	BIONE.submitForm($("#mainform"), function() {
		BIONE.closeDialog("metaPackageWin","保存成功");
	}, function() {
		BIONE.closeDialog("metaPackageWin","保存失败");
	});
}
function f_close(){
	BIONE.closeDialog("metaPackageWin");
}
</script>
</head>
<body>
<div id="template.center">
	<form id="mainform" action="${ctx}/metadata/model/meta-package" method="post">
	</form>
</div>
</body>
</html>