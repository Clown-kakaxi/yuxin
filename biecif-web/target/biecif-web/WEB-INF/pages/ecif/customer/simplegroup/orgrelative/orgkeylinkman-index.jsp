<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template18.jsp">
<script type="text/javascript">
	var dialog;
	var custId = "${custId}";
	url = "${ctx}/ecif/orgrelative/orgkeylinkman/list.json?custId=" + custId;
	$(function() {

		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '联系人姓名',
				name : 'linkmanName',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '联系人证件类型',
				name : 'linkmanIdentType',
				align : 'left',
				width : 150,
				minWidth : 120
			} , {
				display : '联系人证件号码',
				name : 'linkmanIdentNo',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '联系人证件有效期',
				name : 'linkmanIdentLimit',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '联系人联系电话',
				name : 'linkmanTel',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '联系人英文名',
				name : 'linkmanEnName',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '联系人负责业务类型',
				name : 'linkmanBusiType',
				align : 'center',
				width : 120,
				minWidth : 120
			}, {
				display : '是否法定代表人',
				name : 'isLegalRepr',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '职务',
				name : 'duty',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '担任职务时间',
				name : 'workTime',
				align : 'center',
				width : 120,
				minWidth : 100,
				type : "date"
			}, {
				display : '现单位工作时间',
				name : 'currUnitStartDate',
				align : 'center',
				width : 120,
				minWidth : 100,
				type : "date"
			}, {
				display : '重要程度',
				name : 'importantLevel',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '是否我行个人客户',
				name : 'isPersonCustomer',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '营销主要关键要素',
				name : 'mainMarketingElement',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '关系背景',
				name : 'relaBackground',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '持股情况',
				name : 'shareholding',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '个人爱好',
				name : 'hobby',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '人员类别',
				name : 'kind',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '国籍',
				name : 'citizenship',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '民族',
				name : 'nationality',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '籍贯',
				name : 'nativeplace',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '出生日期',
				name : 'birthday',
				align : 'center',
				width : 100,
				minWidth : 80,
				type : "date"
			}, {
				display : '性别',
				name : 'gender',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '宗教信仰',
				name : 'religiousBelief',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '婚姻状况',
				name : 'marriage',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '学历',
				name : 'highestSchooling',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '毕业学校',
				name : 'graduateSchool',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '相关行业从业年限',
				name : 'workYear',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '月收入',
				name : 'monthIncome',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '主要经济来源',
				name : 'mainEconomicSource',
				align : 'center',
				width : 120,
				minWidth : 100
			}, {
				display : '其他经济来源',
				name : 'otherEconomicSource',
				align : 'center',
				width : 120,
				minWidth : 90
			}, {
				display : '职称',
				name : 'title',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '社会职务',
				name : 'societyDuty',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '工作经历',
				name : 'career',
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
				display : '办公电话',
				name : 'officeMobile',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '家庭电话',
				name : 'homeTel',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '电子邮件地址（办公）',
				name : 'officeEmail',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '电子邮件地址（个人）',
				name : 'email',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '办公地址',
				name : 'officeAddr',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '家庭住址',
				name : 'officeAddress',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '家庭供养人口',
				name : 'supplyPopNum',
				align : 'center',
				width : 100,
				minWidth : 100
			}, {
				display : '备注',
				name : 'remark1',
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
			sortName : 'linkmanInfoId',//第一次默认排序的字段
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