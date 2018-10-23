<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">
    var groupicon = "${ctx}/images/classics/icons/communication.gif", mainform;
    $(function() {
    	initForm();
		initButton();
    });
    	
    function initForm() {
		var mainform = $("#mainform").ligerForm({
		    fields : [ {
				name : 'roleId',
				type : 'hidden'
		    }, {
				display : '角色标识<font color="red">*</font>',
				name : 'roleNo',
				newline : true,
				type : 'text',
				group : "角色信息",
				validate : {
				    required : true,
				    maxlength : 32
				},
				groupicon : groupicon
		    }, {
				display : '角色名称<font color="red">*</font>',
				name : 'roleName',
				newline : false,
				type : 'text',
				validate : {
				    required : true,
				    maxlength : 100
				}
		    }, {
				display : '角色类型<font color="red">*</font>',
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
				display : '角色状态<font color="red">*</font>',
				name : 'roleStsValue',
				newline : false,
				type : 'select',
				options : {
				    valueFieldID : 'roleSts',
				    data : [ {
					text : '启用',
					id : '1'
				    }, {
					text : '停用',
					id : '0'
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
		    }]
		});
	
		BIONE.loadForm(mainform, {
		    url : "${ctx}/bione/admin/role/${id}"
		});
		var managers = $.ligerui.find($.ligerui.controls.Input);
		for ( var i = 0, l = managers.length; i < l; i++) {
		    //改变了表单的值，需要调用这个方法来更新ligerui样式
		    managers[i].updateStyle();
		}
    }
    
    function initButton() {
		var buttons = [];
	
		$("#mainform input[name=roleNo]").attr("readonly", "readonly");
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform"));
		
		buttons.push({
		    text : '取消',
		    onclick : function() {
			BIONE.closeDialog("roleModifyWin");
		    }
		});
		buttons.push({
		    text : '修改',
		    onclick : f_save
		});
		BIONE.addFormButtons(buttons);
    };
    function f_save() {
		BIONE.submitForm($("#mainform"), function() {
		    BIONE.closeDialogAndReloadParent("roleModifyWin", "maingrid",
			    "修改成功");
		}, function() {
		    BIONE.closeDialog("roleModifyWin", "修改失败");
		});
    };    
	function closeDialog(){
		BIONE.closeDialog("roleModifyWin");
	};
</script>
</head>
<body>
	<div id="template.center">
		<form name="mainform" method="post" id="mainform"
			action="${ctx}/bione/admin/role"></form>
	</div>
</body>
</html>