<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template3.jsp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var zTreeObj;
	var rootId;
	var rootName;
	var currentNode = null;
	
    var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var buttons = [];
	buttons.push({
		text : '关闭',
		onclick : closeCallBack
	});
	buttons.push({
		text : '保存',
		onclick : savedCallback
	});
	
	$(init);
	
	function init() {
		initRoot();
		beforeTree();
		initTree();
		initMenubar();
	}
	
	function initRoot() {
		rootId = "${id}";
		if (rootId.length != 0) {
			$.get('${ctx}/bione/admin/module/' + rootId, {}, function(
					returnedData, status) {
				rootName = returnedData.moduleName;
				$("#treeTitle").text(rootName);
			});
		}
	}

	// 组织树对象
	function beforeTree() {
		var setting = {
			data : {
				key : {
					name : 'text'
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "upId"
				}
			},
			callback : {
				onClick : zTreeOnClick
			}
		};
		zTreeObj = $.fn.zTree.init($("#tree"), setting);
	}

	// 获得树结点数据, 追加到树上
	function initTree() {
		$.ajax({
			cache : false,
			async : false,
			url : '${ctx}/bione/admin/func/' + rootId + '/list.json',
			success : function(result) {
				var id = null;
				$(result).each(function() {
					/* 	if(this.params.open) {
							this.open = true;
						} else {
							this.open = false;
						} */
					if (this.ischecked) {
						id = this.id;
					}
				});
				zTreeObj.addNodes(null, result, false);
				if (id != null && id != "") {
					zTreeObj.selectNode(currentNode = zTreeObj.getNodeByParam(
							"id", id));
					getCurrentInfo(currentNode);
				}
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}

	// init menu toolbar
	function initMenubar() {
		$("#treeToolbar").ligerTreeToolBar({
			items : [ {
				icon : 'add',
				text : '新增',
				click : addFunc
			}, {
				line : true
			}, {
				icon : 'delete',
				text : '删除',
				click : deleteCallback
			} ]
		});
	}

	// zTree onClick
	function zTreeOnClick(event, treeId, treeNode) {
		zTreeObj.expandNode(treeNode);		/*点击节点名称也展开节点*/
		currentNode = treeNode;
		getCurrentInfo(treeNode);
	}

	// 刷新树
	function refreshTree() {
		var rootTmp = zTreeObj.getNodeByParam("id", "0", null);
		if (rootTmp) {
			zTreeObj.removeNode(rootTmp);
		}
		initTree();
	}

	function getCurrentInfo(node) { //load current info
		if (node.id == "0")
			return false;
		if ($(".l-group l-group-hasicon").length == 0) //judge the form whether exist where id='mainform' 
			ligerFormNow();
		$("#mainform input[name]").val("");
		$("#mainform textarea[name]").val("");
		$("#mainform input[name=upFunc]").attr("disabled", "disabled");
		BIONE.loadForm($("#mainform"), {
			url : '${ctx}/bione/admin/func/' + node.id
		});
		// load upFunc
		$.ajax({
			type : 'get',
			url : '${ctx}/bione/admin/func/' + node.upId + '/up',
			dataType : 'json',
			success : function(data) {
				if (data && data.funcName != "")
					$("#mainform input[name='upFunc']").val(data.funcName);
				else
					$("#mainform input[name='upFunc']").val(rootName);
			},
			error : function() {
				$("#mainform input[name='upFunc']").val(rootName);
			}
		});
		$(".l-dialog-btn").remove();
		BIONE.addFormButtons(buttons); //generate the 'update' and 'delete' buttons
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
	}

	//创建表单结构 
	function ligerFormNow() {
		$("form").ligerForm({
			inputWidth : 160,
			labelWidth : 80,
			space : 30,
			fields : [ {
				name : "funcId",
				type : "hidden"
			}, {
				name : "upId",
				type : "hidden"
			}, {
				name : "moduleId",
				type : "hidden"
			}, {
				display : "功能名称",
				name : "funcName",
				newline : true,
				type : "text",
				group : "功能信息",
				groupicon : groupicon,
				validate : {
					required : true,
					maxlength : 50
				}
			}, {
				display : "顺序编号",
				name : "orderNo",
				newline : false,
				type : "text",
				validate : {
					number : true,
					required : true,
					maxlength : 5
				}
			},

			{
				display : "上级功能 ",
				name : "upFunc",
				newline : true,
				type : "text",
				validate : {
					required : true,
					maxlength : 32
				}
			}, {
				display : "导航路径",
				name : "navPath",
				newline : false,
				type : "text",
				validate : {
					maxlength : 100
				}
			},

			{
				display : "功能状态",
				name : "funcStsBox",
				newline : true,
				type : "select",
				options : {
					valueFieldID : "funcSts",
					data : [ {
						text : '启用',
						id : '1'
					}, {
						text : '停用',
						id : '0'
					} ]
				},
				validate : {
					required : true
				}
			},

			{
				display : "导航图标",
				name : "navIcon",
				newline : false,
				type : "select",
				comboboxName : "IconBoxID",
				options : {
					valueFieldID : "navIcon",
					url : "${ctx}/bione/admin/func/buildIconCombox.json",
					ajaxType : "get"
				},
				validate : {
					required : false
				}
			},

			{
				display : "备注",
				name : "remark",
				newline : true,
				type : "textarea",
				width : 434,
				attr : {
					style : "resize: none;"
				},
				validate : {
					maxlength : 250
				}
			} ]
		});
		$.ligerui.get("IconBoxID").openSelect({
			url : '${ctx}/bione/admin/func/selectIcon.html',
			dialogname : 'iconselector',
			title : '选择图标',
			comboxName : 'IconBoxID',
			dialogType : 'icon'
		});
	};

	//DELETE
	function deleteCallback() {
		if (currentNode) {
			if (currentNode.id != '0') {
				$.ligerDialog.confirm('确实要删除该记录吗?', function(yes) {
					if (yes) {
						var flag = false;
						$.ajax({
							async : false,
							cache : false,
							type : "DELETE",
							url : "${ctx}/bione/admin/func/" + currentNode.id
									+ "," + currentNode.upId,
							dataType : "script",
							success : function() {
								flag = true;
							}
						});
						if (flag == true) {
							BIONE.tip('删除成功');
							currentNode = null;
							refreshTree();
						} else {
							BIONE.tip('删除失败');
						}
					}
				});
			} else {
				BIONE.tip('此节点不能删除');
			}
		}
	};
	//SAVE
	function savedCallback() {
		BIONE.submitForm($("#mainform"), function(data) {
			BIONE.tip("保存成功");
			refreshTree();
		});
	};

	//CANCLE
	function closeCallBack() {
		BIONE.closeDialog("moduleFunc");
	};

	function addFunc() {
		if ($(".l-group l-group-hasicon").length == 0)
			ligerFormNow();

		$("#mainform input[name != 'upFunc']").removeAttr("disabled").css(
				"color", "black");
		$("#mainform input").val("");
		$.ligerui.get("funcStsBox").setValue('1');
		$("#mainform textarea[name]").removeAttr("readonly").val("").css(
				"color", "black");
		$("#mainform input[name='upFunc']").attr("disabled", "disabled");

		if (currentNode) {
			$("#mainform input[name=upFunc]").val(currentNode.text);
			$("#mainform input[name=upId]").val(currentNode.id);
		} else {
			$("#mainform input[name=upFunc]").val("模块树");
			$("#mainform input[name=upId]").val("0");
		}

		/* 	if($("#mainform input[name=upId]").val() == "0") {
				$("#mainform input[name=navPath]").attr("disabled", "disabled").val("");
			} */

		$("#mainform input[name='moduleId']").val(rootId);

		$(".l-dialog-btn").remove();
		BIONE.addFormButtons(buttons);
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
	}
</script>
</head>
<body>
	<div id="template.left.up.icon">
		<img src="${ctx}/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">
		<span id="treeTitle" style="font-size: 12">功能树</span>
	</div>
	<div id="template.right">
		<form id="mainform" action="${ctx}/bione/admin/func/" method="post"></form>
	</div>
</body>
</html>