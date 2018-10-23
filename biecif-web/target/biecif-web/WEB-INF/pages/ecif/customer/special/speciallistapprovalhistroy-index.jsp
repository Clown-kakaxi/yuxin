<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [], test = [];
	var testId;
	var custNoTemp = identNoTemp = lastUpdateSysTemp = specialListKindTemp = sdateTemp = edateTemp = "";
	$(init);

	function init() {
		url = "${ctx}/ecif/customer/speciallistapprovalhistroy/list.json";
		initGrid();
		searchForm();
		initButtons();
		addSearchButtons("#search", grid, "#searchbtn");
	}
	function checkTime(sDate){
		if(sDate == null || sDate.length == 0){
			//BIONE.tip('统计日期必填！');
			return true;
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
					
					var flag = true;
					if($('#custNo').val() != ""){
						if($('#custNo').val().length > 32){
							flag = false;
							alert("客户编号不能大于32位");
						}
					}
					if($('#identNo').val() != ""){
						if($('#identNo').val().length > 40){
							flag = false;
							alert("证件号码不能大于40位");
						}
					}
					if(flag){
						flag = checkTime($('#srptMonth').val()) && checkTime($('#erptMonth').val()) && greaterThan();
					}
					if(flag){
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
						custNoTemp =  $('#custNo').val();
						identNoTemp = $('#identNo').val();
						lastUpdateSysTemp = $('#approvalStat').val();
						specialListKindTemp = $('#specialListKind').val();
						sdateTemp = $('#srptMonth').val();
						edateTemp = $('#erptMonth').val();
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

	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
			fields : [{
				display : "客户编号",
				name : "custNo",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "c.cust_No"
				}
			}, {
				display : "证件号码",
				name : "identNo",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "s.ident_No"
				}
			}, {
				display : "黑名单类别",
				name : "specialListKindBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'specialListKind',
					//data : [{id : '1', text : '信贷黑名单'}, {id : '2', text : '客户黑名单'}]
					url : "${ctx}/ecif/customer/speciallist/getComBoBoxSpecialListKind.json",
					ajaxType : "get"
				},
				attr : {
					op : "=",
					field : "s.SPECIAL_LIST_KIND"
				}
			}, {
				display : "审批状态",
				name : "approvalStatBox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'approvalStat',
					data : [{id : '02', text : '审批通过'}, {id : '03', text : '审批未通过'}]
				},
				attr : {
					op : "=",
					field : "s.APPROVAL_STAT"
				}
			},{
				display : "审批开始时间",
				name : "srptMonth",
				newline : false,
				type : "date",
				cssClass : "field",
				attr : {
					op : "=",
					field : "srptMonth"
				}
			},{
				display : "审批结束时间",
				name : "erptMonth",
				newline : false,
				type : "date",
				cssClass : "field",
				attr : {
					op : "=",
					field : "erptMonth"
				}
			}]
		});
	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					width : '100%',
					columns : [
								{
									display : '证件户名',
									name : 'identCustName',
									align : 'center',
									width : '14%',
									minWidth : '10%'
								},{
									display : '证件类型',
									name : 'identType',
									align : 'center',
									width : '15%',
									minWidth : '15%',
									render : RenderIdentType
								},{
									display : '证件号码',
									name : 'identNo',
									align : 'center',
									width : '13%',
									minWidth : '10%'
								},{
									display : '客户编号',
									name : 'custNo',
									align : 'left',
									width : '13%',
									minWidth : '10%'
								},{
									display : '黑名单类别',
									name : 'specialListKind',
									align : 'center',
									width : '13%',
									minWidth : '10%',
									render : RenderSpecialListKind
								},{
									display : '列入原因',
									name : 'enterReason',
									align : 'center',
									width : '13%',
									minWidth : '10%'
								},{
									display : '状态标志',
									name : 'statFlag',
									align : 'center',
									width : '13%',
									minWidth : '10%',
									render : RenderValidType
								},{
									display : '起始日期',
									name : 'startDate',
									align : 'center',
									width : '13%',
									minWidth : '10%',
									type : "date",
									options :{
										format : "yyyy-MM-dd"
									}
								},{
									display : '结束日期',
									name : 'endDate',
									align : 'center',
									width : '13%',
									minWidth : '10%',
									type : "date",
									options :{
										format : "yyyy-MM-dd"
									}
								},{
									display : '提交人',
									name : 'operator',
									align : 'center',
									width : '13%',
									minWidth : '10%'
								},{
									display : '提交时间',
									name : 'operTime',
									align : 'center',
									width : '13%',
									minWidth : '10%',
									type : "date",
									format : 'yyyy-MM-dd hh:mm:ss'
								},{
									display : '操作状态',
									name : 'operStat',
									align : 'center',
									width : '13%',
									minWidth : '10%',
									render : RenderDisStatus
								},{
									display : '审批人',
									name : 'approvalOperator',
									align : 'center',
									width : '13%',
									minWidth : '10%'
								},{
									display : '审批时间',
									name : 'approvalTime',
									align : 'center',
									width : '13%',
									minWidth : '10%',
									type : "date",
									format : 'yyyy-MM-dd hh:mm:ss'
								},{
									display : '审批状态',
									name : 'approvalStat',
									align : 'center',
									width : '13%',
									minWidth : '10%',
									render : RenderApprovalStatus
								},{
									display : '审批意见',
									name : 'approvalNote',
									align : 'center',
									width : '13%',
									minWidth : '10%'
								}],
					checkbox : false,
					//delayLoad :true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortName : 'testId', //第一次默认排序的字段
					sortOrder : 'asc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [
		/*动态维护功能按钮*/
		{
			text : '下载审批记录信息',
			click : file_down,
			icon : 'export',
			operNo : 'file_down'
		}];
		BIONE.loadToolbar(grid, btns, function() { });
	}
	
	function RenderValidType(rowdata){
		return BIONE.paramTransformer(rowdata.statFlag, 
				'${ctx}/ecif/customer/speciallist/getCodeMapValidType.json', '');
	}
	
	function RenderIdentType(rowdata){
		return BIONE.paramTransformer(rowdata.identType, 
				'${ctx}/ecif/customer/speciallist/getCodeMapIdentType.json', '');
	}
	
	function RenderDisStatus(rowdata){
		return BIONE.paramTransformer(rowdata.operStat, 
				'${ctx}/ecif/customer/speciallist/getCodeMapDisStatus.json', '');
	}
	
	function RenderApprovalStatus(rowdata){
		return BIONE.paramTransformer(rowdata.approvalStat, 
				'${ctx}/ecif/customer/speciallist/getCodeMapApprovalStatus.json', '');
	}
	
	function RenderSpecialListKind(rowdata){
		return BIONE.paramTransformer(rowdata.specialListKind, 
				'${ctx}/ecif/customer/speciallist/getCodeMapSpecialListKind.json', '');
	}
	
	function test_add_dynamic(){
		BIONE.commonOpenLargeDialog('添加信息', 'testManage',
				'${ctx}/ecif/customer/speciallist/new');
	}
	
	function doDownload(file) {
		if(file==null||file==""){
			BIONE.tip("下载失败。");
			return;
		}
		var form = $('<form/>').attr({
			target: '',
			method: 'post',
			action: '${ctx}/ecif/customer/speciallistapprovalhistroy/export.xls'
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
		var rows = grid.data.Rows;
		if(rows.length <= 0) {
			BIONE.tip("表格中没有数据！");
			return;
		}
		var timestamp=new Date().getTime();
		$.ajax({
			url: '${ctx}/ecif/customer/speciallistapprovalhistroy/getReportFile?'+timestamp,
			type: 'get',
			data: {
				repo: "3",
				//others: others
				custNo: custNoTemp,
				identNo: identNoTemp,
				lastUpdateSys: lastUpdateSysTemp,
				specialListKind: specialListKindTemp,
				srptMonth : sdateTemp,
				erptMonth : edateTemp
			},
			//success: doDownload
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
	/* window.document.onkeydown = function(e) {
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
	}; */
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