<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template1.jsp">
<head>
<!-- 基础的JS和CSS文件 -->
<script type="text/javascript">
	var grid;
	var ids;
	var grpId;

	function initSearchForm() {
		// 	parent.window.userAttr = window;
		//搜索表单应用ligerui样式
		$("#search").ligerForm({
			fields : [ {
				name : "grpId",
				type : "hidden",
				attr : {
					field : 'grpId',
					op : "="
				}
			}, {
				display : '字段名称',
				name : "fieldName",
				newline : true,
				labelWidth : 100,
				width : 220,
				space : 30,
				type : "text",
				cssClass : "field",
				attr : {
					field : 'fieldName',
					op : "like"
				}
			}, {
				display : '标签名称',
				name : 'labelName',
				newline : false,
				labelWidth : 100,
				width : 220,
				space : 30,
				type : "text",
				cssClass : "field",
				attr : {
					field : 'labelName',
					op : "like"
				}
			} ]
		});
	};
	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			toolbar : {},
			checkbox : true,
			columns : [ {
				display : '字段名称',
				name : 'fieldName',
				width : "25%",
				align : 'left'
			}, {
				display : '标签名称',
				name : 'labelName',
				width : "25%",
				align : 'left'
			}, {
				display : '属性状态',
				name : 'attrSts',
				width : "10%",
				align : 'left',
				render : function(rowData) {
					return rowData.attrSts == "0" ? "停用" : "启用";
				}
			}, {
				display : '是否扩展',
				name : 'isExt',
				width : "10%",
				align : 'left',
				render : function(rowData) {
					return rowData.isExt == "0" ? "否" : "是";
				}
			}, {
				display : '组件类型',
				name : 'elementType',
				width : "11%",
				align : 'left',
				render : function(rowData) {
					if ("01" == rowData.elementType) {
						return "文本框";
					} else if ("02" == rowData.elementType) {
						return "下拉框";
					} else if ("03" == rowData.elementType) {
						return "日期选择框";
					} else if ("04" == rowData.elementType) {
						return "隐藏域";
					} else if ("05" == rowData.elementType) {
						return "密码框";
					} else if ("06" == rowData.elementType) {
						return "多行文本输入域";
					} else {
						return "文本框";
					}
				}
			}, {
				display : '是否换行',
				name : 'isNewline',
				width : "10%",
				align : 'left',
				render : function(rowData) {
					return rowData.isNewline == "0" ? "否" : "是";
				}
			} ],
			dataAction : 'server', //从后台获取数据
			usePager : true, //服务器分页
			alternatingRow : true, //附加奇偶行效果行
			colDraggable : true,
			url : "${ctx}/bione/admin/userattrgrp/list.json",
			sortName : 'attrId',//第一次默认排序的字段
			sortOrder : 'asc', //排序的方式
			pageParmName : 'page',
			pagesizeParmName : 'pagesize',
			rownumbers : true,
			width : '100%',
			height : '100%'
		});
	};
	function initToolBar() {
		var toolBars = [ {
			text : '增加',
			click : f_open_add,
			icon : 'add'
		}, {
			text : '修改',
			click : f_open_update,
			icon : 'modify'
		}, {
			text : '删除',
			click : f_delete,
			icon : 'delete'
		} ];
		BIONE.loadToolbar(grid, toolBars, function() {
		});
	}
	$(function() {
		initSearchForm();
		initGrid();
		initToolBar();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});
	//对象组添加函数
	function f_open_add() {
		if (grpId) {
			parent.window.userAttr = window;
			parent.BIONE.commonOpenLargeDialog("新增用户信息属性", "userAttrWin",
					"${ctx}/bione/admin/userattrgrp/new?t=attr&grpId="
							+ grpId);
		} else {
			BIONE.tip("请选择分组！");
		}
	}
	function f_open_update() {
		var row = grid.getCheckedRows();
		// 	if (!row) {
		// 		BIONE.tip('请选择行');
		// 		return;
		// 	}
		if (row && row.length == 1) {
			var id = row[0].attrId;
			parent.window.userAttr = window;
			parent.BIONE.commonOpenLargeDialog("用户信息属性修改", "userAttrWin",
					"${ctx}/bione/admin/userattrgrp/" + id + "/edit?t=attr");
		} else {
			BIONE.tip('请选择一行数据。');
		}
	}
	function f_delete() {
		var checkedRole = grid.getSelectedRows();
		if (checkedRole.length == 0) {
			BIONE.tip('请选择行');
			return;
		}
		;
		var id = "";
		var length = checkedRole.length;
		for ( var i = 0; i < length; i++) {
			id += checkedRole[i].attrId + ",";
		}
		window.parent.$.ligerDialog.confirm('确实要删除这' + checkedRole.length + '条记录吗!',
				function(yes) {
					if (yes) {
						$.ajax({
							type : "DELETE",
							url : '${ctx}/bione/admin/userattr/' + id,
							dataType : "json",
							success : function(result) {
								if (grpId) {
									grid.set('parms', {
										grpId : grpId,
										d : new Date().getTime()
									});
								} else {
									grid.set('parms', {
										d : new Date().getTime()
									});
								}

								grid.loadData();
								if (result == true) {
									BIONE.tip("删除成功");
								} else {
									BIONE.tip("该记录无法删除");
								}
							}
						});
					}
				});
	}
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for ( var i in rows) {
			ids.push(rows[i]);
		}
	}
	function changeGrp(grpId) {
		if (grpId) {
			grid.set('parms', {
				grpId : grpId,
				d : new Date().getTime()
			});
			$('#grpId').val(grpId);
		} else {
			grid.set('parms', {
				d : new Date().getTime()
			});
			$('#grpId').val("");
		}
		grid.loadData();
	}
</script>
</head>
<body>

	<div id="template.right.down">
		<div id="aaa">
			<div id="maingrid" style="margin-top: 60px;"></div>
		</div>
	</div>
</body>
</html>