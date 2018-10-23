<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [], test = [];
	var testId;
	var custId = "${custId}";
	$(init);

	function init() {
		url = "${ctx}/ecif/perbasic/eduresume/list.json?custId=" + custId;
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '所在学校',
				name : 'university',
				align : 'left',
				width : 150,
				minWidth : 120
			}, {
				display : '所在院系',
				name : 'college',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '专业',
				name : 'major',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '学制',
				name : 'eduSys',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '学历证书号',
				name : 'certificateNo',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '学位证书号',
				name : 'diplomaNo',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '开始日期',
				name : 'startDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '结束日期',
				name : 'endDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}],
			checkbox : false,
			usePager : true,
			isScroll : false,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'custId', //第一次默认排序的字段
			sortOrder : 'asc',
			toolbar : {}
		});
	}
</script>
</head>
<body>
</body>
</html>