<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrelative/actualcontroller/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '实际控制人类型',
				name : 'actualCtrlType',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '实际控制人性别',
				name : 'actualCtrlGender',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '实际控制人出生日期',
				name : 'actualCtrlBirthday',
				align : 'center',
				width : 150,
				minWidth : 120
			} , {
				display : '实际控制人移动电话',
				name : 'actualCtrlMobile',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '实际控制人固定电话',
				name : 'actualCtrlTel',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '实际控制人名称',
				name : 'actualCtrlName',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '实际控制人证件类型',
				name : 'actualCtrlIdentType',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '实际控制人证件号码',
				name : 'actualCtrlIdentNo',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '实际控制人组织机构类型',
				name : 'actualCtrlOrgType',
				align : 'center',
				width : 150,
				minWidth : 150
			}, {
				display : '实际控制人证件是否年检',
				name : 'actualCtrlIdentCheck',
				align : 'center',
				width : 180,
				minWidth : 150
			}, {
				display : '实际控制人与法定代表人关系',
				name : 'actualCtrlLegalReprRel',
				align : 'center',
				width : 180,
				minWidth : 160
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
			sortName : 'custId',//第一次默认排序的字段
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