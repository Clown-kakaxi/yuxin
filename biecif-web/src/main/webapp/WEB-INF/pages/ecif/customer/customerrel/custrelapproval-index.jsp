<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [], test = [];
	var srcIdTemp = destIdTemp = custRelTypeTemp = lastUpdateSysTemp = '';
	var custrelApprovalId;
	
	$(init);

	function init() {
		url = "${ctx}/ecif/custrelapproval/list.json";
		initGrid();
		searchForm();
		initButtons();
		addSearchButtons("#search", grid, "#searchbtn");
	}
	
	function greaterThan(){
		var value1 = $('#operTimeEndBegin').val();
		var value2 = $('#operTimeEnd').val();
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
					if($('#srcId').val() != ""){
						if($('#srcId').val().length > 32){
							flag = false;
							alert("源客户号不能大于32位");
						}
					}
					if($('#destId').val() != ""){
						if($('#destId').val().length > 32){
							flag = false;
							alert("目标客户号不能大于32位");
						}
					}
					//if($('#custRelType').val() != "" && flag != false){
					if(flag != false){
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
							srcIdTemp = $('#srcId').val(),
						    destIdTemp = $('#destId').val();
						    custRelTypeTemp = $('#custRelType').val();
	//	 				    lastUpdateSysTemp = $('#lastUpdateSys').val();
						}
					}
					}/* else{
						BIONE.tip("请输入关系类型");
					} */
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
			fields : [ {
				display : "源客户号",
				name : "srcId",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
// 					field : "cust.SRC_CUST_ID"
					field : "customer1.CUST_NO"
				}
			},{
				display : "目标客户号",
				name : "destId",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
// 					field : "custrel.DEST_CUST_ID"
					field : "customer2.CUST_NO"
				}
			},{
				display : "提交审批开始日期",
				name : "operTimeEndBegin",
				newline : false,
				type : "date",
				cssClass : "field",
				width : 150,
			 	labelWidth : 150,
				attr : {
					op : ">=",
					field : "custrelApproval.OPER_TIME"
				}
			},{
				display : "关系种类",
				name : "relationClass",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					data : [ 
					{
						id : '1',
						text : '机构类客户与机构类客户'
					}, {
						id : '2',
						text : '机构类客户与个人类客户'
					}, {
						id : '3',
						text : '个人类客户与个人类客户'
					} ],
					onSelected : function(val) {
						    $("#custRel").ligerGetComboBoxManager().setData();
		        			adapid = val;
		        			var url='${ctx}/ecif/customerrelationlook/getModeVer.json?adapterId='+adapid;
		        			$.get(url, {}, function(ret, status) {
		        				$("#custRel").ligerGetComboBoxManager().setData(ret);
		        			});
		        		
					}
				},
				attr : {
					op : "="//,
				}
			},{
				display : "关系类型",
				name : "custRel",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'custRelType',
					onSelected : function(val) {
					}
				},
				attr : {
					op : "=",
					field : "custrelApproval.CUST_REL_TYPE"
				}
			},{
				display : "提交审批结束日期",
				name : "operTimeEnd",
				newline : false,
				type : "date",
				cssClass : "field",
				width : 150,
			 	labelWidth : 150,
				attr : {
					op : "<=",
					field : "custrelApproval.OPER_TIME"
				}
			}]
		});
	}	
	
	function initGrid() {

		grid = $("#maingrid").ligerGrid({
			width : '100%',
			columns : [ 
			/* {
				display : '源客户标识',
				name : 'srcId',
				align : 'left',
				width : '10%',
				minWidth : '8%'
			}, */{
				display : '源客户名称',
				name : 'srcName',
				align : 'center',
				width : '15%',
				minWidth : '10%'
			},{
				display : '源客户号',
				name : 'srcCustNo',
				align : 'left',
				width : '15%',
				minWidth : '10%'/* ,
				render : function(rowdata){
					var custNo = rowdata.srcCustNo;
					var custName = rowdata.srcName;
					return "<a href='#' onClick='custNoLink("+custNo+",\""+custName+"\");'>"+rowdata.srcCustNo+"</a>";
				} */
			},/* {
				display : '目标客户标识',
				name : 'destId',
				align : 'center',
				width : '10%',
				minWidth : '8%'
			}, */{
				display : '目标客户名称',
				name : 'destName',
				align : 'center',
				width : '15%',
				minWidth : '10%'
			},{
				display : '目标客户号',
				name : 'destCustNo',
				align : 'center',
				width : '15%',
				minWidth : '10%'/* ,
				render : function(rowdata){
					var custNo = rowdata.destCustNo;
					var custName = rowdata.destName;
					return "<a href='#' onClick='custNoLink("+custNo+",\""+custName+"\");'>"+rowdata.destCustNo+"</a>";
				} */
			},{
				display : '关系类型',
				name : 'custRelType',
				align : 'center',
				width : '8%',
				minWidth : '8%',
				render : RenderRelType
			},{
				display : '关系描述',
				name : 'custRelDesc',
				align : 'center',
				width : '8%',
				minWidth : '8%'
			},/* {
				display : '关系状态',
				name : 'custRelStat',
				align : 'center',
				width : '8%',
				minWidth : '8%',
				render : RenderRelStat
			}, */{
				display : '关系开始时间',
				name : 'custRelStart',
				align : 'center',
				width : '8%',
				type : 'date',
				minWidth : '8%'
			},{
				display : '关系结束时间',
				name : 'custRelEnd',
				align : 'center',
				width : '8%',
				type : 'date',
				minWidth : '8%'
			},{
				display : '提交人',
				name : 'operator',
				align : 'center',
				width : '8%',
				minWidth : '8%'
			},{
				display : '提交时间',
				name : 'operTime',
				align : 'center',
				width : '8%',
				type : 'date',
				minWidth : '8%',
				format : 'yyyy-MM-dd hh:mm:ss'
			},{
				display : '操作状态',
				name : 'operStat',
				align : 'center',
				width : '8%',
				minWidth : '8%',
				render : RenderOperStat
			}],
			checkbox : true,
			//delayLoad :true,
			usePager : true,
			isScroll : true,
			rownumbers : true,
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : url,
			sortName : 'OPER_TIME', //第一次默认排序的字段
			sortOrder : 'desc',
			toolbar : {}
		});
	}

	function initButtons() {
		btns = [
		/*动态维护功能按钮*/
		{
			text : '审核',
			click : test_modify_dynamic,
			icon : 'modify',
			operNo : 'test_modify'
		},{
			text : '下载客户关系待审批信息',
			click : file_down,
			icon : 'export',
			operNo : 'file_down'
		},{
			text : '导入客户关系审批信息',
			click : file_load,
			icon : 'import',
			operNo : 'file_load'
		}];
		BIONE.loadToolbar(grid, btns, function() {
		});
	}
	
	function file_load(item) {
		// 导入
		BIONE.commonOpenDialog('导入客户关系审批信息', 'upLoad', 562, 334,
					'${ctx}/ecif/custrelapproval/importresult');
	}
	
	function file_down() { 
		var rule = BIONE.bulidFilterGroup($("#search"));
		var timestamp=new Date().getTime();
		var ruleJson = JSON2.stringify(rule);
		$.ajax({
			url: '${ctx}/ecif/custrelapproval/getReportFile.json?' + timestamp,
			type: 'post',
			data: {
				reportNo: "2",
				rule : ruleJson
			},
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
	
	function doDownload(file) {
		if(file==null||file==""){
			BIONE.tip("下载失败。");
			return;
		}
		var form = $('<form/>').attr({
			target: '',
			method: 'post',
			action: '${ctx}/ecif/custrelapproval/export.xls?'+new Date().getTime()
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
	
	function test_modify_dynamic(){
		achieveIds();
		if (ids.length > 0) {
			
			for(var i = 0; i < ids.length ; i++){
// 				alert(i+'--'+ids[i]);
			}
			//传id
			BIONE.commonOpenLargeDialog('添加审批信息', 'testManage','${ctx}/ecif/custrelapproval/'+ids+'/new');			
		}else {
			BIONE.tip('请选择记录');
		}
	}
	
	function RenderRelStat(rowdata){		
		return BIONE.paramTransformer(rowdata.custRelStat, 
				'${ctx}/ecif/customerrelationlook/getRenderRelStat.json', '');
	}
	
	function achieveIds() {
		ids = [];
		test = [];
		var rows = grid.getSelectedRows();
		$(rows).each(function() {
// 			alert(this.custrelApprovalId);
			ids.push(this.custrelApprovalId);
		});
	}
	
	function RenderRelType(rowdata){
		/* return BIONE.paramTransformer(rowdata.custRelType, 
				'${ctx}/ecif/customerrelationlook/getCodeComBoBox.json', ''); */
		return BIONE.paramTransformer(rowdata.custRelType, 
				'${ctx}/ecif/customerrelationlook/getCodeMapRelType.json', '');
	}
	
	function RenderOperStat(rowdata){
		if(rowdata.operStat == '01'){
			return '新增';
		}else if(rowdata.operStat == '02'){
			return '修改';
		}else if(rowdata.operStat == '03'){
			return '删除';
		}else{
			return rowdata.operStat;
		}
	}
	
	function custNoLink(custNo, custName){
		var custId = new Array();
		var url='${ctx}/ecif/customerrelationlook/getCustomerId.json?custNo='+custNo;
		$.get(url, {}, function(ret, status) {
			custId = ret.split(",");
			if(custId[1] == "1"){
				var url='${ctx}/ecif/perinfo/perdetailed?custId='+custId[0]+'&name='+custName;
				window.location.href=url;
			}else if(custId[1] == "2"){
				var url='${ctx}/ecif/orginfo/orgdetailed?custId='+custId[0]+'&name='+custName;
				window.location.href=url;
			}else{
				alert("不是个人与机构客户");		
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