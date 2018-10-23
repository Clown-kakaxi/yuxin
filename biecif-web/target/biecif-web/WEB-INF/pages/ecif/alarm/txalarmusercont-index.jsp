<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template0.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];
	
	var userId = "${userId}";
	//alert(userId);
	$(function() {
		url = "${ctx}/ecif/alarm/txalarmusercont/list.json?userId=" + userId;
		//searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});
	
	function RenderFlag(rowdata){
		if(rowdata.contmethType == '0'){
			return '网页';
		}else if(rowdata.contmethType == '1'){
			return '邮件';
		}else if(rowdata.contmethType == '2'){
			return '短信';
		}else{
			return rowdata.contmethType;
		}
	}


	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '联系方式标识',
								name : 'contmethId',
								align : 'center',
								width : '20%',
								minWidth : '10%',
								hide:1 
							},
							{
								display : '用户标识',
								name : 'userId',
								align : 'center',
								width : '20%',
								minWidth : '10%',
								hide:1 
							},
							{
								display : '联系方式类型',
								name : 'contmethType',
								align : 'center',
								width : '20%',
								minWidth : '10%',
								render : RenderFlag
							},
							{
								display : '联系方式内容',
								name : 'contmethInfo',
								align : 'center',
								width : '20%',
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
					sortName : 'contmethId', //第一次默认排序的字段
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
		BIONE.commonOpenDialog('添加联系方式', 'resDefManage',600,300,
				'${ctx}/ecif/alarm/txalarmusercont/new?userId=' + userId);
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
			BIONE.commonOpenLargeDialog('修改联系方式', 'resDefManage',
					'${ctx}/ecif/alarm/txalarmusercont/' + ids[0] + '/edit', buttons);
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
						url : "${ctx}/ecif/alarm/txalarmusercont/" + ids.join(','),
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
			ids.push(rows[i].contmethId);
		}
	}
</script>
</head>
<body>
</body>
</html>