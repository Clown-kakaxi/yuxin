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
		display : "法人客户成立日期",
		name : "setupDate",
		newline : true,
		type : "text",
		group : "注册信息",
		groupicon : groupicon
	}, {
		display : "法人客户到期日期",
		name : "maturityDate",
		newline : false,
		type : "text"
	}, {
		display : "客户注册资本额",
		name : "registerCapital",
		newline : false,
		type : "text"
	}, {
		display : "客户注册币种",
		name : "registerCcurrency",
		newline : true,
		type : "text"
	}, {
		display : "注册资金构成",
		name : "registerComposing",
		newline : false,
		type : "text"
	}, {
		display : "客户注册地址",
		name : "registerAddr",
		newline : false,
		type : "text"
	}, {
		display : "客户主营业务",
		name : "mainBusiness",
		newline : true,
		type : "text"
	}, {
		display : "客户兼营业务",
		name : "minorBusiness",
		newline : false,
		type : "text"
	}, {
		display : "企业注册类型1",
		name : "registerType1",
		newline : false,
		type : "text"
	}, {
		display : "企业注册类型2",
		name : "registerType2",
		newline : true,
		type : "text"
	}, {
		display : "经营方式",
		name : "businessMode",
		newline : false,
		type : "text"
	}, {
		display : "经营范围",
		name : "businessScope",
		newline : false,
		type : "text"
	}, {
		display : "行政区划",
		name : "adminZone",
		newline : true,
		type : "text"
	}, {
		display : "实收资本币种",
		name : "factCapitalCurr",
		newline : false,
		type : "text"
	}, {
		display : "实收资本",
		name : "factCapital",
		newline : false,
		type : "text"
	}, {
		display : "财务部联系方式",
		name : "financeContact",
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
				url : "${ctx}/ecif/orgbasic/registerinfo/${custId}"
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

<title>名称称谓</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/orgbasic">
	</form>
</div>
</body>
</html>