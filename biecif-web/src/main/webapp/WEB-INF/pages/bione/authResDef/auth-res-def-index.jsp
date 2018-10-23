<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];

	$(function() {
		url = "${ctx}/bione/admin/authResDef/list.json";
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});

	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		/*	labelWidth : 100,
		 	inputWidth : 220,
			space : 30, */
			fields : [ {
				display : "资源标识",
				name : "resDefNo",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "authResDef.resDefNo"
				}
			}, {
				display : "资源名称",
				name : "resName",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "authResDef.resName"
				}
			} ]
		});

	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '资源标识',
								name : 'resDefNo',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '资源名称',
								name : 'resName',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '实现类名称',
								name : 'beanName',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '备注',
								name : 'remark',
								align : 'center',
								width : '33%',
								minWidth : '10%'
							} ],
					checkbox : true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortName : 'resDefNo', //第一次默认排序的字段
					sortOrder : 'asc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [ {
			text : '增加',
			click : resDef_add,
			icon : 'add',
			operNo : 'resDef_add'
		}, {
			text : '修改',
			click : resDef_modify,
			icon : 'modify',
			operNo : 'resDef_modify'
		}, {
			text : '删除',
			click : resDef_delete,
			icon : 'delete',
			operNo : 'resDef_delete'
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	function resDef_add(item) {
		BIONE.commonOpenLargeDialog('添加资源', 'resDefManage',
				'${ctx}/bione/admin/authResDef/new');
	}
	function resDef_modify(item) {
		achieveIds();
		if (ids.length == 1) {
			var buttons = [
					{
						text : '保存',
						onclick : function(item, dialog) {
							BIONE.submitForm($("#mainform",dialog.frame.window.document), function() {
								dialog.close();
								initGrid();
								parent.BIONE.tip('修改成功');
							}, function() {
								BIONE.tip('保存失败');
							});
						}
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ];
			BIONE.commonOpenLargeDialog('修改资源', 'resDefManage',
					'${ctx}/bione/admin/authResDef/' + ids[0] + '/edit', buttons);
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	function resDef_delete(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "DELETE",
						url : "${ctx}/bione/admin/authResDef/" + ids.join(','),
						dataType : "script",
						success : function() {
							flag = true;
						}
					});
					if (flag == true) {
						BIONE.tip('删除成功');
						initGrid();
					} else {
						BIONE.tip('删除失败');
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
			ids.push(rows[i].resDefNo);
		}
	}
</script>
</head>
<body>
</body>
</html>