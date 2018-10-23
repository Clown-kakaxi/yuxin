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
		display : "上市地国家代码",
		name : "exchangeCountryCode",
		newline : true,
		type : "text",
		group : "客户财务信息",
		groupicon : groupicon
	}	, {
		display : "上市交易所代码",
		name : "exchangeCode",
		newline : false,
		type : "text"
	}
	, {
		display : "上市交易所名称",
		name : "exchangeName",
		newline : false,
		type : "text"
	}
	, {
		display : "上市地点",
		name : "marketPlace",
		newline : true,
		type : "text"
	}
	, {
		display : "上市日期",
		name : "ipoDate",
		newline : false,
		type : "text"
	}
	, {
		display : "股票类型",
		name : "stockType",
		newline : false,
		type : "text"
	}
	, {
		display : "股票种类",
		name : "stocktKind",
		newline : true,
		type : "text"
	}
	, {
		display : "股票代码",
		name : "stockCode",
		newline : false,
		type : "text"
	}
	, {
		display : "股票名称",
		name : "stockName",
		newline : false,
		type : "text"
	}
	, {
		display : "股东数目",
		name : "shareholderNum",
		newline : true,
		type : "text"
	}
	, {
		display : "流通股数",
		name : "flowStockNum",
		newline : false,
		type : "text"
	}
	, {
		display : "股本总量",
		name : "currStockNum",
		newline : false,
		type : "text"
	}
	, {
		display : "发行数量",
		name : "issueStockNum",
		newline : true,
		type : "text"
	}
	, {
		display : "发行价格",
		name : "issueStockPrice",
		newline : false,
		type : "text"
	}
	, {
		display : "每股净资产",
		name : "netassetPerShare",
		newline : false,
		type : "text"
	}
	, {
		display : "当年增发配股额",
		name : "allotmentShareAmt",
		newline : true,
		type : "text"
	}
	, {
		display : "最后更新系统",
		name : "lastUpdateSys",
		newline : false,
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
		newline : true,
		type : "text"
	}
	, {
		display : "交易流水号",
		name : "txSeqNo",
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
				url : "${ctx}/ecif/orgfinance/issuestock/${custId}"
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

