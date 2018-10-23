<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template11.jsp">
<head>
<script type="text/javascript">
	var grid;
	var btns;
	var resNo = null;//资源标识
	var resDefNo = null;//资源定义标识
	var urlType = "add";//方法标志位
	var currentRow; //行内容
	var dialog;
	var checkOperId = null;//上级标识树标志位 
	var selectedResOper;//选中行
	var menuResNo = "AUTH_RES_MENU";

	$(function() {
		//该方法暂只提供function的操作维护
		//initOperTree();
		initFuncTree();
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
		$("#search input[name = 'resDefNo']").attr("field", "resDefNo");
		$("#search input[name = 'resNo']").attr("field", "resNo");
	});

	function initFuncTree() {
		window['navtab1'] = $("#navtab1").ligerTab({
			onAfterSelectTabItem : function() {
				resNo = null;//点击tab是清空参数
				resDefNo = null;
			}
		});
		var resDefNoTmp = menuResNo;
		var setting = {
			check : {
				chkStyle : 'checkbox',
				enable : false,
				chkboxType : {
					"Y" : "ps",
					"N" : "s"
				}
			},
			data : {
				key : {
					name : 'text'
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "upId",
					rootPId : 0
				}
			},
			view : {
				selectedMulti : false,
				showLine : true
			},
			callback : {
				onClick : function(event, treeId, treeNode) {
					if (treeNode.params.type != "M") {
						resDefNo = resDefNoTmp;
						resNo = treeNode.id;
						grid.loadServerData({
							"resDefNo" : resDefNo,
							"resNo" : resNo
						}, null);
						$("#search input[name=resDefNo]").val(resDefNo);
						$("#search input[name=resNo]").val(resNo);
						$("#search input[name=resOper_operNo_code]").val("");
						$("#search input[name=resOper_operName_name]").val("");
					} else {
						resDefNo = null;
						resNo = null;
						grid.loadServerData({
							"resDefNo" : resDefNo,
							"resNo" : resNo
						}, null);
						$("#search input[name=resDefNo]").val(resDefNo);
						$("#search input[name=resNo]").val(resNo);
						$("#search input[name=resOper_operNo_code]").val("");
						$("#search input[name=resOper_operName_name]").val("");
					}
				}
			}
		};
		leftTreeObj = $.fn.zTree.init($("#tree1"), setting);
		//加载数据
		loadTree("${ctx}/bione/admin/logicSys/getFuncList.json",
				leftTreeObj);
		//构造高度
		if ($("#home1Container") && $("#left")) {
			$("#home1Container").height($("#left").height() - 32);
		}

	}

	//加载树中数据
	function loadTree(url, component, data) {
		$.ajax({
			cache : false,
			async : true,
			url : url,
			dataType : 'json',
			type : "post",
			beforeSend : function() {
				BIONE.loading = true;
				BIONE.showLoading("正在加载数据中...");
			},
			complete : function() {
				BIONE.loading = false;
				BIONE.hideLoading();
			},
			success : function(result) {
				var nodes = component.getNodes();
				var num = nodes.length;
				for ( var i = 0; i < num; i++) {
					component.removeNode(nodes[0], false);
				}
				if (result.nodes.length > 0) {
					var resultNodes = result.nodes;
					component.addNodes(null, resultNodes, false);
					component.expandAll(false);
				}
				if (result.indexPage != null && result.indexPage != "") {
					indexPageId = result.indexPage;
				}
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}

	//搜索表单
	function searchForm() {
		$("#search").ligerForm({
			fields : [ {
				display : '操作标识',
				name : 'resOper_operNo_code',
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					field : "operNo",
					op : "="
				}
			}, {
				display : '操作名称',
				name : 'resOper_operName_name',
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					field : "operName",
					op : "like"
				}
			}, {
				name : "resDefNo",
				type : "hidden",
				attr : {
					field : "resDefNo",
					op : "="
				}
			}, {
				name : "resNo",
				type : "hidden",
				attr : {
					field : "resNo",
					op : "="
				}
			} ]
		});
	}

	//渲染表单
	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			height : '100%',
			width : '100%',
			columns : [ {
				display : '操作标识',
				id : 'operNo',
				name : 'operNo',
				align : 'left',
				isSort : false,
				width : '15%'
			}, {
				display : '操作名称',
				name : 'operName',
				align : 'left',
				isSort : false,
				width : '15%'
			}, {
				display : '访问URL',
				name : 'visitUrl',
				align : 'left',
				isSort : false,
				width : '20%'
			}, {
				display : '方法名称',
				name : 'methodName',
				align : 'left',
				isSort : false,
				width : '20%'
			}, {
				display : '备注',
				name : 'remark',
				align : 'left',
				isSort : false,
				width : '27%'
			} ],
			tree : {
				columnName : 'operNo'
			},
			checkbox : false,
			isScroll : false,
			rownumbers : true,
			usePager : false, //服务器分页
			alternatingRow : true,//附加奇偶行效果行
			colDraggable : true,
			autoCheckChildren : false,
			dataAction : 'server',//从后台获取数据
			method : 'post',
			url : "${ctx}/bione/admin/resOper/getResOperInfo.json",
			sortName : 'operNo', //第一次默认排序的字段
			sortOrder : 'asc',
			//isChecked : f_isChecked,
			//onCheckRow : f_onCheckRow,
			//onCheckAllRow : f_onCheckAllRow,
			toolbar : {}
		});
	}

	//创建按钮
	function initButtons() {
		btns = [ {
			text : "新增",
			click : resOper_add,
			icon : 'add',
			operNo : 'resOper_add'
		}, {
			text : "修改",
			click : resOper_modify,
			icon : 'modify',
			operNo : 'resOper_modify'
		}, {
			text : "删除",
			click : resOper_delete,
			icon : 'delete',
			operNo : 'resOper_delete'
		} ];

		BIONE.loadToolbar(grid, btns, function() {
		});
	}

	function resOper_add(item) {
		if (resNo != null && resDefNo != null) {
			urlType = "add";
			checkOperId = null;
			var selected = grid.getSelectedRow();
			if (selected) {
				var upid = selected.operId;
				var upno = selected.operNo;
				BIONE.commonOpenLargeDialog('添加资源操作', 'resOperChange',
						'${ctx}/bione/admin/resOper/new?upid=' + upid
								+ '&&upno=' + upno);
			} else {
				BIONE.commonOpenLargeDialog('添加资源操作', 'resOperChange',
						'${ctx}/bione/admin/resOper/new');
			}
		} else {
			BIONE.tip("请先选择资源节点 ");
		}
	}

	function resOper_modify(item) {
		selectedResOper = grid.getSelectedRows();
		if (selectedResOper.length == 1) {
			urlType = "modify";
			currentRow = selectedResOper[0];
			checkOperId = selectedResOper[0].operId;
			dialog = BIONE.commonOpenLargeDialog('修改资源操作', 'resOperChange',
					'${ctx}/bione/admin/resOper/'
							+ selectedResOper[0].operId + '/edit');
		} else if (selectedResOper.length > 1) {
			BIONE.tip("只能选择一条记录");
		} else {
			BIONE.tip("请选择一条记录");
		}
	}

	function resOper_delete() {
		selectedResOper = grid.getSelectedRows();
		if (selectedResOper.length == 1) {
			//拼接待删除条目ID
			$.ligerDialog
					.confirm(
							'确定要删除选定的记录吗',
							function(flag) {
								if (flag) {
									/*var deleteStr = '';
									for ( var i = 0; i < selectedResOper.length; i++) {
										deleteStr+=selectedResOper[i].operId;
										if(i!=(selectedResOper.length-1))
											deleteStr+=',';
									}*/

									$
											.ajax({
												type : "POST",
												url : "${ctx}/bione/admin/resOper/deleteResOper.json?operId="
														+ selectedResOper[0].operId
														+ "&resDefNo="
														+ resDefNo
														+ "&resNo="
														+ resNo,
												success : function(data) {
													deleteFlag = true;
													delete_result(data.operNoStrB);
												}
											});
								} else {
									return;
								}
								;
							});
		} else if (selectedResOper.length > 1) {
			BIONE.tip("只能选择一条记录");
		} else {
			BIONE.tip('请选择一条记录');
		}
	}
	function delete_result(operNoStrB) {
		if (deleteFlag) {
			if (operNoStrB == null || operNoStrB == "") {
				BIONE.tip('删除成功');
			} else {
				//得到已授权资源操作的operNo
				/*var operNo = operNoStrB.split(",");
				var tipStr = "";
				for(var i=0;i<operNo.length;i++){
					var tmp = operNo[i];
					var j=(i+1);
					while(j<operNo.length){
						if(tmp==operNo[j]){
							operNo[j] = operNo[(operNo.length-1)];
							operNo.pop();
						}
						j++;
					}
					tipStr+=tmp+',';
				}
				tipStr = tipStr.slice(0,(tipStr.length-1));多行删除时用*/
				$.ligerDialog.success('操作标识为"' + operNoStrB + '"的节点已被授权，不能删除');
			}
			grid.loadServerData({
				"resDefNo" : resDefNo,
				"resNo" : resNo
			}, null);
			//清空原选中数组 
			//checkedDatatype = [];

		} else {
			$.ligerDialog.error('删除失败');
		}
		deleteFlag = false;
	}

	//全选方法
	function f_onCheckAllRow(checked) {
		for ( var rowid in this.records) {
			if (checked)
				addCheckedDatatype(this.records[rowid]['operId']);
			else
				removeCheckedDatatype(this.records[rowid]['operId']);
		}
	}

	//分页多选
	var checkedDatatype = [];
	function findCheckedDatatype(operId) {
		for ( var i = 0; i < checkedDatatype.length; i++) {
			if (checkedDatatype[i] == operId) {
				return i;
			}
		}
		return -1;
	}
	function addCheckedDatatype(operId) {
		if (findCheckedDatatype(operId) == -1)
			checkedDatatype.push(operId);
	}
	function removeCheckedDatatype(operId) {
		var i = findCheckedDatatype(operId);
		if (i == -1)
			return;
		checkedDatatype.splice(i, 1);
	}
	function f_isChecked(rowdata) {
		if (findCheckedDatatype(rowdata.operId) == -1) {
			return false;
		} else {
			return true;
		}
	}
	function f_onCheckRow(checked, data) {
		if (checked) {
			addCheckedDatatype(data.operId);
		} else
			removeCheckedDatatype(data.operId);
	}
	function f_getChecked() {
		alert(checkedDatatype.join(','));
	}
</script>
</head>
<body>
	<div id="template.left">
		<div id="navtab1" style="overflow: hidden;">
			<div tabid="home1" title="功能" lselected="true"
				style="overflow: auto;">
				<div id="home1Container"
					style="width: 100%; overflow: auto; clear: both;">
					<div id="tree1" class="ztree"
						style="font-size: 12; background-color: F1F1F1; width: 95%"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>