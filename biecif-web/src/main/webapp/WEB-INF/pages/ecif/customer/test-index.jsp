<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template1.jsp">
<script type="text/javascript">
	var grid, btns, url, ids = [], test = [];
	var testId;
	$(init);

	function init() {
		url = "${ctx}/ecif/test/list.json";
		initGrid();
		searchForm();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
	}

	//搜索表单应用ligerui样式
	function searchForm() {
		$("#search").ligerForm({
		/* 	labelWidth : 100,
			inputWidth : 220,
			space : 30, */
			fields : [ {
				display : "帐号",
				name : "testId",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "=",
					field : "test.testId"
				}
			}, {
				display : "名称",
				name : "testName",
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "test.testName"
				}
			}, {
				display : "类型",
				name : "testTypeBox",
				newline : true,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "testType",
					url : "${ctx}/ecif/test/getComBoBox.json",
					ajaxType : "get"
				},
				attr : {
					op : "=",
					field : "test.testType"
				}
			} , {
				display : "标志",
				name : "testFlagBox",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : 'testFlag',
					data : [{id : '1', text : '启用'}, {id : '0', text : '停用'}]
				},
				attr : {
					op : "=",
					field : "test.testFlag"
				}
			} ]
		});

	}

	function initGrid() {

		grid = $("#maingrid").ligerGrid(
				{
					width : '100%',
					columns : [
							{
								display : '帐号',
								name : 'testId',
								align : 'left',
								width : '13%',
								minWidth : '10%'
							},
							{
								display : '名称',
								name : 'testName',
								align : 'center',
								width : '14%',
								minWidth : '10%'
							},
							{
								display : '类型',
								name : 'testType',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								render : RenderType
							},
							{
								display : '标志',
								name : 'testFlag',
								align : 'center',
								width : '13%',
								minWidth : '10%',
								render : RenderFlag
							} ],
					checkbox : true,
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
		},{
			text : '下载模板',
			click : file_down,
			icon : 'export',
			operNo : 'file_down'
		}, {
			text : '上传文件',
			click : file_add,
			icon : 'import',
			operNo : 'file_add'
		}];
		BIONE.loadToolbar(grid, btns, function() { });
	}
	
	function RenderType(rowdata){
		return BIONE.paramTransformer(rowdata.testType, 
				'${ctx}/ecif/test/getCodeComBoBox.json', '');
	}
	
	function RenderFlag(rowdata){
		if(rowdata.testFlag == '1'){
			return '启用';
		}else if(rowdata.testFlag == '0'){
			return '停用';
		}else{
			return rowdata.testFlag;
		}
	}
	
	function test_add_dynamic(){
		BIONE.commonOpenLargeDialog('添加信息', 'testManage',
				'${ctx}/ecif/test/new');
	}
	
	function test_modify_dynamic(){
		achieveIds();
		if (ids.length == 1) {
			if(test != "") {
				$.ligerDialog.error("信息: [ " + test.join(", ").toString() + " ] 不可修改!", "错误", function() {
					return false;
				});
			} else {
				testId = ids[0];
				BIONE.commonOpenLargeDialog('修改信息', 'testManage',
					'${ctx}/ecif/test/'+testId+'/edit');
			}
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
					if(test != "") {
						$.ligerDialog.error("信息: [ " + test.join(", ").toString() + " ] 不可删除!", "错误", function() {
							return false;
						});
					} else {
					 	$.ajax({
							async : false,
							type : "DELETE",
							url : "${ctx}/ecif/test/"+ids.join(","),
							success : function() {
								flag = true;
							}
						});
					}
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
	function file_add(item) {
		window.parent.BIONE.commonOpenDialog('上传模板', 'upload',550,335,
		'${ctx}/bicustrisk/custInfoadd/custadd/showUploadControl?custType='+custType);
	}
	function file_down(){
		/* $.ajax({
			type : "POST",
			url : '${ctx}/bicustrisk/custInfoadd/custadd/downLoad?custType='+custType,
			dataType : "json",
			success : function() {
				BIONE.tip("下载成功!");
			},
			error: function() {
				BIONE.tip("下载失败!");
			}
		}); */
		$('body').append($('<iframe id="download"/>'));
		$("#download").attr('src','${ctx}/bicustrisk/custInfoadd/custadd/downLoad?custType='+custType);
	}
	function achieveIds() {
		ids = [];
		test = [];
		var rows = grid.getSelectedRows();
		$(rows).each(function() {
			ids.push(this.testId);
			//if(this.isBuiltin == "1") 
			//	test.push(this.testId);
		});
/* 		for(var i in rows) {
			ids.push(rows[i].userId);
		} */
	}
</script>
</head>
<body>
</body>
</html>