<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template14.jsp">
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var mainform;
    var field = [ {
		display : "投资目的",
		name : "investAim",
		newline : true,
		type : "text",
		group : "客户财务信息",
		groupicon : groupicon
	}	, {
		display : "投资预期",
		name : "investExpect",
		newline : false,
		type : "text"
	}
	, {
		display : "投资种类",
		name : "investType",
		newline : false,
		type : "text"
	}
	, {
		display : "投资金额",
		name : "investAmt",
		newline : true,
		type : "text"
	}
	, {
		display : "投资币种",
		name : "investCurr",
		newline : false,
		type : "text"
	}
	, {
		display : "投资收益率",
		name : "investYield",
		newline : false,
		type : "text"
	}
	, {
		display : "投资收益",
		name : "investIncome",
		newline : true,
		type : "text"
	}
	, {
		display : "开始日期",
		name : "startDate",
		newline : false,
		type : "text"
	}
	, {
		display : "结束日期",
		name : "endDate",
		newline : false,
		type : "text"
	}
	, {
		display : "最后更新系统",
		name : "lastUpdateSys",
		newline : true,
		type : "text"
	}
	, {
		display : "最后更新人",
		name : "lastUpdateUser",
		newline : false,
		type : "text"
	}
	, {
		display : "最后更新时间",
		name : "lastUpdateTm",
		newline : false,
		type : "text"
	}
	, {
		display : "交易流水号",
		name : "txSeqNo",
		newline : true,
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
				url : "${ctx}/ecif/perfinance/personinvestment/${custId}"
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
<title>客户财务信息</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perfinance">
	</form>
</div>
</body>
</html>

