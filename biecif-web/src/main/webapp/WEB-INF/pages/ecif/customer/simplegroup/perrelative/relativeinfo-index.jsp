<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/perrelative/relative/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '亲属关系',
				name : 'relativeType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '亲属姓名',
				name : 'relativeName',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '证件类型',
				name : 'identType',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '证件号码',
				name : 'identNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '性别',
				name : 'gender',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '固定电话',
				name : 'tel',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '手机号码',
				name : 'mobile',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '月收入工资',
				name : 'monthIncome',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '亲属身体状况',
				name : 'healthStat',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '亲属现家庭住址',
				name : 'homeAddr',
				align : 'center',
				width : 100,
				minWidth : 100
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
			sortName : 'relativeId',//第一次默认排序的字段
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