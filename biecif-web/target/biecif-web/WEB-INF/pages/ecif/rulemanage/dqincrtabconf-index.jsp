<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template0.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];

	$(function() {
		url = "${ctx}/ecif/rulemanage/dqincrtabconf/list.json";
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});

	function RenderFlag(rowdata){
		if(rowdata.flag == '1'){
			return '有效';
		}else if(rowdata.flag == '0'){
			return '无效';
		}else{
			return rowdata.flag;
		}
	}

	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		 	labelWidth : 110,
			inputWidth : 110,
			space : 10, 
			fields : [ {
				display : "源表模式名",
				name : "schsrc",
				align : 'left',
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "schsrc"
				}
			}, {
				display : "目标表模式名",
				name : "schdst",
				align : 'left',
				newline : false,
				type : "text",
				cssClass : "field",
			 	labelWidth : 200,
				width : 250,
				attr : {
					op : "=",
					field : "schdst"
				}
			}]
		});
	}

	function initGrid() {
		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [{
						display : 'ID',
						name : 'id',
						align : 'left',
						width : 120,
						minWidth : 80,
						hide :1

					},
					{
						display : '源表模式名',
						name : 'schsrc',
						align : 'left',
						width : 120,
						minWidth : 80
					},
					{
						display : '目标表模式名',
						name : 'schdst',
						align : 'left',
						width : 120,
						minWidth : 80
					},
					{
						display : '源表英文名',
						name : 'srcTab',
						align : 'left',
						width : 180,
						minWidth : 150
					},
					{
						display : '目标表英文名',
						name : 'dstTab',
						align : 'left',
						width : 180,
						minWidth : 150
					},
					{
						display : '有效性校验',
						name : 'type',
						align : 'left',
						width : 150,
						minWidth : 120
					},
					{
						display : '覆盖规则',
						name : 'app',
						align : 'left',
						width : 120,
						minWidth : 80
					},
					{
						display : '源表主键',
						name : 'key',
						align : 'left',
						width : 180,
						minWidth : 150
					},
					{
						display : '源表关联条件',
						name : 'srcJoin',
						align : 'left',
						width : 180,
						minWidth : 150
					},
					{
						display : '目标表关联条件',
						name : 'dstJoin',
						align : 'left',
						width : 180,
						minWidth : 150
					},
					{
						display : '系统优先级字段英文名',
						name : 'sysCol',
						align : 'center',
						width : 150,
						minWidth : 120
					} ,
					{
						display : '跑数时间',
						name : 'hisOperTime',
						align : 'left',
						width : 150,
						minWidth : 120
					} ,
					{
						display : '历史附加字段',
						name : 'bakCol',
						align : 'left',
						width : 150,
						minWidth : 120
					} ,
					{
						display : '历史附加字段值',
						name : 'bakVal',
						align : 'left',
						width : 150,
						minWidth : 120
					}  ],
					checkbox : true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortName : 'id', //第一次默认排序的字段
					sortOrder : 'asc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [ {
			text : '新增',
			click : dqincrtabconf_add,
			icon : 'add',
			operNo : 'dqincrtabconf_add'
		}, {
			text : '修改',
			click : dqincrtabconf_modify,
			icon : 'modify',
			operNo : 'dqincrtabconf_modify'
		}, {
			text : '删除',
			click : dqincrtabconf_delete,
			icon : 'delete',
			operNo : 'dqincrtabconf_delete'
		}, {
			text : '配置字段',
			click : dqincrtabconf_config,
			icon : 'config',
			operNo : 'dqincrtabconf_config'
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	function dqincrtabconf_add() {
		BIONE.commonOpenLargeDialog('新增', 'dqincrtabconfManage',
				'${ctx}/ecif/rulemanage/dqincrtabconf/new');
	}
	function dqincrtabconf_modify(item) {
		achieveIds();
		if (ids.length == 1) {
			BIONE.commonOpenDialog('修改', 'dqincrtabconfManage',800,400,
					'${ctx}/ecif/rulemanage/dqincrtabconf/' + ids[0] + '/edit');
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录!');
		} else {
			BIONE.tip('请选择记录!');
		}
	}
	function dqincrtabconf_delete(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "DELETE",
						url : "${ctx}/ecif/rulemanage/dqincrtabconf/" + ids.join(','),
						dataType : "script",
						success : function() {
							flag = true;
						}
					});
					if (flag == true) {
						BIONE.tip('删除成功');
						initGrid();
					} else {
						BIONE.tip('删除失败');
					}
				}
			});
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
    //配置字段
    function dqincrtabconf_config() {
		var rows = grid.getSelectedRows();
		if (rows.length == 1) {
		    var id = rows[0].id;
		    var dstTab = rows[0].dstTab;
		    dialog = BIONE.commonOpenDialog(dstTab + "表配置字段", "dqincrcolconfWin", 650, 380, 
			    "${ctx}/ecif/rulemanage/dqincrtabconf/dqincrcolconf?id=" + id + "&dstTab=" + dstTab
				    );
		} else {
		    BIONE.tip("请选择需要配置的表");
		}
    }	
	// 获取选中的行
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i].id);
		}
		
	}
</script>
</head>
<body>
</body>
</html>