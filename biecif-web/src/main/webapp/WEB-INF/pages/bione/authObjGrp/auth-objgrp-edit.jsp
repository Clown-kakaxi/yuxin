<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
    var groupicon = "${ctx}/images/classics/icons/communication.gif";
    $(function() {
	var mainform = $("#mainform").ligerForm({
	    fields : [ {
		name : 'objgrpId',
		type : 'hidden'
	    }, {
		display : '对象组标识',
		name : 'objgrpNo',
		newline : true,
		type : 'text',
		group : "对象组信息",
		validate : {
		    required : true,
		    maxlength : 32
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

	BIONE.loadForm(mainform, {
	    url : "${ctx}/bione/admin/authObjgrp/${id}"
	});

	$("#mainform input[name=objgrpNo]").attr("readonly", "readonly");

	jQuery.metadata.setType("attr", "validate");
	BIONE.validate($("#mainform"));

	var managers = $.ligerui.find($.ligerui.controls.Input);
	for ( var i = 0, l = managers.length; i < l; i++) {
	    //改变了表单的值，需要调用这个方法来更新ligerui样式
	    managers[i].updateStyle();
	}

	var buttons = [];
	buttons.push({
	    text : '取消',
	    onclick : function() {
		BIONE.closeDialog("objgrpModifyWin");
	    }
	});
	buttons.push({
	    text : '修改',
	    onclick : f_save
	});

	BIONE.addFormButtons(buttons);
    });
    function f_save() {
	BIONE.submitForm($("#mainform"), function() {
	    BIONE.closeDialogAndReloadParent("objgrpModifyWin", "maingrid",
		    "修改成功");
	}, function() {
	    BIONE.closeDialog("objgrpModifyWin", "修改失败");
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