<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template0.jsp">
<head>
<!-- 基础的JS和CSS文件 -->
<script type="text/javascript">
	var grid;
	var ids;
	var names;
	var grpId;
    //debugger;
	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			toolbar : {},
			checkbox : true,
			columns : [ {
				display : '表ID',
				name : 'tabId',
				width : "25%",
				hide : 1
			}, {
				display : '表中文名',
				name : 'tabDesc',
				width : "35%",
				align : 'left'
			}],
			dataAction : 'server', //从后台获取数据
			delayLoad :false,			
			usePager : false,
			alternatingRow : true, //附加奇偶行效果行
			colDraggable : true,
			url : "${ctx}/ecif/core/tabdef/listall.json" ,
			method : 'get',
			sortName : 'tabDesc',//第一次默认排序的字段
			sortOrder : 'asc', //排序的方式
			rownumbers : false,
			width : '100%',
			height : '100%'
		});
	};
	function initToolBar() {
		var toolBars = [ {
			text : '选中',
			click : f_select,
			icon : 'add'
		} ,
		 {
			text : '取消',
			click : f_unselect,
			icon : 'delete'
		}];
		BIONE.loadToolbar(grid, toolBars, function() {
		});
	}
	
	
	$(function() {
		initGrid();
		initToolBar();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});
	//对象组添加函数
	function f_select() {
		achieveIds();
		if (ids.length == 1) {
			
			var grid = parent.ruleEqual.gridTable;
			var row = parent.ruleEqualRow;
			//alert(ids[0]);
			row.tabId = ids[0];
			row.tabDesc = names[0];
	
			//grid.updateCell('tabId', ids[0], row);
			
			var main = parent || window;
			var dialog = main.jQuery.ligerui.get("pWin");
			if(dialog.beforeClose != null 
	         		&& typeof dialog.beforeClose == "function"
	         			&&flag!=null&&flag==true){        		
				 dialog.beforeClose();
	         }
			dialog.close();

		} else {
			BIONE.tip("请选择一个属性！");
		}
	}
	
	function f_unselect() {

		/**
		var idstr ;
		var namestr ;

		var grid = parent.ruleEqual.gridTable;
		var row = parent.ruleEqualRow;

		row.tabId = idstr;
		row.tabDesc = namestr;

		grid.updateCell('tabId', '', row);
**/		
		var main = parent || window;
		var dialog = main.jQuery.ligerui.get("pWin");
		if(dialog.beforeClose != null 
         		&& typeof dialog.beforeClose == "function"
         			&&flag!=null&&flag==true){        		
			 dialog.beforeClose();
         }
		dialog.close();
	}	
	
	
	function achieveIds() {
		ids = [];
		names = [];
		var rows = grid.getSelectedRows();
		for ( var i in rows) {
			ids.push(rows[i].tabId);
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