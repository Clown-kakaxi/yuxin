<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/agreement/cesubcredit/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '序号',
				name : 'id',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '授信编号',
				name : 'creditid',
				align : 'center',
				width : 100,
				minWidth : 80
			},{
				display : '客户名称',
				name : 'custname',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '审定授信额度',
				name : 'finalsubcreditlimit',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '币种',
				name : 'currency',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '拟报送分项授信额度',
				name : 'subcreditlimit',
				align : 'center',
				width : 130,
				minWidth : 130
			}, {
				display : '审批状态',
				name : 'appstate',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '分项授信种类',
				name : 'productcredittype',
				align : 'center',
				width : 100,
				minWidth : 80
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
			sortName : 'contrId',//第一次默认排序的字段
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