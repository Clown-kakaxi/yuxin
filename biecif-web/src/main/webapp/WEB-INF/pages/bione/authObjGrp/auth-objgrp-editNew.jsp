<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
    var groupicon = "${ctx}/images/classics/icons/communication.gif";
    $(function() {
	$("#mainform").ligerForm({
	    fields : [ {
		display : '对象组标识',
		name : 'objgrpNo',
		newline : true,
		type : 'text',
		group : "对象组信息",
		validate : {
		    required : true,
		    maxlength : 32,
		    remote : "${ctx}/bione/admin/authObjgrp/testObjgrpNo",
		    messages : {
			remote : "对象组标识已存在"
		    }
		},
		groupicon : groupicon
	    }, {
		display : '对象组名称',
		name : 'objgrpName',
		newline : false,
		type : 'text',
		validate : {
		    required : true,
		    maxlength : 100
		}
	    }, {
		display : "是否启用",
		name : "objgrpStsSelect",
		type : "select",
		newline : true,
		options : {
		    valueFieldID : "objgrpSts",
		    initValue : "1",
		    initText : "启用",
		    data : [ {
			text : "启用",
			id : "1"
		    }, {
			text : "停用",
			id : "0"
		    } ]
		}
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

	jQuery.metadata.setType("attr", "validate");
	BIONE.validate($("#mainform"));
	var buttons = [];
	buttons.push({
	    text : '取消',
	    onclick : function() {
		BIONE.closeDialog("objgrpAddWin");
	    }
	});
	buttons.push({
	    text : '保存',
	    onclick : f_save
	});

	BIONE.addFormButtons(buttons);
    });
    function f_save() {
	BIONE.submitForm($("#mainform"), function() {
	    BIONE
		    .closeDialogAndReloadParent("objgrpAddWin", "maingrid",
			    "添加成功");
	}, function() {
	    BIONE.closeDialog("objgrpAddWin", "添加失败");
	});
    }
</script>
</head>
<body>
	<div id="template.center">
		<form name="mainform" method="post" id="mainform"
			action="${ctx}/bione/admin/authObjgrp"></form>
	</div>
</body>
</html>