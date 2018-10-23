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
		display : "投资/理财种类",
		name : "investment",
		newline : true,
		type : "text",
		group : "个人投资理财信息",
		groupicon : groupicon
	},{
		display : "投资目的",
		name : "investaim",
		newline : false,
		type : "text",
		groupicon : groupicon
	}, {
		display : "投资预期",
		name : "investexpect",
		newline : true,
		type : "text"
	}, {
		display : "是否持有股票",
		name : "stockflag",
		newline : false,
		type : "text"
	}, {
		display : "是否持有基金",
		name : "fundflag",
		newline : true,
		type : "text"
	}, {
		display : "是否购买过我行理财产品",
		name : "financeflag",
		newline : false,
		type : "text"
	}
     ];
	//创建表单结构 
	function ligerFormNow() {
		mainform = $("#mainform").ligerForm({
		    inputWidth : 180,
		    labelWidth : 150,
		    space : 60,
		    fields : field
		});
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
	}
	$(function(){
		ligerFormNow();
		if ("${custId}") {
			BIONE.loadForm(mainform, {
				url : "${ctx}/ecif/perfinance/perinvestment/${custId}"
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

<title>个人投资理财信息</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perfinance">
	</form>
</div>
</body>
</html>