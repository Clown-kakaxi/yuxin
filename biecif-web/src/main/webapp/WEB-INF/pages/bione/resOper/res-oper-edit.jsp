<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
    var groupicon = "${ctx}/images/classics/icons/communication.gif";
    var urlType = window.parent.urlType;//操作标志位
    var resDefNo = window.parent.resDefNo;
    var resNo = window.parent.resNo;
    var currentRow = window.parent.currentRow;
    var checkOperId = window.parent.checkOperId;//上级标识树标志位
    var checkOperNo;//修改时的验证标志位
    var selectOperId;//修改时,填充下拉框过滤掉选择的节点及其子节点
    //创建表单结构 
    var mainform;
    $(function() {
	var upid = "${upid}";
	var upno = "${upno}";
	if (urlType == "add") {
	    checkOperNo = "";
	    selectOperId = "";
	} else {
	    checkOperNo = currentRow.operNo;
	    selectOperId = currentRow.operId;
	}
	mainform = $("#mainform");
	mainform.ligerForm({
	    fields : [ {
		name : "operId",
		type : "hidden"
	    }, {
		display : "操作标识",
		name : "operNo",
		newline : true,
		type : "text",
		group : "资源操作信息",
		groupicon : groupicon,
		validate : {
		    maxlength : 32,
		    required : true,
		    specificSymbeol : true,
		    remote : {
			url : "${ctx}/bione/admin/resOper/checkedOperNo",
			type : "POST",
			data : {
			    urlType : urlType,
			    currentOperNo : checkOperNo
			}
		    },
		    messages : {
			required : "该字段不能为空",
			remote : "操作标识已存在"
		    }
		}
	    }, {
		display : "操作名称",
		name : "operName",
		newline : false,
		type : "text",
		validate : {
		    maxlength : 100,
		    required : true
		}
	    }, {
		display : "上级标识",
		name : "upNojia",
		newline : true,
		validate : {
		    required : true
		}
	    }, {
		name : "upNo",
		type : "hidden"
	    }, {
		display : "访问URL",
		name : "visitUrl",
		newline : false,
		type : "text",
		validate : {
		    maxlength : 100
		}
	    }, {
		display : "方法名称",
		name : "methodName",
		type : "text",
		newline : true,
		validate : {
		    maxlength : 100
		}
	    }, {
		display : "备注",
		name : "remark",
		newline : true,
		type : "textarea",
		width : 500,
		attr : {
		    style : "resize: none;"
		},
		validate : {
		    maxlength : 500
		}
	    }, {
		name : "resDefNo",
		type : "hidden"
	    }, {
		name : "resNo",
		type : "hidden"
	    } ]
	});
	//填充下拉框
	fillSelect();

	var buttons = [];
	buttons.push({
	    text : '取消',
	    onclick : f_close
	});
	buttons.push({
	    text : '保存',
	    onclick : f_save
	});
	BIONE.addFormButtons(buttons);

	jQuery.metadata.setType("attr", "validate");
	BIONE.validate("#mainform");//给表单加验证

	$("#mainform input[name= 'resDefNo']").val(resDefNo);
	$("#mainform input[name= 'resNo']").val(resNo);
	//修改时填充表单
	if (urlType == "modify") {
	    $
		    .ajax({
			url : "${ctx}/bione/admin/resOper/getUpOperNo.json",
			data : {
			    upNo : currentRow.upNo,
			    resDefNo : resDefNo,
			    resNo : resNo
			},
			success : function(data) {
			    $("#mainform input[name= 'operId']").val(
				    currentRow.operId);
			    $("#mainform input[name= 'operNo']").val(
				    currentRow.operNo);
			    $("#mainform input[name= 'operName']").val(
				    currentRow.operName);
			    $("#mainform input[name= 'upNojia']").val(
				    data.upOperNo);
			    $("#mainform input[name= 'upNo']").val(
				    currentRow.upNo);
			    $("#mainform input[name= 'visitUrl']").val(
				    currentRow.visitUrl);
			    $("#mainform input[name= 'methodName']").val(
				    currentRow.methodName);
			    $("#mainform [name= 'remark']").val(
				    currentRow.remark);
			}
		    });
	} else {
	    if (upid) {
		$("#mainform input[name= 'upNo']").val(upid);
		$("#mainform input[name= 'upNojia']").val(upno);
	    } else {
		$("#mainform input[name= 'upNo']").val("0");
		$("#mainform input[name= 'upNojia']").val("根目录");
	    }
	}
    });
    function f_save() {
	BIONE.submitForm($("#mainform"), function() {
	    var treePage = window.parent.grid;
	    treePage.loadServerData({
		"resDefNo" : resDefNo,
		"resNo" : resNo
	    }, null);
	    BIONE.closeDialog("resOperChange", "保存成功");
	}, function() {
	    BIONE.closeDialog("resOperChange", "保存失败");
	});
    }

    function f_close() {
	BIONE.closeDialog("resOperChange");
    }

    //填充下拉框
    function fillSelect() {
	$("#mainform [name='upNojia']").attr("id", "upNojia" + 0).removeAttr(
		"ligeruiid").attr("ltype", "select");
	$("#upNojia" + 0)
		.ligerComboBox(
			{
			    width : 220,
			    selectBoxWidth : 180,
			    selectBoxHeight : 180,
			    type : "select",
			    treeLeafOnly : false,
			    tree : {
				checkbox : false,
				nodeWidth : 136,
				url : "${ctx}/bione/admin/resOper/getResOperTree.json?resDefNo="
					+ resDefNo
					+ "&resNo="
					+ resNo
					+ "&operId=" + selectOperId,
				idFieldName : 'id',
				parentIDFieldName : 'upId',
				isLeaf : function(node) {
				    if (node.children != null
					    && node.children.length != 0) {
					return true;
				    } else {
					return false;
				    }
				},
				onBeforeSelect : function(node) {
				    $("#mainform input[name= 'upNo']").val(
					    node.data.id);

				}
			    }
			});
    }
</script>
</head>
<body>
	<div id="template.center">
		<form id="mainform" action="${ctx}/bione/admin/resOper/" method="post"></form>
	</div>
</body>
</html>