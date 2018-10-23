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
		display : "是否享受住房补贴",
		name : "havealloflag",
		newline : true,
		type : "text",
		group : "个人社会保障信息",
		groupicon : groupicon
	}, {
		display : "是否参加社会保险",
		name : "havecommflag",
		newline : false,
		type : "text"
	}, {
		display : "是否参加养老保险",
		name : "haveendoflag",
		newline : true,
		type : "text"
	}, {
		display : "是否参加失业保险",
		name : "haveidleflag",
		newline : false,
		type : "text"
	}, {
		display : "是否参加医疗保险",
		name : "havemediflag",
		newline : true,
		type : "text"
	}
     ];
	//创建表单结构 
	function ligerFormNow() {
		mainform = $("#mainform").ligerForm({
		    inputWidth : 180,
		    labelWidth : 110,
		    space : 60,
		    fields : field
		});
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
	}
	$(function(){
		ligerFormNow();
		if ("${custId}") {
			BIONE.loadForm(mainform, {
				url : "${ctx}/ecif/perrisk/persocialsecurity/${custId}"
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

<title>个人社会保障信息</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perrisk">
	</form>
</div>
</body>
</html>