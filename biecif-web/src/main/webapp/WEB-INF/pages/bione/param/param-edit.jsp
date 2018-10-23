<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
    $(function() {
	var paramTypeName = "${paramTypeName}";
	var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var mainform = $("#mainform").ligerForm({
	    fields : [ {
		name : 'paramId',
		type : 'hidden'
	    }, {
		display : '参数类型标识',
		name : 'paramTypeNo',
		newline : true,
		type : 'hidden'
	    }, {
		group : "参数值",
		groupicon : groupicon,
		display : '参数类型',
		name : 'paramTypeName',
		newline : true,
		type : 'text'
	    }, {
		display : '参数名称',
		name : 'paramName',
		newline : false,
		type : 'text',
		validate : {
		    required : true,
		    maxlength : 100
		}
	    }, {
		display : '参数值',
		name : 'paramValue',
		newline : true,
		type : 'text',
		validate : {
		    required : true,
		    maxlength : 500,
		    remote : "${ctx}/bione/variable/param/testParamValue?paramTypeId=${paramTypeId}&id=${id}",
		    messages : {
			remote : "参数值已存在"
		    }
		}
	    }, {
		display : '顺序标识',
		name : 'orderNo',
		newline : false,
		type : 'hidden',
		validate : {
		    required : true,
		    number : "请输入合法的数字",
		    maxlength : 5
		}
	    }, {
	    	display:'参数上级代码',
	    	name:'upNo',
	    	newline:false,
	    	type:'hidden'
	    },{
	    	display:'参数上级名称',
	    	name:'upParamName',
	    	newline:false,
	    	type:'hidden'
	    },{
		display : '备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',
		name : 'remark',
		newline : true,
		width : '503',
		validate : {
		    maxlength : 500
		},
		type : 'textarea',
		attr : {
		    style : "resize : none"
		}
	    } ]
	});

	BIONE.loadForm(mainform, {
	    url : "${ctx}/bione/variable/param/${id}"
	});
	$("#mainform input[name=paramTypeName]").val(paramTypeName);
	$("#mainform input[name=paramName]").attr("readonly", "readonly");
	$("#mainform input[name=paramTypeName]").attr("readonly", "readonly");
	$("#mainform input[name=upNo]").attr("readOnly","readOnly");

	jQuery.metadata.setType("attr", "validate");
	BIONE.validate($("#mainform"));
	var buttons = [];
	buttons.push({
	    text : '取消',
	    onclick : function() {
		BIONE.closeDialog("paramModifyWin");
	    }
	});
	buttons.push({
	    text : '保存',
	    onclick : f_modify
	});

	BIONE.addFormButtons(buttons);
    });
    function f_modify() {
	BIONE.submitForm($("#mainform"), function() {
	    BIONE.closeDialogAndReloadParent("paramModifyWin", "maingrid",
		    "修改成功");
	}, function() {
	    BIONE.closeDialog("paramModifyWin", "修改失败");
	});
    }

    function QYBZRenderTypeName() {
	return BIONE.paramTransformer(paramTypeNo,
		'${ctx}/bione/common/getParamTypeName');
    }
</script>
</head>
<body>
	<div id="template.center">
		<form name="mainform" method="post" id="mainform"
			action="${ctx}/bione/variable/param"></form>
	</div>
</body>
</html>