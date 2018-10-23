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
		var url = "${ctx}/bione/mtool/dataset/getSysVar.json";//URL
		ligerSearchForm();//初始化查询表单
		ligerGrid();//初始化GRID
		BIONE.addSearchButtons("#search", grid, "#searchbtn");//生成搜索按钮

		//渲染查询表单
		function ligerSearchForm() {
			$("#search").ligerForm({
				fields : [ {
					display : "变量标识",
					name : "varNo_name",
					newline : true,
					type : "text",
					labelWidth : 65,
					cssClass : "field",
					attr : {
						field : "varNo",
						op : "like"
					}
				}, {
					display : "变量名称",
					name : "varName_name",
					newline : false,
					type : "text",
					labelWidth : 65,
					cssClass : "field",
					attr : {
						field : "varName",
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
							display : "变量标识",
							name : "varNo",
							width : "15%"
						}, {
							display : "变量名称",
							name : "varName",
							width : "20%"
						}, {
							display : "变量值",
							name : "varValue",
							width : "22%"
						}, {
							display : "备注",
							name : "remark",
							width : "34%"
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
							w.$("#mainform [name='querySql']").insertAtCaret(
									'$' + rowdata.varNo + '$');
							BIONE.closeDialog("selectGrid");
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