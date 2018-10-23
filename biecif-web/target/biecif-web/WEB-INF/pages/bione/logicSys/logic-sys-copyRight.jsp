<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var logicSysNo="${logicSysNo}";
	var maiform;
	var dialog;
	var groupicon = "${ctx}/images/classics/icons/communication.gif";

	$(function() {
		mainform = $("#mainform").ligerForm({
			align : 'center',
			fields : [ {
				name:"logicSysId",
				type:"hidden"
			},{
				name:"logicSysNo",
				type:"hidden"
			},{
				name:"authTypeNo",
				type:"hidden"
			},{
				name:"basicDeptSts",
				type:"hidden"
			},{
				name:"basicOrgSts",
				type:"hidden"
			},{
				name:"logicSysIcon",
				type:"hidden"
			},{
				name:"logicSysName",
				type:"hidden"
			},{
				name:"logicSysSts",
				type:"hidden"
			},{
				name:"orderNo",
				type:"hidden"
			},{
				name:"remark",
				type:"hidden"
			},{
				display : "逻辑系统标志",
				name : "logicSysLogo",
				id : "logicSysLogo",
				newline : true,
				type : "select",
				group : "版权信息",
				groupicon : groupicon,
				comboboxName : "IconBoxID",
				options : {
				valueFieldID : "logicSysLogo",
				url : "${ctx}/bione/logicSys/logic-sys!findImgForCombo.json",
				ajaxType:"get"
			},
				validate : {
					required : false
				}

			}, {
				display : "逻辑系统明细",
				name : "logicSysDetail",
				id : "logicSysDetail",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 32,
					messages : {
						required : "该字段不能为空"
					}
				}
			}, {
				display : "中文版权",
				name : "cnCopyright",
				id : "cnCopyright",
				newline : true,
				type : "textarea",
				width : 250,
				validate : {
					required : true,
					maxlength : 500,
					messages : {
						required : "不能为空"
					}
				}
			}, {
				display : "英文版权",
				name : "enCopyright",
				id : "enCopyright",
				newline : true,
				type : "textarea",
				width : 250,
				validate : {
					required : true,
					maxlength : 500,
					messages : {
						required : "不能为空"
					}
				}
			} ]
		});
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick : f_close
		});
		buttons.push({
			text : '保存',
			onclick : f_save
		});
		//打开图标对话框
		$.ligerui.get("IconBoxID").openSelect({
			url : '${ctx}/bione/logicSys/logic-sys!selectImages.xhtml',
			dialogname:'iconselector',
			title:'选择图标',
			comboxName:'IconBoxID',
			dialogType:'icon'
		}); 
		
		BIONE.addFormButtons(buttons);

		jQuery.metadata.setType("attr", "validate");
		BIONE.validate("#mainform");
		BIONE.loadForm(mainform, {
			url : "${ctx}/bione/logicSys/logic-sys!getFromInfo.json",
			data : {
				id : "${id}"
			}
		});
	
	});
	function f_save() {
		BIONE.submitForm($("#mainform"), function() {
			BIONE.closeDialog("logicSysCopyWin","保存成功");
		}, function() {
			BIONE.closeDialog("logicSysCopyWin","保存失败");
		});
	}

	function f_close() {
		BIONE.closeDialog("logicSysCopyWin");
	}
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/bione/logicSys/logic-sys"
			method="post"></form>
	</div>
</body>
</html>