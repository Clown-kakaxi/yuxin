<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
	//扩展JQuery以增强textarea编辑功能
	jQuery.extend({
		/** 
		 * 清除当前选择内容 
		 */
		unselectContents : function() {
			if (window.getSelection)
				window.getSelection().removeAllRanges();
			else if (document.selection)
				document.selection.empty();
		}
	});
	jQuery.fn
			.extend({
				/** 
				 * 选中内容 
				 */
				selectContents : function() {
					$(this)
							.each(
									function(i) {
										var node = this;
										var selection, range, doc, win;
										if ((doc = node.ownerDocument)
												&& (win = doc.defaultView)
												&& typeof win.getSelection != 'undefined'
												&& typeof doc.createRange != 'undefined'
												&& (selection = window
														.getSelection())
												&& typeof selection.removeAllRanges != 'undefined') {
											range = doc.createRange();
											range.selectNode(node);
											if (i == 0) {
												selection.removeAllRanges();
											}
											selection.addRange(range);
										} else if (document.body
												&& typeof document.body.createTextRange != 'undefined'
												&& (range = document.body
														.createTextRange())) {
											range.moveToElementText(node);
											range.select();
										}
									});
				},
				/** 
				 * 初始化对象以支持光标处插入内容 
				 */
				setCaret : function() {
					if (!$.browser.msie)
						return;
					var initSetCaret = function() {
						var textObj = $(this).get(0);
						textObj.caretPos = document.selection.createRange()
								.duplicate();
					};
					$(this).click(initSetCaret).select(initSetCaret).keyup(
							initSetCaret);
				},
				/** 
				 * 在当前对象光标处插入指定的内容 
				 */
				insertAtCaret : function(textFeildValue) {
					var textObj = $(this).get(0);
					if (document.all && textObj.createTextRange
							&& textObj.caretPos) {
						var caretPos = textObj.caretPos;
						caretPos.text = caretPos.text
								.charAt(caretPos.text.length - 1) == '' ? textFeildValue
								+ ''
								: textFeildValue;
					} else if (textObj.setSelectionRange) {
						var rangeStart = textObj.selectionStart;
						var rangeEnd = textObj.selectionEnd;
						var tempStr1 = textObj.value.substring(0, rangeStart);
						var tempStr2 = textObj.value.substring(rangeEnd);
						textObj.value = tempStr1 + textFeildValue + tempStr2;
						textObj.focus();
						var len = textFeildValue.length;
						textObj.setSelectionRange(rangeStart + len, rangeStart
								+ len);
						textObj.blur();
					} else {
						textObj.value += textFeildValue;
					}
				}
			});
	//处理不同数据源类型的验证
	jQuery.validator.addMethod("tableVLD", function(value, element) {
		var dsType = $("#mainform input[name='dsType']").val();
		var table_box = $("#mainform input[name='table_box']").val();
		if (dsType == "01") {
			return (table_box != null && table_box != "") ? true : false;
		} else {
			return true;
		}
	}, "请选择一个数据库表");
	//扩展sql基本语法验证
	jQuery.validator.addMethod("queryVLD", function(value, element) {
		var dsType = $("#mainform input[name='dsType']").val();
		if (dsType == "02") {
			if (value == null || value == "") {
				return false;
			}
			var valueTmp = $.trim(value).toLowerCase();
			//匹配形如:select a from dual的sql格式
			var regExp = /^select[\s][^;]+[\s]from[\s][^;]+$/;
			return regExp.test(valueTmp);
		} else {
			return true;
		}
	}, "SQL格式不正确");

	var mainform;

	$(function() {
		var parent = window.parent;
		var datasetObj = parent.datasetObj;
		var datasetId = datasetObj.datasetId;
		var groupicon = "${ctx}/images/classics/icons/communication.gif";

		//渲染表单
		mainform = $("#mainform");
		mainform
				.ligerForm({
					inputWidth : 160,
					labelWidth : 80,
					space : 30,
					fields : [
							{
								name : "datasetId",
								type : "hidden"
							},
							{
								name : "catalogId",
								type : "hidden"
							},
							{
								display : "所属目录",
								name : "catalogName",
								newline : true,
								type : "text",
								width : 215,
								group : "数据集管理",
								groupicon : groupicon
							},
							{
								display : "数据集名称",
								name : "datasetName",
								newline : false,
								type : "text",
								width : 215,
								validate : {
									required : true,
									maxlength : 100
								}
							},
							{
								display : "数据源",
								name : "ds_box",
								newline : true,
								type : "select",
								width : 215,
								validate : {
									required : true,
									messages : {
										required : "请选择一个数据源。"
									}
								},
								options : {
									valueFieldID : "dsId",
									url : "${ctx}/bione/mtool/dataset/dsList.json",
									onBeforeSelect : function() {
										$("#mainform input[name='table_box']")
												.val("");
									}
								}
							},
							{
								display : "类型",
								name : "dsType_box",
								newline : false,
								type : "select",
								width : 215,
								validate : {
									maxlength : 100
								},
								options : {
									valueFieldID : "dsType",
									data : [ {
										text : '数据库表',
										id : '01'
									}, {
										text : '标准SQL',
										id : '02'
									} ],
									onBeforeSelect : selectDsType
								}
							},
							{
								display : "数据库表",
								name : "table_box",
								newline : true,
								type : "select",
								width : 540,
								validate : {
									tableVLD : true
								},
								options : {
									url : "${ctx}/bione/mtool/dataset/tables.json",
									onBeforeOpen : function() {
										var dsId = $(
												"#mainform input[name='dsId']")
												.val();
										if (!dsId) {
											BIONE.tip("请选择一个数据源。");
											return false;
										}
										var url = "${ctx}/bione/mtool/dataset/tablePage?dsId="
												+ dsId;
										dialog = window.parent.BIONE
												.commonOpenDialog('请选择物理表',
														"selectGrid", "700",
														"350", url);
										return false;
									}
								}
							}, {
								display : "SQL语句",
								name : "querySql",
								newline : true,
								type : "textarea",
								width : 440,
								validate : {
									queryVLD : true,
									maxlength : 500
								}
							}, {
								display : "描述",
								name : "remark",
								newline : true,
								type : "textarea",
								width : 543,
								validate : {
									maxlength : 500
								}
							} ]

				});

		jQuery.metadata.setType("attr", "validate");
		BIONE.validate("#mainform");
		//系统变量
		$("#querySql")
				.parent()
				.next()
				.after(
						'<li style="text-align: left;"><div id="systVarBtn" class="l-dialog-btn" style="float:left;"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner">系统变量</div></div></li>');
		$("#systVarBtn").click(
				function() {
					window.parent.BIONE.commonOpenDialog("系统变量", "selectGrid",
							"600", "380",
							"${ctx}/bione/mtool/dataset/sysvarPage");
				});
		$("#querySql").parents("li").hide();
		$("li:contains('SQL语句')").hide();
		$("#systVarBtn").parent().hide();
		$.ligerui.get('dsType_box').selectValue("01");
		$("#mainform [name='querySql']").setCaret();//增强textarea功能，支持光标处插入字符串

		//表单赋值
		$("#mainform input[name='catalogId']").val(datasetObj.catalogId);
		$("#mainform input[name='catalogName']").val(datasetObj.catalogName)
				.css("color", "#999").attr("readOnly", "readOnly");

		//修改时初始化表单
		if (datasetId != "" && datasetId != null) {
			$.ajax({
				type : "POST",
				url : "${ctx}/bione/mtool/dataset/datasetInfo.json?datasetId="
						+ datasetId,
				success : function(model) {
					datasetObj.dsType = model.dsType;
					datasetObj.dsId = model.dsId;
					datasetObj.table = model.tableEname==null?"":model.tableEname;

					$("#mainform input[name='datasetName']").val(
							model.datasetName);
					$.ligerui.get('ds_box').selectValue(model.dsId);
					$.ligerui.get('dsType_box').selectValue(model.dsType);
					selectDsType(model.dsType);
					$("#mainform input[name='table_box']")
							.val(model.tableEname);
					$("#mainform textarea[name='querySql']")
							.val(model.querySql);
					$("#mainform textarea[name='remark']").val(model.remark);
				}
			});
		}

		//添加按钮
		var btns = [ {
			text : "取消",
			onclick : function() {
				window.parent.closeDsetBox();
			}
		}, {
			text : "下一步",
			onclick : f_save
		} ];
		BIONE.addFormButtons(btns);

		//保存
		function f_save() {
			if ($("#mainform").valid()) {
				var dsType = $("#mainform input[name='dsType']").val();
				var dsId = $("#mainform input[name='dsId']").val();
				var table = $("#mainform input[name='table_box']").val();
				//决定是否刷新
				if (datasetObj.dsType != dsType || datasetObj.dsId != dsId
						|| datasetObj.table != table) {
					datasetObj.refresh = true;
				} else {
					datasetObj.refresh = false;
				}
				//决定数据来源
				if (!datasetObj.dsId) {
					//新增
					datasetObj.from = (dsType == "01") ? "table" : "";
				} else {
					//修改
					if (datasetObj.firstEdit && datasetObj.dsType == dsType
							&& datasetObj.table == table) {
						datasetObj.from = "dataset";
					} else {
						firstEdit = false;
						datasetObj.from = (dsType == "01") ? "table" : "";
					}
				}
				//数据集基本信息缓存
				datasetObj.dsType = dsType;
				datasetObj.dsId = dsId;
				datasetObj.table = (datasetObj.dsType == "01") ? table : "";
				datasetObj.datasetName = $(
						"#mainform input[name='datasetName']").val();
				datasetObj.querySql = (datasetObj.dsType == "01") ? "" : $(
						"#mainform textarea[name='querySql']").val();
				datasetObj.remark = $("#mainform textarea[name='remark']")
						.val();
				parent.next();
			} else {
				window.parent.BIONE.showInvalid(BIONE.validator);
			}
		}

		//数据源类型选择触发函数
		function selectDsType(dsType) {
			var b = $("#mainform input[name='table_box']").val();
			var sql = $("#mainform textarea[name='querySql']").val();
			$("#mainform input[name='table_box']").val("---");
			$("#mainform textarea[name='querySql']").val("select a from b");
			$("#mainform").valid();
			$("#mainform input[name='table_box']").val(b);
			$("#mainform textarea[name='querySql']").val(sql);
			if (dsType == "01") {
				$("#querySql").parents("li").hide();
				$("#systVarBtn").parent().hide();
				$("li:contains('SQL语句')").hide();
				$("#table_box").parents("li").show();
				$("li:contains('数据库表')").show();
			} else {
				$("#table_box").parents("li").hide();
				$("li:contains('数据库表')").hide();
				$("#querySql").parents("li").show();
				$("li:contains('SQL语句')").show();
				$("#systVarBtn").parent().show();
			}
		}
	});
</script>
<title>Insert title here</title>
</head>
<body >
	<div id="template.center">
		<form id="mainform"
			action="${ctx}/dataquality/rulemanage/meta-rule-grp/saveRuleGrpType"
			method="POST"></form>
	</div>
</body>
</html>