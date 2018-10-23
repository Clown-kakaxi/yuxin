<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
<!--//
	var grid, btns, url, ids = [], test = [], idss = [], kind = [], sys = [];
	var testId, custNo;
	var custNoTemp = identNoTemp = lastUpdateSysTemp = "";
	var others = ",,";
	$(init);

	function init() {
		url = "${ctx}/ecif/customer/speciallist/list.json";
		initGrid();
		searchForm();
		//searchOtherForm();
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
					/* debugger;
					if(!$('#search').valid()){
						return;
					} */
					var flag = true;
					if($('#custNo').val() != ""){
						if($('#custNo').val().length > 32){
							flag = false;
							alert("客户编号不能大于32位");
						}
					}else if($('#identNo').val() != ""){
						if($('#identNo').val().length > 40){
							flag = false;
							alert("证件号码不能大于40位");
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
						identNoTemp = $('#identNo').val();
						lastUpdateSysTemp = $('#specialListKind').val();
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
/*
	//搜索表单应用ligerui样式
	function searchOtherForm() {
		$("#searchother").ligerForm({
			fields : [ {
				name : "repo",
				type : "hidden",
				value: "1"
			},{
				name : "other",
				type : "hidden",
				value: other
			}]
		});
	}
	*/
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
				}/* ,
				validate : {
				    required : false,
				    maxlength : 32,
				    number : "请输入合法的数值"
				} */
			}, {
				display : "证件号码",
				name : "identNo",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "s.ident_No"
				}/* ,
				validate : {
				    required : false,
				    maxlength : 32,
				    number : "请输入合法的数值"
				} */
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
			}]
		});
		//jQuery.metadata.setType("attr", "validate");
		//BIONE.validate($("#search"));
	}

	function initGrid() {

		grid = $("#maingrid")
			.ligerGrid({
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
								display : '来源系统',
								name : 'approvalFlag',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								render : RenderFlag
							}/*,{
								display : '来源系统',
								name : 'lastUpdateSys',
								align : 'center',
								width : '13%',
								minWidth : '10%'
							},{
								display : '更新人',
								name : 'lastUpdateUser',
								align : 'center',
								width : '13%',
								minWidth : '10%'
							},{
								display : '更新时间',
								name : 'lastUpdateTm',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								type : "date",
								options :{
									format : "yyyy-MM-dd"
								}
							}*/],
					checkbox : true,
					delayLoad :true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'post',
					url : url,
					sortName : 'custId', //第一次默认排序的字段
					sortOrder : 'asc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [
		/*动态维护功能按钮*/
		{
			text : '增加',
			click : test_add_dynamic,
			icon : 'add',
			operNo : 'test_add'
		},{
			text : '修改',
			click : test_modify_dynamic,
			icon : 'modify',
			operNo : 'test_modify'
		},{
			text : '删除',
			click : test_delete_dynamic,
			icon : 'delete',
			operNo : 'test_delete'
		}, {
			text : '下载导入信息模板',
			click : doDownload,
			icon : 'export',
			operNo : 'file_down'
		}, {
			text : '导入黑名单信息',
			click : file_load,
			icon : 'import',
			operNo : 'file_load'
		},{
			text : '下载黑名单信息',
			click : file_down,
			icon : 'export',
			operNo : 'file_down'
		}];
		BIONE.loadToolbar(grid, btns, function() { });
	}
	
	function RenderFlag(rowdata){
		if(rowdata.approvalFlag == '0'){
			return "ECIF";
		}else if(rowdata.approvalFlag == '1'){
			return "ECIF待审批";
		}else{
			return "";
		}
	}
	
	function RenderValidType(rowdata){
		return BIONE.paramTransformer(rowdata.statFlag, 
				'${ctx}/ecif/customer/speciallist/getCodeMapValidType.json', '');
	}
	
	function RenderIdentType(rowdata){
		return BIONE.paramTransformer(rowdata.identType, 
				'${ctx}/ecif/customer/speciallist/getCodeMapIdentType.json', '');
	}
	
	function RenderSpecialListKind(rowdata){
		return BIONE.paramTransformer(rowdata.specialListKind, 
				'${ctx}/ecif/customer/speciallist/getCodeMapSpecialListKind.json', '');
	}
	
	function test_add_dynamic(){
		BIONE.commonOpenLargeDialog('添加信息', 'testManage',
				'${ctx}/ecif/customer/speciallist/new');
	}
	
	function test_modify_dynamic(){
		achieveIds();
		if (ids.length == 1) {
			/* if(test != "") {
				$.ligerDialog.error("信息: [ " + test.join(", ").toString() + " ] 不可修改!", "错误", function() {
					return false;
				});
			} else { */
				
			//if(sys[0] == "0"){
			if(sys[0] == "1"){
				alert('选择的记录已提交待审批');
			}else{
				/* if(kind[0] == "1"){
					alert('选择记录中的黑名单类别不可维护');
				}else{
					testId = idss[0];
					BIONE.commonOpenLargeDialog('修改信息', 'testManage',
						'${ctx}/ecif/customer/speciallist/'+testId+'/edit');
				} */
				testId = idss[0];
				BIONE.commonOpenLargeDialog('修改信息', 'testManage',
						'${ctx}/ecif/customer/speciallist/'+testId+'/edit');
			}/* else if(sys[0] == "1"){
				alert('选择的记录已提交待审批');
			}else{
				alert('选择的记录不是ecif系统的');
			} */
			//}
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}

	function test_delete_dynamic(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					/* if(test != "") {
						$.ligerDialog.error("信息: [ " + test.join(", ").toString() + " ] 不可删除!", "错误", function() {
							return false;
						});
					} else { */
						
					$.each(sys, function() {
						/* if(this != "0" && this != "1"){
							flag = true;
							alert("选择的记录中,有不是ecif系统的");
							return false;
						} */
						if(this == "1"){
							flag = true;
							alert("选择的记录中,有已提交待审批");
							return false;
						}
					});
					
					if(flag == true){
						flag = false;
					}else{
						//
						/* $.each(kind, function() { 
							if(this == "1"){
								flag = true;
								return false;
							}
						});
						if(flag == true){
							alert("选择的黑名单类别不允许删除,请重新选择");
							flag = false;
						}else{
							$.ajax({
								async : false,
								type : "DELETE",
								url : "${ctx}/ecif/customer/speciallist/"+ids.join(","),
								success : function() {
									flag = true;
								}
							});
						} */
						$.ajax({
							async : false,
							type : "DELETE",
							url : "${ctx}/ecif/customer/speciallist/"+ids.join(","),
							success : function() {
								flag = true;
							}
						});
					}
					//}
					if (flag == true) {
						BIONE.tip('删除提交待审批');
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
	function file_load(item) {
		//window.parent.BIONE.commonOpenDialog('上传模板','upload',550,335,
		//'${ctx}/bicustrisk/custInfoadd/custadd/showUploadControl?custType='+custType);
		// 导入
		BIONE.commonOpenDialog('上传文件', 'upload', 562, 334,
					'${ctx}/ecif/customer/speciallist/importresult');
	}
	function doDownload(file) {
		if(file==null||file==""){
			BIONE.tip("下载失败。");
			return;
		}
		var form = $('<form/>').attr({
			target: '',
			method: 'post',
			action: '${ctx}/ecif/customer/speciallist/export.xls'
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
			url: '${ctx}/ecif/customer/speciallist/getReportFile?'+timestamp,
			type: 'get',
			data: {
				repo: "1",
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
	function achieveIds() {
		ids = [];
		test = [];
		idss = [];
		kind = [];
		sys = [];
		var rows = grid.getSelectedRows();
		$(rows).each(function() {
			ids.push(this.specialListId);
			kind.push(this.otherKind);
			sys.push(this.approvalFlag);
			var cn = this.custNo;
			if(cn == ""){
				cn = 0;
			}
			idss.push(this.specialListId + "-" + cn);
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
//-->
</script>
</head>
<body>
</body>
</html>