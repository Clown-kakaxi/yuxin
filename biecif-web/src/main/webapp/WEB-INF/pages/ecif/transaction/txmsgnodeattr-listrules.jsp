<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	var nodeId = "${nodeId}";
    //debugger;
	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			toolbar : {},
			checkbox : true,
			columns : [ {
				display : '规则编号',
				name : 'ruleId',
				width : "25%",
				hide : 1
			}, {
				display : '规则名称',
				name : 'ruleName',
				width : "25%",
				align : 'left'
			}],
			dataAction : 'server', //从后台获取数据
			usePager : false, //服务器分页
			alternatingRow : true, //附加奇偶行效果行
			colDraggable : true,
			url : "${ctx}/ecif/transaction/txmsgnodeattr/rulelist.json",
			method : 'get',
			sortName : 'key',//第一次默认排序的字段
			sortOrder : 'asc', //排序的方式
			rownumbers : true,
			width : '100%',
			height : '100%'
		});
	};
	function initToolBar() {
		var toolBars = [ {
			text : '选中',
			click : f_select,
			icon : 'add'
		},
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
		if (ids.length >= 1) {
			var idstr = ids[0];
			var namestr = names[0];
			for(var i=1;i<ids.length;i++){
				idstr = idstr + ',' + ids[i];
				namestr = namestr  + ',' + names[i];
			}
			var grid = parent.ruleEqual.gridTable3;
			var row = parent.ruleEqualRow;

			row.ctRule = idstr;
			row.ctDesc = namestr;
	
			grid.updateCell('ctRule', idstr, row);
			
			var main = parent || window;
			var dialog = main.jQuery.ligerui.get("checkRuleWin");
			if(dialog.beforeClose != null 
	         		&& typeof dialog.beforeClose == "function"
	         			&&flag!=null&&flag==true){        		
				 dialog.beforeClose();
	         }
			dialog.close();

			//BIONE.closeDialog("checkRuleWin");

		} else {
			BIONE.tip("请选择属性！");
		}
	}
	
	
	function f_unselect() {
		
		var idstr ;
		var namestr ;

		var grid = parent.ruleEqual.gridTable3;
		var row = parent.ruleEqualRow;

		row.ctRule = idstr;
		row.ctDesc = namestr;

		grid.updateCell('ctRule', idstr, row);
		
		var main = parent || window;
		var dialog = main.jQuery.ligerui.get("checkRuleWin");
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
			ids.push(rows[i].ruleId);
			names.push(rows[i].ruleName);
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