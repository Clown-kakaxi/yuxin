<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template0.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];

	$(function() {
		url = "${ctx}/ecif/transaction/txclientauth/list.json";
		//searchForm();
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
		/*	labelWidth : 100,
		 	inputWidth : 220,
			space : 30, */
			fields : [ {
				display : "源系统代码",
				name : "srcSysCd",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "txclientauth.srcSysCd"
				}
			} ]
		});

	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '授权编号',
								name : 'clientAuthId',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								hide :1

							},
							{
								display : '源系统代码',
								name : 'srcSysNm',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '源系统凭证',
								name : 'username',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : 'IP地址',
								name : 'ipaddr',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : 'IPv6地址',
								name : 'ipv6addr',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '物理地址',
								name : 'macaddr',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '开始日期',
								name : 'startDt',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '结束日期',
								name : 'endDt',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '有效性',
								name : 'remark',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag
							} ],
					checkbox : true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortName : 'srcSysCd', //第一次默认排序的字段
					sortOrder : 'asc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [ {
			text : '增加',
			click : resDef_add,
			icon : 'add',
			operNo : 'resDef_add'
		}, {
			text : '修改',
			click : resDef_modify,
			icon : 'modify',
			operNo : 'resDef_modify'
		}, {
			text : '删除',
			click : resDef_delete,
			icon : 'delete',
			operNo : 'resDef_delete'
		}, {
			text : '配置服务授权',
			click : resDef_config,
			icon : 'config',
			operNo : 'resDef_config'
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	function resDef_add(item) {
		BIONE.commonOpenLargeDialog('添加客户端', 'resDefManage',
				'${ctx}/ecif/transaction/txclientauth/new');
	}
	function resDef_modify(item) {
		achieveIds();
		if (ids.length == 1) {
			var buttons = [
					{
						text : '保存',
						onclick : function(item, dialog) {
							BIONE.submitForm($("#mainform",dialog.frame.window.document), function() {
								dialog.close();
								initGrid();
								parent.BIONE.tip('修改成功');
							}, function() {
								BIONE.tip('保存失败');
							});
						}
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ];
			BIONE.commonOpenLargeDialog('修改资源', 'resDefManage',
					'${ctx}/ecif/transaction/txclientauth/' + ids[0] + '/edit', buttons);
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	function resDef_delete(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "DELETE",
						url : "${ctx}/ecif/transaction/txclientauth/" + ids.join(','),
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
	
    //授权资源
    function resDef_config() {
		var rows = grid.getSelectedRows();
		if (rows.length == 1) {
		    var clientAuthId = rows[0].clientAuthId;
		    dialog = BIONE.commonOpenLargeDialog("服务授权", "authResWin",
			    "${ctx}/ecif/transaction/txclientauth/authservice?clientAuthId=" + clientAuthId
				    );
		} else {
		    BIONE.tip("请选择需要授权的记录");
		}
    }	
	// 获取选中的行
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i].clientAuthId);
		}
		
	}
</script>
</head>
<body>
</body>
</html>