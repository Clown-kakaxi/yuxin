<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template14.jsp">
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var mainform;
	var id="${custId}";
    var field = [ {
		name : "custId",
		type : "hidden"
	},{
		display : "核查状态",
		name : "verifyStat",
		newline : true,
		type : "text",
		group : "联网核查信息",
		groupicon : groupicon
	}, {
		display : "核查结果",
		name : "verifyResult",
		newline : false,
		type : "text"
	}, {
		display : "无法核查原因",
		name : "reason",
		newline : false,
		type : "text"
	}, {
		display : "处置方法",
		name : "dealway",
		newline : true,
		type : "text"
	}, {
		display : "核查机构",
		name : "verifyBrc",
		newline : false,
		type : "text"
	}, {
		display : "核查柜员",
		name : "verifyTeller",
		newline : false,
		type : "text"
	}, {
		display : "核查日期",
		name : "verifyDate",
		newline : true,
		type : "text"
	}
     ];
	//创建表单结构 
	function ligerFormNow() {
		mainform = $("#mainform").ligerForm({
		    inputWidth : 150,
		    labelWidth : 100,
		    space : 30,
		    fields : field
		});
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
	}
	$(function(){
		ligerFormNow();
		if ("${custId}") {
			BIONE.loadForm(mainform, {
				url : "${ctx}/ecif/perbasic/identityverify/${custId}"
			});
			$(":input", $("#mainform")).not(":submit, :reset, :image,:button, [disabled]")
			.each(
				function() {
					$(this).attr("readonly","readonly");
					$(this).css({color:"#000000"});
				}
			);
		}
	});
	

</script>

<title>联网核查信息</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perbasic">
	</form>
</div>
</body>
</html>