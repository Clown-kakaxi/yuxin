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
		display : "法人客户员工数",
		name : "employeeNum",
		newline : true,
		type : "text",
		group : "机构客户重要指标",
		groupicon : groupicon
	}, {
		display : "总资产",
		name : "totalAssets",
		newline : false,
		type : "text"
	}, {
		display : "净资产",
		name : "netAssets",
		newline : false,
		type : "text"
	}, {
		display : "年收入",
		name : "annualIncome",
		newline : true,
		type : "text"
	}, {
		display : "营业收入",
		name : "busiIncome",
		newline : false,
		type : "text"
	}
     ];
	//创建表单结构 
	function ligerFormNow() {
		mainform = $("#mainform").ligerForm({
		    inputWidth : 150,
		    labelWidth : 110,
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
				url : "${ctx}/ecif/orgbasic/orgkeyindex/${custId}"
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

<title>机构客户重要指标</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/orgbasic">
	</form>
</div>
</body>
</html>