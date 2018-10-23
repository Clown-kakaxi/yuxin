<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [], test = [];
	var testId;
	var custNoTemp = identNoTemp = lastUpdateSysTemp = "";
	$(init);

	function init() {
		url = "${ctx}/ecif/customer/suspectgroup/list.json";
		initGrid();
		searchForm();
		initButtons();
		addSearchButtons("#search", grid, "#searchbtn");
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
						identNoTemp = $('#custType').val();
						//lastUpdateSysTemp = $('#suspectComfirmFlag').val();
						lastUpdateSysTemp = "";
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
				display : "客户类型",			
				name : "custTypeBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'custType',
					//data : [{id : '2', text : '对私'}, {id : '1', text : '对公'}]
					url : "${ctx}/ecif/customer/suspectgroup/getComBoBoxCustType.json",
					ajaxType : "get"
				},
				attr : {
					op : "=",
					field : "l.cust_Type"
				}
			}, {
				display : "客户编号",
				name : "custNo",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "l.cust_No"
				}
			}/* , {
				display : "确认标志",
				name : "suspectComfirmFlagBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'suspectComfirmFlag',
					//data : [{id : '0', text : '未确认'}, {id : '1', text : '已确认'}]
					url : "${ctx}/ecif/customer/suspectgroup/getComBoBoxComfirmType.json",
					ajaxType : "get"
				},
				attr : {
					op : "=",
					field : "sl.suspectComfirmFlag"
				}
			}*/]
		});

	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					width : '100%',
					columns : [
							{
								display : '分组标识',
								name : 'suspectGroupId',
								align : 'left',
								width : '13%',
								minWidth : '10%'
							},{
								display : '客户编号',
								name : 'custNo',
								align : 'center',
								width : '14%',
								minWidth : '10%'
							},{
								display : '客户名称',
								name : 'custName',
								align : 'center',
								width : '14%',
								minWidth : '10%'
							},{
								display : '客户类型',
								name : 'custType',
								align : 'center',
								width : '14%',
								minWidth : '10%',
								render : RenderCustType
							},{
								display : '机构编号',
								name : 'createBranchNo',
								align : 'center',
								width : '13%',
								minWidth : '10%'
							},/* {
								display : '疑似客户分组描述',
								name : 'suspectGroupDesc',
								align : 'center',
								width : '13%',
								minWidth : '10%'
							}, {
								display : '疑似信息数据日期',
								name : 'suspectDataDate',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								type : "date",
								options :{
									format : "yyyy-MM-dd"
								}
							},*/{
								display : '疑似信息生成日期',
								name : 'suspectSearchDate',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								type : "date",
								options :{
									format : "yyyy-MM-dd"
								}
							},/*{
								display : '导出处理标志',
								name : 'suspectExportFlag',
								align : 'center',
								width : '13%',
								minWidth : '10%'
							},{
								display : '疑似确认标志',
								name : 'suspectComfirmFlag',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								render : RenderComfirmType
							},{
								display : '疑似确认时间',
								name : 'suspectComfirmResult',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								type : "date",
								options :{
									format : "yyyy-MM-dd"
								}
							},{
								display : '疑似确认人',
								name : 'suspectComfirmOperator',
								align : 'center',
								width : '13%',
								minWidth : '10%'
							},*/{
								display : '合并处理标志',
								name : 'mergeDealFlag',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								render : RenderMergeType
							},{
								display : '合并处理日期',
								name : 'mergeDealDate',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								type : "date",
								format : 'yyyy-MM-dd hh:mm:ss'
							},{
								display : '列入原因',
								name : 'enterReason',
								align : 'center',
								width : '20%',
								minWidth : '15%',
								render : renderData
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
					sortName : 'suspect_Group_Id', //第一次默认排序的字段
					sortOrder : 'asc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [
		/*动态维护功能按钮*/
		{
			text : '导出疑似客户名单',
			click : file_down,
			icon : 'export',
			operNo : 'file_down'
		}/*,{
			text : '导入疑似客户确认名单',
			click : file_add,
			icon : 'import',
			operNo : 'file_add'
		} */];
		BIONE.loadToolbar(grid, btns, function() { });
	}
	
	function renderData(data) {
		//data.enterReason
		var value = "<a href='#' onclick='alertWin(\""+data.enterReason+"\");'><font color='blue'>" + data.enterReason + "</font></a>";
		return value;
	}
	
	function alertWin(data){
		var message = "规则1: 证件类型相同+证件号码相同+证件户名相同，客户号不同\n" +
			"规则2: 证件号码相同+证件户名相同，证件类型不同+客户号不同\n" +
			"规则3: 证件类型相同+证件号码相同，证件户名不同+客户号不同\n" +
			"规则4: 证件户名相同+联系手机号码相同，客户号不同\n" +
			"规则5: 证件户名相同+联系地址相同,客户号不同\n" +
			"规则6: 证件号码相同+联系手机号码相同,客户号不同\n" +
			"规则7: 证件号码相同+联系地址相同，客户号不同\n";
		alert(message);
		/* $.ligerDialog.alert(message, "说明", function() {
			return false;
		}); */
	}
	
	function RenderComfirmType(rowdata){
		return BIONE.paramTransformer(rowdata.suspectComfirmFlag, 
				'${ctx}/ecif/customer/suspectgroup/getCodeMapComfirmType.json', '');
	}
	
	function RenderCustType(rowdata){
		return BIONE.paramTransformer(rowdata.custType, 
				'${ctx}/ecif/customer/suspectgroup/getCodeMapCustType.json', '');
	}
	
	function RenderMergeType(rowdata){
		return BIONE.paramTransformer(rowdata.mergeDealFlag, 
				'${ctx}/ecif/customer/suspectgroup/getCodeMapMergeType.json', '');
	}

	function file_add(item) {
		//window.parent.
		BIONE.commonOpenDialog('上传模板', 'upload',550,335,
				'${ctx}/ecif/customer/suspectgroup/importresult');
	}
	function doDownload(file) {
		if(file==null||file==""){
			BIONE.tip("下载失败。");
			return;
		}
		var form = $('<form/>').attr({
			target: '',
			method: 'post',
			action: '${ctx}/ecif/customer/suspectgroup/export.xls'
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
			url: '${ctx}/ecif/customer/suspectgroup/getReportFile?'+timestamp,
			type: 'get',
			data: {
				repo: "6",
				//others: others
				custNo: custNoTemp,
				identNo: identNoTemp,
				lastUpdateSys: lastUpdateSysTemp
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