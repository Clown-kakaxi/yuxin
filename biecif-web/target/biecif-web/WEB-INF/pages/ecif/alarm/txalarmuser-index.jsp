<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template0.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];

	$(function() {
		url = "${ctx}/ecif/alarm/txalarmuser/list.json";
		//searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});


	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '用户标识',
								name : 'userId',
								align : 'center',
								width : '30%',
								minWidth : '30%',
								hide:1
							},{
								display : '登录名称',
								name : 'loginName',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '用户名称',
								name : 'userName',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '用户称谓',
								name : 'userTitle',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '用户描述',
								name : 'userDesc',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '所属机构',
								name : 'userBranch',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '所属部门',
								name : 'userDept',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							}],
					checkbox : true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortName : 'userId', //第一次默认排序的字段
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
		}, {
			text : '配置联系方式',
			click : resDef_config,
			icon : 'config',
			operNo : 'resDef_config'
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	function resDef_add(item) {
		BIONE.commonOpenLargeDialog('添加人员', 'resDefManage',
				'${ctx}/ecif/alarm/txalarmuser/new');
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
			BIONE.commonOpenLargeDialog('修改人员', 'resDefManage',
					'${ctx}/ecif/alarm/txalarmuser/' + ids[0] + '/edit', buttons);
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
						url : "${ctx}/ecif/alarm/txalarmuser/" + ids.join(','),
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
	
    //授权资源
    function resDef_config() {
		var rows = grid.getSelectedRows();
		if (rows.length == 1) {
		    var userId = rows[0].userId;
		    dialog = BIONE.commonOpenLargeDialog("配置联系方式", "authResWin",
			    "${ctx}/ecif/alarm/txalarmuser/authservice?userId=" + String(userId)
				    );
		} else {
		    BIONE.tip("请选择需要配置的记录");
		}
    }	
	// 获取选中的行
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i].userId);
		}
		
	}
</script>
</head>
<body>
</body>
</html>