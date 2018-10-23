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
		display : "经营场地面积",
		name : "workFieldArea",
		newline : true,
		type : "text",
		group : "机构客户扩展信息",
		groupicon : groupicon
	}, {
		display : "客户办公面积",
		name : "custOfficeArea",
		newline : false,
		type : "text"
	}, {
		display : "客户营业面积",
		name : "custBusiArea",
		newline : false,
		type : "text"
	}, {
		display : "经营场地所有权",
		name : "workFieldOwnership",
		newline : true,
		type : "text"
	}, {
		display : "合法经营情况",
		name : "manageinfo",
		newline : false,
		type : "text"
	}, {
		display : "企业发展历史",
		name : "corpDeveHistory",
		newline : false,
		type : "text"
	}, {
		display : "客户历史沿革",
		name : "custHistory",
		newline : true,
		type : "text"
	}, {
		display : "管理水平简介",
		name : "manageLevel",
		newline : false,
		type : "text"
	}, {
		display : "母公司贷款卡编码",
		name : "superLoanCardNo",
		newline : false,
		type : "text"
	}, {
		display : "借款主体类型",
		name : "loanBodyType",
		newline : true,
		type : "text"
	}, {
		display : "财务报表所属",
		name : "financeBelong",
		newline : false,
		type : "text"
	}, {
		display : "信用等级评估表类型",
		name : "creditBelong",
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
	}, {
		display : "行业地位",
		name : "industryPosition",
		newline : false,
		type : "text"
	}, {
		display : "规模性行业分类",
		name : "occuindustrytype",
		newline : true,
		type : "text"
	}, {
		display : "企业内部环境行业分类",
		name : "innerenvindustrytype",
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
				url : "${ctx}/ecif/orgbasic/orgextend/${custId}"
			});
		}
	});
	

</script>

<title>机构客户扩展信息</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/orgbasic">
	</form>
</div>
</body>
</html>