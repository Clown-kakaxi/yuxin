<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/image/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '影像信息类型',
				name : 'imageInfoType',
				align : 'left',
				width : 100,
				minWidth : 100
			}, {
				display : '影像信息索引',
				name : 'imageInfoIndex',
				align : 'center',
				width : 300,
				minWidth : 200
			}
// 			, {
// 				display : '最后更新系统',
// 				name : 'lastUpdateSys',
// 				align : 'center',
// 				width : 100,
// 				minWidth : 80
// 			}, {
// 				display : '最后更新人',
// 				name : 'lastUpdateUser',
// 				align : 'center',
// 				width : 100,
// 				minWidth : 80
// 			} , {
// 				display : '最后更新时间',
// 				name : 'lastUpdateTm',
// 				align : 'center',
// 				width : 100,
// 				minWidth : 80
// 			}
			],
			checkbox : false,
			usePager : true,
			isScroll : true,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'imageInfoId',//第一次默认排序的字段
			sortOrder : 'asc', //排序的方式
			pageParmName : 'page',
			pagesizeParmName : 'pagesize',
			toolbar : {}
		});

	});
</script>
</head>
<body>
</body>
</html>