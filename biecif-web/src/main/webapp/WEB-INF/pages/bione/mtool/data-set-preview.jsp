<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	//全局变量
	var grid;

	$(function() {

		//渲染grid
		ligerGrid();
		ligerForm();

		function ligerForm() {
			//Form结构
			var formStructure = eval("${formStructure}");

			$("#search").ligerForm({
				fields : formStructure
			});

			if ("${formStructure}" != null && $.trim("${formStructure}") != "") {
				BIONE.addSearchButtons("#search", grid, "#searchbtn");//生成搜索按钮
			} else {
				$("#search").html("<font text='12px'>未设置任何查询项</font>");
				$("#searchbtn").remove();
			}
		}

		//渲染GRID
		function ligerGrid() {
			//Grid结构
			var gridStructure = eval("${gridStructure}");

			$("#maingrid")
					.before(
							'	<div id="tipMainDiv" style="width:100%;height:24px;margin:0 auto;overflow: hidden;position: relative;border:1px solid gray;padding-top:1px;padding-bottom:1px;"><div id="tipContainerDiv" style="padding:4px;background:#fffee6;color:#8f5700;"><div style="width:24px;float:left;height:14px;background:url(${ctx}/images/classics/icons/comment_yellow.gif) no-repeat" /><div id="tipAreaDiv">tips : 此处仅显示数据集中前100条数据。<br /></div></div></div><div style="height:2px;"></div>');
			//初始化grid
			grid = $("#maingrid")
					.ligerGrid(
							{
								width : "100%",
								height : "99%",
								columns : gridStructure,
								dataAction : 'local',
								method : 'post',
								url : "${ctx}/bione/mtool/dataset/previewData.json?datasetId=${datasetId}&d="
										+ new Date(),
								checkbox : false,
								rownumbers : true,
								usePager : true,
								alternatingRow : true,//奇偶行效果行
								colDraggable : false,
								rowDraggable : false,
								onSuccess : function(data) {
									if (!!data.errorMsg) {
										BIONE.tip(data.errorMsg);
									}
								},
								delayLoad : false
							//初始化时不加载数据
							});
		}

		//添加表单按钮
		var btns = [ {
			text : "确定",
			onclick : function() {
				BIONE.closeDialog("previewBox");
			}
		} ];
		BIONE.addFormButtons(btns);
	});
</script>
<title>Insert title here</title>
</head>
<body>
</body>
</html>