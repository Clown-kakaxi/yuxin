<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">	
	var grid, btns, url;
	var sdateTemp = edateTemp = orgTemp = "";
	$(init);
	function init() {
		url = "${ctx}/ecif/report/closeaccount/list.json";
		initGrid();
		searchForm();
		initButtons();
		addSearchButtons("#search", grid, "#searchbtn");
	};
	function checkTime(sDate){
		if(sDate == null || sDate.length == 0){
			BIONE.tip('统计日期必填！');
			return false;
		}
		if(sDate.length != 10 ){
			BIONE.tip('统计日期格式为yyyy-MM-dd！');
			return false;
		}
		if (!/^[0-9]{4}-[0-9]{2}-[0-9]{2}$/.test(sDate)) {
			BIONE.tip('统计日期格式为yyyy-MM-dd！');
			return false;
		}
		var year, month, day;
		year = sDate.substring(0, 4);
		month = sDate.substring(5, 7);
		day = sDate.substring(8, 10);
		var iaMonthDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
		if (year < 1900 || year > 9999){
			BIONE.tip('请输入有效的统计年份 [1900]<=[年]<=[9999]！');
			return false;
		}
		if (month < 1 || month > 12){
			BIONE.tip('统计月份不合法！');
			return false;
		}
		if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)){
			iaMonthDays[1] = 29;
		}
		if (day < 1 || day > iaMonthDays[month - 1]){
			BIONE.tip('统计日期不合法！');
			return false;
		}
		return true;
	}
	function greaterThan(){
		var value1 = $('#srptMonth').val();
		var value2 = $('#erptMonth').val();
		if(value1 == "" || value2 == ""){
			return false;
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
					
					if(checkTime($('#srptMonth').val()) && checkTime($('#erptMonth').val()) && greaterThan()){
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
						sdateTemp = $('#srptMonth').val();
						edateTemp = $('#erptMonth').val();
						var optionsJSON = $('#createBranchNo').attr("ligerui"), options;
						if (optionsJSON) {
							options = JSON2.parse(optionsJSON);
						}
						orgTemp = $("#" + options.valueFieldID).val();
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
	function searchForm() {
		$("#search").ligerForm({
			fields : [{
				display : "<font color='red'>*</font>销户开始日期",
				name : "srptMonth",
				newline : true,
				type : "date",
				cssClass : "field",
				width : 150,
			 	labelWidth : 100,
				attr : {
					op : "=",
					field : "srptMonth"
				}
			},{
				display : "<font color='red'>*</font>销户结束日期",
				name : "erptMonth",
				newline : false,
				type : "date",
				cssClass : "field",
				width : 150,
			 	labelWidth : 100,
				attr : {
					op : "=",
					field : "erptMonth"
				}
			},{
				display : "开户机构",
				name : "createBranchNo",
				newline : false,
				type : "select",
				cssClass : "field",		
				width : 300,
				options : {
	                width : 300, 
	                selectBoxWidth: 298,
	                selectBoxHeight: 300, 
					valueFieldID : "id", 
	                treeLeafOnly: false,
	                tree: {
	                	url : '${ctx}/ecif/report/orgtreelist/list.json'
					}
				},
				attr : {
					op : "=",
					field : "createBranchNo"
				}
			}]
		});
	};

	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			columns : [{
				display : '开户机构',
				name : 'createbranchno',
				align : 'center',
				width : 150,
				minWidth : 150
			}, {
				display : '开户机构名',
				name : 'branchname',
				align : 'left',
				width : 250,
				minWidth : 200
			}, {
				display : '客户号',
				name : 'custno',
				align : 'center',
				width : 150,
				minWidth : 150
			}, {
				display : '客户名称',
				name : 'custname',
				align : 'center',
				width : 200,
				minWidth : 150
			}, {
				display : '年日均',
				name : 'depositbaldayavg',
				align : 'center',
				width : 150,
				minWidth : 120
			}, {
				display : '开户日',
				name : 'createtime',
				align : 'center',
				width : 100,
				minWidth : 80
			}, {
				display : '销户日',
				name : 'rptdate',
				align : 'center',
				width : 100,
				minWidth : 80
			}],
			checkbox : false,
			usePager : true,
			isScroll : false,
			rownumbers : false,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'holdingId',//第一次默认排序的字段
			sortOrder : 'asc', //排序的方式
			pageParmName : 'page',
			pagesizeParmName : 'pagesize',
			toolbar : {}
		});
	};

	function initButtons() {
		btns = [
		/*动态维护功能按钮*/
		{
			text : '下载',
			click : file_down,
			icon : 'export',
			operNo : 'file_down'
		}];
		BIONE.loadToolbar(grid, btns, function(){});
	}
	
	function doDownload(file) {
		if(file==null||file==""){
			BIONE.tip("没有查询到符合条件的信息。");
			return;
		}
		var form = $('<form/>').attr({
			target: '',
			method: 'post',
			action: '${ctx}/ecif/report/closeaccount/export.xls'
		}).css('display', 'none');
		var input = $('<input/>').attr({
			type: 'hidden',
			name: 'file',
			value: file
		});
		$('body').append(form);
		form.append(input);
		form.submit();
		form.remove();
	}
	
	function file_down() {
		var timestamp=new Date().getTime();
		$.ajax({
			cache : false,
			async : true,
			data : {
				srptMonth : sdateTemp,
				erptMonth : edateTemp,
				createBranchNo : orgTemp
			},
			url: '${ctx}/ecif/report/closeaccount/getExportFile.json?' + timestamp,
			type: 'post',
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("下载中，请稍候...");
			},
			complete : function(XMLHttpRequest) {
				BIONE.loading = false;
				BIONE.hideLoading();				
				BIONE.isSessionAlive(XMLHttpRequest);
			},
			success: doDownload,
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}
	window.document.onkeydown = function(e) {
		e = !e ? window.event : e;
		var key = window.event ? e.keyCode : e.which;
		if (key == 13) {
			var btns = $(".l-btn");
			for(var i=0;i<btns.length;i++){
				var offset = btns[i].innerText.indexOf('搜索');
				if (offset != -1) {
					btns[i].click();
				}
			}
		}
	};
</script>
</head>
<body>
</body>
</html>