<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template1.jsp">
<head>
<!-- 基础的JS和CSS文件 -->
<script type="text/javascript">
	var grid,ids;
	
	$(function() {
		initSearchForm();
		initGrid();
		initToolBar();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	});

	
	function initSearchForm(){
		$("#search").ligerForm({
			fields : [ {
				name : 'roleId',
				type : 'hidden'
			}, {
				display : '角色名称',
				name : "roleName",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					field : 'roleName',
					op : "like"
				}
			}, {
				display : '角色类型',
				newline : false,
				cssClass : "field",
				name:'roleTypeValue',
				type:'select',
				options : {
					valueFieldID : "roleType",
					data:[{
						text:'系统角色',
						id:"01"
					},{
						text:'业务角色',
						id:'02'
					}]
				},
				attr : {
					field : 'roleType',
					op : "="
				}
			}
			]
		});
	};
	
	function initGrid(){
		grid = $("#maingrid").ligerGrid({
			toolbar : {},
			checkbox : true,
			columns : [ {
				display : '角色编号',
				name : 'roleNo',
				width : "19%",
				align : 'left'
			}, {
				display : '角色名称',
				name : 'roleName',
				width : "19%",
				align : 'left'
			}, {
				display : '角色状态',
				name : 'roleSts',
				width : "19%",
				align : 'left',
				render : QYBZRender
			}, {
				display : '角色类型',
				name : 'roleType',
				width : "19%",
				align : 'left',
				render:QYBZRender2
			}, {
				display : '最后修改时间',
				name : 'lastUpdateTime',
				width : "17%",
				type : 'date'
			} ],
			dataAction : 'server', //从后台获取数据
			usePager : true, //服务器分页
			alternatingRow : true, //附加奇偶行效果行
			colDraggable : true,
			url : "${ctx}/bione/admin/role/list.json",
			sortName : 'roleId',//第一次默认排序的字段
			sortOrder : 'asc', //排序的方式
			rownumbers : true,
			width:'100%'
		});
	}
	
	function initToolBar(){
		var btns=[ {
				text : '增加',
				click : f_open_add,
				icon : 'add'
			}, {
				text : '修改',
				click : f_open_update,
				icon : 'modify'
			}, {
				text : '删除',
				click : f_delete,
				icon : 'delete'
			}, {
				text : '导出用户角色',
				click : f_export_userrole,
				icon : 'export'
			}, {
				text : '导出角色权限',
				click : f_export_rolegrant,
				icon : 'export'
			} ];
		BIONE.loadToolbar(grid, btns, function() { });
	}
	
	function QYBZRender2(rowdata) {
		if (rowdata.roleType == '01') {
			return "系统角色";
		}
		if (rowdata.roleType == '02') {
			return "业务角色";
		} else {
			return rowdata.roleType;
		}
	}
	
	function QYBZRender(rowdata) {
		if (rowdata.roleSts == '1') {
			return "启用";
		}
		if (rowdata.roleSts == '0') {
			return "停用";
		} else {
			return rowdata.roleSts;
		}
	}
	//角色添加函数
	function f_open_add() {

		BIONE.commonOpenLargeDialog("角色添加", "roleAddWin",
				"${ctx}/bione/admin/role/new");

	}

	function f_open_update() {
		achieveIds();
		
		if(ids.length == 1){
			BIONE.commonOpenLargeDialog("角色修改", "roleModifyWin","${ctx}/bione/admin/role/" + ids[0].roleId + "/edit");
		}else if(ids.length>1){
			BIONE.tip("只能选择一行进行修改");
		}else{
			BIONE.tip("请选择需要修改的角色信息");
			return;
		}
		
		var row = grid.getSelectedRow();
		if (!row) {
			BIONE.tip('请选择行');
			return;
		}
		

	}
	function f_delete() {
		var selectedRow = grid.getSelecteds();
		if(selectedRow.length == 0){
			BIONE.tip('请选择行');
			return;
		}
		$.ligerDialog.confirm('确实要删除这' + selectedRow.length + '条记录吗!',
				function(yes) {
					var length = selectedRow.length;
					if (yes) {
						var ids = "";
						for ( var i = 0; i < length; i++) {
							if(ids != ""){
								ids += ",";
							}
							ids += selectedRow[i].roleId;
						}
						$.ajax({
							type : "DELETE",
							url : '${ctx}/bione/admin/role/destroyOwn.json',
							dataType : "json",
							type:"post",
							data : {
								"ids":ids
							},
							success : function(result){
								if(result && result.message == "success"){
									BIONE.tip('删除成功');
									grid.loadData();
								}else{
									BIONE.tip('删除失败,请联系管理员!');
								}
							}
						});
					}
				});
	}
	
	function achieveIds() {
		ids = [];
		var rows = grid.getSelectedRows();
		for(var i in rows) {
			ids.push(rows[i]);
		}
	}
	
	function f_export_userrole(){
		var timestamp=new Date().getTime();

		$.ajax({
			cache : false,
			async : true,
			url: '${ctx}/ecif/authexport/getExportFile.json?' + timestamp,
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

	function doDownload(file) {
		if(file==null||file==""){
			BIONE.tip("下载失败。");
			return;
		}
		var form = $('<form/>').attr({
			target: '',
			method: 'post',
			action: '${ctx}/ecif/authexport/export.xls'
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
	
	function f_export_rolegrant(){
		var timestamp=new Date().getTime();

		$.ajax({
			cache : false,
			async : true,
			url: '${ctx}/ecif/authexport/getExportFileGrant.json?' + timestamp,
			type: 'post',
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("下载中，请稍候...");
			},
			complete : function() {
				BIONE.loading = false;
				BIONE.hideLoading();
			},
			success: doDownloadGrant,
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
		
	}
	
	function doDownloadGrant(file) {
		if(file==null||file==""){
			BIONE.tip("下载失败。");
			return;
		}
		var form = $('<form/>').attr({
			target: '',
			method: 'post',
			action: '${ctx}/ecif/authexport/exportGrant.xls'
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
	
	
</script>
</head>
<body>
</body>
</html>