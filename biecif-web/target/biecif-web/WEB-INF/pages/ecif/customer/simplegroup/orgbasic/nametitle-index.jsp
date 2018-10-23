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
		display : "客户名称",
		name : "name",
		newline : true,
		type : "text",
		group : "名称称谓",
		groupicon : groupicon
	}, {
		display : "客户全称",
		name : "fullName",
		newline : false,
		type : "text"
	}, {
		display : "客户简称",
		name : "shortName",
		newline : false,
		type : "text"
	}, {
		display : "英文名称",
		name : "enName",
		newline : true,
		type : "text"
	}, {
		display : "英文简称",
		name : "enShortName",
		newline : false,
		type : "text"
	}, {
		display : "客户称谓",
		name : "title",
		newline : false,
		type : "text"
	}, {
		display : "拼音名称",
		name : "pinYin",
		newline : true,
		type : "text"
	}, {
		display : "其他名称",
		name : "otherName",
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
				url : "${ctx}/ecif/orgbasic/nametitle/${custId}"
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