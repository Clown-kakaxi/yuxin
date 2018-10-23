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
		display : "性别",
		name : "gender",
		newline : true,
		type : "text",
		group : "人口轮廓信息",
		groupicon : groupicon
	}, {
		display : "出生日期",
		name : "birthday",
		newline : false,
		type : "text"
	}, {
		display : "出生地点",
		name : "birthLocale",
		newline : false,
		type : "text"
	}, {
		display : "国籍",
		name : "citizenship",
		newline : true,
		type : "text"
	}, {
		display : "民族",
		name : "nationality",
		newline : false,
		type : "text"
	}, {
		display : "籍贯",
		name : "nativePlace",
		newline : false,
		type : "text"
	}, {
		display : "户籍性质",
		name : "houseHold",
		newline : true,
		type : "text"
	}, {
		display : "政治面貌",
		name : "politicalFace",
		newline : false,
		type : "text"
/**		
	}, {
		display : "户口所在地区",
		name : "hukouRegion",
		newline : false,
		type : "text"
**/		
	}, {
		display : "户口所在地",
		name : "hukouPlace",
		newline : false,
		type : "text"
	}, {
		display : "婚姻状况",
		name : "marriage",
		newline : true,
		type : "text"
	}, {
		display : "居住状况",
		name : "residence",
		newline : false,
		type : "text"
	}, {
		display : "健康状况",
		name : "health",
		newline : false,
		type : "text"
	}, {
		display : "宗教信仰",
		name : "religiousBelief",
		newline : true,
		type : "text"
	}, {
		display : "最高学历",
		name : "highestSchooling",
		newline : false,
		type : "text"
	}, {
		display : "最高学位",
		name : "highestDegree",
		newline : false,
		type : "text"
	}, {
		display : "毕业学校",
		name : "graduateSchool",
		newline : true,
		type : "text"
	}, {
		display : "所学专业",
		name : "major",
		newline : false,
		type : "text"
	}, {
		display : "毕业时间",
		name : "graduationTime",
		newline : false,
		type : "text"
	}, {
		display : "简历",
		name : "resume",
		newline : true,
		type : "textarea",
		width : 730
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
				url : "${ctx}/ecif/perbasic/personprofile/${custId}"
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

<title>人口轮廓</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perbasic">
	</form>
</div>
</body>
</html>