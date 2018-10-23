<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">

	var dialog;
	var grid;
	
	$(function() {
		$("#search").ligerForm({
			fields : [ {
				display : "数据源名称 ",
				name : "dsName",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					field : "ds.dsName",
					op : "like"
				}
			}]
		});

		initGrid();
		
		var btns = [{
			text : '添加',
			click : create,
			icon : 'add',
			operNo : 'add'
		},{
			text : '修改',
			click : modify,
			icon : 'modify',
			operNo : 'modify'
		},{
			text : '删除',
			click : deleteDs,
			icon : 'delete',
			operNo : 'deleteDs'
		}];

		BIONE.loadToolbar(grid, btns, function() {});
		
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
		
		
	});
	//初始化表格 
	function initGrid() {
		//alert("进入initDrid");
		grid = $("#maingrid").ligerGrid({
			//InWindow : false,
			height : '100%',
			width : '100%',
			columns : [ {
				name : 'dsId',
				hide :1
			}, {
				display : '数据源名称',
				name : 'dsName',
				align : 'left',
				width : '10%'
			}, {
				display : '逻辑系统标识 ',
				name : 'logicSysNo',
				align : 'left',
				width : '10%'
			}, {
				display : '驱动类型',
				name : 'driverId',
				align : 'left',
				width : '10%'
			}, {
				display : '连接URL',
				name : 'connUrl',
				align : 'left',
				width : '25%'
			}, {
				display : '连接用户',
				name : 'connUser',
				align : 'left',
				width : '10%'
			}, {
				display : '描述',
				name : 'remark',
				align : 'left',
				width : '27%'
			} ],
			checkbox : true,
			isScroll : true,
			rownumbers : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : "${ctx}/bione/mtool/datasource/list.json",
			usePager : true, //服务器分页
			alternatingRow : true, //附加奇偶行效果行
			colDraggable : true,
			sortName : 'dsId',//第一次默认排序的字段
			sortOrder : 'asc', //排序的方式
			pageParmName : 'page',
			pagesizeParmName : 'pagesize',
			toolbar : {}
		});
		
	}
	//新加
	function create() {
		dialog = BIONE.commonOpenLargeDialog('新加数据源', 'addDsWin',
				'${ctx}/bione/mtool/datasource/new');
	}
	//修改
	function modify(){
		//获得被选中 的行
		var rows = grid.getSelectedRows();
		if(rows.length==1){
			BIONE.commonOpenLargeDialog('修改数据源', 'addDsWin',
					'${ctx}/bione/mtool/datasource/' + rows[0].dsId + '/edit');
		}else{
			BIONE.tip("请选择一条数据进行修改 ");
		}
	}

	//删除
	function deleteDs() {
		//获得被选中 的行
		var rows = grid.getSelectedRows();
		//判断被选中的行数  ，分别进行 不同的操作 
		if (rows.length > 0) {
			$.ligerDialog
					.confirm(
							'您确定删除这' + rows.length + "条记录么？",
							function(yes) {
								if (yes) {
									var ids="";
									for ( var i = 0; i < rows.length; i++) {
										ids=ids+"/"+rows[i].dsId;
									}
									$.ajax({
										type : "POST",
										url : "${ctx}/bione/mtool/datasource/removeAll",
										data:{
											dsId :ids
										},
										//dataType : "scriptType",
										success : function() {
											BIONE.tip('删除成功');
											initGrid();
										} 
									});
									
									
								}
							});
		} else {
			BIONE.tip('请选择要删除的行');
		}
	}
	
	
</script>
</head>
<body>
</body>
</html>