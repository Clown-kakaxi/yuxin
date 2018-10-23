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
		display : "基本账户开户行编号",
		name : "basicAcctBankNo",
		newline : true,
		type : "text",
		group : "基本账户",
		groupicon : groupicon
	}, {
		display : "基本账户开户行名称",
		name : "basicAcctBankName",
		newline : false,
		type : "text"
	}, {
		display : "基本账户账号",
		name : "basicAcctNo",
		newline : true,
		type : "text"
	}, {
		display : "基本账户户名",
		name : "basicAcctName",
		newline : false,
		type : "text"
	}, {
		display : "基本户状态",
		name : "basicAcctStat",
		newline : true,
		type : "text"
	}, {
		display : "基本户开户许可证编号",
		name : "basicAcctLicNo",
		newline : true,
		type : "text"
	}, {
		display : "基本户开户时间",
		name : "basicAcctOpenDate",
		newline : false,
		type : "text"
	}, {
		display : "基本户年检日期",
		name : "basicAcctVerifyDate",
		newline : true,
		type : "text"
	}, {
		display : "基本户是否在本机构",
		name : "isOwnBank",
		newline : false,
		type : "text"
	}
     ];
	//创建表单结构 
	function ligerFormNow() {
		mainform = $("#mainform").ligerForm({
		    inputWidth : 180,
		    labelWidth : 150,
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
				url : "${ctx}/ecif/orgbasic/basicaccount/${custId}"
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

<title>基本账户信息</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/orgbasic">
	</form>
</div>
</body>
</html>