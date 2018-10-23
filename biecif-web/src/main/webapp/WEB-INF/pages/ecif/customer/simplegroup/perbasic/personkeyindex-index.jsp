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
		display : "个人年收入",
		name : "annualIncome",
		newline : true,
		type : "text",
		group : "个人客户重要指标",
		groupicon : groupicon
	}, {
		display : "个人月工资收入",
		name : "monthSalaryIncome",
		newline : false,
		type : "text"
	}, {
		display : "个人月经营收入",
		name : "monthBusiIncome",
		newline : true,
		type : "text"
	}, {
		display : "个人月其他收入",
		name : "monthOtherIncome",
		newline : false,
		type : "text"
	}, {
		display : "个人月均收入（税后）",
		name : "monthAvgIncome",
		newline : true,
		type : "text"
	}, {
		display : "个人金融资产",
		name : "financialAssets",
		newline : false,
		type : "text"
	}, {
		display : "个人本行存款余额",
		name : "depositBal",
		newline : true,
		type : "text"
	}, {
		display : "个人本行国债余额",
		name : "nationalDebtBal",
		newline : false,
		type : "text"
	}, {
		display : "个人本行理财产品余额",
		name : "financialProdBal",
		newline : true,
		type : "text"
	}, {
		display : "个人本行贷款余额",
		name : "loanBal",
		newline : false,
		type : "text"
	}, {
		display : "个人本行资产总额",
		name : "assets",
		newline : true,
		type : "text"
	}, {
		display : "个人非本行总资产",
		name : "otherBankAssets",
		newline : false,
		type : "text"
	}, {
		display : "个人非本行总负债",
		name : "otherBankDebt",
		newline : true,
		type : "text"
	}
     ];
	//创建表单结构 
	function ligerFormNow() {
		mainform = $("#mainform").ligerForm({
		    inputWidth : 180,
		    labelWidth : 135,
		    space : 50,
		    fields : field
		});
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
	}
	$(function(){
		ligerFormNow();
		if ("${custId}") {
			BIONE.loadForm(mainform, {
				url : "${ctx}/ecif/perbasic/personkeyindex/${custId}"
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

<title>个人客户重要指标</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perbasic">
	</form>
</div>
</body>
</html>