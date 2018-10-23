<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template1.jsp">
<head>
<!-- 基础的JS和CSS文件 -->
<script type="text/javascript">
	var grid;
	
	$(function() {
		initSearchForm();
		initGrid();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});

	
	function initSearchForm(){
		$("#search").ligerForm({
			fields : [ {
				display : '操作人',
				name : "operUser",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					field : 'operUser',
					op : "like"
				}
			}, {
				id:'beginTime',
				display : '开始时间',
				newline : false,
				cssClass : "field",
				name:'occurTime',
				type:'date',
				attr:{
					field:'occurTime',
					vt:'date',
					op:">="
				}
			},{
				display : '结束时间',
				newline : false,
				cssClass : "field",
				name:'endTime',
				type:'date',
				attr:{
					field:'endTime',
					vt:'date',
					op:"<="
				}
			}
		]
		});
	};
	
	function initGrid(){
		grid = $("#maingrid").ligerGrid({
			checkbox : true,
			columns : [ {
				display : '逻辑系统',
				name : 'logicSysNo',
				width : "15%",
				align : 'left'
			}, {
				display : '操作人',
				name : 'operUser',
				width : "10%",
				align : 'left'
			},{
				display:'IP地址',
				name:'loginIP',
				width:"10%",
				align:'left'
			}, {
				display : '发生事件',
				name : 'logEvent',
				width : "40%",
				align : 'left'
			}, {
				display : '发生时间',
				name : 'occurTime',
				width : "15%",
				type : 'date'
			} ],
			dataAction : 'server', //从后台获取数据
			usePager : true, //服务器分页
			alternatingRow : true, //附加奇偶行效果行
			colDraggable : true,
			url : "${ctx}/bione/admin/log/list.json?t="+new Date(),
			sortName : 'occurTime',//第一次默认排序的字段
			sortOrder : 'desc', //排序的方式
			rownumbers : true,
			width:'100%'
		});
	}
	
</script>
</head>
<body>
</body>
</html>