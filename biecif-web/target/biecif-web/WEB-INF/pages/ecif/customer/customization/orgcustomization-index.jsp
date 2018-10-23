<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript" src="jquery-1.4.2.js"></script>
<script type="text/javascript" src="jquery.ztree.core-3.4.js"></script>
<script type="text/javascript" src="jquery.ztree.excheck-3.4.js"></script>
<script type="text/javascript" src="jquery.ztree.exedit-3.4.js"></script>
<meta name="decorator" content="/template/template20.jsp">
<script type="text/javascript">
	var mm = 0;
	var gridinitHeight;
	var treeObj;
	var deptTreeObj;
	var grid;
	var btns;
	var gridJson;
	var tableColumn;
	$(function() {
		//var currentNode = null;

		var setting = {
			data:{
				key:{
					checked:"ischecked",
					name:'text'
				},
				simpleData:{
					enable:true,
					idKey: "id",
					pIdKey: "upId"
				}
			},
			check:{
				autoCheckTrigger:true,
				chkboxType:{"Y":"ps","N":"ps"},
				chkStyle:"checkbox",
				enable:true,
				nocheckInherit:true
			}
		};
		deptTreeObj = $.fn.zTree.init($("#tree"), setting);

		$.ajax({
			cache : false,
			async : false,
			url : "${ctx}/ecif/orgctzt/getColumns.json",
			type : "post",
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("正在加载数据中...");
			},
			complete : function(XMLHttpRequest) {
				BIONE.loading = false;
				BIONE.hideLoading();				
				BIONE.isSessionAlive(XMLHttpRequest);
			},
			success : function(result) {
				if(result) {
					for(var i in result) {
						if(result[i].params.open == "true") {
							result[i].open = true;
						} else {
							result[i].open = false;
						}
					}
				}
				deptTreeObj.addNodes(null, result, false);
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
		setupUpTree();
		//初始化右侧树按钮
		$("#treeToolbar").ligerTreeToolBar({
		    items : [ {
				icon : 'save',
				text : '保存',
				click : setupTree
		    }, {
				line : true
		    }, {		
	    		icon : 'refresh2',
				text : '重置',
				click : tree_refresh
		    }],
		    treemenu : false
		});
		$("#search").ligerForm({
		 	labelWidth : 110,
			inputWidth : 110,
			space : 10, 
			fields : [ {
				display : "客户号",
				name : "custNo",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "custNo"
				}
			}, {
				display : "客户名称(支持中英文)",
				name : "name",
				align : 'right',
				newline : false,
				type : "text",
				cssClass : "field",
			 	labelWidth : 200,
				width : 250,
				attr : {
					op : "like",
					field : "name"
				}
			}, {
				display : "国家代码",
				name : "nationCode",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "id",
					url : "${ctx}/ecif/orgctzt/getComBoBox.json?codeType="+"XD000002",
					ajaxType : "post"
					
				},
				attr : {
					op : "=",
					field : "nationCode"
				}
			}, {
				display : "组织机构类型",
				name : "orgType",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "id",
					url : "${ctx}/ecif/orgctzt/getComBoBox.json?codeType="+"XD010014",
					ajaxType : "post"
				},
				attr : {
					op : "=",
					field : "orgType"
				}
			}, {
				display : "行业分类",
				name : "industry",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "id",
					url : "${ctx}/ecif/orgctzt/getComBoBox.json?codeType="+"XD000020",
					ajaxType : "post"
				},
				attr : {
					op : "=",
					field : "industry"
				}
			} , {
				display : "企业性质",
				name : "entProperty",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'id',
					url : "${ctx}/ecif/orgctzt/getComBoBox.json?codeType="+"XD010032",
					ajaxType : "post"
				},
				attr : {
					op : "=",
					field : "entProperty"
				}
			} , {
				display : "企业规模",
				name : "entScale",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "id",
					url : "${ctx}/ecif/orgctzt/getComBoBox.json?codeType="+"XD010033",
					ajaxType : "post"
				},
				attr : {
					op : "=",
					field : "entScale"
				}
			} , {
				display : "经济类型",
				name : "economicType",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "id",
					url : "${ctx}/ecif/orgctzt/getComBoBox.json?codeType="+"XD010036",
					ajaxType : "post"
				},
				attr : {
					op : "=",
					field : "economicType"
				}
			} , {
				display : "客户创建柜员编号",
				name : "createTellerNo",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "createTellerNo"
				}
			}, {
				display : "客户创建机构编码",
				name : "createBranchNo",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "createBranchNo"
				}
			}, {
				display : "客户归属机构编码",
				name : "brccode1",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "brccode1"
				}
			}, {
				display : "客户经理编号",
				name : "empcode1",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "empcode1"
				}
			} , {
				display : "生命周期状态类型",
				name : "lifecycleStatType",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "id",
					url : "${ctx}/ecif/orgctzt/getComBoBox.json?codeType="+"XD010151",
					ajaxType : "post"
				},
				attr : {
					op : "=",
					field : "lifecycleStatType"
				}
			}, {
				display : "证件类型",
				name : "identtype",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "id",
					url : "${ctx}/ecif/orgctzt/getComBoBox.json?codeType="+"XD010061",
					ajaxType : "post"
				},
				attr : {
					op : "=",
					field : "identtype"
				}
			}, {
				display : "联系信息",
				name : "contmethinfo",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "contmethinfo"
				}
			}, {
				display : "地址信息",
				name : "addr",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "addr"
				}
			} ]
		});
		$.ajax({
			cache : false,
			async : false,
			type : 'post',
			url : "${ctx}/ecif/orgctzt/getTableColumn.json",
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("正在加载数据中...");
			},
			complete : function(XMLHttpRequest) {
				BIONE.loading = false;
				BIONE.hideLoading();				
				BIONE.isSessionAlive(XMLHttpRequest);
			},
			success : function(result) {
				tableColumn = result;
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
		var columns = tableColumn.columns;
		var tables = tableColumn.tables;
		var gridJsonStr = "";
		var cstidStr = "";
		var columnsJsonStr = "columns : [";
		if(columns != null){
			var tablesStr = "";
			for(var j = 0; j < tables.length; j ++){
				var tableStr = "{display: '"+tables[j].tbCnnm+"', columns: [";
				var oneTableColumnStr = "";
				for(var i = 0; i < columns.length; i ++){
					if(tables[j].tbSerialNo == columns[i].tbSerialNo){
						var tbNmAcronym = columns[i].tbNmAcronym; // 获取列所属表缩写
						var cumName = columns[i].cumName.replace(/[_]/g,""); // 获取列名并去下划线
						var columnName = tbNmAcronym+cumName; // 拼接列名为属性名
						columnName = columnName.toLowerCase(); // 转换为小写
						var cumCnnm = columns[i].cumCnnm; // 获取列中文名
						var dataType = columns[i].dataType; // 获取字段数据类型
						var dataLen = columns[i].dataLen;
						var oneCumJsonStr = "";
						if(columnName == "cstcustid"){
							cstidStr = "{display: '"+cumCnnm+"',name : '"+columnName+"',hide:1},";
						}else{
							var minWidth = "12%";
							var width = "20%";
							var len = 3*cumCnnm.length;
							if(len > 20){
								width = len+"%";
							}
							var align = "center";
							if(dataType == "DECIMAL"){
								align = "right";
							}
							if(dataType == "VARCHAR"){
								align = "left";
							}
							oneCumJsonStr = "{display: '"+cumCnnm+"',name: '"+columnName+"',align: '"+align+"',width: '"+width+"',minWidth: '"+minWidth+"'}";
							oneTableColumnStr = oneTableColumnStr + oneCumJsonStr + ",";
						}
					}
				}
				if(oneTableColumnStr.length != 0){
					oneTableColumnStr = oneTableColumnStr.substring(0, oneTableColumnStr.length-1);
				}
				if(j == tables.length -1){
					tableStr = tableStr + oneTableColumnStr +"]}";
				} else {
					tableStr = tableStr + oneTableColumnStr +"]},";
				}
				tablesStr = tablesStr + tableStr;
			}
			columnsJsonStr = columnsJsonStr + cstidStr + tablesStr + "],";
			gridJsonStr = "{width : '100%'," + columnsJsonStr + "checkbox : false, delayLoad :false,usePage : true, isScroll : false, rownumbers : true, alternatingRow : true, colDraggable : true, dataAction : 'server', method : 'post', url : '${ctx}/ecif/orgctzt/list.json', sortName : 'CST.CUST_NO', sortOrder : 'asc', toolbar : {} }";
			gridJson = eval("("+gridJsonStr+")"); 
			grid = $("#maingrid").ligerGrid(gridJson);
			addSearchButtons("#search", grid, "#searchbtn");
			gridinitHeight = $("#mainsearch").height();
		}

		btns = [
		/*动态维护功能按钮*/
		{
			text : '下载',
			click : file_down,
			icon : 'export',
			operNo : 'cst_export'
		}, {
			text : '缩放',
			click : minmax,
			icon : 'scale',
			operNo : 'cst_minmax'
		}];
		if(grid == null){
			return;
		}
		BIONE.loadToolbar(grid, btns, function() { });
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
					
					var rule = BIONE.bulidFilterGroup(form);
					if (rule.rules ==  null || rule.rules.length == 0) {
						BIONE.tip("请至少输入一个查询条件！");
						return;
					}
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
					if (grid.gridloading.css("display")=="none") {
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
					}
					else {
						BIONE.showError("查询进行中，请勿重复查询！");
					}
					/* @Revision 20130704182000 END */
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
	
	function doDownload(file) {
		if(file==null||file==""){
			//BIONE.tip("下载失败。");
			alert("下载失败。");
			return;
		}
		var form = $('<form/>').attr({
			target: '',
			method: 'post',
			action: '${ctx}/ecif/orgctzt/export.xls'
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
		var rule = BIONE.bulidFilterGroup($("#search"));
		if (rule.rules ==  null || rule.rules.length == 0) {
			BIONE.tip("请至少输入一个查询条件！");
			return;
		}
		var timestamp=new Date().getTime();
		var ruleJson = JSON2.stringify(rule);
		$.ajax({
			cache : false,
			async : true,
			url: '${ctx}/ecif/orgctzt/getExportFile.json?' + timestamp,
			type: 'post',
			data:{
				'rule':ruleJson
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
	
	function minmax(){
		if(mm == 0){
			mm = 1;
			document.getElementById("mainsearch").style.display = "none";
			document.getElementById("right").style.display = "none";
			$("#left").width("100%");
			$("#maingrup").width("100%");
			$("#maingrup").height($("#left").height());
			$("#maingrid").width("100%");
			$("#maingrid").height($(document).height());	
		    var rightHeight = $("#right").height();
			grid.setHeight(rightHeight);
			return;
		}
		if(mm == 1){
			mm = 0;
			document.getElementById("mainsearch").style.display = "block";
			document.getElementById("right").style.display = "block";
			document.getElementById("left").style.width = "79%";
			document.getElementById("right").style.width = "20%";
		    var rightHeight = $("#right").height();
			grid.setHeight(rightHeight - gridinitHeight - 9);
			return;
		}
	}
	function tree_refresh(){
		var setting = {
			data:{
				key:{
					checked:"ischecked",
					name:'text'
				},
				simpleData:{
					enable:true,
					idKey: "id",
					pIdKey: "upId"
				}
			},
			check:{
				autoCheckTrigger:true,
				chkboxType:{"Y":"ps","N":"ps"},
				chkStyle:"checkbox",
				enable:true,
				nocheckInherit:true
			}
		};
		deptTreeObj = $.fn.zTree.init($("#tree"), setting);
		$.ajax({
			cache : false,
			async : false,
			url : "${ctx}/ecif/orgctzt/getColumns.json",
			type : "post",
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("正在加载数据中...");
			},
			complete : function(XMLHttpRequest) {
				BIONE.loading = false;
				BIONE.hideLoading();				
				BIONE.isSessionAlive(XMLHttpRequest);
			},
			success : function(result) {
				deptTreeObj.addNodes(null, result, false);
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
		setForm();
		setupUpTree();
		setcolumn();
		$("#maingrid").ligerGrid(gridJson);
	}

	function setForm() {
		var rule = BIONE.bulidFilterGroup($("#search"));
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
		grid.loadData();
	}
	function treeKeyDown(obj){
		if(event.keyCode == 13){
			f_selectNode();
		}	
		obj.focus();
	}
	
    function f_selectNode() {
    	var treeValue = document.getElementById("treeValue").value;
    	var hiddenTree = document.getElementById("hiddenTree").value;
    	var nodes = deptTreeObj.getNodesByParam("text", treeValue, null);
    	if(treeValue != hiddenTree){
    		document.getElementById("hiddenTree").value = treeValue;
       		deptTreeObj.selectNode(nodes[0]);
       		document.getElementById("searchNumber").value = 1;
    	}else{
    		var searchNumber = document.getElementById("searchNumber").value;
    		if(searchNumber < nodes.length){
				deptTreeObj.selectNode(nodes[searchNumber]);
	    		document.getElementById("searchNumber").value = parseInt(document.getElementById("searchNumber").value) + 1;
    		}else{
           		deptTreeObj.selectNode(nodes[0]);
           		document.getElementById("searchNumber").value = 1;
    		}
    	}
    }
    function setupUpTree(){
    	deptTreeObj.expandAll(false);
    	
    	var allNodes = deptTreeObj.getNodes();
		for(var i = 0; i < allNodes.length; i ++){
			var treeNode = allNodes[i];
			if(treeNode.params.open == "true"){
				deptTreeObj.expandNode(treeNode, true);
			}
		}
		
		var nodes = deptTreeObj.getCheckedNodes(true);
		for(var i = 0; i < nodes.length; i ++){
			var treeNode = nodes[i];
			if(treeNode != null && treeNode.data != null && treeNode.data.cumSerialNo != null && treeNode.data.cumSerialNo != "0"){
				deptTreeObj.checkNode(treeNode, true, true);
			}
		}
    }
    
    function setupTree(){
		var nodes = deptTreeObj.getCheckedNodes(true);
		var cumSerialNoStr = "";
		var tbs = 0;
		var cls = 0;
		for(var i = 0; i < nodes.length; i ++){
			var treeNode = nodes[i];
			if(treeNode.params.open == "true"){
				tbs ++;
			}
			if(treeNode.params.open == "false"){
				cls ++;
				var cumSerialNo = treeNode.data.cumSerialNo;
				if(i == treeNode.length-1){
					cumSerialNoStr = cumSerialNoStr + cumSerialNo;
				}else{
					cumSerialNoStr = cumSerialNoStr + cumSerialNo + ",";
				}
			}
		}
		if(tbs -1 > 10){
			//('定制表信息不能大于10张！');
			alert('定制表信息不能大于10张！');
			return;
		}
		<%--
		if(cls > 50){
			BIONE.tip('定制列信息不能大于50列！');
			return;
		}
		--%>
		$.ajax({
			cache : false,
			async : true,
			data:{
				cumSerialNos : cumSerialNoStr
			},
			url : "${ctx}/ecif/orgctzt/setupColumns.json",
			type : "post",		
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("请稍后...");
			},
			complete : function(XMLHttpRequest) {
				BIONE.loading = false;
				BIONE.hideLoading();				
				BIONE.isSessionAlive(XMLHttpRequest);
			},
			success : function() {
				//BIONE.tip("保存成功！");
				alert("保存成功！");
				//刷新grid
				tree_refresh();
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}	
		});
    }
    function cancelSet(){
		tree_refresh();
    }
    
    function setcolumn(){
		$.ajax({
			cache : false,
			async : false,
			type : 'post',
			url : "${ctx}/ecif/orgctzt/getTableColumn.json",
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("正在加载数据中...");
			},
			complete : function(XMLHttpRequest) {
				BIONE.loading = false;
				BIONE.hideLoading();				
				BIONE.isSessionAlive(XMLHttpRequest);
			},
			success : function(result) {
				tableColumn = result;
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
		var columns = tableColumn.columns;
		var tables = tableColumn.tables;
		var gridJsonStr = "";
		var cstidStr = "";
		var columnsJsonStr = "columns : [";
		if(columns != null){
			var tablesStr = "";
			for(var j = 0; j < tables.length; j ++){
				var tableStr = "{display: '"+tables[j].tbCnnm+"', columns: [";
				var oneTableColumnStr = "";
				for(var i = 0; i < columns.length; i ++){
					if(tables[j].tbSerialNo == columns[i].tbSerialNo){
						var tbNmAcronym = columns[i].tbNmAcronym; // 获取列所属表缩写
						var cumName = columns[i].cumName.replace(/[_]/g,""); // 获取列名并去下划线
						var columnName = tbNmAcronym+cumName; // 拼接列名为属性名
						columnName = columnName.toLowerCase(); // 转换为小写
						var cumCnnm = columns[i].cumCnnm; // 获取列中文名
						var dataType = columns[i].dataType; // 获取字段数据类型
						var dataLen = columns[i].dataLen;
						var oneCumJsonStr = "";
						if(columnName == "cstcustid"){
							cstidStr = "{name : '"+columnName+"',hide:1},";
						}else{
							var minWidth = "12%";
							var width = "20%";
							var len = 3*cumCnnm.length;
							if(len > 20){
								width = len+"%";
							}
							var align = "center";
							if(dataType == "DECIMAL"){
								align = "right";
							}
							if(dataType == "VARCHAR"){
								align = "left";
							}
							oneCumJsonStr = "{display: '"+cumCnnm+"',name: '"+columnName+"',align: '"+align+"',width: '"+width+"',minWidth: '"+minWidth+"'}";
							oneTableColumnStr = oneTableColumnStr + oneCumJsonStr + ",";
						}
					}
				}
				if(oneTableColumnStr.length != 0){
					oneTableColumnStr = oneTableColumnStr.substring(0, oneTableColumnStr.length-1);
				}
				if(j == tables.length -1){
					tableStr = tableStr + oneTableColumnStr +"]}";
				} else {
					tableStr = tableStr + oneTableColumnStr +"]},";
				}
				tablesStr = tablesStr + tableStr;
			}
			columnsJsonStr = columnsJsonStr + cstidStr + tablesStr + "],";
			gridJsonStr = "{width : '100%'," + columnsJsonStr + "checkbox : false, delayLoad :true, usePage : true, isScroll : false, rownumbers : true, alternatingRow : true, colDraggable : true, dataAction : 'server', method : 'post', url : '${ctx}/ecif/orgctzt/list.json', sortName : 'CST.CUST_NO', sortOrder : 'asc', toolbar : {} }";
			gridJson = eval("("+gridJsonStr+")"); 
			grid = $("#maingrid").ligerGrid(gridJson);
		}
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
	<div id="template.left.up.icon">
		<img src="${ctx }/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">
		<div style="float: left"><span style="font-size: 12">定制查询列</span></div>&nbsp;&nbsp;<div style="float: right;margin-top: 5px;"><div style='float:left;'><input id='hiddenTree' type='hidden' /><input id='searchNumber' type='hidden' value=0 /> <input id='treeValue' type='text' style='height:18px;width:100px;border-left:1px solid #D6D6D6;border-right:0px solid #FFFFFF;border-top:1px solid #D6D6D6;border-bottom:1px solid #D6D6D6;' onkeypress='treeKeyDown(this);'/></div><div id='test' class='l-icon l-icon-search3' style='float:left;padding:1px;cursor:pointer;border-right:1px solid #D6D6D6;border-top:1px solid #D6D6D6;border-bottom:1px solid #D6D6D6;' onclick='f_selectNode();'></div></div>
	</div>
	<div id="template.treeSetup.down">
		<div style="width: 100%" align="center"><span style="font-size: 12;">定制表不能大于10张</span></div>
	</div>
</body>
</html>