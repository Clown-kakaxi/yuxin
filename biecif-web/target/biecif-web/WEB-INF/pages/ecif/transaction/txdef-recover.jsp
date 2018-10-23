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
	var txCode = "${txCode}";
    //debugger;
	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			toolbar : {},
			checkbox : true,
			columns : [ {
				display : '交易文件/版本',
				name : 'fileName',
				width : "35%"
			}, {
				display : '修改时间',
				name : 'createTime',
				width : "25%",
				align : 'left'
			}, {
				display : '操作',
				name : 'relPath',
				width : "12%",
				align : 'left'
			} ],
			dataAction : 'server', //从后台获取数据
			usePager : false, //服务器分页
			alternatingRow : true, //附加奇偶行效果行
			colDraggable : true,
			url : "${ctx}/ecif/transaction/txdef/recoverlist.json?txCode=" + txCode,
			method : 'get',
			rownumbers : true,
			width : '100%',
			height : '100%'
		});
	};
	function initToolBar() {
		var toolBars = [ {
			text : '还原',
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
		if (ids.length == 1 ) {
			$.ligerDialog.confirm('旧的交易代码将被覆盖，确实要还原这个交易吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "GET",
						url : "${ctx}/ecif/transaction/txdef/recovertodb?filename=" + ids[0],
						//dataType : "script",
						success : function() {
							flag = true;
						}
					});
					if (flag == true) {
						//BIONE.tip('还原成功');
						BIONE.closeDialogAndReloadParent("recover","maingrid" , "还原成功");
						
					} else {
						BIONE.tip('还原失败');
					}
				}
			});
		} else {
			BIONE.tip('请选择一条记录');
		}

	}
	
	function f_unselect() {
				
		var main = parent || window;
		var dialog = main.jQuery.ligerui.get("recover");
		if(dialog.beforeClose != null 
         		&& typeof dialog.beforeClose == "function"
         			&&flag!=null&&flag==true){        		
			 dialog.beforeClose();
         }
		dialog.close();
	}	
	
	
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for ( var i in rows) {
			ids.push(rows[i].fileName);
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