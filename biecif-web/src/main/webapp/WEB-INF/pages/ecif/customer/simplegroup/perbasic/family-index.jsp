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
		display : "家庭地址",
		name : "familyAddr",
		newline : true,
		type : "text",
		group : "家庭状况",
		groupicon : groupicon
	}, {
		display : "固定电话",
		name : "homeTel",
		newline : false,
		type : "text"
	}, {
		display : "总人口数量",
		name : "population",
		newline : false,
		type : "text"
	}, {
		display : "住宅状况",
		name : "residenceStat",
		newline : true,
		type : "text"
	}, {
		display : "是否户主标志",
		name : "isHouseHolder",
		newline : true,
		type : "text"
	}, {
		display : "子女人数",
		name : "childrenNum",
		newline : false,
		type : "text"
	}, {
		display : "供养人数",
		name : "supplyPopNum",
		newline : false,
		type : "text"
	}, {
		display : "赡养人数",
		name : "providePopNum",
		newline : true,
		type : "text"
	}
     ];
	//创建表单结构 
	function ligerFormNow() {
		mainform = $("#mainform").ligerForm({
		    inputWidth : 150,
		    labelWidth : 100,
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
				url : "${ctx}/ecif/perbasic/family/${custId}"
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

<title>家庭状况</title>
</head>
<body>
<div id="template.center">
	<form name="mainform" method="post" id="mainform" action="${ctx}/ecif/perbasic">
	</form>
</div>
</body>
</html>