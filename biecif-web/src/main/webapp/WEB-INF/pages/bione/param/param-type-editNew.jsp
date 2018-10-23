<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
    var groupicon = "${ctx}/images/classics/icons/communication.gif";
    var upNo = "${upNo}";
    var upParamType = "${upParamType}";
    $(function() {
	$("#mainform").ligerForm({
	    fields : [ {
		group : "参数类型",
		groupicon : groupicon,
		display : '参数类型标识',
		name : 'paramTypeNo',
		newline : true,
		type : 'text',
		validate : {
		    required : true,
			checkAttrName : true,//a-z,A-Z,_开头的合法变量名验证
		    maxlength : 32,
		    remote : "${ctx}/bione/variable/paramType/testParamTypeNo",
		    messages : {
			remote : "参数类型标识已存在"
		    }
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
		type : 'text',
		validate : {
		    required : true,
		    maxlength : 100
		},
		render : QYBZRenderUpType
	    }, {
		display : '备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',
		name : 'remark',
		newline : true,
		width : 503,
		validate : {
		    maxlength : 500
		},
		type : 'textarea'
	    } ]
	});
	$("#mainform input[name=upNo]").val(upNo);
	$("#mainform input[name=upParamType]").val(upParamType);
	$("#mainform input[name=upNo]").attr("readonly", "readonly");
	$("#mainform input[name=upParamType]").attr("readonly", "readonly");

	jQuery.metadata.setType("attr", "validate");
	BIONE.validate($("#mainform"));
	var buttons = [];
	buttons.push({
	    text : '取消',
	    onclick : function() {
		BIONE.closeDialog("paramTypeAddWin");
	    }
	});
	buttons.push({
	    text : '保存',
	    onclick : f_save
	});

	BIONE.addFormButtons(buttons);
    });
    function f_save() {
	var treeObj = window.parent.treeObj;
	BIONE.submitForm($("#mainform"), function() {
	    //刷新左边树
	    BIONE.refreshAsyncTreeNodes(treeObj, "id", upNo, "refresh");
	    BIONE.closeDialogAndReloadParent("paramTypeAddWin", "maingrid",
		    "添加成功");
	}, function() {
	    BIONE.closeDialog("paramTypeAddWin", "添加失败");
	});

    }
    //获取上级参数类型
    function QYBZRenderUpType(rowdata) {
	return BIONE.paramTransformer(rowdata.upNo,
		'${ctx}/bione/common/getParamName');
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