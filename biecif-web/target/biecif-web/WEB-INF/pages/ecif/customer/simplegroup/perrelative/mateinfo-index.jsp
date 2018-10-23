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
		display : "配偶姓名",
		name : "mateName",
		newline : true,
		type : "text",
		group : "配偶信息",
		groupicon : groupicon
	}, {
		display : "配偶证件类型",
		name : "mateIdentType",
		newline : false,
		type : "text"
	}, {
		display : "配偶证件号码",
		name : "mateIdentNo",
		newline : false,
		type : "text"
	}, {
		display : "配偶工作单位",
		name : "mateWorkUnit",
		newline : true,
		type : "text"
	}, {
		display : "配偶单位性质",
		name : "mateWorkUnitChar",
		newline : false,
		type : "text"
	}, {
		display : "配偶参加工作时间",
		name : "mateWorkStartTime",
		newline : false,
		type : "text"
	}, {
		display : "配偶所在行业",
		name : "mateIndustry",
		newline : true,
		type : "text"
	}, {
		display : "配偶职业",
		name : "mateCareer",
		newline : false,
		type : "text"
	}, {
		display : "配偶职务",
		name : "mateDuty",
		newline : false,
		type : "text"
	}, {
		display : "配偶职称",
		name : "mateJobTitle",
		newline : true,
		type : "text"
	}, {
		display : "配偶联系电话",
		name : "mateTel",
		newline : false,
		type : "text"
	}, {
		display : "配偶手机号码",
		name : "mateMobile",
		newline : false,
		type : "text"
	}, {
		display : "配偶固定电话",
		name : "mateLandlineTel",
		newline : true,
		type : "text"
	}, {
		display : "配偶年收入",
		name : "mateAnnualIncome",
		newline : false,
		type : "text"
	}, {
		display : "配偶月收入",
		name : "mateMonthIncome",
		newline : false,
		type : "text"
	}, {
		display : "配偶户籍",
		name : "mateHousehold",
		newline : true,
		type : "text"
	}, {
		display : "最高学历",
		name : "highestSchooling",
		newline : false,
		type : "text"
	}, {
		display : "出生日期",
		name : "birthday",
		newline : false,
		type : "text"
	}, {
		display : "性别",
		name : "gender",
		newline : true,
		type : "text"
	}, {
		display : "民族",
		name : "nationality",
		newline : false,
		type : "text"
	}, {
		display : "配偶身体状况",
		name : "mateHealthStat",
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
				url : "${ctx}/ecif/perrelative/mateinfo/${custId}"
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

<title>配偶信息</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perrelative">
	</form>
</div>
</body>
</html>