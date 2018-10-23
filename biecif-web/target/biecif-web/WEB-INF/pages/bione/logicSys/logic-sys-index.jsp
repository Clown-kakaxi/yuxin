<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
    var dialog;

    $(function() {
	$("#search").ligerForm({
	    fields : [ {
		display : "逻辑系统缩写",
		name : "logicSysNo",
		newline : true,
		type : "text",
		cssClass : "field",
		attr : {
		    field : "logicSysNo",
		    op : "="
		}
	    }, {
		display : "逻辑系统名称",
		name : "logicSysName",
		newline : false,
		type : "text",
		cssClass : "field",
		attr : {
		    field : "logicSysName",
		    op : "like"
		}
	    } ]
	});

	grid = $("#maingrid").ligerGrid({
	    height : '100%',
	    width : '100%',
	    columns : [ {
		display : '逻辑系统编号',
		name : 'logicSysNo',
		align : 'left',
		width : 100,
		minWidth : 60
	    }, {
		display : '逻辑系统名称',
		name : 'logicSysName',
		minWidth : 200
	    }, {
		display : '认证方式',
		name : 'authTypeName',
		minWidth : 140
	    }, {
		display : '是否启用基线机构',
		name : 'basicOrgStsName',
		minWidth : 140
	    }, {
		display : '是否启用基线部门',
		name : 'basicDeptStsName',
		minWidth : 140
	    }, {
		display : '是否启用',
		name : 'logicSysStsName',
		minWidth : 140
	    }, {
		display : '最后修改人',
		name : 'lastUpdateUserName',
		minWidth : 140
	    }, {
		display : '最后修改时间',
		name : 'lastUpdateTimeStr',
		minWidth : 200
	    }, {
		display : '备注',
		name : 'remark',
		minWidth : 200,
		render : function(data, row, context, it) {
		    if (context&&context.length > 20) {
			return context.substring(0, 20) + "...";
		    }
		    return context;
		}
	    } ],
	    checkbox : false,
	    usePager : true,
	    isScroll : false,
	    rownumbers : true,
	    alternatingRow : true,//附加奇偶行效果行
	    colDraggable : true,
	    dataAction : 'server',//从后台获取数据
	    url : "${ctx}/bione/admin/logicSys/list.json",
	    usePager : true, //服务器分页
	    sortName : 'logicSysNo',//第一次默认排序的字段
	    sortOrder : 'asc', //排序的方式
	    pageParmName : 'page',
	    pagesizeParmName : 'pagesize',
	    toolbar : {}
	});

	var btns = [ {
	    text : '增加',
	    click : create,
	    icon : 'add',
	    operNo : 'user_add'
	}, {
	    text : '修改',
	    click : edit,
	    icon : 'modify',
	    operNo : 'user_modify'
	}, {
	    text : '删除',
	    click : deleteBatch,
	    icon : 'delete',
	    operNo : 'user_delete'
	}, {
	    text : '导入',
	    click : importLogicSysNo,
	    icon : 'import',
	    operNo : 'logicSysNo_import'
	}, {
	    text : '导出',
	    click : exportLogicSysNo,
	    icon : 'export',
	    operNo : 'logicSysNo_export'
	}, {
	    text : '配置管理员',
	    click : addAdmin,
	    icon : 'customers',
	    operNo : 'admin_add'
	}, {
	    text : '配置菜单',
	    click : addMenu,
	    icon : 'config',
	    operNo : 'menu_add'
	}, {
	    text : '配置授权资源',
	    click : authRes,
	    icon : 'config',
	    operNo : 'authRes_add'
	}, {
	    text : '配置授权对象',
	    click : authObj,
	    icon : 'config',
	    operNo : 'authObj_add'
	} ];

	BIONE.loadToolbar(grid, btns, function() {
	});

	BIONE.addSearchButtons("#search", grid, "#searchbtn");
    });

    //新建
    function create() {
	dialog = BIONE.commonOpenLargeDialog('添加逻辑系统', 'logicSysWin',
		'${ctx}/bione/admin/logicSys/new');
    }

    //修改
    function edit() {
	var rows = grid.getSelectedRows();
	if (rows.length == 1) {
	    var id = rows[0].logicSysId;
	    dialog = BIONE.commonOpenLargeDialog("修改逻辑系统", "logicSysWin",
		    "${ctx}/bione/admin/logicSys/" + id + "/edit");
	} else {
	    BIONE.tip("请选择需要修改的记录");
	}
    }

    //批量删除
    function deleteBatch() {
	var rows = grid.getSelectedRows();
	if (rows.length == 1) {
	    var ids = '';
	    var i = 0;
	    for (; i < rows.length; i++) {
		ids += rows[i].logicSysId + ',';
	    }
	    $.ligerDialog.confirm('您确定删除这条记录么？', function(yes) {
		if (yes) {
		    $.ajax({
			cache : false,
			async : true,
			url : '${ctx}/bione/admin/logicSys/' + ids,
			type : "DELETE",
			beforeSend : function() {
			    BIONE.loading = true;
			    BIONE.showLoading("正在加载数据中...");
			},
			complete : function() {
			    BIONE.loading = false;
			    BIONE.hideLoading();
			},
			success : function() {
			    BIONE.tip('删除成功');
			    grid.loadData();
			},
			error : function(result, b) {
			    BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		    });
		}
	    });
	} else {
	    BIONE.tip("请选择需要删除的记录");
	}
    }

    //添加逻辑系统管理员
    function addAdmin() {
	var rows = grid.getSelectedRows();
	if (rows.length == 1) {
	    var id = rows[0].logicSysId;
	    dialog = BIONE.commonOpenLargeDialog("配置逻辑系统管理员", "logicAdminWin",
		    "${ctx}/bione/admin/logicSys/" + id + "/adminUser");
	} else {
	    BIONE.tip("请选择需要添加管理员的记录");
	}
    }

    //配置逻辑系统菜单
    function addMenu() {
	var rows = grid.getSelectedRows();
	if (rows.length == 1) {
	    var logicSysNo = rows[0].logicSysNo;
	    dialog = BIONE.commonOpenLargeDialog("配置逻辑系统菜单", "logicMenuWin",
		    "${ctx}/bione/admin/logicSys/addMenu?logicSysNo="
			    + logicSysNo);
	} else {
	    BIONE.tip("请选择需要添加菜单的记录");
	}
    }
    //授权资源
    function authRes() {
	var rows = grid.getSelectedRows();
	if (rows.length == 1) {
	    var logicSysNo = rows[0].logicSysNo;
	    dialog = BIONE.commonOpenLargeDialog("授权资源", "authResWin",
		    "${ctx}/bione/admin/logicSys/authRes?logicSysNo="
			    + logicSysNo);
	} else {
	    BIONE.tip("请选择需要授权的记录");
	}
    }

    //授权对象
    function authObj() {
	var rows = grid.getSelectedRows();
	if (rows.length == 1) {
	    var logicSysNo = rows[0].logicSysNo;
	    dialog = BIONE.commonOpenLargeDialog("授权对象", "authObjWin",
		    "${ctx}/bione/admin/logicSys/authObj?logicSysNo="
			    + logicSysNo);
	} else {
	    BIONE.tip("请选择需要授权的记录");
	}
    }

    // 导入逻辑系统信息
    function importLogicSysNo() {
	BIONE.commonOpenDialog('上传文件', 'upLoad', 562, 334,
		'${ctx}/bione/admin/logicSys/importsys');
    }

    // 导出逻辑系统信息
    function exportLogicSysNo() {
	var ids = achieveIds();
	if (ids.length == 1) {
	    var logicSysNo = ids[0];
	    $('body').append($('<iframe id="download"/>'));
	    $("#download").attr('src','${ctx}/bione/admin/logicSys/exportsys?logicSysNo='
		    + logicSysNo);
	} else if (ids.length > 1) {
	    BIONE.tip('只能选择一条记录');
	} else {
	    BIONE.tip('请选择记录');
	}
    }
    //版权管理
    /* 	function logicSysCopyRight(){
     var rows = grid.getSelectedRows(); 
     if(rows.length==1){
     var logicSysId=rows[0].logicSysId;
     dialog=BIONE.commonOpenLargeDialog("版权授予","logicSysCopyWin","${ctx}/bione/logicSys/logic-sys!copyRight?id="+logicSysId);
     }else {
     BIONE.tip('请选择记录');
    
     }
     } */
  

    // 获取选中的行
    function achieveIds() {
	var ids = [];
	var rows = grid.getSelectedRows();
	for ( var i in rows) {
	    ids.push(rows[i].logicSysNo);
	}
	return ids;
    }
</script>
</head>
<body>
</body>
</html>