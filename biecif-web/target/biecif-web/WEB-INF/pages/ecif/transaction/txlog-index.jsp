<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];
	
	$(function() {
		url = "${ctx}/ecif/transaction/txlog/list.json";
		searchForm();
		initGrid();
		initButtons();
		//BIONE.addSearchButtons("#search", grid, "#searchbtn");
		addSearchButtons("#search", grid, "#searchbtn");
	});
	function greaterThan(){
		var value1 = $('#startDate').val();
		var value2 = $('#endDate').val();
		if(value1 == "" || value2 == ""){
			return true;
		}
		var tdate;
		var fdate;
		tdate = new Date(new Number(value1.substr(0, 4)), new Number(value1.substr(5, 2)) - 1, 
				new Number(value1.substr(8, 2))).valueOf() / (60 * 60 * 24 * 1000);
		fdate = new Date(new Number(value2.substr(0, 4)), new Number(value2.substr(5, 2)) - 1,
				new Number(value2.substr(8, 2))).valueOf() / (60 * 60 * 24 * 1000);
		if(tdate -  fdate > 0){
			BIONE.tip('结束日期不能小于开始日期！');
			return false;
		} 
		return true;
	}
	
	
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
				    "${ctx}/ecif/transaction/txlog/" + ids[0]  +"/edit"  
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
			ids.push(rows[i].txLogId);
		}
		
	}

	
	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		/*	labelWidth : 100,
		 	inputWidth : 220,
			space : 30, */
			fields : [ {
				display : "交易流水号",
				name : "txFwId",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "txlog.txFwId"
				}
			}, {
				display : "交易代码",
				name : "txCode",
				type : "text",
				newline : false,
				cssClass : "field",
				attr : {
					op : "=",
					field : "txlog.txCode"
				}
			}, {
				display : "交易名称",
				name : "txName",
				type : "text",
				newline : false,
				cssClass : "field",
				attr : {
					op : "=",
					field : "txlog.txName"
				}
			},
			{
				name: "srcSysCdBox",
				display: "源系统",
				newline: true,
				type : "select",
				options : {
					valueFieldID : "srcSysCd",
					url : "${ctx}/ecif/transaction/txclientauth/getComBoBox.json",
					ajaxType : "get"
				},
				attr : {
					op : "=",
					field : "txlog.srcSysCd"
				}
			}			
			,
			{
				display : "日期区间(开始)",
				name : "startDate",
				id:'startDate',
				newline : false,
				type : "date",
				options:{
					onChangeDate:function(){
						$("#endDate").focus();
						$("#startDate").focus();
					}
				},
				attr : {
					op : ">=",
					field : "txlog.txDt"
				}

			}, {
				display : "日期区间(结束)",
				name : "endDate",
				id:'endDate',
				newline : false,
				type : "date",
				validate : {
					greaterThan : "startDate"
				},
				attr : {
					op : "<=",
					field : "txlog.txDt"
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
								display : '交易日志标识',
								name : 'txLogId',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								hide :1

							},
							{
								display : '交易流水号',
								name : 'txFwId',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '交易标识',
								name : 'txId',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '交易代码',
								name : 'txCode',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '交易名称',
								name : 'txName',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '交易中文名称',
								name : 'txCnName',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '交易方式',
								name : 'txMethod',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '交易日期',
								name : 'txDt',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								type : "date",
								format : 'yyyy-MM-dd'
							},
							{
								display : '交易时间',
								name : 'txReqTm',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : Long2Date1
							},
							{
								display : '交易结果',
								name : 'txResult',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag3
							}
							,
							{
								display : '源系统代码',
								name : 'srcSysCd',
								align : 'center',
								width : '10%',
								minWidth : '5%'
							},
							{
								display : '源系统简称',
								name : 'srcSysNm',
								align : 'center',
								width : '10%',
								minWidth : '5%'
							}							
							],
					checkbox : true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					delayLoad :true,
					sortName : 'txReqTm', //第一次默认排序的字段
					sortOrder : 'desc',
					onDblClickRow : function(rowdata, rowindex, rowDomElement){//双击选择
					    dialog = BIONE.commonOpenLargeDialog("日志信息", "authResWin",
							    "${ctx}/ecif/transaction/txlog/" + rowdata.txLogId  +"/edit"  
								    );
					},
					toolbar : {}
				});
	}

    
	function RenderFlag3(rowdata){
		if(rowdata.txResult == '0'){
			return '成功';
		}else if(rowdata.txResult == '1'){
			return '警告';
		}else if(rowdata.txResult == '2'){
			return '失败';
		}else{
			return rowdata.txResult;
		}
	}
    
	function Long2Date1(rowdata){
		if(rowdata.txReqTm!=null ){
			return Long2Date(rowdata.txReqTm);
		}
	}
	
	function Long2Date(time){
		if(time!=null){
           var d;
           d = new Date(time);
           return getShortDateTimeString(d);
			
		}
	}
	function getShortDateTimeString(d) {   //如：2011-07-29 13:30:50
	    var year = d.getFullYear();
	    var month = d.getMonth();
	    var day = d.getDate();
	    month = month + 1;
	    if (month < 10) month = '0' + month;
	    if (day < 10) day = '0' + day;
	    var hour = d.getHours();
	    if (hour < 10) {
	        hour = '0' + hour;
	    }
	    var minute = d.getMinutes();
	    if (minute < 10) {
	        minute = '0' + minute;
	    }
	    var second = d.getSeconds();
	    if (second < 10) {
	        second = '0' + second;
	    }
	    //var str = year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
	    var str =  hour + ':' + minute + ':' + second;

	    return str;
	}

</script>
</head>
<body>
</body>
</html>