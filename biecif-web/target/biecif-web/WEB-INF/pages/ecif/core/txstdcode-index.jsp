<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];
	
	//从上级页面传来父ID
	var stdCate = "${stdcate}";
	
	$(function() {
		url = "${ctx}/ecif/core/txstdcode/list.json?stdCate=" + stdCate;
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});
	

	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({

			fields : [ 
			{
				display : "标准代码",
				name : "stdCode",
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "txstdcode.id.stdCode"
				}
			},
			{
				display : "代码描述",
				name : "stdCodeDesc",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "txstdcode.stdCodeDesc"
				}
			}
			]
		});
		
		//给隐含父节点赋值
		var e = $("[name=stdCate]");
		e.val(stdCate);
	}

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
								display : '标准码',
								name : 'stdCode',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '标准码描述',
								name : 'stdCodeDesc',
								align : 'center',
								width : '20%',
								minWidth : '10%'
							},
							{
								display : '展示顺序',
								name : 'orderId',
								align : 'center',
								width : '10%',
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
					sortName : 'orderId', //第一次默认排序的字段
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
				'${ctx}/ecif/core/txstdcode/new?stdCate=' + stdCate);
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
					'${ctx}/ecif/core/txstdcode/edit?stdCate=' + stdCate +'&stdCode='+ids[0], buttons);
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	function resDef_delete(item) {
		achieveIds();
		if (ids.length == 1) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "DELETE",
						url : "${ctx}/ecif/core/txstdcode/" +stdCate +','+ ids[0] ,
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
			BIONE.tip('请选择一条记录！');
		}
	}
	
	// 获取选中的行
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i].stdCode);
		}
	}
</script>
</head>
<body>
</body>
</html>