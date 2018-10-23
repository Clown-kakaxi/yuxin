<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
	//全局变量
	var grid;
	var gridLoadOver = false;

	$(function() {
		var parent = window.parent;
		var datasetObj = parent.datasetObj;

		//渲染grid
		ligerGrid();

		//渲染GRID
		function ligerGrid() {
			//初始化tip
			$("#tipContainerDiv")
					.prepend(
							"<div style='width:24px;float:left;height:16px;background:url(${ctx}/images/classics/icons/comment_yellow.gif) no-repeat' />");
			var gridHeight = $("#center").height() - 5
					- $("#tipMainDiv").height() - 6;
			parent.colGrid = grid = $("#maingrid")
					.ligerGrid(
							{
								width : "99%",
								height : gridHeight,
								columns : [
										{
											display : "字段名",
											name : "enName",
											width : '18%',
											isSort : false,
											editor : {
												type : 'text'
											}
										},
										{
											display : "中文名称",
											width : '18%',
											name : "cnName",
											editor : {
												type : 'text'
											},
											isSort : false
										},
										{
											display : "类型",
											name : "fieldType",
											width : '8%',
											isSort : false,
											editor : {
												type : 'select',
												data : [ {
													'id' : 'text',
													'text' : '文本'
												}, {
													'id' : 'number',
													'text' : '数字'
												}, {
													'id' : 'date',
													'text' : '日期'
												}, {
													'id' : 'object',
													'text' : '对象'
												} ]
											},
											render : function(row) {
												switch (row.fieldType) {
												case "text":
													return "文本";
												case "number":
													return "数字";
												case "date":
													return "日期";
												case "object":
													return "对象";
												default:
													return "未知";
												}
											}
										},
										{
											display : "长度",
											name : "length",
											width : '8%',
											isSort : false,
											editor : {
												type : 'int'
											}
										},
										{
											display : "精度",
											name : "precision",
											width : '8%',
											isSort : false,
											editor : {
												type : 'int'
											}
										},
										{
											display : "可空",
											name : "isNullable",
											width : '7%',
											isSort : false,
											render : function(row) {
												return "<input name='isNullCheck' style='margin-top:5px;' onclick='checkFunc(\"nullable\",\""
														+ row.__id
														+ "\",this);' type='checkbox' "
														+ ((row.isNullable == "1") ? "checked='checked'"
																: "") + "/>";
											}
										},
										{
											display : "主键",
											name : "isPk",
											width : '7%',
											isSort : false,
											render : function(row) {
												return "<input name='isPkCheck' style='margin-top:5px;' onclick='checkFunc(\"pk\",\""
														+ row.__id
														+ "\",this);' type='checkbox' "
														+ ((row.isPk == "1") ? "checked='checked'"
																: "") + "/>";
											}
										},
										{
											display : "查询项",
											name : "isQueryField",
											width : '8%',
											isSort : false,
											render : function(row) {
												return "<input name='isPkCheck' style='margin-top:5px;' onclick='checkFunc(\"queryField\",\""
														+ row.__id
														+ "\",this);' type='checkbox' "
														+ ((row.isQueryField == "1") ? "checked='checked'"
																: "") + "/>";
											}
										},{
											display : "运算符",
											name : "queryLogic",
											width : '6%',
											isSort : false,
											editor : {
												type : 'select',
												data : [  {
													'id' : '=',
													'text' : '='
												},{
													'id' : '>',
													'text' : '>'
												}, {
													'id' : '>=',
													'text' : '>='
												},{
													'id' : '<',
													'text' : '<'
												},  {
													'id' : '<=',
													'text' : '<='
												}, {
													'id' : 'like',
													'text' : 'like'
												} ]
											}
										}
								/*,
								{
									display : "操作",
									name : "isPk",
									width : '15%',
									isSort : false,
									render : function(row) {
										return "<input type='button' value='上移' onclick='orderFunc(\"up\",\""
												+ row.__id
												+ "\");' />&nbsp;&nbsp;<input type='button' value='下移' onclick='orderFunc(\"down\",\""
												+ row.__id + "\");' />";
									}
								}*/
								],
								onSelectRow : function(rowdata, rowindex) {
									$("#txtrowindex").val(rowindex);
								},
								dataAction : 'server',//从后台获取数据
								method : 'post',
								url : "${ctx}/bione/mtool/dataset/colList.json?d="
										+ new Date(),
								delayLoad : true,//初始化时不加载数据
								enabledEdit : true,
								checkbox : true,
								rownumbers : true,
								usePager : false,
								alternatingRow : false,//奇偶行效果行
								colDraggable : false,
								rowDraggable : false,
								clickToEdit : true,
								isChecked : function(row) {
									return (row.isUse == "1") ? true : false;
								},
								onBeforeEdit : function(e) {
									var colName = e.column.columnname;
									if (datasetObj.dsType == "01") {
										return (colName != "enName" && colName != "fieldType");
									} else {
										return true;
									}

								},
								onLoading : function() {
									gridLoadOver = false;
								},
								onLoaded : function() {
									gridLoadOver = true;
								},
								toolbar : {}
							});

			colDrag(grid);
			//初始化按钮
			var btns = [ {
				text : "重置",
				icon : "refresh",
				click : function() {
					grid.loadData();
				},
				operNo : "col_refresh"
			}, {
				text : "增加字段",
				icon : "add",
				click : col_add,
				operNo : "col_add"
			}, {
				text : "删除字段",
				icon : "delete",
				click : col_delete,
				operNo : "col_delete"
			} ];
			BIONE.loadToolbar(grid, btns, function() {
			});
			//添加一个编辑行
			function col_add() {
				grid.addEditRow({
					enName : "",
					cnName : "",
					fieldType : "text",
					length : 100,
					precision : 0,
					isNullable : "1",
					isPk : "0"
				});
			}

			//删除选中行
			function col_delete() {
				grid.deleteSelectedRow();
			}

			//加载数据
			grid.set('parms', {
				from : datasetObj.from,
				datasetId : datasetObj.datasetId,
				dsId : datasetObj.dsId,
				table : datasetObj.table,
				d : new Date().getTime()
			});
			grid.loadData();

			//按钮可用
			parent.canAddOrDel = function(flag) {
				if (flag) {
					$(
							"#maingrid .l-panel-topbar .l-toolbar-item[toolbarid='col_add']")
							.show();
					$(
							"#maingrid .l-panel-topbar .l-toolbar-item[toolbarid='col_delete']")
							.show();
					$("#maingrid .l-panel-topbar .l-toolbar-separator").show();
				} else {
					$(
							"#maingrid .l-panel-topbar .l-toolbar-item[toolbarid='col_add']")
							.hide();
					$(
							"#maingrid .l-panel-topbar .l-toolbar-item[toolbarid='col_delete']")
							.hide();
					$("#maingrid .l-panel-topbar .l-toolbar-separator").hide();
					$("#maingrid .l-panel-topbar .l-toolbar-separator").first()
							.show();
				}
			};
			if (datasetObj.dsType == "02") {
				parent.canAddOrDel(true);
			} else {
				parent.canAddOrDel(false);
			}
		}

		//添加表单按钮
		var btns = [ {
			text : "取消",
			onclick : function() {
				if (gridLoadOver) {
					window.parent.closeDsetBox();
				} else {
					BIONE.tip("努力加载中，请稍后。");
					return;
				}
			}
		}, {
			text : "保存",
			onclick : col_save
		}, {
			text : "上一步",
			onclick : parent.last
		} ];
		BIONE.addFormButtons(btns);

		//保存
		function col_save() {
			if (!gridLoadOver) {
				BIONE.tip("努力加载中，请稍后。");
				return;
			}
			grid.endEdit(); //结束编辑
			var rows = grid.getData();
			if (rows.length == 0) {
				BIONE.tip("数据集至少配置一个数据项。");
				return;
			}
			var checkCols = [];
			for ( var i = 0, l = rows.length; i < l; i++) {
				if (!rows[i].enName) {
					BIONE.tip("数据项字段名不能为空。");
					return;
				}
				if (!contains(checkCols, rows[i].enName.toUpperCase())) {
					checkCols.push(rows[i].enName.toUpperCase());
				} else {
					BIONE.tip("数据项名称必须唯一。");
					return;
				}
			}

			var sRows = grid.getSelectedRows();
			var sCols = [];
			if (sRows) {
				for ( var i = 0, l = sRows.length; i < l; i++) {
					sCols.push(sRows[i].enName.toUpperCase());
				}
			}
			var cols = [];
			for ( var i = 0, l = rows.length; i < l; i++) {
				var ft = rows[i].fieldType;
				var ql = rows[i].queryLogic;
				if(rows[i].isQueryField=="1"){
				if(ft=="text"){
					if(ql!="="&&ql!="like"){
						BIONE.tip("文本型查询项只支持[=,like]");
						return;
					}
				}else{
					if(ql=="like"){
						BIONE.tip("非文本型查询项不支持[like]");
						return;
					}
				}}
				var col = {
					isUse : (contains(sCols, rows[i].enName.toUpperCase())) ? "1"
							: "0",
					orderNo : (i + 1),
					enName : rows[i].enName.toUpperCase(),
					cnName : rows[i].cnName,
					fieldType : rows[i].fieldType,
					length : rows[i].length,
					precision : rows[i].precision,
					isNullable : rows[i].isNullable=="1"?"1":"0",
					isPk : rows[i].isPk=="1"?"1":"0",
					isQueryField : rows[i].isQueryField=="1"?"1":"0",
					queryLogic :  rows[i].queryLogic
				};
				cols.push(col);
			}
			var datacolsJsonStr = '{fields:' + JSON2.stringify(cols) + '}';
			$.ajax({
				type : "POST",
				url : "${ctx}/bione/mtool/dataset/save",
				dataType : 'json',
				data : {
					datasetId : datasetObj.datasetId,
					catalogId : datasetObj.catalogId,
					dsId : datasetObj.dsId,
					datasetName : datasetObj.datasetName,
					dsType : datasetObj.dsType,
					tableEname : datasetObj.table,
					querySql : datasetObj.querySql,
					remark : datasetObj.remark,
					datacolsJsonStr : datacolsJsonStr
				},
				success : function() {
					window.parent.parent.BIONE.tip("保存成功！");
					//刷新grid
					window.parent.parent.datasetGrid.loadData();
					window.parent.BIONE.closeDialog("datasetBox");
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					BIONE.tip('保存失败,错误信息:' + textStatus);
				}
			});
		}

		//包含测试
		function contains(array, string) {
			if (array) {
				for ( var i = 0, l = array.length; i < l; i++) {
					if (array[i] == string)
						return true;
				}
			}
			return false;
		}

	});

	//复选框操作
	function checkFunc(type, rowId, e) {
		var row = grid.getRow(rowId);
		if (type == "nullable") {
			row.isNullable = e.checked ? "1" : "0";
		} else if(type=="pk"){
			row.isPk = e.checked ? "1" : "0";
		}else if(type=="queryField"){
			row.isQueryField = e.checked ? "1" : "0";
		}
	}
	//排序操作
	/*	function orderFunc(type, rowId) {
	 var row = grid.getRow(rowId);
	 if (type == "up") {
	 grid.up(row);
	 } else {
	 grid.down(row);
	 }
	 }
	 */
	 
	 //拖拽支持
		var colDrag = function(grid){
			 var g = grid, p = grid.options;
                g.rowDroptip = $("<div class='l-drag-rowdroptip' style='display:none'></div>").appendTo('body');
                g.gridbody.add(g.f.gridbody).ligerDrag({ revert: true, animate: false,
                    proxyX: 0, proxyY: 0,
                    proxy: function (draggable, e)
                    {
                        var src = g._getSrcElementByEvent(e);
                        if (src.row)
                        {
                            var content = p.draggingMessage.replace(/{count}/, draggable.draggingRows ? draggable.draggingRows.length : 1);
                            if (p.rowDraggingRender)
                            {
                                content = p.rowDraggingRender(draggable.draggingRows, draggable, g);
                            }
                            var proxy = $("<div class='l-drag-proxy' style='display:none'><div class='l-drop-icon l-drop-no'></div>" + content + "</div>").appendTo('body');
                            return proxy;
                        }
                    },
                    onRevert: function () { return false; },
                    onRendered: function ()
                    {
                        this.set('cursor', 'default');
                        g.children[this.id] = this;
                    },
                    onStartDrag: function (current, e)
                    {
                        if (e.button == 2) return false;
                        if (g.colresizing) return false;
                        if (!g.columns.length) return false;
                        this.set('cursor', 'default');
                        var src = g._getSrcElementByEvent(e);
                        if (!src.cell || !src.data || src.checkbox) return false;
                        var ids = src.cell.id.split('|');
                        var column = g._columns[ids[ids.length - 1]];
                        if (src.rownumberscell || src.detailcell || src.checkboxcell || column == g.columns[0]|| column == g.columns[1])
                        {
                            //if (g.enabledCheckbox())
                            //{
                            //    this.draggingRows = g.getSelecteds();
                            //    if (!this.draggingRows || !this.draggingRows.length) return false;
                           // }
                            //else
                           // {
                                this.draggingRows = [src.data];
                            //}
                            this.draggingRow = src.data;
                            this.set('cursor', 'move');
                            g.rowdragging = true;
                            this.validRange = {
                                top: g.gridbody.offset().top,
                                bottom: g.gridbody.offset().top + g.gridbody.height(),
                                left: g.grid.offset().left - 10,
                                right: g.grid.offset().left + g.grid.width() + 10
                            };
                        }
                        else
                        {
                            return false;
                        }
                    },
                    onDrag: function (current, e)
                    {
                        var rowdata = this.draggingRow;
                        if (!rowdata) return false;
                        var rows = this.draggingRows ? this.draggingRows : [rowdata];
                        if (g.colresizing) return false;
                        if (g.rowDropIn == null) g.rowDropIn = -1;
                        var pageX = e.pageX;
                        var pageY = e.pageY;
                        var visit = false;
                        var validRange = this.validRange;
                        if (pageX < validRange.left || pageX > validRange.right
                            || pageY > validRange.bottom || pageY < validRange.top)
                        {
                            g.rowDropIn = -1;
                            g.rowDroptip.hide();
                            this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes l-drop-add").addClass("l-drop-no");
                            return;
                        }
                        for (var i in g.rows)
                        {
                            var rd = g.rows[i];
                            var rowid = rd['__id'];
                            if (rowdata == rd) visit = true;
                            if ($.inArray(rd, rows) != -1) continue;
                            var isAfter = visit ? true : false;
                            if (g.rowDropIn != -1 && g.rowDropIn != rowid) continue;
                            var rowobj = g.getRowObj(rowid);
                            var offset = $(rowobj).offset();
                            var range = {
                                top: offset.top - 4,
                                bottom: offset.top + $(rowobj).height() + 4,
                                left: g.grid.offset().left,
                                right: g.grid.offset().left + g.grid.width()
                            };
                            if (pageX > range.left && pageX < range.right && pageY > range.top && pageY < range.bottom)
                            {
                                var lineTop = offset.top;
                                if (isAfter) lineTop += $(rowobj).height();
                                g.rowDroptip.css({
                                    left: range.left,
                                    top: lineTop,
                                    width: range.right - range.left
                                }).show();
                                g.rowDropIn = rowid;
                                g.rowDropDir = isAfter ? "bottom" : "top";
                                if (p.tree && pageY > range.top + 5 && pageY < range.bottom - 5)
                                {
                                    this.proxy.find(".l-drop-icon:first").removeClass("l-drop-no l-drop-yes").addClass("l-drop-add");
                                    g.rowDroptip.hide();
                                    g.rowDropInParent = true;
                                }
                                else
                                {
                                    this.proxy.find(".l-drop-icon:first").removeClass("l-drop-no l-drop-add").addClass("l-drop-yes");
                                    g.rowDroptip.show();
                                    g.rowDropInParent = false;
                                }
                                break;
                            }
                            else if (g.rowDropIn != -1)
                            {
                                g.rowDropIn = -1;
                                g.rowDropInParent = false;
                                g.rowDroptip.hide();
                                this.proxy.find(".l-drop-icon:first").removeClass("l-drop-yes  l-drop-add").addClass("l-drop-no");
                            }
                        }
                    },
                    onStopDrag: function (current, e)
                    {
                        var rows = this.draggingRows;
                        g.rowdragging = false;
                        for (var i = 0; i < rows.length; i++)
                        {
                            var children = rows[i].children;
                            if (children)
                            {
                                rows = $.grep(rows, function (node, i)
                                {
                                    var isIn = $.inArray(node, children) == -1;
                                    return isIn;
                                });
                            }
                        }
                        if (g.rowDropIn != -1)
                        {
                            if (p.tree)
                            {
                                var neardata, prow;
                                if (g.rowDropInParent)
                                {
                                    prow = g.getRow(g.rowDropIn);
                                }
                                else
                                {
                                    neardata = g.getRow(g.rowDropIn);
                                    prow = g.getParent(neardata);
                                }
                                g.appendRange(rows, prow, neardata, g.rowDropDir != "bottom");
                                g.trigger('rowDragDrop', {
                                    rows: rows,
                                    parent: prow,
                                    near: neardata,
                                    after: g.rowDropDir == "bottom"
                                });
                            }
                            else
                            {
                                g.moveRange(rows, g.rowDropIn, g.rowDropDir == "bottom");
                                g.trigger('rowDragDrop', {
                                    rows: rows,
                                    parent: prow,
                                    near: g.getRow(g.rowDropIn),
                                    after: g.rowDropDir == "bottom"
                                });
                            }

                            g.rowDropIn = -1;
                        }
                        g.rowDroptip.hide();
                        this.set('cursor', 'default');
                    }
                });
            };
</script>
<title>Insert title here</title>
</head>
<body>
	<div id="template.center">
		<div id="tipMainDiv"
			style="width: 99.8%; margin: 0 auto; overflow: hidden; position: relative; border: 1px solid gray; padding-top: 1px; padding-bottom: 1px;">
			<div id="tipContainerDiv"
				style="padding: 5px 2px; background: #fffee6; color: #8f5700;">
				<div id="tipAreaDiv">
					tips : 表格中所有数据项将被保存，但只有勾选的数据项状态为可用。 (拖拽行序号区可改变字段顺序)<br />
				</div>
			</div>
		</div>
		<div style="height: 2px;"></div>
		<div id="maingrid"></div>
	</div>
</body>
</html>