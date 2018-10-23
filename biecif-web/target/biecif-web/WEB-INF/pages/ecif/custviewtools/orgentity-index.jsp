<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template0.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];

	$(function() {
		url = "${ctx}/ecif/orgcustviewtools/list.json";
		//searchForm();
		initGrid();
		initButtons();
	});

	function RenderFlag(rowdata){
		if(rowdata.state == '1'){
			return '有效';
		}else if(rowdata.state == '0'){
			return '无效';
		}else{
			return rowdata.state;
		}
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '一级分类名称',
								name : 'custMenu1Name',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '实体名称',
								name : 'custMenu2Name',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '实体对象',
								name : 'entityName',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							}],
					checkbox : true,
					usePager : false,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortOrder : 'asc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [ {
			text : '生成工程文件',
			click : resDef_delete,
			icon : 'add',
			operNo : 'resDef_add'
		}];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	function resDef_delete(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要生成这些文件吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "POST",
						url : "${ctx}/ecif/orgcustviewtools/" + ids.join(','),
						dataType : "script",
						success : function() {
							flag = true;
						}
					});
					if (flag == true) {
						BIONE.tip('生成成功');
						initGrid();
					} else {
						BIONE.tip('生成失败');
					}
				}
			});
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	// 获取选中的行
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i].entityName);
		}
		
	}
</script>
</head>
<body>
</body>
</html>