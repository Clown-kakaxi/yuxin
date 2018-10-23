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
		display : "债券种类",
		name : "bondKind",
		newline : true,
		type : "text",
		group : "客户财务信息",
		groupicon : groupicon
	}	, {
		display : "债券评级机构",
		name : "evalBank",
		newline : false,
		type : "text"
	}
	, {
		display : "债券发行金额",
		name : "issueAmt",
		newline : false,
		type : "text"
	}
	, {
		display : "发行日期",
		name : "issueDate",
		newline : true,
		type : "text"
	}
	, {
		display : "债券类型",
		name : "bondType",
		newline : false,
		type : "text"
	}
	, {
		display : "债券级别",
		name : "bondGrade",
		newline : false,
		type : "text"
	}
	, {
		display : "债券名称",
		name : "bondName",
		newline : true,
		type : "text"
	}
	, {
		display : "币种",
		name : "bondCurr",
		newline : false,
		type : "text"
	}
	, {
		display : "金额",
		name : "bondAmt",
		newline : false,
		type : "text"
	}
	, {
		display : "利率规定",
		name : "irregulation",
		newline : true,
		type : "text"
	}
	, {
		display : "债券承销商",
		name : "bondSeller",
		newline : false,
		type : "text"
	}
	, {
		display : "债券主担保人",
		name : "bondWarrantor",
		newline : false,
		type : "text"
	}
	, {
		display : "债券期限",
		name : "bondTerm",
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
				url : "${ctx}/ecif/orgfinance/issuebond/${custId}"
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

