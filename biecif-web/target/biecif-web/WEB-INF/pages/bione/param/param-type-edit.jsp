<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
    var groupicon = "${ctx}/images/classics/icons/communication.gif";
    var upParamType = "${upParamType}";
    $(function() {
	var mainform = $("#mainform").ligerForm({
	    fields : [ {
		name : 'paramTypeId',
		type : 'hidden'
	    }, {
		group : "参数类型",
		groupicon : groupicon,
		display : '参数类型标识',
		name : 'paramTypeNo',
		newline : true,
		type : 'text',
		validate : {
		    required : true,
		    maxlength : 32
		}
	    }, {
		display : '参数类型名称',
		name : 'paramTypeName',
		newline : false,
		type : 'text',
		validate : {
		    required : true,
		    maxlength : 100
		}
	    }, {
		display : '上级标识',
		name : 'upNo',
		type : 'hidden',
		validate : {
		    required : true,
		    maxlength : 32
		}
	    }, {
		display : '上级参数类型',
		name : "upParamType",
		newline : true,
		type : 'hidden'
	    }, {
		display : '备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',
		name : 'remark',
		newline : true,
		width : '503',
		validate : {
		    maxlength : 500
		},
		type : 'textarea'
	    } ]
	});

	BIONE.loadForm(mainform, {
	    url : "${ctx}/bione/variable/paramType/${id}"
	});
	$("#mainform input[name=upParamType]").val(upParamType);
	$("#mainform input[name=upParamType]").attr("readonly", "readonly");
	$("#mainform input[name=paramTypeNo]").attr("readonly", "readonly");
	$("#mainform input[name=upNo]").attr("readonly", "readonly");
	jQuery.metadata.setType("attr", "validate");
	BIONE.validate($("#mainform"));
	var buttons = [];
	buttons.push({
	    text : '取消',
	    onclick : function() {
		BIONE.closeDialog("paramTypeUpdateWin");
	    }
	});
	buttons.push({
	    text : '保存',
	    onclick : f_update
	});

	BIONE.addFormButtons(buttons);
    });

    function f_update() {
	BIONE.submitForm($("#mainform"), function() {
	    var treeObj = window.parent.treeObj;
	    //刷新左边树
	    BIONE.refreshAsyncTreeNodes(treeObj, "id", $(
		    "#mainform input[name=upNo]").attr("value"), "refresh");
	    BIONE.closeDialog("paramTypeUpdateWin", "修改成功");
	}, function() {
	    BIONE.closeDialog("paramTypeUpdateWin", "修改失败");
	});
    }
</script>
</head>
<body>
	<div id="template.center">
		<form name="mainform" method="post" id="mainform"
			action="${ctx}/bione/variable/paramType"></form>
	</div>
</body>
</html>