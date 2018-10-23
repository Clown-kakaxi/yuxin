<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];
	
	$(function() {
		url = "${ctx}/ecif/alarm/txalarmweb/list.json";
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
		//addSearchButtons("#search", grid, "#searchbtn");
		setInterval(function(){
			form=$("#search");
			var loading=$(".l-grid-loading");
			if(loading&&loading.css("display")=="block"){
				BIONE.tip('数据正在加载中,请稍后进行查询操作!');
			}else{
			
				var rule = BIONE.bulidFilterGroup(form);
				if (rule.rules.length) {
					grid.set('parms', {
						condition : JSON2.stringify(rule)
					});
					grid.set('newPage', 1);
				} else {
					grid.set('parms', {
						condition : ''
					});
					grid.set('newPage', 1);
				}
				/* @Revision 20130704182000 liucheng2@yuchengtech.com
				 * 防止grid进行ajax取数时session失效而无法正确获取数据； */
				if (!grid.loading || grid.loading!=true) {
					var opurl = grid.options.url;
					var ctx = "/";
					if (opurl) {
						ctx = "/" + opurl.split("/")[1] + "/";
					}
					$.ajax({
						cache : false,
						async : true,
						url : ctx + "/bione/common/getComboxData.json",
						dataType : 'json',
						data : {
							"paramTypeNo" : ""
						},
						type : grid.options.type,
						complete : function(XMLHttpRequest) {
							BIONE.isSessionAlive(XMLHttpRequest);
						},
						success : function() {
							grid.loadData();
						}
					});
				} else {
					BIONE.showError("查询进行中，请勿重复查询！");
				}
				/* @Revision 20130704182000 END */
			}	
		},50000);
	});
	

	
	// 创建表单搜索按钮：搜索、高级搜索
	addSearchButtons = function(form, grid, btnContainer) {
		if (!form)
			return;
		form = $(form);
		if (btnContainer) {
			BIONE.createButton({
				appendTo : btnContainer,
				text : '搜索',
				icon : 'search3',
				width : '50px',
				click : function() {
					var loading=$(".l-grid-loading");
					if(loading&&loading.css("display")=="block"){
						BIONE.tip('数据正在加载中,请稍后进行查询操作!');
					}else{
					
					if(greaterThan()){
						var rule = BIONE.bulidFilterGroup(form);
						if (rule.rules.length) {
							grid.set('parms', {
								condition : JSON2.stringify(rule)
							});
							grid.set('newPage', 1);
						} else {
							grid.set('parms', {
								condition : ''
							});
							grid.set('newPage', 1);
						}
						/* @Revision 20130704182000 liucheng2@yuchengtech.com
						 * 防止grid进行ajax取数时session失效而无法正确获取数据； */
						if (!grid.loading || grid.loading!=true) {
							var opurl = grid.options.url;
							var ctx = "/";
							if (opurl) {
								ctx = "/" + opurl.split("/")[1] + "/";
							}
							$.ajax({
								cache : false,
								async : true,
								url : ctx + "/bione/common/getComboxData.json",
								dataType : 'json',
								data : {
									"paramTypeNo" : ""
								},
								type : grid.options.type,
								complete : function(XMLHttpRequest) {
									BIONE.isSessionAlive(XMLHttpRequest);
								},
								success : function() {
									grid.loadData();
								}
							});
						} else {
							BIONE.showError("查询进行中，请勿重复查询！");
						}
						/* @Revision 20130704182000 END */
					}
					}
				}
			});
			BIONE.createButton({
				appendTo : btnContainer,
				text : '重置',
				icon : 'refresh2',
				width : '50px',
				click : function() {
					$(":input", form)
							.not(":submit, :reset,:hidden,:image,:button, [disabled]")
							.each(function() {
								$(this).val("");
							});
					$(":input[ltype=combobox]", form)
					.each(function() {
						$(this).val("");
					});
				}
			});
		}
	};
	
	function initButtons() {
		btns = [ {
			text : '查看详细信息',
			click : resDef_modify,
			icon : 'modify',
			operNo : 'resDef_modify'
		}];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}
	
	function resDef_modify(item) {
		achieveIds();
		if (ids.length == 1) {
			
		    BIONE.commonOpenLargeDialog("日志信息", "authResWin", 
				    "${ctx}/ecif/alarm/txalarmweb/" + ids[0]  +"/edit"  
					    );
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	// 获取选中的行
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i].alarmId);
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
					initValue : "ECIF",
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
					data : [{id : 'SERVICE', text : '服务(SERVICE)'},{id : 'TX', text : '交易(TX)'}, {id : 'SYN', text : '同步(SYN)'}, {id : 'ETL', text : 'ETL(ETL)'}]
				},
				attr : {
					op : "=",
					field : "alarmModule"
				}
			}, {
				display : "报警状态",
				name : "alarmStatBox",
				type : "select",
				newline : false,
				cssClass : "field",
				options : {
					valueFieldID : 'alarmStat',
					data : [{id : '1', text : '未处理'}, {id : '2', text : '已处理'}]
				},
				attr : {
					op : "=",
					field : "alarmStat"
				}
			},{
				display : "发生日期",
				name : "occurDate",
				id:'occurDate',
				newline : true,
				type : "date",
				options:{
					onChangeDate:function(){
						$("#occurDate").focus();
					}
				},
				attr : {
					op : "=",
					field : "occurDate"
				}
			},{
				display : "报警级别",
				name : "alarmLevel",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "alarmLevel"
				}
		    }, {
				display : "发起系统",
				name : "srcSysCdName",
				newline : false,
				type : "select",
				options : {
					valueFieldID : "srcSysCd",
					url : "${ctx}/ecif/transaction/txclientauth/getComBoBox.json",
					ajaxType : "get"
				},
				attr : {
					op : "=",
					field : "srcSysCd"
				}
			}]
		});

	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '报警编号',
								name : 'alarmId',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								hide :1
							},
							/**
							{
								display : '报警系统',
								name : 'alarmSys',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},**/
							{
								display : '报警模块',
								name : 'alarmModule',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag5
							},
							{
								display : '报警级别',
								name : 'alarmLevel',
								align : 'center',
								width : '5%',
								minWidth : '10%'
							},
							{
								display : '错误代码',
								name : 'errorCode',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '报警内容',
								name : 'alarmInfo',
								align : 'center',
								width : '25%',
								minWidth : '10%',
								render: function (row) {  
								       var html = row.alarmStat == '2' ? row.alarmInfo : "<span style='color:red'>"+ row.alarmInfo +"</span>";  
								       return html;  
								}  								
							},
							{
								display : '发生日期',
								name : 'occurDate',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								type : "date",
								format : 'yyyy-MM-dd'
							},
							{
								display : '发生时间',
								name : 'occurTime',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								type : "date",
								format : 'hh:mm:ss'
							},
							{
								display : '发起系统',
								name : 'srcSysCd',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '报警状态',
								name : 'alarmStat',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag3
							},
							{
								display : '发送次数',
								name : 'sendNum',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '用户名称',
								name : 'userName',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '用户称谓',
								name : 'userTitle',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							} ],
					checkbox : true,
					delayLoad :false,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortName : 'txDt', //第一次默认排序的字段
					sortOrder : 'desc',
					onDblClickRow : function(rowdata, rowindex, rowDomElement){//双击选择
					    dialog = BIONE.commonOpenLargeDialog("详细信息", "authResWin",
							    "${ctx}/ecif/alarm/txalarmweb/"+ rowdata.alarmId +"/edit" 
								    );
					},
					toolbar : {}
				});
	}

	function RenderFlag3(rowdata){
		if(rowdata.alarmStat == '1'){
			return '未处理';
		}else if(rowdata.alarmStat == '2'){
			return '已处理';
		}else{
			return rowdata.alarmStat;
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
	
</script>
</head>
<body>
</body>
</html>