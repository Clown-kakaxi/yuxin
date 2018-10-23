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
		display : "监测对象类型",
		name : "monitorObjType",
		newline : true,
		type : "text",
		group : "个人客户扩展信息",
		groupicon : groupicon
	}, {
		display : "自然情况其他说明",
		name : "otherDesc",
		newline : false,
		type : "text"
	}, {
		display : "所属行政村编号",
		name : "adminVillageNo",
		newline : false,
		type : "text"
	}, {
		display : "所属行政村名称",
		name : "adminVillageName",
		newline : true,
		type : "text"
	}, {
		display : "个人情况简介",
		name : "introduction",
		newline : false,
		type : "text"
	}, {
		display : "个人资产与负债详情",
		name : "assetsDebtDetail",
		newline : false,
		type : "text"
	}, {
		display : "主要收入来源",
		name : "mainIncome",
		newline : true,
		type : "text"
	}, {
		display : "工资账号",
		name : "salaryAccount",
		newline : false,
		type : "text"
	}, {
		display : "工资账号开户银行",
		name : "salaryAccountBank",
		newline : false,
		type : "text"
	}, {
		display : "借款主体类型",
		name : "loanBodyType",
		newline : true,
		type : "text"
	}, {
		display : "个人客户授权级别",
		name : "personAuthLevel",
		newline : false,
		type : "text"
	}, {
		display : "财务报表所属",
		name : "financeBelong",
		newline : false,
		type : "text"
	}, {
		display : "暂存标志",
		name : "tempSaveFlag",
		newline : true,
		type : "text"
	}, {
		display : "与本行建立信贷关系最早日期",
		name : "buildLoanOriginalDate",
		newline : false,
		type : "text"
	}
     ];
	//创建表单结构 
	function ligerFormNow() {
		mainform = $("#mainform").ligerForm({
		    inputWidth : 140,
		    labelWidth : 120,
		    space : 20,
		    fields : field
		});
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
	}
	$(function(){
		ligerFormNow();
		if ("${custId}") {
			BIONE.loadForm(mainform, {
				url : "${ctx}/ecif/perbasic/personextend/${custId}"
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

<title>个人客户扩展信息</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perbasic">
	</form>
</div>
</body>
</html>