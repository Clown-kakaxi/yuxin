<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	//全局变量
	var grid;
	var dialog;

	$(function() {
		var parent = window.parent;
		var catalogId = "";
		var catalogName = "";

		//初始化
		ligerSearchForm();
		ligerGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");

		//渲染查询表单
		function ligerSearchForm() {
			$("#search").ligerForm({
				fields : [ {
					display : "数据源",
					name : "ds_box",
					newline : true,
					type : "select",
					cssClass : "field",
					attr : {
						field : "dsId",
						op : "="
					},
					options : {
						valueFieldID : "dsId",
						url : "${ctx}/bione/mtool/dataset/dsList.json"
					}
				}, {
					display : "数据集名称",
					name : "dataset_box",
					newline : false,
					type : "text",
					cssClass : "field",
					attr : {
						field : "datasetName",
						op : "like"
					}
				}, {
					name : "catalogId_box",
					newline : false,
					type : "hidden",
					cssClass : "field",
					attr : {
						field : "catalogId",
						op : "="
					}
				} ]
			});
		}

		//渲染GRID
		function ligerGrid() {
			parent.datasetGrid = grid = $("#maingrid").ligerGrid({
				width : "100%",
				height : "99%",
				InWindow : true,
				columns : [ {
					display : "数据集名称",
					name : "datasetName",
					width : "20%"
				}, {
					display : "数据源",
					name : "dsName",
					width : "25%"
				}, {
					display : "数据源类型",
					name : "dsType",
					width : "15%",
					render : function(row) {
						if (row.dsType == "01")
							return "数据库表";
						else if (row.dsType == "02")
							return "标准SQL";
						else
							return "未知";
					}
				}, {
					display : "备注",
					name : "remark",
					width : "30%"
				} ],
				delayLoad : true,//初始化时不加载数据
				checkbox : true,
				userPager : true,
				rownumbers : true,
				alternatingRow : true,//附加奇偶行效果行
				colDraggable : false,
				dataAction : 'server',//从后台获取数据
				method : 'post',
				url : "${ctx}/bione/mtool/dataset/setList.json",
				sortName : 'datasetName', //第一次默认排序的字段
				sortOrder : 'asc',
				toolbar : {}
			});
			//设置参数
			if (parent.currentNode) {
				catalogId = parent.currentNode.realId;
				catalogName = parent.currentNode.text;
			}
			grid.set('parms', {
				catalogId : catalogId,
				catalogName : catalogName,
				d : new Date().getTime()
			});
			$("input[name='catalogId_box']").val(catalogId);
			if (parent.currentNode) {
				grid.loadData();
			}
			parent.catalogBox = $("input[name='catalogId_box']");
		}

		//初始化按钮
		function initButtons() {
			var btns = [ {
				text : "添加",
				icon : "add",
				click : dataset_add,
				operNo : "dataset_add"
			}, {
				text : "修改",
				icon : "modify",
				click : dataset_modify,
				operNo : "dataset_modify"
			}, {
				text : "删除",
				icon : "delete",
				click : dataset_delete,
				operNo : "dataset_delete"
			}, {
				text : "预览",
				icon : "process",
				click : dataset_preview,
				operNo : "dataset_preview"
			} ];

			BIONE.loadToolbar(grid, btns, function() {
			});
		}

		//新增
		function dataset_add(type) {
			if (parent.currentNode) {
				var curCatalogName = parent.currentNode.text;
				window.parent.curCatalogName = curCatalogName;
				dialog = window.parent.BIONE.commonOpenLargeDialog("添加数据集",
						"datasetBox",
						"${ctx}/bione/mtool/dataset/infoFrame?catalogId="
								+ parent.currentNode.realId , null);
			} else {
				BIONE.tip("请选择一个目录。");
			}
		}

		//修改
		function dataset_modify() {
			if (parent.currentNode) {
				var rows = grid.getSelectedRows();
				if (rows.length == 1) {
					var curCatalogName = parent.currentNode.text;
					window.parent.curCatalogName = curCatalogName;
					dialog = window.parent.BIONE.commonOpenLargeDialog("修改数据集",
							"datasetBox",
							"${ctx}/bione/mtool/dataset/infoFrame?datasetId="
									+ rows[0].datasetId + "&catalogId="
									+ parent.currentNode.realId
									);
				} else {
					BIONE.tip("请选择一条记录");
				}
			} else {
				BIONE.tip("请选择一个目录。");
			}
		}

		//删除
		function dataset_delete() {
			var rows = grid.getSelectedRows();
			if (rows.length > 0) {
				//拼接待删除条目ID
				parent.$.ligerDialog.confirm('确定要删除选定的数据集吗', function(flag) {
					if (flag) {
						var ids = "";
						for ( var i = 0, l = rows.length; i < l; i++) {
							ids += rows[i].datasetId + ",";
						}
						ids = ids.substring(0, ids.length - 1);
						$.ajax({
							type : "POST",
							url : "${ctx}/bione/mtool/dataset/deleteDataset",
							dataType : "json",
							data : {
								ids : ids
							},
							success : function(res) {
								BIONE.tip("删除成功！");
								grid.loadData();
							},
							error : function(XMLHttpRequest, textStatus,
									errorThrown) {
								BIONE.tip('删除失败,错误信息:' + textStatus);
							}
						});
					}
				});
			} else {
				BIONE.tip('请选择要删除的记录。');
			}
		}

		//预览
		function dataset_preview() {
			var rows = grid.getSelectedRows();
			if (rows.length == 1) {
				dialog = window.parent.BIONE.commonOpenLargeDialog("数据集预览",
						"previewBox",
						"${ctx}/bione/mtool/dataset/preview?datasetId="
								+ rows[0].datasetId, null);
			} else {
				BIONE.tip("请选择一条记录");
			}

		}
	});
</script>
<title>Insert title here</title>
</head>
<body>
</body>
</html>