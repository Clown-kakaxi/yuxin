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
		display : "本行借款金额",
		name : "bankloan",
		newline : true,
		type : "text",
		group : "个人负债",
		groupicon : groupicon
	}, {
		display : "负债金额合计",
		name : "loanamt",
		newline : false,
		type : "text"
	}, {
		display : "借款项目",
		name : "debtitem",
		newline : false,
		type : "text"
	}, {
		display : "还款截止时间",
		name : "enddate",
		newline : true,
		type : "text"
	}, {
		display : "家庭是否负债",
		name : "isdebt",
		newline : false,
		type : "text"
	}, {
		display : "家庭总负债",
		name : "famidebt",
		newline : false,
		type : "text"
	}, {
		display : "未还款额",
		name : "bal",
		newline : true,
		type : "text"
	}, {
		display : "按揭银行+A2",
		name : "mortgagebank",
		newline : false,
		type : "text"
	}, {
		display : "借款人编号",
		name : "loancustid",
		newline : false,
		type : "text"
	}, {
		display : "借款人",
		name : "loancustname",
		newline : true,
		type : "text"
	}, {
		display : "每期还款额",
		name : "mamt",
		newline : false,
		type : "text"
	}, {
		display : "是否抵押",
		name : "mortflag",
		newline : false,
		type : "text"
	}, {
		display : "是否存在拖欠",
		name : "overflag",
		newline : true,
		type : "text"
	}, {
		display : "所有人编号",
		name : "owncustid",
		newline : false,
		type : "text"
	}, {
		display : "完税价",
		name : "taxprice",
		newline : false,
		type : "text"
	}, {
		display : "租用期限",
		name : "term",
		newline : true,
		type : "text"
	}, {
		display : "购车每月还款额",
		name : "monthlyrepay",
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
				url : "${ctx}/ecif/perfinance/perdebt/${custId}"
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

<title>个人负债</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perfinance">
	</form>
</div>
</body>
</html>