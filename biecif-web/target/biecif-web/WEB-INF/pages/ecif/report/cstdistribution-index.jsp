<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url;
	var dateTemp = orgTemp = "";
	$(init);
	function init() {
		url = "${ctx}/ecif/report/distribution/list.json";
		initGrid();
		searchForm();
		initButtons();
		addSearchButtons("#search", grid, "#searchbtn");
	};
	function checkTime(){
		var sDate = $('#rptMonth').val();
		if(sDate == null || sDate.length == 0){
			BIONE.tip('统计日期必填！');
			return false;
		}
		if(sDate.length != 6 ){
			BIONE.tip('统计日期格式为yyyyMM！');
			return false;
		}
		if (!/^[0-9]{4}[0-1][0-9]$/.test(sDate)) {
			BIONE.tip('统计日期格式为yyyyMM！');
			return false;
		}
		var year, month;
		year = sDate.substring(0, 4);
		month = sDate.substring(4, 6);
		if (parseInt(year) < 1900 || parseInt(year) > 9999){
			BIONE.tip('请输入有效的统计年份 [1900]<=[年]<=[9999]！');
			return false;
		}
		if (month < 1 || month > 12){
			BIONE.tip('统计月份不合法！');
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
					
					if(checkTime()){
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
						dateTemp = $('#rptMonth').val();
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
				display : "<font color='red'>*</font>统计日期[yyyyMM]",
				name : "rptMonth",
				newline : true,
				type : "text",
				cssClass : "field",
				width : 150,
			 	labelWidth : 150,
				attr : {
					op : "=",
					field : "rptMonth"
				}
			}, {
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
				width : '200',
				minWidth : '150'
			},  {
				display : '开户机构名',
				name : 'brcname',
				align : 'center',
				width : '250',
				minWidth : '200'
			}, {
				display : 'AUM等级划分',
				name : 'rptsign1',
				align : 'center',
				width : '200',
				minWidth : '150'
			}, {
				display : '客户量',
				name : 'custsum',
				align : 'center',
				width : '200',
				minWidth : '150'
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
			action: '${ctx}/ecif/report/distribution/export.xls'
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
				rptMonth : dateTemp,
				createBranchNo : orgTemp
			},
			url: '${ctx}/ecif/report/distribution/getExportFile.json?' + timestamp,
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
				if(btns[i].innerText=='搜索'){
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