<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];

	$(function() {
		url = "${ctx}/ecif/alarm/txalarmconf/list.json";
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});

	function RenderFlag(rowdata){
		if(rowdata.alarmObjectType == '0'){
			return '分组';
		}else if(rowdata.alarmObjectType == '1'){
			return '人员';
		}else{
			return rowdata.alarmObjectType;
		}
	}
	
	function RenderFlag2(rowdata){
		if(rowdata.alarmMethod == '0'){
			return '网页';
		}else if(rowdata.alarmMethod == '1'){
			return '邮件';
		}else if(rowdata.alarmMethod == '2'){
			return '短信';
		}else{
			return rowdata.alarmMethod;
		}
	}	
	
	function RenderFlag3(rowdata){
		if(rowdata.confStat == '0'){
			return '无效';
		}else if(rowdata.confStat == '1'){
			return '有效';
		}else{
			return rowdata.confStat;
		}
	}
	
	function RenderFlag5(rowdata){
		if(rowdata.alarmModule == 'SERVICE'){
			return '服务';
		}else if(rowdata.alarmModule == 'TX'){
			return '交易';
		}else if(rowdata.alarmModule == 'SYN'){
			return '同步';
		}else if(rowdata.alarmModule == 'ETL'){
			return 'ETL';
		}else{
			return rowdata.alarmModule;
		}
	}
	
	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		/*	labelWidth : 100,
		 	inputWidth : 220,
			space : 30, */
			fields : [ 
		          {
						display : "报警系统",
						name : "alarmSysBox",
						newline : true,
						type : "select",
						options : {
							valueFieldID : "alarmSys",
							url : "${ctx}/ecif/transaction/txclientauth/getComBoBox.json",
							ajaxType : "get"
						},
						attr : {
							op : "=",
							field : "alarmSys"
						}
					}, {
						display : "报警模块",
						name : "alarmModuleBox",
						newline : false,
						type : "select",
						cssClass : "field",
						options : {
							valueFieldID : 'alarmModule',
							data : [{id : 'SERVICE', text : '服务'},{id : 'TX', text : '交易'}, {id : 'SYN', text : '同步'}, {id : 'ETL', text : 'ETL'}]
						},
						attr : {
							op : "=",
							field : "alarmModule"
						}
					}
            ]
		});

	}
	
	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '报警配置ID',
								name : 'alarmConfId',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								hide:1
							},{
								display : '报警系统',
								name : 'alarmSys',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},{
								display : '报警模块',
								name : 'alarmModule',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag5
							},{
								display : '报警对象类型',
								name : 'alarmObjectType',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag,
								minWidth : '10%'
							},{
								display : '报警对象标识',
								name : 'alarmObjectName',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								minWidth : '10%'
							},{
								display : '报警方式',
								name : 'alarmMethod',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag2,
								minWidth : '10%'
							},{
								display : '配置状态',
								name : 'confStat',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag3,
								minWidth : '10%'
							},{
								display : '报警级别',
								name : 'alarmLevel',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							}],
					checkbox : true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortName : 'alarmConfId', //第一次默认排序的字段
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
		}];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	function resDef_add(item) {
		BIONE.commonOpenLargeDialog('添加', 'resDefManage',
				'${ctx}/ecif/alarm/txalarmconf/new');
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
			BIONE.commonOpenLargeDialog('修改', 'resDefManage',
					'${ctx}/ecif/alarm/txalarmconf/' + ids[0] + '/edit', buttons);
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
						url : "${ctx}/ecif/alarm/txalarmconf/" + ids.join(','),
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
	
	// 获取选中的行
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i].alarmConfId);
		}
		
	}
</script>
</head>
<body>
</body>
</html>