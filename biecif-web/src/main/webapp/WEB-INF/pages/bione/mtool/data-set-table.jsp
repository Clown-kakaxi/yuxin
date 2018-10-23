<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	//全局变量
	var grid;
	var dialog;

	$(function() {
		//初始化
		var url = "${ctx}/bione/mtool/dataset/tables.json?dsId=${dsId}";//URL
		ligerSearchForm();//初始化查询表单
		ligerGrid();//初始化GRID
		BIONE.addSearchButtons("#search", grid, "#searchbtn");//生成搜索按钮

		//渲染查询表单
		function ligerSearchForm() {
			$("#search").ligerForm({
				fields : [ {
					display : "表名",
					name : "tableName_box",
					newline : true,
					type : "text",
					labelWidth : 65,
					cssClass : "field",
					attr : {
						field : "tableName",
						op : "like"
					}
				}, {
					display : "注释",
					name : "tableComment_box",
					newline : false,
					type : "text",
					labelWidth : 65,
					cssClass : "field",
					attr : {
						field : "tableComment",
						op : "like"
					}
				} ]
			});
		}

		//渲染GRID
		function ligerGrid() {
			grid = $("#maingrid").ligerGrid(
					{
						InWindow : false,
						width : "100%",
						height : "98%",
						InWindow : true,
						columns : [ {
							display : "表名",
							name : "tableName",
							width : "45%",
							isSort : false
						}, {
							display : "注释",
							name : "tableComment",
							width : "45%",
							isSort : false
						} ],
						checkbox : false,
						userPager : true,
						rownumbers : true,
						alternatingRow : true,//附加奇偶行效果行
						colDraggable : true,
						dataAction : 'server',//从后台获取数据
						method : 'post',
						url : url,
						onDblClickRow : function(rowdata, rowindex,
								rowDomElement) {//双击选择
							var w = window.parent.document.getElementById("tab1frame").contentWindow;
							w.$("#mainform [name='table_box']").val(
									rowdata.tableName);
							w.$("#mainform").valid();
							w.BIONE.closeDialog("selectGrid");
						}
					});
		}
	});
</script>
<title>Insert title here</title>
</head>
<body>
</body>
</html>