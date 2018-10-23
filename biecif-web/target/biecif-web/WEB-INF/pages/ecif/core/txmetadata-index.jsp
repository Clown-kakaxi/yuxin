<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];

	$(function() {
		url = "${ctx}/ecif/core/tabdef/listmetadatachange.json";
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});

	function RenderFlag(rowdata){
		if(rowdata.state == '1'){
			return '已处理';
		}else if(rowdata.state == '0'){
			return '未处理';
		}else{
			return rowdata.state;
		}
	}

	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		/*	labelWidth : 100,
		 	inputWidth : 220,
			space : 30, */
			fields : [ {
				display : "表名",
				name : "tabName11",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "tabdef.tabName"
				}
			} ]
		});

	}
	
	window.document.onkeydown = function(e) {
		e = !e ? window.event : e;
		var key = window.event ? e.keyCode : e.which;
		if (key == 13) {
			return false;
		}
	};
	
	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '结果ID',
								name : 'resultId',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								hide :1
							},
							{
								display : '表名',
								name : 'tabName',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '列名',
								name : 'colName',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '字段类型',
								name : 'dataType',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '长度',
								name : 'dataLen',
								align : 'center',
								width : '5%',
								minWidth : '10%'
							},
							{
								display : '精度',
								name : 'dataPrec',
								align : 'center',
								width : '5%',
								minWidth : '10%'
							},
							{
								display : '可否为空',
								name : 'nulls',
								align : 'center',
								width : '5%',
								minWidth : '10%'
							},
							{
								display : '元数据变化描述',
								name : 'changeDesc',
								align : 'center',
								width : '30%',
								minWidth : '10%'
							},
							{
								display : '元数据处理状态',
								name : 'state',
								align : 'center',
								width : '10%',
								minWidth : '5%',
								render : RenderFlag
							},
							{
								display : '关联交易',
								name : 'txCodesDesc',
								align : 'center',
								width : '20%',
								minWidth : '10%'

							
							}],
					checkbox : true,
//					delayLoad :true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'get',
					url : url,
					sortName : 'tabName', //第一次默认排序的字段
					sortOrder : 'asc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [ {
			text : '同步元数据',
			click : resDef_config,
			icon : 'modify',
			operNo : 'resDef_config'
		}	, {
			text : '删除过期信息',
			click : resDef_delete,
			icon : 'refresh',
			operNo : 'resDef_delete'
		} 

		/**
		, {
			text : '同步交易报文',
			click : resDef_sync,
			icon : 'refresh',
			operNo : 'resDef_sync'
		} 
		**/
		];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	
    //授权资源
    function resDef_config() {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('元数据将根据数据库表进行改变（元数据变化描述已显示数据库值）,‘数据库没有此列’请手工同步元数据。确实要同步这些改变吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "POST",
						url : "${ctx}/ecif/core/tabdef/changemetadata/" + ids.join(','),
						dataType : "script",
						success : function() {
							flag = true;
						}
					});
					if (flag == true) {
						BIONE.tip('同步成功');
						initGrid();
					} else {
						BIONE.tip('同步失败');
					}
				}
			});
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
						url : "${ctx}/ecif/core/tabdef/changemetadata/" + ids.join(','),
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
    function resDef_sync() {
		var rows = grid.getSelectedRows();
		if (rows.length == 1) {
		    var tabId = rows[0].tabId;
		    dialog = BIONE.commonOpenLargeDialog("字段展示", "authResWin",
			    "${ctx}/ecif/core/tabdef/authservice?tabId=" + tabId
				    );
		} else {
		    BIONE.tip("请选择记录");
		}
    }
       
    
    
	// 获取选中的行
	function achieveIds() {
		ids = [];
		tips = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i].resultId);
		}
	}
	
	BIONE.commonOpenLargeDialog = function commonOpenLargeDialog(title, name,
			url, beforeClose) {
		var width = 1000;
		var height = 500;
		var _dialog = $.ligerui.get(name);
		if (_dialog) {
			$.ligerui.remove(name);
		}
		_dialog = $.ligerDialog.open({
			height : height,
			width : width,
			url : url,
			name : name,
			id : name,
			title : title,
			isResize : false,
			isDrag : false,
			isHidden : false
		});
		if(beforeClose!=null &&
				typeof beforeClose == "function"){
			_dialog._beforeClose = beforeClose;
		}
		return _dialog;
	};	
	
	function tx_modify(txId) {
		
		commonOpenLargeDialog("报文信息", "authResWin",
			    "${ctx}/ecif/transaction/txmsg/" + txId + "/index"
				    );
		
	}
	
	commonOpenLargeDialog = function commonOpenLargeDialog(title, name,
			url, beforeClose) {
		var width = 1100;
		var height = 500;
		var _dialog = $.ligerui.get(name);
		if (_dialog) {
			$.ligerui.remove(name);
		}
		_dialog = $.ligerDialog.open({
			height : height,
			width : width,
			url : url,
			name : name,
			id : name,
			title : title,
			isResize : false,
			isDrag : true,
			isHidden : false
		});
		if(beforeClose!=null &&
				typeof beforeClose == "function"){
			_dialog._beforeClose = beforeClose;
		}
		return _dialog;
	};
		

</script>
</head>
<body>
</body>
</html>