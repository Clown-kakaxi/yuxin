<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid;
	var url;
	var dialog;
	$(init);

	function init() {
		url = "${ctx}/bione/variable/sysvar/getAllDsInfo.json";
		searchForm();
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	//搜索表单
	function searchForm() {
		$("#search").ligerForm({
			fields : [ {
				display : '数据源名称',
				name : 'ds.dsName_code',
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					field : "dsName",
					op : "like"
				}
			}, {
				display : '逻辑系统标识',
				name : 'ds.logicSysNo_code',
				newline : false,
				type : "text",
				attr : {
					field : "logicSysNo",
					op : "like"
				}
			}]
		});
	}

	//渲染表单
	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			InWindow : true,
			height : '200',
			width : '100%',
			columns : [ {
				display : '数据源名称',
				name : 'dsName',
				align : 'left',
				width : '23%'
			}, {
				display : '逻辑系统标识',
				name : 'logicSysNo',
				align : 'left',
				width : '23%'
			}, {
				display : '连接URL',
				name : 'connUrl',
				align : 'left',
				width : '24%'
			}, {
				display : '备注',
				name : 'remark',
				align : 'left',
				width : '24%'
			} ],
			checkbox : false,
			usePager : true,
			isScroll : false,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'dsName', //第一次默认排序的字段
			sortOrder : 'asc',
			onDblClickRow : function(rowdata, rowindex, rowDomElement){//双击选择
				window.parent.addNewDsName.val(rowdata.dsName);
				window.parent.addNewDsId.val(rowdata.dsId);
				window.parent.addNewDsName.focusout();
				BIONE.closeDialog("addNewDs");
			},
			toolbar : {}
		});
		
	}
</script>
</head>
<body>
</body>
</html>