<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	//创建表单结构 
	var mainform;
	$(function() {
		mainform = $("#mainform");
		mainform.ligerForm({
			labelWidth : 140,
			inputWidth : 160,
			space : 80, 
			fields : [ 
	           {
					name : 'ruleGroupId',
					type : 'hidden'
			    },
				{
					display : "规则组编号<font color='red'>*</font>",
					name : "ruleGroupNo",
					newline : true,
					group : "规则大类",
					groupicon : groupicon,
					type : "text" ,
					validate : {
						required : true,
						maxlength : 32,
						remote: {
							url:"${ctx}/ecif/rulemanage/txbizrulegroup/ruleGroupValid?d="+new Date()
						},
						messages:{
							remote : "规则组编号重复"
						}
					} 
				}, {
				display : "规则组名称<font color='red'>*</font>",
				name : "ruleGroupName",
				newline : false,
				groupicon : groupicon,
				type : "text",
				validate : {
					required : true,
					maxlength : 40,
					remote:{
						url:"${ctx}/ecif/rulemanage/txbizrulegroup/ruleGroupValid?ruleGroupId="+"${id}"+"&d="+new Date()
					},
					messages:{
						remote : "规则组名称重复"
					}
				} 
			}, {
				display : "备注",
				name : "ruleGroupDesc",
				newline : true,
				type : "textarea",
				width : 545,
				attr : {
					style : "resize: none;"
				},
				validate : {
					maxlength : 255
				}
			} ]
		});
		//修改判定
		 if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/rulemanage/txbizrulegroup/${id}"});
			$("#mainform input[name=ruleGroupNo]").attr("readonly", "readonly").css("color", "black").removeAttr("validate");
		} 
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate(mainform);
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick : cancleCallBack
		});
		buttons.push({
			text : '保存',
			onclick : save
		});
		BIONE.addFormButtons(buttons);

	});
	function save() {
		BIONE.submitForm($("#mainform"), function() {
			window.parent.initTree();
			BIONE.closeDialog("RuleGroupAdd","保存成功");
		}, function() {
			BIONE.closeDialog("RuleGroupAdd", "保存失败");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("RuleGroupAdd");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/rulemanage/txbizrulegroup" method="post"></form>
	</div>
</body>
</html>