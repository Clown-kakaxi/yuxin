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
	}, {
		display : "主要客户",
		name : "mainCust",
		newline : false,
		type : "text",
		group : "基本概况",
		groupicon : groupicon
	},{
		display : "主要服务",
		name : "mainService",
		newline : false,
		type : "text"
	},{
		display : "主要产品",
		name : "mainProduct",
		newline : false,
		type : "text"
	},{
		display : "经济区编码",
		name : "zoneCode",
		newline : true,
		type : "text"
	},{
		display : "员工规模",
		name : "employeeScale",
		newline : false,
		type : "text"
	},{
		display : "主营业务",
		name : "mainBusiness",
		newline : false,
		type : "text"
	},{
		display : "资产规模",
		name : "assetsScale",
		newline : true,
		type : "text"
	}, {
		display : "组织机构类型",
		name : "orgType",
		newline : false,
		type : "text"
	}, {
		display : "行业分类",
		name : "industry",
		newline : false,
		type : "text"
	}, {
		display : "三次产业划分",
		name : "industryDivision",
		newline : true,
		type : "text"
	}, {
		display : "行业特征",
		name : "industryChar",
		newline : false,
		type : "text"
	}, {
		display : "企业性质",
		name : "entProperty",
		newline : false,
		type : "text"
	}, {
		display : "企业规模",
		name : "entScale",
		newline : true,
		type : "text"
	}, {
		display : "经济类型",
		name : "economicType",
		newline : false,
		type : "text"
	}, {
		display : "组织形式",
		name : "orgForm",
		newline : false,
		type : "text"
	}, {
		display : "客户开始营业时间",
		name : "busiStartTime",
		newline : true,
		type : "text"
	}, {
		display : "行业发展前景",
		name : "induDeveProspect",
		newline : false,
		type : "text"
	}, {
		display : "经费来源",
		name : "fundSource",
		newline : false,
		type : "text"
	}, {
		display : "工商局锁定情况",
		name : "lockSituation",
		newline : true,
		type : "text"
	}, {
		display : "税务登记证号（国税）",
		name : "nationalTax",
		newline : false,
		type : "text"
	}, {
		display : "税务登记证号（地税）",
		name : "localTax",
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
		//jQuery.metadata.setType("attr", "validate");
		//BIONE.validate($("#mainform"));
	}
	$(function(){
		ligerFormNow();
		
		//alert("aa");
		if ("${custId}") {
			
			BIONE.loadForm(mainform, {
				url : "${ctx}/ecif/orgbasic/orgprofile/${custId}"
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