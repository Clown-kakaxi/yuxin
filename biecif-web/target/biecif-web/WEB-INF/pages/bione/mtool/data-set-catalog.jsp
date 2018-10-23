<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
	var mainform;

	$(function() {

		var groupicon = "${ctx}/images/classics/icons/communication.gif";
		var catalogId = "${catalogId}";

		//渲染表单
		mainform = $("#mainform");
		mainform
				.ligerForm({
					inputWidth : 160,
					labelWidth : 80,
					space : 30,
					fields : [
							{
								name : "upId",
								type : "hidden"
							},
							{
								name : "catalogId",
								type : "hidden"
							},
							{
								display : "目录名称",
								name : "catalogName",
								labelWidth : 90,
								width : 250,
								newline : false,
								type : "text",
								validate : {
									required : true,
									maxlength : 100,
									remote : "${ctx}/bione/mtool/dataset/catalogNameCanUse?catalogId=${catalogId}&upId=${upId}&d="
											+ new Date(),
									messages : {
										required : "目录名称不能为空。",
										remote : "该路径下已存在同名目录。"
									}
								},
								group : "目录信息",
								groupicon : groupicon
							}, {
								display : "描述",
								name : "catalogDesc",
								labelWidth : 90,
								width : 250,
								newline : true,
								type : "textarea",
								validate : {
									maxlength : 500
								}
							} ]

				});

		jQuery.metadata.setType("attr", "validate");
		BIONE.validate("#mainform");

		//表单赋值
		$("#mainform input[name='catalogId']").val("${catalogId}");
		$("#mainform input[name='upId']").val("${upId}");

		//修改时初始化表单
		if (catalogId != "" && catalogId != null) {
			BIONE.loadForm(mainform,{
				type : "POST",
				url : "${ctx}/bione/mtool/dataset/getCatalogInfo.json?catalogId="
					+ catalogId+"&d="+new Date().getTime()});
		}

		//添加按钮
		var btns = [ {
			text : "取消",
			onclick : function() {
				BIONE.closeDialog("editCatalogBox");
			}
		}, {
			text : "保存",
			onclick : f_save
		} ];
		BIONE.addFormButtons(btns);

		//保存
		function f_save() {
			var treeObj = window.parent.leftTreeObj;
			var currentNode = window.parent.currentNode;

			BIONE.submitForm($("#mainform"), function() {
				var parentNode = null;
				if (catalogId != "" && catalogId != null){
					parentNode = currentNode.getParentNode();
					window.parent.currentNode = null;
				}
				else
					parentNode = currentNode;
				treeObj.reAsyncChildNodes(parentNode, "refresh");
				BIONE.closeDialog("editCatalogBox", "保存成功");
			}, function() {
				BIONE.tip("editCatalogBox", "保存失败");
			});
		}

	});
</script>
<title>Insert title here</title>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/bione/mtool/dataset/saveCatalog"
			method="POST"></form>
	</div>
</body>
</html>