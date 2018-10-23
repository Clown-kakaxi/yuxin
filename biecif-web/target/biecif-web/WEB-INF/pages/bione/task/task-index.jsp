<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
    var dialog;
    var grid;
    var TASK_STS_NORMAL = "01";//正常
    var TASK_STS_STOP = "02";//挂起
    $(function() {
	$("#search").ligerForm({
	    fields : [ {
		display : "任务名称",
		name : "taskName",
		newline : true,
		type : "text",
		cssClass : "field",
		attr : {
		    op : "like",
		    field : "task.taskName"
		}
	    }, {
		display : "实体类名称",
		name : "beanName",
		newline : false,
		type : "text",
		cssClass : "field",
		attr : {
		    op : "like",
		    field : "task.beanName"
		}
	    } ]
	});

	grid = $("#maingrid").ligerGrid({
	    columns : [ {
		name : 'taskId',
		hide : 'true',
		width : '0'
	    }, {
		name : 'logicSysNo',
		hide : 'true',
		width : '0'
	    }, {
		display : '任务名称',
		name : 'taskName',
		width : '20%',
		align : 'center'
	    }, {
		name : 'taskType',
		hide : 'true',
		width : '0'
	    }, {
		display : '实体类名称',
		name : 'beanName',
		width : '30%',
		align : 'left'
	    }, {
		display : '触发器名称',
		name : 'triggerName',
		width : '20%',
		align : 'center',
		render : QYBZRenderTri
	    }, {
		display : '任务状态',
		name : 'taskSts',
		width : '8%',
		align : 'center',
		render : QYBZRenderSts
	    }, {
		display : '创建时间',
		name : 'createTime',
		width : '16%',
		align : 'center',
		type : 'date',
		format : 'yyyy-MM-dd hh:mm:ss'
	    } ],
	    checkbox : true,
	    rownumbers : true,
	    isScroll : false,
	    alternatingRow : true,//附加奇偶行效果行
	    colDraggable : true,
	    dataAction : 'server',//从后台获取数据
	    method : 'post',
	    url : "${ctx}/bione/schedule/task/list.json",
	    usePager : true, //服务器分页
	    sortName : 'logicSysNo',//第一次默认排序的字段
	    sortOrder : 'asc', //排序的方式
	    pageParmName : 'page',
	    pagesizeParmName : 'pagesize',
	    width : '100%',
	    height : '100%',
	    toolbar : {}
	});

	var btns = [ {
	    text : '增加',
	    click : create,
	    icon : 'add'
	}, {
	    text : '修改',
	    click : edit,
	    icon : 'modify'
	}, {
	    text : '删除',
	    click : deleteBatch,
	    icon : 'delete'
	}, {
	    text : '继续',
	    click : resumeJob,
	    icon : 'modify'
	}, {
	    text : '挂起',
	    click : pauseJob,
	    icon : 'modify'
	}, {
	    text : '立即执行',
	    click : startJob,
	    icon : 'modify'
	} ];

	BIONE.loadToolbar(grid, btns, function() {
	});

	BIONE.addSearchButtons("#search", grid, "#searchbtn");
    });

    //新建
    function create() {
	dialog = BIONE.commonOpenLargeDialog('添加任务信息', 'taskAddWin',
		'${ctx}/bione/schedule/task/new');
    }

    //修改
    function edit() {
	var rows = grid.getSelectedRows();
	if (rows.length == 1) {
	    var id = rows[0].taskId;
	    dialog = BIONE.commonOpenLargeDialog("修改任务信息", "taskModifyWin",
		    "${ctx}/bione/schedule/task/" + id + "/edit");
	} else {
	    BIONE.tip('请选择一条记录');
	}
    }
    //挂起任务
    function pauseJob() {
		var row = grid.getSelectedRows();
		if (row.length != 1) {
		    BIONE.tip('请选择一个任务');
		    return;
		}
		var id = row[0].taskId;
		var sts = row[0].taskSts;
	
		if (sts != TASK_STS_NORMAL) {
		    BIONE.tip('不是运行状态');
		    return;
		}
		$.ligerDialog.confirm('确实要挂起吗!', function(yes) {
		    if (yes) {
			$.ajax({
			    url : '${ctx}/bione/schedule/task/pause.json?id=' + id,
			    success : function(result) {
				if (grid) {
				    grid.loadData();
				}
			    }
			});
		    }
		});
    }
    //继续任务
    function resumeJob() {
	var row = grid.getSelectedRows();
	if (row.length != 1) {
	    BIONE.tip('请选择一个任务');
	    return;
	}
	var id = row[0].taskId;
	var sts = row[0].taskSts;
	if (sts != TASK_STS_STOP) {
	    BIONE.tip('不是挂起状态');
	    return;
	}
	$.ligerDialog.confirm('确实要继续吗!', function(yes) {
	    if (yes) {
		$.ajax({
		    url : '${ctx}/bione/schedule/task/resume.json?id=' + id,
		    success : function(result) {
			if (grid) {
			    grid.loadData();
			}
		    }
		});
	    }
	});

    }
    //立即执行
    function startJob() {
	var row = grid.getSelectedRows();
	if (row.length != 1) {
	    BIONE.tip('请选择一个任务');
	    return;
	}
	var id = row[0].taskId;
	$.ligerDialog.confirm('确实要立即执行吗!', function(yes) {
	    if (yes) {
		$.ajax({
		    url : '${ctx}/bione/schedule/task/start?id=' + id,
		    dataType:"json",
		    success : function(result) {
			if (grid) {
			    grid.loadData();
			}
		    }
		});
	    }
	});
    }
    //批量删除
    function deleteBatch() {
	var rows = grid.getSelectedRows();
	if (rows == null || rows.length == 0) {
	    BIONE.tip('请选择行');
	    return;
	}
	;
	$.ligerDialog.confirm('确实要删除这' + rows.length + '条记录吗!', function(yes) {
	    var length = rows.length;
	    if (yes) {
		var ids = "";
		for ( var i = 0; i < length; i++) {
		    if (ids != "") {
			ids += ",";
		    }
		    ids += rows[i].taskId;
		}
		$.ajax({
		    async : false,
		    dataType : "json",
		    type : "post",
		    url : '${ctx}/bione/schedule/task/batchDelete',
		    data : {
			"ids" : ids
		    },
		    beforeSend : function() {
			BIONE.loading = true;
			BIONE.showLoading("正在加载数据中...");
		    },
		    complete : function() {
			BIONE.loading = false;
			BIONE.hideLoading();
		    },
		    success : function(result) {
			BIONE.tip('删除成功');
			grid.loadData();
		    },
		    error : function(result, b) {
			BIONE.tip('删除错误 <BR>错误码：' + result.status);
		    }

		});
	    }
	});
    }

    // 状态显示,停/启用等
    function QYBZRenderSts(rowdata) {
	if (rowdata.taskSts == TASK_STS_NORMAL) {
	    return "正常";
	} else if (rowdata.taskSts == TASK_STS_STOP) {
	    return "挂起";
	} else {
	    return rowdata.taskSts;
	}
    }

    function QYBZRenderTri(rowdata) {
	return BIONE.paramTransformer(rowdata.triggerId,
		'${ctx}/bione/common/getTriggerName');
    }
</script>
</head>
<body>
</body>
</html>