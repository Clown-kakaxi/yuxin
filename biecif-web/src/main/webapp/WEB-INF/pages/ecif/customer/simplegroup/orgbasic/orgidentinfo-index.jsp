<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgbasic/orgidentinfo/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '证件类型',
				name : 'identType',
				align : 'left',
				width : 150,
				minWidth : 120
			}, {
				display : '证件号码',
				name : 'identNo',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '证件户名',
				name : 'identCustName',
				align : 'left',
				width : 200,
				minWidth : 180
			}, {
				display : '证件描述',
				name : 'identDesc',
				align : 'center',
				width : 100,
				minWidth : 80
			} , {
				display : '发证国家或地区',
				name : 'countryOrRegion',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '发证机构',
				name : 'identOrg',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '发证批准单位',
				name : 'identApproveUnit',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '证件年检标志',
				name : 'identCheckFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '证件年检到期日',
				name : 'identCheckingDate',
				align : 'center',
				width : 100,
				minWidth : 100,
				type : "date"
			}, {
				display : '证件年检日期',
				name : 'identCheckedDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '证件有效期',
				name : 'identValidPeriod',
				align : 'center',
				width : 100,
				minWidth : 80
			}, /* {
				display : '证件生效日期',
				name : 'identEffectiveDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			},  */{
				display : '证件失效日期',
				name : 'identExpriedDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '证件有效标志',
				name : 'identValidFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '证件期限',
				name : 'identPeriod',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '校验日期',
				name : 'verifyDate',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '校验员工',
				name : 'verifyEmployee',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '校验结果',
				name : 'verifyResult',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '主辅证件标志',
				name : 'mainIdentFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '开户证件标志',
				name : 'openIdentFlag',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '更新时间',
				name : 'lastUpdateTm',
				align : 'center',
				width : 135,
				minWidth : 120,
				type : "date",
				format : 'yyyy-MM-dd hh:mm:ss'
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