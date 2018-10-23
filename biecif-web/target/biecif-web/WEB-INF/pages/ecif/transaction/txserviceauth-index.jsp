<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template0.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];
	
	var clientAuthId = "${clientAuthId}";
	
	$(function() {
		url = "${ctx}/ecif/transaction/txserviceauth/list.json?clientAuthId=" + clientAuthId;
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

			fields : [ {
				name:"clientAuthId",
				type:'hidden'
			} ]
		});
		var e = $("[name=clientAuthId]");
		
		e.val(clientAuthId);
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '服务授权标识',
								name : 'serviceAuthId',
								align : 'center',
								width : '20%',
								minWidth : '10%',
								hide:1 
							},
							{
								display : '客户端授权标识',
								name : 'clientAuthId',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '源系统代码',
								name : 'srcSysCd',
								align : 'center',
								width : '20%',
								minWidth : '10%',
								hide:1 
							},
							{
								display : '交易编码',
								name : 'txCode',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '交易名称',
								name : 'txName',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '授权类型',
								name : 'authType',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '开始日期',
								name : 'startDt',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '结束日期',
								name : 'endDt',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '有效性',
								name : 'flag',
								align : 'center',
								width : '33%',
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
					sortName : 'serviceAuthId', //第一次默认排序的字段
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
		BIONE.commonOpenDialog('添加交易', 'resDefManage',600,300,
				'${ctx}/ecif/transaction/txserviceauth/new?clientAuthId=' + clientAuthId);
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
					'${ctx}/ecif/transaction/txserviceauth/' + ids[0] + '/edit', buttons);
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
						url : "${ctx}/ecif/transaction/txserviceauth/" + ids.join(','),
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
			ids.push(rows[i].serviceAuthId);
		}
	}
</script>
</head>
<body>
</body>
</html>