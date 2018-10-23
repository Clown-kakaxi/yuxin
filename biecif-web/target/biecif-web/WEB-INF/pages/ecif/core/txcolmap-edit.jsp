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
		/* 	labelWidth : 80,
			inputWidth : 160,
			space : 30, */
			fields : [ {
				name: "srcSysCdName",
				display: "源系统<font color='red'>*</font>",
				newline: true,
				type : "select",
				options : {
					valueFieldID : "srcSysCd",
					url : "${ctx}/ecif/transaction/txclientauth/getComBoBox.json",
					ajaxType : "get"
				},				
				validate: {
					required : true,
					maxlength: 20
				}
			}, {
				display : "源系统表名<font color='red'>*</font>",
				name : "srcTab",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 60
				}
			}, {
				display : "源系统字段名<font color='red'>*</font>",
				name : "srcCol",
				newline: true,
				type : "text",
				validate : {
					required : true,
					maxlength : 60
				}
			}, {
				display : "ECIF表名",
				name : "stdTab",
				newline: true,
				type : "text",
				validate : {
					maxlength : 60
				}
			}, {
				display : "ECIF字段",
				name : "stdCol",
				newline: true,
				type : "text",
				validate : {
					maxlength : 60
				}
			}, {
				display : "分类代码",
				name : "stdCate",
				newline: true,
				type : "text",
				validate : {
					maxlength : 20
				}
			}, {
				display : "是否需要标准化",
				name : "stdFlagBox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'stdFlag',
					data : [{id : '1', text : '是'}, {id : '0', text : '否'}]
				},
				validate : {
					maxlength : 10
				}
			}, {
				display : "是否需要去空格",
				name : "trimFlagBox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'trimFlag',
					data : [{id : '1', text : '是'}, {id : '0', text : '否'}]
				},
				validate : {
					maxlength : 10
				}
			}, {
				display : "记录状态<font color='red'>*</font>",
				name : "stateBox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'state',
					data : [{id : '1', text : '有效'}, {id : '0', text : '无效'}]
				},
				validate : {
					required : true,
					maxlength : 10
				}
			
			} ]
		});
		if("${id}") {
			var idstr = "${id}";
			var params = idstr.split(",");
			sycSysCd = params[0];
			srcTab = params[1];
			srcCol = params[2];
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/core/txcolmap/show?sycSysCd="+sycSysCd+"&srcTab="+srcTab+"&srcCol="+srcCol});
			
			$("#mainform input[name=srcSysCdName]").attr("disabled", "true").css("color", "black").removeAttr("validate");
			$("#mainform input[name=srcTab]").attr("readOnly", "readOnly").css("color", "grey").removeAttr("validate");
			$("#mainform input[name=srcCol]").attr("readOnly", "readOnly").css("color", "grey").removeAttr("validate");
		
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
			onclick : save_resDef
		});
		BIONE.addFormButtons(buttons);

	});
	function save_resDef() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("resDefManage","maingrid" , "保存成功");
		}, function() {
			BIONE.closeDialog("resDefManage", "保存失败");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("resDefManage");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/core/txcolmap" method="post"></form>
	</div>
</body>
</html>