<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
var grid;
var btns;
$(function() {
	initGrid();
	initSearch();
	BIONE.addSearchButtons("#search", grid, "#searchbtn");
	initBtns();
});

function initSearch(){
	$("#search").ligerForm({
		fields : [{
			display : "服务标识",
			name : "serverNo",
			newLine : true,
			type : "text",
			attr : {
				op : "like",
				field : "serverNo"
			}
		}, {
			display : "服务名称",
			name : "serverName",
			newline : false,
			type : "text",
			attr : {
				field : "serverName",
				op : "like"
			}
		}]
	});
}

function initGrid() {
	grid = $("#maingrid").ligerGrid({
		height : '100%',
		width : '100%',
		columns : [ {
			display : "服务标识",
			name : "serverNo",
			align : 'left',
			minWidth : 100,
			width : "13%"
		}, {
			display : "服务名称",
			name : "serverName",
			align : 'left',
			minWidth : 100,
			width : "13%"
		}, {
			display : "服务IP",
			name : "serverIp",
			align : 'left',
			minWidth : 100,
			width : "13%"
		}, {
			display : "服务端口",
			name : "serverPort",
			align : 'left',
			minWidth : 100,
			width : "13%"
		}, {
			display : "服务路径",
			name : "serverPath",
			align : 'left',
			minWidth : 100,
			width : "20%"
		}, {
			display : "备注",
			name : "remark",
			align : 'left',
			minWidth : 100,
			width : "20%"
		}],
		checkbox : true,
		usePager : true,
		frozen : false,
		isScroll : false,
		rownumbers : true,
		alternatingRow : true,
		colDraggable : true,
		dataAction : 'server',
		method : 'post',
		url : "${ctx}/bione/serverinfo/list.json",
		toolbar : {},
		sortName : 'serverId',
		sortOrder : 'desc'
	});
}

function initBtns() {
	btns = [{
		text : "新增",
		icon : "add",
		click : f_add
	}, {
		text : "修改",
		icon : "modify",
		click : f_modify
	}, {
		text : "删除",
		icon : "delete",
		click : f_delete
	} ];
	BIONE.loadToolbar(grid, btns, function() {
	});
}

function f_delete() {
	var rows = grid.getCheckedRows();
	var l = rows.length;
	if(l > 0) {
		var str = "";
		for(var i = 0; i < l; i++) {
			if(i != 0) {
				str =  str + ",";
			}
			str = str + rows[i].serverId;
		}
		$.ligerDialog.confirm('确实要删除这'+l+'条记录吗!', function(yes) {
			if(yes){
				$.ajax({
					type : "DELETE",
					url : "${ctx}/bione/serverinfo/"+str,
					dataType : "text",
					success:function(text){						
						BIONE.tip(text);
						grid.loadData();
					}
				});
			}
		});
	} else {
		BIONE.tip("请选择数据！");
	}
}

function f_add() {
	BIONE.commonOpenLargeDialog("服务模块", "server", "${ctx}/bione/serverinfo/new");
}
function f_modify() {
	var rows =  grid.getCheckedRows();
	if(rows.length == 1) {
		BIONE.commonOpenLargeDialog("服务模块", "server", "${ctx}/bione/serverinfo/"+rows[0].serverId+"/edit");
	} else {
		BIONE.tip("请选择一条数据！");
	}
}
</script>
</head>
<body>
</body>
</html>