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
	var names;
	var grpId;

	var dbtype = '${dbtype}';

    //debugger;
    
	$(function() {
		searchForm();		
		initGrid();
		initToolBar();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});    
    
	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		/*	labelWidth : 100,
		 	inputWidth : 220,
			space : 30, */
			fields : [ {
				display : "模式名(DB2必须填写)",
				name : "tabSchema",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "tabdef.tabSchema"
				}
			},{
					display : "表名(开头字母模糊匹配，支持多个，如TX_,M_)",
					name : "tabName",
					newline : false,
					type : "text",
					cssClass : "field",
					attr : {
						op : "=",
						field : "tabdef.tabName"
					}				
			} ]
		});

	}
    
	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			toolbar : {},
			checkbox : true,
			columns : [ {
				display : '表名',
				name : 'tabName',
				width : "25%"
			}, {
				display : '中文名称',
				name : 'tabDesc',
				width : "25%",
				align : 'left'
			} ],
			dataAction : 'server', //从后台获取数据
			usePager : false, //服务器分页
			alternatingRow : true, //附加奇偶行效果行
			colDraggable : true,
			url : "${ctx}/ecif/core/tabdef/listschematables.json",
			method : 'get',
			sortName : 'tabName',//第一次默认排序的字段
			sortOrder : 'asc', //排序的方式
			pageParmName : 'page',
			pagesizeParmName : 'pagesize',
			rownumbers : true,
			onCheckAllRow : null,
			width : '100%',
			height : '100%'
		});
	};
	function initToolBar() {
		var toolBars = [ {
			text : '执行',
			click : f_select,
			icon : 'add'
		} ,
		 {
			text : '关闭',
			click : f_unselect,
			icon : 'delete'
		}];
		BIONE.loadToolbar(grid, toolBars, function() {
		});
	}
	
	

	//对象组添加函数
	function f_select() {
		
		//prompt('',document.all.formsearch.tabSchema.outerHTML);		
		var t = document.all.formsearch.tabSchema.value;
		if(t==null){
			if(dbtype=='db2'){				//db2需要输入schema
				BIONE.tip('db2数据库请先指定schema！');
			}
		}

		achieveIds();
		//alert(ids+":" + t);
		
		if (ids.length > 0) {
			$.ligerDialog.confirm('所选物理表及字段将被更新至元数据定义，已存在的数据将被覆盖，确实要导入这些表吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "post",
						url : "${ctx}/ecif/core/tabdef/batchimportexec" ,
						dataType : "json",
						data : {
							"id" :ids.join(','),
							"tabSchema" : t
						},
						
						success : function() {
							flag = true;
						}
					});
					if (flag == true) {
						BIONE.tip('批量导入成功');
						//initGrid();
					} else {
						BIONE.tip('批量导入失败');
					}
				}
			});

		} else {
			BIONE.tip("请选择要导入的表！");
		}
	}
	
	function f_unselect() {

		BIONE.closeDialog("resDefManage");
	}	
	
	
	function achieveIds() {
		ids = [];
		names = [];
		var rows = grid.getSelectedRows();
		for ( var i in rows) {
			ids.push(rows[i].tabName);
			names.push(rows[i].tabDesc);
		}
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