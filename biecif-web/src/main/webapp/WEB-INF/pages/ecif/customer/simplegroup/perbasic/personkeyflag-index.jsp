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
		display : "是否商户",
		name : "isMerchant",
		newline : true,
		type : "text",
		group : "个人客户重要标志",
		groupicon : groupicon
	}, {
		display : "是否农户",
		name : "isPeasant",
		newline : false,
		type : "text"
	}, {
		display : "是否信用农户",
		name : "isCrediblePeasant",
		newline : false,
		type : "text"
	}, {
		display : "农户分类",
		name : "peasantType",
		newline : true,
		type : "text"
	}, {
		display : "是否本行股东",
		name : "isShareholder",
		newline : false,
		type : "text"
	}, {
		display : "是否是本行职工",
		name : "isEmployee",
		newline : false,
		type : "text"
	}, {
		display : "是否有本行贷款",
		name : "hasThisBankLoan",
		newline : true,
		type : "text"
	}, {
		display : "是否有他行贷款",
		name : "hasOtherBankLoan",
		newline : false,
		type : "text"
	}, {
		display : "是否存在不良贷款",
		name : "hasBadLoan",
		newline : false,
		type : "text"
	}, {
		display : "是否对外提供担保",
		name : "isGuarantee",
		newline : true,
		type : "text"
	}, {
		display : "是否本地人",
		name : "isNative",
		newline : false,
		type : "text"
	}, {
		display : "本地居住时间（年）",
		name : "localResideTime",
		newline : false,
		type : "text"
	}, {
		display : "是否正式在岗职工",
		name : "isOnJobWorker",
		newline : true,
		type : "text"
	}, {
		display : "有无社会保险",
		name : "hasSocialSecurity",
		newline : false,
		type : "text"
	}, {
		display : "有无商业保险",
		name : "hasCommercialInsurance",
		newline : false,
		type : "text"
	}, {
		display : "有无征信信息",
		name : "hasCreditInfo",
		newline : true,
		type : "text"
	}, {
		display : "是否重要客户",
		name : "isImportantCust",
		newline : false,
		type : "text"
	}, {
		display : "是否授信客户",
		name : "isCreditCust",
		newline : false,
		type : "text"
	}, {
		display : "是否保密客户",
		name : "isSecretCust",
		newline : true,
		type : "text"
	}
     ];
	//创建表单结构 
	function ligerFormNow() {
		mainform = $("#mainform").ligerForm({
		    inputWidth : 140,
		    labelWidth : 120,
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
				url : "${ctx}/ecif/perbasic/personkeyflag/${custId}"
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

<title>个人客户重要标志</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perbasic">
	</form>
</div>
</body>
</html>