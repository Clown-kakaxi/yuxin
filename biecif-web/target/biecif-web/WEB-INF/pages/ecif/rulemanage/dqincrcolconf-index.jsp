<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template0.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];
	
	var tid = "${tid}";
	var dstTab = "${dstTab}";
	
	$(function() {
		//alert(tid);
		url = "${ctx}/ecif/rulemanage/dqincrcolconf/list.json?tid=" + tid;
		//searchForm();
		initGrid();
		initButtons();
		//BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});

	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({

			fields : [ {
				name:"tid",
				type:'hidden'
			} ]
		});
		var e = $("[name=tid]");
		
		e.val(tid);
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							
							{
								display : '目标表字段名',
								name : 'dstCol',
								align : 'left',
								width : 180,
								minWidth : 150
							},
							{
								display : '源表字段名或常量',
								name : 'srcCol',
								align : 'left',
								width : 180,
								minWidth : 150
							},
							{
								display : '源表字段常量变量标识',
								name : 'insOnl',
								align : 'left',
								width : 180,
								minWidth : 150
							},
							{
								display : '系统优先级',
								name : 'sys',
								align : 'left',
								width : 180,
								minWidth : 150
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
//					sortName : 'colid', //第一次默认排序的字段
//					sortOrder : 'asc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [ {
			text : '增加',
			click : dqincrcolconf_add,
			icon : 'add',
			operNo : 'dqincrcolconf_add'
		}, {
			text : '修改',
			click : dqincrcolconf_modify,
			icon : 'modify',
			operNo : 'dqincrcolconf_modify'
		}, {
			text : '删除',
			click : dqincrcolconf_delete,
			icon : 'delete',
			operNo : 'dqincrcolconf_delete'
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	function dqincrcolconf_add() {
		BIONE.commonOpenDialog('添加', 'dqincrcolconfManage',600,300,
				'${ctx}/ecif/rulemanage/dqincrcolconf/new?tid=' + tid);
	}
	function dqincrcolconf_modify(item) {
		achieveIds();
		if (ids.length == 1) {
			BIONE.commonOpenDialog('修改', 'dqincrcolconfManage',600,300,
					'${ctx}/ecif/rulemanage/dqincrcolconf/edit?tid='+tid+"&dstCol=" + ids[0] );
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	function dqincrcolconf_delete(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "DELETE",
						url : "${ctx}/ecif/rulemanage/dqincrcolconf/" +tid +','+ ids[0] ,
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
			ids.push(rows[i].dstCol);
		}
	}
</script>
</head>
<body>
</body>
</html>