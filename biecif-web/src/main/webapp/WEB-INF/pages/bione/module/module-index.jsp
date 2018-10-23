<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];

	$(init);

	function init() {
		url = "${ctx}/bione/admin/module/list.json";
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		/*	labelWidth : 100,
		 	inputWidth : 220,
			space : 30, */
			fields : [ {
				display : "模块标识",
				name : "moduleNo",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "module.moduleNo"
				}
			}, {
				display : "模块名称",
				name : "moduleName",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "module.moduleName"
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
								display : '模块标识',
								name : 'moduleNo',
								align : 'center',
								minWidth : '15%',
								width : '22%'
							},
							{
								display : '模块名称',
								name : 'moduleName',
								align : 'center',
								minWidth : '15%',
								width : '22%'
							},
							{
								display : '备注',
								name : 'remark',
								align : 'center',
								minWidth : '50%',
								width : '53%'
							} ],
					checkbox : false,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true, /* 附加奇偶行效果行 */
					colDraggable : true,	/* 是否允许表头拖拽 */
					dataAction : 'server',	/* 从后台获取数据 */
					method : 'post',
					url : url,
					sortName : 'moduleNo', //第一次默认排序的字段
					sortOrder : 'asc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [ {
			text : '增加',
			click : modlue_add,
			icon : 'add',
			operNo : 'module_add'
		}, {
			text : '修改',
			click : module_modify,
			icon : 'modify',
			operNo : 'module_modify'
		}, {
			text : '删除',
			click : module_delete,
			icon : 'delete',
			operNo : 'module_delete'
		}, {
			text : '配置',
			click : module_config,
			icon : 'config',
			operNo : 'module_config'
		} ];
		BIONE.loadToolbar(grid, btns, function() { });
	}

	function module_config(item) {
		achieveIds();
		if (ids.length == 1) {
			BIONE.commonOpenDialog("模块功能配置", "moduleFunc", 900, 450, 
					"${ctx}/bione/admin/func/" + ids[0]+"/index", null);
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}

	function modlue_add(item) {
		BIONE.commonOpenLargeDialog('添加模块', 'moduleUpdate',
				'${ctx}/bione/admin/module/new');
	}
	function module_modify(item) {
		achieveIds();
		if (ids.length == 1) {
			BIONE.commonOpenLargeDialog('修改模块', 'moduleUpdate',
					'${ctx}/bione/admin/module/' + ids[0] + '/edit');
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	function module_delete(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					$.ajax({
						cache : false,
						async : false,
						type : "DELETE",
						url : "${ctx}/bione/admin/module/" + ids.join(','),
						dataType : "text",
						success : function(result) {
							if(result && result != "") {
								$.ligerDialog.error("模块 [ " + result + " ] 有功能正在使用!", "错误", function() {});
								if(result.split(", ").length < ids.length) {
									grid.loadData();
								}
							} else {
								BIONE.tip('删除成功');
								grid.loadData();
							}
						},
						error : function() {
							BIONE.tip('删除失败');
						}
					});
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
			ids.push(rows[i].moduleId);
		}
	}
</script>
</head>
<body>
</body>
</html>