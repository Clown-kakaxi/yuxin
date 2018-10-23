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
		display : "是否具备法人资格",
		name : "isLegalCorp",
		newline : true,
		type : "text",
		group : "机构客户重要标志",
		groupicon : groupicon
	}, {
		display : "是否上市企业",
		name : "isListedCorp",
		newline : false,
		type : "text"
	}, {
		display : "是否高新技术企业",
		name : "isHighTechCorp",
		newline : false,
		type : "text"
	}, {
		display : "是否农村企业",
		name : "isRuralCorp",
		newline : true,
		type : "text"
	}, {
		display : "是否小企业",
		name : "isSmallCorp",
		newline : false,
		type : "text"
	}, {
		display : "是否民营企业",
		name : "isPrivateCorp",
		newline : false,
		type : "text"
	}, {
		display : "是否新建企业",
		name : "isNewCorp",
		newline : true,
		type : "text"
	}, {
		display : "是否存在不良贷款",
		name : "hasBadLoan",
		newline : false,
		type : "text"
	}, {
		display : "是否有他行贷款",
		name : "hasOtherBankLoan",
		newline : false,
		type : "text"
	}, {
		display : "集团客户标志",
		name : "isGroupCust",
		newline : true,
		type : "text"
	}, {
		display : "是否关联集团",
		name : "isRelaGroup",
		newline : false,
		type : "text"
	}, {
		display : "是否保密",
		name : "isSecret",
		newline : false,
		type : "text"
	}, {
		display : "是否涉农",
		name : "isRural",
		newline : true,
		type : "text"
	}, {
		display : "是否限制行业",
		name : "isLimitIndustry",
		newline : false,
		type : "text"
	}, {
		display : "有无特种经营许可证",
		name : "hasSpecialBusiLic",
		newline : false,
		type : "text"
	}, {
		display : "是否授信客户",
		name : "isCreditCust",
		newline : true,
		type : "text"
	}, {
		display : "集团授信标志",
		name : "groupCreditFlag",
		newline : false,
		type : "text"
	}, {
		display : "是否我行关联方",
		name : "isAssociatedParty",
		newline : false,
		type : "text"
	}, {
		display : "是否网银签约客户",
		name : "isEbankSignCust",
		newline : true,
		type : "text"
	}, {
		display : "客户破产标识",
		name : "bankruptFlag",
		newline : false,
		type : "text"
	}, {
		display : "是否母公司",
		name : "hasParentCorp",
		newline : false,
		type : "text"
	}, {
		display : "有无董事会",
		name : "hasBoard",
		newline : true,
		type : "text"
	}, {
		display : "有无进出口经营权",
		name : "hasIeRight",
		newline : false,
		type : "text"
	}, {
		display : "是否从事房地产开发",
		name : "realtyFlag",
		newline : false,
		type : "text"
	}, {
		display : "企业目前是否有项目",
		name : "projectFlag",
		newline : true,
		type : "text"
	}, {
		display : "是否无须提供财务报表",
		name : "financeReportFlag",
		newline : false,
		type : "text"
	}, {
		display : "地方政府融资平台标志",
		name : "udivFlag",
		newline : false,
		type : "text"
	}, {
		display : "是否是重点客户",
		name : "isImportantCust",
		newline : true,
		type : "text"
	}, {
		display : "重点户类型",
		name : "importantCustType",
		newline : false,
		type : "text"
	}, {
		display : "免税标志",
		name : "freeTaxFlag",
		newline : false,
		type : "text"
	}, {
		display : "双大客户标志",
		name : "grtCustFlag",
		newline : true,
		type : "text"
	}, {
		display : "银企合作标志",
		name : "bankCorpFlag",
		newline : false,
		type : "text"
	}, {
		display : "是否进口付汇名录内",
		name : "payList",
		newline : false,
		type : "text"
	}, {
		display : "是否结汇信得过企业",
		name : "payCredit",
		newline : true,
		type : "text"
	}, {
		display : "是否担保公司",
		name : "isGuaranty",
		newline : false,
		type : "text"
	}, {
		display : "是否评估机构",
		name : "isEvaluate",
		newline : false,
		type : "text"
	}, {
		display : "是否审计单位",
		name : "isAudit",
		newline : true,
		type : "text"
	}, {
		display : "是否保险公司",
		name : "isInsurance",
		newline : false,
		type : "text"
	}, {
		display : "是否第三方监管单位",
		name : "isSupervision",
		newline : false,
		type : "text"
	}, {
		display : "500强标志",
		name : "isTop500",
		newline : true,
		type : "text"
	}, {
		display : "现金管理客户类别",
		name : "cashAdminCustType",
		newline : false,
		type : "text"
	}, {
		display : "是否代理行客户",
		name : "isAgentBankCust",
		newline : false,
		type : "text"
	}, {
		display : "是否关联方",
		name : "isRelatedParty",
		newline : true,
		type : "text"
	}, {
		display : "是否合作伙伴",
		name : "isPartner",
		newline : false,
		type : "text"
	}];
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
				url : "${ctx}/ecif/orgbasic/orgkeyflag/${custId}"
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

<title>机构客户重要标志</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/orgbasic">
	</form>
</div>
</body>
</html>