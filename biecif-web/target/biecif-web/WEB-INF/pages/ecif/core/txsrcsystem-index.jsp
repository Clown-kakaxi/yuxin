<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template0.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];

	$(function() {
		url = "${ctx}/ecif/core/txsrcsystem/list.json";
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
								display : '源系统代码',
								name : 'srcSysCd',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '源系统简称',
								name : 'srcSysNm',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : 'ESB简称',
								name : 'esbSysNm',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : 'EDW简称',
								name : 'edwSysNm',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '源系统名称',
								name : 'srcSysName',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '源系统描述',
								name : 'srcSysDesc',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '记录状态',
								name : 'state',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag
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
					sortName : 'srcSysCd', //第一次默认排序的字段
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
		BIONE.commonOpenLargeDialog('添加系统', 'resDefManage',
				'${ctx}/ecif/core/txsrcsystem/new');
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
			BIONE.commonOpenLargeDialog('修改系统', 'resDefManage',
					'${ctx}/ecif/core/txsrcsystem/' + ids[0] + '/edit', buttons);
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	function resDef_delete(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('客户端授权、代码映射相关数据将被影响，确实要删除此源系统吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "DELETE",
						url : "${ctx}/ecif/core/txsrcsystem/" + ids.join(','),
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
			ids.push(rows[i].srcSysCd);
		}
		
	}
</script>
</head>
<body>
</body>
</html>