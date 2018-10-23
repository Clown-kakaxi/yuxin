<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template5.jsp">
<head>
<script type="text/javascript">
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var mainform;
	var isEdit = false;
	
	$(function () {
		mainform = $("#mainform").ligerForm({
			fields : [{
				name : "serverId",
				type:"hidden"
			}, {
				display : "服务标识",
				name : "serverNo",
				newline : true,
				type : "text",
				group : "服务模块 ",
				groupicon : groupicon,
				validate : {
					required : true,
					remote :  "${ctx}/bione/serverinfo/checkServerNo?t=${t}",
					messages : {
						remote:"服务标识已存在，请检查！"
					},
					maxlength : 32
				}
			}, {
				display : "服务名称",
				name : "serverName",
				newline : false,
				type : "text",
				validate : {
					required : true,
					maxlength : 100
				}
			}, {
				display : "服务IP",
				name : "serverIp",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 50
				}
			},  {
				display : "服务端口",
				name : "serverPort",
				newline : false,
				type : "text",
				validate : {
					required : true,
					maxlength : 50
				}
			},  {
				display : "服务路径",
				name : "serverPath",
				newline : true,
				width : 503,
				type : "textarea",
				validate : {
					required : true,
					maxlength : 100
				}
			}, {
				display : "备注",
				name : "remark",
				newline : true,
				width : 503,
				type : "textarea",
				validate : {
					maxlength : 100
				}
			}]
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
		
		BIONE.addFormButtons(buttons);
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate("#mainform");
		BIONE.loadForm(mainform, {
			url : "${ctx}/bione/serverinfo/info.json",
			data : {
				id : "${id}"
			}
		});
	});
	function f_save() {
		BIONE.submitForm($("#mainform"), function() {

			BIONE.tip("保存成功");
			parent.grid.loadData();
			BIONE.closeDialog("server");
		}, function() {
			BIONE.tip("保存失败");
		});
	}
	function f_close() {
		BIONE.closeDialog("server");
	}
	
</script>
</head>
<body>
	<div id="template.center">
		<form action="${ctx}/bione/serverinfo" method="post" id="mainform">
		</form>
	</div>
</body>
</html>