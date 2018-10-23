<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];

	$(function() {
		url = "${ctx}/ecif/core/tabdef/list.json";
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});

	function RenderFlag(rowdata){
		if(rowdata.flag == '1'){
			return '有效';
		}else if(rowdata.flag == '0'){
			return '无效';
		}else{
			return rowdata.flag;
		}
	}

	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		/*	labelWidth : 100,
		 	inputWidth : 220,
			space : 30, */
			fields : [ {
				display : "表名",
				name : "tabName11",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "tabdef.tabName"
				}
			} ]
		});

	}
	
	window.document.onkeydown = function(e) {
		e = !e ? window.event : e;
		var key = window.event ? e.keyCode : e.which;
		if (key == 13) {
			return false;
		}
	};
	
	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '表编号',
								name : 'tabId',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '表名',
								name : 'tabName',
								align : 'center',
								width : '30%',
								minWidth : '10%'
							},
							{
								display : '表中文描述',
								name : 'tabDesc',
								align : 'center',
								width : '30%',
								minWidth : '10%'
// 							},
// 							{
// 								display : '创建时间',
// 								name : 'createTm',
// 								align : 'center',
// 								width : '15%',
// 								minWidth : '10%'
// 							},
// 							{
// 								display : '创建人',
// 								name : 'createUser',
// 								align : 'center',
// 								width : '15%',
// 								minWidth : '10%'
// 							},
// 							{
// 								display : '更新时间',
// 								name : 'updateTm',
// 								align : 'center',
// 								width : '10%',
// 								minWidth : '10%'
// 							},
// 							{
// 								display : '更新人',
// 								name : 'updateUser',
// 								align : 'center',
// 								width : '10%',
// 								minWidth : '10%'
							} ],
					checkbox : true,
//					delayLoad :true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortName : 'tabName', //第一次默认排序的字段
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
			text : '批量导入',
			click : resDef_batchimport,
			icon : 'add',
			operNo : 'resDef_batchimport'
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
			text : '字段维护',
			click : resDef_config,
			icon : 'modify',
			operNo : 'resDef_config'
		}, {
			text : '同步字段',
			click : resDef_sync,
			icon : 'refresh',
			operNo : 'resDef_sync'
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	function resDef_add(item) {
		BIONE.commonOpenSmallDialog('添加表', 'resDefManage',
				'${ctx}/ecif/core/tabdef/new');
	}

	function resDef_batchimport(item) {
		BIONE.commonOpenLargeDialog('批量导入', 'resDefManage',
				'${ctx}/ecif/core/tabdef/batchimport');
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
			BIONE.commonOpenSmallDialog('修改资源', 'resDefManage',
					'${ctx}/ecif/core/tabdef/' + ids[0] + '/edit', buttons);
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	

	//同步多个表
	function resDef_sync(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('所选表的列定义将被更新，确实要同步这些表吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "GET",
						url : "${ctx}/ecif/core/tabdef/sync/" + ids.join(','),
						dataType : "script",
						success : function() {
							flag = true;
						}
					});
					if (flag == true) {
						BIONE.tip('同步成功');
						//initGrid();
					} else {
						BIONE.tip('同步失败');
					}
				}
			});
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	function resDef_delete(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('所包含的表与字段的信息都将被删除，确实要删除这些记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "DELETE",
						url : "${ctx}/ecif/core/tabdef/" + ids.join(','),
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
		    var tabId = rows[0].tabId;
		    dialog = BIONE.commonOpenLargeDialog("字段展示", "authResWin",
			    "${ctx}/ecif/core/tabdef/authservice?tabId=" + tabId
				    );
		} else {
		    BIONE.tip("请选择记录");
		}
    }
    
	// 获取选中的行
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i].tabId);
		}
		
	}
	
	BIONE.commonOpenLargeDialog = function commonOpenLargeDialog(title, name,
			url, beforeClose) {
		var width = 1000;
		var height = 500;
		var _dialog = $.ligerui.get(name);
		if (_dialog) {
			$.ligerui.remove(name);
		}
		_dialog = $.ligerDialog.open({
			height : height,
			width : width,
			url : url,
			name : name,
			id : name,
			title : title,
			isResize : false,
			isDrag : false,
			isHidden : false
		});
		if(beforeClose!=null &&
				typeof beforeClose == "function"){
			_dialog._beforeClose = beforeClose;
		}
		return _dialog;
	};	
</script>
</head>
<body>
</body>
</html>