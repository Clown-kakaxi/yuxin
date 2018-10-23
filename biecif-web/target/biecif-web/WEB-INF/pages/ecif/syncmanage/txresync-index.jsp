<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid;
	function reloadGrid() {
		var manager = $("#maingrid").ligerGetGridManager();
		manager.loadData();
	};
	$(init);

	//初始化函数
	function init() {
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
			fields : [ {
				display : "交易代码",
				name : "txCode",
				newline : true,
				//labelWidth : 100,
				width : 150,
				space : 30,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",// "=" , 
					vt : "string",
					field : "t.txCode"
				}
			}, {
				display : "事件发生系统",
				name : "eventSysNo",
				newline : false,
				//labelWidth : 100,
				width : 150,
				space : 30,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "t.eventSysNo"
				}
			},{
				display : "手动处理状态",
				name : "manualStat",
				newline : false,
				type : "select",
				width : 150,
				cssClass : "field",
				options : {
					valueFieldID : 'manualStatType',
					data : [{id : '0', text : '未手动处理'},{id : '1', text : '手动处理中'},{id : '2', text : '手动处理成功'},{id : '3', text : '手动处理失败'}]
					
				},
				attr : {
					op : "=",
					field : "t.manualStat"
				}
	         } ,{
					display : "系统同步处理结果",
					name : "eventDealResult",
					newline : false,
					width : 150,
					type : "select",
					cssClass : "field",
					options : {
						valueFieldID : 'evtDealResultType',
						data : [{id : '000000', text : '同步成功'},{id : '1', text : '同步失败'}]
						
					},
					attr : {
						op : "=",
						field : "t.eventDealResult"
					}
		         }]
		});

	}
	function RenderFlag(rowdata){
		
		if(rowdata.eventDealResult == '000000'){
			return '<font color=green>同步成功</font>';
		}else if(rowdata.eventDealResult == null){
			return '<font color=red>无结果</font>';
		}else{
			return '<font color=red>同步失败：'+rowdata.eventDealResult+'</font>';
		}
	}
	function RenderFlag1(rowdata){
		
		if(rowdata.manualStat == '0'){
			return '未手动处理';
		}else if(rowdata.manualStat == '1'){
			return '<font color=green>手动处理中...</font>';
		}else if(rowdata.manualStat == '2'){
			return '<font color=green>手动处理成功</font>';
		}else if(rowdata.manualStat == '3'){
			return '<font color=red>手动处理失败</font>';
		}else{
			return '<font color=red>手动处理状态出错</font>';
		}
	}

	//初始化Grid
	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			height : '100%',
			width : '100%',
			columns : [ {
				display : '事件标识',
				hide: true,
				align : 'left',
				name:'eventId',
				type: 'string',
				minWidth : '10%',
				width : 100
			},{
				display : '事件名称',
				name : 'eventName',
				align : 'left',
				minWidth : '15%',
				width : 100
			},{
				display : '事件描述',
				name : 'eventDesc',
				align : 'left',
				minWidth : '20%',
				width : 100
			} ,{
				display : '事件发生系统',
				name : 'eventSysNo',
				align : 'left',
				minWidth : '20%',
				width : 100
			} ,{
				display : '交易代码',
				name : 'txCode',
				align : 'left',
				minWidth : '20%',
				width : 100
			} ,{
				display : '客户标识',
				name : 'custId',
				align : 'left',
				minWidth : '20%',
				width : 100
			} , {
				display : '客户编号',
				name : 'custNo',
				align : 'left',
				minWidth : '20%',
				width : 100
			} ,{
				display : '手动处理状态',
				name : 'manualStat',
				align : 'left',
				minWidth : '20%',
				render : RenderFlag1,
				width : 120
			},{
				display : '系统同步处理结果',
				name : 'eventDealResult',
				align : 'left',
				minWidth : '20%',
				render : RenderFlag,
				width : 120
			} ,  {
				display : '事件类型',
				name : 'eventType',
				align : 'left',
				minWidth : '10%',
				width : 100
			}, {
				display : '事件类别',
				name : 'eventKind',
				align : 'left',
				minWidth : '10%',
				width : 100
			},  {
				display : '事件发生时间',
				name : 'eventTime',
				align : 'left',
				type: 'date',
				minWidth : '20%',
				width : 100
			} , {
				display : '事件发生位置',
				name : 'eventLocation',
				align : 'left',
				minWidth : '20%',
				width : 100
			} ,   {
				display : '交易流水号',
				name : 'txFwId',
				align : 'left',
				minWidth : '20%',
				width : 100
			} ,  {
				display : '关键凭证',
				name : 'keyInfo',
				align : 'left',
				minWidth : '20%',
				width : 100
			} , {
				display : '事件处理方式',
				name : 'eventDealType',
				align : 'left',
				minWidth : '20%',
				width : 100
			} , {
				display : '事件处理状态',
				name : 'eventDealStat',
				align : 'left',
				minWidth : '20%',
				width : 100
			} , {
				display : '事件处理时间',
				name : 'eventDealTime',
				align : 'left',
				type : 'date',
				minWidth : '20%',
				width : 100
			}  , {
				display : '事件处理信息',
				name : 'eventDealInfo',
				align : 'left',
				minWidth : '20%',
				width : 150
			} ],
			checkbox : true,
			usePager : true,
			isScroll : false,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : '${ctx}/ecif/syncmanage/resync/list.json',
			sortName : 'eventDealTime', //第一次默认排序的字段
			sortOrder : 'desc',
			toolbar : {}
		});
	}

	//初始化Button
	function initButtons() {
		var btns;
		btns = [ {
			text : '手动同步',
			click : re_sync,
			icon : 'export',
			operNo : 're_sync'
		}];
		BIONE.loadToolbar(grid, btns, function() {
		});
	}

	//增加系统变量
	function re_sync(item) {
		var rows = grid.getSelectedRows();
		var ids = '';
		var i = 0;
		if (rows.length > 0) {
			for (; i < rows.length; i++) {
				if(rows[i].eventDealResult=='000000'){
					$.ligerDialog.warn('操作失败：您选择的记录中包含已同步成功的记录！');
					return;
				}else{
					if(rows[i].eventDealStat=='1'){
						$.ligerDialog.warn('操作失败：您选择的记录中包含已处于等待的事件！');
						return;
					}else{
						if(rows[i].manualStat=='1'){
							$.ligerDialog.warn('操作失败：您选择的记录中包含“手动同步处理中”状态的记录！');
							return;
						}else if(rows[i].manualStat=='2'){
							$.ligerDialog.warn('操作失败：您选择的记录中包含已“手动同步成功”的记录！');
							return;
						}
					}
					
				}
					
				ids += rows[i].eventId + ',';
				
			}
			$.ligerDialog
					.confirm(
							'您确定同步这' + i + "条记录吗？",
							function(yes) {
								if (yes) {
									var flag = false;
									$.ajax({
										async : false,
										type : "POST",
										url : "${ctx}/ecif/syncmanage/resync/" + ids+"/resync",
										dataType : "script",
										success : function() {
											flag = true;
										}
									});
									if (flag == true) {
										BIONE.tip('成功开始手动同步');
										initGrid();
									} else {
										BIONE.tip('手动同步操作失败');
									}
									
								} else {
									BIONE.tip('取消同步');
								}
							});
		} else {
			BIONE.tip('请选择记录');
		}
	}

	
	
</script>

</head>
<body>
</body>
</html>