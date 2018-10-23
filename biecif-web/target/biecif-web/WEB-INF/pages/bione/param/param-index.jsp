<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template2.jsp">
<script type="text/javascript">
	var treeObj = null;

	$(function() {
		var currentNode = null;

		var setting = {
			async : {
				autoParam : [ "id=nodeId" ],
				enable : true,
				contentType : "application/json",
				url : "${ctx}/bione/variable/paramType/list.json",
				dataType : "json",
				type : "get",
				dataFilter : function(treeId, parentNode, childNodes) {
					if (childNodes) {
						for ( var i = 0; i < childNodes.length; i++) {
							var isHadChild = childNodes[i].params.isParent;
							childNodes[i].isParent = isHadChild;
							if (isHadChild == "true") {
								childNodes[i].icon = '${ctx}/images/classics/icons/folder.png';
							} else {
								childNodes[i].icon = '${ctx}/images/classics/icons/collapse-all.gif';
							}
						}
					}
					return childNodes;
				}
			},
			data : {
				key : {
					name : "text"
				}
			},
			view : {
				selectedMulti : false,
				showLine : false
			},
			callback : {
				onClick : function(event, treeId, treeNode) {
					$("#treeContainer").bind("mousedown", onBodyMouseDown);
					currentNode = treeNode;
					grid.set('parms', {
						paramTypeNo : currentNode.data.paramTypeNo
					});
					$("#search input[name='paramTypeNo']").val("");
					$("#search input[name='paramTypeNo']").val(
							currentNode.data.paramTypeNo);
					grid.loadData();
				}
			}
		};
		treeObj = $.fn.zTree.init($("#tree"), setting);

		$("#treeToolbar").ligerTreeToolBar({
			items : [ {
				text : '增加',
				click : addParamType,
				icon : 'add'
			}, {
				line : true
			}, {
				text : '删除',
				click : delParamType,
				icon : 'delete'
			}, {
				line : true
			}, {
				text : '修改',
				click : updateParamType,
				icon : 'modify'
			} ]
		});

		$("#search").ligerForm({
			fields : [ {
				display : '参数名称',
				name : "paramName",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "paramName"
				}
			}, {
				display : "参数值",
				name : 'paramValue',
				newline : false,
				type : "text",
				cssClass : "field",
				attr : {
					op : "like",
					field : "paramValue"
				}
			}, {
				name : 'paramTypeNo',
				type : 'hidden',
				attr : {
					field : 'paramTypeNo',
					op : "="
				}
			} ]
		});
		grid = $("#maingrid").ligerGrid({
			checkbox : false,
			columns : [  {
				display : '参数名称',
				name : 'paramName',
				id:'paramName',
				width : '45%',
				align : 'left'
			}, {
				display : '参数值',
				name : 'paramValue',
				width : '20%',
				align : 'left'
			}, {
				display : '备注',
				name : 'remark',
				width : '30%',
				align : 'left'
			} ],
			dataAction : 'server', //从后台获取数据
			usePager : false, //服务器分页
			alternatingRow : false, //附加奇偶行效果行
			colDraggable : true,
			url : "${ctx}/bione/variable/param/list.json",
			sortName : 'orderNo',//第一次默认排序的字段
			sortOrder : 'asc', //排序的方式
			rownumbers : true,
			tree : {
				columnId : 'paramName'
			},
			toolbar : []
		});

		var btns = [ {
			text : '新增子参数值',
			click : addParam,
			icon : 'add'
		},{
			text:'新增根参数值',
			click:addFathParam,
			icon:'add'
		},{
			text : '修改参数值',
			click : modifyParam,
			icon : 'modify'
		}, {
			text : '删除参数值',
			click : delParam,
			icon : 'delete'
		} ];

		BIONE.loadToolbar(grid, btns, function() {
		});

		BIONE.addSearchButtons("#search", grid, "#searchbtn");

		//单击空白处取消选择
		function onBodyMouseDown(event) {
			if (!(event.target.id == "treeToolbar" || $(event.target).parents(
					"#treeToolbar").length > 0)) {
				treeObj.cancelSelectedNode();
				currentNode = null;
			}
		}

		function addParam() {
			if (currentNode == undefined) {
				BIONE.tip('请选择参数类型');
				return;
			}
			var rows = grid.getSelectedRows();
			 if(rows.length==0){
				BIONE.tip("请选择一条记录作为上级节点。");
			} 
			
			dialog = BIONE.commonOpenLargeDialog('新增参数值', 'paramAddWin',
					'${ctx}/bione/variable/param/new?paramTypeNo='
							+ currentNode.data.paramTypeNo+"&upNo="+rows[0].paramId);
		}
		 function addFathParam(){
			if (currentNode == undefined) {
				BIONE.tip('请选择参数类型');
				return;
			}
			dialog = BIONE.commonOpenLargeDialog('新增根参数值', 'paramAddWin',
					'${ctx}/bione/variable/param/new?paramTypeNo='
							+ currentNode.data.paramTypeNo+"&upNo=0");
		} 

		 var str="";
			function getParamIdAndChildIdByInfo(rowInfo){
				
				if(rowInfo==1){
					str="";
					}
				var children=grid.getChildren(rowInfo);
				str+=rowInfo.paramId+",";
				if(children){
					for(var i=0;i<children.length;i++){
						getParamIdAndChildIdByInfo(children[i]);
					}
				}
			}
 function delParam() {
		var selectedRow = grid.getSelecteds();
		if(selectedRow.length == 0){
			BIONE.tip('请选择行');
			return;
		}
		for(var k=0;k<selectedRow.length;k++){
			getParamIdAndChildIdByInfo(selectedRow[k]);	
		}
		$.ligerDialog.confirm('确实要删除这些记录及其子记录吗!', function(yes) {
			if(yes){
				BIONE.ajax({
					type : "DELETE",
					url : '${ctx}/bione/variable/param/' + str,
					dataType : "scriptType"
				},function(){
					BIONE.tip('删除成功');
					str="";
					grid.loadData();
				});
			}else{
				BIONE.tip('取消删除');
			}
		});
	}
		function modifyParam() {
			var rows = grid.getSelectedRows();
			if (rows == null || rows.length == 0) {
				BIONE.tip('请选择行');
				return;
			}
			if (rows.length > 1) {
				BIONE.tip('请选择行');
				return;
			}

			var id = rows[0].paramId;
			BIONE.commonOpenLargeDialog("参数值修改", "paramModifyWin",
					"${ctx}/bione/variable/param/" + id + "/edit");
		}

		function addParamType() {
			if (currentNode != null) {
				dialog = BIONE.commonOpenLargeDialog('新增参数类型',
						'paramTypeAddWin',
						'${ctx}/bione/variable/paramType/new?upNo='
								+ currentNode.data.paramTypeNo);
			} else{
				dialog = BIONE.commonOpenLargeDialog('新增参数类型',
						'paramTypeAddWin',
						'${ctx}/bione/variable/paramType/new?upNo=0');
			}
		}

		function updateParamType() {
			if (currentNode == null) {
				BIONE.tip('请选择参数类型');
				return;
			}
			dialog = BIONE.commonOpenLargeDialog('修改参数类型',
					'paramTypeUpdateWin', '${ctx}/bione/variable/paramType/'
							+ currentNode.data.paramTypeNo + '/edit');
		}

		function delParamType() {
			if (currentNode == null) {
				BIONE.tip('请选择参数类型');
				return;
			}
			;
			$.ligerDialog.confirm('确实要删除' + currentNode.data.paramTypeName
					+ '及所有子类型吗!', function(yes) {
				if (yes) {
					$.ajax({
						async : false,
						type : "DELETE",
						url : '${ctx}/bione/variable/paramType/'
								+ currentNode.data.paramTypeNo,
						success : function() {
							BIONE.tip('删除成功');
						},
						error : function() {
							BIONE.tip('删除失败');
						}
					});
					//刷新表格和树
					grid.loadData();
					BIONE.refreshAsyncTreeNodes(treeObj, "id",
							currentNode.upId, "refresh");
					currentNode = null;
				}
			});
		}
		;

		// 状态显示,停/启用等
		function QYBZRenderSts(rowdata) {
			if (rowdata.userSts == '1') {
				return "启用";
			} else if (rowdata.userSts == '0') {
				return "停用";
			} else {
				return rowdata.userSts;
			}
		}
		//参数类型标志转名称
		function QYBZRenderParTypeName(rowdata) {
			return BIONE.paramTransformer(rowdata.paramTypeNo,
					'${ctx}/bione/common/getParamTypeName');
		}
	});
</script>
</head>
<body>
	<div id="template.left.up.icon">
		<img src="${ctx }/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">
		<span style="font-size: 12">参数类型信息</span>
	</div>
</body>
</html>