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
	           /*{
					name : 'id',
					type : 'hidden'
			    },*/
				{
					display : "ID<font color='red'>*</font>",
					name : "id",
					newline : true,
					group : "通用覆盖程序配置信息",
					groupicon : groupicon,
					type : "text",
					validate : {
						required : true,
						maxlength : 64
					}
				}, {
				display : "源表模式名<font color='red'>*</font>",
				name : "schsrc",
				newline : true,
				groupicon : groupicon,
				type : "text",
				validate : {
					required : true,
					maxlength : 64
				}
			}, {
				display : "目标表模式名<font color='red'>*</font>",
				name : "schdst",
				newline : false,
				type : "text",
				validate : {
					required : true,
					maxlength : 64
				}
			}, {
				display : "源表英文名<font color='red'>*</font>",
				name : "srcTab",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 64
				}
			}, {
				display : "目标表英文名<font color='red'>*</font>",
				name : "dstTab",
				newline : false,
				type : "test",
				validate : {
					required : true,
					maxlength : 64
				}
			}, {
				display : "有效性校验",
				name : "type",
				newline : true,
				type : "text",
				validate : {
					required : false,
					maxlength : 50
				}
			}, {
				display : "覆盖规则",
				name : "app",
				newline : false,
				type : "text",
				validate : {
					required : false,
					maxlength : 50
				}
			}, {
				display : "源表主键",
				name : "key",
				newline : true,
				type : "text",
				validate : {
					required : false,
					maxlength : 256
				}
			}, {
				display : "源表关联条件",
				name : "srcJoin",
				newline : false,
				type : "text",
				validate : {
					required : false,
					maxlength : 256
				}
			}, {
				display : "目标表关联条件",
				name : "dstJoin",
				newline : true,
				type : "text",
				validate : {
					required : false,
					maxlength : 256
				}
			}, {
				display : "系统优先级字段英文名",
				name : "sysCol",
				newline : false,
				type : "text",
				validate : {
					required : false,
					maxlength : 64
				}
			}, {
				display : "跑数时间",
				name : "hisOperTime",
				newline : true,
				type : "text",
				validate : {
					required : false,
					maxlength : 64
				}
			}, {
				display : "历史附加字段",
				name : "bakCol",
				newline : false,
				type : "text",
				validate : {
					required : false,
					maxlength : 500
				}
			}, {
				display : "历史附加字段值",
				name : "bakVal",
				newline : true,
				type : "text",
				validate : {
					required : false,
					maxlength : 500
				}
			} ]
		});
		
		if("${id}") {
			BIONE.loadForm($("#mainform"), {url : "${ctx}/ecif/rulemanage/dqincrtabconf/${id}"});
			$("#mainform input[name=id]").attr("readOnly", "readOnly").css("color", "black").removeAttr("validate");
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
			onclick : save_dqincrtabconf
		});
		BIONE.addFormButtons(buttons);

	});
	function save_dqincrtabconf() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialogAndReloadParent("dqincrtabconfManage","maingrid" , "保存成功");
		}, function() {
			BIONE.closeDialog("dqincrtabconfManage", "保存失败");
		});
	}
	function cancleCallBack() {
		BIONE.closeDialog("dqincrtabconfManage");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/ecif/rulemanage/dqincrtabconf" method="post"></form>
	</div>
</body>
</html>