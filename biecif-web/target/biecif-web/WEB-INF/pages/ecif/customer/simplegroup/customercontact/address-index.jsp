<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/customercontact/address/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [ {
				display : '地址类型',
				name : 'addrType',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '详细地址',
				name : 'addr',
				align : 'center',
				width : 180,
				minWidth : 150
			}, {
				display : '联系信息',
				name : 'contmethInfo',
				align : 'left',
				width : 180,
				minWidth : 150
			}, {
				display : '邮政编码',
				name : 'zipcode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '国家或地区代码',
				name : 'countryOrRegion',
				align : 'center',
				width : 100,
				minWidth : 100
			} , {
				display : '行政区划代码',
				name : 'adminZone',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '大区代码',
				name : 'areaCode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '省直辖市自治区代码',
				name : 'provinceCode',
				align : 'center',
				width : 120,
				minWidth : 120
			}, {
				display : '市地区州盟代码',
				name : 'cityCode',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '县区代码',
				name : 'countyCode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '乡镇代码',
				name : 'townCode',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '乡镇名称',
				name : 'townName',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '街道名称',
				name : 'streetName',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '行政村名称',
				name : 'viliageName',
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
			sortName : 'txSeqNo',//第一次默认排序的字段
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