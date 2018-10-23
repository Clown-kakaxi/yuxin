<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
	$(function() {
		var groupicon = "${ctx}/images/classics/icons/communication.gif";
		var paramTypeNo = '${paramTypeNo}';
		var paramTypeName = '${paramTypeName}';
		var upNo = '${upNo}';
		$("#mainform")
				.ligerForm(
						{
							fields : [
									{
										display : '参数类型标识',
										name : 'paramTypeNo',
										newline : true,
										type : 'hidden',
										validate : {
											required : true,
											maxlength : 32
										}
									},
									{
										group : "参数值",
										groupicon : groupicon,
										display : '参数类型',
										name : 'paramTypeName',
										newline : true,
										type : 'text',
										validate : {
											required : true,
											maxlength : 32
										}
									},
									{
										display : '参数名称',
										name : 'paramName',
										newline : false,
										type : 'text',
										validate : {
											required : true,
											maxlength : 100
										}
									},
									{
										display : '参数值',
										name : 'paramValue',
										type : 'text',
										validate : {
											required : true,
											maxlength : 500,
											remote : "${ctx}/bione/variable/param/testParamValue?paramTypeId=${paramTypeId}",
											messages : {
												remote : "参数值已存在"
											}
										}
									},
									{
										display : '顺序编号',
										name : 'orderNo',
										newline : false,
										type : 'text',
										validate : {
											required : true,
											number : "请输入合法的数字",
											maxlength : 5
										}
									},
									{
										display : '参数上级代码',
										name : 'upNo',
										newline : true,
										type : 'hidden'
									},
									{
										display : '备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',
										name : 'remark',
										newline : true,
										width : '503',
										validate : {
											maxlength : 500
										},
										type : 'textarea',
										attr : {
											style : "resize : none"
										}
									} ],
							toJSON : JSON2.stringify
						});
		if (paramTypeNo != null) {
			$("#mainform input[name=paramTypeNo]").val(paramTypeNo);
			$("#mainform input[name=paramTypeNo]").attr("readonly", "readonly");
			$("#mainform input[name=paramTypeName]").val(paramTypeName);
			$("#mainform input[name=paramTypeName]").attr("readonly",
					"readonly");
			$("#mainform input[name=upNo]").val(upNo).attr("readonly",
					"readonly");
		} else {
			$("#mainform input[name=paramTypeNo]").val(paramTypeNo);
			$("#mainform input[name=upNo]").val("0").attr("readonly",
					"readonly");
		}
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
		var buttons = [];
		buttons.push({
			text : '取消',
			onclick : function() {
				BIONE.closeDialog("paramAddWin");
			}
		});
		buttons.push({
			text : '保存',
			onclick : f_save
		});

		BIONE.addFormButtons(buttons);
	});

	function f_save() {
		BIONE.submitForm($("#mainform"),
				function() {
					BIONE.closeDialogAndReloadParent("paramAddWin", "maingrid",
							"添加成功");
				}, function() {
					BIONE.closeDialog("paramAddWin", "添加失败");
				});
	}
</script>
</head>
<body>
	<div id="template.center">
		<form name="mainform" method="post" id="mainform"
			action="${ctx}/bione/variable/param"></form>
	</div>
</body>
</html>