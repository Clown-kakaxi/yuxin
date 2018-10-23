<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [];
	var sform ;
	
	$(function() {
		url = "${ctx}/ecif/transaction/txdef/list.json";
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});

	function RenderFlag(rowdata){
		if(rowdata.txLvl1Tp == 'W'){
			return '写交易';
		}else if(rowdata.txLvl1Tp == 'R'){
			return '读交易';
		}else{
			return rowdata.txLvl1Tp;
		}
	}

	function RenderFlag2(rowdata){
		if(rowdata.txLvl2Tp == 'K'){
			return '开户';
		}else if(rowdata.txLvl2Tp == 'U'){
			return '修改';
		}else if(rowdata.txLvl2Tp == 'D'){
			return '删除';
		}else if(rowdata.txLvl2Tp == 'A'){
			return '增加';
		}else if(rowdata.txLvl2Tp == 'Q'){
			return '查询';
		}else{
			return rowdata.txLvl2Tp;
		}
	}

	function RenderFlag3(rowdata){
		if(rowdata.txCfgTp == '1'){
			return '扩展';
		}else if(rowdata.txCfgTp == '2'){
			return '定制';
		}else if(rowdata.txCfgTp == '0'){
			return '标准';
		}else{
			return rowdata.txCfgTp;
		}
	}

	function RenderFlag4(rowdata){
		if(rowdata.txState == '1'){
			return '启用';
		}else if(rowdata.txState == '0'){
			return '未启用';
		}else{
			return rowdata.txState;
		}
	}	

	function RenderFlag5(rowdata){
		if(rowdata.txCustType == '0'){
			return '未知';
		}else if(rowdata.txCustType == '1'){
			return '个人';
		}else if(rowdata.txCustType == '2'){
			return '公司';
		}else if(rowdata.txCustType == '3'){
			return '同业';
		}else{
			return rowdata.txCustType;
		}
	}
	
	function RenderFlag6(rowdata){
		if(rowdata.txCheckXsd == '0'){
			return '否';
		}else if(rowdata.txCheckXsd == '1'){
			return '是';
		}else{
			return rowdata.txCheckXsd;
		}
	}
	
	function RenderFlag7(rowdata){
		if(rowdata.txDivInsUpd == '0'){
			return '否';
		}else if(rowdata.txDivInsUpd == '1'){
			return '是';
		}else{
			return rowdata.txDivInsUpd;
		}
	}
	
	//搜索表单应用ligerui样式
	function searchForm() {
		sform =$("#search").ligerForm({
		/*	labelWidth : 100,
		 	inputWidth : 220,
			space : 30, */

			fields : [ {
				display : "交易编号",
				name : "txCode",
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "txdef.txCode"
				}	
			}, 
			{
				display : "交易名称",
				name : "txName",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "txdef.txName"
				}
			 }, 
				{
					display : "交易中文名称",
					name : "txCnName",
					newline : false,
					type : "text",
					cssClass : "field",
					attr : {
						op : "like",
						field : "txdef.txCnName"
					}
				 }
			]
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

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
							{
								display : '交易ID',
								name : 'txId',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								hide :1

							},
							{
								display : '交易编号',
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
								display : '交易类型一级分类',
								name : 'txLvl1Tp',
								align : 'center',
								width : '15%',
								minWidth : '10%',
								render : RenderFlag
							},
							{
								display : '交易类型二级分类',
								name : 'txLvl2Tp',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag2
							},
							{
								display : '交易处理类名称',
								name : 'txDealClass',
								align : 'center',
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '交易客户化处理类名称',
								name : 'txDealEngine',
								align : 'center',
								width : '15%',
								minWidth : '10%'
							},
							{
								display : '交易配置类型',
								name : 'txCfgTp',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag3
							},
							{
								display : '交易配置状态',
								name : 'txState',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag4
							},
							{
								display : '交易客户类型',
								name : 'txCustType',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag5
							},
							{
								display : '是否进行XSD验证',
								name : 'txCheckXsd',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag6
							} ,
							{
								display : '是否区分新增与更新',
								name : 'txDivInsUpd',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								render : RenderFlag7 
							} ,
							{
								display : '创建日期',
								name : 'createTm',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								type : 'date',
								format : 'yyyy-MM-dd'							
							}
							
							],
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
					sortName : 'updateTm', //第一次默认排序的字段
					sortOrder : 'desc',
					toolbar : {}
				});
	}

	function initButtons() {
		btns = [ {
			text : '增加',
			click : resDef_add,
			icon : 'add',
			operNo : 'resDef_add'
		}, {
			text : '修改',
			click : resDef_modify,
			icon : 'modify',
			operNo : 'resDef_modify'
		}, {
			text : '删除',
			click : resDef_delete,
			icon : 'delete',
			operNo : 'resDef_delete'
		}, {	
			text : '报文配置',
			click : resDef_config,
			icon : 'modify',
			operNo : 'resDef_config'
		}, {
			text : '交易复制',
			click : resDef_copy,
			icon : 'modify',
			operNo : 'resDef_copy'
		}, {	
			text : '生成文件',
			click : resDef_newfile,
			icon : 'export',
			operNo : 'resDef_newfile'
		}, {	
			text : '下载文件',
			click : resDef_downfile,
			icon : 'download',
			operNo : 'resDef_downfile'	
		}, {	
			text : '导出全部交易',
			click : resDef_exportall,
			icon : 'export',
			operNo : 'resDef_exportall'
		}, {	
			text : '导出交易',
			click : resDef_exportsome,
			icon : 'export',	
			operNo : 'resDef_exportsome'
		}, {
			text: "导入交易",
			click: resDef_upload,
			icon: "up",
			operNo: "resDef_upload"
		}, {
			text: "还原交易",
			click: resDef_recover,
			icon: "up",
			operNo: "resDef_recover"
		} ];
		BIONE.loadToolbar(grid, btns, function() {
		});

	}

	function resDef_add(item) {
		BIONE.commonOpenLargeDialog('添加', 'resDefManage',
				'${ctx}/ecif/transaction/txdef/new');
	}
	function resDef_modify(item) {
		achieveIds();
		if (ids.length == 1) {
			//alert(ids[0]);
			var buttons = [
					{
						text : '保存',
						onclick : function(item, dialog) {
							BIONE.submitForm($("#mainform",dialog.frame.window.document), function() {
								//dialog.close();
								//initGrid();
								//parent.BIONE.tip('修改成功');
							}, function() {
								BIONE.tip('保存失败');
							});
						}
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ];
			BIONE.commonOpenLargeDialog('修改', 'resDefManage',
					'${ctx}/ecif/transaction/txdef/' + ids[0] + '/edit', buttons);
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	function resDef_copy(item) {
		achieveIds();
		if (ids.length == 1) {
			var buttons = [
					{
						text : '保存',
						onclick : function(item, dialog) {
							BIONE.submitForm($("#mainform",dialog.frame.window.document), function() {
								dialog.close();
								initGrid();
								parent.BIONE.tip('修改成功');
							}, function() {
								BIONE.tip('保存失败');
							});
						}
					}, {
						text : '取消',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ];
			BIONE.commonOpenLargeDialog('交易复制', 'resDefManage',
					'${ctx}/ecif/transaction/txdef/' + ids[0] + '/copy', buttons);
		} else if (ids.length > 1) {
			BIONE.tip('只能选择一条记录');
		} else {
			BIONE.tip('请选择记录');
		}
	}
	
	function resDef_delete(item) {
		achieveIds();
		if (ids.length > 0) {
			$.ligerDialog.confirm('确实要删除这些记录吗?', function(yes) {
				if(yes) {
					var flag = false;
					$.ajax({
						async : false,
						type : "DELETE",
						url : "${ctx}/ecif/transaction/txdef/" + ids.join(','),
						dataType : "script",
						success : function() {
							flag = true;
						}
					});
					if (flag == true) {
						BIONE.tip('删除成功');
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
	
    //报文配置
    function resDef_config() {
		var rows = grid.getSelectedRows();
		if (rows.length == 1) {
		    var txId = rows[0].txId;
		    dialog = commonOpenLargeDialog("报文信息", "authResWin",
			    "${ctx}/ecif/transaction/txmsg/" + txId + "/index"
				    );
		} else {
		    BIONE.tip("请选择需要操作的交易！");
		}
    }	
    
    
    //生成文件
    function resDef_newfile() {
		var rows = grid.getSelectedRows();
		if (rows.length >= 1) {
			var txId = rows[0].txId;
			for(var i=1;i<rows.length;i++){
				var id = rows[i].txId;
				txId = txId +"," + id;
			}
		   
		    //dialog = commonOpenLargeDialog("报文信息", "authResWin",
			//    "${ctx}/ecif/transaction/txmsgcheckinfo/new?txId=" + txId 
			//	    );
			$
			.ajax({
				type : "GET",
				async : false,
				url : "${ctx}/ecif/transaction/txmsgcheckinfo/new?txId=" + txId ,
				success : function() {
					
					BIONE.tip("生成报文成功！！");
				}
			});		    
		} else {
		    BIONE.tip("请选择需要操作的交易！");
		}
    }	

    //下载文件
    function resDef_downfile() {
		var rows = grid.getSelectedRows();
		if (rows.length >= 1) {
		   var txId = rows[0].txId;
		   for(var i=1;i<rows.length;i++){
				var id = rows[i].txId;
				txId = txId +"," + id;
		   }
		   /*dialog = commonOpenLargeDialog("报文信息", "authResWin",
			    "${ctx}/ecif/transaction/txdef/downfile?txId=" + txId 
				    );
		   /*  var timestamp=new Date().getTime();
		    $.ajax({
				url: '${ctx}/ecif/transaction/txdef/downfile?txId='+txId,
				type: 'get',
				success: ""
			});
		    
		    window.open("${ctx}/ecif/transaction/txdef/downfile?txId="+txId, "_blank",
			'toolbar=no,scrollbars=yes,overflow-x:auto,status=no,resizable=yes,center:yes,statusbars=yes,top=0,left=0,width=100,height=100');
		    */ 
		    $('body').append($('<iframe id="download" style="display:none;" />'));
		    $("#download").attr('src','${ctx}/ecif/transaction/txdef/downfile?txId='
			    + txId);		    
		    
		} else {
		    BIONE.tip("请选择一笔交易信息！");
		}
    }
    
	function resDef_exportall(){
		var timestamp=new Date().getTime();

		$.ajax({
			cache : false,
			async : true,
			url: '${ctx}/ecif/transaction/txdef/exportAll.json?' + timestamp,
			type: 'post',
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("下载中，请稍候...");
			},
			complete : function() {
				BIONE.loading = false;
				BIONE.hideLoading();
			},
			success: doDownload,
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
		
	}   
    
    function down(){
    	window.location.href=url;
    }
   
	// 获取选中的行
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		//debugger;
		for(var i in rows) {
			ids.push(rows[i].txId);
		}
		
	}
	
	commonOpenLargeDialog = function commonOpenLargeDialog(title, name,
			url, beforeClose) {
		var width = 1100;
		var height = 500;
		var _dialog = $.ligerui.get(name);
		if (_dialog) {
			$.ligerui.remove(name);
		}
		_dialog = $.ligerDialog.open({
			height : height,
			width : width,
			url : url,
			name : name,
			id : name,
			title : title,
			isResize : false,
			isDrag : true,
			isHidden : false
		});
		if(beforeClose!=null &&
				typeof beforeClose == "function"){
			_dialog._beforeClose = beforeClose;
		}
		return _dialog;
	};
	
	function doDownload(url) {
		window.open(url);
	}
	
	function resDef_exportall() {
		var openWindow = doDownload("${ctx}/ecif/transaction/excel/download");
		OpenWindow.document.close();
	}
	
	function resDef_exportsome() {
		var rows = grid.getCheckedRows(),
		str = "";
		if (rows && rows.length > 0) {
			$.each(rows, function(i, n) {
				if (str != "") {
					str += ",";
				}
				str += n.txId;
			});
			if ("" != str) {
				doDownload("${ctx}/ecif/transaction/excel/" + str + "/download");
			}
		} else {
			BIONE.tip("请选择数据！");
		}
	}
	
	function resDef_upload() {
		BIONE.commonOpenDialog("上传", "uploadobj", 562, 352, "${ctx}/ecif/transaction/excel/upload");
// 		BIONE.commonOpenSmallDialog("上传", "upload", "${ctx}/ecif/transaction/excel/upload");
	}
	
	function resDef_recover() {
		var rows = grid.getSelectedRows();
		if (rows.length == 1) {
			BIONE.commonOpenDialog("还原", "recover", 720, 400, "${ctx}/ecif/transaction/txdef/recover?txCode="+rows[0].txCode);
		} else {
		    BIONE.tip("只能选择一个交易进行还原！");
		}	
	}
	
	
</script>
</head>
<body>
</body>
</html>