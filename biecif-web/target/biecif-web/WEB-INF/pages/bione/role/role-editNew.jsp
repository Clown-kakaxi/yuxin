<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
    var groupicon = "${ctx}/images/classics/icons/communication.gif";
    $(function() {
	$("#mainform").ligerForm({
	    fields : [ {
		display : '角色标识',
		name : 'roleNo',
		newline : true,
		type : 'text',
		group : "角色信息",
		validate : {
		    required : true,
		    maxlength : 32,
		    remote : "${ctx}/bione/admin/role/testRoleNo",
		    messages : {
			remote : "角色标识已存在"
		    }
		},
		groupicon : groupicon
	    }, {
		display : '角色名称',
		name : 'roleName',
		newline : false,
		type : 'text',
		validate : {
		    required : true,
		    maxlength : 100
		}
	    }, {
		display : '角色类型',
		name : 'roleTypeValue',
		newline : true,
		type : 'select',
		options : {
		    initValue : '01',
		    valueFieldID : "roleType",
		    data : [ {
			text : '系统角色',
			id : "01"
		    }, {
			text : '业务角色',
			id : '02'
		    } ]
		},
		validate : {
		    required : true
		}
	    }, {
		display : '角色状态',
		name : 'roleStsValue',
		newline : false,
		type : 'select',
		options : {
		    valueFieldID : 'roleSts',
		    initValue : '1',
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
		BIONE.closeDialog("roleAddWin");
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
	    BIONE.closeDialogAndReloadParent("roleAddWin", "maingrid", "添加成功");
	}, function() {
	    BIONE.closeDialog("roleModifyWin", "添加失败");
	});
    }
</script>
</head>
<body>
	<div id="template.center">
		<form name="mainform" method="post" id="mainform"
			action="${ctx}/bione/admin/role"></form>
	</div>
</body>
</html>