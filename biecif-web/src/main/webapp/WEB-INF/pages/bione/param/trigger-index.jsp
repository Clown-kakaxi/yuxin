\<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template1.jsp">
<head>
<!-- 基础的JS和CSS文件 -->
<script type="text/javascript">
    var grid, items, ids = [];
    $(function() {
	searchForm();
	initGrid();
	initButton();
	BIONE.addSearchButtons("#search", grid, "#searchbtn");
    });

    function searchForm() {
	//搜索表单应用ligerui样式
	$("#search").ligerForm({
	    fields : [ {
		display : '触发器名称',
		name : "triggerName",
		newline : true,
		type : "text",
		cssClass : "field",
		attr : {
		    field : 'triggerName',
		    op : "like"
		}
	    } ]
	});
    }

    function initGrid() {
	grid = $("#maingrid").ligerGrid({
	    width : '100%',
	    columns : [ {
		display : '触发器名称',
		name : 'triggerName',
		width : "30%",
		align : 'left'
	    }, {
		display : '描述',
		name : 'remark',
		width : '64%',
		align : 'left'
	    } ],
	    checkbox : true,
	    dataAction : 'server', //从后台获取数据
	    usePager : true, //服务器分页
	    alternatingRow : true, //附加奇偶行效果行
	    colDraggable : true,
	    url : "${ctx}/bione/schedule/trigger/list.json",
	    method : 'get',
	    sortName : 'triggerId',//第一次默认排序的字段
	    sortOrder : 'asc', //排序的方式
	    rownumbers : true,
	    toolbar : {}
	});
    }

    function initButton() {
	items = [ {
	    text : '增加',
	    click : trigger_add,
	    icon : 'add'
	}, {
	    text : '修改',
	    click : trigger_update,
	    icon : 'modify'
	}, {
	    text : '删除',
	    click : trigger_delete,
	    icon : 'delete'
	} ];
	BIONE.loadToolbar(grid, items, function() {
	});
    }

    //角色添加函数
    function trigger_add() {
	BIONE.commonOpenLargeDialog("触发器添加", "triggerAddWin",
		"${ctx}/bione/schedule/trigger/new");
    }

    // 修改
    function trigger_update() {
	achieveIds();
	if (ids.length == 1) {
	    BIONE.commonOpenLargeDialog("触发器修改", "triggerModifyWin",
		    "${ctx}/bione/schedule/trigger/" + ids[0] + "/edit");
	} else if (ids.length > 1) {
	    BIONE.tip('只能选择一行进行修改');
	} else {
	    BIONE.tip('请选择一行进行修改');
	}
    }

    // 删除
    function trigger_delete() {
	achieveIds();
	if (ids.length == 1) {
	    var idsTmp = ids.join(",");
	    $
		    .ajax({
			cache : false,
			async : true,
			url : "${ctx}/bione/schedule/trigger/checkHasJobOrNot",
			dataType : 'json',
			type : "post",
			data : {
			    "ids" : idsTmp
			},
			success : function(result) {
			    if (result) {
				var confirmMessage = "";
				if (!result || result == "") {
				    //若触发器没被引用
				    confirmMessage = "确定删除该触发器吗";
				} else {
				    BIONE.tip("触发器正在被作业引用，不允许删除");
				    return;
				}
				$.ligerDialog
					.confirm(
						confirmMessage,
						function(yes) {
						    if (yes) {
							$
								.ajax({
								    cache : false,
								    async : true,
								    url : "${ctx}/bione/schedule/trigger/destroyOwn",
								    type : "post",
								    dataType : "json",
								    data : {
									"idStr" : idsTmp
								    },
								    success : function() {
									BIONE
										.tip('删除成功');
									initGrid();
								    }
								});
						    }
						});
			    }
			}
		    });
	} else {
	    BIONE.tip('请选择一行进行删除');
	}
    }

    // 获取选中的行
    function achieveIds() {
	ids = [];
	var rows = grid.getSelectedRows();
	for ( var i in rows) {
	    ids.push(rows[i].triggerId);
	}
    }
</script>
</head>
<body>
	<div id="template.right.down">
		<div id="maingrid" style="margin-top: 60px;"></div>
	</div>
</body>
</html>