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
		display : "报表编号",
		name : "reportNo",
		newline : true,
		type : "text",
		group : "客户财务信息",
		groupicon : groupicon
	}	, {
		display : "报表种类",
		name : "reportKind",
		newline : false,
		type : "text"
	}
	, {
		display : "会计准则类型",
		name : "accountingRule",
		newline : false,
		type : "text"
	}
	, {
		display : "报表类型",
		name : "reportType",
		newline : true,
		type : "text"
	}
	, {
		display : "报表金额单位",
		name : "reportUnit",
		newline : false,
		type : "text"
	}
	, {
		display : "财务信息细项名称",
		name : "reportDetail",
		newline : false,
		type : "text"
	}
	, {
		display : "财务信息细项金额",
		name : "reportDetailAmt",
		newline : true,
		type : "text"
	}
	, {
		display : "资产规模情况",
		name : "assetsCondition",
		newline : false,
		type : "text"
	}
	, {
		display : "资本充足情况",
		name : "capitalCondition",
		newline : false,
		type : "text"
	}
	, {
		display : "盈利能力情况",
		name : "profitAbility",
		newline : true,
		type : "text"
	}
	, {
		display : "利润监管指标",
		name : "profitIndex",
		newline : false,
		type : "text"
	}
	, {
		display : "信贷资产质量情况",
		name : "loanQuality",
		newline : false,
		type : "text"
	}
	, {
		display : "风险情况监管指标",
		name : "riskMonitorIndex",
		newline : true,
		type : "text"
	}
	, {
		display : "流动性情况监管指标",
		name : "liquidityMonitorIndex",
		newline : false,
		type : "text"
	}
	, {
		display : "资产总额",
		name : "totalAssets",
		newline : false,
		type : "text"
	}
	, {
		display : "负债总额",
		name : "totalDebt",
		newline : true,
		type : "text"
	}
	, {
		display : "审计日期",
		name : "auditDate",
		newline : false,
		type : "text"
	}
	, {
		display : "审计单位",
		name : "auditOffice",
		newline : false,
		type : "text"
	}
	, {
		display : "审计意见",
		name : "auditOpinion",
		newline : true,
		type : "text"
	}
	, {
		display : "审计标志",
		name : "auditFlag",
		newline : false,
		type : "text"
	}
	, {
		display : "是否有保留意见",
		name : "hasReserveOpt",
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
				url : "${ctx}/ecif/orgfinance/financebriefreport/${custId}"
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
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/orgfinance">
	</form>
</div>
</body>
</html>

