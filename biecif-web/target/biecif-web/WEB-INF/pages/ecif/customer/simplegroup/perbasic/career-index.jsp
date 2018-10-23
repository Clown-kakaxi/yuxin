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
    var field = [{
		name : "custId",
		type : "hidden"
	},{
		display : "职业",
		name : "career",
		newline : true,
		type : "text",
		group : "职业信息",
		groupicon : groupicon
	}, {
		display : "职业状况",
		name : "careerCase",
		newline : false,
		type : "text"
	}, {
		display : "职业状态",
		name : "careerStat",
		newline : false,
		type : "text"
	}, {
		display : "职称",
		name : "careerTitle",
		newline : true,
		type : "text"
	}, {
		display : "参加工作时间",
		name : "careerStartDate",
		newline : false,
		type : "text"
	}, {
		display : "客户从事行业",
		name : "profession",
		newline : false,
		type : "text"
	}, {
		display : "单位客户代码",
		name : "jobcustomid",
		newline : true,
		type : "text"
	}, {
		display : "工作单位名称",
		name : "workUnitName",
		newline : false,
		type : "text"
	}, {
		display : "工作单位性质",
		name : "workUnitChar",
		newline : false,
		type : "text"
	}, {
		display : "单位所属行业类型",
		name : "workUnitIndustry",
		newline : true,
		type : "text"
	}, {
		display : "单位地址邮政编码",
		name : "zipcode",
		newline : false,
		type : "text"
	}, {
		display : "单位地址",
		name : "unitAddr",
		newline : false,
		type : "text"
	}, {
		display : "职务",
		name : "duty",
		newline : true,
		type : "text"
	}, {
		display : "详细职务",
		name : "dutyDetail",
		newline : false,
		type : "text"
	}, {
		display : "参加本单位日期",
		name : "currCareerStartDate",
		newline : false,
		type : "text"
	}, {
		display : "任职时间",
		name : "currWorkTime",
		newline : true,
		type : "text"
	}, {
		display : "行政级别",
		name : "adminLevel",
		newline : false,
		type : "text"
	}, {
		display : "资格证书名称",
		name : "qualification",
		newline : false,
		type : "text"
	}, {
		display : "主要工作业绩",
		name : "workResult",
		newline : true,
		type : "text"
	}, {
		display : "是否有执业资格",
		name : "hasQualification",
		newline : false,
		type : "text"
	}, {
		display : "年薪范围",
		name : "annualSalaryScope",
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
				url : "${ctx}/ecif/perbasic/career/${custId}"
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

<title>职业信息</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perbasic">
	</form>
</div>
</body>
</html>