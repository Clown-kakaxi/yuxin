<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];
	
	//从上级页面传来父ID
	var tabId = "${tabId}";
	
	$(function() {
		url = "${ctx}/ecif/core/coldef/list.json?tabId=" + tabId;
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});
	
	function RenderFlag(rowdata){
		if(rowdata.keyType == '1'){
			return '是';
		}else if(rowdata.keyType == '0'){
			return '否';
		}else{
			return rowdata.keyType;
		}
	}

	function RenderFlag2(rowdata){
		if(rowdata.isCode == '1'){
			return '是';
		}else if(rowdata.isCode == '0'){
			return '否';
		}else{
			return rowdata.isCode;
		}
	}

	
	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({

			fields : [ 
			{
				display : "字段名（代码）",
				name : "colName",
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "coldef.colName"
				}
			},
			{
				display : "字段名（中文）",
				name : "colChName",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "coldef.colChName"
				}
			}
			]
		});
		
		//给隐含父节点赋值
		var e = $("[name=tabId]");
		e.val(tabId);
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '字段编号',
								name : 'colId',
								align : 'center',
								width : '20%',
								minWidth : '10%',
								hide:1 
							},
							{
								display : '表名',
								name : 'tabId',
								align : 'center',
								width : '20%',
								minWidth : '10%',
								hide:1 
							},
							{
								display : '字段名',
								name : 'colName',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '字段中文描述',
								name : 'colChName',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '字段类型',
								name : 'dataType',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '长度',
								name : 'dataLen',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '精度',
								name : 'dataPrec',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '可否为空',
								name : 'nulls',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '关键码类型',
								name : 'keyType',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag
							},
							{
								display : '是否转码',
								name : 'isCode',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag2
							},
							{
								display : '类别代码',
								name : 'cateId',
								align : 'center',
								width : '10%',
								minWidth : '10%' 
							},
							{
								display : '创建时间',
								name : 'createTm',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '创建人',
								name : 'createUser',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '更新时间',
								name : 'updateTm',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '更新人',
								name : 'updateUser',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							} ],
					checkbox : true,
					usePager : false,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortName : 'tabId', //第一次默认排序的字段
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
		BIONE.commonOpenLargeDialog('添加', 'resDefManage',
				'${ctx}/ecif/core/coldef/new?tabId=' + tabId);
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
			
			
			BIONE.commonOpenLargeDialog('修改', 'resDefManage',
					'${ctx}/ecif/core/coldef/edit?tabId=' + tabId +'&colId='+ids[0], buttons);
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	function resDef_delete(item) {
		achieveIds();
		if (ids.length == 1) {
			$.ligerDialog.confirm('确实要删除这条记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "DELETE",
						url : "${ctx}/ecif/core/coldef/" +tabId +','+ ids[0],
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
			BIONE.tip('请选择一条记录');
		}
	}
	
	// 获取选中的行
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i].colId);
		}
	}
</script>
</head>
<body>
</body>
</html>