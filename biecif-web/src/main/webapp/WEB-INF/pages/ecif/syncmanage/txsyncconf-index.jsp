<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url;
	var state = {"syncConfStat": {"0": "失效", "1": "有效"}, "syncMode": {"S": "同步", "A": "异步"}, "syncMethod": {"0": "订阅发布", "1": "主动调用"}, "syncDealMethod": {"HTTP": "http方式", "YCESB": "宇信ESB方式", "WS": "web service方式", "MQ": "MQ方式", "SOCKET": "SOCKET方式"}, "isRetry": {"1": "是", "0": "否"}, "syncFailStrategy": {"0": "重发", "1": "放弃"}};
	$(function() {
		url = "${ctx}/ecif/syncmanage/syncconf/list.json";
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});
	
	function initButtons() {
		btns = [ {
			text: '增加',
			click: syncconf_add,
			icon: 'add',
			operNo: 'syncconf_add'
		}, {
			text: '修改',
			click: syncconf_modify,
			icon: 'modify',
			operNo: 'syncconf_modify'
		}, {
			text: '删除',
			click: syncconf_delete,
			icon: 'delete',
			operNo: 'syncconf_delete'
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	function syncconf_add() {
		BIONE.commonOpenLargeDialog('添加', 'frame',
				'${ctx}/ecif/syncmanage/syncconf/new', function() {
					grid.loadData();
				});
	}
	
	function syncconf_modify() {
		var ids = achieveIds();
		if (ids.length == 1) {
			BIONE.commonOpenLargeDialog('修改', 'frame',
					'${ctx}/ecif/syncmanage/syncconf/' + ids[0] + '/edit', function() {
						grid.loadData();
					});
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	function syncconf_delete(item) {
		var ids = achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					$.ajax({
						async: false,
						type: "DELETE",
						url: "${ctx}/ecif/syncmanage/syncconf/" + ids.join(','),
						dataType: "script",
						success: function() {
							grid.loadData();
							BIONE.tip("删除成功！");
						},
						error: function() {
							BIONE.tip("删除失败！");
						}
					});
				}
			});
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	// 获取选中的行
	function achieveIds() {
		var ids = [];
		var rows = grid.getSelectedRows();
		$.each(rows, function(i, n) {
			ids.push(n.syncConfId);
		});
		return ids;
	}

	
	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
			fields: [ {
				display: "交易代码",
				name: "txCode",
				type: "text",
				newline: false,
				cssClass: "field",
				attr: {
					op: "=",
					field: "t.txCode"
				}
			}]
		});

	}

	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			height: '100%',
			width: '100%',
			columns: [{
				display: '交易代码',
				name: 'txCode',
				align: 'center',
				width: '10%',
				minWidth: '5%'
			}, {
				display: '源系统',
				name: 'srcSysNo',
				align: 'center',
				width: '10%',
				minWidth: '5%'
			}, {
				display: '目标系统',
				name: 'destSysNo',
				align: 'center',
				width: '10%',
				minWidth: '5%'
			}, {
				display: '同步配置状态',
				name: 'syncConfStat',
				align: 'center',
				width: '10%',
				minWidth: '5%',
				render: function(data) {
					return state.syncConfStat[data.syncConfStat];
				}
			}, {
				display: '同步配置描述',
				name: 'syncConfDesc',
				align: 'center',
				width: '10%',
				minWidth: '5%'
			}, {
				display: '数据同步模式',
				name: 'syncMode',
				align: 'center',
				width: '10%',
				minWidth: '5%',
				render: function(data) {
					return state.syncMode[data.syncMode];
				}
			}, {
				display: '数据同步方式',
				name: 'syncMethod',
				align: 'center',
				width: '10%',
				minWidth: '5%',
				render: function(data) {
					return state.syncMethod[data.syncMethod];
				}
			}, {
				display: '同步处理方式',
				name: 'syncDealMethod',
				align: 'center',
				width: '10%',
				minWidth: '5%',
				render: function(data) {
					return state.syncDealMethod[data.syncDealMethod];
				}
			}, {
				display: '同步内容定义',
				name: 'syncContentDef',
				align: 'center',
				width: '10%',
				minWidth: '5%'
			}, {
				display: '同步内容描述',
				name: 'syncContentDesc',
				align: 'center',
				width: '10%',
				minWidth: '5%'
			}, {
				display: '同步处理类',
				name: 'syncDealClass',
				align: 'center',
				width: '10%',
				minWidth: '5%'
			}, {
				display: '是否重试',
				name: 'isRetry',
				align: 'center',
				width: '10%',
				minWidth: '5%',
				render: function(data) {
					return state.isRetry[data.isRetry];
				}
			}, {
				display: '最大重试次数',
				name: 'maxRetry',
				align: 'center',
				width: '10%',
				minWidth: '5%'
			}, {
				display: '失败处理策略',
				name: 'syncFailStrategy',
				align: 'center',
				width: '10%',
				minWidth: '5%',
				render: function(data) {
					return state.syncFailStrategy[data.syncFailStrategy];
				}
			}, {
				display: '创建人',
				name: 'createOper',
				align: 'center',
				width: '10%',
				minWidth: '5%'
			}, {
				display: '创建时间',
				name: 'createTime',
				align: 'center',
				width: '10%',
				minWidth: '5%',
				render: function(date) {
					return toDate(date.createTime);
				}
			}, {
				display: '修改人',
				name: 'updateOper',
				align: 'center',
				width: '10%',
				minWidth: '5%'
			}, {
				display: '修改时间',
				name: 'updateTime',
				align: 'center',
				width: '10%',
				minWidth: '5%',
				render: function(date) {
					return toDate(date.updateTime);
				}
			}, {
				display: '审批人',
				name: 'approvalOper',
				align: 'center',
				width: '10%',
				minWidth: '5%'
			}, {
				display: '审批时间',
				name: 'approvalTime',
				align: 'center',
				width: '10%',
				minWidth: '5%',
				render: function(date) {
					return toDate(date.approvalTime);
				}
			}, {
				display: '生效时间',
				name: 'effectiveTime',
				align: 'center',
				width: '10%',
				minWidth: '5%',
				render: function(date) {
					return toDate(date.effectiveTime);
				}
			}, {
				display: '失效时间',
				name: 'expiredTime',
				align: 'center',
				width: '10%',
				minWidth: '5%',
				render: function(date) {
					return toDate(date.expiredTime);
				}
			}],
			checkbox: true,
			usePager: true,
			isScroll: false,
			rownumbers: true,
			alternatingRow: true,//附加奇偶行效果行
			colDraggable: true,
			dataAction: 'server',//从后台获取数据
			method: 'post',
			url: url,
			delayLoad:false,
			toolbar: {}
		});
	}

	function toDate(data) {
		if (data) {
			data = Number(data);
			var date = new Date(data);
			var s = "";
			s = date.getFullYear() + "-";
			s += ("0"+(date.getMonth() + 1)).slice(-2) + "-";
			s += ("0"+date.getDate()).slice(-2);
			return s;
		}
		return "";
	}
    

</script>
</head>
<body>
</body>
</html>