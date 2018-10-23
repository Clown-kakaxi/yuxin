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
		display : "行业分类",
		name : "occuIndustry",
		newline : true,
		type : "text",
		group : "客户财务信息",
		groupicon : groupicon
	}	, {
		display : "经营形式",
		name : "manageForm",
		newline : false,
		type : "text"
	}
	, {
		display : "经营资质",
		name : "manageAptitude",
		newline : false,
		type : "text"
	}
	, {
		display : "经营状况",
		name : "manageStat",
		newline : true,
		type : "text"
	}
	, {
		display : "主要原料",
		name : "mainMaterial",
		newline : false,
		type : "text"
	}
	, {
		display : "主要产品",
		name : "mainProduct",
		newline : false,
		type : "text"
	}
	, {
		display : "主要原料产地",
		name : "mainMaterialArea",
		newline : true,
		type : "text"
	}
	, {
		display : "主要销售市场",
		name : "mainSaleMarket",
		newline : false,
		type : "text"
	}
	, {
		display : "主要供货商",
		name : "mainSupplier",
		newline : false,
		type : "text"
	}
	, {
		display : "主要销售商",
		name : "mainSale",
		newline : true,
		type : "text"
	}
	, {
		display : "主营业务收入（上年）",
		name : "lastInco",
		newline : false,
		type : "text"
	}
	, {
		display : "主营业务收入（本年）",
		name : "yearInco",
		newline : false,
		type : "text"
	}
	, {
		display : "年销售量",
		name : "saleNum",
		newline : true,
		type : "text"
	}
	, {
		display : "年销售额",
		name : "saleAmt",
		newline : false,
		type : "text"
	}
	, {
		display : "预算管理类型",
		name : "budgetAdminType",
		newline : false,
		type : "text"
	}
	, {
		display : "企业未来经营规划",
		name : "businessPlan",
		newline : true,
		type : "text"
	}
	, {
		display : "经营场地所有权",
		name : "workFieldOwnership",
		newline : false,
		type : "text"
	}
	, {
		display : "经营场地面积",
		name : "workFieldArea",
		newline : false,
		type : "text"
	}
	, {
		display : "客户营业面积",
		name : "custBusiArea",
		newline : true,
		type : "text"
	}
	, {
		display : "客户办公面积",
		name : "custOfficeArea",
		newline : false,
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
		newline : true,
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
				url : "${ctx}/ecif/orgfinance/orgbusiinfo/${custId}"
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

